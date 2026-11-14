package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;
import net.adventurez.entity.PiglinBeastEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class PiglinBeastModel<T extends PiglinBeastEntity> extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart ears;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart body;
    private final ModelPart flag;
    private final ModelPart flagCloth;
    private final ModelPart rightArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart leftArm;
    private final ModelPart hammer;

    public PiglinBeastModel(ModelPart root) {
        this.head = root.getChild("head");
        this.ears = this.head.getChild("ears");
        this.leftEar = this.ears.getChild("leftEar");
        this.rightEar = this.ears.getChild("rightEar");
        this.body = root.getChild("body");
        this.flag = this.body.getChild("flag");
        this.flagCloth = this.flag.getChild("flagCloth");
        this.rightArm = root.getChild("rightArm");
        this.rightLeg = root.getChild("rightLeg");
        this.leftLeg = root.getChild("leftLeg");
        this.leftArm = root.getChild("leftArm");
        this.hammer = this.leftArm.getChild("hammer");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(76, 72).cuboid(-5.0F, -10.0086F, -4.1305F, 10.0F, 9.0F, 10.0F, new Dilation(0.0F))
                .uv(42, 55).cuboid(0.0F, -18.0086F, -4.1305F, 0.0F, 16.0F, 17.0F, new Dilation(0.0F))
                .uv(0, 54).cuboid(-3.0F, -6.0086F, -5.1305F, 6.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

        ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create().uv(35, 38).mirrored().cuboid(-12.0F, -46.0F, -3.0F, 7.0F, 7.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 38.9914F, 1.8695F, 0.0F, -0.7854F, 0.0F));

        ModelPartData head_r2 = head.addChild("head_r2", ModelPartBuilder.create().uv(35, 38).cuboid(5.0F, -46.0F, -3.0F, 7.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 38.9914F, 1.8695F, 0.0F, 0.7854F, 0.0F));

        ModelPartData ears = head.addChild("ears", ModelPartBuilder.create(), ModelTransform.of(0.0F, -7.0086F, -0.1305F, 0.0F, 0.0F, -1.9199F));

        ModelPartData leftEar = ears.addChild("leftEar", ModelPartBuilder.create().uv(35, 51).cuboid(-5.2103F, -0.1205F, -3.1305F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 4.0086F, 1.1305F));

        ModelPartData rightEar = ears.addChild("rightEar", ModelPartBuilder.create(), ModelTransform.of(2.0F, -3.9914F, 0.1305F, 3.1416F, 0.0F, 0.6545F));

        ModelPartData bone7_r1 = rightEar.addChild("bone7_r1", ModelPartBuilder.create().uv(35, 51).cuboid(1.8794F, -46.1262F, -3.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-4.998F, 46.0534F, -0.8695F, 0.0F, 0.0F, -0.0436F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, 0.0F, -9.0F, 20.0F, 14.0F, 16.0F, new Dilation(0.0F))
                .uv(35, 30).cuboid(-9.0F, 11.0F, -9.0F, 20.0F, 5.0F, 16.0F, new Dilation(0.3F))
                .uv(96, 51).cuboid(-9.0F, 14.0F, -9.0F, 20.0F, 8.0F, 0.0F, new Dilation(0.0F))
                .uv(90, 91).cuboid(-9.0F, 14.0F, 7.0F, 20.0F, 8.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 75).cuboid(11.0F, 14.0F, -9.0F, 0.0F, 8.0F, 16.0F, new Dilation(0.0F))
                .uv(42, 72).cuboid(-9.0F, 14.0F, -9.0F, 0.0F, 8.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -3.0F, 1.0F));

        ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(52, 51).cuboid(-8.0F, -32.25F, -3.0F, 16.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 25.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        ModelPartData flag = body.addChild("flag", ModelPartBuilder.create().uv(54, 96).cuboid(-1.0F, -33.8093F, 0.5517F, 2.0F, 29.0F, 2.0F, new Dilation(0.0F))
                .uv(72, 0).cuboid(-2.0F, -37.8775F, -0.9306F, 4.0F, 5.0F, 18.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.0F, -4.8093F, -0.4483F, 4.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 12.0F, 7.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData flagCloth = flag.addChild("flagCloth", ModelPartBuilder.create().uv(0, 16).cuboid(0.0F, -0.6136F, -6.8755F, 0.0F, 24.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -33.8775F, 9.0694F, 0.1745F, 0.0F, 0.0F));

        ModelPartData rightArm = modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(68, 91).cuboid(-3.9115F, -2.3282F, -3.0F, 5.0F, 24.0F, 6.0F, new Dilation(-0.02F))
                .uv(106, 64).cuboid(-4.9115F, 2.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new Dilation(0.0F))
                .uv(98, 0).cuboid(-4.9115F, 7.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new Dilation(0.0F))
                .uv(56, 0).cuboid(-4.9115F, 14.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new Dilation(0.0F))
                .uv(26, 110).cuboid(-0.9115F, -3.3282F, -4.0F, 3.0F, 6.0F, 8.0F, new Dilation(0.0F))
                .uv(108, 111).cuboid(-0.9115F, 10.6718F, -4.0F, 3.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-9.0F, -10.0F, 1.0F, 0.0F, 0.0F, 0.2618F));

        ModelPartData rightLeg = modelPartData.addChild("rightLeg", ModelPartBuilder.create().uv(0, 103).cuboid(-3.0F, 0.0F, -4.0F, 6.0F, 13.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.9F, 11.0F, 0.0F));

        ModelPartData leftLeg = modelPartData.addChild("leftLeg", ModelPartBuilder.create().uv(90, 99).cuboid(-3.0F, 0.0F, -4.0F, 6.0F, 13.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(3.9F, 11.0F, 0.0F));

        ModelPartData leftArm = modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(68, 91).mirrored().cuboid(-1.0885F, -2.3282F, -3.0F, 5.0F, 24.0F, 6.0F, new Dilation(-0.02F)).mirrored(false)
                .uv(56, 0).mirrored().cuboid(-2.0885F, 14.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
                .uv(108, 111).mirrored().cuboid(-2.0885F, 10.6718F, -4.0F, 3.0F, 4.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
                .uv(98, 0).mirrored().cuboid(-2.0885F, 7.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
                .uv(106, 64).mirrored().cuboid(-2.0885F, 2.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
                .uv(26, 110).mirrored().cuboid(-2.0885F, -3.3282F, -4.0F, 3.0F, 6.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(9.0F, -10.0F, 1.0F, 0.0F, 0.0F, -0.2618F));

        ModelPartData hammer = leftArm.addChild("hammer", ModelPartBuilder.create().uv(0, 30).cuboid(-1.8774F, -2.0694F, -19.0F, 3.0F, 3.0F, 29.0F, new Dilation(0.0F))
                .uv(35, 30).cuboid(-2.3774F, -2.5694F, 10.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(91, 23).cuboid(-4.8774F, 1.9306F, -28.0F, 9.0F, 10.0F, 9.0F, new Dilation(0.0F))
                .uv(20, 91).cuboid(-2.8774F, -4.0694F, -27.0F, 5.0F, 7.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0012F, 19.7412F, 0.0F));

        ModelPartData leftArm_r1 = hammer.addChild("leftArm_r1", ModelPartBuilder.create().uv(91, 23).cuboid(5.1226F, 19.9306F, 18.0F, 9.0F, 10.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, 17.0F, -1.0F, 3.1416F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.0079453292F;
        this.head.pitch = headPitch * 0.0027453292F;

        float l = animationProgress * 0.1F + limbAngle * 0.5F;
        float m = 0.08F + limbDistance * 0.4F;
        this.leftEar.roll = (float) (-Math.PI / 6) - MathHelper.cos(l * 1.2F) * m;
        this.rightEar.roll = (float) (Math.PI / 6) + MathHelper.cos(l) * m;

        // Legs
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        // Flag
        this.flagCloth.roll = MathHelper.cos(animationProgress / 10) / 4;
        // Arms
        this.rightArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
        this.leftArm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;

        float k = MathHelper.sin(entity.handSwingProgress * 3.1415927F);
        if (k > 0) {
            this.leftArm.pitch = -k;
        }

        float handUp = entity.getDataTracker().get(PiglinBeastEntity.LEAD_ARM);
        if (handUp > 0.0F) {
            float g = handUp;
            float h = MathHelper.sin(g * 3.1415927F);
            float i = MathHelper.sin(handUp * 3.1415927F) * -(this.head.pitch - 0.7F) * 0.75F;
            this.rightArm.pitch = (float) ((double) this.rightArm.pitch - ((double) h * 1.2D + (double) i));
        }
    }

}