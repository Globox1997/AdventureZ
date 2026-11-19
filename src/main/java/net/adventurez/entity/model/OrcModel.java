package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.OrcEntity;
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

@Environment(EnvType.CLIENT)
public class OrcModel<T extends OrcEntity> extends ListModel<T> {
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart torso;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart head;
    private final ModelPart header;
    private final ModelPart earLeft;
    private final ModelPart ear2;
    private final ModelPart earRight;
    private final ModelPart ear;

    public OrcModel(ModelPart root) {
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
        this.torso = root.getChild("torso");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.head = root.getChild("head");
        this.header = this.head.getChild("header");
        this.earRight = this.header.getChild("earRight");
        this.ear = this.earRight.getChild("ear");
        this.earLeft = this.header.getChild("earLeft");
        this.ear2 = this.earLeft.getChild("ear2");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(54, 0).addBox(-3.5F, 6.0F, -3.0F, 6.0F, 6.0F, 6.0F).texOffs(60, 35).addBox(-3.0F, -1.0F, -2.5F, 5.0F, 7.0F, 5.0F),
                PartPose.offset(-3.0F, 12.0F, 0.0F));
        modelPartData.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(42, 50).addBox(-2.5F, 6.0F, -3.0F, 6.0F, 6.0F, 6.0F).texOffs(0, 57).addBox(-2.0F, -1.0F, -2.5F, 5.0F, 7.0F, 5.0F),
                PartPose.offset(3.0F, 12.0F, 0.0F));
        modelPartData.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 17).addBox(-7.0F, 5.0F, -4.0F, 14.0F, 7.0F, 8.0F).texOffs(0, 0).addBox(-7.0F, -5.0F, -3.0F, 14.0F, 10.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(18, 50).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 6.0F, 6.0F).texOffs(61, 61).addBox(-2.5F, 4.0F, -2.5F, 5.0F, 6.0F, 5.0F).texOffs(44, 23)
                .addBox(-3.0F, 10.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(-10.0F, -2.0F, 1.0F));
        modelPartData.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 44).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 6.0F, 6.0F).texOffs(60, 12).addBox(-2.5F, 4.0F, -2.5F, 5.0F, 6.0F, 5.0F).texOffs(36, 11)
                .addBox(-3.0F, 10.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(10.0F, -2.0F, 1.0F));
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 32).addBox(-4.5F, 0.0F, -8.0F, 9.0F, 3.0F, 9.0F).texOffs(0, 2)
                .addBox(2.5F, -1.0F, -7.5F, 1.0F, 1.0F, 1.0F).texOffs(0, 0).addBox(-3.5F, -1.0F, -7.5F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, -4.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("header", CubeListBuilder.create().texOffs(28, 36).addBox(-4.0F, 0.0F, 0.2F, 8.0F, 6.0F, 8.0F), PartPose.offset(0.0F, -6.0F, -7.0F));
        PartDefinition modelPartData3 = modelPartData2.addOrReplaceChild("earLeft", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 7.0F));
        modelPartData3.addOrReplaceChild("ear2", CubeListBuilder.create().texOffs(42, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 4.0F, 4.0F), PartPose.offset(-2.0F, -2.0F, -1.0F));
        PartDefinition modelPartData4 = modelPartData2.addOrReplaceChild("earRight", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 7.0F));
        modelPartData4.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(60, 47).addBox(0.0F, 0.0F, 0.0F, 1.0F, 4.0F, 4.0F), PartPose.offset(2.0F, 4.0F, -1.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.head, this.torso, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.leftArm.xRot = -0.2618F;
        this.leftArm.zRot = 0.0436F;
        this.rightArm.xRot = -0.2618F;
        this.rightArm.zRot = -0.0436F;
        this.header.xRot = -0.0436F;
        this.ear2.xRot = 0.48F;
        this.ear2.yRot = -0.5236F;
        this.ear.xRot = 0.48F;
        this.ear.yRot = 0.5236F;

        this.head.yRot = headYaw * 0.0089453292F;
        this.head.xRot = headPitch * 0.0047453292F;

        this.rightLeg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftLeg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        this.rightArm.xRot = -Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.32F;
        this.leftArm.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.32F;
        float k = Mth.sin(entity.attackAnim * 3.1415927F);
        if (k > 0) {
            this.rightArm.xRot = -k;
            if (entity.getEntityData().get(OrcEntity.DOUBLE_HAND_ATTACK)) {
                this.leftArm.xRot = -k;
            }
        }
    }

    public ModelPart getTorso() {
        return this.torso;
    }

}