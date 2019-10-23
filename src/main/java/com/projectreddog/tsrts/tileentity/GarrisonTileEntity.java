package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.entities.MinionEntity;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.init.ModEntities;

import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class GarrisonTileEntity extends TileEntity implements ITickableTileEntity {

	private String onwersName;

	public GarrisonTileEntity() {
		super(ModBlocks.GARRISON_TILE_ENTITY_TYPE);
	}

	// TSRTS.LOGGER.info("Owner is :" + getOwner());

	// TSRTS.LOGGER.info(world.getScoreboard().getPlayersTeam(getOwner()).getName());
	@Override
	public void tick() {
		if (!world.isRemote) {
			if (getOwner() != null) {
				ModEntities.MINION.spawn(world, null, null, this.pos, SpawnReason.TRIGGERED, true, true);

				MinionEntity me = new MinionEntity(null, world);

				world.addEntity(me);
			}
		}
	}

	public String getOwner() {
		return onwersName;
	}

	public void setOwner(String onwersName) {
		this.onwersName = onwersName;
		this.markDirty();
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.putString("owner", onwersName);

		return nbt;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		onwersName = compound.getString("owner");
	}

}
