package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.GarrisonTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class GarrisonBlock extends Block {

	public GarrisonBlock() {
		super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0f));
		setRegistryName(Reference.REIGSTRY_NAME_GARRISON_BLOCK);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {

		return new GarrisonTileEntity();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		if (placer instanceof PlayerEntity) {

			TileEntity te = worldIn.getTileEntity(pos);

			if (te != null) {
				if (te instanceof GarrisonTileEntity) {
					GarrisonTileEntity gte = (GarrisonTileEntity) te;
					gte.setOwner(((PlayerEntity) placer).getScoreboardName());
				}
			}
		}
	}

}
