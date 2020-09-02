package net.adventurez.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

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
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
  }

  @Override
  public void initGoals() {
    super.initGoals();
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, false));
    this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
    this.goalSelector.add(8, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { NecromancerEntity.class })));
    this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { PiglinEntity.class })));
    this.targetSelector.add(3, new WitherPuppetEntity.TrackOwnerTargetGoal(this));
    this.targetSelector.add(4, new FollowTargetGoal<>(this, PlayerEntity.class, true));
  }

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
    if (this.owner != null && this.owner.deathTime > 0) {
      this.damage(DamageSource.STARVE, 14.0F);
    }
    if (this.world.isClient) {
      for (int i = 0; i < 2; ++i) {
        this.world.addParticle(ParticleTypes.SMOKE, this.getParticleX(0.5D), this.getRandomBodyY(),
            this.getParticleZ(0.5D), 0.0D, 0.0D, 0.0D);
      }
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

  @Override
  public void dealDamage(LivingEntity attacker, Entity target) {
    LivingEntity bob = (LivingEntity) target;
    int attackEffectChance = world.getRandom().nextInt(5);
    if (attackEffectChance == 0 && bob instanceof LivingEntity) {
      StatusEffectInstance weakness = new StatusEffectInstance(StatusEffect.byRawId(18), 80, 0, false, false);
      if (!world.isClient) {
        bob.addStatusEffect(weakness);
      }
    }
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