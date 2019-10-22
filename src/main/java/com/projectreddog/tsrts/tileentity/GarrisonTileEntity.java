package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.init.ModBlocks;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class GarrisonTileEntity extends TileEntity implements ITickableTileEntity {

	public GarrisonTileEntity() {
		super(ModBlocks.GARRISON_TILE_ENTITY_TYPE);
	}

	@Override
	public void tick() {
		if (!world.isRemote) {
		}
	}

}
