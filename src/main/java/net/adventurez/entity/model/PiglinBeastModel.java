package net.adventurez.entity.model;

import net.adventurez.entity.render.state.PiglinBeastRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.PiglinBeastEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class PiglinBeastModel extends EntityModel<PiglinBeastRenderState> {
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
        super(root);
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

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(76, 72).addBox(-5.0F, -10.0086F, -4.1305F, 10.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(42, 55).addBox(0.0F, -18.0086F, -4.1305F, 0.0F, 16.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(-3.0F, -6.0086F, -5.1305F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(35, 38).mirror().addBox(-12.0F, -46.0F, -3.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 38.9914F, 1.8695F, 0.0F, -0.7854F, 0.0F));

        PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(35, 38).addBox(5.0F, -46.0F, -3.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 38.9914F, 1.8695F, 0.0F, 0.7854F, 0.0F));

        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.0086F, -0.1305F, 0.0F, 0.0F, -1.9199F));

        PartDefinition leftEar = ears.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(35, 51).addBox(-5.2103F, -0.1205F, -3.1305F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 4.0086F, 1.1305F));

        PartDefinition rightEar = ears.addOrReplaceChild("rightEar", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -3.9914F, 0.1305F, 3.1416F, 0.0F, 0.6545F));

        PartDefinition bone7_r1 = rightEar.addOrReplaceChild("bone7_r1", CubeListBuilder.create().texOffs(35, 51).addBox(1.8794F, -46.1262F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.998F, 46.0534F, -0.8695F, 0.0F, 0.0F, -0.0436F));

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, 0.0F, -9.0F, 20.0F, 14.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(35, 30).addBox(-9.0F, 11.0F, -9.0F, 20.0F, 5.0F, 16.0F, new CubeDeformation(0.3F))
                .texOffs(96, 51).addBox(-9.0F, 14.0F, -9.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(90, 91).addBox(-9.0F, 14.0F, 7.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 75).addBox(11.0F, 14.0F, -9.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(42, 72).addBox(-9.0F, 14.0F, -9.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -3.0F, 1.0F));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(52, 51).addBox(-8.0F, -32.25F, -3.0F, 16.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 25.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition flag = body.addOrReplaceChild("flag", CubeListBuilder.create().texOffs(54, 96).addBox(-1.0F, -33.8093F, 0.5517F, 2.0F, 29.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(72, 0).addBox(-2.0F, -37.8775F, -0.9306F, 4.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -4.8093F, -0.4483F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 12.0F, 7.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition flagCloth = flag.addOrReplaceChild("flagCloth", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -0.6136F, -6.8755F, 0.0F, 24.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -33.8775F, 9.0694F, 0.1745F, 0.0F, 0.0F));

        PartDefinition rightArm = modelPartData.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(68, 91).addBox(-3.9115F, -2.3282F, -3.0F, 5.0F, 24.0F, 6.0F, new CubeDeformation(-0.02F))
                .texOffs(106, 64).addBox(-4.9115F, 2.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(98, 0).addBox(-4.9115F, 7.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(56, 0).addBox(-4.9115F, 14.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(26, 110).addBox(-0.9115F, -3.3282F, -4.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(108, 111).addBox(-0.9115F, 10.6718F, -4.0F, 3.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -10.0F, 1.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition rightLeg = modelPartData.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 103).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.9F, 11.0F, 0.0F));

        PartDefinition leftLeg = modelPartData.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(90, 99).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(3.9F, 11.0F, 0.0F));

        PartDefinition leftArm = modelPartData.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(68, 91).mirror().addBox(-1.0885F, -2.3282F, -3.0F, 5.0F, 24.0F, 6.0F, new CubeDeformation(-0.02F)).mirror(false)
                .texOffs(56, 0).mirror().addBox(-2.0885F, 14.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(108, 111).mirror().addBox(-2.0885F, 10.6718F, -4.0F, 3.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(98, 0).mirror().addBox(-2.0885F, 7.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(106, 64).mirror().addBox(-2.0885F, 2.6718F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(26, 110).mirror().addBox(-2.0885F, -3.3282F, -4.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, -10.0F, 1.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition hammer = leftArm.addOrReplaceChild("hammer", CubeListBuilder.create().texOffs(0, 30).addBox(-1.8774F, -2.0694F, -19.0F, 3.0F, 3.0F, 29.0F, new CubeDeformation(0.0F))
                .texOffs(35, 30).addBox(-2.3774F, -2.5694F, 10.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(91, 23).addBox(-4.8774F, 1.9306F, -28.0F, 9.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(20, 91).addBox(-2.8774F, -4.0694F, -27.0F, 5.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0012F, 19.7412F, 0.0F));

        PartDefinition leftArm_r1 = hammer.addOrReplaceChild("leftArm_r1", CubeListBuilder.create().texOffs(91, 23).addBox(5.1226F, 19.9306F, 18.0F, 9.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 17.0F, -1.0F, 3.1416F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }


    @Override
    public void setupAnim(PiglinBeastRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;
        this.head.yRot = headYaw * 0.0079453292F;
        this.head.xRot = headPitch * 0.0027453292F;

        float l = animationProgress * 0.1F + limbAngle * 0.5F;
        float m = 0.08F + limbDistance * 0.4F;
        this.leftEar.zRot = (float) (-Math.PI / 6) - Mth.cos(l * 1.2F) * m;
        this.rightEar.zRot = (float) (Math.PI / 6) + Mth.cos(l) * m;

        // Legs
        this.rightLeg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftLeg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        // Flag
        this.flagCloth.zRot = Mth.cos(animationProgress / 10) / 4;
        // Arms
        this.rightArm.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
        this.leftArm.xRot = -Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;

        float k = Mth.sin(state.attackAnim * 3.1415927F);
        if (k > 0) {
            this.leftArm.xRot = -k;
        }

        float handUp = state.leadArm;
        if (handUp > 0.0F) {
            float g = handUp;
            float h = Mth.sin(g * 3.1415927F);
            float i = Mth.sin(handUp * 3.1415927F) * -(this.head.xRot - 0.7F) * 0.75F;
            this.rightArm.xRot = (float) ((double) this.rightArm.xRot - ((double) h * 1.2D + (double) i));
        }
    }

}