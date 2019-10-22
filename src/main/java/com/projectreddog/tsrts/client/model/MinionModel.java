package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.MinionEntity;

import net.minecraft.client.renderer.entity.model.PlayerModel;

public class MinionModel extends PlayerModel<MinionEntity> {

	public MinionModel() {

		// TODO second parm needs to take into account if the arms are "SMALL" or not .. eg alex or steve model
		// FALSE is default steve
		super(0f, false);
	}

}
