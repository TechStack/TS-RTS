package com.projectreddog.tsrts.handler;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.AdvancedKnightEntity;
import com.projectreddog.tsrts.entities.KnightEntity;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.items.builderitems.BuilderItem;
import com.projectreddog.tsrts.items.builderitems.TownHallBuilderItem;
import com.projectreddog.tsrts.items.builderitems.WallBuilderItem;
import com.projectreddog.tsrts.network.GuiRequestPacketToServer;
import com.projectreddog.tsrts.network.PlayerSelectionChangedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {

	public static boolean toggleDisplayInfo = true;
	public static final KeyBinding controlGroup1 = new KeyBinding(Reference.MODID + ".key.controlgroup1", GLFW.GLFW_KEY_Z, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup2 = new KeyBinding(Reference.MODID + ".key.controlgroup2", GLFW.GLFW_KEY_X, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup3 = new KeyBinding(Reference.MODID + ".key.controlgroup3", GLFW.GLFW_KEY_C, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup4 = new KeyBinding(Reference.MODID + ".key.controlgroup4", GLFW.GLFW_KEY_V, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup5 = new KeyBinding(Reference.MODID + ".key.controlgroup5", GLFW.GLFW_KEY_B, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup6 = new KeyBinding(Reference.MODID + ".key.controlgroup6", GLFW.GLFW_KEY_N, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup7 = new KeyBinding(Reference.MODID + ".key.controlgroup7", GLFW.GLFW_KEY_M, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup8 = new KeyBinding(Reference.MODID + ".key.controlgroup8", GLFW.GLFW_KEY_COMMA, "key.categories." + Reference.MODID);
	public static final KeyBinding controlGroup9 = new KeyBinding(Reference.MODID + ".key.controlgroup9", GLFW.GLFW_KEY_PERIOD, "key.categories." + Reference.MODID);
	public static final KeyBinding controlModifier = new KeyBinding(Reference.MODID + ".key.control", GLFW.GLFW_KEY_LEFT_CONTROL, "key.categories." + Reference.MODID);
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

				if (Minecraft.getInstance().player.isSpectator()) {
					if (controlModifier.isPressed()) {
						toggleDisplayInfo = !toggleDisplayInfo;
					}
				}

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

					if (Minecraft.getInstance().player.isSneaking()) {
						if (TSRTS.playerSelections.containsKey(Minecraft.getInstance().player.getScoreboardName())) {
							if (TSRTS.playerSelections.get(Minecraft.getInstance().player.getScoreboardName()).selectedUnits.size() == 1) {

								int entId = TSRTS.playerSelections.get(Minecraft.getInstance().player.getScoreboardName()).selectedUnits.get(0);
								World w = Minecraft.getInstance().player.world;

								Entity e = w.getEntityByID(entId);
								if (e instanceof UnitEntity) {
									UnitEntity ue = (UnitEntity) e;
									if (ue instanceof KnightEntity) {
										SelectUnitsInBoundingBox(Minecraft.getInstance().player.getBoundingBox().grow(16, 3, 16), KnightEntity.class);
									} else if (ue instanceof AdvancedKnightEntity) {
										SelectUnitsInBoundingBox(Minecraft.getInstance().player.getBoundingBox().grow(16, 3, 16), AdvancedKnightEntity.class);
									} else {

										SelectUnitsInBoundingBox(Minecraft.getInstance().player.getBoundingBox().grow(16, 3, 16), ue.getClass());
									}
								}
							}
						}
					} else {
						SelectUnitsInBoundingBox(Minecraft.getInstance().player.getBoundingBox().grow(16, 3, 16), UnitEntity.class);
					}

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

								SelectUnitsInBoundingBox(bb.grow(0, 1, 0), UnitEntity.class);
							}

						}
					}

				}
				if (mainGuiOpen.isPressed()) {
					ModNetwork.SendToServer(new GuiRequestPacketToServer(Reference.GUI_BUTTON_MAIN_MENU_ECO));
				}
			}

		}
	}

	public static void SelectUnitsInBoundingBox(AxisAlignedBB boxIn, Class<? extends UnitEntity> class1) {

		List<UnitEntity> uel = Minecraft.getInstance().player.world.getEntitiesWithinAABB(class1, boxIn);
		Team team = Minecraft.getInstance().player.getTeam();

		int size = uel.size();
		for (Iterator iterator = uel.iterator(); iterator.hasNext();) {
			UnitEntity unitEntity = (UnitEntity) iterator.next();

			if (unitEntity.getTeam() != null && !unitEntity.getTeam().isSameTeam(team)) {
				size = size - 1;
			}
		}

		int[] tmp = new int[size];
		// TODO CONSIDER TEAMS!
		for (int i = 0; i < tmp.length; i++) {
			if (uel.get(i).getTeam() != null && uel.get(i).getTeam().isSameTeam(team)) {
				tmp[i] = uel.get(i).getEntityId();
			}

		}

		ModNetwork.SendToServer(new PlayerSelectionChangedPacketToServer(tmp));
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRenderWorldLastEvent(RenderWorldLastEvent event) {

		if (Minecraft.getInstance().player.getHeldItemMainhand().getItem() instanceof BuilderItem) {
			// we have a builder item so we want to go deeper
			if (Minecraft.getInstance().objectMouseOver != null && Minecraft.getInstance().objectMouseOver.getType() == RayTraceResult.Type.BLOCK) {
				BlockPos blockpos = ((BlockRayTraceResult) Minecraft.getInstance().objectMouseOver).getPos();
				// we are looking at a block and we have its cords.
				Vec3i v3 = ((BuilderItem) Minecraft.getInstance().player.getHeldItemMainhand().getItem()).getSize();
				// Vec3i v3 = new Vec3i(3, 1, 3);

				Direction d = Direction.getFacingFromVector(Minecraft.getInstance().player.getLookVec().getX(), 0, Minecraft.getInstance().player.getLookVec().getZ());

				GlStateManager.pushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				double playerX = Minecraft.getInstance().player.prevPosX + (Minecraft.getInstance().player.posX - Minecraft.getInstance().player.prevPosX) * event.getPartialTicks();
				double playerY = Minecraft.getInstance().player.prevPosY + (Minecraft.getInstance().player.posY - Minecraft.getInstance().player.prevPosY) * event.getPartialTicks();
				double playerZ = Minecraft.getInstance().player.prevPosZ + (Minecraft.getInstance().player.posZ - Minecraft.getInstance().player.prevPosZ) * event.getPartialTicks();

				GlStateManager.translated(-playerX, -playerY, -playerZ);

				GlStateManager.rotatef(d.getHorizontalAngle(), 0, 1, 0);

				// Sphere of influence
				if ((!(Minecraft.getInstance().player.getHeldItemMainhand().getItem() instanceof WallBuilderItem) && Utilities.isValidLocation(Minecraft.getInstance().player.world, blockpos, d, v3) || (Minecraft.getInstance().player.getHeldItemMainhand().getItem() instanceof WallBuilderItem && Utilities.IsLocationValidForWall(Minecraft.getInstance().player.world, blockpos, d))) && ((BuilderItem) Minecraft.getInstance().player.getHeldItemMainhand().getItem()).CanPlaceOn(Minecraft.getInstance().player.world.getBlockState(blockpos).getBlock())) {

					if (Utilities.isInSphereOfInfluence(Minecraft.getInstance().player.world.getScoreboard().getPlayersTeam(Minecraft.getInstance().player.getScoreboardName()), blockpos) || (Minecraft.getInstance().player.getHeldItemMainhand().getItem() instanceof TownHallBuilderItem)) {
						GlStateManager.color4f(255, 255, 255, 128);
					} else {
						GlStateManager.color4f(0, 0, 0, 128);
					}
				} else if (!((BuilderItem) Minecraft.getInstance().player.getHeldItemMainhand().getItem()).CanPlaceOn(Minecraft.getInstance().player.world.getBlockState(blockpos).getBlock()) && Utilities.isValidLocation(Minecraft.getInstance().player.world, blockpos, d, v3)) {

					GlStateManager.color4f(255, 255, 0, 128);
				} else {
					GlStateManager.color4f(255, 0, 0, 128);
				}
				float x = 0;
				float y = 0;
				float z = 0;
				float zSign = 1;
				switch (d) {

				case NORTH:
					x = blockpos.getX() * -1 - .5f;
					y = blockpos.getY();
					z = blockpos.getZ() * -1;
					break;
				case EAST:
					x = (blockpos.getZ() * 1) + .5f;
					y = blockpos.getY();
					z = (blockpos.getX() * -1) - 1;
					zSign = -1;
					break;
				case SOUTH:
					x = blockpos.getX() + .5f;
					y = blockpos.getY();
					z = blockpos.getZ() + 1;
					break;
				case WEST:
					x = blockpos.getZ() * -1 - .5f;
					y = blockpos.getY();
					z = blockpos.getX() * 1;
					zSign = -1;
					break;

				}

				GL11.glBegin(GL11.GL_LINE_STRIP);
				GlStateManager.vertex3f(x + ((float) v3.getX() / 2), y - .5f, z);
				GlStateManager.vertex3f(x - ((float) v3.getX() / 2), y - .5f, z);

				GlStateManager.vertex3f(x - ((float) v3.getX() / 2), y + ((float) v3.getY()) - .5f, z);
				GlStateManager.vertex3f(x + ((float) v3.getX() / 2), y + ((float) v3.getY()) - .5f, z);
				GlStateManager.vertex3f(x + ((float) v3.getX() / 2), y - .5f, z);
				GlStateManager.vertex3f(x + ((float) v3.getX() / 2), y - .5f, z + (((float) v3.getZ()) * zSign));

				GlStateManager.vertex3f(x - ((float) v3.getX() / 2), y - .5f, z + (((float) v3.getZ()) * zSign));
				GlStateManager.vertex3f(x - ((float) v3.getX() / 2), y - .5f, z);
				GlStateManager.vertex3f(x - ((float) v3.getX() / 2), y + ((float) v3.getY()) - .5f, z);

				GlStateManager.vertex3f(x - ((float) v3.getX() / 2), y + ((float) v3.getY()) - .5f, z + (((float) v3.getZ()) * zSign));
				GlStateManager.vertex3f(x - ((float) v3.getX() / 2), y - .5f, z + (((float) v3.getZ()) * zSign));
				GlStateManager.vertex3f(x - ((float) v3.getX() / 2), y + ((float) v3.getY()) - .5f, z + (((float) v3.getZ()) * zSign));

				GlStateManager.vertex3f(x + ((float) v3.getX() / 2), y + ((float) v3.getY()) - .5f, z + (((float) v3.getZ()) * zSign));
				GlStateManager.vertex3f(x + ((float) v3.getX() / 2), y - .5f, z + (((float) v3.getZ()) * zSign));
				GlStateManager.vertex3f(x + ((float) v3.getX() / 2), y + ((float) v3.getY()) - .5f, z + (((float) v3.getZ()) * zSign));
				GlStateManager.vertex3f(x + ((float) v3.getX() / 2), y + ((float) v3.getY()) - .5f, z);

				// GlStateManager.vertex3f(x - (v3.getX() / 2), y - .5f, z);
				GL11.glEnd();
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GlStateManager.popMatrix();

			}
		}

	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void OnColorHandlerEvent(final ColorHandlerEvent.Item event) {

		final ItemColors itemColors = event.getItemColors();

		final IItemColor itemColorHandler = (stack, tintIndex) -> {
			return itemColors.getColor(stack, tintIndex);

		};
		itemColors.register(itemColorHandler, ModItems.TEAM_DIAMOND_ARMOR_BOOTS);
		itemColors.register(itemColorHandler, ModItems.TEAM_DIAMOND_ARMOR_LEGGINGS);
		itemColors.register(itemColorHandler, ModItems.TEAM_DIAMOND_ARMOR_CHESTPLATE);
		itemColors.register(itemColorHandler, ModItems.TEAM_DIAMOND_ARMOR_HELMET);

		itemColors.register(itemColorHandler, ModItems.TEAM_IRON_ARMOR_BOOTS);
		itemColors.register(itemColorHandler, ModItems.TEAM_IRON_ARMOR_LEGGINGS);
		itemColors.register(itemColorHandler, ModItems.TEAM_IRON_ARMOR_CHESTPLATE);
		itemColors.register(itemColorHandler, ModItems.TEAM_IRON_ARMOR_HELMET);

	}
}
