package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.PiglinBeastEntity;
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
public class PiglinBeastModel<T extends PiglinBeastEntity> extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftHorn;
    private final ModelPart rightHorn;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart flagHolder;
    private final ModelPart flag;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public PiglinBeastModel(ModelPart root) {
        this.head = root.getChild("head");
        this.rightEar = this.head.getChild("rightEar");
        this.leftEar = this.head.getChild("leftEar");
        this.rightHorn = this.head.getChild("rightHorn");
        this.leftHorn = this.head.getChild("leftHorn");
        this.body = root.getChild("body");
        this.flagHolder = this.body.getChild("flagHolder");
        this.flag = this.flagHolder.getChild("flag");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("head", ModelPartBuilder.create().uv(74, 0).cuboid(-5.0F, -11.0F, -5.0F, 10.0F, 9.0F, 10.0F).uv(57, 11)
                .cuboid(3.0F, -5.0F, -6.0F, 3.0F, 2.0F, 1.0F).uv(48, 56).cuboid(-6.0F, -5.0F, -6.0F, 3.0F, 2.0F, 1.0F).uv(0, 38).cuboid(-3.0F, -7.0F, -6.0F, 6.0F, 4.0F, 1.0F),
                ModelTransform.pivot(0.0F, -16.0F, -1.0F));
        modelPartData1.addChild("leftHorn", ModelPartBuilder.create().uv(51, 30).cuboid(-4.7379F, 1.2403F, 0.62F, 6.0F, 2.0F, 1.0F), ModelTransform.pivot(-3.0F, -6.0F, -7.0F));
        modelPartData1.addChild("rightHorn", ModelPartBuilder.create().uv(0, 14).cuboid(-1.8264F, 1.6629F, -0.3197F, 6.0F, 2.0F, 1.0F), ModelTransform.pivot(3.0F, -7.0F, -6.0F));
        modelPartData1.addChild("hair",
                ModelPartBuilder.create().uv(61, 32).cuboid(-3.0F, -8.0F, 6.0F, 0.0F, 4.0F, 1.0F).uv(0, 51).cuboid(-3.0F, -7.0F, 8.0F, 0.0F, 3.0F, 1.0F).uv(10, 51)
                        .cuboid(-3.0F, -9.0F, 4.0F, 0.0F, 5.0F, 1.0F).uv(50, 46).cuboid(-3.0F, -6.0F, 10.0F, 0.0F, 2.0F, 1.0F).uv(48, 48).cuboid(-3.0F, 1.0F, 11.0F, 0.0F, 1.0F, 3.0F).uv(8, 45)
                        .cuboid(-3.0F, -1.0F, 11.0F, 0.0F, 1.0F, 2.0F).uv(13, 12).cuboid(-3.0F, -3.0F, 11.0F, 0.0F, 1.0F, 2.0F).uv(0, 49).cuboid(-3.0F, -4.0F, 11.0F, 0.0F, 1.0F, 1.0F).uv(8, 41)
                        .cuboid(-3.0F, -2.0F, 11.0F, 0.0F, 1.0F, 3.0F).uv(0, 40).cuboid(-3.0F, 0.0F, 11.0F, 0.0F, 1.0F, 4.0F).uv(0, 39).cuboid(-3.0F, 2.0F, 11.0F, 0.0F, 1.0F, 4.0F).uv(8, 40)
                        .cuboid(-3.0F, 3.0F, 11.0F, 0.0F, 1.0F, 3.0F).uv(13, 37).cuboid(-3.0F, 4.0F, 11.0F, 0.0F, 1.0F, 1.0F).uv(61, 1).cuboid(-3.0F, -8.0F, 2.0F, 0.0F, 4.0F, 1.0F).uv(48, 46)
                        .cuboid(-3.0F, -6.0F, 3.0F, 0.0F, 2.0F, 1.0F).uv(0, 29).cuboid(-3.0F, -7.0F, 5.0F, 0.0F, 3.0F, 1.0F).uv(14, 14).cuboid(-3.0F, -6.0F, 7.0F, 0.0F, 2.0F, 1.0F).uv(2, 49)
                        .cuboid(-3.0F, -5.0F, 9.0F, 0.0F, 1.0F, 1.0F),
                ModelTransform.pivot(3.0F, -7.0F, -6.0F));
        modelPartData1.addChild("leftEar", ModelPartBuilder.create().uv(57, 0).cuboid(-5.0F, -1.0F, -3.0F, 6.0F, 1.0F, 6.0F), ModelTransform.pivot(-5.0F, -8.0F, 0.0F));
        modelPartData1.addChild("rightEar", ModelPartBuilder.create().uv(48, 49).cuboid(-5.0F, -0.0603F, -3.0F, 6.0F, 1.0F, 6.0F), ModelTransform.pivot(5.0F, -8.0F, 0.0F));
        ModelPartData modelPartData2 = modelPartData.addChild("body",
                ModelPartBuilder.create().uv(47, 56).cuboid(-8.0F, 12.0F, -8.0F, 18.0F, 3.0F, 13.0F).uv(0, 30).cuboid(-8.0F, 10.0F, -9.0F, 18.0F, 2.0F, 15.0F).uv(0, 0)
                        .cuboid(-9.0F, -3.0F, -10.0F, 20.0F, 13.0F, 17.0F).uv(74, 19).cuboid(-9.0F, 10.0F, -10.0F, 20.0F, 10.0F, 0.0F).uv(72, 72).cuboid(-9.0F, 10.0F, 7.0F, 20.0F, 10.0F, 0.0F)
                        .uv(29, 55).cuboid(11.0F, 10.0F, -10.0F, 0.0F, 10.0F, 17.0F).uv(29, 55).cuboid(-9.0F, 10.0F, -10.0F, 0.0F, 10.0F, 17.0F).uv(52, 33)
                        .cuboid(-8.0F, -5.0F, -7.0F, 18.0F, 2.0F, 14.0F).uv(0, 47).cuboid(-8.0F, -15.0F, -5.0F, 18.0F, 10.0F, 12.0F),
                ModelTransform.pivot(-1.0F, -3.0F, 1.0F));
        ModelPartData modelPartData3 = modelPartData2.addChild("flagHolder", ModelPartBuilder.create().uv(76, 95).cuboid(-2.0F, -37.9319F, 0.4824F, 2.0F, 29.0F, 2.0F).uv(46, 72)
                .cuboid(-3.0F, -42.0F, -1.0F, 4.0F, 5.0F, 18.0F).uv(0, 0).cuboid(-3.0F, -8.9319F, -0.5176F, 4.0F, 10.0F, 4.0F), ModelTransform.pivot(4.0F, 8.0F, 6.0F));
        modelPartData3.addChild("flag", ModelPartBuilder.create().uv(0, 79).cuboid(0.0F, -0.6136F, -6.8755F, 0.0F, 24.0F, 14.0F), ModelTransform.pivot(-1.0F, -38.0F, 9.0F));
        modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(28, 93).cuboid(-3.3939F, -1.3963F, -3.0F, 5.0F, 24.0F, 6.0F), ModelTransform.pivot(-9.0F, -15.0F, 2.0F));
        modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(84, 89).cuboid(-0.899F, -1.1716F, -3.0F, 5.0F, 25.0F, 6.0F).uv(101, 71).cuboid(0.8422F, 18.7943F, -6.0F, 2.0F, 3.0F, 11.0F)
                .uv(51, 39).cuboid(-0.1578F, 18.7943F, -8.0F, 3.0F, 4.0F, 2.0F).uv(0, 30).cuboid(-0.1578F, 17.7943F, -11.0F, 4.0F, 5.0F, 3.0F).uv(0, 69)
                .cuboid(-1.1578F, 16.7943F, -28.0F, 6.0F, 7.0F, 17.0F).uv(51, 33).cuboid(-0.1578F, 17.7943F, -29.0F, 4.0F, 5.0F, 1.0F).uv(64, 13).cuboid(1.3951F, 15.9987F, -14.0F, 3.0F, 1.0F, 1.0F)
                .uv(57, 7).cuboid(0.3951F, 15.9987F, -21.0F, 1.0F, 1.0F, 3.0F).uv(12, 0).cuboid(1.361F, 14.2576F, -27.0F, 1.0F, 3.0F, 1.0F).uv(0, 0)
                .cuboid(3.5517F, 13.8093F, -24.0F, 1.0F, 3.0F, 1.0F).uv(11, 30).cuboid(2.5858F, 14.5505F, -19.0F, 1.0F, 2.0F, 1.0F).uv(57, 0).cuboid(4.9319F, 17.5176F, -24.0F, 2.0F, 1.0F, 1.0F)
                .uv(48, 52).cuboid(4.6731F, 18.4836F, -16.0F, 2.0F, 1.0F, 1.0F).uv(0, 56).cuboid(4.3801F, 19.7083F, -25.0F, 4.0F, 1.0F, 1.0F).uv(64, 10)
                .cuboid(4.0F, 21.6401F, -24.0F, 3.0F, 1.0F, 1.0F).uv(0, 52).cuboid(3.5F, 21.6401F, -17.0F, 2.0F, 1.0F, 3.0F).uv(57, 2).cuboid(3.3449F, 23.572F, -26.0F, 1.0F, 2.0F, 1.0F).uv(56, 56)
                .cuboid(0.4471F, 23.7955F, -24.0F, 1.0F, 2.0F, 1.0F).uv(0, 47).cuboid(1.413F, 23.7F, -19.0F, 1.0F, 2.0F, 1.0F).uv(8, 48).cuboid(2.3789F, 23.3131F, -16.0F, 1.0F, 2.0F, 1.0F).uv(62, 7)
                .cuboid(-2.9331F, 21.0872F, -27.0F, 3.0F, 1.0F, 1.0F).uv(57, 14).cuboid(-3.3813F, 18.8965F, -23.0F, 3.0F, 1.0F, 1.0F).uv(48, 49).cuboid(-2.1225F, 17.9306F, -16.0F, 2.0F, 1.0F, 1.0F)
                .uv(0, 47).cuboid(-2.3813F, 19.8965F, -18.0F, 2.0F, 1.0F, 4.0F), ModelTransform.pivot(8.0F, -15.0F, 2.0F));
        modelPartData.addChild("leftLeg", ModelPartBuilder.create().uv(96, 49).cuboid(-3.0F, 0.0F, -4.0F, 6.0F, 13.0F, 7.0F), ModelTransform.pivot(-3.9F, 11.0F, 0.0F));
        modelPartData.addChild("rightLeg", ModelPartBuilder.create().uv(50, 95).cuboid(-3.0F, 0.0F, -4.0F, 6.0F, 13.0F, 7.0F), ModelTransform.pivot(3.9F, 11.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.0079453292F;
        this.head.pitch = headPitch * 0.0027453292F;
        this.leftHorn.yaw = -0.3491F;
        this.leftHorn.roll = 1.1345F;
        this.rightHorn.yaw = 0.3491F;
        this.rightHorn.roll = -1.1345F;
        this.leftEar.roll = -1.1345F;
        this.rightEar.roll = -1.9199F;
        this.leftArm.roll = 0.2618F;
        this.rightArm.roll = -0.2618F;
        this.flagHolder.pitch = -0.2618F;
        this.flag.pitch = 0.1745F;
        // Animation
        // Legs
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        // Flag
        flag.roll = MathHelper.cos(animationProgress / 10) / 4;
        // Arms
        this.rightArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
        this.leftArm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;

        float attackTick = entity.getDataTracker().get(PiglinBeastEntity.ATTACK_TICK_VISUAL);
        if (attackTick > 0F) {
            float g = attackTick;
            float h = MathHelper.sin(g * 3.1415927F);
            float i = MathHelper.sin(attackTick * 3.1415927F) * -(this.head.pitch - 0.7F) * 0.75F;
            rightArm.pitch = (float) ((double) rightArm.pitch - ((double) h * 1.2D + (double) i));
            rightArm.roll += MathHelper.sin(attackTick * 3.1415927F) * -0.4F - limbDistance;
        }

        float handUp = entity.getDataTracker().get(PiglinBeastEntity.LEAD_ARM);
        if (handUp > 0.0F) {
            float g = handUp;
            float h = MathHelper.sin(g * 3.1415927F);
            float i = MathHelper.sin(handUp * 3.1415927F) * -(this.head.pitch - 0.7F) * 0.75F;
            leftArm.pitch = (float) ((double) leftArm.pitch - ((double) h * 1.2D + (double) i));
        }
        // Ears
        rightEar.roll = rightEar.roll + ((MathHelper.cos((limbAngle * 0.6662F + 3.1415927F) / 3) * limbDistance) / 2);
        leftEar.roll = leftEar.roll - ((MathHelper.cos((limbAngle * 0.6662F + 3.1415927F) / 3) * limbDistance) / 2);
    }

}