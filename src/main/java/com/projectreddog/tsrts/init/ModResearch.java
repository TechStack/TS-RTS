package com.projectreddog.tsrts.init;

import java.util.HashMap;
import java.util.Map;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.data.Research;

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
		registerResearchTopic("pikeman", "townhall", false, Config.CONFIG_RESEARCH_COSTS_PIKEMAN, 100, 3, 3);
		registerResearchTopic("lancer", "townhall", false, Config.CONFIG_RESEARCH_COSTS_LANCER, 100, 2, 3);
		registerResearchTopic("armory", "townhall", false, Config.CONFIG_RESEARCH_COSTS_ARMORY, 100, 1, 1);
		registerResearchTopic("marketplace", "townhall", false, Config.CONFIG_RESEARCH_COSTS_MARKETPLACE, 100, 1, 1);
		registerResearchTopic("wall", "townhall", false, Config.CONFIG_RESEARCH_COSTS_WALL, 100, 9, 0);
		registerResearchTopic("siegeworkshop", "townhall", false, Config.CONFIG_RESEARCH_COSTS_SIEGEWORKSHOP, 100, 1, 1);
		registerResearchTopic("crossbow", "archer", false, Config.CONFIG_RESEARCH_COSTS_CROSSBOW, 100, 1, 1);
		registerResearchTopic("advcedarmor", "armory", false, Config.CONFIG_RESEARCH_COSTS_ADVCEDARMOR, 100, 1, 1);
		registerResearchTopic("watchtower", "wall", false, Config.CONFIG_RESEARCH_COSTS_WATCHTOWER, 100, 0, 1);
		registerResearchTopic("batteringrams", "siegeworkshop", false, Config.CONFIG_RESEARCH_COSTS_BATTERINGRAMS, 100, 1, 1);
		registerResearchTopic("trebuchet", "batteringrams", false, Config.CONFIG_RESEARCH_COSTS_TREBUCHET, 100, 1, 1);
		updateAllCalcs();

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

	private static void calculateYvalueByCounting(int level) {
		int count = research_topics.get("townhall").getTreeNodeValue();

		int vertSpace = count * ySpacing;
		int i = 0;
		for (Map.Entry<String, Research> entry : research_topics.entrySet()) {
			if (entry.getValue().getCalculatedLevel() == level) {

				entry.getValue().setCurrentY((vertSpace / 2) - (i * ySpacing));
				i++;
			}
		}
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

		if (parentKey != null) {
			// has parent so check it
			if (!research_topics.containsKey(parentKey)) {
				// parent key not found throw error

				throw new IllegalArgumentException("Tried to add key:" + key + " with a parent of:" + parentKey + " but the parent was not found!");
			}
		}
		research_topics.put(key, new Research(key, parentKey, unlocked, rv, workRequired, buttonIndexX, buttonIndexY));

	}

	public static Research getResearch(String key) {
		if (research_topics.containsKey(key)) {
			return research_topics.get(key);
		} else {
			TSRTS.LOGGER.error("ERROR tried to get research with invaid key:" + key);
			throw new IllegalArgumentException("loooking for an invalid research topic KEY!");
		}
	}

}
