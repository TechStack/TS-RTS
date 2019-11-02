package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.containers.GarrisonContainer;
import com.projectreddog.tsrts.containers.TownCenterContainer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModContainers {

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_GARRISON_BLOCK)
	public static ContainerType<GarrisonContainer> GARRISON_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TOWN_CENTER_BLOCK)
	public static ContainerType<TownCenterContainer> TOWN_CENTER_CONTAINER;

	public static void RegisterContainers(final RegistryEvent.Register<ContainerType<?>> event) {

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			return new GarrisonContainer(windowId, TSRTS.proxy.getClientWorld(), pos, inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_GARRISON_BLOCK));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			return new TownCenterContainer(windowId, TSRTS.proxy.getClientWorld(), pos, inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_TOWN_CENTER_BLOCK));

	}
}
