package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.MammothEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
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

    public MammothModel() {
        body = (new ModelPart(this)).setTextureSize(128, 128);
        body.setPivot(0.0F, 7.0F, 0.0F);
        body.setTextureOffset(0, 0).addCuboid(-8.0F, -9.0F, -11.0F, 16.0F, 15.0F, 13.0F, 0.0F, false);
        body.setTextureOffset(80, 0).addCuboid(-7.0F, -6.0F, 2.0F, 14.0F, 12.0F, 10.0F, 0.0F, false);
        body.setTextureOffset(0, 23).addCuboid(-7.0F, 6.0F, 2.0F, 0.0F, 2.0F, 10.0F, 0.0F, false);
        body.setTextureOffset(0, 15).addCuboid(-8.0F, 6.0F, -11.0F, 0.0F, 3.0F, 13.0F, 0.0F, false);
        body.setTextureOffset(0, 15).addCuboid(8.0F, 6.0F, -11.0F, 0.0F, 3.0F, 13.0F, 0.0F, false);
        body.setTextureOffset(0, 35).addCuboid(-8.0F, 6.0F, -11.0F, 16.0F, 3.0F, 0.0F, 0.0F, false);
        body.setTextureOffset(0, 23).addCuboid(7.0F, 6.0F, 2.0F, 0.0F, 2.0F, 10.0F, 0.0F, false);
        body.setTextureOffset(0, 31).addCuboid(-7.0F, 6.0F, 12.0F, 14.0F, 2.0F, 0.0F, 0.0F, false);

        humpRight = (new ModelPart(this)).setTextureSize(128, 128);
        humpRight.setPivot(0.0F, -9.0F, -1.0F);
        body.addChild(humpRight);
        humpRight.setTextureOffset(45, 0).addCuboid(-5.0F, 0.0F, -7.0F, 10.0F, 6.0F, 7.0F, 0.0F, false);

        tail = (new ModelPart(this)).setTextureSize(128, 128);
        tail.setPivot(0.0F, -1.0F, 12.0F);
        body.addChild(tail);
        tail.setTextureOffset(5, 5).addCuboid(-1.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        legBackLeft = (new ModelPart(this)).setTextureSize(128, 128);
        legBackLeft.setPivot(-3.0F, 13.0F, 9.0F);
        legBackLeft.setTextureOffset(59, 14).addCuboid(-3.0F, 0.0F, -3.0F, 5.0F, 11.0F, 5.0F, 0.0F, false);

        legBackRight = (new ModelPart(this)).setTextureSize(128, 128);
        legBackRight.setPivot(3.0F, 13.0F, 9.0F);
        legBackRight.setTextureOffset(59, 14).addCuboid(-2.0F, 0.0F, -3.0F, 5.0F, 11.0F, 5.0F, 0.0F, false);

        legFrontLeft = (new ModelPart(this)).setTextureSize(128, 128);
        legFrontLeft.setPivot(-5.0F, 13.0F, -8.0F);
        legFrontLeft.setTextureOffset(59, 14).addCuboid(-2.0F, 0.0F, -2.0F, 5.0F, 11.0F, 5.0F, 0.0F, false);

        legFrontRight = (new ModelPart(this)).setTextureSize(128, 128);
        legFrontRight.setPivot(4.0F, 13.0F, -8.0F);
        legFrontRight.setTextureOffset(59, 14).addCuboid(-2.0F, 0.0F, -2.0F, 5.0F, 11.0F, 5.0F, 0.0F, false);

        head = (new ModelPart(this)).setTextureSize(128, 128);
        head.setPivot(0.0F, 1.0F, -11.0F);
        head.setTextureOffset(92, 22).addCuboid(-4.5F, -5.316F, -5.8794F, 9.0F, 12.0F, 9.0F, 0.0F, false);

        headRight = (new ModelPart(this)).setTextureSize(128, 128);
        headRight.setPivot(-4.5F, -3.316F, -2.8794F);
        head.addChild(headRight);
        headRight.setTextureOffset(92, 23).addCuboid(-3.0F, 0.0F, 0.0F, 3.0F, 6.0F, 1.0F, 0.0F, false);

        headLeft = (new ModelPart(this)).setTextureSize(128, 128);
        headLeft.setPivot(4.5F, -3.316F, -2.8794F);
        head.addChild(headLeft);
        headLeft.setTextureOffset(92, 23).addCuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 1.0F, 0.0F, false);

        trunk = (new ModelPart(this)).setTextureSize(128, 128);
        trunk.setPivot(0.0F, 6.684F, -3.8794F);
        head.addChild(trunk);

        trunk_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        trunk_r1.setPivot(0.0F, 0.5176F, -1.9319F);
        trunk.addChild(trunk_r1);
        trunk_r1.setTextureOffset(112, 43).addCuboid(-2.0F, 0.0F, 0.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);

        trunkOne = (new ModelPart(this)).setTextureSize(128, 128);
        trunkOne.setPivot(0.0F, 6.4458F, 3.0681F);
        trunk.addChild(trunkOne);

        trunkOne_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        trunkOne_r1.setPivot(0.0F, 0.909F, -1.0834F);
        trunkOne.addChild(trunkOne_r1);
        trunkOne_r1.setTextureOffset(100, 43).addCuboid(-1.5F, 0.0F, 0.0F, 3.0F, 7.0F, 3.0F, 0.0F, false);

        trunkTwo = (new ModelPart(this)).setTextureSize(128, 128);
        trunkTwo.setPivot(0.0F, 4.8588F, 4.8663F);
        trunkOne.addChild(trunkTwo);

        trunkTwo_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        trunkTwo_r1.setPivot(0.0F, 0.8609F, -1.1219F);
        trunkTwo.addChild(trunkTwo_r1);
        trunkTwo_r1.setTextureOffset(92, 43).addCuboid(-1.0F, 0.0F, 0.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

        tuskRight = (new ModelPart(this)).setTextureSize(128, 128);
        tuskRight.setPivot(4.5F, 2.684F, -3.8794F);
        head.addChild(tuskRight);

        tuskTipRight_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        tuskTipRight_r1.setPivot(7.907F, 12.1641F, -1.8623F);
        tuskRight.addChild(tuskTipRight_r1);
        tuskTipRight_r1.setTextureOffset(102, 53).addCuboid(-2.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        tuskMidRight_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        tuskMidRight_r1.setPivot(5.3262F, 8.4783F, 3.5F);
        tuskRight.addChild(tuskMidRight_r1);
        tuskMidRight_r1.setTextureOffset(110, 55).addCuboid(-2.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        tuskBackRight_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        tuskBackRight_r1.setPivot(2.4583F, 4.3826F, 0.5F);
        tuskRight.addChild(tuskBackRight_r1);
        tuskBackRight_r1.setTextureOffset(118, 55).addCuboid(-2.0F, -1.0F, 0.0F, 2.0F, 6.0F, 3.0F, 0.0F, false);

        tuskFurOverlay_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        tuskFurOverlay_r1.setPivot(0.0F, 0.0F, 0.0F);
        tuskRight.addChild(tuskFurOverlay_r1);
        tuskFurOverlay_r1.setTextureOffset(46, 28).addCuboid(-3.0F, 4.0F, 0.0F, 3.0F, 2.0F, 4.0F, 0.0F, false);
        tuskFurOverlay_r1.setTextureOffset(32, 28).addCuboid(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);

        tuskLeft = (new ModelPart(this)).setTextureSize(128, 128);
        tuskLeft.setPivot(-5.5F, 1.684F, -3.8794F);
        head.addChild(tuskLeft);

        tuskTipLeft_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        tuskTipLeft_r1.setPivot(-4.6746F, 14.3523F, -1.8623F);
        tuskLeft.addChild(tuskTipLeft_r1);
        tuskTipLeft_r1.setTextureOffset(102, 53).addCuboid(0.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        tuskMidLeft_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        tuskMidLeft_r1.setPivot(-2.773F, 10.2744F, 3.5F);
        tuskLeft.addChild(tuskMidLeft_r1);
        tuskMidLeft_r1.setTextureOffset(110, 55).addCuboid(0.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        tuskBackLeft_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        tuskBackLeft_r1.setPivot(-0.6599F, 5.7428F, 3.5F);
        tuskLeft.addChild(tuskBackLeft_r1);
        tuskBackLeft_r1.setTextureOffset(118, 55).addCuboid(0.0F, -1.0F, -3.0F, 2.0F, 6.0F, 3.0F, 0.0F, false);

        tuskBaseLeft_r1 = (new ModelPart(this)).setTextureSize(128, 128);
        tuskBaseLeft_r1.setPivot(1.0F, 1.0F, 0.0F);
        tuskLeft.addChild(tuskBaseLeft_r1);
        tuskBaseLeft_r1.setTextureOffset(46, 28).addCuboid(0.0F, 4.0F, 0.0F, 3.0F, 2.0F, 4.0F, 0.0F, false);
        tuskBaseLeft_r1.setTextureOffset(32, 28).addCuboid(0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);

    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.legBackLeft, this.legBackRight, this.legFrontLeft,
                this.legFrontRight);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
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
