package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.hanlder.Config;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OwnedCooldownTileEntity extends OwnedTileEntity implements ITickableTileEntity {

	public int coolDownReset = 10 * 20;
	public int coolDownRemainig = coolDownReset;
	public float priorHealth = -1;
	private Stage currentStage = Stage.FULL_HEALTH;
	private Stage priorStage = Stage.FULL_HEALTH;

	private int rubbleTimerRemaining = 20 * 30;

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

			coolDownRemainig = coolDownRemainig - 1;
			if (coolDownRemainig <= 0) {
				if (Config.CONFIG_GAME_MODE.get() == Config.Modes.RUN) {
					ActionAfterCooldown();
				}
				coolDownRemainig = coolDownReset;
			}

			if (priorHealth != getHealth()) {
				priorHealth = getHealth();

				if (getHealth() < 80) {
					currentStage = Stage.HALF_DESTROYED;

				}
				if (getHealth() <= 0) {
					currentStage = Stage.RUBBLE;
				}

				if (priorStage != currentStage) {
					priorStage = currentStage;
					if (getStructureData() != null) {
						if (currentStage == Stage.HALF_DESTROYED) {
							Utilities.LoadStructure(this.world, getStructureData().getTemplate50(), getStructureData(), getOwner(), false);
						}
						if (currentStage == Stage.RUBBLE) {
							Utilities.LoadStructure(this.world, getStructureData().getTemplate0(), getStructureData(), getOwner(), false);
						}
					}
				}

			}
			if (currentStage == Stage.RUBBLE) {
				rubbleTimerRemaining--;

				if (rubbleTimerRemaining <= 0) {
					if (getStructureData() != null) {
						Utilities.clearAreaTELast(world, getStructureData().getSpawnPoint(), getStructureData().getDirection(), getStructureData().getSize());
					}
				}
			}
		}

	}

	public float getHealth() {

		float health = 0;
		if (targetEntityIds != null) {
			for (int i = 0; i < this.targetEntityIds.length; i++) {
				Entity e = this.world.getEntityByID(this.targetEntityIds[i]);
				if (e instanceof TargetEntity) {
					// its one of ours YEA!
					TargetEntity targetEnttiy = (TargetEntity) e;
					health = health + targetEnttiy.getHealth();
				}
			}
		}

		return health;
	}

	public void ActionAfterCooldown() {

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.putInt("coolDownReset", coolDownReset);
		nbt.putInt("coolDownRemainig", coolDownRemainig);

		return nbt;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		coolDownReset = compound.getInt("coolDownReset");
		coolDownRemainig = compound.getInt("coolDownRemainig");

	}

}
