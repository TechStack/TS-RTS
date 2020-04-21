package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;

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
	private int researchCenter = 0;
	private int baracks = 0;
	private int archeryrange = 0;
	private int stables = 0;
	private int marketplace = 0;
	private int armory = 0;
	private int siegeworkshop = 0;
	private int walls = 0;
	private int watchtowers = 0;
	private int gates = 0;
	private int wallsteps = 0;

	private int[] unitCount = new int[UNIT_TYPES.values().length];

	private String CurrenResearchKey;
	private int teamPopulationCap;
	private int teamPlayerCount;

	private int currenResearchWorkRemaining;
	private int fullResearchWorkRemaining;

	public TeamInfo() {
		super();
		CurrenResearchKey = "";
	}

	public String getUnitNameForStatsLog(String delimeter) {
		String returnValue = "";
		for (int i = 0; i < UNIT_TYPES.values().length; i++) {
			returnValue = returnValue + delimeter + UNIT_TYPES.values()[i].getName();
		}
		return returnValue;
	}

	public String getUnitCountForStatsLog(String delimeter) {
		String returnValue = "";
		for (int i = 0; i < unitCount.length; i++) {
			returnValue = returnValue + delimeter + unitCount[i];
		}
		return returnValue;
	}

	public int getCurrentPopulation() {
		int count = 0;
		for (int i = 0; i < unitCount.length; i++) {
			count = count + unitCount[i];
		}
		return count;
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

	public String getCurrenResearchKey() {
		return CurrenResearchKey;
	}

	public void setCurrenResearchKey(String currenResearchKey) {
		CurrenResearchKey = currenResearchKey;
	}

	public int getCurrenResearchWorkRemaining() {
		return currenResearchWorkRemaining;
	}

	public void setCurrenResearchWorkRemaining(int currenResearchWorkRemaining) {
		this.currenResearchWorkRemaining = currenResearchWorkRemaining;
	}

	public int getFullResearchWorkRemaining() {
		return fullResearchWorkRemaining;
	}

	public void setFullResearchWorkRemaining(int fullResearchWorkRemaining) {
		this.fullResearchWorkRemaining = fullResearchWorkRemaining;
	}

	public int getResearchCenter() {
		return researchCenter;
	}

	public void setResearchCenter(int researchCenter) {
		this.researchCenter = researchCenter;
	}

	public int getBaracks() {
		return baracks;
	}

	public void setBaracks(int baracks) {
		this.baracks = baracks;
	}

	public int getArcheryrange() {
		return archeryrange;
	}

	public void setArcheryrange(int archeryrange) {
		this.archeryrange = archeryrange;
	}

	public int getStables() {
		return stables;
	}

	public void setStables(int stables) {
		this.stables = stables;
	}

	public int getArmory() {
		return armory;
	}

	public void setArmory(int armory) {
		this.armory = armory;
	}

	public int getSiegeworkshop() {
		return siegeworkshop;
	}

	public void setSiegeworkshop(int siegeworkshop) {
		this.siegeworkshop = siegeworkshop;
	}

	public int getWalls() {
		return walls;
	}

	public void setWalls(int walls) {
		this.walls = walls;
	}

	public int getWatchtowers() {
		return watchtowers;
	}

	public void setWatchtowers(int watchtowers) {
		this.watchtowers = watchtowers;
	}

	public int getGates() {
		return gates;
	}

	public void setGates(int gates) {
		this.gates = gates;
	}

	public int getWallsteps() {
		return wallsteps;
	}

	public void setWallsteps(int wallsteps) {
		this.wallsteps = wallsteps;
	}

	public int getUnitCount(UNIT_TYPES type) {
		return unitCount[type.ordinal()];
	}

	public void AddOneUnitCount(UNIT_TYPES type) {
		unitCount[type.ordinal()]++;
	}

	public void RemoveOneUnitCount(UNIT_TYPES type) {
		unitCount[type.ordinal()]--;
	}

	public void setUnitCount(UNIT_TYPES type, int newUnitCount) {
		unitCount[type.ordinal()] = newUnitCount;
	}

	public int getTeamPopulationCap() {
		return teamPopulationCap;
	}

	public void setTeamPopulationCap(int teamPopulationCap) {
		this.teamPopulationCap = teamPopulationCap;
	}

	public int getTeamPlayerCount() {
		return teamPlayerCount;
	}

	public void setTeamPlayerCount(int teamPlayerCount) {
		this.teamPlayerCount = teamPlayerCount;
	}

	public int getMarketplace() {
		return marketplace;
	}

	public void setMarketplace(int marketplace) {
		this.marketplace = marketplace;
	}

}
