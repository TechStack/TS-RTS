package com.projectreddog.tsrts.entities;

import com.projectreddog.tsrts.entities.ai.FireballAttackGoal;
import com.projectreddog.tsrts.entities.ai.NearestAttackableVisionNotRequiredTargetGoal;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class TrebuchetEntity extends UnitEntity {

	public static final EntityPredicate VISION_NOT_REQUIRED = new EntityPredicate().setLineOfSiteRequired();
	private FireballAttackGoal fbag = new FireballAttackGoal(this);
	private static final DataParameter<Float> ATTACK_STEP = EntityDataManager.createKey(TrebuchetEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> SETUP_STEP = EntityDataManager.createKey(TrebuchetEntity.class, DataSerializers.FLOAT);
	public static final int MAX_STEUP_STEP_COUNT = 7;
	public float attackStep = 0;

	public float lastAttackStep = 0;
	public float setupStep = 0;
	public float lastSetupStep = 0;

	private float tickCount = 0;
	private int particleTimer;
	private boolean aiActivated = false;

	public Reference.UNIT_TYPES getUnitType() {
		return UNIT_TYPES.TREBUCHET;
	}

	public TrebuchetEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		// this.world.getScoreboard().addPlayerToTeam(this.getCachedUniqueIdString(), scoreplayerteam);
		// Minecraft.getInstance().player.getTeam()
	}

	@Override
	public void tick() {
		tickCount++;
		super.tick();
		if (lastAttackStep != attackStep) {
			this.dataManager.set(ATTACK_STEP, attackStep);
			lastAttackStep = attackStep;
		}

		if (lastSetupStep != setupStep) {
			this.dataManager.set(SETUP_STEP, setupStep);
			lastSetupStep = setupStep;
		}
		if (setupStep < TrebuchetEntity.MAX_STEUP_STEP_COUNT) {

			particleTimer++;
			if (this.particleTimer % 2 == 0) {
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.posX + (double) (this.rand.nextFloat() * this.getWidth() * 10f) - (this.getWidth() * 10f / 2), this.posY + (this.rand.nextFloat() * 5), this.posZ + (double) (this.rand.nextFloat() * this.getWidth() * 10f) - (this.getWidth() * 10f / 2), this.rand.nextFloat() * .01d - .005d, -.01d, this.rand.nextFloat() * .01d - .005d);

			}
			if (tickCount > 60) {
				tickCount = 0;
				setupStep++;
			}
		} else {
			if (!aiActivated) {

				this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
				this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
				aiActivated = true;
			}
		}

	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(ATTACK_STEP, (float) 0);
		this.dataManager.register(SETUP_STEP, (float) 0);

	}

	protected void registerGoals() {
		// this.goalSelector.addGoal(4, new ZombieEntity.AttackTurtleEggGoal(this, 1.0D, 3));

		// this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		fbag = new FireballAttackGoal(this);
		this.goalSelector.addGoal(2, fbag);

		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, UnitEntity.class, true, true));
		this.targetSelector.addGoal(3, new NearestAttackableVisionNotRequiredTargetGoal<>(this, TargetEntity.class, false));

	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 15F;
	}

	protected void registerAttributes() {
		super.registerAttributes();
		// getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_TREBUCHET.getARMOR());
		this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_TREBUCHET.getARMOR_TOUGHNESS());
		this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_TREBUCHET.getATTACK_KNOCKBACK());
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_TREBUCHET.getATTACK_DAMAGE());
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_TREBUCHET.getFOLLOW_RANGE());
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_TREBUCHET.getKNOCK_BACK_RESISTANCE());
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_TREBUCHET.getMAX_HEALTH());
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_TREBUCHET.getMOVEMENT_SPEED());

	}

	public float getAttackStep() {
		return this.dataManager.get(ATTACK_STEP);
	}

	public float getSetupStep() {
		return this.dataManager.get(SETUP_STEP);
	}
}
