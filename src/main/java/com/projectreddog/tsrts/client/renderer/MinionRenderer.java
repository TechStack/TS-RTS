package com.projectreddog.tsrts.client.renderer;

import javax.annotation.Nullable;

import com.projectreddog.tsrts.client.model.MinionModel;
import com.projectreddog.tsrts.entities.MinionEntity;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MinionRenderer extends MobRenderer<MinionEntity, MinionModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/minion.png");

	public MinionRenderer(EntityRendererManager manager) {
		super(manager, new MinionModel(), .5f);

	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(MinionEntity entity) {
		return ((AbstractClientPlayerEntity) entity.getPlayer()).getLocationSkin();
	}

}
