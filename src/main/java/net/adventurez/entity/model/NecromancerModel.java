package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.NecromancerEntity;
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
public class NecromancerModel<T extends NecromancerEntity> extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart head2;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public NecromancerModel(ModelPart root) {
        this.head = root.getChild("head");
        this.head2 = this.head.getChild("head2");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("head",
                ModelPartBuilder.create().uv(27, 8).cuboid(-4.0F, -6.0F, -4.0F, 8.0F, 8.0F, 8.0F).uv(16, 0).cuboid(5.0F, -2.0F, -2.0F, 1.0F, 3.0F, 1.0F).uv(0, 2)
                        .cuboid(-6.0F, -2.0F, -2.0F, 1.0F, 3.0F, 1.0F).uv(4, 20).cuboid(-5.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F).uv(0, 20).cuboid(-5.0F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F).uv(19, 3)
                        .cuboid(4.0F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F).uv(16, 4).cuboid(4.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F).uv(0, 0).cuboid(-1.0F, -1.0F, -5.0F, 2.0F, 1.0F, 1.0F),
                ModelTransform.pivot(0.0F, -6.0F, -2.0F));
        modelPartData1.addChild("head2", ModelPartBuilder.create().uv(36, 36).cuboid(-8.0F, -11.0F, 0.0F, 16.0F, 14.0F, 0.0F), ModelTransform.pivot(0.0F, -2.0F, 1.0F));
        modelPartData.addChild("body",
                ModelPartBuilder.create().uv(0, 27).cuboid(-5.0F, -5.0F, -3.0F, 10.0F, 9.0F, 8.0F).uv(0, 44).cuboid(-5.0F, 4.0F, -3.0F, 10.0F, 6.0F, 6.0F).uv(19, 0)
                        .cuboid(-6.0F, 10.0F, -4.0F, 12.0F, 0.0F, 8.0F).uv(52, 24).cuboid(-6.0F, 10.0F, 4.0F, 12.0F, 12.0F, 0.0F).uv(51, 0).cuboid(-6.0F, 10.0F, -4.0F, 12.0F, 12.0F, 0.0F).uv(48, 48)
                        .cuboid(-6.0F, 10.0F, -4.0F, 0.0F, 12.0F, 8.0F).uv(0, 48).cuboid(6.0F, 10.0F, -4.0F, 0.0F, 12.0F, 8.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(60, 64).cuboid(-1.0F, 3.0F, -2.0F, 4.0F, 14.0F, 4.0F).uv(10, 64).cuboid(-2.0F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F),
                ModelTransform.pivot(-8.0F, -3.0F, 0.0F));
        modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(44, 68).cuboid(-2.0F, 3.0F, -2.0F, 4.0F, 14.0F, 4.0F).uv(0, 0).cuboid(0.0F, 13.0F, -15.0F, 1.0F, 2.0F, 25.0F).uv(59, 12)
                .cuboid(-2.0F, 11.0F, -21.0F, 5.0F, 6.0F, 6.0F).uv(62, 44).cuboid(-2.0F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F), ModelTransform.pivot(7.0F, -3.0F, 0.0F));
        modelPartData.addChild("leftLeg", ModelPartBuilder.create().uv(26, 50).cuboid(-2.0F, 2.0F, -3.0F, 5.0F, 14.0F, 6.0F), ModelTransform.pivot(-2.9F, 8.0F, 0.0F));
        modelPartData.addChild("rightLeg", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, 2.0F, -3.0F, 5.0F, 14.0F, 6.0F), ModelTransform.pivot(2.9F, 8.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
    }

    @Override
    public void setAngles(T necromancer, float f, float g, float h, float i, float j) {
        this.head.yaw = i * 0.0119453292F;
        this.head.pitch = j * 0.0061453292F;
        this.head2.pitch = -0.2618F;
        this.rightArm.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.5F;
        this.rightArm.yaw = 0.0F;
        this.rightArm.roll = 0.0F;
        this.leftArm.pitch = MathHelper.cos(f * 0.6662F) * 2.0F * g * 0.5F;
        this.leftArm.yaw = 0.0F;
        this.leftArm.roll = 0.0F;
        this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g * 0.5F;
        this.rightLeg.yaw = 0.0F;
        this.rightLeg.roll = 0.0F;
        this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
        this.leftLeg.yaw = 0.0F;
        this.leftLeg.roll = 0.0F;

        if (necromancer.isSpellcasting()) {
            this.rightArm.pivotZ = 0.0F;
            this.rightArm.pivotX = 7.0F;
            this.rightArm.pitch = MathHelper.cos(h * 0.6662F) * 0.1F - 0.6F;
            this.rightArm.roll = -2.0F;
            this.rightArm.yaw = -0.5F;
        }
    }

}
