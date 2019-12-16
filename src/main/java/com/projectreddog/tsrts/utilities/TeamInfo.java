package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.init.ModResearch;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TeamInfo {
	private int resourceAmt[] = new int[Resources.values().length];
	private int townHalls = 0;
	private int farms = 0;
	private int lumberYard = 0;
	private int mineSiteStone = 0;
	private int mineSiteIron = 0;
	private int mineSiteGold = 0;
	private int mineSiteDiamond = 0;
	private int mineSiteEmerald = 0;

	private ModResearch research = new ModResearch();

	public ModResearch getResearch() {
		return research;
	}

	public int[] getResourceAmt() {
		return resourceAmt;

	}

	public void setResourceAmt(int[] resourceAmt) {
		this.resourceAmt = resourceAmt;
	}

	public int getTownHalls() {
		return townHalls;
	}

	public void setTownHalls(int townHalls) {
		this.townHalls = townHalls;
	}

	public int getFarms() {
		return farms;
	}

	public void setFarms(int farms) {
		this.farms = farms;
	}

	public int getLumberYard() {
		return lumberYard;
	}

	public void setLumberYard(int lumberYard) {
		this.lumberYard = lumberYard;
	}

	public int getMineSiteStone() {
		return mineSiteStone;
	}

	public void setMineSiteStone(int mineSiteStone) {
		this.mineSiteStone = mineSiteStone;
	}

	public int getMineSiteIron() {
		return mineSiteIron;
	}

	public void setMineSiteIron(int mineSiteIron) {
		this.mineSiteIron = mineSiteIron;
	}

	public int getMineSiteGold() {
		return mineSiteGold;
	}

	public void setMineSiteGold(int mineSiteGold) {
		this.mineSiteGold = mineSiteGold;
	}

	public int getMineSiteDiamond() {
		return mineSiteDiamond;
	}

	public void setMineSiteDiamond(int mineSiteDiamond) {
		this.mineSiteDiamond = mineSiteDiamond;
	}

	public int getMineSiteEmerald() {
		return mineSiteEmerald;
	}

	public void setMineSiteEmerald(int mineSiteEmerald) {
		this.mineSiteEmerald = mineSiteEmerald;
	}

	public void clear() {
		resourceAmt = new int[Resources.values().length];
	}

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
		if (block == Blocks.OAK_LOG) {
			return Resources.WOOD;
		}
		if (block == Blocks.GRASS_BLOCK) {
			return Resources.FOOD;
		}
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

}
