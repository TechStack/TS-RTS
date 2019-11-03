package com.projectreddog.tsrts.items;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class TownCenterBuilderItem extends GenericBuilderItem {

	private ResourceLocation templateName = new ResourceLocation(Reference.MODID + ":" + "garrison_red_2");

	public TownCenterBuilderItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_TOWN_CENTER_BUILDER_ITEM);
	}

	public ResourceLocation getTemplateName() {
		return this.templateName;
	}

}
