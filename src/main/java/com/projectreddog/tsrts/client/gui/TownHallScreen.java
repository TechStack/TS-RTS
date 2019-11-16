package com.projectreddog.tsrts.client.gui;

import com.projectreddog.tsrts.containers.TownHallContainer;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TeGuiButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class TownHallScreen extends ContainerScreen<TownHallContainer> {

	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/barracks_gui.png");
	BlockPos pos;

	public TownHallScreen(TownHallContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
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

		/*
		 * public static final int GUI_BUTTON_BUY_BARRACKS = 1; public static final int GUI_BUTTON_BUY_ARCHERY_RANGE = 2;
		 * 
		 * public static final int GUI_BUTTON_BUY_LUMBER_YARD = 3; public static final int GUI_BUTTON_BUY_MINE_SITE_STONE = 4; public static final int GUI_BUTTON_BUY_MINE_SITE_IRON = 5; public static final int GUI_BUTTON_BUY_MINE_SITE_GOLD = 6; public static final int GUI_BUTTON_BUY_MINE_SITE_DIAMOND = 7; public static final int GUI_BUTTON_BUY_MINE_SITE_EMERALD = 8;
		 */
		y = y + 5;
		int width = 90;
		x = x + (width / 2);

		int height = 20;

		addButton(new Button(x, y, width, height, "Farm", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_BUY_FARM));
			this.onClose();
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Lumber Yard", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_BUY_LUMBER_YARD));
			this.onClose();
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Stone Mine Site", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_BUY_MINE_SITE_STONE));
			this.onClose();
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Iron Mine Site", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_BUY_MINE_SITE_IRON));
			this.onClose();
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Gold Mine Site", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_BUY_MINE_SITE_GOLD));
			this.onClose();
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Diamond Mine Site", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_BUY_MINE_SITE_DIAMOND));
			this.onClose();
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Barracks", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_BUY_BARRACKS));
			this.onClose();
		}));
		y = y + 20;

		addButton(new Button(x, y, width, height, "Arrchery Range", (button) -> {
			ModNetwork.SendToServer(new TeGuiButtonClickedPacketToServer(pos.getX(), pos.getY(), pos.getZ(), Reference.GUI_BUTTON_BUY_ARCHERY_RANGE));
			this.onClose();
		}));
		y = y + 20;
	}
}
