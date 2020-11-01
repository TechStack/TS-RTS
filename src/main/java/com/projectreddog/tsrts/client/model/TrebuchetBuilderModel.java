package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.TrebuchetBuilderEntity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.HandSide;

public class TrebuchetBuilderModel extends BipedModel<TrebuchetBuilderEntity> {

	public TrebuchetBuilderModel() {
		super(0, 0.0F, 64, 64);
	}

	public TrebuchetBuilderModel(float size) {

		super(size, 0.0F, 64, 32);
	}

	@Override
	public void render(TrebuchetBuilderEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

	}

	@Override
	public void setRotationAngles(TrebuchetBuilderEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		// this.bipedLeftArm.rotateAngleX = 3.14f;
		// this.bipedLeftArm.rotateAngleY = .08f;
		//
		// this.bipedLeftArm.rotateAngleZ = -2.5f;
		// this.bipedRightArm.rotateAngleZ = 2.5f;

		if (entityIn.isBuilding()) {
			float armAmt = 6.5f - (2.5f * (ageInTicks % 10) / 10);
			// building so swing arm.
			if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
				this.bipedRightArm.rotateAngleX = armAmt;
			} else {
				this.bipedLeftArm.rotateAngleX = armAmt;
			}
		}

	}
}