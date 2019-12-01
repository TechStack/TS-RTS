package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.utilities.TeamInfo.Resources;

public class ResourceValues {
	private int FOOD;
	private int WOOD;
	private int STONE;
	private int IRON;
	private int GOLD;
	private int DIAMOND;
	private int EMERALD;

	public ResourceValues(int fOOD, int wOOD, int sTONE, int iRON, int gOLD, int dIAMOND, int eMERALD) {
		super();
		FOOD = fOOD;
		WOOD = wOOD;
		STONE = sTONE;
		IRON = iRON;
		GOLD = gOLD;
		DIAMOND = dIAMOND;
		EMERALD = eMERALD;
	}

	public ResourceValues(int[] values) {

		FOOD = values[Resources.FOOD.ordinal()];
		WOOD = values[Resources.WOOD.ordinal()];
		STONE = values[Resources.STONE.ordinal()];
		IRON = values[Resources.IRON.ordinal()];
		GOLD = values[Resources.GOLD.ordinal()];
		DIAMOND = values[Resources.DIAMOND.ordinal()];
		EMERALD = values[Resources.EMERALD.ordinal()];
	}

	public int getFOOD() {
		return FOOD;
	}

	public int getWOOD() {
		return WOOD;
	}

	public int getSTONE() {
		return STONE;
	}

	public int getIRON() {
		return IRON;
	}

	public int getGOLD() {
		return GOLD;
	}

	public int getDIAMOND() {
		return DIAMOND;
	}

	public int getEMERALD() {
		return EMERALD;
	}

}
