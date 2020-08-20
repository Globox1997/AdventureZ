package net.adventurez.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.mob.MobEntity;

@Environment(EnvType.CLIENT)
public class WitherPuppetModel<T extends MobEntity> extends BipedEntityModel<T> {
  public WitherPuppetModel() {
    this(0.0F);
  }

  public WitherPuppetModel(float stretch) {
    super(stretch);
    this.torso = (new ModelPart(this)).setTextureSize(64, 64);
    this.torso.setPivot(0.0F, 14.0F, 0.0F);
    this.torso.setTextureOffset(0, 16).addCuboid(-4.0F, -3.0F, -2.0F, 8.0F, 7.0F, 4.0F, 0.0F);

    this.head = (new ModelPart(this)).setTextureSize(64, 64);
    this.head.setPivot(0.0F, 10.0F, 0.0F);
    this.head.setTextureOffset(0, 0).addCuboid(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F);

    this.rightArm = (new ModelPart(this)).setTextureSize(64, 64);
    this.rightArm.setPivot(5.0F, 12.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 27).addCuboid(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(64, 64);
    this.leftArm.setPivot(-5.0F, 12.0F, 0.0F);
    this.leftArm.setTextureOffset(24, 24).addCuboid(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F);

    this.rightLeg = (new ModelPart(this)).setTextureSize(64, 64);
    this.rightLeg.setPivot(2.0F, 17.0F, 0.0F);
    this.rightLeg.setTextureOffset(24, 0).addCuboid(-1.0F, 1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F);

    this.leftLeg = (new ModelPart(this)).setTextureSize(64, 64);
    this.leftLeg.setPivot(-2.0F, 17.0F, 0.0F);
    this.leftLeg.setTextureOffset(0, 0).addCuboid(-1.0F, 1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F);

  }

  @Override
  public void setAngles(T puppet, float f, float g, float h, float i, float j) {
    float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
    this.rightArm.roll = 0.0F;
    this.leftArm.roll = 0.0F;
    this.rightArm.yaw = -(0.1F - k * 0.6F);
    this.leftArm.yaw = 0.1F - k * 0.6F;
    this.rightArm.pitch = -k * 1.2F;
    this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
    this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
  }

}
