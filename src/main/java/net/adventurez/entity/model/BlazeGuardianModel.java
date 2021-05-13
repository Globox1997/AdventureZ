package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.BlazeGuardianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

@Environment(EnvType.CLIENT)
public class BlazeGuardianModel<T extends BlazeGuardianEntity> extends CompositeEntityModel<T> {
  private final ModelPart body;
  private final ModelPart head;
  private final ModelPart shieldOne;
  private final ModelPart shieldTwo;
  private final ModelPart shieldThree;
  private final ModelPart shieldFour;

  public BlazeGuardianModel() {

    body = (new ModelPart(this)).setTextureSize(128, 128);
    body.setPivot(0.0F, 13.0F, 0.0F);
    body.setTextureOffset(0, 42).addCuboid(-2.0F, -12.0F, -2.0F, 4.0F, 22.0F, 4.0F, 0.0F, false);

    head = (new ModelPart(this)).setTextureSize(128, 128);
    head.setPivot(0.0F, 2.0F, 0.0F);
    head.setTextureOffset(38, 20).addCuboid(-3.0F, -9.0F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);
    head.setTextureOffset(38, 33).addCuboid(-4.0F, -9.0F, -4.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(0, 40).addCuboid(3.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(10, 28).addCuboid(3.0F, -9.0F, -3.0F, 1.0F, 7.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(24, 7).addCuboid(-4.0F, -9.0F, -3.0F, 1.0F, 7.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(22, 46).addCuboid(3.0F, -9.0F, 2.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
    head.setTextureOffset(0, 2).addCuboid(2.0F, -3.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(0, 0).addCuboid(-3.0F, -3.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(16, 46).addCuboid(-4.0F, -9.0F, 2.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
    head.setTextureOffset(14, 0).addCuboid(-3.0F, -9.0F, 3.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(0, 28).addCuboid(3.0F, -9.0F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
    head.setTextureOffset(0, 0).addCuboid(-4.0F, -9.0F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
    head.setTextureOffset(0, 28).addCuboid(3.0F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(6, 2).addCuboid(-4.0F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(24, 15).addCuboid(3.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(6, 0).addCuboid(-4.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(8, 36).addCuboid(-4.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(32, 46).addCuboid(-3.0F, -6.0F, -4.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(28, 46).addCuboid(2.0F, -6.0F, -4.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(14, 7).addCuboid(-2.0F, -10.0F, -4.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(0, 36).addCuboid(-4.0F, -10.0F, -4.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
    head.setTextureOffset(28, 24).addCuboid(3.0F, -10.0F, -4.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
    head.setTextureOffset(33, 21).addCuboid(-4.0F, -10.0F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
    head.setTextureOffset(28, 20).addCuboid(3.0F, -10.0F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
    head.setTextureOffset(0, 8).addCuboid(-2.0F, -10.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(38, 22).addCuboid(-1.0F, -11.0F, -4.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(38, 20).addCuboid(-1.0F, -11.0F, 3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

    shieldTwo = (new ModelPart(this)).setTextureSize(128, 128);
    shieldTwo.setPivot(0.0F, 12.0F, 0.0F);
    shieldTwo.setTextureOffset(28, 0).addCuboid(-5.0F, -6.0F, -12.0F, 10.0F, 18.0F, 2.0F, 0.0F, false);

    shieldThree = (new ModelPart(this)).setTextureSize(128, 128);
    shieldThree.setPivot(0.0F, 12.0F, 0.0F);
    shieldThree.setTextureOffset(38, 38).addCuboid(-5.0F, -6.0F, 10.0F, 10.0F, 18.0F, 2.0F, 0.0F, false);

    shieldFour = (new ModelPart(this)).setTextureSize(128, 128);
    shieldFour.setPivot(0.0F, 12.0F, 0.0F);
    shieldFour.setTextureOffset(28, 0).addCuboid(-5.0F, -6.0F, -12.0F, 10.0F, 18.0F, 2.0F, 0.0F, false);

    shieldOne = (new ModelPart(this)).setTextureSize(128, 128);
    shieldOne.setPivot(0.0F, 12.0F, 0.0F);
    shieldOne.setTextureOffset(28, 0).addCuboid(-5.0F, -6.0F, -12.0F, 10.0F, 18.0F, 2.0F, 0.0F, false);

  }

  @Override
  public Iterable<ModelPart> getParts() {
    return ImmutableList.of(this.head, this.body, this.shieldOne, this.shieldTwo, this.shieldThree, this.shieldFour);
  }

  @Override
  public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
      float headPitch) {
    float f = 0.47123894F + animationProgress * 3.1415927F * -0.05F;
    this.shieldOne.pitch = -0.3927F;
    this.shieldTwo.pitch = -0.3927F;
    this.shieldThree.pitch = 0.3927F;
    this.shieldFour.pitch = -0.3927F;
    this.shieldOne.yaw = f + 1.5708F;
    this.shieldTwo.yaw = f - 1.5708F;
    this.shieldThree.yaw = f;
    this.shieldFour.yaw = f;
    this.head.yaw = headYaw * 0.017453292F;
    this.head.pitch = headPitch * 0.017453292F;
  }

}
