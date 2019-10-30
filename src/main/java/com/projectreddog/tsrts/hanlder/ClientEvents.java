package com.projectreddog.tsrts.hanlder;

import org.lwjgl.glfw.GLFW;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.client.settings.KeyBinding;
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

	@SubscribeEvent
	public static void onClientTickEvent(final ClientTickEvent event) {

		if (event.phase != TickEvent.Phase.END)
			return;

		{
			if (controlGroup1.isPressed()) {
				// DO STUFF HERE
				TSRTS.LOGGER.info("Z PRESS");

			}
			if (controlGroup2.isPressed()) {
				// DO STUFF HERE

			}
			if (controlGroup3.isPressed()) {
				// DO STUFF HERE
			}
			if (controlGroup4.isPressed()) {
				// DO STUFF HERE

			}
			if (controlGroup5.isPressed()) {
				// DO STUFF HERE
			}
			if (controlGroup6.isPressed()) {
				// DO STUFF HERE

			}
			if (controlGroup7.isPressed()) {
				// DO STUFF HERE
			}
			if (controlGroup8.isPressed()) {
				// DO STUFF HERE

			}
			if (controlGroup9.isPressed()) {
				// DO STUFF HERE
			}

		}
	}
}
