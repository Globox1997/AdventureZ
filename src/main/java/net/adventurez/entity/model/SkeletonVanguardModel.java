package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.SkeletonVanguardEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SkeletonVanguardModel<T extends SkeletonVanguardEntity> extends CompositeEntityModel<T> {
  private final ModelPart head;
  private final ModelPart spear;
  private final ModelPart body;
  private final ModelPart leftArm;
  private final ModelPart rightArm;
  private final ModelPart leftLeg;
  private final ModelPart rightLeg;

  public SkeletonVanguardModel() {
    this.body = (new ModelPart(this)).setTextureSize(128, 128);
    this.body.setPivot(0.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(0, 38).addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F);
    this.body.setTextureOffset(32, 0).addCuboid(-5.0F, 0.0F, -3.0F, 10.0F, 6.0F, 6.0F, 0.0F);

    this.head = (new ModelPart(this)).setTextureSize(128, 128);
    this.head.setPivot(0.0F, 0.0F, 0.0F);
    this.head.setTextureOffset(32, 32).addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F);
    this.head.setTextureOffset(0, 21).addCuboid(-5.0F, -9.0F, -5.0F, 10.0F, 7.0F, 10.0F, 0.0F);
    this.head.setTextureOffset(24, 38).addCuboid(-1.0F, -11.0F, -6.0F, 2.0F, 8.0F, 1.0F, 0.0F);

    this.rightArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightArm.setPivot(5.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(22, 63).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);

    this.spear = (new ModelPart(this)).setTextureSize(128, 128);
    this.spear.setPivot(0.0F, 9.0F, 0.0F);
    this.rightArm.addChild(spear);

    this.spear.setTextureOffset(58, 0).addCuboid(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(56, 36).addCuboid(-0.5F, 1.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(56, 33).addCuboid(-0.5F, 2.0F, -3.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(56, 30).addCuboid(-0.5F, 3.0F, -4.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(44, 55).addCuboid(-0.5F, 4.0F, -5.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(54, 51).addCuboid(-0.5F, 5.0F, -6.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(54, 48).addCuboid(-0.5F, 6.0F, -7.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(54, 54).addCuboid(-0.5F, 7.0F, -8.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(40, 54).addCuboid(-0.5F, 8.0F, -9.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(32, 16).addCuboid(-0.5F, 9.0F, -11.0F, 1.0F, 1.0F, 3.0F, 0.0F);
    this.spear.setTextureOffset(30, 21).addCuboid(-0.5F, 10.0F, -12.0F, 1.0F, 1.0F, 5.0F, 0.0F);
    this.spear.setTextureOffset(0, 54).addCuboid(-0.5F, 11.0F, -13.0F, 1.0F, 1.0F, 4.0F, 0.0F);
    this.spear.setTextureOffset(40, 49).addCuboid(-0.5F, 12.0F, -14.0F, 1.0F, 1.0F, 4.0F, 0.0F);
    this.spear.setTextureOffset(34, 48).addCuboid(-0.5F, 13.0F, -15.0F, 1.0F, 1.0F, 4.0F, 0.0F);
    this.spear.setTextureOffset(40, 27).addCuboid(-0.5F, 14.0F, -16.0F, 1.0F, 1.0F, 4.0F, 0.0F);
    this.spear.setTextureOffset(40, 12).addCuboid(-0.5F, 15.0F, -17.0F, 1.0F, 1.0F, 4.0F, 0.0F);
    this.spear.setTextureOffset(0, 26).addCuboid(-0.5F, 16.0F, -18.0F, 1.0F, 1.0F, 4.0F, 0.0F);
    this.spear.setTextureOffset(0, 21).addCuboid(-0.5F, 17.0F, -19.0F, 1.0F, 1.0F, 4.0F, 0.0F);
    this.spear.setTextureOffset(9, 12).addCuboid(-0.5F, 18.0F, -20.0F, 1.0F, 1.0F, 4.0F, 0.0F);
    this.spear.setTextureOffset(0, 2).addCuboid(-0.5F, 11.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.spear.setTextureOffset(52, 12).addCuboid(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(46, 17).addCuboid(-0.5F, -2.0F, 1.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(46, 12).addCuboid(-0.5F, -3.0F, 2.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(40, 17).addCuboid(-0.5F, -4.0F, 3.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(37, 21).addCuboid(-0.5F, -5.0F, 4.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(12, 17).addCuboid(-0.5F, -6.0F, 5.0F, 1.0F, 1.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(32, 0).addCuboid(-0.5F, -8.0F, 6.0F, 1.0F, 2.0F, 2.0F, 0.0F);
    this.spear.setTextureOffset(0, 0).addCuboid(-0.5F, -9.0F, 8.0F, 1.0F, 1.0F, 1.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftArm.setPivot(-5.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(56, 59).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(0, 0).addCuboid(-3.0F, 11.0F, -6.0F, 6.0F, 1.0F, 20.0F, 0.0F);
    this.leftArm.setTextureOffset(0, 12).addCuboid(-1.0F, 10.0F, -2.0F, 2.0F, 1.0F, 5.0F, 0.0F);
    this.leftArm.setTextureOffset(32, 12).addCuboid(3.0F, 11.0F, -4.0F, 3.0F, 1.0F, 3.0F, 0.0F);
    this.leftArm.setTextureOffset(0, 54).addCuboid(3.0F, 11.0F, 4.0F, 2.0F, 1.0F, 10.0F, 0.0F);
    this.leftArm.setTextureOffset(40, 48).addCuboid(-5.0F, 11.0F, 4.0F, 2.0F, 1.0F, 10.0F, 0.0F);
    this.leftArm.setTextureOffset(30, 27).addCuboid(-6.0F, 11.0F, -4.0F, 3.0F, 1.0F, 3.0F, 0.0F);
    this.leftArm.setTextureOffset(0, 6).addCuboid(3.0F, 11.0F, -1.0F, 5.0F, 1.0F, 5.0F, 0.0F);
    this.leftArm.setTextureOffset(0, 0).addCuboid(-8.0F, 11.0F, -1.0F, 5.0F, 1.0F, 5.0F, 0.0F);

    this.rightLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightLeg.setPivot(2.0F, 12.0F, 0.0F);
    this.rightLeg.setTextureOffset(48, 59).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);
    this.rightLeg.setTextureOffset(18, 48).addCuboid(-2.0F, -2.0F, -3.0F, 5.0F, 9.0F, 6.0F, 0.0F);

    this.leftLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftLeg.setPivot(-2.0F, 12.0F, 0.0F);
    this.leftLeg.setTextureOffset(40, 59).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);
    this.leftLeg.setTextureOffset(46, 15).addCuboid(-3.0F, -2.0F, -3.0F, 5.0F, 9.0F, 6.0F, 0.0F);

  }

  @Override
  public Iterable<ModelPart> getParts() {
    return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
  }

  @Override
  public void setAngles(T vanguard, float f, float g, float h, float i, float j) {
    this.spear.pitch = -0.7854F;
    this.rightArm.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.4F;
    this.rightArm.yaw = 0.0F;
    this.rightArm.roll = 0.0F;
    this.leftArm.pitch = (MathHelper.cos(f * 0.6662F) * 2.0F * g * 0.5F) * 0.2F - 1.5708F;
    this.leftArm.yaw = 0.0F;
    this.leftArm.roll = 0.0F;
    this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g * 0.5F;
    this.rightLeg.yaw = 0.0F;
    this.rightLeg.roll = 0.0F;
    this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
    this.leftLeg.yaw = 0.0F;
    this.leftLeg.roll = 0.0F;
    float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
    this.rightArm.pitch = -k * 1.5F;
    float shieldSwing = vanguard.getDataTracker().get(SkeletonVanguardEntity.SHIELD_SWING) * 2F;
    if (shieldSwing > 0.0F) {
      this.leftArm.pitch = MathHelper.sin(shieldSwing * 3.1415927F) - 1.5708F;
    }
  }

}
