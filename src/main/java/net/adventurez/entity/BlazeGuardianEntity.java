package net.adventurez.entity;

import java.util.EnumSet;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.adventurez.entity.nonliving.BlazeGuardianShieldEntity;
import net.adventurez.init.EntityInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoToWalkTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;

public class BlazeGuardianEntity extends HostileEntity {
    private float eyeOffset = 0.5F;
    private int eyeOffsetCooldown;
    private static final TrackedData<Byte> GUARDIAN_FLAGS;
    public static final TrackedData<Boolean> SHIELD_NORTH;
    public static final TrackedData<Boolean> SHIELD_EAST;
    public static final TrackedData<Boolean> SHIELD_SOUTH;
    public static final TrackedData<Boolean> SHIELD_WEST;
    private boolean isTryingToShockwave = false;
    private final BlazeGuardianShieldEntity shield_north;
    private final BlazeGuardianShieldEntity shield_east;
    private final BlazeGuardianShieldEntity shield_south;
    private final BlazeGuardianShieldEntity shield_west;

    public BlazeGuardianEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.experiencePoints = 20;

        shield_north = new BlazeGuardianShieldEntity(EntityInit.BLAZEGUARDIAN_SHIELD_ENTITY, this, "shield_north");
        shield_east = new BlazeGuardianShieldEntity(EntityInit.BLAZEGUARDIAN_SHIELD_ENTITY, this, "shield_east");
        shield_south = new BlazeGuardianShieldEntity(EntityInit.BLAZEGUARDIAN_SHIELD_ENTITY, this, "shield_south");
        shield_west = new BlazeGuardianShieldEntity(EntityInit.BLAZEGUARDIAN_SHIELD_ENTITY, this, "shield_west");
        if (this.world instanceof ServerWorld) {
            world.spawnEntity(shield_north);
            world.spawnEntity(shield_east);
            world.spawnEntity(shield_south);
            world.spawnEntity(shield_west);
        }
    }

    public static DefaultAttributeContainer.Builder createBlazeGuardianAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D).add(EntityAttributes.GENERIC_ARMOR, 3.0D);
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(3, new BlazeGuardianEntity.ShockWaveGoal(this));
        this.goalSelector.add(4, new BlazeGuardianEntity.ShootFireballGoal(this));
        this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0D));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D, 0.0F));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
        this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { BlazeEntity.class })));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(GUARDIAN_FLAGS, (byte) 0);
        this.dataTracker.startTracking(SHIELD_NORTH, true);
        this.dataTracker.startTracking(SHIELD_EAST, true);
        this.dataTracker.startTracking(SHIELD_SOUTH, true);
        this.dataTracker.startTracking(SHIELD_WEST, true);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putBoolean("ShieldNorth", this.shield_north.isAlive());
        tag.putBoolean("ShieldEast", this.shield_east.isAlive());
        tag.putBoolean("ShieldSouth", this.shield_south.isAlive());
        tag.putBoolean("ShieldWest", this.shield_west.isAlive());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.dataTracker.set(SHIELD_NORTH, tag.getBoolean("ShieldNorth"));
        if (!tag.getBoolean("ShieldNorth")) {
            this.shield_north.discard();
        }
        this.dataTracker.set(SHIELD_EAST, tag.getBoolean("ShieldEast"));
        if (!tag.getBoolean("ShieldEast")) {
            this.shield_east.discard();
        }
        this.dataTracker.set(SHIELD_SOUTH, tag.getBoolean("ShieldSouth"));
        if (!tag.getBoolean("ShieldSouth")) {
            this.shield_south.discard();
        }
        this.dataTracker.set(SHIELD_WEST, tag.getBoolean("ShieldWest"));
        if (!tag.getBoolean("ShieldWest")) {
            this.shield_west.discard();
        }
    }

    private void movePart(BlazeGuardianShieldEntity blazeGuardianShieldEntity, double dx, double dy, double dz) {
        blazeGuardianShieldEntity.setPosition(this.getX() + dx, this.getY() + 0.2D + dy, this.getZ() + dz);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }

    @Override
    public float getBrightnessAtEyes() {
        return 1.0F;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public void tickMovement() {
        if (!this.onGround && this.getVelocity().y < 0.0D) {
            this.setVelocity(this.getVelocity().multiply(1.0D, 0.6D, 1.0D));
        }

        if (this.world.isClient) {
            if (this.random.nextInt(24) == 0 && !this.isSilent()) {
                this.world.playSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.random.nextFloat(),
                        this.random.nextFloat() * 0.7F + 0.3F, false);
            }

            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getParticleX(0.6D), this.getRandomBodyY(), this.getParticleZ(0.6D), 0.0D, 0.0D, 0.0D);
            }
        } else {
            double f = (double) this.age / 6.2831853D + (this.bodyYaw / 360D * Math.PI * 2D) - 1.0D;
            this.movePart(this.shield_north, Math.cos(f) * 0.9D, 0.0D, Math.sin(f) * 0.9D);
            this.movePart(this.shield_east, Math.cos(f + 1.570796D) * 0.9D, 0.0D, Math.sin(f + 1.570796D) * 0.9D);
            this.movePart(this.shield_south, Math.cos(f + 3.1415926D) * 0.9D, 0.0D, Math.sin(f + 3.1415926D) * 0.9D);
            this.movePart(this.shield_west, Math.cos(f + 4.7123889D) * 0.9D, 0.0D, Math.sin(f + 4.7123889D) * 0.9D);
        }

        super.tickMovement();
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public void mobTick() {
        --this.eyeOffsetCooldown;
        if (this.eyeOffsetCooldown <= 0) {
            this.eyeOffsetCooldown = 100;
            this.eyeOffset = 0.5F + (float) this.random.nextGaussian() * 3.0F;
        }

        LivingEntity livingEntity = this.getTarget();
        if (livingEntity != null && livingEntity.getEyeY() > this.getEyeY() + (double) this.eyeOffset && this.canTarget(livingEntity) && !this.isTryingToShockwave) {
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(this.getVelocity().add(0.0D, (0.30000001192092896D - vec3d.y) * 0.30000001192092896D, 0.0D));
            this.velocityDirty = true;
        }

        super.mobTick();
    }

    @Override
    public boolean isOnFire() {
        return this.isFireActive();
    }

    private boolean isFireActive() {
        return ((Byte) this.dataTracker.get(GUARDIAN_FLAGS) & 1) != 0;
    }

    private void setFireActive(boolean fireActive) {
        byte b = (Byte) this.dataTracker.get(GUARDIAN_FLAGS);
        if (fireActive) {
            b = (byte) (b | 1);
        } else {
            b &= -2;
        }

        this.dataTracker.set(GUARDIAN_FLAGS, b);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!source.isUnblockable() && this.getHealth() < this.getMaxHealth() / 2F) {
            amount *= 0.5F;
        }
        return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
    }

    @Override
    public void onDeath(DamageSource source) {
        if (!this.world.isClient) {
            if (!this.shield_north.isRemoved())
                this.shield_north.discard();
            if (!this.shield_east.isRemoved())
                this.shield_east.discard();
            if (!this.shield_south.isRemoved())
                this.shield_south.discard();
            if (!this.shield_west.isRemoved())
                this.shield_west.discard();
        }
        super.onDeath(source);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        if (spawnReason.equals(SpawnReason.NATURAL) || spawnReason.equals(SpawnReason.CHUNK_GENERATION)) {
            for (int i = 0; i < serverWorldAccess.getRandom().nextInt(3) + 2; i++) {
                for (int u = 0; u < 10; u++) {
                    BlockPos pos = new BlockPos(this.getBlockPos().add(world.random.nextInt(5), world.random.nextInt(5), world.random.nextInt(5)));
                    if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, serverWorldAccess.toServerWorld(), pos, EntityType.BLAZE)) {
                        BlazeEntity blazeEntity = (BlazeEntity) EntityType.BLAZE.create(serverWorldAccess.toServerWorld());
                        blazeEntity.initialize(serverWorldAccess, this.world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
                        blazeEntity.refreshPositionAndAngles(pos, world.random.nextFloat() * 360.0F, 0.0F);
                        serverWorldAccess.spawnEntity(blazeEntity);
                        break;
                    }
                }
            }
        }
        return super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
    }

    public static boolean canSpawn(EntityType<BlazeGuardianEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (world.getBlockState(pos.down()).isOf(Blocks.NETHERRACK) && canSpawnInDark(type, world, spawnReason, pos, random) && random.nextInt(4) == 0) || spawnReason == SpawnReason.SPAWNER;
    }

    static {
        GUARDIAN_FLAGS = DataTracker.registerData(BlazeGuardianEntity.class, TrackedDataHandlerRegistry.BYTE);
        SHIELD_NORTH = DataTracker.registerData(BlazeGuardianEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        SHIELD_EAST = DataTracker.registerData(BlazeGuardianEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        SHIELD_SOUTH = DataTracker.registerData(BlazeGuardianEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        SHIELD_WEST = DataTracker.registerData(BlazeGuardianEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    static class ShootFireballGoal extends Goal {
        private final BlazeGuardianEntity guardian;
        private int fireballsFired;
        private int fireballCooldown;
        private int targetNotVisibleTicks;

        public ShootFireballGoal(BlazeGuardianEntity guardian) {
            this.guardian = guardian;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.guardian.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.guardian.canTarget(livingEntity);
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
                boolean bl = this.guardian.getVisibilityCache().canSee(livingEntity);
                if (bl) {
                    this.targetNotVisibleTicks = 0;
                } else {
                    ++this.targetNotVisibleTicks;
                }
                double d = this.guardian.squaredDistanceTo(livingEntity);
                if (d < 4.0D) {
                    if (!bl) {
                        return;
                    }
                    if (this.fireballCooldown <= 0) {
                        this.fireballCooldown = 20;
                        this.guardian.tryAttack(livingEntity);
                    }
                    this.guardian.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
                } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                    double e = livingEntity.getX() - this.guardian.getX();
                    double f = livingEntity.getBodyY(0.5D) - this.guardian.getBodyY(0.5D);
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
                            float h = MathHelper.sqrt(MathHelper.sqrt((float) d)) * 0.7F;
                            if (!this.guardian.isSilent()) {
                                this.guardian.world.syncWorldEvent((PlayerEntity) null, 1018, this.guardian.getBlockPos(), 0);
                            }
                            for (int i = 0; i < 1; ++i) {
                                SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.guardian.world, this.guardian, e + this.guardian.getRandom().nextGaussian() * (double) h, f,
                                        g + this.guardian.getRandom().nextGaussian() * (double) h);
                                smallFireballEntity.updatePosition(smallFireballEntity.getX(), this.guardian.getBodyY(0.5D) + 0.5D, smallFireballEntity.getZ());
                                this.guardian.world.spawnEntity(smallFireballEntity);
                            }
                        }
                    }

                    this.guardian.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
                } else if (this.targetNotVisibleTicks < 5) {
                    this.guardian.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
                }

                super.tick();
            }
        }

        private double getFollowRange() {
            return this.guardian.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        }
    }

    static class ShockWaveGoal extends Goal {
        private final BlazeGuardianEntity guardian;
        private int explosionTicker;

        public ShockWaveGoal(BlazeGuardianEntity guardian) {
            this.guardian = guardian;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.guardian.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.guardian.canTarget(livingEntity) && this.guardian.squaredDistanceTo(livingEntity) < 8D;
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
                if (!this.guardian.world.isClient) {
                    for (int o = 0; o < 3; o++) {
                        this.guardian.world.addParticle(ParticleTypes.LAVA, this.guardian.getParticleX(0.7D), this.guardian.getRandomBodyY(), this.guardian.getParticleZ(0.7D), 0.0D, 0.0D, 0.0D);
                        ((ServerWorld) this.guardian.world).spawnParticles(ParticleTypes.LAVA, this.guardian.getParticleX(0.7D), this.guardian.getRandomBodyY(), this.guardian.getParticleZ(0.7D), 0,
                                0.0D, 0.0D, 0.0D, 0.01D);
                    }
                    if (!this.guardian.world.isClient && explosionTicker == 1) {
                        this.guardian.world.createExplosion(this.guardian, this.guardian.getX(), this.guardian.getY(), this.guardian.getZ(), 6.0F, true, Explosion.DestructionType.NONE);
                    }
                }
                super.tick();
            }
        }

    }

}
