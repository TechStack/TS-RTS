package com.projectreddog.tsrts.entities;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.ai.MoveToOwnerSpecifiedLocation;
import com.projectreddog.tsrts.entities.ai.RetreatToOwnerSpecifiedLocation;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class TrebuchetBuilderEntity extends UnitEntity {

	public static final EntityPredicate VISION_NOT_REQUIRED = new EntityPredicate().setLineOfSiteRequired();
	private static final DataParameter<Float> BUILD_PHASE = EntityDataManager.createKey(TrebuchetBuilderEntity.class, DataSerializers.FLOAT);

	public static final int MAX_STEUP_STEP_COUNT = 7;
	public float buildPhase = 0;

	public float lastBuildPhase = 0;
	public int spawnedTrebEntityId = -1;
	private float tickCount = 0;
	private int particleTimer;
	private boolean aiActivated = false;

	public Reference.UNIT_TYPES getUnitType() {
		return UNIT_TYPES.TREBUCHETBUILDER;
	}

	@Override
	public void remove() {

		super.remove();

	}

	public TrebuchetBuilderEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		// this.world.getScoreboard().addPlayerToTeam(this.getCachedUniqueIdString(), scoreplayerteam);
		// Minecraft.getInstance().player.getTeam()
	}

	public boolean isBuilding() {
		if (getBuildPhase() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void tick() {
		tickCount++;
		super.tick();

		if (this.isHoldingGround == true && this.ownerControlledDestination == null) {
			if (this.world.isRemote == false) {
				if (buildPhase == 0) {
					buildPhase = 1;
					// Spawn the trebuchet in !

					spawnedTrebEntityId = Utilities.SpawnUnitForTeam(UNIT_TYPES.TREBUCHET, this.getOwnerName(), this.world, this.getPosition(), getTeam(), null);

				} else {

					if (spawnedTrebEntityId > 0) {
						Entity e = this.world.getEntityByID(spawnedTrebEntityId);
						if (e instanceof TrebuchetEntity) {
							TrebuchetEntity t = (TrebuchetEntity) e;
							if (t.getSetupStep() == TrebuchetEntity.MAX_STEUP_STEP_COUNT) {
								buildPhase = 2;
								this.remove();

								UnitEntity ue = (UnitEntity) t;
								if (ue.getTeam() != null) {
									String teamName = ue.getTeam().getName();

									TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].RemoveOneUnitCount(this.getUnitType());

								}
							}
						}
					}
				}
			}
			// cancel movement if holding ground && at destination
			// arrived so set move speed to 0 ?

			this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0);

		} else {
			// buildPhase = 0;
			if (buildPhase == 0) {
				this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(Config.CONFIG_UNIT_ATTRIBUTES_ARCHER.getMOVEMENT_SPEED());
			}
		}

		if (lastBuildPhase != buildPhase) {
			this.dataManager.set(BUILD_PHASE, buildPhase);
			lastBuildPhase = buildPhase;
		}

	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(BUILD_PHASE, (float) 0);

	}

	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));

		this.goalSelector.addGoal(3, new MoveToOwnerSpecifiedLocation(this, 1.1D, 32));
		this.goalSelector.addGoal(1, new RetreatToOwnerSpecifiedLocation(this, 1.1D, 32));

	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 0.93F;
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

	public float getBuildPhase() {
		return this.dataManager.get(BUILD_PHASE);
	}

}
