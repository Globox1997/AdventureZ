package net.adventurez.entity;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ParticleInit;
import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class EnderwarthogEntity extends Monster {

    public static final EntityDataAccessor<Boolean> RARE_VARIANT;
    public static final EntityDataAccessor<Boolean> BITE_ATTACK;
    private int sprintedTicker = 0;

    public EnderwarthogEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createEnderwarthogAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.26D).add(Attributes.ARMOR, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D).add(Attributes.ATTACK_DAMAGE, 9.0D).add(Attributes.ATTACK_KNOCKBACK, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 38.0D);
    }

    public static boolean canSpawn(EntityType<EnderwarthogEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        BlockState blockState = world.getBlockState(pos);
        return world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Monster.checkMobSpawnRules(type, world, spawnReason, pos, random)
                && NaturalSpawner.isValidEmptySpawnBlock(world, pos, blockState, blockState.getFluidState(), EntityInit.ENDERWARTHOG);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SprintAttackGoal(this));
        this.goalSelector.addGoal(2, new HeadAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.85D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, new Class[]{EnderwarthogEntity.class})));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[]{EnderDragon.class})));
        this.targetSelector.addGoal(4, (new HurtByTargetGoal(this, new Class[]{EnderMan.class})));
        this.targetSelector.addGoal(5, (new HurtByTargetGoal(this, new Class[]{Endermite.class})));
        this.targetSelector.addGoal(6, (new HurtByTargetGoal(this, new Class[]{Shulker.class})));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SprintTicker", this.sprintedTicker);
        tag.putBoolean("RareVariant", this.entityData.get(RARE_VARIANT));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.sprintedTicker = tag.getInt("SprintTicker");
        this.entityData.set(RARE_VARIANT, tag.getBoolean("RareVariant"));
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(RARE_VARIANT, false);
        builder.define(BITE_ATTACK, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && sprintedTicker > 0) {
            sprintedTicker--;
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
        this.playSound(SoundInit.ENDERWARTHOG_STEP_EVENT, 0.15F, 1.0F);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.ENDERWARTHOG_ATTACK_EVENT, SoundSource.HOSTILE, 1.0F,
                0.8F + this.level().getRandom().nextFloat() * 0.4F);
        return super.doHurtTarget(target);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorldAccess, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        entityData = super.finalizeSpawn(serverWorldAccess, difficulty, spawnReason, entityData);
        if (spawnReason.equals(MobSpawnType.COMMAND))
            this.entityData.set(RARE_VARIANT, true);
        if ((spawnReason.equals(MobSpawnType.NATURAL) || spawnReason.equals(MobSpawnType.CHUNK_GENERATION)) && this.level().getRandom().nextFloat() <= ConfigInit.CONFIG.warthog_rare_chance) {
            this.entityData.set(RARE_VARIANT, true);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAttributeBaseValue(Attributes.MAX_HEALTH) + 20.0D);
        }
        return entityData;
    }

    static {
        RARE_VARIANT = SynchedEntityData.defineId(EnderwarthogEntity.class, EntityDataSerializers.BOOLEAN);
        BITE_ATTACK = SynchedEntityData.defineId(EnderwarthogEntity.class, EntityDataSerializers.BOOLEAN);
    }

    private class HeadAttackGoal extends MeleeAttackGoal {
        private final EnderwarthogEntity enderwarthogEntity;
        private int cooldown;

        public HeadAttackGoal(PathfinderMob mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.enderwarthogEntity = (EnderwarthogEntity) mob;
        }

        @Override
        public boolean canUse() {
            if (this.enderwarthogEntity.sprintedTicker > 0) {
                return false;
            } else
                return super.canUse();
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            if (canPerformAttack(target) && this.cooldown <= 0) {
                this.enderwarthogEntity.entityData.set(BITE_ATTACK, true);
                this.resetAttackCooldown();
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(target);
            }
        }

        @Override
        public void stop() {
            super.stop();
            this.enderwarthogEntity.entityData.set(BITE_ATTACK, false);
        }
    }

    private class SprintAttackGoal extends Goal {
        private final EnderwarthogEntity enderwarthogEntity;
        private Vec3 targetPos;
        private int cooldown;

        public SprintAttackGoal(EnderwarthogEntity enderwarthogEntity) {
            this.enderwarthogEntity = enderwarthogEntity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.cooldown-- > 0) {
                return false;
            } else {
                LivingEntity livingEntity = this.enderwarthogEntity.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    if (this.enderwarthogEntity.hasLineOfSight(livingEntity) && Math.abs(livingEntity.getY() - this.enderwarthogEntity.getY()) <= 3.0D && livingEntity.onGround()) {
                        this.targetPos = livingEntity.position();
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.enderwarthogEntity.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (this.attack(livingEntity, this.enderwarthogEntity.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()))) {
                return false;
            } else if (this.enderwarthogEntity.distanceToSqr(this.targetPos.x(), this.targetPos.y(), this.targetPos.z()) < 10.0D) {
                return false;
            } else {
                if (!(livingEntity instanceof Player) || livingEntity.isSpectator() || ((Player) livingEntity).isCreative()) {
                    return false;
                } else {
                    Vec3 vec3d = new Vec3(this.enderwarthogEntity.getX(), this.enderwarthogEntity.getEyeY(), this.enderwarthogEntity.getZ());
                    Vec3 vec3d2 = new Vec3(targetPos.x() - this.enderwarthogEntity.getX(), targetPos.y() + 1.8D, targetPos.z() - this.enderwarthogEntity.getZ()).normalize().multiply(8.0D,
                            0.0D, 8.0D);
                    BlockHitResult blockHitResult = this.enderwarthogEntity.level()
                            .clip(new ClipContext(vec3d, vec3d.add(vec3d2), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.enderwarthogEntity));
                    return blockHitResult.getType() != HitResult.Type.BLOCK && !this.enderwarthogEntity.level().getBlockState(blockHitResult.getBlockPos().below(3)).isAir();
                }
            }
        }

        @Override
        public void start() {
            double d = targetPos.x() - this.enderwarthogEntity.getX();
            double e = targetPos.z() - this.enderwarthogEntity.getZ();
            this.targetPos = this.targetPos.add(new Vec3(d, 0.0D, e).normalize().x * 10.0D, 0.0D, new Vec3(d, 0.0D, e).normalize().z * 10.0D);
        }

        @Override
        public void tick() {
            double d = targetPos.x() - this.enderwarthogEntity.getX();
            double e = targetPos.z() - this.enderwarthogEntity.getZ();
            float q = (float) (Mth.atan2(e, d) * 57.2957763671875D) - 90.0F;
            this.enderwarthogEntity.setYRot(this.wrapDegrees(this.enderwarthogEntity.getYRot(), q, 90.0F));
            this.enderwarthogEntity.move(MoverType.SELF, new Vec3(d, 0.0D, e).normalize().scale(0.6D));
            if (!this.enderwarthogEntity.level().isClientSide()) {
                Vec3 vec3d2 = new Vec3(targetPos.x() - this.enderwarthogEntity.getX(), targetPos.y() + 1.8D, targetPos.z() - this.enderwarthogEntity.getZ()).normalize();
                ((ServerLevel) level()).sendParticles(ParticleInit.SPRINT_PARTICLE, this.enderwarthogEntity.getRandomX(0.7D), this.enderwarthogEntity.getRandomY(),
                        this.enderwarthogEntity.getRandomZ(0.7D), 0, vec3d2.x, 0.0D, vec3d2.z, 1.0D);
            }

        }

        @Override
        public void stop() {
            this.cooldown = 100 + this.enderwarthogEntity.level().getRandom().nextInt(100);
            this.enderwarthogEntity.setAggressive(false);
            this.enderwarthogEntity.sprintedTicker = 40;
            super.stop();
        }

        private boolean attack(LivingEntity target, double squaredDistance) {
            double d = (double) (this.enderwarthogEntity.getBbWidth() * 1.5F * this.enderwarthogEntity.getBbWidth() * 1.5F + target.getBbWidth());
            if (squaredDistance <= d) {
                this.enderwarthogEntity.swing(InteractionHand.MAIN_HAND);
                if (this.enderwarthogEntity.doHurtTarget(target)) {
                    Vec3 attackedPos = new Vec3(target.getX(), target.getY(), target.getZ());

                    if (!this.enderwarthogEntity.level().getBlockState(BlockPos.containing(attackedPos).below()).isAir())
                        for (int i = 0; i < 30; i++) {
                            ((ServerLevel) level()).sendParticles(
                                    new BlockParticleOption(ParticleTypes.BLOCK, this.enderwarthogEntity.level().getBlockState(BlockPos.containing(attackedPos).below())),
                                    attackedPos.x() + this.enderwarthogEntity.level().getRandom().nextDouble() * 2.5D - 1.25D,
                                    attackedPos.y() + this.enderwarthogEntity.level().getRandom().nextDouble() * 0.2D,
                                    attackedPos.z() + this.enderwarthogEntity.level().getRandom().nextDouble() * 2.5D - 1.25D, 4, 0.0D,
                                    this.enderwarthogEntity.level().getRandom().nextDouble() * 0.15D, 0.D, 1.0D);
                        }
                    target.setDeltaMovement(target.getDeltaMovement().add(0.0D, 0.1D, 0.0D));
                    target.knockback(3.0D, (double) Mth.sin(this.enderwarthogEntity.getYRot() * 0.017453292F), (double) (-Mth.cos(this.enderwarthogEntity.getYRot() * 0.017453292F)));
                }
                return true;
            } else {
                return false;
            }
        }

        private float wrapDegrees(float from, float to, float max) {
            float f = Mth.wrapDegrees(to - from);
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
