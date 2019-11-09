package com.projectreddog.tsrts.entities.ai;

import java.util.function.Predicate;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

public class NearestAttackableVisionNotRequiredTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal {

	public NearestAttackableVisionNotRequiredTargetGoal(MobEntity p_i50313_1_, Class<T> p_i50313_2_, boolean p_i50313_3_) {
		this(p_i50313_1_, p_i50313_2_, p_i50313_3_, false);
	}

	public NearestAttackableVisionNotRequiredTargetGoal(MobEntity p_i50314_1_, Class<T> p_i50314_2_, boolean p_i50314_3_, boolean p_i50314_4_) {
		this(p_i50314_1_, p_i50314_2_, 10, p_i50314_3_, p_i50314_4_, (Predicate<LivingEntity>) null);
	}

	public NearestAttackableVisionNotRequiredTargetGoal(MobEntity p_i50315_1_, Class p_i50315_2_, int p_i50315_3_, boolean p_i50315_4_, boolean p_i50315_5_, Predicate p_i50315_6_) {
		super(p_i50315_1_, p_i50315_2_, p_i50315_3_, p_i50315_4_, p_i50315_5_, p_i50315_6_);
		// TODO Auto-generated constructor stub

		targetEntitySelector = targetEntitySelector.setLineOfSiteRequired();
	}

}
