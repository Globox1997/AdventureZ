package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.DragonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;

// import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
// import net.minecraft.client.render.entity.WolfEntityRenderer;
// import net.minecraft.entity.boss.dragon.EnderDragonEntity;
// import net.minecraft.entity.boss.dragon.EnderDragonPart;
// import net.minecraft.client.render.entity.WolfEntityRenderer;
// import net.minecraft.client.render.entity.ShulkerEntityRenderer;
// import net.minecraft.client.render.entity.model.ShulkerEntityModel;

@Environment(EnvType.CLIENT)
public class DragonModel<T extends DragonEntity> extends CompositeEntityModel<T> {
        private final ModelPart body;
        private final ModelPart spike;
        private final ModelPart wingleft;
        private final ModelPart wingtipleft;
        private final ModelPart wingright;
        private final ModelPart wingtipright;
        private final ModelPart rearlegright;
        private final ModelPart rearlegrighttip;
        private final ModelPart rearlegrighttip_r1;
        private final ModelPart rearfootright;
        private final ModelPart rearlegleft;
        private final ModelPart rearleglefttip;
        private final ModelPart rearleglefttip_r1;
        private final ModelPart rearfootleft;
        private final ModelPart frontlegright;
        private final ModelPart frontlegright_r1;
        private final ModelPart frontlegrighttip;
        private final ModelPart frontlegrighttip_r1;
        private final ModelPart frontfootright;
        private final ModelPart frontlegleft;
        private final ModelPart frontlegleft_r1;
        private final ModelPart frontleglefttip;
        private final ModelPart frontleglefttip_r1;
        private final ModelPart frontfootleft;
        private final ModelPart neck;
        private final ModelPart neck2;
        private final ModelPart neck3;
        private final ModelPart neck4;
        private final ModelPart head;
        private final ModelPart jaw;
        private final ModelPart tail;
        private final ModelPart tail2;
        private final ModelPart tail3;
        private final ModelPart tail4;
        private final ModelPart tail5;
        private final ModelPart tail6;
        private final ModelPart tail7;

        private float betweenFloater;
        private float startFlyingTicker;
        private boolean endFlying;
        private int endFlyingTicker;
        private boolean isPlayingSound;
        private int delayerSoundTick;

        public DragonModel() {
                body = (new ModelPart(this)).setTextureSize(512, 512);
                body.setPivot(0.0F, -8.0F, -2.0F);
                body.setTextureOffset(0, 168).addCuboid(-12.0F, -11.0F, -32.0F, 24.0F, 24.0F, 64.0F, 0.0F, false);
                body.setTextureOffset(86, 256).addCuboid(-1.0F, -17.0F, -26.0F, 2.0F, 6.0F, 12.0F, 0.0F, false);
                body.setTextureOffset(30, 256).addCuboid(-1.0F, -17.0F, 14.0F, 2.0F, 6.0F, 12.0F, 0.0F, false);

                spike = (new ModelPart(this)).setTextureSize(512, 512);
                spike.setPivot(0.0F, -11.0F, -1.0F);
                body.addChild(spike);
                spike.setTextureOffset(58, 256).addCuboid(-1.0F, -6.0F, -5.0F, 2.0F, 6.0F, 12.0F, 0.0F, false);

                wingleft = (new ModelPart(this)).setTextureSize(512, 512);
                wingleft.setPivot(-12.0F, -18.0F, -24.0F);
                wingleft.setTextureOffset(168, 0).addCuboid(-56.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F, 0.0F, false);
                wingleft.setTextureOffset(112, 112).addCuboid(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, 0.0F, false);

                wingtipleft = (new ModelPart(this)).setTextureSize(512, 512);
                wingtipleft.setPivot(-56.0F, 0.0F, 0.0F);
                wingleft.addChild(wingtipleft);
                wingtipleft.setTextureOffset(168, 24).addCuboid(-56.0F, -3.0F, -2.0F, 56.0F, 4.0F, 4.0F, 0.0F, false);
                wingtipleft.setTextureOffset(0, 112).addCuboid(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, 0.0F, false);

                wingright = (new ModelPart(this)).setTextureSize(512, 512);
                wingright.setPivot(12.0F, -18.0F, -24.0F);
                wingright.setTextureOffset(112, 168).addCuboid(0.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F, 0.0F, false);
                wingright.setTextureOffset(0, 56).addCuboid(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, 0.0F, false);

                wingtipright = (new ModelPart(this)).setTextureSize(512, 512);
                wingtipright.setPivot(56.0F, 0.0F, 0.0F);
                wingright.addChild(wingtipright);
                wingtipright.setTextureOffset(168, 16).addCuboid(0.0F, -3.0F, -2.0F, 56.0F, 4.0F, 4.0F, 0.0F, false);
                wingtipright.setTextureOffset(0, 0).addCuboid(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, 0.0F, false);

                rearlegright = (new ModelPart(this)).setTextureSize(512, 512);
                rearlegright.setPivot(16.0F, -5.0F, 22.0F);
                rearlegright.setTextureOffset(0, 40).addCuboid(-8.0F, -4.2322F, -6.0798F, 12.0F, 28.0F, 12.0F, 0.0F,
                                false);

                rearlegrighttip = (new ModelPart(this)).setTextureSize(512, 512);
                rearlegrighttip.setPivot(-1.0F, 20.327F, 1.5816F);
                rearlegright.addChild(rearlegrighttip);
                rearlegrighttip_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                rearlegrighttip_r1.setPivot(0.0F, 1.1105F, -0.517F);
                rearlegrighttip.addChild(rearlegrighttip_r1);
                rearlegrighttip_r1.setTextureOffset(176, 184).addCuboid(-6.0F, -7.5F, -7.0F, 10.0F, 32.0F, 11.0F, 0.0F,
                                false);

                rearfootright = (new ModelPart(this)).setTextureSize(512, 512);
                rearfootright.setPivot(-2.0F, 16.0502F, 14.6409F);
                rearlegrighttip.addChild(rearfootright);
                rearfootright.setTextureOffset(168, 66).addCuboid(-5.0F, 0.0F, -15.0F, 12.0F, 6.0F, 19.0F, 0.0F, false);

                rearlegleft = (new ModelPart(this)).setTextureSize(512, 512);
                rearlegleft.setPivot(-16.0F, -5.0F, 21.0F);
                rearlegleft.setTextureOffset(0, 0).addCuboid(-4.0F, -5.1472F, -5.6383F, 12.0F, 28.0F, 12.0F, 0.0F,
                                false);

                rearleglefttip = (new ModelPart(this)).setTextureSize(512, 512);
                rearleglefttip.setPivot(2.0F, 19.9876F, 2.1028F);
                rearlegleft.addChild(rearleglefttip);

                rearleglefttip_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                rearleglefttip_r1.setPivot(-1.0F, 0.6549F, -0.4294F);
                rearleglefttip.addChild(rearleglefttip_r1);
                rearleglefttip_r1.setTextureOffset(0, 80).addCuboid(-4.0F, -6.6164F, -6.486F, 10.0F, 32.0F, 11.0F, 0.0F,
                                false);

                rearfootleft = (new ModelPart(this)).setTextureSize(512, 512);
                rearfootleft.setPivot(0.0F, 15.6549F, 15.0706F);
                rearleglefttip.addChild(rearfootleft);
                rearfootleft.setTextureOffset(0, 182).addCuboid(-6.0F, 0.3F, -14.7F, 12.0F, 6.0F, 19.0F, 0.0F, false);

                frontlegright = (new ModelPart(this)).setTextureSize(512, 512);
                frontlegright.setPivot(12.0F, -3.0F, -25.0F);

                frontlegright_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                frontlegright_r1.setPivot(0.0F, 30.8475F, 16.5052F);
                frontlegright.addChild(frontlegright_r1);

                frontlegright_r1.setTextureOffset(252, 32).addCuboid(-4.0F, -38.2535F, -12.1057F, 8.0F, 24.0F, 8.0F,
                                0.0F, false);

                frontlegrighttip = (new ModelPart(this)).setTextureSize(512, 512);
                frontlegrighttip.setPivot(0.0F, 16.207F, -0.1549F);
                frontlegright.addChild(frontlegrighttip);

                frontlegrighttip_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                frontlegrighttip_r1.setPivot(-1.0F, -16.207F, 32.1549F);
                frontlegrighttip.addChild(frontlegrighttip_r1);
                frontlegrighttip_r1.setTextureOffset(253, 210).addCuboid(-2.9F, 3.0F, -39.0F, 7.0F, 17.0F, 8.0F, 0.0F,
                                false);

                frontfootright = (new ModelPart(this)).setTextureSize(512, 512);
                frontfootright.setPivot(1.0F, 10.793F, 3.1549F);
                frontlegrighttip.addChild(frontfootright);
                frontfootright.setTextureOffset(144, 256).addCuboid(-4.8F, 0.0F, -15.1665F, 7.0F, 5.0F, 8.0F, 0.0F,
                                false);
                frontfootright.setTextureOffset(240, 168).addCuboid(-4.8F, -1.0F, -7.1665F, 7.0F, 6.0F, 8.0F, 0.0F,
                                false);

                frontlegleft = (new ModelPart(this)).setTextureSize(512, 512);
                frontlegleft.setPivot(-12.0F, -3.0F, -25.0F);

                frontlegleft_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                frontlegleft_r1.setPivot(0.0F, 30.8475F, 16.5052F);
                frontlegleft.addChild(frontlegleft_r1);
                frontlegleft_r1.setTextureOffset(224, 238).addCuboid(-4.0F, -38.2535F, -12.1057F, 8.0F, 24.0F, 8.0F,
                                0.0F, false);

                frontleglefttip = (new ModelPart(this)).setTextureSize(512, 512);
                frontleglefttip.setPivot(0.0F, 15.4023F, 0.2817F);
                frontlegleft.addChild(frontleglefttip);

                frontleglefttip_r1 = (new ModelPart(this)).setTextureSize(512, 512);
                frontleglefttip_r1.setPivot(1.0F, -16.4023F, 30.7183F);
                frontleglefttip.addChild(frontleglefttip_r1);
                frontleglefttip_r1.setTextureOffset(0, 256).addCuboid(-4.9F, 4.0F, -37.0F, 7.0F, 17.0F, 8.0F, 0.0F,
                                false);

                frontfootleft = (new ModelPart(this)).setTextureSize(512, 512);
                frontfootleft.setPivot(0.0F, 12.1611F, 3.9136F);
                frontleglefttip.addChild(frontfootleft);
                frontfootleft.setTextureOffset(256, 256).addCuboid(-3.8F, -0.3F, -14.4825F, 7.0F, 5.0F, 8.0F, 0.0F,
                                false);
                frontfootleft.setTextureOffset(114, 256).addCuboid(-3.8F, -1.3F, -6.4825F, 7.0F, 6.0F, 8.0F, 0.0F,
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
                head.setTextureOffset(174, 260).addCuboid(3.0F, -15.0F, -10.0F, 4.0F, 7.0F, 6.0F, 0.0F, false);
                head.setTextureOffset(42, 99).addCuboid(2.0F, -5.0F, -28.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
                head.setTextureOffset(256, 235).addCuboid(-7.0F, -15.0F, -10.0F, 4.0F, 7.0F, 6.0F, 0.0F, false);
                head.setTextureOffset(42, 91).addCuboid(-5.0F, -5.0F, -28.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);

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
                tail4.setTextureOffset(212, 101).addCuboid(1.0F, -8.0F, 3.0F, 2.0F, 3.0F, 8.0F, 0.0F, false);

                tail5 = (new ModelPart(this)).setTextureSize(512, 512);
                tail5.setPivot(2.0F, 0.0F, 14.0F);
                tail4.addChild(tail5);
                tail5.setTextureOffset(225, 51).addCuboid(-3.0F, -4.0F, -1.0F, 6.0F, 8.0F, 15.0F, 0.0F, false);
                tail5.setTextureOffset(160, 184).addCuboid(-1.0F, -7.0F, 3.0F, 2.0F, 3.0F, 8.0F, 0.0F, false);

                tail6 = (new ModelPart(this)).setTextureSize(512, 512);
                tail6.setPivot(-2.0F, 0.0F, 14.0F);
                tail5.addChild(tail6);
                tail6.setTextureOffset(210, 32).addCuboid(4.0F, 0.0F, 0.0F, 11.0F, 0.0F, 14.0F, 0.0F, false);
                tail6.setTextureOffset(246, 85).addCuboid(0.0F, -2.0F, -1.0F, 4.0F, 5.0F, 15.0F, 0.0F, false);
                tail6.setTextureOffset(31, 80).addCuboid(1.0F, -5.0F, 3.0F, 2.0F, 3.0F, 8.0F, 0.0F, false);
                tail6.setTextureOffset(29, 182).addCuboid(-10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 14.0F, 0.0F, false);

                tail7 = (new ModelPart(this)).setTextureSize(512, 512);
                tail7.setPivot(2.0F, 1.0F, 14.0F);
                tail6.addChild(tail7);
                tail7.setTextureOffset(252, 64).addCuboid(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 15.0F, 0.0F, false);
                tail7.setTextureOffset(0, 0).addCuboid(0.0F, -3.0F, 5.0F, 0.0F, 2.0F, 4.0F, 0.0F, false);

        }

        @Override
        public Iterable<ModelPart> getParts() {
                return ImmutableList.of(this.body, this.wingleft, this.wingright, this.rearlegleft, this.rearlegright,
                                this.frontlegleft, this.frontlegright, this.neck, this.tail);
        }

        @Override
        public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
                        float headPitch) {
                if (entity.getDataTracker().get(DragonEntity.HAS_SADDLE)) {
                        this.spike.visible = false;
                } else {
                        this.spike.visible = true;
                }

                // General Body
                this.rearlegright.pitch = -0.9599F;
                this.rearlegrighttip.pitch = 1.309F;
                this.rearlegrighttip_r1.pitch = 0.9599F;
                this.rearfootright.pitch = -0.3491F;
                this.rearlegleft.pitch = -0.9599F;
                this.rearleglefttip.pitch = 1.309F;
                this.rearleglefttip_r1.pitch = 0.9599F;
                this.rearfootleft.pitch = -0.3491F;
                this.frontlegright.pitch = 0.3491F;
                this.frontlegright_r1.pitch = 0.2618F;
                this.frontlegrighttip.pitch = -1.5708F;
                this.frontlegrighttip_r1.pitch = 0.2618F;
                this.frontfootright.pitch = 1.2217F;
                this.frontlegleft.pitch = 0.3491F;
                this.frontlegleft_r1.pitch = 0.2618F;
                this.frontleglefttip.pitch = -1.5708F;
                this.frontleglefttip_r1.pitch = 0.2618F;
                this.frontfootleft.pitch = 1.2217F;

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

                float slowlyIncreasingFloat = ((float) Math.floorMod(entity.world.getTime(), 100L) + animationProgress)
                                / 100.0F;

                // Between Animation
                if (entity.getDataTracker().get(DragonEntity.CLIENT_START_FLYING)) {
                        if (entity.getDataTracker().get(DragonEntity.IS_START_FLYING)) {
                                startFlyingTicker = MathHelper.clamp(startFlyingTicker + 0.012914655172F, 0.0F,
                                                1.4981F);
                        } else {
                                startFlyingTicker = MathHelper.clamp(startFlyingTicker - 0.012914655172F, 0.0F,
                                                1.4981F);
                        }
                        this.wingright.roll = 0.6981F - startFlyingTicker;
                        this.wingleft.roll = -0.6981F + startFlyingTicker;
                        this.wingtipleft.roll = 2.618F - (startFlyingTicker * 1.320339096230F);
                        this.wingtipright.roll = -2.618F + (startFlyingTicker * 1.320339096230F);
                        betweenFloater = 12.566370614F * slowlyIncreasingFloat;
                        // System.out.println(this.wingright.roll + "::" + startFlyingTicker);
                        if (this.wingright.roll <= -0.8F && startFlyingTicker >= 1.4981F && !this.isPlayingSound) {
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
                        this.wingright.roll = mediumSpeedSin * 0.8F; // double pi inside cos does it make faster
                        this.wingtipright.roll = this.wingright.roll * 0.8F;
                        this.wingleft.roll = -this.wingright.roll;
                        this.wingtipleft.roll = -this.wingtipright.roll;

                        if (this.wingright.roll <= -0.79F && mediumSpeedSin <= -0.999F && !this.isPlayingSound) {
                                this.playDragonWingSound(entity);
                        }
                        // Body Floating
                        float bodyFloating = -mediumSpeedSin - 4.0F;
                        this.body.pivotY = bodyFloating;
                        this.wingleft.pivotY = bodyFloating - 10.0F;
                        this.wingright.pivotY = bodyFloating - 10.0F; // Dont know why it has to be -10F
                        this.rearlegleft.pivotY = bodyFloating;
                        this.rearlegright.pivotY = bodyFloating;
                        this.frontlegleft.pivotY = bodyFloating;
                        this.frontlegright.pivotY = bodyFloating;
                        this.neck.pivotY = bodyFloating;
                        this.tail.pivotY = bodyFloating;
                        // head floating
                        float headFloat = MathHelper.cos(bodyFloating / 2.0F);
                        this.neck2.pivotY = -headFloat;
                        this.neck3.pivotY = -headFloat;
                        this.neck4.pivotY = -headFloat * 0.9F;
                        this.head.pivotY = -headFloat * 0.8F;
                        // yaw
                        this.jaw.pitch = -headFloat * 0.3F;
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
                        this.rearlegright.pitch = 0.5672F;
                        this.rearlegrighttip.pitch = -0.2182F;
                        this.rearlegrighttip_r1.pitch = 0.9599F;
                        this.rearfootright.pitch = 1.4835F;

                        this.rearlegleft.pitch = 0.5672F;
                        this.rearleglefttip.pitch = -0.2182F;
                        this.rearleglefttip_r1.pitch = 0.9599F;
                        this.rearfootleft.pitch = 1.4835F;

                        // Legs front
                        this.frontlegright.pitch = 0.7418F;
                        this.frontlegright_r1.pitch = 0.2618F;
                        this.frontlegrighttip.pitch = -1.0472F;
                        this.frontlegrighttip_r1.pitch = 0.2618F;
                        this.frontfootright.pitch = 1.4835F;

                        this.frontlegleft.pitch = 0.7418F;
                        this.frontlegleft_r1.pitch = 0.2618F;
                        this.frontleglefttip.pitch = -1.0472F;
                        this.frontleglefttip_r1.pitch = 0.2618F;
                        this.frontfootleft.pitch = 1.4835F;

                        // End of Flying Animation
                } else if (entity.getDataTracker().get(DragonEntity.CLIENT_END_FLYING)) {
                        float mediumSpeedSin = MathHelper.cos(
                                        12.566370614F * slowlyIncreasingFloat - (betweenFloater + 3.1415926535897F)); // 1to-1
                        if (mediumSpeedSin < 0.02F && mediumSpeedSin > -0.02F) {
                                endFlying = true;
                        }
                        if (endFlying == false) {
                                this.wingright.roll = mediumSpeedSin * 0.8F;
                                this.wingtipright.roll = this.wingright.roll * 0.8F;
                                this.wingleft.roll = -this.wingright.roll;
                                this.wingtipleft.roll = -this.wingtipright.roll;
                        } else {
                                endFlyingTicker++;
                                // Wings
                                if (endFlyingTicker <= 30) {
                                        startFlyingTicker = MathHelper.clamp(startFlyingTicker + 0.02327F, 0.0F,
                                                        0.6981F);
                                        // a*b=c
                                        this.wingright.roll = startFlyingTicker;
                                        this.wingleft.roll = -startFlyingTicker;
                                        this.wingtipleft.roll = (startFlyingTicker * 3.75017905F);
                                        this.wingtipright.roll = -(startFlyingTicker * 3.75017905F);

                                } else {
                                        entity.getDataTracker().set(DragonEntity.CLIENT_END_FLYING, false);
                                        endFlyingTicker = 0;
                                        startFlyingTicker = 0;
                                        endFlying = false;

                                }

                        }
                        if (this.wingright.roll <= -0.79F && mediumSpeedSin <= -0.999F && !this.isPlayingSound) {
                                this.playDragonWingSound(entity);
                        }
                        // Other End Flying Animation

                        // float mediumSpeedSin = MathHelper.cos(
                        // 12.566370614F * slowlyIncreasingFloat - (betweenFloater + 3.1415926535897F));
                        // // 1to-1
                        // // System.out.println(mediumSpeedSin);
                        // // System.out.println(mediumSpeedSin);
                        // if (mediumSpeedSin > 0.98F) {
                        // endFlying = true;
                        // }
                        // if (endFlying == false) {
                        // this.wingright.roll = mediumSpeedSin * 0.8F; // double pi inside cos does it
                        // make faster
                        // this.wingtipright.roll = this.wingright.roll * 0.8F;//
                        // MathHelper.cos(6.2831855F *
                        // // slowlyIncreasingFloat
                        // // * 1.2F);
                        // this.wingleft.roll = -this.wingright.roll;
                        // this.wingtipleft.roll = -this.wingtipright.roll;
                        // } else {
                        // endFlyingTicker++;
                        // // Wings
                        // if (endFlyingTicker <= 30) {
                        // startFlyingTicker = MathHelper.clamp(startFlyingTicker + (0.1019F / 30F),
                        // 0.0F,
                        // 0.1019F);
                        // this.wingright.roll = 0.8F - startFlyingTicker;
                        // this.wingleft.roll = -0.8F + startFlyingTicker;
                        // // 0.64F tip has to roll 1,978 divided by time 30 = -0,0659333333F
                        // // tip has to roll 3,258? divided by 30 = 0,1086
                        // // this.wingtipleft.roll=2.618F - (startFlyingTicker*2.1747546893427F);
                        // // this.wingtipright.roll=-2.618F +(startFlyingTicker*2.1747546893427F);

                        // this.wingtipleft.roll = -0.64F + (startFlyingTicker * 31.97252835F);
                        // this.wingtipright.roll = 0.64F - (startFlyingTicker * 31.97252835F);
                        // // a * b = c; 0,003396666 * b = c
                        // // this.wingleft.roll= -0.6981F + 0.0236F *
                        // MathHelper.method_24504(limbAngle,
                        // // 40.0F) * limbDistance;
                        // // this.wingtipleft.roll=2.618F;
                        // // this.wingright.roll=0.6981F - 0.0236F * MathHelper.method_24504(limbAngle,
                        // // 40.0F) * limbDistance;
                        // // this.wingtipright.roll=-2.618F;
                        // } else {
                        // entity.getDataTracker().set(DragonEntity.CLIENT_END_FLYING, false);
                        // endFlyingTicker = 0;
                        // endFlying = false;

                        // }

                        // }
                } else

                // Walk Animation
                if (!entity.isInSittingPose()) {

                        float testfloat = 32.0F;
                        this.rearlegright.pitch = -0.9599F
                                        + 0.3F * MathHelper.method_24504(limbAngle, testfloat) * limbDistance * 1.3F;
                        this.rearlegleft.pitch = -0.9599F
                                        - 0.3F * MathHelper.method_24504(limbAngle, testfloat) * limbDistance * 1.3F;

                        this.rearfootright.pitch = -0.3491F - 0.3F * MathHelper.method_24504(limbAngle, testfloat)
                                        * limbDistance * 1.3F * 0.9F;
                        this.rearfootleft.pitch = -0.3491F + 0.3F * MathHelper.method_24504(limbAngle, testfloat)
                                        * limbDistance * 1.3F * 0.9F;

                        this.frontlegright.pitch = 0.3491F
                                        - 0.3F * MathHelper.method_24504(limbAngle, testfloat) * limbDistance * 1.3F;
                        this.frontlegleft.pitch = 0.3491F
                                        + 0.3F * MathHelper.method_24504(limbAngle, testfloat) * limbDistance * 1.3F;

                        this.frontlegrighttip.pitch = -1.5708F + 0.3F * MathHelper.method_24504(limbAngle, testfloat)
                                        * limbDistance * 1.3F * 0.8F;
                        this.frontleglefttip.pitch = -1.5708F - 0.3F * MathHelper.method_24504(limbAngle, testfloat)
                                        * limbDistance * 1.3F * 0.8F;

                        this.frontfootright.pitch = 1.2217F + 0.3F * MathHelper.method_24504(limbAngle, testfloat)
                                        * limbDistance * 1.3F * 0.2F;
                        this.frontfootleft.pitch = 1.2217F - 0.3F * MathHelper.method_24504(limbAngle, testfloat)
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

                        // wings
                        this.wingleft.roll = -0.6981F
                                        + 0.0236F * MathHelper.method_24504(limbAngle, 40.0F) * limbDistance;
                        this.wingtipleft.roll = 2.618F;
                        this.wingright.roll = 0.6981F
                                        - 0.0236F * MathHelper.method_24504(limbAngle, 40.0F) * limbDistance;
                        this.wingtipright.roll = -2.618F;

                        // Jaw
                        this.jaw.pitch = 0.0F;
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
                        this.wingleft.roll = -0.6981F;
                        this.wingtipleft.roll = 2.618F;
                        this.wingright.roll = 0.6981F;
                        this.wingtipright.roll = -2.618F;
                }
                if (this.isPlayingSound == true) {
                        this.delayerSoundTick++;
                        if (this.delayerSoundTick >= 10) {
                                this.isPlayingSound = false;
                                this.delayerSoundTick = 0;
                        }
                }
        }

        private void playDragonWingSound(Entity entity) {
                entity.world.playSound(entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.ENTITY_ENDER_DRAGON_FLAP, entity.getSoundCategory(), 5.0F,
                                0.8F + entity.world.random.nextFloat() * 0.3F, false);
                this.isPlayingSound = true;
        }
}