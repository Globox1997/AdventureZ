package net.adventurez.entity.model;


import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.VoidShadeEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class VoidShadeModel extends EntityModel<AdventureRenderState> {
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public VoidShadeModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.leftArm = this.body.getChild("leftArm");
        this.rightArm = this.body.getChild("rightArm");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(25, 28).addBox(-4.0F, -9.0F, -5.0F, 8.0F, 6.0F, 9.0F).texOffs(34, 12).addBox(-3.0F, -13.0F, -3.0F, 6.0F, 4.0F, 10.0F).texOffs(34, 0)
                        .addBox(-3.0F, -9.0F, 4.0F, 6.0F, 4.0F, 6.0F).texOffs(0, 0).addBox(-6.0F, -3.0F, -5.0F, 12.0F, 12.0F, 10.0F).texOffs(0, 37).addBox(-2.0F, 13.0F, 0.0F, 4.0F, 4.0F, 11.0F).texOffs(0, 22)
                        .addBox(-3.0F, 9.0F, -3.0F, 6.0F, 4.0F, 11.0F),
                PartPose.offset(0.0F, 7.0F, -3.0F));
        modelPartData1.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(46, 43).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F), PartPose.offset(-8.0F, 1.0F, 0.0F));
        modelPartData1.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(30, 43).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F), PartPose.offset(8.0F, 1.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }


    @Override
    public void setupAnim(AdventureRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.body.y = 4.0F + Mth.sin(animationProgress * 0.1262F) * 0.8F;
        this.rightArm.xRot = -1.5708F + Mth.sin(animationProgress * 0.1262F) * 0.1F;
        this.leftArm.xRot = this.rightArm.xRot;
        float k = Mth.sin(state.attackTime * 3.1415927F);
        if (k > 0) {
            this.rightArm.xRot = -k * 1.5F;
        }
    }
}