package net.adventurez.entity;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class IguanaEntity extends Animal {

    public static final EntityDataAccessor<Boolean> OPEN_MOUTH;

    public IguanaEntity(EntityType<? extends IguanaEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createIguanaAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.22D);
    }

    public static boolean checkAnimalSpawnRules(EntityType<? extends Animal> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return world.getBlockState(pos.below()).is(Blocks.RED_SAND) && world.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.8D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.DEAD_BUSH), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new EatDeadBushGoal(this, 1.0D));
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(OPEN_MOUTH, false);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.IGUANA_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.IGUANA_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.IGUANA_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.IGUANA_STEP_EVENT, 0.35F, 1.0F);
    }

    @Override
    public IguanaEntity getBreedOffspring(ServerLevel serverWorld, AgeableMob passiveEntity) {
        return EntityInit.IGUANA.create(serverWorld);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.DEAD_BUSH);
    }

    static {
        OPEN_MOUTH = SynchedEntityData.defineId(IguanaEntity.class, EntityDataSerializers.BOOLEAN);
    }

    private class EatDeadBushGoal extends MoveToBlockGoal {
        private final IguanaEntity iguanaEntity;

        public EatDeadBushGoal(IguanaEntity iguanaEntity, double speed) {
            super(iguanaEntity, speed, 12);
            this.iguanaEntity = iguanaEntity;
        }

        @Override
        public boolean canUse() {
            return !this.iguanaEntity.isBaby() && !this.iguanaEntity.isInLove() && super.canUse();
        }

        @Override
        public void start() {
            super.start();
            this.iguanaEntity.entityData.set(IguanaEntity.OPEN_MOUTH, true);
        }

        @Override
        public void stop() {
            super.stop();
            this.iguanaEntity.entityData.set(IguanaEntity.OPEN_MOUTH, false);
        }

        @Override
        public void tick() {
            if (!this.iguanaEntity.level().isClientSide() && this.isReachedTarget()) {
                if (this.iguanaEntity.getHealth() < this.iguanaEntity.getMaxHealth())
                    this.iguanaEntity.heal(2f);

                this.iguanaEntity.level().destroyBlock(blockPos, false);
                this.iguanaEntity.setInLoveTime(600);
            }
            super.tick();
        }

        @Override
        public double acceptedDistance() {
            return 2.1D;
        }

        @Override
        protected int nextStartTick(PathfinderMob mob) {
            return 60 + mob.getRandom().nextInt(600);
        }

        @Override
        protected boolean isValidTarget(LevelReader world, BlockPos pos) {
            if (world.getBlockState(pos).is(Blocks.DEAD_BUSH))
                return true;
            else
                return false;
        }
    }
}
