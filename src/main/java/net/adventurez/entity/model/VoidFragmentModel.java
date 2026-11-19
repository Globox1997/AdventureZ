package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.VoidFragmentEntity;
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
import net.minecraft.world.entity.Entity;

@Environment(EnvType.CLIENT)
public class VoidFragmentModel<T extends Entity> extends ListModel<T> {
    private final ModelPart small;
    private final ModelPart small1;
    private final ModelPart small2;
    private final ModelPart small3;
    private final ModelPart small4;
    private final ModelPart small5;
    private final ModelPart small6;
    private final ModelPart big;
    private final ModelPart big1;
    private final ModelPart big2;
    private final ModelPart big3;
    private final ModelPart big4;
    private final ModelPart big5;

    public VoidFragmentModel(ModelPart root) {
        this.small = root.getChild("small");
        this.small6 = this.small.getChild("small6");
        this.small5 = this.small.getChild("small5");
        this.small4 = this.small.getChild("small4");
        this.small3 = this.small.getChild("small3");
        this.small2 = this.small.getChild("small2");
        this.small1 = this.small.getChild("small1");
        this.big = root.getChild("big");
        this.big5 = this.big.getChild("big5");
        this.big4 = this.big.getChild("big4");
        this.big3 = this.big.getChild("big3");
        this.big2 = this.big.getChild("big2");
        this.big1 = this.big.getChild("big1");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("small", CubeListBuilder.create().texOffs(16, 0).addBox(-1.5F, -7.0F, -1.5F, 3.0F, 13.0F, 3.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
        modelPartData1.addOrReplaceChild("small1", CubeListBuilder.create().texOffs(16, 32).addBox(3.5F, -16.0F, 1.5F, 1.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        modelPartData1.addOrReplaceChild("small2", CubeListBuilder.create().texOffs(0, 0).addBox(1.5F, -11.0F, -2.5F, 1.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        modelPartData1.addOrReplaceChild("small3", CubeListBuilder.create().texOffs(30, 30).addBox(-4.5F, -11.0F, 0.5F, 2.0F, 6.0F, 2.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        modelPartData1.addOrReplaceChild("small4", CubeListBuilder.create().texOffs(32, 15).addBox(-2.5F, -5.0F, -4.5F, 1.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        modelPartData1.addOrReplaceChild("small5", CubeListBuilder.create().texOffs(12, 0).addBox(-1.5F, -7.0F, 2.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        modelPartData1.addOrReplaceChild("small6", CubeListBuilder.create().texOffs(28, 11).addBox(3.5F, -6.0F, 0.5F, 2.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData.addOrReplaceChild("big", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -11.0F, -2.0F, 4.0F, 23.0F, 4.0F), PartPose.offset(0.0F, 6.0F, 0.0F));
        modelPartData2.addOrReplaceChild("big1", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0F, -21.0F, 4.0F, 2.0F, 14.0F, 2.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
        modelPartData2.addOrReplaceChild("big2", CubeListBuilder.create().texOffs(16, 16).addBox(-6.0F, -25.0F, -3.0F, 2.0F, 14.0F, 2.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
        modelPartData2.addOrReplaceChild("big3", CubeListBuilder.create().texOffs(28, 0).addBox(5.0F, -11.0F, -6.0F, 2.0F, 9.0F, 2.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
        modelPartData2.addOrReplaceChild("big4", CubeListBuilder.create().texOffs(8, 27).addBox(-1.0F, -32.0F, -7.0F, 2.0F, 9.0F, 2.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
        modelPartData2.addOrReplaceChild("big5", CubeListBuilder.create().texOffs(0, 27).addBox(3.0F, -17.0F, 2.0F, 2.0F, 11.0F, 2.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.small, this.big);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (entity.getEntityData().get(VoidFragmentEntity.IS_VOID_ORB)) {
            big.visible = true;
            small.visible = false;
            this.big.y = 6.0F + Mth.cos(animationProgress * 0.1662F) * 0.6F;
            this.big1.y = 16.0F + Mth.cos(animationProgress * 0.2662F + Mth.PI * 0.3F) * 0.9F;
            this.big2.y = 16.0F + Mth.cos(animationProgress * 0.2662F + Mth.PI * 0.6F) * 1.2F;
            this.big3.y = 16.0F + Mth.cos(animationProgress * 0.2662F + Mth.PI * 1.3F) * 0.7F;
            this.big4.y = 16.0F + Mth.cos(animationProgress * 0.2662F + Mth.PI * 1.6F) * 0.8F;
            this.big5.y = 16.0F + Mth.cos(animationProgress * 0.2662F + Mth.PI) * 0.6F;
        } else {
            big.visible = false;
            small.visible = true;
            this.small.y = 12.0F + Mth.cos(animationProgress * 0.1662F) * 0.6F;
            this.small1.y = 8.0F + Mth.cos(animationProgress * 0.1662F + Mth.PI * 0.3F) * 0.9F;
            this.small2.y = 8.0F + Mth.cos(animationProgress * 0.1662F + Mth.PI * 0.6F) * 1.2F;
            this.small3.y = 8.0F + Mth.cos(animationProgress * 0.1662F + Mth.PI * 1.3F) * 0.7F;
            this.small4.y = 8.0F + Mth.cos(animationProgress * 0.1662F + Mth.PI * 1.6F) * 0.8F;
            this.small5.y = 8.0F + Mth.cos(animationProgress * 0.1662F + Mth.PI) * 0.6F;
            this.small6.y = 8.0F + Mth.cos(animationProgress * 0.1662F + Mth.PI) * 0.4F;
        }
    }
}