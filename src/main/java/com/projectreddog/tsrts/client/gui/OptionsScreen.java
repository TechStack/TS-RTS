package com.projectreddog.tsrts.client.gui;

import com.projectreddog.tsrts.containers.OptionsContainer;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.GenericGuiButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.ClientUtilities;
import com.projectreddog.tsrts.utilities.GameOptions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class OptionsScreen extends ContainerScreen<OptionsContainer> {

	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/lobby_gui.png");
	private ResourceLocation RED_TEAM_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/redteambutton.png");

	Button btnBack;
	Button btnSpeedMinus;
	Button btnSpeedPlus;

	Button btnWeatherOff;

	Button btnWeatherOn;
	PlayerEntity playerEntity;

	public OptionsScreen(OptionsContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		playerEntity = inv.player;
		setSize(256, 256);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int x = ((this.width - this.xSize) / 2) - 20;

		int y = ((this.height - this.ySize) / 2) - 30;

		ClientUtilities.renderTexture(x, y, 256, 256);
		this.blit(x, y, 0, 0, this.xSize, this.ySize);

		int OptionNameColumnPosition = x + 5;
		int OptionValueColumnPosition = x + 222;

		Minecraft.getInstance().fontRenderer.drawString(new TranslationTextComponent("gui.options.option").getUnformattedComponentText(), OptionNameColumnPosition, y + 5, 4210752);
		y = y + 25;
		Minecraft.getInstance().fontRenderer.drawString(new TranslationTextComponent("gui.options.speedeffect").getUnformattedComponentText(), OptionNameColumnPosition, y + 5, 4210752);
//TODO: PACKETS ARE NEEDED !
		Minecraft.getInstance().fontRenderer.drawString("" + GameOptions.speedEffectAmount, OptionValueColumnPosition, y + 5, 4210752);

		y = y + 25;
		Minecraft.getInstance().fontRenderer.drawString(new TranslationTextComponent("gui.options.weathercycle").getUnformattedComponentText(), OptionNameColumnPosition, y + 5, 4210752);
//TODO: PACKETS ARE NEEDED !
		Minecraft.getInstance().fontRenderer.drawString((GameOptions.weatherEnabled) ? "On" : "Off", OptionValueColumnPosition, y + 5, 4210752);

	}

	@Override
	protected void init() {
		super.init();
		int x = ((this.width - this.xSize) / 2) - 14;
		int y = ((this.height - this.ySize) / 2);

		int OptionValueColumnPosition = x + 225;

		y = y + 139;

		btnBack = addButton(new Button(x + 196, y + 21, 48, 20, "Back", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_OPTIONS_BACK));

		}));
		btnBack.active = true;

		y = ((this.height - this.ySize) / 2) - 30;

		btnSpeedMinus = addButton(new Button(OptionValueColumnPosition - 20, y + 25, 10, 20, "-", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_OPTIONS_SPEED_MINUS));

		}));
		btnSpeedPlus = addButton(new Button(OptionValueColumnPosition + 10, y + 25, 10, 20, "+", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_OPTIONS_SPEED_PLUS));

		}));
		y = y + 25;

		btnWeatherOff = addButton(new Button(OptionValueColumnPosition - 20, y + 25, 10, 20, "-", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_OPTIONS_WEATHER_OFF));

		}));

		btnWeatherOn = addButton(new Button(OptionValueColumnPosition + 10, y + 25, 10, 20, "+", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_OPTIONS_WEATHER_ON));

		}));

	}
}
