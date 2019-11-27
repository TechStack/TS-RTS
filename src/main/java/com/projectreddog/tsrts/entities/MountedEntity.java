package com.projectreddog.tsrts.entities;

import com.projectreddog.tsrts.entities.ai.MoveToOwnerSpecifiedLocation;
import com.projectreddog.tsrts.entities.ai.NearestAttackableVisionNotRequiredTargetGoal;
import com.projectreddog.tsrts.entities.ai.RetreatToOwnerSpecifiedLocation;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class MountedEntity extends UnitEntity {

	public MountedEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	protected void registerGoals() {
		// this.goalSelector.addGoal(4, new ZombieEntity.AttackTurtleEggGoal(this, 1.0D, 3));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1D, true));
		// this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new MoveToOwnerSpecifiedLocation(this, 1.1D, 32));
		this.goalSelector.addGoal(1, new RetreatToOwnerSpecifiedLocation(this, 1.1D, 32));

		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));

		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, UnitEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableVisionNotRequiredTargetGoal<>(this, TargetEntity.class, false));

	}
}
