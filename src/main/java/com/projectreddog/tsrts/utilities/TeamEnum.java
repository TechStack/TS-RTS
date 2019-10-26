package com.projectreddog.tsrts.utilities;

import net.minecraft.util.IStringSerializable;

public enum TeamEnum implements IStringSerializable {
	BLUE(0, "blue"), RED(1, "red"), YELLOW(2, "yellow"), GREEN(3, "green");
	private final int index;
	private final String name;

	private TeamEnum(int indexIn, String nameIn) {
		this.index = indexIn;
		this.name = nameIn;

	}

	@Override
	public String getName() {
		return this.name;
	}

}
