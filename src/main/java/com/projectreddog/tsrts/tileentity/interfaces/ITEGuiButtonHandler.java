package com.projectreddog.tsrts.tileentity.interfaces;

import net.minecraft.entity.player.PlayerEntity;

public abstract interface ITEGuiButtonHandler {
	public abstract void HandleGuiButton(int buttonId, PlayerEntity player);
}
