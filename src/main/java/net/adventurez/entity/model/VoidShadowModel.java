package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.VoidShadowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
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

    private float throwingBlocksTicker;

    public VoidShadowModel(ModelPart root) {
        this.rightArm = root.getChild("rightArm");
        this.leftArm = root.getChild("leftArm");
        this.body = root.getChild("body");
        this.leftParticle = this.body.getChild("leftParticle");
        this.bone12 = this.leftParticle.getChild("bone12");
        this.bone11 = this.leftParticle.getChild("bone11");
        this.bone10 = this.leftParticle.getChild("bone10");
        this.bone9 = this.leftParticle.getChild("bone9");
        this.bone8 = this.leftParticle.getChild("bone8");
        this.rightParticle = this.body.getChild("rightParticle");
        this.bone7 = this.rightParticle.getChild("bone7");
        this.bone6 = this.rightParticle.getChild("bone6");
        this.bone5 = this.rightParticle.getChild("bone5");
        this.bone4 = this.rightParticle.getChild("bone4");
        this.bone3 = this.rightParticle.getChild("bone3");
        this.bone2 = this.rightParticle.getChild("bone2");
        this.bone = this.rightParticle.getChild("bone");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("rightArm",
                ModelPartBuilder.create().uv(44, 58).cuboid(1.0F, 6.0F, -2.0F, 2.0F, 2.0F, 4.0F).uv(0, 50).cuboid(-3.0F, 8.0F, -2.0F, 6.0F, 2.0F, 4.0F).uv(20, 62)
                        .cuboid(-1.0F, 8.0F, -4.0F, 4.0F, 2.0F, 2.0F).uv(0, 20).cuboid(-1.0F, 14.0F, -4.0F, 2.0F, 2.0F, 2.0F).uv(0, 16).cuboid(-1.0F, 14.0F, 2.0F, 2.0F, 2.0F, 2.0F).uv(8, 62)
                        .cuboid(-1.0F, 8.0F, 2.0F, 4.0F, 2.0F, 2.0F).uv(0, 28).cuboid(-1.0F, 10.0F, -4.0F, 6.0F, 4.0F, 8.0F).uv(48, 28).cuboid(-3.0F, 14.0F, -2.0F, 6.0F, 2.0F, 4.0F).uv(32, 58)
                        .cuboid(1.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F).uv(24, 56).cuboid(-3.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F).uv(0, 56).cuboid(-1.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F).uv(12, 56)
                        .cuboid(-5.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F).uv(26, 16).cuboid(-5.0F, 10.0F, -2.0F, 2.0F, 2.0F, 2.0F),
                ModelTransform.pivot(11.0F, 1.0F, 0.0F));
        modelPartData.addChild("leftArm",
                ModelPartBuilder.create().uv(26, 20).cuboid(3.0F, 10.0F, -2.0F, 2.0F, 2.0F, 2.0F).uv(36, 52).cuboid(-3.0F, 8.0F, -2.0F, 6.0F, 2.0F, 4.0F).uv(60, 60)
                        .cuboid(-3.0F, 8.0F, 2.0F, 4.0F, 2.0F, 2.0F).uv(16, 40).cuboid(-3.0F, 8.0F, -4.0F, 4.0F, 2.0F, 2.0F).uv(0, 4).cuboid(-1.0F, 14.0F, -4.0F, 2.0F, 2.0F, 2.0F).uv(0, 0)
                        .cuboid(-1.0F, 14.0F, 2.0F, 2.0F, 2.0F, 2.0F).uv(0, 62).cuboid(-5.0F, 6.0F, -2.0F, 2.0F, 2.0F, 4.0F).uv(28, 28).cuboid(-5.0F, 10.0F, -4.0F, 6.0F, 4.0F, 8.0F).uv(60, 34)
                        .cuboid(1.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F).uv(52, 60).cuboid(-1.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F).uv(58, 44).cuboid(-3.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F).uv(20, 50)
                        .cuboid(-3.0F, 14.0F, -2.0F, 6.0F, 2.0F, 4.0F).uv(58, 0).cuboid(3.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F),
                ModelTransform.pivot(-11.0F, 1.0F, 0.0F));
        ModelPartData modelPartData1 = modelPartData.addChild("body",
                ModelPartBuilder.create().uv(26, 16).cuboid(-6.0F, -11.0F, -4.0F, 6.0F, 2.0F, 8.0F).uv(0, 40).cuboid(2.0F, -11.0F, -4.0F, 4.0F, 2.0F, 8.0F).uv(50, 34)
                        .cuboid(-8.0F, -9.0F, -3.0F, 2.0F, 4.0F, 6.0F).uv(20, 28).cuboid(8.0F, -9.0F, -3.0F, 2.0F, 2.0F, 6.0F).uv(0, 0).cuboid(-6.0F, -9.0F, -5.0F, 14.0F, 6.0F, 10.0F).uv(0, 16)
                        .cuboid(-4.0F, -3.0F, -5.0F, 8.0F, 2.0F, 10.0F).uv(38, 0).cuboid(-2.0F, -1.0F, -4.0F, 6.0F, 2.0F, 8.0F).uv(42, 44).cuboid(-2.0F, 1.0F, -3.0F, 5.0F, 2.0F, 6.0F).uv(46, 10)
                        .cuboid(-1.0F, 3.0F, -3.0F, 5.0F, 2.0F, 6.0F).uv(48, 20).cuboid(-2.0F, 5.0F, -3.0F, 4.0F, 2.0F, 6.0F).uv(24, 40).cuboid(-4.0F, 7.0F, -2.0F, 8.0F, 6.0F, 4.0F).uv(52, 54)
                        .cuboid(-2.0F, 13.0F, -2.0F, 4.0F, 2.0F, 4.0F),
                ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        ModelPartData modelPartData2 = modelPartData1.addChild("rightParticle", ModelPartBuilder.create(), ModelTransform.pivot(8.0F, -9.0F, 0.0F));
        modelPartData2.addChild("bone", ModelPartBuilder.create().uv(40, 58).cuboid(12.0F, -22.0F, -2.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(-8.0F, 24.0F, 0.0F));
        modelPartData2.addChild("bone2", ModelPartBuilder.create().uv(8, 56).cuboid(10.0F, -26.0F, 0.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(-8.0F, 24.0F, 0.0F));
        modelPartData2.addChild("bone3", ModelPartBuilder.create().uv(20, 56).cuboid(8.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(-8.0F, 24.0F, 0.0F));
        modelPartData2.addChild("bone4", ModelPartBuilder.create().uv(46, 18).cuboid(14.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(-8.0F, 24.0F, 0.0F));
        modelPartData2.addChild("bone5", ModelPartBuilder.create().uv(62, 10).cuboid(8.0F, -18.0F, 0.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(-8.0F, 24.0F, 0.0F));
        modelPartData2.addChild("bone6", ModelPartBuilder.create().uv(16, 50).cuboid(10.0F, -30.0F, 0.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(-8.0F, 24.0F, 0.0F));
        modelPartData2.addChild("bone7", ModelPartBuilder.create().uv(16, 44).cuboid(4.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(-8.0F, 24.0F, 0.0F));
        ModelPartData modelPartData3 = modelPartData1.addChild("leftParticle", ModelPartBuilder.create(), ModelTransform.pivot(-6.0F, -9.0F, 0.0F));
        modelPartData3.addChild("bone8", ModelPartBuilder.create().uv(0, 44).cuboid(-4.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(6.0F, 24.0F, 0.0F));
        modelPartData3.addChild("bone9", ModelPartBuilder.create().uv(0, 40).cuboid(-2.0F, -30.0F, 0.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(6.0F, 24.0F, 0.0F));
        modelPartData3.addChild("bone10", ModelPartBuilder.create().uv(38, 4).cuboid(-8.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(6.0F, 24.0F, 0.0F));
        modelPartData3.addChild("bone11", ModelPartBuilder.create().uv(0, 32).cuboid(-12.0F, -24.0F, 0.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(6.0F, 24.0F, 0.0F));
        modelPartData3.addChild("bone12", ModelPartBuilder.create().uv(0, 28).cuboid(-12.0F, -18.0F, 0.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(6.0F, 24.0F, 0.0F));
        modelPartData3.addChild("bone13", ModelPartBuilder.create().uv(38, 0).cuboid(-15.0F, -30.0F, 0.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(6.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.rightArm, this.leftArm, this.body);
    }

    @Override
    public void setAngles(T shadow, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

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
        this.rightArm.roll = 0.0F;
        this.leftArm.roll = 0.0F;

        if (shadow.getDataTracker().get(VoidShadowEntity.IS_THROWING_BLOCKS)) {
            throwingBlocksTicker = MathHelper.clamp(throwingBlocksTicker + 0.05817666666666F, 0.0F, 1.7453F);
        } else {
            throwingBlocksTicker = MathHelper.clamp(throwingBlocksTicker - 0.05817666666666F, 0.0F, 1.7453F);
        }

        this.rightArm.pitch = -throwingBlocksTicker;
        this.leftArm.pitch = this.rightArm.pitch;
        this.rightArm.yaw = -throwingBlocksTicker * 0.20002291869F;
        this.leftArm.yaw = throwingBlocksTicker * 0.20002291869F;

        if (shadow.getDataTracker().get(VoidShadowEntity.HOVERING_MAGIC_HANDS)) {
            this.rightArm.roll = -2.4871F - MathHelper.cos(animationProgress * 0.3662F) * 0.1F;
            this.leftArm.roll = 2.4871F + MathHelper.cos(animationProgress * 0.3662F) * 0.1F;
            this.rightArm.pitch = -MathHelper.cos(animationProgress * 0.1662F) * 0.25F;
            this.leftArm.pitch = MathHelper.cos(animationProgress * 0.1662F) * 0.25F;
            // this.rightAttackingArm.pitch = MathHelper.cos(animationProgress * 0.6662F) *
            // 0.25F;
            // this.leftAttackingArm.pitch = MathHelper.cos(animationProgress * 0.6662F) *
            // 0.25F;
        } else if (shadow.getDataTracker().get(VoidShadowEntity.CIRCLING_HANDS)) {
            this.rightArm.roll = -1.5708F;
            this.leftArm.roll = 1.5708F;
        }

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
