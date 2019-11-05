package com.projectreddog.tsrts.hanlder;

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

	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec CLIENT_CONFIG;

	public static ForgeConfigSpec.EnumValue<Modes> CONFIG_GAME_MODE;
	// EXAMPLE: public static ForgeConfigSpec.IntValue CONFIG_INT_VALUE_VAR_NAME;

	static {

		setupGeneralConfig();

		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();

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
