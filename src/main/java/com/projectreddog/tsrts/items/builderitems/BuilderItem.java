package com.projectreddog.tsrts.items.builderitems;

import com.projectreddog.tsrts.data.StructureData;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;

public abstract class BuilderItem extends Item {

	public BuilderItem(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}

	protected ResourceLocation templateNameRed100;
	protected ResourceLocation templateNameRed50;
	protected ResourceLocation templateNameRed0;

	protected ResourceLocation templateNameYellow100;
	protected ResourceLocation templateNameYellow50;
	protected ResourceLocation templateNameYellow0;

	protected ResourceLocation templateNameGreen100;
	protected ResourceLocation templateNameGreen50;
	protected ResourceLocation templateNameGreen0;

	protected ResourceLocation templateNameBlue100;
	protected ResourceLocation templateNameBlue50;
	protected ResourceLocation templateNameBlue0;

	public abstract ResourceLocation getTemplateName100(String team);

	public abstract ResourceLocation getTemplateName50(String team);

	public abstract ResourceLocation getTemplateName0(String team);

	public abstract Vec3i getSize();

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {

		if (!context.getPlayer().world.isRemote) {

			Direction d = Direction.getFacingFromVector(context.getPlayer().getLookVec().getX(), 0, context.getPlayer().getLookVec().getZ());

			if (CanPlaceOn(context.getPlayer().world.getBlockState(context.getPos()).getBlock())) {
				if (Utilities.LoadStructure(context.getWorld(), getTemplateName100(context.getPlayer().getTeam().getName()), new StructureData(getTemplateName100(context.getPlayer().getTeam().getName()), getTemplateName50(context.getPlayer().getTeam().getName()), getTemplateName0(context.getPlayer().getTeam().getName()), context.getPos(), d, getSize()), context.getPlayer().getScoreboardName(), true)) {

					context.getItem().shrink(1);

				} else {
					return ActionResultType.FAIL;

				}
			}
			return ActionResultType.FAIL;

		}

		return super.onItemUse(context);
	}

	public abstract boolean CanPlaceOn(Block block);

}
