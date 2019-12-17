package com.projectreddog.tsrts.client.gui;

import org.lwjgl.opengl.GL11;

import com.projectreddog.tsrts.containers.TroopBuildingsContainer;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TownHallButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.TeamInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
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
		GuiUtil.RenderTabsSelected(this, 1);

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

		x = ((this.width - this.xSize - (TeamInfo.Resources.values().length * (xtextOffset + xTextWidth)))) + 370;
// DRAW HEAER:
		// this.blit(x - 10, 5, 0, 0, 256, 18);
		RenderHelper.enableGUIStandardItemLighting();
		for (int i = 0; i < res.length; i++) {

			Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, TeamInfo.GetRenderItemStack(res[i]), x, y);
			x = x + xTextWidth;

		}
		y = y + 0;
		// y = y + yOffset;

		// GuiUtil.drawCosts(this,ModItems.BARRACKSBUILDERITEM, y , xtextOffset, ytextOffset, xTextWidth, teamName);

		GuiUtil.drawCosts(this, ModItems.BARRACKSBUILDERITEM, y, xtextOffset, ytextOffset, xTextWidth, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, ModItems.ARCHERYRANGEBUILDERITEM, y, xtextOffset, ytextOffset, xTextWidth, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, ModItems.STABLESBUILDERITEM, y, xtextOffset, ytextOffset, xTextWidth, teamName);
		y = y + yOffset;

		GL11.glPopMatrix();
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
		y = this.guiTop + GuiUtil.TOP_BUTTON_OFFSET;
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
