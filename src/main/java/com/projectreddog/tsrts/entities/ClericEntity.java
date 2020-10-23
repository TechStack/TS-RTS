package com.projectreddog.tsrts.entities;

import com.projectreddog.tsrts.entities.ai.HealTeammateGoal;
import com.projectreddog.tsrts.entities.ai.MoveToOwnerSpecifiedLocation;
import com.projectreddog.tsrts.entities.ai.NearestHealableTargetGoal;
import com.projectreddog.tsrts.entities.ai.RetreatToOwnerSpecifiedLocation;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ClericEntity extends UnitEntity {
	private static final DataParameter<Boolean> CASTING_DATA_PARAMETER = EntityDataManager.createKey(ClericEntity.class, DataSerializers.BOOLEAN);
	public static final EntityPredicate VISION_NOT_REQUIRED = new EntityPredicate().setLineOfSiteRequired();
	private boolean casting = false;
	private float castingTimer = 20f;

	public ClericEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);

	}

	@Override
	protected void registerData() {
		// TODO Auto-generated method stub
		super.registerData();
		this.dataManager.register(CASTING_DATA_PARAMETER, false);

	}

	public Reference.UNIT_TYPES getUnitType() {
		return UNIT_TYPES.CLERIC;
	}

	public void healEntityAsMob(LivingEntity target) {
		if (target != null) {
			if (target.getHealth() < target.getMaxHealth()) {
				if (target.getActivePotionEffect(Effects.REGENERATION) == null) {
					// doesn't have regen so re-add it!
					target.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100, 1));
					this.setIsCasting(true);
				}
			} else {
				// target healed
				this.setAttackTarget(null);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.IsCasting()) {
			this.castingTimer--;

			if (this.castingTimer <= 0) {
				setIsCasting(false);
			}
			if (this.castingTimer % 2 == 0) {
				this.world.addParticle(ParticleTypes.EFFECT, this.posX + (double) (this.rand.nextFloat() * this.getWidth() * .5f), this.posY + (double) (this.getHeight()), this.posZ + (double) (this.rand.nextFloat() * this.getWidth() * .5f), 0, .1d, 0);
				this.world.addParticle(ParticleTypes.EFFECT, this.posX + (double) (this.rand.nextFloat() * this.getWidth() * .5f), this.posY + (double) (this.getHeight()), this.posZ + (double) (this.rand.nextFloat() * this.getWidth() * .5f), 0, .1d, 0);

			}
		} else {
			castingTimer = 20f;
		}

	}

	protected void registerGoals() {
		// this.goalSelector.addGoal(4, new ZombieEntity.AttackTurtleEggGoal(this, 1.0D, 3));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new HealTeammateGoal(this, 1D, true));
		// this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new MoveToOwnerSpecifiedLocation(this, 1.1D, 32));
		this.goalSelector.addGoal(1, new RetreatToOwnerSpecifiedLocation(this, 1.1D, 32));

//		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));

		this.targetSelector.addGoal(4, new NearestHealableTargetGoal<>(this, PlayerEntity.class, true, true));
		this.targetSelector.addGoal(2, new NearestHealableTargetGoal<>(this, UnitEntity.class, true, true));
		// this.targetSelector.addGoal(3, new NearestAttackableVisionNotRequiredTargetGoal<>(this, TargetEntity.class, false));

	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 0.93F;
	}

	protected void registerAttributes() {
		super.registerAttributes();
		// getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_CLERIC.getARMOR());
		this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_CLERIC.getARMOR_TOUGHNESS());
		this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_CLERIC.getATTACK_KNOCKBACK());
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_CLERIC.getATTACK_DAMAGE());
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_CLERIC.getFOLLOW_RANGE());
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_CLERIC.getKNOCK_BACK_RESISTANCE());
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_CLERIC.getMAX_HEALTH());
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_CLERIC.getMOVEMENT_SPEED());

	}

	public boolean IsCasting() {
		return this.dataManager.get(CASTING_DATA_PARAMETER);
	}

	public void setIsCasting(boolean Casting) {
		this.casting = Casting;
		this.dataManager.set(CASTING_DATA_PARAMETER, this.casting);
		castingTimer = 20f;
	}
}
