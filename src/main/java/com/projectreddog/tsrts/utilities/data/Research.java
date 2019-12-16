package com.projectreddog.tsrts.utilities.data;

import java.util.List;

public class Research {
	public boolean isUnlocked;
	public List<Research> preReqs;
	public String displayName;

	public Research(boolean isUnlocked, List<Research> preReqs, String displayName) {
		super();
		this.isUnlocked = isUnlocked;
		this.preReqs = preReqs;
		this.displayName = displayName;
	}

}
