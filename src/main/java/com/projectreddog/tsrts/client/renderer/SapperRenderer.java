package com.projectreddog.tsrts.client.renderer;

import javax.annotation.Nullable;

import com.projectreddog.tsrts.client.model.SapperModel;
import com.projectreddog.tsrts.client.renderer.layer.BackpackLayer;
import com.projectreddog.tsrts.entities.SapperEntity;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SapperRenderer extends BipedRenderer<SapperEntity, SapperModel> {

	private static ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/minion_yellow.png");
	private static ResourceLocation TEXTURE_YELLOW = new ResourceLocation(Reference.MODID, "textures/entity/minion_yellow.png");
	private static ResourceLocation TEXTURE_BLUE = new ResourceLocation(Reference.MODID, "textures/entity/minion_blue.png");
	private static ResourceLocation TEXTURE_GREEN = new ResourceLocation(Reference.MODID, "textures/entity/minion_green.png");
	private static ResourceLocation TEXTURE_RED = new ResourceLocation(Reference.MODID, "textures/entity/minion_red.png");

	public SapperRenderer(EntityRendererManager manager) {
		super(manager, new SapperModel(), .5f);

		this.addLayer(new BackpackLayer(this));

	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(SapperEntity entity) {
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
