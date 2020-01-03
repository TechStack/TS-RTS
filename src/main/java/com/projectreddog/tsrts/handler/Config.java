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

	public static final String CATEGORY_OPTIONS = "options";

	public static final String CATEGORY_UNIT_COST = "unit_cost";

	public static final String CATEGORY_BUILDING_COST = "building_cost";
	public static final String CATEGORY_RESEARCH_COST = "research_cost";

	public static final String CATEGORY_STRUCTURE_HEALTH = "structure_health";

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

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_COSTS_KNIGHT_STRING;
	public static ResourceValues CONFIG_UNIT_COSTS_KNIGHT;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_COSTS_ADVANCED_KNIGHT_STRING;
	public static ResourceValues CONFIG_UNIT_COSTS_ADVANCED_KNIGHT;

	// archers

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_COSTS_ARCHER_STRING;
	public static ResourceValues CONFIG_UNIT_COSTS_ARCHER;

	// mounted unit

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_COSTS_LANCER_STRING;
	public static ResourceValues CONFIG_UNIT_COSTS_LANCER;

// pikeman
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_COSTS_PIKEMAN_STRING;
	public static ResourceValues CONFIG_UNIT_COSTS_PIKEMAN;

// trebuchet
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_COSTS_TREBUCHET_STRING;
	public static ResourceValues CONFIG_UNIT_COSTS_TREBUCHET;

	// starting resrources

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_START_AMT_STRING;
	public static ResourceValues CONFIG_START_AMT;

	// generation amounts for town hall
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_TOWN_HALL_GENERATE_STRING;
	public static ResourceValues CONFIG_TOWN_HALL_GENERATE;

	// building costs
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_FARM_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_FARM;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_LUMBER_YARD_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_LUMBER_YARD;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_MINESITE_STONE_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_MINESITE_STONE;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_MINESITE_IRON_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_MINESITE_IRON;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_MINESITE_GOLD_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_MINESITE_GOLD;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_MINESITE_DIAMOND;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_MINESITE_EMERALD_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_MINESITE_EMERALD;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_BARRACKS_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_BARRACKS;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_ARCHERY_RANGE_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_ARCHERY_RANGE;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_WALL_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_WALL;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_WALL_STEPS_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_WALL_STEPS;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_GATE_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_GATE;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_RESEARCH_CENTER_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_RESEARCH_CENTER;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_SIEGE_WORKSHOP_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_SIEGE_WORKSHOP;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_STABLES_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_STABLES;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_ARMORY_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_ARMORY;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_BUILDING_COSTS_WATCH_TOWER_STRING;
	public static ResourceValues CONFIG_BUILDING_COSTS_WATCH_TOWER;

	/// UNIt attributes
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_MINION_ATTRIBUTES_STRING;
	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_MINION;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_TREBUCHET_ATTRIBUTES_STRING;
	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_TREBUCHET;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_KNIGHT_ATTRIBUTES_STRING;
	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_KNIGHT;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_ADVANCED_KNIGHT_ATTRIBUTES_STRING;
	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_ADVANCED_KNIGHT;

	/// UNIt attributes
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_PIKEMAN_ATTRIBUTES_STRING;
	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_PIKEMAN;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_ARCHER_ATTRIBUTES_STRING;
	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_ARCHER;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_UNIT_MOUNTED_ATTRIBUTES_STRING;
	public static UnitAttributes CONFIG_UNIT_ATTRIBUTES_MOUNTED;
/// weapon moidifiers
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_LANCE_WEAPON_MODIFIER_ATTRIBUTES_STRING;
	public static WeaponModifierAttributes CONFIG_WEAPON_MODIFIER_ATTRIBUTES_LANCE;

	public static ForgeConfigSpec.ConfigValue<String> CONFIG_PIKE_WEAPON_MODIFIER_ATTRIBUTES_STRING;
	public static WeaponModifierAttributes CONFIG_WEAPON_MODIFIER_ATTRIBUTES_PIKE;

	// Structure Health
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_ARCHERY_RANGE;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_BARRACKS;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_FARM;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_LUMBER_YARD;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_EMERALD;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_RESEARCH_CENTER;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_STABLES;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_TOWN_HALL;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_WALL;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_WATCH_TOWER;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_GATE;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_WALL_STEPS;

	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_SIEGE_WORKSHOP;
	public static ForgeConfigSpec.IntValue CONFIG_STRCTURE_TOTAL_HEALTH_ARMORY;

	public static ForgeConfigSpec.IntValue CONFIG_RATE_GENRATE_FOOD;
	public static ForgeConfigSpec.IntValue CONFIG_RATE_GENRATE_WOOD;
	public static ForgeConfigSpec.IntValue CONFIG_RATE_GENRATE_STONE;
	public static ForgeConfigSpec.IntValue CONFIG_RATE_GENRATE_IRON;
	public static ForgeConfigSpec.IntValue CONFIG_RATE_GENRATE_GOLD;
	public static ForgeConfigSpec.IntValue CONFIG_RATE_GENRATE_DIAMOND;
	public static ForgeConfigSpec.IntValue CONFIG_RATE_GENRATE_EMERALD;
//research costs
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_TOWNHALL_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_TOWNHALL;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_MINION_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_MINION;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_ARCHER_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_ARCHER;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_PIKEMAN_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_PIKEMAN;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_LANCER_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_LANCER;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_ARMORY_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_ARMORY;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_MARKETPLACE_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_MARKETPLACE;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_WALL_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_WALL;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_SIEGEWORKSHOP_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_SIEGEWORKSHOP;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_CROSSBOW_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_CROSSBOW;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_ADVCEDARMOR_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_ADVCEDARMOR;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_WATCHTOWER_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_WATCHTOWER;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_BATTERINGRAMS_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_BATTERINGRAMS;
	public static ForgeConfigSpec.ConfigValue<String> CONFIG_RESEARCH_COSTS_TREBUCHET_STRING;
	public static ResourceValues CONFIG_RESEARCH_COSTS_TREBUCHET;

	public static ForgeConfigSpec.BooleanValue CONFIG_PLAYERS_CAN_ATTACK_BUILDINGS;
	static {

		setupGeneralConfig();
		setupOptionsConfig();
		setupStartinResourcesConfig();
		setupResourceGeneration();
		setupUnitCostConfig();
		setupBuildingCostConfig();
		setupUnitAttributeConfig();
		setupWeaponAttributeConfig();
		setupStructureHealthConfig();
		setupResearchCostConfig();
		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();
	}

	private static void setupOptionsConfig() {
		COMMON_BUILDER.comment("Options").push(CATEGORY_OPTIONS);

		CONFIG_PLAYERS_CAN_ATTACK_BUILDINGS = COMMON_BUILDER.comment("Sets if players can attack buildings or not directly. if FALSE they must use troops to attack buildings").define("canPlayersAttackBulidings", false);
	}

	private static void setupResearchCostConfig() {
		COMMON_BUILDER.comment("Research Costs").push(CATEGORY_RESEARCH_COST);

		CONFIG_RESEARCH_COSTS_TOWNHALL_STRING = COMMON_BUILDER.comment("Defines the cost For the townhall").define("townhallResearchCosts", "0,0,0,0,0,0,0");
		CONFIG_RESEARCH_COSTS_MINION_STRING = COMMON_BUILDER.comment("Defines the cost For the minion").define("minionResearchCosts", "0,0,0,0,0,0,0");
		CONFIG_RESEARCH_COSTS_ARCHER_STRING = COMMON_BUILDER.comment("Defines the cost For the archer").define("archerResearchCosts", "250,500,0,0,50,50,0");
		CONFIG_RESEARCH_COSTS_PIKEMAN_STRING = COMMON_BUILDER.comment("Defines the cost For the pikeman").define("pikemanResearchCosts", "250,250,0,250,50,50,0");
		CONFIG_RESEARCH_COSTS_LANCER_STRING = COMMON_BUILDER.comment("Defines the cost For the lancer").define("lancerResearchCosts", "500,500,0,250,50,50,0");
		CONFIG_RESEARCH_COSTS_ARMORY_STRING = COMMON_BUILDER.comment("Defines the cost For the armory").define("armoryResearchCosts", "250,100,0,500,50,50,0");
		CONFIG_RESEARCH_COSTS_MARKETPLACE_STRING = COMMON_BUILDER.comment("Defines the cost For the marketplace").define("marketplaceResearchCosts", "100,100,100,100,250,250,0");
		CONFIG_RESEARCH_COSTS_WALL_STRING = COMMON_BUILDER.comment("Defines the cost For the wall").define("wallResearchCosts", "250,50,500,0,50,50,0");
		CONFIG_RESEARCH_COSTS_SIEGEWORKSHOP_STRING = COMMON_BUILDER.comment("Defines the cost For the siegeworkshop").define("siegeworkshopResearchCosts", "250,500,100,100,50,50,0");
		CONFIG_RESEARCH_COSTS_CROSSBOW_STRING = COMMON_BUILDER.comment("Defines the cost For the crossbow").define("crossbowResearchCosts", "250,1000,0,250,100,100,0");
		CONFIG_RESEARCH_COSTS_ADVCEDARMOR_STRING = COMMON_BUILDER.comment("Defines the cost For the advcedarmor").define("advcedarmorResearchCosts", "250,0,0,200,50,250,0");
		CONFIG_RESEARCH_COSTS_WATCHTOWER_STRING = COMMON_BUILDER.comment("Defines the cost For the watchtower").define("watchtowerResearchCosts", "250,200,500,50,50,50,0");
		CONFIG_RESEARCH_COSTS_BATTERINGRAMS_STRING = COMMON_BUILDER.comment("Defines the cost For the batteringrams").define("batteringramsResearchCosts", "250,500,100,50,50,50,0");
		CONFIG_RESEARCH_COSTS_TREBUCHET_STRING = COMMON_BUILDER.comment("Defines the cost For the trebuchet").define("trebuchetResearchCosts", "250,1000,200,100,100,100,0");
		COMMON_BUILDER.pop();
	}

	private static void setupStructureHealthConfig() {
		COMMON_BUILDER.comment("Sets the health of the various buildings. The health is evenly split between the target entities in the strcture.").push(CATEGORY_STRUCTURE_HEALTH);

		CONFIG_STRCTURE_TOTAL_HEALTH_ARCHERY_RANGE = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthArcheryRange", 160, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_ARMORY = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthArmory", 160, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_BARRACKS = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthBarracks", 80, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_FARM = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthFarm", 40, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_LUMBER_YARD = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthLumberYard", 40, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_STONE = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthMinesiteStone", 40, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_IRON = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthMinesiteIron", 40, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_GOLD = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthMinesiteGold", 40, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_DIAMOND = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthMinesiteDiamond", 40, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_EMERALD = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthMinesiteEmerald", 40, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_RESEARCH_CENTER = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthResearchCenter", 40, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_GATE = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthGate", 400, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_SIEGE_WORKSHOP = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthSiegeWorkshop", 160, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_STABLES = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthStables", 160, 0, 1024);

		CONFIG_STRCTURE_TOTAL_HEALTH_TOWN_HALL = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthTownHall", 500, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_WALL = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthWall", 400, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_WALL_STEPS = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthWallSteps", 400, 0, 1024);
		CONFIG_STRCTURE_TOTAL_HEALTH_WATCH_TOWER = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").defineInRange("buildingHealthWatchTower", 300, 0, 1024);

		COMMON_BUILDER.pop();

	}

	private static void setupWeaponAttributeConfig() {
		COMMON_BUILDER.comment("Weapon ATTRIBUTES. ATTRIBUTES ARE ORDERED LIKE THIS ATTACK_DAMAGE_MODIFIER, ATTACK_SPEED_MODIFIER: . They are comma separated list of floats Example : 8,-2.9").push(CATEGORY_WEAPON_MODIFIER_ATTRIBUTES);
		CONFIG_LANCE_WEAPON_MODIFIER_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the LANCE").define("lance_attribute_modifier", "8,-2.9");
		CONFIG_PIKE_WEAPON_MODIFIER_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute modifier in order for the pike").define("pike_attribute_modifier", "7,0");
		COMMON_BUILDER.pop();

	}

	private static void PostProcessConfigs() {
		CONFIG_UNIT_ATTRIBUTES_MINION = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_MINION_ATTRIBUTES_STRING.get()));
		CONFIG_UNIT_ATTRIBUTES_KNIGHT = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_KNIGHT_ATTRIBUTES_STRING.get()));

		CONFIG_UNIT_ATTRIBUTES_ADVANCED_KNIGHT = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_ADVANCED_KNIGHT_ATTRIBUTES_STRING.get()));
		CONFIG_UNIT_ATTRIBUTES_TREBUCHET = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_TREBUCHET_ATTRIBUTES_STRING.get()));

		CONFIG_UNIT_ATTRIBUTES_ARCHER = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_ARCHER_ATTRIBUTES_STRING.get()));
		CONFIG_UNIT_ATTRIBUTES_MOUNTED = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_MOUNTED_ATTRIBUTES_STRING.get()));
		CONFIG_UNIT_ATTRIBUTES_PIKEMAN = new UnitAttributes(StringToFloatArray(CONFIG_UNIT_PIKEMAN_ATTRIBUTES_STRING.get()));

		CONFIG_WEAPON_MODIFIER_ATTRIBUTES_LANCE = new WeaponModifierAttributes(StringToFloatArray(CONFIG_LANCE_WEAPON_MODIFIER_ATTRIBUTES_STRING.get()));
		CONFIG_WEAPON_MODIFIER_ATTRIBUTES_PIKE = new WeaponModifierAttributes(StringToFloatArray(CONFIG_PIKE_WEAPON_MODIFIER_ATTRIBUTES_STRING.get()));

		CONFIG_START_AMT = new ResourceValues(StringToIntArray(CONFIG_START_AMT_STRING.get()));
		CONFIG_TOWN_HALL_GENERATE = new ResourceValues(StringToIntArray(CONFIG_TOWN_HALL_GENERATE_STRING.get()));

		// COSTS below These will need to be added to send to the client at startup
		CONFIG_UNIT_COSTS_MINION = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_MINION_STRING.get()));
		CONFIG_UNIT_COSTS_ARCHER = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_ARCHER_STRING.get()));
		CONFIG_UNIT_COSTS_LANCER = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_LANCER_STRING.get()));
		CONFIG_UNIT_COSTS_PIKEMAN = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_PIKEMAN_STRING.get()));

		CONFIG_UNIT_COSTS_TREBUCHET = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_TREBUCHET_STRING.get()));

		CONFIG_UNIT_COSTS_KNIGHT = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_KNIGHT_STRING.get()));
		CONFIG_UNIT_COSTS_ADVANCED_KNIGHT = new ResourceValues(StringToIntArray(CONFIG_UNIT_COSTS_ADVANCED_KNIGHT_STRING.get()));

		CONFIG_BUILDING_COSTS_FARM = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_FARM_STRING.get()));
		CONFIG_BUILDING_COSTS_LUMBER_YARD = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_LUMBER_YARD_STRING.get()));
		CONFIG_BUILDING_COSTS_MINESITE_STONE = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_MINESITE_STONE_STRING.get()));
		CONFIG_BUILDING_COSTS_MINESITE_IRON = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_MINESITE_IRON_STRING.get()));
		CONFIG_BUILDING_COSTS_MINESITE_GOLD = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_MINESITE_GOLD_STRING.get()));
		CONFIG_BUILDING_COSTS_MINESITE_DIAMOND = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_STRING.get()));
		CONFIG_BUILDING_COSTS_MINESITE_EMERALD = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_MINESITE_EMERALD_STRING.get()));
		CONFIG_BUILDING_COSTS_BARRACKS = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_BARRACKS_STRING.get()));
		CONFIG_BUILDING_COSTS_ARCHERY_RANGE = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_ARCHERY_RANGE_STRING.get()));
		CONFIG_BUILDING_COSTS_WALL = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_WALL_STRING.get()));
		CONFIG_BUILDING_COSTS_WALL_STEPS = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_WALL_STEPS_STRING.get()));
		CONFIG_BUILDING_COSTS_GATE = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_GATE_STRING.get()));
		CONFIG_BUILDING_COSTS_RESEARCH_CENTER = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_RESEARCH_CENTER_STRING.get()));

		CONFIG_BUILDING_COSTS_SIEGE_WORKSHOP = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_SIEGE_WORKSHOP_STRING.get()));

		CONFIG_BUILDING_COSTS_STABLES = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_STABLES_STRING.get()));
		CONFIG_BUILDING_COSTS_ARMORY = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_ARMORY_STRING.get()));

		CONFIG_BUILDING_COSTS_WATCH_TOWER = new ResourceValues(StringToIntArray(CONFIG_BUILDING_COSTS_WATCH_TOWER_STRING.get()));

		// RESEARCH
		CONFIG_RESEARCH_COSTS_TOWNHALL = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_TOWNHALL_STRING.get()));
		CONFIG_RESEARCH_COSTS_MINION = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_MINION_STRING.get()));
		CONFIG_RESEARCH_COSTS_ARCHER = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_ARCHER_STRING.get()));
		CONFIG_RESEARCH_COSTS_PIKEMAN = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_PIKEMAN_STRING.get()));
		CONFIG_RESEARCH_COSTS_LANCER = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_LANCER_STRING.get()));
		CONFIG_RESEARCH_COSTS_ARMORY = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_ARMORY_STRING.get()));
		CONFIG_RESEARCH_COSTS_MARKETPLACE = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_MARKETPLACE_STRING.get()));
		CONFIG_RESEARCH_COSTS_WALL = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_WALL_STRING.get()));
		CONFIG_RESEARCH_COSTS_SIEGEWORKSHOP = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_SIEGEWORKSHOP_STRING.get()));
		CONFIG_RESEARCH_COSTS_CROSSBOW = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_CROSSBOW_STRING.get()));
		CONFIG_RESEARCH_COSTS_ADVCEDARMOR = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_ADVCEDARMOR_STRING.get()));
		CONFIG_RESEARCH_COSTS_WATCHTOWER = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_WATCHTOWER_STRING.get()));
		CONFIG_RESEARCH_COSTS_BATTERINGRAMS = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_BATTERINGRAMS_STRING.get()));
		CONFIG_RESEARCH_COSTS_TREBUCHET = new ResourceValues(StringToIntArray(CONFIG_RESEARCH_COSTS_TREBUCHET_STRING.get()));
	}

	private static void setupUnitAttributeConfig() {
		COMMON_BUILDER.comment("UNIT ATTRIBUTES. ATTRIBUTES ARE ORDERED LIKE THIS : MAX_HEALTH,KNOCK_BACK_RESISTANCE, MOVEMENT_SPEED,ARMOR,ARMOR_TOUGHNESS,ATTACK_KNOCKBACK,ATTACKD_DAMAGE,FOLLOW_RANGE. They are comma separated list of floats Example : 20.0,0.0,0.25,2.0,0.0,0.0,3.0,35.0").push(CATEGORY_UNIT_ATTRIBUTES);

		CONFIG_UNIT_MINION_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the MINION. Atttributes are ").define("unit_minion_attributes", "15.0,0.0,0.35,2.0,0.0,0.0,3.0,24.0");
		CONFIG_UNIT_ARCHER_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the ARCHER. Atttributes are ").define("unit_archer_attributes", "10.0,0.0,0.30,2.0,0.0,0.0,3.0,24.0");
		CONFIG_UNIT_MOUNTED_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the MOUNTED. Atttributes are ").define("unit_mounted_attributes", "10.0,5.0,0.40,2.0,0.0,0.0,3.0,24.0");
		CONFIG_UNIT_PIKEMAN_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the PIKEMAN. Atttributes are ").define("unit_pikeman_attributes", "15.0,5.0,0.33,2.0,0.0,0.0,3.0,24.0");
		CONFIG_UNIT_KNIGHT_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the KNIGHT. Atttributes are ").define("unit_knight_attributes", "15.0,0.0,0.35,2.0,0.0,0.0,3.0,24.0");
		CONFIG_UNIT_ADVANCED_KNIGHT_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the ADVANCED KNIGHT. Atttributes are ").define("unit_advanced_knight_attributes", "15.0,0.0,0.35,2.0,0.0,0.0,3.0,24.0");

		CONFIG_UNIT_TREBUCHET_ATTRIBUTES_STRING = COMMON_BUILDER.comment("Defines a comma separted list of values for each attribute in order for the MINION. Atttributes are ").define("unit_minion_attributes", "15.0,0.0,0.35,2.0,0.0,0.0,3.0,24.0");
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

		CONFIG_RATE_GENRATE_FOOD = COMMON_BUILDER.comment("Defines the amount of food collected per farm").defineInRange("foodGeneration", 12, 0, 1024);
		CONFIG_RATE_GENRATE_WOOD = COMMON_BUILDER.comment("Defines the amount of wood collected per lumber yard").defineInRange("woodGeneration", 17, 0, 1024);
		CONFIG_RATE_GENRATE_STONE = COMMON_BUILDER.comment("Defines the amount of stone collected per stone Mine").defineInRange("stoneGeneration", 9, 0, 1024);
		CONFIG_RATE_GENRATE_IRON = COMMON_BUILDER.comment("Defines the amount of iron collected per iron Mine").defineInRange("ironGeneration", 10, 0, 1024);
		CONFIG_RATE_GENRATE_GOLD = COMMON_BUILDER.comment("Defines the amount of gold collected per gold Mine").defineInRange("goldGeneration", 9, 0, 1024);
		CONFIG_RATE_GENRATE_DIAMOND = COMMON_BUILDER.comment("Defines the amount of diamond collected per diamond Mine").defineInRange("diamondGeneration", 7, 0, 1024);
		CONFIG_RATE_GENRATE_EMERALD = COMMON_BUILDER.comment("Defines the amount of emerald collected per emerald Mine").defineInRange("emeraldGeneration", 5, 0, 1024);

		COMMON_BUILDER.pop();

	}

	private static void setupBuildingCostConfig() {
		COMMON_BUILDER.comment("Building Costs").push(CATEGORY_BUILDING_COST);

		CONFIG_BUILDING_COSTS_FARM_STRING = COMMON_BUILDER.comment("Defines the cost For the farm").define("farmBulidingCosts", "10,15,5,0,0,0,0");
		CONFIG_BUILDING_COSTS_LUMBER_YARD_STRING = COMMON_BUILDER.comment("Defines the cost For the lumber Yard").define("lumberYardBulidingCosts", "10,10,15,5,0,0,0");
		CONFIG_BUILDING_COSTS_MINESITE_STONE_STRING = COMMON_BUILDER.comment("Defines the cost For the minesite Stone").define("minesiteStoneBulidingCosts", "2,30,15,15,0,0,0");
		CONFIG_BUILDING_COSTS_MINESITE_IRON_STRING = COMMON_BUILDER.comment("Defines the cost For the minesite Iron").define("minesiteIronBulidingCosts", "30,45,9,8,0,0,0");
		CONFIG_BUILDING_COSTS_MINESITE_GOLD_STRING = COMMON_BUILDER.comment("Defines the cost For the minesite Gold").define("minesiteGoldBulidingCosts", "36,38,9,20,0,0,0");
		CONFIG_BUILDING_COSTS_MINESITE_DIAMOND_STRING = COMMON_BUILDER.comment("Defines the cost For the minesite Diamond").define("minesiteDiamondBulidingCosts", "42,45,14,23,0,0,0");
		CONFIG_BUILDING_COSTS_MINESITE_EMERALD_STRING = COMMON_BUILDER.comment("Defines the cost For the minesite Emerald").define("minesiteEmeraldBulidingCosts", "600,5,2,3,2,0,0");
		CONFIG_BUILDING_COSTS_BARRACKS_STRING = COMMON_BUILDER.comment("Defines the cost For the barracks").define("barracksBulidingCosts", "44,70,38,29,11,0,0");
		CONFIG_BUILDING_COSTS_ARCHERY_RANGE_STRING = COMMON_BUILDER.comment("Defines the cost For the Archery Range").define("archeryRangeBulidingCosts", "44,100,46,36,17,0,0");
		CONFIG_BUILDING_COSTS_WALL_STRING = COMMON_BUILDER.comment("Defines the cost For the Wall").define("wallBulidingCosts", "10,15,32,2,3,0,0");
		CONFIG_BUILDING_COSTS_GATE_STRING = COMMON_BUILDER.comment("Defines the cost For the Gate").define("gateBulidingCosts", "10,15,32,2,3,0,0");
		CONFIG_BUILDING_COSTS_WALL_STEPS_STRING = COMMON_BUILDER.comment("Defines the cost For the Wall Steps").define("wallStepsBulidingCosts", "10,15,32,2,3,0,0");

		CONFIG_BUILDING_COSTS_RESEARCH_CENTER_STRING = COMMON_BUILDER.comment("Defines the cost For the Research Center").define("researchCenterBulidingCosts", "100,250,100,25,25,25,0");
		CONFIG_BUILDING_COSTS_SIEGE_WORKSHOP_STRING = COMMON_BUILDER.comment("Defines the cost For the Siege Workshop").define("siegeWorkshopBulidingCosts", "100,250,100,25,25,50,0");
		CONFIG_BUILDING_COSTS_STABLES_STRING = COMMON_BUILDER.comment("Defines the cost For the Stables").define("stablesBulidingCosts", "66,120,40,30,35,0,0");
		CONFIG_BUILDING_COSTS_ARMORY_STRING = COMMON_BUILDER.comment("Defines the cost For the Armory").define("stablesBulidingArmory", "100,250,100,100,50,0,0");

		CONFIG_BUILDING_COSTS_WATCH_TOWER_STRING = COMMON_BUILDER.comment("Defines the cost For the Watch Tower").define("watchTowerBulidingCosts", "20,125,25,12,15,0,0");

		COMMON_BUILDER.pop();
	}

	private static void setupUnitCostConfig() {
		COMMON_BUILDER.comment("Unit Costs").push(CATEGORY_UNIT_COST);
		CONFIG_UNIT_COSTS_MINION_STRING = COMMON_BUILDER.comment("Defines the cost For the minion").define("unitCostsMinion", "6,4,4,4,0,0,0");
		CONFIG_UNIT_COSTS_ARCHER_STRING = COMMON_BUILDER.comment("Defines the cost For the archer").define("unitCostsArcher", "5,6,2,4,4,0,0");
		CONFIG_UNIT_COSTS_LANCER_STRING = COMMON_BUILDER.comment("Defines the cost For the lancer").define("unitCostsLancer", "10,6,0,1,6,0,0");
		CONFIG_UNIT_COSTS_PIKEMAN_STRING = COMMON_BUILDER.comment("Defines the cost For the pikeman").define("unitCostsPikeman", "5,6,0,2,5,0,0");

		CONFIG_UNIT_COSTS_TREBUCHET_STRING = COMMON_BUILDER.comment("Defines the cost For the trebuchet").define("unitCostsTrebuchet", "5,6,0,2,5,0,0");

		CONFIG_UNIT_COSTS_KNIGHT_STRING = COMMON_BUILDER.comment("Defines the cost For the knight").define("unitCostsKnight", "6,4,4,10,10,0,0");
		CONFIG_UNIT_COSTS_ADVANCED_KNIGHT_STRING = COMMON_BUILDER.comment("Defines the cost For the Advanced Knight").define("unitCostsAdvancedKnight", "6,4,4,10,10,10,0");

		COMMON_BUILDER.pop();
	}

	private static void setupGeneralConfig() {
		COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
		CONFIG_GAME_MODE = COMMON_BUILDER.comment("Sets the game mode, 0 = RUN , 1 = WORLDBUILDER. World builder will not spawn units from structures").defineEnum("gameMode", Modes.RUN);
		COMMON_BUILDER.pop();
	}

	public enum Modes {
		RUN, WORLDBUILDER, WAVESURVIVAL
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
