package com.projectreddog.tsrts.entities;

import javax.annotation.Nullable;

import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.EntityOwnerChangedPacketToClient;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TargetEntity extends CreatureEntity {

	private BlockPos owningTePos;

	public BlockPos getOwningTePos() {
		return owningTePos;
	}

	public void setOwningTePos(BlockPos owningTePos) {
		this.owningTePos = owningTePos;
	}

	public TargetEntity(EntityType type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 0;
	}

	@Override
	protected boolean canDropLoot() {
		return false;
	}

	public String getOwnerName() {
		return ownerName;
	}

	private String ownerName;

	@Override
	public ScorePlayerTeam getTeam() {
		return this.world.getScoreboard().getPlayersTeam(ownerName);
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		setOwnerName(compound.getString("onwerName"));
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;

		if (!world.isRemote) {
			// from server send to others
			ModNetwork.SendToALLPlayers(new EntityOwnerChangedPacketToClient(this.getEntityId(), this.ownerName));
		}

	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putString("onwerName", ownerName);
	}

	@Override
	public void move(MoverType typeIn, Vec3d pos) {
	}

	@Override
	public void tick() {
		super.tick();

		if (this.posX >= 0) {
			this.posX = ((int) this.posX) + .5f;
		} else if (this.posX < 0) {
			this.posX = ((int) this.posX) - .5f;
		}
		this.posY = ((int) this.posY);
		if (this.posZ >= 0) {
			this.posZ = ((int) this.posZ) + .5f;
		} else if (this.posZ < 0) {
			this.posZ = ((int) this.posZ) - .5f;
		}
		this.setBoundingBox((new AxisAlignedBB(this.posX - 0.5D, this.posY, this.posZ - 0.5D, this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D)));

	}

	@Override
	protected AxisAlignedBB getBoundingBox(Pose p_213321_1_) {
		EntitySize entitysize = this.getSize(p_213321_1_);
		float f = entitysize.width / 2.0F;
		Vec3d vec3d = new Vec3d(this.posX - (double) f, this.posY, this.posZ - (double) f);
		Vec3d vec3d1 = new Vec3d(this.posX + (double) f, this.posY + (double) entitysize.height, this.posZ + (double) f);
		return new AxisAlignedBB(vec3d, vec3d1);
	}

	/**
	 * Returns the <b>solid</b> collision bounding box for this entity. Used to make (e.g.) boats solid. Return null if this entity is not solid.
	 * 
	 * For general purposes, use {@link #width} and {@link #height}.
	 * 
	 * @see getEntityBoundingBox
	 */
	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.isAlive() ? this.getBoundingBox() : null;
	}
}
