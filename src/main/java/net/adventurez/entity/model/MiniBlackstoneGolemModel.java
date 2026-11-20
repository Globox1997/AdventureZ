package net.adventurez.entity.model;


import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.MiniBlackstoneGolemEntity;
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
public class MiniBlackstoneGolemModel extends EntityModel<AdventureRenderState> {
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public MiniBlackstoneGolemModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.torso = root.getChild("torso");
        this.rightArm = root.getChild("rightArm");
        this.leftArm = root.getChild("leftArm");
        this.rightLeg = root.getChild("rightLeg");
        this.leftLeg = root.getChild("leftLeg");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.2796F, -7.044F, 8.0F, 10.0F, 8.0F).texOffs(24, 0).addBox(-1.0F, -1.2796F, -9.044F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 13.0F, -5.0F));
        modelPartData.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 40).addBox(-9.0F, -5.7148F, -5.5702F, 18.0F, 11.0F, 11.0F), PartPose.offset(0.0F, 15.0F, 0.0F));
        modelPartData.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(60, 21).addBox(9.0F, -2.4685F, -2.9247F, 4.0F, 13.0F, 6.0F), PartPose.offset(0.0F, 15.0F, -4.0F));
        modelPartData.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(60, 58).addBox(-13.0F, -2.4685F, -2.9247F, 4.0F, 13.0F, 6.0F), PartPose.offset(0.0F, 15.0F, -4.0F));
        modelPartData.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(37, 0).addBox(1.5F, -1.7043F, -2.5805F, 6.0F, 11.0F, 5.0F), PartPose.offset(0.0F, 16.0F, 5.0F));
        modelPartData.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(60, 0).addBox(-7.5F, -1.7043F, -2.5805F, 6.0F, 11.0F, 5.0F), PartPose.offset(0.0F, 16.0F, 5.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }


    @Override
    public void setupAnim(AdventureRenderState state) {
        float f = state.walkAnimationPos;
        float g = state.walkAnimationSpeed;
        float h = state.ageInTicks;
        float i = state.yRot;
        float j = state.xRot;
        this.head.yRot = i * 0.0077453292F;
        this.head.xRot = j * 0.0017453292F + 0.1745F;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
        this.rightLeg.xRot = -0.3F * Mth.triangleWave(f, 13.0F) * g * 1.3F + 0.4363F;
        this.leftLeg.xRot = 0.3F * Mth.triangleWave(f, 13.0F) * g * 1.3F + 0.4363F;
        this.torso.xRot = 1.309F;
        this.rightArm.xRot = 0.5F * Mth.triangleWave(f, 13.0F) * g * 1.3F - 0.6109F;
        this.leftArm.xRot = -0.5F * Mth.triangleWave(f, 13.0F) * g * 1.3F - 0.6109F;
        float k = Mth.sin(state.attackTime * 3.1415927F);
        if (k > 0) {
            this.rightArm.xRot = -k * 1.5F;
        }
    }

}
