package com.projectreddog.tsrts.handler;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.UnitAttributes;
import com.projectreddog.tsrts.utilities.WeaponModifierAttributes;

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

	public static final String CATEGORY_UNIT_ATTRIBUTES = "unit_attributes";
	public static final String CATEGORY_WEAPON_MODIFIER_ATTRIBUTES = "weapon_modifier_attributes";

	public static final String CATEGORY_RESOURCE_GENERATION = "resource_generation";

	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec CLIENT_CONFIG;

	public static ForgeConfigSpec.EnumValue<Modes> CONFIG_GAME_MODE;
	// EXAMPLE: public static ForgeConfigSpec.IntValue CONFIG_INT_VALUE_VAR_NAME;
	// minions

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_COSTS_MINION_STRING;
	public static ResourceValues CONFIG_UNIT_COSTS_MINION;

	// archers

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_COSTS_ARCHER_STRING;
	public static ResourceValues CONFIG_UNIT_COSTS_ARCHER;

	// mounted unit

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_COSTS_LANCER_STRING;
	public static ResourceValues CONFIG_UNIT_COSTS_LANCER;

	// starting resrources

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_START_AMT_STRING;
	public static ResourceValues CONFIG_START_AMT;

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

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_TOWN_HALL_GENERATE_STRING;
	public static ResourceValues CONFIG_TOWN_HALL_GENERATE;
	// WALL
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WALL_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WALL_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WALL_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WALL_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WALL_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WALL_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WALL_EMERALD;

	// stables
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_STABLES_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_STABLES_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_STABLES_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_STABLES_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_STABLES_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_STABLES_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_STABLES_EMERALD;

	// watch tower
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WATCH_TOWER_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WATCH_TOWER_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WATCH_TOWER_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WATCH_TOWER_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WATCH_TOWER_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WATCH_TOWER_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_BUILDING_COSTS_WATCH_TOWER_EMERALD;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_MINION_ATTRIBUTES_STRING;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_ARCHER_ATTRIBUTES_STRING;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_MOUNTED_ATTRIBUTES_STRING;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_LANCE_WEAPON_MODIFIER_ATTRIBUTES_STRING;

	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_MINION;
	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_ARCHER;
	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_MOUNTED;

	public static WeaponModifierAttributes CONFIG_WEAPON_MODIFIER_ATTRIBUTES_LANCE;

	static {

		setupGeneralConfig();
		setupStartinResourcesConfig();
		setupResourceGeneration();
		setupUnitCostConfig();
		setupBuildingCostConfig();
		setupUnitAttributeConfig();
		setupWeaponAttributeConfig();

		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();
	}

	private static void setupWeaponAttributeConfig() {
		COMMON_BUILDER.comment("Weapon ATTRIBUTES. ATTRIBUTES ARE ORDERED LIKE THIS ATTACK_DAMAGE_MODIFIER, ATTACK_SPEED_MODIFIER: . They are comma separated list of floats Example : 8,-2.9").push(CATEGORY_WEAPON_MODIFIER_ATTRIBUTES);
		CONFIG_LANCE_WEAPON_MODIFIER_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").define("lance_attribute_modifier", "8,-2.9");
		COMMON_BUILDER.pop();

	}

	private static void PostProcessConfigs() {
		CONFIG_UNIT_ATTRIBUTES_MINION = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_MINION_ATTRIBUTES_STRING.get()));
		CONFIG_UNIT_ATTRIBUTES_ARCHER = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_ARCHER_ATTRIBUTES_STRING.get()));
		CONFIG_UNIT_ATTRIBUTES_MOUNTED = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_MOUNTED_ATTRIBUTES_STRING.get()));
		CONFIG_WEAPON_MODIFIER_ATTRIBUTES_LANCE = new WeaponModifierAttributes(StringToFloatArray(CONFIG_LANCE_WEAPON_MODIFIER_ATTRIBUTES_STRING.get()));
		// TODO ADD A LINE HERE FOR EVERY STRING ARRAY TO OBJECT CONVERSION
		CONFIG_START_AMT = new ResourceValues(StringToIntArray(CONFIG_START_AMT_STRING.get()));
		CONFIG_TOWN_HALL_GENERATE = new ResourceValues(StringToIntArray(CONFIG_TOWN_HALL_GENERATE_STRING.get()));
		CONFIG_UNIT_COSTS_MINION = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_MINION_STRING.get()));
		CONFIG_UNIT_COSTS_ARCHER = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_ARCHER_STRING.get()));
		CONFIG_UNIT_COSTS_LANCER = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_LANCER_STRING.get()));
	}

	private static void setupUnitAttributeConfig() {
		COMMON_BUILDER.comment("UNIT ATTRIBUTES. ATTRIBUTES ARE ORDERED LIKE THIS : MAX_HEALTH,KNOCK_BACK_RESISTANCE, MOVEMENT_SPEED,ARMOR,ARMOR_TOUGHNESS,ATTACK_KNOCKBACK,ATTACKD_DAMAGE,FOLLOW_RANGE. They are comma separated list of floats Example : 20.0,0.0,0.25,2.0,0.0,0.0,3.0,35.0").push(CATEGORY_UNIT_ATTRIBUTES);

		CONFIG_UNIT_MINION_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the MINION. Atttributes are ").define("unit_minion_attributes", "15.0,0.0,0.35,2.0,0.0,0.0,3.0,35.0");
		CONFIG_UNIT_ARCHER_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the ARCHER. Atttributes are ").define("unit_archer_attributes", "10.0,0.0,0.30,2.0,0.0,0.0,3.0,35.0");
		CONFIG_UNIT_MOUNTED_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the MOUNTED. Atttributes are ").define("unit_mounted_attributes", "10.0,0.0,0.40,2.0,0.0,0.0,3.0,35.0");
		COMMON_BUILDER.pop();

	}

	private static void setupStartinResourcesConfig() {
		COMMON_BUILDER.comment("Starting resources").push(CATEGORY_STARTUP_RESOURCES);
		CONFIG_START_AMT_STRING = COMMON_BUILDER.comment("Defines the starting amount for each resource").define("StartingAmount", "100,100,75,50,25,0,0");

		COMMON_BUILDER.pop();
	}

	private static void setupResourceGeneration() {
		COMMON_BUILDER.comment("resource generation").push(CATEGORY_RESOURCE_GENERATION);

		CONFIG_TOWN_HALL_GENERATE_STRING = COMMON_BUILDER.comment("Defines the amount of food the town hall generate per unit of time").define("townHallGenerates", "5,5,5,5,1,1,0");

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

		// wall

		CONFIG_BUILDING_COSTS_WALL_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the Wall").defineInRange("WallBulidingCostsFood", 10, 0, 100000);
		CONFIG_BUILDING_COSTS_WALL_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the Wall").defineInRange("WallBulidingCostsWood", 5, 0, 100000);
		CONFIG_BUILDING_COSTS_WALL_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the Wall").defineInRange("WallBulidingCostsStone", 20, 0, 100000);
		CONFIG_BUILDING_COSTS_WALL_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the Wall").defineInRange("WallBulidingCostsIron", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_WALL_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the Wall").defineInRange("WallBulidingCostsGold", 3, 0, 100000);
		CONFIG_BUILDING_COSTS_WALL_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the Wall").defineInRange("WallBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_WALL_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the Wall").defineInRange("WallBulidingCostsEmerald", 0, 0, 100000);

/// stables
		CONFIG_BUILDING_COSTS_STABLES_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the Stables").defineInRange("StablesBulidingCostsFood", 66, 0, 100000);
		CONFIG_BUILDING_COSTS_STABLES_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the Stables").defineInRange("StablesBulidingCostsWood", 120, 0, 100000);
		CONFIG_BUILDING_COSTS_STABLES_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the Stables").defineInRange("StablesBulidingCostsStone", 40, 0, 100000);
		CONFIG_BUILDING_COSTS_STABLES_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the Stables").defineInRange("StablesBulidingCostsIron", 30, 0, 100000);
		CONFIG_BUILDING_COSTS_STABLES_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the Stables").defineInRange("StablesBulidingCostsGold", 35, 0, 100000);
		CONFIG_BUILDING_COSTS_STABLES_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the Stables").defineInRange("StablesBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_STABLES_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the Stables").defineInRange("StablesBulidingCostsEmerald", 0, 0, 100000);

// watch tower
		CONFIG_BUILDING_COSTS_WATCH_TOWER_FOOD = COMMON_BUILDER.comment("Defines the cost in food For the Watch Tower").defineInRange("WatchTowerBulidingCostsFood", 20, 0, 100000);
		CONFIG_BUILDING_COSTS_WATCH_TOWER_WOOD = COMMON_BUILDER.comment("Defines the cost in wood For the Watch Tower").defineInRange("WatchTowerBulidingCostsWood", 50, 0, 100000);
		CONFIG_BUILDING_COSTS_WATCH_TOWER_STONE = COMMON_BUILDER.comment("Defines the cost in stone For the Watch Tower").defineInRange("WatchTowerBulidingCostsStone", 25, 0, 100000);
		CONFIG_BUILDING_COSTS_WATCH_TOWER_IRON = COMMON_BUILDER.comment("Defines the cost in iron For the Watch Tower").defineInRange("WatchTowerBulidingCostsIron", 4, 0, 100000);
		CONFIG_BUILDING_COSTS_WATCH_TOWER_GOLD = COMMON_BUILDER.comment("Defines the cost in gold For the Watch Tower").defineInRange("WatchTowerBulidingCostsGold", 20, 0, 100000);
		CONFIG_BUILDING_COSTS_WATCH_TOWER_DIAMOND = COMMON_BUILDER.comment("Defines the cost in diamond For the Watch Tower").defineInRange("WatchTowerBulidingCostsDiamond", 0, 0, 100000);
		CONFIG_BUILDING_COSTS_WATCH_TOWER_EMERALD = COMMON_BUILDER.comment("Defines the cost in emerald For the Watch Tower").defineInRange("WatchTowerBulidingCostsEmerald", 0, 0, 100000);

		COMMON_BUILDER.pop();
	}

	private static void setupUnitCostConfig() {
		COMMON_BUILDER.comment("Unit Costs").push(CATEGORY_UNIT_COST);
		CONFIG_UNIT_COSTS_MINION_STRING = COMMON_BUILDER.comment("Defines the cost For the minion").define("unitCostsMinion", "6,4,4,4,0,0,0");
		CONFIG_UNIT_COSTS_ARCHER_STRING = COMMON_BUILDER.comment("Defines the cost For the archer").define("unitCostsArcher", "5,6,2,4,4,,0,0");
		CONFIG_UNIT_COSTS_LANCER_STRING = COMMON_BUILDER.comment("Defines the cost For the lancer").define("unitCostsLancer", "10,6,0,1,6,0,0");

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
		PostProcessConfigs();

	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
		PostProcessConfigs();

	}

	@SubscribeEvent
	public static void onReload(final ModConfig.ConfigReloading configEvent) {
		PostProcessConfigs();

	}

	public static float[] StringToFloatArray(String input) {
		float[] tmp;
		try {
			String[] stringArray = input.split(",");
			tmp = new float[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				tmp[i] = Float.parseFloat(stringArray[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TSRTS.LOGGER.error("INVALID CONFIG PLEASE FIX- MOD WILL MISSBEHAVE!!");
			tmp = new float[0];
		}

		return tmp;
	}

	public static int[] StringToIntArray(String input) {
		int[] tmp;
		try {
			String[] stringArray = input.split(",");
			tmp = new int[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				tmp[i] = Integer.parseInt(stringArray[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TSRTS.LOGGER.error("INVALID CONFIG PLEASE FIX- MOD WILL MISSBEHAVE!!");
			tmp = new int[0];
		}

		return tmp;
	}

	public enum UnitStats {
		MAX_HEALTH, KNOCK_BACK_RESISTANCE, MOVEMENT_SPEED, ARMOR, ARMOR_TOUGHNESS, ATTACK_KNOCKBACK, ATTACKD_DAMAGE, FOLLOW_RANGE
	}

	public enum WeaponModifierStats {
		ATTACK_DAMAGE_MODIFIER, ATTACK_SPEED_MODIFIER
	}
}
