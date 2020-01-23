package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.LongBowmanEntity;

import net.minecraft.client.renderer.entity.model.BipedModel;

public class LongBowmanModel extends BipedModel<LongBowmanEntity> {

	public LongBowmanModel() {

		// FALSE is default steve
		// BODY
		super(0, 0.0F, 64, 64);
	}

	public LongBowmanModel(float size) {
		// super(size, true);
// ARMOR?
		super(size, 0.0F, 64, 32);
	}
}
