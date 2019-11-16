package com.projectreddog.tsrts.handler;

import java.util.Iterator;
import java.util.Map;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.TSRTS.GAMESTATE;
import com.projectreddog.tsrts.containers.provider.LobbyContinerProvider;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.RequestOwnerInfoToServer;
import com.projectreddog.tsrts.tileentity.OwnedCooldownTileEntity;
import com.projectreddog.tsrts.utilities.PlayerSelections;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent.Arrow;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkHooks;

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
// TODO FIX this CUT OUT 
//			if (!pe.world.isRemote) {
//				if (TSRTS.CURRENT_GAME_STATE == GAMESTATE.LOBBY) {
//					NetworkHooks.openGui((ServerPlayerEntity) pe, (INamedContainerProvider) new LobbyContinerProvider());
//
//				}
//			}

		} else if (event.getEntity() instanceof UnitEntity || event.getEntity() instanceof TargetEntity) {
			if (event.getWorld() != null) {
				if (event.getWorld().isRemote) {
					// client

					ModNetwork.SendToServer(new RequestOwnerInfoToServer(event.getEntity().getEntityId()));

				}
			}
		}
	}

	@SubscribeEvent

	public static void onPlayerLoggedInEvent(PlayerLoggedInEvent event) {
		PlayerEntity pe = event.getPlayer();
		if (!pe.world.isRemote) {
			if (TSRTS.CURRENT_GAME_STATE == GAMESTATE.LOBBY) {
				NetworkHooks.openGui((ServerPlayerEntity) pe, (INamedContainerProvider) new LobbyContinerProvider());

			}
		}
	}

	@SubscribeEvent
	public static void onLivingDamageEvent(LivingDamageEvent event) {
		if (!event.getEntity().world.isRemote) {
			// server only!
			if (event.getEntity() instanceof TargetEntity) {
				// its a target entity
				// check its ownedTEpos for a TE and update its health stat accordingly.
				TargetEntity targetEntity = (TargetEntity) event.getEntity();
				if (targetEntity.getOwningTePos() != null) {
					TileEntity te = targetEntity.world.getTileEntity(targetEntity.getOwningTePos());
					if (te != null && te instanceof OwnedCooldownTileEntity) {
						OwnedCooldownTileEntity octe = (OwnedCooldownTileEntity) te;
						float damageAmount = 0;
						if (event.getAmount() <= event.getEntityLiving().getHealth()) {
							damageAmount = event.getAmount();
						} else {
							damageAmount = event.getEntityLiving().getHealth();
						}

						octe.setHealth(octe.getHealth() - damageAmount);
						TSRTS.LOGGER.info("DAMAGE :" + damageAmount);

						TSRTS.LOGGER.info("remaining health : " + octe.getHealth());

					}
				}
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

		Utilities.removeDeadEntityFromControlGroups(event.getEntity().getEntityId());
	}

	@SubscribeEvent
	public static void onLoad(Load event) {
		TSRTS.playerSelections.clear();
		Utilities.CheckTeamsAndCreatedIfNeeded((World) event.getWorld());
		if (Config.CONFIG_GAME_MODE.get() == Config.Modes.RUN) {
			TSRTS.CURRENT_GAME_STATE = GAMESTATE.LOBBY;
		}

		World world = (World) event.getWorld();
		TSRTS.teamInfoMap.clear();
		if (!world.isRemote) {

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
					if (hitEntity.getTeam() != null) {
						if (hitEntity.getTeam().isSameTeam(((PlayerEntity) shooter).getTeam())) {
							// PVU
							// SAME TEAM CANCEL !
							shouldCancel = true;
						}
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
		RayTraceResult rtr = event.getRayTraceResult();
		if (!(rtr instanceof EntityRayTraceResult)) {
			// ASSUME BLOCK?
			if (rtr.getType() == Type.BLOCK) {
				event.getEntity().remove();
			}
		}
		event.setCanceled(shouldCancel);
	}

}
