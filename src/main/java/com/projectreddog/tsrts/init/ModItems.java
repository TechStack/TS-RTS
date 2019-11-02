package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.items.SampleItem;
import com.projectreddog.tsrts.items.TownCenterBuilderItem;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {
	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_SAMPLE_ITEM)
	public static SampleItem SAMPLEITEM = new SampleItem();

	@ObjectHolder(Reference.MODID + ":" + Reference.REIGSTRY_NAME_TOWN_CENTER_BUILDER_ITEM)
	public static TownCenterBuilderItem TOWNCENTERBUILDERITEM = new TownCenterBuilderItem();

	public static void RegisterItems(final RegistryEvent.Register<Item> event) {
		RegisterItem(event, ModItems.SAMPLEITEM);
		RegisterItem(event, ModItems.TOWNCENTERBUILDERITEM);

	}

	// Called to register every item.
	private static void RegisterItem(final RegistryEvent.Register<Item> event, Item item) {
		event.getRegistry().register(item);
	}
}
