package com.projectreddog.tsrts.init;

import java.util.HashMap;
import java.util.Map;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.Utilities;
import com.projectreddog.tsrts.utilities.data.Research;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.world.server.ServerWorld;

public class ModResearch {

	public static HashMap<String, Research> research_topics = new HashMap<String, Research>();

	private static int xSpacing = 40;
	private static int ySpacing = 20;

	private static int maxLevels = 0;

	public static void init() {
		// add research topics here

		registerResearchTopic("townhall", null, true, Config.CONFIG_RESEARCH_COSTS_TOWNHALL, 100, 4, 3);
		registerResearchTopic("minion", "townhall", true, Config.CONFIG_RESEARCH_COSTS_MINION, 100, 0, 3);
		registerResearchTopic("archer", "townhall", false, Config.CONFIG_RESEARCH_COSTS_ARCHER, 100, 1, 3);
		registerResearchTopic("lancer", "townhall", false, Config.CONFIG_RESEARCH_COSTS_LANCER, 100, 2, 3);
		registerResearchTopic("blacksmithing", "townhall", false, Config.CONFIG_RESEARCH_COSTS_BLACKSMITHING, 100, 2, 1);
		registerResearchTopic("pikeman", "blacksmithing", false, Config.CONFIG_RESEARCH_COSTS_PIKEMAN, 100, 3, 3);
		registerResearchTopic("armor", "blacksmithing", false, Config.CONFIG_RESEARCH_COSTS_ARMOR, 100, 0, 5);
		// registerResearchTopic("marketplace", "townhall", false, Config.CONFIG_RESEARCH_COSTS_MARKETPLACE, 100, 1, 1);
		registerResearchTopic("wall", "townhall", false, Config.CONFIG_RESEARCH_COSTS_WALL, 50, 9, 0);
		registerResearchTopic("siegeworkshop", "townhall", false, Config.CONFIG_RESEARCH_COSTS_SIEGEWORKSHOP, 100, 3, 1);
		registerResearchTopic("advcedarmor", "armor", false, Config.CONFIG_RESEARCH_COSTS_ADVCEDARMOR, 100, 1, 5);
		registerResearchTopic("watchtower", "wall", false, Config.CONFIG_RESEARCH_COSTS_WATCHTOWER, 100, 0, 1);
		// registerResearchTopic("batteringrams", null, false, Config.CONFIG_RESEARCH_COSTS_BATTERINGRAMS, 100, 1, 1);
		registerResearchTopic("trebuchet", "siegeworkshop", false, Config.CONFIG_RESEARCH_COSTS_TREBUCHET, 100, 3, 1);
		registerResearchTopic("longbows", "archer", false, Config.CONFIG_RESEARCH_COSTS_LONGBOWS, 100, 5, 1);
		registerResearchTopic("crossbows", "longbows", false, Config.CONFIG_RESEARCH_COSTS_CROSSBOW, 100, 6, 1);
		registerResearchTopic("faith", "townhall", false, Config.CONFIG_RESEARCH_COSTS_FAITH, 100, 7, 1);
		registerResearchTopic("armorrefinement1", "advcedarmor", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT1, 100, 1, 5, true);
		registerResearchTopic("armorrefinement2", "armorrefinement1", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT2, 100, 1, 5, true);
		registerResearchTopic("armorrefinement3", "armorrefinement2", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT3, 100, 1, 5, true);
		registerResearchTopic("armorrefinement4", "armorrefinement3", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT4, 100, 1, 5, true);
		registerResearchTopic("armorrefinement5", "armorrefinement4", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT5, 100, 1, 5, true);
		registerResearchTopic("armorrefinement6", "armorrefinement5", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT6, 100, 1, 5, true);
		registerResearchTopic("armorrefinement7", "armorrefinement6", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT7, 100, 1, 5, true);
		registerResearchTopic("armorrefinement8", "armorrefinement7", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT8, 100, 1, 5, true);
		registerResearchTopic("armorrefinement9", "armorrefinement8", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT9, 100, 1, 5, true);
		registerResearchTopic("armorrefinement10", "armorrefinement9", false, Config.CONFIG_RESEARCH_COSTS_ARMORREFINEMENT10, 100, 1, 5, true);

		updateAllCalcs();

	}

	public static void FireEvent(ServerWorld world, String key, String teamName) {
		ScorePlayerTeam team = world.getScoreboard().getTeam(teamName);
		switch (key) {
		case "armorrefinement1":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 1);
			break;
		case "armorrefinement2":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 2);

			break;
		case "armorrefinement3":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 3);

			break;
		case "armorrefinement4":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 4);

			break;
		case "armorrefinement5":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 5);

			break;
		case "armorrefinement6":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 6);

			break;
		case "armorrefinement7":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 7);

			break;
		case "armorrefinement8":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 8);

			break;
		case "armorrefinement9":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 9);

			break;
		case "armorrefinement10":
			Utilities.EnchantAllCurrentUnitsOnTeam(world, team, Enchantments.PROTECTION, 10);

			break;

		}
	}

	public static void updateAllCalcs() {

		xSpacing = 40;
		ySpacing = 20;
		calculateLevels(null, 1);

		calculateXvalue();
//TODO : remove next line if this is ran in startup only..
		// its here for debugging only
		for (Map.Entry<String, Research> entry : research_topics.entrySet()) {
			entry.getValue().setTreeNodeValue(0);
		}

		for (int i = maxLevels; i >= 1; i--) {

			calculateTreeNodeValue(i);

		}

//		for (int i = 1; i <= maxLevels; i++) {
//
//			calculateYvalueByCounting(i);
//		}

		calculateYvalueByTreeNodeValue(null, 0);

		for (int i = maxLevels; i >= 1; i--) {

			calculateParentYByLevel(i);

		}
	}

	private static void calculateParentYByLevel(int level) {
		for (Map.Entry<String, Research> entry : research_topics.entrySet()) {
			if (entry.getValue().getCalculatedLevel() == level) {
				entry.getValue().setParentY(GetParentsYvalue(entry.getValue().getParentKey()));

				entry.getValue().setParentX(GetParentsXvalue(entry.getValue().getParentKey()));
			}
		}

	}

	private static double GetParentsXvalue(String parentKey) {
		if (research_topics.containsKey(parentKey)) {
			return research_topics.get(parentKey).getCurrentX();

		} else {
			return 0;
		}
	}

	private static double GetParentsYvalue(String parentKey) {
		if (research_topics.containsKey(parentKey)) {
			return research_topics.get(parentKey).getCurrentY();

		} else {
			return 0;
		}
	}

	private static void calculateTreeNodeValue(int level) {
		for (Map.Entry<String, Research> entry : research_topics.entrySet()) {
			if (entry.getValue().getCalculatedLevel() == level) {
				if (entry.getValue().getParentKey() != null) {
					if (entry.getValue().getTreeNodeValue() == 0) {
						entry.getValue().setTreeNodeValue(1);
					}
					research_topics.get(entry.getValue().getParentKey()).setTreeNodeValue(research_topics.get(entry.getValue().getParentKey()).getTreeNodeValue() + entry.getValue().getTreeNodeValue());
				}

			}
		}

	}

	private static int calculateYvalueByTreeNodeValue(String key, int parentStart) {

		int i = 0;
		int priorSpotSpace = parentStart;
		int prevChildSpot = 0;
		for (Map.Entry<String, Research> entry : research_topics.entrySet()) {
			if ((entry.getValue().getParentKey() == null && key == null) || entry.getValue().getParentKey() != null && entry.getValue().getParentKey().equals(key)) {
				int count = entry.getValue().getTreeNodeValue();
				if (key == null) {
					// top parent so set it by itself
					entry.getValue().setCurrentY(0);
					priorSpotSpace = -ySpacing;
				} else {
					entry.getValue().setCurrentY(((count) * ySpacing) + priorSpotSpace);
					priorSpotSpace = priorSpotSpace + (count * ySpacing);
				}
				i++;
				prevChildSpot = calculateYvalueByTreeNodeValue(entry.getValue().getKey(), priorSpotSpace - (((int) ((count + 1) / 2) * ySpacing)));
				if (prevChildSpot > priorSpotSpace) {
					priorSpotSpace = prevChildSpot;
				}
			}
		}
		return priorSpotSpace;
	}

	private static void calculateXvalue() {
		for (Map.Entry<String, Research> entry : research_topics.entrySet()) {
			entry.getValue().setCurrentX((entry.getValue().getCalculatedLevel() - 1) * xSpacing);
		}

	}

	private static void calculateLevels(String parentKey, int level) {
		for (Map.Entry<String, Research> entry : research_topics.entrySet()) {
			if ((entry.getValue().getParentKey() == null && parentKey == null) || (entry.getValue().getParentKey() != null && entry.getValue().getParentKey().equals(parentKey))) {
				// has same key
				research_topics.get(entry.getKey()).setCalculatedLevel(level);
				TSRTS.LOGGER.info("RESEARCH TOPIC: " + entry.getValue().getKey() + " set to level :" + level);
				// check if this key has any children of its own and set their level!

				if (maxLevels < level) {
					maxLevels = level;
				}
				calculateLevels(entry.getValue().getKey(), level + 1);

			}
		}
	}

	private static void registerResearchTopic(String key, String parentKey, boolean unlocked, ResourceValues rv, int workRequired, int buttonIndexX, int buttonIndexY) {
		registerResearchTopic(key, parentKey, unlocked, rv, workRequired, buttonIndexX, buttonIndexY, false);

	}

	private static void registerResearchTopic(String key, String parentKey, boolean unlocked, ResourceValues rv, int workRequired, int buttonIndexX, int buttonIndexY, boolean hasEvent) {

		if (parentKey != null) {
			// has parent so check it
			if (!research_topics.containsKey(parentKey)) {
				// parent key not found throw error

				throw new IllegalArgumentException("Tried to add key:" + key + " with a parent of:" + parentKey + " but the parent was not found!");
			}
		}
		research_topics.put(key, new Research(key, parentKey, unlocked, rv, workRequired, buttonIndexX, buttonIndexY, hasEvent));

	}

	public static Research getResearch(String key) {
		if (research_topics.containsKey(key)) {
			return research_topics.get(key);
		} else {
			TSRTS.LOGGER.error("ERROR tried to get research with invaid key:" + key);
			throw new IllegalArgumentException("loooking for an invalid research topic KEY! :" + key);
		}
	}

}
