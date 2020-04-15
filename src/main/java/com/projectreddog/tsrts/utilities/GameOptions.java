package com.projectreddog.tsrts.utilities;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class GameOptions {

	public static int speedEffectAmount = 0;
	public static boolean weatherEnabled = true;

	public static void SpeedMinus() {
		speedEffectAmount--;
		if (speedEffectAmount < 0) {
			speedEffectAmount = 0;
		}
	}

	public static void SpeedPlus() {
		speedEffectAmount++;
		if (speedEffectAmount > 3) {
			speedEffectAmount = 3;
		}
	}

	public static void EnableWeather(World world) {
		// server.getGameRules().DO_WEATHER_CYCLE.

		world.getGameRules().get(GameRules.DO_WEATHER_CYCLE).set(true, (MinecraftServer) null);

		weatherEnabled = true;
	}

	public static void DisableWeather(World world) {

		world.getWorldInfo().setClearWeatherTime(6000);
		world.getWorldInfo().setRainTime(0);
		world.getWorldInfo().setThunderTime(0);
		world.getWorldInfo().setRaining(false);
		world.getWorldInfo().setThundering(false);

		world.getGameRules().get(GameRules.DO_WEATHER_CYCLE).set(false, (MinecraftServer) null);

		weatherEnabled = false;
	}
}
