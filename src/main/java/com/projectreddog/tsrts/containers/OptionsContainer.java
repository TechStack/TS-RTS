package com.projectreddog.tsrts.containers;

import com.projectreddog.tsrts.init.ModContainers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.world.World;

public class OptionsContainer extends Container {

	private PlayerInventory playerInventory;

	public OptionsContainer(int id, World world, PlayerInventory playerInventory) {
		super(ModContainers.OPTIONS_CONTAINER, id);
		this.playerInventory = playerInventory;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {

		return true;
	}

}
