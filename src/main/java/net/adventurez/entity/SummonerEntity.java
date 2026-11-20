package net.adventurez.entity;

import java.util.List;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SummonerEntity extends SpellCastingEntity {
    public static final EntityDataAccessor<Boolean> INVULNERABLE_SHIELD = SynchedEntityData.defineId(SummonerEntity.class, EntityDataSerializers.BOOLEAN);
    private int invulnerableMagicTick;
    private boolean gotShotByABow = false;

    public SummonerEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createSummonerAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 55.0D).add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.ATTACK_KNOCKBACK, 2.2D).add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtTargetGoalNecro());
        this.goalSelector.addGoal(2, new NormalAttack(this, 1.0D, false));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.7D, 0.9D));
        this.goalSelector.addGoal(4, new SummonPuppetGoal());
        this.goalSelector.addGoal(5, new ThunderboltSpellGoal());
        this.goalSelector.addGoal(6, new InvulnerableSpellGoal());
        this.goalSelector.addGoal(7, new TeleportSpellGoal());
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 7.0F, 1.0F));
        this.goalSelector.addGoal(9, new RandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{SkeletonVanguardEntity.class})));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, new Class[]{Zombie.class})));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[]{Skeleton.class})));
        this.targetSelector.addGoal(4, (new NearestAttackableTargetGoal<>(this, Player.class, true)).setUnseenMemoryTicks(300));
    }

    public static boolean canSpawn(EntityType<SummonerEntity> type, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return (checkMonsterSpawnRules(type, world, spawnReason, pos, random) && world.canSeeSky(pos) && world.getLevel().isRaining()) || spawnReason == EntitySpawnReason.SPAWNER;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("InvulnerableMagicTick", this.invulnerableMagicTick);
        tag.putBoolean("InvulnerableMagicShield", this.getEntityData().get(INVULNERABLE_SHIELD));
    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        this.invulnerableMagicTick = tag.getIntOr("InvulnerableMagicTick", 0);
        this.getEntityData().set(INVULNERABLE_SHIELD, tag.getBooleanOr("InvulnerableMagicShield", false));
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(INVULNERABLE_SHIELD, false);
    }

    @Override
    public void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        if (entityData.get(INVULNERABLE_SHIELD)) {
            this.setPermanentlyInvulnerable(true);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
            this.invulnerableMagicTick--;
            if (this.invulnerableMagicTick < 0) {
                this.setPermanentlyInvulnerable(false);
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                entityData.set(INVULNERABLE_SHIELD, false);
            }
        }

    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WITHER_SKELETON_STEP, 0.5F, 0.7F);
    }

    @Override
    public SoundEvent getCastSpellSound() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        int chance = 0;
        if (source.is(DamageTypeTags.IS_LIGHTNING)) {
            return false;
        }
        if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            chance = this.level().getRandom().nextInt(2);
            this.gotShotByABow = true;
        }
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && chance == 1) {
            this.level().playSound(null, this, SoundInit.MAGIC_SHIELD_HIT_EVENT, SoundSource.HOSTILE, 1.0F, 1.0F);
            if (source.getDirectEntity() != null && source.getDirectEntity() instanceof Arrow) {
                if (!this.level().isClientSide()) {
                    source.getDirectEntity().discard();
                }
            }
            return false;
        } else {
            return !this.isInvulnerableTo(level, source) && super.hurtServer(level, source, amount);
        }
    }

    private class ThunderboltSpellGoal extends SpellCastingEntity.CastSpellGoal {
        private ThunderboltSpellGoal() {
            super();
        }

        @Override
        protected int getInitialCooldown() {
            return 200;
        }

        @Override
        public int getSpellTicks() {
            return 20;
        }

        @Override
        public int startTimeDelay() {
            return 20;
        }

        @Override
        public boolean canUse() {
            LivingEntity attacker = SummonerEntity.this.getLastHurtByMob();
            if (super.canUse() && attacker != null && attacker.getMainHandItem().getItem() instanceof ProjectileWeaponItem && SummonerEntity.this.distanceToSqr(attacker) > 12
                    && SummonerEntity.this.gotShotByABow) {
                return true;
            } else
                return false;
        }

        @Override
        public void castSpell() {
            LivingEntity attacker = SummonerEntity.this.getLastHurtByMob();
            if (attacker != null) {
                ServerLevel serverWorld = (ServerLevel) attacker.level();
                double posX = attacker.getX() + SummonerEntity.this.level().getRandom().nextInt(3);
                double posY = attacker.getY();
                double posZ = attacker.getZ() + SummonerEntity.this.level().getRandom().nextInt(3);
                BlockPos pos = BlockPos.containing(posX, posY, posZ);
                LightningBolt lightningEntity = (LightningBolt) EntityTypes.LIGHTNING_BOLT.create(attacker.level(), EntitySpawnReason.MOB_SUMMONED);
                lightningEntity.snapTo(pos, 0.0F, 0.0F);
                serverWorld.addFreshEntity(lightningEntity);
            }
            gotShotByABow = false;
        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        @Override
        public SpellCastingEntity.Spell getSpell() {
            return SpellCastingEntity.Spell.THUNDERBOLT;
        }
    }

    private class TeleportSpellGoal extends SpellCastingEntity.CastSpellGoal {
        private TeleportSpellGoal() {
            super();
        }

        @Override
        protected int getInitialCooldown() {
            return 200;
        }

        @Override
        public int getSpellTicks() {
            return 40;
        }

        @Override
        public int startTimeDelay() {
            return 60;
        }

        @Override
        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            } else
                return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void castSpell() {
            for (int counter = 0; counter < 100; counter++) {
                float randomFloat = SummonerEntity.this.level().getRandom().nextFloat() * 6.2831855F;
                int posX = SummonerEntity.this.blockPosition().getX() + Mth.floor(Mth.cos(randomFloat) * 8.0F + SummonerEntity.this.level().getRandom().nextInt(12));
                int posZ = SummonerEntity.this.blockPosition().getZ() + Mth.floor(Mth.sin(randomFloat) * 8.0F + SummonerEntity.this.level().getRandom().nextInt(12));
                int posY = SummonerEntity.this.level().getHeight(Heightmap.Types.WORLD_SURFACE, posX, posZ);
                BlockPos teleportPos = new BlockPos(posX, posY, posZ);
                if (SummonerEntity.this.level().hasChunksAt(teleportPos.getX() - 4, teleportPos.getY() - 4, teleportPos.getZ() - 4, teleportPos.getX() + 4, teleportPos.getY() + 4,
                        teleportPos.getZ() + 4)
                        && SpawnPlacements.checkSpawnRules(EntityInit.SUMMONER, (ServerLevel) SummonerEntity.this.level(), EntitySpawnReason.EVENT, teleportPos, SummonerEntity.this.getRandom())) {
                    SummonerEntity.this.randomTeleport(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ(), true, state -> false);
                    break;
                }
            }
            SummonerEntity.this.spawnAnim();

        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        @Override
        public SpellCastingEntity.Spell getSpell() {
            return SpellCastingEntity.Spell.TELEPORT;
        }
    }

    private class InvulnerableSpellGoal extends SpellCastingEntity.CastSpellGoal {
        private InvulnerableSpellGoal() {
            super();
        }

        @Override
        protected int getInitialCooldown() {
            return 420;
        }

        @Override
        public int getSpellTicks() {
            return 40;
        }

        @Override
        public int startTimeDelay() {
            return 60;
        }

        @Override
        public boolean canUse() {
            if (!super.canUse() || (SummonerEntity.this.getTarget() != null && SummonerEntity.this.distanceToSqr(SummonerEntity.this.getTarget()) > 7D)) {
                return false;
            } else
                return true;
        }

        @Override
        public void tick() {
            --this.spellCooldown;
            if (this.spellCooldown == 0) {
                this.castSpell();
                SummonerEntity.this.playSound(SoundInit.SPELL_CAST_SHIELD_EVENT, 1.0F, 1.0F);
            }

        }

        @Override
        public void castSpell() {
            entityData.set(INVULNERABLE_SHIELD, true);
            SummonerEntity.this.invulnerableMagicTick = 160;
            ((ServerLevel) SummonerEntity.this.level()).sendParticles(ParticleTypes.END_ROD, SummonerEntity.this.getX(), SummonerEntity.this.getRandomY(),
                    SummonerEntity.this.getZ(), 60, 0.7D, SummonerEntity.this.getBbHeight() * 0.3D, 0.7D, 0.0D);

            if (SummonerEntity.this.getTarget() != null) {
                SummonerEntity.this.getTarget().addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1, false, false));
            }

        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        @Override
        public SpellCastingEntity.Spell getSpell() {
            return SpellCastingEntity.Spell.SHIELD;
        }
    }

    private class SummonPuppetGoal extends SpellCastingEntity.CastSpellGoal {
        private final TargetingConditions PUPPET_PREDICATE = TargetingConditions.forCombat().range(24.0D).ignoreLineOfSight().ignoreInvisibilityTesting();

        private SummonPuppetGoal() {
            super();

        }

        @Override
        public boolean canUse() {
            if (!super.canUse() || tooManySlaves()) {
                return false;
            } else {
                int i = ((ServerLevel) SummonerEntity.this.level()).getNearbyEntities(WitherPuppetEntity.class, this.PUPPET_PREDICATE, SummonerEntity.this, SummonerEntity.this.getBoundingBox().inflate(16.0D)).size();
                return SummonerEntity.this.getRandom().nextInt(8) + 1 > i;
            }
        }

        @Override
        public int getSpellTicks() {
            return 140;
        }

        @Override
        public int startTimeDelay() {
            return 340;
        }

        @Override
        public void castSpell() {
            ServerLevel serverWorld = (ServerLevel) SummonerEntity.this.level();
            int spellCount = 0;
            for (int i = 0; i < 20; ++i) {
                BlockPos blockPos = SummonerEntity.this.blockPosition().offset(-2 + serverWorld.getRandom().nextInt(5), serverWorld.getRandom().nextInt(3), -2 + serverWorld.getRandom().nextInt(5));
                if (SpawnPlacements.checkSpawnRules(EntityInit.SKELETON_VANGUARD, serverWorld, EntitySpawnReason.EVENT, blockPos, serverWorld.getRandom())) {
                    spellCount++;
                    if (SummonerEntity.this.getHealth() <= 40.0F || SummonerEntity.this.level().isBrightOutside()) {
                        SkeletonVanguardEntity skeletonVanguardEntity = EntityInit.SKELETON_VANGUARD.create(serverWorld, EntitySpawnReason.MOB_SUMMONED);
                        skeletonVanguardEntity.snapTo(blockPos, serverWorld.getRandom().nextFloat() * 360F, 0.0F);
                        skeletonVanguardEntity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(blockPos), EntitySpawnReason.EVENT, null);
                        serverWorld.addFreshEntityWithPassengers(skeletonVanguardEntity);
                    } else {
                        Zombie zombieEntity = EntityTypes.ZOMBIE.create(serverWorld, EntitySpawnReason.MOB_SUMMONED);
                        zombieEntity.snapTo(blockPos, serverWorld.getRandom().nextFloat() * 360F, 0.0F);
                        zombieEntity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(blockPos), EntitySpawnReason.EVENT, null);
                        serverWorld.addFreshEntityWithPassengers(zombieEntity);
                        int skeletonChance = serverWorld.getRandom().nextInt(8);
                        if (skeletonChance == 0) {
                            Skeleton skeletonEntity = EntityTypes.SKELETON.create(serverWorld, EntitySpawnReason.MOB_SUMMONED);
                            skeletonEntity.snapTo(blockPos, serverWorld.getRandom().nextFloat() * 360F, 0.0F);
                            skeletonEntity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(blockPos), EntitySpawnReason.EVENT, null);
                            if (SummonerEntity.this.gotShotByABow) {
                                skeletonEntity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                            }
                            serverWorld.addFreshEntityWithPassengers(skeletonEntity);
                        }
                    }
                }
                if (spellCount >= 3)
                    break;
            }

        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        @Override
        public SpellCastingEntity.Spell getSpell() {
            return SpellCastingEntity.Spell.SUMMON_PUPPET;
        }
    }

    private class LookAtTargetGoalNecro extends SpellCastingEntity.LookAtTargetGoal {
        private LookAtTargetGoalNecro() {
            super();
        }

        @Override
        public void tick() {
            if (SummonerEntity.this.getTarget() != null) {
                SummonerEntity.this.getLookControl().setLookAt(SummonerEntity.this.getTarget(), (float) SummonerEntity.this.getMaxHeadYRot(), (float) SummonerEntity.this.getMaxHeadXRot());
            }

        }
    }

    private boolean tooManySlaves() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(40D), EntitySelector.NO_SPECTATORS);
        if (!list.isEmpty()) {
            int vanguards = 0;
            int othermobs = 0;
            for (LivingEntity livingEntity : list) {
                if (livingEntity.getType() == EntityInit.SKELETON_VANGUARD) {
                    vanguards++;
                } else if (livingEntity.getType() == EntityTypes.ZOMBIE || livingEntity.getType() == EntityTypes.SKELETON) {
                    othermobs++;
                }
                if (vanguards >= 5 || othermobs >= 7) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class NormalAttack extends MeleeAttackGoal {
        private final SummonerEntity summonerEntity;

        public NormalAttack(PathfinderMob mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.summonerEntity = (SummonerEntity) mob;
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.summonerEntity.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.summonerEntity.canAttack(livingEntity) && this.summonerEntity.distanceToSqr(livingEntity) < 7.0D && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null || this.summonerEntity.distanceToSqr(livingEntity) > 5D) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.mob.isWithinHome(livingEntity.blockPosition())) {
                return false;
            } else {
                return !(livingEntity instanceof Player player) || !livingEntity.isSpectator() && !player.isCreative();
            }
        }

    }

}
