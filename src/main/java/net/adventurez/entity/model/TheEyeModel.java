package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.TheEyeEntity;
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
public class TheEyeModel<T extends TheEyeEntity> extends CompositeEntityModel<T> {
    private final ModelPart middle;
    private final ModelPart leftSite;
    private final ModelPart rightSite;

    public TheEyeModel(ModelPart root) {
        this.middle = root.getChild("middle");
        this.leftSite = root.getChild("leftSite");
        this.rightSite = root.getChild("rightSite");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("middle", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -6.0F, -1.0F, 3.0F, 13.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, 0.0F));
        modelPartData.addChild("leftSite", ModelPartBuilder.create().uv(10, 10).cuboid(-3.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F).uv(18, 18).cuboid(-4.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F).uv(10, 0)
                .cuboid(-5.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F).uv(8, 23).cuboid(-6.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, 0.0F));
        modelPartData.addChild("rightSite", ModelPartBuilder.create().uv(0, 15).cuboid(1.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F).uv(16, 0).cuboid(3.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F).uv(20, 9)
                .cuboid(4.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F).uv(22, 0).cuboid(5.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.middle, this.leftSite, this.rightSite);
    }

    @Override
    public void setAngles(T eye, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.middle.pitch = headPitch * 0.017453292F;
        this.leftSite.pitch = headPitch * 0.017453292F;
        this.rightSite.pitch = headPitch * 0.017453292F;
        // Split

        if (eye.getHealth() < (eye.getMaxHealth() / 3) && !(eye.getDataTracker().get(TheEyeEntity.INVUL_TIMER) > 0)) {
            this.leftSite.pivotX = -1.0F;
            this.rightSite.pivotX = 1.0F;
        } else {
            this.leftSite.pivotX = 0F;
            this.rightSite.pivotX = 0F;
        }

    }

}
