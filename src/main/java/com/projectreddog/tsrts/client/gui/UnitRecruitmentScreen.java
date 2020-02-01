package com.projectreddog.tsrts.client.gui;

import org.lwjgl.opengl.GL11;

import com.projectreddog.tsrts.client.gui.widget.HoverImageButton;
import com.projectreddog.tsrts.containers.UnitRecruitmentContainer;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.init.ModResearch;
import com.projectreddog.tsrts.network.RecruitTroopButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;
import com.projectreddog.tsrts.utilities.Utilities;
import com.projectreddog.tsrts.utilities.data.Research;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class UnitRecruitmentScreen extends ContainerScreen<UnitRecruitmentContainer> {

	PlayerEntity player;
	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/barracks_gui.png");
	int ytextOffset = 20;
	int xtextOffset = 5;
	int xTextWidth = 25;
	String teamName = "";
	UNIT_TYPES[] pageUnitTypes = new UNIT_TYPES[6];
	int page = 1;

	public UnitRecruitmentScreen(UnitRecruitmentContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
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
//		if (player.getTeam() != null) {
//			String team = player.getTeam().getName();
//			TeamInfo ti = TSRTS.teamInfoArray[TeamEnum.getIDFromName(team)];
//			if (ti.getResearch() != null) {
//				// minion.active = ti.getResearch().soldiers.isUnlocked;
////				archer.active = ti.getResearch().achery.isUnlocked;
//				// lancer.active = ti.getResearch().lancer.isUnlocked;
//				// pikeman.active = ti.getResearch().pikeman.isUnlocked;
//			}
//
//		}

		/// TABS Code:
		GuiUtil.RenderTabsSelected(this, 3);

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

		for (int i = 0; i < pageUnitTypes.length; i++) {
			if (pageUnitTypes[i] != null) {

				GuiUtil.drawCosts(this, Utilities.GetResourceValuesforUnitID(pageUnitTypes[i]), y, teamName);
			}
			y = y + yOffset;
		}

		GL11.glPopMatrix();
	}

	@Override
	protected void init() {
		super.init();
		addButtonsForPage();
	}

	private void clearButtons() {
		this.buttons.clear();
		this.children.clear();

		for (int i = 0; i < pageUnitTypes.length; i++) {
			pageUnitTypes[i] = null;
		}

	}

	private void addButtonsForPage() {
		clearButtons();
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
		int index = 0;
		if (page == 1) {
			addUnitButton(UNIT_TYPES.MINION, y, index);
			y = y + 20;
			index++;
			addUnitButton(UNIT_TYPES.ARCHER, y, index);
			y = y + 20;
			index++;
			addUnitButton(UNIT_TYPES.LANCER, y, index);
			y = y + 20;
			index++;
			addUnitButton(UNIT_TYPES.PIKEMAN, y, index);
			y = y + 20;
			index++;
			addUnitButton(UNIT_TYPES.KNIGHT, y, index);
			y = y + 20;
			index++;
			addUnitButton(UNIT_TYPES.ADVANCED_KNIGHT, y, index);
			y = y + 20;
			index++;

		} else if (page == 2) {

			addUnitButton(UNIT_TYPES.SAPPER, y, index);
			y = y + 20;
			index++;
			addUnitButton(UNIT_TYPES.LONGBOWMAN, y, index);
			y = y + 20;
			index++;
			addUnitButton(UNIT_TYPES.CROSSBOWMAN, y, index);
			y = y + 20;
			index++;
		}

		Button back = addButton(new Button(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, this.guiTop + GuiUtil.TOP_BUTTON_OFFSET + 20 * 6, 20, 20, "<", (button) -> {
			this.page--;
			addButtonsForPage();
		}));

		Button next = addButton(new Button(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET + 100, this.guiTop + GuiUtil.TOP_BUTTON_OFFSET + 20 * 6, 20, 20, ">", (button) -> {
			this.page++;
			addButtonsForPage();
		}));

		if (page == 1) {
			back.active = false;
		} else if (page == 2) {
			next.active = false;

		}

	}

	private void addUnitButton(UNIT_TYPES unitType, int y, int index) {
		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(unitType.getButtonImageX()), GuiUtil.GetYStartForButtonImageXYIndex(unitType.getButtonImageY()), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
			ModNetwork.SendToServer(new RecruitTroopButtonClickedPacketToServer(unitType));
		}, "gui.units." + unitType.getName().toLowerCase(), this, getResearchOrNull(unitType.getResearchKeyRequiredToBuy()), "gui.troop." + unitType.getName().toLowerCase() + ".description"));

		pageUnitTypes[index] = unitType;
	}

	private Research getResearchOrNull(String researchKey) {
		if (researchKey == null) {
			return null;
		} else {
			return ModResearch.getResearch(researchKey);
		}
	}
}
