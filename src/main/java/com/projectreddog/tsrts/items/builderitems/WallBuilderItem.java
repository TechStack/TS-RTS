package com.projectreddog.tsrts.items.builderitems;

import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class WallBuilderItem extends BuilderItem {

	public WallBuilderItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_WALL_BUILDER_ITEM);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {

		if (!context.getPlayer().world.isRemote) {

			Direction d = Direction.getFacingFromVector(context.getPlayer().getLookVec().getX(), 0, context.getPlayer().getLookVec().getZ());

			if (CanPlaceOn(context.getPlayer().world.getBlockState(context.getPos()).getBlock())) {
				if (Utilities.BuildWall(context.getWorld(), context.getPos(), d)) {

					context.getItem().shrink(1);
					context.getPlayer().container.detectAndSendChanges();
					ActionAfterSpawn(context.getWorld(), context.getPlayer(), context.getPos().up());

				} else {
					return ActionResultType.FAIL;

				}
			}
			return ActionResultType.FAIL;

		}

		return super.onItemUse(context);
	}

	public ResourceLocation getTemplateName100(String team) {
		if (team.contentEquals("green")) {
			return this.templateNameGreen100;
		} else if (team.contentEquals("red")) {
			return this.templateNameRed100;
		} else if (team.contentEquals("blue")) {
			return this.templateNameBlue100;
		} else {
			// assume yellow
			return this.templateNameYellow100;
		}
	}

	public ResourceLocation getTemplateName50(String team) {
		if (team.contentEquals("green")) {
			return this.templateNameGreen50;
		} else if (team.contentEquals("red")) {
			return this.templateNameRed50;
		} else if (team.contentEquals("blue")) {
			return this.templateNameBlue50;
		} else {
			// assume yellow
			return this.templateNameYellow50;
		}
	}

	public ResourceLocation getTemplateName0(String team) {
		if (team.contentEquals("green")) {
			return this.templateNameGreen0;
		} else if (team.contentEquals("red")) {
			return this.templateNameRed0;
		} else if (team.contentEquals("blue")) {
			return this.templateNameBlue0;
		} else {
			// assume yellow
			return this.templateNameYellow0;
		}
	}

	public Vec3i getSize() {

		return new Vec3i(3, 3, 3);
	}

	@Override
	public boolean CanPlaceOn(Block block) {

		return true;
	}

	@Override
	public void ActionAfterSpawn(World world, PlayerEntity player, BlockPos bp) {
	}

	@Override
	public float getTotalStructureHealth() {
		return Config.CONFIG_STRCTURE_TOTAL_HEALTH_WALL.get();
	}
}
