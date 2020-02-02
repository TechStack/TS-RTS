package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.CrossbowmanEntity;

import net.minecraft.client.renderer.entity.model.BipedModel;

public class CrossbowmanModel extends BipedModel<CrossbowmanEntity> {

	public CrossbowmanModel() {
		super(0, 0.0F, 64, 64);
	}

	public CrossbowmanModel(float size) {
		super(size, 0.0F, 64, 32);
	}
}
