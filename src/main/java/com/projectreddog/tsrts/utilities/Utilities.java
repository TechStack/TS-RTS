package com.projectreddog.tsrts.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.TSRTS.GAMESTATE;
import com.projectreddog.tsrts.blocks.OwnedBlock;
import com.projectreddog.tsrts.data.StructureData;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModEntities;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.PlayerReadyUpPacketToClient;
import com.projectreddog.tsrts.network.PlayerSelectionChangedPacketToClient;
import com.projectreddog.tsrts.network.PlayerSelectionChangedPacketToServer;
import com.projectreddog.tsrts.network.SendTeamInfoPacketToClient;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.OwnedCooldownTileEntity;
import com.projectreddog.tsrts.tileentity.OwnedTileEntity;
import com.projectreddog.tsrts.tileentity.interfaces.ResourceGenerator;
import com.projectreddog.tsrts.utilities.TeamInfo.Resources;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

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

	public static void LobbyGuiHandler(int buttonID, ServerPlayerEntity player) {
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
			TSRTS.LOGGER.info("Lobby Button: READY:" + Utilities.getPlayerReady(player));

			break;
		case Reference.GUI_BUTTON_LOBBY_START:
			Utilities.startGame(player.world);
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

		// give players items
		List<? extends PlayerEntity> players = world.getPlayers();
		for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			PlayerEntity playerEntity = (PlayerEntity) iterator.next();
			Utilities.giveStartingItems(playerEntity);
			// close the gui's

			playerEntity.closeScreen();

		}

		// give teams resoruces
		Collection<ScorePlayerTeam> teams = world.getScoreboard().getTeams();

		// get starting resources
		int[] tmpRes = getStartingResourceAmounts();

		for (Iterator iterator = teams.iterator(); iterator.hasNext();) {
			ScorePlayerTeam team = (ScorePlayerTeam) iterator.next();
			String teamName = team.getName();
			Utilities.setResourcesOfTeam(teamName, tmpRes);
		}
	}

	private static void giveStartingItems(PlayerEntity playerEntity) {
		// TODO Auto-generated method stub
		GivePlayerItemStack(playerEntity, new ItemStack(Items.DIAMOND_SWORD, 1));
		GivePlayerItemStack(playerEntity, new ItemStack(ModItems.SAMPLEITEM));
		GivePlayerItemStack(playerEntity, new ItemStack(ModItems.TOWNHALLBUILDERITEM));

	}

	public static int[] getStartingResourceAmounts() {
		TeamInfo ti = new TeamInfo();
		ti.SetResource(Resources.FOOD, Config.CONFIG_START_AMT_FOOD.get());
		ti.SetResource(Resources.WOOD, Config.CONFIG_START_AMT_WOOD.get());
		ti.SetResource(Resources.STONE, Config.CONFIG_START_AMT_STONE.get());
		ti.SetResource(Resources.IRON, Config.CONFIG_START_AMT_IRON.get());
		ti.SetResource(Resources.GOLD, Config.CONFIG_START_AMT_GOLD.get());
		ti.SetResource(Resources.DIAMOND, Config.CONFIG_START_AMT_DIAMOND.get());
		ti.SetResource(Resources.EMERALD, Config.CONFIG_START_AMT_EMERALD.get());

		return ti.GetResourceArray();
	}

	public static void PlayerBuysItem(PlayerEntity player, ItemStack itemStack) {
		// EntityType.ITEM.spawn(player.world, itemSTack, playerIn, pos, reason, p_220331_6_, p_220331_7_)
//TODO SPEND RESROUCES here before giving the player the items !

		int foodCosts = getFoodCostsForBuilder(itemStack.getItem());
		int woodCosts = getWoodCostsForBuilder(itemStack.getItem());
		int stoneCosts = getStoneCostsForBuilder(itemStack.getItem());

		int ironCosts = getIronCostsForBuilder(itemStack.getItem());
		int goldCosts = getGoldCostsForBuilder(itemStack.getItem());
		int diamondCosts = getDiamondCostsForBuilder(itemStack.getItem());
		int emeraldCosts = getEmeraldCostsForBuilder(itemStack.getItem());

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

			if (result) {
				GivePlayerItemStack(player, itemStack);
			}

		}

	}

	public static int getFoodCostsForBuilder(Item item) {
		if (item == ModItems.FARMBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_FARM_FOOD.get();
		} else if (item == ModItems.LUMBERYARDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_LUMBER_YARD_FOOD.get();
		} else if (item == ModItems.MINESITESTONEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_STONE_FOOD.get();
		} else if (item == ModItems.MINESITEIRONBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_IRON_FOOD.get();
		} else if (item == ModItems.MINESITEGOLDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD_FOOD.get();
		} else if (item == ModItems.MINESITEDIAMONDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_FOOD.get();
		} else if (item == ModItems.MINESITEEMERALDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD_FOOD.get();
		} else if (item == ModItems.BARRACKSBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_BARRACKS_FOOD.get();
		} else if (item == ModItems.ARCHERYRANGEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE_FOOD.get();
		}
		return 0;
	}

	public static int getWoodCostsForBuilder(Item item) {
		if (item == ModItems.FARMBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_FARM_WOOD.get();
		} else if (item == ModItems.LUMBERYARDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_LUMBER_YARD_WOOD.get();
		} else if (item == ModItems.MINESITESTONEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_STONE_WOOD.get();
		} else if (item == ModItems.MINESITEIRONBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_IRON_WOOD.get();
		} else if (item == ModItems.MINESITEGOLDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD_WOOD.get();
		} else if (item == ModItems.MINESITEDIAMONDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_WOOD.get();
		} else if (item == ModItems.MINESITEEMERALDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD_WOOD.get();
		} else if (item == ModItems.BARRACKSBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_BARRACKS_WOOD.get();
		} else if (item == ModItems.ARCHERYRANGEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE_WOOD.get();
		}
		return 0;
	}

	public static int getStoneCostsForBuilder(Item item) {
		if (item == ModItems.FARMBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_FARM_STONE.get();
		} else if (item == ModItems.LUMBERYARDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_LUMBER_YARD_STONE.get();
		} else if (item == ModItems.MINESITESTONEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_STONE_STONE.get();
		} else if (item == ModItems.MINESITEIRONBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_IRON_STONE.get();
		} else if (item == ModItems.MINESITEGOLDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD_STONE.get();
		} else if (item == ModItems.MINESITEDIAMONDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_STONE.get();
		} else if (item == ModItems.MINESITEEMERALDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD_STONE.get();
		} else if (item == ModItems.BARRACKSBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_BARRACKS_STONE.get();
		} else if (item == ModItems.ARCHERYRANGEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE_STONE.get();
		}
		return 0;
	}

	public static int getIronCostsForBuilder(Item item) {
		if (item == ModItems.FARMBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_FARM_IRON.get();
		} else if (item == ModItems.LUMBERYARDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_LUMBER_YARD_IRON.get();
		} else if (item == ModItems.MINESITESTONEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_STONE_IRON.get();
		} else if (item == ModItems.MINESITEIRONBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_IRON_IRON.get();
		} else if (item == ModItems.MINESITEGOLDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD_IRON.get();
		} else if (item == ModItems.MINESITEDIAMONDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_IRON.get();
		} else if (item == ModItems.MINESITEEMERALDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD_IRON.get();
		} else if (item == ModItems.BARRACKSBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_BARRACKS_IRON.get();
		} else if (item == ModItems.ARCHERYRANGEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE_IRON.get();
		}
		return 0;
	}

	public static int getGoldCostsForBuilder(Item item) {
		if (item == ModItems.FARMBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_FARM_GOLD.get();
		} else if (item == ModItems.LUMBERYARDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_LUMBER_YARD_GOLD.get();
		} else if (item == ModItems.MINESITESTONEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_STONE_GOLD.get();
		} else if (item == ModItems.MINESITEIRONBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_IRON_GOLD.get();
		} else if (item == ModItems.MINESITEGOLDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD_GOLD.get();
		} else if (item == ModItems.MINESITEDIAMONDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_GOLD.get();
		} else if (item == ModItems.MINESITEEMERALDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD_GOLD.get();
		} else if (item == ModItems.BARRACKSBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_BARRACKS_GOLD.get();
		} else if (item == ModItems.ARCHERYRANGEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE_GOLD.get();
		}
		return 0;
	}

	public static int getDiamondCostsForBuilder(Item item) {
		if (item == ModItems.FARMBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_FARM_DIAMOND.get();
		} else if (item == ModItems.LUMBERYARDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_LUMBER_YARD_DIAMOND.get();
		} else if (item == ModItems.MINESITESTONEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_STONE_DIAMOND.get();
		} else if (item == ModItems.MINESITEIRONBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_IRON_DIAMOND.get();
		} else if (item == ModItems.MINESITEGOLDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD_DIAMOND.get();
		} else if (item == ModItems.MINESITEDIAMONDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_DIAMOND.get();
		} else if (item == ModItems.MINESITEEMERALDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD_DIAMOND.get();
		} else if (item == ModItems.BARRACKSBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_BARRACKS_DIAMOND.get();
		} else if (item == ModItems.ARCHERYRANGEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE_DIAMOND.get();
		}
		return 0;
	}

	public static int getEmeraldCostsForBuilder(Item item) {
		if (item == ModItems.FARMBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_FARM_EMERALD.get();
		} else if (item == ModItems.LUMBERYARDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_LUMBER_YARD_EMERALD.get();
		} else if (item == ModItems.MINESITESTONEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_STONE_EMERALD.get();
		} else if (item == ModItems.MINESITEIRONBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_IRON_EMERALD.get();
		} else if (item == ModItems.MINESITEGOLDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_GOLD_EMERALD.get();
		} else if (item == ModItems.MINESITEDIAMONDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_EMERALD.get();
		} else if (item == ModItems.MINESITEEMERALDBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_MINESITE_EMERALD_EMERALD.get();
		} else if (item == ModItems.BARRACKSBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_BARRACKS_EMERALD.get();
		} else if (item == ModItems.ARCHERYRANGEBUILDERITEM) {
			return Config.CONFIG_BUILDING_COSTS_ARCHERY_RANGE_EMERALD.get();
		}
		return 0;
	}

	public static void GivePlayerItemStack(PlayerEntity player, ItemStack itemStack) {

		boolean result = player.inventory.addItemStackToInventory(itemStack);

		if (!result) {
			player.dropItem(itemStack, false);

		}
		player.container.detectAndSendChanges();
	}

	public static void SpawnUnitForTeam(EntityType entityType, String Owner, World world, BlockPos pos, ScorePlayerTeam team, @Nullable BlockPos rallyPoint) {
		Entity e = SpawnUnit(entityType, Owner, world, pos, rallyPoint);
		if (team != null) {
			world.getScoreboard().addPlayerToTeam(e.getCachedUniqueIdString(), team);
		}

	}

	public static void SpawnMountedUnitForTeam(EntityType entityType, EntityType mountEntityType, String Owner, World world, BlockPos pos, ScorePlayerTeam team, @Nullable BlockPos rallyPoint) {
		Entity mount = mountEntityType.spawn(world, null, null, pos, SpawnReason.TRIGGERED, true, true);
		Entity e = SpawnUnit(entityType, Owner, world, pos, rallyPoint);
		e.startRiding(mount);
		if (team != null) {
			world.getScoreboard().addPlayerToTeam(e.getCachedUniqueIdString(), team);
			world.getScoreboard().addPlayerToTeam(mount.getCachedUniqueIdString(), team);
		}

	}

	public static Entity SpawnUnit(EntityType entityType, String Owner, World world, BlockPos pos, BlockPos rallyPoint) {
		Entity e = entityType.spawn(world, null, null, pos, SpawnReason.TRIGGERED, true, true);
		if (e instanceof UnitEntity) {
			UnitEntity ue = (UnitEntity) e;
			ue.setOwnerName(Owner);
			if (rallyPoint != null) {
				ue.ownerControlledDestination = rallyPoint;
			}

			if (entityType == ModEntities.ARCHER_MINION) {
				ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
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
				TSRTS.LOGGER.info("DE-Select");
				final Iterator<Integer> it = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.iterator();
				while (it.hasNext()) {
					int currentID = it.next();
					if (currentID == entityId) {
						it.remove();
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

				TSRTS.LOGGER.info("Select");
				TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.add(entityId);
				int[] tmpids = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];
				for (int i = 0; i < tmpids.length; i++) {
					tmpids[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
				}

				ModNetwork.SendToPlayer((ServerPlayerEntity) player, new PlayerSelectionChangedPacketToClient(tmpids));
			}
		} else {
			throw new IllegalStateException(" COuld not find the player in the hasmap used for selections !");

		}

	}

	public static void ServerControlGroupToSelectedUnits(ServerPlayerEntity player, String playerScoreboardname, int[] entityIds) {
		if (TSRTS.playerSelections.containsKey(playerScoreboardname)) {
			TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.clear();
			for (int i = 0; i < entityIds.length; i++) {
				TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.add(entityIds[i]);

			}
		}

		ModNetwork.SendToPlayer(player, new PlayerSelectionChangedPacketToClient(entityIds));

	}

	public static void clientControlGroupToSelectedUnits(String playerScoreboardname, int controlGroupNumber) {

		int[] activeSelections = null;
		switch (controlGroupNumber) {
		case 1:
			activeSelections = TSRTS.playerSelectionsControlGroup1;

			break;
		case 2:

			activeSelections = TSRTS.playerSelectionsControlGroup2;
			break;
		case 3:

			activeSelections = TSRTS.playerSelectionsControlGroup3;
			break;
		case 4:

			activeSelections = TSRTS.playerSelectionsControlGroup4;
			break;
		case 5:

			activeSelections = TSRTS.playerSelectionsControlGroup5;
			break;
		case 6:

			activeSelections = TSRTS.playerSelectionsControlGroup6;
			break;
		case 7:

			activeSelections = TSRTS.playerSelectionsControlGroup7;
			break;
		case 8:

			activeSelections = TSRTS.playerSelectionsControlGroup8;
			break;
		case 9:

			activeSelections = TSRTS.playerSelectionsControlGroup9;
			break;

		}
		// TSRTS.playerSelections.put(playerScoreboardname, activeSelections);
		if (activeSelections != null) {

			ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(activeSelections));
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
		if (TSRTS.playerSelections.containsKey(playerScoreboardname)) {
			// already selected if we wanted this to be a toggle this is where we edit it be removed

			int[] activeSelections = new int[TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.size()];

			for (int i = 0; i < activeSelections.length; i++) {
				activeSelections[i] = TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.get(i);
			}

			switch (controlGroupNumber) {
			case 1:
				TSRTS.playerSelectionsControlGroup1 = activeSelections;
				break;
			case 2:
				TSRTS.playerSelectionsControlGroup2 = activeSelections;
				break;
			case 3:
				TSRTS.playerSelectionsControlGroup3 = activeSelections;
				break;
			case 4:
				TSRTS.playerSelectionsControlGroup4 = activeSelections;
				break;
			case 5:
				TSRTS.playerSelectionsControlGroup5 = activeSelections;
				break;
			case 6:
				TSRTS.playerSelectionsControlGroup6 = activeSelections;
				break;
			case 7:
				TSRTS.playerSelectionsControlGroup7 = activeSelections;
				break;
			case 8:
				TSRTS.playerSelectionsControlGroup8 = activeSelections;
				break;
			case 9:
				TSRTS.playerSelectionsControlGroup9 = activeSelections;
				break;
			}
		}

	}

	public static void SendTeamToClient(String teamName) {
		ModNetwork.SendToALLPlayers(new SendTeamInfoPacketToClient(TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)], teamName));

	}

	public static boolean hasNeededResource(String teamName, TeamInfo.Resources res, int amt) {

		if (TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].HasEnoughResource(res, amt)) {
			return true;
		}
		return false;

	}

	public static boolean SpendResourcesFromTeam(String teamName, TeamInfo.Resources res, int amt) {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].SpendResource(res, amt);
		TSRTS.LOGGER.info("TEAM: " + teamName + " spent (RES ORD): " + res.ordinal() + " for amount: " + amt);
		SendTeamToClient(teamName);
		return true;

	}

	public static void setResourcesOfTeam(String teamName, int[] amts) {
		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].SetResourceArray(amts);
		SendTeamToClient(teamName);

	}

	public static void AddResourcesToTeam(String teamName, TeamInfo.Resources res, int amt) {
		TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].AddResource(res, amt);

		SendTeamToClient(teamName);

	}

	public static void SelectedUnitsMoveToBlock(World world, BlockPos target, String ownerName, PlayerEntity player) {
		if (TSRTS.playerSelections.containsKey(ownerName)) {
			// found the player in the hasmap get and loop thru the enitties 1
			int count = TSRTS.playerSelections.get(ownerName).selectedUnits.size();

			List<BlockPos> lbp = SetFormationPoint(world, target.up(), count, Direction.getFacingFromVector(player.getLookVec().getX(), 0, player.getLookVec().getZ()));

			if (lbp != null && lbp.size() > 0) {
				for (int i = 0; i < TSRTS.playerSelections.get(ownerName).selectedUnits.size(); i++) {
					UnitEntity ue = (UnitEntity) world.getEntityByID(TSRTS.playerSelections.get(ownerName).selectedUnits.get(i));

					if (ue != null) {
						ue.ownerControlledDestination = lbp.get(i);/// context.getPos();
						TSRTS.LOGGER.info("Destination set to:" + ue.ownerControlledDestination);

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
		TSRTS.LOGGER.info("size = :" + size);
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

		widthOffset = (width - 1) / 2;
		for (int j = 0; j < depth; j++) {
			for (int i = 0; i < width; i++) {

				if ((j + 1) == depth) {
					// last row
					int remain = (size - (width * j));

					widthOffset = (remain - 1) / 2;

					if (i < remain) {

						lbp.add(bp.offset(direction.rotateY(), (-widthOffset + i)).offset(direction.getOpposite(), j));
					}

				} else {

					lbp.add(bp.offset(direction.rotateY(), (-widthOffset + i)).offset(direction.getOpposite(), j));
				}

			}
		}

		if (isEven && size <= 9) {

			lbp.add(bp.offset(direction.getOpposite(), (depth - 1) + 1));
		}
		return lbp;
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

	public static boolean isValidLocation(World world, BlockPos bp, Direction d, Vec3i size) {
		boolean result = true;

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

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {

				for (int z = 0; z < zSize; z++) {

					if ((!world.getBlockState(bp2.add(x, y, z)).getBlock().isAir(world.getBlockState(bp2.add(x, y, z))) && !(world.getBlockState(bp2.add(x, y, z)).getBlock().getMaterial(world.getBlockState(bp2.add(x, y, z))).isReplaceable()) && (world.getBlockState(bp2.add(x, y, z)).getBlock().getMaterial(world.getBlockState(bp2.add(x, y, z))).blocksMovement()))) {
						result = false;
						// BLOCK in the way
					}

					if (y == 0) {
						if (world.getBlockState(bp2.add(x, y - 1, z)).getBlock().isAir(world.getBlockState(bp2.add(x, y - 1, z)))) {
							result = false;
							// No block under !!

						}
					}
				}

			}

		}

		return result;
	}

	public static boolean LoadStructure(World world, ResourceLocation templateName, StructureData structureData, String ownerName, Boolean shouldCheckifValid) {

		BlockPos pos = structureData.getSpawnPoint();
		Direction d = structureData.getDirection();
		Vec3i size = structureData.getSize();

		if (isValidLocation(world, pos, d, size) || !shouldCheckifValid) {

			// TODO need to consider if the player is on the same team as the entity or not !

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
					if (controllerTE != null) {
						teList.get(i).setOwningTePos(controllerTE.getPos());
						teList.get(i).setOwnerName(ownerName);
						health = health + teList.get(i).getHealth();
					}

				}
				if (controllerTE instanceof OwnedCooldownTileEntity) {
					OwnedCooldownTileEntity octe = (OwnedCooldownTileEntity) controllerTE;
					octe.setHealth(health);

					TSRTS.LOGGER.info("Health set to :" + health);
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
		}
	}
}
