package com.projectreddog.tsrts.client.gui.widget;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemImageButton extends Button {

	private ItemStack item;

	public ItemImageButton(int widthIn, int heightIn, int width, int height, String text, IPressable onPress, ItemStack itemIn) {
		super(widthIn, heightIn, width, height, text, onPress);
		item = itemIn;
	}

	public void setPosition(int xIn, int yIn) {
		this.x = xIn;
		this.y = yIn;
	}

	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
		Minecraft minecraft = Minecraft.getInstance();
		GlStateManager.disableDepthTest();

		Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(item, x, y);

		GlStateManager.enableDepthTest();
	}
}
