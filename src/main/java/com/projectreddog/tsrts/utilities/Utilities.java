package com.projectreddog.tsrts.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.TSRTS.GAMESTATE;
import com.projectreddog.tsrts.blocks.OwnedBlock;
import com.projectreddog.tsrts.containers.provider.DefensiveBuildingsContinerProvider;
import com.projectreddog.tsrts.containers.provider.EcoBuildingsContinerProvider;
import com.projectreddog.tsrts.containers.provider.LobbyContinerProvider;
import com.projectreddog.tsrts.containers.provider.MarketplaceContinerProvider;
import com.projectreddog.tsrts.containers.provider.OptionsContinerProvider;
import com.projectreddog.tsrts.containers.provider.ResearchContinerProvider;
import com.projectreddog.tsrts.containers.provider.TeamOptionsContinerProvider;
import com.projectreddog.tsrts.containers.provider.TroopBuildingsContinerProvider;
import com.projectreddog.tsrts.containers.provider.UnitRecruitmentContinerProvider;
import com.projectreddog.tsrts.data.StructureData;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.handler.Config.Modes;
import com.projectreddog.tsrts.handler.ServerEvents;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.init.ModEntities;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.init.ModResearch;
import com.projectreddog.tsrts.items.builderitems.BuilderItem;
import com.projectreddog.tsrts.network.AlertToastToClient;
import com.projectreddog.tsrts.network.CostsPacketToClient;
import com.projectreddog.tsrts.network.MarketRatesPacketToClient;
import com.projectreddog.tsrts.network.PlayerReadyUpPacketToClient;
import com.projectreddog.tsrts.network.PlayerSelectionChangedPacketToClient;
import com.projectreddog.tsrts.network.PlayerSelectionChangedPacketToServer;
import com.projectreddog.tsrts.network.ResearchUnlockedPacketToClient;
import com.projectreddog.tsrts.network.SendGameOptionPacketToClient;
import com.projectreddog.tsrts.network.SendTeamInfoPacketToClient;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.RTS_COSTS;
import com.projectreddog.tsrts.reference.Reference.STRUCTURE_TYPE;
import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;
import com.projectreddog.tsrts.tileentity.OwnedCooldownTileEntity;
import com.projectreddog.tsrts.tileentity.OwnedTileEntity;
import com.projectreddog.tsrts.tileentity.WallTileEntity;
import com.projectreddog.tsrts.tileentity.interfaces.ResourceGenerator;
import com.projectreddog.tsrts.utilities.TeamInfo.Resources;
import com.projectreddog.tsrts.utilities.data.MapStructureData;
import com.projectreddog.tsrts.utilities.data.MarketRates;
import com.projectreddog.tsrts.utilities.data.Research;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class Utilities {

	public static void setPlayerReady(PlayerEntity player, boolean isReady) {
		TSRTS.LOGGER.info("setPlayerReady: " + isReady);

		TSRTS.isPlayerReadyArray.put(player.getScoreboardName(), isReady);

		if (!player.world.isRemote) {
			// server so also send packet

			ModNetwork.SendToALLPlayers(new PlayerReadyUpPacketToClient(player.getEntityId(), isReady));

		}
	}

	public static boolean getPlayerReady(PlayerEntity player) {

		return getPlayerReady(player.getScoreboardName());
	}

	public static boolean getPlayerReady(String playerScoreboardName) {
		if (TSRTS.isPlayerReadyArray.containsKey(playerScoreboardName)) {
			return TSRTS.isPlayerReadyArray.get(playerScoreboardName);
		}
		TSRTS.LOGGER.info(" player " + playerScoreboardName + " not in hashmap ASSUME THEY ARE FALSE & put them in the map");
		TSRTS.isPlayerReadyArray.put(playerScoreboardName, false);

		return false;
	}

	public static void GuiRequestHandler(int guiID, ServerPlayerEntity player) {
		switch (guiID) {
		case Reference.GUI_ID_TOWN_HALL:
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) new DefensiveBuildingsContinerProvider());

			break;

		case Reference.GUI_BUTTON_MAIN_MENU_ECO:
			if (TSRTS.CURRENT_GAME_STATE == GAMESTATE.LOBBY) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) new LobbyContinerProvider());

			} else if (TSRTS.CURRENT_GAME_STATE == GAMESTATE.RUNNINNG) {

				if (TSRTS.hasPlayerPlacedTownHall.containsKey(player.getScoreboardName()) && TSRTS.hasPlayerPlacedTownHall.get(player.getScoreboardName())) {

					// only open menu if they have placed a townhall !
					NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) new EcoBuildingsContinerProvider());

				} else {
					player.sendMessage(new TranslationTextComponent("message.placetownhallfirst"));
				}
			}
			break;
		default:
			break;
		}
	}

	public static void StartResearch(String key, String team) {
		if (!ModResearch.getResearch(key).isUnlocked(team)) {

			// CHECK if there is a research in progress still!
			if (TSRTS.teamInfoArray[TeamEnum.getIDFromName(team)].getCurrenResearchWorkRemaining() > 0) {
				// something is actively being researched so do nothing!

			} else {

				// set this as the active research
				if (ModResearch.research_topics.containsKey(key)) {
					Research r = ModResearch.getResearch(key);
					if (!r.isUnlocked(team)) {

						if (Utilities.hasNeededResourcesForResourceValues(team, r.getRv())) {
							Utilities.spendResourcesForResourceValues(team, r.getRv());
							TSRTS.teamInfoArray[TeamEnum.getIDFromName(team)].setCurrenResearchKey(key);
							TSRTS.teamInfoArray[TeamEnum.getIDFromName(team)].setCurrenResearchWorkRemaining(r.getWorkRequired());
							TSRTS.teamInfoArray[TeamEnum.getIDFromName(team)].setFullResearchWorkRemaining(r.getWorkRequired());

							SendTeamToClient(team);
						}
					}
				}

			}

		}
	}

	public static void SendCostsToClient(ServerPlayerEntity player) {
		for (int i = 0; i < RTS_COSTS.values().length; i++) {
			ModNetwork.SendToPlayer(player, new CostsPacketToClient(RTS_COSTS.values()[i], GetResourceValuesbyRTS_Costs(RTS_COSTS.values()[i])));
		}
	}

	public static ResourceValues GetResourceValuesbyRTS_Costs(RTS_COSTS costs) {
		switch (costs) {
		case ARCHER:
			return Config.CONFIG_UNIT_COSTS_ARCHER;

		case ARCHERY_RANGE:
			return Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE;

		case BARRACKS:
			return Config.CONFIG_BUILDING_COSTS_BARRACKS;

		case FARM:
			return Config.CONFIG_BUILDING_COSTS_FARM;

		case GATE:
			return Config.CONFIG_BUILDING_COSTS_GATE;

		case LANCER:
			return Config.CONFIG_UNIT_COSTS_LANCER;

		case LUMBER_YARD:
			return Config.CONFIG_BUILDING_COSTS_LUMBER_YARD;

		case MINE_DIAMOND:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND;

		case MINE_EMERALD:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD;

		case MINE_GOLD:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD;

		case MINE_IRON:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_IRON;

		case MINE_STONE:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_STONE;

		case MINION:
			return Config.CONFIG_UNIT_COSTS_MINION;

		case PIKEMAN:
			return Config.CONFIG_UNIT_COSTS_PIKEMAN;

		case RESEARCH_ADVANCED_ARMOR:
			return Config.CONFIG_RESEARCH_COSTS_ADVCEDARMOR;

		case RESEARCH_ARCHER:
			return Config.CONFIG_RESEARCH_COSTS_ARCHER;

		case RESEARCH_BATTERING_RAM:
			return Config.CONFIG_RESEARCH_COSTS_BATTERINGRAMS;

		case RESEARCH_CENTER:
			return Config.CONFIG_BUILDING_COSTS_RESEARCH_CENTER;

		case RESEARCH_CROSSBOW:
			return Config.CONFIG_RESEARCH_COSTS_CROSSBOW;

		case RESEARCH_LANCER:
			return Config.CONFIG_RESEARCH_COSTS_LANCER;

		case RESEARCH_MARKETPLACE:
			return Config.CONFIG_RESEARCH_COSTS_MARKETPLACE;

		case RESEARCH_MINION:
			return Config.CONFIG_RESEARCH_COSTS_MINION;

		case RESEARCH_PIKEMAN:
			return Config.CONFIG_RESEARCH_COSTS_PIKEMAN;

		case RESEARCH_SIEGE_WORKSHOP:
			return Config.CONFIG_RESEARCH_COSTS_SIEGEWORKSHOP;

		case RESEARCH_TOWNHALL:
			return Config.CONFIG_RESEARCH_COSTS_TOWNHALL;

		case RESEARCH_TREBUCHET:
			return Config.CONFIG_RESEARCH_COSTS_TREBUCHET;

		case RESEARCH_WALL:
			return Config.CONFIG_RESEARCH_COSTS_WALL;

		case RESEARCH_WATCHTOWER:
			return Config.CONFIG_RESEARCH_COSTS_WATCHTOWER;

		case STABLES:
			return Config.CONFIG_BUILDING_COSTS_STABLES;
		case WALL:
			return Config.CONFIG_BUILDING_COSTS_WALL;

		case WALL_STEPS:
			return Config.CONFIG_BUILDING_COSTS_WALL_STEPS;

		case WATCH_TOWER:
			return Config.CONFIG_BUILDING_COSTS_WATCH_TOWER;
		case SIEGE_WORKSHOP:
			return Config.CONFIG_BUILDING_COSTS_SIEGE_WORKSHOP;
		case MARKETPLACE:
			return Config.CONFIG_BUILDING_COSTS_MAKRETPLACE;
		case RESEARCH_ARMOR:
			return Config.CONFIG_RESEARCH_COSTS_ARMOR;
		case RESEARCH_BLACKSMITHING:
			return Config.CONFIG_RESEARCH_COSTS_BLACKSMITHING;
		case ARMORY:
			return Config.CONFIG_BUILDING_COSTS_ARMORY;

		case SAPPER:
			return Config.CONFIG_UNIT_COSTS_SAPPER;

		case LONGBOWMEN:
			return Config.CONFIG_UNIT_COSTS_LONGBOWMEN;

		case CROSSBOWMEN:
			return Config.CONFIG_UNIT_COSTS_CROSSBOWMEN;
		default:
			throw new IllegalArgumentException("case Cost Type not handled");
		}
	}

	public static ResourceValues GetResourceValuesforUnitID(UNIT_TYPES unitID) {
		switch (unitID) {
		case MINION:
			return Config.CONFIG_UNIT_COSTS_MINION;
		case ARCHER:
			return Config.CONFIG_UNIT_COSTS_ARCHER;
		case LANCER:
			return Config.CONFIG_UNIT_COSTS_LANCER;
		case PIKEMAN:
			return Config.CONFIG_UNIT_COSTS_PIKEMAN;
		case TREBUCHET:
			return Config.CONFIG_UNIT_COSTS_TREBUCHET;
		case KNIGHT:
			return Config.CONFIG_UNIT_COSTS_KNIGHT;
		case ADVANCED_KNIGHT:
			return Config.CONFIG_UNIT_COSTS_ADVANCED_KNIGHT;
		case SAPPER:
			return Config.CONFIG_UNIT_COSTS_SAPPER;
		case LONGBOWMAN:
			return Config.CONFIG_UNIT_COSTS_LONGBOWMEN;

		case CROSSBOWMAN:
			return Config.CONFIG_UNIT_COSTS_CROSSBOWMEN;
		}
		return null;
	}

	public static boolean isResearchUnlockedOrNull(String researchName, String teamName) {
		// returns true if research name is null
		if (researchName == null) {
			return true;
		}
		if (teamName == null) {
			// don't allow for null teams !
			return false;
		}
		return ModResearch.getResearch(researchName).isUnlocked(teamName);
	}

	public static void RecruitTroopHandler(UNIT_TYPES unitType, ServerPlayerEntity player) {
		String teamName = "";
		// TODO: RESEARCH UPDATE WITH RESEARCH UNLOCKS AS NEEDED
		if (player.getTeam() != null) {
			teamName = player.getTeam().getName();
		}

		if (isResearchUnlockedOrNull(unitType.getResearchKeyRequiredToBuy(), teamName) && (!unitType.IsArmoryRquired() || TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].getArmory() > 0)) {

			if (player.getTeam() != null) {
				String team = player.getTeam().getName();
				ResourceValues rv = GetResourceValuesforUnitID(unitType);
				if (hasNeededResourcesForResourceValues(team, rv)) {
					spendResourcesForResourceValues(team, rv);
					TSRTS.TeamQueues[TeamEnum.getIDFromName(team)].AddToProperQueue(unitType);
				}
			}
		}
	}

	public static void TownHallGuiHandler(int buttonId, ServerPlayerEntity player) {
		String teamName = "";
		// TODO: RESEARCH UPDATE WITH RESEARCH UNLOCKS AS NEEDED
		if (player.getTeam() != null) {
			teamName = player.getTeam().getName();
		}
		if (buttonId == Reference.GUI_BUTTON_BUY_BARRACKS) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.BARRACKSBUILDERITEM));
		} else if (buttonId == Reference.GUI_BUTTON_BUY_ARCHERY_RANGE && ModResearch.getResearch("archer").isUnlocked(teamName)) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.ARCHERYRANGEBUILDERITEM));
		} else if (buttonId == Reference.GUI_BUTTON_BUY_MINE_SITE_STONE) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.MINESITESTONEBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_MINE_SITE_IRON) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.MINESITEIRONBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_MINE_SITE_GOLD) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.MINESITEGOLDBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_MINE_SITE_DIAMOND) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.MINESITEDIAMONDBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_LUMBER_YARD) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.LUMBERYARDBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_FARM) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.FARMBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_STABLES && ModResearch.getResearch("lancer").isUnlocked(teamName)) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.STABLESBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_WALL && ModResearch.getResearch("wall").isUnlocked(teamName)) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.WALLBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_WATCH_TOWER && ModResearch.getResearch("watchtower").isUnlocked(teamName)) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.WATCHTOWERBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_WALL_STEPS && ModResearch.getResearch("wall").isUnlocked(teamName)) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.WALLSTEPSBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_GATE && ModResearch.getResearch("wall").isUnlocked(teamName)) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.GATEBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_SIEGE_WORKSHOP && ModResearch.getResearch("siegeworkshop").isUnlocked(teamName)) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.SIEGEWORKSHOPBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_RESEARCH_CENTER) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.RESEARCHCENTERBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_ARMORY) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.ARMORYBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_BUY_FOOD) {
			Utilities.PlayerBuysResources(player, TeamInfo.Resources.FOOD);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_BUY_WOOD) {
			Utilities.PlayerBuysResources(player, TeamInfo.Resources.WOOD);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_BUY_STONE) {
			Utilities.PlayerBuysResources(player, TeamInfo.Resources.STONE);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_BUY_IRON) {
			Utilities.PlayerBuysResources(player, TeamInfo.Resources.IRON);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_BUY_GOLD) {
			Utilities.PlayerBuysResources(player, TeamInfo.Resources.GOLD);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_BUY_DIAMOND) {
			Utilities.PlayerBuysResources(player, TeamInfo.Resources.DIAMOND);

		}

		else if (buttonId == Reference.GUI_BUTTON_MARKET_SELL_FOOD) {
			Utilities.PlayerSellsResources(player, TeamInfo.Resources.FOOD);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_SELL_WOOD) {
			Utilities.PlayerSellsResources(player, TeamInfo.Resources.WOOD);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_SELL_STONE) {
			Utilities.PlayerSellsResources(player, TeamInfo.Resources.STONE);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_SELL_IRON) {
			Utilities.PlayerSellsResources(player, TeamInfo.Resources.IRON);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_SELL_GOLD) {
			Utilities.PlayerSellsResources(player, TeamInfo.Resources.GOLD);

		} else if (buttonId == Reference.GUI_BUTTON_MARKET_SELL_DIAMOND) {
			Utilities.PlayerSellsResources(player, TeamInfo.Resources.DIAMOND);

		} else if (buttonId == Reference.GUI_BUTTON_BUY_MARKET_PLACE) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.MARKETPLACEITEM));

		}

	}

	public static boolean spendResourcesForResourceValues(String teamName, ResourceValues rv) {

		boolean result = true;

		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.FOOD, rv.getFOOD());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.WOOD, rv.getWOOD());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.STONE, rv.getSTONE());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.IRON, rv.getIRON());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.GOLD, rv.getGOLD());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.DIAMOND, rv.getDIAMOND());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.EMERALD, rv.getEMERALD());
		Utilities.SendTeamToClient(teamName);

		return result;
	}

	public static boolean hasNeededResourcesForResourceValues(String teamName, ResourceValues rv) {
		if (teamName == null) {
			return false;
		}
		boolean result = true;
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.FOOD, rv.getFOOD());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.WOOD, rv.getWOOD());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.STONE, rv.getSTONE());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.IRON, rv.getIRON());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.GOLD, rv.getGOLD());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.DIAMOND, rv.getDIAMOND());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.EMERALD, rv.getEMERALD());
		return result;
	}

	public static void GenericGuiHandler(int buttonID, ServerPlayerEntity player) {
		ScorePlayerTeam team;
		switch (buttonID) {

		case Reference.GUI_BUTTON_LOBBY_RED:

			team = player.world.getScoreboard().getTeam("red");
			player.world.getScoreboard().addPlayerToTeam(player.getScoreboardName(), team);
			break;
		case Reference.GUI_BUTTON_LOBBY_BLUE:

			team = player.world.getScoreboard().getTeam("blue");
			player.world.getScoreboard().addPlayerToTeam(player.getScoreboardName(), team);
			break;

		case Reference.GUI_BUTTON_LOBBY_GREEN:
			team = player.world.getScoreboard().getTeam("green");
			player.world.getScoreboard().addPlayerToTeam(player.getScoreboardName(), team);
			break;
		case Reference.GUI_BUTTON_LOBBY_YELLOW:
			team = player.world.getScoreboard().getTeam("yellow");
			player.world.getScoreboard().addPlayerToTeam(player.getScoreboardName(), team);
			break;
		case Reference.GUI_BUTTON_LOBBY_READY:
			Utilities.setPlayerReady(player, !Utilities.getPlayerReady(player));
			// TSRTS.LOGGER.info("Lobby Button: READY:" + Utilities.getPlayerReady(player));

			break;
		case Reference.GUI_BUTTON_LOBBY_START:
			Utilities.startGame(player.world);
			break;

		case Reference.GUI_BUTTON_LOBBY_SEPECTATE:
			if (player.isSpectator()) {
				player.setGameType(GameType.SURVIVAL);

				player.posY = player.posY + .75f;

				Utilities.setPlayerReady(player, false);
			} else {
				player.setGameType(GameType.SPECTATOR);
				Utilities.setPlayerReady(player, true);
				player.posY = player.posY + .75f;

			}
			break;
		case Reference.GUI_BUTTON_LOBBY_OPTIONS:
			ModNetwork.SendToALLPlayers(new SendGameOptionPacketToClient(GameOptions.speedEffectAmount, GameOptions.weatherEnabled));

			NetworkHooks.openGui(player, new OptionsContinerProvider());

			break;

		case Reference.GUI_BUTTON_OPTIONS_SPEED_MINUS:
			GameOptions.SpeedMinus();
			ModNetwork.SendToALLPlayers(new SendGameOptionPacketToClient(GameOptions.speedEffectAmount, GameOptions.weatherEnabled));
			break;
		case Reference.GUI_BUTTON_OPTIONS_SPEED_PLUS:
			GameOptions.SpeedPlus();
			ModNetwork.SendToALLPlayers(new SendGameOptionPacketToClient(GameOptions.speedEffectAmount, GameOptions.weatherEnabled));
			break;
		case Reference.GUI_BUTTON_OPTIONS_WEATHER_OFF:
			GameOptions.DisableWeather(player.world);
			ModNetwork.SendToALLPlayers(new SendGameOptionPacketToClient(GameOptions.speedEffectAmount, GameOptions.weatherEnabled));
			break;
		case Reference.GUI_BUTTON_OPTIONS_WEATHER_ON:
			GameOptions.EnableWeather(player.world);
			ModNetwork.SendToALLPlayers(new SendGameOptionPacketToClient(GameOptions.speedEffectAmount, GameOptions.weatherEnabled));
			break;

		case Reference.GUI_BUTTON_OPTIONS_BACK:
			NetworkHooks.openGui(player, new LobbyContinerProvider());
			break;
		case Reference.GUI_BUTTON_MAIN_MENU_ECO:
			NetworkHooks.openGui(player, new EcoBuildingsContinerProvider());
			break;

		case Reference.GUI_BUTTON_MAIN_MENU_DEFENSE_BUILDINGS:
			NetworkHooks.openGui(player, new DefensiveBuildingsContinerProvider());
			break;

		case Reference.GUI_BUTTON_MAIN_MENU_TROOP_BUILDINGS:
			NetworkHooks.openGui(player, new TroopBuildingsContinerProvider());
			break;

		case Reference.GUI_BUTTON_MAIN_MENU_UNIT_RECRUITMENT:
			NetworkHooks.openGui(player, new UnitRecruitmentContinerProvider());
			break;
		case Reference.GUI_BUTTON_MAIN_MENU_RESEARCH:
			NetworkHooks.openGui(player, new ResearchContinerProvider());
			break;

		case Reference.GUI_BUTTON_MAIN_MENU_TEAM_OPTIONS:
			NetworkHooks.openGui(player, new TeamOptionsContinerProvider());
			break;

		case Reference.GUI_BUTTON_MAIN_MENU_MARKET:
			NetworkHooks.openGui(player, new MarketplaceContinerProvider());
			break;
		}
		TSRTS.LOGGER.info("TEAM:" + player.getTeam().getName());

	}

	private static void startGame(World world) {
		// send players to spawn
		// set gamemodes of players

		// change the gamemode
		// TODO later this should be the count down !
		TSRTS.CURRENT_GAME_STATE = GAMESTATE.RUNNINNG;
		if (!world.isRemote) {
			ServerEvents.WriteGameEvent("GAME-START", "", "");
		}
		// give players items
		List<? extends PlayerEntity> players = world.getPlayers();
		for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			PlayerEntity playerEntity = (PlayerEntity) iterator.next();
			Utilities.giveStartingItems(playerEntity);
			// close the gui's

			playerEntity.closeScreen();

			if (GameOptions.speedEffectAmount > 0) {
				playerEntity.addPotionEffect(new EffectInstance(Effects.SPEED, 99999999, GameOptions.speedEffectAmount - 1));
			}

			if (playerEntity.getTeam() != null && !playerEntity.isSpectator()) {
				String teamName = playerEntity.getTeam().getName();
				int teamIndex = TeamEnum.getIDFromName(teamName);
				TSRTS.teamInfoArray[teamIndex].setTeamPlayerCount(TSRTS.teamInfoArray[teamIndex].getTeamPlayerCount() + 1);

				if (!world.isRemote) {
					ServerEvents.WriteGameEvent("TEAM-PLAYER", teamName, playerEntity.getScoreboardName());
				}
			}
		}

		int teamCount = getTeamWithPlayerCount();

		for (int i = 0; i < TSRTS.teamInfoArray.length; i++) {
			TSRTS.teamInfoArray[i].setTeamPopulationCap(Config.CONFIG_SERVER_MAX_POPULATION.get() / teamCount);
		}
		// give teams resoruces
		Collection<ScorePlayerTeam> teams = world.getScoreboard().getTeams();

		// get starting resources

		for (Iterator iterator = teams.iterator(); iterator.hasNext();) {
			ScorePlayerTeam team = (ScorePlayerTeam) iterator.next();
			int[] tmpRes = getStartingResourceAmounts();

			String teamName = team.getName();
			Utilities.setResourcesOfTeam(teamName, tmpRes);
		}
	}

	public static int getTeamWithPlayerCount() {

		int teamCount = 0;
		for (int i = 0; i < TSRTS.teamInfoArray.length; i++) {
			if (TSRTS.teamInfoArray[i].getTeamPlayerCount() > 0) {
				teamCount++;
			}
		}

		return teamCount;
	}

	private static void giveStartingItems(PlayerEntity playerEntity) {
		// TODO Auto-generated method stub
		GivePlayerItemStack(playerEntity, new ItemStack(Items.DIAMOND_SWORD, 1));
		GivePlayerItemStack(playerEntity, new ItemStack(ModItems.SAMPLEITEM));
		GivePlayerItemStack(playerEntity, new ItemStack(ModItems.RETREATESEPTERITEM));
		GivePlayerItemStack(playerEntity, new ItemStack(ModItems.RALLYPOINTTOOLITEM));
		GivePlayerItemStack(playerEntity, new ItemStack(Items.COOKED_BEEF, 64));
		GivePlayerItemStack(playerEntity, new ItemStack(ModItems.TOWNHALLBUILDERITEM));
		GivePlayerItemStack(playerEntity, new ItemStack(ModItems.WATCHTOWERBUILDERITEM, 3));

	}

	public static int[] getStartingResourceAmounts() {
		TeamInfo ti = new TeamInfo();
		ti.SetResource(Resources.FOOD, Config.CONFIG_START_AMT.getFOOD());
		ti.SetResource(Resources.WOOD, Config.CONFIG_START_AMT.getWOOD());
		ti.SetResource(Resources.STONE, Config.CONFIG_START_AMT.getSTONE());
		ti.SetResource(Resources.IRON, Config.CONFIG_START_AMT.getIRON());
		ti.SetResource(Resources.GOLD, Config.CONFIG_START_AMT.getGOLD());
		ti.SetResource(Resources.DIAMOND, Config.CONFIG_START_AMT.getDIAMOND());
		ti.SetResource(Resources.EMERALD, Config.CONFIG_START_AMT.getEMERALD());

		return ti.GetResourceArray();
	}

	public static int GetMarketRateForResource(TeamInfo.Resources resource) {
		switch (resource) {
		case FOOD:
			return MarketRates.foodRate;
		case WOOD:
			return MarketRates.woodRate;
		case STONE:
			return MarketRates.stoneRate;
		case IRON:
			return MarketRates.ironRate;
		case GOLD:
			return MarketRates.goldRate;
		case DIAMOND:
			return MarketRates.diamondRate;
		}
		return 0;

	}

	public static void UpdateMarketRateForResource(TeamInfo.Resources resource, int newAmount) {
		switch (resource) {
		case FOOD:
			MarketRates.foodRate = newAmount;
			break;
		case WOOD:
			MarketRates.woodRate = newAmount;
			break;
		case STONE:
			MarketRates.stoneRate = newAmount;
			break;
		case IRON:
			MarketRates.ironRate = newAmount;
			break;
		case GOLD:
			MarketRates.goldRate = newAmount;
			break;
		case DIAMOND:
			MarketRates.diamondRate = newAmount;
			break;
		}
		SendMarketRatesToClient();
	}

	public static void PlayerSellsResources(PlayerEntity player, TeamInfo.Resources resource) {

		String teamName = player.getTeam().getName();
		if (TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].getMarketplace() > 0) {
			int rate = GetMarketRateForResource(resource);

			boolean result = true;
			result = result && Utilities.hasNeededResource(teamName, resource, rate);
			if (result) {

				result = result && Utilities.SpendResourcesFromTeam(teamName, resource, rate);
				SendTeamToClient(teamName);

				if (result) {
					Utilities.AddResourcesToTeam(teamName, TeamInfo.Resources.EMERALD, 1);
					SendTeamToClient(teamName);
					Utilities.UpdateMarketRateForResource(resource, rate + 1);

				}

			}
		}

	}

	public static void PlayerBuysResources(PlayerEntity player, TeamInfo.Resources resource) {

		String teamName = player.getTeam().getName();
		if (TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].getMarketplace() > 0) {
			int rate = GetMarketRateForResource(resource) - 5;

			boolean result = true;
			result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.EMERALD, 1);
			if (result) {

				result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.EMERALD, 1);
				SendTeamToClient(teamName);

				if (result) {
					Utilities.AddResourcesToTeam(teamName, resource, rate);
					SendTeamToClient(teamName);

					Utilities.UpdateMarketRateForResource(resource, MathHelper.clamp(rate - 1 + 5, 10, Integer.MAX_VALUE));
				}

			}
		}

	}

	public static void PlayerBuysItem(PlayerEntity player, ItemStack itemStack) {
		Item item = itemStack.getItem();
		if (item instanceof BuilderItem) {
			BuilderItem bi = (BuilderItem) item;

			int foodCosts = bi.getFoodCosts();
			int woodCosts = bi.getWoodCosts();
			int stoneCosts = bi.getStoneCosts();

			int ironCosts = bi.getIronCosts();
			int goldCosts = bi.getGoldCosts();
			int diamondCosts = bi.getDiamondCosts();
			int emeraldCosts = bi.getEmeraldCosts();

			String teamName = player.getTeam().getName();

			boolean result = true;
			result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.FOOD, foodCosts);
			result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.WOOD, woodCosts);
			result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.STONE, stoneCosts);
			result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.IRON, ironCosts);
			result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.GOLD, goldCosts);
			result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.DIAMOND, diamondCosts);
			result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.EMERALD, emeraldCosts);
			if (result) {

				result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.FOOD, foodCosts);
				result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.WOOD, woodCosts);
				result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.STONE, stoneCosts);
				result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.IRON, ironCosts);
				result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.GOLD, goldCosts);
				result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.DIAMOND, diamondCosts);
				result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.EMERALD, emeraldCosts);
				SendTeamToClient(teamName);

				if (result) {
					GivePlayerItemStack(player, itemStack);
				}

			}
		} else {
			TSRTS.LOGGER.info("tried to buy a non builder item!");
		}

	}

	public static int getTotalCostsForStructureType(Reference.STRUCTURE_TYPE type) {
		return getTotalCostsForResrouceValues(getResourceValuesByStructureType(type));
	}

	public static int getTotalCostsForResrouceValues(ResourceValues rv) {
		if (rv != null) {
			return rv.getFOOD() + rv.getWOOD() + rv.getSTONE() + rv.getIRON() + rv.getGOLD() + rv.getDIAMOND() + rv.getEMERALD();
		} else {
			return 0;
		}

	}

	public static ResourceValues getResourceValuesByStructureType(Reference.STRUCTURE_TYPE type) {
		switch (type) {
		case ARCHERY_RANGE:
			return Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE;
		case ARMORY:
			return Config.CONFIG_BUILDING_COSTS_ARMORY;
		case BARRACKS:
			return Config.CONFIG_BUILDING_COSTS_BARRACKS;
		case FARM:
			return Config.CONFIG_BUILDING_COSTS_FARM;
		case GATE:
			return Config.CONFIG_BUILDING_COSTS_GATE;
		case LUMBER_YARD:
			return Config.CONFIG_BUILDING_COSTS_LUMBER_YARD;
		case MINE_SITE_DIAMOND:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND;
		case MINE_SITE_EMERALD:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD;
		case MINE_SITE_GOLD:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD;
		case MINE_SITE_IRON:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_IRON;
		case MINE_SITE_STONE:
			return Config.CONFIG_BUILDING_COSTS_MINESITE_STONE;
		case RESEARCH_CENTER:
			return Config.CONFIG_BUILDING_COSTS_RESEARCH_CENTER;
		case SIEGE_WORKSHOP:
			return null;
		case STABLES:
			return Config.CONFIG_BUILDING_COSTS_STABLES;
		case TOWN_HALL:
			return new ResourceValues(0, 0, 0, 0, 0, 0, 0);
		case WALL:
			return Config.CONFIG_BUILDING_COSTS_WALL;
		case WALL_STEPS:
			return Config.CONFIG_BUILDING_COSTS_WALL_STEPS;
		case WATCH_TOWER:
			return Config.CONFIG_BUILDING_COSTS_WATCH_TOWER;
		default:
			break;

		}
		return null;
	}

	public static void GivePlayerItemStack(PlayerEntity player, ItemStack itemStack) {

		boolean result = player.inventory.addItemStackToInventory(itemStack);

		if (!result) {
			player.dropItem(itemStack, false);

		}
		// player.container.detectAndSendChanges();
		if (player instanceof ServerPlayerEntity) {
			ServerPlayerEntity sp = (ServerPlayerEntity) player;
			sp.sendContainerToPlayer(sp.container);
		}
	}

	public static EntityType getEntityTypeForUnitID(UNIT_TYPES unitID) {
		switch (unitID) {
		case MINION:
			return ModEntities.MINION;
		case KNIGHT:
			return ModEntities.KNIGHT;
		case ADVANCED_KNIGHT:
			return ModEntities.ADVANCED_KNIGHT;
		case ARCHER:
			return ModEntities.ARCHER_MINION;
		case LANCER:
			return ModEntities.MOUNTED_ENTITY;
		case PIKEMAN:
			return ModEntities.PIKEMAN_ENTITY;
		case TREBUCHET:
			return ModEntities.TREBUCHET_ENTITY;
		case SAPPER:
			return ModEntities.SAPPER;
		case LONGBOWMAN:
			return ModEntities.LONGBOWMAN;

		case CROSSBOWMAN:
			return ModEntities.CROSSBOWMAN;
		default:
			return null;
		}
	}

	public static void AddToUnitCounts(UNIT_TYPES unitID, String teamName) {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].AddOneUnitCount(unitID);

	}

	public static void SpawnUnitForTeam(UNIT_TYPES unitID, String Owner, World world, BlockPos pos, ScorePlayerTeam team, @Nullable BlockPos rallyPoint) {
		EntityType et = getEntityTypeForUnitID(unitID);
		Entity e = SpawnUnit(et, Owner, world, pos, rallyPoint, unitID);
		if (team != null) {
			AddToUnitCounts(unitID, team.getName());

			world.getScoreboard().addPlayerToTeam(e.getCachedUniqueIdString(), team);
		}

	}

//	public static void SpawnMountedUnitForTeam(EntityType entityType, EntityType mountEntityType, String Owner, World world, BlockPos pos, ScorePlayerTeam team, @Nullable BlockPos rallyPoint) {
//		Entity mount = mountEntityType.spawn(world, null, null, pos, SpawnReason.TRIGGERED, true, true);
//		Entity e = SpawnUnit(entityType, Owner, world, pos, rallyPoint);
//		e.startRiding(mount);
//		if (team != null) {
//			world.getScoreboard().addPlayerToTeam(e.getCachedUniqueIdString(), team);
//			world.getScoreboard().addPlayerToTeam(mount.getCachedUniqueIdString(), team);
//		}
//
//	}

	public static List<DyeItem> getDyeListForTeam(String team) {
		List<DyeItem> dyes = Lists.newArrayList();
		if (team.equals(TeamEnum.BLUE.getName())) {
			dyes.add(DyeItem.getItem(DyeColor.BLUE));
		} else if (team.equals(TeamEnum.RED.getName())) {
			dyes.add(DyeItem.getItem(DyeColor.RED));

		} else if (team.equals(TeamEnum.GREEN.getName())) {
			dyes.add(DyeItem.getItem(DyeColor.GREEN));

		} else if (team.equals(TeamEnum.YELLOW.getName())) {
			dyes.add(DyeItem.getItem(DyeColor.YELLOW));

		} else {
			dyes.add(DyeItem.getItem(DyeColor.YELLOW));

		}
		return dyes;
	}

	public static Entity SpawnUnit(EntityType entityType, String Owner, World world, BlockPos pos, BlockPos rallyPoint, UNIT_TYPES unitID) {
		Entity e = entityType.spawn(world, null, null, pos, SpawnReason.TRIGGERED, true, true);
		if (e instanceof UnitEntity) {
			UnitEntity ue = (UnitEntity) e;
			ue.setOwnerName(Owner);
			if (rallyPoint != null) {
				ue.ownerControlledDestination = rallyPoint;
			}

			if (unitID == UNIT_TYPES.KNIGHT) {
				List<DyeItem> teamColorList = Lists.newArrayList();
				if (ue.getTeam() != null) {
					teamColorList = getDyeListForTeam(ue.getTeam().getName());
				}

				ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));

				ItemStack is = new ItemStack(ModItems.TEAM_IRON_ARMOR_HELMET);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.HEAD, is);

				is = new ItemStack(ModItems.TEAM_IRON_ARMOR_CHESTPLATE);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.CHEST, is);

				is = new ItemStack(ModItems.TEAM_IRON_ARMOR_LEGGINGS);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.LEGS, is);

				is = new ItemStack(ModItems.TEAM_IRON_ARMOR_BOOTS);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.FEET, is);

			} else if (unitID == UNIT_TYPES.ADVANCED_KNIGHT) {
				List<DyeItem> teamColorList = Lists.newArrayList();
				if (ue.getTeam() != null) {
					teamColorList = getDyeListForTeam(ue.getTeam().getName());
				}

				ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));

				ItemStack is = new ItemStack(ModItems.TEAM_DIAMOND_ARMOR_HELMET);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.HEAD, is);

				is = new ItemStack(ModItems.TEAM_DIAMOND_ARMOR_CHESTPLATE);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.CHEST, is);

				is = new ItemStack(ModItems.TEAM_DIAMOND_ARMOR_LEGGINGS);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.LEGS, is);

				is = new ItemStack(ModItems.TEAM_DIAMOND_ARMOR_BOOTS);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.FEET, is);

			}
			if (entityType == ModEntities.ARCHER_MINION) {
				ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
			}

			if (entityType == ModEntities.LONGBOWMAN) {
				ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModItems.LONGBOW));
			}
			if (entityType == ModEntities.CROSSBOWMAN) {
				ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.CROSSBOW));
				List<DyeItem> teamColorList = Lists.newArrayList();
				if (ue.getTeam() != null) {
					teamColorList = getDyeListForTeam(ue.getTeam().getName());
				}

				ItemStack is = new ItemStack(Items.LEATHER_HELMET);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.HEAD, is);

				is = new ItemStack(Items.LEATHER_LEGGINGS);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.LEGS, is);

				is = new ItemStack(Items.LEATHER_BOOTS);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.FEET, is);

				is = new ItemStack(Items.LEATHER_CHESTPLATE);
				is = IDyeableArmorItem.dyeItem(is, teamColorList);
				ue.setItemStackToSlot(EquipmentSlotType.CHEST, is);

			}

			if (entityType == ModEntities.MOUNTED_ENTITY) {
				ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModItems.LANCEITEM));
			}

			if (entityType == ModEntities.PIKEMAN_ENTITY) {
				ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModItems.PIKEITEM));
			}
			//
//			ue.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
			// ue.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.DIAMOND_HELMET));
//			ue.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Items.IRON_LEGGINGS));

		}

		return e;

	}

	public static void serverDeSelectUnit(PlayerEntity player, String playerScoreboardname, int entityId) {
		if (TSRTS.playerSelections.containsKey(playerScoreboardname)) {
			if (TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.contains(entityId)) {
				// TSRTS.LOGGER.info("DE-Select");
				final Iterator<Integer> it = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.iterator();
				while (it.hasNext()) {
					int currentID = it.next();
					if (currentID == entityId) {
						it.remove();
						TSRTS.playerSelections.get(playerScoreboardname).hasChanged = true;

					}
				}
				int[] tmpids = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < tmpids.length; i++) {
					tmpids[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}

				ModNetwork.SendToPlayer((ServerPlayerEntity) player, new PlayerSelectionChangedPacketToClient(tmpids));
			}
		}
	}

	public static void serverSelectUnit(PlayerEntity player, String playerScoreboardname, int entityId) {
		if (TSRTS.playerSelections.containsKey(playerScoreboardname)) {
			if (TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.contains(entityId)) {
				// already selected if we wanted this to be a toggle this is where we edit it be removed

			} else {

				// TSRTS.LOGGER.info("Select");
				TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.add(entityId);
				TSRTS.playerSelections.get(playerScoreboardname).hasChanged = true;
				int[] tmpids = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < tmpids.length; i++) {
					tmpids[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}

				ModNetwork.SendToPlayer((ServerPlayerEntity) player, new PlayerSelectionChangedPacketToClient(tmpids));
			}
		} else {
			TSRTS.playerSelections.put(playerScoreboardname, new PlayerSelections());

		}

	}

	public static void ServerControlGroupToSelectedUnits(ServerPlayerEntity player, String playerScoreboardname, int[] entityIds) {
		// TSRTS.LOGGER.info("CONTROLGROUPBUG:" + "in ServerControlGroupToSelectedUnits for " + player.getName() + entityIds.toString());

		if (TSRTS.playerSelections.containsKey(playerScoreboardname)) {
			TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.clear();
			for (int i = 0; i < entityIds.length; i++) {
				TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.add(entityIds[i]);
				TSRTS.playerSelections.get(playerScoreboardname).hasChanged = true;

			}
		}

		ModNetwork.SendToPlayer(player, new PlayerSelectionChangedPacketToClient(entityIds));

	}

	public static void clientControlGroupToSelectedUnits(String playerScoreboardname, int controlGroupNumber) {
		// TSRTS.LOGGER.info("CONTROLGROUPBUG:" + "in clientControlGroupToSelectedUnits for " + playerScoreboardname + " group " + controlGroupNumber);
		switch (controlGroupNumber) {
		case 1:
			if (TSRTS.playerSelectionsControlGroup1 != null) {
				ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(TSRTS.playerSelectionsControlGroup1));
			}
			break;
		case 2:
			if (TSRTS.playerSelectionsControlGroup2 != null) {
				ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(TSRTS.playerSelectionsControlGroup2));
			}
			break;
		case 3:
			if (TSRTS.playerSelectionsControlGroup3 != null) {
				ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(TSRTS.playerSelectionsControlGroup3));
			}
			break;
		case 4:
			if (TSRTS.playerSelectionsControlGroup4 != null) {
				ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(TSRTS.playerSelectionsControlGroup4));
			}
			break;
		case 5:
			if (TSRTS.playerSelectionsControlGroup5 != null) {
				ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(TSRTS.playerSelectionsControlGroup5));
			}
			break;
		case 6:
			if (TSRTS.playerSelectionsControlGroup6 != null) {
				ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(TSRTS.playerSelectionsControlGroup6));
			}
			break;
		case 7:
			if (TSRTS.playerSelectionsControlGroup7 != null) {
				ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(TSRTS.playerSelectionsControlGroup7));
			}
			break;
		case 8:
			if (TSRTS.playerSelectionsControlGroup8 != null) {
				ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(TSRTS.playerSelectionsControlGroup8));
			}
			break;
		case 9:
			if (TSRTS.playerSelectionsControlGroup9 != null) {
				ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(TSRTS.playerSelectionsControlGroup9));
			}
			break;
		}
	}

	public static int GetSelectedCountForControlGroup(int controlGroupNumber) {
		switch (controlGroupNumber) {
		case 1:
			if (TSRTS.playerSelectionsControlGroup1 != null) {
				return TSRTS.playerSelectionsControlGroup1.length;
			}
		case 2:
			if (TSRTS.playerSelectionsControlGroup2 != null) {
				return TSRTS.playerSelectionsControlGroup2.length;
			}
		case 3:
			if (TSRTS.playerSelectionsControlGroup3 != null) {
				return TSRTS.playerSelectionsControlGroup3.length;
			}
		case 4:
			if (TSRTS.playerSelectionsControlGroup4 != null) {
				return TSRTS.playerSelectionsControlGroup4.length;
			}
		case 5:
			if (TSRTS.playerSelectionsControlGroup5 != null) {
				return TSRTS.playerSelectionsControlGroup5.length;
			}
		case 6:
			if (TSRTS.playerSelectionsControlGroup6 != null) {
				return TSRTS.playerSelectionsControlGroup6.length;
			}
		case 7:
			if (TSRTS.playerSelectionsControlGroup7 != null) {
				return TSRTS.playerSelectionsControlGroup7.length;
			}
		case 8:
			if (TSRTS.playerSelectionsControlGroup8 != null) {
				return TSRTS.playerSelectionsControlGroup8.length;
			}
		case 9:
			if (TSRTS.playerSelectionsControlGroup9 != null) {
				return TSRTS.playerSelectionsControlGroup9.length;
			}
		}
		return 0;
	}

	public static void clientSelectedUnitsToControlGroup(String playerScoreboardname, int controlGroupNumber) {
		// TSRTS.LOGGER.info("CONTROLGROUPBUG:" + "in client selected units to control group for " + playerScoreboardname + " group " + controlGroupNumber);
		if (TSRTS.playerSelections.containsKey(playerScoreboardname)) {
			// already selected if we wanted this to be a toggle this is where we edit it be removed

			switch (controlGroupNumber) {
			case 1:
				TSRTS.playerSelectionsControlGroup1 = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup1.length; i++) {
					TSRTS.playerSelectionsControlGroup1[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}
				break;
			case 2:
				TSRTS.playerSelectionsControlGroup2 = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup2.length; i++) {
					TSRTS.playerSelectionsControlGroup2[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}
				break;
			case 3:
				TSRTS.playerSelectionsControlGroup3 = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup3.length; i++) {
					TSRTS.playerSelectionsControlGroup3[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}
				break;
			case 4:
				TSRTS.playerSelectionsControlGroup4 = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup4.length; i++) {
					TSRTS.playerSelectionsControlGroup4[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}
				break;
			case 5:
				TSRTS.playerSelectionsControlGroup5 = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup5.length; i++) {
					TSRTS.playerSelectionsControlGroup5[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}
				break;
			case 6:
				TSRTS.playerSelectionsControlGroup6 = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup6.length; i++) {
					TSRTS.playerSelectionsControlGroup6[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}
				break;
			case 7:
				TSRTS.playerSelectionsControlGroup7 = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup7.length; i++) {
					TSRTS.playerSelectionsControlGroup7[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}
				break;
			case 8:
				TSRTS.playerSelectionsControlGroup8 = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup8.length; i++) {
					TSRTS.playerSelectionsControlGroup8[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}
				break;
			case 9:
				TSRTS.playerSelectionsControlGroup9 = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup9.length; i++) {
					TSRTS.playerSelectionsControlGroup9[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}
				break;
			}
		}
	}

	public static void SendMarketRatesToClient() {
		ModNetwork.SendToALLPlayers(new MarketRatesPacketToClient(MarketRates.foodRate, MarketRates.woodRate, MarketRates.stoneRate, MarketRates.ironRate, MarketRates.goldRate, MarketRates.diamondRate));

	}

	public static void SendTeamToClient(String teamName) {
		ModNetwork.SendToALLPlayers(new SendTeamInfoPacketToClient(TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)], teamName));

	}

	public static void SendResearchStatusToClient(ServerPlayerEntity player) {
		// team
		for (int i = 0; i < TeamEnum.values().length; i++) {
			for (Map.Entry<String, Research> entry : ModResearch.research_topics.entrySet()) {
				ModNetwork.SendToPlayer(player, new ResearchUnlockedPacketToClient(entry.getValue().getKey(), TeamEnum.values()[i].getName(), entry.getValue().isUnlocked(i)));
			}
		}
	}

	public static boolean hasNeededResource(String teamName, TeamInfo.Resources res, int amt) {

		if (TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].HasEnoughResource(res, amt)) {
			return true;
		}
		return false;

	}

	public static boolean SpendResourcesFromTeam(String teamName, TeamInfo.Resources res, int amt) {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].SpendResource(res, amt);
		// TSRTS.LOGGER.info("TEAM: " + teamName + " spent (RES ORD): " + res.ordinal() + " for amount: " + amt);
		return true;

	}

	public static void setResourcesOfTeam(String teamName, int[] amts) {
		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].SetResourceArray(amts);
		SendTeamToClient(teamName);

	}

	public static void AddResourcesToTeam(String teamName, TeamInfo.Resources res, int amt) {
		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].AddResource(res, amt);

	}

	public static void SelectedUnitsMoveToBlock(World world, BlockPos target, String ownerName, PlayerEntity player, boolean isRetreatMove, boolean isHoldingGround) {
		if (TSRTS.playerSelections.containsKey(ownerName)) {
			// found the player in the hasmap get and loop thru the enitties 1
			int count = TSRTS.playerSelections.get(ownerName).selectedUnits.size();

			List<BlockPos> lbp = SetFormationPoint(world, target.up(), count, Direction.getFacingFromVector(player.getLookVec().getX(), 0, player.getLookVec().getZ()));

			if (lbp != null && lbp.size() > 0) {
				for (int i = 0; i < TSRTS.playerSelections.get(ownerName).selectedUnits.size(); i++) {
					UnitEntity ue = (UnitEntity) world.getEntityByID(TSRTS.playerSelections.get(ownerName).selectedUnits.get(i));

					if (ue != null) {
						ue.ownerControlledDestination = lbp.get(i);/// context.getPos();
						// TSRTS.LOGGER.info("Destination set to:" + ue.ownerControlledDestination);
						ue.isRetreating = isRetreatMove;
						ue.isHoldingGround = isHoldingGround;
					}
				}
			}
		}
	}

	public static void SelectedUnitsTargetEntity(World world, LivingEntity target, String ownerName) {

		if (TSRTS.playerSelections.containsKey(ownerName)) {
			// found the player in the hasmap get and loop thru the enitties 1
			int count = TSRTS.playerSelections.get(ownerName).selectedUnits.size();

			for (int i = 0; i < TSRTS.playerSelections.get(ownerName).selectedUnits.size(); i++) {
				UnitEntity ue = (UnitEntity) world.getEntityByID(TSRTS.playerSelections.get(ownerName).selectedUnits.get(i));

				if (ue != null) {
					// TODO check if target is on "Other" team
					ue.setAttackTarget(target);
				}

			}
		}

	}

	public static List<BlockPos> SetFormationPoint(World world, BlockPos bp, int size, Direction direction) {
		List<BlockPos> lbp = new ArrayList<BlockPos>();
		// TSRTS.LOGGER.info("size = :" + size);
		if (size == 0) {
			return null;
		}
		boolean isEven = ((size % 2) == 0);

		int row = 0;
		int col = 0;
		int width = 0;
		int depth = 0;
		if (!isEven) {
			// ODD
			if (size <= 9) {
				width = size;
			} else {
				width = 9;
			}

		} else {
			// even
			if (size <= 9) {
				width = size - 1;
			} else {
				width = 9;
			}
		}

		if (width >= 9) {
			int tmpDepth = (size / 9);
			int tmpMod = (size % 9);
			if (tmpMod > 0) {
				depth = tmpDepth + 1;
			} else {
				depth = tmpDepth;
			}
		} else {
			depth = 1;
		}

		int widthOffset = 0;
		BlockPos lastWorkingPos = bp;
		BlockPos suggestedpos = null;
		widthOffset = (width - 1) / 2;
		for (int j = 0; j < depth; j++) {
			for (int i = 0; i < width; i++) {

				if ((j + 1) == depth) {
					// last row
					int remain = (size - (width * j));

					widthOffset = (remain - 1) / 2;

					if (i < remain) {
						suggestedpos = FindLocationAboveSolid(world, bp.offset(direction.rotateY(), (-widthOffset + i)).offset(direction.getOpposite(), j), lastWorkingPos);
						lastWorkingPos = suggestedpos;
						lbp.add(suggestedpos);
					}

				} else {
					suggestedpos = FindLocationAboveSolid(world, bp.offset(direction.rotateY(), (-widthOffset + i)).offset(direction.getOpposite(), j), lastWorkingPos);
					lastWorkingPos = suggestedpos;

					lbp.add(suggestedpos);
				}

			}
		}

		if (isEven && size <= 9) {
			suggestedpos = FindLocationAboveSolid(world, bp.offset(direction.getOpposite(), (depth - 1) + 1), lastWorkingPos);
			lastWorkingPos = suggestedpos;
			lbp.add(suggestedpos);
		}
		return lbp;
	}

	private static BlockPos FindLocationAboveSolid(World world, BlockPos bp, BlockPos altPos) {
		if (!world.getBlockState(bp).getMaterial().isSolid()) {
			return bp;
		} else {
			BlockPos bp2;
			int attempts = 0;
			for (bp2 = bp.up(); bp2.getY() < world.getHeight() && world.getBlockState(bp2).getMaterial().isSolid(); bp2 = bp2.up()) {
				attempts++;
				if (attempts >= 5) {
					return altPos;
				}
			}
			return bp2;

		}

	}

	public static void clearAreaTELast(World world, BlockPos bp, Direction d, Vec3i size) {
		BlockPos bp2 = bp;

		int xSize = size.getX();
		int ySize = size.getY();
		int zSize = size.getZ();

		Rotation r = Rotation.NONE;
		if (d == Direction.NORTH) {
			bp2 = bp.up().offset(d.rotateYCCW(), (xSize / 2)).offset(d, zSize).offset(d, (-1));
		}
		if (d == Direction.SOUTH) {
			r = Rotation.CLOCKWISE_180;
			bp2 = bp.up().offset(d.rotateYCCW(), -(xSize / 2));
		}

		if (d == Direction.WEST) {
			r = Rotation.COUNTERCLOCKWISE_90;

			bp2 = bp.up().offset(d.rotateYCCW(), -(zSize / 2)).offset(d, (xSize)).offset(d, (-1));

		}

		if (d == Direction.EAST) {
			r = Rotation.CLOCKWISE_90;
			bp2 = bp.up().offset(d.rotateYCCW(), (zSize / 2));
		}

		List<BlockPos> tePos = new ArrayList<BlockPos>();
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {

				for (int z = 0; z < zSize; z++) {
					BlockPos currentbp = bp2.add(x, y, z);
					if (!world.getBlockState(currentbp).getBlock().isAir(world.getBlockState(currentbp))) {
						// FOUND A BLOCK check if its a TE we care about
						if (world.getTileEntity(currentbp) != null) {
							tePos.add(currentbp);
						} else {
							// NOT A TE so destroy it now

							world.setBlockState(currentbp, Blocks.AIR.getDefaultState());
							world.notifyBlockUpdate(currentbp, world.getBlockState(currentbp), world.getBlockState(currentbp), 3);

						}

					}

				}

			}

		}
// Destroy any TE locations after to avoid miss-ordered Killing of it
		for (Iterator iterator = tePos.iterator(); iterator.hasNext();) {
			BlockPos currentbp = (BlockPos) iterator.next();
			world.setBlockState(currentbp, Blocks.AIR.getDefaultState());
			world.notifyBlockUpdate(currentbp, world.getBlockState(currentbp), world.getBlockState(currentbp), 3);

		}

	}

	public static boolean isInSphereOfInfluence(ScorePlayerTeam team, BlockPos bp) {
		if (Config.CONFIG_ENFORCE_SPHERE_OF_INFLUENCE.get() == false) {
			return true;
		}

		String teamName = team.getName();
		if (teamName == null) {
			return false;
		}
		for (Map.Entry<BlockPos, MapStructureData> entry : TSRTS.Structures.entrySet()) {
			MapStructureData msd = entry.getValue();
			if (msd.getTeamName().equals(teamName)) {
				BlockPos keyBP = entry.getKey();
				double distanceSq = ((bp.getX() - keyBP.getX()) * (bp.getX() - keyBP.getX())) + ((bp.getY() - keyBP.getY()) * (bp.getY() - keyBP.getY())) + ((bp.getZ() - keyBP.getZ()) * (bp.getZ() - keyBP.getZ()));
				double checkDistance = 1024;// same as 32 * 32;
				if (msd.getType() == STRUCTURE_TYPE.TOWN_HALL) {
					checkDistance = 4096;// same as 64 * 64;
				}
				if (distanceSq <= checkDistance) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isClearOfEntities(World world, BlockPos bp, Direction d, Vec3i size) {

		BlockPos bp2 = bp;

		int xSize = size.getX();
		int ySize = size.getY();
		int zSize = size.getZ();

		if (d == Direction.NORTH) {
			bp2 = bp.up().offset(d.rotateYCCW(), (xSize / 2)).offset(d, zSize).offset(d, (-1));
		}
		if (d == Direction.SOUTH) {
			bp2 = bp.up().offset(d.rotateYCCW(), -(xSize / 2));
		}

		if (d == Direction.WEST) {

			bp2 = bp.up().offset(d.rotateYCCW(), -(zSize / 2)).offset(d, (xSize)).offset(d, (-1));
		}

		if (d == Direction.EAST) {
			bp2 = bp.up().offset(d.rotateYCCW(), (zSize / 2));
		}

		BlockPos bp3 = bp2.add(xSize - 1, ySize - 1, zSize - 1);

		AxisAlignedBB aabb = new AxisAlignedBB(bp2, bp3);

		List<Entity> list = world.getEntitiesWithinAABB(PlayerEntity.class, aabb);
		if (list.size() > 0) {
			return false;
		}
		list = world.getEntitiesWithinAABB(UnitEntity.class, aabb);
		if (list.size() > 0) {
			return false;
		}
		return true;
	}

	public static boolean isValidLocation(World world, BlockPos bp, Direction d, Vec3i size) {
		boolean result = true;

		BlockPos bp2 = bp;

		int xSize = size.getX();
		int ySize = size.getY();
		int zSize = size.getZ();

		if (d == Direction.NORTH) {
			bp2 = bp.up().offset(d.rotateYCCW(), (xSize / 2)).offset(d, zSize).offset(d, (-1));
		}
		if (d == Direction.SOUTH) {
			bp2 = bp.up().offset(d.rotateYCCW(), -(xSize / 2));
		}

		if (d == Direction.WEST) {

			bp2 = bp.up().offset(d.rotateYCCW(), -(zSize / 2)).offset(d, (xSize)).offset(d, (-1));

		}

		if (d == Direction.EAST) {
			bp2 = bp.up().offset(d.rotateYCCW(), (zSize / 2));
		}

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {

				for (int z = 0; z < zSize; z++) {

					if ((!world.getBlockState(bp2.add(x, y, z)).getBlock().isAir(world.getBlockState(bp2.add(x, y, z))) && !(world.getBlockState(bp2.add(x, y, z)).getBlock().getMaterial(world.getBlockState(bp2.add(x, y, z))).isReplaceable()) && (world.getBlockState(bp2.add(x, y, z)).getBlock().getMaterial(world.getBlockState(bp2.add(x, y, z))).blocksMovement()))) {
						result = false;
						// BLOCK in the way
					}

					if (y == 0) {
						if (world.getBlockState(bp2.add(x, y - 1, z)).getBlock().isAir(world.getBlockState(bp2.add(x, y - 1, z))) || world.getBlockState(bp2.add(x, y - 1, z)).getMaterial() == Material.LAVA || world.getBlockState(bp2.add(x, y - 1, z)).getMaterial() == Material.WATER) {
							result = false;
							// No block under !!

						}
					}
				}

			}

		}

		return result;
	}

	public static boolean IsWallClearOfEntities(World world, BlockPos blockPos, Direction d) {

		int deep = 3;
		int width = 3;
		int height = 5;

		BlockPos bp = blockPos.offset(d.rotateYCCW(), (width / 2)).offset(d, deep);
		BlockPos bp2 = bp;
		if (d == Direction.NORTH) {
			bp2 = bp;

		}
		if (d == Direction.SOUTH) {
			bp2 = blockPos.offset(d.rotateYCCW(), -(width / 2)).offset(d, (1));
		}

		if (d == Direction.WEST) {

			bp2 = blockPos.offset(d.rotateYCCW(), -(deep / 2)).offset(d, (width));

		}

		if (d == Direction.EAST) {
			bp2 = blockPos.offset(d.rotateYCCW(), (deep / 2)).offset(d, (1));
		}

		// 3 wide 3 deep
		BlockPos bp3 = bp2.add(width - 1, height - 1, deep - 1);

		AxisAlignedBB aabb = new AxisAlignedBB(bp2, bp3);

		List<Entity> list = world.getEntitiesWithinAABB(PlayerEntity.class, aabb);
		if (list.size() > 0) {
			return false;
		}
		list = world.getEntitiesWithinAABB(UnitEntity.class, aabb);
		if (list.size() > 0) {
			return false;
		}
		return true;

	}

	public static boolean IsLocationValidForWall(World world, BlockPos blockPos, Direction d) {

		int deep = 3;
		int width = 3;
		int height = 5;

		BlockPos bp = blockPos.offset(d.rotateYCCW(), (width / 2)).offset(d, deep);
		BlockPos bp2 = bp;
		Rotation r = Rotation.NONE;
		if (d == Direction.NORTH) {
			bp2 = bp;

		}
		if (d == Direction.SOUTH) {
			r = Rotation.CLOCKWISE_180;
			bp2 = blockPos.offset(d.rotateYCCW(), -(width / 2)).offset(d, (1));
		}

		if (d == Direction.WEST) {
			r = Rotation.COUNTERCLOCKWISE_90;

			bp2 = blockPos.offset(d.rotateYCCW(), -(deep / 2)).offset(d, (width));

		}

		if (d == Direction.EAST) {
			r = Rotation.CLOCKWISE_90;
			bp2 = blockPos.offset(d.rotateYCCW(), (deep / 2)).offset(d, (1));
		}

		int clickedHeight = bp2.getY() + 1;
		int[][] startHeights;
		startHeights = new int[width][height];
		// 3 wide 3 deep
		boolean result = true;
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < deep; z++) {
				// find bottom air block above a solid block

				if ((world.getBlockState(bp2.add(x, 3, z)).isAir() || world.getBlockState(bp2.add(x, 3, z)).getMaterial().isReplaceable() || (!world.getBlockState(bp2.add(x, 3, z)).getMaterial().blocksMovement()))) {

				} else {
					result = false;
				}

			}

		}

		return result;
	}

	public static BlockState GetWallBlockForTeam(String teamName) {
		BlockState bs = Blocks.AIR.getDefaultState();
		switch (TeamEnum.values()[TeamEnum.getIDFromName(teamName)]) {
		case BLUE:
			bs = Blocks.STONE_BRICKS.getDefaultState();
			break;
		case RED:
			bs = Blocks.QUARTZ_BLOCK.getDefaultState();
			break;
		case GREEN:
			bs = Blocks.DARK_OAK_LOG.getDefaultState();
			break;
		case YELLOW:
			bs = Blocks.SMOOTH_SANDSTONE.getDefaultState();
			break;
		}

		return bs;

	}

	public static BlockState GetWallBlockDecoForTeam(String teamName) {
		BlockState bs = Blocks.AIR.getDefaultState();
		switch (TeamEnum.values()[TeamEnum.getIDFromName(teamName)]) {
		case BLUE:
			bs = Blocks.SPRUCE_LOG.getDefaultState();
			break;
		case RED:
			bs = Blocks.CHISELED_QUARTZ_BLOCK.getDefaultState();
			break;
		case GREEN:
			bs = Blocks.BIRCH_PLANKS.getDefaultState();
			break;
		case YELLOW:
			bs = Blocks.CHISELED_SANDSTONE.getDefaultState();
			break;
		}

		return bs;

	}

	public static BlockState GetWallBlockDeco2ForTeam(String teamName) {
		BlockState bs = Blocks.AIR.getDefaultState();
		switch (TeamEnum.values()[TeamEnum.getIDFromName(teamName)]) {
		case BLUE:
			bs = Blocks.STONE_BRICKS.getDefaultState();
			break;
		case RED:
			bs = Blocks.BRICKS.getDefaultState();
			break;
		case GREEN:
			bs = Blocks.STRIPPED_BIRCH_LOG.getDefaultState();
			break;
		case YELLOW:
			bs = Blocks.CUT_SANDSTONE.getDefaultState();
			break;
		}

		return bs;

	}

	public static boolean BuildWall(World world, BlockPos blockPos, Direction d, String ownerName, String teamName, boolean destroyMode, int[][] inStartHeights) {
		BlockState bs = Blocks.AIR.getDefaultState();
		BlockState bsDeco = Blocks.AIR.getDefaultState();
		BlockState bsDeco2 = Blocks.AIR.getDefaultState();
		if (!destroyMode) {
			bs = GetWallBlockForTeam(teamName);
			bsDeco = GetWallBlockDecoForTeam(teamName);
			bsDeco2 = GetWallBlockDeco2ForTeam(teamName);
		}

		if ((!(IsLocationValidForWall(world, blockPos, d)) && !destroyMode) || !isInSphereOfInfluence(world.getScoreboard().getPlayersTeam(ownerName), blockPos)) {
			return false;
		}
		if (!IsWallClearOfEntities(world, blockPos, d)) {
			return false;
		}

		// settings
		int deep = 3;
		int width = 3;
		int height = 5;

		BlockPos bp = blockPos.offset(d.rotateYCCW(), (width / 2)).offset(d, deep);
		BlockPos bp2 = bp;
		Rotation r = Rotation.NONE;
		if (d == Direction.NORTH) {
			bp2 = bp;

		}
		if (d == Direction.SOUTH) {
			r = Rotation.CLOCKWISE_180;
			bp2 = blockPos.offset(d.rotateYCCW(), -(width / 2)).offset(d, (1));
		}

		if (d == Direction.WEST) {
			r = Rotation.COUNTERCLOCKWISE_90;

			bp2 = blockPos.offset(d.rotateYCCW(), -(deep / 2)).offset(d, (width));

		}

		if (d == Direction.EAST) {
			r = Rotation.CLOCKWISE_90;
			bp2 = blockPos.offset(d.rotateYCCW(), (deep / 2)).offset(d, (1));
		}

		int clickedHeight = bp2.getY() + 1;
		int[][] startHeights;
		if (!destroyMode) {
			startHeights = new int[width][height];
			// 3 wide 3 deep
			for (int x = 0; x < width; x++) {
				for (int z = 0; z < deep; z++) {
					// find bottom air block above a solid block
					for (int y = 0; y < height + 2; y++) {

						if ((world.getBlockState(bp2.add(x, y, z)).isAir() || world.getBlockState(bp2.add(x, y, z)).getMaterial().isReplaceable() || (!world.getBlockState(bp2.add(x, y, z)).getMaterial().blocksMovement())) && (!world.getBlockState(bp2.add(x, y - 1, z)).isAir())) {
							// valid starting point for this x,y save it.
							startHeights[x][z] = y;
							y = height + 2;
						}
					}
				}
			}
		} else {
			startHeights = inStartHeights;
		}
		BlockState stateForLevel = bs;
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < deep; z++) {
				for (int y = 0; y < height; y++) {

					if (y == 1) {
						stateForLevel = bsDeco;
					} else if (y == height - 1) {
						stateForLevel = bsDeco2;
					} else {
						stateForLevel = bs;
					}

					world.setBlockState(bp2.add(x, startHeights[x][z] + y, z), stateForLevel);
					world.notifyBlockUpdate(bp2.add(x, startHeights[x][z] + y, z), world.getBlockState(bp2.add(x, startHeights[x][z] + y, z)), world.getBlockState(bp2.add(x, startHeights[x][z] + y, z)), 3);

					if ((y == height - 1) && ((x == 0 && z == 0) || (x == 0 && z == deep - 1) || (x == width - 1 && z == 0) || (x == width - 1 && z == deep - 1))) {
						world.setBlockState(bp2.add(x, startHeights[x][z] + y + 1, z), bsDeco);
						world.notifyBlockUpdate(bp2.add(x, startHeights[x][z] + y + 1, z), world.getBlockState(bp2.add(x, startHeights[x][z] + y + 1, z)), world.getBlockState(bp2.add(x, startHeights[x][z] + y, z)), 3);
					}

				}
			}
		}

		int x = 1;
		int z = 1;
		int y = 1;
		if (destroyMode) {
			world.setBlockState(bp2.add(x, startHeights[x][z] + y, z), Blocks.AIR.getDefaultState());
			world.notifyBlockUpdate(bp2.add(x, startHeights[x][z] + y, z), world.getBlockState(bp2.add(x, startHeights[x][z] + y, z)), world.getBlockState(bp2.add(x, startHeights[x][z] + y, z)), 3);

			// LOOK FOR TARGET BLOCKS AND TELL THE TE about it.
			AxisAlignedBB bb = new AxisAlignedBB(bp2, bp2.add(width, height, deep));
			List<TargetEntity> teList = world.getEntitiesWithinAABB(TargetEntity.class, bb);
			float health = 0;
			int[] ids = new int[teList.size()];
			for (int i = 0; i < teList.size(); i++) {
				teList.get(i).setHealth(0);
			}
		} else {
			world.setBlockState(bp2.add(x, startHeights[x][z] + y, z), ModBlocks.WALL_BLOCK.getDefaultState());
			world.notifyBlockUpdate(bp2.add(x, startHeights[x][z] + y, z), world.getBlockState(bp2.add(x, startHeights[x][z] + y, z)), world.getBlockState(bp2.add(x, startHeights[x][z] + y, z)), 3);
			TileEntity te = world.getTileEntity(bp2.add(x, startHeights[x][z] + y, z));

			OwnedTileEntity controllerTE;
			OwnedCooldownTileEntity octe = null;
			if (te instanceof OwnedTileEntity) {
				// its ours so we can set the rally point.
				((OwnedTileEntity) te).setOwner(ownerName);
				controllerTE = ((OwnedTileEntity) te);
				controllerTE.setStructureData(null);
				controllerTE.setRallyPoint(blockPos);
				if (controllerTE instanceof OwnedCooldownTileEntity) {

					octe = (OwnedCooldownTileEntity) controllerTE;

				}
				if (te instanceof WallTileEntity) {
					WallTileEntity wte = (WallTileEntity) te;
					wte.startHeights = startHeights;
					wte.storedDirection = d;
				}

			}
			float health = 0;

			for (x = 0; x < 3; x++) {
				for (z = 0; z < 3; z++) {
					if ((x == 1 && z == 0) || (x == 1 && z == 2) || (x == 0 && z == 1) || (x == 2 && z == 1)) {
						y = 1;
						health = 0;
						world.setBlockState(bp2.add(x, startHeights[x][z] + y, z), Blocks.AIR.getDefaultState());
						world.notifyBlockUpdate(bp2.add(x, startHeights[x][z] + y, z), world.getBlockState(bp2.add(x, startHeights[x][z] + y, z)), world.getBlockState(bp2.add(x, startHeights[x][z] + y, z)), 3);

						Entity e = ModEntities.TARGET_ENTITY.spawn(world, null, null, bp2.add(x, startHeights[x][z] + y, z), SpawnReason.TRIGGERED, true, true);
						if (e instanceof TargetEntity) {

							TargetEntity targetE = (TargetEntity) e;
							if (octe != null) {
								targetE.setOwningTePos(octe.getPos());
								targetE.setOwnerName(ownerName);

								float currhealth = Config.CONFIG_STRCTURE_TOTAL_HEALTH_WALL.get();
								targetE.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(currhealth);
								targetE.setHealth(currhealth);
								health = health + targetE.getHealth();
							}
						}

					}
				}
			}
			if (octe != null) {
				octe.setHealth(health);

				// TSRTS.LOGGER.info("Health set to :" + health);
			}
		}

		return true;
	}

	public static boolean LoadStructure(World world, ResourceLocation templateName, StructureData structureData, String ownerName, boolean shouldCheckifValid, boolean setHealth, float healthTarget, boolean checkSphere) {

		BlockPos pos = structureData.getSpawnPoint();
		Direction d = structureData.getDirection();
		Vec3i size = structureData.getSize();

		boolean spreadHealthAroundTargets = structureData.GetSpreadHealthAroundTargets();
		if (isValidLocation(world, pos, d, size) && (!checkSphere || isInSphereOfInfluence(world.getScoreboard().getPlayersTeam(ownerName), pos)) || !shouldCheckifValid) {

			// TODO need to consider if the player is on the same team as the entity or not !

			if (shouldCheckifValid) {
				// check for entities in the area!
				if (!(isClearOfEntities(world, pos, d, size))) {
					// FOUND AN ENTITY WE DONT WANT TO smother!
					return false;
				}
			}

			TemplateManager templateManager = ((ServerWorld) world).getStructureTemplateManager();

			Template template;
			try {
				template = templateManager.getTemplate(templateName);
			} catch (ResourceLocationException e) {
				e.printStackTrace();
				return false;
			}

			if (template == null) {
				return false;
			} else {
				// has template

				int xSize = template.getSize().getX();
				int ySize = template.getSize().getY();
				int zSize = template.getSize().getZ();

				BlockPos bp = pos.up().offset(d.rotateYCCW(), (xSize / 2)).offset(d, zSize);
				BlockPos bp2 = bp;
				Rotation r = Rotation.NONE;
				if (d == Direction.NORTH) {
					bp2 = bp;

				}
				if (d == Direction.SOUTH) {
					r = Rotation.CLOCKWISE_180;
					bp2 = pos.up().offset(d.rotateYCCW(), -(xSize / 2)).offset(d, (1));
				}

				if (d == Direction.WEST) {
					r = Rotation.COUNTERCLOCKWISE_90;

					bp2 = pos.up().offset(d.rotateYCCW(), -(zSize / 2)).offset(d, (xSize));

				}

				if (d == Direction.EAST) {
					r = Rotation.CLOCKWISE_90;
					bp2 = pos.up().offset(d.rotateYCCW(), (zSize / 2)).offset(d, (1));
				}

				PlacementSettings ps = (new PlacementSettings()).setRotation(r).setIgnoreEntities(false).setChunk((ChunkPos) null);
				template.addBlocksToWorldChunk(world, bp, ps);

				OwnedTileEntity controllerTE = null;

				OwnedCooldownTileEntity octe = null;
				for (int x = 0; x < xSize; x++) {
					for (int y = 0; y < ySize; y++) {
						for (int z = 0; z < zSize; z++) {

							// TSRTS.LOGGER.info("CHECKING FOR " + bp.add(x, y, z));
							world.notifyBlockUpdate(bp2.add(x, y, z), world.getBlockState(bp2.add(x, y, z)), world.getBlockState(bp2.add(x, y, z)), 3);

							if (world.getBlockState(bp2.add(x, y, z)).getBlock() instanceof OwnedBlock) {
								TileEntity te = world.getTileEntity(bp2.add(x, y, z));
								if (te instanceof OwnedTileEntity) {
									// its ours so we can set the rally point.
									((OwnedTileEntity) te).setRallyPoint(pos.up());
									((OwnedTileEntity) te).setOwner(ownerName);
									controllerTE = ((OwnedTileEntity) te);
									controllerTE.setStructureData(structureData);
									if (controllerTE instanceof OwnedCooldownTileEntity) {

										octe = (OwnedCooldownTileEntity) controllerTE;

									}
									if (te instanceof ResourceGenerator) {
										ResourceGenerator rg = (ResourceGenerator) te;
										rg.setResource(TeamInfo.GetResoureceForBlock(world.getBlockState(pos).getBlock()));
									}
								}
							}
						}
					}

				}

				// LOOK FOR TARGET BLOCKS AND TELL THE TE about it.
				AxisAlignedBB bb = new AxisAlignedBB(bp2, bp2.add(xSize, ySize, zSize));
				List<TargetEntity> teList = world.getEntitiesWithinAABB(TargetEntity.class, bb);
				float health = 0;

				int[] ids = new int[teList.size()];
				for (int i = 0; i < teList.size(); i++) {
					if (octe != null) {
						teList.get(i).setOwningTePos(octe.getPos());
						teList.get(i).setOwnerName(ownerName);
						float currhealth;

						if (setHealth) {
							if (spreadHealthAroundTargets) {
								currhealth = healthTarget / teList.size();
							} else {
								currhealth = healthTarget;
							}
							// TODO set the max here to override what was in the structure file ... call method that is overridden on each builder item if possible. or use passsed in pramater that is pouplated by callilng the "getTargetEntityHealthPer" on the builder item.
							teList.get(i).getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(currhealth);
							teList.get(i).setHealth(currhealth);
						}
						if (spreadHealthAroundTargets) {

							health = health + teList.get(i).getHealth();
						} else {
							health = healthTarget;
						}
					}

				}
				if (setHealth && octe != null) {
					octe.setHealth(health);

					// TSRTS.LOGGER.info("Health set to :" + health);
				}

			}
			return true;

		}

		return false;
	}

	public static void removeDeadEntityFromControlGroups(int entityId) {
		boolean found = false;
		if (TSRTS.playerSelectionsControlGroup1 != null) {
			if (TSRTS.playerSelectionsControlGroup1.length > 0) {
				int[] tmp = new int[TSRTS.playerSelectionsControlGroup1.length - 1];
				int j = 0;
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup1.length; i++) {
					if (TSRTS.playerSelectionsControlGroup1[i] == entityId) {
						found = true;
					} else {
						if (j < tmp.length) {
							tmp[j] = TSRTS.playerSelectionsControlGroup1[i];
						}
						j++;
					}
				}
				if (found) {
					TSRTS.playerSelectionsControlGroup1 = tmp;
				}

			}
		}
		found = false;
		if (TSRTS.playerSelectionsControlGroup2 != null) {
			if (TSRTS.playerSelectionsControlGroup2.length > 0) {

				int[] tmp = new int[TSRTS.playerSelectionsControlGroup2.length - 1];
				int j = 0;
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup2.length; i++) {
					if (TSRTS.playerSelectionsControlGroup2[i] == entityId) {
						found = true;
					} else {
						if (j < tmp.length) {

							tmp[j] = TSRTS.playerSelectionsControlGroup2[i];
						}
						j++;
					}
				}
				if (found) {
					TSRTS.playerSelectionsControlGroup2 = tmp;
				}

			}
		}

		found = false;
		if (TSRTS.playerSelectionsControlGroup3 != null) {
			if (TSRTS.playerSelectionsControlGroup3.length > 0) {

				int[] tmp = new int[TSRTS.playerSelectionsControlGroup3.length - 1];
				int j = 0;
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup3.length; i++) {
					if (TSRTS.playerSelectionsControlGroup3[i] == entityId) {
						found = true;
					} else {
						if (j < tmp.length) {

							tmp[j] = TSRTS.playerSelectionsControlGroup3[i];
						}
						j++;
					}
				}
				if (found) {
					TSRTS.playerSelectionsControlGroup3 = tmp;
				}
			}

		}

		found = false;
		if (TSRTS.playerSelectionsControlGroup4 != null) {
			if (TSRTS.playerSelectionsControlGroup4.length > 0) {

				int[] tmp = new int[TSRTS.playerSelectionsControlGroup4.length - 1];
				int j = 0;
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup4.length; i++) {
					if (TSRTS.playerSelectionsControlGroup4[i] == entityId) {
						found = true;
					} else {
						if (j < tmp.length) {

							tmp[j] = TSRTS.playerSelectionsControlGroup4[i];
						}
						j++;
					}
				}
				if (found) {
					TSRTS.playerSelectionsControlGroup4 = tmp;
				}
			}

		}

		found = false;
		if (TSRTS.playerSelectionsControlGroup5 != null) {
			if (TSRTS.playerSelectionsControlGroup5.length > 0) {

				int[] tmp = new int[TSRTS.playerSelectionsControlGroup5.length - 1];
				int j = 0;
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup5.length; i++) {
					if (TSRTS.playerSelectionsControlGroup5[i] == entityId) {
						found = true;
					} else {
						if (j < tmp.length) {

							tmp[j] = TSRTS.playerSelectionsControlGroup5[i];
						}
						j++;
					}
				}
				if (found) {
					TSRTS.playerSelectionsControlGroup5 = tmp;
				}
			}
		}

		found = false;
		if (TSRTS.playerSelectionsControlGroup6 != null) {
			if (TSRTS.playerSelectionsControlGroup6.length > 0) {

				int[] tmp = new int[TSRTS.playerSelectionsControlGroup6.length - 1];
				int j = 0;
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup6.length; i++) {
					if (TSRTS.playerSelectionsControlGroup6[i] == entityId) {
						found = true;
					} else {
						if (j < tmp.length) {

							tmp[j] = TSRTS.playerSelectionsControlGroup6[i];
						}
						j++;
					}
				}
				if (found) {
					TSRTS.playerSelectionsControlGroup6 = tmp;
				}

			}
		}

		found = false;
		if (TSRTS.playerSelectionsControlGroup7 != null) {
			if (TSRTS.playerSelectionsControlGroup7.length > 0) {

				int[] tmp = new int[TSRTS.playerSelectionsControlGroup7.length - 1];
				int j = 0;
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup7.length; i++) {
					if (TSRTS.playerSelectionsControlGroup7[i] == entityId) {
						found = true;
					} else {
						if (j < tmp.length) {

							tmp[j] = TSRTS.playerSelectionsControlGroup7[i];
						}
						j++;
					}
				}
				if (found) {
					TSRTS.playerSelectionsControlGroup7 = tmp;
				}
			}
		}

		found = false;
		if (TSRTS.playerSelectionsControlGroup8 != null) {
			if (TSRTS.playerSelectionsControlGroup8.length > 0) {

				int[] tmp = new int[TSRTS.playerSelectionsControlGroup8.length - 1];
				int j = 0;
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup8.length; i++) {
					if (TSRTS.playerSelectionsControlGroup8[i] == entityId) {
						found = true;
					} else {
						if (j < tmp.length) {

							tmp[j] = TSRTS.playerSelectionsControlGroup8[i];
						}
						j++;
					}
				}
				if (found) {
					TSRTS.playerSelectionsControlGroup8 = tmp;
				}
			}
		}

		found = false;
		if (TSRTS.playerSelectionsControlGroup9 != null) {
			if (TSRTS.playerSelectionsControlGroup9.length > 0) {

				int[] tmp = new int[TSRTS.playerSelectionsControlGroup9.length - 1];
				int j = 0;
				for (int i = 0; i < TSRTS.playerSelectionsControlGroup9.length; i++) {
					if (TSRTS.playerSelectionsControlGroup9[i] == entityId) {
						found = true;
					} else {
						if (j < tmp.length) {

							tmp[j] = TSRTS.playerSelectionsControlGroup9[i];
						}
						j++;
					}
				}
				if (found) {
					TSRTS.playerSelectionsControlGroup9 = tmp;
				}
			}
		}

	}

	public static void CheckTeamsAndCreatedIfNeeded(World world) {
		if (!world.isRemote) {
			// server only!

			Collection<ScorePlayerTeam> teams = world.getScoreboard().getTeams();
			boolean hasGreen = false;
			boolean hasRed = false;
			boolean hasBlue = false;
			boolean hasYellow = false;

			for (Iterator team = teams.iterator(); team.hasNext();) {
				ScorePlayerTeam scorePlayerTeam = (ScorePlayerTeam) team.next();
				if (scorePlayerTeam.getName().equals("red")) {
					hasRed = true;
				} else if (scorePlayerTeam.getName().equals("blue")) {
					hasBlue = true;
				} else if (scorePlayerTeam.getName().equals("green")) {
					hasGreen = true;
				} else if (scorePlayerTeam.getName().equals("yellow")) {
					hasYellow = true;
				}
			}

			if (!hasRed) {
				world.getScoreboard().createTeam("red").setColor(TextFormatting.RED);
			}
			if (!hasBlue) {
				world.getScoreboard().createTeam("blue").setColor(TextFormatting.BLUE);
			}
			if (!hasGreen) {
				world.getScoreboard().createTeam("green").setColor(TextFormatting.GREEN);
			}
			if (!hasYellow) {
				world.getScoreboard().createTeam("yellow").setColor(TextFormatting.YELLOW);
			}
			if (Config.CONFIG_GAME_MODE.get() == Modes.WAVESURVIVAL) {
				world.getScoreboard().addPlayerToTeam(Reference.WAVE_SURVIAL_AI_NAME, world.getScoreboard().getTeam(Reference.WAVE_SURVIAL_AI_TEAM_NAME));

				ServerEvents.WAVE_SURVIVAL_TEAM = world.getScoreboard().getTeam(Reference.WAVE_SURVIAL_AI_TEAM_NAME);
			}
		}
	}

	public static void SendMessageToAllTeams(World world, String LangLookup, String part2) {
		List<? extends PlayerEntity> players = world.getPlayers();
		for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			PlayerEntity playerEntity = (PlayerEntity) iterator.next();

			playerEntity.sendMessage(new TranslationTextComponent(LangLookup, part2));
			if (playerEntity instanceof ServerPlayerEntity) {
				ModNetwork.SendToPlayer((ServerPlayerEntity) playerEntity, new AlertToastToClient("tsrts.alerttoast.alert", LangLookup, AlertToastBackgroundType.WARN));
			}
		}
	}

	public static void SendMessageToTeam(World world, String team, String LangLookup) {
		SendMessageToTeam(world, team, LangLookup, true);
	}

	public static void SendMessageToEveryoneNoToast(World world, String LangLookup) {
		List<? extends PlayerEntity> players = world.getPlayers();
		for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			PlayerEntity playerEntity = (PlayerEntity) iterator.next();
			playerEntity.sendMessage(new TranslationTextComponent(LangLookup));
		}
	}

	public static void SendMessageToTeam(World world, String team, String LangLookup, boolean sendToast) {
		List<? extends PlayerEntity> players = world.getPlayers();
		for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			PlayerEntity playerEntity = (PlayerEntity) iterator.next();

			if (playerEntity.getTeam() != null) {
				if (playerEntity.getTeam().getName().equals(team)) {
					playerEntity.sendMessage(new TranslationTextComponent(LangLookup));

					if (sendToast) {
						if (playerEntity instanceof ServerPlayerEntity) {
							ModNetwork.SendToPlayer((ServerPlayerEntity) playerEntity, new AlertToastToClient("tsrts.alerttoast.alert", LangLookup, AlertToastBackgroundType.WARN));
						}
					}
				}
			}

		}
	}
}
