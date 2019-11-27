package com.projectreddog.tsrts.client.model;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.entities.MountedEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class MountedModel extends EntityModel<MountedEntity> {

	RendererModel Box1;

	RendererModel Head;

	RendererModel Box3;
	RendererModel backLeftLeg;

	RendererModel Box5;
	RendererModel Box6;
	RendererModel Box7;

	RendererModel Box8;

	RendererModel[] Box9;

	RendererModel[] Box10;

	float scaleFactor = .1f;

	public MountedModel() {
		super();

		this.textureWidth = 64;
		this.textureHeight = 64;
		this.Box1 = new RendererModel(this, 0, 32);
		this.Box1.addBox(-5.0F, -8.0F, -17.0F, 10, 10, 22, 0.05F);
		this.Box1.setRotationPoint(0.0F, 11.0F, 5.0F);
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
		this.Box5 = new RendererModel(this, 48, 21);
		this.Box5.addBox(-1.0F, -1.01F, -1.0F, 4, 11, 4, scaleFactor);
		this.Box5.setRotationPoint(-4.0F, 14.0F, 7.0F);
		this.Box6 = new RendererModel(this, 48, 21);
		this.Box6.mirror = true;
		this.Box6.addBox(-3.0F, -1.01F, -1.9F, 4, 11, 4, scaleFactor);
		this.Box6.setRotationPoint(4.0F, 6.0F, -12.0F);
		this.Box7 = new RendererModel(this, 48, 21);
		this.Box7.addBox(-1.0F, -1.01F, -1.9F, 4, 11, 4, scaleFactor);
		this.Box7.setRotationPoint(-4.0F, 6.0F, -12.0F);
		this.Box8 = new RendererModel(this, 42, 36);
		this.Box8.addBox(-1.5F, 0.0F, 0.0F, 3, 14, 4, scaleFactor);
		this.Box8.setRotationPoint(0.0F, -5.0F, 2.0F);
		this.Box8.rotateAngleX = ((float) Math.PI / 6F);
		this.Box1.addChild(this.Box8);
		RendererModel renderermodel3 = new RendererModel(this, 26, 0);
		renderermodel3.addBox(-5.0F, -8.0F, -9.0F, 10, 9, 9, 0.5F);
		this.Box1.addChild(renderermodel3);
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

	@Override
	public void render(MountedEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		// always child so SCALE!
		float f = entityIn.getRenderScale() * .75f;

		GlStateManager.pushMatrix();
		GlStateManager.translatef(0.0F, -1.8F, 0.0F);
		GlStateManager.rotatef(180F, 0, 1, 0);

		GlStateManager.pushMatrix();
		GlStateManager.scalef(f, 0.5F + f * 0.5F, f);
		GlStateManager.translatef(0.0F, 0.95F * (1.0F - f), 0.0F);

		GlStateManager.translatef(0.0F, -.65F * (1.0F - f), 0.0F);

		this.backLeftLeg.render(scale);
		this.Box5.render(scale); // BACK RIGHT LEG

		GlStateManager.translatef(0.0F, 1.3F * (1.0F - f), 0.2F);

		this.Box6.render(scale);// front left leg
		this.Box7.render(scale); // front right leg

		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.scalef(f, f, f);
		GlStateManager.translatef(0.0F, 2.3F * (1.0F - f), 0.0F);

		this.Box1.render(scale); // body

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		float f1 = f + 0.1F * f;
		GlStateManager.scalef(f1, f1, f1);
		GlStateManager.translatef(0.0F, 2.9F * (1.0F - f1), -1.2F);

		this.Head.render(scale);

		GlStateManager.popMatrix();

		GlStateManager.popMatrix();

	}
}
