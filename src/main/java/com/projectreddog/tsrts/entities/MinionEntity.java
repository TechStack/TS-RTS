package com.projectreddog.tsrts.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class MinionEntity extends MobEntity {

	public PlayerEntity player;

	public MinionEntity(EntityType<? extends MobEntity> type, World worldIn) {
		super(type, worldIn);
		// this.world.getScoreboard().addPlayerToTeam(this.getCachedUniqueIdString(), scoreplayerteam);
		// Minecraft.getInstance().player.getTeam()

	}

	@Override
	public boolean isChild() {
		return true;
	}

	@Override
	public ITextComponent getName() {
		if (player != null) {
			return player.getName();
		}
		return null;

	}

	@Override
	public Team getTeam() {
		if (player != null) {
			return player.getTeam();
		}
		return null;
	}

	public PlayerEntity getPlayer() {
		return Minecraft.getInstance().player;

	}

}
