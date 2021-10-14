package net.adventurez.entity;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.adventurez.entity.nonliving.AmethystShardEntity;
import net.adventurez.init.ParticleInit;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class AmethystGolemEntity extends HostileEntity {

    public static final TrackedData<Integer> BACK_CRYSTALS;
    public static final TrackedData<Boolean> DEEPSLATE_VARIANT;
    private int grow = 0;
    private boolean isStronger = false;

    public AmethystGolemEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
    }

    public static DefaultAttributeContainer.Builder createAmethystGolemAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0D).add(EntityAttributes.GENERIC_ARMOR, 1.0D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.85D, false));
        this.goalSelector.add(2, new ThrowShardGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(7, new WanderAroundGoal(this, 0.9D));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static boolean canSpawn(EntityType<AmethystGolemEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (canSpawnInDark(type, world, spawnReason, pos, random) && !world.isSkyVisible(pos)) || spawnReason == SpawnReason.SPAWNER;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BACK_CRYSTALS, 4);
        this.dataTracker.startTracking(DEEPSLATE_VARIANT, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.dataTracker.set(BACK_CRYSTALS, tag.getInt("Crystals"));
        this.dataTracker.set(DEEPSLATE_VARIANT, tag.getBoolean("DeepslateGolem"));
        this.grow = tag.getInt("GrowAmethysts");
        this.isStronger = tag.getBoolean("StrongerGolem");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putInt("Crystals", this.dataTracker.get(BACK_CRYSTALS));
        tag.putBoolean("DeepslateGolem", this.dataTracker.get(DEEPSLATE_VARIANT));
        tag.putInt("GrowAmethysts", this.grow);
        tag.putBoolean("StrongerGolem", this.isStronger);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient && this.dataTracker.get(BACK_CRYSTALS) < 4) {
            this.grow++;
            if (this.grow >= 3600) {
                this.dataTracker.set(BACK_CRYSTALS, this.dataTracker.get(BACK_CRYSTALS) + 1);
                this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(this.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH) + 10.0D);
                this.heal(10F);
                this.grow = 0;
            }
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        int random = this.random.nextInt(5);
        this.dataTracker.set(BACK_CRYSTALS, random);
        if (random > 0)
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(this.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH) + random * 10.0D);
        return super.initialize(world, difficulty, spawnReason, (EntityData) entityData, entityTag);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.AMETHYST_GOLEM_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.AMETHYST_GOLEM_HIT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.AMETHYST_GOLEM_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.AMETHYST_GOLEM_WALK_EVENT, 0.9F, 1.0F);
    }

    public void amethystGolemRageMode() {
        if (this.world instanceof ServerWorld) {
            this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(this.getAttributeBaseValue(EntityAttributes.GENERIC_ARMOR) + 2.0D);
            if (!this.isStronger)
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(this.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) + 0.1D);
            ((ServerWorld) world).playSoundFromEntity(null, this, SoundInit.AMETHYST_GOLEM_RAGE_EVENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
            for (int i = 0; i < 20; i++) {
                double d = (double) this.getX() - 1.0F + this.world.random.nextFloat() * 2.0F;
                double e = (double) ((float) this.getRandomBodyY() + this.world.random.nextFloat() * 0.1F);
                double f = (double) this.getZ() - 1.0F + this.world.random.nextFloat() * 2.0F;
                double g = (double) (world.random.nextFloat() * 0.4D);
                double h = (double) world.random.nextFloat() * 0.2D;
                double l = (double) (world.random.nextFloat() * 0.4D);
                ((ServerWorld) world).spawnParticles(ParticleInit.AMETHYST_SHARD_PARTICLE, d, e, f, 4, g, h, l, 1.0D);
            }
            this.isStronger = true;
        }
    }

    static {
        BACK_CRYSTALS = DataTracker.registerData(AmethystGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        DEEPSLATE_VARIANT = DataTracker.registerData(AmethystGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public class ThrowShardGoal extends Goal {
        private final AmethystGolemEntity amethystGolemEntity;
        private int cooldown;
        private long lastUpdateTime;

        public ThrowShardGoal(AmethystGolemEntity amethystGolemEntity) {
            this.amethystGolemEntity = amethystGolemEntity;
        }

        @Override
        public boolean canStart() {
            long l = this.amethystGolemEntity.world.getTime();
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
                    if (this.amethystGolemEntity.canSee(livingEntity)) {
                        return true;
                    } else
                        return false;
                }
            }
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.amethystGolemEntity.getTarget();
            if (livingEntity == null)
                return false;
            else if (!livingEntity.isAlive())
                return false;
            else if (!this.amethystGolemEntity.isInWalkTargetRange(livingEntity.getBlockPos()) || !this.amethystGolemEntity.canSee(livingEntity))
                return false;
            else if (this.cooldown > 0)
                return false;
            else
                return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative();

        }

        @Override
        public void start() {
            this.amethystGolemEntity.swingHand(Hand.MAIN_HAND);
            if (!this.amethystGolemEntity.world.isClient) {
                ((ServerWorld) this.amethystGolemEntity.world).playSoundFromEntity(null, this.amethystGolemEntity, SoundInit.ROCK_THROW_EVENT, SoundCategory.HOSTILE, 0.74F, 1.0F);
                AmethystShardEntity amethystShardEntity = new AmethystShardEntity(this.amethystGolemEntity, world);
                amethystShardEntity.setProperties(amethystGolemEntity, amethystGolemEntity.getPitch(), amethystGolemEntity.getYaw(), -20.0F, 0.7F, 1.0F);
                world.spawnEntity(amethystShardEntity);

            }
            this.cooldown = 80 + this.amethystGolemEntity.random.nextInt(200);
            this.stop();
        }
    }

}
