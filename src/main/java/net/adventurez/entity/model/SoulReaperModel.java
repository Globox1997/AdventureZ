package net.adventurez.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Environment(EnvType.CLIENT)
public class SoulReaperModel<T extends Mob & RangedAttackMob> extends HumanoidModel<T> {

    public SoulReaperModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(8, 32).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(47, 11).addBox(-2.0F, -2.0F, -1.0F, 1.0F, 5.0F, 2.0F).texOffs(47, 20)
                        .addBox(-2.0F, 3.0F, -1.0F, 1.0F, 1.0F, 1.0F).texOffs(28, 16).addBox(-2.0F, -1.0F, 1.0F, 3.0F, 3.0F, 1.0F).texOffs(35, 41).addBox(-3.0F, -1.0F, 1.0F, 1.0F, 2.0F, 1.0F).texOffs(32, 8)
                        .addBox(-3.0F, -2.0F, -1.0F, 1.0F, 3.0F, 2.0F).texOffs(32, 25).addBox(-4.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F).texOffs(36, 16).addBox(-4.0F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F).texOffs(40, 37)
                        .addBox(-3.0F, -2.0F, -2.0F, 1.0F, 2.0F, 1.0F).texOffs(20, 16).addBox(-3.0F, -3.0F, -2.0F, 1.0F, 1.0F, 3.0F).texOffs(52, 20).addBox(-3.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F).texOffs(32, 13)
                        .addBox(-2.0F, -3.0F, -1.0F, 1.0F, 1.0F, 2.0F).texOffs(59, 20).addBox(-3.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F).texOffs(38, 21).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F).texOffs(41, 26)
                        .addBox(-1.0F, 2.0F, 1.0F, 1.0F, 1.0F, 1.0F).texOffs(24, 20).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 3.0F, 1.0F).texOffs(38, 29).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 1.0F, 1.0F).texOffs(40, 33)
                        .addBox(-1.0F, 2.0F, -2.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        modelPartData.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(16, 32).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(49, 1).addBox(1.0F, -2.0F, -1.0F, 1.0F, 5.0F, 2.0F).texOffs(42, 42)
                        .addBox(1.0F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F).texOffs(32, 20).addBox(2.0F, -2.0F, -1.0F, 1.0F, 3.0F, 2.0F).texOffs(41, 7).addBox(2.0F, -1.0F, 1.0F, 1.0F, 2.0F, 1.0F).texOffs(42, 2)
                        .addBox(2.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F).texOffs(32, 36).addBox(3.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F).texOffs(37, 24).addBox(3.0F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F).texOffs(41, 23)
                        .addBox(2.0F, -2.0F, -2.0F, 1.0F, 2.0F, 1.0F).texOffs(56, 4).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 3.0F).texOffs(42, 0).addBox(2.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F).texOffs(34, 29)
                        .addBox(1.0F, -3.0F, -1.0F, 1.0F, 1.0F, 2.0F).texOffs(32, 32).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 3.0F, 1.0F).texOffs(39, 31).addBox(0.0F, -2.0F, -2.0F, 2.0F, 1.0F, 1.0F).texOffs(28, 42)
                        .addBox(0.0F, 2.0F, -2.0F, 1.0F, 1.0F, 1.0F).texOffs(56, 11).addBox(-1.0F, -1.0F, 1.0F, 3.0F, 3.0F, 1.0F).texOffs(39, 35).addBox(0.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F).texOffs(24, 42)
                        .addBox(0.0F, 2.0F, 1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        modelPartData.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(24, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(39, 40).addBox(0.0F, 0.0F, 1.0F, 1.0F, 2.0F, 1.0F).texOffs(32, 4)
                        .addBox(0.0F, 0.0F, -2.0F, 1.0F, 2.0F, 1.0F).texOffs(28, 38).addBox(-1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 1.0F).texOffs(24, 38).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 3.0F, 1.0F).texOffs(49, 24)
                        .addBox(-2.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F).texOffs(36, 36).addBox(-2.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(-2.0F, 12.0F, 0.0F));
        modelPartData.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F).texOffs(36, 4).addBox(1.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F).texOffs(39, 13)
                        .addBox(1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F).texOffs(38, 9).addBox(0.0F, 0.0F, -2.0F, 1.0F, 3.0F, 1.0F).texOffs(38, 0).addBox(0.0F, 0.0F, 1.0F, 1.0F, 3.0F, 1.0F).texOffs(40, 17)
                        .addBox(-1.0F, 0.0F, -2.0F, 1.0F, 2.0F, 1.0F).texOffs(40, 4).addBox(-1.0F, 0.0F, 1.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(2.0F, 12.0F, 0.0F));
        modelPartData.addOrReplaceChild("hat", CubeListBuilder.create().addBox(0F, 0F, 0F, 0F, 0F, 0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void prepareMobModel(T mobEntity, float f, float g, float h) {
        this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        ItemStack itemStack = mobEntity.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem() == Items.BOW && mobEntity.isAggressive() && !this.riding) {
            this.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
        }
        super.prepareMobModel((T) mobEntity, f, g, h);
    }

    @Override
    public void setupAnim(T mobEntity, float f, float g, float h, float i, float j) {
        super.setupAnim((T) mobEntity, f, g, h, i, j);
        ItemStack itemStack = mobEntity.getMainHandItem();
        if (mobEntity.isAggressive() && (itemStack.isEmpty() || itemStack.getItem() != Items.BOW) && !this.riding) {
            float k = Mth.sin(this.attackTime * 3.1415927F);
            float l = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * 3.1415927F);
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightArm.yRot = -(0.1F - k * 0.6F);
            this.leftArm.yRot = 0.1F - k * 0.6F;
            this.rightArm.xRot = -1.2707964F;
            this.leftArm.xRot = Mth.cos(f * 0.6662F) * g * 0.5F;
            ModelPart var10000 = this.rightArm;
            var10000.xRot -= k * 1.2F - l * 0.4F;
        }
        if (this.riding) {
            this.rightArm.xRot = 0.3F;
            this.leftArm.xRot = 0.3F;
            if (mobEntity.isAggressive()) {
                if (itemStack.getItem() == Items.BOW) {
                    this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
                    this.leftArm.yRot = 0.1F + this.head.yRot;
                    this.rightArm.xRot = -1.0F + this.head.xRot;
                    this.leftArm.xRot = -1.0F + this.head.xRot;
                } else {
                    float k = Mth.sin(this.attackTime * 3.1415927F);
                    float l = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * 3.1415927F);
                    this.rightArm.zRot = 0.0F;
                    this.leftArm.zRot = 0.0F;
                    this.rightArm.yRot = -(0.1F - k * 0.6F);
                    this.leftArm.yRot = 0.1F - k * 0.6F;
                    this.rightArm.xRot = -0.7F;
                    this.leftArm.xRot = 0.3F;
                    ModelPart var10000 = this.rightArm;
                    var10000.xRot -= k * 1.2F - l * 0.4F;
                }
            }
            this.body.y = 5.2F;
            this.head.y = 5.2F;
            this.rightArm.y = 5.2F;
            this.leftArm.y = 5.2F;
            this.rightLeg.y = rightLeg.y + 4F;
            this.leftLeg.y = leftLeg.y + 4F;
            ModelPart var10000 = this.rightArm;
            var10000 = this.rightArm;
            var10000.xRot += -0.62831855F;
            var10000 = this.leftArm;
            var10000.xRot += -0.62831855F;
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = 0.31415927F;
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = -0.31415927F;
            this.leftLeg.zRot = -0.07853982F;
        }

    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack matrices) {
        float f = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        ModelPart modelPart = this.getArm(arm);
        modelPart.x += f;
        modelPart.translateAndRotate(matrices);
        modelPart.x -= f;
    }
}
