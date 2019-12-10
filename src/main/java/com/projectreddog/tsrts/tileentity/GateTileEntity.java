package com.projectreddog.tsrts.tileentity;

import java.util.List;

import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class GateTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider {

	private boolean isOpen = true;

	public GateTileEntity() {
		super(ModBlocks.GATE_ENTITY_ENTITY_TYPE);
	}

	public void toggleOpen() {
		isOpen = !isOpen;

		if (isOpen) {
			Close();
		} else {
			Open();
		}
		this.markDirty();

	}

	private Direction getDirection() {
		return this.world.getBlockState(this.pos).get(BlockStateProperties.HORIZONTAL_FACING);
	}

	private void Open() {
		Direction d = getDirection();
		BlockState bs = Blocks.AIR.getDefaultState();
		updateGate(d, bs);
	}

	private void updateGate(Direction d, BlockState bs) {
		BlockState oldState = null;
		if (d == Direction.NORTH || d == Direction.SOUTH) {
			// need to interact with east / west & mid
			BlockPos bp = this.getPos();
			for (int y = 1; y <= 4; y++) {

				oldState = world.getBlockState(bp.down(y));
				world.setBlockState(bp.down(y), bs);
				world.notifyBlockUpdate(bp.down(y), oldState, bs, 3);

				oldState = world.getBlockState(bp.down(y).east());
				world.setBlockState(bp.down(y).east(), bs);
				world.notifyBlockUpdate(bp.down(y).east(), oldState, bs, 3);

				oldState = world.getBlockState(bp.down(y).west());
				world.setBlockState(bp.down(y).west(), bs);
				world.notifyBlockUpdate(bp.down(y).west(), oldState, bs, 3);

			}

		} else {
			// need to interact with norht / south & mid
			BlockPos bp = this.getPos();
			for (int y = 1; y <= 4; y++) {

				oldState = world.getBlockState(bp.down(y));
				world.setBlockState(bp.down(y), bs);
				world.notifyBlockUpdate(bp.down(y), oldState, bs, 3);

				oldState = world.getBlockState(bp.down(y).north());
				world.setBlockState(bp.down(y).north(), bs);
				world.notifyBlockUpdate(bp.down(y).north(), oldState, bs, 3);

				oldState = world.getBlockState(bp.down(y).south());
				world.setBlockState(bp.down(y).south(), bs);
				world.notifyBlockUpdate(bp.down(y).south(), oldState, bs, 3);

			}

		}
	}

	private BlockState getBlockStateForGateFromTeam(String teamName) {
		BlockState bs = Blocks.AIR.getDefaultState();
		switch (TeamEnum.values()[TeamEnum.getIDFromName(teamName)]) {
		case BLUE:
			bs = Blocks.SPRUCE_FENCE.getDefaultState();
			break;
		case RED:
			bs = Blocks.IRON_BARS.getDefaultState();
			break;
		case GREEN:
			bs = Blocks.NETHER_BRICK_FENCE.getDefaultState();
			break;
		case YELLOW:
			bs = Blocks.ACACIA_FENCE.getDefaultState();
			break;
		}

		return bs;
	}

	private void Close() {
		Direction d = getDirection();
		BlockState bs = getBlockStateForGateFromTeam(this.getTeam().getName());
		updateGate(d, bs);
	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToTeam(this.getWorld(), this.getTeam().getName(), "tsrts.destroy.gate");
	}

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_STABLES.get();
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putBoolean("isOpen", isOpen);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		isOpen = compound.getBoolean("isOpen");
	}

	@Override
	public void AfterDeathAction() {
		super.AfterDeathAction();

		AxisAlignedBB bb = new AxisAlignedBB(this.getPos(), this.getPos().down(6)).grow(3, 0, 3);

		List<TargetEntity> teList = world.getEntitiesWithinAABB(TargetEntity.class, bb);
		float health = 0;
		int[] ids = new int[teList.size()];
		for (int i = 0; i < teList.size(); i++) {
			teList.get(i).setHealth(0);
		}

	}
}
