package com.projectreddog.tsrts.containers;

import com.projectreddog.tsrts.init.ModContainers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.world.World;

public class TroopBuildingsContainer extends Container {

	public TroopBuildingsContainer(int id, World world, PlayerInventory playerInventory) {
		super(ModContainers.TROOP_BUILDINGS_CONTAINER, id);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {

		return true;
	}

}
