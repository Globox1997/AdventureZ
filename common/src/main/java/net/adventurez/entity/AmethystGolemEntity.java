package net.adventurez.entity;

import org.jetbrains.annotations.Nullable;

import net.adventurez.entity.nonliving.AmethystShardEntity;
import net.adventurez.init.ParticleInit;
import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class AmethystGolemEntity extends Monster {

    public static final EntityDataAccessor<Integer> BACK_CRYSTALS;
    public static final EntityDataAccessor<Boolean> DEEPSLATE_VARIANT;
    private int grow = 0;
    private boolean isStronger = false;

    public AmethystGolemEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createAmethystGolemAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.ARMOR, 1.0D);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.85D, false));
        this.goalSelector.addGoal(2, new ThrowShardGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.9D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static boolean canSpawn(EntityType<AmethystGolemEntity> type, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return (checkMonsterSpawnRules(type, world, spawnReason, pos, random) && !world.canSeeSky(pos)) || spawnReason == EntitySpawnReason.SPAWNER;
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BACK_CRYSTALS, 4);
        builder.define(DEEPSLATE_VARIANT, false);
    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(BACK_CRYSTALS, tag.getIntOr("Crystals", 0));
        this.entityData.set(DEEPSLATE_VARIANT, tag.getBooleanOr("DeepslateGolem", false));
        this.grow = tag.getIntOr("GrowAmethysts", 0);
        this.isStronger = tag.getBooleanOr("StrongerGolem", false);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Crystals", this.entityData.get(BACK_CRYSTALS));
        tag.putBoolean("DeepslateGolem", this.entityData.get(DEEPSLATE_VARIANT));
        tag.putInt("GrowAmethysts", this.grow);
        tag.putBoolean("StrongerGolem", this.isStronger);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && this.entityData.get(BACK_CRYSTALS) < 4) {
            this.grow++;
            if (this.grow >= 3600) {
                this.entityData.set(BACK_CRYSTALS, this.entityData.get(BACK_CRYSTALS) + 1);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAttributeBaseValue(Attributes.MAX_HEALTH) + 10.0D);
                this.heal(10F);
                this.grow = 0;
            }
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        int random = this.getRandom().nextInt(5);
        this.entityData.set(BACK_CRYSTALS, random);
        if (random > 0) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAttributeBaseValue(Attributes.MAX_HEALTH) + random * 10.0D);
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
        return SoundInit.AMETHYST_GOLEM_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.AMETHYST_GOLEM_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.AMETHYST_GOLEM_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.AMETHYST_GOLEM_STEP_EVENT, 0.9F, 1.0F);
    }

    public void amethystGolemRageMode() {
        if (this.level() instanceof ServerLevel serverWorld) {
            this.getAttribute(Attributes.ARMOR).setBaseValue(this.getAttributeBaseValue(Attributes.ARMOR) + 2.0D);
            if (!this.isStronger) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + 0.1D);
            }
            serverWorld.playSound(null, this, SoundInit.AMETHYST_GOLEM_RAGE_EVENT, SoundSource.HOSTILE, 1.0F, 1.0F);
            for (int i = 0; i < 20; i++) {
                double d = (double) this.getX() - 1.0F + this.level().getRandom().nextFloat() * 2.0F;
                double e = (double) ((float) this.getRandomY() + this.level().getRandom().nextFloat() * 0.1F);
                double f = (double) this.getZ() - 1.0F + this.level().getRandom().nextFloat() * 2.0F;
                double g = (double) (this.level().getRandom().nextFloat() * 0.4D);
                double h = (double) this.level().getRandom().nextFloat() * 0.2D;
                double l = (double) (this.level().getRandom().nextFloat() * 0.4D);
                ((ServerLevel) this.level()).sendParticles(ParticleInit.AMETHYST_SHARD_PARTICLE, d, e, f, 4, g, h, l, 1.0D);
            }
            this.isStronger = true;
        }
    }

    static {
        BACK_CRYSTALS = SynchedEntityData.defineId(AmethystGolemEntity.class, EntityDataSerializers.INT);
        DEEPSLATE_VARIANT = SynchedEntityData.defineId(AmethystGolemEntity.class, EntityDataSerializers.BOOLEAN);
    }

    public static class ThrowShardGoal extends Goal {
        private final AmethystGolemEntity amethystGolemEntity;
        private int cooldown;
        private long lastUpdateTime;

        public ThrowShardGoal(AmethystGolemEntity amethystGolemEntity) {
            this.amethystGolemEntity = amethystGolemEntity;
        }

        @Override
        public boolean canUse() {
            long l = this.amethystGolemEntity.level().getGameTime();
            if (l - this.lastUpdateTime < 100L || this.cooldown-- > 0) {
                return false;
            } else {
                this.lastUpdateTime = l;
                LivingEntity livingEntity = this.amethystGolemEntity.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    return this.amethystGolemEntity.hasLineOfSight(livingEntity);
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.amethystGolemEntity.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.amethystGolemEntity.isWithinHome(livingEntity.blockPosition()) || !this.amethystGolemEntity.hasLineOfSight(livingEntity)) {
                return false;
            } else if (this.cooldown > 0) {
                return false;
            } else {
                return !(livingEntity instanceof Player) || !livingEntity.isSpectator() && !((Player) livingEntity).isCreative();
            }
        }

        @Override
        public void start() {
            this.amethystGolemEntity.swingForAttack(InteractionHand.MAIN_HAND);
            if (!this.amethystGolemEntity.level().isClientSide()) {
                this.amethystGolemEntity.level().playSound(null, this.amethystGolemEntity, SoundInit.ROCK_THROW_EVENT, SoundSource.HOSTILE, 0.74F, 1.0F);
                AmethystShardEntity amethystShardEntity = new AmethystShardEntity(this.amethystGolemEntity, this.amethystGolemEntity.level());
                amethystShardEntity.shootFromRotation(amethystGolemEntity, amethystGolemEntity.getXRot(), amethystGolemEntity.getYRot(), -20.0F, 0.7F, this.amethystGolemEntity.getRandom().nextFloat());
                this.amethystGolemEntity.level().addFreshEntity(amethystShardEntity);
            }
            this.cooldown = 80 + this.amethystGolemEntity.getRandom().nextInt(200);
            this.stop();
        }
    }

}
