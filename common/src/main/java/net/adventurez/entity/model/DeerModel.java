package net.adventurez.entity.model;


import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Animal;

public class DeerModel extends EntityModel<AdventureRenderState> {
    private final ModelPart root;
    private final ModelPart root_r1;
    private final ModelPart neck;
    private final ModelPart neck_r1;
    private final ModelPart head;
    private final ModelPart head_r1;
    private final ModelPart head_r2;
    private final ModelPart head_r3;
    private final ModelPart head_r4;
    private final ModelPart head_r5;
    private final ModelPart antlers;
    private final ModelPart head_r6;
    private final ModelPart head_r7;
    private final ModelPart leg0;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg1;

    public DeerModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.leg1 = this.root.getChild("leg1");
        this.leg3 = this.root.getChild("leg3");
        this.leg2 = this.root.getChild("leg2");
        this.leg0 = this.root.getChild("leg0");
        this.neck = this.root.getChild("neck");
        this.head = root.getChild("head");
        this.antlers = this.head.getChild("antlers");
        this.head_r7 = this.antlers.getChild("head_r7");
        this.head_r6 = this.antlers.getChild("head_r6");
        this.head_r5 = this.head.getChild("head_r5");
        this.head_r4 = this.head.getChild("head_r4");
        this.head_r3 = this.head.getChild("head_r3");
        this.head_r2 = this.head.getChild("head_r2");
        this.head_r1 = this.head.getChild("head_r1");
        this.neck_r1 = this.neck.getChild("neck_r1");
        this.root_r1 = this.root.getChild("root_r1");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -12.0F, -12.0F, 12.0F, 10.0F, 22.0F), PartPose.offset(0.0F, 14.0F, 0.0F));
        modelPartData1.addOrReplaceChild("root_r1", CubeListBuilder.create().texOffs(60, 7).addBox(-6.0F, -7.0F, 13.5F, 12.0F, 10.0F, 0.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -11.0F));
        modelPartData2.addOrReplaceChild("neck_r1",
                CubeListBuilder.create().texOffs(0, 53).addBox(0.0F, -15.0F, -4.0F, 0.0F, 18.0F, 5.0F).texOffs(0, 32).addBox(-3.0F, -15.0F, -2.0F, 6.0F, 15.0F, 8.0F, new CubeDeformation(-0.9F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData1.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(64, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-4.0F, -2.0F, 7.0F));
        modelPartData1.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(56, 61).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-4.0F, -2.0F, -9.0F));
        modelPartData1.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(56, 45).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(4.0F, -2.0F, -9.0F));
        modelPartData1.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(4.0F, -2.0F, 7.0F));
        PartDefinition modelPartData3 = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -14.0F));
        modelPartData3.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(46, 10).addBox(-2.0F, -2.1F, -11.0F, 4.0F, 4.0F, 5.0F).texOffs(28, 32).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 7.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData3.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(20, 32).addBox(-3.0F, -4.0F, 4.0F, 1.0F, 3.0F, 5.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData3.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(14, 14).addBox(-2.0F, -7.0F, 1.0F, 1.0F, 4.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData3.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 16).addBox(1.0F, -7.0F, 1.0F, 1.0F, 4.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData3.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(50, 32).addBox(2.0F, -4.0F, 4.0F, 1.0F, 3.0F, 5.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData4 = modelPartData3.addOrReplaceChild("antlers", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData4.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(28, 40).addBox(-0.25F, -21.0F, -0.5F, 0.0F, 16.0F, 9.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData4.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(28, 40).addBox(0.25F, -21.0F, -0.5F, 0.0F, 16.0F, 9.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }



    @Override
    public void setupAnim(AdventureRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.head.xRot = headPitch * 0.010453292F;
        this.head.yRot = headYaw * 0.010453292F;
        this.root_r1.xRot = 0.3927F;
        this.neck_r1.xRot = 0.3491F;
        this.head_r1.xRot = 0.0873F;
        this.head_r2.xRot = 0.5236F;
        this.head_r2.yRot = -0.3927F;
        this.head_r3.xRot = 0.5236F;
        this.head_r3.yRot = 0.2182F;
        this.head_r4.xRot = 0.5236F;
        this.head_r4.yRot = -0.2182F;
        this.head_r5.xRot = 0.5236F;
        this.head_r5.yRot = 0.3927F;
        this.head_r6.xRot = 0.5236F;
        this.head_r6.yRot = -0.2182F;
        this.head_r6.zRot = 0.2618F;
        this.head_r7.xRot = 0.5236F;
        this.head_r7.yRot = 0.2182F;
        this.head_r7.zRot = -0.2618F;
        this.leg0.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leg1.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg2.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg3.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        if (state.isBaby) {
            this.antlers.visible = false;
        } else {
            this.antlers.visible = true;
        }
    }

}
