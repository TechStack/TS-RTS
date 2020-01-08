package com.projectreddog.tsrts.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.TSRTS.GAMESTATE;
import com.projectreddog.tsrts.handler.Config.Modes;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.init.ModResearch;
import com.projectreddog.tsrts.network.ResearchUnlockedPacketToClient;
import com.projectreddog.tsrts.network.UnitQueueChangedPacketToClient;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.OwnedCooldownTileEntity;
import com.projectreddog.tsrts.tileentity.TownHallTileEntity;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.TeamInfo.Resources;
import com.projectreddog.tsrts.utilities.Utilities;
import com.projectreddog.tsrts.utilities.data.MapStructureData;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ServerEvents {

	private static int coolDownAmountRest = 20 * 10;
	private static int coolDownAmountRemaining = coolDownAmountRest;
	private static boolean writeBuildingHeader = true;
	private static boolean writeUnitHeader = true;

	private static boolean[] hasPlacedTownHall = { false, false, false, false };

	private static MinecraftServer server;

	private static BlockPos waveSurvivalTownhallPos = null;

	private static BlockPos waveSurvivalSpawnPoint = null;

	private static int wavertsTicksScienceLastSpawn = 0;
	private static boolean waveSurvivalSetupComplete = false;
	private static int wavertsWaveCount = 0;

	public static ScorePlayerTeam WAVE_SURVIVAL_TEAM = null;

	public static void onWaveSruvivalTick() {
		wavertsTicksScienceLastSpawn++;

		if (wavertsTicksScienceLastSpawn >= 9) {

			wavertsTicksScienceLastSpawn = 0;
			ServerWorld world = server.getWorld(DimensionType.OVERWORLD);

			if ((!waveSurvivalSetupComplete)) {
				waveSurvivalSetupComplete = true;
				for (Map.Entry<BlockPos, MapStructureData> entry : TSRTS.Structures.entrySet()) {
					BlockPos bp = entry.getKey();
					MapStructureData msd = entry.getValue();

					if (msd.getTeamName().equals(Reference.WAVE_SURVIAL_AI_TEAM_NAME)) {
						// found our AI Team here and need to process this to check for the town hall
						TileEntity te = world.getTileEntity(bp);
						if (te instanceof TownHallTileEntity) {
							TownHallTileEntity townHall = (TownHallTileEntity) te;
							waveSurvivalTownhallPos = bp;
							waveSurvivalSpawnPoint = townHall.getRallyPoint();
						}

					}
				}
			}

			int targettype = world.rand.nextInt(2);
			BlockPos target = null;
			if (targettype == 0) {
				target = onWaveSruvivalChooseTownHallTarget(world);
			} else if (targettype == 1) {
				target = onWaveSruvivalChooseHighValueTarget(world);
			}

			if (target != null) {
				for (int i = 0; i < ((int) (wavertsWaveCount * wavertsWaveCount * .5)); i++) {
					Utilities.SpawnUnitForTeam(Reference.UNIT_ID_MINION, Reference.WAVE_SURVIAL_AI_NAME, world, waveSurvivalSpawnPoint, WAVE_SURVIVAL_TEAM, target);
				}
			}
			wavertsWaveCount++;
		}
	}

	public static BlockPos onWaveSruvivalChooseHighValueTarget(World world) {

		int highestCost = 0;
		BlockPos hightestCostbp = null;
		for (Map.Entry<BlockPos, MapStructureData> entry : TSRTS.Structures.entrySet()) {
			BlockPos bp = entry.getKey();
			MapStructureData msd = entry.getValue();
			if (!(msd.getTeamName().equals(Reference.WAVE_SURVIAL_AI_TEAM_NAME))) {
				// found our AI Team here and need to process this to check for the town hall
				TileEntity te = world.getTileEntity(bp);
				if (!(te instanceof TownHallTileEntity)) {
					if (te instanceof OwnedCooldownTileEntity) {
						OwnedCooldownTileEntity cooldownTileEntity = (OwnedCooldownTileEntity) te;
						int currentCosts = Utilities.getTotalCostsForStructureType(cooldownTileEntity.getStructureType());
						if (highestCost < currentCosts) {
							highestCost = currentCosts;
							hightestCostbp = bp;
						}
					}

				}
			}
		}
		return hightestCostbp;

	}

	public static BlockPos onWaveSruvivalChooseTownHallTarget(World world) {
		for (Map.Entry<BlockPos, MapStructureData> entry : TSRTS.Structures.entrySet()) {
			BlockPos bp = entry.getKey();
			MapStructureData msd = entry.getValue();
			if (!(msd.getTeamName().equals(Reference.WAVE_SURVIAL_AI_TEAM_NAME))) {
				// found our AI Team here and need to process this to check for the town hall
				TileEntity te = world.getTileEntity(bp);
				if (te instanceof TownHallTileEntity) {
					return bp;
				}
			}
		}
		return null;

	}

	@SubscribeEvent
	public static void onServerTickEvent(final ServerTickEvent event) {

		if (event.phase == Phase.END && TSRTS.CURRENT_GAME_STATE == GAMESTATE.RUNNINNG) {

			coolDownAmountRemaining--;

			if (coolDownAmountRemaining <= 0) {

				coolDownAmountRemaining = coolDownAmountRest;

				// if wave mode do AI for wave Commander in Chief.
				if (Config.CONFIG_GAME_MODE.get() == Modes.WAVESURVIVAL) {
					onWaveSruvivalTick();
				}

				for (int i = 0; i < TSRTS.teamInfoArray.length; i++) {

					int foodDelta = 0;
					int woodDelta = 0;
					int stoneDelta = 0;
					int ironDelta = 0;
					int goldDelta = 0;
					int diamondDelta = 0;
					int emeraldDelta = 0;

					if (!hasPlacedTownHall[i] && TSRTS.teamInfoArray[i].getTownHalls() > 0) {

						// player placed a town hall on this team !
						hasPlacedTownHall[i] = true;
					}

					if (hasPlacedTownHall[i] && TSRTS.teamInfoArray[i].getTownHalls() == 0) {
						// this team is now "OUT"!

						SetTeamToSpectator(server.getWorld(DimensionType.OVERWORLD), TeamEnum.values()[i].getName());
						WriteGameEvent("TEAM-OUT", TeamEnum.values()[i].getName(), "");

						// reset it to false becuase they are out now and we dont want to re-set them over and over.
						hasPlacedTownHall[i] = false;
					}

					foodDelta = foodDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE.getFOOD());
					woodDelta = woodDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE.getWOOD());
					stoneDelta = stoneDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE.getSTONE());
					ironDelta = ironDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE.getIRON());
					goldDelta = goldDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE.getGOLD());
					diamondDelta = diamondDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE.getDIAMOND());
					emeraldDelta = emeraldDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE.getEMERALD());

					foodDelta = foodDelta + (TSRTS.teamInfoArray[i].getFarms() * Config.CONFIG_RATE_GENRATE_FOOD.get());
					woodDelta = woodDelta + (TSRTS.teamInfoArray[i].getLumberYard() * Config.CONFIG_RATE_GENRATE_WOOD.get());
					stoneDelta = stoneDelta + (TSRTS.teamInfoArray[i].getMineSiteStone() * Config.CONFIG_RATE_GENRATE_STONE.get());
					ironDelta = ironDelta + (TSRTS.teamInfoArray[i].getMineSiteIron() * Config.CONFIG_RATE_GENRATE_IRON.get());
					goldDelta = goldDelta + (TSRTS.teamInfoArray[i].getMineSiteGold() * Config.CONFIG_RATE_GENRATE_GOLD.get());
					diamondDelta = diamondDelta + (TSRTS.teamInfoArray[i].getMineSiteDiamond() * Config.CONFIG_RATE_GENRATE_DIAMOND.get());
					emeraldDelta = emeraldDelta + (TSRTS.teamInfoArray[i].getMineSiteEmerald() * Config.CONFIG_RATE_GENRATE_EMERALD.get());

					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.FOOD, foodDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.WOOD, woodDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.STONE, stoneDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.IRON, ironDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.GOLD, goldDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.DIAMOND, diamondDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.EMERALD, emeraldDelta);

					// REsearch
					if (TSRTS.teamInfoArray[i].getCurrenResearchWorkRemaining() > 0) {
						TSRTS.teamInfoArray[i].setCurrenResearchWorkRemaining(TSRTS.teamInfoArray[i].getCurrenResearchWorkRemaining() - (TSRTS.teamInfoArray[i].getResearchCenter()));
					}
					if (TSRTS.teamInfoArray[i].getCurrenResearchWorkRemaining() <= 0) {
						if (TSRTS.teamInfoArray[i].getCurrenResearchKey() != "") {
							ModResearch.getResearch(TSRTS.teamInfoArray[i].getCurrenResearchKey()).setUnlocked(true, i);
							ModNetwork.SendToALLPlayers(new ResearchUnlockedPacketToClient(TSRTS.teamInfoArray[i].getCurrenResearchKey(), TeamEnum.values()[i].getName(), ModResearch.getResearch(TSRTS.teamInfoArray[i].getCurrenResearchKey()).isUnlocked(i)));

							TSRTS.teamInfoArray[i].setCurrenResearchKey("");
							TSRTS.teamInfoArray[i].setCurrenResearchWorkRemaining(0);
							TSRTS.teamInfoArray[i].setFullResearchWorkRemaining(0);

						}
					}

					// SEND the changes to the clients !
					Utilities.SendTeamToClient(TeamEnum.values()[i].getName());

					int totFood = TSRTS.teamInfoArray[TeamEnum.getIDFromName(TeamEnum.values()[i].getName())].GetResource(Resources.FOOD);
					int totWood = TSRTS.teamInfoArray[TeamEnum.getIDFromName(TeamEnum.values()[i].getName())].GetResource(Resources.WOOD);
					int totStone = TSRTS.teamInfoArray[TeamEnum.getIDFromName(TeamEnum.values()[i].getName())].GetResource(Resources.STONE);
					int totIron = TSRTS.teamInfoArray[TeamEnum.getIDFromName(TeamEnum.values()[i].getName())].GetResource(Resources.IRON);
					int totGold = TSRTS.teamInfoArray[TeamEnum.getIDFromName(TeamEnum.values()[i].getName())].GetResource(Resources.GOLD);
					int totDiamond = TSRTS.teamInfoArray[TeamEnum.getIDFromName(TeamEnum.values()[i].getName())].GetResource(Resources.DIAMOND);
					int totEmerald = TSRTS.teamInfoArray[TeamEnum.getIDFromName(TeamEnum.values()[i].getName())].GetResource(Resources.EMERALD);

					WriteToEcoStats(TeamEnum.values()[i].getName(), foodDelta, woodDelta, stoneDelta, ironDelta, goldDelta, diamondDelta, emeraldDelta, totFood, totWood, totStone, totIron, totGold, totDiamond, totEmerald);
					WriteBuildingStats(TeamEnum.values()[i].getName(), TSRTS.teamInfoArray[TeamEnum.getIDFromName(TeamEnum.values()[i].getName())]);
					WriteUnitStats(TeamEnum.values()[i].getName(), TSRTS.teamInfoArray[TeamEnum.getIDFromName(TeamEnum.values()[i].getName())]);
				}

			}

			for (int i = 0; i < TeamEnum.values().length; i++) {
				if (TSRTS.TeamQueues[i].hasChanged) {
					ModNetwork.SendToALLPlayers(new UnitQueueChangedPacketToClient(TSRTS.TeamQueues[i].getBarracks(), TSRTS.TeamQueues[i].getArcheryRange(), TSRTS.TeamQueues[i].getStables(), TSRTS.TeamQueues[i].getSiegeWorkshop(), TSRTS.TeamQueues[i].isInfinateBarracksQueue(), TSRTS.TeamQueues[i].isInfinateArcheryRangeQueue(), TSRTS.TeamQueues[i].isInfinateStablesQueue(), TSRTS.TeamQueues[i].isInfinateSiegeWorkshopQueue(), i));

					TSRTS.TeamQueues[i].hasChanged = false;
				}
			}

		}
	}

	public static void WriteGameEvent(String EventName, String teamName, String Player) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timeStamp = dtf.format(now);
		TSRTS.LOGGER.info("GAME-EVENT:," + timeStamp + "," + EventName + "," + teamName + "," + Player);

	}

	public static void WriteUnitStats(String teamName, TeamInfo ti) {
		String delimiter = ",";
		if (writeUnitHeader) {
			TSRTS.LOGGER.info("UNITSTATUS-HEADER: Timestamp, TeamName, Minion , Archer, Lancer, Pikeman, Trebuchet, Knight, Advanced Knight ");
			writeUnitHeader = false;

		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timeStamp = dtf.format(now);
		String tmp = "UNITSTATUS: " + delimiter + timeStamp + delimiter + teamName + delimiter + ti.getUnitCountMinion() + delimiter + ti.getUnitCountArcher() + delimiter + ti.getUnitCountLancer() + delimiter + ti.getUnitCountPikeman() + delimiter + ti.getUnitCountTrebuchet() + delimiter + ti.getUnitCountKnight() + delimiter + ti.getUnitCountAdvancedKnight();

		TSRTS.LOGGER.info(tmp);

	}

	public static void WriteBuildingStats(String teamName, TeamInfo ti) {
		String delimiter = ",";
		if (writeBuildingHeader) {
			TSRTS.LOGGER.info("BUILDINGSTATS-HEADER: Timestamp, TeamName, Archeryrange , Armory , Baracks , Farms , Gates , LumberYard , MineSiteDiamond , MineSiteEmerald ,  MineSiteGold , MineSiteIron ,  MineSiteStone , ResearchCenter , Siegeworkshop , Stables , TownHalls , Walls , Wallsteps , Watchtowers");
			writeBuildingHeader = false;

		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timeStamp = dtf.format(now);
		String tmp = "BUILDINGSTATS: " + delimiter + timeStamp + delimiter + teamName + delimiter + ti.getArcheryrange() + delimiter + ti.getArmory() + delimiter + ti.getBaracks() + delimiter + ti.getFarms() + delimiter + ti.getGates() + delimiter + ti.getLumberYard() + delimiter + ti.getMineSiteDiamond() + delimiter + ti.getMineSiteEmerald() + delimiter + ti.getMineSiteGold() + delimiter + ti.getMineSiteIron() + delimiter + ti.getMineSiteStone() + delimiter + ti.getResearchCenter() + delimiter + ti.getSiegeworkshop() + delimiter + ti.getStables() + delimiter + ti.getTownHalls() + delimiter + ti.getWalls() + delimiter + ti.getWallsteps() + delimiter + ti.getWatchtowers();

		TSRTS.LOGGER.info(tmp);

	}

	public static void WriteToEcoStats(String teamName, int foodDelta, int woodDelta, int stoneDelta, int ironDelta, int goldDelta, int diamondDelta, int emeraldDelta, int totFood, int totWood, int totStone, int totIron, int totGold, int totDiamond, int totEmerald) {
		String delimiter = ",";

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timeStamp = dtf.format(now);
		String tmp = "ECOSTATS: " + delimiter + timeStamp + delimiter + teamName + delimiter + foodDelta + delimiter + woodDelta + delimiter + stoneDelta + delimiter + ironDelta + delimiter + goldDelta + delimiter + diamondDelta + delimiter + emeraldDelta + delimiter + totFood + delimiter + totWood + delimiter + totStone + delimiter + totIron + delimiter + totGold + delimiter + totDiamond + delimiter + totEmerald;

		TSRTS.LOGGER.info(tmp);

	}

	public static void SetTeamToSpectator(World world, String TeamName) {
		List<? extends PlayerEntity> players = world.getPlayers();
		for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			PlayerEntity playerEntity = (PlayerEntity) iterator.next();
			if (playerEntity.getTeam() != null) {
				//
				if (playerEntity.getTeam().getName().equals(TeamName)) {
					// same team
					playerEntity.setGameType(GameType.SPECTATOR);
					playerEntity.playSound(SoundEvents.ENTITY_ZOMBIE_DEATH, 1, 1);
				}
			}

		}

	}

	@SubscribeEvent
	public static void onFMLServerStartingEvent(FMLServerStartingEvent event) {
		server = event.getServer();
	}
}
