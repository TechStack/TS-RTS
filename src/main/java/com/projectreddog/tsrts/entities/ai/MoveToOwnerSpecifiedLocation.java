package com.projectreddog.tsrts.entities.ai;

import java.util.EnumSet;

import com.projectreddog.tsrts.entities.UnitEntity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;

public class MoveToOwnerSpecifiedLocation extends MoveToBlockGoal {
	private boolean isAboveDestination;
	UnitEntity ue;
	private Vec3d lastPos = new Vec3d(0, -100, 0);
	private int timeUnableToMove = 0;

	public MoveToOwnerSpecifiedLocation(CreatureEntity creature, double speedIn, int length) {
		super(creature, speedIn, length);
		if (creature instanceof UnitEntity) {
			ue = (UnitEntity) creature;
		}
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));

	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		if (this.runDelay > 0) {
			--this.runDelay;
			return false;
		} else {
			this.runDelay = this.getRunDelay(this.creature);
			return ShouldMove(ue.ownerControlledDestination);
		}
	}

	public boolean ShouldMove(BlockPos bp) {
		if (bp == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void resetTask() {
		timeUnableToMove = 0;
		super.resetTask();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return ShouldMove(ue.ownerControlledDestination);
	}

	protected int getRunDelay(CreatureEntity creatureIn) {
		return 10;
	}

	public boolean arrived(BlockPos bp, Vec3d entPos, double range) {
		boolean result = false;
		if (bp != null) {
			double xdelta = ((double) bp.getX() + .5d) - entPos.getX();
			double ydelta = ((double) bp.getY()) - entPos.getY();
			double zdelta = ((double) bp.getZ() + .5d) - entPos.getZ();
			result = ((xdelta * xdelta) + ydelta * ydelta + (zdelta * zdelta) <= range * range);
		} else {
			// NO target so arrived please
			result = true;
		}

		if (lastPos.distanceTo(entPos) < .05d) {

			timeUnableToMove++;
			if (timeUnableToMove > 10) {
				// half a second not able to move! ABORT and assume arrived so we don't tank TPS
				result = true;
			}
		}
		lastPos = entPos;
		return result;
	}

	public double getTargetDistanceSq() {
		return .5D;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		if (ue != null) {
			if (!arrived(ue.ownerControlledDestination, this.creature.getPositionVec(), getTargetDistanceSq())) {
				Path path = this.creature.getNavigator().getPathToPos(ue.ownerControlledDestination, 0);
				this.creature.getNavigator().setPath(path, 1);
			} else {
				this.creature.getNavigator().clearPath();
				ue.ownerControlledDestination = null;

			}
		}

	}

	@Override
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		// TODO Auto-generated method stub
		return false;
	}

}
