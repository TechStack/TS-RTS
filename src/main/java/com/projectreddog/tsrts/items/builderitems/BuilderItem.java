package com.projectreddog.tsrts.items.builderitems;

import com.projectreddog.tsrts.data.StructureData;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.handler.Config.Modes;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.ResourceValues;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public abstract class BuilderItem extends Item {

	public BuilderItem(Properties properties) {
		super(properties);
	}

	protected ResourceLocation templateNameRed100;
	protected ResourceLocation templateNameRed50;
	protected ResourceLocation templateNameRed0;

	protected ResourceLocation templateNameYellow100;
	protected ResourceLocation templateNameYellow50;
	protected ResourceLocation templateNameYellow0;

	protected ResourceLocation templateNameGreen100;
	protected ResourceLocation templateNameGreen50;
	protected ResourceLocation templateNameGreen0;

	protected ResourceLocation templateNameBlue100;
	protected ResourceLocation templateNameBlue50;
	protected ResourceLocation templateNameBlue0;

	public abstract ResourceLocation getTemplateName100(String team);

	public abstract ResourceLocation getTemplateName50(String team);

	public abstract ResourceLocation getTemplateName0(String team);

	public abstract Vec3i getSize();

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {

		if (!context.getPlayer().world.isRemote) {

			Direction d = Direction.getFacingFromVector(context.getPlayer().getLookVec().getX(), 0, context.getPlayer().getLookVec().getZ());

			if (CanPlaceOn(context.getPlayer().world.getBlockState(context.getPos()).getBlock())) {

				if (!(Config.CONFIG_GAME_MODE.get() == Modes.WAVESURVIVAL)) {
					if (Utilities.LoadStructure(context.getWorld(), getTemplateName100(context.getPlayer().getTeam().getName()), new StructureData(getTemplateName100(context.getPlayer().getTeam().getName()), getTemplateName50(context.getPlayer().getTeam().getName()), getTemplateName0(context.getPlayer().getTeam().getName()), context.getPos(), d, getSize(), spreadHealthAroundTargets()), context.getPlayer().getScoreboardName(), true, true, getTotalStructureHealth())) {

						context.getItem().shrink(1);
						context.getPlayer().container.detectAndSendChanges();
						ActionAfterSpawn(context.getWorld(), context.getPlayer(), context.getPos().up());

					} else {
						return ActionResultType.FAIL;

					}
				} else {

					// wave survial mode
					if (!(context.getPlayer().getTeam().getName().equals("yellow"))) {
						// not team yellow normal player stuff in wave mode !
						if (Utilities.LoadStructure(context.getWorld(), getTemplateName100(context.getPlayer().getTeam().getName()), new StructureData(getTemplateName100(context.getPlayer().getTeam().getName()), getTemplateName50(context.getPlayer().getTeam().getName()), getTemplateName0(context.getPlayer().getTeam().getName()), context.getPos(), d, getSize(), spreadHealthAroundTargets()), context.getPlayer().getScoreboardName(), true, true, getTotalStructureHealth())) {

							context.getItem().shrink(1);
							context.getPlayer().container.detectAndSendChanges();
							ActionAfterSpawn(context.getWorld(), context.getPlayer(), context.getPos().up());

						} else {
							return ActionResultType.FAIL;
						}
					} else {
// wave mode team yellow !
						if (Utilities.LoadStructure(context.getWorld(), getTemplateName100(Reference.WAVE_SURVIAL_AI_TEAM_NAME), new StructureData(getTemplateName100(Reference.WAVE_SURVIAL_AI_TEAM_NAME), getTemplateName50(Reference.WAVE_SURVIAL_AI_TEAM_NAME), getTemplateName0(Reference.WAVE_SURVIAL_AI_TEAM_NAME), context.getPos(), d, getSize(), spreadHealthAroundTargets()), Reference.WAVE_SURVIAL_AI_NAME, true, true, getTotalStructureHealth())) {
							context.getPlayer().setHealth(0);
						} else {
							return ActionResultType.FAIL;
						}
					}
				}
			}
			return ActionResultType.FAIL;

		}

		return super.onItemUse(context);
	}

	public boolean spreadHealthAroundTargets() {
		return true;
	}

	public abstract void ActionAfterSpawn(World world, PlayerEntity Player, BlockPos bp);

	public abstract boolean CanPlaceOn(Block block);

	public abstract float getTotalStructureHealth();

	public abstract ResourceValues getResourceCosts();

	public int getFoodCosts() {
		return this.getResourceCosts().getFOOD();
	}

	public int getWoodCosts() {
		return this.getResourceCosts().getWOOD();
	}

	public int getStoneCosts() {
		return this.getResourceCosts().getSTONE();
	}

	public int getIronCosts() {
		return this.getResourceCosts().getIRON();
	}

	public int getGoldCosts() {
		return this.getResourceCosts().getGOLD();
	}

	public int getDiamondCosts() {
		return this.getResourceCosts().getDIAMOND();
	}

	public int getEmeraldCosts() {
		return this.getResourceCosts().getEMERALD();
	}

}
