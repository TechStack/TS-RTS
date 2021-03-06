package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.STRUCTURE_TYPE;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.tileentity.interfaces.ResourceGenerator;
import com.projectreddog.tsrts.utilities.MapStructureUtilities;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;
import com.projectreddog.tsrts.utilities.data.MapStructureData;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MineSiteTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler, ResourceGenerator {

	private TeamInfo.Resources resource;

	public TeamInfo.Resources getResource() {
		return resource;
	}

	public void setResource(TeamInfo.Resources resource) {
		this.resource = resource;
	}

	public MineSiteTileEntity() {
		super(ModBlocks.MINE_SITE_TILE_ENITTY_TYPE);
	}

	@Override
	public STRUCTURE_TYPE getStructureType() {

		switch (resource) {

		case STONE:
			return STRUCTURE_TYPE.MINE_SITE_STONE;

		case IRON:
			return STRUCTURE_TYPE.MINE_SITE_IRON;

		case GOLD:
			return STRUCTURE_TYPE.MINE_SITE_GOLD;

		case DIAMOND:
			return STRUCTURE_TYPE.MINE_SITE_DIAMOND;
		case EMERALD:
			return STRUCTURE_TYPE.MINE_SITE_EMERALD;
		}
		return null;
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
		MapStructureUtilities.Add(pos, new MapStructureData(pos, getStructureType(), this.getTeam().getName()));

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
		MapStructureUtilities.Remove(pos);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {
			// TODO ADD RESROUCES TO THE TEAM HERE

			TeamInfo.Resources res = getResource();

			if (res != null) {

//				Utilities.AddResourcesToTeam(this.getTeam().getName(), res, 1);
			}

		}
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToTeam(this.getWorld(), this.getTeam().getName(), "tsrts.destroy.minesite");

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
		float tmp = 0;
		switch (resource) {
		case STONE:
			tmp = Config.CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_STONE.get();
			break;
		case IRON:
			tmp = Config.CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_IRON.get();
			break;
		case GOLD:
			tmp = Config.CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_GOLD.get();
			break;
		case DIAMOND:
			tmp = Config.CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_DIAMOND.get();
			break;
		case EMERALD:
			tmp = Config.CONFIG_STRCTURE_TOTAL_HEALTH_MINESITE_EMERALD.get();
			break;
		}

		return .50f * tmp;
	}
}
