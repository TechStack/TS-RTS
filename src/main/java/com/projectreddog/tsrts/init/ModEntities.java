package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.entities.MinionEntity;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModEntities {

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINION_ENTITY)
	public static EntityType<MinionEntity> MINION;

	public static void RegisterEntites(final RegistryEvent.Register<EntityType<?>> event) {

		event.getRegistry().register(EntityType.Builder.create(MinionEntity::new, EntityClassification.MONSTER).size(1, 1).setShouldReceiveVelocityUpdates(false).build(Reference.REIGSTRY_NAME_MINION_ENTITY).setRegistryName(Reference.MODID, Reference.REIGSTRY_NAME_MINION_ENTITY));
	}

}
