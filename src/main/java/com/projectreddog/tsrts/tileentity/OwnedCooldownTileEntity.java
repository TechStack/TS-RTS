package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.containers.BasicContainer;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public class OwnedCooldownTileEntity extends OwnedTileEntity implements ITickableTileEntity, INamedContainerProvider, ITEGuiButtonHandler {

	public int coolDownReset = 10 * 20;
	public int coolDownRemainig = coolDownReset;
	public float priorHealth = -1;
	protected Stage currentStage = Stage.FULL_HEALTH;
	protected Stage priorStage = Stage.FULL_HEALTH;
	protected boolean justLoaded = true;
	protected int justLoadedRemaining = 20;
	protected int rubbleTimerRemaining = 20 * 30;
	protected float health;

	protected boolean enabled = true;
	protected boolean writeDirty = false;

	protected boolean shouldIncreaseCounts = true;

	public enum Stage {
		FULL_HEALTH, HALF_DESTROYED, RUBBLE
	}

	public OwnedCooldownTileEntity(TileEntityType tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}

	public void IncreaseCount() {

	}

	public void DecreaseCount() {

	}

	public void StructureLost() {

	}

	@Override
	public void tick() {
		if ((!world.isRemote && enabled) && Config.CONFIG_GAME_MODE.get() != Config.Modes.WORLDBUILDER) {
			writeDirty = false;
			if (shouldIncreaseCounts) {
				IncreaseCount();
				shouldIncreaseCounts = false;
			}

			if (justLoaded) {
				// just loaded in so give the entities time to load before checking health and all that.
				justLoadedRemaining--;
				if (justLoadedRemaining <= 0) {
					justLoaded = false;

				} else {
					return;
				}
			}
			if (priorHealth != getHealth()) {
				priorHealth = getHealth();
				writeDirty = true;
				if (getHealth() < getDamagedHealthThreashold()) {
					currentStage = Stage.HALF_DESTROYED;
					writeDirty = true;
				}
				if (getHealth() <= 0) {
					currentStage = Stage.RUBBLE;
					writeDirty = true;
				}

				if (priorStage != currentStage) {
					priorStage = currentStage;
					writeDirty = true;
					if (getStructureData() != null) {
						if (currentStage == Stage.HALF_DESTROYED) {
							if (getStructureData().getTemplate50() != null) {
								Utilities.LoadStructure(this.world, getStructureData().getTemplate50(), getStructureData(), getOwner(), false, false, 0);
							}
						}
						if (currentStage == Stage.RUBBLE) {
							Utilities.LoadStructure(this.world, getStructureData().getTemplate0(), getStructureData(), getOwner(), false, false, 0);

							StructureLost();
							DecreaseCount();
							AfterDeathAction();
						}
					}
				}

			}
			if (currentStage == Stage.RUBBLE) {
				rubbleTimerRemaining--;
				writeDirty = true;
				if (rubbleTimerRemaining <= 0) {
					if (getStructureData() != null) {
						Utilities.clearAreaTELast(world, getStructureData().getSpawnPoint(), getStructureData().getDirection(), getStructureData().getSize());
					}
				}
			}
			coolDownRemainig = coolDownRemainig - 1;
			writeDirty = true;
			if (coolDownRemainig <= 0) {
				if (Config.CONFIG_GAME_MODE.get() == Config.Modes.RUN) {
					if (getHealth() > 0) {
						ActionAfterCooldown();
					}
				}
				coolDownRemainig = coolDownReset;
				writeDirty = true;
			}
			if (writeDirty) {
				this.markDirty();
			}
		}

	}

	public float getDamagedHealthThreashold() {
		return 80;
	}

	public void setHealth(float inhealth) {

		this.health = inhealth;
		this.markDirty();

	}

	public float getHealth() {

		return health;

	}

	public void AfterDeathAction() {

	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new BasicContainer(p_createMenu_1_, this.world, this.getPos(), playerInventory);
	}

	public void ActionAfterCooldown() {

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.putInt("coolDownReset", coolDownReset);
		nbt.putInt("coolDownRemainig", coolDownRemainig);

		if (Config.CONFIG_GAME_MODE.get() != Config.Modes.WORLDBUILDER) {
			nbt.putInt("currentStage", currentStage.ordinal());
			nbt.putInt("priorStage", priorStage.ordinal());
			nbt.putFloat("priorHealth", priorHealth);
			nbt.putFloat("health", health);
		} else {
			// world builder
			nbt.putInt("currentStage", Stage.FULL_HEALTH.ordinal());
			nbt.putInt("priorStage", Stage.FULL_HEALTH.ordinal());
		}

		nbt.putInt("rubbleTimerRemaining", rubbleTimerRemaining);
		if (Config.CONFIG_GAME_MODE.get() != Config.Modes.WORLDBUILDER) {

			nbt.putBoolean("enabled", enabled);

			nbt.putBoolean("shouldIncreaseCounts", shouldIncreaseCounts);
		}

		return nbt;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		coolDownReset = compound.getInt("coolDownReset");
		coolDownRemainig = compound.getInt("coolDownRemainig");

		if (compound.contains("priorHealth")) {
			priorHealth = compound.getFloat("priorHealth");
		}

		if (compound.contains("health")) {
			health = compound.getFloat("health");
		}

		if (compound.contains("currentStage")) {
			currentStage = Stage.values()[compound.getInt("currentStage")];
		} else {
			currentStage = Stage.FULL_HEALTH;
		}

		if (compound.contains("priorStage")) {
			priorStage = Stage.values()[compound.getInt("priorStage")];
		} else {
			priorStage = Stage.FULL_HEALTH;
		}

		if (compound.contains("enabled")) {
			enabled = compound.getBoolean("enabled");
		}

		if (Config.CONFIG_GAME_MODE.get() != Config.Modes.WORLDBUILDER && compound.contains("shouldIncreaseCounts")) {
			shouldIncreaseCounts = compound.getBoolean("shouldIncreaseCounts");
		}
		// TODO: add back the rubble timer after these are all scanned
		// rubbleTimerRemaining = compound.getInt("rubbleTimerRemaining");
	}

	@Override
	public void HandleGuiButton(int buttonId, PlayerEntity player) {
		switch (buttonId) {
		case Reference.GUI_BUTTON_ENABLE_TE:
			enabled = true;
			break;
		case Reference.GUI_BUTTON_DISABLE_TE:
			enabled = false;
			break;

		default:
			break;
		}
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

}
