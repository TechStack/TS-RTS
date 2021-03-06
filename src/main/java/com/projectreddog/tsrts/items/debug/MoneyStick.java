package com.projectreddog.tsrts.items.debug;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.TeamInfo.Resources;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class MoneyStick extends Item {
	public ActionResultType onItemUse(ItemUseContext context) {

		if (!context.getPlayer().world.isRemote) {

			if (context.getPlayer().isCreative()) {
				Utilities.AddResourcesToTeam(context.getPlayer().getTeam().getName(), Resources.FOOD, 1000);
				Utilities.AddResourcesToTeam(context.getPlayer().getTeam().getName(), Resources.WOOD, 1000);
				Utilities.AddResourcesToTeam(context.getPlayer().getTeam().getName(), Resources.STONE, 1000);
				Utilities.AddResourcesToTeam(context.getPlayer().getTeam().getName(), Resources.IRON, 1000);
				Utilities.AddResourcesToTeam(context.getPlayer().getTeam().getName(), Resources.GOLD, 1000);
				Utilities.AddResourcesToTeam(context.getPlayer().getTeam().getName(), Resources.DIAMOND, 1000);
				Utilities.AddResourcesToTeam(context.getPlayer().getTeam().getName(), Resources.EMERALD, 1000);

			}
			return ActionResultType.SUCCESS;

		}

		return super.onItemUse(context);
	}

	public MoneyStick() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_MONEY_STICK_ITEM);
	}

}