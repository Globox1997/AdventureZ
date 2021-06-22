package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;

@Environment(EnvType.CLIENT)
public class TinyEyeModel<T extends Entity> extends CompositeEntityModel<T> {
    private final ModelPart body;

    public TinyEyeModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("body",
                ModelPartBuilder.create().uv(10, 10).cuboid(-3.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F).uv(0, 0).cuboid(-1.5F, -6.0F, -1.0F, 3.0F, 13.0F, 2.0F).uv(18, 18)
                        .cuboid(-4.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F).uv(10, 0).cuboid(-5.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F).uv(8, 23).cuboid(-6.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F).uv(0, 15)
                        .cuboid(1.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F).uv(16, 0).cuboid(3.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F).uv(20, 9).cuboid(4.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F).uv(22, 0)
                        .cuboid(5.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F),
                ModelTransform.pivot(0.0F, 15.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setAngles(T bullet, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.body.yaw = headYaw * 0.017453292F;
    }
}