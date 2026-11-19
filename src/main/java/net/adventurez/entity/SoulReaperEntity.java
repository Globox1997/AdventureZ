package net.adventurez.entity;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.adventurez.mixin.accessor.ProjectileEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class SoulReaperEntity extends Monster implements RangedAttackMob {

    private boolean switchCombat = false;
    private final RangedBowAttackGoal<SoulReaperEntity> bowAttackGoal = new RangedBowAttackGoal<>(this, 1.0D, 40, 15.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, true) {
        @Override
        public void stop() {
            super.stop();
            SoulReaperEntity.this.setAggressive(false);
        }

        @Override
        public void start() {
            super.start();
            SoulReaperEntity.this.setAggressive(true);
        }
    };

    public SoulReaperEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.xpReward = 30;
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0f);
    }

    public static AttributeSupplier.Builder createSoulReaperAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.MAX_HEALTH, 120.0D);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{SoulReaperEntity.class})));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractPiglin.class, true));
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorldAccess, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        entityData = super.finalizeSpawn(serverWorldAccess, difficulty, spawnReason, entityData);
        RandomSource random = this.level().getRandom();
        this.populateDefaultEquipmentSlots(random, difficulty);
        this.populateDefaultEquipmentEnchantments(serverWorldAccess, random, difficulty);
        this.bowAttackGoal.setMinAttackInterval(40);
        this.goalSelector.addGoal(4, this.bowAttackGoal);
        if (spawnReason.equals(MobSpawnType.COMMAND) || spawnReason.equals(MobSpawnType.NATURAL) || spawnReason.equals(MobSpawnType.CHUNK_GENERATION)) {
            NightmareEntity nightmareEntity = EntityInit.NIGHTMARE.create(this.level());
            nightmareEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            nightmareEntity.finalizeSpawn(serverWorldAccess, difficulty, spawnReason, null);
            serverWorldAccess.addFreshEntity(nightmareEntity);
            this.startRiding(nightmareEntity);
        }
        return entityData;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    public static boolean canSpawn(EntityType<SoulReaperEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return (random.nextInt(7) == 0 && world.getBlockState(pos.above(3)).isAir() && checkMonsterSpawnRules(type, world, spawnReason, pos, random)
                && world.getEntitiesOfClass(SoulReaperEntity.class, new AABB(pos).inflate(60D), EntitySelector.NO_SPECTATORS).isEmpty())
                || spawnReason == MobSpawnType.SPAWNER;
    }

    @Override
    public void rideTick() {
        super.rideTick();
        if (this.getVehicle() instanceof PathfinderMob pathAwareEntity) {
            this.yBodyRot = pathAwareEntity.yBodyRot;
        }

    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != MobEffects.WITHER && super.canBeAffected(effect);
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (target instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 300));
        }
        return super.doHurtTarget(target);
    }

    @Override
    public void aiStep() {
        if (this.level() != null && !this.level().isClientSide()) {
            boolean meleeGoal = this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof SwordItem;
            LivingEntity target = this.getTarget();
            if (target != null) {
                if (meleeGoal) {
                    if (this.distanceToSqr(target) > 64F) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                        this.goalSelector.addGoal(4, this.bowAttackGoal);
                        this.goalSelector.removeGoal(this.meleeAttackGoal);
                        this.switchCombat = true;
                    }

                } else if (this.distanceToSqr(target) < 36F) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
                    this.goalSelector.addGoal(4, this.meleeAttackGoal);
                    this.goalSelector.removeGoal(this.bowAttackGoal);
                    this.switchCombat = true;
                }
            } else if (!this.switchCombat) {
                if (this.getItemBySlot(EquipmentSlot.MAINHAND).getItem().equals(Items.BOW)) {
                    this.goalSelector.addGoal(4, this.bowAttackGoal);
                } else {
                    this.goalSelector.addGoal(4, this.meleeAttackGoal);
                }
                this.switchCombat = true;
            }
        }
        super.aiStep();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW)));
        AbstractArrow persistentProjectileEntity = this.createArrowProjectile(itemStack, pullProgress);
        double d = target.getX() - this.getX();
        double e = target.getY(0.3333333333333333D) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = (double) Mth.sqrt((float) (d * d + f * f));
        persistentProjectileEntity.shoot(d, e + g * 0.21D, f, 1.3F, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(persistentProjectileEntity);
    }

    public AbstractArrow createArrowProjectile(ItemStack arrow, float damageModifier) {
        ItemStack blackarrow = new ItemStack(Items.ARROW);
        ItemStack itemStack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
        AbstractArrow persistentProjectileEntity = ProjectileUtil.getMobArrow(this, blackarrow, 4F, itemStack);
        if (persistentProjectileEntity instanceof Arrow arrowEntity) {
            arrowEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 200));
        }
        ((ProjectileEntityAccessor) persistentProjectileEntity).callSetPierceLevel((byte) 1);

        return persistentProjectileEntity;
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem weapon) {
        return weapon == Items.BOW;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        super.setItemSlot(slot, stack);
    }

    @Override
    public boolean canUsePortal(boolean allowVehicles) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.SOUL_REAPER_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.SOUL_REAPER_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.SOUL_REAPER_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WITHER_SKELETON_STEP, 0.5F, 1.0F);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel world, DamageSource source, boolean causedByPlayer) {
        if (source.getEntity() instanceof Creeper creeperEntity && creeperEntity.canDropMobsSkull()) {
            creeperEntity.increaseDroppedSkulls();
            this.spawnAtLocation(Items.WITHER_SKELETON_SKULL);
        }
    }
}
