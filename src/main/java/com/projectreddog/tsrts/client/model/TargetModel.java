package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.TargetEntity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.PositionTextureVertex;
import net.minecraft.client.renderer.model.TexturedQuad;

public class TargetModel extends EntityModel<TargetEntity> {
	private final RendererModel box;

	PositionTextureVertex[] vertices = new PositionTextureVertex[8];
	TexturedQuad[] quad = new TexturedQuad[6];

	public TargetModel() {
		this.textureWidth = 16;
		this.textureHeight = 16;
		box = new RendererModel(this, 0, 0);
		box.addBox(-.5f, 0f, -.5f, 1, 1, 1, 0f);

		box.setRotationPoint(0.0F, 0.0F, 0.0F);

		float x = -.5f;
		float y = 0;
		float z = -.5f;

		float x2 = .5f;
		float y2 = 1;
		float z2 = .5f;

		PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(x2, y, z, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(x2, y, z2, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(x, y, z2, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(x, y2, z, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(x2, y2, z, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(x2, y2, z2, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex8 = new PositionTextureVertex(x, y2, z2, 0.0F, 0.0F);

		quad[0] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex3, positiontexturevertex4 }, 0, 0, 16, 16, 16, 16);
		quad[1] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex5, positiontexturevertex6, positiontexturevertex2 }, 0, 0, 16, 16, 16, 16);
		quad[2] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex7, positiontexturevertex8, positiontexturevertex4 }, 0, 0, 16, 16, 16, 16);
		quad[3] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex2, positiontexturevertex6, positiontexturevertex7, positiontexturevertex3 }, 0, 0, 16, 16, 16, 16);
		quad[4] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex4, positiontexturevertex8, positiontexturevertex5 }, 0, 0, 16, 16, 16, 16);
		quad[5] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex8, positiontexturevertex7, positiontexturevertex6, positiontexturevertex5 }, 0, 0, 16, 16, 16, 16);

	}

	@Override
	public void render(TargetEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
		drawBox(bufferbuilder, 1);
		// box.render(scale);

	}

	public void drawBox(BufferBuilder renderer, float scale) {

		for (TexturedQuad texturedquad : this.quad) {
			texturedquad.draw(renderer, scale);
		}

	}

}