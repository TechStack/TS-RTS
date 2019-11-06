package com.projectreddog.tsrts.data;

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

	public StructureData(ResourceLocation template100, ResourceLocation template50, ResourceLocation template0, BlockPos spawnPoint, Direction direction, Vec3i size) {
		super();
		this.template100 = template100;
		this.template50 = template50;
		this.template0 = template0;
		this.spawnPoint = spawnPoint;
		this.direction = direction;
		this.size = size;
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
