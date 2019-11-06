package com.projectreddog.tsrts.client.network;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.tileentity.OwnedTileEntity;
import com.projectreddog.tsrts.utilities.TeamInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ClientPacketHandler {

	public static void EntityOwnerChangedPacketToClient(int entityID, String ownerName) {

		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null) {
			Entity e = player.world.getEntityByID(entityID);
			if (e instanceof UnitEntity) {
				UnitEntity ue = (UnitEntity) e;
				ue.setOwnerName(ownerName);
			} else if (e instanceof TargetEntity) {
				TargetEntity te = (TargetEntity) e;
				te.setOwnerName(ownerName);
			}
		}
	}

	public static void SendTeamInfoPacketToClient(int[] resourceAmt, String teamName) {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null) {
			// should be on CLIENT !
			TeamInfo ti = new TeamInfo();
			ti.SetResourceArray(resourceAmt);
			TSRTS.teamInfoMap.put(teamName, ti);

		}
	}

	public static void TEOwnerChangedPacketToClient(int posX, int posY, int posZ, String ownerName) {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null) {
			TileEntity te = player.world.getTileEntity(new BlockPos(posX, posY, posZ));
			if (te instanceof OwnedTileEntity) {
				OwnedTileEntity ote = (OwnedTileEntity) te;
				ote.setOwner(ownerName);
			}
		}
	}
}
