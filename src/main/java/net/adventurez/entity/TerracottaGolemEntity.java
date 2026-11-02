package net.adventurez.entity;

import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.DataTracker.Builder;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TerracottaGolemEntity extends HostileEntity {

    public static final TrackedData<Boolean> BLOCK_VARIANT = DataTracker.registerData(TerracottaGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int blockTimer = 200;

    public TerracottaGolemEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createTerracottaGolemAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0D).add(EntityAttributes.GENERIC_ARMOR, 1.5D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.85D, false));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(7, new WanderAroundGoal(this, 0.9D));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static boolean canSpawn(EntityType<TerracottaGolemEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (canSpawnInDark(type, world, spawnReason, pos, random) && world.getBlockState(pos.down()).isIn(BlockTags.TERRACOTTA)) || spawnReason == SpawnReason.SPAWNER;
    }

    @Override
    protected void initDataTracker(Builder builder) {
        super.initDataTracker(builder);
        builder.add(BLOCK_VARIANT, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.dataTracker.set(BLOCK_VARIANT, tag.getBoolean("BlockVariant"));
        this.blockTimer = tag.getInt("BlockTimer");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putBoolean("BlockVariant", this.dataTracker.get(BLOCK_VARIANT));
        tag.putInt("BlockTimer", this.blockTimer);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            if (this.getTarget() != null) {
                if (this.getDataTracker().get(BLOCK_VARIANT)) {
                    setBlockVariant(false);
                }
            } else if (!this.getDataTracker().get(BLOCK_VARIANT)) {
                this.blockTimer--;
                if (this.blockTimer <= 0) {
                    setBlockVariant(true);
                }
            } else if (this.getHealth() < this.getMaxHealth() && this.getWorld().getTime() % 40 == 0) {
                this.heal(2f);
            } else if (this.getWorld().getTime() % 40 == 0) {
                if (this.getWorld().getClosestPlayer(this.getX(), this.getY(), this.getZ(), 8D, true) != null) {
                    setBlockVariant(false);
                }
            }
        }
    }

    private void setBlockVariant(boolean blockVariant) {
        if (blockVariant) {
            this.getDataTracker().set(BLOCK_VARIANT, true);
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.TERRACOTTA_GOLEM_BUILD_DOWN_EVENT, SoundCategory.BLOCKS, 0.8F, this.getWorld().getRandom().nextFloat() * 0.4F + 0.8F, this.getWorld().getRandom().nextLong());
            ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.EXPLOSION, this.getX() + this.getRandom().nextFloat(), this.getY() + this.getRandom().nextFloat() + 0.2f, this.getZ() + this.getRandom().nextFloat(), 4, 0.0f, 0.0f, 0.0f, 1.0D);
        } else {
            if (this.getDataTracker().get(BLOCK_VARIANT)) {
                this.getDataTracker().set(BLOCK_VARIANT, false);
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.TERRACOTTA_GOLEM_BUILD_UP_EVENT, SoundCategory.BLOCKS, 1.2F, this.getWorld().getRandom().nextFloat() * 0.4F + 0.8F, this.getWorld().getRandom().nextLong());
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.EXPLOSION, this.getX() + this.getRandom().nextFloat(), this.getY() + this.getRandom().nextFloat() + 0.2f, this.getZ() + this.getRandom().nextFloat(), 4, 0.0f, 0.0f, 0.0f, 1.0D);
            }
        }
        this.blockTimer = this.getWorld().getRandom().nextInt(100) + 200;
        this.refreshPosition();
        this.calculateDimensions();
    }

    @Override
    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        if (this.getDataTracker().get(BLOCK_VARIANT)) {
            return super.getBaseDimensions(pose).scaled(1f / 2.7f, 1f / 3.2f);
        }
        return super.getBaseDimensions(pose);
    }

    @Override
    public void playAmbientSound() {
        if (this.getDataTracker().get(BLOCK_VARIANT)) {
            return;
        }
        super.playAmbientSound();
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (spawnReason.equals(SpawnReason.CHUNK_GENERATION) || spawnReason.equals(SpawnReason.STRUCTURE)) {
            this.dataTracker.set(BLOCK_VARIANT, true);
            this.refreshPosition();
            this.calculateDimensions();
        }
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public void checkDespawn() {
        if (this.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.TERRACOTTA_GOLEM_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.TERRACOTTA_GOLEM_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.TERRACOTTA_GOLEM_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.TERRACOTTA_GOLEM_STEP_EVENT, 0.25F, 1.0F);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient()) {
            setBlockVariant(false);
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean canSee(Entity entity) {
        if (this.getDataTracker().get(BLOCK_VARIANT)) {
            return false;
        }
        return super.canSee(entity);
    }

    @Override
    protected boolean isImmobile() {
        if (this.getDataTracker().get(BLOCK_VARIANT)) {
            return true;
        }
        return super.isImmobile();
    }

    @Override
    public int getLimitPerChunk() {
        return 1;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (BLOCK_VARIANT.equals(data)) {
            this.calculateDimensions();
        }
        super.onTrackedDataSet(data);
    }
}
