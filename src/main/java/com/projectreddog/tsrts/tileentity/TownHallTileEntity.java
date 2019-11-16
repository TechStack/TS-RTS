package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.containers.TownHallContainer;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
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

			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.FOOD, Config.CONFIG_TOWN_HALL_GENERATE_FOOD.get());
			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.WOOD, Config.CONFIG_TOWN_HALL_GENERATE_WOOD.get());
			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.STONE, Config.CONFIG_TOWN_HALL_GENERATE_STONE.get());
			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.IRON, Config.CONFIG_TOWN_HALL_GENERATE_IRON.get());
			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.GOLD, Config.CONFIG_TOWN_HALL_GENERATE_GOLD.get());
			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.DIAMOND, Config.CONFIG_TOWN_HALL_GENERATE_DIAMOND.get());
			Utilities.AddResourcesToTeam(this.getTeam().getName(), TeamInfo.Resources.EMERALD, Config.CONFIG_TOWN_HALL_GENERATE_EMERALD.get());

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
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.BARRACKSBUILDERITEM));
		} else if (buttonId == Reference.GUI_BUTTON_BUY_ARCHERY_RANGE) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.ARCHERYRANGEBUILDERITEM));
		} else if (buttonId == Reference.GUI_BUTTON_BUY_MINE_SITE_STONE) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.MINESITESTONEBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_MINE_SITE_IRON) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.MINESITEIRONBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_MINE_SITE_GOLD) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.MINESITEGOLDBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_MINE_SITE_DIAMOND) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.MINESITEDIAMONDBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_LUMBER_YARD) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.LUMBERYARDBUILDERITEM));

		} else if (buttonId == Reference.GUI_BUTTON_BUY_FARM) {
			Utilities.PlayerBuysItem(player, new ItemStack(ModItems.FARMBUILDERITEM));

		}

	}

}
