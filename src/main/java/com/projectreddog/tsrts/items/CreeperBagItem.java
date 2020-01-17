package com.projectreddog.tsrts.items;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;

public class CreeperBagItem extends Item {

	public CreeperBagItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_CREEPER_BAG_ITEM);
	}

}
