package com.projectreddog.tsrts.client.gui;

import com.projectreddog.tsrts.containers.MainMenuContainer;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.GenericGuiButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
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

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int x = (this.width - this.xSize) / 2;

		int y = (this.height - this.ySize) / 2;

		this.blit(x, y - 32, 0, 0, this.xSize, this.ySize);

		this.blit(x, y + 23, 0, 0, this.xSize, this.ySize);

	}

	@Override
	protected void init() {
		super.init();
		teamName = Minecraft.getInstance().player.getTeam().getName();

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		y = y + -30;
		int width = 80;
		// x = 0;// x + (width / 2);

		int height = 20;

		addButton(new Button(x, y, width, height, "Economy Buildings", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_MAIN_MENU_ECO));
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Troop Production", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_MAIN_MENU_TROOP_BUILDINGS));
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Defensive Buildings", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_MAIN_MENU_DEFENSE_BUILDINGS));
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Unit Recruitment", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_MAIN_MENU_UNIT_RECRUITMENT));
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Research", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_MAIN_MENU_RESEARCH));
		}));
		y = y + 20;
	}

}
