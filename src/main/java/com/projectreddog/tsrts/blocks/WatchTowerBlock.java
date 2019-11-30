package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.containers.provider.TownHallContinerProvider;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.WatchTowerTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WatchTowerBlock extends OwnedBlock {

	public WatchTowerBlock() {
		super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0f));
		setRegistryName(Reference.REIGSTRY_NAME_WATCH_TOWER_BLOCK);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {

		return new WatchTowerTileEntity();
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

		if (!world.isRemote) {
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) new TownHallContinerProvider());

			return true;

		}

		return super.onBlockActivated(state, world, pos, player, handIn, hit);
	}

	public void increaseCountOfThisType(World world, PlayerEntity player) {
	}

}
