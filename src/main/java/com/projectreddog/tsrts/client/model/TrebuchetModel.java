package com.projectreddog.tsrts.client.model;
//Made with Blockbench

import com.projectreddog.tsrts.entities.TrebuchetEntity;

//Paste this code into your mod.

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class TrebuchetModel extends EntityModel {
	private final RendererModel parent;
	private final RendererModel base;
	private final RendererModel Pivotpoint;
	private final RendererModel bone;
	private final RendererModel bone2;

	public TrebuchetModel() {
		textureWidth = 64;
		textureHeight = 64;

		parent = new RendererModel(this);
		parent.setRotationPoint(0.0F, 24.0F, 0.0F);

		base = new RendererModel(this);
		base.setRotationPoint(7.0F, -1.0F, -8.0F);
		parent.addChild(base);
		base.cubeList.add(new ModelBox(base, -20, 0, -15.0F, -1.0F, 0.0F, 2, 2, 16, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -15.0F, -25.0F, 0.0F, 2, 2, 16, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -1.0F, -1.0F, 0.0F, 2, 2, 16, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -1.0F, -25.0F, 0.0F, 2, 2, 16, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -1.0F, -23.0F, 0.0F, 2, 22, 2, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -1.0F, -23.0F, 14.0F, 2, 22, 2, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -15.0F, -23.0F, 14.0F, 2, 22, 2, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -15.0F, -23.0F, 0.0F, 2, 22, 2, 0.0F, false));

		Pivotpoint = new RendererModel(this);
		Pivotpoint.setRotationPoint(0.0F, -26.0F, 0.0F);
		parent.addChild(Pivotpoint);
		Pivotpoint.cubeList.add(new ModelBox(Pivotpoint, 0, 5, -8.0F, -2.0F, -1.0F, 16, 2, 2, 0.0F, false));
		Pivotpoint.cubeList.add(new ModelBox(Pivotpoint, 0, 3, -1.0F, -4.0F, -12.0F, 2, 2, 55, 0.0F, true));

		bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, -2.0F, -11.0F);
		Pivotpoint.addChild(bone);
		bone.cubeList.add(new ModelBox(bone, 0, 0, -1.0F, -1.0F, 0.0F, 2, 4, 1, 0.0F, false));

		bone2 = new RendererModel(this);
		bone2.setRotationPoint(0.0F, 3.0F, 0.5F);
		bone.addChild(bone2);
		bone2.cubeList.add(new ModelBox(bone2, 0, 8, -2.0F, 0.0F, -3.5F, 4, 9, 8, 0.0F, false));
	}

	public void render(TrebuchetEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		parent.render(f5);
	}

	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
