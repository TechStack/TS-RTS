package com.projectreddog.tsrts.client.gui;

import com.projectreddog.tsrts.containers.GarrisonContainer;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TeGuiButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class GarrisonScreen extends ContainerScreen<GarrisonContainer> {

	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/garrison_gui.png");
	BlockPos pos;

	public GarrisonScreen(GarrisonContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		pos = screenContainer.pos;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int x = (this.width - this.xSize) / 2;

		int y = (this.height - this.ySize) / 2;

		this.blit(x, y, 0, 0, this.xSize, this.ySize);

	}

	@Override
	protected void init() {
		super.init();
		int x = (this.width - this.xSize) / 2;

		int y = (this.height - this.ySize) / 2;
		addButton(new Button(x, y, 50, 10, "Y", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_DEBUG_TESTERYELLOW));
		}));

		addButton(new Button(x, y + 10, 50, 10, "G", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_DEBUG_TESTERGREEN));
		}));

		addButton(new Button(x, y + 20, 50, 10, "R", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_DEBUG_TESTERRED));
		}));

		addButton(new Button(x, y + 30, 50, 10, "B", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_DEBUG_TESTERBLUE));
		}));
	}
}
