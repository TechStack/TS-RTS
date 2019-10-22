package com.projectreddog.tsrts.items;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;

public class SampleItem extends Item {

	public SampleItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_SAMPLE_ITEM);
	}

}
