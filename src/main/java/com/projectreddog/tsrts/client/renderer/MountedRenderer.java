package com.projectreddog.tsrts.client.renderer;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.client.model.MountedMinionModel;
import com.projectreddog.tsrts.client.model.MountedModel;
import com.projectreddog.tsrts.entities.MountedEntity;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class MountedRenderer extends EntityRenderer<MountedEntity> {

	private static ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_YELLOW = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_BLUE = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_GREEN = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_RED = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");

	private static ResourceLocation RIDER_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/minion_yellow.png");
	private static ResourceLocation RIDER_TEXTURE_YELLOW = new ResourceLocation(Reference.MODID, "textures/entity/minion_yellow.png");
	private static ResourceLocation RIDER_TEXTURE_BLUE = new ResourceLocation(Reference.MODID, "textures/entity/minion_blue.png");
	private static ResourceLocation RIDER_TEXTURE_GREEN = new ResourceLocation(Reference.MODID, "textures/entity/minion_green.png");
	private static ResourceLocation RIDER_TEXTURE_RED = new ResourceLocation(Reference.MODID, "textures/entity/minion_red.png");

	public MountedModel mountedModel = new MountedModel();
	public MountedMinionModel riderMinionModel = new MountedMinionModel();

	public MountedRenderer(EntityRendererManager manager) {
		super(manager);

	}

	@Override
	public void doRender(MountedEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		float yaw = MathHelper.func_219805_h(partialTicks, entity.prevRenderYawOffset, entity.renderYawOffset);
		this.bindEntityTexture(entity);

		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x, (float) y, (float) z);
		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		GlStateManager.rotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);

		mountedModel.render(entity, 1, 1, 1, 1, 1, .1f);
		GlStateManager.popMatrix();

		this.bindRiderEntityTexture(entity);

		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x, (float) y, (float) z);
		GlStateManager.translatef(0, 2.2f, 0);
		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		GlStateManager.scalef(.055F, .055F, .055F);
		GlStateManager.rotatef(180, 0, 1, 0);
		GlStateManager.rotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);

		riderMinionModel.render(entity, 1, 1, 1, 1, 1, 1);
		GlStateManager.popMatrix();

		// body of characater

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

	@Nullable
	protected ResourceLocation getRiderTexture(MountedEntity entity) {

		if (entity.getTeam() != null) {
			if (entity.getTeam().getName().equals("red")) {
				return RIDER_TEXTURE_RED;
			} else if (entity.getTeam().getName().equals("blue")) {
				return RIDER_TEXTURE_BLUE;
			} else if (entity.getTeam().getName().equals("green")) {
				return RIDER_TEXTURE_GREEN;
			} else if (entity.getTeam().getName().equals("yellow")) {
				return RIDER_TEXTURE_YELLOW;
			} else {
				return RIDER_TEXTURE;
			}
		} else {
			return RIDER_TEXTURE;
		}
	}

	protected boolean bindRiderEntityTexture(MountedEntity entity) {
		ResourceLocation resourcelocation = this.getRiderTexture(entity);
		if (resourcelocation == null) {
			return false;
		} else {
			this.bindTexture(resourcelocation);
			return true;
		}
	}
}
