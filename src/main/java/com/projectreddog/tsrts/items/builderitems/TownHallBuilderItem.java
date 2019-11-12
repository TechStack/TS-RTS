package com.projectreddog.tsrts.items.builderitems;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;

public class TownHallBuilderItem extends BuilderItem {
	protected ResourceLocation templateNameRed100 = new ResourceLocation(Reference.MODID + ":" + "townhall_red_100");
	protected ResourceLocation templateNameRed50 = new ResourceLocation(Reference.MODID + ":" + "townhall_red_50");
	protected ResourceLocation templateNameRed0 = new ResourceLocation(Reference.MODID + ":" + "townhall_red_0");

	protected ResourceLocation templateNameYellow100 = new ResourceLocation(Reference.MODID + ":" + "townhall_yellow_100");
	protected ResourceLocation templateNameYellow50 = new ResourceLocation(Reference.MODID + ":" + "townhall_yellow_50");
	protected ResourceLocation templateNameYellow0 = new ResourceLocation(Reference.MODID + ":" + "townhall_yellow_0");

	protected ResourceLocation templateNameGreen100 = new ResourceLocation(Reference.MODID + ":" + "townhall_green_100");
	protected ResourceLocation templateNameGreen50 = new ResourceLocation(Reference.MODID + ":" + "townhall_green_50");
	protected ResourceLocation templateNameGreen0 = new ResourceLocation(Reference.MODID + ":" + "townhall_green_0");

	protected ResourceLocation templateNameBlue100 = new ResourceLocation(Reference.MODID + ":" + "townhall_blue_100");
	protected ResourceLocation templateNameBlue50 = new ResourceLocation(Reference.MODID + ":" + "townhall_blue_50");
	protected ResourceLocation templateNameBlue0 = new ResourceLocation(Reference.MODID + ":" + "townhall_blue_0");

	public TownHallBuilderItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_TOWN_HALL_BUILDER_ITEM);
	}

	public ResourceLocation getTemplateName100(String team) {
		if (team.contentEquals("green")) {
			return this.templateNameGreen100;
		} else if (team.contentEquals("red")) {
			return this.templateNameRed100;
		} else if (team.contentEquals("blue")) {
			return this.templateNameBlue100;
		} else {
			// assume yellow
			return this.templateNameYellow100;
		}
	}

	public ResourceLocation getTemplateName50(String team) {
		if (team.contentEquals("green")) {
			return this.templateNameGreen50;
		} else if (team.contentEquals("red")) {
			return this.templateNameRed50;
		} else if (team.contentEquals("blue")) {
			return this.templateNameBlue50;
		} else {
			// assume yellow
			return this.templateNameYellow50;
		}
	}

	public ResourceLocation getTemplateName0(String team) {
		if (team.contentEquals("green")) {
			return this.templateNameGreen0;
		} else if (team.contentEquals("red")) {
			return this.templateNameRed0;
		} else if (team.contentEquals("blue")) {
			return this.templateNameBlue0;
		} else {
			// assume yellow
			return this.templateNameYellow0;
		}
	}

	public Vec3i getSize() {

		return new Vec3i(17, 25, 17);
	}

	@Override
	public boolean CanPlaceOn(Block block) {

		return true;
	}

}
