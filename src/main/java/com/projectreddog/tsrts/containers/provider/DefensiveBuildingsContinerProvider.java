package com.projectreddog.tsrts.containers.provider;

import com.projectreddog.tsrts.containers.DefensiveBuildingsContainer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DefensiveBuildingsContinerProvider implements INamedContainerProvider {

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new DefensiveBuildingsContainer(p_createMenu_1_, playerEntity.world, playerInventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(Reference.MODID + ":" + Reference.REIGSTRY_NAME_DEFENSIVE_BUILDINGS_CONTAINER);
	}

}
