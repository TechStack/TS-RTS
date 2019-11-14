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

	static {

		setupGeneralConfig();
		setupStartinResourcesConfig();
		setupUnitCostConfig();
		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();

	}

	private static void setupStartinResourcesConfig() {
		COMMON_BUILDER.comment("Starting resources").push(CATEGORY_UNIT_COST);
		CONFIG_START_AMT_FOOD = COMMON_BUILDER.comment("Defines the starting amount of food").defineInRange("StartingFood", 100, 0, 100000);
		CONFIG_START_AMT_WOOD = COMMON_BUILDER.comment("Defines the starting amount of wood").defineInRange("StartingWood", 100, 0, 100000);
		CONFIG_START_AMT_STONE = COMMON_BUILDER.comment("Defines the starting amount of stone").defineInRange("StartingStone", 75, 0, 100000);
		CONFIG_START_AMT_IRON = COMMON_BUILDER.comment("Defines the starting amount of iron").defineInRange("StartingIron", 50, 0, 100000);
		CONFIG_START_AMT_GOLD = COMMON_BUILDER.comment("Defines the starting amount of gold").defineInRange("StartingGold", 25, 0, 100000);
		CONFIG_START_AMT_DIAMOND = COMMON_BUILDER.comment("Defines the starting amount of diamond").defineInRange("StartingDiamond", 0, 0, 100000);
		CONFIG_START_AMT_EMERALD = COMMON_BUILDER.comment("Defines the starting amount of emerald").defineInRange("StartingEmerald", 0, 0, 100000);

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
