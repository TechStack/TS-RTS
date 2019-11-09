package com.projectreddog.tsrts.containers;

import com.projectreddog.tsrts.init.ModContainers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.world.World;

public class LobbyContainer extends Container {

	private PlayerInventory playerInventory;

	public LobbyContainer(int id, World world, PlayerInventory playerInventory) {
		super(ModContainers.LOBBY_CONTAINER, id);
		this.playerInventory = playerInventory;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {

		return true;
	}

}
