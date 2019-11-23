package com.projectreddog.tsrts.entities;

import com.projectreddog.tsrts.entities.ai.MoveToOwnerSpecifiedLocation;
import com.projectreddog.tsrts.handler.Config;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ArcherMinionEntity extends UnitEntity implements IRangedAttackMob {

	public ArcherMinionEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		// this.world.getScoreboard().addPlayerToTeam(this.getCachedUniqueIdString(), scoreplayerteam);
		// Minecraft.getInstance().player.getTeam()
	}

	protected void registerGoals() {
		// this.goalSelector.addGoal(4, new ZombieEntity.AttackTurtleEggGoal(this, 1.0D, 3));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(1, new RangedBowAttackGoal(this, 1.0D, 20, 15.0F));
		// this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new MoveToOwnerSpecifiedLocation(this, 1.1D, 32));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));

		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, UnitEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, TargetEntity.class, false));

	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 0.93F;
	}

	protected void registerAttributes() {
		super.registerAttributes();
		// getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_ARCHER.getARMOR());
		this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_ARCHER.getARMOR_TOUGHNESS());
		this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_ARCHER.getATTACK_KNOCKBACK());
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_ARCHER.getATTACK_DAMAGE());
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_ARCHER.getFOLLOW_RANGE());
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_ARCHER.getKNOCK_BACK_RESISTANCE());
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_ARCHER.getMAX_HEALTH());
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_ARCHER.getMOVEMENT_SPEED());

	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, Items.BOW)));
		AbstractArrowEntity abstractarrowentity = ProjectileHelper.func_221272_a(this, itemstack, distanceFactor);
		if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.BowItem)
			abstractarrowentity = ((net.minecraft.item.BowItem) this.getHeldItemMainhand().getItem()).customeArrow(abstractarrowentity);
		double d0 = target.posX - this.posX;
		double d1 = 0;
		if (target instanceof TargetEntity) {
			d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 4.0F) - abstractarrowentity.posY;
		} else if (target instanceof UnitEntity) {
			d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 5.0F) - abstractarrowentity.posY;
		} else {
			d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 3.0F) - abstractarrowentity.posY;
		}

		double d2 = target.posZ - this.posZ;
		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
		abstractarrowentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (0));
		this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.world.addEntity(abstractarrowentity);
	}

	@Override
	public void tick() {
		if (this.getAttackTarget() != null) {
			if (!this.getAttackTarget().isAlive()) {
				this.setAttackTarget(null);
				this.setMoveStrafing(0);
				Vec3d v = this.getMotion();
				Vec3d v2 = new Vec3d(0, v.y, 0);

				this.setMotion(v2);
			}
		}
		super.tick();

	}

}
