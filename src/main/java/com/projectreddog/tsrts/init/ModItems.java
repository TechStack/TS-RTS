package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.items.LanceItem;
import com.projectreddog.tsrts.items.PikeItem;
import com.projectreddog.tsrts.items.RallyPointToolItem;
import com.projectreddog.tsrts.items.RetreateScepterItem;
import com.projectreddog.tsrts.items.SampleItem;
import com.projectreddog.tsrts.items.builderitems.ArcheryRangeBuilderItem;
import com.projectreddog.tsrts.items.builderitems.ArmoryBuilderItem;
import com.projectreddog.tsrts.items.builderitems.BarracksBuilderItem;
import com.projectreddog.tsrts.items.builderitems.FarmBuilderItem;
import com.projectreddog.tsrts.items.builderitems.GateBuilderItem;
import com.projectreddog.tsrts.items.builderitems.LumberYardBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteDiamondBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteEmeraldBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteGoldBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteIronBuilderItem;
import com.projectreddog.tsrts.items.builderitems.MinesiteStoneBuilderItem;
import com.projectreddog.tsrts.items.builderitems.ResearchCenterBuilderItem;
import com.projectreddog.tsrts.items.builderitems.SiegeWorkshopBuilderItem;
import com.projectreddog.tsrts.items.builderitems.StablesBuilderItem;
import com.projectreddog.tsrts.items.builderitems.TownHallBuilderItem;
import com.projectreddog.tsrts.items.builderitems.WallBuilderItem;
import com.projectreddog.tsrts.items.builderitems.WallStepsBuilderItem;
import com.projectreddog.tsrts.items.builderitems.WatchTowerBuilderItem;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {
	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_SAMPLE_ITEM)
	public static SampleItem SAMPLEITEM = new SampleItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_LANCE_ITEM)
	public static LanceItem LANCEITEM = new LanceItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_PIKE_ITEM)
	public static PikeItem PIKEITEM = new PikeItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_RETREAT_SEPTER_ITEM)
	public static RetreateScepterItem RETREATESEPTERITEM = new RetreateScepterItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_RALLY_POINT_TOOL)
	public static RallyPointToolItem RALLYPOINTTOOLITEM = new RallyPointToolItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TOWN_HALL_BUILDER_ITEM)
	public static TownHallBuilderItem TOWNHALLBUILDERITEM = new TownHallBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ARMORY_BUILDER_ITEM)
	public static ArmoryBuilderItem ARMORYBUILDERITEM = new ArmoryBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_SIEGE_WORKSHOP_BUILDER_ITEM)
	public static SiegeWorkshopBuilderItem SIEGEWORKSHOPBUILDERITEM = new SiegeWorkshopBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_WALL_BUILDER_ITEM)
	public static WallBuilderItem WALLBUILDERITEM = new WallBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_RESEARCH_CENTER_BUILDER_ITEM)
	public static ResearchCenterBuilderItem RESEARCHCENTERBUILDERITEM = new ResearchCenterBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_WALL_STEPS_BUILDER_ITEM)
	public static WallStepsBuilderItem WALLSTEPSBUILDERITEM = new WallStepsBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_GATE_BUILDER_ITEM)
	public static GateBuilderItem GATEBUILDERITEM = new GateBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_WATCH_TOWER_BUILDER_ITEM)
	public static WatchTowerBuilderItem WATCHTOWERBUILDERITEM = new WatchTowerBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_BARRACKS_BUILDER_ITEM)
	public static BarracksBuilderItem BARRACKSBUILDERITEM = new BarracksBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ARCHERY_RANGE_BUILDER_ITEM)
	public static ArcheryRangeBuilderItem ARCHERYRANGEBUILDERITEM = new ArcheryRangeBuilderItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_STABLES_BUILDER_ITEM)
	public static StablesBuilderItem STABLESBUILDERITEM = new StablesBuilderItem();

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
		RegisterItem(event, ModItems.STABLESBUILDERITEM);
		RegisterItem(event, ModItems.LUMBERYARDBUILDERITEM);
		RegisterItem(event, ModItems.MINESITEDIAMONDBUILDERITEM);
		RegisterItem(event, ModItems.MINESITEEMERALDBUILDERITEM);
		RegisterItem(event, ModItems.MINESITEGOLDBUILDERITEM);
		RegisterItem(event, ModItems.MINESITEIRONBUILDERITEM);
		RegisterItem(event, ModItems.MINESITESTONEBUILDERITEM);
		RegisterItem(event, ModItems.FARMBUILDERITEM);

		RegisterItem(event, ModItems.RALLYPOINTTOOLITEM);

		RegisterItem(event, ModItems.RETREATESEPTERITEM);

		RegisterItem(event, ModItems.LANCEITEM);

		RegisterItem(event, ModItems.PIKEITEM);
		RegisterItem(event, ModItems.WATCHTOWERBUILDERITEM);

		RegisterItem(event, ModItems.WALLBUILDERITEM);

		RegisterItem(event, ModItems.GATEBUILDERITEM);

		RegisterItem(event, ModItems.WALLSTEPSBUILDERITEM);

		RegisterItem(event, ModItems.RESEARCHCENTERBUILDERITEM);

		RegisterItem(event, ModItems.ARMORYBUILDERITEM);

		RegisterItem(event, ModItems.SIEGEWORKSHOPBUILDERITEM);

	}

	// Called to register every item.
	private static void RegisterItem(final RegistryEvent.Register<Item> event, Item item) {
		event.getRegistry().register(item);
	}
}
