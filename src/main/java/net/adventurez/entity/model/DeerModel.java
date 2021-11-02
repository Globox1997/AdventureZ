package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.MathHelper;

public class DeerModel<T extends AnimalEntity> extends AnimalModel<T> {
    private final ModelPart root;
    private final ModelPart root_r1;
    private final ModelPart neck;
    private final ModelPart neck_r1;
    private final ModelPart head;
    private final ModelPart head_r1;
    private final ModelPart head_r2;
    private final ModelPart head_r3;
    private final ModelPart head_r4;
    private final ModelPart head_r5;
    private final ModelPart antlers;
    private final ModelPart head_r6;
    private final ModelPart head_r7;
    private final ModelPart leg0;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg1;

    public DeerModel(ModelPart root) {
        super(true, 17.0F, 4.0F);
        this.root = root.getChild("root");
        this.leg1 = this.root.getChild("leg1");
        this.leg3 = this.root.getChild("leg3");
        this.leg2 = this.root.getChild("leg2");
        this.leg0 = this.root.getChild("leg0");
        this.neck = this.root.getChild("neck");
        this.head = root.getChild("head");
        this.antlers = this.head.getChild("antlers");
        this.head_r7 = this.antlers.getChild("head_r7");
        this.head_r6 = this.antlers.getChild("head_r6");
        this.head_r5 = this.head.getChild("head_r5");
        this.head_r4 = this.head.getChild("head_r4");
        this.head_r3 = this.head.getChild("head_r3");
        this.head_r2 = this.head.getChild("head_r2");
        this.head_r1 = this.head.getChild("head_r1");
        this.neck_r1 = this.neck.getChild("neck_r1");
        this.root_r1 = this.root.getChild("root_r1");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -12.0F, -12.0F, 12.0F, 10.0F, 22.0F), ModelTransform.pivot(0.0F, 14.0F, 0.0F));
        modelPartData1.addChild("root_r1", ModelPartBuilder.create().uv(60, 7).cuboid(-6.0F, -7.0F, 13.5F, 12.0F, 10.0F, 0.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData modelPartData2 = modelPartData1.addChild("neck", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, -11.0F));
        modelPartData2.addChild("neck_r1",
                ModelPartBuilder.create().uv(0, 53).cuboid(0.0F, -15.0F, -4.0F, 0.0F, 18.0F, 5.0F).uv(0, 32).cuboid(-3.0F, -15.0F, -2.0F, 6.0F, 15.0F, 8.0F, new Dilation(-0.9F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData1.addChild("leg0", ModelPartBuilder.create().uv(64, 28).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(-4.0F, -2.0F, 7.0F));
        modelPartData1.addChild("leg2", ModelPartBuilder.create().uv(56, 61).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(-4.0F, -2.0F, -9.0F));
        modelPartData1.addChild("leg3", ModelPartBuilder.create().uv(56, 45).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(4.0F, -2.0F, -9.0F));
        modelPartData1.addChild("leg1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(4.0F, -2.0F, 7.0F));
        ModelPartData modelPartData3 = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, -14.0F));
        modelPartData3.addChild("head_r1", ModelPartBuilder.create().uv(46, 10).cuboid(-2.0F, -2.1F, -11.0F, 4.0F, 4.0F, 5.0F).uv(28, 32).cuboid(-3.0F, -5.0F, -6.0F, 6.0F, 7.0F, 10.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData3.addChild("head_r2", ModelPartBuilder.create().uv(20, 32).cuboid(-3.0F, -4.0F, 4.0F, 1.0F, 3.0F, 5.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData3.addChild("head_r3", ModelPartBuilder.create().uv(14, 14).cuboid(-2.0F, -7.0F, 1.0F, 1.0F, 4.0F, 2.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData3.addChild("head_r4", ModelPartBuilder.create().uv(0, 16).cuboid(1.0F, -7.0F, 1.0F, 1.0F, 4.0F, 2.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData3.addChild("head_r5", ModelPartBuilder.create().uv(50, 32).cuboid(2.0F, -4.0F, 4.0F, 1.0F, 3.0F, 5.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData modelPartData4 = modelPartData3.addChild("antlers", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData4.addChild("head_r6", ModelPartBuilder.create().uv(28, 40).cuboid(-0.25F, -21.0F, -0.5F, 0.0F, 16.0F, 9.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData4.addChild("head_r7", ModelPartBuilder.create().uv(28, 40).cuboid(0.25F, -21.0F, -0.5F, 0.0F, 16.0F, 9.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.root);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;
        this.root_r1.pitch = 0.3927F;
        this.neck_r1.pitch = 0.3491F;
        this.head_r1.pitch = 0.0873F;
        this.head_r2.pitch = 0.5236F;
        this.head_r2.yaw = -0.3927F;
        this.head_r3.pitch = 0.5236F;
        this.head_r3.yaw = 0.2182F;
        this.head_r4.pitch = 0.5236F;
        this.head_r4.yaw = -0.2182F;
        this.head_r5.pitch = 0.5236F;
        this.head_r5.yaw = 0.3927F;
        this.head_r6.pitch = 0.5236F;
        this.head_r6.yaw = -0.2182F;
        this.head_r6.roll = 0.2618F;
        this.head_r7.pitch = 0.5236F;
        this.head_r7.yaw = 0.2182F;
        this.head_r7.roll = -0.2618F;
        this.leg0.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leg1.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg2.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg3.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        if (entity.isBaby())
            this.antlers.visible = false;
        else
            this.antlers.visible = true;
    }

}
