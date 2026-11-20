package net.adventurez.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.adventurez.entity.render.state.ProjectileRenderState;

public class VoidBulletModel extends EntityModel<ProjectileRenderState> {
    private final ModelPart body;

    public VoidBulletModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(18, 20).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 1.0F).texOffs(12, 20).addBox(-1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 1.0F).texOffs(16, 5)
                        .addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 1.0F).texOffs(15, 15).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 1.0F).texOffs(16, 12).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 1.0F, 1.0F).texOffs(16, 10)
                        .addBox(-2.0F, 2.0F, -2.0F, 4.0F, 1.0F, 1.0F).texOffs(0, 16).addBox(-2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 2.0F).texOffs(0, 0).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 2.0F),
                PartPose.offset(0.0F, 21.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }


    @Override
    public void setupAnim(ProjectileRenderState state) {
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.body.yRot = headYaw * 0.017453292F;
        this.body.xRot = headPitch * 0.017453292F;
    }
}