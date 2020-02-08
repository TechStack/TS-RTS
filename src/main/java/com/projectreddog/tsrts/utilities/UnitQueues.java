package com.projectreddog.tsrts.utilities;

import java.util.ArrayList;
import java.util.List;

import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;

public class UnitQueues {
	private List<UNIT_TYPES> Barracks;
	private List<UNIT_TYPES> Stables;
	private List<UNIT_TYPES> ArcheryRange;
	private List<UNIT_TYPES> SiegeWorkshop;

	private boolean infinateBarracksQueue;
	private boolean infinateStablesQueue;
	private boolean infinateArcheryRangeQueue;
	private boolean infinateSiegeWorkshopQueue;

	public boolean hasChanged;

	public void AddToProperQueue(UNIT_TYPES ID) {
		switch (ID) {
		case MINION:
			AddToBarracksQueue(ID);
			break;
		case ADVANCED_KNIGHT:
			AddToBarracksQueue(ID);
			break;

		case KNIGHT:
			AddToBarracksQueue(ID);
			break;
		case ARCHER:
			AddToArcheryRangeQueue(ID);
			break;
		case LANCER:
			AddToStablesQueue(ID);
			break;
		case PIKEMAN:
			AddToBarracksQueue(ID);
			break;
		case TREBUCHET:
			AddToSiegeWorkshopQueue(ID);
			break;
		case SAPPER:
			AddToSiegeWorkshopQueue(ID);
			break;
		case LONGBOWMAN:
			AddToArcheryRangeQueue(ID);
			break;

		case CROSSBOWMAN:
			AddToArcheryRangeQueue(ID);
		default:
			break;
		}
	}

	private void AddToSiegeWorkshopQueue(UNIT_TYPES ID) {
		getSiegeWorkshop().add(ID);
		// TSRTS.LOGGER.info("Added :" + ID + " to the siegeWorkshop QUEUE");
		hasChanged = true;
	}

	private void AddToBarracksQueue(UNIT_TYPES ID) {
		getBarracks().add(ID);
		// TSRTS.LOGGER.info("Added :" + ID + " to the barraks QUEUE");
		hasChanged = true;
	}

	private void AddToArcheryRangeQueue(UNIT_TYPES ID) {
		getArcheryRange().add(ID);
		hasChanged = true;
	}

	private void AddToStablesQueue(UNIT_TYPES ID) {
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

	public UnitQueues(List<UNIT_TYPES> barracks, List<UNIT_TYPES> archeryRange, List<UNIT_TYPES> stables, List<UNIT_TYPES> siegeWorkshop, boolean infinateBarracksQueue, boolean infinateArcheryRangeQueue, boolean infinateStablesQueue, boolean infinateSiegeWorkshopQueue) {
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

	public List<UNIT_TYPES> getBarracks() {
		if (Barracks == null) {
			Barracks = new ArrayList<UNIT_TYPES>();
		}
		return Barracks;

	}

	public List<UNIT_TYPES> getStables() {
		if (Stables == null) {
			Stables = new ArrayList<UNIT_TYPES>();
		}
		return Stables;
	}

	public List<UNIT_TYPES> getArcheryRange() {
		if (ArcheryRange == null) {
			ArcheryRange = new ArrayList<UNIT_TYPES>();
		}
		return ArcheryRange;
	}

	public List<UNIT_TYPES> getSiegeWorkshop() {
		if (SiegeWorkshop == null) {
			SiegeWorkshop = new ArrayList<UNIT_TYPES>();
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
