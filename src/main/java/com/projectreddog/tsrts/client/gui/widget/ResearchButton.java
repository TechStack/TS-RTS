package com.projectreddog.tsrts.client.gui.widget;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.ResourceLocation;

public class ResearchButton extends HoverImageButton {

	public int offsetX;
	public int offsetY;

	public ResearchButton(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, IPressable onPressIn, String textIn, ContainerScreen screen, int offsetX, int offsetY) {
		super(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, onPressIn, textIn, screen);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

}
