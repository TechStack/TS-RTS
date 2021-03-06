package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.MarketplaceTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MarketplaceBlock extends OwnedBlock {

	public MarketplaceBlock() {
		super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0f));
		setRegistryName(Reference.REIGSTRY_NAME_MARKETPLACE_BLOCK);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, TEAM);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {

		return state.with(BlockStateProperties.HORIZONTAL_FACING, mirrorIn.mirror(state.get(BlockStateProperties.HORIZONTAL_FACING)));

	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(BlockStateProperties.HORIZONTAL_FACING, rot.rotate(state.get(BlockStateProperties.HORIZONTAL_FACING)));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {

		return new MarketplaceTileEntity();
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

		if (!world.isRemote && handIn == Hand.MAIN_HAND) {

			TileEntity te = world.getTileEntity(pos);
			if (te instanceof MarketplaceTileEntity) {
				MarketplaceTileEntity market = (MarketplaceTileEntity) te;
				if (market.getTeam() == null || market.getTeam().isSameTeam(player.getTeam())) {
					// TODO: Open the gui
				}
			}
			return true;

		}

		return super.onBlockActivated(state, world, pos, player, handIn, hit);
	}

	public void increaseCountOfThisType(World world, PlayerEntity player) {
	}

}
