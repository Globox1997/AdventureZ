package net.adventurez.entity;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class DesertRhinoEntity extends HostileEntity {

    private int sprintedTicker = 0;

    public DesertRhinoEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
        this.experiencePoints = 30;
    }

    public static DefaultAttributeContainer.Builder createDesertRhinoAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26D).add(EntityAttributes.GENERIC_ARMOR, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.2D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
    }

    public static boolean canSpawn(EntityType<DesertRhinoEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        List<DesertRhinoEntity> list = world.getEntitiesByClass(DesertRhinoEntity.class, new Box(pos).expand(120D), EntityPredicates.EXCEPT_SPECTATOR);
        return ((world.getBlockState(pos.down()).isOf(Blocks.SAND) || world.getBlockState(pos.down()).isOf(Blocks.SANDSTONE)) && world.getBaseLightLevel(pos, 0) > 8 && world.isSkyVisible(pos)
                && list.isEmpty() && world.getRandom().nextFloat() < 0.5F) || spawnReason == SpawnReason.SPAWNER;
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SprintAttackGoal(this));
        this.goalSelector.add(2, new HeadAttackGoal(this, 1.1D, true));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.85D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, HuskEntity.class, true));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[] { DesertRhinoEntity.class })));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient && sprintedTicker > 0) {
            sprintedTicker--;
        }

    }

    @Override
    public boolean canImmediatelyDespawn(double num) {
        return false;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.DESERT_RHINO_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.DESERT_RHINO_HIT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.DESERT_RHINO_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.DESERT_RHINO_WALK_EVENT, 0.8F, 1.0F);
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.DESERT_RHINO_ATTACK_EVENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
        return super.tryAttack(target);
    }

    private class HeadAttackGoal extends MeleeAttackGoal {
        private final DesertRhinoEntity desertRhinoEntity;

        public HeadAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.desertRhinoEntity = (DesertRhinoEntity) mob;
        }

        @Override
        public boolean canStart() {
            if (this.desertRhinoEntity.sprintedTicker > 0) {
                return false;
            } else
                return super.canStart();
        }

        @Override
        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double) (this.mob.getWidth() * 1.6F * this.mob.getWidth() * 1.6F + entity.getWidth());
        }
    }

    private class SprintAttackGoal extends Goal {
        private final DesertRhinoEntity desertRhinoEntity;
        private Path path;
        private BlockPos targetPos;
        private int cooldown;

        public SprintAttackGoal(DesertRhinoEntity desertRhinoEntity) {
            this.desertRhinoEntity = desertRhinoEntity;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            if (this.cooldown-- > 0) {
                return false;
            } else {
                LivingEntity livingEntity = this.desertRhinoEntity.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    this.path = this.desertRhinoEntity.getNavigation().findPathTo((Entity) livingEntity, 0);
                    if (this.path != null && this.path.reachesTarget()) {
                        this.targetPos = new BlockPos(this.path.getTarget());
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.desertRhinoEntity.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.desertRhinoEntity.isInWalkTargetRange(livingEntity.getBlockPos())) {
                return false;
            } else if (this.desertRhinoEntity.squaredDistanceTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ()) < 10.0D) {
                return false;
            } else {
                return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative();
            }
        }

        @Override
        public void start() {
            this.desertRhinoEntity.getNavigation().startMovingAlong(this.path, 1.5D);
        }

        @Override
        public void stop() {
            this.cooldown = 100 + world.random.nextInt(300);
            LivingEntity livingEntity = this.desertRhinoEntity.getTarget();
            if (livingEntity != null)
                this.attack(livingEntity, this.desertRhinoEntity.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()));
            this.desertRhinoEntity.setAttacking(false);
            this.desertRhinoEntity.sprintedTicker = 40;
        }

        private void attack(LivingEntity target, double squaredDistance) {
            double d = (double) (this.desertRhinoEntity.getWidth() * 2.25F * this.desertRhinoEntity.getWidth() * 2.25F + target.getWidth() * 1.25F);
            if (squaredDistance <= d) {
                this.desertRhinoEntity.swingHand(Hand.MAIN_HAND);
                this.desertRhinoEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK)
                        .setBaseValue(this.desertRhinoEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK) + 10.0D);
                BlockPos attackedPos;
                if (this.desertRhinoEntity.tryAttack(target))
                    attackedPos = new BlockPos(target.getX(), target.getY(), target.getZ());
                else
                    attackedPos = targetPos;
                if (!this.desertRhinoEntity.world.getBlockState(attackedPos.down()).isAir())
                    for (int i = 0; i < 30; i++) {
                        ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, this.desertRhinoEntity.world.getBlockState(attackedPos.down())),
                                attackedPos.getX() + world.random.nextDouble() * 2.5D - 1.25D, attackedPos.getY() + world.random.nextDouble() * 0.2D,
                                attackedPos.getZ() + world.random.nextDouble() * 2.5D - 1.25D, 4, 0.0D, world.random.nextDouble() * 0.15D, 0.D, 1.0D);
                    }
                this.desertRhinoEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK)
                        .setBaseValue(this.desertRhinoEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK) - 10.0D);
            }
        }
    }

}
