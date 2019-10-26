package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamProperty;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;

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
}
