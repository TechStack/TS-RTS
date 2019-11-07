package com.projectreddog.tsrts.client.gui;

import java.util.Collection;

import com.projectreddog.tsrts.containers.LobbyContainer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.ClientUtilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class LobbyScreen extends ContainerScreen<LobbyContainer> {

	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/lobby_gui.png");

	public LobbyScreen(LobbyContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

		setSize(256, 256);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int x = (this.width - this.xSize) / 2;

		int y = (this.height - this.ySize) / 2;

		ClientUtilities.renderTexture(x, y, 256, 256);
		this.blit(x, y, 0, 0, this.xSize, this.ySize);

		ClientPlayNetHandler clientplaynethandler = Minecraft.getInstance().player.connection;
		Collection<NetworkPlayerInfo> list = clientplaynethandler.getPlayerInfoMap();

		for (NetworkPlayerInfo networkplayerinfo : list) {
			if (networkplayerinfo != null && networkplayerinfo.getGameProfile() != null) {
				Minecraft.getInstance().fontRenderer.drawString(networkplayerinfo.getGameProfile().getName(), x + 5, y + 5, 4210752);
				if (networkplayerinfo.getPlayerTeam() != null) {
					Minecraft.getInstance().fontRenderer.drawString(networkplayerinfo.getPlayerTeam().getName(), x + 50, y + 5, 4210752);
				}

			}

		}

	}

	@Override
	protected void init() {
		super.init();
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		addButton(new ImageButton(0, y, 0, 0, 0, 0, 0, TEXTURE, (button) -> {

		}));

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
