package com.projectreddog.tsrts.tileentity.interfaces;

import com.projectreddog.tsrts.utilities.TeamInfo;

public interface ResourceGenerator {
	public abstract TeamInfo.Resources getResource();

	public abstract void setResource(TeamInfo.Resources resource);
}
