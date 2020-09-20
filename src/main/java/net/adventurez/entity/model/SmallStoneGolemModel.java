package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.SmallStoneGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SmallStoneGolemModel<T extends SmallStoneGolemEntity> extends CompositeEntityModel<T> {
  private final ModelPart head;
  private final ModelPart torso;
  private final ModelPart rightArm;
  private final ModelPart leftArm;
  private final ModelPart rightLeg;
  private final ModelPart leftLeg;

  public SmallStoneGolemModel() {
    this.head = (new ModelPart(this)).setTextureSize(128, 128);
    this.head.setPivot(0.0F, 13.0F, -5.0F);
    this.head.setTextureOffset(0, 0).addCuboid(-4.0F, -8.2796F, -7.044F, 8.0F, 10.0F, 8.0F, 0.0F);
    this.head.setTextureOffset(24, 0).addCuboid(-1.0F, -1.2796F, -9.044F, 2.0F, 4.0F, 2.0F, 0.0F);

    this.torso = (new ModelPart(this)).setTextureSize(128, 128);
    this.torso.setPivot(0.0F, 15.0F, 0.0F);
    this.torso.setTextureOffset(0, 40).addCuboid(-9.0F, -5.7148F, -5.5702F, 18.0F, 11.0F, 11.0F, 0.0F);

    this.rightArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightArm.setPivot(0.0F, 15.0F, -4.0F);
    this.rightArm.setTextureOffset(60, 21).addCuboid(9.0F, -2.4685F, -2.9247F, 4.0F, 13.0F, 6.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftArm.setPivot(0.0F, 15.0F, -4.0F);
    this.leftArm.setTextureOffset(60, 58).addCuboid(-13.0F, -2.4685F, -2.9247F, 4.0F, 13.0F, 6.0F, 0.0F);

    this.rightLeg = (new ModelPart(this, 0, 22)).setTextureSize(128, 128);
    this.rightLeg.setPivot(0.0F, 16.0F, 5.0F);
    this.rightLeg.setTextureOffset(37, 0).addCuboid(1.5F, -1.7043F, -2.5805F, 6.0F, 11.0F, 5.0F, 0.0F);

    this.leftLeg = (new ModelPart(this, 0, 22)).setTextureSize(128, 128);
    this.leftLeg.mirror = true;
    this.leftLeg.setPivot(0.0F, 16.0F, 5.0F);
    this.leftLeg.setTextureOffset(60, 0).addCuboid(-7.5F, -1.7043F, -2.5805F, 6.0F, 11.0F, 5.0F, 0.0F);
  }

  @Override
  public Iterable<ModelPart> getParts() {
    return ImmutableList.of(this.head, this.torso, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
  }

  @Override
  public void setAngles(T stoneGolem, float f, float g, float h, float i, float j) {
    this.head.yaw = i * 0.0077453292F;
    this.head.pitch = j * 0.0017453292F + 0.1745F;
    this.rightLeg.yaw = 0.0F;
    this.leftLeg.yaw = 0.0F;
    this.rightLeg.pitch = -0.3F * MathHelper.method_24504(f, 13.0F) * g * 1.3F + 0.4363F;
    this.leftLeg.pitch = 0.3F * MathHelper.method_24504(f, 13.0F) * g * 1.3F + 0.4363F;
    this.torso.pitch = 1.309F;
    this.rightArm.pitch = 0.5F * MathHelper.method_24504(f, 13.0F) * g * 1.3F - 0.6109F;
    this.leftArm.pitch = -0.5F * MathHelper.method_24504(f, 13.0F) * g * 1.3F - 0.6109F;
    float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
    this.rightArm.pitch = -k * 1.5F;
  }

}
