package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OwnedCooldownTileEntity extends OwnedTileEntity implements ITickableTileEntity {

	public int coolDownReset = 10 * 20;
	public int coolDownRemainig = coolDownReset;
	public float priorHealth = -1;
	private Stage currentStage = Stage.FULL_HEALTH;
	private Stage priorStage = Stage.FULL_HEALTH;
	private boolean justLoaded = true;
	private int justLoadedRemaining = 20;
	private int rubbleTimerRemaining = 20 * 30;
	protected float health;

	public enum Stage {
		FULL_HEALTH, HALF_DESTROYED, RUBBLE
	}

	public OwnedCooldownTileEntity(TileEntityType tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}

	// TSRTS.LOGGER.info("Owner is :" + getOwner());

	// TSRTS.LOGGER.info(world.getScoreboard().getPlayersTeam(getOwner()).getName());
	@Override
	public void tick() {
		if (!world.isRemote) {
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
				this.markDirty();
				if (getHealth() < 80) {
					currentStage = Stage.HALF_DESTROYED;
					this.markDirty();
				}
				if (getHealth() <= 0) {
					currentStage = Stage.RUBBLE;
					this.markDirty();
				}

				if (priorStage != currentStage) {
					priorStage = currentStage;
					this.markDirty();
					if (getStructureData() != null) {
						if (currentStage == Stage.HALF_DESTROYED) {
							if (getStructureData().getTemplate50() != null) {
								Utilities.LoadStructure(this.world, getStructureData().getTemplate50(), getStructureData(), getOwner(), false);
							}
						}
						if (currentStage == Stage.RUBBLE) {
							Utilities.LoadStructure(this.world, getStructureData().getTemplate0(), getStructureData(), getOwner(), false);
						}
					}
				}

			}
			if (currentStage == Stage.RUBBLE) {
				rubbleTimerRemaining--;
				this.markDirty();
				if (rubbleTimerRemaining <= 0) {
					if (getStructureData() != null) {

						Utilities.clearAreaTELast(world, getStructureData().getSpawnPoint(), getStructureData().getDirection(), getStructureData().getSize());
					}
				}
			}
			coolDownRemainig = coolDownRemainig - 1;
			this.markDirty();
			if (coolDownRemainig <= 0) {
				if (Config.CONFIG_GAME_MODE.get() == Config.Modes.RUN) {
					if (getHealth() > 0) {
						ActionAfterCooldown();
					}
				}
				coolDownRemainig = coolDownReset;
				this.markDirty();
			}
		}

	}

	public void setHealth(float inhealth) {

		this.health = inhealth;
		this.markDirty();

	}

	public float getHealth() {

		return health;

	}

	public void ActionAfterCooldown() {

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.putInt("coolDownReset", coolDownReset);
		nbt.putInt("coolDownRemainig", coolDownRemainig);
		nbt.putFloat("priorHealth", priorHealth);
		nbt.putFloat("health", health);
		nbt.putInt("currentStage", currentStage.ordinal());
		nbt.putInt("priorStage", priorStage.ordinal());
		nbt.putInt("rubbleTimerRemaining", rubbleTimerRemaining);

		return nbt;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		coolDownReset = compound.getInt("coolDownReset");
		coolDownRemainig = compound.getInt("coolDownRemainig");
		priorHealth = compound.getFloat("priorHealth");
		health = compound.getFloat("health");

		currentStage = Stage.values()[compound.getInt("currentStage")];

		priorStage = Stage.values()[compound.getInt("priorStage")];
		// TODO: add back the rubble timer after these are all scanned
		// rubbleTimerRemaining = compound.getInt("rubbleTimerRemaining");
	}

}
