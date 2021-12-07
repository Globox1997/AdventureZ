package net.adventurez.entity;

import java.util.EnumSet;
import java.util.Random;

import com.google.common.collect.UnmodifiableIterator;

import org.jetbrains.annotations.Nullable;

import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;
import net.adventurez.init.SoundInit;
import net.adventurez.mixin.accessor.EntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SaddledComponent;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class EnderWhaleEntity extends FlyingEntity implements ItemSteerable {

    private static final TrackedData<Boolean> ALWAYS_SADDLED;
    private static final TrackedData<Integer> BOOST_TIME;
    private final SaddledComponent saddledComponent;

    public EnderWhaleEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, ALWAYS_SADDLED);
        this.moveControl = new EnderWhaleEntity.EnderWhaleMovementControl(this);
    }

    public static DefaultAttributeContainer.Builder createEnderWhaleAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2.5D);
    }

    public static boolean canSpawn(EntityType<EnderWhaleEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos);
        return random.nextInt(6) == 0 && pos.getY() > 40 && pos.getY() - world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).getY() > 20 && blockState.isAir()
                && world.getEntitiesByClass(EnderDragonEntity.class, new Box(pos).expand(80D), EntityPredicates.EXCEPT_SPECTATOR).isEmpty()
                && SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), EntityInit.ENDER_WHALE_ENTITY);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(5, new EnderWhaleEntity.FlyRandomlyGoal(this));
        this.goalSelector.add(4, new EnderWhaleEntity.TemptGoal(this, 1.2D, Ingredient.ofItems(ItemInit.CHORUS_FRUIT_ON_A_STICK)));
        this.goalSelector.add(7, new EnderWhaleEntity.LookWhereToFlyGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ALWAYS_SADDLED, true);
        this.dataTracker.startTracking(BOOST_TIME, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.saddledComponent.writeNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.saddledComponent.readNbt(nbt);
    }

    @Nullable
    @Override
    public Entity getPrimaryPassenger() {
        return this.getFirstPassenger();
    }

    @Override
    public boolean canBeControlledByRider() {
        Entity entity = this.getPrimaryPassenger();
        if (!(entity instanceof PlayerEntity)) {
            return false;
        } else {
            PlayerEntity playerEntity = (PlayerEntity) entity;
            return playerEntity.getMainHandStack().isOf(ItemInit.CHORUS_FRUIT_ON_A_STICK);
        }
    }

    @Override
    public float getSaddledSpeed() {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.225F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.WHALE_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.WHALE_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.WHALE_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected float getSoundVolume() {
        return 1.1F;
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 500;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!player.shouldCancelInteraction()) {
            if (!this.hasPassengers()) {
                if (!this.world.isClient) {
                    player.startRiding(this);
                }
                return ActionResult.success(this.world.isClient);
            } else if (((EntityAccessor) this).getPassengerList().size() < 2) {
                if (!this.world.isClient) {
                    player.startRiding(this, true);
                }
                return ActionResult.success(this.world.isClient);
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.updatePassengerForDismount(passenger);
        } else {
            int[][] is = Dismounting.getDismountOffsets(direction);
            BlockPos blockPos = this.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            UnmodifiableIterator<EntityPose> var6 = passenger.getPoses().iterator();

            while (var6.hasNext()) {
                EntityPose entityPose = (EntityPose) var6.next();
                Box box = passenger.getBoundingBox(entityPose);
                int[][] var9 = is;
                int var10 = is.length;

                for (int var11 = 0; var11 < var10; ++var11) {
                    int[] js = var9[var11];
                    mutable.set(blockPos.getX() + js[0], blockPos.getY(), blockPos.getZ() + js[1]);
                    double d = this.world.getDismountHeight(mutable);
                    if (Dismounting.canDismountInBlock(d)) {
                        Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                        if (Dismounting.canPlaceEntityAt(this.world, passenger, box.offset(vec3d))) {
                            passenger.setPose(entityPose);
                            return vec3d;
                        }
                    }
                }
            }

            return super.updatePassengerForDismount(passenger);
        }
    }

    @Override
    public void travel(Vec3d movementInput) {
        Entity firstPassenger = this.getFirstPassenger();
        if (this.hasPassengers() && this.canBeControlledByRider() && firstPassenger instanceof PlayerEntity) {
            this.setYaw(firstPassenger.getYaw());
            this.prevYaw = this.getYaw();
            this.setPitch(firstPassenger.getPitch() * 0.5F);
            this.setRotation(this.getYaw(), this.getPitch());
            this.bodyYaw = this.getYaw();
            this.headYaw = this.getYaw();
            this.stepHeight = 1.0F;
            this.airStrafingSpeed = this.getMovementSpeed() * 0.1F;
            if (saddledComponent.boosted && saddledComponent.boostedTime++ > saddledComponent.currentBoostTime) {
                saddledComponent.boosted = false;
            }

            if (this.isLogicalSideForUpdatingMovement()) {
                float f = this.getSaddledSpeed();
                if (saddledComponent.boosted) {
                    f += f * 1.12F * MathHelper.sin((float) saddledComponent.boostedTime / (float) saddledComponent.currentBoostTime * 3.1415927F);
                }

                this.setMovementSpeed(f);
                this.setMovementInput(new Vec3d(0.0D, (double) -firstPassenger.getPitch() * 0.005F, 1.0D));
                this.bodyTrackingIncrements = 0;
            } else {
                this.updateLimbs(this, false);
                this.setVelocity(Vec3d.ZERO);
            }

            this.tryCheckBlockCollision();

        } else {
            this.stepHeight = 0.5F;
            this.airStrafingSpeed = 0.02F;
            this.setMovementInput(movementInput);
        }
    }

    @Override
    public void setMovementInput(Vec3d movementInput) {
        super.travel(movementInput);
    }

    @Override
    public boolean consumeOnAStickItem() {
        return this.saddledComponent.boost(this.getRandom());
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
        float offSet = 12F;
        if (passenger.equals(this.getPrimaryPassenger()))
            offSet = 1F;
        float f = MathHelper.sin(this.bodyYaw * 0.017453292F) * offSet;
        float g = MathHelper.cos(this.bodyYaw * 0.017453292F) * offSet;

        passenger.setPosition(this.getX() + (double) (0.1F * f), this.getBodyY(0.83F) + passenger.getHeightOffset() + 0.0D, this.getZ() - (double) (0.1F * g));
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    private static class EnderWhaleMovementControl extends MoveControl {
        private final EnderWhaleEntity enderWhaleEntity;
        private int collisionCheckCooldown;
        private int waitCooldown;

        public EnderWhaleMovementControl(EnderWhaleEntity enderWhaleEntity) {
            super(enderWhaleEntity);
            this.enderWhaleEntity = enderWhaleEntity;
        }

        @Override
        public void tick() {
            if (this.state == MoveControl.State.MOVE_TO) {
                if (this.collisionCheckCooldown-- <= 0) {
                    this.collisionCheckCooldown += 3;
                    Vec3d vec3d = new Vec3d(this.targetX - this.enderWhaleEntity.getX(), this.targetY - this.enderWhaleEntity.getY(), this.targetZ - this.enderWhaleEntity.getZ());
                    // Normalize vector
                    double d = Math.sqrt(vec3d.x * vec3d.x + vec3d.y * vec3d.y + vec3d.z * vec3d.z);
                    vec3d = new Vec3d(vec3d.x / d, vec3d.y / d, vec3d.z / d);
                    if ((!this.willCollide() && d > 1) || (this.waitCooldown++ < 0 && d > 1)) {
                        this.enderWhaleEntity.setVelocity(vec3d.multiply(0.12));
                    } else {
                        this.state = MoveControl.State.WAIT;
                        if (this.waitCooldown++ >= 200) {
                            this.waitCooldown = -40;
                        }
                    }
                }
            }
        }

        private boolean willCollide() {
            Box box = this.enderWhaleEntity.getBoundingBox();
            box = box.expand(0.2D);
            if (this.enderWhaleEntity.world.isSpaceEmpty(this.enderWhaleEntity, box)) {
                return false;
            }

            return true;
        }
    }

    private static class FlyRandomlyGoal extends Goal {
        private final EnderWhaleEntity enderWhaleEntity;

        public FlyRandomlyGoal(EnderWhaleEntity enderWhaleEntity) {
            this.enderWhaleEntity = enderWhaleEntity;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            MoveControl moveControl = this.enderWhaleEntity.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            Random random = this.enderWhaleEntity.getRandom();
            double randomX = 48D + (random.nextDouble() * 2.0D - 1.0D) * 128.0D;
            double randomZ = 48D + (random.nextDouble() * 2.0D - 1.0D) * 128.0D;

            double d = this.enderWhaleEntity.getX() + randomX;
            double e = this.enderWhaleEntity.getY() + (double) ((random.nextDouble() * 2.0F - 1.0F) * 16.0F);
            double f = this.enderWhaleEntity.getZ() + randomZ;

            if (this.enderWhaleEntity.getY() < 40D)
                e = this.enderWhaleEntity.getY() + 16D * random.nextDouble();
            else if (this.enderWhaleEntity.getY() > 100)
                e = this.enderWhaleEntity.getY() - 16D * random.nextDouble();

            this.enderWhaleEntity.getMoveControl().moveTo(d, e, f, 1.0D);
        }
    }

    private static class LookWhereToFlyGoal extends Goal {
        private final EnderWhaleEntity enderWhaleEntity;

        public LookWhereToFlyGoal(EnderWhaleEntity enderWhaleEntity) {
            this.enderWhaleEntity = enderWhaleEntity;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void tick() {
            Vec3d vec3d = this.enderWhaleEntity.getVelocity();
            this.enderWhaleEntity.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
            this.enderWhaleEntity.bodyYaw = this.enderWhaleEntity.getYaw();
        }
    }

    private static class TemptGoal extends Goal {
        private static final TargetPredicate TEMPTING_ENTITY_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility();
        private final TargetPredicate predicate;
        private final EnderWhaleEntity enderWhaleEntity;
        private final double speed;
        private double lastPlayerX;
        private double lastPlayerY;
        private double lastPlayerZ;
        private double lastPlayerPitch;
        private double lastPlayerYaw;
        protected PlayerEntity closestPlayer;
        private int cooldown;
        private final Ingredient food;

        public TemptGoal(EnderWhaleEntity entity, double speed, Ingredient food) {
            this.enderWhaleEntity = entity;
            this.speed = speed;
            this.food = food;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
            this.predicate = TEMPTING_ENTITY_PREDICATE.copy().setPredicate(this::isTemptedBy);
        }

        @Override
        public boolean canStart() {
            if (this.cooldown > 0) {
                --this.cooldown;
                return false;
            } else {
                this.closestPlayer = this.enderWhaleEntity.world.getClosestPlayer(this.predicate, this.enderWhaleEntity);
                return this.closestPlayer != null;
            }
        }

        private boolean isTemptedBy(LivingEntity entity) {
            return this.food.test(entity.getMainHandStack()) || this.food.test(entity.getOffHandStack());
        }

        @Override
        public boolean shouldContinue() {
            if (this.enderWhaleEntity.squaredDistanceTo(this.closestPlayer) < 36.0D) {
                if (this.closestPlayer.squaredDistanceTo(this.lastPlayerX, this.lastPlayerY, this.lastPlayerZ) > 0.010000000000000002D) {
                    return false;
                }

                if (Math.abs((double) this.closestPlayer.getPitch() - this.lastPlayerPitch) > 5.0D || Math.abs((double) this.closestPlayer.getYaw() - this.lastPlayerYaw) > 5.0D) {
                    return false;
                }
            } else {
                this.lastPlayerX = this.closestPlayer.getX();
                this.lastPlayerY = this.closestPlayer.getY();
                this.lastPlayerZ = this.closestPlayer.getZ();
            }

            this.lastPlayerPitch = (double) this.closestPlayer.getPitch();
            this.lastPlayerYaw = (double) this.closestPlayer.getYaw();

            return this.canStart();
        }

        @Override
        public void start() {
            this.lastPlayerX = this.closestPlayer.getX();
            this.lastPlayerY = this.closestPlayer.getY();
            this.lastPlayerZ = this.closestPlayer.getZ();
        }

        @Override
        public void stop() {
            this.closestPlayer = null;
            this.enderWhaleEntity.getNavigation().stop();
            this.cooldown = 100;
        }

        @Override
        public void tick() {
            this.enderWhaleEntity.getLookControl().lookAt(this.closestPlayer, (float) (this.enderWhaleEntity.getMaxHeadRotation() + 20), (float) this.enderWhaleEntity.getMaxLookPitchChange());
            if (this.enderWhaleEntity.squaredDistanceTo(this.closestPlayer) < 9.25D) {
                this.enderWhaleEntity.getNavigation().stop();
            } else {
                this.enderWhaleEntity.getNavigation().startMovingTo(this.closestPlayer, this.speed);
            }

        }

    }

    static {
        ALWAYS_SADDLED = DataTracker.registerData(EnderWhaleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(EnderWhaleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

}
