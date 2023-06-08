package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.DragonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class DragonModel<T extends DragonEntity> extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart chest;
    private final ModelPart spike;
    private final ModelPart wingLeft;
    private final ModelPart wingTipLeft;
    private final ModelPart wingRight;
    private final ModelPart wingTipRight;
    private final ModelPart rearLegRight;
    private final ModelPart rearLegRightTip;
    private final ModelPart rearLegRightTip_r1;
    private final ModelPart rearFootRight;
    private final ModelPart rearLegLeft;
    private final ModelPart rearLegLefttip;
    private final ModelPart rearLegLefttip_r1;
    private final ModelPart rearFootLeft;
    private final ModelPart frontLegRight;
    private final ModelPart frontLegRight_r1;
    private final ModelPart frontLegRighttip;
    private final ModelPart frontLegRighttip_r1;
    private final ModelPart frontFootRight;
    private final ModelPart frontLegLeft;
    private final ModelPart frontLegLeft_r1;
    private final ModelPart frontLegLefttip;
    private final ModelPart frontLegLefttip_r1;
    private final ModelPart frontFootLeft;
    private final ModelPart neck;
    private final ModelPart neck2;
    private final ModelPart neck3;
    private final ModelPart neck4;
    private final ModelPart head;
    private final ModelPart otherEars;
    private final ModelPart otherEars_r1;
    private final ModelPart otherEars_r2;
    private final ModelPart ears;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart tail5;
    private final ModelPart tail6;
    private final ModelPart otherTail;
    private final ModelPart otherTail_r1;
    private final ModelPart otherTail_r2;
    private final ModelPart otherTail_r3;
    private final ModelPart otherTail_r4;
    private final ModelPart extraTail;
    private final ModelPart tail7;

    private float betweenFloater;
    private float startFlyingTicker;
    private boolean endFlying;
    private int endFlyingTicker;
    private boolean randomYawFire;
    private int randomYawFireTick;

    public DragonModel(ModelPart root) {
        this.body = root.getChild("body");
        this.spike = this.body.getChild("spike");
        this.chest = this.body.getChild("chest");
        this.wingLeft = root.getChild("wingLeft");
        this.wingTipLeft = this.wingLeft.getChild("wingTipLeft");
        this.wingRight = root.getChild("wingRight");
        this.wingTipRight = this.wingRight.getChild("wingTipRight");
        this.rearLegRight = root.getChild("rearLegRight");
        this.rearLegRightTip = this.rearLegRight.getChild("rearLegRightTip");
        this.rearFootRight = this.rearLegRightTip.getChild("rearFootRight");
        this.rearLegRightTip_r1 = this.rearLegRightTip.getChild("rearLegRightTip_r1");
        this.rearLegLeft = root.getChild("rearLegLeft");
        this.rearLegLefttip = this.rearLegLeft.getChild("rearLegLefttip");
        this.rearFootLeft = this.rearLegLefttip.getChild("rearFootLeft");
        this.rearLegLefttip_r1 = this.rearLegLefttip.getChild("rearLegLefttip_r1");
        this.frontLegRight = root.getChild("frontLegRight");
        this.frontLegRighttip = this.frontLegRight.getChild("frontLegRighttip");
        this.frontFootRight = this.frontLegRighttip.getChild("frontFootRight");
        this.frontLegRighttip_r1 = this.frontLegRighttip.getChild("frontLegRighttip_r1");
        this.frontLegRight_r1 = this.frontLegRight.getChild("frontLegRight_r1");
        this.frontLegLeft = root.getChild("frontLegLeft");
        this.frontLegLefttip = this.frontLegLeft.getChild("frontLegLefttip");
        this.frontFootLeft = this.frontLegLefttip.getChild("frontFootLeft");
        this.frontLegLefttip_r1 = this.frontLegLefttip.getChild("frontLegLefttip_r1");
        this.frontLegLeft_r1 = this.frontLegLeft.getChild("frontLegLeft_r1");
        this.neck = root.getChild("neck");
        this.neck2 = this.neck.getChild("neck2");
        this.neck3 = this.neck2.getChild("neck3");
        this.neck4 = this.neck3.getChild("neck4");
        this.head = this.neck4.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.ears = this.head.getChild("ears");
        this.otherEars = this.head.getChild("otherEars");
        this.otherEars_r2 = this.otherEars.getChild("otherEars_r2");
        this.otherEars_r1 = this.otherEars.getChild("otherEars_r1");
        this.tail = root.getChild("tail");
        this.tail2 = this.tail.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.tail4 = this.tail3.getChild("tail4");
        this.tail5 = this.tail4.getChild("tail5");
        this.tail6 = this.tail5.getChild("tail6");
        this.tail7 = this.tail6.getChild("tail7");
        this.extraTail = this.tail6.getChild("extraTail");
        this.otherTail = this.tail6.getChild("otherTail");
        this.otherTail_r4 = this.otherTail.getChild("otherTail_r4");
        this.otherTail_r3 = this.otherTail.getChild("otherTail_r3");
        this.otherTail_r2 = this.otherTail.getChild("otherTail_r2");
        this.otherTail_r1 = this.otherTail.getChild("otherTail_r1");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 168).cuboid(-12.0F, -11.0F, -32.0F, 24.0F, 24.0F, 64.0F).uv(116, 256)
                .cuboid(-1.0F, -17.0F, -26.0F, 2.0F, 6.0F, 12.0F).uv(60, 256).cuboid(-1.0F, -17.0F, 14.0F, 2.0F, 6.0F, 12.0F), ModelTransform.pivot(0.0F, -8.0F, -2.0F));
        modelPartData1.addChild("chest", ModelPartBuilder.create().uv(252, 196).cuboid(-15.0F, -11.0F, 0.0F, 3.0F, 12.0F, 14.0F).uv(247, 86).cuboid(12.0F, -11.0F, 0.0F, 3.0F, 12.0F, 14.0F),
                ModelTransform.pivot(0.0F, 1.0F, 0.0F));
        modelPartData1.addChild("spike", ModelPartBuilder.create().uv(88, 256).cuboid(-1.0F, -6.0F, -5.0F, 2.0F, 6.0F, 12.0F), ModelTransform.pivot(0.0F, -11.0F, -1.0F));
        ModelPartData modelPartData2 = modelPartData.addChild("wingLeft",
                ModelPartBuilder.create().uv(168, 0).cuboid(-56.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F).uv(112, 112).cuboid(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F),
                ModelTransform.pivot(-12.0F, -18.0F, -24.0F));
        modelPartData2.addChild("wingTipLeft", ModelPartBuilder.create().uv(168, 24).cuboid(-56.0F, -3.0F, -2.0F, 56.0F, 4.0F, 4.0F).uv(0, 112).cuboid(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F),
                ModelTransform.pivot(-56.0F, 0.0F, 0.0F));
        ModelPartData modelPartData3 = modelPartData.addChild("wingRight",
                ModelPartBuilder.create().uv(112, 168).cuboid(0.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F).uv(0, 56).cuboid(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F),
                ModelTransform.pivot(12.0F, -18.0F, -24.0F));
        modelPartData3.addChild("wingTipRight", ModelPartBuilder.create().uv(168, 16).cuboid(0.0F, -3.0F, -2.0F, 56.0F, 4.0F, 4.0F).uv(0, 0).cuboid(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F),
                ModelTransform.pivot(56.0F, 0.0F, 0.0F));
        ModelPartData modelPartData4 = modelPartData.addChild("rearLegRight", ModelPartBuilder.create().uv(0, 40).cuboid(-8.0F, -4.2322F, -6.0798F, 12.0F, 28.0F, 12.0F),
                ModelTransform.pivot(16.0F, -5.0F, 22.0F));
        ModelPartData modelPartData5 = modelPartData4.addChild("rearLegRightTip", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 20.327F, 1.5816F));
        modelPartData5.addChild("rearLegRightTip_r1", ModelPartBuilder.create().uv(176, 184).cuboid(-6.0F, -7.5F, -7.0F, 10.0F, 32.0F, 11.0F), ModelTransform.pivot(0.0F, 1.1105F, -0.517F));
        modelPartData5.addChild("rearFootRight", ModelPartBuilder.create().uv(168, 66).cuboid(-5.0F, 0.0F, -15.0F, 12.0F, 6.0F, 19.0F), ModelTransform.pivot(-2.0F, 16.0502F, 14.6409F));
        ModelPartData modelPartData6 = modelPartData.addChild("rearLegLeft", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.1472F, -5.6383F, 12.0F, 28.0F, 12.0F),
                ModelTransform.pivot(-16.0F, -5.0F, 21.0F));
        ModelPartData modelPartData7 = modelPartData6.addChild("rearLegLefttip", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, 19.9876F, 2.1028F));
        modelPartData7.addChild("rearLegLefttip_r1", ModelPartBuilder.create().uv(0, 80).cuboid(-4.0F, -6.6164F, -6.486F, 10.0F, 32.0F, 11.0F), ModelTransform.pivot(-1.0F, 0.6549F, -0.4294F));
        modelPartData7.addChild("rearFootLeft", ModelPartBuilder.create().uv(0, 182).cuboid(-6.0F, 0.3F, -14.7F, 12.0F, 6.0F, 19.0F), ModelTransform.pivot(0.0F, 15.6549F, 15.0706F));
        ModelPartData modelPartData8 = modelPartData.addChild("frontLegRight", ModelPartBuilder.create(), ModelTransform.pivot(12.0F, -3.0F, -25.0F));
        modelPartData8.addChild("frontLegRight_r1", ModelPartBuilder.create().uv(252, 32).cuboid(-4.0F, -38.2535F, -12.1057F, 8.0F, 24.0F, 8.0F), ModelTransform.pivot(0.0F, 30.8475F, 16.5052F));
        ModelPartData modelPartData9 = modelPartData8.addChild("frontLegRighttip", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.207F, -0.1549F));
        modelPartData9.addChild("frontLegRighttip_r1", ModelPartBuilder.create().uv(0, 256).cuboid(-2.9F, 3.0F, -39.0F, 7.0F, 17.0F, 8.0F), ModelTransform.pivot(-1.0F, -16.207F, 32.1549F));
        modelPartData9.addChild("frontFootRight",
                ModelPartBuilder.create().uv(256, 256).cuboid(-4.8F, 0.0F, -15.1665F, 7.0F, 5.0F, 8.0F).uv(240, 168).cuboid(-4.8F, -1.0F, -7.1665F, 7.0F, 6.0F, 8.0F),
                ModelTransform.pivot(1.0F, 10.793F, 3.1549F));
        ModelPartData modelPartData10 = modelPartData.addChild("frontLegLeft", ModelPartBuilder.create(), ModelTransform.pivot(-12.0F, -3.0F, -25.0F));
        modelPartData10.addChild("frontLegLeft_r1", ModelPartBuilder.create().uv(224, 238).cuboid(-4.0F, -38.2535F, -12.1057F, 8.0F, 24.0F, 8.0F), ModelTransform.pivot(0.0F, 30.8475F, 16.5052F));
        ModelPartData modelPartData11 = modelPartData10.addChild("frontLegLefttip", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 15.4023F, 0.2817F));
        modelPartData11.addChild("frontLegLefttip_r1", ModelPartBuilder.create().uv(30, 256).cuboid(-4.9F, 4.0F, -37.0F, 7.0F, 17.0F, 8.0F), ModelTransform.pivot(1.0F, -16.4023F, 30.7183F));
        modelPartData11.addChild("frontFootLeft",
                ModelPartBuilder.create().uv(256, 243).cuboid(-3.8F, -0.3F, -14.4825F, 7.0F, 5.0F, 8.0F).uv(144, 256).cuboid(-3.8F, -1.3F, -6.4825F, 7.0F, 6.0F, 8.0F),
                ModelTransform.pivot(0.0F, 12.1611F, 3.9136F));
        ModelPartData modelPartData12 = modelPartData.addChild("neck",
                ModelPartBuilder.create().uv(218, 184).cuboid(-7.0F, -8.0F, -10.0F, 14.0F, 16.0F, 10.0F).uv(168, 66).cuboid(-1.0F, -12.0F, -8.0F, 2.0F, 5.0F, 6.0F),
                ModelTransform.pivot(0.0F, -10.0F, -34.0F));
        ModelPartData modelPartData13 = modelPartData12.addChild("neck2",
                ModelPartBuilder.create().uv(0, 207).cuboid(-7.0F, -7.0F, -10.0F, 14.0F, 15.0F, 10.0F).uv(168, 32).cuboid(-1.0F, -11.0F, -8.0F, 2.0F, 6.0F, 6.0F),
                ModelTransform.pivot(0.0F, -2.0F, -10.0F));
        ModelPartData modelPartData14 = modelPartData13.addChild("neck3",
                ModelPartBuilder.create().uv(112, 207).cuboid(-7.0F, -7.0F, -10.0F, 14.0F, 14.0F, 10.0F).uv(39, 117).cuboid(-1.0F, -11.0F, -8.0F, 2.0F, 7.0F, 6.0F),
                ModelTransform.pivot(0.0F, -1.0F, -10.0F));
        ModelPartData modelPartData15 = modelPartData14.addChild("neck4",
                ModelPartBuilder.create().uv(176, 238).cuboid(-7.0F, -6.0F, -10.0F, 14.0F, 12.0F, 10.0F).uv(0, 182).cuboid(-1.0F, -10.0F, -8.0F, 2.0F, 5.0F, 6.0F),
                ModelTransform.pivot(0.0F, -2.0F, -10.0F));
        ModelPartData modelPartData16 = modelPartData15.addChild("head", ModelPartBuilder.create().uv(112, 184).cuboid(-8.0F, -3.0F, -30.0F, 16.0F, 7.0F, 16.0F).uv(168, 32)
                .cuboid(-10.0F, -10.0F, -16.0F, 20.0F, 18.0F, 16.0F).uv(39, 130).cuboid(2.0F, -5.0F, -28.0F, 3.0F, 4.0F, 4.0F).uv(0, 123).cuboid(-5.0F, -5.0F, -28.0F, 3.0F, 4.0F, 4.0F),
                ModelTransform.pivot(0.0F, 0.0F, -10.0F));
        ModelPartData modelPartData17 = modelPartData16.addChild("otherEars", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -9.0F, -7.0F));
        modelPartData17.addChild("otherEars_r1", ModelPartBuilder.create().uv(42, 80).cuboid(-2.0F, -13.0F, -1.0F, 4.0F, 15.0F, 3.0F), ModelTransform.pivot(5.0F, 0.0F, 0.0F));
        modelPartData17.addChild("otherEars_r2", ModelPartBuilder.create().uv(42, 98).cuboid(-2.0F, -13.0F, -1.0F, 4.0F, 15.0F, 3.0F), ModelTransform.pivot(-5.0F, 0.0F, 0.0F));
        modelPartData16.addChild("ears", ModelPartBuilder.create().uv(194, 260).cuboid(8.0F, -5.0F, -4.0F, 4.0F, 7.0F, 6.0F).uv(174, 260).cuboid(-2.0F, -5.0F, -4.0F, 4.0F, 7.0F, 6.0F),
                ModelTransform.pivot(-5.0F, -10.0F, -6.0F));
        modelPartData16.addChild("jaw", ModelPartBuilder.create().uv(168, 91).cuboid(-6.0F, -2.0F, -14.0F, 12.0F, 4.0F, 14.0F), ModelTransform.pivot(0.0F, 6.0F, -16.0F));
        ModelPartData modelPartData18 = modelPartData.addChild("tail",
                ModelPartBuilder.create().uv(0, 153).cuboid(-7.0F, -7.0F, -1.0F, 14.0F, 14.0F, 15.0F).uv(160, 219).cuboid(-1.0F, -12.0F, 3.0F, 2.0F, 5.0F, 8.0F),
                ModelTransform.pivot(0.0F, -9.0F, 30.0F));
        ModelPartData modelPartData19 = modelPartData18.addChild("tail2",
                ModelPartBuilder.create().uv(0, 123).cuboid(-6.0F, -6.0F, -1.0F, 12.0F, 12.0F, 15.0F).uv(36, 40).cuboid(-1.0F, -10.0F, 3.0F, 2.0F, 4.0F, 8.0F),
                ModelTransform.pivot(0.0F, 0.0F, 14.0F));
        ModelPartData modelPartData20 = modelPartData19.addChild("tail3",
                ModelPartBuilder.create().uv(203, 212).cuboid(-5.0F, -6.0F, -1.0F, 10.0F, 11.0F, 15.0F).uv(36, 0).cuboid(-1.0F, -10.0F, 2.0F, 2.0F, 4.0F, 8.0F),
                ModelTransform.pivot(0.0F, 1.0F, 14.0F));
        ModelPartData modelPartData21 = modelPartData20.addChild("tail4",
                ModelPartBuilder.create().uv(215, 76).cuboid(-2.0F, -5.0F, -1.0F, 8.0F, 9.0F, 15.0F).uv(238, 214).cuboid(1.0F, -8.0F, 3.0F, 2.0F, 3.0F, 8.0F),
                ModelTransform.pivot(-2.0F, 0.0F, 14.0F));
        ModelPartData modelPartData22 = modelPartData21.addChild("tail5",
                ModelPartBuilder.create().uv(225, 51).cuboid(-3.0F, -4.0F, -1.0F, 6.0F, 8.0F, 15.0F).uv(176, 227).cuboid(-1.0F, -7.0F, 3.0F, 2.0F, 3.0F, 8.0F),
                ModelTransform.pivot(2.0F, 0.0F, 14.0F));
        ModelPartData modelPartData23 = modelPartData22.addChild("tail6",
                ModelPartBuilder.create().uv(248, 223).cuboid(0.0F, -2.0F, -1.0F, 4.0F, 5.0F, 15.0F).uv(160, 184).cuboid(1.0F, -5.0F, 3.0F, 2.0F, 3.0F, 8.0F),
                ModelTransform.pivot(-2.0F, 0.0F, 14.0F));
        ModelPartData modelPartData24 = modelPartData23.addChild("otherTail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 8.0F));
        modelPartData24.addChild("otherTail_r1", ModelPartBuilder.create().uv(38, 207).cuboid(-12.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F), ModelTransform.pivot(2.0F, 0.0F, 0.0F));
        modelPartData24.addChild("otherTail_r2", ModelPartBuilder.create().uv(206, 100).cuboid(1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F), ModelTransform.pivot(2.0F, 0.0F, 0.0F));
        modelPartData24.addChild("otherTail_r3", ModelPartBuilder.create().uv(150, 207).cuboid(-12.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F), ModelTransform.pivot(2.0F, 0.0F, -6.0F));
        modelPartData24.addChild("otherTail_r4", ModelPartBuilder.create().uv(218, 107).cuboid(1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F), ModelTransform.pivot(2.0F, 0.0F, -6.0F));
        modelPartData23.addChild("extraTail", ModelPartBuilder.create().uv(210, 32).cuboid(4.0F, 0.0F, -7.0F, 11.0F, 0.0F, 14.0F).uv(29, 182).cuboid(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 14.0F),
                ModelTransform.pivot(0.0F, 0.0F, 7.0F));
        modelPartData23.addChild("tail7", ModelPartBuilder.create().uv(252, 64).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 15.0F).uv(0, 0).cuboid(0.0F, -3.0F, 5.0F, 0.0F, 2.0F, 4.0F),
                ModelTransform.pivot(2.0F, 0.0F, 14.0F));
        return TexturedModelData.of(modelData, 512, 512);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body, this.wingLeft, this.wingRight, this.rearLegLeft, this.rearLegRight, this.frontLegLeft, this.frontLegRight, this.neck, this.tail);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // Extras
        this.renderExtras(entity);
        // General Body
        this.otherEars.pitch = -1.0036F;
        this.otherEars_r1.pitch = 0.1745F;
        this.otherEars_r1.roll = 0.2182F;
        this.otherEars_r2.pitch = 0.1745F;
        this.otherEars_r2.roll = -0.2182F;
        this.otherTail_r1.yaw = 0.7418F;
        this.otherTail_r2.yaw = -0.7418F;
        this.otherTail_r3.yaw = 0.6109F;
        this.otherTail_r4.yaw = -0.7418F;

        this.rearLegRight.pitch = -0.9599F;
        this.rearLegRightTip.pitch = 1.309F;
        this.rearLegRightTip_r1.pitch = 0.9599F;
        this.rearFootRight.pitch = -0.3491F;
        this.rearLegLeft.pitch = -0.9599F;
        this.rearLegLefttip.pitch = 1.309F;
        this.rearLegLefttip_r1.pitch = 0.9599F;
        this.rearFootLeft.pitch = -0.3491F;
        this.frontLegRight.pitch = 0.3491F;
        this.frontLegRight_r1.pitch = 0.2618F;
        this.frontLegRighttip.pitch = -1.5708F;
        this.frontLegRighttip_r1.pitch = 0.2618F;
        this.frontFootRight.pitch = 1.2217F;
        this.frontLegLeft.pitch = 0.3491F;
        this.frontLegLeft_r1.pitch = 0.2618F;
        this.frontLegLefttip.pitch = -1.5708F;
        this.frontLegLefttip_r1.pitch = 0.2618F;
        this.frontFootLeft.pitch = 1.2217F;

        // Watching Animation
        // Head glitches while riding
        this.head.yaw = headYaw * (0.017453292F / 6.0F); // 0.0049453292F
        this.head.pitch = headPitch * 0.0037453292F;
        this.neck.yaw = headYaw * (0.017453292F / 5.0F);
        this.neck2.yaw = headYaw * (0.017453292F / 5.0F);
        this.neck3.yaw = headYaw * (0.017453292F / 4.0F);
        this.neck4.yaw = headYaw * (0.017453292F / 4.0F);

        this.neck.pitch = headPitch * 0.0017453292F;
        this.neck2.pitch = headPitch * 0.0017453292F;
        this.neck3.pitch = headPitch * 0.0017453292F;
        this.neck4.pitch = headPitch * 0.0017453292F;

        // Fire Breath
        Boolean isFireBreathing = entity.getDataTracker().get(DragonEntity.FIRE_BREATH);
        if (isFireBreathing) {
            this.jaw.pitch = 0.4F;
        }

        float slowlyIncreasingFloat = ((float) Math.floorMod(entity.getWorld().getTime(), 100L) + animationProgress) / 100.0F;

        // Between Animation
        if (entity.getDataTracker().get(DragonEntity.CLIENT_START_FLYING)) {
            if (entity.getDataTracker().get(DragonEntity.IS_START_FLYING)) {
                startFlyingTicker = MathHelper.clamp(startFlyingTicker + 0.05164F, 0.0F, 1.4981F);
            } else {
                startFlyingTicker = MathHelper.clamp(startFlyingTicker - 0.05164F, 0.0F, 1.4981F);
            }
            this.wingRight.roll = 0.6981F - startFlyingTicker;
            this.wingLeft.roll = -0.6981F + startFlyingTicker;
            this.wingTipLeft.roll = 2.618F - (startFlyingTicker * 1.32033909618F);
            this.wingTipRight.roll = -2.618F + (startFlyingTicker * 1.32033909618F);
            betweenFloater = 12.566370614F * slowlyIncreasingFloat;
            if (entity.isOnGround()) {
                float walkFloat = 32.0F;
                this.rearLegRight.pitch = -0.9599F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;
                this.rearLegLeft.pitch = -0.9599F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;

                this.rearFootRight.pitch = -0.3491F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.9F;
                this.rearFootLeft.pitch = -0.3491F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.9F;

                this.frontLegRight.pitch = 0.3491F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;
                this.frontLegLeft.pitch = 0.3491F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;

                this.frontLegRighttip.pitch = -1.5708F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.8F;
                this.frontLegLefttip.pitch = -1.5708F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.8F;

                this.frontFootRight.pitch = 1.2217F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.2F;
                this.frontFootLeft.pitch = 1.2217F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.2F;
            }
        } else

        // While Flying Animation
        if (entity.getDataTracker().get(DragonEntity.IS_FLYING)) {
            startFlyingTicker = 0.0F;
            // mediumSpeedSin goes from -1 to 1
            float mediumSpeedSin = MathHelper.cos(12.566370614F * slowlyIncreasingFloat - (betweenFloater + 3.1415926535897F));
            // Wings
            this.wingRight.roll = mediumSpeedSin * 0.8F; // double pi inside cos does it make faster
            this.wingTipRight.roll = this.wingRight.roll * 0.8F;
            this.wingLeft.roll = -this.wingRight.roll;
            this.wingTipLeft.roll = -this.wingTipRight.roll;

            // Body Floating
            float bodyFloating = -mediumSpeedSin - 4.0F;
            this.body.pivotY = bodyFloating;
            this.wingLeft.pivotY = bodyFloating - 10.0F;
            this.wingRight.pivotY = bodyFloating - 10.0F; // Dont know why it has to be -10F
            this.rearLegLeft.pivotY = bodyFloating;
            this.rearLegRight.pivotY = bodyFloating;
            this.frontLegLeft.pivotY = bodyFloating;
            this.frontLegRight.pivotY = bodyFloating;
            this.neck.pivotY = bodyFloating;
            this.tail.pivotY = bodyFloating;
            // head floating
            float headFloat = MathHelper.cos(bodyFloating / 2.0F);
            this.neck2.pivotY = -headFloat;
            this.neck3.pivotY = -headFloat;
            this.neck4.pivotY = -headFloat * 0.9F;
            this.head.pivotY = -headFloat * 0.8F;
            // yaw
            if (!isFireBreathing) {
                this.jaw.pitch = -headFloat * 0.3F;
            }
            // tail
            this.tail2.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 3F));
            this.tail3.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 3F));
            this.tail4.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 4F));
            this.tail5.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 4F));
            this.tail5.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 5F));
            this.tail6.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 5F));
            this.tail7.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 6F)) * 0.5F;
            if (entity.isOnGround()) {
                float walkFloat = 32.0F;
                this.rearLegRight.pitch = -0.9599F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;
                this.rearLegLeft.pitch = -0.9599F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;

                this.rearFootRight.pitch = -0.3491F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.9F;
                this.rearFootLeft.pitch = -0.3491F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.9F;

                this.frontLegRight.pitch = 0.3491F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;
                this.frontLegLeft.pitch = 0.3491F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;

                this.frontLegRighttip.pitch = -1.5708F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.8F;
                this.frontLegLefttip.pitch = -1.5708F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.8F;

                this.frontFootRight.pitch = 1.2217F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.2F;
                this.frontFootLeft.pitch = 1.2217F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.2F;
            } else {
                // Legs rear
                this.rearLegRight.pitch = 0.5672F;
                this.rearLegRightTip.pitch = -0.2182F;
                this.rearLegRightTip_r1.pitch = 0.9599F;
                this.rearFootRight.pitch = 1.4835F;

                this.rearLegLeft.pitch = 0.5672F;
                this.rearLegLefttip.pitch = -0.2182F;
                this.rearLegLefttip_r1.pitch = 0.9599F;
                this.rearFootLeft.pitch = 1.4835F;

                // Legs front
                this.frontLegRight.pitch = 0.7418F;
                this.frontLegRight_r1.pitch = 0.2618F;
                this.frontLegRighttip.pitch = -1.0472F;
                this.frontLegRighttip_r1.pitch = 0.2618F;
                this.frontFootRight.pitch = 1.4835F;

                this.frontLegLeft.pitch = 0.7418F;
                this.frontLegLeft_r1.pitch = 0.2618F;
                this.frontLegLefttip.pitch = -1.0472F;
                this.frontLegLefttip_r1.pitch = 0.2618F;
                this.frontFootLeft.pitch = 1.4835F;
            }
        } else

        // End of Flying Animation
        if (entity.getDataTracker().get(DragonEntity.CLIENT_END_FLYING)) {
            float mediumSpeedSin = MathHelper.cos(12.566370614F * slowlyIncreasingFloat - (betweenFloater + 3.1415926535897F)); // 1to-1
            if (mediumSpeedSin < 0.04F && mediumSpeedSin > -0.04F) {
                endFlying = true;
            }
            if (endFlying == false) {
                this.wingRight.roll = mediumSpeedSin * 0.8F;
                this.wingTipRight.roll = this.wingRight.roll * 0.8F;
                this.wingLeft.roll = -this.wingRight.roll;
                this.wingTipLeft.roll = -this.wingTipRight.roll;
            } else {
                endFlyingTicker++;
                // Wings
                if (endFlyingTicker <= 10) {
                    startFlyingTicker = MathHelper.clamp(startFlyingTicker + 0.06205F, 0.0F, 0.6981F);
                    // a*b=c
                    this.wingRight.roll = startFlyingTicker;
                    this.wingLeft.roll = -startFlyingTicker;
                    this.wingTipLeft.roll = (startFlyingTicker * 3.75017905F);
                    this.wingTipRight.roll = -(startFlyingTicker * 3.75017905F);

                } else {
                    entity.getDataTracker().set(DragonEntity.CLIENT_END_FLYING, false);
                    endFlyingTicker = 0;
                    startFlyingTicker = 0;
                    endFlying = false;
                }

            }

            if (entity.isOnGround()) {
                float walkFloat = 32.0F;
                this.rearLegRight.pitch = -0.9599F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;
                this.rearLegLeft.pitch = -0.9599F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;

                this.rearFootRight.pitch = -0.3491F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.9F;
                this.rearFootLeft.pitch = -0.3491F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.9F;

                this.frontLegRight.pitch = 0.3491F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;
                this.frontLegLeft.pitch = 0.3491F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;

                this.frontLegRighttip.pitch = -1.5708F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.8F;
                this.frontLegLefttip.pitch = -1.5708F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.8F;

                this.frontFootRight.pitch = 1.2217F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.2F;
                this.frontFootLeft.pitch = 1.2217F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.2F;
            }

        } else

        // Walk Animation
        if (!entity.isInSittingPose() || entity.hasPassengers()) {
            float walkFloat = 32.0F;
            this.rearLegRight.pitch = -0.9599F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;
            this.rearLegLeft.pitch = -0.9599F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;

            this.rearFootRight.pitch = -0.3491F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.9F;
            this.rearFootLeft.pitch = -0.3491F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.9F;

            this.frontLegRight.pitch = 0.3491F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;
            this.frontLegLeft.pitch = 0.3491F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F;

            this.frontLegRighttip.pitch = -1.5708F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.8F;
            this.frontLegLefttip.pitch = -1.5708F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.8F;

            this.frontFootRight.pitch = 1.2217F + 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.2F;
            this.frontFootLeft.pitch = 1.2217F - 0.3F * MathHelper.wrap(limbAngle, walkFloat) * limbDistance * 1.3F * 0.2F;

            // tail
            this.tail.pitch = -0.1309F;
            this.tail2.pitch = -0.1309F;
            this.tail3.pitch = -0.1309F;
            this.tail4.pitch = -0.1745F;
            this.tail5.pitch = 0.1309F;
            this.tail6.pitch = 0.1745F;
            this.tail7.pitch = 0.2618F;

            this.tail.yaw = 0.0436F * MathHelper.wrap(limbAngle, 60.0F) * limbDistance;
            this.tail2.yaw = 0.0436F * MathHelper.wrap(limbAngle, 60.0F) * limbDistance;
            this.tail3.yaw = 0.0436F * MathHelper.wrap(limbAngle, 60.0F) * limbDistance;
            this.tail4.yaw = 0.0436F * MathHelper.wrap(limbAngle, 60.0F) * limbDistance;
            this.tail5.yaw = 0.0436F * MathHelper.wrap(limbAngle, 70.0F) * limbDistance;
            this.tail6.yaw = 0.0436F * MathHelper.wrap(limbAngle, 70.0F) * limbDistance;
            this.tail7.yaw = 0.0436F * MathHelper.wrap(limbAngle, 80.0F) * limbDistance;

            this.tail2.pivotY = 0.0F;
            this.tail3.pivotY = 0.0F;
            this.tail4.pivotY = 0.0F;
            this.tail5.pivotY = 0.0F;
            this.tail5.pivotY = 0.0F;
            this.tail6.pivotY = 0.0F;
            this.tail7.pivotY = 0.0F;

            // wings
            this.wingLeft.roll = -0.6981F + 0.0236F * MathHelper.wrap(limbAngle, 40.0F) * limbDistance;
            this.wingTipLeft.roll = 2.618F;
            this.wingRight.roll = 0.6981F - 0.0236F * MathHelper.wrap(limbAngle, 40.0F) * limbDistance;
            this.wingTipRight.roll = -2.618F;

            // Jaw
            if (!isFireBreathing) {
                this.jaw.pitch = 0.0F;
            }
        } else// if (entity.isInSittingPose())

        // Sitting Position Animation
        {
            this.tail.pitch = -0.1309F;
            this.tail2.pitch = -0.1309F;
            this.tail3.pitch = -0.1309F;
            this.tail4.pitch = -0.1745F;
            this.tail5.pitch = 0.1309F;
            this.tail6.pitch = 0.1745F;
            this.tail7.pitch = 0.1745F;

            this.tail.yaw = -0.0436F;
            this.tail2.yaw = -0.0436F;
            this.tail3.yaw = -0.0873F;
            this.tail4.yaw = -0.0436F;
            this.tail5.yaw = -0.2182F;
            this.tail6.yaw = -0.2182F;
            this.tail6.roll = -0.0436F;
            this.tail7.roll = -0.1309F;

            // Wings
            this.wingLeft.roll = -0.6981F;
            this.wingTipLeft.roll = 2.618F;
            this.wingRight.roll = 0.6981F;
            this.wingTipRight.roll = -2.618F;

            // Random Yaw Fire
            Float yawPitch = Math.abs(MathHelper.cos(6.2831853071F * slowlyIncreasingFloat) * 0.3F);
            if (entity.getSize() == 3 && !this.randomYawFire && yawPitch <= 0.01F && entity.getWorld().random.nextInt(16) == 0 && !isFireBreathing) {
                this.randomYawFire = true;
            }
            if (this.randomYawFire) {
                this.randomYawFireTick++;
                Float yawler = this.head.yaw + this.neck.yaw + this.neck2.yaw + this.neck3.yaw + this.neck4.yaw;
                Float pitcher = this.head.pitch + this.neck.pitch + this.neck2.pitch + this.neck3.pitch + this.neck4.pitch;
                yawler = (yawler / (float) (Math.PI)) * 1.5F;
                pitcher = pitcher * 3F;
                entity.getWorld().addParticle(ParticleTypes.FLAME, true, entity.getX() + Math.sin((entity.bodyYaw / 360F) * 2F * Math.PI + (Math.PI) + yawler) * 6D, entity.getY() - pitcher + 1.95D,
                        entity.getZ() - Math.cos((entity.bodyYaw / 360F) * 2F * Math.PI + (Math.PI) + yawler) * 6D, 0.0D, 0.0D, 0.0D);

                this.jaw.pitch = yawPitch;
                if ((this.randomYawFireTick > 10 && yawPitch <= 0.01F) || isFireBreathing) {
                    this.randomYawFireTick = 0;
                    this.randomYawFire = false;
                }
            }

        }

    }

    private void renderExtras(Entity entity) {
        if (entity.getDataTracker().get(DragonEntity.HAS_SADDLE)) {
            this.spike.visible = false;
        } else {
            this.spike.visible = true;
        }
        if (entity.getDataTracker().get(DragonEntity.HAS_CHEST)) {
            this.chest.visible = true;
        } else {
            this.chest.visible = false;
        }
        if (entity.getDataTracker().get(DragonEntity.OTHER_EARS)) {
            this.otherEars.visible = true;
            this.ears.visible = false;
        } else {
            this.otherEars.visible = false;
            this.ears.visible = true;
        }
        if (entity.getDataTracker().get(DragonEntity.OTHER_TAIL)) {
            this.otherTail.visible = true;
            this.extraTail.visible = false;
        } else {
            this.otherTail.visible = false;
            this.extraTail.visible = true;
        }

    }

}
