package com.projectreddog.tsrts.client.gui;

import com.projectreddog.tsrts.containers.ResearchContainer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class ResearchScreen extends ContainerScreen<ResearchContainer> {

	PlayerEntity player;
	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/barracks_gui.png");
	private ResourceLocation TEXTURE_WIDGETS = new ResourceLocation(Reference.MODID, "textures/gui/guiwidgets.png");

	int ytextOffset = 20;
	int xtextOffset = 5;
	int xTextWidth = 25;
	String teamName = "";

	float currentScrollAmount = 0;

	float totalScrollUnits = 150 - 15;

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

		/// TABS Code:
		GuiUtil.RenderTabsSelected(this, 4);

		// DRAW THe scroll bar bits
		this.minecraft.getTextureManager().bindTexture(TEXTURE_WIDGETS);

		int xOffset = (this.xSize - 152) / 2;
		// scroll trench
		this.blit(this.guiLeft + xOffset - 1, this.guiTop + this.ySize - 13 - 6, 0, 24, 152, 14);

		// Scroll indicator
		this.blit(this.guiLeft + xOffset + (int) currentScrollAmount, this.guiTop + this.ySize - 13 - 5, 0, 0, 15, 12);

	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollAmount) {

		currentScrollAmount = (float) (currentScrollAmount - scrollAmount);
		currentScrollAmount = MathHelper.clamp(currentScrollAmount, 0, totalScrollUnits);
		return true;
	}

	/// TABS Code:
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {

		GuiUtil.MouseClick(this, (int) mouseX, (int) mouseY);
		moveScrollIndicatorToMouse(mouseX, mouseY, button);
		return super.mouseClicked(mouseX, mouseY, button);

	}

	private void moveScrollIndicatorToMouse(double mouseX, double mouseY, int button) {
		if (button == 0) {
			if (mouseX > this.guiLeft + (this.xSize - 152) / 2 - 1 && mouseX < this.guiLeft + (this.xSize - 152) / 2 - 1 + 152) {

				/// in the scroll bar trench area on the X axis

				if (mouseY > this.guiTop + this.ySize - 13 - 5 && mouseY < this.guiTop + this.ySize - 13 - 6 + 14 - 2) {

					currentScrollAmount = (float) ((mouseX - (this.guiLeft + (this.xSize - 152) / 2)));

					currentScrollAmount = MathHelper.clamp(currentScrollAmount, 0, totalScrollUnits);

				}

			}
		}
	}

	public boolean mouseDragged(double mouseX, double mouseY, int button, double p_mouseDragged_6_, double p_mouseDragged_8_) {
		moveScrollIndicatorToMouse(mouseX, mouseY, button);

		return super.mouseDragged(mouseX, mouseY, button, p_mouseDragged_6_, p_mouseDragged_8_);

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
