package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.OrcEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class OrcModel<T extends OrcEntity> extends CompositeEntityModel<T> {
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

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("leftLeg", ModelPartBuilder.create().uv(54, 0).cuboid(-3.5F, 6.0F, -3.0F, 6.0F, 6.0F, 6.0F).uv(60, 35).cuboid(-3.0F, -1.0F, -2.5F, 5.0F, 7.0F, 5.0F),
                ModelTransform.pivot(-3.0F, 12.0F, 0.0F));
        modelPartData.addChild("rightLeg", ModelPartBuilder.create().uv(42, 50).cuboid(-2.5F, 6.0F, -3.0F, 6.0F, 6.0F, 6.0F).uv(0, 57).cuboid(-2.0F, -1.0F, -2.5F, 5.0F, 7.0F, 5.0F),
                ModelTransform.pivot(3.0F, 12.0F, 0.0F));
        modelPartData.addChild("torso", ModelPartBuilder.create().uv(0, 17).cuboid(-7.0F, 5.0F, -4.0F, 14.0F, 7.0F, 8.0F).uv(0, 0).cuboid(-7.0F, -5.0F, -3.0F, 14.0F, 10.0F, 7.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(18, 50).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 6.0F, 6.0F).uv(61, 61).cuboid(-2.5F, 4.0F, -2.5F, 5.0F, 6.0F, 5.0F).uv(44, 23)
                .cuboid(-3.0F, 10.0F, -3.0F, 6.0F, 6.0F, 6.0F), ModelTransform.pivot(-10.0F, -2.0F, 1.0F));
        modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(0, 44).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 6.0F, 6.0F).uv(60, 12).cuboid(-2.5F, 4.0F, -2.5F, 5.0F, 6.0F, 5.0F).uv(36, 11)
                .cuboid(-3.0F, 10.0F, -3.0F, 6.0F, 6.0F, 6.0F), ModelTransform.pivot(10.0F, -2.0F, 1.0F));
        ModelPartData modelPartData1 = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 32).cuboid(-4.5F, 0.0F, -8.0F, 9.0F, 3.0F, 9.0F).uv(0, 2)
                .cuboid(2.5F, -1.0F, -7.5F, 1.0F, 1.0F, 1.0F).uv(0, 0).cuboid(-3.5F, -1.0F, -7.5F, 1.0F, 1.0F, 1.0F), ModelTransform.pivot(0.0F, -4.0F, 0.0F));
        ModelPartData modelPartData2 = modelPartData1.addChild("header", ModelPartBuilder.create().uv(28, 36).cuboid(-4.0F, 0.0F, 0.2F, 8.0F, 6.0F, 8.0F), ModelTransform.pivot(0.0F, -6.0F, -7.0F));
        ModelPartData modelPartData3 = modelPartData2.addChild("earLeft", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, 7.0F));
        modelPartData3.addChild("ear2", ModelPartBuilder.create().uv(42, 0).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 4.0F, 4.0F), ModelTransform.pivot(-2.0F, -2.0F, -1.0F));
        ModelPartData modelPartData4 = modelPartData2.addChild("earRight", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -4.0F, 7.0F));
        modelPartData4.addChild("ear", ModelPartBuilder.create().uv(60, 47).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 4.0F, 4.0F), ModelTransform.pivot(2.0F, 4.0F, -1.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.torso, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.leftArm.pitch = -0.2618F;
        this.leftArm.roll = 0.0436F;
        this.rightArm.pitch = -0.2618F;
        this.rightArm.roll = -0.0436F;
        this.header.pitch = -0.0436F;
        this.ear2.pitch = 0.48F;
        this.ear2.yaw = -0.5236F;
        this.ear.pitch = 0.48F;
        this.ear.yaw = 0.5236F;

        this.head.yaw = headYaw * 0.0089453292F;
        this.head.pitch = headPitch * 0.0047453292F;

        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        this.rightArm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.32F;
        this.leftArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.32F;
        float k = MathHelper.sin(entity.handSwingProgress * 3.1415927F);
        if (k > 0) {
            this.rightArm.pitch = -k;
            if (entity.getDataTracker().get(OrcEntity.DOUBLE_HAND_ATTACK)) {
                this.leftArm.pitch = -k;
            }
        }
    }

    public ModelPart getTorso() {
        return this.torso;
    }

}