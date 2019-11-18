package com.projectreddog.tsrts.handler;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo.Resources;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerEvents {

	private static int coolDownAmountRest = 20 * 10;
	private static int coolDownAmountRemaining = coolDownAmountRest;

	@SubscribeEvent
	public static void onServerTickEvent(final ServerTickEvent event) {

		if (event.phase == Phase.END) {

			coolDownAmountRemaining--;

			if (coolDownAmountRemaining <= 0) {

				coolDownAmountRemaining = coolDownAmountRest;

				for (int i = 0; i < TSRTS.teamInfoArray.length; i++) {

					int foodDelta = 0;
					int woodDelta = 0;
					int stoneDelta = 0;
					int ironDelta = 0;
					int goldDelta = 0;
					int diamondDelta = 0;
					int emeraldDelta = 0;

					foodDelta = foodDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE_FOOD.get());
					woodDelta = woodDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE_WOOD.get());
					stoneDelta = stoneDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE_STONE.get());
					ironDelta = ironDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE_IRON.get());
					goldDelta = goldDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE_GOLD.get());
					diamondDelta = diamondDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE_DIAMOND.get());
					emeraldDelta = emeraldDelta + (TSRTS.teamInfoArray[i].getTownHalls() * Config.CONFIG_TOWN_HALL_GENERATE_EMERALD.get());

					foodDelta = foodDelta + (TSRTS.teamInfoArray[i].getFarms() * 1);
					woodDelta = woodDelta + (TSRTS.teamInfoArray[i].getLumberYard() * 1);
					stoneDelta = stoneDelta + (TSRTS.teamInfoArray[i].getMineSiteStone() * 1);
					ironDelta = ironDelta + (TSRTS.teamInfoArray[i].getMineSiteIron() * 1);
					goldDelta = goldDelta + (TSRTS.teamInfoArray[i].getMineSiteGold() * 1);
					diamondDelta = diamondDelta + (TSRTS.teamInfoArray[i].getMineSiteDiamond() * 1);
					emeraldDelta = emeraldDelta + (TSRTS.teamInfoArray[i].getMineSiteEmerald() * 1);

					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.FOOD, foodDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.WOOD, woodDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.STONE, stoneDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.IRON, ironDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.GOLD, goldDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.DIAMOND, diamondDelta);
					Utilities.AddResourcesToTeam(TeamEnum.values()[i].getName(), Resources.EMERALD, emeraldDelta);

					// SEND the changes to the clients !
					Utilities.SendTeamToClient(TeamEnum.values()[i].getName());

				}

			}
		}
	}

}
