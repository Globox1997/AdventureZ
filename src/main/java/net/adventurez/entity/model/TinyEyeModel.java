package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;

@Environment(EnvType.CLIENT)
public class TinyEyeModel<T extends Entity> extends CompositeEntityModel<T> {
    private final ModelPart body;

    public TinyEyeModel() {
        this.body = (new ModelPart(this)).setTextureSize(32, 32);
        this.body.setPivot(0.0F, 15.0F, 0.0F);
        this.body.setTextureOffset(10, 10).addCuboid(-3.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F);
        this.body.setTextureOffset(0, 0).addCuboid(-1.5F, -6.0F, -1.0F, 3.0F, 13.0F, 2.0F, 0.0F);
        this.body.setTextureOffset(18, 18).addCuboid(-4.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F, 0.0F);
        this.body.setTextureOffset(10, 0).addCuboid(-5.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F);
        this.body.setTextureOffset(8, 23).addCuboid(-6.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F);
        this.body.setTextureOffset(0, 15).addCuboid(1.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F);
        this.body.setTextureOffset(16, 0).addCuboid(3.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F, 0.0F);
        this.body.setTextureOffset(20, 9).addCuboid(4.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F);
        this.body.setTextureOffset(22, 0).addCuboid(5.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F);
    }

    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body);
    }

    public void setAngles(T bullet, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
        this.body.yaw = headYaw * 0.017453292F;
    }
}