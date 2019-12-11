package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.MountedEntity;

import net.minecraft.client.renderer.entity.model.BipedModel;

public class MountedMinionModel extends BipedModel<MountedEntity> {

	public MountedMinionModel() {

		// FALSE is default steve
		// BODY
		super(0, 0.0F, 64, 64);
		this.isChild = true;
		this.isSitting = true;
	}

	public MountedMinionModel(float size) {
		// super(size, true);
// ARMOR?
		super(size, 0.0F, 64, 32);
	}

	@Override
	public void render(MountedEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

	}
}
