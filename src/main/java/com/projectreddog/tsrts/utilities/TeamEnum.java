package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.TSRTS;

import net.minecraft.util.IStringSerializable;

public enum TeamEnum implements IStringSerializable {
	BLUE(0, "blue", 170), RED(1, "red", 11141120), YELLOW(2, "yellow", 16777045), GREEN(3, "green", 43520);
	private final int index;
	private final String name;
	private final int colorCode;

	private TeamEnum(int indexIn, String nameIn, int colorCode) {
		this.index = indexIn;
		this.name = nameIn;
		this.colorCode = colorCode;

	}

	public int getColorCode() {
		return this.colorCode;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public static int getIDFromName(String name) {
		switch (name) {
		case "blue":
			return 0;
		case "red":
			return 1;
		case "yellow":
			return 2;
		case "green":
			return 3;
		}

		TSRTS.LOGGER.info("INVALID PLAYER TEAM FOUND: " + name);
		return 0;

	}

	public static String getNameFromID(int id) {
		switch (id) {
		case 0:
			return "blue";
		case 1:
			return "red";
		case 2:
			return "yellow";
		case 3:
			return "green";
		}
		return "blue";
	}

}
