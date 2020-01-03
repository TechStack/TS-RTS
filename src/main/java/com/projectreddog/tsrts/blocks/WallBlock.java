package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.WallTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class WallBlock extends OwnedBlock {

	public WallBlock() {
		super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0f));
		setRegistryName(Reference.REIGSTRY_NAME_WALL_BLOCK);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {

		return new WallTileEntity();
	}

	public void increaseCountOfThisType(World world, PlayerEntity player) {
	}

}
