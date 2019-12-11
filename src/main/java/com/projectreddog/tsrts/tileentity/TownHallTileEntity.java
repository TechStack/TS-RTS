package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TownHallTileEntity extends OwnedCooldownTileEntity {

	public TownHallTileEntity() {
		super(ModBlocks.TOWN_HALL_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {
			// ModEntities.MINION.spawn(world, null, null, this.pos, SpawnReason.TRIGGERED, true, true);

//			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.FOOD, Config.CONFIG_TOWN_HALL_GENERATE_FOOD.get());
//			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.WOOD, Config.CONFIG_TOWN_HALL_GENERATE_WOOD.get());
//			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.STONE, Config.CONFIG_TOWN_HALL_GENERATE_STONE.get());
//			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.IRON, Config.CONFIG_TOWN_HALL_GENERATE_IRON.get());
//			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.GOLD, Config.CONFIG_TOWN_HALL_GENERATE_GOLD.get());
//			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.DIAMOND, Config.CONFIG_TOWN_HALL_GENERATE_DIAMOND.get());
//			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.EMERALD, Config.CONFIG_TOWN_HALL_GENERATE_EMERALD.get());

		}
	}

	@Override
	public void IncreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setTownHalls(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getTownHalls() + 1);

	}

	@Override
	public void DecreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setTownHalls(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getTownHalls() - 1);

	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToAllTeams(this.getWorld(), "tsrts.destroy.townhall." + this.getTeam().getName(), this.getTeam().getName());

	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_TOWN_HALL.get();
	}

}
