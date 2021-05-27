package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.VoidFragmentEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class VoidFragmentModel<T extends Entity> extends CompositeEntityModel<T> {
    private final ModelPart body;
	private final ModelPart orbBody;

    public VoidFragmentModel() {
        // this.body = (new ModelPart(this)).setTextureSize(64, 64);
        // this.body.setPivot(0.0F, 24.0F, 0.0F);
        // this.body.setTextureOffset(0, 4).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        // this.body.setTextureOffset(30, 30).addCuboid(-3.0F, -4.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
        // this.body.setTextureOffset(0, 30).addCuboid(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
        // this.body.setTextureOffset(0, 0).addCuboid(-7.0F, -10.0F, -7.0F, 14.0F, 4.0F, 14.0F, 0.0F, false);
        // this.body.setTextureOffset(0, 18).addCuboid(-5.0F, -12.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
        // this.body.setTextureOffset(30, 18).addCuboid(-3.0F, -14.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
        // this.body.setTextureOffset(0, 0).addCuboid(-1.0F, -16.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		body = (new ModelPart(this)).setTextureSize(128, 128);
		body.setPivot(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(6, 10).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		body.setTextureOffset(56, 12).addCuboid(-3.0F, -4.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
		body.setTextureOffset(42, 20).addCuboid(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
		body.setTextureOffset(0, 20).addCuboid(-7.0F, -10.0F, -7.0F, 14.0F, 4.0F, 14.0F, 0.0F, false);
		body.setTextureOffset(42, 0).addCuboid(-5.0F, -12.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
		body.setTextureOffset(48, 52).addCuboid(-3.0F, -14.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
		body.setTextureOffset(6, 4).addCuboid(-1.0F, -16.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		orbBody = (new ModelPart(this)).setTextureSize(128, 128);
		orbBody.setPivot(0.0F, 24.0F, 0.0F);
		orbBody.setTextureOffset(0, 6).addCuboid(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		orbBody.setTextureOffset(24, 52).addCuboid(-3.0F, -8.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, false);
		orbBody.setTextureOffset(40, 38).addCuboid(-5.0F, -12.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
		orbBody.setTextureOffset(0, 0).addCuboid(-7.0F, -18.0F, -7.0F, 14.0F, 6.0F, 14.0F, 0.0F, false);
		orbBody.setTextureOffset(0, 38).addCuboid(-5.0F, -22.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
		orbBody.setTextureOffset(0, 52).addCuboid(-3.0F, -26.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, false);
		orbBody.setTextureOffset(0, 0).addCuboid(-1.0F, -30.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body, this.orbBody);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
        this.body.pivotY = 23.0F + MathHelper.cos(animationProgress * 0.1662F) * 0.6F;
        this.orbBody.pivotY = 23.0F + MathHelper.cos(animationProgress * 0.1662F) * 0.6F;
        if (entity.getDataTracker().get(VoidFragmentEntity.IS_VOID_ORB)) {
            orbBody.visible = true;
            body.visible = false;
        } else {
            orbBody.visible = false;
            body.visible = true;
        }
    }
}