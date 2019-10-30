package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.SendTeamInfoPacketToClient;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Utilities {

	public static void SpawnUnitForTeam(EntityType entityType, String Owner, World world, BlockPos pos, ScorePlayerTeam team) {
		Entity e = SpawnUnit(entityType, Owner, world, pos);
		if (team != null) {
			world.getScoreboard().addPlayerToTeam(e.getCachedUniqueIdString(), team);
		}

	}

	public static Entity SpawnUnit(EntityType entityType, String Owner, World world, BlockPos pos) {
		Entity e = entityType.spawn(world, null, null, pos, SpawnReason.TRIGGERED, true, true);
		if (e instanceof UnitEntity) {
			UnitEntity ue = (UnitEntity) e;
			ue.setOwnerName(Owner);

			ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.CROSSBOW));
			ue.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
			// ue.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.DIAMOND_HELMET));
			ue.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Items.IRON_LEGGINGS));

		}

		return e;

	}

	public static void SelectUnit(String playerScoreboardname, int entityId) {
		if (TSRTS.playerSelections.containsKey(playerScoreboardname)) {
			if (TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.contains(entityId)) {
				// already selected if we wanted this to be a toggle this is where we edit it be removed
			} else {
				TSRTS.playerSelections.get(playerScoreboardname).selectedUnits.add(entityId);
			}
		} else {
			throw new IllegalStateException(" COuld not find the player in the hasmap used for selections !");

		}
	}

	public static void SendTeamToClient(String teamName) {
		if (TSRTS.teamInfoMap.containsKey(teamName)) {
			ModNetwork.SendToALLPlayers(new SendTeamInfoPacketToClient(TSRTS.teamInfoMap.get(teamName), teamName));
		}

	}

	public static void AddResourcesToTeam(String teamName, TeamInfo.Resources res, int amt) {
		if (TSRTS.teamInfoMap.containsKey(teamName)) {
			TeamInfo ti = TSRTS.teamInfoMap.get(teamName);
			ti.AddResource(res, amt);
			TSRTS.teamInfoMap.put(teamName, ti);

			SendTeamToClient(teamName);

		} else {
			try {
				throw new IllegalStateException(" Team not found :" + teamName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
