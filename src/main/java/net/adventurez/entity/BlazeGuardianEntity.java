package net.adventurez.entity;

import java.util.EnumSet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.GoToWalkTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;

public class BlazeGuardianEntity extends HostileEntity {
  private float eyeOffset = 0.5F;
  private int eyeOffsetCooldown;
  private static final TrackedData<Byte> GUARDIAN_FLAGS;

  public BlazeGuardianEntity(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
    this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
    this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
    this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
    this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
    this.experiencePoints = 20;
  }

  public static DefaultAttributeContainer.Builder createBlazeGuardianAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 52.0D)
        .add(EntityAttributes.GENERIC_ARMOR, 2.0D);
  }

  @Override
  public void initGoals() {
    this.goalSelector.add(3, new BlazeGuardianEntity.ShockWaveGoal(this));
    this.goalSelector.add(4, new BlazeGuardianEntity.ShootFireballGoal(this));
    this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0D));
    this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D, 0.0F));
    this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
    this.goalSelector.add(8, new LookAroundGoal(this));
    this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
    this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { BlazeEntity.class })));
    this.targetSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, true));
  }

  @Override
  public void initDataTracker() {
    super.initDataTracker();
    this.dataTracker.startTracking(GUARDIAN_FLAGS, (byte) 0);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_BLAZE_AMBIENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_BLAZE_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_BLAZE_DEATH;
  }

  @Override
  public float getBrightnessAtEyes() {
    return 1.0F;
  }

  @Override
  public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
    return false;
  }

  @Override
  public void tickMovement() {
    if (!this.onGround && this.getVelocity().y < 0.0D) {
      this.setVelocity(this.getVelocity().multiply(1.0D, 0.6D, 1.0D));
    }

    if (this.world.isClient) {
      if (this.random.nextInt(24) == 0 && !this.isSilent()) {
        this.world.playSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.ENTITY_BLAZE_BURN,
            this.getSoundCategory(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
      }

      for (int i = 0; i < 2; ++i) {
        this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getParticleX(0.6D), this.getRandomBodyY(),
            this.getParticleZ(0.6D), 0.0D, 0.0D, 0.0D);
      }
    }

    super.tickMovement();
  }

  @Override
  public boolean hurtByWater() {
    return true;
  }

  @Override
  public void mobTick() {
    --this.eyeOffsetCooldown;
    if (this.eyeOffsetCooldown <= 0) {
      this.eyeOffsetCooldown = 100;
      this.eyeOffset = 0.5F + (float) this.random.nextGaussian() * 3.0F;
    }

    LivingEntity livingEntity = this.getTarget();
    if (livingEntity != null && livingEntity.getEyeY() > this.getEyeY() + (double) this.eyeOffset
        && this.canTarget(livingEntity)) {
      Vec3d vec3d = this.getVelocity();
      this.setVelocity(this.getVelocity().add(0.0D, (0.30000001192092896D - vec3d.y) * 0.30000001192092896D, 0.0D));
      this.velocityDirty = true;
    }

    super.mobTick();
  }

  @Override
  public boolean isOnFire() {
    return this.isFireActive();
  }

  private boolean isFireActive() {
    return ((Byte) this.dataTracker.get(GUARDIAN_FLAGS) & 1) != 0;
  }

  private void setFireActive(boolean fireActive) {
    byte b = (Byte) this.dataTracker.get(GUARDIAN_FLAGS);
    if (fireActive) {
      b = (byte) (b | 1);
    } else {
      b &= -2;
    }

    this.dataTracker.set(GUARDIAN_FLAGS, b);
  }

  @Override
  public boolean damage(DamageSource source, float amount) {
    int chance = 0;
    if (source.isProjectile()) {
      chance = world.random.nextInt(3);
    } else if (!source.isUnblockable() && this.getHealth() < this.getMaxHealth() / 2) {
      chance = world.random.nextInt(4);
    } else {
      chance = world.random.nextInt(6);
    }
    if (chance == 1) {
      this.world.playSoundFromEntity(null, this, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.HOSTILE, 1.0F, 1.0F);
      return false;
    } else
      return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
  }

  static {
    GUARDIAN_FLAGS = DataTracker.registerData(BlazeGuardianEntity.class, TrackedDataHandlerRegistry.BYTE);
  }

  static class ShootFireballGoal extends Goal {
    private final BlazeGuardianEntity guardian;
    private int fireballsFired;
    private int fireballCooldown;
    private int targetNotVisibleTicks;

    public ShootFireballGoal(BlazeGuardianEntity guardian) {
      this.guardian = guardian;
      this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public boolean canStart() {
      LivingEntity livingEntity = this.guardian.getTarget();
      return livingEntity != null && livingEntity.isAlive() && this.guardian.canTarget(livingEntity);
    }

    public void start() {
      this.fireballsFired = 0;
    }

    public void stop() {
      this.guardian.setFireActive(false);
      this.targetNotVisibleTicks = 0;
    }

    public void tick() {
      --this.fireballCooldown;
      LivingEntity livingEntity = this.guardian.getTarget();
      if (livingEntity != null) {
        boolean bl = this.guardian.getVisibilityCache().canSee(livingEntity);
        if (bl) {
          this.targetNotVisibleTicks = 0;
        } else {
          ++this.targetNotVisibleTicks;
        }

        double d = this.guardian.squaredDistanceTo(livingEntity);
        if (d < 4.0D) {
          if (!bl) {
            return;
          }

          if (this.fireballCooldown <= 0) {
            this.fireballCooldown = 20;
            this.guardian.tryAttack(livingEntity);
          }

          this.guardian.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
        } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
          double e = livingEntity.getX() - this.guardian.getX();
          double f = livingEntity.getBodyY(0.5D) - this.guardian.getBodyY(0.5D);
          double g = livingEntity.getZ() - this.guardian.getZ();
          if (this.fireballCooldown <= 0) {
            ++this.fireballsFired;
            if (this.fireballsFired == 1) {
              this.fireballCooldown = 60;
              this.guardian.setFireActive(true);
            } else if (this.fireballsFired <= 6) {
              this.fireballCooldown = 6;
            } else {
              this.fireballCooldown = 100;
              this.fireballsFired = 0;
              this.guardian.setFireActive(false);
            }

            if (this.fireballsFired > 1) {
              float h = MathHelper.sqrt(MathHelper.sqrt(d)) * 0.7F;
              if (!this.guardian.isSilent()) {
                this.guardian.world.syncWorldEvent((PlayerEntity) null, 1018, this.guardian.getBlockPos(), 0);
              }
              for (int i = 0; i < 1; ++i) {
                SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.guardian.world, this.guardian,
                    e + this.guardian.getRandom().nextGaussian() * (double) h, f,
                    g + this.guardian.getRandom().nextGaussian() * (double) h);
                smallFireballEntity.updatePosition(smallFireballEntity.getX(), this.guardian.getBodyY(0.5D) + 0.5D,
                    smallFireballEntity.getZ());
                this.guardian.world.spawnEntity(smallFireballEntity);
              }
            }
          }

          this.guardian.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
        } else if (this.targetNotVisibleTicks < 5) {
          this.guardian.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
        }

        super.tick();
      }
    }

    private double getFollowRange() {
      return this.guardian.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
    }
  }

  static class ShockWaveGoal extends Goal {
    private final BlazeGuardianEntity guardian;
    private int explosionTicker;

    public ShockWaveGoal(BlazeGuardianEntity guardian) {
      this.guardian = guardian;
      this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public boolean canStart() {
      LivingEntity livingEntity = this.guardian.getTarget();
      return livingEntity != null && livingEntity.isAlive() && this.guardian.canTarget(livingEntity)
          && this.guardian.squaredDistanceTo(livingEntity) < 8D;
    }

    public void start() {
      this.explosionTicker = 20;
      this.guardian.setFireActive(true);
    }

    public void stop() {
      this.guardian.setFireActive(false);
      this.explosionTicker = 0;
    }

    public void tick() {
      --this.explosionTicker;
      LivingEntity livingEntity = this.guardian.getTarget();
      if (livingEntity != null) {
        if (!this.guardian.world.isClient) {
          for (int o = 0; o < 3; o++) {
            this.guardian.world.addParticle(ParticleTypes.LAVA, this.guardian.getParticleX(0.7D),
                this.guardian.getRandomBodyY(), this.guardian.getParticleZ(0.7D), 0.0D, 0.0D, 0.0D);
            ((ServerWorld) this.guardian.world).spawnParticles(ParticleTypes.LAVA, this.guardian.getParticleX(0.7D),
                this.guardian.getRandomBodyY(), this.guardian.getParticleZ(0.7D), 0, 0.0D, 0.0D, 0.0D, 0.01D);
          }
          if (!this.guardian.world.isClient && explosionTicker == 1) {
            this.guardian.world.createExplosion(this.guardian, this.guardian.getX(), this.guardian.getY(),
                this.guardian.getZ(), 6.0F, true, Explosion.DestructionType.NONE);
          }
        }
        super.tick();
      }
    }

  }

}
