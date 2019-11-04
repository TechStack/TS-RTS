package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.containers.BarracksContainer;
import com.projectreddog.tsrts.containers.TownHallContainer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModContainers {

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_BARRACKS_BLOCK)
	public static ContainerType<BarracksContainer> BARRACKS_CONTAINER;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TOWN_HALL_BLOCK)
	public static ContainerType<TownHallContainer> TOWN_HALL_CONTAINER;

	public static void RegisterContainers(final RegistryEvent.Register<ContainerType<?>> event) {

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			return new BarracksContainer(windowId, TSRTS.proxy.getClientWorld(), pos, inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_BARRACKS_BLOCK));

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			return new TownHallContainer(windowId, TSRTS.proxy.getClientWorld(), pos, inv);
		}).setRegistryName(Reference.REIGSTRY_NAME_TOWN_HALL_BLOCK));

	}
}
