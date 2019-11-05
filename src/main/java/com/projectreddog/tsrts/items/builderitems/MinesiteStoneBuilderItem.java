package com.projectreddog.tsrts.items.builderitems;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;

public class MinesiteStoneBuilderItem extends BuilderItem {

	private ResourceLocation templateName = new ResourceLocation(Reference.MODID + ":" + "stone_red_2");

	public MinesiteStoneBuilderItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_MINE_SITE_STONE_BUILDER_ITEM);
	}

	public ResourceLocation getTemplateName() {
		return this.templateName;
	}

	public Vec3i getSize() {

		return new Vec3i(9, 6, 9);
	}

}
