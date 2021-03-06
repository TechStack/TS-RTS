package com.projectreddog.tsrts.client.model;

import com.projectreddog.tsrts.entities.TrebuchetEntity;

//Made with Blockbench
//Paste this code into your mod.

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class TrebuchetModel extends EntityModel<TrebuchetEntity> {
	private final RendererModel parent;
	private final RendererModel base;
	private final RendererModel backLegs;
	private final RendererModel frontLegs;
	private final RendererModel sideLegR;
	private final RendererModel sideLegL;
	private final RendererModel PivotPoint;
	private final RendererModel slingAttach;
	private final RendererModel slingEnd;
	private final RendererModel counterWeightAttachment;
	private final RendererModel counterWeightEnd;

	public TrebuchetModel() {
		textureWidth = 64;
		textureHeight = 64;

		parent = new RendererModel(this);
		parent.setRotationPoint(0.0F, 24.0F, 0.0F);

		base = new RendererModel(this);
		base.setRotationPoint(7.0F, -1.0F, -8.0F);
		parent.addChild(base);
		base.cubeList.add(new ModelBox(base, -20, 0, -19.0F, -1.0F, -5.0F, 2, 2, 26, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, 3.0F, -1.0F, -5.0F, 2, 2, 26, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -17.0F, -1.0F, 7.0F, 20, 2, 2, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -31.0F, -1.0F, 7.0F, 12, 2, 2, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, 5.0F, -1.0F, 7.0F, 12, 2, 2, 0.0F, false));

		backLegs = new RendererModel(this);
		backLegs.setRotationPoint(-7.0F, -1.0F, 16.0F);
		setRotationAngle(backLegs, 0.3142F, 0.0F, 0.0F);
		base.addChild(backLegs);
		backLegs.cubeList.add(new ModelBox(backLegs, 0, 0, -12.0F, -37.0F, 3.0F, 2, 39, 2, 0.0F, false));
		backLegs.cubeList.add(new ModelBox(backLegs, 0, 0, 10.0F, -37.0F, 3.0F, 2, 39, 2, 0.0F, false));

		frontLegs = new RendererModel(this);
		frontLegs.setRotationPoint(-7.0F, 0.0F, 0.0F);
		setRotationAngle(frontLegs, -0.3142F, 0.0F, 0.0F);
		base.addChild(frontLegs);
		frontLegs.cubeList.add(new ModelBox(frontLegs, 0, 0, -12.0F, -38.0F, -5.0F, 2, 39, 2, 0.0F, false));
		frontLegs.cubeList.add(new ModelBox(frontLegs, 0, 0, 10.0F, -38.0F, -5.0F, 2, 39, 2, 0.0F, false));

		sideLegR = new RendererModel(this);
		sideLegR.setRotationPoint(-27.0F, 0.0F, 9.0F);
		setRotationAngle(sideLegR, 0.0F, 0.0F, 0.3142F);
		base.addChild(sideLegR);
		sideLegR.cubeList.add(new ModelBox(sideLegR, 0, 0, -4.0F, -38.0F, -2.0F, 2, 39, 2, 0.0F, false));

		sideLegL = new RendererModel(this);
		sideLegL.setRotationPoint(17.0F, 0.0F, 9.0F);
		setRotationAngle(sideLegL, 0.0F, 0.0F, -0.3142F);
		base.addChild(sideLegL);
		sideLegL.cubeList.add(new ModelBox(sideLegL, 0, 0, -2.0F, -39.0F, -2.0F, 2, 39, 2, 0.0F, false));

		PivotPoint = new RendererModel(this);
		PivotPoint.setRotationPoint(0.0F, -38.0F, 0.0F);
		parent.addChild(PivotPoint);
		PivotPoint.cubeList.add(new ModelBox(PivotPoint, 0, 5, -12.0F, -2.0F, -1.0F, 24, 2, 2, 0.0F, false));
		PivotPoint.cubeList.add(new ModelBox(PivotPoint, 0, 3, -2.0F, -4.0F, -12.0F, 4, 2, 58, 0.0F, true));

		slingAttach = new RendererModel(this);
		slingAttach.setRotationPoint(0.0F, -2.0F, 45.0F);
		PivotPoint.addChild(slingAttach);
		slingAttach.cubeList.add(new ModelBox(slingAttach, 0, 0, -1.0F, 0.0F, 0.0F, 2, 24, 1, 0.0F, false));

		slingEnd = new RendererModel(this);
		slingEnd.setRotationPoint(0.0F, 24.0F, 0.5F);
		slingAttach.addChild(slingEnd);
		slingEnd.cubeList.add(new ModelBox(slingEnd, 0, 0, -2.0F, 0.0F, -0.5F, 4, 4, 1, 0.0F, false));

		counterWeightAttachment = new RendererModel(this);
		counterWeightAttachment.setRotationPoint(0.0F, -2.0F, -11.0F);
		PivotPoint.addChild(counterWeightAttachment);
		counterWeightAttachment.cubeList.add(new ModelBox(counterWeightAttachment, 0, 0, -1.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F, false));

		counterWeightEnd = new RendererModel(this);
		counterWeightEnd.setRotationPoint(0.0F, 15.0F, 0.5F);
		counterWeightAttachment.addChild(counterWeightEnd);
		counterWeightEnd.cubeList.add(new ModelBox(counterWeightEnd, 0, 8, -3.0F, -12.0F, -4.0F, 6, 9, 8, 0.0F, false));
	}

	@Override
	public void render(TrebuchetEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationsForAttackStep(entity);
		// if (entity.getSetupStep() < TrebuchetEntity.MAX_STEUP_STEP_COUNT) {
		base.isHidden = true;

		sideLegL.isHidden = false;
		sideLegR.isHidden = false;

		frontLegs.isHidden = false;
		backLegs.isHidden = false;

		PivotPoint.isHidden = false;

		slingAttach.isHidden = false;

		slingEnd.isHidden = false;

		counterWeightAttachment.isHidden = false;
		counterWeightEnd.isHidden = false;

		base.isHidden = false;
		if (entity.getSetupStep() <= 0) {
			sideLegL.isHidden = true;
			sideLegR.isHidden = true;
		}
		if (entity.getSetupStep() <= 1) {

			frontLegs.isHidden = true;
			backLegs.isHidden = true;
		}
		if (entity.getSetupStep() <= 2) {

			PivotPoint.isHidden = true;
		}
		if (entity.getSetupStep() <= 3) {

			slingAttach.isHidden = true;
		}

		if (entity.getSetupStep() <= 4) {

			slingEnd.isHidden = true;
		}

		if (entity.getSetupStep() <= 5) {
			counterWeightAttachment.isHidden = true;
		}
		if (entity.getSetupStep() <= 6) {
			counterWeightEnd.isHidden = true;
		}

		// }
		parent.render(f5);
//		entity.attackStep++;
//		if (entity.attackStep > 12) {
//			entity.attackStep = 0;
//		}

		// GlStateManager.rotatef(entity.attackStep + 15f, 1.0F, 0.0F, 0.0F);
		// PivotPoint.postRender(f5);
	}

	public void setRotationsForAttackStep(TrebuchetEntity entity) {
		if (entity.getAttackStep() == 0) {
			setRotationAngle(PivotPoint, (float) ((float) -57f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (-33f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (33f) * Math.PI / 180f), 0, 0);
		}
		if (entity.getAttackStep() == 1) {
			setRotationAngle(PivotPoint, (float) ((float) -45f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (-32f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (33f) * Math.PI / 180f), 0, 0);

		}
		if (entity.getAttackStep() == 2) {
			setRotationAngle(PivotPoint, (float) ((float) -21f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (-21f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (21f) * Math.PI / 180f), 0, 0);

		}
		if (entity.getAttackStep() == 3) {
			setRotationAngle(PivotPoint, (float) ((float) 15f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (21f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (-15f) * Math.PI / 180f), 0, 0);

		}

		if (entity.getAttackStep() == 4) {
			setRotationAngle(PivotPoint, (float) ((float) 48f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (40f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (-48f) * Math.PI / 180f), 0, 0);

		}

		if (entity.getAttackStep() == 5) {
			setRotationAngle(PivotPoint, (float) ((float) 84f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (70f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (-84f) * Math.PI / 180f), 0, 0);

		}

		if (entity.getAttackStep() == 6) {
			setRotationAngle(PivotPoint, (float) ((float) 110f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (100f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (-110f) * Math.PI / 180f), 0, 0);

		}

		if (entity.getAttackStep() == 7) {
			setRotationAngle(PivotPoint, (float) ((float) 115f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (120f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (-115f) * Math.PI / 180f), 0, 0);

		}

		if (entity.getAttackStep() == 8) {
			setRotationAngle(PivotPoint, (float) ((float) 90f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (160f) * Math.PI / 180f), 0, 0);

			setRotationAngle(counterWeightAttachment, (float) ((float) (-90f) * Math.PI / 180f), 0, 0);

		}

		if (entity.getAttackStep() == 9) {
			setRotationAngle(PivotPoint, (float) ((float) 70f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (200f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (-70f) * Math.PI / 180f), 0, 0);

		}

		if (entity.getAttackStep() == 10) {
			setRotationAngle(PivotPoint, (float) ((float) 80f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (230f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (-80f) * Math.PI / 180f), 0, 0);

		}

		if (entity.getAttackStep() == 11) {
			setRotationAngle(PivotPoint, (float) ((float) 90f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (270f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (-90f) * Math.PI / 180f), 0, 0);

		}

		if (entity.getAttackStep() == 12) {
			setRotationAngle(PivotPoint, (float) ((float) 95f * Math.PI / 180f), 0, 0);
			setRotationAngle(slingAttach, (float) ((float) (270f) * Math.PI / 180f), 0, 0);
			setRotationAngle(counterWeightAttachment, (float) ((float) (-95f) * Math.PI / 180f), 0, 0);

		}
	}

	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}