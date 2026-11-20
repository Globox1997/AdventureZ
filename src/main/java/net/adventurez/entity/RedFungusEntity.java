package net.adventurez.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.entity.EntityReference;

public class RedFungusEntity extends PathfinderMob implements NeutralMob {
    private static final UniformInt ANGER_TIME_RANGE;
    private long angerTime;
    @Nullable
    private EntityReference<LivingEntity> angryAt;

    public RedFungusEntity(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 2;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.SEAGRASS), true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new ResetUniversalAngerTargetGoal<>(this, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers());
    }

    public static AttributeSupplier.Builder createRedFungusAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 38.0D);
    }

    public static boolean canSpawn(EntityType<RedFungusEntity> type, LevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return world.getBlockState(pos.below()).is(BlockTags.MOOSHROOMS_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.FUNGUS_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.FUNGUS_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.FUNGUS_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP.value(), 0.15F, 1.0F);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput nbt) {
        super.addAdditionalSaveData(nbt);
        this.addPersistentAngerSaveData(nbt);
    }

    @Override
    public void readAdditionalSaveData(ValueInput nbt) {
        super.readAdditionalSaveData(nbt);
        this.readPersistentAngerSaveData(this.level(), nbt);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
            int randomMushroom = this.level().getRandom().nextInt(22000);
            if (randomMushroom == 1997
                    && (this.level().getBlockState(this.blockPosition().below()).is(Blocks.MYCELIUM) || this.level().getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK))
                    && this.level().getBlockState(this.blockPosition()).isAir()) {
                this.level().setBlockAndUpdate(this.blockPosition(), Blocks.RED_MUSHROOM.defaultBlockState());
            }
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (source.getDirectEntity() != null && source.getDirectEntity() instanceof Player attacker) {
            this.setPersistentAngerTarget(EntityReference.of(attacker));
        }
        return !this.isInvulnerableTo(level, source) && super.hurtServer(level, source, amount);

    }

    @Override
    public void startPersistentAngerTimer() {
        this.setTimeToRemainAngry(ANGER_TIME_RANGE.sample(this.random));
    }

    @Override
    public void setPersistentAngerEndTime(long ticks) {
        this.angerTime = ticks;
    }

    @Override
    public long getPersistentAngerEndTime() {
        return this.angerTime;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable EntityReference<LivingEntity> uuid) {
        this.angryAt = uuid;
    }

    @Override
    public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
        return this.angryAt;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4;
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (!super.doHurtTarget(level, target)) {
            return false;
        } else {
            if (!this.level().isClientSide() && target instanceof LivingEntity livingEntity && this.level().getRandom().nextInt(160) > 140) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 120 + this.level().getRandom().nextInt(160), 0, false, false), this);
            }
            return true;
        }
    }

    static {
        ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 60);
    }

}
