package com.projectreddog.tsrts.containers.provider;

import com.projectreddog.tsrts.containers.LobbyContainer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class LobbyContinerProvider implements INamedContainerProvider {

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new LobbyContainer(p_createMenu_1_, playerEntity.world, playerInventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(Reference.MODID + ":" + Reference.REIGSTRY_NAME_LOBBY_CONTAINER);
	}

}
