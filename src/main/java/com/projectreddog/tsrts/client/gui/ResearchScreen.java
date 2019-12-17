package com.projectreddog.tsrts.client.gui;

import org.lwjgl.opengl.GL11;

import com.projectreddog.tsrts.containers.ResearchContainer;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TownHallButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ResearchScreen extends ContainerScreen<ResearchContainer> {

	PlayerEntity player;
	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/barracks_gui.png");
	int ytextOffset = 20;
	int xtextOffset = 5;
	int xTextWidth = 25;
	String teamName = "";

	public ResearchScreen(ResearchContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		player = inv.player;
		// TABS Code set the width , height and left top!

		this.xSize = 168;
		this.ySize = 167;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		/// TABS Code:
		GuiUtil.RenderTabsBackground(this);

		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int x = (this.width - this.xSize) / 2;

		int y = (this.height - this.ySize) / 2;

		this.blit(x, y, 0, 0, this.xSize, this.ySize);

		drawResourceIcons();

		/// TABS Code:
		GuiUtil.RenderTabsSelected(this, 4);

	}

	/// TABS Code:
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {

		GuiUtil.MouseClick(this, (int) mouseX, (int) mouseY);
		return super.mouseClicked(mouseX, mouseY, button);
	}

	// TABS Code
	@Override
	public void render(int mouseX, int mouseY, float p_render_3_) {
		super.render(mouseX, mouseY, p_render_3_);
		GuiUtil.renderHoveredToolTip(this, mouseX, mouseY);
	}

	protected void drawResourceIcons() {
		GL11.glPushMatrix();
		GL11.glScalef(.5f, .5f, .5f);
		TeamInfo.Resources[] res = TeamInfo.Resources.values();
		int x = 170;
		int yOffset = 40;
		int y = ((this.height - this.ySize - (8 * yOffset))) + GuiUtil.TOP_RESOURCE_OFFSET;

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
		// GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_MINION, y, xtextOffset, ytextOffset, xTextWidth, teamName);

		GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_MINION, y, xtextOffset, ytextOffset, xTextWidth, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_ARCHER, y, xtextOffset, ytextOffset, xTextWidth, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_LANCER, y, xtextOffset, ytextOffset, xTextWidth, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_PIKEMAN, y, xtextOffset, ytextOffset, xTextWidth, teamName);
		y = y + yOffset;
		GL11.glPopMatrix();
	}

	private void drawCosts(ResourceValues rv, int y) {

		int x = ((this.width - this.xSize - (TeamInfo.Resources.values().length * (xtextOffset + xTextWidth)))) + 380;
		// FARM
		int colorWhite = 14737632;
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

	@Override
	protected void init() {
		super.init();
		teamName = Minecraft.getInstance().player.getTeam().getName();

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		y = this.guiTop + GuiUtil.TOP_BUTTON_OFFSET;
		int width = 80;
		// x = 0;// x + (width / 2);

		int height = 20;

		addButton(new Button(x, y, width, height, "R 1", (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_MINION));
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "R2", (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_ARCHER));
		}));
		y = y + 20;
		addButton(new Button(x, y, width, height, "R3", (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_LANCER));
		}));
		y = y + 20;
		addButton(new Button(x, y, width, height, "R4", (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_PIKEMAN));
		}));
		y = y + 20;
	}
}
