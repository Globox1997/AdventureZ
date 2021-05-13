package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.TheEyeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

@Environment(EnvType.CLIENT)
public class TheEyeModel<T extends TheEyeEntity> extends CompositeEntityModel<T> {
    private final ModelPart middle;
    private final ModelPart leftSite;
    private final ModelPart rightSite;

    public TheEyeModel() {

        this.middle = (new ModelPart(this)).setTextureSize(32, 32);
        this.middle.setPivot(0.0F, 15.0F, 0.0F);
        this.middle.setTextureOffset(0, 0).addCuboid(-1.5F, -6.0F, -1.0F, 3.0F, 13.0F, 2.0F, 0.0F, false);

        this.leftSite = (new ModelPart(this)).setTextureSize(32, 32);
        this.leftSite.setPivot(0.0F, 15.0F, 0.0F);
        this.leftSite.setTextureOffset(10, 10).addCuboid(-3.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);
        this.leftSite.setTextureOffset(18, 18).addCuboid(-4.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F, 0.0F, false);
        this.leftSite.setTextureOffset(10, 0).addCuboid(-5.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        this.leftSite.setTextureOffset(8, 23).addCuboid(-6.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);

        this.rightSite = (new ModelPart(this)).setTextureSize(32, 32);
        this.rightSite.setPivot(0.0F, 15.0F, 0.0F);
        this.rightSite.setTextureOffset(0, 15).addCuboid(1.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);
        this.rightSite.setTextureOffset(16, 0).addCuboid(3.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F, 0.0F, false);
        this.rightSite.setTextureOffset(20, 9).addCuboid(4.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        this.rightSite.setTextureOffset(22, 0).addCuboid(5.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);

    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.middle, this.leftSite, this.rightSite);
    }

    @Override
    public void setAngles(T eye, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
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
