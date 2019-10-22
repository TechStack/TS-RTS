package com.projectreddog.tsrts.proxy;

import com.projectreddog.tsrts.client.renderer.MinionRenderer;
import com.projectreddog.tsrts.entities.MinionEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}

	@Override
	public void init() {
		RenderingRegistry.registerEntityRenderingHandler(MinionEntity.class, MinionRenderer::new);
	}

}
