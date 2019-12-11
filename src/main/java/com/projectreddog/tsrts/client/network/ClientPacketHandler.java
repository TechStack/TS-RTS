package com.projectreddog.tsrts.client.network;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.client.gui.toast.AlertToast;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.tileentity.OwnedTileEntity;
import com.projectreddog.tsrts.utilities.AlertToastBackgroundType;
import com.projectreddog.tsrts.utilities.PlayerSelections;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

	public static void AlertToastToClient(String title, String subTitle, AlertToastBackgroundType backgroundType) {
		Minecraft.getInstance().getToastGui().add(new AlertToast(title, subTitle, backgroundType));
	}

	public static void SendTeamInfoPacketToClient(int[] resourceAmt, String teamName) {
		TSRTS.LOGGER.info("Client recieved team packet of resource info for team: " + teamName + " resource ord 0 :" + resourceAmt[0]);
		// should be on CLIENT !
		if (TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)] == null) {
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)] = new TeamInfo();
		}
		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].SetResourceArray(resourceAmt);

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

	public static void PlayerSelectionChangedPacketToClient(int[] entityIds) {
		TSRTS.LOGGER.info("CONTROLGROUPBUG:" + "in PlayerSelectionChangedPacketToClient( handler)  for " + entityIds.toString());

		if (Minecraft.getInstance() != null && Minecraft.getInstance().player != null) {
			String playerScoreboardName = Minecraft.getInstance().player.getScoreboardName();
			PlayerSelections ps = new PlayerSelections();
			for (int i = 0; i < entityIds.length; i++) {
				ps.selectedUnits.add(entityIds[i]);
			}

			TSRTS.playerSelections.put(playerScoreboardName, ps);

		}

	}

	public static void PlayerReadyUpPacketToClient(int playerEntityID, Boolean isReady) {
		World world = Minecraft.getInstance().player.world;
		Entity e = world.getEntityByID(playerEntityID);
		TSRTS.LOGGER.info("READY UP PACKET CLIENT: " + isReady);

		if (e instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) e;

			Utilities.setPlayerReady(player, isReady);
		}
	}

}
