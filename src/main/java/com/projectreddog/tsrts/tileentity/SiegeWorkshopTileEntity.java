package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class SiegeWorkshopTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler {

	public SiegeWorkshopTileEntity() {
		super(ModBlocks.SIEGE_WORKSHOP_TILE_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {
			if (TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getSiegeWorkshop().size() > 0) {
				Utilities.SpawnUnitForTeam(TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getSiegeWorkshop().get(0), this.getOwner(), this.getWorld(), this.getPos(), this.getTeam(), this.getRallyPoint());
				TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].RemoveFirstFromSiegeWorkshopQueue();
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

	public void IncreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setSiegeworkshop(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getSiegeworkshop() + 1);

	}

	public void DecreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setSiegeworkshop(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getSiegeworkshop() - 1);

	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToTeam(this.getWorld(), this.getTeam().getName(), "tsrts.destroy.siegeworkshop");

	}

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_SIEGE_WORKSHOP.get();
	}
}