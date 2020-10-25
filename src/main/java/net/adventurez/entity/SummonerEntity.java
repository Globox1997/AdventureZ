package net.adventurez.entity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;

public class SummonerEntity extends SpellCastingEntity {
  public static final TrackedData<Boolean> inVulnerableShield = DataTracker.registerData(SummonerEntity.class,
      TrackedDataHandlerRegistry.BOOLEAN);
  private int invulnerableMagicTick;
  private boolean gotShotByABow = false;

  public SummonerEntity(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
    this.stepHeight = 1.0F;
  }

  public static DefaultAttributeContainer.Builder createSummonerAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 55.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D)
        .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.8D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D);
  }

  @Override
  public void initGoals() {
    super.initGoals();
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(1, new SummonerEntity.LookAtTargetGoalNecro());
    this.goalSelector.add(2, new NormalAttack(this, 1.0D, false));
    this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.7D, 0.9D));
    this.goalSelector.add(4, new SummonerEntity.SummonPuppetGoal());
    this.goalSelector.add(5, new SummonerEntity.ThunderboltSpellGoal());
    this.goalSelector.add(6, new SummonerEntity.InvulnerableSpellGoal());
    this.goalSelector.add(7, new SummonerEntity.TeleportSpellGoal());
    this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 7.0F, 1.0F));
    this.goalSelector.add(9, new WanderAroundGoal(this, 0.9D));
    this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { SkeletonVanguardEntity.class })));
    this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { ZombieEntity.class })));
    this.targetSelector.add(3, (new RevengeGoal(this, new Class[] { SkeletonEntity.class })));
    this.targetSelector.add(4,
        (new FollowTargetGoal<>(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
  }

  public static boolean canSpawn(EntityType<SummonerEntity> type, ServerWorldAccess world, SpawnReason spawnReason,
      BlockPos pos, Random random) {
    Optional<RegistryKey<Biome>> optional = world.method_31081(pos);
    boolean bl = (world.getDifficulty() != Difficulty.PEACEFUL && canSpawnInDark(type, world, spawnReason, pos, random)
        && world.isSkyVisible(pos) && world.getLevelProperties().isRaining()) || spawnReason == SpawnReason.SPAWNER;
    if (Objects.equals(optional, Optional.of(BiomeKeys.TAIGA))
        || Objects.equals(optional, Optional.of(BiomeKeys.SNOWY_TAIGA))
        || Objects.equals(optional, Optional.of(BiomeKeys.SNOWY_TUNDRA))
        || Objects.equals(optional, Optional.of(BiomeKeys.GIANT_SPRUCE_TAIGA))) {
      return bl;
    } else
      return false;
  }

  @Override
  public void writeCustomDataToTag(CompoundTag tag) {
    super.writeCustomDataToTag(tag);
    tag.putInt("InvulnerableMagicTick", this.invulnerableMagicTick);
  }

  @Override
  public void readCustomDataFromTag(CompoundTag tag) {
    super.readCustomDataFromTag(tag);
    this.invulnerableMagicTick = tag.getInt("InvulnerableMagicTick");
  }

  @Override
  public void initDataTracker() {
    super.initDataTracker();
    dataTracker.startTracking(inVulnerableShield, false);
  }

  @Override
  public void mobTick() {
    super.mobTick();
    if (dataTracker.get(inVulnerableShield)) {
      this.setInvulnerable(true);
      this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.0D);
      this.invulnerableMagicTick--;
      if (this.invulnerableMagicTick < 0) {
        this.setInvulnerable(false);
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
        dataTracker.set(inVulnerableShield, false);
      }
    }

  }

  @Override
  public int getLimitPerChunk() {
    return 1;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
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
    this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.5F, 0.7F);
  }

  @Override
  public SoundEvent getCastSpellSound() {
    return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
  }

  @Override
  public boolean damage(DamageSource source, float amount) {
    int chance = 0;
    if (source.equals(DamageSource.LIGHTNING_BOLT)) {
      return false;
    }
    if (source.isProjectile()) {
      chance = world.random.nextInt(2);
      this.gotShotByABow = true;
    }
    if (chance == 1) {
      this.world.playSoundFromEntity(null, this, SoundInit.MAGIC_SHIELD_HIT_EVENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
      if (source.getSource() != null && source.getSource() instanceof ProjectileEntity) {
        if (!world.isClient) {
          source.getSource().remove();
        }
      }
      return false;
    } else
      return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
  }

  class ThunderboltSpellGoal extends SpellCastingEntity.CastSpellGoal {
    private ThunderboltSpellGoal() {
      super();
    }

    @Override
    protected int getInitialCooldown() {
      return 200;
    }

    @Override
    public int getSpellTicks() {
      return 20;
    }

    @Override
    public int startTimeDelay() {
      return 20;
    }

    @Override
    public boolean canStart() {
      LivingEntity attacker = SummonerEntity.this.getAttacker();
      if (super.canStart() && (attacker != null && attacker.getMainHandStack().isItemEqual(new ItemStack(Items.BOW))
          && SummonerEntity.this.squaredDistanceTo(attacker) > 12) && SummonerEntity.this.gotShotByABow) {
        return true;
      } else
        return false;
    }

    @Override
    public void castSpell() {
      LivingEntity attacker = SummonerEntity.this.getAttacker();
      if (attacker != null) {
        ServerWorld serverWorld = (ServerWorld) attacker.world;
        double posX = attacker.getX() + world.getRandom().nextInt(3);
        double posY = attacker.getY();
        double posZ = attacker.getZ() + world.getRandom().nextInt(3);
        BlockPos pos = new BlockPos(posX, posY, posZ);
        LightningEntity lightningEntity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(attacker.world);
        lightningEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
        serverWorld.spawnEntity(lightningEntity);
      }
      gotShotByABow = false;
    }

    @Override
    public SoundEvent getSoundPrepare() {
      return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
    }

    @Override
    public SpellCastingEntity.Spell getSpell() {
      return SpellCastingEntity.Spell.THUNDERBOLT;
    }
  }

  class TeleportSpellGoal extends SpellCastingEntity.CastSpellGoal {
    private TeleportSpellGoal() {
      super();
    }

    @Override
    protected int getInitialCooldown() {
      return 200;
    }

    @Override
    public int getSpellTicks() {
      return 40;
    }

    @Override
    public int startTimeDelay() {
      return 60;
    }

    @Override
    public boolean canStart() {
      if (!super.canStart()) {
        return false;
      } else
        return true;
    }

    @Override
    public void castSpell() {
      for (int counter = 0; counter < 100; counter++) {
        float randomFloat = world.getRandom().nextFloat() * 6.2831855F;
        int posX = SummonerEntity.this.getBlockPos().getX()
            + MathHelper.floor(MathHelper.cos(randomFloat) * 8.0F + world.getRandom().nextInt(8));
        int posZ = SummonerEntity.this.getBlockPos().getZ()
            + MathHelper.floor(MathHelper.sin(randomFloat) * 8.0F + world.getRandom().nextInt(8));
        int posY = world.getTopY(Heightmap.Type.WORLD_SURFACE, posX, posZ);
        BlockPos teleportPos = new BlockPos(posX, posY, posZ);
        if (world.isRegionLoaded(teleportPos.getX() - 4, teleportPos.getY() - 4, teleportPos.getZ() - 4,
            teleportPos.getX() + 4, teleportPos.getY() + 4, teleportPos.getZ() + 4)
            && world.getChunkManager().shouldTickChunk(new ChunkPos(teleportPos)) && SpawnHelper
                .canSpawn(SpawnRestriction.Location.ON_GROUND, world, teleportPos, EntityInit.SUMMONER_ENTITY)) {
          SummonerEntity.this.teleport(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());
          break;
        }
      }
      SummonerEntity.this.playSpawnEffects();

    }

    @Override
    public SoundEvent getSoundPrepare() {
      return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
    }

    @Override
    public SpellCastingEntity.Spell getSpell() {
      return SpellCastingEntity.Spell.TELEPORT;
    }
  }

  class InvulnerableSpellGoal extends SpellCastingEntity.CastSpellGoal {
    private InvulnerableSpellGoal() {
      super();
    }

    @Override
    protected int getInitialCooldown() {
      return 420;
    }

    @Override
    public int getSpellTicks() {
      return 40;
    }

    @Override
    public int startTimeDelay() {
      return 60;
    }

    @Override
    public boolean canStart() {
      if (!super.canStart() || (SummonerEntity.this.getTarget() != null
          && SummonerEntity.this.squaredDistanceTo(SummonerEntity.this.getTarget()) > 7D)) {
        return false;
      } else
        return true;
    }

    @Override
    public void tick() {
      --this.spellCooldown;
      if (this.spellCooldown == 0) {
        this.castSpell();
        SummonerEntity.this.playSound(SoundInit.SPELL_CAST_SHIELD_EVENT, 1.0F, 1.0F);
      }

    }

    @Override
    public void castSpell() {
      dataTracker.set(inVulnerableShield, true);
      SummonerEntity.this.invulnerableMagicTick = 160;
      for (int i = 0; i < 60; ++i) {
        ((ServerWorld) world).spawnParticles(ParticleTypes.END_ROD, SummonerEntity.this.getParticleX(1.5D),
            SummonerEntity.this.getRandomBodyY(), SummonerEntity.this.getParticleZ(1.5D), 0, 0.0D, 0.0D, 0.0D, 0.0D);
      }
      if (SummonerEntity.this.getTarget() != null) {
        SummonerEntity.this.getTarget()
            .addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1, false, false));
      }

    }

    @Override
    public SoundEvent getSoundPrepare() {
      return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
    }

    @Override
    public SpellCastingEntity.Spell getSpell() {
      return SpellCastingEntity.Spell.SHIELD;
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
      if (!super.canStart() || tooManySlaves()) {
        return false;
      } else {
        int i = SummonerEntity.this.world.getTargets(WitherPuppetEntity.class, this.closeVexPredicate,
            SummonerEntity.this, SummonerEntity.this.getBoundingBox().expand(16.0D)).size();
        return SummonerEntity.this.random.nextInt(8) + 1 > i;
      }
    }

    @Override
    public int getSpellTicks() {
      return 140;
    }

    @Override
    public int startTimeDelay() {
      return 340;
    }

    @Override
    public void castSpell() {
      ServerWorld serverWorld = (ServerWorld) SummonerEntity.this.world;
      for (int i = 0; i < 3; ++i) {
        BlockPos blockPos = SummonerEntity.this.getBlockPos().add(-2 + SummonerEntity.this.random.nextInt(5), 1,
            -2 + SummonerEntity.this.random.nextInt(5));
        if (SummonerEntity.this.getHealth() <= 40.0F || SummonerEntity.this.getEntityWorld().isDay()) {
          SkeletonVanguardEntity skeletonVanguardEntity = (SkeletonVanguardEntity) EntityInit.SKELETON_VANGUARD_ENTITY
              .create(SummonerEntity.this.world);
          skeletonVanguardEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
          serverWorld.spawnEntityAndPassengers(skeletonVanguardEntity);
        } else {
          ZombieEntity zombieEntity = (ZombieEntity) EntityType.ZOMBIE.create(SummonerEntity.this.world);
          zombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
          serverWorld.spawnEntityAndPassengers(zombieEntity);
          int skeletonChance = world.getRandom().nextInt(8);
          if (skeletonChance == 0) {
            SkeletonEntity skeletonEntity = (SkeletonEntity) EntityType.SKELETON.create(SummonerEntity.this.world);
            skeletonEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
            if (SummonerEntity.this.gotShotByABow) {
              skeletonEntity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }
            serverWorld.spawnEntityAndPassengers(skeletonEntity);
          }
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
      if (SummonerEntity.this.getTarget() != null) {
        SummonerEntity.this.getLookControl().lookAt(SummonerEntity.this.getTarget(),
            (float) SummonerEntity.this.getBodyYawSpeed(), (float) SummonerEntity.this.getLookPitchSpeed());
      }

    }
  }

  private boolean tooManySlaves() {
    List<Entity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(40D),
        EntityPredicates.EXCEPT_SPECTATOR);
    if (!list.isEmpty()) {
      int vanguards = 0;
      int othermobs = 0;
      for (int i = 0; i < list.size(); i++) {
        Entity entity = (Entity) list.get(i);
        if (entity.getType() == EntityInit.SKELETON_VANGUARD_ENTITY) {
          vanguards++;
        } else if (entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.SKELETON) {
          othermobs++;
        }
        if (vanguards >= 5 || othermobs >= 7) {
          return true;
        }
      }
    }
    return false;
  }

  static class NormalAttack extends MeleeAttackGoal {
    private final SummonerEntity summonerEntity;

    public NormalAttack(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
      super(mob, speed, pauseWhenMobIdle);
      this.summonerEntity = (SummonerEntity) mob;
    }

    @Override
    public boolean canStart() {
      LivingEntity livingEntity = this.summonerEntity.getTarget();

      return livingEntity != null && livingEntity.isAlive() && this.summonerEntity.canTarget(livingEntity)
          && this.summonerEntity.squaredDistanceTo(livingEntity) < 7.0D && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
      LivingEntity livingEntity = this.mob.getTarget();
      if (livingEntity == null || this.summonerEntity.squaredDistanceTo(livingEntity) > 5D) {
        return false;
      } else if (!livingEntity.isAlive()) {
        return false;
      } else if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
        return false;
      } else {
        return !(livingEntity instanceof PlayerEntity)
            || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative();
      }
    }

    @Override
    public double getSquaredMaxAttackDistance(LivingEntity entity) {
      return (double) (this.mob.getWidth() * 2.5F * this.mob.getWidth() * 2.5F + entity.getWidth());
    }

  }

}
