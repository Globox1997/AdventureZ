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
  private final ModelPart leftHorn;
  private final ModelPart rightHorn;
  private final ModelPart hair;
  private final ModelPart leftEar;
  private final ModelPart rightEar;
  private final ModelPart flagHolder;
  private final ModelPart flag;
  private final ModelPart leftArm;
  private final ModelPart rightArm;
  private final ModelPart leftLeg;
  private final ModelPart rightLeg;

  public PiglinBeastModel() {
    this.head = (new ModelPart(this)).setTextureSize(128, 128);
    this.head.setPivot(0.0F, -16.0F, -1.0F);
    this.head.setTextureOffset(74, 0).addCuboid(-5.0F, -11.0F, -5.0F, 10.0F, 9.0F, 10.0F, 0.0F);
    this.head.setTextureOffset(57, 11).addCuboid(3.0F, -5.0F, -6.0F, 3.0F, 2.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(48, 56).addCuboid(-6.0F, -5.0F, -6.0F, 3.0F, 2.0F, 1.0F, 0.0F);
    this.head.setTextureOffset(0, 38).addCuboid(-3.0F, -7.0F, -6.0F, 6.0F, 4.0F, 1.0F, 0.0F);

    this.leftHorn = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftHorn.setPivot(-3.0F, -6.0F, -7.0F);
    this.head.addChild(this.leftHorn);
    this.leftHorn.setTextureOffset(51, 30).addCuboid(-4.7379F, 1.2403F, 0.62F, 6.0F, 2.0F, 1.0F, 0.0F);

    this.rightHorn = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightHorn.setPivot(3.0F, -7.0F, -6.0F);
    this.head.addChild(this.rightHorn);
    this.rightHorn.setTextureOffset(0, 14).addCuboid(-1.8264F, 1.6629F, -0.3197F, 6.0F, 2.0F, 1.0F, 0.0F);

    this.hair = (new ModelPart(this)).setTextureSize(128, 128);
    this.hair.setPivot(3.0F, -7.0F, -6.0F);
    this.head.addChild(this.hair);
    this.hair.setTextureOffset(61, 32).addCuboid(-3.0F, -8.0F, 6.0F, 0.0F, 4.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(0, 51).addCuboid(-3.0F, -7.0F, 8.0F, 0.0F, 3.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(10, 51).addCuboid(-3.0F, -9.0F, 4.0F, 0.0F, 5.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(50, 46).addCuboid(-3.0F, -6.0F, 10.0F, 0.0F, 2.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(48, 48).addCuboid(-3.0F, 1.0F, 11.0F, 0.0F, 1.0F, 3.0F, 0.0F);
    this.hair.setTextureOffset(8, 45).addCuboid(-3.0F, -1.0F, 11.0F, 0.0F, 1.0F, 2.0F, 0.0F);
    this.hair.setTextureOffset(13, 12).addCuboid(-3.0F, -3.0F, 11.0F, 0.0F, 1.0F, 2.0F, 0.0F);
    this.hair.setTextureOffset(0, 49).addCuboid(-3.0F, -4.0F, 11.0F, 0.0F, 1.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(8, 41).addCuboid(-3.0F, -2.0F, 11.0F, 0.0F, 1.0F, 3.0F, 0.0F);
    this.hair.setTextureOffset(0, 40).addCuboid(-3.0F, 0.0F, 11.0F, 0.0F, 1.0F, 4.0F, 0.0F);
    this.hair.setTextureOffset(0, 39).addCuboid(-3.0F, 2.0F, 11.0F, 0.0F, 1.0F, 4.0F, 0.0F);
    this.hair.setTextureOffset(8, 40).addCuboid(-3.0F, 3.0F, 11.0F, 0.0F, 1.0F, 3.0F, 0.0F);
    this.hair.setTextureOffset(13, 37).addCuboid(-3.0F, 4.0F, 11.0F, 0.0F, 1.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(61, 1).addCuboid(-3.0F, -8.0F, 2.0F, 0.0F, 4.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(48, 46).addCuboid(-3.0F, -6.0F, 3.0F, 0.0F, 2.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(0, 29).addCuboid(-3.0F, -7.0F, 5.0F, 0.0F, 3.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(14, 14).addCuboid(-3.0F, -6.0F, 7.0F, 0.0F, 2.0F, 1.0F, 0.0F);
    this.hair.setTextureOffset(2, 49).addCuboid(-3.0F, -5.0F, 9.0F, 0.0F, 1.0F, 1.0F, 0.0F);

    this.leftEar = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftEar.setPivot(-5.0F, -8.0F, 0.0F);
    this.head.addChild(this.leftEar);
    this.leftEar.setTextureOffset(57, 0).addCuboid(-5.0F, -1.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F);

    this.rightEar = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightEar.setPivot(5.0F, -8.0F, 0.0F);
    this.head.addChild(this.rightEar);
    this.rightEar.setTextureOffset(48, 49).addCuboid(-5.0F, -0.0603F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F);

    this.body = (new ModelPart(this)).setTextureSize(128, 128);
    this.body.setPivot(-1.0F, -3.0F, 1.0F);
    this.body.setTextureOffset(47, 56).addCuboid(-8.0F, 12.0F, -8.0F, 18.0F, 3.0F, 13.0F, 0.0F);
    this.body.setTextureOffset(0, 30).addCuboid(-8.0F, 10.0F, -9.0F, 18.0F, 2.0F, 15.0F, 0.0F);
    this.body.setTextureOffset(0, 0).addCuboid(-9.0F, -3.0F, -10.0F, 20.0F, 13.0F, 17.0F, 0.0F);
    this.body.setTextureOffset(74, 19).addCuboid(-9.0F, 10.0F, -10.0F, 20.0F, 10.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(72, 72).addCuboid(-9.0F, 10.0F, 7.0F, 20.0F, 10.0F, 0.0F, 0.0F);
    this.body.setTextureOffset(29, 55).addCuboid(11.0F, 10.0F, -10.0F, 0.0F, 10.0F, 17.0F, 0.0F);
    this.body.setTextureOffset(29, 55).addCuboid(-9.0F, 10.0F, -10.0F, 0.0F, 10.0F, 17.0F, 0.0F);
    this.body.setTextureOffset(52, 33).addCuboid(-8.0F, -5.0F, -7.0F, 18.0F, 2.0F, 14.0F, 0.0F);
    this.body.setTextureOffset(0, 47).addCuboid(-8.0F, -15.0F, -5.0F, 18.0F, 10.0F, 12.0F, 0.0F);

    this.flagHolder = (new ModelPart(this)).setTextureSize(128, 128);
    this.flagHolder.setPivot(4.0F, 8.0F, 6.0F);
    this.body.addChild(this.flagHolder);
    this.flagHolder.setTextureOffset(76, 95).addCuboid(-2.0F, -37.9319F, 0.4824F, 2.0F, 29.0F, 2.0F, 0.0F);
    this.flagHolder.setTextureOffset(46, 72).addCuboid(-3.0F, -42.0F, -1.0F, 4.0F, 5.0F, 18.0F, 0.0F);
    this.flagHolder.setTextureOffset(0, 0).addCuboid(-3.0F, -8.9319F, -0.5176F, 4.0F, 10.0F, 4.0F, 0.0F);

    this.flag = (new ModelPart(this)).setTextureSize(128, 128);
    this.flag.setPivot(-1.0F, -38.0F, 9.0F);
    this.flagHolder.addChild(this.flag);
    this.flag.setTextureOffset(0, 79).addCuboid(0.0F, -0.6136F, -6.8755F, 0.0F, 24.0F, 14.0F, 0.0F);

    this.leftArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftArm.setPivot(-9.0F, -15.0F, 2.0F);
    this.leftArm.setTextureOffset(28, 93).addCuboid(-3.3939F, -1.3963F, -3.0F, 5.0F, 24.0F, 6.0F, 0.0F);

    this.rightArm = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightArm.setPivot(8.0F, -15.0F, 2.0F);
    this.rightArm.setTextureOffset(84, 89).addCuboid(-0.899F, -1.1716F, -3.0F, 5.0F, 25.0F, 6.0F, 0.0F);
    this.rightArm.setTextureOffset(101, 71).addCuboid(0.8422F, 18.7943F, -6.0F, 2.0F, 3.0F, 11.0F, 0.0F);
    this.rightArm.setTextureOffset(51, 39).addCuboid(-0.1578F, 18.7943F, -8.0F, 3.0F, 4.0F, 2.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 30).addCuboid(-0.1578F, 17.7943F, -11.0F, 4.0F, 5.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 69).addCuboid(-1.1578F, 16.7943F, -28.0F, 6.0F, 7.0F, 17.0F, 0.0F);
    this.rightArm.setTextureOffset(51, 33).addCuboid(-0.1578F, 17.7943F, -29.0F, 4.0F, 5.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(64, 13).addCuboid(1.3951F, 15.9987F, -14.0F, 3.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(57, 7).addCuboid(0.3951F, 15.9987F, -21.0F, 1.0F, 1.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(12, 0).addCuboid(1.361F, 14.2576F, -27.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 0).addCuboid(3.5517F, 13.8093F, -24.0F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(11, 30).addCuboid(2.5858F, 14.5505F, -19.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(57, 0).addCuboid(4.9319F, 17.5176F, -24.0F, 2.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(48, 52).addCuboid(4.6731F, 18.4836F, -16.0F, 2.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 56).addCuboid(4.3801F, 19.7083F, -25.0F, 4.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(64, 10).addCuboid(4.0F, 21.6401F, -24.0F, 3.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 52).addCuboid(3.5F, 21.6401F, -17.0F, 2.0F, 1.0F, 3.0F, 0.0F);
    this.rightArm.setTextureOffset(57, 2).addCuboid(3.3449F, 23.572F, -26.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(56, 56).addCuboid(0.4471F, 23.7955F, -24.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 47).addCuboid(1.413F, 23.7F, -19.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(8, 48).addCuboid(2.3789F, 23.3131F, -16.0F, 1.0F, 2.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(62, 7).addCuboid(-2.9331F, 21.0872F, -27.0F, 3.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(57, 14).addCuboid(-3.3813F, 18.8965F, -23.0F, 3.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(48, 49).addCuboid(-2.1225F, 17.9306F, -16.0F, 2.0F, 1.0F, 1.0F, 0.0F);
    this.rightArm.setTextureOffset(0, 47).addCuboid(-2.3813F, 19.8965F, -18.0F, 2.0F, 1.0F, 4.0F, 0.0F);

    this.leftLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.leftLeg.setPivot(-3.9F, 11.0F, 0.0F);
    this.leftLeg.setTextureOffset(96, 49).addCuboid(-3.0F, 0.0F, -4.0F, 6.0F, 13.0F, 7.0F, 0.0F);

    this.rightLeg = (new ModelPart(this)).setTextureSize(128, 128);
    this.rightLeg.setPivot(3.9F, 11.0F, 0.0F);
    this.rightLeg.setTextureOffset(50, 95).addCuboid(-3.0F, 0.0F, -4.0F, 6.0F, 13.0F, 7.0F, 0.0F);
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
    this.leftHorn.yaw = -0.3491F;
    this.leftHorn.roll = 1.1345F;
    this.rightHorn.yaw = 0.3491F;
    this.rightHorn.roll = -1.1345F;
    this.leftEar.roll = -1.1345F;
    this.rightEar.roll = -1.9199F;
    this.leftArm.roll = 0.2618F;
    this.rightArm.roll = -0.2618F;
    this.flagHolder.pitch = -0.2618F;
    this.flag.pitch = 0.1745F;
    // Animation
    // Legs
    this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
    this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    // Flag
    flag.roll = MathHelper.cos(animationProgress / 10) / 4;
    // Arms
    this.rightArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;
    this.leftArm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.3F;

    float attackTick = entity.getDataTracker().get(PiglinBeastEntity.AttackTickVisual);
    if (attackTick > 0F) {
      float g = attackTick;
      float h = MathHelper.sin(g * 3.1415927F);
      float i = MathHelper.sin(attackTick * 3.1415927F) * -(this.head.pitch - 0.7F) * 0.75F;
      rightArm.pitch = (float) ((double) rightArm.pitch - ((double) h * 1.2D + (double) i));
      rightArm.roll += MathHelper.sin(attackTick * 3.1415927F) * -0.4F - limbDistance;
    }

    float handUp = entity.getDataTracker().get(PiglinBeastEntity.LeadArm);
    if (handUp > 0.0F) {
      float g = handUp;
      float h = MathHelper.sin(g * 3.1415927F);
      float i = MathHelper.sin(handUp * 3.1415927F) * -(this.head.pitch - 0.7F) * 0.75F;
      leftArm.pitch = (float) ((double) leftArm.pitch - ((double) h * 1.2D + (double) i));
    }
    // Ears
    rightEar.roll = rightEar.roll + ((MathHelper.cos((limbAngle * 0.6662F + 3.1415927F) / 3) * limbDistance) / 2);
    leftEar.roll = leftEar.roll - ((MathHelper.cos((limbAngle * 0.6662F + 3.1415927F) / 3) * limbDistance) / 2);
  }

}