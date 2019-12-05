package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.entities.ArcherMinionEntity;
import com.projectreddog.tsrts.entities.MinionEntity;
import com.projectreddog.tsrts.entities.MountedEntity;
import com.projectreddog.tsrts.entities.PikemanEntity;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModEntities {

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MINION_ENTITY)
	public static EntityType<MinionEntity> MINION;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_ARCHER_MINION_ENTITY)
	public static EntityType<ArcherMinionEntity> ARCHER_MINION;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TARGET_ENTITY)
	public static EntityType<TargetEntity> TARGET_ENTITY;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_MOUNTED_ENTITY)
	public static EntityType<MountedEntity> MOUNTED_ENTITY;

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_PIKEMAN_ENTITY)
	public static EntityType<PikemanEntity> PIKEMAN_ENTITY;

	public static void RegisterEntites(final RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(EntityType.Builder.create(MinionEntity::new, EntityClassification.MONSTER).size(0.751F, 1.95F).setShouldReceiveVelocityUpdates(false).build(Reference.REIGSTRY_NAME_MINION_ENTITY).setRegistryName(Reference.MODID, Reference.REIGSTRY_NAME_MINION_ENTITY));
		event.getRegistry().register(EntityType.Builder.create(ArcherMinionEntity::new, EntityClassification.MONSTER).size(0.751F, 1.95F).setShouldReceiveVelocityUpdates(false).build(Reference.REIGSTRY_NAME_ARCHER_MINION_ENTITY).setRegistryName(Reference.MODID, Reference.REIGSTRY_NAME_ARCHER_MINION_ENTITY));
		event.getRegistry().register(EntityType.Builder.create(TargetEntity::new, EntityClassification.CREATURE).size(1F, 1F).setShouldReceiveVelocityUpdates(false).build(Reference.REIGSTRY_NAME_TARGET_ENTITY).setRegistryName(Reference.MODID, Reference.REIGSTRY_NAME_TARGET_ENTITY));

		event.getRegistry().register(EntityType.Builder.create(MountedEntity::new, EntityClassification.MONSTER).size(.5F, 1.75F).setShouldReceiveVelocityUpdates(false).build(Reference.REIGSTRY_NAME_MOUNTED_ENTITY).setRegistryName(Reference.MODID, Reference.REIGSTRY_NAME_MOUNTED_ENTITY));
		event.getRegistry().register(EntityType.Builder.create(PikemanEntity::new, EntityClassification.MONSTER).size(0.751F, 1.95F).setShouldReceiveVelocityUpdates(false).build(Reference.REIGSTRY_NAME_PIKEMAN_ENTITY).setRegistryName(Reference.MODID, Reference.REIGSTRY_NAME_PIKEMAN_ENTITY));

	}

}
