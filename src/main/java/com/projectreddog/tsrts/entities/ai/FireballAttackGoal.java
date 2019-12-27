package com.projectreddog.tsrts.entities.ai;

import java.util.EnumSet;

import com.projectreddog.tsrts.entities.UnitEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class FireballAttackGoal extends Goal {
	private final UnitEntity controllingEntity;
	public int attackStep;
	private int attackTime;
	private int counter;

	public FireballAttackGoal(UnitEntity ue) {
		this.controllingEntity = ue;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		LivingEntity livingentity = this.controllingEntity.getAttackTarget();
		return livingentity != null && livingentity.isAlive() && this.controllingEntity.canAttack(livingentity);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.attackStep = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask() {
		this.counter = 0;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		--this.attackTime;
		LivingEntity livingentity = this.controllingEntity.getAttackTarget();
		if (livingentity != null) {
			boolean flag = this.controllingEntity.getEntitySenses().canSee(livingentity);
			if (flag) {
				this.counter = 0;
			} else {
				++this.counter;
			}

			double d0 = this.controllingEntity.getDistanceSq(livingentity);
			if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
				double d1 = livingentity.posX - this.controllingEntity.posX;
				double d2 = livingentity.getBoundingBox().minY + (double) (livingentity.getHeight() / 2.0F) - (this.controllingEntity.posY + (double) (this.controllingEntity.getHeight() / 2.0F));
				double d3 = livingentity.posZ - this.controllingEntity.posZ;
				if (this.attackTime <= 0) {
					++this.attackStep;
					if (this.attackStep == 1) {
						this.attackTime = 60;
					} else if (this.attackStep <= 4) {
						this.attackTime = 6;
					} else {
						this.attackTime = 100;
						this.attackStep = 0;
					}

					if (this.attackStep > 1) {
						float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
						this.controllingEntity.world.playEvent((PlayerEntity) null, 1018, new BlockPos(this.controllingEntity), 0);

						for (int i = 0; i < 1; ++i) {
							SmallFireballEntity smallfireballentity = new SmallFireballEntity(this.controllingEntity.world, this.controllingEntity, d1 + this.controllingEntity.getRNG().nextGaussian() * (double) f, d2, d3 + this.controllingEntity.getRNG().nextGaussian() * (double) f);
							smallfireballentity.posY = this.controllingEntity.posY + (double) (this.controllingEntity.getHeight() / 2.0F) + 0.5D;
							this.controllingEntity.world.addEntity(smallfireballentity);
						}
					}
				}

				this.controllingEntity.getLookController().setLookPositionWithEntity(livingentity, 10.0F, 10.0F);
			} else if (this.counter < 5) {
				this.controllingEntity.getMoveHelper().setMoveTo(livingentity.posX, livingentity.posY, livingentity.posZ, 1.0D);
			}

			super.tick();
		}
	}

	private double getFollowDistance() {
		return this.controllingEntity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getValue();
	}
}