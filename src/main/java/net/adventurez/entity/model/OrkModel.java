package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.OrkEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class OrkModel<T extends OrkEntity> extends CompositeEntityModel<T> {
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart torso;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart head;
    private final ModelPart header;
    private final ModelPart earleft;
    private final ModelPart ear2;
    private final ModelPart earright;
    private final ModelPart ear;

    public OrkModel() {

        leftLeg = (new ModelPart(this)).setTextureSize(128, 128);
        leftLeg.setPivot(-3.0F, 12.0F, 0.0F);
        leftLeg.setTextureOffset(54, 0).addCuboid(-3.5F, 6.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
        leftLeg.setTextureOffset(60, 35).addCuboid(-3.0F, -1.0F, -2.5F, 5.0F, 7.0F, 5.0F, 0.0F, false);

        rightLeg = (new ModelPart(this)).setTextureSize(128, 128);
        rightLeg.setPivot(3.0F, 12.0F, 0.0F);
        rightLeg.setTextureOffset(42, 50).addCuboid(-2.5F, 6.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
        rightLeg.setTextureOffset(0, 57).addCuboid(-2.0F, -1.0F, -2.5F, 5.0F, 7.0F, 5.0F, 0.0F, false);

        torso = (new ModelPart(this)).setTextureSize(128, 128);
        torso.setPivot(0.0F, 0.0F, 0.0F);
        torso.setTextureOffset(0, 17).addCuboid(-7.0F, 5.0F, -4.0F, 14.0F, 7.0F, 8.0F, 0.0F, false);
        torso.setTextureOffset(0, 0).addCuboid(-7.0F, -5.0F, -3.0F, 14.0F, 10.0F, 7.0F, 0.0F, false);

        leftArm = (new ModelPart(this)).setTextureSize(128, 128);
        leftArm.setPivot(-10.0F, -2.0F, 1.0F);
        leftArm.setTextureOffset(18, 50).addCuboid(-3.0F, -2.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
        leftArm.setTextureOffset(61, 61).addCuboid(-2.5F, 4.0F, -2.5F, 5.0F, 6.0F, 5.0F, 0.0F, false);
        leftArm.setTextureOffset(44, 23).addCuboid(-3.0F, 10.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        rightArm = (new ModelPart(this)).setTextureSize(128, 128);
        rightArm.setPivot(10.0F, -2.0F, 1.0F);
        rightArm.setTextureOffset(0, 44).addCuboid(-3.0F, -2.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
        rightArm.setTextureOffset(60, 12).addCuboid(-2.5F, 4.0F, -2.5F, 5.0F, 6.0F, 5.0F, 0.0F, false);
        rightArm.setTextureOffset(36, 11).addCuboid(-3.0F, 10.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        head = (new ModelPart(this)).setTextureSize(128, 128);
        head.setPivot(0.0F, -4.0F, 0.0F);
        head.setTextureOffset(0, 32).addCuboid(-4.5F, 0.0F, -8.0F, 9.0F, 3.0F, 9.0F, 0.0F, false);
        head.setTextureOffset(0, 2).addCuboid(2.5F, -1.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        head.setTextureOffset(0, 0).addCuboid(-3.5F, -1.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        header = (new ModelPart(this)).setTextureSize(128, 128);
        header.setPivot(0.0F, -6.0F, -7.0F);
        head.addChild(header);
        header.setTextureOffset(28, 36).addCuboid(-4.0F, 0.0F, 0.2F, 8.0F, 6.0F, 8.0F, 0.0F, false);

        earleft = (new ModelPart(this)).setTextureSize(128, 128);
        earleft.setPivot(0.0F, 2.0F, 7.0F);
        header.addChild(earleft);

        ear2 = (new ModelPart(this)).setTextureSize(128, 128);
        ear2.setPivot(-2.0F, -2.0F, -1.0F);
        earleft.addChild(ear2);
        ear2.setTextureOffset(42, 0).addCuboid(-1.0F, 0.0F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

        earright = (new ModelPart(this)).setTextureSize(128, 128);
        earright.setPivot(0.0F, -4.0F, 7.0F);
        header.addChild(earright);

        ear = (new ModelPart(this)).setTextureSize(128, 128);
        ear.setPivot(2.0F, 4.0F, -1.0F);
        earright.addChild(ear);
        ear.setTextureOffset(60, 47).addCuboid(0.0F, 0.0F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.torso, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
        this.leftArm.pitch = -0.2618F;
        this.leftArm.roll = 0.0436F;
        this.rightArm.pitch = -0.2618F;
        this.rightArm.roll = -0.0436F;
        this.header.pitch = -0.0436F;
        this.ear2.pitch = 0.48F;
        this.ear2.yaw = -0.5236F;
        this.ear.pitch = 0.48F;
        this.ear.yaw = 0.5236F;

        this.head.yaw = headYaw * 0.0089453292F;
        this.head.pitch = headPitch * 0.0047453292F;

        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        this.rightArm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.32F;
        this.leftArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.32F;
        float k = MathHelper.sin(entity.handSwingProgress * 3.1415927F);
        if (k > 0) {
            this.rightArm.pitch = -k;
            if (entity.getDataTracker().get(OrkEntity.DOUBLE_HAND_ATTACK)) {
                this.leftArm.pitch = -k;
            }
        }

    }

}