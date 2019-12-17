package com.projectreddog.tsrts.client.gui.widget;

import java.util.ArrayList;
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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HoverImageButton extends ImageButton {
	FontRenderer font;
	ContainerScreen screen;
	TextComponent t;

	public HoverImageButton(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, Button.IPressable onPressIn, String textIn, ContainerScreen screen) {
		super(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, 256, 256, onPressIn, textIn);
		font = Minecraft.getInstance().fontRenderer;
		this.screen = screen;
	}

	@Override
	public void render(int mouseX, int mouseY, float p_render_3_) {
		super.render(mouseX, mouseY, p_render_3_);

		if (mouseX > this.x && mouseX < this.x + this.width) {

			if (mouseY > this.y && mouseY < this.y + this.height) {
				TranslationTextComponent ttc = new TranslationTextComponent(this.getMessage());

				List<String> text = new ArrayList<String>();
				text.add(ttc.getUnformattedComponentText());
				this.renderTooltip(text, mouseX, mouseY);
			}
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
