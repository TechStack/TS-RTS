package com.projectreddog.tsrts.itemgroups;

import com.projectreddog.tsrts.init.ModBlocks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BlocksItemGroup extends ItemGroup {

	public BlocksItemGroup() {
		super("blocks");

	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Item.BLOCK_TO_ITEM.get(ModBlocks.SAMPLEBLOCK));
	}

}
