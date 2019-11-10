package com.projectreddog.tsrts.utilities;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TeamInfo {

	public static enum Resources {
		FOOD, WOOD, STONE, IRON, GOLD, DIAMOND, EMERALD
	}

	public static ItemStack GetRenderItemStack(Resources res) {
		switch (res) {
		case FOOD:
			return new ItemStack(Items.WHEAT);
		case WOOD:
			return new ItemStack(Items.OAK_LOG);
		case STONE:
			return new ItemStack(Items.COBBLESTONE);
		case IRON:
			return new ItemStack(Items.IRON_INGOT);
		case GOLD:
			return new ItemStack(Items.GOLD_INGOT);
		case DIAMOND:
			return new ItemStack(Items.DIAMOND);
		case EMERALD:
			return new ItemStack(Items.EMERALD);
		}
		return new ItemStack(Items.BARRIER);

	}

	public static Resources GetResoureceForBlock(Block block) {

		if (block == Blocks.STONE) {
			return Resources.STONE;
		}
		if (block == Blocks.IRON_ORE) {
			return Resources.IRON;
		}
		if (block == Blocks.GOLD_ORE) {
			return Resources.GOLD;
		}
		if (block == Blocks.DIAMOND_ORE) {
			return Resources.DIAMOND;
		}
		if (block == Blocks.EMERALD_ORE) {
			return Resources.EMERALD;
		}
		return null;

	}

	private int resourceAmt[] = new int[Resources.values().length];

	public int[] GetResourceArray() {
		return resourceAmt;
	}

	public void SetResourceArray(int[] input) {
		resourceAmt = input;
	}

	public int GetResource(Resources resource) {
		return resourceAmt[resource.ordinal()];
	}

	public void SetResource(Resources resource, int amt) {
		resourceAmt[resource.ordinal()] = amt;
	}

	public void AddResource(Resources resource, int amt) {
		resourceAmt[resource.ordinal()] = resourceAmt[resource.ordinal()] + amt;
	}

	public boolean HasEnoughResource(Resources resource, int amt) {
		return resourceAmt[resource.ordinal()] >= amt;
	}

	public boolean SpendResource(Resources resource, int amt) {
		if (HasEnoughResource(resource, amt)) {
			resourceAmt[resource.ordinal()] = resourceAmt[resource.ordinal()] - amt;
			return true;
		} else {
			return false;
		}
	}

}
