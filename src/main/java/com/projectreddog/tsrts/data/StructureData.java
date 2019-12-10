package com.projectreddog.tsrts.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class StructureData {

	ResourceLocation template100;
	ResourceLocation template50;
	ResourceLocation template0;

	BlockPos spawnPoint;
	Direction direction;
	Vec3i size;

	boolean spreadHealthAroundTargets = true;

	public boolean GetSpreadHealthAroundTargets() {
		return spreadHealthAroundTargets;
	}

	public StructureData(CompoundNBT compound) {
		template100 = new ResourceLocation(compound.getString("template100"));
		template50 = new ResourceLocation(compound.getString("template50"));
		template0 = new ResourceLocation(compound.getString("template0"));

		int bpX = compound.getInt("bpX");
		int bpY = compound.getInt("bpY");
		int bpZ = compound.getInt("bpZ");
		this.spawnPoint = new BlockPos(bpX, bpY, bpZ);
		int directionord = compound.getInt("directionOrdnial");
		this.direction = Direction.values()[directionord];
		int vecX = compound.getInt("vecX");
		int vecY = compound.getInt("vecY");
		int vecZ = compound.getInt("vecZ");
		this.size = new Vec3i(vecX, vecY, vecZ);

	}

	public void writeToNbt(CompoundNBT compound) {
		if (template100 != null) {
			compound.putString("template100", template100.toString());
		}
		if (template50 != null) {
			compound.putString("template50", template50.toString());
		}
		if (template0 != null) {
			compound.putString("template0", template0.toString());
		}
		if (spawnPoint != null) {
			compound.putInt("bpX", spawnPoint.getX());
			compound.putInt("bpY", spawnPoint.getY());
			compound.putInt("bpZ", spawnPoint.getZ());
		}
		if (direction != null) {
			compound.putInt("directionOrdnial", direction.ordinal());
		}
		if (size != null) {
			compound.putInt("vecX", size.getX());
			compound.putInt("vecY", size.getY());
			compound.putInt("vecZ", size.getZ());
		}
	}

	public StructureData(ResourceLocation template100, ResourceLocation template50, ResourceLocation template0, BlockPos spawnPoint, Direction direction, Vec3i size, boolean spreadHealthAroundTargets) {
		super();
		this.template100 = template100;
		this.template50 = template50;
		this.template0 = template0;
		this.spawnPoint = spawnPoint;
		this.direction = direction;
		this.size = size;
		this.spreadHealthAroundTargets = spreadHealthAroundTargets;
	}

	public ResourceLocation getTemplate100() {
		return template100;
	}

	public void setTemplate100(ResourceLocation template100) {
		this.template100 = template100;
	}

	public ResourceLocation getTemplate50() {
		return template50;
	}

	public void setTemplate50(ResourceLocation template50) {
		this.template50 = template50;
	}

	public ResourceLocation getTemplate0() {
		return template0;
	}

	public void setTemplate0(ResourceLocation template0) {
		this.template0 = template0;
	}

	public BlockPos getSpawnPoint() {
		return spawnPoint;
	}

	public void setSpawnPoint(BlockPos spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Vec3i getSize() {
		return size;
	}

	public void setSize(Vec3i size) {
		this.size = size;
	}

}
