package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.EnderWhaleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class EnderWhaleModel<T extends EnderWhaleEntity> extends CompositeEntityModel<T> {
    private final ModelPart body1;
    private final ModelPart body2;
    private final ModelPart frontLflipper1a;
    private final ModelPart frontRflipper1a;
    private final ModelPart body3;
    private final ModelPart backLflipper1a;
    private final ModelPart backRflipper1a;
    private final ModelPart tailflipperM;
    private final ModelPart tailflipperL;
    private final ModelPart tailflipperR;
    private final ModelPart backLflipper2b;
    private final ModelPart backRflipper2b;

    private float oldAnimationProgress;
    private float slowlyIncreasingFloat;

    public EnderWhaleModel(ModelPart root) {
        this.body1 = root.getChild("body1");
        this.frontRflipper1a = this.body1.getChild("frontRflipper1a");
        this.body2 = this.body1.getChild("body2");
        this.backRflipper1a = this.body2.getChild("backRflipper1a");
        this.frontLflipper1a = this.body1.getChild("frontLflipper1a");
        this.backLflipper1a = this.body2.getChild("backLflipper1a");
        this.body3 = this.body2.getChild("body3");
        this.tailflipperM = this.body3.getChild("tailflipperM");
        this.backLflipper2b = this.backLflipper1a.getChild("backLflipper2b");
        this.backRflipper2b = this.backRflipper1a.getChild("backRflipper2b");
        this.tailflipperR = this.tailflipperM.getChild("tailflipperR");
        this.tailflipperL = this.tailflipperM.getChild("tailflipperL");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData4 = modelPartData.addChild("body1", ModelPartBuilder.create().uv(0, 165).cuboid(-19.0F, -16.0F, -26.0F, 38.0F, 36.0F, 52.0F),
                ModelTransform.pivot(0.0F, 3.0F, 0.0F));
        ModelPartData modelPartData6 = modelPartData4.addChild("frontLflipper1a", ModelPartBuilder.create().uv(0, 100).cuboid(0.0F, -2.5F, -9.0F, 25.0F, 5.0F, 20.0F),
                ModelTransform.pivot(16.0F, 13.0F, 9.0F));
        ModelPartData modelPartData5 = modelPartData4.addChild("body2", ModelPartBuilder.create().uv(200, 185).cuboid(-14.5F, -14.5F, 0.0F, 29.0F, 29.0F, 37.0F),
                ModelTransform.pivot(0.0F, 2.0F, 15.5F));
        ModelPartData modelPartData8 = modelPartData5.addChild("body3", ModelPartBuilder.create().uv(360, 195).cuboid(-11.5F, -11.5F, 0.0F, 23.0F, 23.0F, 33.0F),
                ModelTransform.pivot(0.0F, 0.0F, 31.0F));
        ModelPartData modelPartData1 = modelPartData5.addChild("backLflipper1a", ModelPartBuilder.create().uv(0, 50).cuboid(0.0F, -2.5F, -5.0F, 18.0F, 5.0F, 13.0F),
                ModelTransform.pivot(13.0F, 9.0F, 24.0F));
        modelPartData6.addChild("frontLflipper2b", ModelPartBuilder.create().uv(80, 110).cuboid(0.0F, 0.0F, -9.0F, 26.0F, 0.0F, 20.0F, true), ModelTransform.pivot(12.0F, 0.0F, 0.0F));
        ModelPartData modelPartData2 = modelPartData5.addChild("backRflipper1a", ModelPartBuilder.create().uv(0, 50).cuboid(-18.0F, -2.5F, -5.0F, 18.0F, 5.0F, 13.0F, true),
                ModelTransform.pivot(-13.0F, 9.0F, 24.0F));
        modelPartData2.addChild("backRflipper2b", ModelPartBuilder.create().uv(70, 60).cuboid(-20.0F, 0.0F, -6.0F, 21.0F, 0.0F, 13.0F), ModelTransform.pivot(-8.0F, 0.0F, 1.0F));
        modelPartData4.addChild("eyeglowlayer", ModelPartBuilder.create().uv(190, 80).cuboid(-19.0F, -16.0F, -16.5F, 38.0F, 36.0F, 33.0F, new Dilation(0.05F)),
                ModelTransform.pivot(0.0F, 0.0F, -9.0F));
        ModelPartData modelPartData7 = modelPartData4.addChild("frontRflipper1a", ModelPartBuilder.create().uv(0, 100).cuboid(-25.0F, -2.5F, -9.0F, 25.0F, 5.0F, 20.0F, true),
                ModelTransform.pivot(-16.0F, 13.0F, 9.0F));
        modelPartData7.addChild("frontRflipper2b", ModelPartBuilder.create().uv(80, 110).cuboid(-26.0F, 0.0F, -9.0F, 26.0F, 0.0F, 20.0F), ModelTransform.pivot(-12.0F, 0.0F, 0.0F));
        ModelPartData modelPartData3 = modelPartData8.addChild("tailflipperM", ModelPartBuilder.create().uv(1, 0).cuboid(-14.0F, -3.0F, 0.0F, 28.0F, 6.0F, 16.0F),
                ModelTransform.pivot(0.0F, 0.0F, 29.0F));
        modelPartData3.addChild("tailflipperdetail", ModelPartBuilder.create().uv(160, 0).cuboid(-46.5F, 0.0F, 0.0F, 93.0F, 0.0F, 57.0F), ModelTransform.pivot(0.0F, 0.0F, 5.0F));
        modelPartData3.addChild("tailflipperL", ModelPartBuilder.create().uv(110, 0).cuboid(0.0F, -2.0F, -5.0F, 28.0F, 4.0F, 16.0F, true), ModelTransform.pivot(10.0F, 0.0F, 9.0F));
        modelPartData3.addChild("tailflipperR", ModelPartBuilder.create().uv(110, 0).cuboid(-28.0F, -2.0F, -5.0F, 28.0F, 4.0F, 16.0F), ModelTransform.pivot(-10.0F, 0.0F, 9.0F));
        modelPartData1.addChild("backLflipper2b", ModelPartBuilder.create().uv(70, 60).cuboid(0.0F, 0.0F, -6.0F, 21.0F, 0.0F, 13.0F, true), ModelTransform.pivot(8.0F, 0.0F, 1.0F));
        return TexturedModelData.of(modelData, 512, 256);

    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body1);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.backLflipper2b.yaw = -0.41887902047863906F;
        this.backRflipper2b.yaw = 0.41887902047863906F;
        this.frontLflipper1a.yaw = -0.19198621771937624F;
        this.body3.pitch = 0.017453292519943295F;
        this.body3.yaw = 0.017453292519943295F;
        this.body1.pitch = 0.017453292519943295F;
        this.backLflipper1a.yaw = -0.19198621771937624F;
        this.backLflipper1a.roll = 0.296705972839036F;
        this.body2.pitch = 0.017453292519943295F;
        this.body2.yaw = 0.017453292519943295F;
        this.tailflipperL.yaw = -0.4363323129985824F;
        this.tailflipperR.yaw = 0.4363323129985824F;
        this.backRflipper1a.yaw = 0.19198621771937624F;
        this.backRflipper1a.roll = -0.296705972839036F;
        this.frontRflipper1a.yaw = 0.19198621771937624F;
        this.body1.yaw = headYaw * 0.0017453292F;

        slowlyIncreasingFloat += (animationProgress - oldAnimationProgress) * 0.005F;

        float slowSpeedSin = MathHelper.cos(12.566370614F * slowlyIncreasingFloat) * 0.34F;
        frontRflipper1a.roll = 0.22689280275926282F * 0.3F + slowSpeedSin;
        frontLflipper1a.roll = -0.22689280275926282F * 0.3F + -slowSpeedSin;

        backRflipper1a.roll = 0.41887902047863906F * 0.3F + -slowSpeedSin * 0.7F;
        backLflipper1a.roll = -0.41887902047863906F * 0.3F + slowSpeedSin * 0.7F;

        body2.pivotY = 2.0F + slowSpeedSin * 3.0F;
        body3.pivotY = slowSpeedSin * 3.6F;
        body2.pitch = slowSpeedSin * -0.08F;
        body3.pitch = slowSpeedSin * -0.096F;
        tailflipperM.pivotY = slowSpeedSin * 4.2F;
        tailflipperM.pitch = -slowSpeedSin * 0.45F;

        oldAnimationProgress = animationProgress;
    }
}