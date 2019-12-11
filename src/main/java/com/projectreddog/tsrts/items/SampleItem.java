package com.projectreddog.tsrts.items;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SampleItem extends Item {

	public SampleItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_SAMPLE_ITEM);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

		EntityRayTraceResult entityraytraceresult = ProjectileHelper.func_221269_a(worldIn, playerIn, playerIn.getEyePosition(1), playerIn.getEyePosition(1).add(playerIn.getLook(1.0F).x * 32, playerIn.getLook(1.0F).y * 32, playerIn.getLook(1.0F).z * 32), playerIn.getBoundingBox().expand(playerIn.getLook(1.0F).scale(32)), (p_215312_0_) -> {
			return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
		}, 1024);
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (entityraytraceresult != null) {
			// found entity so attack it if possible !
			TSRTS.LOGGER.info("Hit entity:" + entityraytraceresult.getEntity().getName());
			if (entityraytraceresult.getEntity() instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) entityraytraceresult.getEntity();

				if (!playerIn.world.isRemote) {

					// TODO need to consider if the player is on the same team as the entity or not !

					if (playerIn.getTeam() != null && !(playerIn.getTeam().isSameTeam(le.getTeam()))) {
						Utilities.SelectedUnitsTargetEntity(worldIn, le, playerIn.getScoreboardName());
					} else if (playerIn.getTeam() == null) {
						Utilities.SelectedUnitsTargetEntity(worldIn, le, playerIn.getScoreboardName());
					}

				}
			}
		} else {
			// NO ENTITY FOUND so instead look for a block move

			Vec3d vec3d = playerIn.getEyePosition(1);
			Vec3d vec3d1 = playerIn.getLook(1);
			Vec3d vec3d2 = vec3d.add(vec3d1.x * 32, vec3d1.y * 32, vec3d1.z * 32);

			RayTraceResult rtr = worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, playerIn));
			if (rtr != null) {
				if (rtr.getType() == Type.BLOCK) {
					if (rtr instanceof BlockRayTraceResult) {
						BlockRayTraceResult brtr = (BlockRayTraceResult) rtr;

						if (!worldIn.isRemote) {
							TSRTS.LOGGER.info("Block HIT at:" + brtr.getPos());

							Utilities.SelectedUnitsMoveToBlock(worldIn, brtr.getPos(), playerIn.getScoreboardName(), playerIn, false);
						}
					}
				} else {
					// miss
				}
			}
		}
		return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	}

//	@Override
//	public ActionResultType onItemUse(ItemUseContext context) {
//		TSRTS.LOGGER.info("CLICKED of:" + context.getPos().up());
//
//		if (!context.getPlayer().world.isRemote) {
//
//			// TODO need to consider if the player is on the same team as the entity or not !
//
//			if (TSRTS.playerSelections.containsKey(context.getPlayer().getScoreboardName())) {
//				// found the player in the hasmap get and loop thru the enitties 1
//				int count = TSRTS.playerSelections.get(context.getPlayer().getScoreboardName()).selectedUnits.size();
//
//				List<BlockPos> lbp = SetFormationPoint(context.getWorld(), context.getPos().up(), count, Direction.getFacingFromVector(context.getPlayer().getLookVec().getX(), 0, context.getPlayer().getLookVec().getZ()));
//
//				if (lbp != null && lbp.size() > 0) {
//					for (int i = 0; i < TSRTS.playerSelections.get(context.getPlayer().getScoreboardName()).selectedUnits.size(); i++) {
//						UnitEntity ue = (UnitEntity) context.getWorld().getEntityByID(TSRTS.playerSelections.get(context.getPlayer().getScoreboardName()).selectedUnits.get(i));
//
//						if (ue != null) {
//							ue.ownerControlledDestination = lbp.get(i);/// context.getPos();
//							TSRTS.LOGGER.info("Destination set to:" + ue.ownerControlledDestination);
//
//						}
//					}
//				}
//			}
//
//		}
//
//		return super.onItemUse(context);
//	}

}
