package net.adventurez.entity.model;


import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.RedFungusEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class RedFungusModel extends EntityModel<AdventureRenderState> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftFoot;
    private final ModelPart rightFoot;

    public RedFungusModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftFoot = root.getChild("leftFoot");
        this.rightFoot = root.getChild("rightFoot");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -5.0304F, -7.6528F, 16.0F, 7.0F, 15.0F).texOffs(0, 22).addBox(-6.0F, -7.0304F, -5.6528F, 12.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 10.0F, 0.0F));
        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 35).addBox(-4.0F, -4.0F, -3.0F, 8.0F, 7.0F, 5.0F), PartPose.offset(0.0F, 15.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(26, 35).addBox(-1.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), PartPose.offset(-2.0F, 18.0F, -0.5F));
        modelPartData.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), PartPose.offset(2.0F, 18.0F, -0.5F));
        return LayerDefinition.create(modelData, 64, 64);
    }


    @Override
    public void setupAnim(AdventureRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.head.xRot = -0.1745F;
        this.rightFoot.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.leftFoot.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;

        float k = Mth.sin(state.attackTime * 3.1415927F);
        if (k > 0) {
            this.head.xRot = -k * 1.2F;
        }

    }

}
