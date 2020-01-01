package com.projectreddog.tsrts.items.builderitems;

import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.ResourceValues;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class ResearchCenterBuilderItem extends BuilderItem {

	protected ResourceLocation templateNameRed100 = new ResourceLocation(Reference.MODID + ":" + "researchcenter_red_100");
	protected ResourceLocation templateNameRed50 = null;
	protected ResourceLocation templateNameRed0 = new ResourceLocation(Reference.MODID + ":" + "researchcenter_red_0");

	protected ResourceLocation templateNameYellow100 = new ResourceLocation(Reference.MODID + ":" + "researchcenter_yellow_100");
	protected ResourceLocation templateNameYellow50 = null;
	protected ResourceLocation templateNameYellow0 = new ResourceLocation(Reference.MODID + ":" + "researchcenter_yellow_0");

	protected ResourceLocation templateNameGreen100 = new ResourceLocation(Reference.MODID + ":" + "researchcenter_green_100");
	protected ResourceLocation templateNameGreen50 = null;
	protected ResourceLocation templateNameGreen0 = new ResourceLocation(Reference.MODID + ":" + "researchcenter_green_0");

	protected ResourceLocation templateNameBlue100 = new ResourceLocation(Reference.MODID + ":" + "researchcenter_blue_100");
	protected ResourceLocation templateNameBlue50 = null;
	protected ResourceLocation templateNameBlue0 = new ResourceLocation(Reference.MODID + ":" + "researchcenter_blue_0");

	public ResearchCenterBuilderItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_RESEARCH_CENTER_BUILDER_ITEM);

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

		return new Vec3i(15, 12, 15);
	}

	@Override
	public boolean CanPlaceOn(Block block) {

		return true;
	}

	@Override
	public void ActionAfterSpawn(World world, PlayerEntity Player, BlockPos bp) {

	}

	@Override
	public float getTotalStructureHealth() {
		return Config.CONFIG_STRCTURE_TOTAL_HEALTH_RESEARCH_CENTER.get();
	}

	@Override
	public ResourceValues getResourceCosts() {
		return Config.CONFIG_BUILDING_COSTS_RESEARCH_CENTER;
	}
}
