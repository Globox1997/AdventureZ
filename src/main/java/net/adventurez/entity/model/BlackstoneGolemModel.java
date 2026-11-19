package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.BlackstoneGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class BlackstoneGolemModel<T extends BlackstoneGolemEntity> extends ListModel<T> {
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public BlackstoneGolemModel(ModelPart root) {
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
        modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -5.6225F, 8.0F, 10.0F, 8.0F).texOffs(24, 0).addBox(-1.0F, -0.0F, -7.6225F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 2.5846F, -7.1554F));
        modelPartData.addOrReplaceChild("torso",
                CubeListBuilder.create().texOffs(0, 40).addBox(-9.0F, -1.2929F, -8.1213F, 18.0F, 12.0F, 11.0F).texOffs(0, 70).addBox(-4.5F, 10.7071F, -5.1213F, 9.0F, 5.0F, 6.0F).texOffs(42, 55)
                        .addBox(-1.0F, 1.0F, 3.0F, 7.0F, 6.0F, 1.0F).texOffs(12, 57).addBox(-1.0F, 6.0F, -9.0F, 8.0F, 3.0F, 1.0F).texOffs(28, 55).addBox(-8.0F, 3.0F, -9.0F, 6.0F, 2.0F, 1.0F).texOffs(9, 56)
                        .addBox(-6.0F, 5.0F, 3.0F, 3.0F, 4.0F, 1.0F).texOffs(33, 49).addBox(-10.0F, 5.0F, -5.0F, 1.0F, 4.0F, 6.0F).texOffs(16, 54).addBox(9.0F, 6.0F, -4.0F, 1.0F, 4.0F, 3.0F),
                PartPose.offset(0.0F, -2.0F, -1.0F));
        modelPartData.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(60, 21).addBox(-2.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F).texOffs(62, 29).addBox(2.0F, 2.4378F, -1.4019F, 1.0F, 9.0F, 2.0F).texOffs(66, 47)
                .addBox(2.0F, 19.5981F, 0.768F, 1.0F, 4.0F, 1.0F).texOffs(61, 46).addBox(-3.0F, 18.4378F, -1.9019F, 1.0F, 9.0F, 2.0F), PartPose.offset(11.0F, 0.0F, -3.0F));
        modelPartData.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(60, 58).addBox(-2.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F).texOffs(70, 65).addBox(-3.0F, 4.4378F, -0.4019F, 1.0F, 9.0F, 2.0F).texOffs(69, 85)
                .addBox(-3.0F, 20.4378F, -0.9019F, 1.0F, 4.0F, 1.0F).texOffs(60, 80).addBox(2.0F, 18.4378F, -1.9019F, 1.0F, 5.0F, 2.0F), PartPose.offset(-11.0F, 0.0F, -3.0F));
        modelPartData.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(37, 0).addBox(1.5F, -0.6233F, -2.5578F, 6.0F, 16.0F, 5.0F), PartPose.offset(0.0F, 10.0F, 9.0F));
        modelPartData.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(60, 0).addBox(-7.5F, -0.6233F, -2.5578F, 6.0F, 16.0F, 5.0F), PartPose.offset(0.0F, 10.0F, 9.0F));
        return LayerDefinition.create(modelData, 128, 128);
        // this.leftLeg.mirror = true;
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.head, this.torso, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
    }

    @Override
    public void setupAnim(T stoneGolem, float f, float g, float h, float i, float j) {
        int roarTick = stoneGolem.getRoarTick();
        this.head.yRot = i * 0.0077453292F;
        this.head.xRot = j * 0.0017453292F + 0.2618F;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
        this.rightLeg.xRot = -0.3F * Mth.triangleWave(f, 13.0F) * g + 0.1745F;
        this.leftLeg.xRot = 0.3F * Mth.triangleWave(f, 13.0F) * g + 0.1745F;
        this.torso.xRot = 0.7854F;
        this.rightArm.xRot = 0.5F * Mth.triangleWave(f, 13.0F) * g - 0.5236F;
        this.leftArm.xRot = -0.5F * Mth.triangleWave(f, 13.0F) * g - 0.5236F;
        int thrownRockTick = stoneGolem.getEntityData().get(BlackstoneGolemEntity.THROW_COOLDOWN);
        if (thrownRockTick >= 100) {
            this.leftArm.xRot = Mth.cos(-thrownRockTick * 0.2F + 0.3F) - 0.3F;
        }
        if (roarTick > 18) {
            this.rightArm.xRot = Mth.sin(roarTick / 5.832F) - 0.5236F;
            this.leftArm.xRot = Mth.sin(roarTick / 5.832F) - 0.5236F;
        }
        float k = Mth.sin(this.attackTime * 3.1415927F) * 0.5F;
        if (k > 0.1F) {
            this.rightArm.xRot = -k - 0.5236F;
        }
    }

}
