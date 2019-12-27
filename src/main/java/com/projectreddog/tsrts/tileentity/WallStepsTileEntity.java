package com.projectreddog.tsrts.tileentity;

import java.util.List;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class WallStepsTileEntity extends OwnedCooldownTileEntity implements INamedContainerProvider, ITEGuiButtonHandler {

	public WallStepsTileEntity() {
		super(ModBlocks.WALL_STEPS_TILE_ENITTY_TYPE);
	}

	@Override
	public void ActionAfterCooldown() {

		super.ActionAfterCooldown();

		if (getOwner() != null) {

		}
	}

	@Override
	public void StructureLost() {
		super.StructureLost();
		Utilities.SendMessageToTeam(this.getWorld(), this.getTeam().getName(), "tsrts.destroy.wallsteps");

	}

	public void IncreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setWallsteps(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getWallsteps() + 1);

	}

	public void DecreaseCount() {

		TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].setWallsteps(TSRTS.teamInfoArray[TeamEnum.getIDFromName(getTeam().getName())].getWallsteps() - 1);

	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Override
	public void HandleGuiButton(int buttonId, PlayerEntity player) {
		// TSRTS.LOGGER.info("button ID:" + buttonId);
		super.HandleGuiButton(buttonId, player);
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

	@Override
	public float getDamagedHealthThreashold() {
		return .50f * Config.CONFIG_STRCTURE_TOTAL_HEALTH_WALL_STEPS.get();
	}

	@Override
	public void AfterDeathAction() {
		super.AfterDeathAction();

		AxisAlignedBB bb = new AxisAlignedBB(this.getPos(), this.getPos().down(9)).grow(3.5, 3, 3.5);

		List<TargetEntity> teList = world.getEntitiesWithinAABB(TargetEntity.class, bb);
		float health = 0;
		int[] ids = new int[teList.size()];
		for (int i = 0; i < teList.size(); i++) {
			if (teList.get(i).getOwningTePos() == this.pos) {// ensure the target is owned by this block
				teList.get(i).setHealth(0);
			}
		}

	}
}
