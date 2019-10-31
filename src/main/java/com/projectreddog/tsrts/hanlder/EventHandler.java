package com.projectreddog.tsrts.hanlder;

import java.util.Iterator;
import java.util.Map;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.utilities.PlayerSelections;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent.Arrow;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {
	@SubscribeEvent
	public static void onEntityJoinWorldEvent(final EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity pe = (PlayerEntity) event.getEntity();
			if (!TSRTS.playerSelections.containsKey(pe.getScoreboardName())) {
				TSRTS.playerSelections.put(pe.getScoreboardName(), new PlayerSelections());
				Utilities.SendTeamToClient("red");
				Utilities.SendTeamToClient("blue");
				Utilities.SendTeamToClient("green");
				Utilities.SendTeamToClient("yellow");
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeathEvent(final LivingDeathEvent event) {
		for (Map.Entry ps : TSRTS.playerSelections.entrySet()) {

			PlayerSelections p = (PlayerSelections) ps.getValue();
			Iterator<Integer> i = p.selectedUnits.iterator();
			while (i.hasNext()) {
				int currentEntityID = i.next();
				if (currentEntityID == event.getEntity().getEntityId()) {
					// found a match it died remove it from the selections!
					i.remove();
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLoad(Load event) {
		TSRTS.playerSelections.clear();

		World world = (World) event.getWorld();
		TSRTS.teamInfoMap.clear();
		if (world.getScoreboard().getTeam("blue") != null) {
			TSRTS.teamInfoMap.put(world.getScoreboard().getTeam("blue").getName(), new TeamInfo());
			Utilities.SendTeamToClient("blue");
		}
		if (world.getScoreboard().getTeam("red") != null) {
			TSRTS.teamInfoMap.put(world.getScoreboard().getTeam("red").getName(), new TeamInfo());
			Utilities.SendTeamToClient("red");
		}
		if (world.getScoreboard().getTeam("yellow") != null) {
			TSRTS.teamInfoMap.put(world.getScoreboard().getTeam("yellow").getName(), new TeamInfo());
			Utilities.SendTeamToClient("yellow");
		}
		if (world.getScoreboard().getTeam("green") != null) {
			TSRTS.teamInfoMap.put(world.getScoreboard().getTeam("green").getName(), new TeamInfo());
			Utilities.SendTeamToClient("green");
		}

	}

	@SubscribeEvent
	public static void onArrowImpactEvent(Arrow event) {
		Entity shooter = event.getArrow().getShooter();
		boolean shouldCancel = false;
		if (shooter instanceof UnitEntity) {
			RayTraceResult rtr = event.getRayTraceResult();
			if (rtr instanceof EntityRayTraceResult) {
				EntityRayTraceResult ertr = (EntityRayTraceResult) rtr;
				Entity hitEntity = ertr.getEntity();
				if (hitEntity instanceof UnitEntity) {
					// Unit hit unit
					UnitEntity hitUnit = (UnitEntity) hitEntity;
					if (hitEntity.getTeam().isSameTeam(((UnitEntity) shooter).getTeam())) {
						// SAME TEAM CANCEL !
						shouldCancel = true;
					}

				} else if (hitEntity instanceof PlayerEntity) {
					// unit hit player
					PlayerEntity hitUnit = (PlayerEntity) hitEntity;
					if (hitEntity.getTeam().isSameTeam(((UnitEntity) shooter).getTeam())) {
						// SAME TEAM CANCEL !
						shouldCancel = true;
					}
				}

			}

		} else if (shooter instanceof PlayerEntity) {

			// PV P

			RayTraceResult rtr = event.getRayTraceResult();
			if (rtr instanceof EntityRayTraceResult) {
				EntityRayTraceResult ertr = (EntityRayTraceResult) rtr;
				Entity hitEntity = ertr.getEntity();
				if (hitEntity instanceof UnitEntity) {
					// Unit hit unit
					UnitEntity hitUnit = (UnitEntity) hitEntity;
					if (hitEntity.getTeam().isSameTeam(((PlayerEntity) shooter).getTeam())) {
						// PVU
						// SAME TEAM CANCEL !
						shouldCancel = true;
					}

				} else if (hitEntity instanceof PlayerEntity) {
					// unit hit player
					PlayerEntity hitUnit = (PlayerEntity) hitEntity;
					if (hitEntity.getTeam().isSameTeam(((PlayerEntity) shooter).getTeam())) {
						// PV P
						// SAME TEAM CANCEL !
						shouldCancel = true;
					}
				}

			}
		}
		event.setCanceled(shouldCancel);
	}

}
