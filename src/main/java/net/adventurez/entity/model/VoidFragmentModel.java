package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.VoidFragmentEntity;
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
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class VoidFragmentModel<T extends Entity> extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart orbBody;

    public VoidFragmentModel(ModelPart root) {
        this.body = root.getChild("body");
        this.orbBody = root.getChild("orbBody");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("body",
                ModelPartBuilder.create().uv(6, 10).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F).uv(56, 12).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 2.0F, 6.0F).uv(42, 20)
                        .cuboid(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 10.0F).uv(0, 20).cuboid(-7.0F, -10.0F, -7.0F, 14.0F, 4.0F, 14.0F).uv(42, 0).cuboid(-5.0F, -12.0F, -5.0F, 10.0F, 2.0F, 10.0F)
                        .uv(48, 52).cuboid(-3.0F, -14.0F, -3.0F, 6.0F, 2.0F, 6.0F).uv(6, 4).cuboid(-1.0F, -16.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData.addChild("orbBody",
                ModelPartBuilder.create().uv(0, 6).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F).uv(24, 52).cuboid(-3.0F, -8.0F, -3.0F, 6.0F, 4.0F, 6.0F).uv(40, 38)
                        .cuboid(-5.0F, -12.0F, -5.0F, 10.0F, 4.0F, 10.0F).uv(0, 0).cuboid(-7.0F, -18.0F, -7.0F, 14.0F, 6.0F, 14.0F).uv(0, 38).cuboid(-5.0F, -22.0F, -5.0F, 10.0F, 4.0F, 10.0F)
                        .uv(0, 52).cuboid(-3.0F, -26.0F, -3.0F, 6.0F, 4.0F, 6.0F).uv(0, 0).cuboid(-1.0F, -30.0F, -1.0F, 2.0F, 4.0F, 2.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body, this.orbBody);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.body.pivotY = 23.0F + MathHelper.cos(animationProgress * 0.1662F) * 0.6F;
        this.orbBody.pivotY = 23.0F + MathHelper.cos(animationProgress * 0.1662F) * 0.6F;
        if (entity.getDataTracker().get(VoidFragmentEntity.IS_VOID_ORB)) {
            orbBody.visible = true;
            body.visible = false;
        } else {
            orbBody.visible = false;
            body.visible = true;
        }
    }
}