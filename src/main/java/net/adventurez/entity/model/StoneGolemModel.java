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
    this.head.setPivot(0.0F, 2.5846F, -7.1554F);
    this.head.setTextureOffset(0, 0).addCuboid(-4.0F, -7.0F, -5.6225F, 8.0F, 10.0F, 8.0F, 0.0F);
    this.head.setTextureOffset(24, 0).addCuboid(-1.0F, -0.0F, -7.6225F, 2.0F, 4.0F, 2.0F, 0.0F);

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
    this.rightArm.setPivot(11.0F, 0.0F, -3.0F);
    this.rightArm.setTextureOffset(60, 21).addCuboid(-2.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
    this.rightArm.setTextureOffset(62, 29).addCuboid(2.0F, 2.4378F, -1.4019F, 1.0F, 9.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(66, 47).addCuboid(2.0F, 19.5981F, 0.768F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(61, 46).addCuboid(-3.0F, 18.4378F, -1.9019F, 1.0F, 9.0F, 2.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftArm.setPivot(-11.0F, 0.0F, -3.0F);
    this.leftArm.setTextureOffset(60, 58).addCuboid(-2.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
    this.leftArm.setTextureOffset(70, 65).addCuboid(-3.0F, 4.4378F, -0.4019F, 1.0F, 9.0F, 2.0F, 0.0F);
    this.leftArm.setTextureOffset(69, 85).addCuboid(-3.0F, 20.4378F, -0.9019F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.leftArm.setTextureOffset(60, 80).addCuboid(2.0F, 18.4378F, -1.9019F, 1.0F, 5.0F, 2.0F, 0.0F);

    this.rightLeg = (new ModelPart(this, 0, 22)).setTextureSize(128, 128);
    this.rightLeg.setPivot(0.0F, 10.0F, 9.0F);
    this.rightLeg.setTextureOffset(37, 0).addCuboid(1.5F, -0.6233F, -2.5578F, 6.0F, 16.0F, 5.0F, 0.0F);

    this.leftLeg = (new ModelPart(this, 0, 22)).setTextureSize(128, 128);
    this.leftLeg.mirror = true;
    this.leftLeg.setPivot(0.0F, 10.0F, 9.0F);
    this.leftLeg.setTextureOffset(60, 0).addCuboid(-7.5F, -0.6233F, -2.5578F, 6.0F, 16.0F, 5.0F, 0.0F);
  }

  @Override
  public Iterable<ModelPart> getParts() {
    return ImmutableList.of(this.head, this.torso, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
  }

  @Override
  public void setAngles(T stoneGolem, float f, float g, float h, float i, float j) {
    this.head.yaw = i * 0.0077453292F;
    this.head.pitch = j * 0.0017453292F + 0.2618F;
    this.rightLeg.yaw = 0.0F;
    this.leftLeg.yaw = 0.0F;
    this.rightLeg.pitch = -0.3F * MathHelper.method_24504(f, 13.0F) * g + 0.1745F;
    this.leftLeg.pitch = 0.3F * MathHelper.method_24504(f, 13.0F) * g + 0.1745F;
    this.torso.pitch = 0.7854F;
    this.rightArm.pitch = 0.5F * MathHelper.method_24504(f, 13.0F) * g - 0.5236F;
    this.leftArm.pitch = -0.5F * MathHelper.method_24504(f, 13.0F) * g - 0.5236F;
    int thrownRockTick = stoneGolem.getDataTracker().get(StoneGolemEntity.throwCooldown);
    if (thrownRockTick >= 100) {
      //this.rightArm.pitch = MathHelper.cos(-thrownRockTick * 0.2F + 0.3F) - 0.3F;
      this.leftArm.pitch = MathHelper.cos(-thrownRockTick * 0.2F + 0.3F) - 0.3F;

    }

  }

  @Override
  public void animateModel(T stoneGolem, float f, float g, float h) {
    int attackTick = stoneGolem.getAttackTick();

    // int thrownRockTick =
    // stoneGolem.getDataTracker().get(StoneGolemEntity.throwCooldown);
    // if (thrownRockTick >= 100) {
    // // this.rightArm.pitch = -1.3963F;
    // this.rightArm.pitch = -1.3963F * h;
    // this.leftArm.pitch = -1.3963F * g;
    // System.out.println(this.leftArm.pitch);
    // System.out.println(this.rightArm.pitch);
    // }
    if (attackTick > 0) {
      this.rightArm.pitch = g * 10.6F; // 2.6F
      this.rightArm.yaw = g * 1.2F;
    } else {
      this.rightArm.pitch = (-0.2F + 10.5F * MathHelper.method_24504(f, 13.0F)) * g * 3 + 5F;
      this.leftArm.pitch = (-0.2F - 10.5F * MathHelper.method_24504(f, 13.0F)) * g * 3 + 5F;

    }

  }

}
