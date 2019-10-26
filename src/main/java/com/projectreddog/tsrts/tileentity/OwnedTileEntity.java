package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.blocks.OwnedBlock;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TEOwnerChangedPacketToClient;
import com.projectreddog.tsrts.utilities.TeamProperty;

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
		if (!world.isRemote) {
			// from server send to others
			ModNetwork.SendToALLPlayers(new TEOwnerChangedPacketToClient(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), this.onwersName));
			if (this.onwersName != null) {

				this.world.setBlockState(this.pos, world.getBlockState(pos).getBlockState().with(OwnedBlock.TEAM, TeamProperty.getTeamForName(getTeam().getName())), 3);
			}
		}
	}

	public ScorePlayerTeam getTeam() {
		return this.world.getScoreboard().getPlayersTeam(onwersName);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		if (onwersName != null) {
			nbt.putString("owner", onwersName);
		} else {
			nbt.putString("owner", "");
		}

		return nbt;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		onwersName = compound.getString("owner");
		if (onwersName.contentEquals("")) {
			onwersName = null;
		}
	}

}
