package com.projectreddog.tsrts.client.gui.toast;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.utilities.AlertToastBackgroundType;

import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AlertToast implements IToast {

	private String title;
	private String subTitle;
	private AlertToastBackgroundType backgroundType;
	private boolean newDisplay;
	private long firstDrawTime;

	public AlertToast(String title, String subTitle, AlertToastBackgroundType backgroundType) {
		super();
		this.title = title;
		this.subTitle = subTitle;
		this.backgroundType = backgroundType;
	}

	@Override
	public Visibility draw(ToastGui toastGui, long delta) {
		if (this.newDisplay) {
			this.firstDrawTime = delta;
			this.newDisplay = false;
		}
		toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
		GlStateManager.color3f(1.0F, 1.0F, 1.0F);
		toastGui.blit(0, 0, 0, 32 * this.backgroundType.ordinal(), 160, 32);
		// draw here

		if (this.subTitle == null) {
			toastGui.getMinecraft().fontRenderer.drawString(new TranslationTextComponent(this.title).getFormattedText(), 30.0F, 12.0F, -16733525);
		} else {
			toastGui.getMinecraft().fontRenderer.drawString(new TranslationTextComponent(this.title).getFormattedText(), 30.0F, 7.0F, -16733525);
			toastGui.getMinecraft().fontRenderer.drawString(new TranslationTextComponent(this.subTitle).getFormattedText(), 30.0F, 18.0F, -16777216);
		}

		return delta - this.firstDrawTime < 5000L ? IToast.Visibility.SHOW : IToast.Visibility.HIDE;
	}

}
