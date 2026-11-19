package net.adventurez.entity;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.TagInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlackstoneGolemEntity extends Monster {

    public static final EntityDataAccessor<Integer> THROW_COOLDOWN;
    public static final EntityDataAccessor<Boolean> INVULNERABLE;
    public static final EntityDataAccessor<Integer> INVULNERABLE_TIMER;
    public static final EntityDataAccessor<Integer> LAVA_TEXTURE;
    public static final EntityDataAccessor<Boolean> HALF_LIFE_CHANGE;
    private static final ResourceLocation WALKING_SPEED_INCREASE_ID;
    private static final AttributeModifier WALKING_SPEED_INCREASE;
    private static final Predicate<Entity> NOT_STONEGOLEM = (entity) -> {
        return entity.isAlive() && !(entity instanceof BlackstoneGolemEntity);
    };
    private int cooldown = 0;
    private int thrownStoneCooldown = 120;
    private int attackTick;
    private int stunTick;
    private int roarTick;
    private int powerPhaseActivate = 0;
    private int lavaRegenerateLife = 0;

    private final ServerBossEvent bossBar;

    private static final boolean isDungeonZLoaded = FabricLoader.getInstance().isModLoaded("dungeonz");

    public BlackstoneGolemEntity(EntityType<? extends BlackstoneGolemEntity> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 200;
        this.bossBar = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
    }

    public static AttributeSupplier.Builder createStoneGolemAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 600.0D).add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.5D).add(Attributes.ATTACK_DAMAGE, 14.0D).add(Attributes.ATTACK_KNOCKBACK, 2.8D)
                .add(Attributes.FOLLOW_RANGE, 38.0D);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BlackstoneGolemEntity.AttackGoal());
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 60.0F));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));

    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackTick", this.attackTick);
        tag.putInt("StunTick", this.stunTick);
        tag.putInt("RoarTick", this.roarTick);
        tag.putInt("Invul", this.getInvulnerableTimer());
        tag.putInt("LavaTexture", this.getLavaTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
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
    public void setCustomName(Component name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(THROW_COOLDOWN, 0);
        builder.define(INVULNERABLE, true);
        builder.define(LAVA_TEXTURE, 400);
        builder.define(HALF_LIFE_CHANGE, false);
        builder.define(INVULNERABLE_TIMER, 0);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new BlackstoneGolemEntity.Navigation(this, world);
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public void customServerAiStep() {
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    protected float getSoundVolume() {
        return 1.3F;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isAlive()) {
            if (this.getInvulnerableTimer() > 0) {
                this.setInvulTimer(this.getInvulnerableTimer() - 1);
            }
            if (this.getInvulnerableTimer() == 0) {
                if (this.getLavaTexture() < 400) {
                    this.setLavaTexture(this.getLavaTexture() + 1);
                }
                if (entityData.get(INVULNERABLE)) {
                    entityData.set(INVULNERABLE, false);
                    this.playSound(SoundInit.BLACKSTONE_GOLEM_AWAKENS_EVENT, 2.0F, 1.0F);
                }
            }
            if (this.getHealth() <= this.getMaxHealth() / 2) {
                if (this.powerPhaseActivate <= 80) {
                    this.powerPhaseActivate++;
                    if (this.powerPhaseActivate == 1 || this.powerPhaseActivate == 2) {
                        this.setNoAi(true);
                    }
                    if (this.powerPhaseActivate == 78 || this.powerPhaseActivate == 79) {
                        this.setNoAi(false);
                        this.roar();
                    }
                }
                if (this.powerPhaseActivate == 80) {
                    this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                    this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(4.0D);
                    entityData.set(HALF_LIFE_CHANGE, true);
                }
            } else if (this.isInLava() && this.getHealth() < this.getMaxHealth()) {
                this.lavaRegenerateLife++;
                if (this.lavaRegenerateLife >= 200) {
                    this.setHealth(this.getHealth() + 2F);
                    this.lavaRegenerateLife = 0;
                }
            }
            if (this.horizontalCollision && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                boolean bl = false;
                AABB box = this.getBoundingBox().inflate(0.25D);
                Iterator<BlockPos> iterator = BlockPos.betweenClosed(Mth.floor(box.minX), Mth.floor(box.minY + 0.25D), Mth.floor(box.minZ), Mth.floor(box.maxX),
                        Mth.floor(box.maxY + 0.4D), Mth.floor(box.maxZ)).iterator();

                while (true) {
                    BlockPos blockPos = null;
                    Block block = null;
                    BlockState blockState = null;

                    if (!iterator.hasNext()) {
                        if (!bl && this.onGround()) {
                            this.jumpFromGround();
                        }
                        break;
                    }

                    blockPos = iterator.next();
                    blockState = this.level().getBlockState(blockPos);
                    block = blockState.getBlock();

                    if (block instanceof Block && !blockState.is(TagInit.UNBREAKABLE_BLOCKS)) {
                        if (isDungeonZLoaded && this.level().dimension().location().equals(ResourceLocation.fromNamespaceAndPath("dungeonz", "dungeon"))) {
                            break;
                        }
                        bl = this.level().destroyBlock(blockPos, false, this) || bl;
                    }
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
                    this.playSound(SoundInit.BLACKSTONE_GOLEM_ROAR_EVENT, 1.0F, 1.0F);
                    this.roarTick = 30;
                }
            }
            if (this.getTarget() != null && this.hasLineOfSight(this.getTarget())) {
                if (this.distanceToSqr(getTarget()) < 1400D && this.distanceToSqr(getTarget()) > 100D) {
                    entityData.set(THROW_COOLDOWN, cooldown);
                    this.cooldown++;
                    if (cooldown == thrownStoneCooldown - 10) {
                        this.playSound(SoundInit.BLACKSTONE_GOLEM_ROAR_EVENT, 1F, 1F);
                        throwRock(this.getTarget());
                    }
                    if (cooldown >= thrownStoneCooldown) {
                        this.cooldown = -80;
                    }
                }
            }
            if (this.isOnSoulSpeedBlock() && !this.getAttributes().hasModifier(Attributes.MOVEMENT_SPEED, WALKING_SPEED_INCREASE_ID)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(WALKING_SPEED_INCREASE);
            } else if (!this.isOnSoulSpeedBlock() && this.getAttributes().hasModifier(Attributes.MOVEMENT_SPEED, WALKING_SPEED_INCREASE_ID)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(WALKING_SPEED_INCREASE_ID);
            }
            if (this.isInLava()) {
                this.setDeltaMovement(this.getDeltaMovement().scale(1.9D));
            }
        }
    }

    private boolean isOnSoulSpeedBlock() {
        return this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.SOUL_SPEED_BLOCKS);
    }

    private void spawnStunnedParticles() {
        if (this.random.nextInt(6) == 0) {
            double d = this.getX() - (double) this.getBbWidth() * Math.sin((double) (this.yBodyRot * 0.017453292F)) + (this.random.nextDouble() * 0.6D - 0.3D);
            double e = this.getY() + (double) this.getBbHeight() - 0.3D;
            double f = this.getZ() + (double) this.getBbWidth() * Math.cos((double) (this.yBodyRot * 0.017453292F)) + (this.random.nextDouble() * 0.6D - 0.3D);
            this.level().addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 0.49803922F, 0.5137255F, 0.57254905F), d, e, f, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.attackTick > 0 || this.stunTick > 0 || this.roarTick > 0 || (this.cooldown > thrownStoneCooldown - 30 && this.cooldown > 0)
                || this.getEntityData().get(INVULNERABLE);
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return this.stunTick <= 0 && this.roarTick <= 0 ? super.hasLineOfSight(entity) : false;
    }

    @Override
    protected void blockedByShield(LivingEntity target) {
        if (this.roarTick == 0) {
            if (this.random.nextDouble() < 0.5D) {
                this.stunTick = 40;
                this.playSound(SoundInit.BLACKSTONE_GOLEM_IDLE_EVENT, 1.0F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 39);
                target.push(this);
            } else {
                this.knockBack(target);
            }

            target.hurtMarked = true;
        }

    }

    private void roar() {
        if (this.isAlive()) {
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.0D), NOT_STONEGOLEM);

            LivingEntity entity;
            for (Iterator<LivingEntity> var2 = list.iterator(); var2.hasNext(); this.knockBack(entity)) {
                entity = (LivingEntity) var2.next();
                entity.hurt(this.damageSources().mobAttack(this), 10.0F);
                entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, 0.63D, 0.0D));
            }

            Vec3 vec3d = this.getBoundingBox().getCenter();

            for (int i = 0; i < 50; ++i) {
                double d = this.random.nextGaussian() * 0.2D;
                double e = this.random.nextGaussian() * 0.2D;
                double f = this.random.nextGaussian() * 0.2D;
                this.level().addParticle(ParticleTypes.POOF, vec3d.x, vec3d.y, vec3d.z, d, e, f);
            }
        }

    }

    private void knockBack(Entity entity) {
        double d = entity.getX() - this.getX();
        double e = entity.getZ() - this.getZ();
        double f = Math.max(d * d + e * e, 0.001D);
        entity.push(d / f * 4.0D, 0.2D, e / f * 4.0D);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte status) {

        if (status == 4) {
            this.attackTick = 10;
            this.playSound(SoundInit.BLACKSTONE_GOLEM_HURT_EVENT, 1.0F, 1.0F);
        } else if (status == 39) {
            this.stunTick = 40;
        }

        super.handleEntityEvent(status);
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
    public boolean doHurtTarget(Entity target) {
        this.attackTick = 10;
        this.level().broadcastEntityEvent(this, (byte) 4);
        this.playSound(SoundInit.BLACKSTONE_GOLEM_HURT_EVENT, 1.0F, 1.0F);
        if (target instanceof LivingEntity)
            ((LivingEntity) target).igniteForSeconds(3 + this.level().getRandom().nextInt(6));
        return super.doHurtTarget(target);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getEntityData().get(INVULNERABLE)) {
            return null;
        } else
            return SoundInit.BLACKSTONE_GOLEM_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.BLACKSTONE_GOLEM_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.BLACKSTONE_GOLEM_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.BLACKSTONE_GOLEM_STEP_EVENT, 0.15F, 1.0F);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return !world.containsAnyLiquid(this.getBoundingBox());
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
        return super.canUsePortal(allowVehicles);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return super.hurt(source, amount);
        } else if (this.getEntityData().get(INVULNERABLE)) {
            return false;
        } else {
            if (source.getDirectEntity() instanceof LivingEntity attacker) {
                this.setLastHurtByMob(attacker);
                this.setTarget(attacker);
                if (this.level().getRandom().nextFloat() <= 0.05F) {
                    MiniBlackstoneGolemEntity smallStoneGolemEntity = EntityInit.MINI_BLACKSTONE_GOLEM.create(this.level());
                    smallStoneGolemEntity.moveTo(this.blockPosition(), this.level().getRandom().nextFloat() * 360F, 0.0F);
                    this.level().addFreshEntity(smallStoneGolemEntity);
                }
            }
            return super.hurt(source, amount);
        }
    }

    private void throwRock(LivingEntity target) {
        Vec3 vec3d_1 = this.getViewVector(1.0F);
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
        double y = target.getY(1D) - this.getY(0.1D);
        double z = target.getZ() - this.getZ() - z_vector;
        ThrownRockEntity thrownRockEntity = new ThrownRockEntity(this.level(), this.getX() + x_vector, this.getY() + 0.5D, this.getZ() + z_vector);
        thrownRockEntity.setOwner(this);
        if (!this.level().isClientSide()) {
            if (this.distanceToSqr(getTarget()) < 800D) {
                thrownRockEntity.shoot(x, y, z, 1.8F, 3.0F);
                this.level().addFreshEntity(thrownRockEntity);
            } else {
                thrownRockEntity.shoot(x, y + 4D, z, 1.8F, 2.0F);
                this.level().addFreshEntity(thrownRockEntity);
            }
        }
    }

    public void sendtoEntity() {
        this.setInvulTimer(220);
        this.setLavaTexture(0);
    }

    public int getInvulnerableTimer() {
        return (Integer) this.entityData.get(INVULNERABLE_TIMER);
    }

    public void setInvulTimer(int ticks) {
        this.entityData.set(INVULNERABLE_TIMER, ticks);
    }

    public int getLavaTexture() {
        return (Integer) this.entityData.get(LAVA_TEXTURE);
    }

    public void setLavaTexture(int ticks) {
        this.entityData.set(LAVA_TEXTURE, ticks);
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide()) {
            MiniBlackstoneGolemEntity smallStoneGolemEntity = (MiniBlackstoneGolemEntity) EntityInit.MINI_BLACKSTONE_GOLEM.create(this.level());
            MiniBlackstoneGolemEntity smallStoneGolemEntitySecond = (MiniBlackstoneGolemEntity) EntityInit.MINI_BLACKSTONE_GOLEM.create(this.level());
            smallStoneGolemEntity.moveTo(this.blockPosition().east(), this.level().getRandom().nextFloat() * 360F, 0.0F);
            smallStoneGolemEntitySecond.moveTo(this.blockPosition().west(), this.level().getRandom().nextFloat() * 360F, 0.0F);
            this.level().addFreshEntity(smallStoneGolemEntity);
            this.level().addFreshEntity(smallStoneGolemEntitySecond);
        }
        super.die(source);
    }

    private static class PathNodeMaker extends WalkNodeEvaluator {
        private PathNodeMaker() {
        }

        // @Override
        // protected PathNodeType adjustNodeType(BlockView world, BlockPos pos, PathNodeType type) {
        // return type == PathNodeType.LAVA ? PathNodeType.OPEN : super.adjustNodeType(world, pos, type);
        // }
    }

    private static class Navigation extends GroundPathNavigation {
        public Navigation(Mob mobEntity, Level world) {
            super(mobEntity, world);
        }

        @Override
        protected boolean hasValidPathType(PathType pathType) {
            return pathType != PathType.LAVA && pathType != PathType.DAMAGE_FIRE && pathType != PathType.DANGER_FIRE ? super.hasValidPathType(pathType) : true;
        }

        @Override
        public boolean isStableDestination(BlockPos pos) {
            return this.level.getBlockState(pos).is(Blocks.LAVA) || super.isStableDestination(pos);
        }

        @Override
        protected PathFinder createPathFinder(int range) {
            this.nodeEvaluator = new BlackstoneGolemEntity.PathNodeMaker();
            return new PathFinder(this.nodeEvaluator, range);
        }
    }

    private class AttackGoal extends MeleeAttackGoal {
        public AttackGoal() {
            super(BlackstoneGolemEntity.this, 1.0D, true);
        }

    }

    static {
        THROW_COOLDOWN = SynchedEntityData.defineId(BlackstoneGolemEntity.class, EntityDataSerializers.INT);
        INVULNERABLE = SynchedEntityData.defineId(BlackstoneGolemEntity.class, EntityDataSerializers.BOOLEAN);
        INVULNERABLE_TIMER = SynchedEntityData.defineId(BlackstoneGolemEntity.class, EntityDataSerializers.INT);
        LAVA_TEXTURE = SynchedEntityData.defineId(BlackstoneGolemEntity.class, EntityDataSerializers.INT);
        HALF_LIFE_CHANGE = SynchedEntityData.defineId(BlackstoneGolemEntity.class, EntityDataSerializers.BOOLEAN);
        WALKING_SPEED_INCREASE_ID = ResourceLocation.parse("adventurez:walking_speed_increase");
        WALKING_SPEED_INCREASE = new AttributeModifier(WALKING_SPEED_INCREASE_ID, 0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }
}
