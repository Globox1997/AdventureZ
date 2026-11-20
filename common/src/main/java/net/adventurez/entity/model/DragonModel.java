package net.adventurez.entity.model;

import net.adventurez.entity.DragonEntity;
import net.adventurez.entity.render.state.DragonRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DragonModel extends EntityModel<DragonRenderState> {
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

    public DragonModel(ModelPart root) {
        super(root);
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

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 168).addBox(-12.0F, -11.0F, -32.0F, 24.0F, 24.0F, 64.0F).texOffs(116, 256)
                .addBox(-1.0F, -17.0F, -26.0F, 2.0F, 6.0F, 12.0F).texOffs(60, 256).addBox(-1.0F, -17.0F, 14.0F, 2.0F, 6.0F, 12.0F), PartPose.offset(0.0F, -8.0F, -2.0F));
        modelPartData1.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(252, 196).addBox(-15.0F, -11.0F, 0.0F, 3.0F, 12.0F, 14.0F).texOffs(247, 86).addBox(12.0F, -11.0F, 0.0F, 3.0F, 12.0F, 14.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        modelPartData1.addOrReplaceChild("spike", CubeListBuilder.create().texOffs(88, 256).addBox(-1.0F, -6.0F, -5.0F, 2.0F, 6.0F, 12.0F), PartPose.offset(0.0F, -11.0F, -1.0F));
        PartDefinition modelPartData2 = modelPartData.addOrReplaceChild("wingLeft",
                CubeListBuilder.create().texOffs(168, 0).addBox(-56.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F).texOffs(112, 112).addBox(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F),
                PartPose.offset(-12.0F, -18.0F, -24.0F));
        modelPartData2.addOrReplaceChild("wingTipLeft", CubeListBuilder.create().texOffs(168, 24).addBox(-56.0F, -3.0F, -2.0F, 56.0F, 4.0F, 4.0F).texOffs(0, 112).addBox(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F),
                PartPose.offset(-56.0F, 0.0F, 0.0F));
        PartDefinition modelPartData3 = modelPartData.addOrReplaceChild("wingRight",
                CubeListBuilder.create().texOffs(112, 168).addBox(0.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F).texOffs(0, 56).addBox(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F),
                PartPose.offset(12.0F, -18.0F, -24.0F));
        modelPartData3.addOrReplaceChild("wingTipRight", CubeListBuilder.create().texOffs(168, 16).addBox(0.0F, -3.0F, -2.0F, 56.0F, 4.0F, 4.0F).texOffs(0, 0).addBox(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F),
                PartPose.offset(56.0F, 0.0F, 0.0F));
        PartDefinition modelPartData4 = modelPartData.addOrReplaceChild("rearLegRight", CubeListBuilder.create().texOffs(0, 40).addBox(-8.0F, -4.2322F, -6.0798F, 12.0F, 28.0F, 12.0F),
                PartPose.offset(16.0F, -5.0F, 22.0F));
        PartDefinition modelPartData5 = modelPartData4.addOrReplaceChild("rearLegRightTip", CubeListBuilder.create(), PartPose.offset(-1.0F, 20.327F, 1.5816F));
        modelPartData5.addOrReplaceChild("rearLegRightTip_r1", CubeListBuilder.create().texOffs(176, 184).addBox(-6.0F, -7.5F, -7.0F, 10.0F, 32.0F, 11.0F), PartPose.offset(0.0F, 1.1105F, -0.517F));
        modelPartData5.addOrReplaceChild("rearFootRight", CubeListBuilder.create().texOffs(168, 66).addBox(-5.0F, 0.0F, -15.0F, 12.0F, 6.0F, 19.0F), PartPose.offset(-2.0F, 16.0502F, 14.6409F));
        PartDefinition modelPartData6 = modelPartData.addOrReplaceChild("rearLegLeft", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.1472F, -5.6383F, 12.0F, 28.0F, 12.0F),
                PartPose.offset(-16.0F, -5.0F, 21.0F));
        PartDefinition modelPartData7 = modelPartData6.addOrReplaceChild("rearLegLefttip", CubeListBuilder.create(), PartPose.offset(2.0F, 19.9876F, 2.1028F));
        modelPartData7.addOrReplaceChild("rearLegLefttip_r1", CubeListBuilder.create().texOffs(0, 80).addBox(-4.0F, -6.6164F, -6.486F, 10.0F, 32.0F, 11.0F), PartPose.offset(-1.0F, 0.6549F, -0.4294F));
        modelPartData7.addOrReplaceChild("rearFootLeft", CubeListBuilder.create().texOffs(0, 182).addBox(-6.0F, 0.3F, -14.7F, 12.0F, 6.0F, 19.0F), PartPose.offset(0.0F, 15.6549F, 15.0706F));
        PartDefinition modelPartData8 = modelPartData.addOrReplaceChild("frontLegRight", CubeListBuilder.create(), PartPose.offset(12.0F, -3.0F, -25.0F));
        modelPartData8.addOrReplaceChild("frontLegRight_r1", CubeListBuilder.create().texOffs(252, 32).addBox(-4.0F, -38.2535F, -12.1057F, 8.0F, 24.0F, 8.0F), PartPose.offset(0.0F, 30.8475F, 16.5052F));
        PartDefinition modelPartData9 = modelPartData8.addOrReplaceChild("frontLegRighttip", CubeListBuilder.create(), PartPose.offset(0.0F, 16.207F, -0.1549F));
        modelPartData9.addOrReplaceChild("frontLegRighttip_r1", CubeListBuilder.create().texOffs(0, 256).addBox(-2.9F, 3.0F, -39.0F, 7.0F, 17.0F, 8.0F), PartPose.offset(-1.0F, -16.207F, 32.1549F));
        modelPartData9.addOrReplaceChild("frontFootRight",
                CubeListBuilder.create().texOffs(256, 256).addBox(-4.8F, 0.0F, -15.1665F, 7.0F, 5.0F, 8.0F).texOffs(240, 168).addBox(-4.8F, -1.0F, -7.1665F, 7.0F, 6.0F, 8.0F),
                PartPose.offset(1.0F, 10.793F, 3.1549F));
        PartDefinition modelPartData10 = modelPartData.addOrReplaceChild("frontLegLeft", CubeListBuilder.create(), PartPose.offset(-12.0F, -3.0F, -25.0F));
        modelPartData10.addOrReplaceChild("frontLegLeft_r1", CubeListBuilder.create().texOffs(224, 238).addBox(-4.0F, -38.2535F, -12.1057F, 8.0F, 24.0F, 8.0F), PartPose.offset(0.0F, 30.8475F, 16.5052F));
        PartDefinition modelPartData11 = modelPartData10.addOrReplaceChild("frontLegLefttip", CubeListBuilder.create(), PartPose.offset(0.0F, 15.4023F, 0.2817F));
        modelPartData11.addOrReplaceChild("frontLegLefttip_r1", CubeListBuilder.create().texOffs(30, 256).addBox(-4.9F, 4.0F, -37.0F, 7.0F, 17.0F, 8.0F), PartPose.offset(1.0F, -16.4023F, 30.7183F));
        modelPartData11.addOrReplaceChild("frontFootLeft",
                CubeListBuilder.create().texOffs(256, 243).addBox(-3.8F, -0.3F, -14.4825F, 7.0F, 5.0F, 8.0F).texOffs(144, 256).addBox(-3.8F, -1.3F, -6.4825F, 7.0F, 6.0F, 8.0F),
                PartPose.offset(0.0F, 12.1611F, 3.9136F));
        PartDefinition modelPartData12 = modelPartData.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(218, 184).addBox(-7.0F, -8.0F, -10.0F, 14.0F, 16.0F, 10.0F).texOffs(168, 66).addBox(-1.0F, -12.0F, -8.0F, 2.0F, 5.0F, 6.0F),
                PartPose.offset(0.0F, -10.0F, -34.0F));
        PartDefinition modelPartData13 = modelPartData12.addOrReplaceChild("neck2",
                CubeListBuilder.create().texOffs(0, 207).addBox(-7.0F, -7.0F, -10.0F, 14.0F, 15.0F, 10.0F).texOffs(168, 32).addBox(-1.0F, -11.0F, -8.0F, 2.0F, 6.0F, 6.0F),
                PartPose.offset(0.0F, -2.0F, -10.0F));
        PartDefinition modelPartData14 = modelPartData13.addOrReplaceChild("neck3",
                CubeListBuilder.create().texOffs(112, 207).addBox(-7.0F, -7.0F, -10.0F, 14.0F, 14.0F, 10.0F).texOffs(39, 117).addBox(-1.0F, -11.0F, -8.0F, 2.0F, 7.0F, 6.0F),
                PartPose.offset(0.0F, -1.0F, -10.0F));
        PartDefinition modelPartData15 = modelPartData14.addOrReplaceChild("neck4",
                CubeListBuilder.create().texOffs(176, 238).addBox(-7.0F, -6.0F, -10.0F, 14.0F, 12.0F, 10.0F).texOffs(0, 182).addBox(-1.0F, -10.0F, -8.0F, 2.0F, 5.0F, 6.0F),
                PartPose.offset(0.0F, -2.0F, -10.0F));
        PartDefinition modelPartData16 = modelPartData15.addOrReplaceChild("head", CubeListBuilder.create().texOffs(112, 184).addBox(-8.0F, -3.0F, -30.0F, 16.0F, 7.0F, 16.0F).texOffs(168, 32)
                        .addBox(-10.0F, -10.0F, -16.0F, 20.0F, 18.0F, 16.0F).texOffs(39, 130).addBox(2.0F, -5.0F, -28.0F, 3.0F, 4.0F, 4.0F).texOffs(0, 123).addBox(-5.0F, -5.0F, -28.0F, 3.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, -10.0F));
        PartDefinition modelPartData17 = modelPartData16.addOrReplaceChild("otherEars", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, -7.0F));
        modelPartData17.addOrReplaceChild("otherEars_r1", CubeListBuilder.create().texOffs(42, 80).addBox(-2.0F, -13.0F, -1.0F, 4.0F, 15.0F, 3.0F), PartPose.offset(5.0F, 0.0F, 0.0F));
        modelPartData17.addOrReplaceChild("otherEars_r2", CubeListBuilder.create().texOffs(42, 98).addBox(-2.0F, -13.0F, -1.0F, 4.0F, 15.0F, 3.0F), PartPose.offset(-5.0F, 0.0F, 0.0F));
        modelPartData16.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(194, 260).addBox(8.0F, -5.0F, -4.0F, 4.0F, 7.0F, 6.0F).texOffs(174, 260).addBox(-2.0F, -5.0F, -4.0F, 4.0F, 7.0F, 6.0F),
                PartPose.offset(-5.0F, -10.0F, -6.0F));
        modelPartData16.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(168, 91).addBox(-6.0F, -2.0F, -14.0F, 12.0F, 4.0F, 14.0F), PartPose.offset(0.0F, 6.0F, -16.0F));
        PartDefinition modelPartData18 = modelPartData.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(0, 153).addBox(-7.0F, -7.0F, -1.0F, 14.0F, 14.0F, 15.0F).texOffs(160, 219).addBox(-1.0F, -12.0F, 3.0F, 2.0F, 5.0F, 8.0F),
                PartPose.offset(0.0F, -9.0F, 30.0F));
        PartDefinition modelPartData19 = modelPartData18.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(0, 123).addBox(-6.0F, -6.0F, -1.0F, 12.0F, 12.0F, 15.0F).texOffs(36, 40).addBox(-1.0F, -10.0F, 3.0F, 2.0F, 4.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 14.0F));
        PartDefinition modelPartData20 = modelPartData19.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(203, 212).addBox(-5.0F, -6.0F, -1.0F, 10.0F, 11.0F, 15.0F).texOffs(36, 0).addBox(-1.0F, -10.0F, 2.0F, 2.0F, 4.0F, 8.0F),
                PartPose.offset(0.0F, 1.0F, 14.0F));
        PartDefinition modelPartData21 = modelPartData20.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(215, 76).addBox(-2.0F, -5.0F, -1.0F, 8.0F, 9.0F, 15.0F).texOffs(238, 214).addBox(1.0F, -8.0F, 3.0F, 2.0F, 3.0F, 8.0F),
                PartPose.offset(-2.0F, 0.0F, 14.0F));
        PartDefinition modelPartData22 = modelPartData21.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(225, 51).addBox(-3.0F, -4.0F, -1.0F, 6.0F, 8.0F, 15.0F).texOffs(176, 227).addBox(-1.0F, -7.0F, 3.0F, 2.0F, 3.0F, 8.0F),
                PartPose.offset(2.0F, 0.0F, 14.0F));
        PartDefinition modelPartData23 = modelPartData22.addOrReplaceChild("tail6",
                CubeListBuilder.create().texOffs(248, 223).addBox(0.0F, -2.0F, -1.0F, 4.0F, 5.0F, 15.0F).texOffs(160, 184).addBox(1.0F, -5.0F, 3.0F, 2.0F, 3.0F, 8.0F),
                PartPose.offset(-2.0F, 0.0F, 14.0F));
        PartDefinition modelPartData24 = modelPartData23.addOrReplaceChild("otherTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));
        modelPartData24.addOrReplaceChild("otherTail_r1", CubeListBuilder.create().texOffs(38, 207).addBox(-12.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F), PartPose.offset(2.0F, 0.0F, 0.0F));
        modelPartData24.addOrReplaceChild("otherTail_r2", CubeListBuilder.create().texOffs(206, 100).addBox(1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F), PartPose.offset(2.0F, 0.0F, 0.0F));
        modelPartData24.addOrReplaceChild("otherTail_r3", CubeListBuilder.create().texOffs(150, 207).addBox(-12.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F), PartPose.offset(2.0F, 0.0F, -6.0F));
        modelPartData24.addOrReplaceChild("otherTail_r4", CubeListBuilder.create().texOffs(218, 107).addBox(1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F), PartPose.offset(2.0F, 0.0F, -6.0F));
        modelPartData23.addOrReplaceChild("extraTail", CubeListBuilder.create().texOffs(210, 32).addBox(4.0F, 0.0F, -7.0F, 11.0F, 0.0F, 14.0F).texOffs(29, 182).addBox(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 14.0F),
                PartPose.offset(0.0F, 0.0F, 7.0F));
        modelPartData23.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(252, 64).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 15.0F).texOffs(0, 0).addBox(0.0F, -3.0F, 5.0F, 0.0F, 2.0F, 4.0F),
                PartPose.offset(2.0F, 0.0F, 14.0F));
        return LayerDefinition.create(modelData, 512, 512);
    }


    @Override
    public void setupAnim(DragonRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.resetPose();
        // Extras
        this.renderExtras(state);

                boolean fireBreathing = state.fireBreath;

        this.setRestPose();
        this.setLookAngles(headYaw, headPitch);

        if (state.clientStartFlying) {
            // Takeoff
            float wingOpen = state.wingOpen;
            this.wingRight.zRot = 0.6981f - wingOpen;
            this.wingLeft.zRot = -0.6981f + wingOpen;
            this.wingTipRight.zRot = -2.618f + wingOpen * 1.32f;
            this.wingTipLeft.zRot = 2.618f - wingOpen * 1.32f;
            this.setTailPose();
            if (state.onGround) {
                this.animateWalking(limbAngle, limbDistance);
            }
        } else if (state.flying) {
            // Flying
            float flap = DragonEntity.flapWaving(animationProgress, state.flapPhase);
            this.setFlapPose(flap);
            this.setTailPose();
            this.setFlightFloat(flap, animationProgress / 100f);
            if (state.onGround) {
                this.animateWalking(limbAngle, limbDistance);
            } else {
                this.setFlyingLegPose();
            }
        } else if (state.clientEndFlying) {
            // Landing
            if (state.landingWings) {
                float fold = state.landing * 0.6981f;
                this.wingRight.zRot = fold;
                this.wingLeft.zRot = -fold;
                this.wingTipRight.zRot = -fold * 3.75f;
                this.wingTipLeft.zRot = fold * 3.75f;
            } else {
                this.setFlapPose(DragonEntity.flapWaving(animationProgress, state.flapPhase));
            }
            this.setTailPose();
            if (state.onGround) {
                this.animateWalking(limbAngle, limbDistance);
            }
        } else if (!state.sittingPose || state.vehicle) {
            // Walking
            this.animateWalking(limbAngle, limbDistance);
            this.setTailPose();

            float sway = 0.0436f * limbDistance;
            this.tail.yRot = sway * Mth.triangleWave(limbAngle, 60.0F);
            this.tail2.yRot = sway * Mth.triangleWave(limbAngle, 60.0F);
            this.tail3.yRot = sway * Mth.triangleWave(limbAngle, 60.0F);
            this.tail4.yRot = sway * Mth.triangleWave(limbAngle, 60.0F);
            this.tail5.yRot = sway * Mth.triangleWave(limbAngle, 70.0F);
            this.tail6.yRot = sway * Mth.triangleWave(limbAngle, 70.0F);
            this.tail7.yRot = sway * Mth.triangleWave(limbAngle, 80.0F);

            float wingSway = 0.0236f * Mth.triangleWave(limbAngle, 40f) * limbDistance;
            this.wingLeft.zRot = -0.6981f + wingSway;
            this.wingTipLeft.zRot = 2.618f;
            this.wingRight.zRot = 0.6981f - wingSway;
            this.wingTipRight.zRot = -2.618f;
        } else {
            // Sitting
            this.tail.xRot = -0.1309F;
            this.tail2.xRot = -0.1309F;
            this.tail3.xRot = -0.1309F;
            this.tail4.xRot = -0.1745F;
            this.tail5.xRot = 0.1309F;
            this.tail6.xRot = 0.1745F;
            this.tail7.xRot = 0.1745F;

            this.tail.yRot = -0.0436F;
            this.tail2.yRot = -0.0436F;
            this.tail3.yRot = -0.0873F;
            this.tail4.yRot = -0.0436F;
            this.tail5.yRot = -0.2182F;
            this.tail6.yRot = -0.2182F;
            this.tail6.zRot = -0.0436F;
            this.tail7.zRot = -0.1309F;

            // Wings
            this.wingLeft.zRot = -0.6981F;
            this.wingTipLeft.zRot = 2.618F;
            this.wingRight.zRot = 0.6981F;
            this.wingTipRight.zRot = -2.618F;

            if (state.yawning) {
                this.jaw.xRot = state.yawnPitch;
            }
        }

        if (fireBreathing) {
            this.jaw.xRot = 0.4f;
        }
    }

    private void setRestPose() {
        this.otherEars.xRot = -1.0036f;
        this.otherEars_r1.xRot = 0.1745f;
        this.otherEars_r1.zRot = 0.2182f;
        this.otherEars_r2.xRot = 0.1745f;
        this.otherEars_r2.zRot = -0.2182f;
        this.otherTail_r1.yRot = 0.7418f;
        this.otherTail_r2.yRot = -0.7418f;
        this.otherTail_r3.yRot = 0.6109f;
        this.otherTail_r4.yRot = -0.7418f;

        this.rearLegRight.xRot = -0.9599f;
        this.rearLegRightTip.xRot = 1.309f;
        this.rearLegRightTip_r1.xRot = 0.9599f;
        this.rearFootRight.xRot = -0.3491f;
        this.rearLegLeft.xRot = -0.9599f;
        this.rearLegLefttip.xRot = 1.309f;
        this.rearLegLefttip_r1.xRot = 0.9599f;
        this.rearFootLeft.xRot = -0.3491f;
        this.frontLegRight.xRot = 0.3491f;
        this.frontLegRight_r1.xRot = 0.2618f;
        this.frontLegRighttip.xRot = -1.5708f;
        this.frontLegRighttip_r1.xRot = 0.2618f;
        this.frontFootRight.xRot = 1.2217f;
        this.frontLegLeft.xRot = 0.3491f;
        this.frontLegLeft_r1.xRot = 0.2618f;
        this.frontLegLefttip.xRot = -1.5708f;
        this.frontLegLefttip_r1.xRot = 0.2618f;
        this.frontFootLeft.xRot = 1.2217f;
    }

    private void setLookAngles(float headYaw, float headPitch) {
        this.head.yRot = headYaw * (0.017453292F / 6.0F); // 0.0049453292F
        this.head.xRot = headPitch * 0.0037453292F;
        this.neck.yRot = headYaw * (0.017453292F / 5.0F);
        this.neck2.yRot = headYaw * (0.017453292F / 5.0F);
        this.neck3.yRot = headYaw * (0.017453292F / 4.0F);
        this.neck4.yRot = headYaw * (0.017453292F / 4.0F);

        this.neck.xRot = headPitch * 0.0017453292F;
        this.neck2.xRot = headPitch * 0.0017453292F;
        this.neck3.xRot = headPitch * 0.0017453292F;
        this.neck4.xRot = headPitch * 0.0017453292F;
    }

    private void setTailPose() {
        this.tail.xRot = -0.1309F;
        this.tail2.xRot = -0.1309F;
        this.tail3.xRot = -0.1309F;
        this.tail4.xRot = -0.1745F;
        this.tail5.xRot = 0.1309F;
        this.tail6.xRot = 0.1745F;
        this.tail7.xRot = 0.2618F;
    }

    private void animateWalking(float limbAngle, float limbDistance) {
        float swing = 0.3f * Mth.triangleWave(limbAngle, 32f) * limbDistance * 1.3f;
        this.rearLegRight.xRot = -0.9599f + swing;
        this.rearLegLeft.xRot = -0.9599f - swing;

        this.rearFootRight.xRot = -0.3491f - swing * 0.9f;
        this.rearFootLeft.xRot = -0.3491f + swing * 0.9f;

        this.frontLegRight.xRot = 0.3491f - swing;
        this.frontLegLeft.xRot = 0.3491f + swing;

        this.frontLegRighttip.xRot = -1.5708f + swing * 0.8f;
        this.frontLegLefttip.xRot = -1.5708f - swing * 0.8f;

        this.frontFootRight.xRot = 1.2217f + swing * 0.2f;
        this.frontFootLeft.xRot = 1.2217f - swing * 0.2f;
    }

    private void setFlapPose(float flap) {
        this.wingRight.zRot = flap * 0.8f;
        this.wingTipRight.zRot = this.wingRight.zRot * 0.8f;
        this.wingLeft.zRot = -this.wingRight.zRot;
        this.wingTipLeft.zRot = -this.wingTipRight.zRot;
    }

    private void setFlightFloat(float flap, float cycle) {
        float bodyFloating = -flap - 4f;
        this.body.y = bodyFloating;

        this.wingLeft.y = bodyFloating - 10f;
        this.wingRight.y = bodyFloating - 10f;
        this.rearLegLeft.y = bodyFloating;
        this.rearLegRight.y = bodyFloating;
        this.frontLegLeft.y = bodyFloating;
        this.frontLegRight.y = bodyFloating;
        this.neck.y = bodyFloating;
        this.tail.y = bodyFloating;

        float headFloat = Mth.cos(bodyFloating / 2f);
        this.neck2.y = -headFloat;
        this.neck3.y = -headFloat;
        this.neck4.y = -headFloat * 0.9f;
        this.head.y = -headFloat * 0.8f;
        this.jaw.xRot = -headFloat * 0.3f;

        float wave = 6.2831f * cycle;
        this.tail2.y = Mth.cos(wave - 6.2831f / 3f);
        this.tail3.y = Mth.cos(wave - 6.2831f / 3f);
        this.tail4.y = Mth.cos(wave - 6.2831f / 4f);
        this.tail5.y = Mth.cos(wave - 6.2831f / 5f);
        this.tail6.y = Mth.cos(wave - 6.2831f / 5f);
        this.tail7.y = Mth.cos(wave - 6.2831f / 6f) * 0.5f;
    }

    private void setFlyingLegPose() {
        this.rearLegRight.xRot = 0.5672f;
        this.rearLegRightTip.xRot = -0.2182f;
        this.rearLegRightTip_r1.xRot = 0.9599f;
        this.rearFootRight.xRot = 1.4835f;

        this.rearLegLeft.xRot = 0.5672f;
        this.rearLegLefttip.xRot = -0.2182f;
        this.rearLegLefttip_r1.xRot = 0.9599f;
        this.rearFootLeft.xRot = 1.4835f;

        this.frontLegRight.xRot = 0.7418f;
        this.frontLegRight_r1.xRot = 0.2618f;
        this.frontLegRighttip.xRot = -1.0472f;
        this.frontLegRighttip_r1.xRot = 0.2618f;
        this.frontFootRight.xRot = 1.4835f;

        this.frontLegLeft.xRot = 0.7418f;
        this.frontLegLeft_r1.xRot = 0.2618f;
        this.frontLegLefttip.xRot = -1.0472f;
        this.frontLegLefttip_r1.xRot = 0.2618f;
        this.frontFootLeft.xRot = 1.4835f;
    }

    private void renderExtras(DragonRenderState state) {
        this.spike.visible = !state.hasSaddle;
        this.chest.visible = state.hasChest;
        if (state.otherEars) {
            this.otherEars.visible = true;
            this.ears.visible = false;
        } else {
            this.otherEars.visible = false;
            this.ears.visible = true;
        }
        if (state.otherTail) {
            this.otherTail.visible = true;
            this.extraTail.visible = false;
        } else {
            this.otherTail.visible = false;
            this.extraTail.visible = true;
        }

    }

}
