package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class WallTileEntity extends OwnedCooldownTileEntity {

	// TODO: add these two to the NBT
	public int[][] startHeights;
	public Direction storedDirection;

	public WallTileEntity() {
		super(ModBlocks.TOWN_WALL_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null && getTeam() != null) {

		}
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

					if (currentStage == Stage.RUBBLE) {
						Utilities.BuildWall(world, this.getRallyPoint(), this.storedDirection, this.getOwner(), this.getTeam().getName(), true, startHeights);

						StructureLost();
						DecreaseCount();

					}
				}

			}
			if (currentStage == Stage.RUBBLE) {
				rubbleTimerRemaining--;
				writeDirty = true;
				if (rubbleTimerRemaining <= 0) {
					Utilities.BuildWall(world, this.getRallyPoint(), this.storedDirection, this.getOwner(), this.getTeam().getName(), true, startHeights);

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

	@Override
	public void IncreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setWalls(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getWalls() + 1);

	}

	@Override
	public void DecreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setWalls(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getWalls() - 1);

	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToTeam(this.getWorld(), this.getTeam().getName(), "tsrts.destroy.wall");

	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_WALL.get();
	}

}
