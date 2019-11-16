package com.projectreddog.tsrts.tileentity;

import com.projectreddog.tsrts.containers.BarracksContainer;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.init.ModEntities;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ArcheryRangeTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler {

	public ArcheryRangeTileEntity() {
		super(ModBlocks.ARCHERY_RANGE_TILE_ENTITY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {
			// ModEntities.MINION.spawn(world, null, null, this.pos, SpawnReason.TRIGGERED, true, true);
			if (hasNeededResources()) {
				spendResources();
				Utilities.SpawnUnitForTeam(ModEntities.ARCHER_MINION, this.getOwner(), this.getWorld(), this.getPos(), this.getTeam(), this.getRallyPoint());
			}
		}
	}

	public boolean spendResources() {
		String teamName = this.getTeam().getName();

		boolean result = true;

		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.FOOD, Config.CONFIG_UNIT_COSTS_ARCHER_FOOD.get());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.WOOD, Config.CONFIG_UNIT_COSTS_ARCHER_WOOD.get());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.STONE, Config.CONFIG_UNIT_COSTS_ARCHER_STONE.get());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.IRON, Config.CONFIG_UNIT_COSTS_ARCHER_IRON.get());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.GOLD, Config.CONFIG_UNIT_COSTS_ARCHER_GOLD.get());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.DIAMOND, Config.CONFIG_UNIT_COSTS_ARCHER_DIAMOND.get());
		result = result && Utilities.SpendResourcesFromTeam(teamName, TeamInfo.Resources.EMERALD, Config.CONFIG_UNIT_COSTS_ARCHER_EMERALD.get());
		return result;
	}

	public boolean hasNeededResources() {
		String teamName = this.getTeam().getName();
		boolean result = true;
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.FOOD, Config.CONFIG_UNIT_COSTS_ARCHER_FOOD.get());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.WOOD, Config.CONFIG_UNIT_COSTS_ARCHER_WOOD.get());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.STONE, Config.CONFIG_UNIT_COSTS_ARCHER_STONE.get());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.IRON, Config.CONFIG_UNIT_COSTS_ARCHER_IRON.get());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.GOLD, Config.CONFIG_UNIT_COSTS_ARCHER_GOLD.get());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.DIAMOND, Config.CONFIG_UNIT_COSTS_ARCHER_DIAMOND.get());
		result = result && Utilities.hasNeededResource(teamName, TeamInfo.Resources.EMERALD, Config.CONFIG_UNIT_COSTS_ARCHER_EMERALD.get());
		return result;
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new BarracksContainer(p_createMenu_1_, this.world, this.getPos(), playerInventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Override
	public void HandleGuiButton(int buttonId, PlayerEntity player) {
		// TSRTS.LOGGER.info("button ID:" + buttonId);

		if (buttonId == Reference.GUI_BUTTON_DEBUG_TESTERYELLOW) {
			this.setOwner("testeryellow");
		} else if (buttonId == Reference.GUI_BUTTON_DEBUG_TESTERBLUE) {
			this.setOwner("testerblue");
		} else if (buttonId == Reference.GUI_BUTTON_DEBUG_TESTERGREEN) {
			this.setOwner("testergreen");
		} else if (buttonId == Reference.GUI_BUTTON_DEBUG_TESTERRED) {
			this.setOwner("testerred");
		}

	}
}
