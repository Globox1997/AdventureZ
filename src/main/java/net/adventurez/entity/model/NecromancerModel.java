package net.adventurez.entity.model;


import net.adventurez.entity.render.state.NecromancerRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.NecromancerEntity;
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
public class NecromancerModel extends EntityModel<NecromancerRenderState> {
    private final ModelPart head;
    private final ModelPart head2;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public NecromancerModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.head2 = this.head.getChild("head2");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(27, 8).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 8.0F, 8.0F).texOffs(16, 0).addBox(5.0F, -2.0F, -2.0F, 1.0F, 3.0F, 1.0F).texOffs(0, 2)
                        .addBox(-6.0F, -2.0F, -2.0F, 1.0F, 3.0F, 1.0F).texOffs(4, 20).addBox(-5.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F).texOffs(0, 20).addBox(-5.0F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F).texOffs(19, 3)
                        .addBox(4.0F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F).texOffs(16, 4).addBox(4.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F).texOffs(0, 0).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, -6.0F, -2.0F));
        modelPartData1.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(36, 36).addBox(-8.0F, -11.0F, 0.0F, 16.0F, 14.0F, 0.0F), PartPose.offset(0.0F, -2.0F, 1.0F));
        modelPartData.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 27).addBox(-5.0F, -5.0F, -3.0F, 10.0F, 9.0F, 8.0F).texOffs(0, 44).addBox(-5.0F, 4.0F, -3.0F, 10.0F, 6.0F, 6.0F).texOffs(19, 0)
                        .addBox(-6.0F, 10.0F, -4.0F, 12.0F, 0.0F, 8.0F).texOffs(52, 24).addBox(-6.0F, 10.0F, 4.0F, 12.0F, 12.0F, 0.0F).texOffs(51, 0).addBox(-6.0F, 10.0F, -4.0F, 12.0F, 12.0F, 0.0F).texOffs(48, 48)
                        .addBox(-6.0F, 10.0F, -4.0F, 0.0F, 12.0F, 8.0F).texOffs(0, 48).addBox(6.0F, 10.0F, -4.0F, 0.0F, 12.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(60, 64).addBox(-1.0F, 3.0F, -2.0F, 4.0F, 14.0F, 4.0F).texOffs(10, 64).addBox(-2.0F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F),
                PartPose.offset(-8.0F, -3.0F, 0.0F));
        modelPartData.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(44, 68).addBox(-2.0F, 3.0F, -2.0F, 4.0F, 14.0F, 4.0F).texOffs(0, 0).addBox(0.0F, 13.0F, -15.0F, 1.0F, 2.0F, 25.0F).texOffs(59, 12)
                .addBox(-2.0F, 11.0F, -21.0F, 5.0F, 6.0F, 6.0F).texOffs(62, 44).addBox(-2.0F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F), PartPose.offset(7.0F, -3.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(26, 50).addBox(-2.0F, 2.0F, -3.0F, 5.0F, 14.0F, 6.0F), PartPose.offset(-2.9F, 8.0F, 0.0F));
        modelPartData.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 2.0F, -3.0F, 5.0F, 14.0F, 6.0F), PartPose.offset(2.9F, 8.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }


    @Override
    public void setupAnim(NecromancerRenderState state) {
        float f = state.walkAnimationPos;
        float g = state.walkAnimationSpeed;
        float h = state.ageInTicks;
        float i = state.yRot;
        float j = state.xRot;
        this.head.yRot = i * 0.0119453292F;
        this.head.xRot = j * 0.0061453292F;
        this.head2.xRot = -0.2618F;
        this.rightArm.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.5F;
        this.rightArm.yRot = 0.0F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.xRot = Mth.cos(f * 0.6662F) * 2.0F * g * 0.5F;
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
            this.rightArm.xRot = Mth.cos(h * 0.6662F) * 0.1F - 0.6F;
            this.rightArm.zRot = -2.0F;
            this.rightArm.yRot = -0.5F;
        }
    }

}
