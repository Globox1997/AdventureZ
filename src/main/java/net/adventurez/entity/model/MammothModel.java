package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.MammothEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class MammothModel<T extends MammothEntity> extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart humpRight;
    private final ModelPart tail;
    private final ModelPart legBackLeft;
    private final ModelPart legBackRight;
    private final ModelPart legFrontLeft;
    private final ModelPart legFrontRight;
    private final ModelPart head;
    private final ModelPart headRight;
    private final ModelPart headLeft;
    private final ModelPart trunk;
    private final ModelPart trunk_r1;
    private final ModelPart trunkOne;
    private final ModelPart trunkOne_r1;
    private final ModelPart trunkTwo;
    private final ModelPart trunkTwo_r1;
    private final ModelPart tuskRight;
    private final ModelPart tuskTipRight_r1;
    private final ModelPart tuskMidRight_r1;
    private final ModelPart tuskBackRight_r1;
    private final ModelPart tuskFurOverlay_r1;
    private final ModelPart tuskLeft;
    private final ModelPart tuskTipLeft_r1;
    private final ModelPart tuskMidLeft_r1;
    private final ModelPart tuskBackLeft_r1;
    private final ModelPart tuskBaseLeft_r1;

    public MammothModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.humpRight = this.body.getChild("humpRight");
        this.legBackLeft = root.getChild("legBackLeft");
        this.legBackRight = root.getChild("legBackRight");
        this.legFrontLeft = root.getChild("legFrontLeft");
        this.legFrontRight = root.getChild("legFrontRight");
        this.head = root.getChild("head");
        this.tuskLeft = this.head.getChild("tuskLeft");
        this.tuskBaseLeft_r1 = this.tuskLeft.getChild("tuskBaseLeft_r1");
        this.tuskBackLeft_r1 = this.tuskLeft.getChild("tuskBackLeft_r1");
        this.tuskMidLeft_r1 = this.tuskLeft.getChild("tuskMidLeft_r1");
        this.tuskTipLeft_r1 = this.tuskLeft.getChild("tuskTipLeft_r1");
        this.tuskRight = this.head.getChild("tuskRight");
        this.tuskFurOverlay_r1 = this.tuskRight.getChild("tuskFurOverlay_r1");
        this.tuskBackRight_r1 = this.tuskRight.getChild("tuskBackRight_r1");
        this.tuskMidRight_r1 = this.tuskRight.getChild("tuskMidRight_r1");
        this.tuskTipRight_r1 = this.tuskRight.getChild("tuskTipRight_r1");
        this.trunk = this.head.getChild("trunk");
        this.trunkOne = this.trunk.getChild("trunkOne");
        this.trunkTwo = this.trunkOne.getChild("trunkTwo");
        this.trunkTwo_r1 = this.trunkTwo.getChild("trunkTwo_r1");
        this.trunkOne_r1 = this.trunkOne.getChild("trunkOne_r1");
        this.trunk_r1 = this.trunk.getChild("trunk_r1");
        this.headLeft = this.head.getChild("headLeft");
        this.headRight = this.head.getChild("headRight");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("body",
                ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -9.0F, -11.0F, 16.0F, 15.0F, 13.0F).uv(80, 0).cuboid(-7.0F, -6.0F, 2.0F, 14.0F, 12.0F, 10.0F).uv(0, 23)
                        .cuboid(-7.0F, 6.0F, 2.0F, 0.0F, 2.0F, 10.0F).uv(0, 15).cuboid(-8.0F, 6.0F, -11.0F, 0.0F, 3.0F, 13.0F).uv(0, 15).cuboid(8.0F, 6.0F, -11.0F, 0.0F, 3.0F, 13.0F).uv(0, 35)
                        .cuboid(-8.0F, 6.0F, -11.0F, 16.0F, 3.0F, 0.0F).uv(0, 23).cuboid(7.0F, 6.0F, 2.0F, 0.0F, 2.0F, 10.0F).uv(0, 31).cuboid(-7.0F, 6.0F, 12.0F, 14.0F, 2.0F, 0.0F),
                ModelTransform.pivot(0.0F, 7.0F, 0.0F));
        modelPartData1.addChild("humpRight", ModelPartBuilder.create().uv(45, 0).cuboid(-5.0F, 0.0F, -7.0F, 10.0F, 6.0F, 7.0F), ModelTransform.pivot(0.0F, -9.0F, -1.0F));
        modelPartData1.addChild("tail", ModelPartBuilder.create().uv(5, 5).cuboid(-1.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F), ModelTransform.pivot(0.0F, -1.0F, 12.0F));
        modelPartData.addChild("legBackLeft", ModelPartBuilder.create().uv(59, 14).cuboid(-3.0F, 0.0F, -3.0F, 5.0F, 11.0F, 5.0F), ModelTransform.pivot(-3.0F, 13.0F, 9.0F));
        modelPartData.addChild("legBackRight", ModelPartBuilder.create().uv(59, 14).cuboid(-2.0F, 0.0F, -3.0F, 5.0F, 11.0F, 5.0F), ModelTransform.pivot(3.0F, 13.0F, 9.0F));
        modelPartData.addChild("legFrontLeft", ModelPartBuilder.create().uv(59, 14).cuboid(-2.0F, 0.0F, -2.0F, 5.0F, 11.0F, 5.0F), ModelTransform.pivot(-5.0F, 13.0F, -8.0F));
        modelPartData.addChild("legFrontRight", ModelPartBuilder.create().uv(59, 14).cuboid(-2.0F, 0.0F, -2.0F, 5.0F, 11.0F, 5.0F), ModelTransform.pivot(4.0F, 13.0F, -8.0F));
        ModelPartData modelPartData2 = modelPartData.addChild("head", ModelPartBuilder.create().uv(92, 22).cuboid(-4.5F, -5.316F, -5.8794F, 9.0F, 12.0F, 9.0F),
                ModelTransform.pivot(0.0F, 1.0F, -11.0F));
        modelPartData2.addChild("headRight", ModelPartBuilder.create().uv(92, 23).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 6.0F, 1.0F), ModelTransform.pivot(-4.5F, -3.316F, -2.8794F));
        modelPartData2.addChild("headLeft", ModelPartBuilder.create().uv(92, 23).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 1.0F), ModelTransform.pivot(4.5F, -3.316F, -2.8794F));
        ModelPartData modelPartData3 = modelPartData2.addChild("trunk", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 6.684F, -3.8794F));
        modelPartData3.addChild("trunk_r1", ModelPartBuilder.create().uv(112, 43).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 8.0F, 4.0F), ModelTransform.pivot(0.0F, 0.5176F, -1.9319F));
        ModelPartData modelPartData4 = modelPartData3.addChild("trunkOne", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 6.4458F, 3.0681F));
        modelPartData4.addChild("trunkOne_r1", ModelPartBuilder.create().uv(100, 43).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 7.0F, 3.0F), ModelTransform.pivot(0.0F, 0.909F, -1.0834F));
        ModelPartData modelPartData5 = modelPartData4.addChild("trunkTwo", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.8588F, 4.8663F));
        modelPartData5.addChild("trunkTwo_r1", ModelPartBuilder.create().uv(92, 43).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 5.0F, 2.0F), ModelTransform.pivot(0.0F, 0.8609F, -1.1219F));
        ModelPartData modelPartData6 = modelPartData2.addChild("tuskRight", ModelPartBuilder.create(), ModelTransform.pivot(4.5F, 2.684F, -3.8794F));
        modelPartData6.addChild("tuskTipRight_r1", ModelPartBuilder.create().uv(102, 53).cuboid(-2.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F), ModelTransform.pivot(7.907F, 12.1641F, -1.8623F));
        modelPartData6.addChild("tuskMidRight_r1", ModelPartBuilder.create().uv(110, 55).cuboid(-2.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F), ModelTransform.pivot(5.3262F, 8.4783F, 3.5F));
        modelPartData6.addChild("tuskBackRight_r1", ModelPartBuilder.create().uv(118, 55).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 6.0F, 3.0F), ModelTransform.pivot(2.4583F, 4.3826F, 0.5F));
        modelPartData6.addChild("tuskFurOverlay_r1", ModelPartBuilder.create().uv(46, 28).cuboid(-3.0F, 4.0F, 0.0F, 3.0F, 2.0F, 4.0F).uv(32, 28).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 4.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData modelPartData7 = modelPartData2.addChild("tuskLeft", ModelPartBuilder.create(), ModelTransform.pivot(-5.5F, 1.684F, -3.8794F));
        modelPartData7.addChild("tuskTipLeft_r1", ModelPartBuilder.create().uv(102, 53).cuboid(0.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F), ModelTransform.pivot(-4.6746F, 14.3523F, -1.8623F));
        modelPartData7.addChild("tuskMidLeft_r1", ModelPartBuilder.create().uv(110, 55).cuboid(0.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F), ModelTransform.pivot(-2.773F, 10.2744F, 3.5F));
        modelPartData7.addChild("tuskBackLeft_r1", ModelPartBuilder.create().uv(118, 55).cuboid(0.0F, -1.0F, -3.0F, 2.0F, 6.0F, 3.0F), ModelTransform.pivot(-0.6599F, 5.7428F, 3.5F));
        modelPartData7.addChild("tuskBaseLeft_r1", ModelPartBuilder.create().uv(46, 28).cuboid(0.0F, 4.0F, 0.0F, 3.0F, 2.0F, 4.0F).uv(32, 28).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 4.0F),
                ModelTransform.pivot(1.0F, 1.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.legBackLeft, this.legBackRight, this.legFrontLeft, this.legFrontRight);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.007453292F;
        this.head.pitch = headPitch * 0.007453292F;
        this.humpRight.pitch = -0.6981F;
        this.tail.pitch = 0.5236F;
        this.head.pitch = -0.3491F;
        this.headRight.yaw = 0.6109F;
        this.headLeft.yaw = -0.6109F;
        this.trunk.pitch = -0.2618F;
        this.trunk_r1.pitch = 0.5236F;
        this.trunkOne.pitch = 0.0873F;
        this.trunkOne_r1.pitch = 0.7854F;
        this.trunkTwo.pitch = 0.1309F;
        this.trunkTwo_r1.pitch = 1.309F;
        this.tuskRight.roll = 0.1745F;
        this.tuskTipRight_r1.pitch = -1.7453F;
        this.tuskTipRight_r1.roll = -0.6109F;
        this.tuskMidRight_r1.pitch = -0.8727F;
        this.tuskMidRight_r1.roll = -0.6109F;
        this.tuskBackRight_r1.roll = -0.6109F;
        this.tuskFurOverlay_r1.roll = -0.6109F;
        this.tuskTipLeft_r1.pitch = -1.7453F;
        this.tuskTipLeft_r1.roll = 0.4363F;
        this.tuskMidLeft_r1.pitch = -0.8727F;
        this.tuskMidLeft_r1.roll = 0.4363F;
        this.tuskBackLeft_r1.roll = 0.4363F;
        this.tuskBaseLeft_r1.roll = 0.4363F;
        float slowerWalking = 0.6662F;
        if (entity.isBaby()) {
            slowerWalking = 0.1F;
        }
        this.legBackRight.pitch = MathHelper.cos(limbAngle * slowerWalking + 3.1415927F) * 0.7F * limbDistance;
        this.legBackLeft.pitch = MathHelper.cos(limbAngle * slowerWalking) * 0.7F * limbDistance;
        this.legFrontLeft.pitch = MathHelper.cos(limbAngle * slowerWalking + 3.1415927F) * 0.7F * limbDistance;
        this.legFrontRight.pitch = MathHelper.cos(limbAngle * slowerWalking) * 0.7F * limbDistance;

        int i = entity.getDataTracker().get(MammothEntity.ATTACK_TICKS);
        if (i > 0) {
            this.head.pitch = -0.3491F + MathHelper.sin((3.14159265358F / 10F) * ((float) i - 9)) * 0.4F;
        }

    }

}
