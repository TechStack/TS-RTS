package com.projectreddog.tsrts.tileentity;

import java.util.Iterator;
import java.util.List;

import com.projectreddog.tsrts.containers.BasicContainer;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.handler.Config;
import com.projectreddog.tsrts.init.ModBlocks;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class WatchTileEntity extends OwnedTileEntity implements ITickableTileEntity, INamedContainerProvider, ITEGuiButtonHandler {

	public int coolDownReset = 1 * 20;
	public int coolDownRemainig = coolDownReset;
	protected boolean enabled = true;
	protected boolean writeDirty = false;

	private int rangeHalf = 8;

	public WatchTileEntity() {
		super(ModBlocks.WATCH_ENTITY_TYPE);
	}

	@Override
	public void tick() {
		if ((!world.isRemote && enabled) && Config.CONFIG_GAME_MODE.get() != Config.Modes.WORLDBUILDER) {
			writeDirty = false;
			coolDownRemainig = coolDownRemainig - 1;
			writeDirty = true;
			if (coolDownRemainig <= 0) {
				if (Config.CONFIG_GAME_MODE.get() == Config.Modes.RUN) {
					ActionAfterCooldown();
				}
				coolDownRemainig = coolDownReset;
				writeDirty = true;
			}
			if (writeDirty) {
				this.markDirty();
			}
		}

	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new BasicContainer(p_createMenu_1_, this.world, this.getPos(), playerInventory);
	}

	public void ActionAfterCooldown() {
		Direction d = this.world.getBlockState(this.pos).get(BlockStateProperties.HORIZONTAL_FACING);

		AxisAlignedBB aabb = new AxisAlignedBB(this.pos.offset(d, rangeHalf + 2)).grow(rangeHalf);

		boolean shouldContinue = true;

		List<LivingEntity> e = this.world.getEntitiesWithinAABB(UnitEntity.class, aabb);

		if (e != null && shouldContinue) {

			for (Iterator iterator = e.iterator(); iterator.hasNext();) {

				LivingEntity livingEntity = (LivingEntity) iterator.next();
				if (livingEntity instanceof UnitEntity) {
					UnitEntity ue = (UnitEntity) livingEntity;
					if (!ue.getTeam().isSameTeam(this.getTeam())) {

						if (shouldContinue) {
							shoot(this.world, this.pos, d, livingEntity);
							shouldContinue = false;
						}
					}

				}

			}
		}

//		e = this.world.getEntitiesWithinAABB(PlayerEntity.class, aabb);
//		if (e != null && shouldContinue) {
//			for (Iterator iterator = e.iterator(); iterator.hasNext();) {
//
//				LivingEntity livingEntity = (LivingEntity) iterator.next();
//				if (shouldContinue) {
//					shoot(this.world, this.pos, d, livingEntity);
//					shouldContinue = false;
//				}
//			}
//		}
	}

	public void shoot(World world, BlockPos bp, Direction direction, LivingEntity target) {
		ArrowEntity arrowentity = new ArrowEntity(world, bp.getX() + .5f + (direction.getXOffset() * .6f), bp.getY() + .5F, bp.getZ() + .5f + (direction.getZOffset() * .6f));
		arrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;

		///

		double d0 = target.posX - (this.pos.getX() + direction.getXOffset() * .6f);
		double d1 = 0;
		if (target instanceof TargetEntity) {
			d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 4.0F) - arrowentity.posY;
		} else if (target instanceof UnitEntity) {
			d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 5.0F) - arrowentity.posY;
		} else {
			d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 3.0F) - arrowentity.posY;
		}

		double d2 = target.posZ - (this.pos.getZ() + direction.getZOffset() * .6f);
		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
		arrowentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (0));
		this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_SKELETON_SHOOT, SoundCategory.BLOCKS, 1.0F, 1.0f, true);

		this.world.addEntity(arrowentity);

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.putInt("coolDownReset", coolDownReset);
		nbt.putInt("coolDownRemainig", coolDownRemainig);
		if (Config.CONFIG_GAME_MODE.get() != Config.Modes.WORLDBUILDER) {
			nbt.putBoolean("enabled", enabled);
		}
		return nbt;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		coolDownReset = compound.getInt("coolDownReset");
		coolDownRemainig = compound.getInt("coolDownRemainig");
		if (compound.contains("enabled")) {
			enabled = compound.getBoolean("enabled");
		}
	}

	@Override
	public void HandleGuiButton(int buttonId, PlayerEntity player) {
		switch (buttonId) {
		case Reference.GUI_BUTTON_ENABLE_TE:
			enabled = true;
			break;
		case Reference.GUI_BUTTON_DISABLE_TE:
			enabled = false;
			break;

		default:
			break;
		}
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

}
