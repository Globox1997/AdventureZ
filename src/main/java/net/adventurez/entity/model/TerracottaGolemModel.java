package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;
import net.adventurez.entity.TerracottaGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class TerracottaGolemModel<T extends TerracottaGolemEntity> extends ListModel<T> {
    private final ModelPart body;
    private final ModelPart block;
    private final ModelPart head;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart right_leg;
    private final ModelPart left_leg;

    public TerracottaGolemModel(ModelPart root) {
        this.body = root.getChild("body");
        this.block = root.getChild("block");
        this.head = body.getChild("head");
        this.right_arm = body.getChild("right_arm");
        this.left_arm = body.getChild("left_arm");
        this.right_leg = body.getChild("right_leg");
        this.left_leg = body.getChild("left_leg");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition block = modelPartData.addOrReplaceChild("block", CubeListBuilder.create().texOffs(112, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -23.0F, -14.0F, 31.0F, 23.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(72, 69).addBox(2.0F, -26.0F, -8.0F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(64, 96).addBox(-10.0F, 0.0F, -6.0F, 22.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(87, 0).addBox(-12.0F, -38.0F, -4.0F, 10.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6109F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -41.0F, -7.0F, 10.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5672F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(72, 48).addBox(-7.0F, -6.0F, -9.0F, 16.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, -14.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 48).addBox(-8.0F, -6.0F, -4.0F, 9.0F, 39.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.0F, -15.0F, -3.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(36, 48).addBox(0.0F, -6.0F, -4.0F, 9.0F, 39.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 122).addBox(1.0F, -3.0F, -5.0F, 7.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 122).addBox(6.0F, -7.0F, -5.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, -15.0F, -3.0F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 96).addBox(-5.0F, 0.0F, -1.0F, 8.0F, 18.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(64, 96).addBox(-4.0F, 2.0F, -2.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 9.0F, -3.0F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 96).addBox(-5.0F, 0.0F, -1.0F, 8.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 9.0F, -3.0F));
        return LayerDefinition.create(modelData, 256, 128);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.body, this.block);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yRot = headYaw * 0.0039453292F;
        this.head.xRot = headPitch * 0.0027453292F;
        this.right_leg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_leg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.right_arm.xRot = -Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
        this.left_arm.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
        float k = Mth.sin(entity.attackAnim * 3.1415927F);
        if (k > 0) {
            this.right_arm.xRot = -k;
        }
        boolean isBlock = entity.getEntityData().get(TerracottaGolemEntity.BLOCK_VARIANT);
        this.body.visible = !isBlock;
        this.block.visible = isBlock;
    }

}
