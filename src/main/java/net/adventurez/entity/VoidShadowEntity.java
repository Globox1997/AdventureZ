package net.adventurez.entity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.google.common.collect.Lists;

import org.jetbrains.annotations.Nullable;

import net.adventurez.block.ShadowChest;
import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.adventurez.entity.nonliving.VoidCloudEntity;
import net.adventurez.init.EffectInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.TagInit;
import net.adventurez.network.packet.VelocityPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class VoidShadowEntity extends Mob implements Enemy {
    private static final ResourceKey<Level> VOIDZ_VOID_WORLD = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath("voidz", "void"));

    public static final EntityDataAccessor<Boolean> HALF_LIFE_CHANGE;
    public static final EntityDataAccessor<Boolean> IS_THROWING_BLOCKS;
    public static final EntityDataAccessor<Boolean> HOVERING_MAGIC_HANDS;
    public static final EntityDataAccessor<Boolean> CIRCLING_HANDS;

    private List<BlockPos> blockPosList = new ArrayList<BlockPos>();
    private final ServerBossEvent bossBar;
    private boolean circling;
    private boolean wasCircling;
    private int portalX;
    private int portalY;
    private int portalZ;
    private boolean isHalfLife;
    private final boolean isInVoidDungeon;
    private boolean invisible;
    public int ticksSinceDeath;
    public float throwingBlocksTicker;

    public VoidShadowEntity(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
        this.bossBar = (ServerBossEvent) (new ServerBossEvent(this.getUUID(), this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS));
        this.xpReward = 100;
        this.moveControl = new VoidShadowEntity.VoidShadowMoveControl(this);
        this.isInVoidDungeon = FabricLoader.getInstance().isModLoaded("voidz") && world.dimension().equals(VOIDZ_VOID_WORLD);
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

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new VoidShadowEntity.ThrowBlocks(this));
        this.goalSelector.addGoal(1, new VoidShadowEntity.DestroyBlocksAttack(this));
        this.goalSelector.addGoal(2, new VoidShadowEntity.SummonFragment(this));
        this.goalSelector.addGoal(3, new VoidShadowEntity.SummonShade(this));
        this.goalSelector.addGoal(4, new VoidShadowEntity.Insanity(this));
        this.goalSelector.addGoal(6, new VoidShadowEntity.FlyGoal(this));
        this.goalSelector.addGoal(5, new VoidShadowEntity.LookGoal(this));
    }

    public static AttributeSupplier.Builder createVoidShadowAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 1000.0D).add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.ATTACK_KNOCKBACK, 2.0D).add(Attributes.ARMOR, 1.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0D).add(Attributes.FOLLOW_RANGE, 80.0D);
    }

    @Override
    public void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        if (this.getHealth() < this.getMaxHealth() / 2) {
            if (!entityData.get(HALF_LIFE_CHANGE)) {
                entityData.set(HALF_LIFE_CHANGE, true);
            }
            if (!this.isHalfLife) {
                this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue((double) (6.0D));
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double) 14.0D);
                this.getAttribute(Attributes.ARMOR).setBaseValue((double) 4.0D);
                this.isHalfLife = true;
            }
            if (this.getHealth() < this.getMaxHealth() / 8 && !this.invisible) {
                this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 400, 0, false, false, false));
                this.invisible = true;
            }
        }
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HALF_LIFE_CHANGE, false);
        builder.define(IS_THROWING_BLOCKS, false);
        builder.define(HOVERING_MAGIC_HANDS, false);
        builder.define(CIRCLING_HANDS, false);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("VoidXPortal", portalX);
        tag.putInt("VoidYPortal", portalY);
        tag.putInt("VoidZPortal", portalZ);
        tag.putBoolean("ShadowCircling", this.circling);
        tag.putBoolean("ShadowIsHalfLife", this.isHalfLife);
        tag.putIntArray("ShadowBrokenBlocks", this.blockListTransform(this.blockPosList).stream().mapToInt(Integer::intValue).toArray());
        tag.putBoolean("ShadowInvisible", this.invisible);
        tag.putBoolean("ShadowWasCircling", this.wasCircling);

    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
        this.portalX = tag.getIntOr("VoidXPortal", 0);
        this.portalY = tag.getIntOr("VoidYPortal", 0);
        this.portalZ = tag.getIntOr("VoidZPortal", 0);
        this.circling = tag.getBooleanOr("ShadowCircling", false);
        this.isHalfLife = tag.getBooleanOr("ShadowIsHalfLife", false);
        this.setBlockList(tag.getIntArray("ShadowBrokenBlocks").orElse(new int[0]));
        this.invisible = tag.getBooleanOr("ShadowInvisible", false);
        this.wasCircling = tag.getBooleanOr("ShadowWasCircling", false);
        if (!this.circling && this.wasCircling) {
            this.circling = true;
        }
    }

    private static Block voidzBlock(String name) {
        return BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("voidz", name));
    }

    private static BlockState activatedVoidStone(BlockState state) {
        for (Property<?> property : state.getProperties()) {
            if (property instanceof BooleanProperty booleanProperty && property.getName().equals("activated_void")) {
                return state.setValue(booleanProperty, true);
            }
        }
        return state;
    }

    private List<Integer> blockListTransform(List<BlockPos> oldBlockPosList) {
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < oldBlockPosList.size(); i++) {
            list.add(oldBlockPosList.get(i).getX());
            list.add(oldBlockPosList.get(i).getY());
            list.add(oldBlockPosList.get(i).getZ());
        }
        return list;
    }

    private void setBlockList(int[] integers) {
        if (integers.length != 0) {
            for (int i = 0; i < integers.length; i++) {
                if (i % 3 == 0) {
                    this.blockPosList.add(i / 3, new BlockPos(integers[i], integers[i + 1], integers[i + 2]));
                }
            }
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        if (!this.hasVoidMiddleCoordinates() && FabricLoader.getInstance().isModLoaded("voidz")) {
            // For test purpose, use spawn egg on portal block
            if (world.getBlockState(this.blockPosition().below()).getBlock() == voidzBlock("void_portal")) {
                this.setVoidMiddle(this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ());
            }
        }

        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossBar.removePlayer(player);
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
    public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean addEffect(MobEffectInstance effect, Entity entity) {
        return false;
    }

    @Override
    public boolean canRide(Entity entity) {
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

    private boolean hasVoidMiddleCoordinates() {
        return this.portalX != 0 || this.portalY != 0 || this.portalZ != 0;
    }

    // Set at VoidZ mod
    // Platform at y = 99, voidmiddle = 100
    public void setVoidMiddle(int x, int y, int z) {
        this.circling = true;
        this.portalX = x;
        this.portalY = y;
        this.portalZ = z;
    }

    public BlockPos getVoidMiddle() {
        return new BlockPos(this.portalX, this.portalY, this.portalZ);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity attacker && !(source.getEntity() instanceof VoidFragmentEntity)) {
            if (this.circling) {
                this.setTarget(attacker);
            }
            amount *= 0.5F;
            attacker.knockback(4.0D, this.getX() - source.getEntity().getX(), this.getZ() - source.getEntity().getZ(), source, 0.0F);
            attacker.hurt(this.damageSources().magic(), 1f);
        }
        if (source.getDirectEntity() instanceof ThrownRockEntity) {
            return false;
        }
        if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            if (source.getDirectEntity() instanceof Arrow arrowEntity) {
                if (arrowEntity.isCurrentlyGlowing()) {
                    return true;
                }
                source.getDirectEntity().discard();
                return false;
            }

        }
        return !this.isInvulnerableTo(level, source) && super.hurtServer(level, source, amount);
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide()) {
            if (!this.blockPosList.isEmpty()) {
                for (int u = 0; u < this.blockPosList.size(); ++u) {
                    this.level().setBlockAndUpdate(this.blockPosList.get(u), voidzBlock("void_stone").defaultBlockState());
                    this.level().playSound(null, this.blockPosList.get(u), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1F, 1F);
                }
            }
        }
        super.die(source);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.VOID_SHADOW_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.VOID_SHADOW_DEATH_EVENT;
    }

    @Override
    protected float getSoundVolume() {
        return 5.0F;
    }

    private void deathParticles(ParticleOptions particleEffect) {
        float f = (this.random.nextFloat() - 0.5F) * 16.0F;
        float g = (this.random.nextFloat() - 0.5F) * 10.0F;
        float h = (this.random.nextFloat() - 0.5F) * 16.0F;
        this.level().addParticle(particleEffect, this.getX() + (double) f, this.getY() + 2.0D + (double) g, this.getZ() + (double) h, 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void tickDeath() {
        ++this.ticksSinceDeath;
        this.setNoAi(true);
        this.setTarget(null);
        if (this.level().isClientSide()) {
            if (this.ticksSinceDeath >= 0 && this.ticksSinceDeath < 40) {
                for (int i = 0; i < 10; i++) {
                    deathParticles(ParticleTypes.PORTAL);
                }
            }

            if (this.ticksSinceDeath >= 41 && this.ticksSinceDeath <= 200) {
                deathParticles(ParticleTypes.FALLING_OBSIDIAN_TEAR);
            }
        }
        if (!this.level().isClientSide()) {
            this.bossBar.setProgress(0.0F);
            if (this.ticksSinceDeath == 1 && !this.isSilent()) {
                this.level().globalLevelEvent(1028, this.blockPosition(), 0);
            }
            if (this.hasVoidMiddleCoordinates() && this.ticksSinceDeath == 40) {
                this.randomTeleport(this.getVoidMiddle().getX(), this.getVoidMiddle().above(5).getY(), this.getVoidMiddle().getZ(), false, state -> false);
            }
        }

        this.move(MoverType.SELF, new Vec3(0.0D, 0.05D, 0.0D));
        if (this.ticksSinceDeath >= 198) {
            for (int i = 0; i < 100; i++) {
                deathParticles(ParticleTypes.SMOKE);
            }
        }
        if (this.ticksSinceDeath >= 200 && !this.level().isClientSide()) {
            AABB box = new AABB(this.blockPosition());
            List<Player> list = this.level().getEntitiesOfClass(Player.class, box.inflate(128D), EntitySelector.NO_SPECTATORS);
            for (int i = 0; i < list.size(); ++i) {
                list.get(i).addEffect(new MobEffectInstance(EffectInit.FAME, 48000, 0, false, false, true));
            }
            if (this.isInVoidDungeon) {
                boolean direction = this.level().getRandom().nextInt(2) == 0;

                BlockPos pos = direction ? this.getVoidMiddle().north(3) : this.getVoidMiddle().south(3);
                BlockState state = net.adventurez.init.BlockInit.SHADOW_CHEST.defaultBlockState().setValue(ShadowChest.FACING, direction ? Direction.SOUTH : Direction.NORTH);
                this.level().setBlock(pos, state, 3);
                state.getBlock().setPlacedBy(this.level(), pos, state, null, ItemStack.EMPTY);

                BlockEntity portalBlockEntity = this.level().getBlockEntity(this.getVoidMiddle().below());
                if (portalBlockEntity != null && portalBlockEntity.getClass().getName().equals("net.voidz.block.entity.PortalBlockEntity")) {
                    try {
                        portalBlockEntity.getClass().getField("bossTime").setInt(portalBlockEntity, (int) this.level().getLevelData().getGameTime());
                    } catch (ReflectiveOperationException exception) {
                    }
                }
            } else {
                this.level().setBlock(this.blockPosition(), Blocks.DRAGON_EGG.defaultBlockState(), 3);
            }
            this.discard();
        }
    }

    static {
        HALF_LIFE_CHANGE = SynchedEntityData.defineId(VoidShadowEntity.class, EntityDataSerializers.BOOLEAN);
        IS_THROWING_BLOCKS = SynchedEntityData.defineId(VoidShadowEntity.class, EntityDataSerializers.BOOLEAN);
        HOVERING_MAGIC_HANDS = SynchedEntityData.defineId(VoidShadowEntity.class, EntityDataSerializers.BOOLEAN);
        CIRCLING_HANDS = SynchedEntityData.defineId(VoidShadowEntity.class, EntityDataSerializers.BOOLEAN);
    }

    static class FlyGoal extends Goal {
        private final VoidShadowEntity voidShadowEntity;

        public FlyGoal(VoidShadowEntity voidShadowEntity) {
            this.voidShadowEntity = voidShadowEntity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            MoveControl moveControl = this.voidShadowEntity.getMoveControl();
            if ((!moveControl.hasWanted() || this.voidShadowEntity.getTarget() != null) && !voidShadowEntity.circling) {
                return true;
            } else {
                double d = moveControl.getWantedX() - this.voidShadowEntity.getX();
                double e = moveControl.getWantedY() - this.voidShadowEntity.getY();
                double f = moveControl.getWantedZ() - this.voidShadowEntity.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0D || g > 3600.0D;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            if (this.voidShadowEntity.getTarget() != null && this.voidShadowEntity.distanceTo(this.voidShadowEntity.getTarget()) > 30.0F) {
                BlockPos pos = this.voidShadowEntity.getTarget().blockPosition();
                this.voidShadowEntity.getMoveControl().setWantedPosition(pos.getX(), pos.getY(), pos.getZ(), 0.01D);
            }
        }
    }

    static class VoidShadowMoveControl extends MoveControl {
        private final VoidShadowEntity voidShadowEntity;

        public VoidShadowMoveControl(VoidShadowEntity voidShadowEntity) {
            super(voidShadowEntity);
            this.voidShadowEntity = voidShadowEntity;
        }

        @Override
        public void tick() {
            if (voidShadowEntity.circling && voidShadowEntity.hasVoidMiddleCoordinates()) {
                BlockPos pos = voidShadowEntity.getVoidMiddle();
                Vec3 vec3d = new Vec3((double) pos.getX() - this.voidShadowEntity.getX(), (double) pos.getY() - this.voidShadowEntity.getY(), (double) pos.getZ() - this.voidShadowEntity.getZ());
                vec3d = vec3d.normalize();
                Vec3 distanceVector = new Vec3((double) this.voidShadowEntity.getX(), this.voidShadowEntity.getY(), this.voidShadowEntity.getZ());
                if (distanceVector.distanceTo(new Vec3(pos.getX(), pos.getY(), pos.getZ())) >= 50D) {
                    this.voidShadowEntity.setDeltaMovement(this.voidShadowEntity.getDeltaMovement().add(vec3d.scale(0.1D)));
                } else {
                    this.voidShadowEntity.setDeltaMovement(this.voidShadowEntity.getDeltaMovement().add(vec3d.scale(0.1D).scale(-1.0D)));
                    // Max partly Vector is 0.21 ca
                }

                this.voidShadowEntity.setDeltaMovement(this.voidShadowEntity.getDeltaMovement().add(vec3d.scale(0.1D).yRot(90F)));
                if (this.voidShadowEntity.getDeltaMovement().length() <= 0.3D) {
                    this.voidShadowEntity.push(0.1D, 0.1D, 0.1D);
                }
            } else if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vec3d = new Vec3(this.wantedX - this.voidShadowEntity.getX(), this.wantedY - this.voidShadowEntity.getY(), this.wantedZ - this.voidShadowEntity.getZ());
                vec3d = vec3d.normalize();
                this.voidShadowEntity.setDeltaMovement(this.voidShadowEntity.getDeltaMovement().add(vec3d.scale(0.1D)));
            }
        }

    }

    static class LookGoal extends Goal {
        private final VoidShadowEntity voidShadowEntity;
        private static final TargetingConditions PLAYER_PREDICATE = TargetingConditions.forCombat().range(80.0D).ignoreLineOfSight();

        public LookGoal(VoidShadowEntity voidShadowEntity) {
            this.voidShadowEntity = voidShadowEntity;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            if (voidShadowEntity.circling && voidShadowEntity.hasVoidMiddleCoordinates()) {
                BlockPos pos = voidShadowEntity.getVoidMiddle();
                Vec3 vec3d = new Vec3((double) pos.getX() - this.voidShadowEntity.getX(), (double) pos.getY() - this.voidShadowEntity.getY(), (double) pos.getZ() - this.voidShadowEntity.getZ());
                this.voidShadowEntity.setYRot(-((float) Mth.atan2(vec3d.x, vec3d.z)) * 57.295776F);
                this.voidShadowEntity.yBodyRot = this.voidShadowEntity.getYRot();
                this.voidShadowEntity.setTarget(getServerLevel(this.voidShadowEntity).getNearestPlayer(PLAYER_PREDICATE, this.voidShadowEntity));
            } else if (this.voidShadowEntity.getTarget() == null) {
                Vec3 vec3d = this.voidShadowEntity.getDeltaMovement();
                this.voidShadowEntity.setYRot(-((float) Mth.atan2(vec3d.x, vec3d.z)) * 57.295776F);
                this.voidShadowEntity.yBodyRot = this.voidShadowEntity.getYRot();
            } else {
                LivingEntity livingEntity = this.voidShadowEntity.getTarget();
                if (livingEntity.distanceToSqr(this.voidShadowEntity) < 4096.0D) {
                    double e = livingEntity.getX() - this.voidShadowEntity.getX();
                    double f = livingEntity.getZ() - this.voidShadowEntity.getZ();
                    this.voidShadowEntity.setYRot(-((float) Mth.atan2(e, f)) * 57.295776F);
                    this.voidShadowEntity.yBodyRot = this.voidShadowEntity.getYRot();
                }
            }

        }
    }

    static class ThrowBlocks extends Goal {
        private final VoidShadowEntity voidShadow;
        private int throwTicker;
        private int throwBlocks;

        public ThrowBlocks(VoidShadowEntity voidShadow) {
            this.voidShadow = voidShadow;
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            // distance 1F per block :)
            if (livingEntity != null && this.voidShadow.circling && this.voidShadow.distanceTo(livingEntity) > 5.0F && this.voidShadow.distanceTo(livingEntity) < 30.0F) {
                throwTicker++;
                return throwTicker >= 80;
            } else
                return false;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (livingEntity != null) {
                this.voidShadow.lookControl.setLookAt(livingEntity, 1.0F, 1.0F);

                throwBlocks++;
                if (throwBlocks >= 10) {
                    BlockPos pos = this.voidShadow.blockPosition();
                    for (int i = 0; i < (this.voidShadow.isHalfLife ? 60 : 40); i++) {
                        if (!this.voidShadow.level().isClientSide()) {
                            double random = this.voidShadow.level().getRandom().nextDouble() + 0.3D;
                            double anotherRandom = this.voidShadow.level().getRandom().nextDouble();
                            double anotherExtraRandom = this.voidShadow.level().getRandom().nextDouble() - 0.5D;
                            double anotherExtraXXXRandom = this.voidShadow.level().getRandom().nextDouble() - 0.5D;
                            Block block;
                            if (this.voidShadow.hasVoidMiddleCoordinates()) {
                                block = this.voidShadow.level().getBlockState(this.voidShadow.getVoidMiddle().north().below()).getBlock();
                            } else if (!this.voidShadow.level().getBlockState(pos.below(5)).isAir()) {
                                block = this.voidShadow.level().getBlockState(pos.below(5)).getBlock();
                            } else {
                                block = Blocks.STONE;
                            }
                            ThrownRockEntity thrownRockEntity = new ThrownRockEntity(this.voidShadow.level(), this.voidShadow);
                            Vec3 vec3d = new Vec3(livingEntity.getX() - this.voidShadow.getX(), livingEntity.getY() - this.voidShadow.getY(), livingEntity.getZ() - this.voidShadow.getZ());
                            vec3d = vec3d.add(anotherExtraRandom * 12.0D, 5.0D * anotherRandom, anotherExtraXXXRandom * 12.0D);
                            vec3d = vec3d.normalize();
                            thrownRockEntity.setDeltaMovement(thrownRockEntity.getDeltaMovement().add(vec3d.scale(1.4D * random)));
                            thrownRockEntity.setItem(new ItemStack(block.asItem()));
                            this.voidShadow.level().addFreshEntity(thrownRockEntity);

                        }
                    }
                    this.voidShadow.level().playSound((Player) null, this.voidShadow, SoundInit.ROCK_THROW_EVENT, SoundSource.HOSTILE, 1.0F, 1.0F);
                    throwBlocks = 0;
                    this.stop();
                }
            }

        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (livingEntity == null || !livingEntity.isAlive()) {
                return false;
            } else
                return this.voidShadow.distanceTo(livingEntity) > 5.0F && this.voidShadow.distanceTo(livingEntity) < 30.0F && throwTicker == 0;
        }

        @Override
        public void start() {
            this.voidShadow.entityData.set(IS_THROWING_BLOCKS, true);
            this.voidShadow.getNavigation().stop();
            throwTicker = 0;
        }

        @Override
        public void stop() {
            this.voidShadow.entityData.set(IS_THROWING_BLOCKS, false);
            throwTicker++;
        }

    }

    // Goals run only on the server
    static class DestroyBlocksAttack extends Goal {
        private final VoidShadowEntity voidShadow;
        private int canStartTicker;
        private int destroyBlocksTicker;
        private static final TargetingConditions PLAYER_PREDICATE = TargetingConditions.forCombat().range(128.0D).ignoreLineOfSight();
        private List<BlockPos> playerBlockPosList = new ArrayList<BlockPos>();

        public DestroyBlocksAttack(VoidShadowEntity voidShadow) {
            this.voidShadow = voidShadow;
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (livingEntity != null && this.voidShadow.isInVoidDungeon) {
                canStartTicker++;

                return canStartTicker >= 200;
            } else
                return false;
        }

        @Override
        public void tick() {
            destroyBlocksTicker++;
            if (destroyBlocksTicker == 40 && this.voidShadow.getTarget() != null) {
                AABB box = new AABB(this.voidShadow.blockPosition());
                List<Player> playerList = getServerLevel(this.voidShadow).getNearbyPlayers(PLAYER_PREDICATE, this.voidShadow, box.inflate(120D));
                for (int i = 0; i < playerList.size(); ++i) {
                    Player playerEntity = playerList.get(i);
                    if (!playerEntity.isCreative() && !playerEntity.isSpectator()) {
                        if (!this.voidShadow.level().getBlockState(playerEntity.blockPosition().below()).isAir()) {
                            playerBlockPosList.add(playerEntity.blockPosition().below());
                        } else if (!this.voidShadow.level().getBlockState(playerEntity.blockPosition().below(2)).isAir()) {
                            playerBlockPosList.add(playerEntity.blockPosition().below(2));
                        }
                    }
                }
                for (int u = 0; u < playerBlockPosList.size(); ++u) {
                    BlockPos pos = playerBlockPosList.get(u);
                    int radius = this.voidShadow.isHalfLife ? 5 : 3;
                    for (int k = -radius; k <= radius; k++) {
                        for (int i = -radius; i <= radius; i++) {
                            BlockPos blockPos = pos.offset(k, 0, i).offset(Mth.floor(Math.sin(k)) * i, 0, Mth.floor(Math.cos(k)) * i);
                            if (!this.voidShadow.blockPosList.contains(blockPos) && !this.voidShadow.level().getBlockState(blockPos).isAir()
                                    && !this.voidShadow.level().getBlockState(blockPos).is(TagInit.UNBREAKABLE_BLOCKS)) {
                                this.voidShadow.blockPosList.add(blockPos);
                            }
                        }
                    }
                }
                for (int u = 0; u < this.voidShadow.blockPosList.size(); ++u) {
                    this.voidShadow.level().setBlockAndUpdate(this.voidShadow.blockPosList.get(u), activatedVoidStone(voidzBlock("void_stone").defaultBlockState()));
                }

            }
            if (destroyBlocksTicker == 110) {
                ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_CAST_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
            }
            if (destroyBlocksTicker == 120) {
                this.voidShadow.entityData.set(HOVERING_MAGIC_HANDS, false);
            }
            if (destroyBlocksTicker >= 400) {
                destroyBlocksTicker = 0;
                this.stop();
            }

        }

        @Override
        public boolean canContinueToUse() {
            return canStartTicker == 0;
        }

        @Override
        public void start() {
            this.voidShadow.entityData.set(HOVERING_MAGIC_HANDS, true);
            canStartTicker = 0;
            ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_PREPARE_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
        }

        @Override
        public void stop() {
            canStartTicker++;
            for (int u = 0; u < this.voidShadow.blockPosList.size(); ++u) {
                this.voidShadow.level().setBlockAndUpdate(this.voidShadow.blockPosList.get(u), voidzBlock("void_stone").defaultBlockState());
                this.voidShadow.level().playSound(null, this.voidShadow.blockPosList.get(u), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1F, 1F);
            }
            if (!this.voidShadow.blockPosList.isEmpty()) {
                this.voidShadow.blockPosList.clear();
            }
            if (!playerBlockPosList.isEmpty()) {
                playerBlockPosList.clear();
            }
        }

    }

    static class SummonFragment extends Goal {
        private final VoidShadowEntity voidShadow;
        private int summonStartTicker;
        private int summonTick;

        public SummonFragment(VoidShadowEntity voidShadow) {
            this.voidShadow = voidShadow;
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (voidShadow.isInVoidDungeon && voidShadow.circling && voidShadow.hasVoidMiddleCoordinates() && livingEntity != null) {
                summonStartTicker++;
                if (summonStartTicker >= 200) {

                    AABB box = new AABB(voidShadow.blockPosition());
                    List<VoidFragmentEntity> list = voidShadow.level().getEntitiesOfClass(VoidFragmentEntity.class, box.inflate(120D), EntitySelector.NO_SPECTATORS);
                    if (list.isEmpty() || list.size() < 2) {
                        return true;
                    }
                    summonStartTicker = 0;
                }

                return false;
            } else
                return false;
        }

        @Override
        public void tick() {
            summonTick++;
            if (summonTick >= 40 && this.voidShadow.getTarget() != null) {
                BlockPos pos = this.voidShadow.getVoidMiddle();
                Boolean isOrb = this.voidShadow.random.nextFloat() < 0.35F;
                for (int i = 0; i < (isOrb ? 4 : 10); i++) {
                    if (!this.voidShadow.level().isClientSide()) {
                        BlockPos spawnPos;
                        if (isOrb) {
                            if (i < 2) {
                                spawnPos = new BlockPos(pos.getX() + 28 * (i - 1), pos.getY(), pos.getZ() + 28 * i);
                            } else {
                                spawnPos = new BlockPos(pos.getX() + 28 * (-i + 3), pos.getY(), pos.getZ() + 28 * (-i + 2));
                            }
                        } else {
                            spawnPos = new BlockPos(pos.getX() - 35 + voidShadow.random.nextInt(70), pos.getY(), pos.getZ() - 35 + voidShadow.random.nextInt(70));
                        }
                        if (!this.voidShadow.level().getBlockState(spawnPos.below()).isAir()) {
                            VoidFragmentEntity voidFragmentEntity = (VoidFragmentEntity) EntityInit.VOID_FRAGMENT.create(voidShadow.level(), EntitySpawnReason.MOB_SUMMONED);
                            voidFragmentEntity.finalizeSpawn((ServerLevel) voidShadow.level(), ((ServerLevel) voidShadow.level()).getCurrentDifficultyAt(pos), EntitySpawnReason.EVENT, null);
                            voidFragmentEntity.setVoidOrb(isOrb);
                            voidFragmentEntity.snapTo(spawnPos, voidShadow.level().getRandom().nextFloat() * 360F, 0.0F);
                            voidShadow.level().addFreshEntity(voidFragmentEntity);
                            if (!isOrb) {
                                VoidCloudEntity voidCloudEntity = new VoidCloudEntity(voidShadow.level(), voidFragmentEntity.getX(), voidFragmentEntity.getY(), voidFragmentEntity.getZ());
                                int random = voidShadow.level().getRandom().nextInt(7) + 6;
                                voidCloudEntity.setRadius(random);
                                voidCloudEntity.setDuration(random * 140);
                                voidShadow.level().addFreshEntity(voidCloudEntity);
                            }
                        }
                    }
                }
                summonTick = 0;
                this.stop();
            }

        }

        @Override
        public void start() {
            this.voidShadow.entityData.set(HOVERING_MAGIC_HANDS, true);
            ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_PREPARE_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
        }

        @Override
        public void stop() {
            this.voidShadow.entityData.set(HOVERING_MAGIC_HANDS, false);
            ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_CAST_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
        }

    }

    static class Insanity extends Goal {
        private final VoidShadowEntity voidShadow;
        private int insanityStartTicker;
        private int tick;
        private static final TargetingConditions PLAYER_PREDICATE = TargetingConditions.forCombat().range(80.0D).ignoreLineOfSight();
        private List<Player> playerList;

        public Insanity(VoidShadowEntity voidShadow) {
            this.voidShadow = voidShadow;
        }

        @Override
        public boolean canUse() {
            if (voidShadow.isInVoidDungeon && voidShadow.circling && this.voidShadow.isHalfLife && voidShadow.hasVoidMiddleCoordinates() && this.voidShadow.blockPosList.isEmpty()) {
                insanityStartTicker++;
                if (insanityStartTicker >= 600) {
                    return true;
                }
                return false;
            } else
                return false;
        }

        @Override
        public void tick() {
            tick++;
            this.voidShadow.setTarget(null);
            if (tick == 60) {
                AABB box = new AABB(this.voidShadow.blockPosition());
                playerList = getServerLevel(this.voidShadow).getNearbyPlayers(PLAYER_PREDICATE, this.voidShadow, box.inflate(120D));
                for (int i = 0; i < playerList.size(); ++i) {
                    Player playerEntity = playerList.get(i);
                    playerEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60 + this.voidShadow.random.nextInt(80), this.voidShadow.random.nextInt(4), false, false, true));
                    playerEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40 + this.voidShadow.random.nextInt(60), 0, false, false, true));
                }
            }
            if (tick == 100) {
                int radius = 8;
                BlockPos pos = this.voidShadow.getVoidMiddle().below();
                for (int k = -radius; k <= radius; k++) {
                    for (int i = -radius; i <= radius; i++) {
                        BlockPos blockPos = pos.offset(k, 0, i);

                        if (this.voidShadow.random.nextFloat() < 0.4F) {
                            if (!this.voidShadow.blockPosList.contains(blockPos) && !this.voidShadow.level().getBlockState(blockPos).isAir()
                                    && !this.voidShadow.level().getBlockState(blockPos).is(TagInit.UNBREAKABLE_BLOCKS)) {
                                this.voidShadow.blockPosList.add(blockPos);
                            }
                        }
                    }
                }
                for (int u = 0; u < this.voidShadow.blockPosList.size(); ++u) {
                    this.voidShadow.level().setBlockAndUpdate(this.voidShadow.blockPosList.get(u), voidzBlock("infested_void_stone").defaultBlockState());
                }
                ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_CAST_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
            }
            if (tick >= 300) {
                if (this.voidShadow.getHealth() < this.voidShadow.getMaxHealth() / 10 && !playerList.isEmpty()) {
                    for (int i = 0; i < playerList.size(); i++) {
                        if (playerList.get(i) instanceof ServerPlayer serverPlayerEntity) {
                            ServerPlayNetworking.send(serverPlayerEntity, new VelocityPacket(playerList.get(i).getId(), this.voidShadow.random.nextFloat() * 2F));
                        }
                    }
                    ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_IDLE_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
                }
                tick = 0;
                this.stop();
            }

        }

        @Override
        public boolean canContinueToUse() {
            return insanityStartTicker >= 600;
        }

        @Override
        public void start() {
            voidShadow.circling = false;
            voidShadow.wasCircling = true;
            this.voidShadow.moveControl.setWantedPosition((double) voidShadow.getVoidMiddle().getX(), (double) voidShadow.getVoidMiddle().getY(), (double) voidShadow.getVoidMiddle().getZ(), 1.0D);
            this.voidShadow.push(0D, 0.2D, 0D);
            this.voidShadow.entityData.set(CIRCLING_HANDS, true);
            ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_PREPARE_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
        }

        @Override
        public void stop() {
            voidShadow.circling = true;
            voidShadow.wasCircling = false;
            this.voidShadow.entityData.set(CIRCLING_HANDS, false);
            ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_CAST_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
            insanityStartTicker = 0;
            for (int u = 0; u < this.voidShadow.blockPosList.size(); ++u) {
                this.voidShadow.level().setBlockAndUpdate(this.voidShadow.blockPosList.get(u), voidzBlock("void_stone").defaultBlockState());
            }
            if (!this.playerList.isEmpty()) {
                this.playerList.clear();
            }
            if (!this.voidShadow.blockPosList.isEmpty()) {
                this.voidShadow.blockPosList.clear();
            }
        }

    }

    static class SummonShade extends Goal {
        private final VoidShadowEntity voidShadow;
        private int summonStartTicker;
        private int summonTick;

        public SummonShade(VoidShadowEntity voidShadow) {
            this.voidShadow = voidShadow;
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (voidShadow.isInVoidDungeon && voidShadow.hasVoidMiddleCoordinates() && livingEntity != null) {
                summonStartTicker++;
                if (summonStartTicker >= 80) {
                    AABB box = new AABB(voidShadow.blockPosition());
                    List<VoidShadeEntity> list = voidShadow.level().getEntitiesOfClass(VoidShadeEntity.class, box.inflate(120D), EntitySelector.NO_SPECTATORS);
                    if (list.size() < 6) {
                        return true;
                    }
                    summonStartTicker = 0;
                }
                return false;
            } else
                return false;
        }

        @Override
        public void tick() {
            summonTick++;
            if (summonTick >= 40 && this.voidShadow.getTarget() != null) {
                BlockPos pos = this.voidShadow.getVoidMiddle();
                for (int i = 0; i < (this.voidShadow.isHalfLife ? 24 : 16); i++) {
                    if (!this.voidShadow.level().isClientSide()) {
                        BlockPos spawnPos = new BlockPos(pos.getX() - 35 + voidShadow.random.nextInt(70), pos.getY(), pos.getZ() - 35 + voidShadow.random.nextInt(70));
                        VoidShadeEntity voidShadeEntity = (VoidShadeEntity) EntityInit.VOID_SHADE.create(voidShadow.level(), EntitySpawnReason.MOB_SUMMONED);
                        voidShadeEntity.finalizeSpawn((ServerLevel) voidShadow.level(), ((ServerLevel) voidShadow.level()).getCurrentDifficultyAt(pos), EntitySpawnReason.EVENT, null);
                        voidShadeEntity.snapTo(spawnPos, voidShadow.level().getRandom().nextFloat() * 360F, 0.0F);
                        voidShadow.level().addFreshEntity(voidShadeEntity);
                    }
                }
                summonTick = 0;
                this.stop();
            }

        }

        @Override
        public void start() {
            this.voidShadow.entityData.set(HOVERING_MAGIC_HANDS, true);
            ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_PREPARE_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
        }

        @Override
        public void stop() {
            this.voidShadow.entityData.set(HOVERING_MAGIC_HANDS, false);
            ((ServerLevel) this.voidShadow.level()).playSound(null, this.voidShadow, SoundInit.VOID_SHADOW_CAST_EVENT, SoundSource.HOSTILE, 20.0F, 1.0F);
        }

    }
}
