package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;
import net.adventurez.entity.CrystalBeetleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CrystalBeetleModel<T extends CrystalBeetleEntity> extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart right_front_leg;
    private final ModelPart right_back_leg;
    private final ModelPart left_back_leg;
    private final ModelPart left_front_leg;
    private final ModelPart body_spike;
    private final ModelPart head;

    public CrystalBeetleModel(ModelPart root) {
        this.body = root.getChild("body");
        this.right_front_leg = body.getChild("right_front_leg");
        this.right_back_leg = body.getChild("right_back_leg");
        this.left_back_leg = body.getChild("left_back_leg");
        this.left_front_leg = body.getChild("left_front_leg");
        this.body_spike = body.getChild("body_spike");
        this.head = body.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.05F, -5.0F, -5.5F, 16.0F, 10.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.05F, 19.0F, 3.5F));

        ModelPartData right_front_leg = body.addChild("right_front_leg", ModelPartBuilder.create().uv(38, 39).cuboid(-4.0F, -0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.05F, -1.5F, -3.5F));

        ModelPartData right_back_leg = body.addChild("right_back_leg", ModelPartBuilder.create().uv(38, 39).cuboid(-4.0F, -0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.05F, -1.5F, 5.5F));

        ModelPartData left_back_leg = body.addChild("left_back_leg", ModelPartBuilder.create().uv(38, 39).mirrored().cuboid(0.0F, -0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(7.95F, -1.5F, 5.5F));

        ModelPartData left_front_leg = body.addChild("left_front_leg", ModelPartBuilder.create().uv(38, 39).mirrored().cuboid(0.0F, -0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(7.95F, -1.5F, -3.5F));

        ModelPartData body_spike = body.addChild("body_spike", ModelPartBuilder.create().uv(40, 23).cuboid(-1.0F, -9.0F, 3.0F, 3.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.45F, -2.0F, -6.5F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 23).cuboid(-5.6F, -4.0F, -8.0F, 12.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 39).cuboid(-1.0F, -4.0F, -16.0F, 3.0F, 5.0F, 8.0F, new Dilation(0.0F))
                .uv(22, 39).cuboid(-1.0F, -10.0F, -16.0F, 3.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.45F, 1.0F, -5.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.012453292F;
        this.head.pitch = headPitch * 0.014453292F;

        this.right_front_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.right_back_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;
        this.left_front_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.left_back_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;
    }

}
