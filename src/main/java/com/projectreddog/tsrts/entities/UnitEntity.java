package com.projectreddog.tsrts.entities;

import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.EntityOwnerChangedPacketToClient;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class UnitEntity extends MonsterEntity {

	private String ownerName;

	public BlockPos ownerControlledDestination;

	public boolean isSelected;

	@Override
	public void tick() {

		super.tick();

	}

	@Override
	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vec3d vec, Hand hand) {
		if (player.getScoreboardName().equals(ownerName)) {
			// TODO Debug why this is called twice and allow for UNselecting
			// isSelected = true;

			Utilities.SelectUnit(player.getScoreboardName(), this.getEntityId());

			return ActionResultType.PASS;
		} else {
			return ActionResultType.FAIL;
		}
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		// TODO REMOVE THE RNG next int as its only for testing here
		this.ownerName = ownerName;

		if (!world.isRemote) {
			// from server send to others
			ModNetwork.SendToALLPlayers(new EntityOwnerChangedPacketToClient(this.getEntityId(), this.ownerName));
		}

	}

	public UnitEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);

	}

	@Override
	public boolean canAttack(EntityType<?> typeIn) {
		return true;
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		return true;
	}

	@Override
	public boolean isChild() {
		return true;
	}

	@Override
	public ScorePlayerTeam getTeam() {
		return this.world.getScoreboard().getPlayersTeam(ownerName);
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		setOwnerName(compound.getString("onwerName"));
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putString("onwerName", ownerName);
	}

	@Override
	public boolean hasCustomName() {
		if (ownerName != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ITextComponent getCustomName() {
		if (ownerName != null) {
			return new StringTextComponent(ownerName);
		} else {
			return null;
		}
	}
}
