package net.adventurez.entity;

import java.util.List;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

// import net.minecraft.entity.mob.EvokerEntity;
// import net.minecraft.entity.mob.SkeletonEntity;

public class NecromancerEntity extends HostileEntity {
  private int summonCooldown = 100;
  private int effectCooldown = 100;

  public NecromancerEntity(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
    this.stepHeight = 1.0F;
    this.experiencePoints = 10;
  }

  public static DefaultAttributeContainer.Builder createNecromancerAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 45.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
        .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
  }

  @Override
  public void initGoals() {
    super.initGoals();
    this.goalSelector.add(0, new SwimGoal(this));
    // this.goalSelector.add(1, new EvokerEntity.LookAtTargetOrWololoTarget());
    this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.6D, 1.0D));
    // this.goalSelector.add(4, new EvokerEntity.SummonVexGoal());
    // this.goalSelector.add(5, new EvokerEntity.ConjureFangsGoal());
    this.goalSelector.add(8, new WanderAroundGoal(this, 0.9D));
    this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F, 1.0F));
    this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    // this.targetSelector.add(1, (new RevengeGoal(this, new Class[] {
    // RaiderEntity.class })).setGroupRevenge());
    this.targetSelector.add(1,
        (new FollowTargetGoal<>(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
  }

  @Override
  public void mobTick() {
    LivingEntity target = this.getTarget();
    if (target != null && target.isAlive() && target instanceof PlayerEntity && this.isInRange(target, 16D)) {
      this.summonCooldown--;
      this.effectCooldown--;
      if (this.summonCooldown < 30 && arePuppetsNearby()) {
        this.summonCooldown = 100;
      }
      if (this.summonCooldown <= 20) {
        this.navigation.stop();
        if (this.summonCooldown == 19) {
          this.playSound(SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, 1F, 1F);
        }
        if (this.summonCooldown <= 0) {
          this.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1F, 1F);
          this.summonPuppets();
          this.getLookControl().lookAt(target, (float) this.getBodyYawSpeed(), (float) this.getLookPitchSpeed());
          this.summonCooldown = 100;
        }
      }

      if (this.isInRange(target, 3D) && effectCooldown <= 0) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 160, 0));
        if (this.world.isClient) {
          for (int i = 0; i < 20; i++) {
            double d = (double) target.getBlockPos().getX() + (double) world.random.nextFloat();
            double e = (double) target.getBlockPos().getY() + (double) MathHelper.nextDouble(random, 0.0D, 1.8D);
            double f = (double) target.getBlockPos().getZ() + (double) world.random.nextFloat();
            this.world.addParticle(ParticleTypes.EFFECT, d, e, f, 0.0D, 1.0D, 0.0D);
          }
        }
        this.effectCooldown = 100;
      }

    }

  }

  public boolean arePuppetsNearby() {
    List<Entity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(40D),
        EntityPredicates.EXCEPT_SPECTATOR);
    if (!list.isEmpty()) {
      for (int i = 0; i < list.size(); i++) {
        Entity entity = (Entity) list.get(i);
        if (entity.getType() == EntityInit.WITHERPUPPET_ENTITY) {
          return true;
        }
      }
    }
    return false;
  }

  // public boolean isTeammate(Entity other) {
  // if (other == null) {
  // return false;
  // } else if (other == this) {
  // return true;
  // } else if (super.isTeammate(other)) {
  // return true;
  // } else if (other instanceof VexEntity) {
  // return this.isTeammate(((VexEntity) other).getOwner());
  // } else if (other instanceof LivingEntity && ((LivingEntity) other).getGroup()
  // == EntityGroup.ILLAGER) {
  // return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
  // } else {
  // return false;
  // }
  // }

  private void summonPuppets() {
    for (int i = 0; i < 3; ++i) {
      BlockPos blockPos = this.getBlockPos().add(-2 + this.random.nextInt(5), 1, -2 + this.random.nextInt(5));
      WitherPuppetEntity witherPuppetEntity = (WitherPuppetEntity) EntityInit.WITHERPUPPET_ENTITY.create(this.world);
      witherPuppetEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
      // witherPuppetEntity.initialize(EvokerEntity.this.world,
      // EvokerEntity.this.world.getLocalDifficulty(blockPos),
      // SpawnReason.MOB_SUMMONED, (EntityData) null, (CompoundTag) null);
      witherPuppetEntity.setOwner(NecromancerEntity.this);
      witherPuppetEntity.setLifeTicks(20 * (40 + NecromancerEntity.this.random.nextInt(90)));
      this.world.spawnEntity(witherPuppetEntity);
    }

  }

  @Override
  public boolean canHaveStatusEffect(StatusEffectInstance effect) {
    return effect.getEffectType() == StatusEffects.WITHER ? false : super.canHaveStatusEffect(effect);
  }

  @Override
  public boolean canImmediatelyDespawn(double distanceSquared) {
    return false;
  }

  @Override
  public int getLimitPerChunk() {
    return 1;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundInit.SOULREAPER_IDLE_EVENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundInit.SOULREAPER_HURT_EVENT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundInit.SOULREAPER_DEATH_EVENT;
  }

  @Override
  protected void playStepSound(BlockPos pos, BlockState state) {
    this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.5F, 1.0F);
  }

}