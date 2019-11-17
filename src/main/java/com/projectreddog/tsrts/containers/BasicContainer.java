package com.projectreddog.tsrts.containers;

import com.projectreddog.tsrts.init.ModContainers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BasicContainer extends Container {

	private TileEntity tileEntity;
	private PlayerInventory playerInventory;
	public BlockPos pos;

	public BasicContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory) {
		super(ModContainers.BASIC_CONTAINER, id);
		this.tileEntity = world.getTileEntity(pos);
		this.playerInventory = playerInventory;
		this.pos = pos;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {

		return true;
	}

}
