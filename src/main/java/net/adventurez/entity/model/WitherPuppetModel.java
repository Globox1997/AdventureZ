package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.WitherPuppetEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class WitherPuppetModel<T extends WitherPuppetEntity> extends CompositeEntityModel<T> {
  private final ModelPart body;
  private final ModelPart head;
  private final ModelPart rightArm;
  private final ModelPart leftArm;
  private final ModelPart rightLeg;
  private final ModelPart leftLeg;

  public WitherPuppetModel() {
    this.body = (new ModelPart(this)).setTextureSize(64, 64);
    this.body.setPivot(0.0F, 14.0F, 0.0F);
    this.body.setTextureOffset(0, 16).addCuboid(-4.0F, -3.0F, -2.0F, 8.0F, 7.0F, 4.0F, 0.0F);

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
  public Iterable<ModelPart> getParts() {
    return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
  }

  @Override
  public void setAngles(T necromancer, float f, float g, float h, float i, float j) {

    // this.head.yaw = i * 0.0077453292F;
    // this.head.pitch = j * 0.0017453292F + 0.1745F;
    // this.rightLeg.yaw = 0.0F;
    // this.leftLeg.yaw = 0.0F;
    this.rightLeg.pitch = -0.3F * MathHelper.method_24504(f, 13.0F) * g * 1.3F + 0.4363F;
    this.leftLeg.pitch = 0.3F * MathHelper.method_24504(f, 13.0F) * g * 1.3F + 0.4363F;
    this.rightArm.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.3F;
    this.leftArm.pitch = -MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.3F;
    // this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F
    // * limbDistance;
    // this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F *
    // limbDistance;

    // this.torso.pitch = 1.309F;
    // this.this.rightArm.pitch = 0.5F * MathHelper.method_24504(f, 13.0F) * g *
    // 1.3F - 0.6109F;
    // this.this.leftArm.pitch = -0.5F * MathHelper.method_24504(f, 13.0F) * g *
    // 1.3F - 0.6109F;
  }

}
