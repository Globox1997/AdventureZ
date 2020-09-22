package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.SummonerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SummonerModel<T extends SummonerEntity> extends CompositeEntityModel<T> {
  private final ModelPart head;
  private final ModelPart cape;
  private final ModelPart body;
  private final ModelPart leftArm;
  private final ModelPart rightArm;
  private final ModelPart leftLeg;
  private final ModelPart rightLeg;

  public SummonerModel() {
    this.body = (new ModelPart(this)).setTextureSize(128, 128);
    this.body.setPivot(0.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(32, 32).addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F);
    this.body.setTextureOffset(44, 48).addCuboid(-9.0F, -1.0F, -3.0F, 5.0F, 4.0F, 7.0F, 0.0F);
    this.body.setTextureOffset(20, 48).addCuboid(4.0F, -1.0F, -3.0F, 5.0F, 4.0F, 7.0F, 0.0F);
    this.body.setTextureOffset(56, 0).addCuboid(-4.0F, 12.0F, -2.0F, 8.0F, 9.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(48, 16).addCuboid(-4.0F, 12.0F, 2.0F, 8.0F, 9.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(52, 55).addCuboid(4.0F, 12.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F);
    this.body.setTextureOffset(44, 55).addCuboid(-4.0F, 12.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F);

    this.cape = (new ModelPart(this)).setTextureSize(128, 128);
    this.cape.setPivot(0.0F, 1.0F, -2.0F);
    this.body.addChild(cape);
    this.cape.setTextureOffset(10, 40).addCuboid(-8.0F, -2.0F, 0.0F, 0.0F, 22.0F, 5.0F, 0.0F);
    this.cape.setTextureOffset(0, 40).addCuboid(8.0F, -2.0F, 0.0F, 0.0F, 22.0F, 5.0F, 0.0F);
    this.cape.setTextureOffset(3, 67).addCuboid(7.0F, -2.0F, 0.0F, 1.0F, 22.0F, 0.0F, 0.0F);
    this.cape.setTextureOffset(0, 67).addCuboid(-8.0F, -2.0F, 0.0F, 1.0F, 22.0F, 0.0F, 0.0F);
    this.cape.setTextureOffset(0, 23).addCuboid(-8.0F, -2.0F, 5.0F, 16.0F, 22.0F, 0.0F, 0.0F);

    this.head = (new ModelPart(this)).setTextureSize(128, 128);
    this.head.setPivot(0.0F, 0.0F, 0.0F);
    this.head.setTextureOffset(24, 0).addCuboid(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F, 0.0F);
    this.head.setTextureOffset(36, 16).addCuboid(-5.0F, -9.0F, -6.0F, 1.0F, 4.0F, 10.0F, 0.0F);
    this.head.setTextureOffset(24, 16).addCuboid(-4.0F, -9.0F, 3.0F, 8.0F, 4.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(0, 14).addCuboid(-4.0F, -9.0F, -6.0F, 8.0F, 4.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(0, 0).addCuboid(4.0F, -9.0F, -6.0F, 1.0F, 4.0F, 10.0F, 0.0F);

    this.rightArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightArm.setPivot(5.0F, 2.0F, 0.0F);

    this.rightArm.setTextureOffset(36, 59).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(37, 48).addCuboid(-1.5F, 7.0F, -8.0F, 3.0F, 3.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 8).addCuboid(-1.5F, 8.0F, -5.0F, 3.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(17, 18).addCuboid(-0.5F, 7.0F, -5.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(12, 4).addCuboid(-0.5F, 6.0F, -7.0F, 1.0F, 1.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(12, 0).addCuboid(-0.5F, 10.0F, -7.0F, 1.0F, 1.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 4).addCuboid(-2.5F, 8.0F, -7.0F, 1.0F, 1.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 0).addCuboid(1.5F, 8.0F, -7.0F, 1.0F, 1.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 0).addCuboid(-0.5F, 8.0F, -4.0F, 1.0F, 1.0F, 22.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftArm.setPivot(-5.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(28, 59).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);

    this.rightLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightLeg.setPivot(2.0F, 12.0F, 0.0F);
    this.rightLeg.setTextureOffset(20, 59).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);

    this.leftLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftLeg.setPivot(-2.0F, 12.0F, 0.0F);
    this.leftLeg.setTextureOffset(56, 28).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F);

  }

  @Override
  public Iterable<ModelPart> getParts() {
    return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
  }

  @Override
  public void setAngles(T summoner, float f, float g, float h, float i, float j) {
    this.rightArm.pitch = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.5F - 1.0472F);
    this.rightArm.yaw = 0.0F;
    this.rightArm.roll = 0.0F;
    this.leftArm.pitch = MathHelper.cos(f * 0.6662F) * 2.0F * g * 0.5F;
    if (this.leftArm.pitch > 0.2F) {
      this.leftArm.pitch = 0.2F;
    }
    this.leftArm.yaw = 0.0F;
    this.leftArm.roll = 0.0F;
    this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g * 0.5F;
    this.rightLeg.yaw = 0.0F;
    this.rightLeg.roll = 0.0F;
    this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
    this.leftLeg.yaw = 0.0F;
    this.leftLeg.roll = 0.0F;

    if (summoner.isSpellcasting()) {
      this.rightArm.pivotZ = 0.0F;
      this.rightArm.pivotX = 7.0F;
      this.rightArm.pitch = MathHelper.cos(h * 0.6662F) * 0.1F - 0.4F;
      this.rightArm.roll = -1.7F;
      this.rightArm.yaw = -0.45F;
    }
  }

}
