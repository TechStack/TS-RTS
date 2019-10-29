package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.containers.GarrisonContainer;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MineSiteTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler {

	public MineSiteTileEntity() {
		super(ModBlocks.MINE_SITE_TILE_ENITTY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {
			// TODO ADD RESROUCES TO THE TEAM HERE

			TeamInfo.Resources res = TeamInfo.GetResoureceForBlock(world.getBlockState(this.pos.down()).getBlock());

			if (res != null) {

				Utilities.AddResourcesToTeam(this.getTeam().getName(), res, 1);
			}

		}
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new GarrisonContainer(p_createMenu_1_, this.world, this.getPos(), playerInventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Override
	public void HandleGuiButton(int buttonId, PlayerEntity player) {
		// TSRTS.LOGGER.info("button ID:" + buttonId);

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
