package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.blocks.ArcheryRangeBlock;
import com.projectreddog.tsrts.blocks.GarrisonBlock;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {
	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_GARRISON_BLOCK)
	public static GarrisonBlock GARRISON_BLOCK = new GarrisonBlock();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ARCHERY_RANGE_BLOCK)
	public static ArcheryRangeBlock ARCHERY_RANGE_BLOCK = new ArcheryRangeBlock();

	public static void RegisterBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().register(ModBlocks.GARRISON_BLOCK);
		event.getRegistry().register(ModBlocks.ARCHERY_RANGE_BLOCK);

	}

	public static void RegisterBlockItems(final RegistryEvent.Register<Item> event) {
		RegisterBlockItem(event, ModBlocks.GARRISON_BLOCK);
		RegisterBlockItem(event, ModBlocks.ARCHERY_RANGE_BLOCK);

	}

	// Use default BlockItem group
	private static void RegisterBlockItem(final RegistryEvent.Register<Item> event, Block block) {
		RegisterBlockItem(event, block, ModItemGroups.blocksItemGroup);
	}

	// For use when need to specify item group
	private static void RegisterBlockItem(final RegistryEvent.Register<Item> event, Block block, ItemGroup group) {
		event.getRegistry().register(new BlockItem(block, new Item.Properties().group(group)).setRegistryName(block.getRegistryName()));
	}

}
