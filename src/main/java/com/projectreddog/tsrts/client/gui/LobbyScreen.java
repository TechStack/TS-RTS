package com.projectreddog.tsrts.client.gui;

import java.util.Collection;

import com.projectreddog.tsrts.containers.LobbyContainer;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.GenericGuiButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.ClientUtilities;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class LobbyScreen extends ContainerScreen<LobbyContainer> {

	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/lobby_gui.png");
	private ResourceLocation RED_TEAM_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/redteambutton.png");
	Button btnRed;
	Button btnBlue;
	Button btnGreen;
	Button btnYellow;
	Button btnReady;
	Button btnStart;
	Button btnSpectate;
	PlayerEntity playerEntity;

	public LobbyScreen(LobbyContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		playerEntity = inv.player;
		setSize(256, 256);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int x = ((this.width - this.xSize) / 2) - 20;

		int y = ((this.height - this.ySize) / 2) - 20;

		ClientUtilities.renderTexture(x, y, 256, 256);
		this.blit(x, y, 0, 0, this.xSize, this.ySize);

		ClientPlayNetHandler clientplaynethandler = Minecraft.getInstance().player.connection;
		Collection<NetworkPlayerInfo> list = clientplaynethandler.getPlayerInfoMap();

		int playerNameColumnPosition = x + 5;
		int playerTeamColumnPosition = playerNameColumnPosition + 100;
		int playerReadyColumnPosition = playerTeamColumnPosition + 50;
		Boolean isEveryoneReady = true;
		for (NetworkPlayerInfo networkplayerinfo : list) {
			if (networkplayerinfo != null && networkplayerinfo.getGameProfile() != null) {
				String playerName = networkplayerinfo.getGameProfile().getName();

				Minecraft.getInstance().fontRenderer.drawString(playerName, playerNameColumnPosition, y + 5, 4210752);
				if (networkplayerinfo.getPlayerTeam() != null) {
					Minecraft.getInstance().fontRenderer.drawString(networkplayerinfo.getPlayerTeam().getColor() + networkplayerinfo.getPlayerTeam().getName(), playerTeamColumnPosition, y + 5, 4210752);

				}

				Minecraft.getInstance().fontRenderer.drawString(Utilities.getPlayerReady(playerName) ? "Ready!" : "Not Ready", playerReadyColumnPosition, y + 5, 4210752);
				if (!Utilities.getPlayerReady(playerName)) {
					isEveryoneReady = false;
				}
			}
			y = y + 15;

		}

		if (isEveryoneReady) {
			btnStart.active = true;
		} else {
			btnStart.active = false;
		}

	}

	@Override
	protected void init() {
		super.init();
		int x = ((this.width - this.xSize) / 2) - 20;
		int y = ((this.height - this.ySize) / 2);

		y = y + 170;

		btnReady = addButton(new Button(x + 200, y, 50, 20, "Ready", (button) -> {

			if (!Utilities.getPlayerReady(playerEntity)) {
				btnRed.active = false;
				btnBlue.active = false;
				btnGreen.active = false;
				btnYellow.active = false;
				btnSpectate.active = false;
				btnReady.setMessage("UnReady");
			} else {
				btnRed.active = true;
				btnBlue.active = true;
				btnGreen.active = true;
				btnYellow.active = true;
				btnSpectate.active = true;
				btnReady.active = false;
				btnReady.setMessage("Ready");
			}
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_LOBBY_READY));
		}));

		btnReady.active = false;

		btnRed = addButton(new Button(x, y, 50, 20, "Red", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_LOBBY_RED));
			btnReady.active = true;
		}));

		btnBlue = addButton(new Button(x + 50, y, 50, 20, "Blue", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_LOBBY_BLUE));
			btnReady.active = true;

		}));

		btnGreen = addButton(new Button(x + 100, y, 50, 20, "Green", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_LOBBY_GREEN));
			btnReady.active = true;

		}));

		btnYellow = addButton(new Button(x + 150, y, 50, 20, "Yellow", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_LOBBY_YELLOW));
			btnReady.active = true;

		}));

		btnStart = addButton(new Button(x + 200, y + 20, 50, 20, "Start", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_LOBBY_START));

		}));
		btnStart.active = false;

		btnSpectate = addButton(new Button(x, y + 20, 50, 20, "Spectate", (button) -> {
			ModNetwork.SendToServer(new GenericGuiButtonClickedPacketToServer(Reference.GUI_BUTTON_LOBBY_SEPECTATE));

		}));
		btnSpectate.active = true;

//		addButton(new Button(x, y, 50, 10, "Y", (button) -> {
//			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_DEBUG_TESTERYELLOW));
//		}));
//
//		addButton(new Button(x, y + 10, 50, 10, "G", (button) -> {
//			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_DEBUG_TESTERGREEN));
//		}));
//
//		addButton(new Button(x, y + 20, 50, 10, "R", (button) -> {
//			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_DEBUG_TESTERRED));
//		}));
//
//		addButton(new Button(x, y + 30, 50, 10, "B", (button) -> {
//			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_DEBUG_TESTERBLUE));
//		}));
	}
}
