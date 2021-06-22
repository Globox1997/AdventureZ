package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.BlazeGuardianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

@Environment(EnvType.CLIENT)
public class BlazeGuardianModel<T extends BlazeGuardianEntity> extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart shieldOne;
    private final ModelPart shieldTwo;
    private final ModelPart shieldThree;
    private final ModelPart shieldFour;

    public BlazeGuardianModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.shieldOne = root.getChild("shieldOne");
        this.shieldTwo = root.getChild("shieldTwo");
        this.shieldThree = root.getChild("shieldThree");
        this.shieldFour = root.getChild("shieldFour");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 42).cuboid(-2.0F, -12.0F, -2.0F, 4.0F, 22.0F, 4.0F), ModelTransform.pivot(0.0F, 13.0F, 0.0F));
        modelPartData.addChild("head",
                ModelPartBuilder.create().uv(38, 20).cuboid(-3.0F, -9.0F, -3.0F, 6.0F, 7.0F, 6.0F).uv(38, 33).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 2.0F, 1.0F).uv(0, 40)
                        .cuboid(3.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F).uv(10, 28).cuboid(3.0F, -9.0F, -3.0F, 1.0F, 7.0F, 1.0F).uv(24, 7).cuboid(-4.0F, -9.0F, -3.0F, 1.0F, 7.0F, 1.0F).uv(22, 46)
                        .cuboid(3.0F, -9.0F, 2.0F, 1.0F, 7.0F, 2.0F).uv(0, 2).cuboid(2.0F, -3.0F, 3.0F, 1.0F, 1.0F, 1.0F).uv(0, 0).cuboid(-3.0F, -3.0F, 3.0F, 1.0F, 1.0F, 1.0F).uv(16, 46)
                        .cuboid(-4.0F, -9.0F, 2.0F, 1.0F, 7.0F, 2.0F).uv(14, 0).cuboid(-3.0F, -9.0F, 3.0F, 6.0F, 6.0F, 1.0F).uv(0, 28).cuboid(3.0F, -9.0F, -2.0F, 1.0F, 4.0F, 4.0F).uv(0, 0)
                        .cuboid(-4.0F, -9.0F, -2.0F, 1.0F, 4.0F, 4.0F).uv(0, 28).cuboid(3.0F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F).uv(6, 2).cuboid(-4.0F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F).uv(24, 15)
                        .cuboid(3.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F).uv(6, 0).cuboid(-4.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F).uv(8, 36).cuboid(-4.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F).uv(32, 46)
                        .cuboid(-3.0F, -6.0F, -4.0F, 1.0F, 4.0F, 1.0F).uv(28, 46).cuboid(2.0F, -6.0F, -4.0F, 1.0F, 4.0F, 1.0F).uv(14, 7).cuboid(-2.0F, -10.0F, -4.0F, 4.0F, 1.0F, 1.0F).uv(0, 36)
                        .cuboid(-4.0F, -10.0F, -4.0F, 1.0F, 1.0F, 3.0F).uv(28, 24).cuboid(3.0F, -10.0F, -4.0F, 1.0F, 1.0F, 3.0F).uv(33, 21).cuboid(-4.0F, -10.0F, 1.0F, 1.0F, 1.0F, 3.0F).uv(28, 20)
                        .cuboid(3.0F, -10.0F, 1.0F, 1.0F, 1.0F, 3.0F).uv(0, 8).cuboid(-2.0F, -10.0F, 3.0F, 4.0F, 1.0F, 1.0F).uv(38, 22).cuboid(-1.0F, -11.0F, -4.0F, 2.0F, 1.0F, 1.0F).uv(38, 20)
                        .cuboid(-1.0F, -11.0F, 3.0F, 2.0F, 1.0F, 1.0F),
                ModelTransform.pivot(0.0F, 2.0F, 0.0F));
        modelPartData.addChild("shieldTwo", ModelPartBuilder.create().uv(28, 0).cuboid(-5.0F, -6.0F, -12.0F, 10.0F, 18.0F, 2.0F), ModelTransform.pivot(0.0F, 12.0F, 0.0F));
        modelPartData.addChild("shieldThree", ModelPartBuilder.create().uv(38, 38).cuboid(-5.0F, -6.0F, 10.0F, 10.0F, 18.0F, 2.0F), ModelTransform.pivot(0.0F, 12.0F, 0.0F));
        modelPartData.addChild("shieldFour", ModelPartBuilder.create().uv(28, 0).cuboid(-5.0F, -6.0F, -12.0F, 10.0F, 18.0F, 2.0F), ModelTransform.pivot(0.0F, 12.0F, 0.0F));
        modelPartData.addChild("shieldOne", ModelPartBuilder.create().uv(28, 0).cuboid(-5.0F, -6.0F, -12.0F, 10.0F, 18.0F, 2.0F), ModelTransform.pivot(0.0F, 12.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.shieldOne, this.shieldTwo, this.shieldThree, this.shieldFour);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float f = 0.47123894F + animationProgress * 3.1415927F * -0.05F;
        this.shieldOne.pitch = -0.3927F;
        this.shieldTwo.pitch = -0.3927F;
        this.shieldThree.pitch = 0.3927F;
        this.shieldFour.pitch = -0.3927F;
        this.shieldOne.yaw = f + 1.5708F;
        this.shieldTwo.yaw = f - 1.5708F;
        this.shieldThree.yaw = f;
        this.shieldFour.yaw = f;
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

}
