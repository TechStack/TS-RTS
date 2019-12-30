package com.projectreddog.tsrts.client.renderer;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.client.model.TrebuchetModel;
import com.projectreddog.tsrts.entities.TrebuchetEntity;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class TrebuchetRenderer<T extends TrebuchetEntity> extends EntityRenderer<TrebuchetEntity> {

	private static ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/oak_planks.png");
	private static ResourceLocation TEXTURE_YELLOW = new ResourceLocation(Reference.MODID, "textures/entity/oak_planks.png");
	private static ResourceLocation TEXTURE_BLUE = new ResourceLocation(Reference.MODID, "textures/entity/oak_planks.png");
	private static ResourceLocation TEXTURE_GREEN = new ResourceLocation(Reference.MODID, "textures/entity/oak_planks.png");
	private static ResourceLocation TEXTURE_RED = new ResourceLocation(Reference.MODID, "textures/entity/oak_planks.png");

	public TrebuchetModel trebuchetModel = new TrebuchetModel();

	public TrebuchetRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(TrebuchetEntity entity) {
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

	@Override
	public void doRender(TrebuchetEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();

		GlStateManager.translatef((float) x, (float) y, (float) z);

		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		GlStateManager.rotatef(entity.rotationYaw - 180, 0.0F, 1.0F, 0.0F);

		bindEntityTexture(entity);
		GlStateManager.translatef((float) 0, (float) -3, (float) 0);

		trebuchetModel.render(entity, 1, 1, 1, 1, 1, .125f);

		GlStateManager.popMatrix();

	}

}
