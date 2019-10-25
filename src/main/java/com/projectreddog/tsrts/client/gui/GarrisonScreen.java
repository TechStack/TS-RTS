package com.projectreddog.tsrts.client.gui;

import com.projectreddog.tsrts.containers.GarrisonContainer;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TeGuiButtonClickedPacket;
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
		addButton(new Button(x, y, 50, 10, "TEST", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacket(pos.getX(), pos.getY(), pos.getZ(), 1));
		}));

	}
}
