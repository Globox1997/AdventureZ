package net.adventurez.entity;

import java.util.EnumSet;

import com.google.common.collect.UnmodifiableIterator;

import org.jetbrains.annotations.Nullable;

import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;
import net.adventurez.init.SoundInit;
import net.adventurez.mixin.accessor.EntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EnderWhaleEntity extends FlyingMob implements ItemSteerable {

    private static final EntityDataAccessor<Boolean> ALWAYS_SADDLED;
    private static final EntityDataAccessor<Integer> BOOST_TIME;
    private final ItemBasedSteering saddledComponent;

    public EnderWhaleEntity(EntityType<? extends FlyingMob> entityType, Level world) {
        super(entityType, world);
        this.saddledComponent = new ItemBasedSteering(this.entityData, BOOST_TIME, ALWAYS_SADDLED);
        this.moveControl = new EnderWhaleEntity.EnderWhaleMovementControl(this);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createEnderWhaleAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.022D).add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5D).add(Attributes.KNOCKBACK_RESISTANCE, 2.5D);
    }

    public static boolean canSpawn(EntityType<EnderWhaleEntity> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        BlockState blockState = world.getBlockState(pos);
        return random.nextInt(6) == 0 && pos.getY() > 40 && pos.getY() - world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos).getY() > 20 && blockState.isAir()
                && world.getEntitiesOfClass(EnderDragon.class, new AABB(pos).inflate(80D), EntitySelector.NO_SPECTATORS).isEmpty()
                && NaturalSpawner.isValidEmptySpawnBlock(world, pos, blockState, blockState.getFluidState(), EntityInit.ENDER_WHALE);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new EnderWhaleEntity.FlyRandomlyGoal(this));
        this.goalSelector.addGoal(4, new EnderWhaleEntity.TemptGoal(this, 1.2D, Ingredient.of(ItemInit.CHORUS_FRUIT_ON_A_STICK)));
        this.goalSelector.addGoal(7, new EnderWhaleEntity.LookWhereToFlyGoal(this));
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ALWAYS_SADDLED, true);
        builder.define(BOOST_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        this.saddledComponent.addAdditionalSaveData(nbt);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.saddledComponent.readAdditionalSaveData(nbt);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < 2;
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        return !this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof Player
                && ((Player) this.getPassengers().get(0)).getMainHandItem().is(ItemInit.CHORUS_FRUIT_ON_A_STICK) ? (LivingEntity) this.getPassengers().get(0) : null;
    }

    // Not used since it is a flying entity which overrides the travel method and doesn't use movementspeed
    @Override
    protected float getRiddenSpeed(Player controllingPlayer) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.saddledComponent.boostFactor();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.ENDER_WHALE_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ENDER_WHALE_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ENDER_WHALE_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected float getSoundVolume() {
        return 1.1F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 500;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!player.isSecondaryUseActive()) {
            if ((player.getItemInHand(hand).is(Items.CHORUS_FRUIT) || player.getItemInHand(hand).is(Items.POPPED_CHORUS_FRUIT)) && this.getMaxHealth() - this.getHealth() > 0.1F) {
                if (!this.level().isClientSide() && !player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                    this.heal(4F);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
            if (!this.isVehicle()) {
                if (!this.level().isClientSide()) {
                    player.startRiding(this);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            } else if (((EntityAccessor) this).getPassengerList().size() < 2) {
                if (!this.level().isClientSide()) {
                    player.startRiding(this, true);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.getDismountLocationForPassenger(passenger);
        } else {
            int[][] is = DismountHelper.offsetsForDirection(direction);
            BlockPos blockPos = this.blockPosition();
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            UnmodifiableIterator<Pose> var6 = passenger.getDismountPoses().iterator();

            while (var6.hasNext()) {
                Pose entityPose = (Pose) var6.next();
                AABB box = passenger.getLocalBoundsForPose(entityPose);
                int[][] var9 = is;
                int var10 = is.length;

                for (int var11 = 0; var11 < var10; ++var11) {
                    int[] js = var9[var11];
                    mutable.set(blockPos.getX() + js[0], blockPos.getY(), blockPos.getZ() + js[1]);
                    double d = this.level().getBlockFloorHeight(mutable);
                    if (DismountHelper.isBlockFloorValid(d)) {
                        Vec3 vec3d = Vec3.upFromBottomCenterOf(mutable, d);
                        if (DismountHelper.canDismountTo(this.level(), passenger, box.move(vec3d))) {
                            passenger.setPose(entityPose);
                            return vec3d;
                        }
                    }
                }
            }

            return super.getDismountLocationForPassenger(passenger);
        }
    }

    // movementInput is somehow affected when ridden otherwise 0,0,0
    @Override
    public void travel(Vec3 movementInput) {
        if (this.getControllingPassenger() != null) {
            if (this.isControlledByLocalInstance()) {
                this.moveRelative(this.getSpeed(), movementInput);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.91f));
            }
            this.calculateEntityAnimation(false);
        } else {
            super.travel(movementInput);
        }
    }

    // movementInput is Vec3d(this.sidewaysSpeed, this.upwardSpeed, this.forwardSpeed) of the whale entity
    // getControlledMovementInput used in method travelControlled()
    // which will execute travel() method
    @Override
    protected Vec3 getRiddenInput(Player controllingPlayer, Vec3 movementInput) {
        return new Vec3(0.0f, controllingPlayer.getXRot() / 180f * -1f, 1.0f);
    }

    @Override
    protected void tickRidden(Player controllingPlayer, Vec3 movementInput) {
        super.tickRidden(controllingPlayer, movementInput);
        this.setRot(controllingPlayer.getYRot(), controllingPlayer.getXRot() * 0.5f);
        this.yBodyRot = this.yHeadRot = this.getYRot();
        this.yRotO = this.yHeadRot;
        this.saddledComponent.tickBoost();
    }

    @Override
    public boolean boost() {
        return this.saddledComponent.boost(this.getRandom());
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction positionUpdater) {
        if (!this.hasPassenger(passenger)) {
            return;
        }
        float offSet = 12F;
        if (passenger.equals(this.getFirstPassenger())) {
            offSet = 1F;
        }
        float f = Mth.sin(this.yBodyRot * 0.017453292F) * offSet;
        float g = Mth.cos(this.yBodyRot * 0.017453292F) * offSet;

        positionUpdater.accept(passenger, this.getX() + (double) (0.1F * f), this.getY(0.68F), this.getZ() - (double) (0.1F * g));
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
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.collisionCheckCooldown-- <= 0) {
                    this.collisionCheckCooldown += 3;
                    Vec3 vec3d = new Vec3(this.wantedX - this.enderWhaleEntity.getX(), this.wantedY - this.enderWhaleEntity.getY(), this.wantedZ - this.enderWhaleEntity.getZ());
                    // Normalize vector
                    double d = Math.sqrt(vec3d.x * vec3d.x + vec3d.y * vec3d.y + vec3d.z * vec3d.z);
                    vec3d = new Vec3(vec3d.x / d, vec3d.y / d, vec3d.z / d);
                    if ((!this.willCollide() && d > 1) || (this.waitCooldown++ < 0 && d > 1)) {
                        this.enderWhaleEntity.setDeltaMovement(vec3d.scale(0.12));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                        if (this.waitCooldown++ >= 200) {
                            this.waitCooldown = -40;
                        }
                    }
                }
            }
        }

        private boolean willCollide() {
            AABB box = this.enderWhaleEntity.getBoundingBox();
            box = box.inflate(0.2D);
            if (this.enderWhaleEntity.level().noCollision(this.enderWhaleEntity, box)) {
                return false;
            }
            return true;
        }
    }

    private static class FlyRandomlyGoal extends Goal {
        private final EnderWhaleEntity enderWhaleEntity;

        public FlyRandomlyGoal(EnderWhaleEntity enderWhaleEntity) {
            this.enderWhaleEntity = enderWhaleEntity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl moveControl = this.enderWhaleEntity.getMoveControl();
            if (!moveControl.hasWanted()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            RandomSource random = this.enderWhaleEntity.getRandom();
            double randomX = 48D + (random.nextDouble() * 2.0D - 1.0D) * 128.0D;
            double randomZ = 48D + (random.nextDouble() * 2.0D - 1.0D) * 128.0D;

            double d = this.enderWhaleEntity.getX() + randomX;
            double e = this.enderWhaleEntity.getY() + (double) ((random.nextDouble() * 2.0F - 1.0F) * 16.0F);
            double f = this.enderWhaleEntity.getZ() + randomZ;

            if (this.enderWhaleEntity.getY() < 40D)
                e = this.enderWhaleEntity.getY() + 16D * random.nextDouble();
            else if (this.enderWhaleEntity.getY() > 100)
                e = this.enderWhaleEntity.getY() - 16D * random.nextDouble();

            this.enderWhaleEntity.getMoveControl().setWantedPosition(d, e, f, 1.0D);
        }
    }

    private static class LookWhereToFlyGoal extends Goal {
        private final EnderWhaleEntity enderWhaleEntity;

        public LookWhereToFlyGoal(EnderWhaleEntity enderWhaleEntity) {
            this.enderWhaleEntity = enderWhaleEntity;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            Vec3 vec3d = this.enderWhaleEntity.getDeltaMovement();
            this.enderWhaleEntity.setYRot(-((float) Mth.atan2(vec3d.x, vec3d.z)) * 57.295776F);
            this.enderWhaleEntity.yBodyRot = this.enderWhaleEntity.getYRot();
        }
    }

    private static class TemptGoal extends Goal {
        private static final TargetingConditions TEMPTING_ENTITY_PREDICATE = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight();
        private final TargetingConditions predicate;
        private final EnderWhaleEntity enderWhaleEntity;
        private final double speed;
        private double lastPlayerX;
        private double lastPlayerY;
        private double lastPlayerZ;
        private double lastPlayerPitch;
        private double lastPlayerYaw;
        protected Player closestPlayer;
        private int cooldown;
        private final Ingredient food;

        public TemptGoal(EnderWhaleEntity entity, double speed, Ingredient food) {
            this.enderWhaleEntity = entity;
            this.speed = speed;
            this.food = food;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.predicate = TEMPTING_ENTITY_PREDICATE.copy().selector(this::isTemptedBy);
        }

        @Override
        public boolean canUse() {
            if (this.cooldown > 0) {
                --this.cooldown;
                return false;
            } else {
                this.closestPlayer = this.enderWhaleEntity.level().getNearestPlayer(this.predicate, this.enderWhaleEntity);
                return this.closestPlayer != null;
            }
        }

        private boolean isTemptedBy(LivingEntity entity) {
            return this.food.test(entity.getMainHandItem()) || this.food.test(entity.getOffhandItem());
        }

        @Override
        public boolean canContinueToUse() {
            if (this.enderWhaleEntity.distanceToSqr(this.closestPlayer) < 36.0D) {
                if (this.closestPlayer.distanceToSqr(this.lastPlayerX, this.lastPlayerY, this.lastPlayerZ) > 0.010000000000000002D) {
                    return false;
                }

                if (Math.abs((double) this.closestPlayer.getXRot() - this.lastPlayerPitch) > 5.0D || Math.abs((double) this.closestPlayer.getYRot() - this.lastPlayerYaw) > 5.0D) {
                    return false;
                }
            } else {
                this.lastPlayerX = this.closestPlayer.getX();
                this.lastPlayerY = this.closestPlayer.getY();
                this.lastPlayerZ = this.closestPlayer.getZ();
            }

            this.lastPlayerPitch = (double) this.closestPlayer.getXRot();
            this.lastPlayerYaw = (double) this.closestPlayer.getYRot();

            return this.canUse();
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
            this.enderWhaleEntity.getLookControl().setLookAt(this.closestPlayer, (float) (this.enderWhaleEntity.getMaxHeadYRot() + 20), (float) this.enderWhaleEntity.getMaxHeadXRot());
            if (this.enderWhaleEntity.distanceToSqr(this.closestPlayer) < 9.25D) {
                this.enderWhaleEntity.getNavigation().stop();
            } else {
                this.enderWhaleEntity.getNavigation().moveTo(this.closestPlayer, this.speed);
            }

        }

    }

    static {
        ALWAYS_SADDLED = SynchedEntityData.defineId(EnderWhaleEntity.class, EntityDataSerializers.BOOLEAN);
        BOOST_TIME = SynchedEntityData.defineId(EnderWhaleEntity.class, EntityDataSerializers.INT);
    }

}
