package com.projectreddog.tsrts.utilities;

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

	private int unitCountMinion = 0;
	private int unitCountArcher = 0;
	private int unitCountLancer = 0;
	private int unitCountPikeman = 0;
	private int unitCountTrebuchet = 0;
	private int unitCountKnight = 0;
	private int unitCountAdvancedKnight = 0;
	private int unitCountSapper = 0;
	private int unitCountLongbowmen = 0;
	private int unitCountCrossbowmen = 0;

	private String CurrenResearchKey;
	private int teamPopulationCap;
	private int teamPlayerCount;

	private int currenResearchWorkRemaining;
	private int fullResearchWorkRemaining;

	public TeamInfo() {
		super();
		CurrenResearchKey = "";
	}

	public int getCurrentPopulation() {

		return unitCountMinion + unitCountArcher + unitCountLancer + unitCountPikeman + unitCountTrebuchet + unitCountKnight + unitCountAdvancedKnight + unitCountSapper;
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

	public int getUnitCountMinion() {
		return unitCountMinion;
	}

	public void AddOneUnitCountMinion() {
		this.unitCountMinion++;
	}

	public int getUnitCountSapper() {
		return unitCountSapper;
	}

	public void AddOneUnitCountSapper() {
		this.unitCountSapper++;
	}

	public int getUnitCountLongbowmen() {
		return unitCountLongbowmen;
	}

	public void AddOneUnitCountLongbowmen() {
		this.unitCountLongbowmen++;
	}

	public int getUnitCountCrossbowmen() {
		return unitCountCrossbowmen;
	}

	public void AddOneUnitCountCrossbowmen() {
		this.unitCountCrossbowmen++;
	}

	public int getUnitCountKnight() {
		return unitCountKnight;
	}

	public void AddOneUnitCountKnight() {
		this.unitCountKnight++;
	}

	public int getUnitCountAdvancedKnight() {
		return unitCountAdvancedKnight;
	}

	public void AddOneUnitCountAdvancedKnight() {
		this.unitCountAdvancedKnight++;
	}

	public void AddOneUnitCountArcher() {
		this.unitCountArcher++;
	}

	public void AddOneUnitCountLancer() {
		this.unitCountLancer++;
	}

	public void AddOneUnitCountPikeman() {
		this.unitCountPikeman++;
	}

	public void AddOneUnitCountTrebuchet() {
		this.unitCountTrebuchet++;
	}

	public void RemoveOneUnitCountMinion() {
		this.unitCountMinion--;
	}

	public void RemoveOneUnitCountSapper() {
		this.unitCountSapper--;
	}

	public void RemoveOneUnitCountLongbowmen() {
		this.unitCountLongbowmen--;
	}

	public void RemoveOneUnitCountCrossbowmen() {
		this.unitCountCrossbowmen--;
	}

	public void RemoveOneUnitCountKnight() {
		this.unitCountKnight--;
	}

	public void RemoveOneUnitCountAdvancedKnight() {
		this.unitCountAdvancedKnight--;
	}

	public void RemoveOneUnitCountArcher() {
		this.unitCountArcher--;
	}

	public void RemoveOneUnitCountLancer() {
		this.unitCountLancer--;
	}

	public void RemoveOneUnitCountPikeman() {
		this.unitCountPikeman--;
	}

	public void RemoveOneUnitCountTrebuchet() {
		this.unitCountTrebuchet--;
	}

	public void setUnitCountMinion(int unitCountMinion) {
		this.unitCountMinion = unitCountMinion;
	}

	public void setUnitCountSapper(int unitCountSapper) {
		this.unitCountSapper = unitCountSapper;
	}

	public void setUnitCountLongbowmen(int unitCountLongbowmen) {
		this.unitCountLongbowmen = unitCountLongbowmen;
	}

	public void setUnitCountCrossbowmen(int unitCountCrossbowmen) {
		this.unitCountCrossbowmen = unitCountCrossbowmen;
	}

	public void setUnitCountKnight(int unitCountKnight) {
		this.unitCountKnight = unitCountKnight;
	}

	public void setUnitCountAdvancedKnight(int unitCountAdvancedKnight) {
		this.unitCountAdvancedKnight = unitCountAdvancedKnight;
	}

	public int getUnitCountArcher() {
		return unitCountArcher;
	}

	public void setUnitCountArcher(int unitCountArcher) {
		this.unitCountArcher = unitCountArcher;
	}

	public int getUnitCountLancer() {
		return unitCountLancer;
	}

	public void setUnitCountLancer(int unitCountLancer) {
		this.unitCountLancer = unitCountLancer;
	}

	public int getUnitCountPikeman() {
		return unitCountPikeman;
	}

	public void setUnitCountPikeman(int unitCountPikeman) {
		this.unitCountPikeman = unitCountPikeman;
	}

	public int getUnitCountTrebuchet() {
		return unitCountTrebuchet;
	}

	public void setUnitCountTrebuchet(int unitCountTrebuchet) {
		this.unitCountTrebuchet = unitCountTrebuchet;
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
