package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.StoneGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class StoneGolemModel<T extends StoneGolemEntity> extends CompositeEntityModel<T> {
  private final ModelPart head;
  private final ModelPart torso;
  private final ModelPart rightArm;
  private final ModelPart leftArm;
  private final ModelPart rightLeg;
  private final ModelPart leftLeg;

  public StoneGolemModel() {
    this.head = (new ModelPart(this)).setTextureSize(128, 128);
    this.head.setPivot(0.0F, 3.0F, -4.0F);
    this.head.setTextureOffset(0, 0).addCuboid(-4.0F, -8.3473F, -8.1378F, 8.0F, 10.0F, 8.0F, 0.0F);
    this.head.setTextureOffset(24, 0).addCuboid(-1.0F, -1.3473F, -10.1378F, 2.0F, 4.0F, 2.0F, 0.0F);

    this.torso = (new ModelPart(this)).setTextureSize(128, 128);
    this.torso.setPivot(0.0F, -2.0F, -1.0F);
    this.torso.setTextureOffset(0, 40).addCuboid(-9.0F, -1.2929F, -8.1213F, 18.0F, 12.0F, 11.0F, 0.0F);
    this.torso.setTextureOffset(0, 70).addCuboid(-4.5F, 10.7071F, -5.1213F, 9.0F, 5.0F, 6.0F, 0.5F);

    this.torso.setTextureOffset(42, 55).addCuboid(-1.0F, 1.0F, 3.0F, 7.0F, 6.0F, 1.0F, 0.0F);
    this.torso.setTextureOffset(12, 57).addCuboid(-1.0F, 6.0F, -9.0F, 8.0F, 3.0F, 1.0F, 0.0F);
    this.torso.setTextureOffset(28, 55).addCuboid(-8.0F, 3.0F, -9.0F, 6.0F, 2.0F, 1.0F, 0.0F);
    this.torso.setTextureOffset(9, 56).addCuboid(-6.0F, 5.0F, 3.0F, 3.0F, 4.0F, 1.0F, 0.0F);
    this.torso.setTextureOffset(33, 49).addCuboid(-10.0F, 5.0F, -5.0F, 1.0F, 4.0F, 6.0F, 0.0F);
    this.torso.setTextureOffset(16, 54).addCuboid(9.0F, 6.0F, -4.0F, 1.0F, 4.0F, 3.0F, 0.0F);

    this.rightArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightArm.setPivot(0.0F, -7.0F, 0.0F);
    this.rightArm.setTextureOffset(60, 21).addCuboid(9.0F, 5.0622F, -2.0981F, 4.0F, 30.0F, 6.0F, 0.0F);
    this.rightArm.setTextureOffset(62, 29).addCuboid(13.0F, 10.0F, -0.5F, 1.0F, 9.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(66, 47).addCuboid(13.0F, 27.0F, 1.7F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(61, 46).addCuboid(8.0F, 26.0F, -1.0F, 1.0F, 9.0F, 2.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftArm.setPivot(0.0F, -7.0F, 0.0F);
    this.leftArm.setTextureOffset(60, 58).addCuboid(-13.0F, 5.0622F, -2.0981F, 4.0F, 30.0F, 6.0F, 0.0F);
    this.leftArm.setTextureOffset(70, 65).addCuboid(-14.0F, 12.0F, 0.5F, 1.0F, 9.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(69, 85).addCuboid(-14.0F, 28.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(60, 80).addCuboid(-9.0F, 26.0F, -1.0F, 1.0F, 5.0F, 2.0F, 0.0F);

    this.rightLeg = (new ModelPart(this, 0, 22)).setTextureSize(128, 128);
    this.rightLeg.setPivot(-4.0F, 11.0F, 0.0F);
    this.rightLeg.setTextureOffset(37, 0).addCuboid(-2.5F, -0.0456F, 6.4791F, 6.0F, 16.0F, 5.0F, 0.0F);

    this.leftLeg = (new ModelPart(this, 0, 22)).setTextureSize(128, 128);
    this.leftLeg.mirror = true;
    this.leftLeg.setTextureOffset(60, 0).setPivot(5.0F, 11.0F, 0.0F);
    this.leftLeg.addCuboid(-4.5F, -0.0456F, 6.4791F, 6.0F, 16.0F, 5.0F, 0.0F);
  }

  @Override
  public Iterable<ModelPart> getParts() {
    return ImmutableList.of(this.head, this.torso, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
  }

  @Override
  public void setAngles(T stoneGolem, float f, float g, float h, float i, float j) {
    this.head.yaw = i * 0.0077453292F; // 0.017453292F
    this.head.pitch = j * 0.0017453292F + 0.2618F; // 0.017453292F
    this.rightLeg.yaw = 0.0F;
    this.leftLeg.yaw = 0.0F;
    this.rightLeg.pitch = -0.3F * MathHelper.method_24504(f, 13.0F) * g + 0.1745F; // + 0.1745F
    this.leftLeg.pitch = 0.3F * MathHelper.method_24504(f, 13.0F) * g + 0.1745F;
    this.torso.pitch = 0.7854F;
    this.rightArm.pitch = 0.5F * MathHelper.method_24504(f, 13.0F) * g - 0.5236F;
    this.leftArm.pitch = -0.5F * MathHelper.method_24504(f, 13.0F) * g - 0.5236F;
  } // 0.5F

  @Override
  public void animateModel(T stoneGolem, float f, float g, float h) {
    int i = stoneGolem.getAttackTick();
    if (i > 0) {
      // this.rightArm.pitch = (-2.0F + 1.5F * MathHelper.method_24504((float) i - h,
      // 10.0F) + 0.5236F) * f;
      // this.leftArm.pitch = (-2.0F + 1.5F * MathHelper.method_24504((float) i - h,
      // 10.0F) + 0.5236F) * g;
      this.rightArm.pitch = f * 2.6F;
      this.rightArm.yaw = g * 1.2F;
      // this.leftArm.yaw = h;

    } else {
      // int j = stoneGolem.getLookingAtVillagerTicks();
      // if (j > 0) {
      // this.rightArm.pitch = -0.8F + 0.025F * MathHelper.method_24504((float) j,
      // 70.0F);
      // this.leftArm.pitch = 0.0F;
      // } else { //-0.2F //13F

      this.rightArm.pitch = (-0.2F + 10.5F * MathHelper.method_24504(f, 13.0F)) * g * 3 + 5F;
      this.leftArm.pitch = (-0.2F - 10.5F * MathHelper.method_24504(f, 13.0F)) * g * 3 + 5F;

      // this.leftArm.pitch = 1.8F;

      // }
    }

  }

  public ModelPart getRightArm() {
    return this.rightArm;
  }
}
