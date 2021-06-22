package net.adventurez.entity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.google.common.collect.Lists;

import org.jetbrains.annotations.Nullable;

import io.netty.buffer.Unpooled;
import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.adventurez.init.EffectInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.TagInit;
import net.adventurez.network.GeneralPacket;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.voidz.block.VoidBlock;
import net.voidz.init.BlockInit;
import net.voidz.init.DimensionInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;

// X Invisible
// X Set blocks to infested blocks
// X Remove blocks
// X Freeze player
// X Throw bunch of blocks
// - Send itself on small variants with low hp
// - Gravity off/on or set volicity y -2
// X Blinding
// - Spawn above players falling blocks
// X No elytra
// - Spawn mirror ghost, throw chunks of end stone
// X Spawn many small shadowentities which shoot void projectiles

public class VoidShadowEntity extends FlyingEntity implements Monster {

    public static final TrackedData<Boolean> HALF_LIFE_CHANGE;
    public static final TrackedData<Boolean> IS_THROWING_BLOCKS;
    public static final TrackedData<Boolean> HOVERING_MAGIC_HANDS;
    public static final TrackedData<Boolean> CIRCLING_HANDS;

    private List<BlockPos> blockPosList = new ArrayList<BlockPos>();
    private final ServerBossBar bossBar;
    private boolean circling;
    private boolean wasCircling;
    private int portalX;
    private int portalY;
    private int portalZ;
    private boolean isHalfLife;
    private final boolean isInVoidDungeon;
    private boolean invisible;
    private int ticksSinceDeath;

    public VoidShadowEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = (ServerBossBar) (new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS));
        this.experiencePoints = 100;
        this.moveControl = new VoidShadowEntity.VoidShadowMoveControl(this);
        this.isInVoidDungeon = FabricLoader.getInstance().isModLoaded("voidz") && world.getRegistryKey() == DimensionInit.VOID_WORLD;
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new VoidShadowEntity.ThrowBlocks(this));
        this.goalSelector.add(1, new VoidShadowEntity.DestroyBlocksAttack(this));
        this.goalSelector.add(2, new VoidShadowEntity.SummonFragment(this));
        this.goalSelector.add(3, new VoidShadowEntity.SummonShade(this));
        this.goalSelector.add(4, new VoidShadowEntity.Insanity(this));
        this.goalSelector.add(6, new VoidShadowEntity.FlyGoal(this));
        this.goalSelector.add(5, new VoidShadowEntity.LookGoal(this));
    }

    public static DefaultAttributeContainer.Builder createVoidShadowAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1000.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.0D).add(EntityAttributes.GENERIC_ARMOR, 1.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10.0D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 80.0D);
    }

    @Override
    public void mobTick() {
        super.mobTick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        if (this.getHealth() < this.getMaxHealth() / 2) {
            if (!dataTracker.get(HALF_LIFE_CHANGE)) {
                dataTracker.set(HALF_LIFE_CHANGE, true);
            }
            if (!this.isHalfLife) {
                this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).setBaseValue((double) (6.0D));
                this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) 14.0D);
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue((double) 4.0D);
                this.isHalfLife = true;
            }
            if (this.getHealth() < this.getMaxHealth() / 8 && !this.invisible) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 400, 0, false, false, false));
                this.invisible = true;
            }
        }
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(HALF_LIFE_CHANGE, false);
        dataTracker.startTracking(IS_THROWING_BLOCKS, false);
        dataTracker.startTracking(HOVERING_MAGIC_HANDS, false);
        dataTracker.startTracking(CIRCLING_HANDS, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putInt("VoidXPortal", portalX);
        tag.putInt("VoidYPortal", portalY);
        tag.putInt("VoidZPortal", portalZ);
        tag.putBoolean("ShadowCircling", this.circling);
        tag.putBoolean("ShadowIsHalfLife", this.isHalfLife);
        tag.putIntArray("ShadowBrokenBlocks", this.blockListTransform(this.blockPosList));
        tag.putBoolean("ShadowInvisible", this.invisible);
        tag.putBoolean("ShadowWasCircling", this.wasCircling);

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
        this.portalX = tag.getInt("VoidXPortal");
        this.portalY = tag.getInt("VoidYPortal");
        this.portalZ = tag.getInt("VoidZPortal");
        this.circling = tag.getBoolean("ShadowCircling");
        this.isHalfLife = tag.getBoolean("ShadowIsHalfLife");
        this.setBlockList(tag.getIntArray("ShadowBrokenBlocks"));
        this.invisible = tag.getBoolean("ShadowInvisible");
        this.wasCircling = tag.getBoolean("ShadowWasCircling");
        if (!this.circling && this.wasCircling) {
            this.circling = true;
        }
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
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        if (!this.hasVoidMiddleCoordinates() && FabricLoader.getInstance().isModLoaded("voidz")) {
            // For test purpose, use spawn egg on portal block
            if (world.getBlockState(this.getBlockPos().down()).getBlock() == BlockInit.PORTAL_BLOCK) {
                this.setVoidMiddle(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ());
            }
        }

        return super.initialize(world, difficulty, spawnReason, (EntityData) entityData, entityTag);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
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
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    private boolean hasVoidMiddleCoordinates() {
        return this.portalX != 0 || this.portalY != 0 || this.portalZ != 0;
    }

    // Set at VoidZ mod
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
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof LivingEntity) {
            if (this.circling) {
                this.setTarget((LivingEntity) source.getAttacker());
            }
            amount *= 0.5F;
        }
        if (source.getSource() instanceof ThrownRockEntity) {
            return false;
        }
        if (source.isProjectile()) {
            if (source.getSource() instanceof ArrowEntity) {
                ArrowEntity arrowEntity = (ArrowEntity) source.getSource();
                if (arrowEntity.isGlowing()) {
                    return true;
                }
                source.getSource().discard();
                return false;
            }

        }
        return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
    }

    @Override
    public void onDeath(DamageSource source) {
        if (!this.world.isClient) {
            if (!this.blockPosList.isEmpty()) {
                for (int u = 0; u < this.blockPosList.size(); ++u) {
                    this.world.setBlockState(this.blockPosList.get(u), BlockInit.VOID_BLOCK.getDefaultState());
                    this.world.playSound(null, this.blockPosList.get(u), SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1F, 1F);
                }
            }
        }
        super.onDeath(source);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.SHADOW_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.SHADOW_DEATH_EVENT;
    }

    // @Override
    // protected SoundEvent getHurtSound(DamageSource source) {
    // return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
    // }

    @Override
    protected float getSoundVolume() {
        return 5.0F;
    }

    private void deathParticles(ParticleEffect particleEffect) {
        float f = (this.random.nextFloat() - 0.5F) * 16.0F;
        float g = (this.random.nextFloat() - 0.5F) * 10.0F;
        float h = (this.random.nextFloat() - 0.5F) * 16.0F;
        this.world.addParticle(particleEffect, this.getX() + (double) f, this.getY() + 2.0D + (double) g, this.getZ() + (double) h, 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void updatePostDeath() {
        ++this.ticksSinceDeath;
        this.setAiDisabled(true);
        this.setTarget(null);
        if (this.world.isClient) {
            if (this.ticksSinceDeath >= 0 && this.ticksSinceDeath < 40) {
                for (int i = 0; i < 10; i++) {
                    deathParticles(ParticleTypes.PORTAL);
                }
            }

            if (this.ticksSinceDeath >= 41 && this.ticksSinceDeath <= 200) {
                deathParticles(ParticleTypes.FALLING_OBSIDIAN_TEAR);
            }
        }
        if (!this.world.isClient) {
            this.bossBar.setPercent(0.0F);
            if (this.ticksSinceDeath == 1 && !this.isSilent()) {
                this.world.syncGlobalEvent(1028, this.getBlockPos(), 0);
            }
            if (this.hasVoidMiddleCoordinates() && this.ticksSinceDeath == 40) {
                this.teleport(this.getVoidMiddle().getX(), this.getVoidMiddle().up(5).getY(), this.getVoidMiddle().getZ());
            }
        }

        this.move(MovementType.SELF, new Vec3d(0.0D, 0.05D, 0.0D));
        if (this.ticksSinceDeath >= 198) {
            for (int i = 0; i < 100; i++) {
                deathParticles(ParticleTypes.SMOKE);
            }
        }
        if (this.ticksSinceDeath >= 200 && !this.world.isClient) {
            if (this.isInVoidDungeon) {
                Box box = new Box(this.getBlockPos());
                List<PlayerEntity> list = world.getEntitiesByClass(PlayerEntity.class, box.expand(128D), EntityPredicates.EXCEPT_SPECTATOR);
                for (int i = 0; i < list.size(); ++i) {
                    list.get(i).addStatusEffect(new StatusEffectInstance(EffectInit.FAME, 48000, 0, false, false, true));
                }
            }
            this.discard();
        }
    }

    static {
        HALF_LIFE_CHANGE = DataTracker.registerData(VoidShadowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        IS_THROWING_BLOCKS = DataTracker.registerData(VoidShadowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        HOVERING_MAGIC_HANDS = DataTracker.registerData(VoidShadowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        CIRCLING_HANDS = DataTracker.registerData(VoidShadowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    static class FlyGoal extends Goal {
        private final VoidShadowEntity voidShadowEntity;

        public FlyGoal(VoidShadowEntity voidShadowEntity) {
            this.voidShadowEntity = voidShadowEntity;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        public boolean canStart() {
            MoveControl moveControl = this.voidShadowEntity.getMoveControl();
            if ((!moveControl.isMoving() || this.voidShadowEntity.getTarget() != null) && !voidShadowEntity.circling) {
                return true;
            } else {
                double d = moveControl.getTargetX() - this.voidShadowEntity.getX();
                double e = moveControl.getTargetY() - this.voidShadowEntity.getY();
                double f = moveControl.getTargetZ() - this.voidShadowEntity.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0D || g > 3600.0D;
            }
        }

        public boolean shouldContinue() {
            return false;
        }

        public void start() {
            if (this.voidShadowEntity.getTarget() != null && this.voidShadowEntity.distanceTo(this.voidShadowEntity.getTarget()) > 30.0F) {
                BlockPos pos = this.voidShadowEntity.getTarget().getBlockPos();
                this.voidShadowEntity.getMoveControl().moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.01D);
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
                Vec3d vec3d = new Vec3d((double) pos.getX() - this.voidShadowEntity.getX(), (double) pos.getY() - this.voidShadowEntity.getY(), (double) pos.getZ() - this.voidShadowEntity.getZ());
                vec3d = vec3d.normalize();
                Vec3d distanceVector = new Vec3d((double) this.voidShadowEntity.getX(), this.voidShadowEntity.getY(), this.voidShadowEntity.getZ());
                if (distanceVector.distanceTo(new Vec3d(pos.getX(), pos.getY(), pos.getZ())) >= 50D) {
                    this.voidShadowEntity.setVelocity(this.voidShadowEntity.getVelocity().add(vec3d.multiply(0.1D)));
                } else {
                    this.voidShadowEntity.setVelocity(this.voidShadowEntity.getVelocity().add(vec3d.multiply(0.1D).multiply(-1.0D)));
                    // Max partly Vector is 0.21 ca
                }

                this.voidShadowEntity.setVelocity(this.voidShadowEntity.getVelocity().add(vec3d.multiply(0.1D).rotateY(90F)));
                if (this.voidShadowEntity.getVelocity().length() <= 0.3D) {
                    this.voidShadowEntity.addVelocity(0.1D, 0.1D, 0.1D);
                }
            } else if (this.state == MoveControl.State.MOVE_TO) {
                Vec3d vec3d = new Vec3d(this.targetX - this.voidShadowEntity.getX(), this.targetY - this.voidShadowEntity.getY(), this.targetZ - this.voidShadowEntity.getZ());
                vec3d = vec3d.normalize();
                this.voidShadowEntity.setVelocity(this.voidShadowEntity.getVelocity().add(vec3d.multiply(0.1D)));
            }
        }

    }

    static class LookGoal extends Goal {
        private final VoidShadowEntity voidShadowEntity;
        private static final TargetPredicate PLAYER_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(80.0D).ignoreVisibility();

        public LookGoal(VoidShadowEntity voidShadowEntity) {
            this.voidShadowEntity = voidShadowEntity;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void tick() {
            if (voidShadowEntity.circling && voidShadowEntity.hasVoidMiddleCoordinates()) {
                BlockPos pos = voidShadowEntity.getVoidMiddle();
                Vec3d vec3d = new Vec3d((double) pos.getX() - this.voidShadowEntity.getX(), (double) pos.getY() - this.voidShadowEntity.getY(), (double) pos.getZ() - this.voidShadowEntity.getZ());
                this.voidShadowEntity.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
                this.voidShadowEntity.bodyYaw = this.voidShadowEntity.getYaw();
                this.voidShadowEntity.setTarget(this.voidShadowEntity.world.getClosestPlayer(PLAYER_PREDICATE, this.voidShadowEntity));
            } else if (this.voidShadowEntity.getTarget() == null) {
                Vec3d vec3d = this.voidShadowEntity.getVelocity();
                this.voidShadowEntity.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
                this.voidShadowEntity.bodyYaw = this.voidShadowEntity.getYaw();
            } else {
                LivingEntity livingEntity = this.voidShadowEntity.getTarget();
                if (livingEntity.squaredDistanceTo(this.voidShadowEntity) < 4096.0D) {
                    double e = livingEntity.getX() - this.voidShadowEntity.getX();
                    double f = livingEntity.getZ() - this.voidShadowEntity.getZ();
                    this.voidShadowEntity.setYaw(-((float) MathHelper.atan2(e, f)) * 57.295776F);
                    this.voidShadowEntity.bodyYaw = this.voidShadowEntity.getYaw();
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
        public boolean canStart() {
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
                this.voidShadow.lookControl.lookAt(livingEntity, 1.0F, 1.0F);

                throwBlocks++;
                if (throwBlocks >= 10) {
                    BlockPos pos = this.voidShadow.getBlockPos();
                    for (int i = 0; i < (this.voidShadow.isHalfLife ? 60 : 40); i++) {
                        if (!this.voidShadow.world.isClient) {
                            double random = this.voidShadow.world.random.nextDouble() + 0.3D;
                            double anotherRandom = this.voidShadow.world.random.nextDouble();
                            double anotherExtraRandom = this.voidShadow.world.random.nextDouble() - 0.5D;
                            double anotherExtraXXXRandom = this.voidShadow.world.random.nextDouble() - 0.5D;
                            Block block;
                            if (this.voidShadow.hasVoidMiddleCoordinates()) {
                                block = this.voidShadow.world.getBlockState(this.voidShadow.getVoidMiddle().north().down()).getBlock();
                            } else if (!this.voidShadow.world.getBlockState(pos.down(5)).isAir()) {
                                block = this.voidShadow.world.getBlockState(pos.down(5)).getBlock();
                            } else {
                                block = Blocks.STONE;
                            }
                            ThrownRockEntity thrownRockEntity = new ThrownRockEntity(this.voidShadow.world, this.voidShadow);
                            Vec3d vec3d = new Vec3d(livingEntity.getX() - this.voidShadow.getX(), livingEntity.getY() - this.voidShadow.getY(), livingEntity.getZ() - this.voidShadow.getZ());
                            vec3d = vec3d.add(anotherExtraRandom * 12.0D, 5.0D * anotherRandom, anotherExtraXXXRandom * 12.0D);
                            vec3d = vec3d.normalize();
                            thrownRockEntity.setVelocity(thrownRockEntity.getVelocity().add(vec3d.multiply(1.4D * random)));
                            thrownRockEntity.setItem(new ItemStack(block.asItem()));
                            this.voidShadow.world.spawnEntity(thrownRockEntity);

                        }
                    }
                    this.voidShadow.world.playSoundFromEntity((PlayerEntity) null, this.voidShadow, SoundInit.ROCK_THROW_EVENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
                    throwBlocks = 0;
                    this.stop();
                }
            }

        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (livingEntity == null || !livingEntity.isAlive()) {
                return false;
            } else
                return this.voidShadow.distanceTo(livingEntity) > 5.0F && this.voidShadow.distanceTo(livingEntity) < 30.0F && throwTicker == 0;
        }

        @Override
        public void start() {
            this.voidShadow.dataTracker.set(IS_THROWING_BLOCKS, true);
            this.voidShadow.getNavigation().stop();
            throwTicker = 0;
        }

        @Override
        public void stop() {
            this.voidShadow.dataTracker.set(IS_THROWING_BLOCKS, false);
            throwTicker++;
        }

    }

    // Goals run only on the server
    static class DestroyBlocksAttack extends Goal {
        private final VoidShadowEntity voidShadow;
        private int canStartTicker;
        private int destroyBlocksTicker;
        private static final TargetPredicate PLAYER_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(128.0D).ignoreVisibility();
        private List<BlockPos> playerBlockPosList = new ArrayList<BlockPos>();

        public DestroyBlocksAttack(VoidShadowEntity voidShadow) {
            this.voidShadow = voidShadow;
        }

        @Override
        public boolean canStart() {
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
                Box box = new Box(this.voidShadow.getBlockPos());
                List<PlayerEntity> playerList = this.voidShadow.world.getPlayers(PLAYER_PREDICATE, this.voidShadow, box.expand(120D));
                for (int i = 0; i < playerList.size(); ++i) {
                    PlayerEntity playerEntity = playerList.get(i);
                    if (!playerEntity.isCreative() && !playerEntity.isSpectator()) {
                        if (!this.voidShadow.world.getBlockState(playerEntity.getBlockPos().down()).isAir()) {
                            playerBlockPosList.add(playerEntity.getBlockPos().down());
                        } else if (!this.voidShadow.world.getBlockState(playerEntity.getBlockPos().down(2)).isAir()) {
                            playerBlockPosList.add(playerEntity.getBlockPos().down(2));
                        }
                    }
                }
                for (int u = 0; u < playerBlockPosList.size(); ++u) {
                    BlockPos pos = playerBlockPosList.get(u);
                    int radius = this.voidShadow.isHalfLife ? 5 : 3;
                    for (int k = -radius; k <= radius; k++) {
                        for (int i = -radius; i <= radius; i++) {
                            BlockPos blockPos = pos.add(k, 0, i).add(Math.sin(k) * i, 0, Math.cos(k) * i);
                            if (!this.voidShadow.blockPosList.contains(blockPos) && !this.voidShadow.world.getBlockState(blockPos).isAir()
                                    && !this.voidShadow.world.getBlockState(blockPos).isIn(TagInit.UNBREAKABLE_BLOCKS)) {
                                this.voidShadow.blockPosList.add(blockPos);
                            }
                        }
                    }
                }
                for (int u = 0; u < this.voidShadow.blockPosList.size(); ++u) {
                    this.voidShadow.world.setBlockState(this.voidShadow.blockPosList.get(u), BlockInit.VOID_BLOCK.getDefaultState().with(VoidBlock.ACTIVATED, true));
                }

            }
            if (destroyBlocksTicker == 110) {
                ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_CAST_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
            }
            if (destroyBlocksTicker == 120) {
                this.voidShadow.dataTracker.set(HOVERING_MAGIC_HANDS, false);
            }
            if (destroyBlocksTicker >= 400) {
                destroyBlocksTicker = 0;
                this.stop();
            }

        }

        @Override
        public boolean shouldContinue() {
            return canStartTicker == 0;
        }

        @Override
        public void start() {
            this.voidShadow.dataTracker.set(HOVERING_MAGIC_HANDS, true);
            canStartTicker = 0;
            ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_PREPARE_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
        }

        @Override
        public void stop() {
            canStartTicker++;
            for (int u = 0; u < this.voidShadow.blockPosList.size(); ++u) {
                this.voidShadow.world.setBlockState(this.voidShadow.blockPosList.get(u), BlockInit.VOID_BLOCK.getDefaultState());
                this.voidShadow.world.playSound(null, this.voidShadow.blockPosList.get(u), SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1F, 1F);
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
        public boolean canStart() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (voidShadow.isInVoidDungeon && voidShadow.circling && voidShadow.hasVoidMiddleCoordinates() && livingEntity != null) {
                summonStartTicker++;
                if (summonStartTicker >= 200) {

                    Box box = new Box(voidShadow.getBlockPos());
                    List<VoidFragmentEntity> list = voidShadow.world.getEntitiesByClass(VoidFragmentEntity.class, box.expand(120D), EntityPredicates.EXCEPT_SPECTATOR);
                    if (list.isEmpty()) {
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
                Boolean isOrb = this.voidShadow.random.nextFloat() < 0.5F;
                for (int i = 0; i < (isOrb ? 4 : 10); i++) {
                    if (!this.voidShadow.world.isClient) {
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
                        VoidFragmentEntity voidShadeEntity = (VoidFragmentEntity) EntityInit.VOID_FRAGMENT_ENTITY.create(voidShadow.world);
                        voidShadeEntity.initialize((ServerWorld) voidShadow.world, voidShadow.world.getLocalDifficulty(pos), SpawnReason.EVENT, null, null);
                        voidShadeEntity.setVoidOrb(isOrb);
                        voidShadeEntity.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
                        voidShadow.world.spawnEntity(voidShadeEntity);
                    }
                }
                summonTick = 0;
                this.stop();
            }

        }

        @Override
        public void start() {
            this.voidShadow.dataTracker.set(HOVERING_MAGIC_HANDS, true);
            ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_PREPARE_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
        }

        @Override
        public void stop() {
            this.voidShadow.dataTracker.set(HOVERING_MAGIC_HANDS, false);
            ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_CAST_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
        }

    }

    static class Insanity extends Goal {
        private final VoidShadowEntity voidShadow;
        private int insanityStartTicker;
        private int tick;
        private static final TargetPredicate PLAYER_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(80.0D).ignoreVisibility();
        private List<PlayerEntity> playerList;

        public Insanity(VoidShadowEntity voidShadow) {
            this.voidShadow = voidShadow;
        }

        @Override
        public boolean canStart() {
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
                Box box = new Box(this.voidShadow.getBlockPos());
                playerList = this.voidShadow.world.getPlayers(PLAYER_PREDICATE, this.voidShadow, box.expand(120D));
                for (int i = 0; i < playerList.size(); ++i) {
                    PlayerEntity playerEntity = playerList.get(i);
                    playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60 + this.voidShadow.random.nextInt(80), this.voidShadow.random.nextInt(4), false, false, true));
                    playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 40 + this.voidShadow.random.nextInt(60), 0, false, false, true));
                }
            }
            if (tick == 100) {
                int radius = 8;
                BlockPos pos = this.voidShadow.getVoidMiddle().down();
                for (int k = -radius; k <= radius; k++) {
                    for (int i = -radius; i <= radius; i++) {
                        BlockPos blockPos = pos.add(k, 0, i);

                        if (this.voidShadow.random.nextFloat() < 0.4F) {
                            if (!this.voidShadow.blockPosList.contains(blockPos) && !this.voidShadow.world.getBlockState(blockPos).isAir()
                                    && !this.voidShadow.world.getBlockState(blockPos).isIn(TagInit.UNBREAKABLE_BLOCKS)) {
                                this.voidShadow.blockPosList.add(blockPos);
                            }
                        }
                    }
                }
                for (int u = 0; u < this.voidShadow.blockPosList.size(); ++u) {
                    this.voidShadow.world.setBlockState(this.voidShadow.blockPosList.get(u), BlockInit.INFESTED_VOID_BLOCK.getDefaultState());
                }
                ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_CAST_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
            }
            if (tick >= 300) {
                if (this.voidShadow.getHealth() < this.voidShadow.getMaxHealth() / 10 && !playerList.isEmpty()) {
                    for (int i = 0; i < playerList.size(); i++) {
                        if (playerList.get(i) instanceof ServerPlayerEntity) {
                            CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(GeneralPacket.VELOCITY_PACKET, new PacketByteBuf(Unpooled.buffer()));
                            ((ServerPlayerEntity) playerList.get(i)).networkHandler.sendPacket(packet);
                        }
                    }
                    ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_IDLE_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
                }
                tick = 0;
                this.stop();
            }

        }

        @Override
        public boolean shouldContinue() {
            return insanityStartTicker >= 600;
        }

        @Override
        public void start() {
            voidShadow.circling = false;
            voidShadow.wasCircling = true;
            this.voidShadow.moveControl.moveTo((double) voidShadow.getVoidMiddle().getX(), (double) voidShadow.getVoidMiddle().getY(), (double) voidShadow.getVoidMiddle().getZ(), 1.0D);
            this.voidShadow.addVelocity(0D, 0.2D, 0D);
            this.voidShadow.dataTracker.set(CIRCLING_HANDS, true);
            ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_PREPARE_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
        }

        @Override
        public void stop() {
            voidShadow.circling = true;
            voidShadow.wasCircling = false;
            this.voidShadow.dataTracker.set(CIRCLING_HANDS, false);
            ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_CAST_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
            insanityStartTicker = 0;
            for (int u = 0; u < this.voidShadow.blockPosList.size(); ++u) {
                this.voidShadow.world.setBlockState(this.voidShadow.blockPosList.get(u), BlockInit.VOID_BLOCK.getDefaultState());
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
        public boolean canStart() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (voidShadow.isInVoidDungeon && voidShadow.hasVoidMiddleCoordinates() && livingEntity != null) {
                summonStartTicker++;
                if (summonStartTicker >= 80) {
                    Box box = new Box(voidShadow.getBlockPos());
                    List<VoidShadeEntity> list = voidShadow.world.getEntitiesByClass(VoidShadeEntity.class, box.expand(120D), EntityPredicates.EXCEPT_SPECTATOR);
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
                    if (!this.voidShadow.world.isClient) {
                        BlockPos spawnPos = new BlockPos(pos.getX() - 35 + voidShadow.random.nextInt(70), pos.getY(), pos.getZ() - 35 + voidShadow.random.nextInt(70));
                        VoidShadeEntity voidShadeEntity = (VoidShadeEntity) EntityInit.VOID_SHADE_ENTITY.create(voidShadow.world);
                        voidShadeEntity.initialize((ServerWorld) voidShadow.world, voidShadow.world.getLocalDifficulty(pos), SpawnReason.EVENT, null, null);
                        voidShadeEntity.refreshPositionAndAngles(spawnPos, voidShadow.world.random.nextFloat() * 360F, 0.0F);
                        voidShadow.world.spawnEntity(voidShadeEntity);
                    }
                }
                summonTick = 0;
                this.stop();
            }

        }

        @Override
        public void start() {
            this.voidShadow.dataTracker.set(HOVERING_MAGIC_HANDS, true);
            ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_PREPARE_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
        }

        @Override
        public void stop() {
            this.voidShadow.dataTracker.set(HOVERING_MAGIC_HANDS, false);
            ((ServerWorld) this.voidShadow.world).playSoundFromEntity(null, this.voidShadow, SoundInit.SHADOW_CAST_EVENT, SoundCategory.HOSTILE, 20.0F, 1.0F);
        }

    }
}
