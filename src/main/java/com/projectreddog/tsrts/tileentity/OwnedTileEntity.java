package com.projectreddog.tsrts.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OwnedTileEntity extends TileEntity {

	public OwnedTileEntity(TileEntityType tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}

	private String onwersName;

	public String getOwner() {
		return onwersName;
	}

	public void setOwner(String onwersName) {
		this.onwersName = onwersName;
		this.markDirty();
	}

	public ScorePlayerTeam getTeam() {
		return this.world.getScoreboard().getPlayersTeam(onwersName);
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
