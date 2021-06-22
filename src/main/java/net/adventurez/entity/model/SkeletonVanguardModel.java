package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.SkeletonVanguardEntity;
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
public class SkeletonVanguardModel<T extends SkeletonVanguardEntity> extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart spear;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public SkeletonVanguardModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.spear = this.rightArm.getChild("spear");
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 38).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F).uv(32, 0).cuboid(-5.0F, 0.0F, -3.0F, 10.0F, 6.0F, 6.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild("head", ModelPartBuilder.create().uv(32, 32).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F).uv(0, 21).cuboid(-5.0F, -9.0F, -5.0F, 10.0F, 7.0F, 10.0F).uv(24, 38)
                .cuboid(-1.0F, -11.0F, -6.0F, 2.0F, 8.0F, 1.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData modelPartData1 = modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(22, 63).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(5.0F, 2.0F, 0.0F));
        modelPartData1.addChild("spear",
                ModelPartBuilder.create().uv(58, 0).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F).uv(56, 36).cuboid(-0.5F, 1.0F, -2.0F, 1.0F, 1.0F, 2.0F).uv(56, 33)
                        .cuboid(-0.5F, 2.0F, -3.0F, 1.0F, 1.0F, 2.0F).uv(56, 30).cuboid(-0.5F, 3.0F, -4.0F, 1.0F, 1.0F, 2.0F).uv(44, 55).cuboid(-0.5F, 4.0F, -5.0F, 1.0F, 1.0F, 2.0F).uv(54, 51)
                        .cuboid(-0.5F, 5.0F, -6.0F, 1.0F, 1.0F, 2.0F).uv(54, 48).cuboid(-0.5F, 6.0F, -7.0F, 1.0F, 1.0F, 2.0F).uv(54, 54).cuboid(-0.5F, 7.0F, -8.0F, 1.0F, 1.0F, 2.0F).uv(40, 54)
                        .cuboid(-0.5F, 8.0F, -9.0F, 1.0F, 1.0F, 2.0F).uv(32, 16).cuboid(-0.5F, 9.0F, -11.0F, 1.0F, 1.0F, 3.0F).uv(30, 21).cuboid(-0.5F, 10.0F, -12.0F, 1.0F, 1.0F, 5.0F).uv(0, 54)
                        .cuboid(-0.5F, 11.0F, -13.0F, 1.0F, 1.0F, 4.0F).uv(40, 49).cuboid(-0.5F, 12.0F, -14.0F, 1.0F, 1.0F, 4.0F).uv(34, 48).cuboid(-0.5F, 13.0F, -15.0F, 1.0F, 1.0F, 4.0F).uv(40, 27)
                        .cuboid(-0.5F, 14.0F, -16.0F, 1.0F, 1.0F, 4.0F).uv(40, 12).cuboid(-0.5F, 15.0F, -17.0F, 1.0F, 1.0F, 4.0F).uv(0, 26).cuboid(-0.5F, 16.0F, -18.0F, 1.0F, 1.0F, 4.0F).uv(0, 21)
                        .cuboid(-0.5F, 17.0F, -19.0F, 1.0F, 1.0F, 4.0F).uv(9, 12).cuboid(-0.5F, 18.0F, -20.0F, 1.0F, 1.0F, 4.0F).uv(0, 2).cuboid(-0.5F, 11.0F, -8.0F, 1.0F, 1.0F, 1.0F).uv(52, 12)
                        .cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F).uv(46, 17).cuboid(-0.5F, -2.0F, 1.0F, 1.0F, 1.0F, 2.0F).uv(46, 12).cuboid(-0.5F, -3.0F, 2.0F, 1.0F, 1.0F, 2.0F).uv(40, 17)
                        .cuboid(-0.5F, -4.0F, 3.0F, 1.0F, 1.0F, 2.0F).uv(37, 21).cuboid(-0.5F, -5.0F, 4.0F, 1.0F, 1.0F, 2.0F).uv(12, 17).cuboid(-0.5F, -6.0F, 5.0F, 1.0F, 1.0F, 2.0F).uv(32, 0)
                        .cuboid(-0.5F, -8.0F, 6.0F, 1.0F, 2.0F, 2.0F).uv(0, 0).cuboid(-0.5F, -9.0F, 8.0F, 1.0F, 1.0F, 1.0F),
                ModelTransform.pivot(0.0F, 9.0F, 0.0F));
        modelPartData.addChild("leftArm",
                ModelPartBuilder.create().uv(56, 59).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).uv(0, 0).cuboid(-3.0F, 11.0F, -6.0F, 6.0F, 1.0F, 20.0F).uv(0, 12)
                        .cuboid(-1.0F, 10.0F, -2.0F, 2.0F, 1.0F, 5.0F).uv(32, 12).cuboid(3.0F, 11.0F, -4.0F, 3.0F, 1.0F, 3.0F).uv(0, 54).cuboid(3.0F, 11.0F, 4.0F, 2.0F, 1.0F, 10.0F).uv(40, 48)
                        .cuboid(-5.0F, 11.0F, 4.0F, 2.0F, 1.0F, 10.0F).uv(30, 27).cuboid(-6.0F, 11.0F, -4.0F, 3.0F, 1.0F, 3.0F).uv(0, 6).cuboid(3.0F, 11.0F, -1.0F, 5.0F, 1.0F, 5.0F).uv(0, 0)
                        .cuboid(-8.0F, 11.0F, -1.0F, 5.0F, 1.0F, 5.0F),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        modelPartData.addChild("rightLeg", ModelPartBuilder.create().uv(48, 59).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).uv(18, 48).cuboid(-2.0F, -2.0F, -3.0F, 5.0F, 9.0F, 6.0F),
                ModelTransform.pivot(2.0F, 12.0F, 0.0F));
        modelPartData.addChild("leftLeg", ModelPartBuilder.create().uv(40, 59).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).uv(46, 15).cuboid(-3.0F, -2.0F, -3.0F, 5.0F, 9.0F, 6.0F),
                ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
    }

    @Override
    public void setAngles(T vanguard, float f, float g, float h, float i, float j) {
        this.spear.pitch = -0.7854F;
        this.rightArm.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.4F;
        this.rightArm.yaw = 0.0F;
        this.rightArm.roll = 0.0F;
        this.leftArm.pitch = (MathHelper.cos(f * 0.6662F) * 2.0F * g * 0.5F) * 0.2F - 1.5708F;
        this.leftArm.yaw = 0.0F;
        this.leftArm.roll = 0.0F;
        this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g * 0.5F;
        this.rightLeg.yaw = 0.0F;
        this.rightLeg.roll = 0.0F;
        this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
        this.leftLeg.yaw = 0.0F;
        this.leftLeg.roll = 0.0F;
        float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
        this.rightArm.pitch = -k * 1.5F;
        float shieldSwing = vanguard.getDataTracker().get(SkeletonVanguardEntity.SHIELD_SWING) * 2F;
        if (shieldSwing > 0.0F) {
            this.leftArm.pitch = MathHelper.sin(shieldSwing * 3.1415927F) - 1.5708F;
        }
    }

}
