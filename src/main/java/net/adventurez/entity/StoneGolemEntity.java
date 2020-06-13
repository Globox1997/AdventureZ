package net.adventurez.entity;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

// import net.minecraft.entity.passive.IronGolemEntity;
// import net.minecraft.entity.mob.HostileEntity;
// import net.minecraft.entity.mob.ZombieEntity;
// import net.minecraft.entity.projectile.WitherSkullEntity;
// import net.minecraft.entity.projectile.FireballEntity;
// import net.minecraft.entity.projectile.thrown.SnowballEntity;
//import net.minecraft.client.render.
import net.minecraft.item.BowItem;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.BlazeEntity;

public class StoneGolemEntity extends HostileEntity {
  private static final Predicate<Entity> IS_NOT_RAVAGER = (entity) -> {
    return entity.isAlive() && !(entity instanceof StoneGolemEntity);
  };
  private int attackTick;
  private int stunTick;
  private int roarTick;
  private int attackTicksLeft;
  private int throwRockTick;

  public StoneGolemEntity(EntityType<? extends StoneGolemEntity> entityType, World world) {
    super(entityType, world);
    this.stepHeight = 1.0F;
    this.experiencePoints = 200; // High!!!!!!!!!!!!!
  }

  protected void initGoals() {
    super.initGoals();
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(4, new StoneGolemEntity.AttackGoal());
    this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.4D));
    this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { RaiderEntity.class })).setGroupRevenge());
    this.targetSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, true));
    this.targetSelector.add(4, new FollowTargetGoal<>(this, AbstractTraderEntity.class, true));
  }

  public static DefaultAttributeContainer.Builder createStoneGolemAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2.5D)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.5D)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
  }

  @Override
  protected EntityNavigation createNavigation(World world) {
    return new StoneGolemEntity.Navigation(this, world);
  }

  @Override
  public int getBodyYawSpeed() {
    return 45;
  }

  @Override
  public void tickMovement() {
    super.tickMovement();
    if (this.isAlive()) {
      if (this.attackTicksLeft > 0) {
        --this.attackTicksLeft;
      }
      if (this.horizontalCollision && this.world.getGameRules().getBoolean(GameRules.field_19388)) {
        boolean bl = false;
        Box box = this.getBoundingBox().expand(0.2D);
        Iterator var8 = BlockPos
            .iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ),
                MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ))
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

          } while (!(block instanceof Block && !block.is(Blocks.GRASS_BLOCK) && !block.is(Blocks.BEDROCK)
              && !block.is(Blocks.GLASS))); // XXXXXXXXXXXXXXXXXXXXXX
          // GroundStone

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
      if (this.throwRockTick > 0) {
        --this.throwRockTick;
        if (throwRockTick == 30) {
          this.throwRock2();
        }
      }

      if (this.stunTick > 0) {
        --this.stunTick;
        this.spawnStunnedParticles();
        if (this.stunTick == 0) {
          this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 1.0F, 1.0F);
          this.roarTick = 20;
        }
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
  private void throwRock2() {
    LivingEntity target = this.getTarget();
  Vec3d vec3d_1 = this.getRotationVec(1.0F);
  double double_3 = target.getX() - (this.getX() + vec3d_1.x * 2.0D);
  double double_4 = target.getBoundingBox().getYLength() + (double) (target.getHeight() / 2.0F)
          - (0.5D + this.getY() + (double) (this.getHeight() / 2.0F)) + 1D;
  double double_5 = target.getZ() - (this.getZ() + vec3d_1.z * 2.0D);
  double double_9 = target.getBoundingBox().getYLength() + (double) (target.getHeight() / 2.0F)
          - (0.5D + this.getY() + (double) (this.getHeight() / 2.0F)) + 0.7D;
  double double_10 = target.getZ() - (this.getZ() + vec3d_1.z * 2.0D) + 0.7D;
  double double_11 = target.getZ() - (this.getZ() + vec3d_1.z * 2.0D) - 0.7D;
  ThrownRockEntity skull1 = new ThrownRockEntity(world, this, double_3, double_4, double_5);
  ThrownRockEntity skull2 = new ThrownRockEntity(world, this, double_3, double_9, double_10);
  ThrownRockEntity skull3 = new ThrownRockEntity(world, this, double_3, double_9, double_11);
  double double_6 = this.getX() + vec3d_1.x * 2.0D;
  double double_7 = this.getY() + (double) this.getHeight();
  double double_8 = this.getZ() + vec3d_1.z * 2.0D;
  skull1.updatePosition(double_6, double_7, double_8);
  skull2.updatePosition(double_6, double_7, double_8);
  skull3.updatePosition(double_6, double_7, double_8);
  world.spawnEntity(skull1);
  world.spawnEntity(skull2);
  world.spawnEntity(skull3);
  }
  private void throwRock() {
    // persistentProjectileEntity.setProperties(playerEntity, playerEntity.pitch,
    // playerEntity.yaw, 0.0F, f * 3.0F, 1.0F);
    LivingEntity target = this.getTarget();
   // if (target != null && this.getVisibilityCache().canSee(target)) {
      double d = this.squaredDistanceTo(target);
      //if (d < 300D) {
        System.out.println("Throwed");
        double e = target.getX() - this.getX();
        double f = target.getBodyY(0.5D) - this.getBodyY(0.5D);
        double g = target.getZ() - this.getZ();

        double a = target.getX();
        double b = target.getBodyY(0.5D);
        double c = target.getZ();
        
        ThrownRockEntity test = new ThrownRockEntity(this.world, this, a , b, c );
        ThrownRockEntity test2 = new ThrownRockEntity(this.world, this.getX(), this.getY() , this.getZ(), a,b,c );
        ThrownRockEntity thrownRockEntity = new ThrownRockEntity(this.world, this, e // +
                                                                                     // this.getRandom().nextGaussian()
                                                                                     // * (double)h
            , f, g // + this.getRandom().nextGaussian() * (double)h
        );
        thrownRockEntity.updatePosition(thrownRockEntity.getX(), this.getBodyY(0.5D) + 0.5D, thrownRockEntity.getZ());
        this.world.spawnEntity(thrownRockEntity); 
        this.world.spawnEntity(test); 
        this.world.spawnEntity(test2); 
    //  }
  //  }
  }

  @Override
  protected boolean isImmobile() {
    return super.isImmobile() || this.attackTick > 0 || this.stunTick > 0 || this.roarTick > 0;
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
        this.playSound(SoundEvents.ENTITY_RAVAGER_STUNNED, 1.0F, 1.0F);
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
      List<Entity> list = this.world.getEntities(LivingEntity.class, this.getBoundingBox().expand(4.0D),
          IS_NOT_RAVAGER);

      Entity entity;
      for (Iterator var2 = list.iterator(); var2.hasNext(); this.knockBack(entity)) {
        entity = (Entity) var2.next();
        if (!(entity instanceof IllagerEntity)) {
          entity.damage(DamageSource.mob(this), 8.0F);
          entity.setVelocity(entity.getVelocity().add(0.0D, 0.63D, 0.0D));
        }
      }

      Vec3d vec3d = this.getBoundingBox().getCenter();

      for (int i = 0; i < 40; ++i) {
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
      this.throwRockTick = 60;
      this.attackTick = 10;
      this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
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
  public int getThrowRockTick() {
    return this.throwRockTick;
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
    this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
    return super.tryAttack(target);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_RAVAGER_AMBIENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_RAVAGER_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_RAVAGER_DEATH;
  }

  @Override
  protected void playStepSound(BlockPos pos, BlockState state) {
    this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15F, 1.0F);
  }

  @Override
  public boolean canSpawn(WorldView world) {
    return !world.containsFluid(this.getBoundingBox());
  }

  static class PathNodeMaker extends LandPathNodeMaker {
    private PathNodeMaker() {
    }

    protected PathNodeType adjustNodeType(BlockView world, boolean canOpenDoors, boolean canEnterOpenDoors,
        BlockPos pos, PathNodeType type) {
      return type == PathNodeType.LEAVES ? PathNodeType.OPEN
          : super.adjustNodeType(world, canOpenDoors, canEnterOpenDoors, pos, type);
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

    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
      float f = StoneGolemEntity.this.getWidth() - 0.1F;
      return (double) (f // * 2.0F
          * f // * 2.0F
          + entity.getWidth());
    }
  }
}
