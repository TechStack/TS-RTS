package com.projectreddog.tsrts.client.model;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.entities.MountedEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class MountedModel extends EntityModel<MountedEntity> {

	RendererModel body;

	RendererModel Head;

	RendererModel Box3;
	RendererModel backLeftLeg;

	RendererModel backRightLeg;
	RendererModel frontLeftLeg;
	RendererModel frontRightLeg;

	RendererModel tail;

	RendererModel[] Box9;

	RendererModel[] Box10;

	float scaleFactor = .1f;

	public MountedModel() {
		super();

		this.textureWidth = 64;
		this.textureHeight = 64;
		this.body = new RendererModel(this, 0, 32);
		this.body.addBox(-5.0F, -8.0F, -17.0F, 10, 10, 22, 0.05F);
		this.body.setRotationPoint(0.0F, 11.0F, 5.0F);
		this.Head = new RendererModel(this, 0, 35);
		this.Head.addBox(-2.05F, -6.0F, -2.0F, 4, 12, 7);
		this.Head.rotateAngleX = ((float) Math.PI / 6F);
		RendererModel renderermodel = new RendererModel(this, 0, 13);
		renderermodel.addBox(-3.0F, -11.0F, -2.0F, 6, 5, 7, scaleFactor);
		RendererModel renderermodel1 = new RendererModel(this, 56, 36);
		renderermodel1.addBox(-1.0F, -11.0F, 5.01F, 2, 16, 2, scaleFactor);
		RendererModel renderermodel2 = new RendererModel(this, 0, 25);
		renderermodel2.addBox(-2.0F, -11.0F, -7.0F, 4, 5, 5, scaleFactor);
		this.Head.addChild(renderermodel);
		this.Head.addChild(renderermodel1);
		this.Head.addChild(renderermodel2);
		this.addEars(this.Head);
		this.backLeftLeg = new RendererModel(this, 48, 21);
		this.backLeftLeg.mirror = true;
		this.backLeftLeg.addBox(-3.0F, -1.01F, -1.0F, 4, 11, 4, scaleFactor);
		this.backLeftLeg.setRotationPoint(4.0F, 14.0F, 7.0F);
		this.backRightLeg = new RendererModel(this, 48, 21);
		this.backRightLeg.addBox(-1.0F, -1.01F, -1.0F, 4, 11, 4, scaleFactor);
		this.backRightLeg.setRotationPoint(-4.0F, 14.0F, 7.0F);
		this.frontLeftLeg = new RendererModel(this, 48, 21);
		this.frontLeftLeg.mirror = true;
		this.frontLeftLeg.addBox(-3.0F, -1.01F, -1.9F, 4, 11, 4, scaleFactor);
		this.frontLeftLeg.setRotationPoint(4.0F, 6.0F, -12.0F);
		this.frontRightLeg = new RendererModel(this, 48, 21);
		this.frontRightLeg.addBox(-1.0F, -1.01F, -1.9F, 4, 11, 4, scaleFactor);
		this.frontRightLeg.setRotationPoint(-4.0F, 6.0F, -12.0F);
		this.tail = new RendererModel(this, 42, 36);
		this.tail.addBox(-1.5F, 0.0F, 0.0F, 3, 14, 4, scaleFactor);
		this.tail.setRotationPoint(0.0F, -5.0F, 2.0F);
		this.tail.rotateAngleX = ((float) Math.PI / 6F);
		this.body.addChild(this.tail);
		RendererModel renderermodel3 = new RendererModel(this, 26, 0);
		renderermodel3.addBox(-5.0F, -8.0F, -9.0F, 10, 9, 9, 0.5F);
		this.body.addChild(renderermodel3);
		RendererModel renderermodel4 = new RendererModel(this, 29, 5);
		renderermodel4.addBox(2.0F, -9.0F, -6.0F, 1, 2, 2, scaleFactor);
		this.Head.addChild(renderermodel4);
		RendererModel renderermodel5 = new RendererModel(this, 29, 5);
		renderermodel5.addBox(-3.0F, -9.0F, -6.0F, 1, 2, 2, scaleFactor);
		this.Head.addChild(renderermodel5);
		RendererModel renderermodel6 = new RendererModel(this, 32, 2);
		renderermodel6.addBox(3.1F, -6.0F, -8.0F, 0, 3, 16, scaleFactor);
		renderermodel6.rotateAngleX = (-(float) Math.PI / 6F);
		this.Head.addChild(renderermodel6);
		RendererModel renderermodel7 = new RendererModel(this, 32, 2);
		renderermodel7.addBox(-3.1F, -6.0F, -8.0F, 0, 3, 16, scaleFactor);
		renderermodel7.rotateAngleX = (-(float) Math.PI / 6F);
		this.Head.addChild(renderermodel7);
		RendererModel renderermodel8 = new RendererModel(this, 1, 1);
		renderermodel8.addBox(-3.0F, -11.0F, -1.9F, 6, 5, 6, 0.2F);
		this.Head.addChild(renderermodel8);
		RendererModel renderermodel9 = new RendererModel(this, 19, 0);
		renderermodel9.addBox(-2.0F, -11.0F, -4.0F, 4, 5, 2, 0.2F);
		this.Head.addChild(renderermodel9);
		this.Box9 = new RendererModel[] { renderermodel3, renderermodel4, renderermodel5, renderermodel8, renderermodel9 };
		this.Box10 = new RendererModel[] { renderermodel6, renderermodel7 };

	}

	protected void addEars(RendererModel p_199047_1_) {
		RendererModel renderermodel = new RendererModel(this, 19, 16);
		renderermodel.addBox(0.55F, -13.0F, 4.0F, 2, 3, 1, -0.001F);
		RendererModel renderermodel1 = new RendererModel(this, 19, 16);
		renderermodel1.addBox(-2.55F, -13.0F, 4.0F, 2, 3, 1, -0.001F);
		p_199047_1_.addChild(renderermodel);
		p_199047_1_.addChild(renderermodel1);
	}

	public void render(MountedEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, float partialTicks) {
		// always child so SCALE!

		setupAnim(entityIn, limbSwing, limbSwingAmount, partialTicks);
		float f = entityIn.getRenderScale() * .75f;

		GlStateManager.pushMatrix();
		GlStateManager.translatef(0.0F, -1.8F, 0.0F);
		// GlStateManager.rotatef(180F, 0, 1, 0);

		GlStateManager.pushMatrix();
		GlStateManager.scalef(f, 0.5F + f * 0.5F, f);
		GlStateManager.translatef(0.0F, 0.95F * (1.0F - f), 0.0F);

		GlStateManager.translatef(0.0F, -.65F * (1.0F - f), 0.0F);

		this.backLeftLeg.render(scale);
		this.backRightLeg.render(scale); // BACK RIGHT LEG

		GlStateManager.translatef(0.0F, 0, 0.2F);

		this.frontLeftLeg.render(scale);// front left leg
		this.frontRightLeg.render(scale); // front right leg

		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.scalef(f, f, f);
		GlStateManager.translatef(0.0F, 2.3F * (1.0F - f), 0.0F);

		this.body.render(scale); // body

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		float f1 = f + 0.1F * f;
		GlStateManager.scalef(f1, f1, f1);
		GlStateManager.translatef(0.0F, 1.8F * (1.0F - f1), 0F);

		this.Head.render(scale);

		GlStateManager.popMatrix();

		GlStateManager.popMatrix();

	}

	private float possibleHeadCalc(float p_217126_1_, float p_217126_2_, float p_217126_3_) {
		float f;
		for (f = p_217126_2_ - p_217126_1_; f < -180.0F; f += 360.0F) {
			;
		}

		while (f >= 180.0F) {
			f -= 360.0F;
		}

		return p_217126_1_ + p_217126_3_ * f;
	}

	public void setupAnim(MountedEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		float f = this.possibleHeadCalc(entityIn.prevRenderYawOffset, entityIn.renderYawOffset, partialTick);
		float f1 = this.possibleHeadCalc(entityIn.prevRotationYawHead, entityIn.rotationYawHead, partialTick);
		float f2 = MathHelper.lerp(partialTick, entityIn.prevRotationPitch, entityIn.rotationPitch);
		float f3 = f1 - f;
		float f4 = f2 * ((float) Math.PI / 180F);
		if (f3 > 20.0F) {
			f3 = 20.0F;
		}

		if (f3 < -20.0F) {
			f3 = -20.0F;
		}

		if (limbSwingAmount > 0.2F) {
			f4 += MathHelper.cos(limbSwing * 0.4F) * 0.15F * limbSwingAmount;
		}

		float f5 = 0;
		float f6 = 0;
		float f7 = 1.0F - f6;
		float f8 = 0;
		boolean flag = entityIn.tailCounter != 0;
		float f9 = (float) entityIn.ticksExisted + partialTick;
		this.Head.rotationPointY = 4.0F;
		this.Head.rotationPointZ = -12.0F;
		this.body.rotateAngleX = 0.0F;
		this.Head.rotateAngleX = ((float) Math.PI / 6F) + f4;
		this.Head.rotateAngleY = f3 * ((float) Math.PI / 180F);
		float f10 = entityIn.isInWater() ? 0.2F : 1.0F;
		float f11 = MathHelper.cos(f10 * limbSwing * 0.6662F + (float) Math.PI);
		float f12 = f11 * 0.8F * limbSwingAmount;
		float f13 = (1.0F - Math.max(f6, f5)) * (((float) Math.PI / 6F) + f4 + f8 * MathHelper.sin(f9) * 0.05F);
		this.Head.rotateAngleX = f6 * (0.2617994F + f4) + f5 * (2.1816616F + MathHelper.sin(f9) * 0.05F) + f13;
		this.Head.rotateAngleY = f6 * f3 * ((float) Math.PI / 180F) + (1.0F - Math.max(f6, f5)) * this.Head.rotateAngleY;
		this.Head.rotationPointY = f6 * -4.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.Head.rotationPointY;
		this.Head.rotationPointZ = f6 * -4.0F + f5 * -12.0F + (1.0F - Math.max(f6, f5)) * this.Head.rotationPointZ;
		this.body.rotateAngleX = f6 * (-(float) Math.PI / 4F) + f7 * this.body.rotateAngleX;
		float f14 = 0.2617994F * f6;
		float f15 = MathHelper.cos(f9 * 0.6F + (float) Math.PI);
		this.frontLeftLeg.rotationPointY = 2.0F * f6 + 14.0F * f7;
		this.frontLeftLeg.rotationPointZ = -6.0F * f6 - 10.0F * f7;
		this.frontRightLeg.rotationPointY = this.frontLeftLeg.rotationPointY;
		this.frontRightLeg.rotationPointZ = this.frontLeftLeg.rotationPointZ;
		float f16 = ((-(float) Math.PI / 3F) + f15) * f6 + f12 * f7;
		float f17 = ((-(float) Math.PI / 3F) - f15) * f6 - f12 * f7;
		this.backLeftLeg.rotateAngleX = f14 - f11 * 0.5F * limbSwingAmount * f7;
		this.backRightLeg.rotateAngleX = f14 + f11 * 0.5F * limbSwingAmount * f7;
		this.frontLeftLeg.rotateAngleX = f16;
		this.frontRightLeg.rotateAngleX = f17;
		this.tail.rotateAngleX = ((float) Math.PI / 6F) + limbSwingAmount * 0.75F;
		this.tail.rotationPointY = -5.0F + limbSwingAmount;
		this.tail.rotationPointZ = 2.0F + limbSwingAmount * 2.0F;
		if (flag) {
			this.tail.rotateAngleY = MathHelper.cos(f9 * 0.7F);
		} else {
			this.tail.rotateAngleY = 0.0F;
		}

	}
}
