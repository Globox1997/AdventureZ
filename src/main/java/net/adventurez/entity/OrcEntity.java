package net.adventurez.entity;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.adventurez.entity.goal.OrcAttackGoal;
import net.adventurez.entity.goal.OrcGroupGoal;
import net.adventurez.entity.goal.WanderAroundVeryFarGoal;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class OrcEntity extends HostileEntity {
    public static final TrackedData<Integer> ORK_SIZE = DataTracker.registerData(OrcEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> DOUBLE_HAND_ATTACK = DataTracker.registerData(OrcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public OrcEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createOrkAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D).add(EntityAttributes.GENERIC_ARMOR, 1.0D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new OrcAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new OrcGroupGoal(this));
        this.goalSelector.add(4, new WanderAroundVeryFarGoal(this, 1.0D, 0.3F));
        this.goalSelector.add(5, new WanderAroundGoal(this, 0.9D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { OrcEntity.class })));
        this.targetSelector.add(3, new FollowTargetGoal<>(this, WanderingTraderEntity.class, true));
        this.targetSelector.add(4, new FollowTargetGoal<>(this, VillagerEntity.class, true));
    }

    public static boolean canSpawn(EntityType<OrcEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean bl = (world.getDifficulty() != Difficulty.PEACEFUL && canSpawnInDark(type, world, spawnReason, pos, random) && world.isSkyVisible(pos)) || spawnReason == SpawnReason.SPAWNER;
        return bl;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ORK_SIZE, 1);
        this.dataTracker.startTracking(DOUBLE_HAND_ATTACK, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        this.setSize(tag.getInt("OrkSize"), false);
        super.readCustomDataFromNbt(tag);

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putInt("OrkSize", this.getSize());

    }

    public int getSize() {
        return (Integer) this.dataTracker.get(ORK_SIZE);
    }

    public void setSize(int size, boolean healOrc) {
        this.dataTracker.set(ORK_SIZE, size);
        this.refreshPosition();
        this.calculateDimensions();
        if (healOrc) {
            this.setHealth(this.getMaxHealth());
        }
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (ORK_SIZE.equals(data)) {
            this.calculateDimensions();
            this.setYaw(this.headYaw);
            this.bodyYaw = this.headYaw;
        }

        super.onTrackedDataSet(data);
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F * (float) this.getSize();
    }

    @Override
    public float getSoundPitch() {
        return 1.6F - ((float) 0.2F * this.getSize());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.ORC_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ORC_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ORC_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.ORC_STEP_EVENT, 1.0F, 1.0F);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.82F * dimensions.height;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        int random = this.random.nextInt(3) + 1;
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double) (16D + random * 6D));
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue((double) (0.26F - 0.012F * (float) random));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) random * 3D + 2D);
        this.setSize(random, true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(0.55F * (float) this.getSize());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.world.random.nextInt(3) == 0 && !this.dataTracker.get(DOUBLE_HAND_ATTACK)) {
            dataTracker.set(DOUBLE_HAND_ATTACK, true);
        } else {
            dataTracker.set(DOUBLE_HAND_ATTACK, false);
        }
        return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
    }

    @Override
    public int getLimitPerChunk() {
        return 4;
    }

}