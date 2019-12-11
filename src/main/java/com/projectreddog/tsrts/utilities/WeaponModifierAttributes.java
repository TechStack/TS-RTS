package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.handler.Config;

public class WeaponModifierAttributes {

	private float ATTACK_DAMAGE_MODIFIER;
	private float ATTACK_SPEED_MODIFIER;

	public WeaponModifierAttributes(float[] values) {

		ATTACK_DAMAGE_MODIFIER = values[Config.WeaponModifierStats.ATTACK_DAMAGE_MODIFIER.ordinal()];
		ATTACK_SPEED_MODIFIER = values[Config.WeaponModifierStats.ATTACK_SPEED_MODIFIER.ordinal()];

	}

	public float getATTACK_DAMAGE_MODIFIER() {
		return ATTACK_DAMAGE_MODIFIER;
	}

	public float getATTACK_SPEED_MODIFIER() {
		return ATTACK_SPEED_MODIFIER;
	}

	public WeaponModifierAttributes(float aTTACK_DAMAGE_MODIFIER, float aTTACK_SPEED_MODIFIER) {
		super();
		ATTACK_DAMAGE_MODIFIER = aTTACK_DAMAGE_MODIFIER;
		ATTACK_SPEED_MODIFIER = aTTACK_SPEED_MODIFIER;
	}

}
