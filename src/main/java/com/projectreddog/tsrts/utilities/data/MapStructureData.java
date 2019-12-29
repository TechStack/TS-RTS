package com.projectreddog.tsrts.utilities.data;

import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.STRUCTURE_TYPE;

import net.minecraft.util.math.BlockPos;

public class MapStructureData {
	private BlockPos position;
	private Reference.STRUCTURE_TYPE type;
	private String teamName;

	public MapStructureData(BlockPos position, STRUCTURE_TYPE type, String teamName) {
		super();
		this.position = position;
		this.type = type;
		this.teamName = teamName;
	}

	public BlockPos getPosition() {
		return position;
	}

	public void setPosition(BlockPos position) {
		this.position = position;
	}

	public Reference.STRUCTURE_TYPE getType() {
		return type;
	}

	public void setType(Reference.STRUCTURE_TYPE type) {
		this.type = type;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

}
