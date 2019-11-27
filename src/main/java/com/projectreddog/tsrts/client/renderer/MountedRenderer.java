package com.projectreddog.tsrts.client.renderer;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.client.model.MountedModel;
import com.projectreddog.tsrts.entities.MountedEntity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class MountedRenderer extends EntityRenderer<MountedEntity> {

	private static ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_YELLOW = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_BLUE = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_GREEN = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_RED = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	public MountedModel model = new MountedModel();

	public MountedRenderer(EntityRendererManager manager) {
		super(manager);

	}

	@Override
	public void doRender(MountedEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		this.bindEntityTexture(entity);
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x, (float) y, (float) z);
		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);

		model.render(entity, 1, 1, 1, 1, 1, .1f);
		GlStateManager.popMatrix();

	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(MountedEntity entity) {

		if (entity.getTeam() != null) {
			if (entity.getTeam().getName().equals("red")) {
				return TEXTURE_RED;
			} else if (entity.getTeam().getName().equals("blue")) {
				return TEXTURE_BLUE;
			} else if (entity.getTeam().getName().equals("green")) {
				return TEXTURE_GREEN;
			} else if (entity.getTeam().getName().equals("yellow")) {
				return TEXTURE_YELLOW;
			} else {
				return TEXTURE;
			}
		} else {
			return TEXTURE;
		}
	}

}
