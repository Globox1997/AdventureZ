package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;

@Environment(EnvType.CLIENT)
public class VoidBulletModel<T extends Entity> extends CompositeEntityModel<T> {
    private final ModelPart body;

    public VoidBulletModel() {
        this.body = (new ModelPart(this)).setTextureSize(32, 32);
        this.body.setPivot(0.0F, 21.0F, 0.0F);
        this.body.setTextureOffset(18, 20).addCuboid(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        this.body.setTextureOffset(12, 20).addCuboid(-1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        this.body.setTextureOffset(16, 5).addCuboid(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.body.setTextureOffset(15, 15).addCuboid(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 1.0F, 0.0F, false);
        this.body.setTextureOffset(16, 12).addCuboid(-2.0F, -3.0F, -2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        this.body.setTextureOffset(16, 10).addCuboid(-2.0F, 2.0F, -2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        this.body.setTextureOffset(0, 16).addCuboid(-2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
        this.body.setTextureOffset(0, 0).addCuboid(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 2.0F, 0.0F, false);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setAngles(T bullet, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
        this.body.yaw = headYaw * 0.017453292F;
        this.body.pitch = headPitch * 0.017453292F;
    }
}