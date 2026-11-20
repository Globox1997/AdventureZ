package net.adventurez.entity.model;


import net.adventurez.entity.render.state.SummonerRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.SummonerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class SummonerModel extends EntityModel<SummonerRenderState> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public SummonerModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(32, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F).texOffs(44, 48).addBox(-9.0F, -1.0F, -3.0F, 5.0F, 4.0F, 7.0F).texOffs(20, 48)
                        .addBox(4.0F, -1.0F, -3.0F, 5.0F, 4.0F, 7.0F).texOffs(56, 0).addBox(-4.0F, 12.0F, -2.0F, 8.0F, 9.0F, 0.0F).texOffs(48, 16).addBox(-4.0F, 12.0F, 2.0F, 8.0F, 9.0F, 0.0F).texOffs(52, 55)
                        .addBox(4.0F, 12.0F, -2.0F, 0.0F, 9.0F, 4.0F).texOffs(44, 55).addBox(-4.0F, 12.0F, -2.0F, 0.0F, 9.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData1.addOrReplaceChild("cape",
                CubeListBuilder.create().texOffs(10, 40).addBox(-8.0F, -2.0F, 0.0F, 0.0F, 22.0F, 5.0F).texOffs(0, 40).addBox(8.0F, -2.0F, 0.0F, 0.0F, 22.0F, 5.0F).texOffs(3, 67)
                        .addBox(7.0F, -2.0F, 0.0F, 1.0F, 22.0F, 0.0F).texOffs(0, 67).addBox(-8.0F, -2.0F, 0.0F, 1.0F, 22.0F, 0.0F).texOffs(0, 23).addBox(-8.0F, -2.0F, 5.0F, 16.0F, 22.0F, 0.0F),
                PartPose.offset(0.0F, 1.0F, -2.0F));
        modelPartData.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(24, 0).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F).texOffs(36, 16).addBox(-5.0F, -9.0F, -6.0F, 1.0F, 4.0F, 10.0F).texOffs(24, 16)
                        .addBox(-4.0F, -9.0F, 3.0F, 8.0F, 4.0F, 1.0F).texOffs(0, 14).addBox(-4.0F, -9.0F, -6.0F, 8.0F, 4.0F, 1.0F).texOffs(0, 0).addBox(4.0F, -9.0F, -6.0F, 1.0F, 4.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("rightArm",
                CubeListBuilder.create().texOffs(36, 59).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(37, 48).addBox(-1.5F, 7.0F, -8.0F, 3.0F, 3.0F, 3.0F).texOffs(0, 8)
                        .addBox(-1.5F, 8.0F, -5.0F, 3.0F, 1.0F, 1.0F).texOffs(17, 18).addBox(-0.5F, 7.0F, -5.0F, 1.0F, 3.0F, 1.0F).texOffs(12, 4).addBox(-0.5F, 6.0F, -7.0F, 1.0F, 1.0F, 3.0F).texOffs(12, 0)
                        .addBox(-0.5F, 10.0F, -7.0F, 1.0F, 1.0F, 3.0F).texOffs(0, 4).addBox(-2.5F, 8.0F, -7.0F, 1.0F, 1.0F, 3.0F).texOffs(0, 0).addBox(1.5F, 8.0F, -7.0F, 1.0F, 1.0F, 3.0F).texOffs(0, 0)
                        .addBox(-0.5F, 8.0F, -4.0F, 1.0F, 1.0F, 22.0F),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(28, 59).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        modelPartData.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(20, 59).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(56, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }


    @Override
    public void setupAnim(SummonerRenderState state) {
        float f = state.walkAnimationPos;
        float g = state.walkAnimationSpeed;
        float h = state.ageInTicks;
        float i = state.yRot;
        float j = state.xRot;
        this.head.yRot = i * 0.0119453292F;
        this.head.xRot = j * 0.0061453292F;
        this.rightArm.xRot = (Mth.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.5F - 1.0472F);
        this.rightArm.yRot = 0.0F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.xRot = Mth.cos(f * 0.6662F) * 2.0F * g * 0.5F;
        if (this.leftArm.xRot > 0.2F) {
            this.leftArm.xRot = 0.2F;
        }
        this.leftArm.yRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * g * 0.5F;
        this.rightLeg.yRot = 0.0F;
        this.rightLeg.zRot = 0.0F;
        this.leftLeg.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
        this.leftLeg.yRot = 0.0F;
        this.leftLeg.zRot = 0.0F;

        if (state.spellcasting) {
            this.rightArm.z = 0.0F;
            this.rightArm.x = 7.0F;
            this.rightArm.xRot = Mth.cos(h * 0.6662F) * 0.1F - 0.4F;
            this.rightArm.zRot = -1.7F;
            this.rightArm.yRot = -0.45F;
        }
    }

}
