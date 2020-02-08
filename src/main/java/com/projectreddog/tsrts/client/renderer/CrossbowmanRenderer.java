package com.projectreddog.tsrts.client.renderer;

import javax.annotation.Nullable;

import com.projectreddog.tsrts.client.model.CrossbowmanModel;
import com.projectreddog.tsrts.entities.CrossbowmanEntity;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;

public class CrossbowmanRenderer extends BipedRenderer<CrossbowmanEntity, CrossbowmanModel> {

	private static ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/longbowmen_yellow.png");
	private static ResourceLocation TEXTURE_YELLOW = new ResourceLocation(Reference.MODID, "textures/entity/longbowmen_yellow.png");
	private static ResourceLocation TEXTURE_BLUE = new ResourceLocation(Reference.MODID, "textures/entity/longbowmen_blue.png");
	private static ResourceLocation TEXTURE_GREEN = new ResourceLocation(Reference.MODID, "textures/entity/longbowmen_green.png");
	private static ResourceLocation TEXTURE_RED = new ResourceLocation(Reference.MODID, "textures/entity/longbowmen_red.png");

	public CrossbowmanRenderer(EntityRendererManager manager) {
		super(manager, new CrossbowmanModel(), .5f);

		this.addLayer(new BipedArmorLayer<>(this, new CrossbowmanModel(1f), new CrossbowmanModel(1f)));

	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(CrossbowmanEntity entity) {
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
