package net.adventurez.entity;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class OrcEntity extends Monster {
    public static final EntityDataAccessor<Integer> ORK_SIZE;
    public static final EntityDataAccessor<Boolean> DOUBLE_HAND_ATTACK;
    public static final EntityDataAccessor<Integer> INVENTORY_ITEM_ID;

    public final SimpleContainer inventory = new SimpleContainer(1);

    @SuppressWarnings("deprecation")
    public OrcEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.fixupDimensions();
    }

    public static AttributeSupplier.Builder createOrcAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.225D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D).add(Attributes.ATTACK_DAMAGE, 7.0D).add(Attributes.ATTACK_KNOCKBACK, 1D)
                .add(Attributes.FOLLOW_RANGE, 38.0D).add(Attributes.ARMOR, 1.0D);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new OrcEscapeGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new OrcAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new OrcGroupGoal(this));
        this.goalSelector.addGoal(4, new WanderAroundVeryFarGoal(this, 0.9D, 0.7F));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.9D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WanderingTrader.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(4, (new HurtByTargetGoal(this, new Class[]{OrcEntity.class})));
    }

    public static boolean canSpawn(EntityType<OrcEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return (checkMonsterSpawnRules(type, world, spawnReason, pos, random) && world.canSeeSky(pos)) || spawnReason == MobSpawnType.SPAWNER;
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ORK_SIZE, 1);
        builder.define(DOUBLE_HAND_ATTACK, false);
        builder.define(INVENTORY_ITEM_ID, -1);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSize(tag.getInt("OrkSize"), false);
        if (tag.contains("OrcItem", 10)) {
            this.inventory.setItem(0, ItemStack.parse(this.registryAccess(), tag.getCompound("OrcItem")).get());
            this.setItemId(this.inventory.getItem(0).getItem());
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("OrkSize", this.getSize());
        if (!this.inventory.getItem(0).isEmpty()) {
            tag.put("OrcItem", this.inventory.getItem(0).save(this.registryAccess()));
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        if (!this.inventory.getItem(0).isEmpty()) {
            return false;
        }
        return true;
    }

    public int getSize() {
        return (Integer) this.entityData.get(ORK_SIZE);
    }

    public void setSize(int size, boolean healOrc) {
        this.entityData.set(ORK_SIZE, size);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double) (16D + (float) size * 6D));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) (0.26F - 0.012F * (float) size));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double) size * 3D + 2D);
        if (healOrc) {
            this.setHealth(this.getMaxHealth());
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        if (ORK_SIZE.equals(data)) {
            this.refreshDimensions();
            this.setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
        }

        super.onSyncedDataUpdated(data);
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F * (float) this.getSize();
    }

    @Override
    public float getVoicePitch() {
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

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        this.setSize(this.getRandom().nextInt(3) + 1, true);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose).scale(0.55F * (float) this.getSize());
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().getRandom().nextInt(3) == 0 && !this.entityData.get(DOUBLE_HAND_ATTACK)) {
            entityData.set(DOUBLE_HAND_ATTACK, true);
        } else {
            entityData.set(DOUBLE_HAND_ATTACK, false);
        }
        return !this.isInvulnerableTo(source) && super.hurt(source, amount);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4;
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        if (this.inventory != null && !this.inventory.isEmpty()) {
            this.spawnAtLocation(this.inventory.getItem(0));
        }
    }

    public boolean isBigOrc() {
        if (this.getSize() == 3) {
            return true;
        } else {
            return false;
        }
    }

    private void setItemId(Item item) {
        this.entityData.set(INVENTORY_ITEM_ID, BuiltInRegistries.ITEM.getId(item));
    }

    static {
        ORK_SIZE = SynchedEntityData.defineId(OrcEntity.class, EntityDataSerializers.INT);
        DOUBLE_HAND_ATTACK = SynchedEntityData.defineId(OrcEntity.class, EntityDataSerializers.BOOLEAN);
        INVENTORY_ITEM_ID = SynchedEntityData.defineId(OrcEntity.class, EntityDataSerializers.INT);
    }

    private class OrcAttackGoal extends MeleeAttackGoal {
        private final OrcEntity orcEntity;

        public OrcAttackGoal(OrcEntity orcEntity, double speed, boolean pauseWhenMobIdle) {
            super(orcEntity, speed, pauseWhenMobIdle);
            this.orcEntity = orcEntity;
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            if (canPerformAttack(target) && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(target);
                if (ConfigInit.CONFIG.allow_orc_stealing && !this.orcEntity.isBigOrc() && this.orcEntity.inventory.isEmpty() && this.orcEntity.level().getRandom().nextFloat() <= 0.3F && target instanceof Player playerEntity) {
                    for (int i = 45; i > 0; i--) {
                        if (!playerEntity.getInventory().getItem(i).isEmpty() && playerEntity.getInventory().getItem(i).getItem() instanceof TieredItem
                                && playerEntity.level().getRandom().nextFloat() < 0.3F) {
                            this.orcEntity.inventory.setItem(0, playerEntity.getInventory().getItem(i));
                            this.orcEntity.setItemId(this.orcEntity.inventory.getItem(0).getItem());
                            playerEntity.getInventory().setItem(i, ItemStack.EMPTY);
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
        public boolean canUse() {
            if (this.orcEntity.getSize() == 3 || this.orcEntity.isAggressive()) {
                return false;
            }
            if (this.orcLeader != null && this.isCloseEnoughToOrcLeader()) {
                return false;
            }
            if (this.checkSurroundingDelay > 0) {
                --this.checkSurroundingDelay;
                return false;
            } else {
                List<? extends OrcEntity> list = orcEntity.level().getEntitiesOfClass(orcEntity.getClass(), orcEntity.getBoundingBox().inflate(30.0D, 8.0D, 30.0D));
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
        public boolean canContinueToUse() {
            if (this.orcLeader != null || this.orcEntity.getLastHurtByMob() == null) {
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
                    this.orcEntity.getNavigation().moveTo(this.orcLeader, 1.0D);
                }
            }
        }
    }

    public class WanderAroundVeryFarGoal extends RandomStrollGoal {
        protected final float probability;
        private final OrcEntity orcEntity;

        public WanderAroundVeryFarGoal(OrcEntity orcEntity, double speed, float probability) {
            super(orcEntity, speed);
            this.probability = probability;
            this.orcEntity = orcEntity;
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            if (this.orcEntity.isInWaterOrBubble()) {
                Vec3 vec3d = DefaultRandomPos.getPos(this.orcEntity, 15, 7);
                return vec3d == null ? super.getPosition() : vec3d;
            } else {
                return this.orcEntity.getRandom().nextFloat() >= this.probability && orcEntity.isBigOrc() ? DefaultRandomPos.getPos(this.orcEntity, 30, 5) : super.getPosition();
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
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.orcEntity.isBigOrc() || this.orcEntity.inventory.isEmpty() || this.orcEntity.level().getNearestPlayer(this.orcEntity, 16D) == null) {
                return false;
            } else {
                return this.findTarget();
            }
        }

        protected boolean findTarget() {
            Vec3 vec3d = DefaultRandomPos.getPos(this.orcEntity, 14, 4);
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
            this.orcEntity.getNavigation().moveTo(this.targetX, this.targetY, this.targetZ, this.speed);
        }

        @Override
        public boolean canContinueToUse() {
            if (this.orcEntity.level().getNearestPlayer(this.orcEntity, 18D) == null) {
                return false;
            } else {
                return !this.orcEntity.getNavigation().isDone();
            }
        }

    }

}
