package com.projectreddog.tsrts.utilities;

import java.util.ArrayList;
import java.util.List;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.reference.Reference;

public class UnitQueues {
	private List<Integer> Barracks;
	private List<Integer> Stables;
	private List<Integer> ArcheryRange;
	public boolean hasChanged;

	public void AddToProperQueue(int ID) {
		switch (ID) {
		case Reference.UNIT_ID_MINION:
			AddToBarracksQueue(ID);
			break;
		case Reference.UNIT_ID_ARCHER:
			AddToArcheryRangeQueue(ID);
			break;
		case Reference.UNIT_ID_LANCER:
			AddToStablesQueue(ID);
			break;
		case Reference.UNIT_ID_PIKEMAN:
			AddToBarracksQueue(ID);
			break;
		default:
			break;
		}
	}

	private void AddToBarracksQueue(int ID) {
		getBarracks().add(ID);
		TSRTS.LOGGER.info("Added :" + ID + " to the barraks QUEUE");
		hasChanged = true;
	}

	private void AddToArcheryRangeQueue(int ID) {
		getArcheryRange().add(ID);
		hasChanged = true;
	}

	private void AddToStablesQueue(int ID) {
		getStables().add(ID);
		hasChanged = true;
	}

	public void RemoveFirstFromBarracksQueue() {
		if (Barracks.size() > 0) {
			TSRTS.LOGGER.info("REMOVING FROM barraks QUEUE");
			Barracks.remove(0);
			hasChanged = true;
		}
	}

	public void RemoveFirstFromArcheryRangeQueue() {
		if (ArcheryRange.size() > 0) {
			ArcheryRange.remove(0);
			hasChanged = true;
		}
	}

	public void RemoveFirstFromStablesQueue() {
		if (Stables.size() > 0) {
			Stables.remove(0);
			hasChanged = true;
		}
	}

	public UnitQueues(List<Integer> barracks, List<Integer> archeryRange, List<Integer> stables) {
		super();
		Barracks = barracks;
		Stables = stables;
		ArcheryRange = archeryRange;
		hasChanged = true;
	}

	public List<Integer> getBarracks() {
		if (Barracks == null) {
			Barracks = new ArrayList<Integer>();
		}
		return Barracks;

	}

	public List<Integer> getStables() {
		if (Stables == null) {
			Stables = new ArrayList<Integer>();
		}
		return Stables;
	}

	public List<Integer> getArcheryRange() {
		if (ArcheryRange == null) {
			ArcheryRange = new ArrayList<Integer>();
		}
		return ArcheryRange;
	}

}
