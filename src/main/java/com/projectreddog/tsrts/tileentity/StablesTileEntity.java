package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.init.ModEntities;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;

public class StablesTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler {

	public StablesTileEntity() {
		super(ModBlocks.STABLES_ENTITY_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {

			Utilities.SpawnMountedUnitForTeam(ModEntities.MINION, EntityType.HORSE, this.getOwner(), this.getWorld(), this.getPos(), this.getTeam(), this.getRallyPoint());

		}
	}

	@Override
	public void HandleGuiButton(int buttonId, PlayerEntity player) {
		// TSRTS.LOGGER.info("button ID:" + buttonId);
		super.HandleGuiButton(buttonId, player);
		if (buttonId == Reference.GUI_BUTTON_DEBUG_TESTERYELLOW) {
			this.setOwner("testeryellow");
		} else if (buttonId == Reference.GUI_BUTTON_DEBUG_TESTERBLUE) {
			this.setOwner("testerblue");
		} else if (buttonId == Reference.GUI_BUTTON_DEBUG_TESTERGREEN) {
			this.setOwner("testergreen");
		} else if (buttonId == Reference.GUI_BUTTON_DEBUG_TESTERRED) {
			this.setOwner("testerred");
		}

	}
}
