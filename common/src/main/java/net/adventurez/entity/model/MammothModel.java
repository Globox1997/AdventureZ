package net.adventurez.entity.model;


import net.adventurez.entity.render.state.MammothRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.MammothEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class MammothModel extends EntityModel<MammothRenderState> {
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
        super(root);
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

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.0F, -11.0F, 16.0F, 15.0F, 13.0F).texOffs(80, 0).addBox(-7.0F, -6.0F, 2.0F, 14.0F, 12.0F, 10.0F).texOffs(0, 23)
                        .addBox(-7.0F, 6.0F, 2.0F, 0.0F, 2.0F, 10.0F).texOffs(0, 15).addBox(-8.0F, 6.0F, -11.0F, 0.0F, 3.0F, 13.0F).texOffs(0, 15).addBox(8.0F, 6.0F, -11.0F, 0.0F, 3.0F, 13.0F).texOffs(0, 35)
                        .addBox(-8.0F, 6.0F, -11.0F, 16.0F, 3.0F, 0.0F).texOffs(0, 23).addBox(7.0F, 6.0F, 2.0F, 0.0F, 2.0F, 10.0F).texOffs(0, 31).addBox(-7.0F, 6.0F, 12.0F, 14.0F, 2.0F, 0.0F),
                PartPose.offset(0.0F, 7.0F, 0.0F));
        modelPartData1.addOrReplaceChild("humpRight", CubeListBuilder.create().texOffs(45, 0).addBox(-5.0F, 0.0F, -7.0F, 10.0F, 6.0F, 7.0F), PartPose.offset(0.0F, -9.0F, -1.0F));
        modelPartData1.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(5, 5).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F), PartPose.offset(0.0F, -1.0F, 12.0F));
        modelPartData.addOrReplaceChild("legBackLeft", CubeListBuilder.create().texOffs(59, 14).addBox(-3.0F, 0.0F, -3.0F, 5.0F, 11.0F, 5.0F), PartPose.offset(-3.0F, 13.0F, 9.0F));
        modelPartData.addOrReplaceChild("legBackRight", CubeListBuilder.create().texOffs(59, 14).addBox(-2.0F, 0.0F, -3.0F, 5.0F, 11.0F, 5.0F), PartPose.offset(3.0F, 13.0F, 9.0F));
        modelPartData.addOrReplaceChild("legFrontLeft", CubeListBuilder.create().texOffs(59, 14).addBox(-2.0F, 0.0F, -2.0F, 5.0F, 11.0F, 5.0F), PartPose.offset(-5.0F, 13.0F, -8.0F));
        modelPartData.addOrReplaceChild("legFrontRight", CubeListBuilder.create().texOffs(59, 14).addBox(-2.0F, 0.0F, -2.0F, 5.0F, 11.0F, 5.0F), PartPose.offset(4.0F, 13.0F, -8.0F));
        PartDefinition modelPartData2 = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(92, 22).addBox(-4.5F, -5.316F, -5.8794F, 9.0F, 12.0F, 9.0F),
                PartPose.offset(0.0F, 1.0F, -11.0F));
        modelPartData2.addOrReplaceChild("headRight", CubeListBuilder.create().texOffs(92, 23).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 6.0F, 1.0F), PartPose.offset(-4.5F, -3.316F, -2.8794F));
        modelPartData2.addOrReplaceChild("headLeft", CubeListBuilder.create().texOffs(92, 23).addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 1.0F), PartPose.offset(4.5F, -3.316F, -2.8794F));
        PartDefinition modelPartData3 = modelPartData2.addOrReplaceChild("trunk", CubeListBuilder.create(), PartPose.offset(0.0F, 6.684F, -3.8794F));
        modelPartData3.addOrReplaceChild("trunk_r1", CubeListBuilder.create().texOffs(112, 43).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 8.0F, 4.0F), PartPose.offset(0.0F, 0.5176F, -1.9319F));
        PartDefinition modelPartData4 = modelPartData3.addOrReplaceChild("trunkOne", CubeListBuilder.create(), PartPose.offset(0.0F, 6.4458F, 3.0681F));
        modelPartData4.addOrReplaceChild("trunkOne_r1", CubeListBuilder.create().texOffs(100, 43).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 7.0F, 3.0F), PartPose.offset(0.0F, 0.909F, -1.0834F));
        PartDefinition modelPartData5 = modelPartData4.addOrReplaceChild("trunkTwo", CubeListBuilder.create(), PartPose.offset(0.0F, 4.8588F, 4.8663F));
        modelPartData5.addOrReplaceChild("trunkTwo_r1", CubeListBuilder.create().texOffs(92, 43).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 5.0F, 2.0F), PartPose.offset(0.0F, 0.8609F, -1.1219F));
        PartDefinition modelPartData6 = modelPartData2.addOrReplaceChild("tuskRight", CubeListBuilder.create(), PartPose.offset(4.5F, 2.684F, -3.8794F));
        modelPartData6.addOrReplaceChild("tuskTipRight_r1", CubeListBuilder.create().texOffs(102, 53).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(7.907F, 12.1641F, -1.8623F));
        modelPartData6.addOrReplaceChild("tuskMidRight_r1", CubeListBuilder.create().texOffs(110, 55).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(5.3262F, 8.4783F, 3.5F));
        modelPartData6.addOrReplaceChild("tuskBackRight_r1", CubeListBuilder.create().texOffs(118, 55).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 6.0F, 3.0F), PartPose.offset(2.4583F, 4.3826F, 0.5F));
        modelPartData6.addOrReplaceChild("tuskFurOverlay_r1", CubeListBuilder.create().texOffs(46, 28).addBox(-3.0F, 4.0F, 0.0F, 3.0F, 2.0F, 4.0F).texOffs(32, 28).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData7 = modelPartData2.addOrReplaceChild("tuskLeft", CubeListBuilder.create(), PartPose.offset(-5.5F, 1.684F, -3.8794F));
        modelPartData7.addOrReplaceChild("tuskTipLeft_r1", CubeListBuilder.create().texOffs(102, 53).addBox(0.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(-4.6746F, 14.3523F, -1.8623F));
        modelPartData7.addOrReplaceChild("tuskMidLeft_r1", CubeListBuilder.create().texOffs(110, 55).addBox(0.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(-2.773F, 10.2744F, 3.5F));
        modelPartData7.addOrReplaceChild("tuskBackLeft_r1", CubeListBuilder.create().texOffs(118, 55).addBox(0.0F, -1.0F, -3.0F, 2.0F, 6.0F, 3.0F), PartPose.offset(-0.6599F, 5.7428F, 3.5F));
        modelPartData7.addOrReplaceChild("tuskBaseLeft_r1", CubeListBuilder.create().texOffs(46, 28).addBox(0.0F, 4.0F, 0.0F, 3.0F, 2.0F, 4.0F).texOffs(32, 28).addBox(0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 4.0F),
                PartPose.offset(1.0F, 1.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }


    @Override
    public void setupAnim(MammothRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.head.yRot = headYaw * 0.007453292F;
        this.head.xRot = headPitch * 0.007453292F;
        this.humpRight.xRot = -0.6981F;
        this.tail.xRot = 0.5236F;
        this.head.xRot = -0.3491F;
        this.headRight.yRot = 0.6109F;
        this.headLeft.yRot = -0.6109F;
        this.trunk.xRot = -0.2618F;
        this.trunk_r1.xRot = 0.5236F;
        this.trunkOne.xRot = 0.0873F;
        this.trunkOne_r1.xRot = 0.7854F;
        this.trunkTwo.xRot = 0.1309F;
        this.trunkTwo_r1.xRot = 1.309F;
        this.tuskRight.zRot = 0.1745F;
        this.tuskTipRight_r1.xRot = -1.7453F;
        this.tuskTipRight_r1.zRot = -0.6109F;
        this.tuskMidRight_r1.xRot = -0.8727F;
        this.tuskMidRight_r1.zRot = -0.6109F;
        this.tuskBackRight_r1.zRot = -0.6109F;
        this.tuskFurOverlay_r1.zRot = -0.6109F;
        this.tuskTipLeft_r1.xRot = -1.7453F;
        this.tuskTipLeft_r1.zRot = 0.4363F;
        this.tuskMidLeft_r1.xRot = -0.8727F;
        this.tuskMidLeft_r1.zRot = 0.4363F;
        this.tuskBackLeft_r1.zRot = 0.4363F;
        this.tuskBaseLeft_r1.zRot = 0.4363F;
        float slowerWalking = 0.6662F;
        if (state.isBaby) {
            slowerWalking = 0.1F;
        }
        this.legBackRight.xRot = Mth.cos(limbAngle * slowerWalking + 3.1415927F) * 0.7F * limbDistance;
        this.legBackLeft.xRot = Mth.cos(limbAngle * slowerWalking) * 0.7F * limbDistance;
        this.legFrontLeft.xRot = Mth.cos(limbAngle * slowerWalking + 3.1415927F) * 0.7F * limbDistance;
        this.legFrontRight.xRot = Mth.cos(limbAngle * slowerWalking) * 0.7F * limbDistance;

        int i = state.attackTicks;
        if (i > 0) {
            this.head.xRot = -0.3491F + Mth.sin((3.14159265358F / 10F) * ((float) i - 9)) * 0.4F;
        }

    }

}
