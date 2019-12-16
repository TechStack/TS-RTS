package com.projectreddog.tsrts.init;

import java.util.ArrayList;
import java.util.List;

import com.projectreddog.tsrts.utilities.data.Research;

public class ModResearch {

	public Research soldiers;

	public Research achery;
	public Research pikeman;
	public Research lancer;

	public Research advancedBuilding;
	private List<Research> preReq;

	public ModResearch() {
		preReq = new ArrayList<Research>();
		soldiers = new Research(true, preReq, "Soldiers");

		preReq = new ArrayList<Research>();
		preReq.add(soldiers);
		achery = new Research(true, preReq, "Archery");

		preReq = new ArrayList<Research>();
		preReq.add(soldiers);
		pikeman = new Research(true, preReq, "Pikeman");

		preReq = new ArrayList<Research>();
		preReq.add(soldiers);
		lancer = new Research(true, preReq, "lancer");

		preReq = new ArrayList<Research>();
		preReq.add(soldiers);
		advancedBuilding = new Research(true, preReq, "Advanced Building");

		preReq = new ArrayList<Research>();
	}

}
