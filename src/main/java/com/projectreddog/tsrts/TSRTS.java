package com.projectreddog.tsrts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.proxy.ClientProxy;
import com.projectreddog.tsrts.proxy.IProxy;
import com.projectreddog.tsrts.proxy.ServerProxy;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MODID)
public class TSRTS {

	public static IProxy Prox = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();

	public TSRTS() {
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

	}

	private void setup(final FMLCommonSetupEvent event) {
		// some preinit code
		LOGGER.info("HELLO FROM PREINIT");
		LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
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

		}
	}
}
