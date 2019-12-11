package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.handler.Config;

public class UnitAttributes {

	private float MAX_HEALTH;
	private float KNOCK_BACK_RESISTANCE;
	private float MOVEMENT_SPEED;
	private float ARMOR;
	private float ARMOR_TOUGHNESS;
	private float ATTACK_KNOCKBACK;
	private float ATTACK_DAMAGE;
	private float FOLLOW_RANGE;

	public UnitAttributes(float mAX_HEALTH, float kNOCK_BACK_RESISTANCE, float mOVEMENT_SPEED, float aRMOR, float aRMOR_TOUGHNESS, float aTTACK_KNOCKBACK, float aTTACK_DAMAGE, float fOLLOW_RANGE) {
		super();
		MAX_HEALTH = mAX_HEALTH;
		KNOCK_BACK_RESISTANCE = kNOCK_BACK_RESISTANCE;
		MOVEMENT_SPEED = mOVEMENT_SPEED;
		ARMOR = aRMOR;
		ARMOR_TOUGHNESS = aRMOR_TOUGHNESS;
		ATTACK_KNOCKBACK = aTTACK_KNOCKBACK;
		ATTACK_DAMAGE = aTTACK_DAMAGE;
		FOLLOW_RANGE = fOLLOW_RANGE;
	}

	public UnitAttributes(float[] values) {

		MAX_HEALTH = values[Config.UnitStats.MAX_HEALTH.ordinal()];
		KNOCK_BACK_RESISTANCE = values[Config.UnitStats.KNOCK_BACK_RESISTANCE.ordinal()];
		MOVEMENT_SPEED = values[Config.UnitStats.MOVEMENT_SPEED.ordinal()];
		ARMOR = values[Config.UnitStats.ARMOR.ordinal()];
		ARMOR_TOUGHNESS = values[Config.UnitStats.ARMOR_TOUGHNESS.ordinal()];
		ATTACK_KNOCKBACK = values[Config.UnitStats.ATTACK_KNOCKBACK.ordinal()];
		ATTACK_DAMAGE = values[Config.UnitStats.ATTACKD_DAMAGE.ordinal()];
		FOLLOW_RANGE = values[Config.UnitStats.FOLLOW_RANGE.ordinal()];
	}

	public float getMAX_HEALTH() {
		return MAX_HEALTH;
	}

	public float getKNOCK_BACK_RESISTANCE() {
		return KNOCK_BACK_RESISTANCE;
	}

	public float getMOVEMENT_SPEED() {
		return MOVEMENT_SPEED;
	}

	public float getARMOR() {
		return ARMOR;
	}

	public float getARMOR_TOUGHNESS() {
		return ARMOR_TOUGHNESS;
	}

	public float getATTACK_KNOCKBACK() {
		return ATTACK_KNOCKBACK;
	}

	public float getATTACK_DAMAGE() {
		return ATTACK_DAMAGE;
	}

	public float getFOLLOW_RANGE() {
		return FOLLOW_RANGE;
	}

}
