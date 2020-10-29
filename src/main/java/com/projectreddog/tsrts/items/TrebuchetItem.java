package com.projectreddog.tsrts.items;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class TrebuchetItem extends Item {

	public TrebuchetItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_TREBUCHET_ITEM);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {

		ItemStack stack = context.getPlayer().getHeldItem(context.getHand());

		Utilities.SpawnUnitForTeam(UNIT_TYPES.TREBUCHET, context.getPlayer().getScoreboardName(), context.getWorld(), context.getPos(), context.getWorld().getScoreboard().getPlayersTeam(context.getPlayer().getScoreboardName()), null);

		if (!context.getPlayer().abilities.isCreativeMode) {
			stack.setCount(stack.getCount() - 1);
		}
		return super.onItemUse(context);
	}
}
