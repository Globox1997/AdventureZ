package net.adventurez.entity.model;


import net.adventurez.entity.render.state.ShamanRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.ShamanEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ShamanModel extends EntityModel<ShamanRenderState> {
    private final ModelPart body1;
    private final ModelPart body2;
    private final ModelPart thighR;
    private final ModelPart kneeR;
    private final ModelPart thighL;
    private final ModelPart kneeL;
    private final ModelPart armL1;
    private final ModelPart shoulderskullL;
    private final ModelPart armR1;
    private final ModelPart shoulderskullR;
    private final ModelPart staffshaft1;
    private final ModelPart staffshaft2;
    private final ModelPart staffshaft3;
    private final ModelPart staffshaft4;
    private final ModelPart staffdetail5;
    private final ModelPart staffdetail6;
    private final ModelPart staffdetail1;
    private final ModelPart staffdetail2;
    private final ModelPart staffdetail3;
    private final ModelPart staffdetail4;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart skullmask;
    private final ModelPart feathercrownM;
    private final ModelPart feathercrownL;
    private final ModelPart feathercrownR;

    public ShamanModel(ModelPart root) {
        super(root);
        this.body1 = root.getChild("body1");
        this.neck = this.body1.getChild("neck");
        this.head = this.neck.getChild("head");
        this.skullmask = this.head.getChild("skullmask");
        this.feathercrownR = this.skullmask.getChild("feathercrownR");
        this.feathercrownL = this.skullmask.getChild("feathercrownL");
        this.feathercrownM = this.skullmask.getChild("feathercrownM");
        this.jaw = this.head.getChild("jaw");
        this.tail1 = this.body1.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.armR1 = this.body1.getChild("armR1");
        this.staffshaft1 = this.armR1.getChild("staffshaft1");
        this.staffdetail1 = this.staffshaft1.getChild("staffdetail1");
        this.staffdetail3 = this.staffshaft1.getChild("staffdetail3");
        this.staffdetail2 = this.staffshaft1.getChild("staffdetail2");
        this.staffdetail4 = this.staffshaft1.getChild("staffdetail4");
        this.staffshaft2 = this.staffshaft1.getChild("staffshaft2");
        this.staffdetail6 = this.staffshaft2.getChild("staffdetail6");
        this.staffshaft3 = this.staffshaft2.getChild("staffshaft3");
        this.staffdetail5 = this.staffshaft3.getChild("staffdetail5");
        this.staffshaft4 = this.staffshaft3.getChild("staffshaft4");
        this.shoulderskullR = this.armR1.getChild("shoulderskullR");
        this.armL1 = this.body1.getChild("armL1");
        this.shoulderskullL = this.armL1.getChild("shoulderskullL");
        this.body2 = this.body1.getChild("body2");
        this.thighL = this.body2.getChild("thighL");
        this.kneeL = this.thighL.getChild("kneeL");
        this.thighR = this.body2.getChild("thighR");
        this.kneeR = this.thighR.getChild("kneeR");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 117).addBox(-4.0F, -7.0F, -2.0F, 8.0F, 7.0F, 4.0F), PartPose.offset(0.0F, 6.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(30, 117).addBox(-3.5F, 0.0F, -1.5F, 7.0F, 7.0F, 3.0F), PartPose.offset(0.0F, -1.0F, 0.3F));
        PartDefinition modelPartData3 = modelPartData2.addOrReplaceChild("thighR", CubeListBuilder.create().texOffs(0, 70).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F), PartPose.offset(2.0F, 6.0F, 0.0F));
        modelPartData3.addOrReplaceChild("kneeR", CubeListBuilder.create().texOffs(20, 70).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 9.0F, 4.0F), PartPose.offset(0.0F, 5.0F, 0.5F));
        PartDefinition modelPartData4 = modelPartData2.addOrReplaceChild("thighL", CubeListBuilder.create().texOffs(0, 70).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, true),
                PartPose.offset(-2.0F, 6.0F, 0.0F));
        modelPartData4.addOrReplaceChild("kneeL", CubeListBuilder.create().texOffs(20, 70).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 9.0F, 4.0F, true), PartPose.offset(0.0F, 5.0F, 0.5F));
        PartDefinition modelPartData5 = modelPartData1.addOrReplaceChild("armL1", CubeListBuilder.create().texOffs(0, 90).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 13.0F, 4.0F), PartPose.offset(-5.0F, -5.0F, 0.0F));
        modelPartData5.addOrReplaceChild("shoulderskullL", CubeListBuilder.create().texOffs(20, 100).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(1.0F, -2.0F, 0.0F));
        PartDefinition modelPartData6 = modelPartData1.addOrReplaceChild("armR1", CubeListBuilder.create().texOffs(0, 90).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 13.0F, 4.0F, true),
                PartPose.offset(5.0F, -5.0F, 0.0F));
        modelPartData6.addOrReplaceChild("shoulderskullR", CubeListBuilder.create().texOffs(20, 100).addBox(0.0F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, true), PartPose.offset(-1.0F, -2.0F, 0.0F));
        PartDefinition modelPartData7 = modelPartData6.addOrReplaceChild("staffshaft1", CubeListBuilder.create().texOffs(59, 0).addBox(-0.5F, 0.0F, -10.0F, 1.0F, 1.0F, 33.0F, new CubeDeformation(0.01F)),
                PartPose.offset(1.0F, 9.0F, 0.0F));
        PartDefinition modelPartData8 = modelPartData7.addOrReplaceChild("staffshaft2", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.4F, -9.2F));
        PartDefinition modelPartData9 = modelPartData8.addOrReplaceChild("staffshaft3", CubeListBuilder.create().texOffs(10, 30).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.02F)),
                PartPose.offset(0.0F, -3.4F, -0.4F));
        modelPartData9.addOrReplaceChild("staffshaft4", CubeListBuilder.create().texOffs(20, 30).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F), PartPose.offset(0.0F, -3.8F, 0.7F));
        modelPartData9.addOrReplaceChild("staffdetail5", CubeListBuilder.create().texOffs(20, 20).addBox(0.0F, 0.0F, -3.5F, 0.0F, 2.0F, 5.0F), PartPose.offset(0.0F, -3.1F, 0.8F));
        modelPartData8.addOrReplaceChild("staffdetail6", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, 0.0F, -3.5F, 0.0F, 2.0F, 5.0F), PartPose.offset(0.0F, -3.0F, 0.0F));
        modelPartData7.addOrReplaceChild("staffdetail1", CubeListBuilder.create().texOffs(80, 0).addBox(0.0F, 0.0F, -1.5F, 0.0F, 5.0F, 3.0F).texOffs(70, 5).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.offset(-0.7F, 0.5F, -9.8F));
        modelPartData7.addOrReplaceChild("staffdetail2", CubeListBuilder.create().texOffs(80, 0).addBox(0.0F, 0.0F, -1.5F, 0.0F, 5.0F, 3.0F), PartPose.offset(0.7F, 0.5F, -9.8F));
        modelPartData7.addOrReplaceChild("staffdetail3", CubeListBuilder.create().texOffs(70, 5).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F), PartPose.offset(0.0F, -0.4F, -9.8F));
        modelPartData7.addOrReplaceChild("staffdetail4", CubeListBuilder.create(), PartPose.offset(0.0F, 1.3F, -9.8F));
        PartDefinition modelPartData10 = modelPartData1.addOrReplaceChild("bonearmor1", CubeListBuilder.create().texOffs(30, 50).addBox(-4.0F, -3.5F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.2F)),
                PartPose.offset(0.0F, -3.2F, 0.0F));
        modelPartData10.addOrReplaceChild("spine", CubeListBuilder.create().texOffs(40, 70).addBox(-0.5F, -3.5F, 0.0F, 1.0F, 7.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 2.0F));
        PartDefinition modelPartData11 = modelPartData1.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(100, 40).addBox(-2.5F, -2.0F, -1.0F, 5.0F, 4.0F, 6.0F), PartPose.offset(0.0F, 5.0F, 0.0F));
        PartDefinition modelPartData12 = modelPartData11.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(100, 60).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, -0.1F, 5.0F));
        modelPartData12.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(100, 80).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 5.0F), PartPose.offset(0.0F, -0.2F, 5.0F));
        PartDefinition modelPartData13 = modelPartData1.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(40, 0).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F), PartPose.offset(0.0F, -5.7F, 0.5F));
        PartDefinition modelPartData14 = modelPartData13.addOrReplaceChild("head", CubeListBuilder.create().texOffs(60, 115).addBox(-3.0F, -6.0F, 0.0F, 6.0F, 7.0F, 3.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, -4.0F, 0.0F));
        PartDefinition modelPartData15 = modelPartData14.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(60, 100).addBox(-3.0F, -6.0F, -2.0F, 6.0F, 7.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData15.addOrReplaceChild("lowerteeth", CubeListBuilder.create().texOffs(20, 0).addBox(-3.0F, -3.5F, 0.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(-0.03F)), PartPose.offset(0.0F, -2.5F, -0.5F));
        PartDefinition modelPartData16 = modelPartData14.addOrReplaceChild("skullmask", CubeListBuilder.create().texOffs(90, 115).addBox(-3.0F, -3.0F, -1.2F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.1F)),
                PartPose.offset(0.0F, -3.0F, 0.8F));
        modelPartData16.addOrReplaceChild("feathercrownM", CubeListBuilder.create().texOffs(-4, 12).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 6.0F), PartPose.offset(0.0F, 3.0F, 2.0F));
        modelPartData16.addOrReplaceChild("feathercrownL", CubeListBuilder.create().texOffs(20, 14).addBox(-6.0F, 0.0F, -2.5F, 6.0F, 0.0F, 5.0F, true), PartPose.offset(-2.5F, 4.0F, 1.3F));
        modelPartData16.addOrReplaceChild("feathercrownR", CubeListBuilder.create().texOffs(20, 14).addBox(0.0F, 0.0F, -2.5F, 6.0F, 0.0F, 5.0F), PartPose.offset(2.5F, 4.0F, 1.3F));
        modelPartData14.addOrReplaceChild("topteeth", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.5F, -2.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, -2.5F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }


    @Override
    public void setupAnim(ShamanRenderState state) {
        float f = state.walkAnimationPos;
        float g = state.walkAnimationSpeed;
        float h = state.ageInTicks;
        float i = state.yRot;
        float j = state.xRot;
        this.body1.xRot = 0.4887F;
        this.body2.xRot = -0.2094F;
        this.thighR.xRot = -0.7156F + Mth.cos(f * 0.6662F) * 1.4F * g * 0.5F;
        this.thighR.yRot = -0.1745F;
        this.thighR.zRot = -0.0873F;
        this.kneeR.xRot = 0.4014F;
        this.kneeR.zRot = 0.1571F;
        this.thighL.xRot = -0.7156F + Mth.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
        this.thighL.yRot = 0.1745F;
        this.thighL.zRot = 0.0873F;
        this.kneeL.xRot = 0.4014F;
        this.kneeL.zRot = -0.1571F;
        this.armL1.xRot = -0.4712F + Mth.cos(f * 0.6662F) * 2.0F * g * 0.2F;
        this.armL1.zRot = 0.3316F;
        this.shoulderskullL.zRot = -0.4189F;
        this.armR1.xRot = -2.0246F + Mth.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.1F;
        this.armR1.yRot = -0.6109F;
        this.armR1.zRot = 0.192F;
        this.shoulderskullR.zRot = 0.4189F;
        this.staffshaft2.xRot = 0.5934F;
        this.staffshaft3.xRot = 1.3614F;
        this.staffshaft4.xRot = 1.3963F;
        this.staffdetail5.xRot = 1.5533F;
        this.staffdetail5.yRot = -0.0349F;
        this.staffdetail6.xRot = 1.5533F;
        this.staffdetail6.yRot = -0.0349F;
        this.staffdetail1.xRot = 1.5533F;
        this.staffdetail1.yRot = -0.4363F;
        this.staffdetail2.xRot = 1.5533F;
        this.staffdetail2.yRot = 0.4363F;
        this.staffdetail3.xRot = 1.85F;
        this.staffdetail4.xRot = 1.1519F;
        this.tail1.xRot = -0.7854F + Mth.cos(f * 0.6662F) * 1.4F * g * 0.1F;
        this.tail1.yRot = -0.1571F + Mth.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.1F;
        this.tail2.xRot = 0.2094F + Mth.cos(f * 0.6662F) * 1.4F * g * 0.05F;
        this.tail2.yRot = -0.1222F + Mth.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.05F;
        this.tail3.xRot = 0.2618F + Mth.cos(f * 0.6662F) * 1.4F * g * 0.01F;
        this.tail3.yRot = -0.1745F + Mth.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.01F;
        this.neck.xRot = 0.5411F;
        this.head.yRot = i * 0.0079453292F;
        this.head.zRot = -i * 0.0079453292F;
        this.head.xRot = 0.5585F + j * 0.0107453292F;
        this.jaw.xRot = 0.5585F;
        this.skullmask.xRot = 0.1571F;
        this.feathercrownM.xRot = -0.5411F;
        this.feathercrownL.xRot = -0.192F;
        this.feathercrownL.yRot = 0.5411F;
        this.feathercrownL.zRot = -0.6807F;
        this.feathercrownR.xRot = -0.192F;
        this.feathercrownR.yRot = -0.5411F;
        this.feathercrownR.zRot = 0.6807F;

        if (state.spellcasting) {
            this.armR1.xRot = -2.0246F + Mth.cos(h * 0.6662F) * 0.1F - 0.6F;
        }
    }

}
