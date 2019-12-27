package com.projectreddog.tsrts.utilities;

import java.util.ArrayList;
import java.util.List;

import com.projectreddog.tsrts.utilities.TeamInfo.Resources;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

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

	public List<String> getToolTipText(String team) {

		String color = TextFormatting.WHITE.toString();

		List<String> output = new ArrayList<String>();

		if (Utilities.hasNeededResource(team, TeamInfo.Resources.FOOD, FOOD)) {
			color = TextFormatting.WHITE.toString();
		} else {
			color = TextFormatting.RED.toString();
		}
		output.add(TextFormatting.WHITE + new TranslationTextComponent("resourcevalues.food.name").getUnformattedComponentText() + ": " + color + FOOD);

		if (Utilities.hasNeededResource(team, TeamInfo.Resources.WOOD, WOOD)) {
			color = TextFormatting.WHITE.toString();
		} else {
			color = TextFormatting.RED.toString();
		}
		output.add(TextFormatting.WHITE + new TranslationTextComponent("resourcevalues.wood.name").getUnformattedComponentText() + ": " + color + WOOD);

		if (Utilities.hasNeededResource(team, TeamInfo.Resources.STONE, STONE)) {
			color = TextFormatting.WHITE.toString();
		} else {
			color = TextFormatting.RED.toString();
		}
		output.add(TextFormatting.WHITE + new TranslationTextComponent("resourcevalues.stone.name").getUnformattedComponentText() + ": " + color + STONE);

		if (Utilities.hasNeededResource(team, TeamInfo.Resources.IRON, IRON)) {
			color = TextFormatting.WHITE.toString();
		} else {
			color = TextFormatting.RED.toString();
		}
		output.add(TextFormatting.WHITE + new TranslationTextComponent("resourcevalues.iron.name").getUnformattedComponentText() + ": " + color + IRON);

		if (Utilities.hasNeededResource(team, TeamInfo.Resources.GOLD, GOLD)) {
			color = TextFormatting.WHITE.toString();
		} else {
			color = TextFormatting.RED.toString();
		}
		output.add(TextFormatting.WHITE + new TranslationTextComponent("resourcevalues.gold.name").getUnformattedComponentText() + ": " + color + GOLD);

		if (Utilities.hasNeededResource(team, TeamInfo.Resources.DIAMOND, DIAMOND)) {
			color = TextFormatting.WHITE.toString();
		} else {
			color = TextFormatting.RED.toString();
		}
		output.add(TextFormatting.WHITE + new TranslationTextComponent("resourcevalues.diamond.name").getUnformattedComponentText() + ": " + color + DIAMOND);

		if (Utilities.hasNeededResource(team, TeamInfo.Resources.EMERALD, EMERALD)) {
			color = TextFormatting.WHITE.toString();
		} else {
			color = TextFormatting.RED.toString();
		}
		output.add(TextFormatting.WHITE + new TranslationTextComponent("resourcevalues.emerald.name").getUnformattedComponentText() + ": " + color + EMERALD);

		return output;
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
