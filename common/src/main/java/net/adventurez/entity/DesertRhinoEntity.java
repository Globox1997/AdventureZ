package net.adventurez.entity;

import java.util.EnumSet;

import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.zombie.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class DesertRhinoEntity extends Monster {

    private int sprintedTicker = 0;

    public DesertRhinoEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 30;
    }

    public static AttributeSupplier.Builder createDesertRhinoAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.26D).add(Attributes.ARMOR, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D).add(Attributes.ATTACK_DAMAGE, 9.0D).add(Attributes.ATTACK_KNOCKBACK, 2.2D)
                .add(Attributes.FOLLOW_RANGE, 38.0D);
    }

    public static boolean canSpawn(EntityType<DesertRhinoEntity> type, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return (world.getRandom().nextFloat() < 0.5F && (world.getBlockState(pos.below()).is(Blocks.SAND) || world.getBlockState(pos.below()).is(Blocks.SANDSTONE))
                && world.getRawBrightness(pos, 0) > 8 && world.canSeeSky(pos) && world.getEntitiesOfClass(DesertRhinoEntity.class, new AABB(pos).inflate(120D), EntitySelector.NO_SPECTATORS).isEmpty())
                || spawnReason == EntitySpawnReason.SPAWNER;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SprintAttackGoal(this));
        this.goalSelector.addGoal(2, new HeadAttackGoal(this, 1.1D, true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.85D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Husk.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[]{DesertRhinoEntity.class})));
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && sprintedTicker > 0) {
            sprintedTicker--;
        }

    }

    @Override
    public boolean removeWhenFarAway(double num) {
        return false;
    }

    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    public boolean canUsePortal(boolean allowVehicles) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.DESERT_RHINO_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.DESERT_RHINO_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.DESERT_RHINO_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.DESERT_RHINO_STEP_EVENT, 0.8F, 1.0F);
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.DESERT_RHINO_ATTACK_EVENT, SoundSource.HOSTILE, 1.0F, 1.0F);
        return super.doHurtTarget(level, target);
    }

    private class HeadAttackGoal extends MeleeAttackGoal {
        private final DesertRhinoEntity desertRhinoEntity;

        public HeadAttackGoal(PathfinderMob mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.desertRhinoEntity = (DesertRhinoEntity) mob;
        }

        @Override
        public boolean canUse() {
            if (this.desertRhinoEntity.sprintedTicker > 0) {
                return false;
            } else
                return super.canUse();
        }

        // @Override
        // protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        //     return (double) (this.mob.getWidth() * 1.6F * this.mob.getWidth() * 1.6F + entity.getWidth());
        // }
    }

    private class SprintAttackGoal extends Goal {
        private final DesertRhinoEntity desertRhinoEntity;
        private Path path;
        private BlockPos targetPos;
        private int cooldown;

        public SprintAttackGoal(DesertRhinoEntity desertRhinoEntity) {
            this.desertRhinoEntity = desertRhinoEntity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.cooldown-- > 0) {
                return false;
            } else {
                LivingEntity livingEntity = this.desertRhinoEntity.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    this.path = this.desertRhinoEntity.getNavigation().createPath((Entity) livingEntity, 0);
                    if (this.path != null && this.path.canReach()) {
                        this.targetPos = this.path.getTarget();
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.desertRhinoEntity.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.desertRhinoEntity.isWithinHome(livingEntity.blockPosition())) {
                return false;
            } else if (this.desertRhinoEntity.distanceToSqr(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ()) < 10.0D) {
                return false;
            } else {
                return !(livingEntity instanceof Player) || !livingEntity.isSpectator() && !((Player) livingEntity).isCreative();
            }
        }

        @Override
        public void start() {
            this.desertRhinoEntity.getNavigation().moveTo(this.path, 1.5D);
        }

        @Override
        public void stop() {
            this.cooldown = 100 + this.desertRhinoEntity.level().getRandom().nextInt(300);
            LivingEntity livingEntity = this.desertRhinoEntity.getTarget();
            if (livingEntity != null)
                this.attack(livingEntity, this.desertRhinoEntity.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()));
            this.desertRhinoEntity.setAggressive(false);
            this.desertRhinoEntity.sprintedTicker = 40;
        }

        private void attack(LivingEntity target, double squaredDistance) {
            double d = (double) (this.desertRhinoEntity.getBbWidth() * 2.25F * this.desertRhinoEntity.getBbWidth() * 2.25F + target.getBbWidth() * 1.25F);
            if (squaredDistance <= d) {
                this.desertRhinoEntity.swingForAttack(InteractionHand.MAIN_HAND);
                this.desertRhinoEntity.getAttribute(Attributes.ATTACK_KNOCKBACK)
                        .setBaseValue(this.desertRhinoEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + 10.0D);
                BlockPos attackedPos;
                if (this.desertRhinoEntity.doHurtTarget(getServerLevel(this.desertRhinoEntity), target)) {
                    attackedPos = BlockPos.containing(target.getX(), target.getY(), target.getZ());
                } else {
                    attackedPos = targetPos;
                }
                if (!this.desertRhinoEntity.level().getBlockState(attackedPos.below()).isAir())
                    for (int i = 0; i < 30; i++) {
                        ((ServerLevel) this.desertRhinoEntity.level()).sendParticles(
                                new BlockParticleOption(ParticleTypes.BLOCK, this.desertRhinoEntity.level().getBlockState(attackedPos.below())),
                                attackedPos.getX() + this.desertRhinoEntity.level().getRandom().nextDouble() * 2.5D - 1.25D,
                                attackedPos.getY() + this.desertRhinoEntity.level().getRandom().nextDouble() * 0.2D,
                                attackedPos.getZ() + this.desertRhinoEntity.level().getRandom().nextDouble() * 2.5D - 1.25D, 4, 0.0D,
                                this.desertRhinoEntity.level().getRandom().nextDouble() * 0.15D, 0.D, 1.0D);
                    }
                this.desertRhinoEntity.getAttribute(Attributes.ATTACK_KNOCKBACK)
                        .setBaseValue(this.desertRhinoEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) - 10.0D);
            }
        }
    }

}
