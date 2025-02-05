package net.adventurez.entity;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.TagInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.data.DataTracker.Builder;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.entity.boss.BossBar;

public class BlackstoneGolemEntity extends HostileEntity {

    public static final TrackedData<Integer> THROW_COOLDOWN;
    public static final TrackedData<Boolean> INVULNERABLE;
    public static final TrackedData<Integer> INVULNERABLE_TIMER;
    public static final TrackedData<Integer> LAVA_TEXTURE;
    public static final TrackedData<Boolean> HALF_LIFE_CHANGE;
    private static final Identifier WALKING_SPEED_INCREASE_ID;
    private static final EntityAttributeModifier WALKING_SPEED_INCREASE;
    private static final Predicate<Entity> NOT_STONEGOLEM = (entity) -> {
        return entity.isAlive() && !(entity instanceof BlackstoneGolemEntity);
    };
    private int cooldown = 0;
    private int thrownStoneCooldown = 120;
    private int attackTick;
    private int stunTick;
    private int roarTick;
    private int powerPhaseActivate = 0;
    private int lavaRegenerateLife = 0;

    private final ServerBossBar bossBar;

    private final boolean isDungeonZLoaded = FabricLoader.getInstance().isModLoaded("dungeonz");

    public BlackstoneGolemEntity(EntityType<? extends BlackstoneGolemEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 200;
        this.bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.RED, BossBar.Style.PROGRESS);
    }

    public static DefaultAttributeContainer.Builder createStoneGolemAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 600.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2.5D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 14.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.8D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new BlackstoneGolemEntity.AttackGoal());
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.6D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 60.0F));
        this.goalSelector.add(4, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putInt("AttackTick", this.attackTick);
        tag.putInt("StunTick", this.stunTick);
        tag.putInt("RoarTick", this.roarTick);
        tag.putInt("Invul", this.getInvulnerableTimer());
        tag.putInt("LavaTexture", this.getLavaTexture());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.attackTick = tag.getInt("AttackTick");
        this.stunTick = tag.getInt("StunTick");
        this.roarTick = tag.getInt("RoarTick");
        this.setInvulTimer(tag.getInt("Invul"));
        this.setLavaTexture(tag.getInt("LavaTexture"));
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(Text name) {
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
    protected void initDataTracker(Builder builder) {
        super.initDataTracker(builder);
        builder.add(THROW_COOLDOWN, 0);
        builder.add(INVULNERABLE, true);
        builder.add(LAVA_TEXTURE, 400);
        builder.add(HALF_LIFE_CHANGE, false);
        builder.add(INVULNERABLE_TIMER, 0);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new BlackstoneGolemEntity.Navigation(this, world);
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public void mobTick() {
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    protected float getSoundVolume() {
        return 1.3F;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            if (this.getInvulnerableTimer() > 0) {
                this.setInvulTimer(this.getInvulnerableTimer() - 1);
            }
            if (this.getInvulnerableTimer() == 0) {
                if (this.getLavaTexture() < 400) {
                    this.setLavaTexture(this.getLavaTexture() + 1);
                }
                if (dataTracker.get(INVULNERABLE)) {
                    dataTracker.set(INVULNERABLE, false);
                    this.playSound(SoundInit.GOLEM_AWAKENS_EVENT, 2.0F, 1.0F);
                }
            }
            if (this.getHealth() <= this.getMaxHealth() / 2) {
                if (this.powerPhaseActivate <= 80) {
                    this.powerPhaseActivate++;
                    if (this.powerPhaseActivate == 1 || this.powerPhaseActivate == 2) {
                        this.setAiDisabled(true);
                    }
                    if (this.powerPhaseActivate == 78 || this.powerPhaseActivate == 79) {
                        this.setAiDisabled(false);
                        this.roar();
                    }
                }
                if (this.powerPhaseActivate == 80) {
                    this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
                    this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).setBaseValue(4.0D);
                    dataTracker.set(HALF_LIFE_CHANGE, true);
                }
            } else if (this.isInLava() && this.getHealth() < this.getMaxHealth()) {
                this.lavaRegenerateLife++;
                if (this.lavaRegenerateLife >= 200) {
                    this.setHealth(this.getHealth() + 2F);
                    this.lavaRegenerateLife = 0;
                }
            }
            if (this.horizontalCollision && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                boolean bl = false;
                Box box = this.getBoundingBox().expand(0.25D);
                Iterator<BlockPos> iterator = BlockPos.iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY + 0.25D), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX),
                        MathHelper.floor(box.maxY + 0.4D), MathHelper.floor(box.maxZ)).iterator();

                while (true) {
                    BlockPos blockPos = null;
                    Block block = null;
                    BlockState blockState = null;

                    if (!iterator.hasNext()) {
                        if (!bl && this.isOnGround()) {
                            this.jump();
                        }
                        break;
                    }

                    blockPos = iterator.next();
                    blockState = this.getWorld().getBlockState(blockPos);
                    block = blockState.getBlock();

                    if (block instanceof Block && !blockState.isIn(TagInit.UNBREAKABLE_BLOCKS)) {
                        if (isDungeonZLoaded && this.getWorld().getRegistryKey().getValue().equals(Identifier.of("dungeonz", "dungeon"))) {
                            break;
                        }
                        bl = this.getWorld().breakBlock(blockPos, false, this) || bl;
                    }
                }
            }

            if (this.roarTick > 0) {
                --this.roarTick;
                if (this.roarTick == 10) {
                    this.roar();
                }
            }

            if (this.attackTick > 0) {
                --this.attackTick;
            }

            if (this.stunTick > 0) {
                --this.stunTick;
                this.spawnStunnedParticles();
                if (this.stunTick == 0) {
                    this.playSound(SoundInit.GOLEM_ROAR_EVENT, 1.0F, 1.0F);
                    this.roarTick = 30;
                }
            }
            if (this.getTarget() != null && this.canSee(this.getTarget())) {
                if (this.squaredDistanceTo(getTarget()) < 1400D && this.squaredDistanceTo(getTarget()) > 100D) {
                    dataTracker.set(THROW_COOLDOWN, cooldown);
                    this.cooldown++;
                    if (cooldown == thrownStoneCooldown - 10) {
                        this.playSound(SoundInit.GOLEM_ROAR_EVENT, 1F, 1F);
                        throwRock(this.getTarget());
                    }
                    if (cooldown >= thrownStoneCooldown) {
                        this.cooldown = -80;
                    }
                }
            }
            if (this.isOnSoulSpeedBlock() && !this.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKING_SPEED_INCREASE_ID)) {
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(WALKING_SPEED_INCREASE);
            } else if (!this.isOnSoulSpeedBlock() && this.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKING_SPEED_INCREASE_ID)) {
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(WALKING_SPEED_INCREASE_ID);
            }
            if (this.isInLava()) {
                this.setVelocity(this.getVelocity().multiply(1.9D));
            }
        }
    }

    private boolean isOnSoulSpeedBlock() {
        return this.getWorld().getBlockState(this.getVelocityAffectingPos()).isIn(BlockTags.SOUL_SPEED_BLOCKS);
    }

    private void spawnStunnedParticles() {
        if (this.random.nextInt(6) == 0) {
            double d = this.getX() - (double) this.getWidth() * Math.sin((double) (this.bodyYaw * 0.017453292F)) + (this.random.nextDouble() * 0.6D - 0.3D);
            double e = this.getY() + (double) this.getHeight() - 0.3D;
            double f = this.getZ() + (double) this.getWidth() * Math.cos((double) (this.bodyYaw * 0.017453292F)) + (this.random.nextDouble() * 0.6D - 0.3D);
            this.getWorld().addParticle(EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, 0.49803922F, 0.5137255F, 0.57254905F), d, e, f, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.attackTick > 0 || this.stunTick > 0 || this.roarTick > 0 || (this.cooldown > thrownStoneCooldown - 30 && this.cooldown > 0)
                || this.getDataTracker().get(INVULNERABLE);
    }

    @Override
    public boolean canSee(Entity entity) {
        return this.stunTick <= 0 && this.roarTick <= 0 ? super.canSee(entity) : false;
    }

    @Override
    protected void knockback(LivingEntity target) {
        if (this.roarTick == 0) {
            if (this.random.nextDouble() < 0.5D) {
                this.stunTick = 40;
                this.playSound(SoundInit.GOLEM_IDLE_EVENT, 1.0F, 1.0F);
                this.getWorld().sendEntityStatus(this, (byte) 39);
                target.pushAwayFrom(this);
            } else {
                this.knockBack(target);
            }

            target.velocityModified = true;
        }

    }

    private void roar() {
        if (this.isAlive()) {
            List<LivingEntity> list = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(5.0D), NOT_STONEGOLEM);

            LivingEntity entity;
            for (Iterator<LivingEntity> var2 = list.iterator(); var2.hasNext(); this.knockBack(entity)) {
                entity = (LivingEntity) var2.next();
                entity.damage(this.getDamageSources().mobAttack(this), 10.0F);
                entity.setVelocity(entity.getVelocity().add(0.0D, 0.63D, 0.0D));
            }

            Vec3d vec3d = this.getBoundingBox().getCenter();

            for (int i = 0; i < 50; ++i) {
                double d = this.random.nextGaussian() * 0.2D;
                double e = this.random.nextGaussian() * 0.2D;
                double f = this.random.nextGaussian() * 0.2D;
                this.getWorld().addParticle(ParticleTypes.POOF, vec3d.x, vec3d.y, vec3d.z, d, e, f);
            }
        }

    }

    private void knockBack(Entity entity) {
        double d = entity.getX() - this.getX();
        double e = entity.getZ() - this.getZ();
        double f = Math.max(d * d + e * e, 0.001D);
        entity.addVelocity(d / f * 4.0D, 0.2D, e / f * 4.0D);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {

        if (status == 4) {
            this.attackTick = 10;
            this.playSound(SoundInit.GOLEM_HIT_EVENT, 1.0F, 1.0F);
        } else if (status == 39) {
            this.stunTick = 40;
        }

        super.handleStatus(status);
    }

    @Environment(EnvType.CLIENT)
    public int getAttackTick() {
        return this.attackTick;
    }

    @Environment(EnvType.CLIENT)
    public int getStunTick() {
        return this.stunTick;
    }

    @Environment(EnvType.CLIENT)
    public int getRoarTick() {
        return this.roarTick;
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.attackTick = 10;
        this.getWorld().sendEntityStatus(this, (byte) 4);
        this.playSound(SoundInit.GOLEM_HIT_EVENT, 1.0F, 1.0F);
        if (target instanceof LivingEntity)
            ((LivingEntity) target).setOnFireFor(3 + this.getWorld().getRandom().nextInt(6));
        return super.tryAttack(target);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getDataTracker().get(INVULNERABLE)) {
            return null;
        } else
            return SoundInit.GOLEM_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.GOLEM_HIT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.GOLEM_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.GOLEM_WALK_EVENT, 0.15F, 1.0F);
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return !world.containsFluid(this.getBoundingBox());
    }

    @Override
    public boolean canImmediatelyDespawn(double num) {
        return false;
    }

    @Override
    public void checkDespawn() {
        if (this.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }

    }

    @Override
    public boolean canUsePortals(boolean allowVehicles) {
        return super.canUsePortals(allowVehicles);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return super.damage(source, amount);
        } else if (this.getDataTracker().get(INVULNERABLE)) {
            return false;
        } else {
            if (source.getSource() instanceof LivingEntity attacker) {
                this.setAttacker(attacker);
                this.setTarget(attacker);
                if (this.getWorld().getRandom().nextFloat() <= 0.05F) {
                    MiniBlackstoneGolemEntity smallStoneGolemEntity = EntityInit.MINI_BLACKSTONE_GOLEM.create(this.getWorld());
                    smallStoneGolemEntity.refreshPositionAndAngles(this.getBlockPos(), this.getWorld().getRandom().nextFloat() * 360F, 0.0F);
                    this.getWorld().spawnEntity(smallStoneGolemEntity);
                }
            }
            return super.damage(source, amount);
        }
    }

    private void throwRock(LivingEntity target) {
        Vec3d vec3d_1 = this.getRotationVec(1.0F);
        double x_vector;
        double z_vector;
        if (vec3d_1.x < 0 && vec3d_1.z < 0) {
            x_vector = vec3d_1.x + 1.8D;
            z_vector = vec3d_1.z - 2.8D;
        } else if (vec3d_1.x < 0 && vec3d_1.z > 0) {
            x_vector = vec3d_1.x - 1.8D;
            z_vector = vec3d_1.z - 2.8D;
        } else if (vec3d_1.x > 0 && vec3d_1.z < 0) {
            x_vector = vec3d_1.x + 1.8D;
            z_vector = vec3d_1.z + 2.8D;
        } else if (vec3d_1.x > 0 && vec3d_1.z > 0) {
            x_vector = vec3d_1.x - 1.8D;
            z_vector = vec3d_1.z + 2.8D;
        } else {
            x_vector = vec3d_1.x + 1.8D;
            z_vector = vec3d_1.z - 2.8D;
        }
        double x = target.getX() - this.getX() - x_vector;
        double y = target.getBodyY(1D) - this.getBodyY(0.1D);
        double z = target.getZ() - this.getZ() - z_vector;
        ThrownRockEntity thrownRockEntity = new ThrownRockEntity(this.getWorld(), this.getX() + x_vector, this.getY() + 0.5D, this.getZ() + z_vector);
        thrownRockEntity.setOwner(this);
        if (!this.getWorld().isClient()) {
            if (this.squaredDistanceTo(getTarget()) < 800D) {
                thrownRockEntity.setVelocity(x, y, z, 1.8F, 3.0F);
                this.getWorld().spawnEntity(thrownRockEntity);
            } else {
                thrownRockEntity.setVelocity(x, y + 4D, z, 1.8F, 2.0F);
                this.getWorld().spawnEntity(thrownRockEntity);
            }
        }
    }

    public void sendtoEntity() {
        this.setInvulTimer(220);
        this.setLavaTexture(0);
    }

    public int getInvulnerableTimer() {
        return (Integer) this.dataTracker.get(INVULNERABLE_TIMER);
    }

    public void setInvulTimer(int ticks) {
        this.dataTracker.set(INVULNERABLE_TIMER, ticks);
    }

    public int getLavaTexture() {
        return (Integer) this.dataTracker.get(LAVA_TEXTURE);
    }

    public void setLavaTexture(int ticks) {
        this.dataTracker.set(LAVA_TEXTURE, ticks);
    }

    @Override
    public void onDeath(DamageSource source) {
        if (!this.getWorld().isClient()) {
            MiniBlackstoneGolemEntity smallStoneGolemEntity = (MiniBlackstoneGolemEntity) EntityInit.MINI_BLACKSTONE_GOLEM.create(this.getWorld());
            MiniBlackstoneGolemEntity smallStoneGolemEntitySecond = (MiniBlackstoneGolemEntity) EntityInit.MINI_BLACKSTONE_GOLEM.create(this.getWorld());
            smallStoneGolemEntity.refreshPositionAndAngles(this.getBlockPos().east(), this.getWorld().getRandom().nextFloat() * 360F, 0.0F);
            smallStoneGolemEntitySecond.refreshPositionAndAngles(this.getBlockPos().west(), this.getWorld().getRandom().nextFloat() * 360F, 0.0F);
            this.getWorld().spawnEntity(smallStoneGolemEntity);
            this.getWorld().spawnEntity(smallStoneGolemEntitySecond);
        }
        super.onDeath(source);
    }

    private static class PathNodeMaker extends LandPathNodeMaker {
        private PathNodeMaker() {
        }

        // @Override
        // protected PathNodeType adjustNodeType(BlockView world, BlockPos pos, PathNodeType type) {
        // return type == PathNodeType.LAVA ? PathNodeType.OPEN : super.adjustNodeType(world, pos, type);
        // }
    }

    private static class Navigation extends MobNavigation {
        public Navigation(MobEntity mobEntity, World world) {
            super(mobEntity, world);
        }

        @Override
        protected boolean canWalkOnPath(PathNodeType pathType) {
            return pathType != PathNodeType.LAVA && pathType != PathNodeType.DAMAGE_FIRE && pathType != PathNodeType.DANGER_FIRE ? super.canWalkOnPath(pathType) : true;
        }

        @Override
        public boolean isValidPosition(BlockPos pos) {
            return this.world.getBlockState(pos).isOf(Blocks.LAVA) || super.isValidPosition(pos);
        }

        @Override
        protected PathNodeNavigator createPathNodeNavigator(int range) {
            this.nodeMaker = new BlackstoneGolemEntity.PathNodeMaker();
            return new PathNodeNavigator(this.nodeMaker, range);
        }
    }

    private class AttackGoal extends MeleeAttackGoal {
        public AttackGoal() {
            super(BlackstoneGolemEntity.this, 1.0D, true);
        }

    }

    static {
        THROW_COOLDOWN = DataTracker.registerData(BlackstoneGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        INVULNERABLE = DataTracker.registerData(BlackstoneGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        INVULNERABLE_TIMER = DataTracker.registerData(BlackstoneGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        LAVA_TEXTURE = DataTracker.registerData(BlackstoneGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        HALF_LIFE_CHANGE = DataTracker.registerData(BlackstoneGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        WALKING_SPEED_INCREASE_ID = Identifier.of("adventurez:walking_speed_increase");
        WALKING_SPEED_INCREASE = new EntityAttributeModifier(WALKING_SPEED_INCREASE_ID, 0.5D, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }
}
