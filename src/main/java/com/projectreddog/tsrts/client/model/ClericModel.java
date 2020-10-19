package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.ClericEntity;

import net.minecraft.client.renderer.entity.model.BipedModel;

public class ClericModel extends BipedModel<ClericEntity> {

	public ClericModel() {
		super(0, 0.0F, 64, 64);
	}

	public ClericModel(float size) {

		super(size, 0.0F, 64, 32);
	}

	@Override
	public void render(ClericEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

	}

	@Override
	public void setRotationAngles(ClericEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		// this.bipedLeftArm.rotateAngleX = 3.14f;
		// this.bipedLeftArm.rotateAngleY = .08f;
		//
		if (entityIn.IsCasting()) {
			this.bipedLeftArm.rotateAngleZ = -2.5f;
			this.bipedRightArm.rotateAngleZ = 2.5f;
		}
	}

}
