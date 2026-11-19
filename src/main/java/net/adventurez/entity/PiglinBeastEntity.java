package net.adventurez.entity;

import com.google.common.collect.ImmutableList;
import net.adventurez.AdventureMain;
import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import java.util.List;
import java.util.Optional;

public class PiglinBeastEntity extends Monster {
    public static final EntityDataAccessor<Float> LEAD_ARM = SynchedEntityData.defineId(PiglinBeastEntity.class, EntityDataSerializers.FLOAT);
    public float armTick;
    private int makePiglinsAngry = 0;
    private static final ResourceLocation LEAD_ARM_IDENTIFIER = AdventureMain.identifierOf("lead_arm_movement");
    private static final AttributeModifier LEAD_ARM_MOVEMENT = new AttributeModifier(
            LEAD_ARM_IDENTIFIER, -0.225F, AttributeModifier.Operation.ADD_VALUE);

    public PiglinBeastEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 30;
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
    }

    public static AttributeSupplier.Builder createPiglinBeastAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 80.0D).add(Attributes.MOVEMENT_SPEED, 0.225D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D).add(Attributes.ATTACK_DAMAGE, 9.0D).add(Attributes.ATTACK_KNOCKBACK, 2D)
                .add(Attributes.FOLLOW_RANGE, 38.0D);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AttackGoal());
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, WitherSkeleton.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BlazeGuardianEntity.class, true));
        this.targetSelector.addGoal(4, (new HurtByTargetGoal(this, new Class[]{Piglin.class})));
    }

    @Override
    public void customServerAiStep() {
        if (this.getTarget() instanceof Player) {
            makePiglinsAngry++;
            if (makePiglinsAngry == 600) {
                entityData.set(LEAD_ARM, 1F);
                angerNearbyPiglins();
                this.level().playSound(null, this, SoundInit.PIGLIN_BEAST_SHOUT_EVENT, SoundSource.HOSTILE, 1.0F, 1.0F);
                this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(LEAD_ARM_MOVEMENT);
            }
            if (makePiglinsAngry > 600) {
                entityData.set(LEAD_ARM, entityData.get(LEAD_ARM) - 0.02F);
            }
            if (makePiglinsAngry == 650) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(LEAD_ARM_IDENTIFIER);
                makePiglinsAngry = 0;
            }
        } else if (makePiglinsAngry > 0) {
            makePiglinsAngry--;
            if (this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(LEAD_ARM_IDENTIFIER)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(LEAD_ARM_IDENTIFIER);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("LeadArm", 0F);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.armTick = tag.getFloat("LeadArm");
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAD_ARM, 0F);
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
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.PIGLIN_BEAST_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.PIGLIN_BEAST_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.PIGLIN_BEAST_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.PIGLIN_BEAST_STEP_EVENT, 0.5F, 1.0F);
    }

    private class AttackGoal extends MeleeAttackGoal {

        public AttackGoal() {
            super(PiglinBeastEntity.this, 1.0D, true);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            if (this.canPerformAttack(target)) {
                this.resetAttackCooldown();
                this.mob.level().playSound(null, PiglinBeastEntity.this, SoundInit.PIGLIN_BEAST_CLUBSWING_EVENT, SoundSource.HOSTILE, 1.0F, 1.0F);

                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(target);
            }
        }
    }

    private void angerNearbyPiglins() {
        List<Piglin> list = this.level().getEntitiesOfClass(Piglin.class, this.getBoundingBox().inflate(40D), EntitySelector.NO_SPECTATORS);
        if (!list.isEmpty()) {
            getNearbyPiglins(list.getFirst()).forEach((abstractPiglinEntity) -> {
                getNearestDetectedPlayer(abstractPiglinEntity).ifPresent((playerEntity) -> {
                    becomeAngryWith(abstractPiglinEntity, playerEntity);
                });
            });
        }
    }

    public static Optional<Player> getNearestDetectedPlayer(AbstractPiglin piglin) {
        return piglin.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) ? piglin.getBrain().getMemoryInternal(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
                : Optional.empty();
    }

    public static void becomeAngryWith(AbstractPiglin piglin, LivingEntity target) {
        piglin.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        piglin.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);

        if (target.getType() == EntityType.PLAYER && piglin.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
            piglin.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
        }
        if (!piglin.level().isClientSide()) {
            piglin.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(piglin.getAttributeValue(Attributes.ATTACK_DAMAGE) + 3.0D);
            piglin.heal(piglin.getMaxHealth());
        }

    }

    private static List<AbstractPiglin> getNearbyPiglins(AbstractPiglin piglin) {
        return piglin.getBrain().getMemoryInternal(MemoryModuleType.NEARBY_ADULT_PIGLINS).orElse(ImmutableList.of());
    }

}
