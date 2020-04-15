package com.projectreddog.tsrts.handler;

import java.util.Iterator;
import java.util.Map;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.TSRTS.GAMESTATE;
import com.projectreddog.tsrts.containers.provider.LobbyContinerProvider;
import com.projectreddog.tsrts.entities.SapperEntity;
import com.projectreddog.tsrts.entities.TargetEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.handler.Config.Modes;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.RequestOwnerInfoToServer;
import com.projectreddog.tsrts.network.UnitQueueChangedPacketToClient;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.OwnedCooldownTileEntity;
import com.projectreddog.tsrts.utilities.GameOptions;
import com.projectreddog.tsrts.utilities.PlayerSelections;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
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
		if (event.getEntity() instanceof PlayerEntity && !event.getWorld().isRemote) {
			// player and server !
			PlayerEntity pe = (PlayerEntity) event.getEntity();
			if (TSRTS.CURRENT_GAME_STATE == GAMESTATE.RUNNINNG) {
				// game in progress check if they are on a team.
				// if they are not kick them out to spectator

				if (pe.getTeam() == null) {
					// player not on a team
					pe.setGameType(GameType.SPECTATOR);
				}
				if (GameOptions.speedEffectAmount > 0) {
					pe.addPotionEffect(new EffectInstance(Effects.SPEED, 99999999, GameOptions.speedEffectAmount - 1));
				}
			}

			if (!TSRTS.playerSelections.containsKey(pe.getScoreboardName())) {
				TSRTS.playerSelections.put(pe.getScoreboardName(), new PlayerSelections());
				Utilities.SendTeamToClient("red");
				Utilities.SendTeamToClient("blue");
				Utilities.SendTeamToClient("green");
				Utilities.SendTeamToClient("yellow");
			}

			Utilities.setPlayerReady(pe, false);

			// TODO: send to client all the unlocked research
			Utilities.SendResearchStatusToClient((ServerPlayerEntity) pe);
			// TODO: also send the client the cost of everything so it can ignore its local configs.
			Utilities.SendCostsToClient((ServerPlayerEntity) pe);

			// send team queues to players to "Clear" them
			for (int i = 0; i < TeamEnum.values().length; i++) {
				ModNetwork.SendToPlayer((ServerPlayerEntity) pe, new UnitQueueChangedPacketToClient(TSRTS.TeamQueues[i].getBarracks(), TSRTS.TeamQueues[i].getArcheryRange(), TSRTS.TeamQueues[i].getStables(), TSRTS.TeamQueues[i].getSiegeWorkshop(), TSRTS.TeamQueues[i].isInfinateBarracksQueue(), TSRTS.TeamQueues[i].isInfinateArcheryRangeQueue(), TSRTS.TeamQueues[i].isInfinateStablesQueue(), TSRTS.TeamQueues[i].isInfinateSiegeWorkshopQueue(), i));
			}

			if (TSRTS.CURRENT_GAME_STATE != GAMESTATE.RUNNINNG) {
				if (Config.CONFIG_GAME_MODE.get() == Modes.WAVESURVIVAL) {
					if (!TSRTS.WAVE_MODE_DATA.isTownhallGiven()) {
						// this is the chosen one give them the town hall and set them to team YELLOW!
						Utilities.GivePlayerItemStack(pe, new ItemStack(ModItems.TOWNHALLBUILDERITEM));
						ScorePlayerTeam team = pe.world.getScoreboard().getTeam(Reference.WAVE_SURVIAL_AI_TEAM_NAME);
						pe.world.getScoreboard().addPlayerToTeam(pe.getScoreboardName(), team);
						TSRTS.WAVE_MODE_DATA.setTownhallGiven(true);

					}
				}
			}

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

				if (!Config.CONFIG_PLAYERS_CAN_ATTACK_BUILDINGS.get()) {
					// check if player is attacking
					if (event.getSource() instanceof EntityDamageSource) {
						EntityDamageSource eds = (EntityDamageSource) event.getSource();
						if (eds.getTrueSource() instanceof PlayerEntity) {
							// it is a player that is attacking so cancel.
							PlayerEntity pe = (PlayerEntity) eds.getTrueSource();
							// toss in team check so a team can kill their own buildings
							if (eds.getTrueSource().getTeam() != null && (!eds.getTrueSource().getTeam().isSameTeam(event.getEntity().getTeam()))) {
								event.setCanceled(true);
								pe.sendMessage(new TranslationTextComponent("message.attackingbuildingsasplayerdisabled"));
								return;
							}
						}
					}
				}

				if (event.getSource() instanceof EntityDamageSource) {
					EntityDamageSource eds = (EntityDamageSource) event.getSource();

					if (eds.getTrueSource() instanceof SapperEntity && event.getSource().damageType.equals("explosion.player")) {
						event.setAmount(MathHelper.clamp(event.getAmount() * 4, 25, 100));

					}
				}

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

						// TSRTS.LOGGER.info("remaining health : " + octe.getHealth());

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
					p.hasChanged = true;
				}
			}
		}
		if (event.getEntity() instanceof UnitEntity && !event.getEntity().world.isRemote) {
			// one of mine
			UnitEntity ue = (UnitEntity) event.getEntity();
			if (ue.getTeam() != null) {
				String teamName = ue.getTeam().getName();

				TSRTS.teamInfoArray[TeamEnum.getIDFromName(teamName)].RemoveOneUnitCount(ue.getUnitType());

			}

		}
		Utilities.removeDeadEntityFromControlGroups(event.getEntity().getEntityId());
	}

	@SubscribeEvent
	public static void onLoad(Load event) {
		TSRTS.playerSelections.clear();
		Utilities.CheckTeamsAndCreatedIfNeeded((World) event.getWorld());
		if (Config.CONFIG_GAME_MODE.get() == Config.Modes.RUN || Config.CONFIG_GAME_MODE.get() == Config.Modes.WAVESURVIVAL) {
			TSRTS.CURRENT_GAME_STATE = GAMESTATE.LOBBY;

			// TODO KEEP INVENTORY?
			// ((World)event.getWorld()).getGameRules().getBoolean(GameRules.KEEP_INVENTORY)

			((World) event.getWorld()).getGameRules().get(GameRules.KEEP_INVENTORY).set(true, (MinecraftServer) null);
			((World) event.getWorld()).getGameRules().get(GameRules.DO_TILE_DROPS).set(false, (MinecraftServer) null);

		}

		World world = (World) event.getWorld();

		if (!world.isRemote) {

			for (int i = 0; i < TSRTS.teamInfoArray.length; i++) {

				if (world.getScoreboard().getTeam(TeamEnum.values()[i].getName()) != null) {
					TSRTS.teamInfoArray[i] = new TeamInfo();

					Utilities.SendTeamToClient(TeamEnum.values()[i].getName());
				}
			}

		} else {

			for (int i = 0; i < TSRTS.teamInfoArray.length; i++) {

				if (world.getScoreboard().getTeam(TeamEnum.values()[i].getName()) != null) {
					TSRTS.teamInfoArray[i] = new TeamInfo();

				}
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
					if (hitEntity.getTeam() != null) {

						if (hitEntity.getTeam().isSameTeam(((UnitEntity) shooter).getTeam())) {
							// SAME TEAM CANCEL !
							shouldCancel = true;
						}
					}

				} else if (hitEntity instanceof PlayerEntity) {
					// unit hit player
					PlayerEntity hitUnit = (PlayerEntity) hitEntity;
					if (hitEntity.getTeam() != null) {
						if (hitEntity.getTeam().isSameTeam(((UnitEntity) shooter).getTeam())) {
							// SAME TEAM CANCEL !
							shouldCancel = true;
						}
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
					if (hitEntity.getTeam() != null) {

						if (hitEntity.getTeam().isSameTeam(((PlayerEntity) shooter).getTeam())) {
							// PV P
							// SAME TEAM CANCEL !
							shouldCancel = true;
						}
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

//	@SubscribeEvent
//	public static void onBreakEvent(BreakEvent event) {
//		TileEntity te = event.getWorld().getTileEntity(event.getPos());
//
//		if (te instanceof OwnedCooldownTileEntity) {
//			OwnedCooldownTileEntity octe = (OwnedCooldownTileEntity) te;
//			if (Config.CONFIG_GAME_MODE.get() == Modes.RUN) {
//				// octe.DecreaseCount();
//			}
//		}
//	}

}
