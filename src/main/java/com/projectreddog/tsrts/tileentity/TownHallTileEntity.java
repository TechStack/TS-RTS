package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.containers.TownHallContainer;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TownHallTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler {

	public TownHallTileEntity() {
		super(ModBlocks.TOWN_HALL_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {
			// ModEntities.MINION.spawn(world, null, null, this.pos, SpawnReason.TRIGGERED, true, true);

		}
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new TownHallContainer(p_createMenu_1_, this.world, this.getPos(), playerInventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Override
	public void HandleGuiButton(int buttonId, PlayerEntity player) {
		// TSRTS.LOGGER.info("button ID:" + buttonId);

		if (buttonId == Reference.GUI_BUTTON_BUY_BARRACKS) {
			Utilities.PlayerBuysItem(player, new ItemStack(Item.BLOCK_TO_ITEM.get(ModBlocks.BARRACKS_BLOCK)));
		} else if (buttonId == Reference.GUI_BUTTON_BUY_ARCHERY_RANGE) {
			Utilities.PlayerBuysItem(player, new ItemStack(Item.BLOCK_TO_ITEM.get(ModBlocks.ARCHERY_RANGE_BLOCK)));
		} else if (buttonId == Reference.GUI_BUTTON_BUY_MINE_SITE) {
			Utilities.PlayerBuysItem(player, new ItemStack(Item.BLOCK_TO_ITEM.get(ModBlocks.MINE_SITE_BLOCK)));

		}

	}

}
