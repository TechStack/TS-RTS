package com.projectreddog.tsrts.client.gui;

import org.lwjgl.opengl.GL11;

import com.projectreddog.tsrts.client.gui.widget.HoverImageButton;
import com.projectreddog.tsrts.containers.UnitRecruitmentContainer;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.init.ModResearch;
import com.projectreddog.tsrts.network.TownHallButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;

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

		if (page == 1) {
			GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_MINION, y, teamName);
			y = y + yOffset;
			GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_ARCHER, y, teamName);
			y = y + yOffset;
			GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_LANCER, y, teamName);
			y = y + yOffset;
			GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_PIKEMAN, y, teamName);
			y = y + yOffset;
//		GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_TREBUCHET, y, teamName);
//		y = y + yOffset;

			GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_KNIGHT, y, teamName);
			y = y + yOffset;
			GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_ADVANCED_KNIGHT, y, teamName);
			y = y + yOffset;
		} else if (page == 2) {
			GuiUtil.drawCosts(this, Config.CONFIG_UNIT_COSTS_SAPPER, y, teamName);
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

		if (page == 1) {

			addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(3), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
				ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_MINION));
			}, "gui.units.minion", this, null, "gui.troop.minion.description"));
			y = y + 20;

			addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(1), GuiUtil.GetYStartForButtonImageXYIndex(3), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
				ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_ARCHER));
			}, "gui.units.archer", this, ModResearch.getResearch("archer"), "gui.troop.archer.description"));
			y = y + 20;
			addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(2), GuiUtil.GetYStartForButtonImageXYIndex(3), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
				ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_LANCER));
			}, "gui.units.lancer", this, ModResearch.getResearch("lancer"), "gui.troop.lancer.description"));
			y = y + 20;
			addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(3), GuiUtil.GetYStartForButtonImageXYIndex(3), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
				ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_PIKEMAN));
			}, "gui.units.pikeman", this, ModResearch.getResearch("pikeman"), "gui.troop.pikeman.description"));
			y = y + 20;

//	pikeman = addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(3), GuiUtil.GetYStartForButtonImageXYIndex(3), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
//		ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_TREBUCHET));
//	}, "gui.units.trebuchet", this, ModResearch.getResearch("trebuchet")));
//	y = y + 20;

			addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(5), GuiUtil.GetYStartForButtonImageXYIndex(3), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
				ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_KNIGHT));
			}, "gui.units.knight", this, ModResearch.getResearch("armor"), "gui.troop.knight.description"));
			y = y + 20;

			addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(6), GuiUtil.GetYStartForButtonImageXYIndex(3), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
				ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_DIAMOND_KNIGHT));
			}, "gui.units.advancedknight", this, ModResearch.getResearch("advcedarmor"), "gui.troop.advancedknight.description"));
			y = y + 20;

		} else if (page == 2) {

			addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(4), GuiUtil.GetYStartForButtonImageXYIndex(1), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
				ModNetwork.SendToServer(new TownHallButtonClickedPacketToServer(Reference.GUI_BUTTON_BUY_SAPPER));
			}, "gui.units.sapper", this, ModResearch.getResearch("siegeworkshop"), "gui.troop.sapper.description"));
			y = y + 20;
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
}
