package com.projectreddog.tsrts.client.renderer;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.client.model.MountedMinionModel;
import com.projectreddog.tsrts.client.model.MountedModel;
import com.projectreddog.tsrts.entities.MountedEntity;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class MountedRenderer extends EntityRenderer<MountedEntity> {

	private static ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_YELLOW = new ResourceLocation("minecraft", "textures/entity/horse/horse_white.png");
	private static ResourceLocation TEXTURE_BLUE = new ResourceLocation("minecraft", "textures/entity/horse/horse_gray.png");
	private static ResourceLocation TEXTURE_GREEN = new ResourceLocation("minecraft", "textures/entity/horse/horse_black.png");
	private static ResourceLocation TEXTURE_RED = new ResourceLocation("minecraft", "textures/entity/horse/horse_brown.png");

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

		float f = MathHelper.func_219805_h(partialTicks, entity.prevRenderYawOffset, entity.renderYawOffset);
		float f1 = MathHelper.func_219805_h(partialTicks, entity.prevRotationYawHead, entity.rotationYawHead);
		float f2 = f1 - f;
		if (true) {
			f2 = f1 - f;
			float f3 = MathHelper.wrapDegrees(f2);
//			if (f3 < -85.0F) {
//				f3 = -85.0F;
//			}
//
//			if (f3 >= 85.0F) {
//				f3 = 85.0F;
//			}

			f = f1 - f3;
			if (f3 * f3 > 2500.0F) {
				f += f3 * 0.2F;
			}

			f2 = f1 - f;
		}
		this.bindEntityTexture(entity);

		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x, (float) y, (float) z);
		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		GlStateManager.rotatef(entity.rotationYaw - 180, 0.0F, 1.0F, 0.0F);

		float f5 = MathHelper.lerp(partialTicks, entity.prevLimbSwingAmount, entity.limbSwingAmount);
		float f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
		if (entity.isChild()) {
			f6 *= 3.0F;
		}

		if (f5 > 1.0F) {
			f5 = 1.0F;
		}
		if (entity.deathTime > 0) {
			float deadamt = ((float) entity.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
			deadamt = MathHelper.sqrt(deadamt);
			if (deadamt > 1.0F) {
				deadamt = 1.0F;
			}

			GlStateManager.rotatef(deadamt * 90f, 0, 0.0F, 1F);

		}

		if (this.renderOutlines) {
			boolean flag = this.setScoreTeamColor(entity);
			GlStateManager.enableColorMaterial();
			GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
			mountedModel.render(entity, f6, f5, 1, 1, 1, .1f, partialTicks);

			GlStateManager.tearDownSolidRenderingTextureCombine();
			GlStateManager.disableColorMaterial();
			if (flag) {
				this.unsetScoreTeamColor();
			}
		} else {
			mountedModel.render(entity, f6, f5, 1, 1, 1, .1f, partialTicks);

		}

		if (((MountedEntity) entity).hurtTime > 0) {

			GlStateManager.depthFunc(514);
			GlStateManager.disableTexture();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.color4f(1.0F, 0.0F, 0.0F, 0.5F);
			mountedModel.render(entity, f6, f5, 1, 1, 1, .1f, partialTicks);
			GlStateManager.enableTexture();
			GlStateManager.disableBlend();
			GlStateManager.depthFunc(515);
		}
		GlStateManager.popMatrix();

		this.bindRiderEntityTexture(entity);

		GlStateManager.pushMatrix();

		GlStateManager.translatef((float) x, (float) y, (float) z);
		GlStateManager.translatef(0, 2.08f, 0);
		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		GlStateManager.scalef(.055F, .055F, .055F);
		// GlStateManager.rotatef(180, 0, 1, 0);
		GlStateManager.rotatef(entity.rotationYaw - 180, 0.0F, 1.0F, 0.0F);

//calculate head look direction 
		float headYaw = f1 - f;

		float headPitch = MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch);
//render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleIn);
		if (entity.deathTime == 0) {

			if (this.renderOutlines) {
				boolean flag = this.setScoreTeamColor(entity);
				GlStateManager.enableColorMaterial();
				GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
				riderMinionModel.render(entity, 1, 1, 1, headYaw, headPitch, 1);
				// render held item
				if (entity.getHeldItemMainhand() != null) {
					GlStateManager.scalef(18.181818181818181818181818181818F, 18.181818181818181818181818181818F, 18.181818181818181818181818181818F);
					GlStateManager.translatef(-.13f, .725f, -.3f);
					GlStateManager.rotatef(15f, 1.0F, 0.0F, 0.0F);

					Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, entity.getHeldItemMainhand(), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false);

				}
				GlStateManager.tearDownSolidRenderingTextureCombine();
				GlStateManager.disableColorMaterial();
				if (flag) {
					this.unsetScoreTeamColor();
				}
			} else {
				riderMinionModel.render(entity, 1, 1, 1, headYaw, headPitch, 1);
				// render held item
				if (entity.getHeldItemMainhand() != null) {
					GlStateManager.scalef(18.181818181818181818181818181818F, 18.181818181818181818181818181818F, 18.181818181818181818181818181818F);
					GlStateManager.translatef(-.13f, .725f, -.3f);
					GlStateManager.rotatef(15f, 1.0F, 0.0F, 0.0F);

					Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, entity.getHeldItemMainhand(), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false);

				}
			}

		}

		if (((MountedEntity) entity).hurtTime > 0) {
			GlStateManager.depthFunc(514);
			GlStateManager.disableTexture();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.color4f(1.0F, 0.0F, 0.0F, 0.5F);
			riderMinionModel.render((MountedEntity) entity, 1, 1, 1, 1, 1, 1);

			GlStateManager.enableTexture();
			GlStateManager.disableBlend();
			GlStateManager.depthFunc(515);
			// render held item

		}

		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		GlStateManager.popMatrix();

		// body of character

	}

	protected boolean setScoreTeamColor(MountedEntity entityLivingBaseIn) {
		GlStateManager.disableLighting();
		GlStateManager.activeTexture(GLX.GL_TEXTURE1);
		GlStateManager.disableTexture();
		GlStateManager.activeTexture(GLX.GL_TEXTURE0);
		return true;
	}

	protected void unsetScoreTeamColor() {
		GlStateManager.enableLighting();
		GlStateManager.activeTexture(GLX.GL_TEXTURE1);
		GlStateManager.enableTexture();
		GlStateManager.activeTexture(GLX.GL_TEXTURE0);
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
