package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference.STRUCTURE_TYPE;
import com.projectreddog.tsrts.utilities.MapStructureUtilities;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.Utilities;
import com.projectreddog.tsrts.utilities.data.MapStructureData;

import net.minecraft.inventory.container.INamedContainerProvider;

public class MarketplaceTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider {

	public MarketplaceTileEntity() {
		super(ModBlocks.MARKETPLACE_ENTITY_TYPE);
	}

	@Override
	public STRUCTURE_TYPE getStructureType() {
		return STRUCTURE_TYPE.MARKETPLACE;
	}

	public void IncreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMarketplace(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMarketplace() + 1);
		MapStructureUtilities.Add(pos, new MapStructureData(pos, getStructureType(), this.getTeam().getName()));

	}

	public void DecreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setMarketplace(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getMarketplace() - 1);
		MapStructureUtilities.Remove(pos);
	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToTeam(this.getWorld(), this.getTeam().getName(), "tsrts.destroy.marketplace");
	}

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_MARKETPLACE.get();
	}

}
