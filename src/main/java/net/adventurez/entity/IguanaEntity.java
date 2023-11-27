package net.adventurez.entity;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class IguanaEntity extends AnimalEntity {

    public static final TrackedData<Boolean> OPEN_MOUTH;

    public IguanaEntity(EntityType<? extends IguanaEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createIguanaAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isOf(Blocks.RED_SAND) && world.getBaseLightLevel(pos, 0) > 8;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.8D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.DEAD_BUSH), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.goalSelector.add(8, new EatDeadBushGoal(this, 1.0D));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OPEN_MOUTH, false);
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
    public IguanaEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return (IguanaEntity) EntityInit.IGUANA.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.DEAD_BUSH);
    }

    static {
        OPEN_MOUTH = DataTracker.registerData(IguanaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    private class EatDeadBushGoal extends MoveToTargetPosGoal {
        private final IguanaEntity iguanaEntity;

        public EatDeadBushGoal(IguanaEntity iguanaEntity, double speed) {
            super(iguanaEntity, speed, 12);
            this.iguanaEntity = iguanaEntity;
        }

        @Override
        public boolean canStart() {
            return !this.iguanaEntity.isBaby() && !this.iguanaEntity.isInLove() && super.canStart();
        }

        @Override
        public void start() {
            super.start();
            this.iguanaEntity.dataTracker.set(IguanaEntity.OPEN_MOUTH, true);
        }

        @Override
        public void stop() {
            super.stop();
            this.iguanaEntity.dataTracker.set(IguanaEntity.OPEN_MOUTH, false);
        }

        @Override
        public void tick() {
            if (!this.iguanaEntity.getWorld().isClient() && this.hasReached()) {
                if (this.iguanaEntity.getHealth() < this.iguanaEntity.getMaxHealth())
                    this.iguanaEntity.heal(2f);

                this.iguanaEntity.getWorld().breakBlock(targetPos, false);
                this.iguanaEntity.setLoveTicks(600);
            }
            super.tick();
        }

        @Override
        public double getDesiredDistanceToTarget() {
            return 2.1D;
        }

        @Override
        protected int getInterval(PathAwareEntity mob) {
            return 60 + mob.getRandom().nextInt(600);
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            if (world.getBlockState(pos).isOf(Blocks.DEAD_BUSH))
                return true;
            else
                return false;
        }
    }
}
