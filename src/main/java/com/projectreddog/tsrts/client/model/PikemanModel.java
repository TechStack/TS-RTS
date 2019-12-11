package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.PikemanEntity;

import net.minecraft.client.renderer.entity.model.BipedModel;

public class PikemanModel extends BipedModel<PikemanEntity> {

	public PikemanModel() {
		super(0, 0.0F, 64, 64);
	}

	public PikemanModel(float size) {

		super(size, 0.0F, 64, 32);
	}

	@Override
	public void render(PikemanEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

	}
}
