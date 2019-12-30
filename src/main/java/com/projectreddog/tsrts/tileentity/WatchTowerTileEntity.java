package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference.STRUCTURE_TYPE;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.Utilities;
import com.projectreddog.tsrts.utilities.data.MapStructureData;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class WatchTowerTileEntity extends OwnedCooldownTileEntity {

	public WatchTowerTileEntity() {
		super(ModBlocks.TOWN_WATCH_TOWER_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null && getTeam() != null) {

		}
	}

	@Override
	public STRUCTURE_TYPE getStructureType() {
		return STRUCTURE_TYPE.WATCH_TOWER;
	}

	@Override
	public void IncreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setWatchtowers(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getWatchtowers() + 1);
		TSRTS.Structures.put(pos, new MapStructureData(pos, getStructureType(), this.getTeam().getName()));

	}

	@Override
	public void DecreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setWatchtowers(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getWatchtowers() - 1);
		TSRTS.Structures.remove(pos);
	}

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_WATCH_TOWER.get();
	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToTeam(this.getWorld(), this.getTeam().getName(), "tsrts.destroy.watchtower");

	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

}
