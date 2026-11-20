package net.adventurez.entity.model;


import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.EnderWhaleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class EnderWhaleModel extends EntityModel<AdventureRenderState> {
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
        super(root);
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

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData4 = modelPartData.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 165).addBox(-19.0F, -16.0F, -26.0F, 38.0F, 36.0F, 52.0F),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition modelPartData6 = modelPartData4.addOrReplaceChild("frontLflipper1a", CubeListBuilder.create().texOffs(0, 100).addBox(0.0F, -2.5F, -9.0F, 25.0F, 5.0F, 20.0F),
                PartPose.offset(16.0F, 13.0F, 9.0F));
        PartDefinition modelPartData5 = modelPartData4.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(200, 185).addBox(-14.5F, -14.5F, 0.0F, 29.0F, 29.0F, 37.0F),
                PartPose.offset(0.0F, 2.0F, 15.5F));
        PartDefinition modelPartData8 = modelPartData5.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(360, 195).addBox(-11.5F, -11.5F, 0.0F, 23.0F, 23.0F, 33.0F),
                PartPose.offset(0.0F, 0.0F, 31.0F));
        PartDefinition modelPartData1 = modelPartData5.addOrReplaceChild("backLflipper1a", CubeListBuilder.create().texOffs(0, 50).addBox(0.0F, -2.5F, -5.0F, 18.0F, 5.0F, 13.0F),
                PartPose.offset(13.0F, 9.0F, 24.0F));
        modelPartData6.addOrReplaceChild("frontLflipper2b", CubeListBuilder.create().texOffs(80, 110).addBox(0.0F, 0.0F, -9.0F, 26.0F, 0.0F, 20.0F, true), PartPose.offset(12.0F, 0.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData5.addOrReplaceChild("backRflipper1a", CubeListBuilder.create().texOffs(0, 50).addBox(-18.0F, -2.5F, -5.0F, 18.0F, 5.0F, 13.0F, true),
                PartPose.offset(-13.0F, 9.0F, 24.0F));
        modelPartData2.addOrReplaceChild("backRflipper2b", CubeListBuilder.create().texOffs(70, 60).addBox(-20.0F, 0.0F, -6.0F, 21.0F, 0.0F, 13.0F), PartPose.offset(-8.0F, 0.0F, 1.0F));
        modelPartData4.addOrReplaceChild("eyeglowlayer", CubeListBuilder.create().texOffs(190, 80).addBox(-19.0F, -16.0F, -16.5F, 38.0F, 36.0F, 33.0F, new CubeDeformation(0.05F)),
                PartPose.offset(0.0F, 0.0F, -9.0F));
        PartDefinition modelPartData7 = modelPartData4.addOrReplaceChild("frontRflipper1a", CubeListBuilder.create().texOffs(0, 100).addBox(-25.0F, -2.5F, -9.0F, 25.0F, 5.0F, 20.0F, true),
                PartPose.offset(-16.0F, 13.0F, 9.0F));
        modelPartData7.addOrReplaceChild("frontRflipper2b", CubeListBuilder.create().texOffs(80, 110).addBox(-26.0F, 0.0F, -9.0F, 26.0F, 0.0F, 20.0F), PartPose.offset(-12.0F, 0.0F, 0.0F));
        PartDefinition modelPartData3 = modelPartData8.addOrReplaceChild("tailflipperM", CubeListBuilder.create().texOffs(1, 0).addBox(-14.0F, -3.0F, 0.0F, 28.0F, 6.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 29.0F));
        modelPartData3.addOrReplaceChild("tailflipperdetail", CubeListBuilder.create().texOffs(160, 0).addBox(-46.5F, 0.0F, 0.0F, 93.0F, 0.0F, 57.0F), PartPose.offset(0.0F, 0.0F, 5.0F));
        modelPartData3.addOrReplaceChild("tailflipperL", CubeListBuilder.create().texOffs(110, 0).addBox(0.0F, -2.0F, -5.0F, 28.0F, 4.0F, 16.0F, true), PartPose.offset(10.0F, 0.0F, 9.0F));
        modelPartData3.addOrReplaceChild("tailflipperR", CubeListBuilder.create().texOffs(110, 0).addBox(-28.0F, -2.0F, -5.0F, 28.0F, 4.0F, 16.0F), PartPose.offset(-10.0F, 0.0F, 9.0F));
        modelPartData1.addOrReplaceChild("backLflipper2b", CubeListBuilder.create().texOffs(70, 60).addBox(0.0F, 0.0F, -6.0F, 21.0F, 0.0F, 13.0F, true), PartPose.offset(8.0F, 0.0F, 1.0F));
        return LayerDefinition.create(modelData, 512, 256);

    }


    @Override
    public void setupAnim(AdventureRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.backLflipper2b.yRot = -0.41887902047863906F;
        this.backRflipper2b.yRot = 0.41887902047863906F;
        this.frontLflipper1a.yRot = -0.19198621771937624F;
        this.body3.xRot = 0.017453292519943295F;
        this.body3.yRot = 0.017453292519943295F;
        this.body1.xRot = 0.017453292519943295F;
        this.backLflipper1a.yRot = -0.19198621771937624F;
        this.backLflipper1a.zRot = 0.296705972839036F;
        this.body2.xRot = 0.017453292519943295F;
        this.body2.yRot = 0.017453292519943295F;
        this.tailflipperL.yRot = -0.4363323129985824F;
        this.tailflipperR.yRot = 0.4363323129985824F;
        this.backRflipper1a.yRot = 0.19198621771937624F;
        this.backRflipper1a.zRot = -0.296705972839036F;
        this.frontRflipper1a.yRot = 0.19198621771937624F;
        this.body1.yRot = headYaw * 0.0017453292F;

        slowlyIncreasingFloat += (animationProgress - oldAnimationProgress) * 0.005F;

        float slowSpeedSin = Mth.cos(12.566370614F * slowlyIncreasingFloat) * 0.34F;
        frontRflipper1a.zRot = 0.22689280275926282F * 0.3F + slowSpeedSin;
        frontLflipper1a.zRot = -0.22689280275926282F * 0.3F + -slowSpeedSin;

        backRflipper1a.zRot = 0.41887902047863906F * 0.3F + -slowSpeedSin * 0.7F;
        backLflipper1a.zRot = -0.41887902047863906F * 0.3F + slowSpeedSin * 0.7F;

        body2.y = 2.0F + slowSpeedSin * 3.0F;
        body3.y = slowSpeedSin * 3.6F;
        body2.xRot = slowSpeedSin * -0.08F;
        body3.xRot = slowSpeedSin * -0.096F;
        tailflipperM.y = slowSpeedSin * 4.2F;
        tailflipperM.xRot = -slowSpeedSin * 0.45F;

        oldAnimationProgress = animationProgress;
    }
}