package com.projectreddog.tsrts.proxy;

import com.projectreddog.tsrts.client.gui.BasicScreen;
import com.projectreddog.tsrts.client.gui.DefensiveBuildingsScreen;
import com.projectreddog.tsrts.client.gui.EcoBuildingsScreen;
import com.projectreddog.tsrts.client.gui.LobbyScreen;
import com.projectreddog.tsrts.client.gui.MainMenuScreen;
import com.projectreddog.tsrts.client.gui.ResearchScreen;
import com.projectreddog.tsrts.client.gui.TroopBuildingsScreen;
import com.projectreddog.tsrts.client.gui.UnitRecruitmentScreen;
import com.projectreddog.tsrts.client.renderer.ArcherMinionRenderer;
import com.projectreddog.tsrts.client.renderer.MinionRenderer;
import com.projectreddog.tsrts.client.renderer.MountedRenderer;
import com.projectreddog.tsrts.client.renderer.PikemanRenderer;
import com.projectreddog.tsrts.client.renderer.TargetRenderer;
import com.projectreddog.tsrts.client.renderer.TrebuchetRenderer;
import com.projectreddog.tsrts.client.renderer.overlay.RenderOverlay;
import com.projectreddog.tsrts.entities.ArcherMinionEntity;
import com.projectreddog.tsrts.entities.MinionEntity;
import com.projectreddog.tsrts.entities.MountedEntity;
import com.projectreddog.tsrts.entities.PikemanEntity;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.TrebuchetEntity;
import com.projectreddog.tsrts.handler.ClientEvents;
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

		RenderingRegistry.registerEntityRenderingHandler(ArcherMinionEntity.class, ArcherMinionRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(TargetEntity.class, TargetRenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(MountedEntity.class, MountedRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(PikemanEntity.class, PikemanRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(TrebuchetEntity.class, TrebuchetRenderer::new);

		// Client Gui
		ScreenManager.registerFactory(ModContainers.BASIC_CONTAINER, BasicScreen::new);
		ScreenManager.registerFactory(ModContainers.DEFENSIVE_BUILDINGS_CONTAINER, DefensiveBuildingsScreen::new);
		ScreenManager.registerFactory(ModContainers.LOBBY_CONTAINER, LobbyScreen::new);
		ScreenManager.registerFactory(ModContainers.MAIN_MENU_CONTAINER, MainMenuScreen::new);
		ScreenManager.registerFactory(ModContainers.ECO_BUILDINGS_CONTAINER, EcoBuildingsScreen::new);
		ScreenManager.registerFactory(ModContainers.TROOP_BUILDINGS_CONTAINER, TroopBuildingsScreen::new);
		ScreenManager.registerFactory(ModContainers.UNIT_RECRUITMENT_CONTAINER, UnitRecruitmentScreen::new);
		ScreenManager.registerFactory(ModContainers.RESEARCH_CONTAINER, ResearchScreen::new);

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
		ClientRegistry.registerKeyBinding(ClientEvents.controlModifier);
		ClientRegistry.registerKeyBinding(ClientEvents.deselectAll);
		ClientRegistry.registerKeyBinding(ClientEvents.areaSelect);
		ClientRegistry.registerKeyBinding(ClientEvents.boxSelect);
		ClientRegistry.registerKeyBinding(ClientEvents.mainGuiOpen);

	}

	@Override
	public boolean isServer() {
		return false;
	}

}
