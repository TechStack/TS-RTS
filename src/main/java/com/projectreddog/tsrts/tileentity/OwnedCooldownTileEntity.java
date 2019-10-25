package com.projectreddog.tsrts.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OwnedCooldownTileEntity extends OwnedTileEntity implements ITickableTileEntity {

	public int coolDownReset = 10 * 20;
	public int coolDownRemainig = coolDownReset;

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

				ActionAfterCooldown();
				coolDownRemainig = coolDownReset;
			}
		}
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
