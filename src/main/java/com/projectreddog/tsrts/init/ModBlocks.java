package com.projectreddog.tsrts.init;

import java.util.function.Supplier;

import com.projectreddog.tsrts.blocks.ArcheryRangeBlock;
import com.projectreddog.tsrts.blocks.BarracksBlock;
import com.projectreddog.tsrts.blocks.MineSite;
import com.projectreddog.tsrts.blocks.ResearchCenterBlock;
import com.projectreddog.tsrts.blocks.StablesBlock;
import com.projectreddog.tsrts.blocks.TownHallBlock;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.ArcheryRangeTileEntity;
import com.projectreddog.tsrts.tileentity.BarracksTileEntity;
import com.projectreddog.tsrts.tileentity.MineSiteTileEntity;
import com.projectreddog.tsrts.tileentity.ResearchCenterTileEntity;
import com.projectreddog.tsrts.tileentity.StablesTileEntity;
import com.projectreddog.tsrts.tileentity.TownHallTileEntity;

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
	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_BARRACKS_BLOCK)
	public static BarracksBlock BARRACKS_BLOCK = new BarracksBlock();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINE_SITE_BLOCK)
	public static MineSite MINE_SITE_BLOCK = new MineSite();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ARCHERY_RANGE_BLOCK)
	public static ArcheryRangeBlock ARCHERY_RANGE_BLOCK = new ArcheryRangeBlock();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TOWN_HALL_BLOCK)
	public static TownHallBlock TOWN_HALL_BLOCK = new TownHallBlock();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_RESERACH_CENTER_BLOCK)
	public static ResearchCenterBlock RESEARCH_CENTER_BLOCK = new ResearchCenterBlock();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_STABLES_BLOCK)
	public static StablesBlock STABLES_BLOCK = new StablesBlock();

	// TILE ENTITIES
	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_BARRACKS_BLOCK)
	public static TileEntityType<BarracksTileEntity> BARRACKS_TILE_ENTITY_TYPE;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ARCHERY_RANGE_BLOCK)
	public static TileEntityType<ArcheryRangeTileEntity> ARCHERY_RANGE_TILE_ENTITY_TYPE;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINE_SITE_BLOCK)
	public static TileEntityType<MineSiteTileEntity> MINE_SITE_TILE_ENITTY_TYPE;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TOWN_HALL_BLOCK)
	public static TileEntityType<TownHallTileEntity> TOWN_HALL_ENTITY_TYPE;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_RESERACH_CENTER_BLOCK)
	public static TileEntityType<ResearchCenterTileEntity> RESEARCH_CENTER_ENTITY_TYPE;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_STABLES_BLOCK)
	public static TileEntityType<StablesTileEntity> STABLES_ENTITY_ENTITY_TYPE;

	public static void RegisterBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().register(ModBlocks.BARRACKS_BLOCK);
		event.getRegistry().register(ModBlocks.ARCHERY_RANGE_BLOCK);
		event.getRegistry().register(ModBlocks.MINE_SITE_BLOCK);
		event.getRegistry().register(ModBlocks.TOWN_HALL_BLOCK);
		event.getRegistry().register(ModBlocks.RESEARCH_CENTER_BLOCK);
		event.getRegistry().register(ModBlocks.STABLES_BLOCK);
	}

	public static void RegisterBlockItems(final RegistryEvent.Register<Item> event) {
		RegisterBlockItem(event, ModBlocks.BARRACKS_BLOCK);
		RegisterBlockItem(event, ModBlocks.ARCHERY_RANGE_BLOCK);
		RegisterBlockItem(event, ModBlocks.MINE_SITE_BLOCK);
		RegisterBlockItem(event, ModBlocks.TOWN_HALL_BLOCK);
		RegisterBlockItem(event, ModBlocks.RESEARCH_CENTER_BLOCK);
		RegisterBlockItem(event, ModBlocks.STABLES_BLOCK);

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
		RegisterTileEntity(event, BarracksTileEntity::new, ModBlocks.BARRACKS_BLOCK);
		RegisterTileEntity(event, MineSiteTileEntity::new, ModBlocks.MINE_SITE_BLOCK);
		RegisterTileEntity(event, ArcheryRangeTileEntity::new, ModBlocks.ARCHERY_RANGE_BLOCK);
		RegisterTileEntity(event, TownHallTileEntity::new, ModBlocks.TOWN_HALL_BLOCK);
		RegisterTileEntity(event, ResearchCenterTileEntity::new, ModBlocks.RESEARCH_CENTER_BLOCK);
		RegisterTileEntity(event, StablesTileEntity::new, ModBlocks.STABLES_BLOCK);
	}

	private static <T extends TileEntity> void RegisterTileEntity(final RegistryEvent.Register<TileEntityType<?>> event, Supplier<? extends T> factoryIn, Block block) {
		event.getRegistry().register(TileEntityType.Builder.create(factoryIn, block).build(null).setRegistryName(block.getRegistryName()));
	}
}
