package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.tileentity.OwnedCooldownTileEntity;
import com.projectreddog.tsrts.tileentity.OwnedTileEntity;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamProperty;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class OwnedBlock extends Block {

	public static final TeamProperty TEAM = TeamProperty.create("team", TeamEnum.BLUE, TeamEnum.RED, TeamEnum.GREEN, TeamEnum.YELLOW);

	public OwnedBlock(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING, TEAM);
		super.fillStateContainer(builder);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		if (placer instanceof PlayerEntity) {
			TileEntity te = worldIn.getTileEntity(pos);

			if (te != null) {
				if (te instanceof OwnedTileEntity) {
					OwnedTileEntity ote = (OwnedTileEntity) te;
					ote.setOwner(((PlayerEntity) placer).getScoreboardName());
				}
			}

		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof INamedContainerProvider && te instanceof OwnedCooldownTileEntity) {
				OwnedCooldownTileEntity octe = (OwnedCooldownTileEntity) te;
				if (octe.getTeam() != null && octe.getTeam().isSameTeam(player.getTeam())) {
					NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, te.getPos());
				}
			} else {
				throw new IllegalStateException(" Our named container provider is missing !");
			}
			return true;

		}

		return super.onBlockActivated(state, world, pos, player, handIn, hit);
	}

}
