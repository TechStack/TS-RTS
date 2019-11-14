package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.blocks.OwnedBlock;
import com.projectreddog.tsrts.data.StructureData;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TEOwnerChangedPacketToClient;
import com.projectreddog.tsrts.tileentity.interfaces.ResourceGenerator;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.TeamProperty;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class OwnedTileEntity extends TileEntity {

	public OwnedTileEntity(TileEntityType tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}

	private String onwersName;
	private StructureData structureData;

	public StructureData getStructureData() {
		return structureData;
	}

	public void setStructureData(StructureData structureData) {
		this.structureData = structureData;
		this.markDirty();
	}

	private BlockPos rallyPoint;

	public BlockPos getRallyPoint() {
		return rallyPoint;
	}

	public void setRallyPoint(BlockPos rallyPoint) {
		this.rallyPoint = rallyPoint;
		this.markDirty();
	}

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
		boolean hasRallyPoint = false;
		if (rallyPoint != null) {
			hasRallyPoint = true;
			nbt.putInt("rallypointx", rallyPoint.getX());
			nbt.putInt("rallypointy", rallyPoint.getY());
			nbt.putInt("rallypointz", rallyPoint.getZ());
		}
		nbt.putBoolean("hasRallyPoint", hasRallyPoint);
		int resourceOrdnial = -1;
		if (this instanceof ResourceGenerator) {
			ResourceGenerator re = (ResourceGenerator) this;
			resourceOrdnial = re.getResource().ordinal();
		}
		compound.putInt("resourceordnial", resourceOrdnial);

		structureData.writeToNbt(compound);

		return nbt;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		onwersName = compound.getString("owner");
		if (onwersName.contentEquals("")) {
			onwersName = null;
		}

		boolean hasRallyPoint = compound.getBoolean("hasRallyPoint");

		if (hasRallyPoint) {
			int x = compound.getInt("rallypointx");
			int y = compound.getInt("rallypointy");
			int z = compound.getInt("rallypointz");
			rallyPoint = new BlockPos(x, y, z);
		}
		if (this instanceof ResourceGenerator) {
			ResourceGenerator re = (ResourceGenerator) this;
			int resourceOrdnial = compound.getInt("resourceordnial");
			if (resourceOrdnial >= 0) {
				re.setResource(TeamInfo.Resources.values()[resourceOrdnial]);
			}
		}
		structureData = new StructureData(compound);
	}

}
