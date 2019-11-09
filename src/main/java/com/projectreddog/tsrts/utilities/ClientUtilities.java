package com.projectreddog.tsrts.utilities;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class ClientUtilities {
	public static void renderTexture(double left, double top, double width, double height) {
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
