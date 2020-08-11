package net.adventurez.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

// import net.minecraft.entity.mob.VexEntity;
// import net.minecraft.entity.mob.ZombieEntity;

public class WitherPuppetEntity extends HostileEntity {
  private MobEntity owner;
  private boolean alive;
  private int lifeTicks;

  public WitherPuppetEntity(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
    this.stepHeight = 1.0F;
  }

  public static DefaultAttributeContainer.Builder createWitherPuppetAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
  }

  public void initGoals() {
    super.initGoals();
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(1, new MeleeAttackGoal(this, 0.85D, true));
    // this.goalSelector.add(4, new VexEntity.ChargeTargetGoal());
    // this.goalSelector.add(8, new VexEntity.LookAtTargetGoal());
    this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
    this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { NecromancerEntity.class })));
    this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
    this.targetSelector.add(3, (new RevengeGoal(this, new Class[] { PiglinEntity.class })));
    this.targetSelector.add(5, new WitherPuppetEntity.TrackOwnerTargetGoal(this));
    this.targetSelector.add(6, new FollowTargetGoal<>(this, PlayerEntity.class, true));
  }

  // protected void initDataTracker() {
  // super.initDataTracker();
  // }
  @Override
  public void readCustomDataFromTag(CompoundTag tag) {
    super.readCustomDataFromTag(tag);
    if (tag.contains("LifeTicks")) {
      this.setLifeTicks(tag.getInt("LifeTicks"));
    }

  }

  @Override
  public void writeCustomDataToTag(CompoundTag tag) {
    super.writeCustomDataToTag(tag);
    if (this.alive) {
      tag.putInt("LifeTicks", this.lifeTicks);
    }

  }

  @Override
  public void tick() {
    super.tick();
    if (this.alive && --this.lifeTicks <= 0) {
      this.lifeTicks = 20;
      this.damage(DamageSource.STARVE, 1.0F);
    }

  }

  public MobEntity getOwner() {
    return this.owner;
  }

  public void setOwner(MobEntity owner) {
    this.owner = owner;
  }

  public void setLifeTicks(int lifeTicks) {
    this.alive = true;
    this.lifeTicks = lifeTicks;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_WITHER_SKELETON_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_WITHER_SKELETON_DEATH;
  }

  @Override
  protected void playStepSound(BlockPos pos, BlockState state) {
    this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.5F, 1.0F);
  }

  @Override
  public boolean canHaveStatusEffect(StatusEffectInstance effect) {
    return effect.getEffectType() == StatusEffects.WITHER ? false : super.canHaveStatusEffect(effect);
  }

  @Override
  public boolean canUsePortals() {
    return false;
  }

  class TrackOwnerTargetGoal extends TrackTargetGoal {
    private final TargetPredicate TRACK_OWNER_PREDICATE = (new TargetPredicate()).includeHidden()
        .ignoreDistanceScalingFactor();

    public TrackOwnerTargetGoal(PathAwareEntity mob) {
      super(mob, false);
    }

    public boolean canStart() {
      return WitherPuppetEntity.this.owner != null && WitherPuppetEntity.this.owner.getTarget() != null
          && this.canTrack(WitherPuppetEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
    }

    public void start() {
      WitherPuppetEntity.this.setTarget(WitherPuppetEntity.this.owner.getTarget());
      super.start();
    }
  }

}