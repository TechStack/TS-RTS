package com.projectreddog.tsrts.client.gui;

import org.lwjgl.opengl.GL11;

import com.projectreddog.tsrts.containers.ResearchContainer;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
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

		int yOffset = GuiUtil.GetResourceCostYOffsetValue();
		int y = GuiUtil.GetResourceCostYStartValue(this);
		GuiUtil.drawResourceIconHeaders(this);

		// y = y + yOffset;
		// GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_MINION, y, xtextOffset, ytextOffset, xTextWidth, teamName);

		GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_MINION, y, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_ARCHER, y, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_LANCER, y, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_PIKEMAN, y, teamName);
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

//		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(5), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
//			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_MINION));
//		}, "Minion", this));
//		y = y + 20;
//
//		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(5), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
//			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_ARCHER));
//		}, "Minion", this));
//		y = y + 20;
//		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(5), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
//			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_LANCER));
//		}, "Minion", this));
//		y = y + 20;
//		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(5), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
//			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_PIKEMAN));
//		}, "Minion", this));
//		y = y + 20;
	}
}
