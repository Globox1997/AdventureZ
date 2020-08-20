package net.adventurez.entity;

import java.util.List;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecromancerEntity extends SpellCastingEntity {

  public NecromancerEntity(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
    this.stepHeight = 1.0F;
    this.experiencePoints = 15;

  }

  public static DefaultAttributeContainer.Builder createNecromancerAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 45.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
        .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
  }

  @Override
  public void initGoals() {
    super.initGoals();
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(1, new NecromancerEntity.LookAtTargetGoalNecro());
    this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.6D, 1.0D));
    this.goalSelector.add(4, new NecromancerEntity.SummonPuppetGoal());
    this.goalSelector.add(8, new WanderAroundGoal(this, 0.9D));
    this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F, 1.0F));
    this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { WitherPuppetEntity.class })));
    this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { WitherSkeletonEntity.class })));
    this.targetSelector.add(3,
        (new FollowTargetGoal<>(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
  }

  private boolean arePuppetsNearby() {
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

  @Override
  public void mobTick() {
    super.mobTick();
    if (this.isSpellcasting() && this.spellTicks == 3) {
      int invisibleChance = world.getRandom().nextInt(5);
      if (invisibleChance == 0) {
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 300));
        if (this.world.isClient) {
          for (int i = 0; i < 10; ++i) {
            this.world.addParticle(ParticleTypes.SMOKE, this.getParticleX(0.5D), this.getRandomBodyY(),
                this.getParticleZ(0.5D), 0.0D, 0.0D, 0.0D);
          }
        }
      }
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

  @Override
  public SoundEvent getCastSpellSound() {
    return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
  }

  @Override
  public boolean isTeammate(Entity other) {
    if (other == null) {
      return false;
    } else if (other == this) {
      return true;
    } else if (super.isTeammate(other)) {
      return true;
    } else if (other instanceof WitherPuppetEntity) {
      return this.isTeammate(((WitherPuppetEntity) other).getOwner());
    } else {
      return false;
    }
  }

  class SummonPuppetGoal extends SpellCastingEntity.CastSpellGoal {
    private final TargetPredicate closeVexPredicate;

    private SummonPuppetGoal() {
      super();
      this.closeVexPredicate = (new TargetPredicate()).setBaseMaxDistance(24.0D).includeHidden()
          .ignoreDistanceScalingFactor().includeInvulnerable().includeTeammates();
    }

    @Override
    public boolean canStart() {
      if (!super.canStart() || arePuppetsNearby()) {
        return false;
      } else {
        int i = NecromancerEntity.this.world.getTargets(WitherPuppetEntity.class, this.closeVexPredicate,
            NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().expand(16.0D)).size();
        return NecromancerEntity.this.random.nextInt(8) + 1 > i;
      }
    }

    @Override
    public int getSpellTicks() {
      return 100;
    }

    @Override
    public int startTimeDelay() {
      return 340;
    }

    @Override
    public void castSpell() {
      ServerWorld serverWorld = (ServerWorld) NecromancerEntity.this.world;

      for (int i = 0; i < 3; ++i) {
        BlockPos blockPos = NecromancerEntity.this.getBlockPos().add(-2 + NecromancerEntity.this.random.nextInt(5), 1,
            -2 + NecromancerEntity.this.random.nextInt(5));
        WitherPuppetEntity puppet = (WitherPuppetEntity) EntityInit.WITHERPUPPET_ENTITY
            .create(NecromancerEntity.this.world);
        puppet.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
        puppet.setOwner(NecromancerEntity.this);
        puppet.setLifeTicks(20 * (40 + NecromancerEntity.this.random.nextInt(90)));
        serverWorld.spawnEntityAndPassengers(puppet);
        int skeletonChance = world.getRandom().nextInt(14);
        if (skeletonChance == 0) {
          WitherSkeletonEntity witherSkeletonEntity = (WitherSkeletonEntity) EntityType.WITHER_SKELETON
              .create(NecromancerEntity.this.world);
          witherSkeletonEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
          serverWorld.spawnEntityAndPassengers(witherSkeletonEntity);
        }
      }

    }

    @Override
    public SoundEvent getSoundPrepare() {
      return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
    }

    @Override
    public SpellCastingEntity.Spell getSpell() {
      return SpellCastingEntity.Spell.SUMMON_PUPPET;
    }
  }

  class LookAtTargetGoalNecro extends SpellCastingEntity.LookAtTargetGoal {
    private LookAtTargetGoalNecro() {
      super();
    }

    @Override
    public void tick() {
      if (NecromancerEntity.this.getTarget() != null) {
        NecromancerEntity.this.getLookControl().lookAt(NecromancerEntity.this.getTarget(),
            (float) NecromancerEntity.this.getBodyYawSpeed(), (float) NecromancerEntity.this.getLookPitchSpeed());
      }

    }
  }

}