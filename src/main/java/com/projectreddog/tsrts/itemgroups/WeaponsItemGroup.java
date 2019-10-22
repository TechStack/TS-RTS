package com.projectreddog.tsrts.itemgroups;

import com.projectreddog.tsrts.init.ModItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class WeaponsItemGroup extends ItemGroup {

	public WeaponsItemGroup() {
		super("weapons");

	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModItems.SAMPLEITEM);
	}

}
