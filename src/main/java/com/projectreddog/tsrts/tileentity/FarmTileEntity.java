package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.tileentity.interfaces.ResourceGenerator;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class FarmTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler, ResourceGenerator {

	private TeamInfo.Resources resource;

	public TeamInfo.Resources getResource() {
		return resource;
	}

	public void setResource(TeamInfo.Resources resource) {
		this.resource = resource;
	}

	public FarmTileEntity() {
		super(ModBlocks.FARM_TILE_ENITTY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {

			TeamInfo.Resources res = getResource();

			if (res != null) {

				Utilities.AddResourcesToTeam(this.getTeam().getName(), res, 1);
			}

		}
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(getType().getRegistryName().getPath());
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
