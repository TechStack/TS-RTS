package com.projectreddog.tsrts.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

public class MountedEntity extends UnitEntity {

	public MountedEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

}
