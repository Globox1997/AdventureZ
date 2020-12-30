package net.adventurez.entity;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;

public class PiglinBeastEntity extends HostileEntity {
  public static final TrackedData<Float> AttackTickVisual = DataTracker.registerData(PiglinBeastEntity.class,
      TrackedDataHandlerRegistry.FLOAT);
  public static final TrackedData<Float> LeadArm = DataTracker.registerData(PiglinBeastEntity.class,
      TrackedDataHandlerRegistry.FLOAT);
  private float attackTick;
  public float armTick;
  private int makePiglinsAngry = 0;

  public PiglinBeastEntity(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
    this.stepHeight = 1.0F;
    this.experiencePoints = 30;
    this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
  }

  public static DefaultAttributeContainer.Builder createPiglinBeastAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2D)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
  }

  @Override
  public void initGoals() {
    super.initGoals();
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(1, new PiglinBeastEntity.AttackGoal());
    this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0D));
    this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 12.0F));
    this.goalSelector.add(6, new LookAroundGoal(this));
    this.goalSelector.add(7, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    this.targetSelector.add(1, new FollowTargetGoal<>(this, WitherSkeletonEntity.class, true));
    this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
    this.targetSelector.add(3, (new RevengeGoal(this, new Class[] { PiglinEntity.class })));
  }

  @Override
  public void mobTick() {
    if (attackTick > 0F) {
      attackTick = attackTick - 0.08F;
      dataTracker.set(AttackTickVisual, attackTick);
    }
    if (this.getTarget() instanceof PlayerEntity) {
      makePiglinsAngry++;
      if (makePiglinsAngry == 600) {
        dataTracker.set(LeadArm, 1F);
        getPiglins();
        world.playSoundFromEntity(null, this, SoundInit.PIGLINBEAST_SHOUT_EVENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.0D);
      }
      if (makePiglinsAngry > 600) {
        dataTracker.set(LeadArm, dataTracker.get(LeadArm) - 0.02F);
      }
      if (makePiglinsAngry == 650) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.23D);
        makePiglinsAngry = 0;
      }
    }
  }

  @Override
  public void writeCustomDataToTag(CompoundTag tag) {
    super.writeCustomDataToTag(tag);
    tag.putFloat("AttackTick", this.attackTick);
    tag.putFloat("LeadArm", 0F);
  }

  @Override
  public void readCustomDataFromTag(CompoundTag tag) {
    super.readCustomDataFromTag(tag);
    this.attackTick = tag.getFloat("AttackTick");
    this.armTick = tag.getFloat("LeadArm");
  }

  @Override
  public void initDataTracker() {
    super.initDataTracker();
    dataTracker.startTracking(AttackTickVisual, 0F);
    dataTracker.startTracking(LeadArm, 0F);
  }

  @Override
  public boolean canSpawn(WorldView world) {
    return !world.containsFluid(this.getBoundingBox());
  }

  @Override
  public boolean canImmediatelyDespawn(double num) {
    return false;
  }

  @Override
  public void checkDespawn() {
    if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
      this.remove();
    }

  }

  @Override
  public boolean canUsePortals() {
    return false;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundInit.PIGLINBEAST_IDLE_EVENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundInit.PIGLINBEAST_HURT_EVENT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundInit.PIGLINBEAST_DEATH_EVENT;
  }

  @Override
  protected void playStepSound(BlockPos pos, BlockState state) {
    this.playSound(SoundInit.PIGLINBEAST_WALK_EVENT, 0.5F, 1.0F);
  }

  class AttackGoal extends MeleeAttackGoal {
    public AttackGoal() {
      super(PiglinBeastEntity.this, 1.0D, true);
    }

    @Override
    public double getSquaredMaxAttackDistance(LivingEntity entity) {
      float f = PiglinBeastEntity.this.getWidth();
      return (double) (f * f * 1.3D + entity.getWidth());
    }

    protected void attack(LivingEntity target, double squaredDistance) {
      double number = this.getSquaredMaxAttackDistance(target);
      if (squaredDistance <= number && attackTick <= 0F) {
        world.playSoundFromEntity(null, PiglinBeastEntity.this, SoundInit.PIGLINBEAST_CLUBSWING_EVENT,
            SoundCategory.HOSTILE, 1.0F, 1.0F);
        this.method_28346();
        this.mob.tryAttack(target);
        attackTick = 1F;
      }
    }
  }

  public void getPiglins() {
    List<Entity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(40D),
        EntityPredicates.EXCEPT_SPECTATOR);
    for (int i = 0; i < list.size(); ++i) {
      Entity entity = (Entity) list.get(i);
      if (entity.getType() == EntityType.PIGLIN) {
        PiglinEntity piglin = (PiglinEntity) entity;
        angerNearbyPiglins(piglin);
      }
    }
  }

  private static void angerNearbyPiglins(AbstractPiglinEntity piglin) {
    getNearbyPiglins(piglin).forEach((abstractPiglinEntity) -> {
      getNearestDetectedPlayer(abstractPiglinEntity).ifPresent((playerEntity) -> {
        becomeAngryWith(abstractPiglinEntity, playerEntity);
      });
    });
  }

  public static Optional<PlayerEntity> getNearestDetectedPlayer(AbstractPiglinEntity piglin) {
    return piglin.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)
        ? piglin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)
        : Optional.empty();
  }

  public static void becomeAngryWith(AbstractPiglinEntity piglin, LivingEntity target) {
    piglin.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    piglin.getBrain().remember(MemoryModuleType.ANGRY_AT, target.getUuid(), 600L);

    if (target.getType() == EntityType.PLAYER && piglin.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
      piglin.getBrain().remember(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
    }

  }

  private static List<AbstractPiglinEntity> getNearbyPiglins(AbstractPiglinEntity piglin) {
    return (List<AbstractPiglinEntity>) piglin.getBrain().getOptionalMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS)
        .orElse(ImmutableList.of());
  }

}