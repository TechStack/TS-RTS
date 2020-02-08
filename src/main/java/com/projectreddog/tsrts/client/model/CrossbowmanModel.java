package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.CrossbowmanEntity;
import com.projectreddog.tsrts.entities.CrossbowmanEntity.ArmState;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.math.MathHelper;

public class CrossbowmanModel extends BipedModel<CrossbowmanEntity> {

	public CrossbowmanModel() {
		super(0, 0.0F, 64, 64);
	}

	public CrossbowmanModel(float size) {
		super(size, 0.0F, 64, 32);
	}

	public void setRotationAngles(CrossbowmanEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		ArmState armState = entityIn.getArmState();
		if (armState == ArmState.HOLDING) {
			this.bipedRightArm.rotateAngleY = -0.3F + this.bipedHead.rotateAngleY;

			this.bipedLeftArm.rotateAngleY = 0.6F + this.bipedHead.rotateAngleY;
			this.bipedRightArm.rotateAngleX = (-(float) Math.PI / 2F) + this.bipedHead.rotateAngleX + 0.1F;
			this.bipedLeftArm.rotateAngleX = -1.5F + this.bipedHead.rotateAngleX;
		} else if (armState == armState.CHARGING) {
			this.bipedRightArm.rotateAngleY = -0.8F;
			this.bipedRightArm.rotateAngleX = -0.97079635F;
			this.bipedLeftArm.rotateAngleX = -0.97079635F;
			float f2 = MathHelper.clamp((float) entityIn.getItemInUseMaxCount(), 0.0F, 25.0F);
			this.bipedLeftArm.rotateAngleY = MathHelper.lerp(f2 / 25.0F, 0.4F, 0.85F);
			this.bipedLeftArm.rotateAngleX = MathHelper.lerp(f2 / 25.0F, this.bipedLeftArm.rotateAngleX, (-(float) Math.PI / 2F));
		}

	}
}
