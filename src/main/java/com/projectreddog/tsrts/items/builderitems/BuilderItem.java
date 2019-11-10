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

	private ResourceLocation templateName100;
	private ResourceLocation templateName50;
	private ResourceLocation templateName0;

	public abstract ResourceLocation getTemplateName100();

	public abstract ResourceLocation getTemplateName50();

	public abstract ResourceLocation getTemplateName0();

	public void setTemplateName100(ResourceLocation templateName) {
		this.templateName100 = templateName;

	}

	public void setTemplateName50(ResourceLocation templateName) {
		this.templateName50 = templateName;
	}

	public void setTemplateName0(ResourceLocation templateName) {
		this.templateName0 = templateName;
	}

	public abstract Vec3i getSize();

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {

		if (!context.getPlayer().world.isRemote) {

			Direction d = Direction.getFacingFromVector(context.getPlayer().getLookVec().getX(), 0, context.getPlayer().getLookVec().getZ());

			if (CanPlaceOn(context.getPlayer().world.getBlockState(context.getPos()).getBlock())) {
				if (Utilities.LoadStructure(context.getWorld(), getTemplateName100(), new StructureData(getTemplateName100(), getTemplateName50(), getTemplateName0(), context.getPos(), d, getSize()), context.getPlayer().getScoreboardName(), true)) {

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
