package com.projectreddog.tsrts.hanlder;

import java.util.Iterator;
import java.util.Map;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.utilities.PlayerSelections;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
	}
}
