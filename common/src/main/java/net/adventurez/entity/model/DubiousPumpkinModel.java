package net.adventurez.entity.model;


import net.adventurez.entity.render.state.DubiousPumpkinRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.DubiousPumpkinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DubiousPumpkinModel extends EntityModel<DubiousPumpkinRenderState> {
    private final ModelPart root;
    private final ModelPart waist;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public DubiousPumpkinModel(ModelPart rootPart) {
        super(rootPart);
        this.root = rootPart.getChild("root");
        this.waist = root.getChild("waist");
        this.body = waist.getChild("body");
        this.leftArm = body.getChild("leftArm");
        this.rightArm = body.getChild("rightArm");
        this.leftLeg = waist.getChild("leftLeg");
        this.rightLeg = waist.getChild("rightLeg");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition waist = root.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition body = waist.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 1).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(37, 0).addBox(-6.0F, -12.0F, 0.0F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(15, 27).addBox(0.0F, -1.5F, 1.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 2.5F, -2.0F));

        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(15, 27).mirror().addBox(-4.0F, -1.5F, 1.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 2.5F, -2.0F));

        PartDefinition leftLeg = waist.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(1, 26).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition rightLeg = waist.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(1, 26).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }


    @Override
    public void setupAnim(DubiousPumpkinRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
//        this.tail.pitch = -1.0F;
//        this.head.pitch = headPitch * 0.010453292F;
//        this.head.yaw = headYaw * 0.010453292F;
        this.body.zRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance*0.04f;
        this.rightLeg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.rightArm.zRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.2f;
        this.leftArm.zRot = Mth.cos(limbAngle * 0.6662F+ 3.1415927F) * 1.4F * limbDistance * 0.2f;

//        float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
//
//        if (k > 0.0F)
//            this.head.pitch = -k * 0.8F;

    }

}
