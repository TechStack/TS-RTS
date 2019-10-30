package com.projectreddog.tsrts.client.renderer.overlay;

import java.util.List;
import java.util.stream.Stream;

import org.lwjgl.opengl.GL11;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.TeamInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderOverlay extends Screen {

	public RenderOverlay() {
		super(new ITextComponent() {

			@Override
			public Stream<ITextComponent> stream() {
				return null;
			}

			@Override
			public ITextComponent shallowCopy() {
				return null;
			}

			@Override
			public ITextComponent setStyle(Style style) {
				return null;
			}

			@Override
			public String getUnformattedComponentText() {
				return null;
			}

			@Override
			public Style getStyle() {
				return null;
			}

			@Override
			public List<ITextComponent> getSiblings() {
				return null;
			}

			@Override
			public ITextComponent appendSibling(ITextComponent component) {
				return null;
			}
		});
	}

	private ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/overlay/overlay.png");
	private ResourceLocation FRAMES_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/overlay/frame.png");

	@SubscribeEvent
	public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {

		if (event.getType() == ElementType.HOTBAR) {
			int width = event.getWindow().getWidth();
			int height = event.getWindow().getHeight();
//			Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
//		    this.blit(0, 0, 0, 0, width, height);

			GL11.glPushMatrix();
			GL11.glScalef(.5f, .5f, .5f);

			if (minecraft.getInstance().player.world != null) {
				if (minecraft.getInstance().player.world.getScoreboard() != null) {
					if (minecraft.getInstance().player.getScoreboardName() != null) {
						if (minecraft.getInstance().player.world.getScoreboard().getPlayersTeam(minecraft.getInstance().player.getScoreboardName()) != null) {
							String team = minecraft.getInstance().player.world.getScoreboard().getPlayersTeam(minecraft.getInstance().player.getScoreboardName()).getName();
							if (TSRTS.teamInfoMap.containsKey(team)) {
								TeamInfo ti = TSRTS.teamInfoMap.get(team);
								TeamInfo.Resources[] res = TeamInfo.Resources.values();
								int x = 5;
								int y = 5;
								int yItemOffset = 30;
								int xItemOffset = 30;
								int ytextOffset = 5;
								int xtextOffset = 20;
								int xTextWidth = 55;

								x = (500 - (res.length * (xtextOffset + xTextWidth)) / 2);

								Minecraft.getInstance().textureManager.bindTexture(TEXTURE);
								// this.blit(x - 10, 5, 0, 0, 256, 18);
								renderTexture(x - 50, 4, 512, 18);
								RenderHelper.enableGUIStandardItemLighting();
								for (int i = 0; i < res.length; i++) {
									int amt = ti.GetResource(res[i]);

									Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, TeamInfo.GetRenderItemStack(res[i]), x, y);
									x = x + xtextOffset;
									// TODO add amt back instaed of the hardcoded value
									Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + amt, x, y + ytextOffset, 14737632);
									x = x + xTextWidth;
								}
							}
						}
					}
				}
			}
			RenderUnitSelectionFrames();

			GL11.glPopMatrix();
		}

	}

	public void RenderUnitSelectionFrames() {

		Minecraft.getInstance().textureManager.bindTexture(FRAMES_TEXTURE);

		for (int i = 0; i < 9; i++) {

			renderTexture(0, i * 32, 32, 32);

		}
		for (int i = 0; i < 9; i++) {

			Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + (i + 1), 26, (i * 32) + 24, 14737632);

		}
	}

	public void renderTexture(double left, double top, double width, double height) {
		double right = left + width;
		double bottom = top + height;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(left, bottom, 0).tex(0, 1).endVertex();
		bufferbuilder.pos(right, bottom, 0).tex(1, 1).endVertex();
		bufferbuilder.pos(right, top, 0).tex(1, 0).endVertex();
		bufferbuilder.pos(left, top, 0).tex(0, 0).endVertex();

		tessellator.draw();
	}
}
