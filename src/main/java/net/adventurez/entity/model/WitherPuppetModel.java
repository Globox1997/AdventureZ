package net.adventurez.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.mob.MobEntity;

@Environment(EnvType.CLIENT)
public class WitherPuppetModel<T extends MobEntity> extends BipedEntityModel<T> {

    public WitherPuppetModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -3.0F, -2.0F, 8.0F, 7.0F, 4.0F), ModelTransform.pivot(0.0F, 14.0F, 0.0F));
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F, 10.0F, 0.0F));
        modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(0, 27).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F), ModelTransform.pivot(5.0F, 12.0F, 0.0F));
        modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(24, 24).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F), ModelTransform.pivot(-5.0F, 12.0F, 0.0F));
        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, 1.0F, -1.0F, 2.0F, 6.0F, 2.0F), ModelTransform.pivot(2.0F, 17.0F, 0.0F));
        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 1.0F, -1.0F, 2.0F, 6.0F, 2.0F), ModelTransform.pivot(-2.0F, 17.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T puppet, float f, float g, float h, float i, float j) {
        float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
        this.rightArm.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.5F;
        this.leftArm.pitch = MathHelper.cos(f * 0.6662F) * 2.0F * g * 0.5F;
        this.rightArm.roll = 0.0F;
        this.leftArm.roll = 0.0F;
        this.rightArm.yaw = -(0.1F - k * 0.6F);
        this.rightArm.pitch = -k * 1.5F;
        this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
        this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
    }

}
