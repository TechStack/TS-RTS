package com.projectreddog.tsrts.client.gui;

import com.projectreddog.tsrts.containers.MainMenuContainer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MainMenuScreen extends ContainerScreen<MainMenuContainer> {
	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/barracks_gui.png");
	int ytextOffset = 20;
	int xtextOffset = 5;
	int xTextWidth = 25;
	String teamName = "";

	public MainMenuScreen(MainMenuContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {

		super(screenContainer, inv, titleIn);

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
		GuiUtil.RenderTabsSelected(this, 0);

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

		y = this.guiTop + GuiUtil.TOP_BUTTON_OFFSET;

		int width = 80;
		// x = 0;// x + (width / 2);

		int height = 20;

	}

}
