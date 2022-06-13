package net.adventurez.entity;

import java.util.EnumSet;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;

public class OrcEntity extends HostileEntity {
    public static final TrackedData<Integer> ORK_SIZE;
    public static final TrackedData<Boolean> DOUBLE_HAND_ATTACK;
    public static final TrackedData<Integer> INVENTORY_ITEM_ID;

    public final SimpleInventory inventory = new SimpleInventory(1);

    public OrcEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createOrkAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D).add(EntityAttributes.GENERIC_ARMOR, 1.0D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new OrcEscapeGoal(this, 1.4D));
        this.goalSelector.add(2, new OrcAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new OrcGroupGoal(this));
        this.goalSelector.add(4, new WanderAroundVeryFarGoal(this, 0.9D, 0.7F));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(7, new WanderAroundGoal(this, 0.9D));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, WanderingTraderEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
        this.targetSelector.add(4, (new RevengeGoal(this, new Class[] { OrcEntity.class })));
    }

    public static boolean canSpawn(EntityType<OrcEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (canSpawnInDark(type, world, spawnReason, pos, random) && world.isSkyVisible(pos)) || spawnReason == SpawnReason.SPAWNER;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ORK_SIZE, 1);
        this.dataTracker.startTracking(DOUBLE_HAND_ATTACK, false);
        this.dataTracker.startTracking(INVENTORY_ITEM_ID, -1);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.setSize(tag.getInt("OrkSize"), false);
        if (tag.contains("OrcItem", 10)) {
            this.inventory.setStack(0, ItemStack.fromNbt(tag.getCompound("OrcItem")));
            this.setItemId(this.inventory.getStack(0).getItem());
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putInt("OrkSize", this.getSize());
        if (!this.inventory.getStack(0).isEmpty()) {
            tag.put("OrcItem", this.inventory.getStack(0).writeNbt(new NbtCompound()));
        }
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        if (!this.inventory.getStack(0).isEmpty())
            return false;
        return true;
    }

    public int getSize() {
        return (Integer) this.dataTracker.get(ORK_SIZE);
    }

    public void setSize(int size, boolean healOrc) {
        this.dataTracker.set(ORK_SIZE, size);
        this.refreshPosition();
        this.calculateDimensions();
        if (healOrc) {
            this.setHealth(this.getMaxHealth());
        }
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (ORK_SIZE.equals(data)) {
            this.calculateDimensions();
            this.setYaw(this.headYaw);
            this.bodyYaw = this.headYaw;
        }

        super.onTrackedDataSet(data);
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F * (float) this.getSize();
    }

    @Override
    public float getSoundPitch() {
        return 1.6F - ((float) 0.2F * this.getSize());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.ORC_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ORC_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ORC_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.ORC_STEP_EVENT, 1.0F, 1.0F);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.82F * dimensions.height;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        int random = this.random.nextInt(3) + 1;
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double) (16D + random * 6D));
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue((double) (0.26F - 0.012F * (float) random));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) random * 3D + 2D);
        this.setSize(random, true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(0.55F * (float) this.getSize());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.world.random.nextInt(3) == 0 && !this.dataTracker.get(DOUBLE_HAND_ATTACK)) {
            dataTracker.set(DOUBLE_HAND_ATTACK, true);
        } else {
            dataTracker.set(DOUBLE_HAND_ATTACK, false);
        }
        return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
    }

    @Override
    public int getLimitPerChunk() {
        return 4;
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.inventory != null && !this.inventory.isEmpty()) {
            this.dropStack(this.inventory.getStack(0));
        }
    }

    public boolean isBigOrc() {
        if (this.getSize() == 3) {
            return true;
        } else
            return false;
    }

    private void setItemId(Item item) {
        this.dataTracker.set(INVENTORY_ITEM_ID, Registry.ITEM.getRawId(item));
    }

    static {
        ORK_SIZE = DataTracker.registerData(OrcEntity.class, TrackedDataHandlerRegistry.INTEGER);
        DOUBLE_HAND_ATTACK = DataTracker.registerData(OrcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        INVENTORY_ITEM_ID = DataTracker.registerData(OrcEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    private class OrcAttackGoal extends MeleeAttackGoal {
        private final OrcEntity orcEntity;

        public OrcAttackGoal(OrcEntity orcEntity, double speed, boolean pauseWhenMobIdle) {
            super(orcEntity, speed, pauseWhenMobIdle);
            this.orcEntity = orcEntity;
        }

        @Override
        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double) (this.mob.getWidth() * 2.0F * this.mob.getWidth() + entity.getWidth());
        }

        @Override
        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.getCooldown() <= 0) {
                this.resetCooldown();
                this.mob.swingHand(Hand.MAIN_HAND);
                this.mob.tryAttack(target);
                if (!this.orcEntity.isBigOrc() && this.orcEntity.inventory.isEmpty() && this.orcEntity.world.random.nextFloat() <= 0.3F && target instanceof PlayerEntity) {
                    PlayerEntity playerEntity = (PlayerEntity) target;
                    for (int i = 45; i > 0; i--) {
                        if (!playerEntity.getInventory().getStack(i).isEmpty() && playerEntity.getInventory().getStack(i).getItem() instanceof ToolItem
                                && playerEntity.world.random.nextFloat() < 0.3F) {
                            this.orcEntity.inventory.setStack(0, playerEntity.getInventory().getStack(i));
                            this.orcEntity.setItemId(this.orcEntity.inventory.getStack(0).getItem());
                            playerEntity.getInventory().setStack(i, ItemStack.EMPTY);
                            break;
                        }
                    }

                }
            }

        }
    }

    private class OrcGroupGoal extends Goal {
        private final OrcEntity orcEntity;
        private int moveDelay;
        private int checkSurroundingDelay;
        private OrcEntity orcLeader;

        public OrcGroupGoal(OrcEntity orcEntity) {
            this.orcEntity = orcEntity;
            this.checkSurroundingDelay = this.getSurroundingSearchDelay(orcEntity);
        }

        private int getSurroundingSearchDelay(OrcEntity orcEntity) {
            return 200 + orcEntity.getRandom().nextInt(200) % 20;
        }

        private boolean isCloseEnoughToOrcLeader() {
            return this.orcEntity.distanceTo(this.orcLeader) <= 10.0F;
        }

        @Override
        public boolean canStart() {
            if (this.orcEntity.getSize() == 3 || this.orcEntity.isAttacking()) {
                return false;
            }
            if (this.orcLeader != null && this.isCloseEnoughToOrcLeader()) {
                return false;
            }
            if (this.checkSurroundingDelay > 0) {
                --this.checkSurroundingDelay;
                return false;
            } else {
                List<? extends OrcEntity> list = orcEntity.world.getNonSpectatingEntities(orcEntity.getClass(), orcEntity.getBoundingBox().expand(30.0D, 8.0D, 30.0D));
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getSize() == 3) {
                            this.orcLeader = list.get(i);
                            return true;
                        }
                    }
                    this.checkSurroundingDelay = this.getSurroundingSearchDelay(orcEntity);
                }
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            if (this.orcLeader != null || this.orcEntity.getAttacker() == null) {
                return !this.isCloseEnoughToOrcLeader();
            } else
                return false;
        }

        @Override
        public void start() {
            this.moveDelay = 0;
        }

        @Override
        public void stop() {
            this.checkSurroundingDelay = this.getSurroundingSearchDelay(orcEntity);
        }

        @Override
        public void tick() {
            if (--this.moveDelay <= 0) {
                this.moveDelay = 20;
                if (this.orcLeader != null) {
                    this.orcEntity.getNavigation().startMovingTo(this.orcLeader, 1.0D);
                }
            }
        }
    }

    public class WanderAroundVeryFarGoal extends WanderAroundGoal {
        protected final float probability;
        private final OrcEntity orcEntity;

        public WanderAroundVeryFarGoal(OrcEntity orcEntity, double speed, float probability) {
            super(orcEntity, speed);
            this.probability = probability;
            this.orcEntity = orcEntity;
        }

        @Nullable
        @Override
        protected Vec3d getWanderTarget() {
            if (this.orcEntity.isInsideWaterOrBubbleColumn()) {
                Vec3d vec3d = NoPenaltyTargeting.find(this.orcEntity, 15, 7);
                return vec3d == null ? super.getWanderTarget() : vec3d;
            } else {
                return this.orcEntity.getRandom().nextFloat() >= this.probability && orcEntity.isBigOrc() ? NoPenaltyTargeting.find(this.orcEntity, 30, 5) : super.getWanderTarget();
            }
        }
    }

    private class OrcEscapeGoal extends Goal {
        protected final OrcEntity orcEntity;
        protected final double speed;
        protected double targetX;
        protected double targetY;
        protected double targetZ;

        public OrcEscapeGoal(OrcEntity orcEntity, double speed) {
            this.orcEntity = orcEntity;
            this.speed = speed;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (this.orcEntity.isBigOrc() || this.orcEntity.inventory.isEmpty() || this.orcEntity.world.getClosestPlayer(this.orcEntity, 16D) == null) {
                return false;
            } else {
                return this.findTarget();
            }
        }

        protected boolean findTarget() {
            Vec3d vec3d = NoPenaltyTargeting.find(this.orcEntity, 14, 4);
            if (vec3d == null) {
                return false;
            } else {
                this.targetX = vec3d.x;
                this.targetY = vec3d.y;
                this.targetZ = vec3d.z;
                return true;
            }
        }

        @Override
        public void start() {
            this.orcEntity.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
        }

        @Override
        public boolean shouldContinue() {
            if (this.orcEntity.world.getClosestPlayer(this.orcEntity, 18D) == null)
                return false;
            else
                return !this.orcEntity.getNavigation().isIdle();
        }

    }

}