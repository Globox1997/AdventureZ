package net.adventurez.entity.model;


import net.adventurez.entity.render.state.SkeletonVanguardRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.SkeletonVanguardEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class SkeletonVanguardModel extends EntityModel<SkeletonVanguardRenderState> {
    private final ModelPart head;
    private final ModelPart spear;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public SkeletonVanguardModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.spear = this.rightArm.getChild("spear");
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F).texOffs(32, 0).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 6.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F).texOffs(0, 21).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 7.0F, 10.0F).texOffs(24, 38)
                .addBox(-1.0F, -11.0F, -6.0F, 2.0F, 8.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(22, 63).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        modelPartData1.addOrReplaceChild("spear",
                CubeListBuilder.create().texOffs(58, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F).texOffs(56, 36).addBox(-0.5F, 1.0F, -2.0F, 1.0F, 1.0F, 2.0F).texOffs(56, 33)
                        .addBox(-0.5F, 2.0F, -3.0F, 1.0F, 1.0F, 2.0F).texOffs(56, 30).addBox(-0.5F, 3.0F, -4.0F, 1.0F, 1.0F, 2.0F).texOffs(44, 55).addBox(-0.5F, 4.0F, -5.0F, 1.0F, 1.0F, 2.0F).texOffs(54, 51)
                        .addBox(-0.5F, 5.0F, -6.0F, 1.0F, 1.0F, 2.0F).texOffs(54, 48).addBox(-0.5F, 6.0F, -7.0F, 1.0F, 1.0F, 2.0F).texOffs(54, 54).addBox(-0.5F, 7.0F, -8.0F, 1.0F, 1.0F, 2.0F).texOffs(40, 54)
                        .addBox(-0.5F, 8.0F, -9.0F, 1.0F, 1.0F, 2.0F).texOffs(32, 16).addBox(-0.5F, 9.0F, -11.0F, 1.0F, 1.0F, 3.0F).texOffs(30, 21).addBox(-0.5F, 10.0F, -12.0F, 1.0F, 1.0F, 5.0F).texOffs(0, 54)
                        .addBox(-0.5F, 11.0F, -13.0F, 1.0F, 1.0F, 4.0F).texOffs(40, 49).addBox(-0.5F, 12.0F, -14.0F, 1.0F, 1.0F, 4.0F).texOffs(34, 48).addBox(-0.5F, 13.0F, -15.0F, 1.0F, 1.0F, 4.0F).texOffs(40, 27)
                        .addBox(-0.5F, 14.0F, -16.0F, 1.0F, 1.0F, 4.0F).texOffs(40, 12).addBox(-0.5F, 15.0F, -17.0F, 1.0F, 1.0F, 4.0F).texOffs(0, 26).addBox(-0.5F, 16.0F, -18.0F, 1.0F, 1.0F, 4.0F).texOffs(0, 21)
                        .addBox(-0.5F, 17.0F, -19.0F, 1.0F, 1.0F, 4.0F).texOffs(9, 12).addBox(-0.5F, 18.0F, -20.0F, 1.0F, 1.0F, 4.0F).texOffs(0, 2).addBox(-0.5F, 11.0F, -8.0F, 1.0F, 1.0F, 1.0F).texOffs(52, 12)
                        .addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F).texOffs(46, 17).addBox(-0.5F, -2.0F, 1.0F, 1.0F, 1.0F, 2.0F).texOffs(46, 12).addBox(-0.5F, -3.0F, 2.0F, 1.0F, 1.0F, 2.0F).texOffs(40, 17)
                        .addBox(-0.5F, -4.0F, 3.0F, 1.0F, 1.0F, 2.0F).texOffs(37, 21).addBox(-0.5F, -5.0F, 4.0F, 1.0F, 1.0F, 2.0F).texOffs(12, 17).addBox(-0.5F, -6.0F, 5.0F, 1.0F, 1.0F, 2.0F).texOffs(32, 0)
                        .addBox(-0.5F, -8.0F, 6.0F, 1.0F, 2.0F, 2.0F).texOffs(0, 0).addBox(-0.5F, -9.0F, 8.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 9.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftArm",
                CubeListBuilder.create().texOffs(56, 59).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(0, 0).addBox(-3.0F, 11.0F, -6.0F, 6.0F, 1.0F, 20.0F).texOffs(0, 12)
                        .addBox(-1.0F, 10.0F, -2.0F, 2.0F, 1.0F, 5.0F).texOffs(32, 12).addBox(3.0F, 11.0F, -4.0F, 3.0F, 1.0F, 3.0F).texOffs(0, 54).addBox(3.0F, 11.0F, 4.0F, 2.0F, 1.0F, 10.0F).texOffs(40, 48)
                        .addBox(-5.0F, 11.0F, 4.0F, 2.0F, 1.0F, 10.0F).texOffs(30, 27).addBox(-6.0F, 11.0F, -4.0F, 3.0F, 1.0F, 3.0F).texOffs(0, 6).addBox(3.0F, 11.0F, -1.0F, 5.0F, 1.0F, 5.0F).texOffs(0, 0)
                        .addBox(-8.0F, 11.0F, -1.0F, 5.0F, 1.0F, 5.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        modelPartData.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(48, 59).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(18, 48).addBox(-2.0F, -2.0F, -3.0F, 5.0F, 9.0F, 6.0F),
                PartPose.offset(2.0F, 12.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(40, 59).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(46, 15).addBox(-3.0F, -2.0F, -3.0F, 5.0F, 9.0F, 6.0F),
                PartPose.offset(-2.0F, 12.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }


    @Override
    public void setupAnim(SkeletonVanguardRenderState state) {
        float f = state.walkAnimationPos;
        float g = state.walkAnimationSpeed;
        float h = state.ageInTicks;
        float i = state.yRot;
        float j = state.xRot;
        this.head.yRot = i * 0.0119453292F;
        this.head.xRot = j * 0.0061453292F;
        this.spear.xRot = -0.7854F;
        this.rightArm.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.4F;
        this.rightArm.yRot = 0.0F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.xRot = (Mth.cos(f * 0.6662F) * 2.0F * g * 0.5F) * 0.2F - 1.5708F;
        this.leftArm.yRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * g * 0.5F;
        this.rightLeg.yRot = 0.0F;
        this.rightLeg.zRot = 0.0F;
        this.leftLeg.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
        this.leftLeg.yRot = 0.0F;
        this.leftLeg.zRot = 0.0F;
        float k = Mth.sin(state.attackTime * 3.1415927F);
        if (k > 0.0F) {
            this.rightArm.xRot = -k * 1.5F;
            this.rightArm.zRot = -k * 0.4F;
        }
        float shieldSwing = state.shieldSwing * 2F;
        if (shieldSwing > 0.0F) {
            this.leftArm.xRot = Mth.sin(shieldSwing * 3.1415927F) - 1.5708F;
        }
    }

}
