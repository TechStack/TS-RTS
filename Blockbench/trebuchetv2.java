//Made with Blockbench
//Paste this code into your mod.

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class  extends ModelBase {
	private final ModelRenderer parent;
	private final ModelRenderer base;
	private final ModelRenderer backLegs;
	private final ModelRenderer frontLegs;
	private final ModelRenderer sideLegR;
	private final ModelRenderer sideLegL;
	private final ModelRenderer PivotPoint;
	private final ModelRenderer slingAttach;
	private final ModelRenderer slingEnd;
	private final ModelRenderer counterWeightAttachment;
	private final ModelRenderer counterWeightEnd;

	public () {
		textureWidth = 64;
		textureHeight = 64;

		parent = new ModelRenderer(this);
		parent.setRotationPoint(0.0F, 24.0F, 0.0F);

		base = new ModelRenderer(this);
		base.setRotationPoint(7.0F, -1.0F, -8.0F);
		parent.addChild(base);
		base.cubeList.add(new ModelBox(base, -20, 0, -19.0F, -1.0F, -5.0F, 2, 2, 26, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, 3.0F, -1.0F, -5.0F, 2, 2, 26, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -17.0F, -1.0F, 7.0F, 20, 2, 2, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -31.0F, -1.0F, 7.0F, 12, 2, 2, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, 5.0F, -1.0F, 7.0F, 12, 2, 2, 0.0F, false));

		backLegs = new ModelRenderer(this);
		backLegs.setRotationPoint(-7.0F, -1.0F, 16.0F);
		setRotationAngle(backLegs, 0.3142F, 0.0F, 0.0F);
		base.addChild(backLegs);
		backLegs.cubeList.add(new ModelBox(backLegs, 0, 0, -12.0F, -37.0F, 3.0F, 2, 39, 2, 0.0F, false));
		backLegs.cubeList.add(new ModelBox(backLegs, 0, 0, 10.0F, -37.0F, 3.0F, 2, 39, 2, 0.0F, false));

		frontLegs = new ModelRenderer(this);
		frontLegs.setRotationPoint(-7.0F, 0.0F, 0.0F);
		setRotationAngle(frontLegs, -0.3142F, 0.0F, 0.0F);
		base.addChild(frontLegs);
		frontLegs.cubeList.add(new ModelBox(frontLegs, 0, 0, -12.0F, -38.0F, -5.0F, 2, 39, 2, 0.0F, false));
		frontLegs.cubeList.add(new ModelBox(frontLegs, 0, 0, 10.0F, -38.0F, -5.0F, 2, 39, 2, 0.0F, false));

		sideLegR = new ModelRenderer(this);
		sideLegR.setRotationPoint(-27.0F, 0.0F, 9.0F);
		setRotationAngle(sideLegR, 0.0F, 0.0F, 0.3142F);
		base.addChild(sideLegR);
		sideLegR.cubeList.add(new ModelBox(sideLegR, 0, 0, -4.0F, -38.0F, -2.0F, 2, 39, 2, 0.0F, false));

		sideLegL = new ModelRenderer(this);
		sideLegL.setRotationPoint(17.0F, 0.0F, 9.0F);
		setRotationAngle(sideLegL, 0.0F, 0.0F, -0.3142F);
		base.addChild(sideLegL);
		sideLegL.cubeList.add(new ModelBox(sideLegL, 0, 0, -2.0F, -39.0F, -2.0F, 2, 39, 2, 0.0F, false));

		PivotPoint = new ModelRenderer(this);
		PivotPoint.setRotationPoint(0.0F, -38.0F, 0.0F);
		parent.addChild(PivotPoint);
		PivotPoint.cubeList.add(new ModelBox(PivotPoint, 0, 5, -12.0F, -2.0F, -1.0F, 24, 2, 2, 0.0F, false));
		PivotPoint.cubeList.add(new ModelBox(PivotPoint, 0, 3, -2.0F, -4.0F, -12.0F, 4, 2, 58, 0.0F, true));

		slingAttach = new ModelRenderer(this);
		slingAttach.setRotationPoint(0.0F, -2.0F, 45.0F);
		PivotPoint.addChild(slingAttach);
		slingAttach.cubeList.add(new ModelBox(slingAttach, 0, 0, -1.0F, 0.0F, 0.0F, 2, 24, 1, 0.0F, false));

		slingEnd = new ModelRenderer(this);
		slingEnd.setRotationPoint(0.0F, 24.0F, 0.5F);
		slingAttach.addChild(slingEnd);
		slingEnd.cubeList.add(new ModelBox(slingEnd, 0, 0, -2.0F, 0.0F, -0.5F, 4, 4, 1, 0.0F, false));

		counterWeightAttachment = new ModelRenderer(this);
		counterWeightAttachment.setRotationPoint(0.0F, -2.0F, -11.0F);
		PivotPoint.addChild(counterWeightAttachment);
		counterWeightAttachment.cubeList.add(new ModelBox(counterWeightAttachment, 0, 0, -1.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F, false));

		counterWeightEnd = new ModelRenderer(this);
		counterWeightEnd.setRotationPoint(0.0F, 15.0F, 0.5F);
		counterWeightAttachment.addChild(counterWeightEnd);
		counterWeightEnd.cubeList.add(new ModelBox(counterWeightEnd, 0, 8, -3.0F, -12.0F, -4.0F, 6, 9, 8, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		parent.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}