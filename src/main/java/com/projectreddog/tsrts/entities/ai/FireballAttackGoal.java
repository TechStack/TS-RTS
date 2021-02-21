package com.projectreddog.tsrts.entities.ai;

import java.util.EnumSet;

import com.projectreddog.tsrts.entities.TrebuchetEntity;
import com.projectreddog.tsrts.entities.projectile.TebuchetFireballEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class FireballAttackGoal extends Goal {
	private final TrebuchetEntity controllingEntity;
	private int attackTime;
	private int counter;

	public FireballAttackGoal(TrebuchetEntity ue) {
		this.controllingEntity = ue;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		if (this.controllingEntity.getSetupStep() < TrebuchetEntity.MAX_STEUP_STEP_COUNT) {
			return false;
		}
		LivingEntity livingentity = this.controllingEntity.getAttackTarget();
		return livingentity != null && livingentity.isAlive() && this.controllingEntity.canAttack(livingentity);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.controllingEntity.attackStep = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask() {
		this.attackTime = 0;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		if (!shouldExecute()) {
			return;
		}
		--this.attackTime;

		LivingEntity livingentity = this.controllingEntity.getAttackTarget();
		if (livingentity != null) {
			boolean flag = this.controllingEntity.getEntitySenses().canSee(livingentity);

			double d0 = this.controllingEntity.getDistanceSq(livingentity);
			if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag && d0 > 100) {
				double d1 = livingentity.posX - this.controllingEntity.posX;
				double d2 = livingentity.getBoundingBox().minY + (double) (livingentity.getHeight() / 2.0F) - (this.controllingEntity.posY + (double) (this.controllingEntity.getHeight() / 2.0F) + 12.5f);
				double d3 = livingentity.posZ - this.controllingEntity.posZ;
				if (this.attackTime < 0) {
					++this.controllingEntity.attackStep;

					if (this.controllingEntity.attackStep == 12) {

						float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.05F;
						this.controllingEntity.world.playEvent((PlayerEntity) null, 1018, new BlockPos(this.controllingEntity), 0);

						for (int i = 0; i < 1; ++i) {
							TebuchetFireballEntity smallfireballentity = new TebuchetFireballEntity(this.controllingEntity.world, this.controllingEntity, d1 + this.controllingEntity.getRNG().nextGaussian() * (double) f, d2, d3 + this.controllingEntity.getRNG().nextGaussian() * (double) f);
							smallfireballentity.posY = this.controllingEntity.posY + (double) (this.controllingEntity.getHeight() / 2.0F) + 12.5D;
							this.controllingEntity.world.addEntity(smallfireballentity);
						}
					}

				}
				if (this.controllingEntity.attackStep == 12) {
					this.controllingEntity.attackStep = 0;
					this.attackTime = 160;
				}

			} else {
				this.controllingEntity.setAttackTarget(null);
			}

			this.controllingEntity.getLookController().setLookPositionWithEntity(livingentity, 10.0F, 10.0F);

		}

		super.tick();

	}

	private double getFollowDistance() {
		return this.controllingEntity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getValue();
	}
}