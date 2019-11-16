package com.projectreddog.tsrts.handler;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {

	public static final String CATEGORY_GENERAL = "general";

	public static final String CATEGORY_UNIT_COST = "unit_cost";

	public static final String CATEGORY_BUILDING_COST = "building_cost";

	public static final String CATEGORY_STARTUP_RESOURCES = "startup_resources";

	public static final String CATEGORY_RESOURCE_GENERATION = "resource_generation";

	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec CLIENT_CONFIG;

	public static ForgeConfigSpec.EnumValue<Modes> CONFIG_GAME_MODE;
	// EXAMPLE: public static ForgeConfigSpec.IntValue CONFIG_INT_VALUE_VAR_NAME;
	// Archers
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_MINION_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_MINION_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_MINION_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_MINION_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_MINION_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_MINION_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_MINION_EMERALD;

	// archers
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_ARCHER_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_ARCHER_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_ARCHER_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_ARCHER_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_ARCHER_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_ARCHER_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_UNIT_COSTS_ARCHER_EMERALD;

	// starting resrources

	public static ForgeConfigSpec.IntValue CONFIG_START_AMT_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_START_AMT_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_START_AMT_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_START_AMT_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_START_AMT_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_START_AMT_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_START_AMT_EMERALD;

	// building costs
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_FARM_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_FARM_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_FARM_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_FARM_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_FARM_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_FARM_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_FARM_EMERALD;

	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_LUMBER_YARD_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_LUMBER_YARD_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_LUMBER_YARD_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_LUMBER_YARD_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_LUMBER_YARD_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_LUMBER_YARD_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_LUMBER_YARD_EMERALD;

	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_STONE_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_STONE_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_STONE_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_STONE_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_STONE_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_STONE_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_STONE_EMERALD;

	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_IRON_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_IRON_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_IRON_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_IRON_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_IRON_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_IRON_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_IRON_EMERALD;

	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_GOLD_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_GOLD_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_GOLD_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_GOLD_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_GOLD_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_GOLD_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_GOLD_EMERALD;

	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_EMERALD;

	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_EMERALD_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_EMERALD_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_EMERALD_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_EMERALD_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_EMERALD_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_EMERALD_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_MINESITE_EMERALD_EMERALD;

	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_BARRACKS_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_BARRACKS_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_BARRACKS_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_BARRACKS_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_BARRACKS_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_BARRACKS_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_BARRACKS_EMERALD;

	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_ARCHERY_RANGE_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_ARCHERY_RANGE_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_ARCHERY_RANGE_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_ARCHERY_RANGE_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_ARCHERY_RANGE_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_ARCHERY_RANGE_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_ARCHERY_RANGE_EMERALD;

	public static ForgeConfigSpec.IntValue CONFIG_TOWN_HALL_GENERATE_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_TOWN_HALL_GENERATE_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_TOWN_HALL_GENERATE_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_TOWN_HALL_GENERATE_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_TOWN_HALL_GENERATE_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_TOWN_HALL_GENERATE_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_TOWN_HALL_GENERATE_EMERALD;

	static {

		setupGeneralConfig();
		setupStartinResourcesConfig();
		setupResourceGeneration();
		setupUnitCostConfig();
		setupBuildingCostConfig();

		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();

	}

	private static void setupStartinResourcesConfig() {
		COMMON_BUILDER.comment("Starting resources").push(CATEGORY_STARTUP_RESOURCES);
		CONFIG_START_AMT_FOOD = COMMON_BUILDER.comment("Defines the starting amount of food").defineInRange("StartingFood", 100, 0, 100000);
		CONFIG_START_AMT_WOOD = COMMON_BUILDER.comment("Defines the starting amount of wood").defineInRange("StartingWood", 100, 0, 100000);
		CONFIG_START_AMT_STONE = COMMON_BUILDER.comment("Defines the starting amount of stone").defineInRange("StartingStone", 75, 0, 100000);
		CONFIG_START_AMT_IRON = COMMON_BUILDER.comment("Defines the starting amount of iron").defineInRange("StartingIron", 50, 0, 100000);
		CONFIG_START_AMT_GOLD = COMMON_BUILDER.comment("Defines the starting amount of gold").defineInRange("StartingGold", 25, 0, 100000);
		CONFIG_START_AMT_DIAMOND = COMMON_BUILDER.comment("Defines the starting amount of diamond").defineInRange("StartingDiamond", 0, 0, 100000);
		CONFIG_START_AMT_EMERALD = COMMON_BUILDER.comment("Defines the starting amount of emerald").defineInRange("StartingEmerald", 0, 0, 100000);

		COMMON_BUILDER.pop();
	}

	private static void setupResourceGeneration() {
		COMMON_BUILDER.comment("resource generation").push(CATEGORY_RESOURCE_GENERATION);

		CONFIG_TOWN_HALL_GENERATE_FOOD = COMMON_BUILDER.comment("Defines the amount of food the town hall generate per unit of time").defineInRange("townHallGeneratesFood", 5, 0, 100000);
		CONFIG_TOWN_HALL_GENERATE_WOOD = COMMON_BUILDER.comment("Defines the amount of wood the town hall generate per unit of time").defineInRange("townHallGeneratesWood", 5, 0, 100000);
		CONFIG_TOWN_HALL_GENERATE_STONE = COMMON_BUILDER.comment("Defines the amount of stone the town hall generate per unit of time").defineInRange("townHallGeneratesStone", 5, 0, 100000);
		CONFIG_TOWN_HALL_GENERATE_IRON = COMMON_BUILDER.comment("Defines the amount of iron the town hall generate per unit of time").defineInRange("townHallGeneratesIron", 5, 0, 100000);
		CONFIG_TOWN_HALL_GENERATE_GOLD = COMMON_BUILDER.comment("Defines the amount of gold the town hall generate per unit of time").defineInRange("townHallGeneratesGold", 1, 0, 100000);
		CONFIG_TOWN_HALL_GENERATE_DIAMOND = COMMON_BUILDER.comment("Defines the amount of diamond the town hall generate per unit of time").defineInRange("townHallGeneratesDiamond", 1, 0, 100000);
		CONFIG_TOWN_HALL_GENERATE_EMERALD = COMMON_BUILDER.comment("Defines the amount of emerald the town hall generate per unit of time").defineInRange("townHallGeneratesEmerald", 0, 0, 100000);

		COMMON_BUILDER.pop();

	}

	private static void setupBuildingCostConfig() {
		COMMON_BUILDER.comment("Building Costs").push(CATEGORY_BUILDING_COST);

		CONFIG_BUILDING_COSTS_FARM_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the farm").defineInRange("farmBulidingCostsFood", 10, 0, 100000);
		CONFIG_BUILDING_COSTS_FARM_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the farm").defineInRange("farmBulidingCostsWood", 15, 0, 100000);
		CONFIG_BUILDING_COSTS_FARM_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the farm").defineInRange("farmBulidingCostsStone", 5, 0, 100000);
		CONFIG_BUILDING_COSTS_FARM_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the farm").defineInRange("farmBulidingCostsIron", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_FARM_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the farm").defineInRange("farmBulidingCostsGold", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_FARM_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the farm").defineInRange("farmBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_FARM_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the farm").defineInRange("farmBulidingCostsEmerald", 0, 0, 100000);

		CONFIG_BUILDING_COSTS_LUMBER_YARD_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the lumber Yard").defineInRange("lumberYardBulidingCostsFood", 10, 0, 100000);
		CONFIG_BUILDING_COSTS_LUMBER_YARD_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the lumber Yard").defineInRange("lumberYardBulidingCostsWood", 10, 0, 100000);
		CONFIG_BUILDING_COSTS_LUMBER_YARD_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the lumber Yard").defineInRange("lumberYardBulidingCostsStone", 15, 0, 100000);
		CONFIG_BUILDING_COSTS_LUMBER_YARD_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the lumber Yard").defineInRange("lumberYardBulidingCostsIron", 5, 0, 100000);
		CONFIG_BUILDING_COSTS_LUMBER_YARD_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the lumber Yard").defineInRange("lumberYardBulidingCostsGold", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_LUMBER_YARD_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the lumber Yard").defineInRange("lumberYardBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_LUMBER_YARD_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the lumber Yard").defineInRange("lumberYardBulidingCostsEmerald", 0, 0, 100000);

		CONFIG_BUILDING_COSTS_MINESITE_STONE_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the minesite Stone").defineInRange("minesiteStoneBulidingCostsFood", 22, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_STONE_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the minesite Stone").defineInRange("minesiteStoneBulidingCostsWood", 30, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_STONE_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the minesite Stone").defineInRange("minesiteStoneBulidingCostsStone", 15, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_STONE_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the minesite Stone").defineInRange("minesiteStoneBulidingCostsIron", 15, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_STONE_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the minesite Stone").defineInRange("minesiteStoneBulidingCostsGold", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_STONE_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the minesite Stone").defineInRange("minesiteStoneBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_STONE_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the minesite Stone").defineInRange("minesiteStoneBulidingCostsEmerald", 0, 0, 100000);

		CONFIG_BUILDING_COSTS_MINESITE_IRON_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the minesite Iron").defineInRange("minesiteIronBulidingCostsFood", 30, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_IRON_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the minesite Iron").defineInRange("minesiteIronBulidingCostsWood", 45, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_IRON_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the minesite Iron").defineInRange("minesiteIronBulidingCostsStone", 9, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_IRON_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the minesite Iron").defineInRange("minesiteIronBulidingCostsIron", 8, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_IRON_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the minesite Iron").defineInRange("minesiteIronBulidingCostsGold", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_IRON_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the minesite Iron").defineInRange("minesiteIronBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_IRON_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the minesite Iron").defineInRange("minesiteIronBulidingCostsEmerald", 0, 0, 100000);

		CONFIG_BUILDING_COSTS_MINESITE_GOLD_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the minesite Gold").defineInRange("minesiteGoldBulidingCostsFood", 36, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_GOLD_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the minesite Gold").defineInRange("minesiteGoldBulidingCostsWood", 38, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_GOLD_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the minesite Gold").defineInRange("minesiteGoldBulidingCostsStone", 9, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_GOLD_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the minesite Gold").defineInRange("minesiteGoldBulidingCostsIron", 20, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_GOLD_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the minesite Gold").defineInRange("minesiteGoldBulidingCostsGold", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_GOLD_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the minesite Gold").defineInRange("minesiteGoldBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_GOLD_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the minesite Gold").defineInRange("minesiteGoldBulidingCostsEmerald", 0, 0, 100000);

		CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the minesite Diamond").defineInRange("minesiteDiamondBulidingCostsFood", 42, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the minesite Diamond").defineInRange("minesiteDiamondBulidingCostsWood", 45, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the minesite Diamond").defineInRange("minesiteDiamondBulidingCostsStone", 14, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the minesite Diamond").defineInRange("minesiteDiamondBulidingCostsIron", 23, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the minesite Diamond").defineInRange("minesiteDiamondBulidingCostsGold", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the minesite Diamond").defineInRange("minesiteDiamondBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the minesite Diamond").defineInRange("minesiteDiamondBulidingCostsEmerald", 0, 0, 100000);

		CONFIG_BUILDING_COSTS_MINESITE_EMERALD_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the minesite Emerald").defineInRange("minesiteEmeraldBulidingCostsFood", 600, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_EMERALD_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the minesite Emerald").defineInRange("minesiteEmeraldBulidingCostsWood", 5, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_EMERALD_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the minesite Emerald").defineInRange("minesiteEmeraldBulidingCostsStone", 2, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_EMERALD_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the minesite Emerald").defineInRange("minesiteEmeraldBulidingCostsIron", 3, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_EMERALD_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the minesite Emerald").defineInRange("minesiteEmeraldBulidingCostsGold", 2, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_EMERALD_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the minesite Emerald").defineInRange("minesiteEmeraldBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_MINESITE_EMERALD_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the minesite Emerald").defineInRange("minesiteEmeraldBulidingCostsEmerald", 0, 0, 100000);

		CONFIG_BUILDING_COSTS_BARRACKS_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the barracks").defineInRange("barracksBulidingCostsFood", 44, 0, 100000);
		CONFIG_BUILDING_COSTS_BARRACKS_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the barracks").defineInRange("barracksBulidingCostsWood", 70, 0, 100000);
		CONFIG_BUILDING_COSTS_BARRACKS_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the barracks").defineInRange("barracksBulidingCostsStone", 38, 0, 100000);
		CONFIG_BUILDING_COSTS_BARRACKS_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the barracks").defineInRange("barracksBulidingCostsIron", 29, 0, 100000);
		CONFIG_BUILDING_COSTS_BARRACKS_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the barracks").defineInRange("barracksBulidingCostsGold", 11, 0, 100000);
		CONFIG_BUILDING_COSTS_BARRACKS_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the barracks").defineInRange("barracksBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_BARRACKS_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the barracks").defineInRange("barracksBulidingCostsEmerald", 0, 0, 100000);

		CONFIG_BUILDING_COSTS_ARCHERY_RANGE_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the Archery Range").defineInRange("ArcheryRangeBulidingCostsFood", 44, 0, 100000);
		CONFIG_BUILDING_COSTS_ARCHERY_RANGE_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the Archery Range").defineInRange("ArcheryRangeBulidingCostsWood", 100, 0, 100000);
		CONFIG_BUILDING_COSTS_ARCHERY_RANGE_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the Archery Range").defineInRange("ArcheryRangeBulidingCostsStone", 46, 0, 100000);
		CONFIG_BUILDING_COSTS_ARCHERY_RANGE_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the Archery Range").defineInRange("ArcheryRangeBulidingCostsIron", 36, 0, 100000);
		CONFIG_BUILDING_COSTS_ARCHERY_RANGE_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the Archery Range").defineInRange("ArcheryRangeBulidingCostsGold", 17, 0, 100000);
		CONFIG_BUILDING_COSTS_ARCHERY_RANGE_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the Archery Range").defineInRange("ArcheryRangeBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_ARCHERY_RANGE_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the Archery Range").defineInRange("ArcheryRangeBulidingCostsEmerald", 0, 0, 100000);

		COMMON_BUILDER.pop();
	}

	private static void setupUnitCostConfig() {
		COMMON_BUILDER.comment("Unit Costs").push(CATEGORY_UNIT_COST);
		CONFIG_UNIT_COSTS_MINION_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the minion").defineInRange("unitCostsMinionFood", 6, 0, 100000);
		CONFIG_UNIT_COSTS_MINION_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the minion").defineInRange("unitCostsMinionWood", 5, 0, 100000);
		CONFIG_UNIT_COSTS_MINION_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the minion").defineInRange("unitCostsMinionStone", 2, 0, 100000);
		CONFIG_UNIT_COSTS_MINION_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the minion").defineInRange("unitCostsMinionIron", 3, 0, 100000);
		CONFIG_UNIT_COSTS_MINION_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the minion").defineInRange("unitCostsMinionGold", 2, 0, 100000);
		CONFIG_UNIT_COSTS_MINION_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the minion").defineInRange("unitCostsMinionDiamond", 0, 0, 100000);
		CONFIG_UNIT_COSTS_MINION_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the minion").defineInRange("unitCostsMinionEmerald", 0, 0, 100000);

		CONFIG_UNIT_COSTS_ARCHER_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the archer").defineInRange("unitCostsArcherFood", 5, 0, 100000);
		CONFIG_UNIT_COSTS_ARCHER_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the archer").defineInRange("unitCostsArcherWood", 5, 0, 100000);
		CONFIG_UNIT_COSTS_ARCHER_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the archer").defineInRange("unitCostsArcherStone", 3, 0, 100000);
		CONFIG_UNIT_COSTS_ARCHER_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the archer").defineInRange("unitCostsArcherIron", 5, 0, 100000);
		CONFIG_UNIT_COSTS_ARCHER_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the archer").defineInRange("unitCostsArcherGold", 3, 0, 100000);
		CONFIG_UNIT_COSTS_ARCHER_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the archer").defineInRange("unitCostsArcherDiamond", 0, 0, 100000);
		CONFIG_UNIT_COSTS_ARCHER_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the archer").defineInRange("unitCostsArcherEmerald", 0, 0, 100000);

		COMMON_BUILDER.pop();
	}

	private static void setupGeneralConfig() {
		COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
		CONFIG_GAME_MODE = COMMON_BUILDER.comment("Sets the game mode, 0 = RUN , 1 = WORLDBUILDER. World builder will not spawn units from structures").defineEnum("gameMode", Modes.RUN);
		COMMON_BUILDER.pop();
	}

	public enum Modes {
		RUN, WORLDBUILDER
	}

	public static void loadConfig(ForgeConfigSpec spec, Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
		configData.load();
		spec.setConfig(configData);
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {

	}

	@SubscribeEvent
	public static void onReload(final ModConfig.ConfigReloading configEvent) {

	}

}
