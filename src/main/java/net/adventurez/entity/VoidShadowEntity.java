package net.adventurez.entity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.Nullable;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.TagInit;
import net.adventurez.mixin.accessor.FallingBlockAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.voidz.block.VoidBlock;
import net.voidz.init.BlockInit;
// import net.minecraft.entity.mob.ZombieEntity;
// import net.minecraft.entity.ai.goal.MeleeAttackGoal;
// import net.minecraft.entity.mob.PhantomEntity;
//import net.minecraft.entity.mob.GhastEntity;
//import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.ai.goal.AttackGoal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.mob.AbstractPiglinEntity;
//this.goalSelector.add(3, new FleeEntityGoal(this, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
//this.targetSelector.add(3, new FollowTargetGoal<>(this, AbstractPiglinEntity.class, true));
import net.voidz.dimension.VoidPlacementHandler;

public class VoidShadowEntity extends FlyingEntity implements Monster {

    public static final TrackedData<Boolean> HALF_LIFE_CHANGE = DataTracker.registerData(VoidShadowEntity.class,
            TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_THROWING_BLOCKS = DataTracker.registerData(VoidShadowEntity.class,
            TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> HOVERING_MAGIC_HANDS = DataTracker.registerData(VoidShadowEntity.class,
            TrackedDataHandlerRegistry.BOOLEAN);

    private final ServerBossBar bossBar;
    private boolean circling;
    private int portalX;
    private int portalY;
    private int portalZ;
    private boolean isHalfLife;

    public VoidShadowEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = (ServerBossBar) (new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE,
                BossBar.Style.PROGRESS));
        this.experiencePoints = 100;
        this.moveControl = new VoidShadowEntity.VoidShadowMoveControl(this);
    }
    // Invisible
    // Set blocks to infested blocks
    // Remove blocks
    // Freeze player
    // Throw bunch of blocks
    // Send itself on small variants with low hp
    // Gravity off/on or set volicity y -2
    // Blinding
    // Spawn above players falling blocks
    // No elytra
    // Spawn mirror ghost, throw chunks of end stone
    // Spawn many small shadowentities which shoot void projectiles

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new VoidShadowEntity.FlyGoal(this));
        this.goalSelector.add(4, new VoidShadowEntity.LookGoal(this));
        this.goalSelector.add(3, new VoidShadowEntity.ThrowBlocks(this));
        // this.goalSelector.add(0, new VoidShadowEntity.DestroyBlocksAttack(this));

        // this.goalSelector.add(1, new AttackGoal(this));
        // this.goalSelector.add(6, new WanderAroundGoal(this, 0.9D));
        // this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F,
        // 1.0F));
        // this.goalSelector.add(8, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        // this.targetSelector.add(1, (new RevengeGoal(this, new Class[] {
        // NecromancerEntity.class })));
        // this.targetSelector.add(2, (new RevengeGoal(this, new Class[] {
        // PiglinEntity.class })));
        // this.targetSelector.add(4, new FollowTargetGoal<>(this, PlayerEntity.class,
        // true));
    }

    public static DefaultAttributeContainer.Builder createVoidShadowAttributes() {

        // Speed was 0.3D before I changed it
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1000.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 80.0D);
    }

    @Override
    public void mobTick() {
        super.mobTick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        if (this.getHealth() < this.getMaxHealth() / 2) {
            if (!dataTracker.get(HALF_LIFE_CHANGE)) {
                dataTracker.set(HALF_LIFE_CHANGE, true);
            }
            if (!this.isHalfLife) {
                this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).setBaseValue((double) (6.0D));
                this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) 14.0D);
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue((double) 4.0D);
                this.isHalfLife = true;
            }

        }

    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(HALF_LIFE_CHANGE, false);
        dataTracker.startTracking(IS_THROWING_BLOCKS, false);
        dataTracker.startTracking(HOVERING_MAGIC_HANDS, false);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("VoidXPortal", portalX);
        tag.putInt("VoidYPortal", portalY);
        tag.putInt("VoidZPortal", portalZ);
        tag.putBoolean("ShadowCircling", this.circling);
        tag.putBoolean("ShadowIsHalfLife", this.isHalfLife);

    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
        this.portalX = tag.getInt("VoidXPortal");
        this.portalY = tag.getInt("VoidYPortal");
        this.portalZ = tag.getInt("VoidZPortal");
        this.circling = tag.getBoolean("ShadowCircling");
        this.isHalfLife = tag.getBoolean("ShadowIsHalfLife");
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public boolean canImmediatelyDespawn(double num) {
        return false;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove();
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    private boolean hasVoidMiddleCoordinates() {
        return this.portalX != 0 || this.portalY != 0 || this.portalZ != 0;
    }

    public void setVoidMiddle(int x, int y, int z) { // Set at VoidZ mod
        this.circling = true;
        this.portalX = x;
        this.portalY = y;
        this.portalZ = z;
    }

    public BlockPos getVoidMiddle() {
        return new BlockPos(this.portalX, this.portalY, this.portalZ);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {

        // Dont know if this is good TESTESTEST
        if (source.getAttacker() instanceof LivingEntity) {
            this.setTarget((LivingEntity) source.getAttacker());
        }
        if (source.isProjectile()) {
            if (source.getSource() instanceof ArrowEntity) {
                ArrowEntity arrowEntity = (ArrowEntity) source.getSource();
                if (arrowEntity.isGlowing()) {
                    return true;
                }
                source.getSource().remove();
                return false;
            }

        }
        return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
    }

    @Override
    public void updatePostDeath() {
        if (!this.world.isClient) {
            this.bossBar.setPercent(0.0F);
        }
        super.updatePostDeath();
    }

    // @Override
    // protected SoundEvent getAmbientSound() {
    // return SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
    // }

    // @Override
    // protected SoundEvent getHurtSound(DamageSource source) {
    // return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
    // }

    @Override
    protected float getSoundVolume() {
        return 5.0F;
    }

    static class FlyGoal extends Goal {
        private final VoidShadowEntity voidShadowEntity;

        public FlyGoal(VoidShadowEntity voidShadowEntity) {
            this.voidShadowEntity = voidShadowEntity;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        public boolean canStart() {
            MoveControl moveControl = this.voidShadowEntity.getMoveControl();
            if ((!moveControl.isMoving() || this.voidShadowEntity.getTarget() != null) && !voidShadowEntity.circling) {
                return true;
            } else {
                double d = moveControl.getTargetX() - this.voidShadowEntity.getX();
                double e = moveControl.getTargetY() - this.voidShadowEntity.getY();
                double f = moveControl.getTargetZ() - this.voidShadowEntity.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0D || g > 3600.0D;
            }
        }

        public boolean shouldContinue() {
            return false;
        }

        public void start() {
            if (this.voidShadowEntity.getTarget() != null
                    && this.voidShadowEntity.distanceTo(this.voidShadowEntity.getTarget()) > 30.0F) {
                BlockPos pos = this.voidShadowEntity.getTarget().getBlockPos();
                this.voidShadowEntity.getMoveControl().moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.01D);
            } else {
                // Random random = this.voidShadowEntity.getRandom();
                // double d = this.voidShadowEntity.getX() + (double) ((random.nextFloat() *
                // 2.0F - 1.0F) * 16.0F);
                // double e = this.voidShadowEntity.getY() + (double) ((random.nextFloat() *
                // 2.0F - 1.0F) * 16.0F);
                // double f = this.voidShadowEntity.getZ() + (double) ((random.nextFloat() *
                // 2.0F - 1.0F) * 16.0F);
                // this.voidShadowEntity.getMoveControl().moveTo(d, e, f, 0.01D);
            }
        }
    }

    static class VoidShadowMoveControl extends MoveControl {
        private final VoidShadowEntity voidShadowEntity;

        public VoidShadowMoveControl(VoidShadowEntity voidShadowEntity) {
            super(voidShadowEntity);
            this.voidShadowEntity = voidShadowEntity;
        }

        @Override
        public void tick() {
            if (voidShadowEntity.circling && voidShadowEntity.hasVoidMiddleCoordinates()) {
                BlockPos pos = voidShadowEntity.getVoidMiddle();
                Vec3d vec3d = new Vec3d((double) pos.getX() - this.voidShadowEntity.getX(),
                        (double) pos.getY() - this.voidShadowEntity.getY(),
                        (double) pos.getZ() - this.voidShadowEntity.getZ());
                vec3d = vec3d.normalize();
                Vec3d distanceVector = new Vec3d((double) this.voidShadowEntity.getX(), this.voidShadowEntity.getY(),
                        this.voidShadowEntity.getZ());
                if (distanceVector.distanceTo(new Vec3d(pos.getX(), pos.getY(), pos.getZ())) >= 72.5D) {
                    this.voidShadowEntity.setVelocity(this.voidShadowEntity.getVelocity().add(vec3d.multiply(0.1D)));
                } else {
                    this.voidShadowEntity
                            .setVelocity(this.voidShadowEntity.getVelocity().add(vec3d.multiply(0.1D).negate()));
                    // Max partly Vector is 0.21 ca
                }
                this.voidShadowEntity
                        .setVelocity(this.voidShadowEntity.getVelocity().add(vec3d.multiply(0.1D).rotateY(90F)));
                System.out.println(this.voidShadowEntity.getVelocity().length()+"::"+this.voidShadowEntity.getVelocity().lengthSquared());
                // 0.2950512766838074::0.08705526230430802 was max

            } else if (this.state == MoveControl.State.MOVE_TO) {
                Vec3d vec3d = new Vec3d(this.targetX - this.voidShadowEntity.getX(),
                        this.targetY - this.voidShadowEntity.getY(), this.targetZ - this.voidShadowEntity.getZ());
                vec3d = vec3d.normalize();
                this.voidShadowEntity.setVelocity(this.voidShadowEntity.getVelocity().add(vec3d.multiply(0.1D)));

                // Vec3d vec3d = new Vec3d((double) this.targetX - this.voidShadowEntity.getX(),
                // (double) this.targetY - this.voidShadowEntity.getY(),
                // (double) this.targetZ - this.voidShadowEntity.getZ());

            }
        }

    }

    static class LookGoal extends Goal {
        private final VoidShadowEntity voidShadowEntity;
        // EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL
        private static final TargetPredicate PLAYER_PREDICATE = (new TargetPredicate()).setBaseMaxDistance(80.0D)
                .includeHidden();

        public LookGoal(VoidShadowEntity voidShadowEntity) {
            this.voidShadowEntity = voidShadowEntity;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void tick() {
            if (voidShadowEntity.circling && voidShadowEntity.hasVoidMiddleCoordinates()) {
                BlockPos pos = voidShadowEntity.getVoidMiddle();
                Vec3d vec3d = new Vec3d((double) pos.getX() - this.voidShadowEntity.getX(),
                        (double) pos.getY() - this.voidShadowEntity.getY(),
                        (double) pos.getZ() - this.voidShadowEntity.getZ());
                this.voidShadowEntity.yaw = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F;
                this.voidShadowEntity.bodyYaw = this.voidShadowEntity.yaw;

                // Test
                this.voidShadowEntity.setTarget(
                        this.voidShadowEntity.world.getClosestPlayer(PLAYER_PREDICATE, this.voidShadowEntity));
                // System.out.print(this.voidShadowEntity.world.getClosestPlayer(PLAYER_PREDICATE,
                // this.voidShadowEntity));
            } else if (this.voidShadowEntity.getTarget() == null) {
                Vec3d vec3d = this.voidShadowEntity.getVelocity();
                this.voidShadowEntity.yaw = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F;
                this.voidShadowEntity.bodyYaw = this.voidShadowEntity.yaw;
            } else {
                LivingEntity livingEntity = this.voidShadowEntity.getTarget();
                if (livingEntity.squaredDistanceTo(this.voidShadowEntity) < 4096.0D) {
                    double e = livingEntity.getX() - this.voidShadowEntity.getX();
                    double f = livingEntity.getZ() - this.voidShadowEntity.getZ();
                    this.voidShadowEntity.yaw = -((float) MathHelper.atan2(e, f)) * 57.295776F;
                    this.voidShadowEntity.bodyYaw = this.voidShadowEntity.yaw;
                }
            }

        }
    }

    static class ThrowBlocks extends Goal {
        private final VoidShadowEntity voidShadow;
        private int throwTicker;
        private int throwBlocks;
        // Maybe a boolean to check while running is better than int

        public ThrowBlocks(VoidShadowEntity voidShadow) {
            this.voidShadow = voidShadow;
            // this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (livingEntity != null && this.voidShadow.distanceTo(livingEntity) > 5.0F
                    && this.voidShadow.distanceTo(livingEntity) < 30.0F) {
                // System.out.print("X:" + throwTicker + ":X");
                throwTicker++;
                return throwTicker >= 80;
            } else
                return false;

            // return livingEntity != null && this.voidShadow != null &&
            // livingEntity.isAlive() || 1.0D >= this.voidShadow
            // .squaredDistanceTo(livingEntity.getX(), livingEntity.getY(),
            // livingEntity.getZ());
        }

        // distance 1F per block :)
        @Override
        public void tick() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (livingEntity != null) {// !this.voidShadow.circling &&
                this.voidShadow.lookControl.lookAt(livingEntity, 1.0F, 1.0F);
            }
            throwBlocks++;
            if (throwBlocks >= 10) {
                BlockPos pos = this.voidShadow.getBlockPos();
                for (int i = 0; i < (this.voidShadow.isHalfLife ? 60 : 40); i++) {
                    if (!this.voidShadow.world.isClient) {
                        double random = this.voidShadow.world.random.nextDouble() + 0.3D;
                        double anotherRandom = this.voidShadow.world.random.nextDouble();
                        double anotherExtraRandom = this.voidShadow.world.random.nextDouble() - 0.5D;
                        double anotherExtraXXXRandom = this.voidShadow.world.random.nextDouble() - 0.5D;
                        Block block;
                        if (this.voidShadow.hasVoidMiddleCoordinates()) {
                            block = this.voidShadow.world.getBlockState(this.voidShadow.getVoidMiddle().north().down())
                                    .getBlock();
                        } else if (!this.voidShadow.world.getBlockState(pos.down(5)).isAir()) {
                            block = this.voidShadow.world.getBlockState(pos.down(5)).getBlock();
                        } else {
                            block = Blocks.STONE;
                        }
                        ThrownRockEntity thrownRockEntity = new ThrownRockEntity(this.voidShadow.world,
                                this.voidShadow);
                        Vec3d vec3d = new Vec3d(livingEntity.getX() - this.voidShadow.getX(),
                                livingEntity.getY() - this.voidShadow.getY(),
                                livingEntity.getZ() - this.voidShadow.getZ());
                        vec3d = vec3d.add(anotherExtraRandom * 12.0D, 5.0D * anotherRandom,
                                anotherExtraXXXRandom * 12.0D);
                        vec3d = vec3d.normalize();
                        thrownRockEntity.setVelocity(thrownRockEntity.getVelocity().add(vec3d.multiply(1.4D * random)));
                        thrownRockEntity.setItem(new ItemStack(block.asItem()));
                        this.voidShadow.world.spawnEntity(thrownRockEntity);

                    }
                }
                this.voidShadow.world.playSoundFromEntity((PlayerEntity) null, this.voidShadow,
                        SoundInit.ROCK_THROW_EVENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
                throwBlocks = 0;
                this.stop();
            }

        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (livingEntity == null || !livingEntity.isAlive()) {
                return false;
            } else
                return this.voidShadow.distanceTo(livingEntity) > 5.0F
                        && this.voidShadow.distanceTo(livingEntity) < 30.0F && throwTicker == 0;
        }

        // return super.shouldContinue()
        // && (this.shadow ||
        // this.voidShadow.squaredDistanceTo(this.voidShadow.getTarget()) > 8.0D);
        // }
        @Override
        public void start() {
            this.voidShadow.dataTracker.set(IS_THROWING_BLOCKS, true);
            this.voidShadow.getNavigation().stop();
            throwTicker = 0;
            System.out.print("START:");
        }

        @Override
        public void stop() {
            this.voidShadow.dataTracker.set(IS_THROWING_BLOCKS, false);
            // Test for null setting target
            // this.voidShadow.setTarget((LivingEntity) null);
            throwTicker++;
            System.out.print("STOP:");
        }

    }

    // Magic sounds would be great
    static class DestroyBlocksAttack extends Goal {
        private final VoidShadowEntity voidShadow;
        private int throwTicker;
        private int throwBlocks;
        private final TargetPredicate PLAYER_PREDICATE = (new TargetPredicate()).setBaseMaxDistance(64.0D);
        private List<BlockPos> blockPosList = new ArrayList<BlockPos>();

        public DestroyBlocksAttack(VoidShadowEntity voidShadow) {
            this.voidShadow = voidShadow;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            // System.out.print("X");
            LivingEntity livingEntity = this.voidShadow.getTarget();
            if (livingEntity != null) {
                throwTicker++;

                return throwTicker >= 40;
            } else
                return false;
        }

        @Override
        public void tick() {
            throwBlocks++;
            List<BlockPos> playerBlockPosList = new ArrayList<BlockPos>();
            if (throwBlocks == 40) {
                Box box = new Box(this.voidShadow.getBlockPos());
                List<PlayerEntity> playerList = this.voidShadow.world.getPlayers(PLAYER_PREDICATE, this.voidShadow,
                        box.expand(80D));
                for (int i = 0; i < playerList.size(); ++i) {
                    PlayerEntity playerEntity = playerList.get(i);
                    playerBlockPosList.add(playerEntity.getBlockPos());
                    blockPosList.add(playerEntity.getBlockPos());
                }
                for (int u = 0; u < playerBlockPosList.size(); ++u) {
                    BlockPos pos = playerBlockPosList.get(u);
                    for (float k = 0.0F; k < Math.PI * 2; k += (float) Math.PI / 16F) {
                        for (int i = 0; i < 4; i++) {
                            BlockPos blockPos = pos.add(Math.sin(k) * i, 0, Math.cos(k) * i);
                            if (!blockPosList.contains(blockPos)
                                    && !this.voidShadow.world.getBlockState(blockPos).isAir() && !this.voidShadow.world
                                            .getBlockState(blockPos).isIn(TagInit.UNBREAKABLE_BLOCKS)) {
                                blockPosList.add(pos.add(Math.sin(k) * i, 0, Math.cos(k) * i));
                            }
                        }
                    }
                }
                for (int u = 0; u < blockPosList.size(); ++u) {
                    if (FabricLoader.getInstance().isModLoaded("voidz")) {
                        this.voidShadow.world.setBlockState(blockPosList.get(u),
                                BlockInit.VOID_BLOCK.getDefaultState().with(VoidBlock.ACTIVATED, true));
                    } else {
                        this.voidShadow.world.breakBlock(blockPosList.get(u), false);
                    }
                }

            }
            if (throwBlocks == 118) {
                for (int u = 0; u < blockPosList.size(); ++u) {
                    this.voidShadow.world.breakBlock(blockPosList.get(u), false);
                }
                this.voidShadow.dataTracker.set(HOVERING_MAGIC_HANDS, false);
            }
            if (throwBlocks >= 400) {
                throwBlocks = 0;
                this.stop();
            }

        }

        @Override
        public boolean shouldContinue() {
            // LivingEntity livingEntity = this.voidShadow.getTarget();
            // if (livingEntity == null || !livingEntity.isAlive()) {
            // System.out.print(throwTicker + ":");
            // return false;
            // } else
            return throwTicker == 0;
        }

        @Override
        public void start() {
            this.voidShadow.dataTracker.set(HOVERING_MAGIC_HANDS, true);
            throwTicker = 0;
        }

        @Override
        public void stop() {
            throwTicker++;
            for (int u = 0; u < blockPosList.size(); ++u) {
                if (FabricLoader.getInstance().isModLoaded("voidz")) {
                    this.voidShadow.world.setBlockState(blockPosList.get(u), BlockInit.VOID_BLOCK.getDefaultState());
                } else {
                    this.voidShadow.world.setBlockState(blockPosList.get(u), Blocks.COBBLESTONE.getDefaultState());
                }
            }
            if (!blockPosList.isEmpty()) {
                blockPosList.clear();
            }
            System.out.print("STOP:");
        }

    }

    // static class AttackGoal extends Goal {
    // private final VoidShadowEntity voidShadow;
    // private int beamTicks;
    // private final boolean shadow;

    // public AttackGoal(VoidShadowEntity voidShadow) {
    // this.voidShadow = voidShadow;
    // this.shadow = voidShadow instanceof VoidShadowEntity;
    // this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    // }

    // public boolean canStart() {
    // LivingEntity livingEntity = this.voidShadow.getTarget();
    // if (livingEntity == null) {
    // return false;
    // }
    // return livingEntity != null && this.voidShadow != null &&
    // livingEntity.isAlive() || 1.0D >= this.voidShadow
    // .squaredDistanceTo(livingEntity.getX(), livingEntity.getY(),
    // livingEntity.getZ());
    // }

    // // public boolean shouldContinue() {
    // // return super.shouldContinue()
    // // && (this.shadow ||
    // // this.voidShadow.squaredDistanceTo(this.voidShadow.getTarget()) > 8.0D);
    // // }

    // public void start() {
    // this.beamTicks = -20;
    // // this.voidShadow.getNavigation().stop();
    // this.voidShadow.getLookControl().lookAt(this.voidShadow.getTarget(), 90.0F,
    // 90.0F);
    // // this.voidShadow.velocityDirty = true;
    // }

    // public void stop() {
    // this.voidShadow.setTarget((LivingEntity) null);
    // }

    // public void tick() {
    // LivingEntity livingEntity = this.voidShadow.getTarget();
    // this.voidShadow.getNavigation().stop();
    // this.voidShadow.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
    // if (!this.voidShadow.canSee(livingEntity)) {
    // this.voidShadow.setTarget((LivingEntity) null);
    // } else {
    // ++this.beamTicks;
    // if (this.beamTicks >= 100) {
    // this.voidShadow.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
    // double d = this.voidShadow.squaredDistanceTo(livingEntity.getX(),
    // livingEntity.getY(),
    // livingEntity.getZ());
    // this.attack(livingEntity, d);
    // this.voidShadow.setTarget((LivingEntity) null);

    // super.tick();
    // }

    // }
    // }

    // public void attack(LivingEntity target, double squaredDistance) {
    // double d = 8.0D;
    // if (squaredDistance <= d) {

    // this.voidShadow.swingHand(Hand.MAIN_HAND);
    // this.voidShadow.tryAttack(target);
    // }

    // }

    // }
}
