package com.projectreddog.tsrts.client.gui.widget;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;

public class ResearchButton extends ImageButton {

	public int offsetX;
	public int offsetY;
	public String key;
	public String parentKey;
	public int parentOffsetX;
	public int parentOffsetY;
	private int xTexStart;

	private int yTexStart;
	private int yDiffText;
	private ResourceLocation resourceLocation;
	private int startWidth;
	private int startHeight;
	private int leftOffset;
	private int topOffset;
	private Boolean shouldRender;

	FontRenderer font;
	ContainerScreen screen;
	TextComponent t;

	private ResearchButton(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, Button.IPressable onPressIn, String textIn, ContainerScreen screen) {
		super(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, 256, 256, onPressIn, textIn);
		font = Minecraft.getInstance().fontRenderer;
		this.screen = screen;
	}

	public ResearchButton(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, IPressable onPressIn, String textIn, ContainerScreen screen, int offsetX, int offsetY, String key, String parentKey, int parentOffsetX, int parentOffsetY) {
		this(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, onPressIn, textIn, screen);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.key = key;
		this.parentKey = parentKey;
		this.parentOffsetX = parentOffsetX;
		this.parentOffsetY = parentOffsetY;
		this.xTexStart = xTexStartIn;
		this.yTexStart = yTexStartIn;
		this.yDiffText = yDiffTextIn;
		this.resourceLocation = resourceLocationIn;
		startWidth = this.width;
		startHeight = this.height;
	}

	public void Cull(int left, int top, int right, int bottom) {
		this.width = startWidth;
		this.height = startHeight;
		leftOffset = 0;
		topOffset = 0;
		shouldRender = true;
		if (this.x < left) {
			leftOffset = left - this.x;
			this.width = startWidth - leftOffset;
			this.x = this.x + leftOffset;
		}

		if (this.y < top) {
			topOffset = top - this.y;
			this.height = startHeight - topOffset;
			this.y = this.y + topOffset;
		}

		if (this.x > right) {
			shouldRender = false;
		} else {
			if (this.x + this.width > right) {
				this.width = right - this.x;

			}
		}
		if (this.y > bottom) {
			shouldRender = false;
		} else {
			if (this.y + this.height > bottom) {
				this.height = bottom - this.y;

			}
		}
		if (this.height <= 0) {
			shouldRender = false;
		}
		if (this.width <= 0) {
			shouldRender = false;
		}
		this.visible = shouldRender;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	@Override
	public void render(int mouseX, int mouseY, float p_render_3_) {

		super.render(mouseX, mouseY, p_render_3_);

	}

	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
		if (shouldRender) {
			Minecraft minecraft = Minecraft.getInstance();
			minecraft.getTextureManager().bindTexture(this.resourceLocation);
			GlStateManager.disableDepthTest();
			int i = this.yTexStart;
			if (this.isHovered()) {
				i += this.yDiffText;
			}

			blit(this.x, this.y, (float) this.xTexStart + leftOffset, (float) i + topOffset, this.width, this.height, 256, 256);
			GlStateManager.enableDepthTest();
		}
	}

	public void renderTooltip(List<String> p_renderTooltip_1_, int mouseX, int mouseY) {
		net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(p_renderTooltip_1_, mouseX, mouseY, screen.width, screen.height, -1, font);

		GlStateManager.enableLighting();
		GlStateManager.enableDepthTest();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableRescaleNormal();

	}

}
