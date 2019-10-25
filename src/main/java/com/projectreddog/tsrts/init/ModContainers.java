package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.containers.GarrisonContainer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModContainers {

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_GARRISON_BLOCK)
	public static ContainerType<GarrisonContainer> GARRISON_CONTAINER;

	public static void RegisterContainers(final RegistryEvent.Register<ContainerType<?>> event) {
		RegisterContainer(event, ModContainers.GARRISON_CONTAINER, Reference.REIGSTRY_NAME_GARRISON_BLOCK);

	}

	private static void RegisterContainer(final RegistryEvent.Register<ContainerType<?>> event, ContainerType<GarrisonContainer> container, String registryName) {
		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			return new GarrisonContainer(windowId, TSRTS.proxy.getClientWorld(), pos, inv);
		}).setRegistryName(registryName));

	}
}
