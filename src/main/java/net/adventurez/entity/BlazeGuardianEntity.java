package net.adventurez.entity;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import net.adventurez.entity.nonliving.BlazeGuardianShieldEntity;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class BlazeGuardianEntity extends Monster {
    private float eyeOffset = 0.5F;
    private int eyeOffsetCooldown;
    private static final EntityDataAccessor<Byte> GUARDIAN_FLAGS;
    public static final EntityDataAccessor<Boolean> SHIELD_NORTH;
    public static final EntityDataAccessor<Boolean> SHIELD_EAST;
    public static final EntityDataAccessor<Boolean> SHIELD_SOUTH;
    public static final EntityDataAccessor<Boolean> SHIELD_WEST;
    private boolean isTryingToShockwave = false;
    private final BlazeGuardianShieldEntity shield_north = new BlazeGuardianShieldEntity(EntityInit.BLAZE_GUARDIAN_SHIELD, this, "shield_north");
    private final BlazeGuardianShieldEntity shield_east = new BlazeGuardianShieldEntity(EntityInit.BLAZE_GUARDIAN_SHIELD, this, "shield_east");
    private final BlazeGuardianShieldEntity shield_south = new BlazeGuardianShieldEntity(EntityInit.BLAZE_GUARDIAN_SHIELD, this, "shield_south");
    private final BlazeGuardianShieldEntity shield_west = new BlazeGuardianShieldEntity(EntityInit.BLAZE_GUARDIAN_SHIELD, this, "shield_west");

    public BlazeGuardianEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createBlazeGuardianAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D).add(Attributes.FOLLOW_RANGE, 48.0D).add(Attributes.ARMOR, 3.0D);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(3, new BlazeGuardianEntity.ShockWaveGoal(this));
        this.goalSelector.addGoal(4, new BlazeGuardianEntity.ShootFireballGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers());
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, new Class[] { Blaze.class })));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(GUARDIAN_FLAGS, (byte) 0);
        builder.define(SHIELD_NORTH, true);
        builder.define(SHIELD_EAST, true);
        builder.define(SHIELD_SOUTH, true);
        builder.define(SHIELD_WEST, true);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("ShieldNorth", this.shield_north.isAlive());
        tag.putBoolean("ShieldEast", this.shield_east.isAlive());
        tag.putBoolean("ShieldSouth", this.shield_south.isAlive());
        tag.putBoolean("ShieldWest", this.shield_west.isAlive());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(SHIELD_NORTH, tag.getBoolean("ShieldNorth"));
        if (tag.getBoolean("ShieldNorth")) {
            this.level().addFreshEntity(shield_north);
        }
        this.entityData.set(SHIELD_EAST, tag.getBoolean("ShieldEast"));
        if (tag.getBoolean("ShieldEast")) {
            this.level().addFreshEntity(shield_east);
        }
        this.entityData.set(SHIELD_SOUTH, tag.getBoolean("ShieldSouth"));
        if (tag.getBoolean("ShieldSouth")) {
            this.level().addFreshEntity(shield_south);
        }
        this.entityData.set(SHIELD_WEST, tag.getBoolean("ShieldWest"));
        if (tag.getBoolean("ShieldWest")) {
            this.level().addFreshEntity(shield_west);
        }
    }

    private void movePart(BlazeGuardianShieldEntity blazeGuardianShieldEntity, double dx, double dy, double dz) {
        blazeGuardianShieldEntity.setPos(this.getX() + dx, this.getY() + 0.2D + dy, this.getZ() + dz);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return false;
    }

    @Override
    public void aiStep() {
        if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }

        if (this.level().isClientSide()) {
            if (this.level().getRandom().nextInt(24) == 0 && !this.isSilent()) {
                this.level().playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.BLAZE_BURN, this.getSoundSource(),
                        1.0F + this.level().getRandom().nextFloat(), this.level().getRandom().nextFloat() * 0.7F + 0.3F, false);
            }

            for (int i = 0; i < 2; ++i) {
                this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D), 0.0D, 0.0D, 0.0D);
            }
        } else {
            double f = (double) this.tickCount / 6.2831853D + (this.yBodyRot / 360D * Math.PI * 2D) - 1.0D;
            this.movePart(this.shield_north, Math.cos(f) * 0.9D, 0.0D, Math.sin(f) * 0.9D);
            this.movePart(this.shield_east, Math.cos(f + 1.570796D) * 0.9D, 0.0D, Math.sin(f + 1.570796D) * 0.9D);
            this.movePart(this.shield_south, Math.cos(f + 3.1415926D) * 0.9D, 0.0D, Math.sin(f + 3.1415926D) * 0.9D);
            this.movePart(this.shield_west, Math.cos(f + 4.7123889D) * 0.9D, 0.0D, Math.sin(f + 4.7123889D) * 0.9D);
        }

        super.aiStep();
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public void customServerAiStep() {
        --this.eyeOffsetCooldown;
        if (this.eyeOffsetCooldown <= 0) {
            this.eyeOffsetCooldown = 100;
            this.eyeOffset = 0.5F + (float) this.level().getRandom().nextGaussian() * 3.0F;
        }

        LivingEntity livingEntity = this.getTarget();
        if (livingEntity != null && livingEntity.getEyeY() > this.getEyeY() + (double) this.eyeOffset && this.canAttack(livingEntity) && !this.isTryingToShockwave) {
            Vec3 vec3d = this.getDeltaMovement();
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, (0.30000001192092896D - vec3d.y) * 0.30000001192092896D, 0.0D));
            this.hasImpulse = true;
        }

        super.customServerAiStep();
    }

    @Override
    public boolean isOnFire() {
        return this.isFireActive();
    }

    private boolean isFireActive() {
        return ((Byte) this.entityData.get(GUARDIAN_FLAGS) & 1) != 0;
    }

    private void setFireActive(boolean fireActive) {
        byte b = (Byte) this.entityData.get(GUARDIAN_FLAGS);
        if (fireActive) {
            b = (byte) (b | 1);
        } else {
            b &= -2;
        }

        this.entityData.set(GUARDIAN_FLAGS, b);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.getHealth() < this.getMaxHealth() / 2F) {
            amount *= 0.5F;
        }
        return this.isInvulnerableTo(source) ? false : super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide()) {
            if (!this.shield_north.isRemoved()) {
                this.shield_north.discard();
            }
            if (!this.shield_east.isRemoved()) {
                this.shield_east.discard();
            }
            if (!this.shield_south.isRemoved()) {
                this.shield_south.discard();
            }
            if (!this.shield_west.isRemoved()) {
                this.shield_west.discard();
            }
        }
        super.die(source);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorldAccess, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        if (spawnReason.equals(MobSpawnType.NATURAL) || spawnReason.equals(MobSpawnType.CHUNK_GENERATION)) {
            for (int i = 0; i < serverWorldAccess.getRandom().nextInt(3) + 2; i++) {
                for (int u = 0; u < 10; u++) {
                    BlockPos pos = new BlockPos(this.blockPosition().offset(this.level().getRandom().nextInt(5), this.level().getRandom().nextInt(5), this.level().getRandom().nextInt(5)));
                    if (SpawnPlacements.checkSpawnRules(EntityType.BLAZE, serverWorldAccess, MobSpawnType.NATURAL, pos, serverWorldAccess.getRandom())) {
                        Blaze blazeEntity = EntityType.BLAZE.create(serverWorldAccess.getLevel());
                        blazeEntity.finalizeSpawn(serverWorldAccess, this.level().getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null);
                        blazeEntity.moveTo(pos, this.level().getRandom().nextFloat() * 360.0F, 0.0F);
                        serverWorldAccess.addFreshEntity(blazeEntity);
                        break;
                    }
                }
            }
        }

        if (this.level() instanceof ServerLevel) {
            serverWorldAccess.addFreshEntity(shield_north);
            serverWorldAccess.addFreshEntity(shield_east);
            serverWorldAccess.addFreshEntity(shield_south);
            serverWorldAccess.addFreshEntity(shield_west);
            this.entityData.set(SHIELD_NORTH, true);
            this.entityData.set(SHIELD_EAST, true);
            this.entityData.set(SHIELD_SOUTH, true);
            this.entityData.set(SHIELD_WEST, true);
        }
        return super.finalizeSpawn(serverWorldAccess, difficulty, spawnReason, entityData);
    }

    public static boolean canSpawn(EntityType<BlazeGuardianEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return ((world.getBlockState(pos.below()).is(Blocks.NETHERRACK) || world.getBlockState(pos.below()).is(Blocks.NETHER_BRICKS)) && checkMonsterSpawnRules(type, world, spawnReason, pos, random)
                && random.nextInt(4) == 0) || spawnReason == MobSpawnType.SPAWNER;
    }

    static {
        GUARDIAN_FLAGS = SynchedEntityData.defineId(BlazeGuardianEntity.class, EntityDataSerializers.BYTE);
        SHIELD_NORTH = SynchedEntityData.defineId(BlazeGuardianEntity.class, EntityDataSerializers.BOOLEAN);
        SHIELD_EAST = SynchedEntityData.defineId(BlazeGuardianEntity.class, EntityDataSerializers.BOOLEAN);
        SHIELD_SOUTH = SynchedEntityData.defineId(BlazeGuardianEntity.class, EntityDataSerializers.BOOLEAN);
        SHIELD_WEST = SynchedEntityData.defineId(BlazeGuardianEntity.class, EntityDataSerializers.BOOLEAN);
    }

    static class ShootFireballGoal extends Goal {
        private final BlazeGuardianEntity guardian;
        private int fireballsFired;
        private int fireballCooldown;
        private int targetNotVisibleTicks;

        public ShootFireballGoal(BlazeGuardianEntity guardian) {
            this.guardian = guardian;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.guardian.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.guardian.canAttack(livingEntity);
        }

        @Override
        public void start() {
            this.fireballsFired = 0;
        }

        @Override
        public void stop() {
            this.guardian.setFireActive(false);
            this.targetNotVisibleTicks = 0;
        }

        @Override
        public void tick() {
            --this.fireballCooldown;
            LivingEntity livingEntity = this.guardian.getTarget();
            if (livingEntity != null) {
                boolean bl = this.guardian.getSensing().hasLineOfSight(livingEntity);
                if (bl) {
                    this.targetNotVisibleTicks = 0;
                } else {
                    ++this.targetNotVisibleTicks;
                }
                double d = this.guardian.distanceToSqr(livingEntity);
                if (d < 4.0D) {
                    if (!bl) {
                        return;
                    }
                    if (this.fireballCooldown <= 0) {
                        this.fireballCooldown = 20;
                        this.guardian.doHurtTarget(livingEntity);
                    }
                    this.guardian.getMoveControl().setWantedPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
                } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                    double e = livingEntity.getX() - this.guardian.getX();
                    double f = livingEntity.getY(0.5D) - this.guardian.getY(0.5D);
                    double g = livingEntity.getZ() - this.guardian.getZ();
                    if (this.fireballCooldown <= 0) {
                        ++this.fireballsFired;
                        if (this.fireballsFired == 1) {
                            this.fireballCooldown = 50;
                            this.guardian.setFireActive(true);
                        } else if (this.fireballsFired <= 7) {
                            this.fireballCooldown = 6;
                        } else {
                            this.fireballCooldown = 100;
                            this.fireballsFired = 0;
                            this.guardian.setFireActive(false);
                        }

                        if (this.fireballsFired > 1) {
                            float h = Mth.sqrt(Mth.sqrt((float) d)) * 0.7F;
                            if (!this.guardian.isSilent()) {
                                this.guardian.level().levelEvent((Player) null, 1018, this.guardian.blockPosition(), 0);
                            }
                            for (int i = 0; i < 1; ++i) {
                                Vec3 vec3d = new Vec3(this.guardian.getRandom().triangle(e, 2.297 * h), f, this.guardian.getRandom().triangle(g, 2.297 * h));
                                SmallFireball smallFireballEntity = new SmallFireball(this.guardian.level(), this.guardian, vec3d.normalize());
                                smallFireballEntity.absMoveTo(smallFireballEntity.getX(), this.guardian.getY(0.5D) + 0.5D, smallFireballEntity.getZ());
                                this.guardian.level().addFreshEntity(smallFireballEntity);
                            }
                        }
                    }

                    this.guardian.getLookControl().setLookAt(livingEntity, 10.0F, 10.0F);
                } else if (this.targetNotVisibleTicks < 5) {
                    this.guardian.getMoveControl().setWantedPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
                }

                super.tick();
            }
        }

        private double getFollowRange() {
            return this.guardian.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }

    static class ShockWaveGoal extends Goal {
        private final BlazeGuardianEntity guardian;
        private int explosionTicker;

        public ShockWaveGoal(BlazeGuardianEntity guardian) {
            this.guardian = guardian;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.guardian.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.guardian.canAttack(livingEntity) && this.guardian.distanceToSqr(livingEntity) < 12D;
        }

        @Override
        public void start() {
            this.explosionTicker = 20;
            this.guardian.setFireActive(true);
            this.guardian.isTryingToShockwave = true;
        }

        @Override
        public void stop() {
            this.guardian.setFireActive(false);
            this.explosionTicker = 0;
            this.guardian.isTryingToShockwave = false;
        }

        @Override
        public void tick() {
            --this.explosionTicker;
            LivingEntity livingEntity = this.guardian.getTarget();
            if (livingEntity != null) {
                if (!this.guardian.level().isClientSide()) {
                    for (int o = 0; o < 3; o++) {
                        this.guardian.level().addParticle(ParticleTypes.LAVA, this.guardian.getRandomX(0.7D), this.guardian.getRandomY(), this.guardian.getRandomZ(0.7D), 0.0D, 0.0D, 0.0D);
                        ((ServerLevel) this.guardian.level()).sendParticles(ParticleTypes.LAVA, this.guardian.getRandomX(0.7D), this.guardian.getRandomY(), this.guardian.getRandomZ(0.7D),
                                0, 0.0D, 0.0D, 0.0D, 0.01D);
                    }
                    if (!this.guardian.level().isClientSide() && explosionTicker == 1) {
                        this.guardian.level().explode(this.guardian, this.guardian.getX(), this.guardian.getY(), this.guardian.getZ(), 6.0F, true, ExplosionInteraction.MOB);
                    }
                }
                super.tick();
            }
        }

    }

}
