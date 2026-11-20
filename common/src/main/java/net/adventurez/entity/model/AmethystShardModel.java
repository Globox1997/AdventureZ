package net.adventurez.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.adventurez.entity.render.state.ProjectileRenderState;

public class AmethystShardModel extends EntityModel<ProjectileRenderState> {
    private final ModelPart shard;
    private final ModelPart cube_r1;
    private final ModelPart cube_r2;
    private final ModelPart cube_r3;
    private final ModelPart cube_r4;
    private final ModelPart cube_r5;
    private final ModelPart cube_r6;

    public AmethystShardModel(ModelPart root) {
        super(root);
        this.shard = root.getChild("shard");
        this.cube_r6 = this.shard.getChild("cube_r6");
        this.cube_r5 = this.shard.getChild("cube_r5");
        this.cube_r4 = this.shard.getChild("cube_r4");
        this.cube_r3 = this.shard.getChild("cube_r3");
        this.cube_r2 = this.shard.getChild("cube_r2");
        this.cube_r1 = this.shard.getChild("cube_r1");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("shard", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -7.0F, -2.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.2F)),
                PartPose.offset(0.0F, 19.0F, 0.0F));
        modelPartData1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 15).addBox(0.0F, -10.0F, -1.0F, 1.0F, 11.0F, 1.0F), PartPose.offset(2.0F, 4.0F, 1.0F));
        modelPartData1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 20).addBox(-1.0F, -8.0F, -1.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.23F)), PartPose.offset(0.0F, 4.0F, -2.0F));
        modelPartData1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(12, 12).addBox(-1.0F, -5.0F, 0.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(-0.17F)), PartPose.offset(0.0F, 4.0F, -3.0F));
        modelPartData1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offset(1.0F, 4.0F, 1.0F));
        modelPartData1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, -8.0F, -2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.22F)), PartPose.offset(2.0F, 5.0F, 0.0F));
        modelPartData1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(20, 0).addBox(0.0F, -6.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offset(-2.0F, 4.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }


    @Override
    public void setupAnim(ProjectileRenderState state) {
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.shard.yRot = headYaw * 0.017453292F;
        this.shard.zRot = headPitch * 0.17453292F;
        this.cube_r1.xRot = -0.1309F;
        this.cube_r1.yRot = 0.2618F;
        this.cube_r2.xRot = 0.2182F;
        this.cube_r2.yRot = 0.6545F;
        this.cube_r3.xRot = 0.1309F;
        this.cube_r4.xRot = -0.1772F;
        this.cube_r4.yRot = 0.1719F;
        this.cube_r4.zRot = -0.0306F;
        this.cube_r5.zRot = 0.2618F;
        this.cube_r6.zRot = -0.2182F;
    }
}