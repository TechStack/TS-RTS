package com.projectreddog.tsrts.client.renderer.layer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.client.model.SapperModel;
import com.projectreddog.tsrts.entities.SapperEntity;
import com.projectreddog.tsrts.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BackpackLayer extends LayerRenderer<SapperEntity, SapperModel> {
	public BackpackLayer(IEntityRenderer<SapperEntity, SapperModel> p_i50949_1_) {
		super(p_i50949_1_);
	}

	@Override
	public void render(SapperEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {

		GlStateManager.pushMatrix();
		GlStateManager.scalef(-2, -2, -2);
		GlStateManager.translatef(0, -.00f, .07f);

		Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entityIn, new ItemStack(ModItems.CREEPERBAGITEM), ItemCameraTransforms.TransformType.NONE, false);
		GlStateManager.popMatrix();
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}
}
