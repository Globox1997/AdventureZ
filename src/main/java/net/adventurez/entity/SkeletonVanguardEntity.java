package net.adventurez.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SkeletonVanguardEntity extends HostileEntity {

  public static final TrackedData<Float> SHIELD_SWING = DataTracker.registerData(SkeletonVanguardEntity.class,
      TrackedDataHandlerRegistry.FLOAT);

  public SkeletonVanguardEntity(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
    this.stepHeight = 1.0F;
  }

  public static DefaultAttributeContainer.Builder createSkeletonVanguardAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
  }

  @Override
  public void initGoals() {
    super.initGoals();
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, false));
    this.goalSelector.add(6, new WanderAroundGoal(this, 0.9D));
    this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
    this.goalSelector.add(8, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    this.goalSelector.add(7, new WanderAroundFarGoal(this, 0.9D));
    this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { SkeletonVanguardEntity.class })));
    this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { SummonerEntity.class })));
    this.targetSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, true));
  }

  @Override
  public void tick() {
    super.tick();
    if (dataTracker.get(SHIELD_SWING) > 0.0F) {
      dataTracker.set(SHIELD_SWING, dataTracker.get(SHIELD_SWING) - 0.01F);
    }
  }

  @Override
  public void initDataTracker() {
    super.initDataTracker();
    dataTracker.startTracking(SHIELD_SWING, 0.0F);
  }

  @Override
  public EntityGroup getGroup() {
    return EntityGroup.UNDEAD;
  }

  @Override
  public boolean canImmediatelyDespawn(double distanceSquared) {
    return false;
  }

  @Override
  public boolean canUsePortals() {
    return false;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_SKELETON_AMBIENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_SKELETON_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_SKELETON_DEATH;
  }

  @Override
  protected void playStepSound(BlockPos pos, BlockState state) {
    this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.5F, 1.0F);
  }

  @Override
  public boolean damage(DamageSource source, float amount) {
    int chance = 0;
    if (source.isProjectile()) {
      chance = world.random.nextInt(2);
    } else if (!source.isUnblockable() && this.getHealth() < this.getMaxHealth() / 2) {
      chance = world.random.nextInt(5);
    } else {
      chance = world.random.nextInt(10);
    }
    if (chance == 1) {
      dataTracker.set(SHIELD_SWING, 0.1F);
      this.world.playSoundFromEntity(null, this, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.HOSTILE, 1.0F, 1.0F);
      return false;
    } else
      return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
  }

}
