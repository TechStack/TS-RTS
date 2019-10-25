package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.init.ModEntities;
import com.projectreddog.tsrts.utilities.Utilities;

public class GarrisonTileEntity extends OwnedCooldownTileEntity {

	public GarrisonTileEntity() {
		super(ModBlocks.GARRISON_TILE_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {
			// ModEntities.MINION.spawn(world, null, null, this.pos, SpawnReason.TRIGGERED, true, true);

			Utilities.SpawnUnitForTeam(ModEntities.MINION, this.getOwner(), this.getWorld(), this.getPos(), this.getTeam());

//
//			MinionEntity me = new MinionEntity(null, world);
//
//			world.addEntity(me);
		}
	}
}
