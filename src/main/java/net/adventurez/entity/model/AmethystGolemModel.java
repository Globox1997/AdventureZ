package net.adventurez.entity.model;


import net.adventurez.entity.render.state.AmethystGolemRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.AmethystGolemEntity;
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
public class AmethystGolemModel extends EntityModel<AmethystGolemRenderState> {
    private final ModelPart root;
    private final ModelPart amethyst_golem;
    private final ModelPart body;
    private final ModelPart body2;
    private final ModelPart amethyst2;
    private final ModelPart amethyst2_r1;
    private final ModelPart amethyst3;
    private final ModelPart head;
    private final ModelPart head_r1;
    private final ModelPart arms;
    private final ModelPart left_arm;
    private final ModelPart amethyst;
    private final ModelPart right_arm;
    private final ModelPart amethyst4;
    private final ModelPart legs;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public AmethystGolemModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.amethyst_golem = this.root.getChild("amethyst_golem");
        this.legs = this.amethyst_golem.getChild("legs");
        this.right_leg = this.legs.getChild("right_leg");
        this.left_leg = this.legs.getChild("left_leg");
        this.arms = this.amethyst_golem.getChild("arms");
        this.right_arm = this.arms.getChild("right_arm");
        this.amethyst4 = this.right_arm.getChild("amethyst4");
        this.left_arm = this.arms.getChild("left_arm");
        this.amethyst = this.left_arm.getChild("amethyst");
        this.head = this.amethyst_golem.getChild("head");
        this.head_r1 = this.head.getChild("head_r1");
        this.body = this.amethyst_golem.getChild("body");
        this.body2 = this.body.getChild("body2");
        this.amethyst3 = this.body2.getChild("amethyst3");
        this.amethyst2 = this.body2.getChild("amethyst2");
        this.amethyst2_r1 = this.amethyst2.getChild("amethyst2_r1");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("amethyst_golem", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData3 = modelPartData2.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 24).addBox(-5.5F, -7.0F, -4.0F, 11.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, -15.0F, 0.0F));
        PartDefinition modelPartData4 = modelPartData3.addOrReplaceChild("body2",
                CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -11.0F, -6.0F, 18.0F, 12.0F, 12.0F).texOffs(60, 74).addBox(2.5F, -7.0F, 3.5F, 7.0F, 6.0F, 3.0F).texOffs(0, 0)
                        .addBox(-0.5F, -6.0F, 3.5F, 3.0F, 4.0F, 3.0F).texOffs(0, 7).addBox(3.5F, -1.0F, 3.5F, 2.0F, 1.0F, 3.0F).texOffs(17, 54).addBox(-9.5F, -10.5F, -6.5F, 8.0F, 12.0F, 7.0F).texOffs(67, 64)
                        .addBox(-9.5F, -6.5F, 0.5F, 8.0F, 6.0F, 4.0F).texOffs(79, 18).addBox(-9.5F, -8.5F, 0.5F, 8.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, -7.0F, 0.0F));
        PartDefinition modelPartData5 = modelPartData4.addOrReplaceChild("amethyst2", CubeListBuilder.create(), PartPose.offset(3.0F, -11.0F, 2.0F));
        modelPartData5.addOrReplaceChild("amethyst2_r1", CubeListBuilder.create().texOffs(76, 46).addBox(-2.0F, -39.0F, 0.0F, 10.0F, 8.0F, 0.0F).texOffs(0, 63).addBox(3.0F, -39.0F, -5.0F, 0.0F, 8.0F, 10.0F),
                PartPose.offset(-3.0F, 32.0F, 0.0F));
        modelPartData4.addOrReplaceChild("amethyst3", CubeListBuilder.create().texOffs(40, 73).addBox(-5.0F, -7.5F, 0.0F, 10.0F, 9.0F, 0.0F).texOffs(62, 27).addBox(0.0F, -7.5F, -5.0F, 0.0F, 9.0F, 10.0F),
                PartPose.offset(-8.0F, -5.0F, -6.0F));
        PartDefinition modelPartData6 = modelPartData2.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(52, 46).addBox(-4.0F, -6.0F, -7.5F, 8.0F, 10.0F, 8.0F).texOffs(24, 40).addBox(-4.5F, -0.5F, -8.0F, 9.0F, 5.0F, 9.0F),
                PartPose.offset(0.0F, -29.0F, -5.0F));
        modelPartData6.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 1.0F), PartPose.offset(0.0F, -1.0F, -7.0F));
        PartDefinition modelPartData7 = modelPartData2.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));
        PartDefinition modelPartData8 = modelPartData7.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -2.5F, -3.0F, 6.0F, 27.0F, 6.0F).texOffs(0, 57)
                .addBox(2.0F, 5.5F, -4.0F, 4.0F, 4.0F, 4.0F).texOffs(60, 0).addBox(-1.5F, 14.5F, -3.5F, 7.0F, 11.0F, 7.0F), PartPose.offset(10.0F, -31.0F, 0.0F));
        modelPartData8.addOrReplaceChild("amethyst", CubeListBuilder.create().texOffs(20, 73).addBox(-5.0F, -7.5F, 0.0F, 10.0F, 9.0F, 0.0F).texOffs(47, 54).addBox(0.0F, -7.5F, -5.0F, 0.0F, 9.0F, 10.0F),
                PartPose.offset(4.0F, -1.0F, 0.0F));
        PartDefinition modelPartData9 = modelPartData7.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 24).addBox(-5.0F, -2.5F, -3.0F, 6.0F, 27.0F, 6.0F, true).texOffs(0, 57)
                .addBox(-6.0F, 6.5F, -4.0F, 4.0F, 4.0F, 4.0F, true).texOffs(60, 0).addBox(-5.5F, 14.5F, -3.5F, 7.0F, 11.0F, 7.0F, true), PartPose.offset(-10.0F, -31.0F, 0.0F));
        modelPartData9.addOrReplaceChild("amethyst4", CubeListBuilder.create().texOffs(20, 73).addBox(-5.0F, -7.5F, 0.0F, 10.0F, 9.0F, 0.0F, true).texOffs(47, 54).addBox(0.0F, -7.5F, -5.0F, 0.0F, 9.0F, 10.0F, true),
                PartPose.offset(-4.0F, -1.0F, 0.0F));
        PartDefinition modelPartData10 = modelPartData2.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 0.0F));
        modelPartData10.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(62, 18).addBox(-2.5F, -1.0F, -3.0F, 6.0F, 14.0F, 5.0F), PartPose.offset(4.0F, 0.0F, 0.0F));
        modelPartData10.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(62, 18).addBox(-3.5F, -1.0F, -3.0F, 6.0F, 14.0F, 5.0F, true), PartPose.offset(-4.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);

    }


    @Override
    public void setupAnim(AmethystGolemRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.amethyst2.xRot = -0.1745F;
        this.amethyst2.yRot = 0.1745F;
        this.amethyst2_r1.yRot = 0.3927F;
        this.amethyst3.xRot = 1.2217F;
        this.amethyst3.yRot = 0.4363F;
        this.head_r1.xRot = -0.0436F;
        this.amethyst.zRot = 0.6981F;
        this.amethyst4.zRot = -0.6981F;
        this.head.yRot = headYaw * 0.0089453292F;
        this.head.xRot = headPitch * 0.0047453292F;
        this.right_leg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_leg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.right_arm.xRot = -Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
        this.left_arm.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
        float k = Mth.sin(state.attackAnim * 3.1415927F);
        if (k > 0) {
            this.right_arm.xRot = -k;
        }
        if (state.backCrystals == 4) {
            amethyst.visible = true;
            amethyst2.visible = true;
            amethyst3.visible = true;
            amethyst4.visible = true;
        } else if (state.backCrystals == 3) {
            amethyst.visible = true;
            amethyst2.visible = true;
            amethyst3.visible = false;
            amethyst4.visible = true;
        } else if (state.backCrystals == 2) {
            amethyst.visible = true;
            amethyst2.visible = true;
            amethyst3.visible = false;
            amethyst4.visible = false;
        } else if (state.backCrystals == 1) {
            amethyst.visible = false;
            amethyst2.visible = true;
            amethyst3.visible = false;
            amethyst4.visible = false;
        } else if (state.backCrystals == 0) {
            amethyst.visible = false;
            amethyst2.visible = false;
            amethyst3.visible = false;
            amethyst4.visible = false;
        }
    }

}
