package net.adventurez.entity;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class SoulReaperEntity extends HostileEntity implements RangedAttackMob {
    private final BowAttackGoal<SoulReaperEntity> bowAttackGoal = new BowAttackGoal<>(this, 1.0D, 40, 15.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, true) {
        @Override
        public void stop() {
            super.stop();
            SoulReaperEntity.this.setAttacking(false);
        }

        @Override
        public void start() {
            super.start();
            SoulReaperEntity.this.setAttacking(true);
        }
    };

    public SoulReaperEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
        this.stepHeight = 1.0F;
        this.experiencePoints = 30;
    }

    public static DefaultAttributeContainer.Builder createSoulReaperAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_MAX_HEALTH, 120.0D);
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { SoulReaperEntity.class })));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, AbstractPiglinEntity.class, true));
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
        this.initEquipment(difficulty);
        this.updateEnchantments(difficulty);
        this.bowAttackGoal.setAttackInterval(40);
        this.goalSelector.add(4, this.bowAttackGoal);
        if (spawnReason.equals(SpawnReason.COMMAND) || spawnReason.equals(SpawnReason.NATURAL) || spawnReason.equals(SpawnReason.CHUNK_GENERATION)) {
            NightmareEntity nightmareEntity = (NightmareEntity) EntityInit.NIGHTMARE_ENTITY.create(this.world);
            nightmareEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
            nightmareEntity.initialize(serverWorldAccess, difficulty, spawnReason, (EntityData) null, (NbtCompound) null);
            serverWorldAccess.spawnEntity(nightmareEntity);
            this.startRiding(nightmareEntity);
        }
        return entityData;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    public static boolean canSpawn(EntityType<SoulReaperEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (canSpawnInDark(type, world, spawnReason, pos, random) && world.getBlockState(pos.up(3)).isAir()
                && world.getEntitiesByClass(SoulReaperEntity.class, new Box(pos).expand(60D), EntityPredicates.EXCEPT_SPECTATOR).isEmpty() && random.nextInt(7) == 0)
                || spawnReason == SpawnReason.SPAWNER;
    }

    @Override
    public void tickRiding() {
        super.tickRiding();
        if (this.getVehicle() instanceof PathAwareEntity) {
            PathAwareEntity pathAwareEntity = (PathAwareEntity) this.getVehicle();
            this.bodyYaw = pathAwareEntity.bodyYaw;
        }

    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public int getLimitPerChunk() {
        return 1;
    }

    @Override
    public float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.6F;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return effect.getEffectType() == StatusEffects.WITHER ? false : super.canHaveStatusEffect(effect);
    }

    @Override
    public void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (target instanceof LivingEntity)
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 300));
        return super.tryAttack(target);
    }

    @Override
    public void tickMovement() {
        if (this.world != null && !this.world.isClient) {
            LivingEntity target = this.getTarget();
            if (target != null) {
                if (this.distanceTo(target) > 7F) {
                    if (this.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(Items.NETHERITE_SWORD)) {
                        this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                    }
                    this.goalSelector.add(4, this.bowAttackGoal);
                    this.goalSelector.remove(this.meleeAttackGoal);
                } else {
                    this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
                    this.goalSelector.add(4, this.meleeAttackGoal);
                    this.goalSelector.remove(this.bowAttackGoal);
                }
            } else {
                if (this.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(Items.BOW)) {
                    this.goalSelector.add(4, this.bowAttackGoal);
                } else {
                    this.goalSelector.add(4, this.meleeAttackGoal);
                }
            }
        }
        super.tickMovement();
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getArrowType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack, pullProgress);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333D) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = (double) MathHelper.sqrt((float) (d * d + f * f));
        persistentProjectileEntity.setVelocity(d, e + g * 0.21D, f, 1.3F, (float) (14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(persistentProjectileEntity);
    }

    public PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        ItemStack blackarrow = new ItemStack(Items.ARROW);
        PersistentProjectileEntity persistentProjectileEntity = ProjectileUtil.createArrowProjectile(this, blackarrow, 4F);
        if (persistentProjectileEntity instanceof ArrowEntity) {
            ((ArrowEntity) persistentProjectileEntity).addEffect(new StatusEffectInstance(StatusEffects.WITHER, 200));
        }
        persistentProjectileEntity.setPierceLevel((byte) 1);
        return persistentProjectileEntity;
    }

    @Override
    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        return weapon == Items.BOW;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        super.equipStack(slot, stack);
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.SOULREAPER_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.SOULREAPER_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.SOULREAPER_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.5F, 1.0F);
    }

}