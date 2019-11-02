package com.projectreddog.tsrts.init;

import java.util.function.Supplier;

import com.projectreddog.tsrts.blocks.ArcheryRangeBlock;
import com.projectreddog.tsrts.blocks.GarrisonBlock;
import com.projectreddog.tsrts.blocks.MineSite;
import com.projectreddog.tsrts.blocks.TownCenterBlock;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.ArcheryRangeTileEntity;
import com.projectreddog.tsrts.tileentity.GarrisonTileEntity;
import com.projectreddog.tsrts.tileentity.MineSiteTileEntity;
import com.projectreddog.tsrts.tileentity.TownCenterTileEntity;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

	// BLOCKS
	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_GARRISON_BLOCK)
	public static GarrisonBlock GARRISON_BLOCK = new GarrisonBlock();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINE_SITE_BLOCK)
	public static MineSite MINE_SITE_BLOCK = new MineSite();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ARCHERY_RANGE_BLOCK)
	public static ArcheryRangeBlock ARCHERY_RANGE_BLOCK = new ArcheryRangeBlock();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TOWN_CENTER_BLOCK)
	public static TownCenterBlock TOWN_CENTER_BLOCK = new TownCenterBlock();

	// TILE ENTITIES
	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_GARRISON_BLOCK)
	public static TileEntityType<GarrisonTileEntity> GARRISON_TILE_ENTITY_TYPE;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ARCHERY_RANGE_BLOCK)
	public static TileEntityType<ArcheryRangeTileEntity> ARCHERY_RANGE_TILE_ENTITY_TYPE;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINE_SITE_BLOCK)
	public static TileEntityType<MineSiteTileEntity> MINE_SITE_TILE_ENITTY_TYPE;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TOWN_CENTER_BLOCK)
	public static TileEntityType<TownCenterTileEntity> TOWN_CENTER_ENTITY_TYPE;

	public static void RegisterBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().register(ModBlocks.GARRISON_BLOCK);
		event.getRegistry().register(ModBlocks.ARCHERY_RANGE_BLOCK);
		event.getRegistry().register(ModBlocks.MINE_SITE_BLOCK);
		event.getRegistry().register(ModBlocks.TOWN_CENTER_BLOCK);

	}

	public static void RegisterBlockItems(final RegistryEvent.Register<Item> event) {
		RegisterBlockItem(event, ModBlocks.GARRISON_BLOCK);
		RegisterBlockItem(event, ModBlocks.ARCHERY_RANGE_BLOCK);
		RegisterBlockItem(event, ModBlocks.MINE_SITE_BLOCK);
		RegisterBlockItem(event, ModBlocks.TOWN_CENTER_BLOCK);

	}

	// Use default BlockItem group
	private static void RegisterBlockItem(final RegistryEvent.Register<Item> event, Block block) {
		RegisterBlockItem(event, block, ModItemGroups.blocksItemGroup);
	}

	// For use when need to specify item group
	private static void RegisterBlockItem(final RegistryEvent.Register<Item> event, Block block, ItemGroup group) {
		event.getRegistry().register(new BlockItem(block, new Item.Properties().group(group)).setRegistryName(block.getRegistryName()));
	}

	public static void RegisterTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
		RegisterTileEntity(event, GarrisonTileEntity::new, ModBlocks.GARRISON_BLOCK);
		RegisterTileEntity(event, MineSiteTileEntity::new, ModBlocks.MINE_SITE_BLOCK);
		RegisterTileEntity(event, ArcheryRangeTileEntity::new, ModBlocks.ARCHERY_RANGE_BLOCK);
		RegisterTileEntity(event, TownCenterTileEntity::new, ModBlocks.TOWN_CENTER_BLOCK);

	}

	private static <T extends TileEntity> void RegisterTileEntity(final RegistryEvent.Register<TileEntityType<?>> event, Supplier<? extends T> factoryIn, Block block) {
		event.getRegistry().register(TileEntityType.Builder.create(factoryIn, block).build(null).setRegistryName(block.getRegistryName()));
	}
}
