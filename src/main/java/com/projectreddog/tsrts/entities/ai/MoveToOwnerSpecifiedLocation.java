package com.projectreddog.tsrts.entities.ai;

import java.util.EnumSet;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.UnitEntity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class MoveToOwnerSpecifiedLocation extends MoveToBlockGoal {
	private boolean isAboveDestination;
	UnitEntity ue;

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
			return checkDestination();

		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return checkDestination();
	}

	protected int getRunDelay(CreatureEntity creatureIn) {
		return 10;
	}

	public boolean checkDestination() {
		if (ue != null) {
			if (ue.owerControlledDestination != null) {
				// desitnation is valid check if we are at it.
				if (ue.owerControlledDestination.up().withinDistance(this.creature.getPositionVec(), this.getTargetDistanceSq())) {
					this.isAboveDestination = true;

					TSRTS.LOGGER.info("ARRIVED my Cords:" + this.creature.getPositionVec());
					TSRTS.LOGGER.info("ARRIVED Target Cords:" + ue.owerControlledDestination);

				}
				if (this.isAboveDestination) {
					ue.owerControlledDestination = null;
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	public double getTargetDistanceSq() {
		return .5D;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		if (ue != null && ue.owerControlledDestination != null) {
			if (!ue.owerControlledDestination.up().withinDistance(this.creature.getPositionVec(), this.getTargetDistanceSq())) {
				this.isAboveDestination = false;

				//

				++this.timeoutCounter;
				if (this.shouldMove()) {
					this.creature.getNavigator().tryMoveToXYZ((double) ((float) ue.owerControlledDestination.getX()) + 0.5D, (double) (ue.owerControlledDestination.getY() + 1), (double) ((float) ue.owerControlledDestination.getZ()) + 0.5D, this.movementSpeed);
				}
			} else {
				TSRTS.LOGGER.info("ARRIVED my Cords:" + this.creature.getPositionVec());
				TSRTS.LOGGER.info("ARRIVED Target Cords:" + ue.owerControlledDestination);

				this.isAboveDestination = true;
				ue.owerControlledDestination = null;
				--this.timeoutCounter;
			}
		}

	}

	public boolean shouldMove() {
		return true;
	}

	@Override
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		TSRTS.LOGGER.error("ShouldMOveTO is called CHeck if false is a valid for this case");
		return false;
	}

}
