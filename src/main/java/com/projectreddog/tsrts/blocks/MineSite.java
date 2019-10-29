package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.MineSiteTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MineSite extends OwnedBlock {

	public MineSite() {
		super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0f));
		setRegistryName(Reference.REIGSTRY_NAME_MINE_SITE_BLOCK);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {

		return new MineSiteTileEntity();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (placer instanceof PlayerEntity) {

			TileEntity te = worldIn.getTileEntity(pos);

			if (te != null) {
				if (te instanceof MineSiteTileEntity) {
					MineSiteTileEntity gte = (MineSiteTileEntity) te;
					gte.setOwner(((PlayerEntity) placer).getScoreboardName());
				}
			}

		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof INamedContainerProvider) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, te.getPos());
			} else {
				throw new IllegalStateException(" Our named container provider is missing !");
			}
			return true;

		}

		return super.onBlockActivated(state, world, pos, player, handIn, hit);
	}
}
