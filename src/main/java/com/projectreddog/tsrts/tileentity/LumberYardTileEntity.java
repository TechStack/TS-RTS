package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.tileentity.interfaces.ResourceGenerator;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class LumberYardTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler, ResourceGenerator {

	private TeamInfo.Resources resource;

	public TeamInfo.Resources getResource() {
		return resource;
	}

	public void setResource(TeamInfo.Resources resource) {
		this.resource = resource;
	}

	public LumberYardTileEntity() {
		super(ModBlocks.LUMBER_YARD_TILE_ENITTY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {

			TeamInfo.Resources res = getResource();

			if (res != null) {

//				Utilities.AddResourcesToTeam(this.getTeam().getName(), res, 1);
			}

		}
	}

	public void IncreaseCount() {
		switch (resource) {
		case FOOD:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setFarms(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getFarms() + 1);
			break;
		case WOOD:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setLumberYard(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getLumberYard() + 1);
			break;
		case STONE:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteStone(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteStone() + 1);
			break;
		case IRON:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteIron(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteIron() + 1);
			break;
		case GOLD:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteGold(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteGold() + 1);
			break;
		case DIAMOND:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteDiamond(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteDiamond() + 1);
			break;
		case EMERALD:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteEmerald(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteEmerald() + 1);
			break;
		}
	}

	public void DecreaseCount() {
		switch (resource) {
		case FOOD:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setFarms(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getFarms() - 1);
			break;
		case WOOD:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setLumberYard(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getLumberYard() - 1);
			break;
		case STONE:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteStone(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteStone() - 1);
			break;
		case IRON:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteIron(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteIron() - 1);
			break;
		case GOLD:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteGold(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteGold() - 1);
			break;
		case DIAMOND:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteDiamond(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteDiamond() - 1);
			break;
		case EMERALD:
			TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMineSiteEmerald(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMineSiteEmerald() - 1);
			break;
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
