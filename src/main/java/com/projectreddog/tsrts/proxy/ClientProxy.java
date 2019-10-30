package com.projectreddog.tsrts.proxy;

import com.projectreddog.tsrts.client.gui.GarrisonScreen;
import com.projectreddog.tsrts.client.renderer.MinionRenderer;
import com.projectreddog.tsrts.client.renderer.overlay.RenderOverlay;
import com.projectreddog.tsrts.entities.MinionEntity;
import com.projectreddog.tsrts.hanlder.ClientEvents;
import com.projectreddog.tsrts.init.ModContainers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}

	@Override
	public void init() {
		// Entity Rendereers
		RenderingRegistry.registerEntityRenderingHandler(MinionEntity.class, MinionRenderer::new);

		// Client Gui
		ScreenManager.registerFactory(ModContainers.GARRISON_CONTAINER, GarrisonScreen::new);
		MinecraftForge.EVENT_BUS.register(new RenderOverlay());
		KeyBindings();
	}

	public void KeyBindings() {
		ClientRegistry.registerKeyBinding(ClientEvents.controlGroup1);
		ClientRegistry.registerKeyBinding(ClientEvents.controlGroup2);
		ClientRegistry.registerKeyBinding(ClientEvents.controlGroup3);
		ClientRegistry.registerKeyBinding(ClientEvents.controlGroup4);
		ClientRegistry.registerKeyBinding(ClientEvents.controlGroup5);
		ClientRegistry.registerKeyBinding(ClientEvents.controlGroup6);
		ClientRegistry.registerKeyBinding(ClientEvents.controlGroup7);
		ClientRegistry.registerKeyBinding(ClientEvents.controlGroup8);
		ClientRegistry.registerKeyBinding(ClientEvents.controlGroup9);

	}

	@Override
	public boolean isServer() {
		// TODO Auto-generated method stub
		return false;
	}

}
