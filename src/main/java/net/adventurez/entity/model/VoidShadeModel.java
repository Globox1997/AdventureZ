package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.VoidShadeEntity;
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
public class VoidShadeModel<T extends VoidShadeEntity> extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public VoidShadeModel(ModelPart root) {
        this.body = root.getChild("body");
        this.leftArm = this.body.getChild("leftArm");
        this.rightArm = this.body.getChild("rightArm");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("body",
                ModelPartBuilder.create().uv(25, 28).cuboid(-4.0F, -9.0F, -5.0F, 8.0F, 6.0F, 9.0F).uv(34, 12).cuboid(-3.0F, -13.0F, -3.0F, 6.0F, 4.0F, 10.0F).uv(34, 0)
                        .cuboid(-3.0F, -9.0F, 4.0F, 6.0F, 4.0F, 6.0F).uv(0, 0).cuboid(-6.0F, -3.0F, -5.0F, 12.0F, 12.0F, 10.0F).uv(0, 37).cuboid(-2.0F, 13.0F, 0.0F, 4.0F, 4.0F, 11.0F).uv(0, 22)
                        .cuboid(-3.0F, 9.0F, -3.0F, 6.0F, 4.0F, 11.0F),
                ModelTransform.pivot(0.0F, 7.0F, -3.0F));
        modelPartData1.addChild("rightArm", ModelPartBuilder.create().uv(46, 43).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F), ModelTransform.pivot(-8.0F, 1.0F, 0.0F));
        modelPartData1.addChild("leftArm", ModelPartBuilder.create().uv(30, 43).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F), ModelTransform.pivot(8.0F, 1.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.body.pivotY = 4.0F + MathHelper.sin(animationProgress * 0.1262F) * 0.8F;
        this.rightArm.pitch = -1.5708F + MathHelper.sin(animationProgress * 0.1262F) * 0.1F;
        this.leftArm.pitch = this.rightArm.pitch;
        float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
        if (k > 0) {
            this.rightArm.pitch = -k * 1.5F;
        }
    }
}