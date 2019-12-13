package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

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
			if (TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getStables().size() > 0) {
				Utilities.SpawnUnitForTeam(TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getStables().get(0), this.getOwner(), this.getWorld(), this.getPos(), this.getTeam(), this.getRallyPoint());
				TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].RemoveFirstFromStablesQueue();

			}
		}
	}

	public boolean spendResources() {
		String teamName = this.getTeam().getName();

		boolean result = true;

		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.FOOD, Config.CONFIG_UNIT_COSTS_LANCER.getFOOD());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.WOOD, Config.CONFIG_UNIT_COSTS_LANCER.getWOOD());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.STONE, Config.CONFIG_UNIT_COSTS_LANCER.getSTONE());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.IRON, Config.CONFIG_UNIT_COSTS_LANCER.getIRON());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.GOLD, Config.CONFIG_UNIT_COSTS_LANCER.getGOLD());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.DIAMOND, Config.CONFIG_UNIT_COSTS_LANCER.getDIAMOND());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.EMERALD, Config.CONFIG_UNIT_COSTS_LANCER.getEMERALD());
		Utilities.SendTeamToClient(teamName);

		return result;
	}

	public boolean hasNeededResources() {
		if (this.getTeam() == null) {
			return false;
		}
		String teamName = this.getTeam().getName();
		boolean result = true;
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.FOOD, Config.CONFIG_UNIT_COSTS_LANCER.getFOOD());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.WOOD, Config.CONFIG_UNIT_COSTS_LANCER.getWOOD());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.STONE, Config.CONFIG_UNIT_COSTS_LANCER.getSTONE());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.IRON, Config.CONFIG_UNIT_COSTS_LANCER.getIRON());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.GOLD, Config.CONFIG_UNIT_COSTS_LANCER.getGOLD());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.DIAMOND, Config.CONFIG_UNIT_COSTS_LANCER.getDIAMOND());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.EMERALD, Config.CONFIG_UNIT_COSTS_LANCER.getEMERALD());
		return result;
	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToTeam(this.getWorld(), this.getTeam().getName(), "tsrts.destroy.stables");

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

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_STABLES.get();
	}
}
