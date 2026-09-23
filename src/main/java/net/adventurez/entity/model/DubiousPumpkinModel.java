package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.DubiousPumpkinEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class DubiousPumpkinModel<T extends DubiousPumpkinEntity> extends CompositeEntityModel<T> {
    private final ModelPart root;
    private final ModelPart waist;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public DubiousPumpkinModel(ModelPart rootPart) {
        this.root = rootPart.getChild("root");
        this.waist = root.getChild("waist");
        this.body = waist.getChild("body");
        this.leftArm = body.getChild("leftArm");
        this.rightArm = body.getChild("rightArm");
        this.leftLeg = waist.getChild("leftLeg");
        this.rightLeg = waist.getChild("rightLeg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData waist = root.addChild("waist", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

        ModelPartData body = waist.addChild("body", ModelPartBuilder.create().uv(1, 1).cuboid(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, 0.0F));

        ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(37, 0).cuboid(-6.0F, -12.0F, 0.0F, 12.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(15, 27).cuboid(0.0F, -1.5F, 1.5F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(6.0F, 2.5F, -2.0F));

        ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(15, 27).mirrored().cuboid(-4.0F, -1.5F, 1.5F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-6.0F, 2.5F, -2.0F));

        ModelPartData leftLeg = waist.addChild("leftLeg", ModelPartBuilder.create().uv(1, 26).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 0.0F, 0.0F));

        ModelPartData rightLeg = waist.addChild("rightLeg", ModelPartBuilder.create().uv(1, 26).mirrored().cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.root);
    }

    @Override
    public void setAngles(T fungus, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
//        this.tail.pitch = -1.0F;
//        this.head.pitch = headPitch * 0.010453292F;
//        this.head.yaw = headYaw * 0.010453292F;
        this.body.roll = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance*0.04f;
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.rightArm.roll = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.2f;
        this.leftArm.roll = MathHelper.cos(limbAngle * 0.6662F+ 3.1415927F) * 1.4F * limbDistance * 0.2f;

//        float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
//
//        if (k > 0.0F)
//            this.head.pitch = -k * 0.8F;

    }

}
