package net.adventurez.entity.model;


import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.DesertRhinoEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DesertRhinoModel extends EntityModel<AdventureRenderState> {
    private final ModelPart main;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart right_front_leg;
    private final ModelPart right_back_leg;
    private final ModelPart left_back_leg;
    private final ModelPart left_front_leg;

    public DesertRhinoModel(ModelPart root) {
        super(root);
        this.main = root.getChild("main");
        this.left_front_leg = this.main.getChild("left_front_leg");
        this.left_back_leg = this.main.getChild("left_back_leg");
        this.right_back_leg = this.main.getChild("right_back_leg");
        this.right_front_leg = this.main.getChild("right_front_leg");
        this.body = this.main.getChild("body");
        this.head = this.body.getChild("head");
        this.tail = this.body.getChild("tail");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -23.0F, -36.0F, 24.0F, 23.0F, 27.0F).texOffs(23, 82).addBox(-8.0F, -28.0F, -32.0F, 4.0F, 5.0F, 4.0F).texOffs(54, 50)
                        .addBox(5.0F, -29.0F, -25.0F, 4.0F, 6.0F, 4.0F).texOffs(86, 73).addBox(-6.0F, -27.0F, -22.0F, 4.0F, 4.0F, 4.0F).texOffs(39, 82).addBox(0.0F, -26.0F, -16.0F, 4.0F, 3.0F, 4.0F).texOffs(0, 50)
                        .addBox(-10.0F, -18.0F, -9.0F, 20.0F, 18.0F, 14.0F),
                PartPose.offset(0.0F, -13.0F, 13.0F));
        modelPartData2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(85, 85).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 13.0F).texOffs(0, 0).addBox(-3.0F, -2.0F, 13.0F, 6.0F, 6.0F, 6.0F),
                PartPose.offset(0.0F, -13.0F, 5.0F));
        modelPartData2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(68, 50).addBox(-5.0F, -5.0F, -13.0F, 10.0F, 10.0F, 13.0F).texOffs(16, 12).addBox(-1.0F, -10.0F, -6.0F, 2.0F, 5.0F, 2.0F).texOffs(0, 12)
                .addBox(-2.0F, -14.0F, -12.0F, 4.0F, 9.0F, 4.0F), PartPose.offset(0.0F, -8.0F, -36.0F));
        modelPartData1.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(75, 0).addBox(-4.0F, 0.0F, -6.0F, 9.0F, 13.0F, 12.0F), PartPose.offset(6.0F, -13.0F, -14.0F));
        modelPartData1.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(32, 89).addBox(-4.0F, 0.0F, -4.0F, 7.0F, 13.0F, 9.0F), PartPose.offset(6.0F, -13.0F, 12.0F));
        modelPartData1.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(0, 82).addBox(-3.0F, 0.0F, -4.0F, 7.0F, 13.0F, 9.0F), PartPose.offset(-6.0F, -13.0F, 12.0F));
        modelPartData1.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(56, 73).addBox(-5.0F, 0.0F, -6.0F, 9.0F, 13.0F, 12.0F), PartPose.offset(-6.0F, -13.0F, -14.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }


    @Override
    public void setupAnim(AdventureRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.tail.xRot = -1.0F;
        this.head.xRot = headPitch * 0.010453292F;
        this.head.yRot = headYaw * 0.010453292F;
        this.right_back_leg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.left_back_leg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.right_front_leg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_front_leg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.tail.yRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.3F;

        float k = Mth.sin(state.attackTime * 3.1415927F);

        if (k > 0.0F)
            this.head.xRot = -k * 0.8F;

    }

}
