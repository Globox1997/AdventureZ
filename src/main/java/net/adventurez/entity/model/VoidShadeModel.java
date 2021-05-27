package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.VoidShadeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class VoidShadeModel<T extends VoidShadeEntity> extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public VoidShadeModel() {
        body = (new ModelPart(this)).setTextureSize(128, 128);
        body.setPivot(0.0F, 7.0F, -3.0F);
        body.setTextureOffset(25, 28).addCuboid(-4.0F, -9.0F, -5.0F, 8.0F, 6.0F, 9.0F, 0.0F, false);
        body.setTextureOffset(34, 12).addCuboid(-3.0F, -13.0F, -3.0F, 6.0F, 4.0F, 10.0F, 0.0F, false);
        body.setTextureOffset(34, 0).addCuboid(-3.0F, -9.0F, 4.0F, 6.0F, 4.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addCuboid(-6.0F, -3.0F, -5.0F, 12.0F, 12.0F, 10.0F, 0.0F, false);
        body.setTextureOffset(0, 37).addCuboid(-2.0F, 13.0F, 0.0F, 4.0F, 4.0F, 11.0F, 0.0F, false);
        body.setTextureOffset(0, 22).addCuboid(-3.0F, 9.0F, -3.0F, 6.0F, 4.0F, 11.0F, 0.0F, false);

        rightArm = (new ModelPart(this)).setTextureSize(128, 128);
        rightArm.setPivot(-8.0F, 1.0F, 0.0F);
        body.addChild(rightArm);
        rightArm.setTextureOffset(46, 43).addCuboid(-2.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.01F, false);

        leftArm = (new ModelPart(this)).setTextureSize(128, 128);
        leftArm.setPivot(8.0F, 1.0F, 0.0F);
        body.addChild(leftArm);
        leftArm.setTextureOffset(30, 43).addCuboid(-2.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.01F, false);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
        this.body.pivotY = 4.0F + MathHelper.sin(animationProgress * 0.1262F) * 0.8F;
        // setRotationAngle(rightArm, -1.5708F, 0.0F, 0.0F);
        // if (entity.getTarget() != null) {
        // System.out.println("TEST");
        this.rightArm.pitch = -1.5708F + MathHelper.sin(animationProgress * 0.1262F) * 0.1F;
        this.leftArm.pitch = this.rightArm.pitch;
        // }
        float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
        if (k > 0) {
            this.rightArm.pitch = -k * 1.5F;
        }
    }
}