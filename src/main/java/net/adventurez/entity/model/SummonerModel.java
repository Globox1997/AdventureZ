package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.SummonerEntity;
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
public class SummonerModel<T extends SummonerEntity> extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public SummonerModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("body",
                ModelPartBuilder.create().uv(32, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F).uv(44, 48).cuboid(-9.0F, -1.0F, -3.0F, 5.0F, 4.0F, 7.0F).uv(20, 48)
                        .cuboid(4.0F, -1.0F, -3.0F, 5.0F, 4.0F, 7.0F).uv(56, 0).cuboid(-4.0F, 12.0F, -2.0F, 8.0F, 9.0F, 0.0F).uv(48, 16).cuboid(-4.0F, 12.0F, 2.0F, 8.0F, 9.0F, 0.0F).uv(52, 55)
                        .cuboid(4.0F, 12.0F, -2.0F, 0.0F, 9.0F, 4.0F).uv(44, 55).cuboid(-4.0F, 12.0F, -2.0F, 0.0F, 9.0F, 4.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData1.addChild("cape",
                ModelPartBuilder.create().uv(10, 40).cuboid(-8.0F, -2.0F, 0.0F, 0.0F, 22.0F, 5.0F).uv(0, 40).cuboid(8.0F, -2.0F, 0.0F, 0.0F, 22.0F, 5.0F).uv(3, 67)
                        .cuboid(7.0F, -2.0F, 0.0F, 1.0F, 22.0F, 0.0F).uv(0, 67).cuboid(-8.0F, -2.0F, 0.0F, 1.0F, 22.0F, 0.0F).uv(0, 23).cuboid(-8.0F, -2.0F, 5.0F, 16.0F, 22.0F, 0.0F),
                ModelTransform.pivot(0.0F, 1.0F, -2.0F));
        modelPartData.addChild("head",
                ModelPartBuilder.create().uv(24, 0).cuboid(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F).uv(36, 16).cuboid(-5.0F, -9.0F, -6.0F, 1.0F, 4.0F, 10.0F).uv(24, 16)
                        .cuboid(-4.0F, -9.0F, 3.0F, 8.0F, 4.0F, 1.0F).uv(0, 14).cuboid(-4.0F, -9.0F, -6.0F, 8.0F, 4.0F, 1.0F).uv(0, 0).cuboid(4.0F, -9.0F, -6.0F, 1.0F, 4.0F, 10.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild("rightArm",
                ModelPartBuilder.create().uv(36, 59).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).uv(37, 48).cuboid(-1.5F, 7.0F, -8.0F, 3.0F, 3.0F, 3.0F).uv(0, 8)
                        .cuboid(-1.5F, 8.0F, -5.0F, 3.0F, 1.0F, 1.0F).uv(17, 18).cuboid(-0.5F, 7.0F, -5.0F, 1.0F, 3.0F, 1.0F).uv(12, 4).cuboid(-0.5F, 6.0F, -7.0F, 1.0F, 1.0F, 3.0F).uv(12, 0)
                        .cuboid(-0.5F, 10.0F, -7.0F, 1.0F, 1.0F, 3.0F).uv(0, 4).cuboid(-2.5F, 8.0F, -7.0F, 1.0F, 1.0F, 3.0F).uv(0, 0).cuboid(1.5F, 8.0F, -7.0F, 1.0F, 1.0F, 3.0F).uv(0, 0)
                        .cuboid(-0.5F, 8.0F, -4.0F, 1.0F, 1.0F, 22.0F),
                ModelTransform.pivot(5.0F, 2.0F, 0.0F));
        modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(28, 59).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        modelPartData.addChild("rightLeg", ModelPartBuilder.create().uv(20, 59).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
        modelPartData.addChild("leftLeg", ModelPartBuilder.create().uv(56, 28).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
    }

    @Override
    public void setAngles(T summoner, float f, float g, float h, float i, float j) {
        this.head.yaw = i * 0.0119453292F;
        this.head.pitch = j * 0.0061453292F;
        this.rightArm.pitch = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.5F - 1.0472F);
        this.rightArm.yaw = 0.0F;
        this.rightArm.roll = 0.0F;
        this.leftArm.pitch = MathHelper.cos(f * 0.6662F) * 2.0F * g * 0.5F;
        if (this.leftArm.pitch > 0.2F) {
            this.leftArm.pitch = 0.2F;
        }
        this.leftArm.yaw = 0.0F;
        this.leftArm.roll = 0.0F;
        this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g * 0.5F;
        this.rightLeg.yaw = 0.0F;
        this.rightLeg.roll = 0.0F;
        this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
        this.leftLeg.yaw = 0.0F;
        this.leftLeg.roll = 0.0F;

        if (summoner.isSpellcasting()) {
            this.rightArm.pivotZ = 0.0F;
            this.rightArm.pivotX = 7.0F;
            this.rightArm.pitch = MathHelper.cos(h * 0.6662F) * 0.1F - 0.4F;
            this.rightArm.roll = -1.7F;
            this.rightArm.yaw = -0.45F;
        }
    }

}
