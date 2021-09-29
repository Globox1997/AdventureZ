package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.IguanaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class IguanaModel<T extends IguanaEntity> extends AnimalModel<T> {
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart cube_r1;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart left_middle;
    private final ModelPart left_front;
    private final ModelPart left_back;
    private final ModelPart right_middle;
    private final ModelPart right_front;
    private final ModelPart right_back;

    public IguanaModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.cube_r1 = this.tail.getChild("cube_r1");
        this.head = root.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.left_middle = root.getChild("left_middle");
        this.left_front = root.getChild("left_front");
        this.left_back = root.getChild("left_back");
        this.right_middle = root.getChild("right_middle");
        this.right_front = root.getChild("right_front");
        this.right_back = root.getChild("right_back");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData modelPartData1 = modelPartData.addChild("body",
                ModelPartBuilder.create().uv(0, 0).cuboid(-5.5F, -3.5F, -18.0F, 11.0F, 6.0F, 15.0F).uv(0, 0).cuboid(-5.5F, -3.5F, -18.0F, 11.0F, 6.0F, 15.0F).uv(37, 0)
                        .cuboid(-4.5F, -2.5F, -3.0F, 9.0F, 5.0F, 6.0F).uv(0, 20).cuboid(0.0F, -6.0F, -17.0F, 0.0F, 3.0F, 13.0F).uv(0, 0).cuboid(0.0F, -5.0F, -1.0F, 0.0F, 3.0F, 3.0F),
                ModelTransform.pivot(0.0F, 20.5F, 10.0F));
        ModelPartData modelPartData2 = modelPartData1.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.5F, 2.5F));
        modelPartData2.addChild("cube_r1", ModelPartBuilder.create().uv(26, 23).cuboid(-3.5F, -2.5F, -0.5F, 7.0F, 4.0F, 10.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData modelPartData3 = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 21).cuboid(-4.5F, -3.5F, -8.5F, 9.0F, 3.0F, 9.0F), ModelTransform.pivot(0.0F, 21.5F, -7.5F));
        modelPartData3.addChild("jaw", ModelPartBuilder.create().uv(0, 37).cuboid(-4.5F, -0.5F, -8.5F, 9.0F, 2.0F, 9.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild("left_middle", ModelPartBuilder.create().uv(27, 37).cuboid(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F), ModelTransform.pivot(7.5F, 19.5F, 4.0F));
        modelPartData.addChild("left_front", ModelPartBuilder.create().uv(27, 37).cuboid(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F), ModelTransform.pivot(7.5F, 19.5F, -5.0F));
        modelPartData.addChild("left_back", ModelPartBuilder.create().uv(27, 37).cuboid(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F), ModelTransform.pivot(6.5F, 19.5F, 10.0F));
        modelPartData.addChild("right_middle", ModelPartBuilder.create().uv(27, 37).cuboid(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, true), ModelTransform.pivot(-7.5F, 19.5F, 4.0F));
        modelPartData.addChild("right_front", ModelPartBuilder.create().uv(27, 37).cuboid(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, true), ModelTransform.pivot(-7.5F, 19.5F, -5.0F));
        modelPartData.addChild("right_back", ModelPartBuilder.create().uv(27, 37).cuboid(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, true), ModelTransform.pivot(-6.5F, 19.5F, 10.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.cube_r1.pitch = -0.1745F;
        this.head.pitch = headPitch * 0.007453292F;
        this.head.yaw = headYaw * 0.0017453292F;
        this.right_back.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.left_back.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.right_front.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_front.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        this.right_middle.pitch = MathHelper.cos(limbAngle * 0.6662F + 4.7123889803F) * 1.4F * limbDistance;
        this.left_middle.pitch = MathHelper.cos(limbAngle * 0.6662F + 1.57079632F) * 1.4F * limbDistance;

        if (entity.getDataTracker().get(IguanaEntity.OPEN_MOUTH)) {
            this.jaw.pitch = 0.3F;
        } else
            this.jaw.pitch = 0.0F;
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.head, this.body, this.left_middle, this.left_front, this.left_back, this.right_middle, this.right_front, this.right_back);
    }

}
