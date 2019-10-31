package com.projectreddog.tsrts.utilities;

import java.util.ArrayList;
import java.util.List;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.SendTeamInfoPacketToClient;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.Direction;
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

			ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
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

}
