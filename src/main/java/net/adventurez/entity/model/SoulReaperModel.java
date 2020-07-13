package net.adventurez.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SoulReaperModel<T extends MobEntity & RangedAttackMob> extends BipedEntityModel<T> {
  public SoulReaperModel() {
    this(0.0F);
  }

  public SoulReaperModel(float stretch) {
    super(stretch);

    this.torso = (new ModelPart(this)).setTextureSize(64, 64);
    this.torso.setPivot(0.0F, 0.0F, 0.0F);
    this.torso.setTextureOffset(0, 16).addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F);

    this.head = (new ModelPart(this)).setTextureSize(64, 64);
    this.head.setPivot(0.0F, 0.0F, 0.0F);
    this.head.setTextureOffset(0, 0).addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F);

    this.rightArm = (new ModelPart(this)).setTextureSize(64, 64);
    this.rightArm.setPivot(-5.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(8, 32).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(47, 11).addCuboid(-2.0F, -2.0F, -1.0F, 1.0F, 5.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(47, 20).addCuboid(-2.0F, 3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(28, 16).addCuboid(-2.0F, -1.0F, 1.0F, 3.0F, 3.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(35, 41).addCuboid(-3.0F, -1.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(32, 8).addCuboid(-3.0F, -2.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(32, 25).addCuboid(-4.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(36, 16).addCuboid(-4.0F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(40, 37).addCuboid(-3.0F, -2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(20, 16).addCuboid(-3.0F, -3.0F, -2.0F, 1.0F, 1.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(52, 20).addCuboid(-3.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(32, 13).addCuboid(-2.0F, -3.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(59, 20).addCuboid(-3.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(38, 21).addCuboid(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(41, 26).addCuboid(-1.0F, 2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(24, 20).addCuboid(-2.0F, -1.0F, -2.0F, 3.0F, 3.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(38, 29).addCuboid(-2.0F, -2.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(40, 33).addCuboid(-1.0F, 2.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(64, 64);
    this.leftArm.setPivot(5.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(16, 32).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(49, 1).addCuboid(1.0F, -2.0F, -1.0F, 1.0F, 5.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(42, 42).addCuboid(1.0F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(32, 20).addCuboid(2.0F, -2.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(41, 7).addCuboid(2.0F, -1.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(42, 2).addCuboid(2.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(32, 36).addCuboid(3.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(37, 24).addCuboid(3.0F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(41, 23).addCuboid(2.0F, -2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(56, 4).addCuboid(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 3.0F, 0.0F);
    this.leftArm.setTextureOffset(42, 0).addCuboid(2.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(34, 29).addCuboid(1.0F, -3.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(32, 32).addCuboid(-1.0F, -1.0F, -2.0F, 3.0F, 3.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(39, 31).addCuboid(0.0F, -2.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(28, 42).addCuboid(0.0F, 2.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(56, 11).addCuboid(-1.0F, -1.0F, 1.0F, 3.0F, 3.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(39, 35).addCuboid(0.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(24, 42).addCuboid(0.0F, 2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F);

    this.rightLeg = (new ModelPart(this)).setTextureSize(64, 64);
    this.rightLeg.setPivot(-2.0F, 12.0F, 0.0F);
    this.rightLeg.setTextureOffset(24, 24).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);
    this.rightLeg.setTextureOffset(39, 40).addCuboid(0.0F, 0.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightLeg.setTextureOffset(32, 4).addCuboid(0.0F, 0.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightLeg.setTextureOffset(28, 38).addCuboid(-1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.rightLeg.setTextureOffset(24, 38).addCuboid(-1.0F, 0.0F, -2.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.rightLeg.setTextureOffset(49, 24).addCuboid(-2.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.rightLeg.setTextureOffset(36, 36).addCuboid(-2.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F);

    this.leftLeg = (new ModelPart(this)).setTextureSize(64, 64);
    this.leftLeg.setPivot(2.0F, 12.0F, 0.0F);
    this.leftLeg.setTextureOffset(0, 32).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);
    this.leftLeg.setTextureOffset(36, 4).addCuboid(1.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.leftLeg.setTextureOffset(39, 13).addCuboid(1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.leftLeg.setTextureOffset(38, 9).addCuboid(0.0F, 0.0F, -2.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.leftLeg.setTextureOffset(38, 0).addCuboid(0.0F, 0.0F, 1.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.leftLeg.setTextureOffset(40, 17).addCuboid(-1.0F, 0.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.leftLeg.setTextureOffset(40, 4).addCuboid(-1.0F, 0.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F);

    this.helmet = new ModelPart(this, 64, 64);
    this.helmet.addCuboid(0F, 0F, 0F, 0F, 0F, 0F, 0F);
    this.helmet.setPivot(0.0F, 0.0F, 0.0F);

  }

  @Override
  public void animateModel(T mobEntity, float f, float g, float h) {
    this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
    this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
    ItemStack itemStack = mobEntity.getStackInHand(Hand.MAIN_HAND);
    if (itemStack.getItem() == Items.BOW && mobEntity.isAttacking() && !this.riding) {
      this.rightArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
    }
    super.animateModel((T) mobEntity, f, g, h);
  }

  @Override
  public void setAngles(T mobEntity, float f, float g, float h, float i, float j) {
    super.setAngles((T) mobEntity, f, g, h, i, j);
    ItemStack itemStack = mobEntity.getMainHandStack();
    if (mobEntity.isAttacking() && (itemStack.isEmpty() || itemStack.getItem() != Items.BOW) && !this.riding) {
      float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
      float l = MathHelper.sin((1.0F - (1.0F - this.handSwingProgress) * (1.0F - this.handSwingProgress)) * 3.1415927F);
      this.rightArm.roll = 0.0F;
      this.leftArm.roll = 0.0F;
      this.rightArm.yaw = -(0.1F - k * 0.6F);
      this.leftArm.yaw = 0.1F - k * 0.6F;
      this.rightArm.pitch = -1.5707964F;
      this.leftArm.pitch = -1.5707964F;
      ModelPart var10000 = this.rightArm;
      var10000.pitch -= k * 1.2F - l * 0.4F;
      var10000 = this.leftArm;
      var10000.pitch -= k * 1.2F - l * 0.4F;
    }
    if (this.riding) {
      if (mobEntity.isAttacking()) {
        if (itemStack.getItem() == Items.BOW) {
          this.rightArm.yaw = -0.1F + this.head.yaw - 0.4F;
          this.leftArm.yaw = 0.1F + this.head.yaw;
          this.rightArm.pitch = -1.0F + this.head.pitch;
          this.leftArm.pitch = -1.0F + this.head.pitch;
        } else {
          float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
          float l = MathHelper
              .sin((1.0F - (1.0F - this.handSwingProgress) * (1.0F - this.handSwingProgress)) * 3.1415927F);
          this.rightArm.roll = 0.0F;
          this.leftArm.roll = 0.0F;
          this.rightArm.yaw = -(0.1F - k * 0.6F);
          this.leftArm.yaw = 0.1F - k * 0.6F;
          this.rightArm.pitch = -1.0F;
          this.leftArm.pitch = -1.0F;
          ModelPart var10000 = this.rightArm;
          var10000.pitch -= k * 1.2F - l * 0.4F;
          var10000 = this.leftArm;
          var10000.pitch -= k * 1.2F - l * 0.4F;
        }
      }
      this.torso.pivotY = 5.2F;
      this.head.pivotY = 5.2F;
      this.rightArm.pivotY = 5.2F;
      this.leftArm.pivotY = 5.2F;
      this.rightLeg.pivotY = rightLeg.pivotY + 4F;
      this.leftLeg.pivotY = leftLeg.pivotY + 4F;
      ModelPart var10000 = this.rightArm;
      var10000 = this.rightArm;
      var10000.pitch += -0.62831855F;
      var10000 = this.leftArm;
      var10000.pitch += -0.62831855F;
      this.rightLeg.pitch = -1.4137167F;
      this.rightLeg.yaw = 0.31415927F;
      this.rightLeg.roll = 0.07853982F;
      this.leftLeg.pitch = -1.4137167F;
      this.leftLeg.yaw = -0.31415927F;
      this.leftLeg.roll = -0.07853982F;
    }

  }

  @Override
  public void setArmAngle(Arm arm, MatrixStack matrices) {
    float f = arm == Arm.RIGHT ? 1.0F : -1.0F;
    ModelPart modelPart = this.getArm(arm);
    modelPart.pivotX += f;
    modelPart.rotate(matrices);
    modelPart.pivotX -= f;
  }
}
