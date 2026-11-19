package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.VoidShadowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class VoidShadowModel<T extends VoidShadowEntity> extends ListModel<T> {

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

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("rightArm",
                CubeListBuilder.create().texOffs(44, 58).addBox(1.0F, 6.0F, -2.0F, 2.0F, 2.0F, 4.0F).texOffs(0, 50).addBox(-3.0F, 8.0F, -2.0F, 6.0F, 2.0F, 4.0F).texOffs(20, 62)
                        .addBox(-1.0F, 8.0F, -4.0F, 4.0F, 2.0F, 2.0F).texOffs(0, 20).addBox(-1.0F, 14.0F, -4.0F, 2.0F, 2.0F, 2.0F).texOffs(0, 16).addBox(-1.0F, 14.0F, 2.0F, 2.0F, 2.0F, 2.0F).texOffs(8, 62)
                        .addBox(-1.0F, 8.0F, 2.0F, 4.0F, 2.0F, 2.0F).texOffs(0, 28).addBox(-1.0F, 10.0F, -4.0F, 6.0F, 4.0F, 8.0F).texOffs(48, 28).addBox(-3.0F, 14.0F, -2.0F, 6.0F, 2.0F, 4.0F).texOffs(32, 58)
                        .addBox(1.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F).texOffs(24, 56).addBox(-3.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F).texOffs(0, 56).addBox(-1.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F).texOffs(12, 56)
                        .addBox(-5.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F).texOffs(26, 16).addBox(-5.0F, 10.0F, -2.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(11.0F, 1.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftArm",
                CubeListBuilder.create().texOffs(26, 20).addBox(3.0F, 10.0F, -2.0F, 2.0F, 2.0F, 2.0F).texOffs(36, 52).addBox(-3.0F, 8.0F, -2.0F, 6.0F, 2.0F, 4.0F).texOffs(60, 60)
                        .addBox(-3.0F, 8.0F, 2.0F, 4.0F, 2.0F, 2.0F).texOffs(16, 40).addBox(-3.0F, 8.0F, -4.0F, 4.0F, 2.0F, 2.0F).texOffs(0, 4).addBox(-1.0F, 14.0F, -4.0F, 2.0F, 2.0F, 2.0F).texOffs(0, 0)
                        .addBox(-1.0F, 14.0F, 2.0F, 2.0F, 2.0F, 2.0F).texOffs(0, 62).addBox(-5.0F, 6.0F, -2.0F, 2.0F, 2.0F, 4.0F).texOffs(28, 28).addBox(-5.0F, 10.0F, -4.0F, 6.0F, 4.0F, 8.0F).texOffs(60, 34)
                        .addBox(1.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F).texOffs(52, 60).addBox(-1.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F).texOffs(58, 44).addBox(-3.0F, 16.0F, -2.0F, 2.0F, 2.0F, 4.0F).texOffs(20, 50)
                        .addBox(-3.0F, 14.0F, -2.0F, 6.0F, 2.0F, 4.0F).texOffs(58, 0).addBox(3.0F, 18.0F, -2.0F, 2.0F, 2.0F, 4.0F),
                PartPose.offset(-11.0F, 1.0F, 0.0F));
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(26, 16).addBox(-6.0F, -11.0F, -4.0F, 6.0F, 2.0F, 8.0F).texOffs(0, 40).addBox(2.0F, -11.0F, -4.0F, 4.0F, 2.0F, 8.0F).texOffs(50, 34)
                        .addBox(-8.0F, -9.0F, -3.0F, 2.0F, 4.0F, 6.0F).texOffs(20, 28).addBox(8.0F, -9.0F, -3.0F, 2.0F, 2.0F, 6.0F).texOffs(0, 0).addBox(-6.0F, -9.0F, -5.0F, 14.0F, 6.0F, 10.0F).texOffs(0, 16)
                        .addBox(-4.0F, -3.0F, -5.0F, 8.0F, 2.0F, 10.0F).texOffs(38, 0).addBox(-2.0F, -1.0F, -4.0F, 6.0F, 2.0F, 8.0F).texOffs(42, 44).addBox(-2.0F, 1.0F, -3.0F, 5.0F, 2.0F, 6.0F).texOffs(46, 10)
                        .addBox(-1.0F, 3.0F, -3.0F, 5.0F, 2.0F, 6.0F).texOffs(48, 20).addBox(-2.0F, 5.0F, -3.0F, 4.0F, 2.0F, 6.0F).texOffs(24, 40).addBox(-4.0F, 7.0F, -2.0F, 8.0F, 6.0F, 4.0F).texOffs(52, 54)
                        .addBox(-2.0F, 13.0F, -2.0F, 4.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("rightParticle", CubeListBuilder.create(), PartPose.offset(8.0F, -9.0F, 0.0F));
        modelPartData2.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(40, 58).addBox(12.0F, -22.0F, -2.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(-8.0F, 24.0F, 0.0F));
        modelPartData2.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(8, 56).addBox(10.0F, -26.0F, 0.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(-8.0F, 24.0F, 0.0F));
        modelPartData2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(20, 56).addBox(8.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(-8.0F, 24.0F, 0.0F));
        modelPartData2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(46, 18).addBox(14.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(-8.0F, 24.0F, 0.0F));
        modelPartData2.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(62, 10).addBox(8.0F, -18.0F, 0.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(-8.0F, 24.0F, 0.0F));
        modelPartData2.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(16, 50).addBox(10.0F, -30.0F, 0.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(-8.0F, 24.0F, 0.0F));
        modelPartData2.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(16, 44).addBox(4.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(-8.0F, 24.0F, 0.0F));
        PartDefinition modelPartData3 = modelPartData1.addOrReplaceChild("leftParticle", CubeListBuilder.create(), PartPose.offset(-6.0F, -9.0F, 0.0F));
        modelPartData3.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(0, 44).addBox(-4.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(6.0F, 24.0F, 0.0F));
        modelPartData3.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, -30.0F, 0.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(6.0F, 24.0F, 0.0F));
        modelPartData3.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(38, 4).addBox(-8.0F, -28.0F, -2.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(6.0F, 24.0F, 0.0F));
        modelPartData3.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(0, 32).addBox(-12.0F, -24.0F, 0.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(6.0F, 24.0F, 0.0F));
        modelPartData3.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(0, 28).addBox(-12.0F, -18.0F, 0.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(6.0F, 24.0F, 0.0F));
        modelPartData3.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(38, 0).addBox(-15.0F, -30.0F, 0.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(6.0F, 24.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.rightArm, this.leftArm, this.body);
    }

    @Override
    public void setupAnim(T shadow, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        this.body.y = 8.0F + Mth.sin(animationProgress / 12.5663706F) / 2.0F;
        this.rightArm.y = 1.0F + Mth.sin(animationProgress / 12.5663706F) / 1.5F;
        this.leftArm.y = 1.0F + Mth.sin(animationProgress / 12.5663706F) / 1.5F;
        this.bone.y = 24F + Mth.cos(2.6F + animationProgress / 12.5663706F) / 1.1F;
        this.bone2.y = 24F + Mth.sin(2.4F + animationProgress / 12.5663706F) / 1.3F;
        this.bone3.y = 24F + Mth.cos(2.2F + animationProgress / 12.5663706F) / 1.45F;
        this.bone4.y = 24F + Mth.sin(2.0F + animationProgress / 12.5663706F) / 1.6F;
        this.bone5.y = 24F + Mth.cos(1.8F + animationProgress / 12.5663706F) / 1.25F;
        this.bone6.y = 24F + Mth.cos(1.6F + animationProgress / 12.5663706F) / 1.4F;
        this.bone7.y = 24F + Mth.sin(1.4F + animationProgress / 12.5663706F);
        this.bone8.y = 24F + Mth.sin(1.2F + animationProgress / 12.5663706F) / 1.25F;
        this.bone9.y = 24F + Mth.cos(1.0F + animationProgress / 12.5663706F) / 1.35F;
        this.bone10.y = 24F + Mth.cos(0.8F + animationProgress / 12.5663706F) / 1.2F;
        this.bone11.y = 24F + Mth.sin(0.6F + animationProgress / 12.5663706F) / 0.9F;
        this.bone12.y = 24F + Mth.cos(0.4F + animationProgress / 12.5663706F) / 1.15F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;

        if (shadow.getEntityData().get(VoidShadowEntity.IS_THROWING_BLOCKS)) {
            throwingBlocksTicker = Mth.clamp(throwingBlocksTicker + 0.05817666666666F, 0.0F, 1.7453F);
        } else {
            throwingBlocksTicker = Mth.clamp(throwingBlocksTicker - 0.05817666666666F, 0.0F, 1.7453F);
        }

        this.rightArm.xRot = -throwingBlocksTicker;
        this.leftArm.xRot = this.rightArm.xRot;
        this.rightArm.yRot = -throwingBlocksTicker * 0.20002291869F;
        this.leftArm.yRot = throwingBlocksTicker * 0.20002291869F;

        if (shadow.getEntityData().get(VoidShadowEntity.HOVERING_MAGIC_HANDS)) {
            this.rightArm.zRot = -2.4871F - Mth.cos(animationProgress * 0.3662F) * 0.1F;
            this.leftArm.zRot = 2.4871F + Mth.cos(animationProgress * 0.3662F) * 0.1F;
            this.rightArm.xRot = -Mth.cos(animationProgress * 0.1662F) * 0.25F;
            this.leftArm.xRot = Mth.cos(animationProgress * 0.1662F) * 0.25F;
            // this.rightAttackingArm.pitch = MathHelper.cos(animationProgress * 0.6662F) *
            // 0.25F;
            // this.leftAttackingArm.pitch = MathHelper.cos(animationProgress * 0.6662F) *
            // 0.25F;
        } else if (shadow.getEntityData().get(VoidShadowEntity.CIRCLING_HANDS)) {
            this.rightArm.zRot = -1.5708F;
            this.leftArm.zRot = 1.5708F;
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
