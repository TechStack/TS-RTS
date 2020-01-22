package com.projectreddog.tsrts.utilities;

import java.util.ArrayList;
import java.util.List;

import com.projectreddog.tsrts.reference.Reference;

public class UnitQueues {
	private List<Integer> Barracks;
	private List<Integer> Stables;
	private List<Integer> ArcheryRange;
	private List<Integer> SiegeWorkshop;

	private boolean infinateBarracksQueue;
	private boolean infinateStablesQueue;
	private boolean infinateArcheryRangeQueue;
	private boolean infinateSiegeWorkshopQueue;

	public boolean hasChanged;

	public void AddToProperQueue(int ID) {
		switch (ID) {
		case Reference.UNIT_ID_MINION:
			AddToBarracksQueue(ID);
			break;
		case Reference.UNIT_ID_ADVANCED_KNIGHT:
			AddToBarracksQueue(ID);
			break;

		case Reference.UNIT_ID_KNIGHT:
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
		case Reference.UNIT_ID_TREBUCHET:
			AddToSiegeWorkshopQueue(ID);
			break;
		case Reference.UNIT_ID_SAPPER:
			AddToSiegeWorkshopQueue(ID);
			break;

		default:
			break;
		}
	}

	private void AddToSiegeWorkshopQueue(int ID) {
		getSiegeWorkshop().add(ID);
		// TSRTS.LOGGER.info("Added :" + ID + " to the siegeWorkshop QUEUE");
		hasChanged = true;
	}

	private void AddToBarracksQueue(int ID) {
		getBarracks().add(ID);
		// TSRTS.LOGGER.info("Added :" + ID + " to the barraks QUEUE");
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
			// TSRTS.LOGGER.info("REMOVING FROM barraks QUEUE");
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

	public void RemoveFirstFromSiegeWorkshopQueue() {
		if (SiegeWorkshop.size() > 0) {
			SiegeWorkshop.remove(0);
			hasChanged = true;
		}
	}

	public UnitQueues(List<Integer> barracks, List<Integer> archeryRange, List<Integer> stables, List<Integer> siegeWorkshop, boolean infinateBarracksQueue, boolean infinateArcheryRangeQueue, boolean infinateStablesQueue, boolean infinateSiegeWorkshopQueue) {
		super();
		Barracks = barracks;
		Stables = stables;
		ArcheryRange = archeryRange;
		hasChanged = true;
		SiegeWorkshop = siegeWorkshop;
		this.infinateBarracksQueue = infinateBarracksQueue;
		this.infinateArcheryRangeQueue = infinateArcheryRangeQueue;
		this.infinateStablesQueue = infinateStablesQueue;
		this.infinateSiegeWorkshopQueue = infinateSiegeWorkshopQueue;
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

	public List<Integer> getSiegeWorkshop() {
		if (SiegeWorkshop == null) {
			SiegeWorkshop = new ArrayList<Integer>();
		}
		return SiegeWorkshop;
	}

	public boolean isInfinateBarracksQueue() {
		return infinateBarracksQueue;
	}

	public void setInfinateBarracksQueue(boolean infinateBarracksQueue) {
		this.infinateBarracksQueue = infinateBarracksQueue;
	}

	public boolean isInfinateStablesQueue() {
		return infinateStablesQueue;
	}

	public void setInfinateStablesQueue(boolean infinateStablesQueue) {
		this.infinateStablesQueue = infinateStablesQueue;
	}

	public boolean isInfinateArcheryRangeQueue() {
		return infinateArcheryRangeQueue;
	}

	public void setInfinateArcheryRangeQueue(boolean infinateArcheryRangeQueue) {
		this.infinateArcheryRangeQueue = infinateArcheryRangeQueue;
	}

	public boolean isInfinateSiegeWorkshopQueue() {
		return infinateSiegeWorkshopQueue;
	}

	public void setInfinateSiegeWorkshopQueue(boolean infinateSiegeWorkshopQueue) {
		this.infinateSiegeWorkshopQueue = infinateSiegeWorkshopQueue;
	}

}
