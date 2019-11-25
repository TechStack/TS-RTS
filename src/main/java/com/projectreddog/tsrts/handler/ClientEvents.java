package com.projectreddog.tsrts.handler;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.GuiRequestPacketToServer;
import com.projectreddog.tsrts.network.PlayerSelectionChangedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {

	public static final KeyBinding controlGroup1 = new KeyBinding(Reference.MODID + ".key.controlgroup1", GLFW.GLFW_KEY_Z, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup2 = new KeyBinding(Reference.MODID + ".key.controlgroup2", GLFW.GLFW_KEY_X, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup3 = new KeyBinding(Reference.MODID + ".key.controlgroup3", GLFW.GLFW_KEY_C, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup4 = new KeyBinding(Reference.MODID + ".key.controlgroup4", GLFW.GLFW_KEY_V, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup5 = new KeyBinding(Reference.MODID + ".key.controlgroup5", GLFW.GLFW_KEY_B, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup6 = new KeyBinding(Reference.MODID + ".key.controlgroup6", GLFW.GLFW_KEY_N, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup7 = new KeyBinding(Reference.MODID + ".key.controlgroup7", GLFW.GLFW_KEY_M, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup8 = new KeyBinding(Reference.MODID + ".key.controlgroup8", GLFW.GLFW_KEY_COMMA, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup9 = new KeyBinding(Reference.MODID + ".key.controlgroup9", GLFW.GLFW_KEY_PERIOD, "key.categories." + Reference.MODID);
	public static final KeyBinding controlModifier = new KeyBinding(Reference.MODID + ".key.controlmodifier", GLFW.GLFW_MOD_CONTROL, "key.categories." + Reference.MODID);
	public static final KeyBinding deselectAll = new KeyBinding(Reference.MODID + ".key.deselectall", GLFW.GLFW_KEY_G, "key.categories." + Reference.MODID);

	public static final KeyBinding areaSelect = new KeyBinding(Reference.MODID + ".key.areaSelect", GLFW.GLFW_KEY_H, "key.categories." + Reference.MODID);
	public static final KeyBinding boxSelect = new KeyBinding(Reference.MODID + ".key.boxSelect", GLFW.GLFW_KEY_J, "key.categories." + Reference.MODID);
	public static final KeyBinding mainGuiOpen = new KeyBinding(Reference.MODID + ".key.mainGuiOpen", GLFW.GLFW_KEY_Y, "key.categories." + Reference.MODID);

	@SubscribeEvent
	public static void onClientTickEvent(final ClientTickEvent event) {

		if (event.phase != TickEvent.Phase.END)
			return;

		{

			if (Minecraft.getInstance() != null && Minecraft.getInstance().player != null) {
				String playerScoreboardName = Minecraft.getInstance().player.getScoreboardName();

				if (controlGroup1.isPressed()) {
					// DO STUFF HERE

					if (controlModifier.isKeyDown()) {
						Utilities.clientSelectedUnitsToControlGroup(playerScoreboardName, 1);
					} else {
						Utilities.clientControlGroupToSelectedUnits(playerScoreboardName, 1);
					}

				}
				if (controlGroup2.isPressed()) {
					// DO STUFF HERE
					if (controlModifier.isKeyDown()) {
						Utilities.clientSelectedUnitsToControlGroup(playerScoreboardName, 2);
					} else {
						Utilities.clientControlGroupToSelectedUnits(playerScoreboardName, 2);
					}
				}
				if (controlGroup3.isPressed()) {
					// DO STUFF HERE
					if (controlModifier.isKeyDown()) {
						Utilities.clientSelectedUnitsToControlGroup(playerScoreboardName, 3);
					} else {
						Utilities.clientControlGroupToSelectedUnits(playerScoreboardName, 3);
					}
				}
				if (controlGroup4.isPressed()) {
					// DO STUFF HERE
					if (controlModifier.isKeyDown()) {
						Utilities.clientSelectedUnitsToControlGroup(playerScoreboardName, 4);
					} else {
						Utilities.clientControlGroupToSelectedUnits(playerScoreboardName, 4);
					}
				}
				if (controlGroup5.isPressed()) {
					// DO STUFF HERE
					if (controlModifier.isKeyDown()) {
						Utilities.clientSelectedUnitsToControlGroup(playerScoreboardName, 5);
					} else {
						Utilities.clientControlGroupToSelectedUnits(playerScoreboardName, 5);
					}
				}
				if (controlGroup6.isPressed()) {
					// DO STUFF HERE
					if (controlModifier.isKeyDown()) {
						Utilities.clientSelectedUnitsToControlGroup(playerScoreboardName, 6);
					} else {
						Utilities.clientControlGroupToSelectedUnits(playerScoreboardName, 6);
					}
				}
				if (controlGroup7.isPressed()) {
					// DO STUFF HERE
					if (controlModifier.isKeyDown()) {
						Utilities.clientSelectedUnitsToControlGroup(playerScoreboardName, 7);
					} else {
						Utilities.clientControlGroupToSelectedUnits(playerScoreboardName, 7);
					}
				}
				if (controlGroup8.isPressed()) {
					// DO STUFF HERE
					if (controlModifier.isKeyDown()) {
						Utilities.clientSelectedUnitsToControlGroup(playerScoreboardName, 8);
					} else {
						Utilities.clientControlGroupToSelectedUnits(playerScoreboardName, 8);
					}
				}
				if (controlGroup9.isPressed()) {
					// DO STUFF HERE
					if (controlModifier.isKeyDown()) {
						Utilities.clientSelectedUnitsToControlGroup(playerScoreboardName, 9);
					} else {
						Utilities.clientControlGroupToSelectedUnits(playerScoreboardName, 9);
					}
				}
				if (deselectAll.isPressed()) {
					ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(new int[0]));
				}
				if (areaSelect.isPressed()) {

					SelectUnitsInBoundingBox(Minecraft.getInstance().player.getBoundingBox().grow(16, 3, 16));
				}
				if (boxSelect.isPressed()) {
					if (TSRTS.playerSelections.containsKey(Minecraft.getInstance().player.getScoreboardName())) {
						if (TSRTS.playerSelections.get(Minecraft.getInstance().player.getScoreboardName()).selectedUnits.size() == 2) {

							int entId1 = TSRTS.playerSelections.get(Minecraft.getInstance().player.getScoreboardName()).selectedUnits.get(0);
							int entId2 = TSRTS.playerSelections.get(Minecraft.getInstance().player.getScoreboardName()).selectedUnits.get(1);

							World w = Minecraft.getInstance().player.world;
							Entity e1 = w.getEntityByID(entId1);
							Entity e2 = w.getEntityByID(entId2);

							if (e1 != null && e2 != null) {

								double xMin = Math.min(e1.posX, e2.posX);
								double yMin = Math.min(e1.posY, e2.posY);
								double zMin = Math.min(e1.posZ, e2.posZ);
								double xMax = Math.max(e1.posX, e2.posX);
								double yMax = Math.max(e1.posY, e2.posY);
								double zMax = Math.max(e1.posZ, e2.posZ);

								xMin = Math.round(xMin - .5);
								zMin = Math.round(zMin - .5);

								xMax = Math.round(xMax + .5);
								yMax = yMax + 1;
								zMax = Math.round(zMax + .5);

								AxisAlignedBB bb = new AxisAlignedBB(xMin, yMin, zMin, xMax, yMax, zMax);

								SelectUnitsInBoundingBox(bb.grow(0, 1, 0));
							}

						}
					}

				}
				if (mainGuiOpen.isPressed()) {
					ModNetwork.SendToServer(new GuiRequestPacketToServer(Reference.GUI_ID_TOWN_HALL));
				}
			}

		}
	}

	public static void SelectUnitsInBoundingBox(AxisAlignedBB boxIn) {

		List<UnitEntity> uel = Minecraft.getInstance().player.world.getEntitiesWithinAABB(UnitEntity.class, boxIn);
		Team team = Minecraft.getInstance().player.getTeam();

		int size = uel.size();
		for (Iterator iterator = uel.iterator(); iterator.hasNext();) {
			UnitEntity unitEntity = (UnitEntity) iterator.next();
			if (!unitEntity.getTeam().isSameTeam(team)) {
				size = size - 1;
			}
		}

		int[] tmp = new int[size];
		// TODO CONSIDER TEAMS!
		for (int i = 0; i < tmp.length; i++) {
			if (uel.get(i).getTeam().isSameTeam(team)) {
				tmp[i] = uel.get(i).getEntityId();
			}

		}

		ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(tmp));
	}
}
