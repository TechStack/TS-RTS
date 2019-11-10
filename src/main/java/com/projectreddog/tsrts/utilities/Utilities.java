package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.blocks.OwnedBlock;
import com.projectreddog.tsrts.data.StructureData;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.PlayerReadyUpPacketToClient;
import com.projectreddog.tsrts.network.SendTeamInfoPacketToClient;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.OwnedTileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
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
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utilities {

	public static void setPlayerReady(World world, PlayerEntity player, Boolean isReady) {
		if (!world.isRemote) {
			// server
			TSRTS.isPlayerReadyMap.put(player.getScoreboardName(), isReady);

			//TODO: SEND PACKET FROM SERVER TO CLIENT!
			ModNetwork.SendToALLPlayers(new PlayerReadyUpPacketToClient(player.getEntityId(), isReady));


		} else {
			//client
			TSRTS.isPlayerReadyMap.put(player.getScoreboardName(), isReady);
			// do not send packet here to avoid looping between client and server
		}
	}

	public static boolean getPlayerReady(PlayerEntity player) {

		if (TSRTS.isPlayerReadyMap.containsKey(player.getScoreboardName())) {
			return TSRTS.isPlayerReadyMap.get(player.getScoreboardName());
		}
		return false;
	}


	public static boolean getPlayerReady(String playerScoreboardName) {

		if (TSRTS.isPlayerReadyMap.containsKey(playerScoreboardName)) {
			return TSRTS.isPlayerReadyMap.get(playerScoreboardName);
		}
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
				Utilities.setPlayerReady(player.world, player, !Utilities.getPlayerReady(player));

				break;

		}
		TSRTS.LOGGER.info("TEAM:" + player.getTeam().getName());

	}


	public static void PlayerBuysItem(PlayerEntity player, ItemStack itemStack) {
		// EntityType.ITEM.spawn(player.world, itemSTack, playerIn, pos, reason, p_220331_6_, p_220331_7_)

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

			//	ue.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
//			ue.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
			// ue.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.DIAMOND_HELMET));
//			ue.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Items.IRON_LEGGINGS));

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
				// e.printStackTrace();
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
		for (Iterator iterator = tePos.iterator(); iterator.hasNext(); ) {
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

					if (!world.getBlockState(bp2.add(x, y, z)).getBlock().isAir(world.getBlockState(bp2.add(x, y, z)))) {
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
								}
							}
						}
					}

				}

				// LOOK FOR TARGET BLOCKS AND TELL THE TE about it.
				AxisAlignedBB bb = new AxisAlignedBB(bp2, bp2.add(xSize, ySize, zSize));
				List<TargetEntity> teList = world.getEntitiesWithinAABB(TargetEntity.class, bb);

				int[] ids = new int[teList.size()];
				for (int i = 0; i < teList.size(); i++) {
					ids[i] = teList.get(i).getEntityId();
					teList.get(i).setOwnerName(ownerName);
				}

				if (controllerTE != null) {
					controllerTE.setTargetEntityIds(ids);
				}

			}

		}

		return false;
	}

}
