package com.projectreddog.tsrts.client.gui;

import org.lwjgl.opengl.GL11;

import com.projectreddog.tsrts.client.gui.widget.HoverImageButton;
import com.projectreddog.tsrts.containers.TroopBuildingsContainer;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.init.ModResearch;
import com.projectreddog.tsrts.network.TownHallButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
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

		int yOffset = GuiUtil.GetResourceCostYOffsetValue();
		int y = GuiUtil.GetResourceCostYStartValue(this);
		GuiUtil.drawResourceIconHeaders(this);

		// y = y + yOffset;

		// GuiUtil.drawCosts(this,ModItems.BARRACKSBUILDERITEM, y , xtextOffset, ytextOffset, xTextWidth, teamName);

		GuiUtil.drawCosts(this, ModItems.BARRACKSBUILDERITEM, y, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, ModItems.ARCHERYRANGEBUILDERITEM, y, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, ModItems.STABLESBUILDERITEM, y, teamName);
		y = y + yOffset;
		GuiUtil.drawCosts(this, ModItems.SIEGEWORKSHOPBUILDERITEM, y, teamName);
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

		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(0), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_BARRACKS));
		}, ModItems.BARRACKSBUILDERITEM.getTranslationKey(), this, null, "gui.troopbuilding.barracks.description"));
		y = y + 20;

		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(1), GuiUtil.GetYStartForButtonImageXYIndex(0), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_ARCHERY_RANGE));
		}, ModItems.ARCHERYRANGEBUILDERITEM.getTranslationKey(), this, ModResearch.getResearch("archer"), "gui.troopbuilding.archeryrange.description"));
		y = y + 20;
		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(2), GuiUtil.GetYStartForButtonImageXYIndex(0), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_STABLES));
		}, ModItems.STABLESBUILDERITEM.getTranslationKey(), this, ModResearch.getResearch("lancer"), "gui.troopbuilding.stables.description"));
		y = y + 20;

		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(3), GuiUtil.GetYStartForButtonImageXYIndex(1), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
			ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_SIEGE_WORKSHOP));
		}, ModItems.SIEGEWORKSHOPBUILDERITEM.getTranslationKey(), this, ModResearch.getResearch("siegeworkshop"), "gui.troopbuilding.siegeworkshop.description"));
		y = y + 20;

	}
}
