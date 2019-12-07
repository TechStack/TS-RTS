package com.projectreddog.tsrts.client.renderer;

import javax.annotation.Nullable;

import com.projectreddog.tsrts.client.model.PikemanModel;
import com.projectreddog.tsrts.entities.PikemanEntity;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;

public class PikemanRenderer extends BipedRenderer<PikemanEntity, PikemanModel> {

	private static ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/minion_yellow.png");
	private static ResourceLocation TEXTURE_YELLOW = new ResourceLocation(Reference.MODID, "textures/entity/minion_yellow.png");
	private static ResourceLocation TEXTURE_BLUE = new ResourceLocation(Reference.MODID, "textures/entity/minion_blue.png");
	private static ResourceLocation TEXTURE_GREEN = new ResourceLocation(Reference.MODID, "textures/entity/minion_green.png");
	private static ResourceLocation TEXTURE_RED = new ResourceLocation(Reference.MODID, "textures/entity/minion_red.png");

	public PikemanRenderer(EntityRendererManager manager) {
		super(manager, new PikemanModel(), .5f);

		this.addLayer(new BipedArmorLayer<>(this, new PikemanModel(1f), new PikemanModel(1f)));

	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(PikemanEntity entity) {
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
