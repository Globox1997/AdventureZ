package net.adventurez.entity;

import java.util.UUID;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MammothEntity extends Animal implements NeutralMob {
    private static final UniformInt ANGER_TIME_RANGE;
    public static final EntityDataAccessor<Integer> ATTACK_TICKS;
    private int angerTime;
    private UUID targetUuid;
    private int attackTicksLeft;

    public MammothEntity(EntityType<? extends MammothEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createMammothAttributes() {
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 26.0D).add(Attributes.ATTACK_DAMAGE, 7.0D).add(Attributes.ATTACK_KNOCKBACK, 0.5D);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return (AgeableMob) EntityInit.MAMMOTH.create(world);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.SHORT_GRASS || stack.getItem() == Items.FERN || stack.getItem() == Items.TALL_GRASS;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new MammothEntity.AttackGoal());
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(1, new MammothEntity.MammothRevengeGoal());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        this.addPersistentAngerSaveData(nbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.readPersistentAngerSaveData(this.level(), nbt);
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int ticks) {
        this.angerTime = ticks;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.targetUuid = uuid;
    }

    @Override
    public UUID getPersistentAngerTarget() {
        return this.targetUuid;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isBaby() ? SoundInit.MAMMOTH_BABY_IDLE_EVENT : SoundInit.MAMMOTH_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.MAMMOTH_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.MAMMOTH_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.POLAR_BEAR_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_TICKS, this.attackTicksLeft);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
            this.getEntityData().set(ATTACK_TICKS, this.attackTicksLeft);
        }

    }

    @Override
    protected float getWaterSlowDown() {
        return 0.98F;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        if (entityData == null) {
            entityData = new AgeableMob.AgeableMobGroupData(1.0F);
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    static {
        ATTACK_TICKS = SynchedEntityData.defineId(MammothEntity.class, EntityDataSerializers.INT);
        ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
    }

    private class AttackGoal extends MeleeAttackGoal {
        public AttackGoal() {
            super(MammothEntity.this, 1.15D, true);
        }
    }

    private class MammothRevengeGoal extends HurtByTargetGoal {

        public MammothRevengeGoal() {
            super(MammothEntity.this);
        }

        @Override
        public void start() {
            super.start();
            if (MammothEntity.this.isBaby()) {
                this.alertOthers();
                this.stop();
            }

        }

        @Override
        public void alertOther(Mob mob, LivingEntity target) {
            if (mob instanceof MammothEntity && !mob.isBaby()) {
                super.alertOther(mob, target);
            }

        }
    }
}
