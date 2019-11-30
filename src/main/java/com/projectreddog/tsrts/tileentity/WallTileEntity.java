package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class WallTileEntity extends OwnedCooldownTileEntity {

	public WallTileEntity() {
		super(ModBlocks.TOWN_WALL_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {

		}
	}

	@Override
	public void IncreaseCount() {

		// TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setTownHalls(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getTownHalls() + 1);

	}

	@Override
	public void DecreaseCount() {

		// TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setTownHalls(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getTownHalls() - 1);

	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToAllTeams(this.getWorld(), "tsrts.destroy.wall", this.getTeam().getName());

	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

}
