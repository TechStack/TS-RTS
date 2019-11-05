package com.projectreddog.tsrts.items.builderitems;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;

public class MinesiteGoldBuilderItem extends BuilderItem {

	private ResourceLocation templateName = new ResourceLocation(Reference.MODID + ":" + "gold_red_2");

	public MinesiteGoldBuilderItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_MINE_SITE_GOLD_BUILDER_ITEM);
	}

	public ResourceLocation getTemplateName() {
		return this.templateName;
	}

	public Vec3i getSize() {

		return new Vec3i(9, 6, 9);

	}

}
