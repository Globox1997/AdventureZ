package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.NecromancerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class NecromancerModel<T extends NecromancerEntity> extends CompositeEntityModel<T> {
  private final ModelPart head;
  private final ModelPart head2;
  private final ModelPart body;
  private final ModelPart leftArm;
  private final ModelPart rightArm;
  private final ModelPart leftLeg;
  private final ModelPart rightLeg;

  public NecromancerModel() {
    this.head = (new ModelPart(this)).setTextureSize(128, 128);
    this.head.setPivot(0.0F, -6.0F, -2.0F);
    this.head.setTextureOffset(27, 8).addCuboid(-4.0F, -6.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F);
    this.head.setTextureOffset(16, 0).addCuboid(5.0F, -2.0F, -2.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(0, 2).addCuboid(-6.0F, -2.0F, -2.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(4, 20).addCuboid(-5.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(0, 20).addCuboid(-5.0F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(19, 3).addCuboid(4.0F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(16, 4).addCuboid(4.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(0, 0).addCuboid(-1.0F, -1.0F, -5.0F, 2.0F, 1.0F, 1.0F, 0.0F);

    this.head2 = (new ModelPart(this)).setTextureSize(128, 128);
    this.head2.setPivot(0.0F, -2.0F, 1.0F);
    this.head.addChild(head2);
    this.head2.setTextureOffset(36, 36).addCuboid(-8.0F, -11.0F, 0.0F, 16.0F, 14.0F, 0.0F, 0.0F);

    this.body = (new ModelPart(this)).setTextureSize(128, 128);
    this.body.setPivot(0.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(0, 27).addCuboid(-5.0F, -5.0F, -3.0F, 10.0F, 9.0F, 8.0F, 0.0F);
    this.body.setTextureOffset(0, 44).addCuboid(-5.0F, 4.0F, -3.0F, 10.0F, 6.0F, 6.0F, 0.0F);
    this.body.setTextureOffset(19, 0).addCuboid(-6.0F, 10.0F, -4.0F, 12.0F, 0.0F, 8.0F, 0.0F);
    this.body.setTextureOffset(52, 24).addCuboid(-6.0F, 10.0F, 4.0F, 12.0F, 12.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(51, 0).addCuboid(-6.0F, 10.0F, -4.0F, 12.0F, 12.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(48, 48).addCuboid(-6.0F, 10.0F, -4.0F, 0.0F, 12.0F, 8.0F, 0.0F);
    this.body.setTextureOffset(0, 48).addCuboid(6.0F, 10.0F, -4.0F, 0.0F, 12.0F, 8.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftArm.setPivot(-8.0F, -3.0F, 0.0F);
    this.leftArm.setTextureOffset(60, 64).addCuboid(-1.0F, 3.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F);
    this.leftArm.setTextureOffset(10, 64).addCuboid(-2.0F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F);

    this.rightArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightArm.setPivot(7.0F, -3.0F, 0.0F);
    this.rightArm.setTextureOffset(44, 68).addCuboid(-2.0F, 3.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 0).addCuboid(0.0F, 13.0F, -15.0F, 1.0F, 2.0F, 25.0F, 0.0F);
    this.rightArm.setTextureOffset(59, 12).addCuboid(-2.0F, 11.0F, -21.0F, 5.0F, 6.0F, 6.0F, 0.0F);
    this.rightArm.setTextureOffset(62, 44).addCuboid(-2.0F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F);

    this.leftLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftLeg.setPivot(-2.9F, 8.0F, 0.0F);
    this.leftLeg.setTextureOffset(26, 50).addCuboid(-2.0F, 2.0F, -3.0F, 5.0F, 14.0F, 6.0F, 0.0F);

    this.rightLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightLeg.setPivot(2.9F, 8.0F, 0.0F);
    this.rightLeg.setTextureOffset(0, 0).addCuboid(-3.0F, 2.0F, -3.0F, 5.0F, 14.0F, 6.0F, 0.0F);

  }

  @Override
  public Iterable<ModelPart> getParts() {
    return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
  }

  @Override
  public void setAngles(T necromancer, float f, float g, float h, float i, float j) {

    this.head2.pitch = -0.2618F;
    this.rightArm.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.5F;
    this.rightArm.yaw = 0.0F;
    this.rightArm.roll = 0.0F;
    this.leftArm.pitch = MathHelper.cos(f * 0.6662F) * 2.0F * g * 0.5F;
    this.leftArm.yaw = 0.0F;
    this.leftArm.roll = 0.0F;
    this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g * 0.5F;
    this.rightLeg.yaw = 0.0F;
    this.rightLeg.roll = 0.0F;
    this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
    this.leftLeg.yaw = 0.0F;
    this.leftLeg.roll = 0.0F;

    if (necromancer.isSpellcasting()) {
      this.rightArm.pivotZ = 0.0F;
      this.rightArm.pivotX = 7.0F;
      this.rightArm.pitch = MathHelper.cos(h * 0.6662F) * 0.1F - 0.6F;
      this.rightArm.roll = -2.0F;
      this.rightArm.yaw = -0.5F;
    }
  }

}
