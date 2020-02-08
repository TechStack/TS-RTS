package com.projectreddog.tsrts.client.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.client.gui.toast.AlertToast;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModResearch;
import com.projectreddog.tsrts.reference.Reference.RTS_COSTS;
import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;
import com.projectreddog.tsrts.tileentity.OwnedTileEntity;
import com.projectreddog.tsrts.utilities.AlertToastBackgroundType;
import com.projectreddog.tsrts.utilities.MapStructureUtilities;
import com.projectreddog.tsrts.utilities.PlayerSelections;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.UnitQueues;
import com.projectreddog.tsrts.utilities.Utilities;
import com.projectreddog.tsrts.utilities.data.MapStructureData;

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

	public static void SendResearchUnlockToClient(String key, String teamName, boolean unLocked) {
		ModResearch.getResearch(key).setUnlocked(unLocked, TeamEnum.getIDFromName(teamName));
	}

	public static void SendTeamInfoPacketToClient(int[] resourceAmt, String teamName, String currentResearchKey, int currentWorkAmount, int fullWorkAmount, int teamPopulationCap, int[] unitCount) {
		// TSRTS.LOGGER.info("Client recieved team packet of resource info for team: " + teamName + " resource ord 0 :" + resourceAmt[0]);
		// should be on CLIENT !
		if (TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)] == null) {
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)] = new TeamInfo();
		}
		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].SetResourceArray(resourceAmt);

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].setCurrenResearchKey(currentResearchKey);
		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].setCurrenResearchWorkRemaining(currentWorkAmount);
		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].setFullResearchWorkRemaining(fullWorkAmount);
		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].setTeamPopulationCap(teamPopulationCap);

		for (int i = 0; i < unitCount.length; i++) {
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].setUnitCount(UNIT_TYPES.values()[i], unitCount[i]);

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

	public static void UnitQueueChangedPacketToClient(int teamOrd, List<UNIT_TYPES> barrcksQueue, List<UNIT_TYPES> archeryRangeQueue, List<UNIT_TYPES> stablesQueue, List<UNIT_TYPES> siegeWorkshopQueue, boolean infinateBarracksQueue, boolean infinateArcheryRangeQueue, boolean infinateStablesQueue, boolean infinateSiegeWorkshopQueue) {
		TSRTS.TeamQueues[teamOrd] = new UnitQueues(barrcksQueue, archeryRangeQueue, stablesQueue, siegeWorkshopQueue, infinateBarracksQueue, infinateArcheryRangeQueue, infinateStablesQueue, infinateSiegeWorkshopQueue);
	}

	public static void PlayerSelectionChangedPacketToClient(int[] entityIds) {
		// TSRTS.LOGGER.info("CONTROLGROUPBUG:" + "in PlayerSelectionChangedPacketToClient( handler) for " + entityIds.toString());

		if (Minecraft.getInstance() != null && Minecraft.getInstance().player != null) {
			String playerScoreboardName = Minecraft.getInstance().player.getScoreboardName();
			PlayerSelections ps = new PlayerSelections();
			ps.hasChanged = true;

			for (int i = 0; i < entityIds.length; i++) {
				ps.selectedUnits.add(entityIds[i]);
			}

			TSRTS.playerSelections.put(playerScoreboardName, ps);

		}

	}

	public static void PlayerReadyUpPacketToClient(int playerEntityID, Boolean isReady) {
		World world = Minecraft.getInstance().player.world;
		Entity e = world.getEntityByID(playerEntityID);
		// TSRTS.LOGGER.info("READY UP PACKET CLIENT: " + isReady);

		if (e instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) e;

			Utilities.setPlayerReady(player, isReady);
		}
	}

	public static void CostsPacketToClient(RTS_COSTS costsType, ResourceValues rv) {
		switch (costsType) {
		case ARCHER:
			Config.CONFIG_UNIT_COSTS_ARCHER = rv;
			break;
		case ARCHERY_RANGE:
			Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE = rv;
			break;
		case BARRACKS:
			Config.CONFIG_BUILDING_COSTS_BARRACKS = rv;
			break;
		case FARM:
			Config.CONFIG_BUILDING_COSTS_FARM = rv;
			break;
		case GATE:
			Config.CONFIG_BUILDING_COSTS_GATE = rv;
			break;
		case LANCER:
			Config.CONFIG_UNIT_COSTS_LANCER = rv;
			break;
		case LUMBER_YARD:
			Config.CONFIG_BUILDING_COSTS_LUMBER_YARD = rv;
			break;
		case MINE_DIAMOND:
			Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND = rv;
			break;
		case MINE_EMERALD:
			Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD = rv;
			break;
		case MINE_GOLD:
			Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD = rv;
			break;
		case MINE_IRON:
			Config.CONFIG_BUILDING_COSTS_MINESITE_IRON = rv;
			break;
		case MINE_STONE:
			Config.CONFIG_BUILDING_COSTS_MINESITE_STONE = rv;
			break;
		case MINION:
			Config.CONFIG_UNIT_COSTS_MINION = rv;
			break;
		case PIKEMAN:
			Config.CONFIG_UNIT_COSTS_PIKEMAN = rv;
			break;
		case RESEARCH_ADVANCED_ARMOR:
			ModResearch.getResearch("advcedarmor").setRv(rv);
			break;
		case RESEARCH_ARCHER:
			ModResearch.getResearch("archer").setRv(rv);
			break;
		case RESEARCH_BATTERING_RAM:
			ModResearch.getResearch("batteringrams").setRv(rv);
			break;
		case RESEARCH_CENTER:
			Config.CONFIG_BUILDING_COSTS_RESEARCH_CENTER = rv;
			break;
		case RESEARCH_CROSSBOW:
			ModResearch.getResearch("crossbow").setRv(rv);
			break;
		case RESEARCH_LANCER:
			ModResearch.getResearch("lancer").setRv(rv);
			break;
		case RESEARCH_MARKETPLACE:
			ModResearch.getResearch("marketplace").setRv(rv);
			break;
		case RESEARCH_MINION:
			ModResearch.getResearch("minion").setRv(rv);
			break;
		case RESEARCH_PIKEMAN:
			ModResearch.getResearch("pikeman").setRv(rv);
			break;
		case RESEARCH_SIEGE_WORKSHOP:
			ModResearch.getResearch("siegeworkshop").setRv(rv);
			break;
		case RESEARCH_TOWNHALL:
			ModResearch.getResearch("townhall").setRv(rv);
			break;
		case RESEARCH_TREBUCHET:
			ModResearch.getResearch("trebuchet").setRv(rv);
			break;
		case RESEARCH_WALL:
			ModResearch.getResearch("wall").setRv(rv);
			break;
		case RESEARCH_WATCHTOWER:
			ModResearch.getResearch("watchtower").setRv(rv);
			break;
		case STABLES:
			Config.CONFIG_BUILDING_COSTS_STABLES = rv;
			break;
		case WALL:
			Config.CONFIG_BUILDING_COSTS_WALL = rv;
			break;
		case WALL_STEPS:
			Config.CONFIG_BUILDING_COSTS_WALL_STEPS = rv;
			break;
		case WATCH_TOWER:
			Config.CONFIG_BUILDING_COSTS_WATCH_TOWER = rv;
			break;
		case MARKETPLACE:
			break;
		case RESEARCH_ARMOR:
			ModResearch.getResearch("armor").setRv(rv);
			break;
		case RESEARCH_BLACKSMITHING:
			ModResearch.getResearch("blacksmithing").setRv(rv);
			break;
		case SIEGE_WORKSHOP:
			ModResearch.getResearch("siegeworkshop").setRv(rv);
			break;
		case ARMORY:
			Config.CONFIG_BUILDING_COSTS_ARMORY = rv;
		case SAPPER:
			Config.CONFIG_UNIT_COSTS_SAPPER = rv;
		case LONGBOWMEN:
			Config.CONFIG_UNIT_COSTS_LONGBOWMEN = rv;

			break;
		default:
			throw new IllegalArgumentException("Case not handled");

		}
	}

	public static void SendMapDataPacketToClient(HashMap<BlockPos, MapStructureData> structures) {
		TSRTS.Structures.clear();
		for (Map.Entry<BlockPos, MapStructureData> entry : structures.entrySet()) {
			MapStructureData msd = entry.getValue();
			MapStructureUtilities.Add(msd.getPosition(), msd);
		}
	}

}
