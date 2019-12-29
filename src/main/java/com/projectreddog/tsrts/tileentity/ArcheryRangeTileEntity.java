package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ArcheryRangeTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler {

	public ArcheryRangeTileEntity() {
		super(ModBlocks.ARCHERY_RANGE_TILE_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null && getTeam() != null) {
			if (TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getArcheryRange().size() > 0) {
				Utilities.SpawnUnitForTeam(TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getArcheryRange().get(0), this.getOwner(), this.getWorld(), this.getPos(), this.getTeam(), this.getRallyPoint());

				if (TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].isInfinateArcheryRangeQueue()) {

					ResourceValues rv = Utilities.GetResourceValuesforUnitID(TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getArcheryRange().get(0));

					if (Utilities.hasNeededResourcesForResourceValues(getTeam().getName(), rv)) {
						Utilities.spendResourcesForResourceValues(getTeam().getName(), rv);
						TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].AddToProperQueue(TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getArcheryRange().get(0));
					}
				}

				TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].RemoveFirstFromArcheryRangeQueue();
			}
		}
	}

	public boolean spendResources() {
		String teamName = this.getTeam().getName();

		boolean result = true;

		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.FOOD, Config.CONFIG_UNIT_COSTS_ARCHER.getFOOD());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.WOOD, Config.CONFIG_UNIT_COSTS_ARCHER.getWOOD());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.STONE, Config.CONFIG_UNIT_COSTS_ARCHER.getSTONE());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.IRON, Config.CONFIG_UNIT_COSTS_ARCHER.getIRON());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.GOLD, Config.CONFIG_UNIT_COSTS_ARCHER.getGOLD());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.DIAMOND, Config.CONFIG_UNIT_COSTS_ARCHER.getDIAMOND());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.EMERALD, Config.CONFIG_UNIT_COSTS_ARCHER.getEMERALD());
		Utilities.SendTeamToClient(teamName);

		return result;
	}

	public boolean hasNeededResources() {
		if (this.getTeam() == null) {
			return false;
		}
		String teamName = this.getTeam().getName();
		boolean result = true;
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.FOOD, Config.CONFIG_UNIT_COSTS_ARCHER.getFOOD());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.WOOD, Config.CONFIG_UNIT_COSTS_ARCHER.getWOOD());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.STONE, Config.CONFIG_UNIT_COSTS_ARCHER.getSTONE());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.IRON, Config.CONFIG_UNIT_COSTS_ARCHER.getIRON());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.GOLD, Config.CONFIG_UNIT_COSTS_ARCHER.getGOLD());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.DIAMOND, Config.CONFIG_UNIT_COSTS_ARCHER.getDIAMOND());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.EMERALD, Config.CONFIG_UNIT_COSTS_ARCHER.getEMERALD());
		return result;
	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToTeam(this.getWorld(), this.getTeam().getName(), "tsrts.destroy.archeryrange");

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

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setArcheryrange(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getArcheryrange() + 1);

	}

	public void DecreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setArcheryrange(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getArcheryrange() - 1);

	}

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_ARCHERY_RANGE.get();
	}
}
