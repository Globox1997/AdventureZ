package net.adventurez.entity;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.ParticleInit;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class EnderwarthogEntity extends HostileEntity {

    public static final TrackedData<Boolean> RARE_VARIANT;
    public static final TrackedData<Boolean> BITE_ATTACK;
    private int sprintedTicker = 0;

    public EnderwarthogEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1.0f);
        this.experiencePoints = 10;
    }

    public static DefaultAttributeContainer.Builder createEnderwarthogAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26D).add(EntityAttributes.GENERIC_ARMOR, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 5.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SprintAttackGoal(this));
        this.goalSelector.add(2, new HeadAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.85D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { EnderwarthogEntity.class })));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[] { EnderDragonEntity.class })));
        this.targetSelector.add(4, (new RevengeGoal(this, new Class[] { EndermanEntity.class })));
        this.targetSelector.add(5, (new RevengeGoal(this, new Class[] { EndermiteEntity.class })));
        this.targetSelector.add(6, (new RevengeGoal(this, new Class[] { ShulkerEntity.class })));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putInt("SprintTicker", this.sprintedTicker);
        tag.putBoolean("RareVariant", this.dataTracker.get(RARE_VARIANT));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.sprintedTicker = tag.getInt("SprintTicker");
        this.dataTracker.set(RARE_VARIANT, tag.getBoolean("RareVariant"));
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(RARE_VARIANT, false);
        this.dataTracker.startTracking(BITE_ATTACK, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient() && sprintedTicker > 0) {
            sprintedTicker--;
        }

    }

    @Override
    public void checkDespawn() {
        if (this.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.ENDERWARTHOG_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ENDERWARTHOG_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ENDERWARTHOG_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.ENDERWARTHOG_WALK_EVENT, 0.15F, 1.0F);
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.ENDERWARTHOG_ATTACK_EVENT, SoundCategory.HOSTILE, 1.0F,
                0.8F + this.getWorld().getRandom().nextFloat() * 0.4F);
        return super.tryAttack(target);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
        if (spawnReason.equals(SpawnReason.COMMAND))
            this.dataTracker.set(RARE_VARIANT, true);
        if ((spawnReason.equals(SpawnReason.NATURAL) || spawnReason.equals(SpawnReason.CHUNK_GENERATION)) && this.getWorld().getRandom().nextFloat() <= ConfigInit.CONFIG.warthog_rare_chance) {
            this.dataTracker.set(RARE_VARIANT, true);
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(this.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH) + 20.0D);
        }
        return entityData;
    }

    static {
        RARE_VARIANT = DataTracker.registerData(EnderwarthogEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BITE_ATTACK = DataTracker.registerData(EnderwarthogEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    private class HeadAttackGoal extends MeleeAttackGoal {
        private final EnderwarthogEntity enderwarthogEntity;
        private int cooldown;

        public HeadAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.enderwarthogEntity = (EnderwarthogEntity) mob;
        }

        @Override
        public boolean canStart() {
            if (this.enderwarthogEntity.sprintedTicker > 0) {
                return false;
            } else
                return super.canStart();
        }

        @Override
        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double) (this.mob.getWidth() * 1.6F * this.mob.getWidth() * 1.5F + entity.getWidth());
        }

        @Override
        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.cooldown <= 0) {
                this.enderwarthogEntity.dataTracker.set(BITE_ATTACK, true);
                this.resetCooldown();
                this.mob.swingHand(Hand.MAIN_HAND);
                this.mob.tryAttack(target);
            }

        }

        @Override
        public void stop() {
            super.stop();
            this.enderwarthogEntity.dataTracker.set(BITE_ATTACK, false);
        }
    }

    private class SprintAttackGoal extends Goal {
        private final EnderwarthogEntity enderwarthogEntity;
        private Vec3d targetPos;
        private int cooldown;

        public SprintAttackGoal(EnderwarthogEntity enderwarthogEntity) {
            this.enderwarthogEntity = enderwarthogEntity;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            if (this.cooldown-- > 0) {
                return false;
            } else {
                LivingEntity livingEntity = this.enderwarthogEntity.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    if (this.enderwarthogEntity.canSee(livingEntity) && Math.abs(livingEntity.getY() - this.enderwarthogEntity.getY()) <= 3.0D && livingEntity.isOnGround()) {
                        this.targetPos = livingEntity.getPos();
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.enderwarthogEntity.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (this.attack(livingEntity, this.enderwarthogEntity.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()))) {
                return false;
            } else if (this.enderwarthogEntity.squaredDistanceTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ()) < 10.0D) {
                return false;
            } else {
                if (!(livingEntity instanceof PlayerEntity) || livingEntity.isSpectator() || ((PlayerEntity) livingEntity).isCreative()) {
                    return false;
                } else {
                    Vec3d vec3d = new Vec3d(this.enderwarthogEntity.getX(), this.enderwarthogEntity.getEyeY(), this.enderwarthogEntity.getZ());
                    Vec3d vec3d2 = new Vec3d(targetPos.getX() - this.enderwarthogEntity.getX(), targetPos.getY() + 1.8D, targetPos.getZ() - this.enderwarthogEntity.getZ()).normalize().multiply(8.0D,
                            0.0D, 8.0D);
                    BlockHitResult blockHitResult = this.enderwarthogEntity.getWorld()
                            .raycast(new RaycastContext(vec3d, vec3d.add(vec3d2), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this.enderwarthogEntity));
                    return blockHitResult.getType() != HitResult.Type.BLOCK && !this.enderwarthogEntity.getWorld().getBlockState(blockHitResult.getBlockPos().down(3)).isAir();
                }
            }
        }

        @Override
        public void start() {
            double d = targetPos.getX() - this.enderwarthogEntity.getX();
            double e = targetPos.getZ() - this.enderwarthogEntity.getZ();
            this.targetPos = this.targetPos.add(new Vec3d(d, 0.0D, e).normalize().x * 10.0D, 0.0D, new Vec3d(d, 0.0D, e).normalize().z * 10.0D);
        }

        @Override
        public void tick() {
            double d = targetPos.getX() - this.enderwarthogEntity.getX();
            double e = targetPos.getZ() - this.enderwarthogEntity.getZ();
            float q = (float) (MathHelper.atan2(e, d) * 57.2957763671875D) - 90.0F;
            this.enderwarthogEntity.setYaw(this.wrapDegrees(this.enderwarthogEntity.getYaw(), q, 90.0F));
            this.enderwarthogEntity.move(MovementType.SELF, new Vec3d(d, 0.0D, e).normalize().multiply(0.6D));
            if (!this.enderwarthogEntity.getWorld().isClient()) {
                Vec3d vec3d2 = new Vec3d(targetPos.getX() - this.enderwarthogEntity.getX(), targetPos.getY() + 1.8D, targetPos.getZ() - this.enderwarthogEntity.getZ()).normalize();
                ((ServerWorld) getWorld()).spawnParticles(ParticleInit.SPRINT_PARTICLE, this.enderwarthogEntity.getParticleX(0.7D), this.enderwarthogEntity.getRandomBodyY(),
                        this.enderwarthogEntity.getParticleZ(0.7D), 0, vec3d2.x, 0.0D, vec3d2.z, 1.0D);
            }

        }

        @Override
        public void stop() {
            this.cooldown = 100 + this.enderwarthogEntity.getWorld().getRandom().nextInt(100);
            this.enderwarthogEntity.setAttacking(false);
            this.enderwarthogEntity.sprintedTicker = 40;
            super.stop();
        }

        private boolean attack(LivingEntity target, double squaredDistance) {
            double d = (double) (this.enderwarthogEntity.getWidth() * 1.5F * this.enderwarthogEntity.getWidth() * 1.5F + target.getWidth());
            if (squaredDistance <= d) {
                this.enderwarthogEntity.swingHand(Hand.MAIN_HAND);
                if (this.enderwarthogEntity.tryAttack(target)) {
                    Vec3d attackedPos = new Vec3d(target.getX(), target.getY(), target.getZ());

                    if (!this.enderwarthogEntity.getWorld().getBlockState(BlockPos.ofFloored(attackedPos).down()).isAir())
                        for (int i = 0; i < 30; i++) {
                            ((ServerWorld) getWorld()).spawnParticles(
                                    new BlockStateParticleEffect(ParticleTypes.BLOCK, this.enderwarthogEntity.getWorld().getBlockState(BlockPos.ofFloored(attackedPos).down())),
                                    attackedPos.getX() + this.enderwarthogEntity.getWorld().getRandom().nextDouble() * 2.5D - 1.25D,
                                    attackedPos.getY() + this.enderwarthogEntity.getWorld().getRandom().nextDouble() * 0.2D,
                                    attackedPos.getZ() + this.enderwarthogEntity.getWorld().getRandom().nextDouble() * 2.5D - 1.25D, 4, 0.0D,
                                    this.enderwarthogEntity.getWorld().getRandom().nextDouble() * 0.15D, 0.D, 1.0D);
                        }
                    target.setVelocity(target.getVelocity().add(0.0D, 0.1D, 0.0D));
                    target.takeKnockback(3.0D, (double) MathHelper.sin(this.enderwarthogEntity.getYaw() * 0.017453292F), (double) (-MathHelper.cos(this.enderwarthogEntity.getYaw() * 0.017453292F)));
                }
                return true;
            } else {
                return false;
            }
        }

        private float wrapDegrees(float from, float to, float max) {
            float f = MathHelper.wrapDegrees(to - from);
            if (f > max) {
                f = max;
            }

            if (f < -max) {
                f = -max;
            }

            float g = from + f;
            if (g < 0.0F) {
                g += 360.0F;
            } else if (g > 360.0F) {
                g -= 360.0F;
            }

            return g;
        }
    }

}
