package com.projectreddog.tsrts.init;

import java.util.HashMap;
import java.util.Map;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.data.Research;

public class ModResearch {

	public static HashMap<String, Research> research_topics = new HashMap<String, Research>();

	private static int xSpacing = 40;
	private static int ySpacing = 20;

	private static int maxLevels = 0;

	public static void init() {
		// add research topics here
		/*
		  minion
		archer
		pikeman
		lancer
		armory
		marketplace
		wall
		siegeworkshop
		crossbow
		advcedarmor
		watchtower
		batteringrams
		trebuchet
		 */
		ResourceValues rv = new ResourceValues(0, 0, 0, 0, 0, 0, 0);
		registerResearchTopic("minion", null, true, rv);
		registerResearchTopic("archer", "minion", false, rv);
		registerResearchTopic("lancer", "minion", false, rv);
		registerResearchTopic("pikeman", "minion", false, rv);
		registerResearchTopic("armory", "minion", false, rv);
		registerResearchTopic("mareketplace", "minion", false, rv);
		registerResearchTopic("wall", "minion", false, rv);
		registerResearchTopic("siegeworkshop", "minion", false, rv);
		registerResearchTopic("crossbow", "archer", false, rv);
		registerResearchTopic("advcedarmor", "armory", false, rv);
		registerResearchTopic("watchtower", "wall", false, rv);

		registerResearchTopic("batteringrams", "siegeworkshop", false, rv);
		registerResearchTopic("trebuchet", "batteringrams", false, rv);
		updateAllCalcs();

	}

	public static void updateAllCalcs() {

		xSpacing = 40;
		ySpacing = 20;
		calculateLevels(null, 1);

		calculateXvalue();

		for (int i = 1; i <= maxLevels; i++) {
			calculateYvalueByCounting(i);
		}
	}

	private static void calculateYvalueByCounting(int level) {
		int count = 0;
		for (Map.Entry<String, Research> entry : research_topics.entrySet()) {
			if (entry.getValue().getCalculatedLevel() == level) {
				count = count + 1;
			}
		}

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
			entry.getValue().setCurrentX(entry.getValue().getCalculatedLevel() * xSpacing);
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

	private static void registerResearchTopic(String key, String parentKey, boolean unlocked, ResourceValues rv) {

		if (parentKey != null) {
			// has parent so check it
			if (!research_topics.containsKey(parentKey)) {
				// parent key not found throw error

				throw new IllegalArgumentException("Tried to add key:" + key + " with a parent of:" + parentKey + " but the parent was not found!");
			}
		}
		research_topics.put(key, new Research(key, parentKey, unlocked, rv));

	}

}
