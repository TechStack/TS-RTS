package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.FarmTileEntity;
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

public class FarmBlock extends OwnedBlock {

	public FarmBlock() {
		super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0f));
		setRegistryName(Reference.REIGSTRY_NAME_FARM_BLOCK);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {

		return new FarmTileEntity();
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
