package com.projectreddog.tsrts.client.gui;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.client.gui.widget.HoverImageButton;
import com.projectreddog.tsrts.containers.TeamOptionsContainer;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TeamOptionButtonToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.reference.Reference.TEAM_OPTION_BUTTONS;
import com.projectreddog.tsrts.utilities.TeamEnum;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TeamOptionsScreen extends ContainerScreen<TeamOptionsContainer> {

	PlayerEntity player;
	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/barracks_gui.png");
	int ytextOffset = 20;
	int xtextOffset = 5;
	int xTextWidth = 25;
	String teamName = "";

	public TeamOptionsScreen(TeamOptionsContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		player = inv.player;
		// TABS Code set the width , height and left top!

		this.xSize = 196;
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

		/// TABS Code:
		GuiUtil.RenderTabsSelected(this, 5);
		y = this.guiTop + GuiUtil.TOP_BUTTON_OFFSET;
		if (player.getTeam() != null) {
			int teamIndex = TeamEnum.getIDFromName(player.getTeam().getName());

			Minecraft.getInstance().fontRenderer.drawStringWithShadow(TSRTS.TeamQueues[teamIndex].isInfinateBarracksQueue() ? "On" : "Off", this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET + 30, y + 5, 16777215);
			y = y + 20;

			Minecraft.getInstance().fontRenderer.drawStringWithShadow(TSRTS.TeamQueues[teamIndex].isInfinateArcheryRangeQueue() ? "On" : "Off", this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET + 30, y + 5, 16777215);
			y = y + 20;

			Minecraft.getInstance().fontRenderer.drawStringWithShadow(TSRTS.TeamQueues[teamIndex].isInfinateStablesQueue() ? "On" : "Off", this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET + 30, y + 5, 16777215);
			y = y + 20;

			Minecraft.getInstance().fontRenderer.drawStringWithShadow(TSRTS.TeamQueues[teamIndex].isInfinateSiegeWorkshopQueue() ? "On" : "Off", this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET + 30, y + 5, 16777215);
			y = y + 20;

		}

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

		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(2), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
			ModNetwork.SendToServer(new TeamOptionButtonToServer(TEAM_OPTION_BUTTONS.INFINATE_BARRACKS_QUEUE));
		}, "gui.options.infinite.barracks", this, null));
		y = y + 20;

		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(2), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
			ModNetwork.SendToServer(new TeamOptionButtonToServer(TEAM_OPTION_BUTTONS.INFINANTE_ARCHERY_RANGE_QUEUE));
		}, "gui.options.infinite.archeryrange", this, null));
		y = y + 20;
		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(2), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
			ModNetwork.SendToServer(new TeamOptionButtonToServer(TEAM_OPTION_BUTTONS.INFINANTE_STABLES_QUEUE));
		}, "gui.options.infinite.stables", this, null));
		y = y + 20;
		addButton(new HoverImageButton(this.guiLeft + GuiUtil.LEFT_BUTTON_OFFSET, y, 20, 18, GuiUtil.GetXStartForButtonImageXYIndex(0), GuiUtil.GetYStartForButtonImageXYIndex(2), 19, GuiUtil.BUTTON_TEXTURE, (button) -> {
			ModNetwork.SendToServer(new TeamOptionButtonToServer(TEAM_OPTION_BUTTONS.INFINANTE_SIEGE_WORKSHOP_QUEUE));
		}, "gui.options.infinite.siegeworkshop", this, null));
		y = y + 20;

	}
}
