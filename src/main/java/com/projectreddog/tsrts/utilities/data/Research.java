package com.projectreddog.tsrts.utilities.data;

import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.TeamEnum;

public class Research {
	private boolean[] isUnlocked = new boolean[TeamEnum.values().length];
	private String key;
	private String parentKey;
	private ResourceValues rv;
	private int workRequired;
	private int buttonIndexX;
	private int buttonIndexY;
	private double parentX;
	private double parentY;
	private double currentX;
	private double currentY;
	private int TreeNodeValue;

	private int calculatedLevel;

	public Research(String key, String parentKey, boolean isStartupUnlockedValue, ResourceValues rv, int workRequired, int buttonIndexX, int buttonIndexY) {
		super();
		for (int i = 0; i < TeamEnum.values().length; i++) {
			this.isUnlocked[i] = isStartupUnlockedValue;
		}
		this.key = key;
		this.parentKey = parentKey;
		this.rv = rv;
		this.workRequired = workRequired;
		this.buttonIndexX = buttonIndexX;
		this.buttonIndexY = buttonIndexY;
	}

	public int getTreeNodeValue() {
		return TreeNodeValue;
	}

	public void setTreeNodeValue(int treeNodeValue) {
		TreeNodeValue = treeNodeValue;
	}

	public double getCurrentX() {
		return currentX;
	}

	public void setCurrentX(double currentX) {
		this.currentX = currentX;
	}

	public double getCurrentY() {
		return currentY;
	}

	public void setCurrentY(double currentY) {
		this.currentY = currentY;
	}

	public double getParentX() {
		return parentX;
	}

	public void setParentX(double parentX) {
		this.parentX = parentX;
	}

	public double getParentY() {
		return parentY;
	}

	public void setParentY(double parentY) {
		this.parentY = parentY;
	}

	public int getCalculatedLevel() {
		return calculatedLevel;
	}

	public void setCalculatedLevel(int calculatedLevel) {
		this.calculatedLevel = calculatedLevel;
	}

	public String getNameTranslationKey() {
		return "gui.research." + key + ".name";
	}

	public String getDescrptionTranslationKey() {
		return "gui.research." + key + ".description";
	}

	public boolean isUnlocked(String team) {
		if (TeamEnum.BLUE.getName().equals(team)) {
			return isUnlocked(TeamEnum.BLUE.ordinal());
		}
		if (TeamEnum.RED.getName().equals(team)) {
			return isUnlocked(TeamEnum.RED.ordinal());
		}

		if (TeamEnum.GREEN.getName().equals(team)) {
			return isUnlocked(TeamEnum.GREEN.ordinal());
		}
		if (TeamEnum.YELLOW.getName().equals(team)) {
			return isUnlocked(TeamEnum.YELLOW.ordinal());
		}
		return false;
	}

	public boolean isUnlocked(int teamIndex) {
		return isUnlocked[teamIndex];
	}

	public void setUnlocked(boolean isUnlocked, int teamIndex) {
		this.isUnlocked[teamIndex] = isUnlocked;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public ResourceValues getRv() {
		return rv;
	}

	public void setRv(ResourceValues rv) {
		this.rv = rv;
	}

	public int getWorkRequired() {
		return workRequired;
	}

	public void setWorkRequired(int workRequired) {
		this.workRequired = workRequired;
	}

	public int getButtonIndexX() {
		return buttonIndexX;
	}

	public void setButtonIndexX(int buttonIndexX) {
		this.buttonIndexX = buttonIndexX;
	}

	public int getButtonIndexY() {
		return buttonIndexY;
	}

	public void setButtonIndexY(int buttonIndexY) {
		this.buttonIndexY = buttonIndexY;
	}

}
