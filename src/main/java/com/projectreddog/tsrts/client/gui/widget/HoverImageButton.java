package com.projectreddog.tsrts.client.gui.widget;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.client.gui.widget.ResearchButton.ButtonState;
import com.projectreddog.tsrts.utilities.data.Research;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HoverImageButton extends ImageButton {
	FontRenderer font;
	ContainerScreen screen;
	TextComponent t;
	Research research;
	private ButtonState buttonState = ButtonState.NORMAL;
	private String additionalText;

	public HoverImageButton(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, Button.IPressable onPressIn, String textIn, ContainerScreen screen, Research research, String additionalText) {
		this(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, onPressIn, textIn, screen, research);
		this.additionalText = additionalText;
	}

	public HoverImageButton(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, Button.IPressable onPressIn, String textIn, ContainerScreen screen, Research research) {

		super(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, 256, 256, onPressIn, textIn);
		font = Minecraft.getInstance().fontRenderer;
		this.screen = screen;
		this.research = research;
	}

	public void updateButtonState() {
		Team team = Minecraft.getInstance().player.getTeam();
		if (team != null) {
			if (research != null) {
				if (!(research.isUnlocked(team.getName()))) {
					buttonState = ButtonState.LOCKED;
					this.active = false;
				} else {
					buttonState = ButtonState.NORMAL;
					this.active = true;
				}
			}
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float p_render_3_) {
		super.render(mouseX, mouseY, p_render_3_);

		if (this.visible) {
			updateButtonState();

			if (mouseX > this.x && mouseX < this.x + this.width) {

				if (mouseY > this.y && mouseY < this.y + this.height) {
					TranslationTextComponent ttc = new TranslationTextComponent(this.getMessage());

					List<String> text = new ArrayList<String>();
					text.add(ttc.getUnformattedComponentText());

					if (additionalText != null) {
						String[] lines = new TranslationTextComponent(additionalText).getUnformattedComponentText().split("\n");
						for (int i = 0; i < lines.length; i++) {
							if (i == 0) {
								text.add(TextFormatting.BLUE.toString() + lines[i].replace("\r", ""));
							} else {
								text.add(TextFormatting.WHITE.toString() + lines[i].replace("\r", ""));
							}
						}

					}

					this.renderTooltip(text, mouseX, mouseY);
				}
			}
		}

		Minecraft.getInstance().getTextureManager().bindTexture(ResearchButton.STATUS_TEXTURE);

		switch (buttonState) {
		case NORMAL:

			break;

		case LOCKED:
			blit(this.x + 11, this.y + 2, 15, 0, 8, 8, 256, 256);

			break;

		default:
			break;
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
