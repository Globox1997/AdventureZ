package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.IguanaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class IguanaModel<T extends IguanaEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart cube_r1;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart left_middle;
    private final ModelPart left_front;
    private final ModelPart left_back;
    private final ModelPart right_middle;
    private final ModelPart right_front;
    private final ModelPart right_back;

    public IguanaModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.cube_r1 = this.tail.getChild("cube_r1");
        this.head = root.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.left_middle = root.getChild("left_middle");
        this.left_front = root.getChild("left_front");
        this.left_back = root.getChild("left_back");
        this.right_middle = root.getChild("right_middle");
        this.right_front = root.getChild("right_front");
        this.right_back = root.getChild("right_back");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -3.5F, -18.0F, 11.0F, 6.0F, 15.0F).texOffs(0, 0).addBox(-5.5F, -3.5F, -18.0F, 11.0F, 6.0F, 15.0F).texOffs(37, 0)
                        .addBox(-4.5F, -2.5F, -3.0F, 9.0F, 5.0F, 6.0F).texOffs(0, 20).addBox(0.0F, -6.0F, -17.0F, 0.0F, 3.0F, 13.0F).texOffs(0, 0).addBox(0.0F, -5.0F, -1.0F, 0.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, 20.5F, 10.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 2.5F));
        modelPartData2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 23).addBox(-3.5F, -2.5F, -0.5F, 7.0F, 4.0F, 10.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData3 = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 21).addBox(-4.5F, -3.5F, -8.5F, 9.0F, 3.0F, 9.0F), PartPose.offset(0.0F, 21.5F, -7.5F));
        modelPartData3.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 37).addBox(-4.5F, -0.5F, -8.5F, 9.0F, 2.0F, 9.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("left_middle", CubeListBuilder.create().texOffs(27, 37).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F), PartPose.offset(7.5F, 19.5F, 4.0F));
        modelPartData.addOrReplaceChild("left_front", CubeListBuilder.create().texOffs(27, 37).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F), PartPose.offset(7.5F, 19.5F, -5.0F));
        modelPartData.addOrReplaceChild("left_back", CubeListBuilder.create().texOffs(27, 37).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F), PartPose.offset(6.5F, 19.5F, 10.0F));
        modelPartData.addOrReplaceChild("right_middle", CubeListBuilder.create().texOffs(27, 37).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, true), PartPose.offset(-7.5F, 19.5F, 4.0F));
        modelPartData.addOrReplaceChild("right_front", CubeListBuilder.create().texOffs(27, 37).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, true), PartPose.offset(-7.5F, 19.5F, -5.0F));
        modelPartData.addOrReplaceChild("right_back", CubeListBuilder.create().texOffs(27, 37).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, true), PartPose.offset(-6.5F, 19.5F, 10.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.cube_r1.xRot = -0.1745F;
        this.head.xRot = headPitch * 0.007453292F;
        this.head.yRot = headYaw * 0.0017453292F;
        this.right_back.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.left_back.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.right_front.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_front.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        this.right_middle.xRot = Mth.cos(limbAngle * 0.6662F + 4.7123889803F) * 1.4F * limbDistance;
        this.left_middle.xRot = Mth.cos(limbAngle * 0.6662F + 1.57079632F) * 1.4F * limbDistance;

        if (entity.getEntityData().get(IguanaEntity.OPEN_MOUTH)) {
            this.jaw.xRot = 0.3F;
        } else
            this.jaw.xRot = 0.0F;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.head, this.body, this.left_middle, this.left_front, this.left_back, this.right_middle, this.right_front, this.right_back);
    }

}
