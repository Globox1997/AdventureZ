package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.VoidShadowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class VoidShadowModel<T extends VoidShadowEntity> extends CompositeEntityModel<T> {

    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart body;
    private final ModelPart rightParticle;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart bone5;
    private final ModelPart bone6;
    private final ModelPart bone7;
    private final ModelPart leftParticle;
    private final ModelPart bone8;
    private final ModelPart bone9;
    private final ModelPart bone10;
    private final ModelPart bone11;
    private final ModelPart bone12;
    private final ModelPart bone13;

    private float throwingBlocksTicker;

    public VoidShadowModel() {
        rightArm = (new ModelPart(this)).setTextureSize(128, 128);
        rightArm.setPivot(11.0F, 1.0F, 0.0F);
        rightArm.setTextureOffset(44, 58).addCuboid(1.0F, 6.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        rightArm.setTextureOffset(0, 50).addCuboid(-3.0F, 8.0F, -2.0F, 6.0F, 2.0F, 4.0F, 0.0F, false);
        rightArm.setTextureOffset(20, 62).addCuboid(-1.0F, 8.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
        rightArm.setTextureOffset(0, 20).addCuboid(-1.0F, 14.0F, -4.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        rightArm.setTextureOffset(0, 16).addCuboid(-1.0F, 14.0F, 2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        rightArm.setTextureOffset(8, 62).addCuboid(-1.0F, 8.0F, 2.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
        rightArm.setTextureOffset(0, 28).addCuboid(-1.0F, 10.0F, -4.0F, 6.0F, 4.0F, 8.0F, 0.0F, false);
        rightArm.setTextureOffset(48, 28).addCuboid(-3.0F, 14.0F, -2.0F, 6.0F, 2.0F, 4.0F, 0.0F, false);
        rightArm.setTextureOffset(32, 58).addCuboid(1.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        rightArm.setTextureOffset(24, 56).addCuboid(-3.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        rightArm.setTextureOffset(0, 56).addCuboid(-1.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        rightArm.setTextureOffset(12, 56).addCuboid(-5.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        rightArm.setTextureOffset(26, 16).addCuboid(-5.0F, 10.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        leftArm = (new ModelPart(this)).setTextureSize(128, 128);
        leftArm.setPivot(-11.0F, 1.0F, 0.0F);
        leftArm.setTextureOffset(26, 20).addCuboid(3.0F, 10.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        leftArm.setTextureOffset(36, 52).addCuboid(-3.0F, 8.0F, -2.0F, 6.0F, 2.0F, 4.0F, 0.0F, false);
        leftArm.setTextureOffset(60, 60).addCuboid(-3.0F, 8.0F, 2.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
        leftArm.setTextureOffset(16, 40).addCuboid(-3.0F, 8.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
        leftArm.setTextureOffset(0, 4).addCuboid(-1.0F, 14.0F, -4.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        leftArm.setTextureOffset(0, 0).addCuboid(-1.0F, 14.0F, 2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        leftArm.setTextureOffset(0, 62).addCuboid(-5.0F, 6.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        leftArm.setTextureOffset(28, 28).addCuboid(-5.0F, 10.0F, -4.0F, 6.0F, 4.0F, 8.0F, 0.0F, false);
        leftArm.setTextureOffset(60, 34).addCuboid(1.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        leftArm.setTextureOffset(52, 60).addCuboid(-1.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        leftArm.setTextureOffset(58, 44).addCuboid(-3.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        leftArm.setTextureOffset(20, 50).addCuboid(-3.0F, 14.0F, -2.0F, 6.0F, 2.0F, 4.0F, 0.0F, false);
        leftArm.setTextureOffset(58, 0).addCuboid(3.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

        body = (new ModelPart(this)).setTextureSize(128, 128);
        body.setPivot(0.0F, 8.0F, 0.0F);
        body.setTextureOffset(26, 16).addCuboid(-6.0F, -11.0F, -4.0F, 6.0F, 2.0F, 8.0F, 0.0F, false);
        body.setTextureOffset(0, 40).addCuboid(2.0F, -11.0F, -4.0F, 4.0F, 2.0F, 8.0F, 0.0F, false);
        body.setTextureOffset(50, 34).addCuboid(-8.0F, -9.0F, -3.0F, 2.0F, 4.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(20, 28).addCuboid(8.0F, -9.0F, -3.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addCuboid(-6.0F, -9.0F, -5.0F, 14.0F, 6.0F, 10.0F, 0.0F, false);
        body.setTextureOffset(0, 16).addCuboid(-4.0F, -3.0F, -5.0F, 8.0F, 2.0F, 10.0F, 0.0F, false);
        body.setTextureOffset(38, 0).addCuboid(-2.0F, -1.0F, -4.0F, 6.0F, 2.0F, 8.0F, 0.0F, false);
        body.setTextureOffset(42, 44).addCuboid(-2.0F, 1.0F, -3.0F, 5.0F, 2.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(46, 10).addCuboid(-1.0F, 3.0F, -3.0F, 5.0F, 2.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(48, 20).addCuboid(-2.0F, 5.0F, -3.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(24, 40).addCuboid(-4.0F, 7.0F, -2.0F, 8.0F, 6.0F, 4.0F, 0.0F, false);
        body.setTextureOffset(52, 54).addCuboid(-2.0F, 13.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);

        rightParticle = (new ModelPart(this)).setTextureSize(128, 128);
        rightParticle.setPivot(8.0F, -9.0F, 0.0F);
        body.addChild(rightParticle);

        bone = (new ModelPart(this)).setTextureSize(128, 128);
        bone.setPivot(-8.0F, 24.0F, 0.0F);
        rightParticle.addChild(bone);
        bone.setTextureOffset(40, 58).addCuboid(12.0F, -22.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone2 = (new ModelPart(this)).setTextureSize(128, 128);
        bone2.setPivot(-8.0F, 24.0F, 0.0F);
        rightParticle.addChild(bone2);
        bone2.setTextureOffset(8, 56).addCuboid(10.0F, -26.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone3 = (new ModelPart(this)).setTextureSize(128, 128);
        bone3.setPivot(-8.0F, 24.0F, 0.0F);
        rightParticle.addChild(bone3);
        bone3.setTextureOffset(20, 56).addCuboid(8.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone4 = (new ModelPart(this)).setTextureSize(128, 128);
        bone4.setPivot(-8.0F, 24.0F, 0.0F);
        rightParticle.addChild(bone4);
        bone4.setTextureOffset(46, 18).addCuboid(14.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone5 = (new ModelPart(this)).setTextureSize(128, 128);
        bone5.setPivot(-8.0F, 24.0F, 0.0F);
        rightParticle.addChild(bone5);
        bone5.setTextureOffset(62, 10).addCuboid(8.0F, -18.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone6 = (new ModelPart(this)).setTextureSize(128, 128);
        bone6.setPivot(-8.0F, 24.0F, 0.0F);
        rightParticle.addChild(bone6);
        bone6.setTextureOffset(16, 50).addCuboid(10.0F, -30.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone7 = (new ModelPart(this)).setTextureSize(128, 128);
        bone7.setPivot(-8.0F, 24.0F, 0.0F);
        rightParticle.addChild(bone7);
        bone7.setTextureOffset(16, 44).addCuboid(4.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        leftParticle = (new ModelPart(this)).setTextureSize(128, 128);
        leftParticle.setPivot(-6.0F, -9.0F, 0.0F);
        body.addChild(leftParticle);

        bone8 = (new ModelPart(this)).setTextureSize(128, 128);
        bone8.setPivot(6.0F, 24.0F, 0.0F);
        leftParticle.addChild(bone8);
        bone8.setTextureOffset(0, 44).addCuboid(-4.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone9 = (new ModelPart(this)).setTextureSize(128, 128);
        bone9.setPivot(6.0F, 24.0F, 0.0F);
        leftParticle.addChild(bone9);
        bone9.setTextureOffset(0, 40).addCuboid(-2.0F, -30.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone10 = (new ModelPart(this)).setTextureSize(128, 128);
        bone10.setPivot(6.0F, 24.0F, 0.0F);
        leftParticle.addChild(bone10);
        bone10.setTextureOffset(38, 4).addCuboid(-8.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone11 = (new ModelPart(this)).setTextureSize(128, 128);
        bone11.setPivot(6.0F, 24.0F, 0.0F);
        leftParticle.addChild(bone11);
        bone11.setTextureOffset(0, 32).addCuboid(-12.0F, -24.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone12 = (new ModelPart(this)).setTextureSize(128, 128);
        bone12.setPivot(6.0F, 24.0F, 0.0F);
        leftParticle.addChild(bone12);
        bone12.setTextureOffset(0, 28).addCuboid(-12.0F, -18.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone13 = (new ModelPart(this)).setTextureSize(128, 128);
        bone13.setPivot(6.0F, 24.0F, 0.0F);
        leftParticle.addChild(bone13);
        bone13.setTextureOffset(38, 0).addCuboid(-15.0F, -30.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.rightArm, this.leftArm, this.body);
    }

    @Override
    public void setAngles(T shadow, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {

        this.body.pivotY = 8.0F + MathHelper.sin(animationProgress / 12.5663706F) / 2.0F;
        this.rightArm.pivotY = 1.0F + MathHelper.sin(animationProgress / 12.5663706F) / 1.5F;
        this.leftArm.pivotY = 1.0F + MathHelper.sin(animationProgress / 12.5663706F) / 1.5F;
        this.bone.pivotY = 24F + MathHelper.cos(2.6F + animationProgress / 12.5663706F) / 1.1F;
        this.bone2.pivotY = 24F + MathHelper.sin(2.4F + animationProgress / 12.5663706F) / 1.3F;
        this.bone3.pivotY = 24F + MathHelper.cos(2.2F + animationProgress / 12.5663706F) / 1.45F;
        this.bone4.pivotY = 24F + MathHelper.sin(2.0F + animationProgress / 12.5663706F) / 1.6F;
        this.bone5.pivotY = 24F + MathHelper.cos(1.8F + animationProgress / 12.5663706F) / 1.25F;
        this.bone6.pivotY = 24F + MathHelper.cos(1.6F + animationProgress / 12.5663706F) / 1.4F;
        this.bone7.pivotY = 24F + MathHelper.sin(1.4F + animationProgress / 12.5663706F);
        this.bone8.pivotY = 24F + MathHelper.sin(1.2F + animationProgress / 12.5663706F) / 1.25F;
        this.bone9.pivotY = 24F + MathHelper.cos(1.0F + animationProgress / 12.5663706F) / 1.35F;
        this.bone10.pivotY = 24F + MathHelper.cos(0.8F + animationProgress / 12.5663706F) / 1.2F;
        this.bone11.pivotY = 24F + MathHelper.sin(0.6F + animationProgress / 12.5663706F) / 0.9F;
        this.bone12.pivotY = 24F + MathHelper.cos(0.4F + animationProgress / 12.5663706F) / 1.15F;

        if (shadow.getDataTracker().get(VoidShadowEntity.IS_THROWING_BLOCKS)) {
            throwingBlocksTicker = MathHelper.clamp(throwingBlocksTicker + 0.05817666666666F, 0.0F, 1.7453F);
        } else {
            throwingBlocksTicker = MathHelper.clamp(throwingBlocksTicker - 0.05817666666666F, 0.0F, 1.7453F);
        }
        if (shadow.getDataTracker().get(VoidShadowEntity.HOVERING_MAGIC_HANDS)) {

        }
        this.rightArm.pitch = -throwingBlocksTicker;
        this.leftArm.pitch = this.rightArm.pitch;

        this.rightArm.yaw = -throwingBlocksTicker * 0.20002291869F;
        this.leftArm.yaw = throwingBlocksTicker * 0.20002291869F;

        // this.rightArm.pitch = this.handSwingProgress;

        // Summon attack or change block attack
        // setRotationAngle(leftArm, 0.0F, 0.0F, 2.4871F);
        // setRotationAngle(rightArm, 0.0F, 0.0F, -2.4871F);

        // Throw blocks attack needs floatings
        // setRotationAngle(rightArm, -1.7453F, -0.3491F, 0.0F);
        // setRotationAngle(leftArm, -1.7453F, 0.3491F, 0.0F);
        // 0,011636666666
    }

}
