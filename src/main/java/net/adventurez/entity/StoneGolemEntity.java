package net.adventurez.entity;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.TagInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.entity.boss.BossBar;

public class StoneGolemEntity extends HostileEntity {
  public static final TrackedData<Integer> throwCooldown = DataTracker.registerData(StoneGolemEntity.class,
      TrackedDataHandlerRegistry.INTEGER);
  public static final TrackedData<Boolean> inVulnerable = DataTracker.registerData(StoneGolemEntity.class,
      TrackedDataHandlerRegistry.BOOLEAN);
  public static final TrackedData<Integer> inVulnerableTimer = DataTracker.registerData(StoneGolemEntity.class,
      TrackedDataHandlerRegistry.INTEGER);
  public static final TrackedData<Integer> lavaTexture = DataTracker.registerData(StoneGolemEntity.class,
      TrackedDataHandlerRegistry.INTEGER);
  public static final TrackedData<Boolean> halfLifeChange = DataTracker.registerData(StoneGolemEntity.class,
      TrackedDataHandlerRegistry.BOOLEAN);
  private static final UUID WALKINSPEEDINCREASE_ID;
  private static final EntityAttributeModifier WALKINSPEEDINCREASE;
  private static final Predicate<Entity> NOT_STONEGOLEM = (entity) -> {
    return entity.isAlive() && !(entity instanceof StoneGolemEntity);
  };
  private int cooldown = 0;
  private int thrownStoneCooldown = 120;
  private int attackTick;
  private int stunTick;
  private int roarTick;
  private int powerPhaseActivate = 0;
  private int lavaRegenerateLife = 0;

  private final ServerBossBar bossBar;

  public StoneGolemEntity(EntityType<? extends StoneGolemEntity> entityType, World world) {
    super(entityType, world);
    this.stepHeight = 1.0F;
    this.experiencePoints = 200;
    this.bossBar = (ServerBossBar) (new ServerBossBar(this.getDisplayName(), BossBar.Color.RED,
        BossBar.Style.PROGRESS));
  }

  public static DefaultAttributeContainer.Builder createStoneGolemAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 600.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2.5D)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.5D)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
  }

  @Override
  public void initGoals() {
    super.initGoals();
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(4, new StoneGolemEntity.AttackGoal());
    this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.6D));
    this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 60.0F));
    this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    this.targetSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, false));
    this.targetSelector.add(4, new FollowTargetGoal<>(this, AbstractTraderEntity.class, true));
  }

  @Override
  public void writeCustomDataToTag(CompoundTag tag) {
    super.writeCustomDataToTag(tag);
    tag.putInt("AttackTick", this.attackTick);
    tag.putInt("StunTick", this.stunTick);
    tag.putInt("RoarTick", this.roarTick);
    tag.putInt("Invul", this.getInvulnerableTimer());
    tag.putInt("LavaTexture", this.getLavaTexture());
  }

  @Override
  public void readCustomDataFromTag(CompoundTag tag) {
    super.readCustomDataFromTag(tag);
    this.attackTick = tag.getInt("AttackTick");
    this.stunTick = tag.getInt("StunTick");
    this.roarTick = tag.getInt("RoarTick");
    this.setInvulTimer(tag.getInt("Invul"));
    this.setLavaTexture(tag.getInt("LavaTexture"));
    if (this.hasCustomName()) {
      this.bossBar.setName(this.getDisplayName());
    }
  }

  @Override
  public void setCustomName(Text name) {
    super.setCustomName(name);
    this.bossBar.setName(this.getDisplayName());
  }

  @Override
  public void onStartedTrackingBy(ServerPlayerEntity player) {
    super.onStartedTrackingBy(player);
    this.bossBar.addPlayer(player);
  }

  @Override
  public void onStoppedTrackingBy(ServerPlayerEntity player) {
    super.onStoppedTrackingBy(player);
    this.bossBar.removePlayer(player);
  }

  @Override
  public void initDataTracker() {
    super.initDataTracker();
    dataTracker.startTracking(throwCooldown, 0);
    dataTracker.startTracking(inVulnerable, true);
    dataTracker.startTracking(lavaTexture, 400);
    dataTracker.startTracking(halfLifeChange, false);
    this.dataTracker.startTracking(inVulnerableTimer, 0);
  }

  @Override
  protected EntityNavigation createNavigation(World world) {
    return new StoneGolemEntity.Navigation(this, world);
  }

  @Override
  public void mobTick() {
    this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
  }

  @Override
  public void tickMovement() {
    super.tickMovement();
    if (this.isAlive()) {
      if (this.getInvulnerableTimer() > 0) {
        this.setInvulTimer(this.getInvulnerableTimer() - 1);
      }
      if (this.getInvulnerableTimer() == 0) {
        dataTracker.set(inVulnerable, false);
      }
      if (this.getInvulnerableTimer() == 0) {
        if (this.getLavaTexture() < 400) {
          this.setLavaTexture(this.getLavaTexture() + 1);
        }
      }
      if (this.getHealth() <= this.getMaxHealth() / 2) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).setBaseValue(4.0D);
        if (this.powerPhaseActivate < 80) {
          this.powerPhaseActivate++;
          if (this.powerPhaseActivate == 1 || this.powerPhaseActivate == 2) {
            this.setAiDisabled(true);
          }
          if (this.powerPhaseActivate == 78 || this.powerPhaseActivate == 79) {
            this.setAiDisabled(false);
            this.roar();
          }
        }
        if (this.powerPhaseActivate == 80) {
          dataTracker.set(halfLifeChange, true);
        }
      } else if (this.isInLava() && this.getHealth() < this.getMaxHealth()) {
        this.lavaRegenerateLife++;
        if (this.lavaRegenerateLife >= 200) {
          this.setHealth(this.getHealth() + 2F);
          this.lavaRegenerateLife = 0;
        }
      }
      if (this.horizontalCollision && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
        boolean bl = false;
        Box box = this.getBoundingBox().expand(0.25D);
        Iterator<BlockPos> var8 = BlockPos
            .iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY + 0.25D), MathHelper.floor(box.minZ),
                MathHelper.floor(box.maxX), MathHelper.floor(box.maxY + 0.4D), MathHelper.floor(box.maxZ))
            .iterator();

        label60: while (true) {
          BlockPos blockPos;
          Block block;
          do {
            if (!var8.hasNext()) {
              if (!bl && this.onGround) {
                this.jump();
              }
              break label60;
            }

            blockPos = (BlockPos) var8.next();
            BlockState blockState = this.world.getBlockState(blockPos);
            block = blockState.getBlock();
          } while (!(block instanceof Block && !block.isIn(TagInit.UNBREAKABLE_BLOCKS)));

          bl = this.world.breakBlock(blockPos, true, this) || bl;
        }
      }

      if (this.roarTick > 0) {
        --this.roarTick;
        if (this.roarTick == 10) {
          this.roar();
        }
      }

      if (this.attackTick > 0) {
        --this.attackTick;
      }

      if (this.stunTick > 0) {
        --this.stunTick;
        this.spawnStunnedParticles();
        if (this.stunTick == 0) {
          this.playSound(SoundInit.GOLEM_ROAR_EVENT, 1.0F, 1.0F);
          this.roarTick = 30;
        }
      }
      if (this.getTarget() != null && this.canSee(this.getTarget())) {
        if (this.squaredDistanceTo(getTarget()) < 1400D && this.squaredDistanceTo(getTarget()) > 100D) {
          dataTracker.set(throwCooldown, cooldown);
          this.cooldown++;
          if (cooldown == thrownStoneCooldown - 10) {
            this.playSound(SoundInit.GOLEM_ROAR_EVENT, 1F, 1F);
            throwRock(this.getTarget());
          }
          if (cooldown >= thrownStoneCooldown) {
            this.cooldown = -80;
          }
        }
      }
      if (this.isOnSoulSpeedBlock() && !this.getAttributes()
          .hasModifierForAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKINSPEEDINCREASE_ID)) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(WALKINSPEEDINCREASE);
      } else if (!this.isOnSoulSpeedBlock() && this.getAttributes()
          .hasModifierForAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKINSPEEDINCREASE_ID)) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(WALKINSPEEDINCREASE_ID);
      }
      if (this.isInLava()) {
        this.setVelocity(this.getVelocity().multiply(1.9D));
      }
    }
  }

  private void spawnStunnedParticles() {
    if (this.random.nextInt(6) == 0) {
      double d = this.getX() - (double) this.getWidth() * Math.sin((double) (this.bodyYaw * 0.017453292F))
          + (this.random.nextDouble() * 0.6D - 0.3D);
      double e = this.getY() + (double) this.getHeight() - 0.3D;
      double f = this.getZ() + (double) this.getWidth() * Math.cos((double) (this.bodyYaw * 0.017453292F))
          + (this.random.nextDouble() * 0.6D - 0.3D);
      this.world.addParticle(ParticleTypes.ENTITY_EFFECT, d, e, f, 0.4980392156862745D, 0.5137254901960784D,
          0.5725490196078431D);
    }

  }

  @Override
  protected boolean isImmobile() {
    return super.isImmobile() || this.attackTick > 0 || this.stunTick > 0 || this.roarTick > 0
        || (this.cooldown > thrownStoneCooldown - 30 && this.cooldown > 0)
        || this.getDataTracker().get(StoneGolemEntity.inVulnerable);
  }

  @Override
  public boolean canSee(Entity entity) {
    return this.stunTick <= 0 && this.roarTick <= 0 ? super.canSee(entity) : false;
  }

  @Override
  protected void knockback(LivingEntity target) {
    if (this.roarTick == 0) {
      if (this.random.nextDouble() < 0.5D) {
        this.stunTick = 40;
        this.playSound(SoundInit.GOLEM_IDLE_EVENT, 1.0F, 1.0F);
        this.world.sendEntityStatus(this, (byte) 39);
        target.pushAwayFrom(this);
      } else {
        this.knockBack(target);
      }

      target.velocityModified = true;
    }

  }

  private void roar() {
    if (this.isAlive()) {
      List<Entity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(5.0D),
          NOT_STONEGOLEM);

      Entity entity;
      for (Iterator<Entity> var2 = list.iterator(); var2.hasNext(); this.knockBack(entity)) {
        entity = (Entity) var2.next();
        entity.damage(DamageSource.mob(this), 8.0F);
        entity.setVelocity(entity.getVelocity().add(0.0D, 0.63D, 0.0D));
      }

      Vec3d vec3d = this.getBoundingBox().getCenter();

      for (int i = 0; i < 50; ++i) {
        double d = this.random.nextGaussian() * 0.2D;
        double e = this.random.nextGaussian() * 0.2D;
        double f = this.random.nextGaussian() * 0.2D;
        this.world.addParticle(ParticleTypes.POOF, vec3d.x, vec3d.y, vec3d.z, d, e, f);
      }
    }

  }

  private void knockBack(Entity entity) {
    double d = entity.getX() - this.getX();
    double e = entity.getZ() - this.getZ();
    double f = Math.max(d * d + e * e, 0.001D);
    entity.addVelocity(d / f * 4.0D, 0.2D, e / f * 4.0D);
  }

  @Override
  @Environment(EnvType.CLIENT)
  public void handleStatus(byte status) {

    if (status == 4) {
      this.attackTick = 10;
      this.playSound(SoundInit.GOLEM_HIT_EVENT, 1.0F, 1.0F);
    } else if (status == 39) {
      this.stunTick = 40;
    }

    super.handleStatus(status);
  }

  @Environment(EnvType.CLIENT)
  public int getAttackTick() {
    return this.attackTick;
  }

  @Environment(EnvType.CLIENT)
  public int getStunTick() {
    return this.stunTick;
  }

  @Environment(EnvType.CLIENT)
  public int getRoarTick() {
    return this.roarTick;
  }

  @Override
  public boolean tryAttack(Entity target) {
    this.attackTick = 10;
    this.world.sendEntityStatus(this, (byte) 4);
    this.playSound(SoundInit.GOLEM_HIT_EVENT, 1.0F, 1.0F);
    return super.tryAttack(target);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    if (this.getDataTracker().get(StoneGolemEntity.inVulnerable)) {
      return null;
    } else
      return SoundInit.GOLEM_IDLE_EVENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundInit.GOLEM_HIT_EVENT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundInit.GOLEM_DEATH_EVENT;
  }

  @Override
  protected void playStepSound(BlockPos pos, BlockState state) {
    this.playSound(SoundInit.GOLEM_WALK_EVENT, 0.15F, 1.0F);
  }

  @Override
  public boolean canSpawn(WorldView world) {
    return !world.containsFluid(this.getBoundingBox());
  }

  @Override
  public boolean cannotDespawn() {
    return true;
  }

  @Override
  public boolean canUsePortals() {
    return false;
  }

  @Override
  public EntityGroup getGroup() {
    return EntityGroup.DEFAULT;
  }

  @Override
  public boolean damage(DamageSource source, float amount) {
    if (this.getDataTracker().get(StoneGolemEntity.inVulnerable)) {
      return false;
    } else
      return super.damage(source, amount);
  }

  private void throwRock(LivingEntity target) {
    Vec3d vec3d_1 = this.getRotationVec(1.0F);
    double x_vector;
    double z_vector;
    if (vec3d_1.x < 0 && vec3d_1.z < 0) {
      x_vector = vec3d_1.x + 1.8D;
      z_vector = vec3d_1.z - 2.8D;
    } else if (vec3d_1.x < 0 && vec3d_1.z > 0) {
      x_vector = vec3d_1.x - 1.8D;
      z_vector = vec3d_1.z - 2.8D;
    } else if (vec3d_1.x > 0 && vec3d_1.z < 0) {
      x_vector = vec3d_1.x + 1.8D;
      z_vector = vec3d_1.z + 2.8D;
    } else if (vec3d_1.x > 0 && vec3d_1.z > 0) {
      x_vector = vec3d_1.x - 1.8D;
      z_vector = vec3d_1.z + 2.8D;
    } else {
      x_vector = vec3d_1.x + 1.8D;
      z_vector = vec3d_1.z - 2.8D;
    }
    double x = target.getX() - this.getX() - x_vector;
    double y = target.getBodyY(1D) - this.getBodyY(0.1D);
    double z = target.getZ() - this.getZ() - z_vector;
    ThrownRockEntity thrownRockEntity = new ThrownRockEntity(this.world, this.getX() + x_vector, this.getY() + 0.5D,
        this.getZ() + z_vector);
    thrownRockEntity.setOwner(this);
    if (!world.isClient) {
      if (this.squaredDistanceTo(getTarget()) < 800D) {
        thrownRockEntity.setVelocity(x, y, z, 1.8F, 3.0F);
        this.world.spawnEntity(thrownRockEntity);
      } else {
        thrownRockEntity.setVelocity(x, y + 4D, z, 1.8F, 2.0F);
        this.world.spawnEntity(thrownRockEntity);
      }
    }
  }

  public void sendtoEntity() {
    this.setInvulTimer(220);
    this.setLavaTexture(0);
  }

  public int getInvulnerableTimer() {
    return (Integer) this.dataTracker.get(inVulnerableTimer);
  }

  public void setInvulTimer(int ticks) {
    this.dataTracker.set(inVulnerableTimer, ticks);
  }

  public int getLavaTexture() {
    return (Integer) this.dataTracker.get(lavaTexture);
  }

  public void setLavaTexture(int ticks) {
    this.dataTracker.set(lavaTexture, ticks);
  }

  @Override
  public void onDeath(DamageSource source) {
    if (!this.removed && !this.dead) {
      Entity entity = source.getAttacker();
      LivingEntity livingEntity = this.getPrimeAdversary();

      if (this.scoreAmount >= 0 && livingEntity != null) {
        livingEntity.updateKilledAdvancementCriterion(this, this.scoreAmount, source);
      }

      if (entity != null) {
        entity.onKilledOther((ServerWorld) this.world, this);
      }

      this.dead = true;
      this.getDamageTracker().update();
      if (!this.world.isClient) {
        this.drop(source);
        this.onKilledBy(livingEntity);
        SmallStoneGolemEntity smallStoneGolemEntity = (SmallStoneGolemEntity) EntityInit.SMALLSTONEGOLEM_ENTITY
            .create(this.world);
        SmallStoneGolemEntity smallStoneGolemEntity2 = (SmallStoneGolemEntity) EntityInit.SMALLSTONEGOLEM_ENTITY
            .create(this.world);
        smallStoneGolemEntity.refreshPositionAndAngles(this.getBlockPos().east(), 0.0F, 0.0F);
        smallStoneGolemEntity2.refreshPositionAndAngles(this.getBlockPos().west(), 0.0F, 0.0F);
        this.world.spawnEntity(smallStoneGolemEntity);
        this.world.spawnEntity(smallStoneGolemEntity2);
      }

      this.world.sendEntityStatus(this, (byte) 3);
      this.setPose(EntityPose.DYING);
    }
  }

  static class PathNodeMaker extends LandPathNodeMaker {
    private PathNodeMaker() {
    }

    protected PathNodeType adjustNodeType(BlockView world, boolean canOpenDoors, boolean canEnterOpenDoors,
        BlockPos pos, PathNodeType type) {
      return type == PathNodeType.LAVA ? PathNodeType.OPEN : super.adjustNodeType(world, false, false, pos, type);
    }
  }

  static class Navigation extends MobNavigation {
    public Navigation(MobEntity mobEntity, World world) {
      super(mobEntity, world);
    }

    protected PathNodeNavigator createPathNodeNavigator(int range) {
      this.nodeMaker = new StoneGolemEntity.PathNodeMaker();
      return new PathNodeNavigator(this.nodeMaker, range);
    }
  }

  class AttackGoal extends MeleeAttackGoal {
    public AttackGoal() {
      super(StoneGolemEntity.this, 1.0D, true);
    }

    public double getSquaredMaxAttackDistance(LivingEntity entity) {
      float f = StoneGolemEntity.this.getWidth() - 0.1F;
      return (double) (f * f * 1.5F + entity.getWidth());
    }
  }

  static {
    WALKINSPEEDINCREASE_ID = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
    WALKINSPEEDINCREASE = new EntityAttributeModifier(WALKINSPEEDINCREASE_ID, "LavaAndSoulSpeed", 0.5D,
        EntityAttributeModifier.Operation.MULTIPLY_BASE);
  }
}