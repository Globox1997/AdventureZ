package net.adventurez.entity;

import net.adventurez.entity.nonliving.TinyEyeEntity;
import net.adventurez.init.*;
import net.adventurez.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

@SuppressWarnings("deprecation")
public class TheEyeEntity extends Mob implements Enemy {
    private static final EntityDataAccessor<Integer> BEAM_TARGET_ID;
    public static final EntityDataAccessor<Integer> INVUL_TIMER;
    private int field_7082;
    @Nullable
    private final ServerBossEvent bossBar;
    private LivingEntity cachedBeamTarget;
    private boolean gotDamage;
    private int attackTpCounter;
    private int deathTimer;
    private int duplicationTimer = 0;

    private final boolean isVoidZLoaded = Services.PLATFORM.isModLoaded("voidz");

    public TheEyeEntity(EntityType<? extends TheEyeEntity> entityType, Level world) {
        super(entityType, world);
        if (this.duplicationTimer <= 0) {
            this.bossBar = new ServerBossEvent(this.getUUID(), this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);
            this.xpReward = 80;
        } else {
            this.bossBar = null;
            this.xpReward = 0;
        }
        this.moveControl = new TheEyeEntity.EyeMoveControl(this);
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    @Override
    public void travel(Vec3 movementInput) {
        this.travelFlying(movementInput, 0.02F);
    }

    public static AttributeSupplier.Builder createTheEntityAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 800.0D).add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FOLLOW_RANGE, 60.0D).add(Attributes.ARMOR, 5.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ShootBulletGoal(this));
        this.goalSelector.addGoal(1, new FireBeamGoal(this));
        this.goalSelector.addGoal(2, new DuplicateAttack(this));
        this.goalSelector.addGoal(3, new FlyRandomlyGoal(this));
        this.goalSelector.addGoal(2, new LookAtTargetGoal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(INVUL_TIMER, 0);
        builder.define(BEAM_TARGET_ID, 0);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Invul", this.getInvulnerableTimer());
        tag.putInt("DeathTimer", this.deathTimer);
        tag.putInt("DuplicationTimer", this.duplicationTimer);
    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        this.setInvulTimer(tag.getIntOr("Invul", 0));
        if (this.hasCustomName() && this.bossBar != null) {
            this.bossBar.setName(this.getDisplayName());
        }
        this.deathTimer = tag.getIntOr("DeathTimer", 0);
        this.duplicationTimer = tag.getIntOr("DuplicationTimer", 0);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        if (this.bossBar != null)
            this.bossBar.setName(this.getDisplayName());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.THE_EYE_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.THE_EYE_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.THE_EYE_DEATH_EVENT;
    }

    @Override
    public void aiStep() {
        Vec3 vec3d = this.getDeltaMovement().multiply(0.8D, 0.5D, 0.8D);
        if (!this.level().isClientSide() && this.getTarget() != null) {
            Entity entity = this.getTarget();
            double d = Math.max(0.0D, vec3d.y);

            if (this.getY() < entity.getY() + 12.0D) {
                d += 0.3D - d * 0.6D;
            } else if (this.getY() > entity.getY() + 26.0D)
                d -= 0.3D - d * 0.6D;

            vec3d = new Vec3(vec3d.x, d, vec3d.z);
            Vec3 vec3d2 = new Vec3(entity.getX() - this.getX(), 0.0D, entity.getZ() - this.getZ());
            if (distanceToSqr(vec3d2) > 16.0D) {
                Vec3 vec3d3 = vec3d2.normalize();
                vec3d = vec3d.add(vec3d3.x * 0.05D - vec3d.x * 0.05D, 0.0D, vec3d3.z * 0.05D - vec3d.z * 0.05D);
            }
        }
        this.setDeltaMovement(vec3d);
        super.aiStep();
    }

    @Override
    public void customServerAiStep(ServerLevel level) {
        if (!this.isNoGravity()) {
            this.setNoGravity(true);
        }
        int j;
        if (this.getInvulnerableTimer() > 0) {
            j = this.getInvulnerableTimer() - 1;
            if (j <= 0) {
                this.level().explode(this, this.getX(), this.getEyeY(), this.getZ(), 7.0F, false, Level.ExplosionInteraction.MOB);
                if (!this.isSilent()) {
                    this.level().globalLevelEvent(1023, this.blockPosition(), 0);
                }
            }
            this.getNavigation().stop();
            this.setTarget(null);
            this.setInvulTimer(j);
        } else {
            super.customServerAiStep(level);
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                if (attackTpCounter >= 120 && !this.hasBeamTarget()) {
                    for (int counter = 0; counter < 100; counter++) {
                        float randomFloat = this.level().getRandom().nextFloat() * 6.2831855F;
                        int posX = livingEntity.blockPosition().getX() + Mth.floor(Mth.cos(randomFloat) * 9.0F + livingEntity.level().getRandom().nextInt(12));
                        int posZ = livingEntity.blockPosition().getZ() + Mth.floor(Mth.sin(randomFloat) * 9.0F + livingEntity.level().getRandom().nextInt(12));
                        int posY = livingEntity.level().getHeight(Heightmap.Types.WORLD_SURFACE, posX, posZ) + 10 + livingEntity.level().getRandom().nextInt(12);
                        BlockPos teleportPos = new BlockPos(posX, posY, posZ);
                        if (livingEntity.level().hasChunksAt(teleportPos.getX() - 4, teleportPos.getY() - 4, teleportPos.getZ() - 4, teleportPos.getX() + 4, teleportPos.getY() + 4,
                                teleportPos.getZ() + 4)) {
                            this.lookControl.setLookAt(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());
                            if (!this.level().isClientSide()) {
                                livingEntity.randomTeleport(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ(), false, state -> false);
                            }
                            livingEntity.level().playSound(null, teleportPos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);
                            if (this.level().isClientSide()) {
                                this.despawnParticlesServer(livingEntity);
                            }

                            attackTpCounter = -100;
                            break;
                        }
                    }
                } else {
                    attackTpCounter++;
                }
            }

            int n;
            if (this.field_7082 > 0) {
                --this.field_7082;
                if (this.field_7082 == 0 && ((ServerLevel) this.level()).getGameRules().get(GameRules.MOB_GRIEFING)) {
                    j = Mth.floor(this.getY());
                    n = Mth.floor(this.getX());
                    int o = Mth.floor(this.getZ());
                    boolean bl = false;

                    for (int p = -1; p <= 1; ++p) {
                        for (int q = -1; q <= 1; ++q) {
                            for (int r = 0; r <= 3; ++r) {
                                int s = n + p;
                                int t = j + r;
                                int u = o + q;
                                BlockPos blockPos = new BlockPos(s, t, u);
                                BlockState blockState = this.level().getBlockState(blockPos);
                                if (canDestroy(blockState)) {
                                    bl = this.level().destroyBlock(blockPos, true, this) || bl;
                                }
                            }
                        }
                    }

                    if (bl) {
                        this.level().levelEvent((Player) null, 1022, this.blockPosition(), 0);
                    }
                }
            }

            if (this.tickCount % 20 == 0) {
                this.heal(1.0F);
            }
            if (this.bossBar != null)
                this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && this.duplicationTimer > 0) {
            this.duplicationTimer--;
            if (this.duplicationTimer == 20) {
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY(0.5D), this.getZ(), 200, this.getBbWidth(), this.getBbHeight(), this.getBbWidth(), 0.0D);
                this.setNoAi(true);
            }
            if (this.duplicationTimer == 1) {
                this.discard();
            }
        }
    }

    private boolean canDestroy(BlockState block) {
        return !block.isAir() && !block.getBlock().builtInRegistryHolder().is(TagInit.UNBREAKABLE_BLOCKS);
    }

    public void setEyeInvulnerabletime() {
        this.setInvulTimer(220);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        if (this.duplicationTimer <= 0)
            this.bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        if (this.bossBar != null)
            this.bossBar.removePlayer(player);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return super.hurtServer(level, source, amount);
        } else if (this.isInvulnerableTo(level, source)) {
            return false;
        } else if (!source.is(DamageTypeTags.IS_DROWNING) && !(source.getEntity() instanceof TheEyeEntity)) {
            if (this.getInvulnerableTimer() > 0 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                return false;
            } else {
                Entity entity2;
                entity2 = source.getEntity();
                if (!(entity2 instanceof Player) && entity2 instanceof LivingEntity livingEntity && livingEntity.is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                    return false;
                } else {
                    if (this.field_7082 <= 0) {
                        this.field_7082 = 20;
                    }
                    if (this.level().getRandom().nextFloat() < 0.6F) {
                        this.gotDamage = true;
                    }
                    return super.hurtServer(level, source, amount);
                }
            }
        } else {
            return false;
        }
    }

    private boolean gotDamage() {
        if (this.gotDamage) {
            this.gotDamage = false;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean addEffect(MobEffectInstance effect, Entity entity) {
        return false;
    }

    public int getInvulnerableTimer() {
        return this.entityData.get(INVUL_TIMER);
    }

    public void setInvulTimer(int ticks) {
        this.entityData.set(INVUL_TIMER, ticks);
    }

    @Override
    protected boolean canRide(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortal(boolean allowVehicles) {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return false;
    }

    private void setBeamTarget(int entityId) {
        this.entityData.set(BEAM_TARGET_ID, entityId);
    }

    public boolean hasBeamTarget() {
        return this.entityData.get(BEAM_TARGET_ID) != 0;
    }

    @Nullable
    public LivingEntity getBeamTarget() {
        if (!this.hasBeamTarget()) {
            return null;
        } else if (this.level().isClientSide()) {
            if (this.cachedBeamTarget != null) {
                return this.cachedBeamTarget;
            } else {
                Entity entity = this.level().getEntity(this.entityData.get(BEAM_TARGET_ID));
                if (entity instanceof LivingEntity) {
                    this.cachedBeamTarget = (LivingEntity) entity;
                    return this.cachedBeamTarget;
                } else {
                    return null;
                }
            }
        } else {
            return this.getTarget();
        }
    }

    public int getWarmupTime() {
        return 120;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);
        if (BEAM_TARGET_ID.equals(data)) {
            this.cachedBeamTarget = null;
        }

    }

    @Override
    public boolean removeWhenFarAway(double num) {
        return false;
    }

    @Override
    public void tickDeath() {
        if (this.duplicationTimer <= 0) {
            this.deathTimer++;
            this.move(MoverType.SELF, new Vec3(0.0D, 0.005D, 0.0D));
            this.setNoAi(true);
            this.setTarget(null);
            if (this.level().isClientSide()) {
                despawnParticlesServer(this);

            }
            if (!this.level().isClientSide()) {
                this.bossBar.setProgress(0.0F);
                BlockPos deathPos = BlockPos.containing(this.getX(), this.getY() - 1, this.getZ());
                if (deathTimer == 20 && !this.isVoidZLoaded) {
                    AABB box = new AABB(this.blockPosition());
                    List<Player> list = this.level().getEntitiesOfClass(Player.class, box.inflate(128D), EntitySelector.NO_SPECTATORS);
                    for (Player player : list) {
                        player.addEffect(new MobEffectInstance(EffectInit.FAME, 48000, 0, false, false, true));
                    }
                }
                if (deathTimer == 140) {
                    this.level().playSound(null, deathPos, SoundInit.THE_EYE_DEATH_PLATFORM_EVENT, SoundSource.HOSTILE, 1F, 1F);
                }
                if (deathTimer >= 200) {
                    for (int o = 0; o < 15; o++) {
                        ((ServerLevel) this.level()).sendParticles(ParticleTypes.EXPLOSION, deathPos.getX() - 6 + this.level().getRandom().nextInt(13),
                                deathPos.getY() - 1 + this.level().getRandom().nextInt(11), deathPos.getZ() - 6 + this.level().getRandom().nextInt(13), 0, 0.0D, 0.0D, 0.0D, 0.01D);
                        this.level().playSound(null, deathPos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 1F, 1F);
                    }
                    // Platform
                    if (this.level().getHeight() - 10 < deathPos.getY())
                        deathPos = deathPos.below(deathPos.getY() - this.level().getHeight() + 10);
                    this.placeDeathStructure(deathPos);
                    if (this.isVoidZLoaded) {
                        this.level().setBlock(deathPos.above(8).north().west(), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("voidz", "void_portal")).defaultBlockState(), 3);
                    } else {
                        this.level().setBlock(deathPos.above(8).north().west(), Blocks.DRAGON_EGG.defaultBlockState(), 3);
                    }
                    if (this.level().getRandom().nextFloat() <= 0.01F) {
                        this.spawnAtLocation((ServerLevel) this.level(), ItemInit.PRIME_EYE);
                    }
                    this.discard();
                }

            }
        } else
            this.discard();

    }

    private void despawnParticlesServer(LivingEntity entity) {
        for (int i = 0; i < 12; ++i) {
            double d = this.random.nextGaussian() * 0.025D;
            double e = this.random.nextGaussian() * 0.025D;
            double f = this.random.nextGaussian() * 0.025D;
            double x = Mth.nextDouble(random, entity.getBoundingBox().minX - 1.5D, entity.getBoundingBox().maxX + 1.5D);
            double y = Mth.nextDouble(random, entity.getBoundingBox().minY - 1.5D, entity.getBoundingBox().maxY + 1.5D);
            double z = Mth.nextDouble(random, entity.getBoundingBox().minZ - 1.5D, entity.getBoundingBox().maxZ + 1.5D);
            entity.level().addParticle(ParticleTypes.PORTAL, x, y, z, d, e, f);
        }
    }

    private void placeDeathStructure(BlockPos blockPos) {
        StructureTemplateManager structureTemplateManager = ((ServerLevel) this.level()).getStructureTemplateManager();
        Optional<StructureTemplate> structure = structureTemplateManager.get(Identifier.parse("adventurez:eyeland"));
        structure.get().placeInWorld((ServerLevel) this.level(), blockPos.west(5).north(5), blockPos,
                (new StructurePlaceSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(true), this.level().getRandom(), Block.UPDATE_CLIENTS);
    }

    static {
        INVUL_TIMER = SynchedEntityData.defineId(TheEyeEntity.class, EntityDataSerializers.INT);
        BEAM_TARGET_ID = SynchedEntityData.defineId(TheEyeEntity.class, EntityDataSerializers.INT);
    }

    private class FireBeamGoal extends Goal {
        private final TheEyeEntity theEye;
        private int beamTicks;

        public FireBeamGoal(TheEyeEntity theEye) {
            this.theEye = theEye;
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.theEye.getTarget();
            return livingEntity != null && livingEntity.isAlive();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.theEye.getTarget();
            if (this.theEye.gotDamage()) {
                return false;
            }
            return super.canContinueToUse() && this.theEye.distanceToSqr(this.theEye.getTarget()) > 8.0D && livingEntity != null && livingEntity.getY() < this.theEye.getY();
        }

        @Override
        public void start() {
            this.beamTicks = -20;
            this.theEye.getNavigation().stop();
            this.theEye.getLookControl().setLookAt(this.theEye.getTarget(), 90.0F, 90.0F);
            this.theEye.needsSync = true;
        }

        @Override
        public void stop() {
            this.theEye.setBeamTarget(0);
            this.theEye.setTarget(null);
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.theEye.getTarget();
            this.theEye.getLookControl().setLookAt(livingEntity, 90.0F, 90.0F);
            if (!this.theEye.hasLineOfSight(livingEntity)) {
                this.theEye.setTarget(null);
            } else {
                ++this.beamTicks;
                if (this.beamTicks == 0) {
                    this.theEye.setBeamTarget(this.theEye.getTarget().getId());
                } else if (this.beamTicks >= this.theEye.getWarmupTime()) {
                    float f = 4.0F;
                    if (this.theEye.level().getDifficulty() == Difficulty.HARD) {
                        f += 1.0F;
                    }
                    livingEntity.hurt(this.theEye.damageSources().magic(), f);
                    livingEntity.hurt(this.theEye.damageSources().mobAttack(this.theEye), (float) this.theEye.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.theEye.setTarget(null);
                }

                super.tick();
            }
        }
    }

    private class ShootBulletGoal extends Goal {
        private int counter;
        private final TheEyeEntity theEyeEntity;

        public ShootBulletGoal(TheEyeEntity theEyeEntity) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.theEyeEntity = theEyeEntity;
        }

        @Override
        public boolean canUse() {
            this.counter++;
            LivingEntity livingEntity = theEyeEntity.getTarget();
            if (livingEntity != null && livingEntity.isAlive() && theEyeEntity.getHealth() < theEyeEntity.getMaxHealth() / 2 && this.counter >= 400) {
                return theEyeEntity.level().getDifficulty() != Difficulty.PEACEFUL;
            } else {
                return false;
            }
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = theEyeEntity.getTarget();
            if (livingEntity != null) {
                int additions = 0;
                if (theEyeEntity.getHealth() < theEyeEntity.getMaxHealth() / 4) {
                    additions = 1;
                }
                for (int i = 0; i < 3 + additions; i++) {
                    theEyeEntity.level().addFreshEntity(new TinyEyeEntity(theEyeEntity.level(), theEyeEntity, livingEntity, Direction.Axis.Y));
                    theEyeEntity.playSound(SoundEvents.SHULKER_SHOOT, 2.0F, (theEyeEntity.random.nextFloat() - theEyeEntity.random.nextFloat()) * 0.2F + 1.0F);
                }
                super.tick();
                this.counter = theEyeEntity.random.nextInt(12) * 20;
            }
        }
    }

    private class FlyRandomlyGoal extends Goal {
        private final TheEyeEntity theEyeEntity;

        public FlyRandomlyGoal(TheEyeEntity theEyeEntity) {
            this.theEyeEntity = theEyeEntity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl moveControl = this.theEyeEntity.getMoveControl();
            if (!moveControl.hasWanted()) {
                return true;
            } else {
                double d = moveControl.getWantedX() - this.theEyeEntity.getX();
                double e = moveControl.getWantedY() - this.theEyeEntity.getY();
                double f = moveControl.getWantedZ() - this.theEyeEntity.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0D || g > 3600.0D;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            RandomSource random = this.theEyeEntity.getRandom();
            double d = this.theEyeEntity.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double e = this.theEyeEntity.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 1.2F);
            double f = this.theEyeEntity.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.theEyeEntity.getMoveControl().setWantedPosition(d, e, f, 1.0D);
        }
    }

    private class LookAtTargetGoal extends Goal {
        private final TheEyeEntity theEyeEntity;

        public LookAtTargetGoal(TheEyeEntity theEyeEntity) {
            this.theEyeEntity = theEyeEntity;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return theEyeEntity.getInvulnerableTimer() <= 0;
        }

        @Override
        public void tick() {
            if (this.theEyeEntity.getTarget() == null) {
                Vec3 vec3d = this.theEyeEntity.getDeltaMovement();
                this.theEyeEntity.setYRot(-((float) Mth.atan2(vec3d.x, vec3d.z)) * 57.295776F);
                this.theEyeEntity.yBodyRot = this.theEyeEntity.getYRot();
            } else {
                LivingEntity livingEntity = this.theEyeEntity.getTarget();
                if (livingEntity.distanceToSqr(this.theEyeEntity) < 4096.0D) {
                    double e = livingEntity.getX() - this.theEyeEntity.getX();
                    double f = livingEntity.getZ() - this.theEyeEntity.getZ();
                    if (Math.abs(theEyeEntity.getTarget().getX() - theEyeEntity.getX()) > 0.3D && Math.abs(theEyeEntity.getTarget().getZ() - theEyeEntity.getZ()) > 0.3D) {
                        this.theEyeEntity.setYRot(-((float) Mth.atan2(e, f)) * 57.295776F);
                        this.theEyeEntity.yBodyRot = this.theEyeEntity.getYRot();
                    }
                }
            }

        }
    }

    private class EyeMoveControl extends MoveControl {
        private final TheEyeEntity theEyeEntity;
        private int collisionCheckCooldown;

        public EyeMoveControl(TheEyeEntity theEyeEntity) {
            super(theEyeEntity);
            this.theEyeEntity = theEyeEntity;
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.collisionCheckCooldown-- <= 0) {
                    this.collisionCheckCooldown += this.theEyeEntity.getRandom().nextInt(5) + 2;
                    Vec3 vec3d = new Vec3(this.wantedX - this.theEyeEntity.getX(), this.wantedY - this.theEyeEntity.getY(), this.wantedZ - this.theEyeEntity.getZ());
                    double d = vec3d.length();
                    vec3d = vec3d.normalize();
                    if (theEyeEntity.getTarget() != null && Math.abs(theEyeEntity.getTarget().getX() - theEyeEntity.getX()) < 3.0D
                            && Math.abs(theEyeEntity.getTarget().getZ() - theEyeEntity.getZ()) < 3.0D) {
                        this.operation = MoveControl.Operation.WAIT;
                    } else if (this.willCollide(vec3d, Mth.ceil(d))) {
                        this.theEyeEntity.setDeltaMovement(this.theEyeEntity.getDeltaMovement().add(vec3d.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }

            }
        }

        private boolean willCollide(Vec3 direction, int steps) {
            AABB box = this.theEyeEntity.getBoundingBox();
            for (int i = 1; i < steps; ++i) {
                box = box.move(direction);
                if (!this.theEyeEntity.level().noCollision(this.theEyeEntity, box)) {
                    return false;
                }
            }
            return true;
        }
    }

    private class DuplicateAttack extends Goal {
        private int cooldown;
        private final TheEyeEntity theEyeEntity;

        public DuplicateAttack(TheEyeEntity theEyeEntity) {
            this.theEyeEntity = theEyeEntity;
        }

        @Override
        public boolean canUse() {
            this.cooldown++;
            LivingEntity livingEntity = theEyeEntity.getTarget();

            if (livingEntity != null && livingEntity.isAlive() && theEyeEntity.duplicationTimer <= 0 && this.cooldown >= 600) {
                List<TheEyeEntity> list = theEyeEntity.level().getEntitiesOfClass(TheEyeEntity.class, theEyeEntity.getBoundingBox().inflate(120D), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
                if (!list.isEmpty())
                    for (TheEyeEntity eyeEntity : list) {
                        if (eyeEntity.duplicationTimer > 0) {
                            this.cooldown = 0;
                            return false;
                        }
                    }
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void start() {
            LivingEntity livingEntity = theEyeEntity.getTarget();
            if (livingEntity != null) {
                for (int i = 0; i < 2; i++) {
                    for (int k = 0; k < 10; k++) {
                        BlockPos pos = theEyeEntity.blockPosition();
                        pos = pos.offset(pos.getX() - livingEntity.blockPosition().getX() + theEyeEntity.level().getRandom().nextInt(6) * 5, 0,
                                pos.getZ() - livingEntity.blockPosition().getZ() + theEyeEntity.level().getRandom().nextInt(6) * 5);
                        if (theEyeEntity.level().getBlockState(pos).isAir()
                                && SpawnPlacements.checkSpawnRules(EntityInit.THE_EYE, (ServerLevel) theEyeEntity.level(), EntitySpawnReason.EVENT, pos, theEyeEntity.level().getRandom())) {
                            TheEyeEntity theEyeEntityDuplicate = EntityInit.THE_EYE.create(theEyeEntity.level(), EntitySpawnReason.MOB_SUMMONED);
                            theEyeEntityDuplicate.snapTo(pos, theEyeEntity.level().getRandom().nextFloat() * 360F, 0.0F);
                            theEyeEntityDuplicate.finalizeSpawn((ServerLevel) theEyeEntity.level(), ((ServerLevel) theEyeEntity.level()).getCurrentDifficultyAt(pos), EntitySpawnReason.EVENT, null);
                            theEyeEntityDuplicate.duplicationTimer = 800;
                            theEyeEntityDuplicate.setLastHurtByMob(livingEntity);
                            theEyeEntityDuplicate.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100.0D);
                            theEyeEntity.level().addFreshEntity(theEyeEntityDuplicate);
                            for (int u = 0; u < 50; u++) {
                                double d = (double) theEyeEntityDuplicate.getX() - 1.5F + theEyeEntity.level().getRandom().nextFloat() * 3.0F;
                                double e = (double) ((float) theEyeEntityDuplicate.getRandomY() + theEyeEntity.level().getRandom().nextFloat() * 0.1F);
                                double f = (double) theEyeEntityDuplicate.getZ() - 1.5F + theEyeEntity.level().getRandom().nextFloat() * 3.0F;
                                double g = (double) (theEyeEntity.level().getRandom().nextFloat() * 0.2D);
                                double h = (double) theEyeEntity.level().getRandom().nextFloat() * 0.1D;
                                double l = (double) (theEyeEntity.level().getRandom().nextFloat() * 0.2D);
                                ((ServerLevel) theEyeEntity.level()).sendParticles(ParticleTypes.PORTAL, d, e, f, 4, g, h, l, 1.0D);
                            }
                            break;
                        }
                    }
                }
            }
            this.stop();

        }

        @Override
        public void stop() {
            this.cooldown = 0;
        }
    }

}
