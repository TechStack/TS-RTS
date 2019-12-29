package com.projectreddog.tsrts.client.renderer.overlay;

import java.util.List;
import java.util.stream.Stream;

import org.lwjgl.opengl.GL11;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.init.ModResearch;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.ClientUtilities;
import com.projectreddog.tsrts.utilities.TeamEnum;
import com.projectreddog.tsrts.utilities.TeamInfo;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
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
	private ResourceLocation TEXTURE_QUEUES = new ResourceLocation(Reference.MODID, "textures/gui/overlay/queuebg.png");
	private ResourceLocation STABLES_QUEUE_ICON = new ResourceLocation(Reference.MODID, "textures/block/stablesblock_yellow_top.png");
	private ResourceLocation SIEGE_WORKSHOP_QUEUE_ICON = new ResourceLocation(Reference.MODID, "textures/block/siegeworkshopblock_yellow_top.png");

	private ResourceLocation BARRACKS_QUEUE_ICON = new ResourceLocation(Reference.MODID, "textures/block/barracksblock_yellow_top.png");

	private ResourceLocation ARCHERY_RANGE_QUEUE_ICON = new ResourceLocation(Reference.MODID, "textures/block/archeryrangeblock_yellow_top.png");
	private static ResourceLocation STATUS_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/guiwidgets.png");

	@SubscribeEvent
	public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {

		if (event.getType() == ElementType.HOTBAR) {

			int width = event.getWindow().getWidth();
			int height = event.getWindow().getHeight();
//			Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
//		    this.blit(0, 0, 0, 0, width, height);

			GL11.glPushMatrix();
			GL11.glScalef(.5f, .5f, .5f);

			if (Minecraft.getInstance().player.world != null) {
				if (Minecraft.getInstance().player.world.getScoreboard() != null) {
					if (Minecraft.getInstance().player.getScoreboardName() != null) {
						if (Minecraft.getInstance().player.world.getScoreboard().getPlayersTeam(Minecraft.getInstance().player.getScoreboardName()) != null) {
							String team = Minecraft.getInstance().player.world.getScoreboard().getPlayersTeam(Minecraft.getInstance().player.getScoreboardName()).getName();

							TeamInfo ti = TSRTS.teamInfoArray[TeamEnum.getIDFromName(team)];
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
							ClientUtilities.renderTexture(x - 10, 4, 512, 18);
							RenderHelper.enableGUIStandardItemLighting();
							for (int i = 0; i < res.length; i++) {
								if (ti != null) {
									int amt = ti.GetResource(res[i]);

									Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, TeamInfo.GetRenderItemStack(res[i]), x, y);
									x = x + xtextOffset;
									// TODO add amt back instaed of the hardcoded value
									Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + amt, x, y + ytextOffset, 14737632);
									x = x + xTextWidth;
								}
							}
							if (ti != null && ti.getCurrenResearchKey() != null && !ti.getCurrenResearchKey().equals("")) {

								String text = new TranslationTextComponent(ModResearch.getResearch(ti.getCurrenResearchKey()).getNameTranslationKey()).getUnformattedComponentText();
								int textWidth = Minecraft.getInstance().fontRenderer.getStringWidth(text);
								Minecraft.getInstance().fontRenderer.drawStringWithShadow(text, event.getWindow().getScaledWidth() * 2 - textWidth - 10, event.getWindow().getScaledHeight() * 2 - 50, 14737632);
								int divisor = ti.getFullResearchWorkRemaining();
								if (divisor > 0) {

									Minecraft.getInstance().textureManager.bindTexture(STATUS_TEXTURE);

									blit(event.getWindow().getScaledWidth() * 2 - 100 + 10, event.getWindow().getScaledHeight() * 2 - 20, 31, 0, (int) (((1 - ((float) ti.getCurrenResearchWorkRemaining() / divisor))) * 100), 8, 256, 256);
									// blit(this.x, this.y, (, (float) i + topOffset, this.width, this.height, 256, 256);

								}
							}
							RenderUnitQueues(team, event.getWindow().getScaledHeight() * 2 - 90);

						}
					}
				}

			}
			RenderUnitSelectionFrames();

			GL11.glPopMatrix();

		} else if (event.getType() == ElementType.ALL) {
			if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isSpectator()) {

				int width = event.getWindow().getWidth();
				int height = event.getWindow().getHeight();
//					Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
//				    this.blit(0, 0, 0, 0, width, height);

				GL11.glPushMatrix();
				GL11.glScalef(.5f, .5f, .5f);

				for (int j = 0; j < TeamEnum.values().length; j++) {
					TeamInfo ti = TSRTS.teamInfoArray[j];
					TeamInfo.Resources[] res = TeamInfo.Resources.values();
					int x = 5;
					int y = 5 + (j * 18 + 5);
					int yItemOffset = 30;
					int xItemOffset = 30;
					int ytextOffset = 5;
					int xtextOffset = 20;
					int xTextWidth = 55;

					x = (500 - (res.length * (xtextOffset + xTextWidth)) / 2);

					Minecraft.getInstance().textureManager.bindTexture(TEXTURE);
					// this.blit(x - 10, 5, 0, 0, 256, 18);
					ClientUtilities.renderTexture(x - 10, y - 1, 512, 18);
					RenderHelper.enableGUIStandardItemLighting();
					for (int i = 0; i < res.length; i++) {
						if (ti != null) {
							int amt = ti.GetResource(res[i]);

							Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, TeamInfo.GetRenderItemStack(res[i]), x, y);
							x = x + xtextOffset;
							// TODO add amt back instaed of the hardcoded value
							Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + amt, x, y + ytextOffset, 14737632);
							x = x + xTextWidth;

						}
					}

					String teamName = TeamEnum.getNameFromID(j);
					Minecraft.getInstance().fontRenderer.drawStringWithShadow(teamName, x - 10, y + ytextOffset, TeamEnum.values()[j].getColorCode());

				}
				GL11.glPopMatrix();

			}
		}

	}

	public void RenderUnitQueues(String team, int yValue) {
		GL11.glPushMatrix();

		// GL11.glScalef(2f, 2f, 2f);

		// GL11.glScalef(.75f, .75f, .75f);
		int teamOrd = TeamEnum.getIDFromName(team);
		int y = yValue;
		Minecraft.getInstance().textureManager.bindTexture(TEXTURE_QUEUES);

		ClientUtilities.renderTexture(0, y, 180, 80);
		int x = 5;

		Minecraft.getInstance().textureManager.bindTexture(BARRACKS_QUEUE_ICON);
		ClientUtilities.renderTexture(1, y + 2, 16, 16);
		Minecraft.getInstance().textureManager.bindTexture(ARCHERY_RANGE_QUEUE_ICON);
		ClientUtilities.renderTexture(1, y + 22, 16, 16);
		Minecraft.getInstance().textureManager.bindTexture(STABLES_QUEUE_ICON);
		ClientUtilities.renderTexture(1, y + 42, 16, 16);
		Minecraft.getInstance().textureManager.bindTexture(SIEGE_WORKSHOP_QUEUE_ICON);
		ClientUtilities.renderTexture(1, y + 62, 16, 16);

		if (TSRTS.TeamQueues[teamOrd].getBarracks() != null) {
			for (int i = 0; i < TSRTS.TeamQueues[teamOrd].getBarracks().size() && i < 30; i++) {
				int id = TSRTS.TeamQueues[teamOrd].getBarracks().get(i);

//				Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + id, 0 + i * 5, 500, 14737632);
				Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, getUnitRenderItemIcon(id), 18 + i * 5, y + 2);

			}
		}

		if (TSRTS.TeamQueues[teamOrd].getArcheryRange() != null) {
			for (int i = 0; i < TSRTS.TeamQueues[teamOrd].getArcheryRange().size() && i < 30; i++) {
				int id = TSRTS.TeamQueues[teamOrd].getArcheryRange().get(i);

//				Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + id, 0 + i * 5, 500, 14737632);
				Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, getUnitRenderItemIcon(id), 18 + i * 5, y + 22);

			}
		}

		if (TSRTS.TeamQueues[teamOrd].getStables() != null) {
			for (int i = 0; i < TSRTS.TeamQueues[teamOrd].getStables().size() && i < 30; i++) {
				int id = TSRTS.TeamQueues[teamOrd].getStables().get(i);

//				Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + id, 0 + i * 5, 500, 14737632);
				Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, getUnitRenderItemIcon(id), 18 + i * 5, y + 42);

			}
		}

		if (TSRTS.TeamQueues[teamOrd].getSiegeWorkshop() != null) {
			for (int i = 0; i < TSRTS.TeamQueues[teamOrd].getSiegeWorkshop().size() && i < 30; i++) {
				int id = TSRTS.TeamQueues[teamOrd].getSiegeWorkshop().get(i);

//				Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + id, 0 + i * 5, 500, 14737632);
				Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(null, getUnitRenderItemIcon(id), 18 + i * 5, y + 62);

			}
		}

		GL11.glPopMatrix();
	}

	public ItemStack getUnitRenderItemIcon(int id) {
		switch (id) {
		case Reference.UNIT_ID_MINION:
			return new ItemStack(Items.STICK);
		case Reference.UNIT_ID_ARCHER:
			return new ItemStack(Items.BOW);
		case Reference.UNIT_ID_LANCER:
			return new ItemStack(ModItems.LANCEITEM);
		case Reference.UNIT_ID_PIKEMAN:
			return new ItemStack(ModItems.PIKEITEM);

		default:
			break;
		}
		return ItemStack.EMPTY;
	}

	public void RenderUnitSelectionFrames() {

		Minecraft.getInstance().textureManager.bindTexture(FRAMES_TEXTURE);

		for (int i = 0; i < 9; i++) {

			ClientUtilities.renderTexture(0, i * 32, 32, 32);

		}
		for (int i = 0; i < 9; i++) {

			Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + (i + 1), 26, (i * 32) + 24, 14737632);

			Minecraft.getInstance().fontRenderer.drawStringWithShadow("" + Utilities.GetSelectedCountForControlGroup(i + 1), 12, (i * 32) + 12, 14737632);

		}
	}

}
