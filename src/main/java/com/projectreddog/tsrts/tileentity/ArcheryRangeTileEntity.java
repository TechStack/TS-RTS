package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.STRUCTURE_TYPE;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.Utilities;
import com.projectreddog.tsrts.utilities.data.MapStructureData;

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
		if (TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getCurrentPopulation() < TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getTeamPopulationCap()) {

			if (getOwner() != null && getTeam() != null) {
				if (TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getArcheryRange().size() > 0) {
					Utilities.SpawnUnitForTeam(TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getArcheryRange().get(0), this.getOwner(), this.getWorld(), this.getPos(), this.getTeam(), this.getRallyPoint());

					if (TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].isInfinateArcheryRangeQueue()) {

						ResourceValues rv = Utilities.GetResourceValuesforUnitID(TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getArcheryRange().get(0));

						if (Utilities.hasNeededResourcesForResourceValues(getTeam().getName(), rv)) {
							Utilities.spendResourcesForResourceValues(getTeam().getName(), rv);
							TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].AddToProperQueue(TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].getArcheryRange().get(0));
						} else {
							Utilities.SendMessageToTeam(this.world, this.getTeam().getName(), "message.cannotrebuy", false);
						}
					}

					TSRTS.TeamQueues[TeamEnum.getIDFromName(getTeam().getName())].RemoveFirstFromArcheryRangeQueue();
				}
			}
		}
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

	@Override
	public STRUCTURE_TYPE getStructureType() {
		return STRUCTURE_TYPE.ARCHERY_RANGE;
	}

	public void IncreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setArcheryrange(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getArcheryrange() + 1);
		TSRTS.Structures.put(pos, new MapStructureData(pos, getStructureType(), this.getTeam().getName()));
	}

	public void DecreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setArcheryrange(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getArcheryrange() - 1);
		TSRTS.Structures.remove(pos);

	}

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_ARCHERY_RANGE.get();
	}
}
