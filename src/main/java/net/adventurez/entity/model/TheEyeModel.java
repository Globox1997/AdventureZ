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
    private final ModelPart left_site;
    private final ModelPart right_site;

    public TheEyeModel() {

        this.middle = (new ModelPart(this)).setTextureSize(32, 32);
        this.middle.setPivot(0.0F, 15.0F, 0.0F);
        this.middle.setTextureOffset(0, 0).addCuboid(-1.5F, -6.0F, -1.0F, 3.0F, 13.0F, 2.0F, 0.0F, false);

        this.left_site = (new ModelPart(this)).setTextureSize(32, 32);
        this.left_site.setPivot(0.0F, 15.0F, 0.0F);
        this.left_site.setTextureOffset(10, 10).addCuboid(-3.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);
        this.left_site.setTextureOffset(18, 18).addCuboid(-4.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F, 0.0F, false);
        this.left_site.setTextureOffset(10, 0).addCuboid(-5.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        this.left_site.setTextureOffset(8, 23).addCuboid(-6.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);

        this.right_site = (new ModelPart(this)).setTextureSize(32, 32);
        this.right_site.setPivot(0.0F, 15.0F, 0.0F);
        this.right_site.setTextureOffset(0, 15).addCuboid(1.5F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);
        this.right_site.setTextureOffset(16, 0).addCuboid(3.5F, -4.0F, -1.0F, 1.0F, 9.0F, 2.0F, 0.0F, false);
        this.right_site.setTextureOffset(20, 9).addCuboid(4.5F, -3.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        this.right_site.setTextureOffset(22, 0).addCuboid(5.5F, -1.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);

    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.middle, this.left_site, this.right_site);
    }

    @Override
    public void setAngles(T eye, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
        this.middle.pitch = headPitch * 0.017453292F;
        this.left_site.pitch = headPitch * 0.017453292F;
        this.right_site.pitch = headPitch * 0.017453292F;
        // Split

        if (eye.getHealth() < (eye.getMaxHealth() / 3) && !(eye.getDataTracker().get(TheEyeEntity.INVUL_TIMER) > 0)) {
            this.left_site.pivotX = -1.0F;
            this.right_site.pivotX = 1.0F;
        }

    }

}
