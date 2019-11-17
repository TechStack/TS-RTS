package com.projectreddog.tsrts;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.projectreddog.tsrts.handler.ClientEvents;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.handler.EventHandler;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.init.ModContainers;
import com.projectreddog.tsrts.init.ModEntities;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.proxy.ClientProxy;
import com.projectreddog.tsrts.proxy.IProxy;
import com.projectreddog.tsrts.proxy.ServerProxy;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.PlayerSelections;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Reference.MODID)
public class TSRTS {
	public static HashMap<String, PlayerSelections> playerSelections = new HashMap<String, PlayerSelections>();

	public static int[] playerSelectionsControlGroup1 = null;
	public static int[] playerSelectionsControlGroup2 = null;
	public static int[] playerSelectionsControlGroup3 = null;
	public static int[] playerSelectionsControlGroup4 = null;
	public static int[] playerSelectionsControlGroup5 = null;
	public static int[] playerSelectionsControlGroup6 = null;
	public static int[] playerSelectionsControlGroup7 = null;
	public static int[] playerSelectionsControlGroup8 = null;
	public static int[] playerSelectionsControlGroup9 = null;

	public static TeamInfo[] teamInfoArray = new TeamInfo[TeamEnum.values().length];
	public static HashMap<String, Boolean> isPlayerReadyArray = new HashMap<>();
	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();

	public static GAMESTATE CURRENT_GAME_STATE = GAMESTATE.STARTUP;

	public TSRTS() {
		// Register the setup method for modloading
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("TSRTS-client.toml"));
		Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("TSRTS-common.toml"));
	}

	private void setup(final FMLCommonSetupEvent event) {
		proxy.init();
		ModNetwork.init();

		MinecraftForge.EVENT_BUS.register(EventHandler.class);
		MinecraftForge.EVENT_BUS.register(ClientEvents.class);
	}

	// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
	// Event bus for receiving Registry Events)
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
			// register a new block here
			LOGGER.info("HELLO from Register Block");
			ModBlocks.RegisterBlocks(event);
		}

		@SubscribeEvent
		public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
			// register a new block here
			LOGGER.info("HELLO from Register ITEM");
			ModBlocks.RegisterBlockItems(event);
			ModItems.RegisterItems(event);

		}

		@SubscribeEvent
		public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
			// register a new block here
			LOGGER.info("HELLO from Register Enityt");
			ModEntities.RegisterEntites(event);
		}

		@SubscribeEvent
		public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
			// register a new block here
			LOGGER.info("HELLO from Register Tile ENtity");
			ModBlocks.RegisterTileEntities(event);

		}

		@SubscribeEvent
		public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
			// register a new block here
			LOGGER.info("HELLO from Register Tile ENtity");
			ModContainers.RegisterContainers(event);

		}

	}

	public enum GAMESTATE {

		STARTUP, LOBBY, COUNTDOWN, RUNNINNG, GAME_OVER
	}
}
