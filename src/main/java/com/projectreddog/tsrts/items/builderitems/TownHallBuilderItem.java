package com.projectreddog.tsrts.items.builderitems;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;

public class TownHallBuilderItem extends BuilderItem {

	private ResourceLocation templateName = new ResourceLocation(Reference.MODID + ":" + "barracks_red_2");

	public TownHallBuilderItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_TOWN_HALL_BUILDER_ITEM);
	}

	public ResourceLocation getTemplateName() {
		return this.templateName;
	}

	public Vec3i getSize() {

		return new Vec3i(9, 9, 9);
	}

}
