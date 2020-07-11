package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.PiglinBeastEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class PiglinBeastModel<T extends PiglinBeastEntity> extends CompositeEntityModel<T> {
  private final ModelPart head;
  private final ModelPart body;
  private final ModelPart bone;
  private final ModelPart bone2;
  private final ModelPart bone3;
  private final ModelPart bone4;
  private final ModelPart bone5;
  private final ModelPart bone6;
  private final ModelPart bone7;
  private final ModelPart leftArm;
  private final ModelPart rightArm;
  private final ModelPart leftLeg;
  private final ModelPart rightLeg;

  public PiglinBeastModel() {
    this.head = (new ModelPart(this)).setTextureSize(128, 128);
    this.head.setPivot(0.0F, -16.0F, -1.0F);
    this.head.setTextureOffset(56, 67).addCuboid(-5.0F, -9.0F, -5.0F, 10.0F, 9.0F, 10.0F, 0.0F);
    this.head.setTextureOffset(0, 57).addCuboid(3.0F, -3.0F, -6.0F, 3.0F, 2.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(9, 8).addCuboid(-6.0F, -3.0F, -6.0F, 3.0F, 2.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(0, 31).addCuboid(-3.0F, -5.0F, -6.0F, 6.0F, 4.0F, 1.0F, 0.0F);

    this.bone = (new ModelPart(this)).setTextureSize(128, 128);
    this.bone.setPivot(-3.0F, -6.0F, -7.0F);
    this.head.addChild(this.bone);
    this.bone.setTextureOffset(0, 48).addCuboid(-3.0346F, 2.0855F, 0.0F, 6.0F, 2.0F, 1.0F, 0.0F);

    this.bone2 = (new ModelPart(this)).setTextureSize(128, 128);
    this.bone2.setPivot(3.0F, -7.0F, -6.0F);
    this.head.addChild(this.bone2);
    this.bone2.setTextureOffset(0, 41).addCuboid(-3.5297F, 2.5081F, -0.9397F, 6.0F, 2.0F, 1.0F, 0.0F);

    this.bone3 = (new ModelPart(this)).setTextureSize(128, 128);
    this.bone3.setPivot(3.0F, -7.0F, -6.0F);
    this.head.addChild(this.bone3);
    this.bone3.setTextureOffset(12, 35).addCuboid(-3.0F, -6.0F, 6.0F, 0.0F, 4.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(0, 7).addCuboid(-3.0F, -5.0F, 8.0F, 0.0F, 3.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(12, 35).addCuboid(-3.0F, -7.0F, 4.0F, 0.0F, 5.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(12, 36).addCuboid(-3.0F, -4.0F, 10.0F, 0.0F, 2.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(13, 52).addCuboid(-3.0F, 3.0F, 11.0F, 0.0F, 1.0F, 2.0F, 0.0F);
    this.bone3.setTextureOffset(12, 51).addCuboid(-3.0F, 1.0F, 11.0F, 0.0F, 1.0F, 3.0F, 0.0F);
    this.bone3.setTextureOffset(13, 52).addCuboid(-3.0F, -1.0F, 11.0F, 0.0F, 1.0F, 2.0F, 0.0F);
    this.bone3.setTextureOffset(12, 36).addCuboid(-3.0F, -2.0F, 11.0F, 0.0F, 1.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(12, 38).addCuboid(-3.0F, 0.0F, 11.0F, 0.0F, 1.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(13, 51).addCuboid(-3.0F, 2.0F, 11.0F, 0.0F, 1.0F, 2.0F, 0.0F);
    this.bone3.setTextureOffset(12, 37).addCuboid(-3.0F, 4.0F, 11.0F, 0.0F, 1.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(10, 50).addCuboid(-3.0F, -6.0F, 2.0F, 0.0F, 4.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(10, 50).addCuboid(-3.0F, -4.0F, 3.0F, 0.0F, 2.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(10, 50).addCuboid(-3.0F, -5.0F, 5.0F, 0.0F, 3.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(10, 51).addCuboid(-3.0F, -4.0F, 7.0F, 0.0F, 2.0F, 1.0F, 0.0F);
    this.bone3.setTextureOffset(10, 52).addCuboid(-3.0F, -3.0F, 9.0F, 0.0F, 1.0F, 1.0F, 0.0F);

    this.bone4 = (new ModelPart(this)).setTextureSize(128, 128);
    this.bone4.setPivot(-5.0F, -6.0F, 0.0F);
    this.head.addChild(this.bone4);
    this.bone4.setTextureOffset(65, 44).addCuboid(-5.0F, -1.0F, -2.0F, 6.0F, 1.0F, 5.0F, 0.0F);

    this.bone5 = (new ModelPart(this)).setTextureSize(128, 128);
    this.bone5.setPivot(5.0F, -6.0F, 0.0F);
    this.head.addChild(this.bone5);
    this.bone5.setTextureOffset(55, 38).addCuboid(-5.0F, -0.0603F, -2.0F, 6.0F, 1.0F, 5.0F, 0.0F);

    this.body = (new ModelPart(this)).setTextureSize(128, 128);
    this.body.setPivot(-1.0F, -3.0F, 1.0F);
    this.body.setTextureOffset(51, 51).addCuboid(-8.0F, 14.0F, -8.0F, 18.0F, 3.0F, 13.0F, 0.0F);
    this.body.setTextureOffset(0, 31).addCuboid(-8.0F, 12.0F, -9.0F, 18.0F, 2.0F, 15.0F, 0.0F);
    this.body.setTextureOffset(0, 0).addCuboid(-9.0F, -1.0F, -11.0F, 20.0F, 13.0F, 18.0F, 0.0F);
    this.body.setTextureOffset(66, 106).addCuboid(-9.0F, 12.0F, -11.0F, 20.0F, 10.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(85, 39).addCuboid(-9.0F, 12.0F, 7.0F, 20.0F, 10.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(70, 100).addCuboid(11.0F, 12.0F, -11.0F, 0.0F, 10.0F, 18.0F, 0.0F);
    this.body.setTextureOffset(67, 100).addCuboid(-9.0F, 12.0F, -11.0F, 0.0F, 10.0F, 18.0F, 0.0F);
    this.body.setTextureOffset(0, 48).addCuboid(-8.0F, -3.0F, -8.0F, 18.0F, 2.0F, 14.0F, 0.0F);
    this.body.setTextureOffset(0, 64).addCuboid(-8.0F, -13.0F, -5.0F, 18.0F, 10.0F, 10.0F, 0.0F);

    this.bone6 = (new ModelPart(this)).setTextureSize(128, 128);
    this.bone6.setPivot(4.0F, 8.0F, 6.0F);
    this.body.addChild(this.bone6);
    this.bone6.setTextureOffset(96, 67).addCuboid(-1.0F, -31.0F, 1.0F, 1.0F, 27.0F, 1.0F, 0.0F);
    this.bone6.setTextureOffset(88, 0).addCuboid(-2.0F, -34.0138F, 0.1046F, 3.0F, 3.0F, 15.0F, 0.0F);
    this.bone6.setTextureOffset(0, 8).addCuboid(-2.0F, -4.0F, 0.0F, 3.0F, 6.0F, 3.0F, 0.0F);

    this.bone7 = (new ModelPart(this)).setTextureSize(128, 128);
    this.bone7.setPivot(0.0F, -31.0F, 8.0F);
    this.bone6.addChild(this.bone7);
    this.bone7.setTextureOffset(44, 96).addCuboid(-0.5F, -1.2693F, -5.1484F, 0.0F, 21.0F, 11.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftArm.setPivot(-9.0F, -13.0F, 0.0F);
    this.leftArm.setTextureOffset(22, 84).addCuboid(-3.3939F, -1.3963F, -3.0F, 5.0F, 24.0F, 6.0F, 0.0F);

    this.rightArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightArm.setPivot(8.0F, -13.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 84).addCuboid(-0.899F, -1.1716F, -3.0F, 5.0F, 25.0F, 6.0F, 0.0F);
    this.rightArm.setTextureOffset(58, 0).addCuboid(0.8422F, 18.7943F, -4.0F, 2.0F, 3.0F, 11.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 51).addCuboid(-0.1578F, 18.7943F, -6.0F, 3.0F, 4.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 0).addCuboid(-0.1578F, 17.7943F, -9.0F, 4.0F, 5.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(59, 14).addCuboid(-1.1578F, 16.7943F, -26.0F, 6.0F, 7.0F, 17.0F, 0.0F);
    this.rightArm.setTextureOffset(50, 50).addCuboid(-0.1578F, 17.7943F, -27.0F, 4.0F, 5.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(58, 0).addCuboid(1.3951F, 15.9987F, -12.0F, 3.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(9, 14).addCuboid(0.3951F, 15.9987F, -19.0F, 1.0F, 1.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 36).addCuboid(1.361F, 14.2576F, -25.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(14, 2).addCuboid(3.5517F, 13.8093F, -22.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(12, 12).addCuboid(4.9319F, 17.5176F, -22.0F, 2.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 44).addCuboid(4.3801F, 19.7083F, -23.0F, 4.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(55, 44).addCuboid(3.8625F, 21.6401F, -22.0F, 3.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(50, 56).addCuboid(3.8625F, 21.6401F, -15.0F, 2.0F, 1.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(8, 36).addCuboid(3.3449F, 23.572F, -24.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(14, 6).addCuboid(0.4471F, 23.7955F, -22.0F, 1.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(14, 14).addCuboid(2.3789F, 23.3131F, -14.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(50, 48).addCuboid(-2.9331F, 21.0872F, -25.0F, 3.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(11, 0).addCuboid(-2.3813F, 18.8965F, -21.0F, 2.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 36).addCuboid(-2.3813F, 19.8965F, -16.0F, 2.0F, 1.0F, 4.0F, 0.0F);

    this.leftLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftLeg.setPivot(-3.9F, 12.0F, 0.0F);
    this.leftLeg.setTextureOffset(74, 86).addCuboid(-3.0F, 1.0F, -5.0F, 6.0F, 11.0F, 9.0F, 0.0F);

    this.rightLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightLeg.setPivot(3.9F, 12.0F, 0.0F);
    this.rightLeg.setTextureOffset(44, 86).addCuboid(-3.0F, 1.0F, -5.0F, 6.0F, 11.0F, 9.0F, 0.0F);
  }

  @Override
  public Iterable<ModelPart> getParts() {
    return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm);
  }

  @Override
  public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
      float headPitch) {
    this.head.yaw = headYaw * 0.0079453292F;
    this.head.pitch = headPitch * 0.0027453292F;
    this.bone.yaw = -0.3491F;
    this.bone.roll = 1.1345F;
    this.bone2.yaw = 0.3491F;
    this.bone2.roll = -1.1345F;
    this.bone4.roll = -1.1345F;
    this.bone5.roll = -1.9199F;
    this.leftArm.roll = 0.2618F;
    this.rightArm.roll = -0.2618F;
    this.bone6.pitch = -0.2618F;
    this.bone7.pitch = 0.1745F;
    // System.out.println(this.handSwingProgress);
    // Animation
    // Legs
    this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
    this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    // Flag
    bone7.roll = MathHelper.cos(animationProgress / 10) / 4; // Slow?
    // Arms
    this.rightArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
    // this.leftArm.pitch = MathHelper.cos(limbAngle * 0.6662F) * 2.0F *
    // limbDistance * 0.5F;
    this.leftArm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;

    float attackTick = entity.getDataTracker().get(PiglinBeastEntity.AttackTickVisual);
    if (attackTick > 0F) {
      float g = attackTick;
      // g = 1.0F - attackTick;
      // g *= g;
      // g *= g;
      // g = 1.0F - g;
      float h = MathHelper.sin(g * 3.1415927F);
      float i = MathHelper.sin(attackTick * 3.1415927F) * -(this.head.pitch - 0.7F) * 0.75F;
      rightArm.pitch = (float) ((double) rightArm.pitch - ((double) h * 1.2D + (double) i));
      rightArm.roll += MathHelper.sin(attackTick * 3.1415927F) * -0.4F - limbDistance;
    }

    float handUp = entity.getDataTracker().get(PiglinBeastEntity.LeadArm);
    if (handUp > 0.0F) {
      float g = handUp;
      // g = 1.0F - handUp;
      // g *= g;
      // g *= g;
      // g = 1.0F - g;
      float h = MathHelper.sin(g * 3.1415927F);
      float i = MathHelper.sin(handUp * 3.1415927F) * -(this.head.pitch - 0.7F) * 0.75F;
      leftArm.pitch = (float) ((double) leftArm.pitch - ((double) h * 1.2D + (double) i));
    }

  }

}