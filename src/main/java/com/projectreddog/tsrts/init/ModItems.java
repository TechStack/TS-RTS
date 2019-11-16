package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.items.SampleItem;
import com.projectreddog.tsrts.items.builderitems.ArcheryRangeBuilderItem;
import com.projectreddog.tsrts.items.builderitems.BarracksBuilderItem;
import com.projectreddog.tsrts.items.builderitems.FarmBuilderItem;
import com.projectreddog.tsrts.items.builderitems.LumberYardBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteDiamondBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteEmeraldBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteGoldBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteIronBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteStoneBuilderItem;
import com.projectreddog.tsrts.items.builderitems.TownHallBuilderItem;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {
	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_SAMPLE_ITEM)
	public static SampleItem SAMPLEITEM = new SampleItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TOWN_HALL_BUILDER_ITEM)
	public static TownHallBuilderItem TOWNHALLBUILDERITEM = new TownHallBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_BARRACKS_BUILDER_ITEM)
	public static BarracksBuilderItem BARRACKSBUILDERITEM = new BarracksBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ARCHERY_RANGE_BUILDER_ITEM)
	public static ArcheryRangeBuilderItem ARCHERYRANGEBUILDERITEM = new ArcheryRangeBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_LUMBER_YARD_BUILDER_ITEM)
	public static LumberYardBuilderItem LUMBERYARDBUILDERITEM = new LumberYardBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_FARM_BUILDER_ITEM)
	public static FarmBuilderItem FARMBUILDERITEM = new FarmBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINE_SITE_DIAMOND_BUILDER_ITEM)
	public static MinesiteDiamondBuilderItem MINESITEDIAMONDBUILDERITEM = new MinesiteDiamondBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINE_SITE_EMERALD_BUILDER_ITEM)
	public static MinesiteEmeraldBuilderItem MINESITEEMERALDBUILDERITEM = new MinesiteEmeraldBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINE_SITE_GOLD_BUILDER_ITEM)
	public static MinesiteGoldBuilderItem MINESITEGOLDBUILDERITEM = new MinesiteGoldBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINE_SITE_IRON_BUILDER_ITEM)
	public static MinesiteIronBuilderItem MINESITEIRONBUILDERITEM = new MinesiteIronBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINE_SITE_STONE_BUILDER_ITEM)
	public static MinesiteStoneBuilderItem MINESITESTONEBUILDERITEM = new MinesiteStoneBuilderItem();

	public static void RegisterItems(final RegistryEvent.Register<Item> event) {
		RegisterItem(event, ModItems.SAMPLEITEM);
		RegisterItem(event, ModItems.TOWNHALLBUILDERITEM);
		RegisterItem(event, ModItems.BARRACKSBUILDERITEM);
		RegisterItem(event, ModItems.ARCHERYRANGEBUILDERITEM);
		RegisterItem(event, ModItems.LUMBERYARDBUILDERITEM);
		RegisterItem(event, ModItems.MINESITEDIAMONDBUILDERITEM);
		RegisterItem(event, ModItems.MINESITEEMERALDBUILDERITEM);
		RegisterItem(event, ModItems.MINESITEGOLDBUILDERITEM);
		RegisterItem(event, ModItems.MINESITEIRONBUILDERITEM);
		RegisterItem(event, ModItems.MINESITESTONEBUILDERITEM);
		RegisterItem(event, ModItems.FARMBUILDERITEM);

	}

	// Called to register every item.
	private static void RegisterItem(final RegistryEvent.Register<Item> event, Item item) {
		event.getRegistry().register(item);
	}
}
