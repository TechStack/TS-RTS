package com.projectreddog.tsrts.client.model;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.entities.SapperEntity;

import net.minecraft.client.renderer.entity.model.BipedModel;

public class SapperModel extends BipedModel<SapperEntity> {

	public SapperModel() {
		super(0, 0.0F, 64, 64);
	}

	public SapperModel(float size) {

		super(size, 0.0F, 64, 32);
	}

	@Override
	public void render(SapperEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.bipedRightArm.rotateAngleX = -0.5F;
		this.bipedLeftArm.rotateAngleX = -0.5F;
		this.bipedRightArm.rotateAngleZ = 0.05F;
		this.bipedLeftArm.rotateAngleZ = -0.05F;
		GlStateManager.pushMatrix();

		float f = 2.0F;
		GlStateManager.scalef(0.75F, 0.75F, 0.75F);
		GlStateManager.translatef(0.0F, 16.0F * scale, 0.0F);
		this.bipedHead.render(scale);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.scalef(0.5F, 0.5F, 0.5F);
		GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
		this.bipedHeadwear.render(scale);

		GlStateManager.popMatrix();

	}
}
