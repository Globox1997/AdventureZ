package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.DragonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
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
        private boolean isPlayingSound;
        private int delayerSoundTick;
        private boolean randomYawFire;
        private int randomYawFireTick;

        public DragonModel() {
                body = (new ModelPart(this)).setTextureSize(512, 512);
                body.setPivot(0.0F, -8.0F, -2.0F);
                body.setTextureOffset(0, 168).addCuboid(-12.0F, -11.0F, -32.0F, 24.0F, 24.0F, 64.0F, 0.0F, false);
                body.setTextureOffset(116, 256).addCuboid(-1.0F, -17.0F, -26.0F, 2.0F, 6.0F, 12.0F, 0.0F, false);
                body.setTextureOffset(60, 256).addCuboid(-1.0F, -17.0F, 14.0F, 2.0F, 6.0F, 12.0F, 0.0F, false);

                chest = (new ModelPart(this)).setTextureSize(512, 512);
                chest.setPivot(0.0F, 1.0F, 0.0F);
                body.addChild(chest);
                chest.setTextureOffset(252, 196).addCuboid(-15.0F, -11.0F, 0.0F, 3.0F, 12.0F, 14.0F, 0.0F, false);
                chest.setTextureOffset(247, 86).addCuboid(12.0F, -11.0F, 0.0F, 3.0F, 12.0F, 14.0F, 0.0F, false);

                spike = (new ModelPart(this)).setTextureSize(512, 512);
                spike.setPivot(0.0F, -11.0F, -1.0F);
                body.addChild(spike);
                spike.setTextureOffset(88, 256).addCuboid(-1.0F, -6.0F, -5.0F, 2.0F, 6.0F, 12.0F, 0.0F, false);

                wingLeft = (new ModelPart(this)).setTextureSize(512, 512);
                wingLeft.setPivot(-12.0F, -18.0F, -24.0F);
                wingLeft.setTextureOffset(168, 0).addCuboid(-56.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F, 0.0F, false);
                wingLeft.setTextureOffset(112, 112).addCuboid(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, 0.0F, false);

                wingTipLeft = (new ModelPart(this)).setTextureSize(512, 512);
                wingTipLeft.setPivot(-56.0F, 0.0F, 0.0F);
                wingLeft.addChild(wingTipLeft);
                wingTipLeft.setTextureOffset(168, 24).addCuboid(-56.0F, -3.0F, -2.0F, 56.0F, 4.0F, 4.0F, 0.0F, false);
                wingTipLeft.setTextureOffset(0, 112).addCuboid(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, 0.0F, false);

                wingRight = (new ModelPart(this)).setTextureSize(512, 512);
                wingRight.setPivot(12.0F, -18.0F, -24.0F);
                wingRight.setTextureOffset(112, 168).addCuboid(0.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F, 0.0F, false);
                wingRight.setTextureOffset(0, 56).addCuboid(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, 0.0F, false);

                wingTipRight = (new ModelPart(this)).setTextureSize(512, 512);
                wingTipRight.setPivot(56.0F, 0.0F, 0.0F);
                wingRight.addChild(wingTipRight);
                wingTipRight.setTextureOffset(168, 16).addCuboid(0.0F, -3.0F, -2.0F, 56.0F, 4.0F, 4.0F, 0.0F, false);
                wingTipRight.setTextureOffset(0, 0).addCuboid(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, 0.0F, false);

                rearLegRight = (new ModelPart(this)).setTextureSize(512, 512);
                rearLegRight.setPivot(16.0F, -5.0F, 22.0F);
                rearLegRight.setTextureOffset(0, 40).addCuboid(-8.0F, -4.2322F, -6.0798F, 12.0F, 28.0F, 12.0F, 0.0F,
                                false);

                rearLegRightTip = (new ModelPart(this)).setTextureSize(512, 512);
                rearLegRightTip.setPivot(-1.0F, 20.327F, 1.5816F);
                rearLegRight.addChild(rearLegRightTip);

                rearLegRightTip_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                rearLegRightTip_r1.setPivot(0.0F, 1.1105F, -0.517F);
                rearLegRightTip.addChild(rearLegRightTip_r1);
                rearLegRightTip_r1.setTextureOffset(176, 184).addCuboid(-6.0F, -7.5F, -7.0F, 10.0F, 32.0F, 11.0F, 0.0F,
                                false);

                rearFootRight = (new ModelPart(this)).setTextureSize(512, 512);
                rearFootRight.setPivot(-2.0F, 16.0502F, 14.6409F);
                rearLegRightTip.addChild(rearFootRight);
                rearFootRight.setTextureOffset(168, 66).addCuboid(-5.0F, 0.0F, -15.0F, 12.0F, 6.0F, 19.0F, 0.0F, false);

                rearLegLeft = (new ModelPart(this)).setTextureSize(512, 512);
                rearLegLeft.setPivot(-16.0F, -5.0F, 21.0F);
                rearLegLeft.setTextureOffset(0, 0).addCuboid(-4.0F, -5.1472F, -5.6383F, 12.0F, 28.0F, 12.0F, 0.0F,
                                false);

                rearLegLefttip = (new ModelPart(this)).setTextureSize(512, 512);
                rearLegLefttip.setPivot(2.0F, 19.9876F, 2.1028F);
                rearLegLeft.addChild(rearLegLefttip);

                rearLegLefttip_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                rearLegLefttip_r1.setPivot(-1.0F, 0.6549F, -0.4294F);
                rearLegLefttip.addChild(rearLegLefttip_r1);
                rearLegLefttip_r1.setTextureOffset(0, 80).addCuboid(-4.0F, -6.6164F, -6.486F, 10.0F, 32.0F, 11.0F, 0.0F,
                                false);

                rearFootLeft = (new ModelPart(this)).setTextureSize(512, 512);
                rearFootLeft.setPivot(0.0F, 15.6549F, 15.0706F);
                rearLegLefttip.addChild(rearFootLeft);
                rearFootLeft.setTextureOffset(0, 182).addCuboid(-6.0F, 0.3F, -14.7F, 12.0F, 6.0F, 19.0F, 0.0F, false);

                frontLegRight = (new ModelPart(this)).setTextureSize(512, 512);
                frontLegRight.setPivot(12.0F, -3.0F, -25.0F);

                frontLegRight_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                frontLegRight_r1.setPivot(0.0F, 30.8475F, 16.5052F);
                frontLegRight.addChild(frontLegRight_r1);
                frontLegRight_r1.setTextureOffset(252, 32).addCuboid(-4.0F, -38.2535F, -12.1057F, 8.0F, 24.0F, 8.0F,
                                0.0F, false);

                frontLegRighttip = (new ModelPart(this)).setTextureSize(512, 512);
                frontLegRighttip.setPivot(0.0F, 16.207F, -0.1549F);
                frontLegRight.addChild(frontLegRighttip);

                frontLegRighttip_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                frontLegRighttip_r1.setPivot(-1.0F, -16.207F, 32.1549F);
                frontLegRighttip.addChild(frontLegRighttip_r1);
                frontLegRighttip_r1.setTextureOffset(0, 256).addCuboid(-2.9F, 3.0F, -39.0F, 7.0F, 17.0F, 8.0F, 0.0F,
                                false);

                frontFootRight = (new ModelPart(this)).setTextureSize(512, 512);
                frontFootRight.setPivot(1.0F, 10.793F, 3.1549F);
                frontLegRighttip.addChild(frontFootRight);
                frontFootRight.setTextureOffset(256, 256).addCuboid(-4.8F, 0.0F, -15.1665F, 7.0F, 5.0F, 8.0F, 0.0F,
                                false);
                frontFootRight.setTextureOffset(240, 168).addCuboid(-4.8F, -1.0F, -7.1665F, 7.0F, 6.0F, 8.0F, 0.0F,
                                false);

                frontLegLeft = (new ModelPart(this)).setTextureSize(512, 512);
                frontLegLeft.setPivot(-12.0F, -3.0F, -25.0F);

                frontLegLeft_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                frontLegLeft_r1.setPivot(0.0F, 30.8475F, 16.5052F);
                frontLegLeft.addChild(frontLegLeft_r1);
                frontLegLeft_r1.setTextureOffset(224, 238).addCuboid(-4.0F, -38.2535F, -12.1057F, 8.0F, 24.0F, 8.0F,
                                0.0F, false);

                frontLegLefttip = (new ModelPart(this)).setTextureSize(512, 512);
                frontLegLefttip.setPivot(0.0F, 15.4023F, 0.2817F);
                frontLegLeft.addChild(frontLegLefttip);

                frontLegLefttip_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                frontLegLefttip_r1.setPivot(1.0F, -16.4023F, 30.7183F);
                frontLegLefttip.addChild(frontLegLefttip_r1);
                frontLegLefttip_r1.setTextureOffset(30, 256).addCuboid(-4.9F, 4.0F, -37.0F, 7.0F, 17.0F, 8.0F, 0.0F,
                                false);

                frontFootLeft = (new ModelPart(this)).setTextureSize(512, 512);
                frontFootLeft.setPivot(0.0F, 12.1611F, 3.9136F);
                frontLegLefttip.addChild(frontFootLeft);
                frontFootLeft.setTextureOffset(256, 243).addCuboid(-3.8F, -0.3F, -14.4825F, 7.0F, 5.0F, 8.0F, 0.0F,
                                false);
                frontFootLeft.setTextureOffset(144, 256).addCuboid(-3.8F, -1.3F, -6.4825F, 7.0F, 6.0F, 8.0F, 0.0F,
                                false);

                neck = (new ModelPart(this)).setTextureSize(512, 512);
                neck.setPivot(0.0F, -10.0F, -34.0F);
                neck.setTextureOffset(218, 184).addCuboid(-7.0F, -8.0F, -10.0F, 14.0F, 16.0F, 10.0F, 0.0F, false);
                neck.setTextureOffset(168, 66).addCuboid(-1.0F, -12.0F, -8.0F, 2.0F, 5.0F, 6.0F, 0.0F, false);

                neck2 = (new ModelPart(this)).setTextureSize(512, 512);
                neck2.setPivot(0.0F, -2.0F, -10.0F);
                neck.addChild(neck2);
                neck2.setTextureOffset(0, 207).addCuboid(-7.0F, -7.0F, -10.0F, 14.0F, 15.0F, 10.0F, 0.0F, false);
                neck2.setTextureOffset(168, 32).addCuboid(-1.0F, -11.0F, -8.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);

                neck3 = (new ModelPart(this)).setTextureSize(512, 512);
                neck3.setPivot(0.0F, -1.0F, -10.0F);
                neck2.addChild(neck3);
                neck3.setTextureOffset(112, 207).addCuboid(-7.0F, -7.0F, -10.0F, 14.0F, 14.0F, 10.0F, 0.0F, false);
                neck3.setTextureOffset(39, 117).addCuboid(-1.0F, -11.0F, -8.0F, 2.0F, 7.0F, 6.0F, 0.0F, false);

                neck4 = (new ModelPart(this)).setTextureSize(512, 512);
                neck4.setPivot(0.0F, -2.0F, -10.0F);
                neck3.addChild(neck4);
                neck4.setTextureOffset(176, 238).addCuboid(-7.0F, -6.0F, -10.0F, 14.0F, 12.0F, 10.0F, 0.0F, false);
                neck4.setTextureOffset(0, 182).addCuboid(-1.0F, -10.0F, -8.0F, 2.0F, 5.0F, 6.0F, 0.0F, false);

                head = (new ModelPart(this)).setTextureSize(512, 512);
                head.setPivot(0.0F, 0.0F, -10.0F);
                neck4.addChild(head);
                head.setTextureOffset(112, 184).addCuboid(-8.0F, -3.0F, -30.0F, 16.0F, 7.0F, 16.0F, 0.0F, false);
                head.setTextureOffset(168, 32).addCuboid(-10.0F, -10.0F, -16.0F, 20.0F, 18.0F, 16.0F, 0.0F, false);
                head.setTextureOffset(39, 130).addCuboid(2.0F, -5.0F, -28.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
                head.setTextureOffset(0, 123).addCuboid(-5.0F, -5.0F, -28.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);

                otherEars = (new ModelPart(this)).setTextureSize(512, 512);
                otherEars.setPivot(0.0F, -9.0F, -7.0F);
                head.addChild(otherEars);

                otherEars_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                otherEars_r1.setPivot(5.0F, 0.0F, 0.0F);
                otherEars.addChild(otherEars_r1);
                otherEars_r1.setTextureOffset(42, 80).addCuboid(-2.0F, -13.0F, -1.0F, 4.0F, 15.0F, 3.0F, 0.0F, false);

                otherEars_r2 = (new ModelPart(this)).setTextureSize(512, 512);
                otherEars_r2.setPivot(-5.0F, 0.0F, 0.0F);
                otherEars.addChild(otherEars_r2);
                otherEars_r2.setTextureOffset(42, 98).addCuboid(-2.0F, -13.0F, -1.0F, 4.0F, 15.0F, 3.0F, 0.0F, false);

                ears = (new ModelPart(this)).setTextureSize(512, 512);
                ears.setPivot(-5.0F, -10.0F, -6.0F);
                head.addChild(ears);
                ears.setTextureOffset(194, 260).addCuboid(8.0F, -5.0F, -4.0F, 4.0F, 7.0F, 6.0F, 0.0F, false);
                ears.setTextureOffset(174, 260).addCuboid(-2.0F, -5.0F, -4.0F, 4.0F, 7.0F, 6.0F, 0.0F, false);

                jaw = (new ModelPart(this)).setTextureSize(512, 512);
                jaw.setPivot(0.0F, 6.0F, -16.0F);
                head.addChild(jaw);
                jaw.setTextureOffset(168, 91).addCuboid(-6.0F, -2.0F, -14.0F, 12.0F, 4.0F, 14.0F, 0.0F, false);

                tail = (new ModelPart(this)).setTextureSize(512, 512);
                tail.setPivot(0.0F, -9.0F, 30.0F);
                tail.setTextureOffset(0, 153).addCuboid(-7.0F, -7.0F, -1.0F, 14.0F, 14.0F, 15.0F, 0.0F, false);
                tail.setTextureOffset(160, 219).addCuboid(-1.0F, -12.0F, 3.0F, 2.0F, 5.0F, 8.0F, 0.0F, false);

                tail2 = (new ModelPart(this)).setTextureSize(512, 512);
                tail2.setPivot(0.0F, 0.0F, 14.0F);
                tail.addChild(tail2);
                tail2.setTextureOffset(0, 123).addCuboid(-6.0F, -6.0F, -1.0F, 12.0F, 12.0F, 15.0F, 0.0F, false);
                tail2.setTextureOffset(36, 40).addCuboid(-1.0F, -10.0F, 3.0F, 2.0F, 4.0F, 8.0F, 0.0F, false);

                tail3 = (new ModelPart(this)).setTextureSize(512, 512);
                tail3.setPivot(0.0F, 1.0F, 14.0F);
                tail2.addChild(tail3);
                tail3.setTextureOffset(203, 212).addCuboid(-5.0F, -6.0F, -1.0F, 10.0F, 11.0F, 15.0F, 0.0F, false);
                tail3.setTextureOffset(36, 0).addCuboid(-1.0F, -10.0F, 2.0F, 2.0F, 4.0F, 8.0F, 0.0F, false);

                tail4 = (new ModelPart(this)).setTextureSize(512, 512);
                tail4.setPivot(-2.0F, 0.0F, 14.0F);
                tail3.addChild(tail4);
                tail4.setTextureOffset(215, 76).addCuboid(-2.0F, -5.0F, -1.0F, 8.0F, 9.0F, 15.0F, 0.0F, false);
                tail4.setTextureOffset(238, 214).addCuboid(1.0F, -8.0F, 3.0F, 2.0F, 3.0F, 8.0F, 0.0F, false);

                tail5 = (new ModelPart(this)).setTextureSize(512, 512);
                tail5.setPivot(2.0F, 0.0F, 14.0F);
                tail4.addChild(tail5);
                tail5.setTextureOffset(225, 51).addCuboid(-3.0F, -4.0F, -1.0F, 6.0F, 8.0F, 15.0F, 0.0F, false);
                tail5.setTextureOffset(176, 227).addCuboid(-1.0F, -7.0F, 3.0F, 2.0F, 3.0F, 8.0F, 0.0F, false);

                tail6 = (new ModelPart(this)).setTextureSize(512, 512);
                tail6.setPivot(-2.0F, 0.0F, 14.0F);
                tail5.addChild(tail6);
                tail6.setTextureOffset(248, 223).addCuboid(0.0F, -2.0F, -1.0F, 4.0F, 5.0F, 15.0F, 0.0F, false);
                tail6.setTextureOffset(160, 184).addCuboid(1.0F, -5.0F, 3.0F, 2.0F, 3.0F, 8.0F, 0.0F, false);

                otherTail = (new ModelPart(this)).setTextureSize(512, 512);
                otherTail.setPivot(0.0F, 0.0F, 8.0F);
                tail6.addChild(otherTail);

                otherTail_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                otherTail_r1.setPivot(2.0F, 0.0F, 0.0F);
                otherTail.addChild(otherTail_r1);
                otherTail_r1.setTextureOffset(38, 207).addCuboid(-12.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, 0.0F, false);

                otherTail_r2 = (new ModelPart(this)).setTextureSize(512, 512);
                otherTail_r2.setPivot(2.0F, 0.0F, 0.0F);
                otherTail.addChild(otherTail_r2);
                otherTail_r2.setTextureOffset(206, 100).addCuboid(1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);

                otherTail_r3 = (new ModelPart(this)).setTextureSize(512, 512);
                otherTail_r3.setPivot(2.0F, 0.0F, -6.0F);
                otherTail.addChild(otherTail_r3);
                otherTail_r3.setTextureOffset(150, 207).addCuboid(-12.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, 0.0F, false);

                otherTail_r4 = (new ModelPart(this)).setTextureSize(512, 512);
                otherTail_r4.setPivot(2.0F, 0.0F, -6.0F);
                otherTail.addChild(otherTail_r4);
                otherTail_r4.setTextureOffset(218, 107).addCuboid(1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);

                extraTail = (new ModelPart(this)).setTextureSize(512, 512);
                extraTail.setPivot(0.0F, 0.0F, 7.0F);
                tail6.addChild(extraTail);
                extraTail.setTextureOffset(210, 32).addCuboid(4.0F, 0.0F, -7.0F, 11.0F, 0.0F, 14.0F, 0.0F, false);
                extraTail.setTextureOffset(29, 182).addCuboid(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 14.0F, 0.0F, false);

                tail7 = (new ModelPart(this)).setTextureSize(512, 512);
                tail7.setPivot(2.0F, 0.0F, 14.0F);
                tail6.addChild(tail7);
                tail7.setTextureOffset(252, 64).addCuboid(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 15.0F, 0.0F, false);
                tail7.setTextureOffset(0, 0).addCuboid(0.0F, -3.0F, 5.0F, 0.0F, 2.0F, 4.0F, 0.0F, false);

        }

        @Override
        public Iterable<ModelPart> getParts() {
                return ImmutableList.of(this.body, this.wingLeft, this.wingRight, this.rearLegLeft, this.rearLegRight,
                                this.frontLegLeft, this.frontLegRight, this.neck, this.tail);
        }

        @Override
        public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
                        float headPitch) {
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

                float slowlyIncreasingFloat = ((float) Math.floorMod(entity.world.getTime(), 100L) + animationProgress)
                                / 100.0F;

                // Between Animation
                if (entity.getDataTracker().get(DragonEntity.CLIENT_START_FLYING)) {
                        if (entity.getDataTracker().get(DragonEntity.IS_START_FLYING)) {
                                startFlyingTicker = MathHelper.clamp(startFlyingTicker + 0.0258293103448F, 0.0F,
                                                1.4981F);
                        } else {
                                startFlyingTicker = MathHelper.clamp(startFlyingTicker - 0.0258293103448F, 0.0F,
                                                1.4981F);
                        }
                        this.wingRight.roll = 0.6981F - startFlyingTicker;
                        this.wingLeft.roll = -0.6981F + startFlyingTicker;
                        this.wingTipLeft.roll = 2.618F - (startFlyingTicker * 1.32033909618F);
                        this.wingTipRight.roll = -2.618F + (startFlyingTicker * 1.32033909618F);
                        betweenFloater = 12.566370614F * slowlyIncreasingFloat;
                        if (this.wingRight.roll <= -0.8F && startFlyingTicker >= 1.4981F && !this.isPlayingSound) {
                                this.playDragonWingSound(entity);
                        }
                } else

                // While Flying Animation
                if (entity.getDataTracker().get(DragonEntity.IS_FLYING)) {
                        startFlyingTicker = 0.0F;
                        // mediumSpeedSin goes from -1 to 1
                        float mediumSpeedSin = MathHelper.cos(
                                        12.566370614F * slowlyIncreasingFloat - (betweenFloater + 3.1415926535897F));
                        // Wings
                        this.wingRight.roll = mediumSpeedSin * 0.8F; // double pi inside cos does it make faster
                        this.wingTipRight.roll = this.wingRight.roll * 0.8F;
                        this.wingLeft.roll = -this.wingRight.roll;
                        this.wingTipLeft.roll = -this.wingTipRight.roll;

                        if (this.wingRight.roll <= -0.78F && mediumSpeedSin <= -0.98F && !this.isPlayingSound) {
                                this.playDragonWingSound(entity);
                        }
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
                        this.tail2.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 3));
                        this.tail3.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 3));
                        this.tail4.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 4));
                        this.tail5.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 4));
                        this.tail5.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 5));
                        this.tail6.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 5));
                        this.tail7.pivotY = MathHelper.cos(6.2831855F * slowlyIncreasingFloat - (6.2831855F / 6))
                                        * 0.5F;
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

                } else

                // End of Flying Animation
                if (entity.getDataTracker().get(DragonEntity.CLIENT_END_FLYING)) {
                        float mediumSpeedSin = MathHelper.cos(
                                        12.566370614F * slowlyIncreasingFloat - (betweenFloater + 3.1415926535897F)); // 1to-1
                        if (mediumSpeedSin < 0.02F && mediumSpeedSin > -0.02F) {
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
                                if (endFlyingTicker <= 30) {
                                        startFlyingTicker = MathHelper.clamp(startFlyingTicker + 0.02327F, 0.0F,
                                                        0.6981F);
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
                        if (this.wingRight.roll <= -0.79F && mediumSpeedSin <= -0.999F && !this.isPlayingSound) {
                                this.playDragonWingSound(entity);
                        }

                } else

                // Walk Animation
                if (!entity.isInSittingPose()) {

                        float walkFloat = 32.0F;
                        this.rearLegRight.pitch = -0.9599F
                                        + 0.3F * MathHelper.method_24504(limbAngle, walkFloat) * limbDistance * 1.3F;
                        this.rearLegLeft.pitch = -0.9599F
                                        - 0.3F * MathHelper.method_24504(limbAngle, walkFloat) * limbDistance * 1.3F;

                        this.rearFootRight.pitch = -0.3491F - 0.3F * MathHelper.method_24504(limbAngle, walkFloat)
                                        * limbDistance * 1.3F * 0.9F;
                        this.rearFootLeft.pitch = -0.3491F + 0.3F * MathHelper.method_24504(limbAngle, walkFloat)
                                        * limbDistance * 1.3F * 0.9F;

                        this.frontLegRight.pitch = 0.3491F
                                        - 0.3F * MathHelper.method_24504(limbAngle, walkFloat) * limbDistance * 1.3F;
                        this.frontLegLeft.pitch = 0.3491F
                                        + 0.3F * MathHelper.method_24504(limbAngle, walkFloat) * limbDistance * 1.3F;

                        this.frontLegRighttip.pitch = -1.5708F + 0.3F * MathHelper.method_24504(limbAngle, walkFloat)
                                        * limbDistance * 1.3F * 0.8F;
                        this.frontLegLefttip.pitch = -1.5708F - 0.3F * MathHelper.method_24504(limbAngle, walkFloat)
                                        * limbDistance * 1.3F * 0.8F;

                        this.frontFootRight.pitch = 1.2217F + 0.3F * MathHelper.method_24504(limbAngle, walkFloat)
                                        * limbDistance * 1.3F * 0.2F;
                        this.frontFootLeft.pitch = 1.2217F - 0.3F * MathHelper.method_24504(limbAngle, walkFloat)
                                        * limbDistance * 1.3F * 0.2F;

                        // tail
                        this.tail.pitch = -0.1309F;
                        this.tail2.pitch = -0.1309F;
                        this.tail3.pitch = -0.1309F;
                        this.tail4.pitch = -0.1745F;
                        this.tail5.pitch = 0.1309F;
                        this.tail6.pitch = 0.1745F;
                        this.tail7.pitch = 0.2618F;

                        this.tail.yaw = 0.0436F * MathHelper.method_24504(limbAngle, 60.0F) * limbDistance;
                        this.tail2.yaw = 0.0436F * MathHelper.method_24504(limbAngle, 60.0F) * limbDistance;
                        this.tail3.yaw = 0.0436F * MathHelper.method_24504(limbAngle, 60.0F) * limbDistance;
                        this.tail4.yaw = 0.0436F * MathHelper.method_24504(limbAngle, 60.0F) * limbDistance;
                        this.tail5.yaw = 0.0436F * MathHelper.method_24504(limbAngle, 70.0F) * limbDistance;
                        this.tail6.yaw = 0.0436F * MathHelper.method_24504(limbAngle, 70.0F) * limbDistance;
                        this.tail7.yaw = 0.0436F * MathHelper.method_24504(limbAngle, 80.0F) * limbDistance;

                        this.tail2.pivotY = 0.0F;
                        this.tail3.pivotY = 0.0F;
                        this.tail4.pivotY = 0.0F;
                        this.tail5.pivotY = 0.0F;
                        this.tail5.pivotY = 0.0F;
                        this.tail6.pivotY = 0.0F;
                        this.tail7.pivotY = 0.0F;

                        // wings
                        this.wingLeft.roll = -0.6981F
                                        + 0.0236F * MathHelper.method_24504(limbAngle, 40.0F) * limbDistance;
                        this.wingTipLeft.roll = 2.618F;
                        this.wingRight.roll = 0.6981F
                                        - 0.0236F * MathHelper.method_24504(limbAngle, 40.0F) * limbDistance;
                        this.wingTipRight.roll = -2.618F;

                        // Jaw
                        if (!isFireBreathing) {
                                this.jaw.pitch = 0.0F;
                        }
                } else

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
                        if (entity.getSize() == 3 && !this.randomYawFire && yawPitch <= 0.01F
                                        && entity.world.random.nextInt(8) == 0 && !isFireBreathing) {
                                this.randomYawFire = true;
                        }
                        if (this.randomYawFire) {
                                this.randomYawFireTick++;
                                Float yawler = this.head.yaw + this.neck.yaw + this.neck2.yaw + this.neck3.yaw
                                                + this.neck4.yaw;
                                Float pitcher = this.head.pitch + this.neck.pitch + this.neck2.pitch + this.neck3.pitch
                                                + this.neck4.pitch;
                                yawler = (yawler / (float) (Math.PI)) * 1.5F;
                                pitcher = pitcher * 3F;
                                entity.world.addParticle(ParticleTypes.FLAME, true,
                                                entity.getX() + Math.sin((entity.bodyYaw / 360F) * 2F * Math.PI
                                                                + (Math.PI) + yawler) * 6D,
                                                entity.getY() - pitcher + 2.2F,
                                                entity.getZ() - Math.cos((entity.bodyYaw / 360F) * 2F * Math.PI
                                                                + (Math.PI) + yawler) * 6D,
                                                0.0D, 0.0D, 0.0D);

                                this.jaw.pitch = yawPitch;
                                if ((this.randomYawFireTick > 10 && yawPitch <= 0.01F) || isFireBreathing) {
                                        this.randomYawFireTick = 0;
                                        this.randomYawFire = false;
                                }
                        }

                }
                if (this.isPlayingSound == true) {
                        this.delayerSoundTick++;
                        if (this.delayerSoundTick >= 10) {
                                this.isPlayingSound = false;
                                this.delayerSoundTick = 0;
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

        private void playDragonWingSound(Entity entity) {
                entity.world.playSound(entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.ENTITY_ENDER_DRAGON_FLAP, entity.getSoundCategory(), 5.0F,
                                0.8F + entity.world.random.nextFloat() * 0.3F, false);
                this.isPlayingSound = true;
        }
}