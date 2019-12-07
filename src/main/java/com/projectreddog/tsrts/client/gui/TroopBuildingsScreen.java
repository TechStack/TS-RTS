package com.projectreddog.tsrts.client.gui;

import org.lwjgl.opengl.GL11;

import com.projectreddog.tsrts.containers.TroopBuildingsContainer;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TownHallButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TroopBuildingsScreen extends ContainerScreen<TroopBuildingsContainer> {

	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/barracks_gui.png");
	int ytextOffset = 20;
	int xtextOffset = 5;
	int xTextWidth = 25;
	String teamName = "";

	public TroopBuildingsScreen(TroopBuildingsContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int x = (this.width - this.xSize) / 2;

		int y = (this.height - this.ySize) / 2;

		this.blit(x, y - 32, 0, 0, this.xSize, this.ySize);

		this.blit(x, y + 23, 0, 0, this.xSize, this.ySize);

		drawResourceIcons();
	}

	protected void drawResourceIcons() {
		GL11.glPushMatrix();
		GL11.glScalef(.5f, .5f, .5f);
		TeamInfo.Resources[] res = TeamInfo.Resources.values();
		int x = 170;
		int yOffset = 40;
		int y = ((this.height - this.ySize - (8 * yOffset))) + 257;

		x = ((this.width - this.xSize - (TeamInfo.Resources.values().length * (xtextOffset + xTextWidth)))) + 380;
// DRAW HEAER:
		// this.blit(x - 10, 5, 0, 0, 256, 18);
		RenderHelper.enableGUIStandardItemLighting();
		for (int i = 0; i < res.length; i++) {

			Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, TeamInfo.GetRenderItemStack(res[i]), x, y);
			x = x + xTextWidth;

		}
		y = y + 0;
		// y = y + yOffset;

		drawCosts(ModItems.BARRACKSBUILDERITEM, y);
		y = y + yOffset;
		drawCosts(ModItems.ARCHERYRANGEBUILDERITEM, y);
		y = y + yOffset;
		drawCosts(ModItems.STABLESBUILDERITEM, y);
		y = y + yOffset;

		GL11.glPopMatrix();
	}

	private void drawCosts(Item item, int y) {

		int x = ((this.width - this.xSize - (TeamInfo.Resources.values().length * (xtextOffset + xTextWidth)))) + 380;
		// FARM
		int colorWhite = 14737632;
		int colorRed = 11141120;
		int color = colorWhite;
		x = x + xtextOffset;
		int cost = Utilities.getFoodCostsForBuilder(item);
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.FOOD, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = Utilities.getWoodCostsForBuilder(item);
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.WOOD, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = Utilities.getStoneCostsForBuilder(item);
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.STONE, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = Utilities.getIronCostsForBuilder(item);
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.IRON, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = Utilities.getGoldCostsForBuilder(item);
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.GOLD, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = Utilities.getDiamondCostsForBuilder(item);
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.DIAMOND, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;
		cost = Utilities.getEmeraldCostsForBuilder(item);
		if (Utilities.hasNeededResource(teamName, TeamInfo.Resources.EMERALD, cost)) {
			color = colorWhite;
		} else {
			color = colorRed;
		}
		Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + cost, x, y + ytextOffset, color);

		x = x + xTextWidth;

	}

	@Override
	protected void init() {
		super.init();
		teamName = Minecraft.getInstance().player.getTeam().getName();

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		/*
		 * public static final int GUI_BUTTON_BUY_BARRACKS = 1; public static final int GUI_BUTTON_BUY_ARCHERY_RANGE = 2;
		 * 
		 * public static final int GUI_BUTTON_BUY_LUMBER_YARD = 3; public static final int GUI_BUTTON_BUY_MINE_SITE_STONE = 4; public static final int GUI_BUTTON_BUY_MINE_SITE_IRON = 5; public static final int GUI_BUTTON_BUY_MINE_SITE_GOLD = 6; public static final int GUI_BUTTON_BUY_MINE_SITE_DIAMOND = 7; public static final int GUI_BUTTON_BUY_MINE_SITE_EMERALD = 8;
		 */
		y = y + -30;
		int width = 80;
		// x = 0;// x + (width / 2);

		int height = 20;

		addButton(new Button(x, y, width, height, "Barracks", (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_BARRACKS));
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Archery Range", (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_ARCHERY_RANGE));
		}));
		y = y + 20;
		addButton(new Button(x, y, width, height, "Stables", (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_STABLES));
		}));
		y = y + 20;

	}
}
