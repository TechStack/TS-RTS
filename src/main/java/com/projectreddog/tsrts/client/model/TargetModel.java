package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.TargetEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class TargetModel extends EntityModel<TargetEntity> {
	private final RendererModel box;

	public TargetModel() {
		this.textureWidth = 16;
		this.textureHeight = 16;
		box = new RendererModel(this, 0, 0);
		box.addBox(-4, -4, -4, 8, 8, 8, 32);

		box.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	@Override
	public void render(TargetEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		box.render(scale);
	}

}