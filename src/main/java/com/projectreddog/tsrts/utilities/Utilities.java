package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.entities.UnitEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Utilities {

	public static void SpawnUnitForTeam(EntityType entityType, String Owner, World world, BlockPos pos, ScorePlayerTeam team) {
		Entity e = SpawnUnit(entityType, Owner, world, pos);
		if (team != null) {
			world.getScoreboard().addPlayerToTeam(e.getCachedUniqueIdString(), team);
		}

	}

	public static Entity SpawnUnit(EntityType entityType, String Owner, World world, BlockPos pos) {
		Entity e = entityType.spawn(world, null, null, pos, SpawnReason.TRIGGERED, true, true);
		if (e instanceof UnitEntity) {
			UnitEntity ue = (UnitEntity) e;
			ue.setOwnerName(Owner);
		}

		return e;

	}

}
