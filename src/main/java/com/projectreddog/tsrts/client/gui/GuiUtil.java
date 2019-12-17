package com.projectreddog.tsrts.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.GenericGuiButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class GuiUtil {
	private static final ResourceLocation TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
	public static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Reference.MODID + ":" + "textures/gui/buttonimages.png");
	public static final int TOP_BUTTON_OFFSET = 20;
	public static final int LEFT_BUTTON_OFFSET = 20;

	public static final int TOP_RESOURCE_OFFSET = 330;
	public static final int ytextOffset = 20;
	public static final int xtextOffset = 5;
	public static final int xTextWidth = 25;
	private static final int yOffset = 40;
	/*
	 * public static final int GUI_BUTTON_MAIN_MENU_ECO = 10; public static final int GUI_BUTTON_MAIN_MENU_TROOP_BUILDINGS = 11; public static final int GUI_BUTTON_MAIN_MENU_DEFENSE_BUILDINGS = 12; public static final int GUI_BUTTON_MAIN_MENU_UNIT_RECRUITMENT = 13; public static final int GUI_BUTTON_MAIN_MENU_RESEARCH = 15;
	 */

	private static final int TAB_COUNT = 5;

	public static int tabIndexToGUiID(int tabIndex) {
		switch (tabIndex) {
		case 0:
			return Reference.GUI_BUTTON_MAIN_MENU_ECO;
		case 1:
			return Reference.GUI_BUTTON_MAIN_MENU_TROOP_BUILDINGS;
		case 2:
			return Reference.GUI_BUTTON_MAIN_MENU_DEFENSE_BUILDINGS;
		case 3:
			return Reference.GUI_BUTTON_MAIN_MENU_UNIT_RECRUITMENT;
		case 4:
			return Reference.GUI_BUTTON_MAIN_MENU_RESEARCH;
		default:
			return -1;
		}
	}

	public static void RenderTabsBackground(ContainerScreen screen) {
		screen.getMinecraft().getTextureManager().bindTexture(TABS);
		screen.blit(screen.getGuiLeft(), screen.getGuiTop() - 28, 0, 0, TAB_COUNT * 28, 32);

		for (int i = 0; i < TAB_COUNT; i++) {
			Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, GetIconForTabIndex(i), screen.getGuiLeft() + i * 28 + 6, screen.getGuiTop() - 28 + 10);
		}

	}

	public static void RenderTabsSelected(ContainerScreen screen, int currentTabIndex) {
		screen.getMinecraft().getTextureManager().bindTexture(TABS);

		screen.blit(screen.getGuiLeft() + currentTabIndex * 28, screen.getGuiTop() - 28, currentTabIndex * 28, 32, 28, 32);
		RenderHelper.enableGUIStandardItemLighting();
		Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, GetIconForTabIndex(currentTabIndex), screen.getGuiLeft() + currentTabIndex * 28 + 6, screen.getGuiTop() - 28 + 10);

	}

	public static ItemStack GetIconForTabIndex(int tabIndex) {

		switch (tabIndex) {
		case 0:
			return new ItemStack(ModBlocks.FARM_BLOCK);
		case 1:
			return new ItemStack(ModBlocks.BARRACKS_BLOCK);
		case 2:
			return new ItemStack(ModBlocks.WALL_BLOCK);
		case 3:
			return new ItemStack(Items.DIAMOND_HELMET);
		case 4:
			return new ItemStack(Items.WRITABLE_BOOK);
		default:
			return ItemStack.EMPTY;
		}
	}

	public static void renderHoveredToolTip(ContainerScreen screen, int mouseX, int mouseY) {
		int tabIndex = getTabIndexFromXY(screen, mouseX, mouseY);
		if (tabIndex > -1) {
			// TSRTS.LOGGER.info("tab Index: " + tabIndex);

			screen.renderTooltip(GetToolTipText(tabIndex), mouseX, mouseY);

		}

	}

	public static List<String> GetToolTipText(int tabIndex) {
		List<String> toolTip = new ArrayList<String>();
		switch (tabIndex) {
		case 0:
			toolTip.add("Eco");
			return toolTip;
		case 1:
			toolTip.add("Troop Production Buildings");
			return toolTip;

		case 2:
			toolTip.add("Defensive Buildings");
			return toolTip;

		case 3:
			toolTip.add("Recruit Troops");
			return toolTip;

		case 4:
			toolTip.add("Research");
			return toolTip;

		default:
			return toolTip;
		}
	}

	public static void MouseClick(ContainerScreen screen, int mouseX, int mouseY) {
		int tabIndex = getTabIndexFromXY(screen, mouseX, mouseY);
		if (tabIndex > -1) {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(tabIndexToGUiID(tabIndex)));
		}
	}

	public static int getTabIndexFromXY(ContainerScreen screen, int mouseX, int mouseY) {
		if (mouseY <= screen.getGuiTop() && mouseY >= screen.getGuiTop() - 32) {
			// correct Y value
			if (mouseX >= screen.getGuiLeft() && mouseX <= screen.getGuiLeft() + screen.getXSize()) {
				// over tab row
				//
				int tabIndex = (mouseX - screen.getGuiLeft()) / 28;
				return tabIndex;
			}
		}
		return -1;
	}

	public static int GetResourceCostYStartValue(ContainerScreen screen) {

		return ((screen.height - screen.getYSize() - (8 * yOffset))) + GuiUtil.TOP_RESOURCE_OFFSET + 20;
	}

	public static int GetResourceCostYOffsetValue() {
		return yOffset;
	}

	public static void drawResourceIconHeaders(ContainerScreen screen) {
		TeamInfo.Resources[] res = TeamInfo.Resources.values();
		int x = GetResourceXCord(screen);
		int y = GetResourceCostYStartValue(screen) - 15;

// DRAW HEAER:
		// this.blit(x - 10, 5, 0, 0, 256, 18);
		RenderHelper.enableGUIStandardItemLighting();
		for (int i = 0; i < res.length; i++) {

			Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, TeamInfo.GetRenderItemStack(res[i]), x, y);
			x = x + xTextWidth;

		}
	}

	private static int GetResourceXCord(ContainerScreen screen) {
		return ((screen.width - screen.getXSize() - (TeamInfo.Resources.values().length * (xtextOffset + xTextWidth)))) + 280 + LEFT_BUTTON_OFFSET;
	}

	public static void drawCosts(ContainerScreen screen, ResourceValues rv, int y, String teamName) {

		int x = GetResourceXCord(screen);
		// FARM
		int colorWhite = 16777215;
		int colorRed = 11141120;
		int color = colorWhite;
		x = x + xtextOffset;
		int cost = rv.getFOOD();
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.FOOD, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = rv.getWOOD();
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.WOOD, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = rv.getSTONE();
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.STONE, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = rv.getIRON();
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.IRON, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = rv.getGOLD();
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.GOLD, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = rv.getDIAMOND();
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.DIAMOND, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = rv.getEMERALD();
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.EMERALD, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;

	}

	public static void drawCosts(ContainerScreen screen, Item item, int y, String teamName) {

		int foodCost = Utilities.getFoodCostsForBuilder(item);
		int woodCost = Utilities.getWoodCostsForBuilder(item);
		int stoneCost = Utilities.getStoneCostsForBuilder(item);
		int ironCost = Utilities.getIronCostsForBuilder(item);
		int goldCost = Utilities.getGoldCostsForBuilder(item);
		int diamondCost = Utilities.getDiamondCostsForBuilder(item);
		int emeraldCost = Utilities.getEmeraldCostsForBuilder(item);
		ResourceValues rv = new ResourceValues(foodCost, woodCost, stoneCost, ironCost, goldCost, diamondCost, emeraldCost);
		drawCosts(screen, rv, y, teamName);
	}

	public static int GetXStartForButtonImageXYIndex(int x) {
		return x * 20;
	}

	public static int GetYStartForButtonImageXYIndex(int y) {
		return y * 38;
	}
}
