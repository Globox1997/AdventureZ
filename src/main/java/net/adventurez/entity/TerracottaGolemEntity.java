package net.adventurez.entity;

import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class TerracottaGolemEntity extends Monster {

    public static final EntityDataAccessor<Boolean> BLOCK_VARIANT = SynchedEntityData.defineId(TerracottaGolemEntity.class, EntityDataSerializers.BOOLEAN);

    private int blockTimer = 200;

    public TerracottaGolemEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createTerracottaGolemAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D).add(Attributes.FOLLOW_RANGE, 30.0D).add(Attributes.ARMOR, 1.5D);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.85D, false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.9D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static boolean canSpawn(EntityType<TerracottaGolemEntity> type, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return (checkMonsterSpawnRules(type, world, spawnReason, pos, random) && world.getBlockState(pos.below()).is(BlockTags.TERRACOTTA)) || spawnReason == EntitySpawnReason.SPAWNER;
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BLOCK_VARIANT, false);
    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(BLOCK_VARIANT, tag.getBooleanOr("BlockVariant", false));
        this.blockTimer = tag.getIntOr("BlockTimer", 0);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("BlockVariant", this.entityData.get(BLOCK_VARIANT));
        tag.putInt("BlockTimer", this.blockTimer);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.getTarget() != null) {
                if (this.getEntityData().get(BLOCK_VARIANT)) {
                    setBlockVariant(false);
                }
            } else if (!this.getEntityData().get(BLOCK_VARIANT)) {
                this.blockTimer--;
                if (this.blockTimer <= 0) {
                    setBlockVariant(true);
                }
            } else if (this.getHealth() < this.getMaxHealth() && this.level().getGameTime() % 40 == 0) {
                this.heal(2f);
            } else if (this.level().getGameTime() % 40 == 0) {
                if (this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), 8D, true) != null) {
                    setBlockVariant(false);
                }
            }
        }
    }

    private void setBlockVariant(boolean blockVariant) {
        if (blockVariant) {
            this.getEntityData().set(BLOCK_VARIANT, true);
            this.level().playSeededSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.TERRACOTTA_GOLEM_BUILD_DOWN_EVENT, SoundSource.BLOCKS, 0.8F, this.level().getRandom().nextFloat() * 0.4F + 0.8F, this.level().getRandom().nextLong());
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX() + this.getRandom().nextFloat(), this.getY() + this.getRandom().nextFloat() + 0.2f, this.getZ() + this.getRandom().nextFloat(), 4, 0.0f, 0.0f, 0.0f, 1.0D);
        } else {
            if (this.getEntityData().get(BLOCK_VARIANT)) {
                this.getEntityData().set(BLOCK_VARIANT, false);
                this.level().playSeededSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.TERRACOTTA_GOLEM_BUILD_UP_EVENT, SoundSource.BLOCKS, 1.2F, this.level().getRandom().nextFloat() * 0.4F + 0.8F, this.level().getRandom().nextLong());
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX() + this.getRandom().nextFloat(), this.getY() + this.getRandom().nextFloat() + 0.2f, this.getZ() + this.getRandom().nextFloat(), 4, 0.0f, 0.0f, 0.0f, 1.0D);
            }
        }
        this.blockTimer = this.level().getRandom().nextInt(100) + 200;
        this.reapplyPosition();
        this.refreshDimensions();
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        if (this.getEntityData().get(BLOCK_VARIANT)) {
            return super.getDefaultDimensions(pose).scale(1f / 2.7f, 1f / 3.2f);
        }
        return super.getDefaultDimensions(pose);
    }

    @Override
    public void playAmbientSound() {
        if (this.getEntityData().get(BLOCK_VARIANT)) {
            return;
        }
        super.playAmbientSound();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        if (spawnReason.equals(EntitySpawnReason.CHUNK_GENERATION) || spawnReason.equals(EntitySpawnReason.STRUCTURE)) {
            this.entityData.set(BLOCK_VARIANT, true);
            this.reapplyPosition();
            this.refreshDimensions();
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return false;
    }

    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (!this.level().isClientSide()) {
            setBlockVariant(false);
        }
        return super.hurtServer(level, source, amount);
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        if (this.getEntityData().get(BLOCK_VARIANT)) {
            return false;
        }
        return super.hasLineOfSight(entity);
    }

    @Override
    protected boolean isImmobile() {
        if (this.getEntityData().get(BLOCK_VARIANT)) {
            return true;
        }
        return super.isImmobile();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        if (BLOCK_VARIANT.equals(data)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(data);
    }
}
