package com.projectreddog.tsrts.entities;

import com.projectreddog.tsrts.TSRTS;
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
	public boolean isRetreating = false;
	public boolean isHoldingGround = false;
	private String ownerName;

	public BlockPos ownerControlledDestination;

	@Override
	public void tick() {

		super.tick();

	}

	@Override
	public boolean canBePushed() {

		return false;
	}

	@Override
	protected void collideWithNearbyEntities() {

	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean canDropLoot() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGlowing() {
		// TODO Auto-generated method stub
		if (TSRTS.playerSelections.containsKey(ownerName)) {
			for (int i = 0; i < TSRTS.playerSelections.get(ownerName).selectedUnits.size(); i++) {
				if (TSRTS.playerSelections.get(ownerName).selectedUnits != null) {

					try {
						if (TSRTS.playerSelections.get(ownerName).selectedUnits.get(i) == this.getEntityId()) {
							return true;
						}
					} catch (Exception e) {
						// Its possible for the user to UNselect the untis at the same time we are trying to process the list(diff threads)
						// if this should happen just ignore as they are no longer selected now anyway.
					}
				}
			}

		}
		return false;
	}

	@Override
	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vec3d vec, Hand hand) {
		if (!player.world.isRemote) {
			if (player.getScoreboardName().equals(ownerName)) {
				if (!player.isSneaking()) {
					Utilities.serverSelectUnit(player, player.getScoreboardName(), this.getEntityId());
				} else {

					Utilities.serverDeSelectUnit(player, player.getScoreboardName(), this.getEntityId());
				}
				return ActionResultType.PASS;
			} else {
				return ActionResultType.FAIL;
			}
		}
		return ActionResultType.PASS;

	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;

		if (!world.isRemote) {
			// from server send to others
			ModNetwork.SendToALLPlayers(new EntityOwnerChangedPacketToClient(this.getEntityId(), this.ownerName));
		}

	}

	public UnitEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.getNavigator().setCanSwim(true);
	}

	@Override
	public boolean canAttack(EntityType<?> target) {

		return true;
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		if (target != null && target.getTeam() != null && getTeam() != null) {

			if (target.getTeam().isSameTeam(getTeam())) {
				return false;
			}
		}
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
		if (compound.contains("isHoldingGround")) {
			isHoldingGround = compound.getBoolean("isHoldingGround");
		}
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putString("onwerName", ownerName);
		compound.putBoolean("isHoldingGround", isHoldingGround);
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
