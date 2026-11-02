package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;
import net.adventurez.entity.TerracottaGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class TerracottaGolemModel<T extends TerracottaGolemEntity> extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart block;
    private final ModelPart head;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart right_leg;
    private final ModelPart left_leg;

    public TerracottaGolemModel(ModelPart root) {
        this.body = root.getChild("body");
        this.block = root.getChild("block");
        this.head = body.getChild("head");
        this.right_arm = body.getChild("right_arm");
        this.left_arm = body.getChild("left_arm");
        this.right_leg = body.getChild("right_leg");
        this.left_leg = body.getChild("left_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData block = modelPartData.addChild("block", ModelPartBuilder.create().uv(112, 0).cuboid(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-15.0F, -23.0F, -14.0F, 31.0F, 23.0F, 25.0F, new Dilation(0.0F))
                .uv(72, 69).cuboid(2.0F, -26.0F, -8.0F, 9.0F, 3.0F, 9.0F, new Dilation(0.0F))
                .uv(64, 96).cuboid(-10.0F, 0.0F, -6.0F, 22.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

        ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(87, 0).cuboid(-12.0F, -38.0F, -4.0F, 10.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.6109F, 0.0F));

        ModelPartData cube_r2 = body.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -41.0F, -7.0F, 10.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.5672F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(72, 48).cuboid(-7.0F, -6.0F, -9.0F, 16.0F, 12.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.0F, -14.0F));

        ModelPartData right_arm = body.addChild("right_arm", ModelPartBuilder.create().uv(0, 48).cuboid(-8.0F, -6.0F, -4.0F, 9.0F, 39.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(-16.0F, -15.0F, -3.0F));

        ModelPartData left_arm = body.addChild("left_arm", ModelPartBuilder.create().uv(36, 48).cuboid(0.0F, -6.0F, -4.0F, 9.0F, 39.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 122).cuboid(1.0F, -3.0F, -5.0F, 7.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(16, 122).cuboid(6.0F, -7.0F, -5.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(16.0F, -15.0F, -3.0F));

        ModelPartData right_leg = body.addChild("right_leg", ModelPartBuilder.create().uv(0, 96).cuboid(-5.0F, 0.0F, -1.0F, 8.0F, 18.0F, 8.0F, new Dilation(0.0F))
                .uv(64, 96).cuboid(-4.0F, 2.0F, -2.0F, 5.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 9.0F, -3.0F));

        ModelPartData left_leg = body.addChild("left_leg", ModelPartBuilder.create().uv(32, 96).cuboid(-5.0F, 0.0F, -1.0F, 8.0F, 18.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(11.0F, 9.0F, -3.0F));
        return TexturedModelData.of(modelData, 256, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body, this.block);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.0039453292F;
        this.head.pitch = headPitch * 0.0027453292F;
        this.right_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.right_arm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
        this.left_arm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
        float k = MathHelper.sin(entity.handSwingProgress * 3.1415927F);
        if (k > 0) {
            this.right_arm.pitch = -k;
        }
        boolean isBlock = entity.getDataTracker().get(TerracottaGolemEntity.BLOCK_VARIANT);
        this.body.visible = !isBlock;
        this.block.visible = isBlock;
    }

}
