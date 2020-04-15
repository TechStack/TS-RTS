package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.containers.BasicContainer;
import com.projectreddog.tsrts.containers.DefensiveBuildingsContainer;
import com.projectreddog.tsrts.containers.EcoBuildingsContainer;
import com.projectreddog.tsrts.containers.LobbyContainer;
import com.projectreddog.tsrts.containers.MainMenuContainer;
import com.projectreddog.tsrts.containers.OptionsContainer;
import com.projectreddog.tsrts.containers.ResearchContainer;
import com.projectreddog.tsrts.containers.TeamOptionsContainer;
import com.projectreddog.tsrts.containers.TroopBuildingsContainer;
import com.projectreddog.tsrts.containers.UnitRecruitmentContainer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModContainers {

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_BASIC_CONTAINER)
	public static ContainerType<BasicContainer> BASIC_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_DEFENSIVE_BUILDINGS_CONTAINER)
	public static ContainerType<DefensiveBuildingsContainer> DEFENSIVE_BUILDINGS_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MAIN_MENU_CONTAINER)
	public static ContainerType<MainMenuContainer> MAIN_MENU_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ECO_BUILDINGS_CONTAINER)
	public static ContainerType<EcoBuildingsContainer> ECO_BUILDINGS_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TROOP_BUILDINGS_CONTAINER)
	public static ContainerType<TroopBuildingsContainer> TROOP_BUILDINGS_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_UNIT_RECRUITMENT_CONTAINER)
	public static ContainerType<UnitRecruitmentContainer> UNIT_RECRUITMENT_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_RESEARCH_CONTAINER)
	public static ContainerType<ResearchContainer> RESEARCH_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TEAM_OPTIONS_CONTAINER)
	public static ContainerType<TeamOptionsContainer> TEAM_OPTIONS_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_LOBBY_CONTAINER)
	public static ContainerType<LobbyContainer> LOBBY_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_OPTIONS_CONTAINER)
	public static ContainerType<OptionsContainer> OPTIONS_CONTAINER;

	public static void RegisterContainers(final RegistryEvent.Register<ContainerType<?>> event) {

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			return new BasicContainer(windowId, TSRTS.proxy.getClientWorld(), pos, inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_BASIC_CONTAINER));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new DefensiveBuildingsContainer(windowId, TSRTS.proxy.getClientWorld(), inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_DEFENSIVE_BUILDINGS_CONTAINER));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new LobbyContainer(windowId, TSRTS.proxy.getClientWorld(), inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_LOBBY_CONTAINER));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new OptionsContainer(windowId, TSRTS.proxy.getClientWorld(), inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_OPTIONS_CONTAINER));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new MainMenuContainer(windowId, TSRTS.proxy.getClientWorld(), inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_MAIN_MENU_CONTAINER));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new EcoBuildingsContainer(windowId, TSRTS.proxy.getClientWorld(), inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_ECO_BUILDINGS_CONTAINER));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new TroopBuildingsContainer(windowId, TSRTS.proxy.getClientWorld(), inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_TROOP_BUILDINGS_CONTAINER));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new UnitRecruitmentContainer(windowId, TSRTS.proxy.getClientWorld(), inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_UNIT_RECRUITMENT_CONTAINER));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new ResearchContainer(windowId, TSRTS.proxy.getClientWorld(), inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_RESEARCH_CONTAINER));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new TeamOptionsContainer(windowId, TSRTS.proxy.getClientWorld(), inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_TEAM_OPTIONS_CONTAINER));
	}
}
