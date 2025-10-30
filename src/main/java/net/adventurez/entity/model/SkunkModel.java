package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class SkunkModel<T extends AnimalEntity> extends AnimalModel<T> {
    private final ModelPart body;
    private final ModelPart leftFrontLeg;
    private final ModelPart leftBackLeg;
    private final ModelPart rightBackLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart tail;
    private final ModelPart head;

    public SkunkModel(ModelPart root) {
        super(true, 10.0F, 4.0F);
        this.body = root.getChild("body");
        this.leftFrontLeg = this.body.getChild("leftFrontLeg");
        this.leftBackLeg = this.body.getChild("leftBackLeg");
        this.rightBackLeg = this.body.getChild("rightBackLeg");
        this.rightFrontLeg = this.body.getChild("rightFrontLeg");
        this.tail = this.body.getChild("tail");
        this.head = root.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData main_r1 = body.addChild("main_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.0F, -7.5F, 7.0F, 6.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 1.0F, 0.5F, 0.1745F, 0.0F, 0.0F));

        ModelPartData leftFrontLeg = body.addChild("leftFrontLeg", ModelPartBuilder.create().uv(0, 33).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 2.0F, -5.0F));

        ModelPartData leftBackLeg = body.addChild("leftBackLeg", ModelPartBuilder.create().uv(30, 27).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 1.0F, 3.0F));

        ModelPartData rightBackLeg = body.addChild("rightBackLeg", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 1.0F, 3.0F));

        ModelPartData rightFrontLeg = body.addChild("rightFrontLeg", ModelPartBuilder.create().uv(0, 4).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 2.0F, -5.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(0, 18).cuboid(-1.5F, -1.75F, 0.0F, 3.0F, 3.0F, 12.0F, new Dilation(0.0F))
                .uv(26, 0).cuboid(-1.5F, 1.25F, 5.0F, 3.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 4.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(18, 18).cuboid(-3.0F, -2.0F, -5.0F, 6.0F, 4.0F, 5.0F, new Dilation(0.0F))
                .uv(8, 6).cuboid(2.0F, -3.0F, -1.5F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(6, 4).cuboid(-4.0F, -3.0F, -1.5F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.0F, 0.0F, -7.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.75F, -7.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
//        this.root. = 10f;
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;

        this.leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftBackLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.rightBackLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }

}
