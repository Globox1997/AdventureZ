package net.adventurez.entity;

import java.util.List;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;

public class SummonerEntity extends SpellCastingEntity {
    public static final TrackedData<Boolean> INVULNERABLE_SHIELD = DataTracker.registerData(SummonerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int invulnerableMagicTick;
    private boolean gotShotByABow = false;

    public SummonerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1.0f);
    }

    public static DefaultAttributeContainer.Builder createSummonerAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 55.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.2D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new LookAtTargetGoalNecro());
        this.goalSelector.add(2, new NormalAttack(this, 1.0D, false));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.7D, 0.9D));
        this.goalSelector.add(4, new SummonPuppetGoal());
        this.goalSelector.add(5, new ThunderboltSpellGoal());
        this.goalSelector.add(6, new InvulnerableSpellGoal());
        this.goalSelector.add(7, new TeleportSpellGoal());
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 7.0F, 1.0F));
        this.goalSelector.add(9, new WanderAroundGoal(this, 0.9D));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { SkeletonVanguardEntity.class })));
        this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { ZombieEntity.class })));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[] { SkeletonEntity.class })));
        this.targetSelector.add(4, (new ActiveTargetGoal<>(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
    }

    public static boolean canSpawn(EntityType<SummonerEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (canSpawnInDark(type, world, spawnReason, pos, random) && world.isSkyVisible(pos) && world.getLevelProperties().isRaining()) || spawnReason == SpawnReason.SPAWNER;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putInt("InvulnerableMagicTick", this.invulnerableMagicTick);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.invulnerableMagicTick = tag.getInt("InvulnerableMagicTick");
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(INVULNERABLE_SHIELD, false);
    }

    @Override
    public void mobTick() {
        super.mobTick();
        if (dataTracker.get(INVULNERABLE_SHIELD)) {
            this.setInvulnerable(true);
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.0D);
            this.invulnerableMagicTick--;
            if (this.invulnerableMagicTick < 0) {
                this.setInvulnerable(false);
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
                dataTracker.set(INVULNERABLE_SHIELD, false);
            }
        }

    }

    @Override
    public int getLimitPerChunk() {
        return 1;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.5F, 0.7F);
    }

    @Override
    public SoundEvent getCastSpellSound() {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        int chance = 0;
        if (source.isIn(DamageTypeTags.IS_LIGHTNING)) {
            return false;
        }
        if (source.isIn(DamageTypeTags.IS_PROJECTILE)) {
            chance = this.getWorld().getRandom().nextInt(2);
            this.gotShotByABow = true;
        }
        if (chance == 1) {
            this.getWorld().playSoundFromEntity(null, this, SoundInit.MAGIC_SHIELD_HIT_EVENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
            if (source.getSource() != null && source.getSource() instanceof ArrowEntity) {
                if (!this.getWorld().isClient()) {
                    source.getSource().discard();
                }
            }
            return false;
        } else {
            return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
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
        public boolean canStart() {
            LivingEntity attacker = SummonerEntity.this.getAttacker();
            if (super.canStart() && attacker != null && attacker.getMainHandStack().getItem() instanceof RangedWeaponItem && SummonerEntity.this.squaredDistanceTo(attacker) > 12
                    && SummonerEntity.this.gotShotByABow) {
                return true;
            } else
                return false;
        }

        @Override
        public void castSpell() {
            LivingEntity attacker = SummonerEntity.this.getAttacker();
            if (attacker != null) {
                ServerWorld serverWorld = (ServerWorld) attacker.getWorld();
                double posX = attacker.getX() + SummonerEntity.this.getWorld().getRandom().nextInt(3);
                double posY = attacker.getY();
                double posZ = attacker.getZ() + SummonerEntity.this.getWorld().getRandom().nextInt(3);
                BlockPos pos = BlockPos.ofFloored(posX, posY, posZ);
                LightningEntity lightningEntity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(attacker.getWorld());
                lightningEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                serverWorld.spawnEntity(lightningEntity);
            }
            gotShotByABow = false;
        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
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
        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else
                return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void castSpell() {
            for (int counter = 0; counter < 100; counter++) {
                float randomFloat = SummonerEntity.this.getWorld().getRandom().nextFloat() * 6.2831855F;
                int posX = SummonerEntity.this.getBlockPos().getX() + MathHelper.floor(MathHelper.cos(randomFloat) * 8.0F + SummonerEntity.this.getWorld().getRandom().nextInt(12));
                int posZ = SummonerEntity.this.getBlockPos().getZ() + MathHelper.floor(MathHelper.sin(randomFloat) * 8.0F + SummonerEntity.this.getWorld().getRandom().nextInt(12));
                int posY = SummonerEntity.this.getWorld().getTopY(Heightmap.Type.WORLD_SURFACE, posX, posZ);
                BlockPos teleportPos = new BlockPos(posX, posY, posZ);
                if (SummonerEntity.this.getWorld().isRegionLoaded(teleportPos.getX() - 4, teleportPos.getY() - 4, teleportPos.getZ() - 4, teleportPos.getX() + 4, teleportPos.getY() + 4,
                        teleportPos.getZ() + 4) && SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, SummonerEntity.this.getWorld(), teleportPos, EntityInit.SUMMONER)) {
                    SummonerEntity.this.teleport(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());
                    break;
                }
            }
            SummonerEntity.this.playSpawnEffects();

        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
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
        public boolean canStart() {
            if (!super.canStart() || (SummonerEntity.this.getTarget() != null && SummonerEntity.this.squaredDistanceTo(SummonerEntity.this.getTarget()) > 7D)) {
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
            dataTracker.set(INVULNERABLE_SHIELD, true);
            SummonerEntity.this.invulnerableMagicTick = 160;
            for (int i = 0; i < 60; ++i) {
                ((ServerWorld) SummonerEntity.this.getWorld()).spawnParticles(ParticleTypes.END_ROD, SummonerEntity.this.getParticleX(1.5D), SummonerEntity.this.getRandomBodyY(),
                        SummonerEntity.this.getParticleZ(1.5D), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            if (SummonerEntity.this.getTarget() != null) {
                SummonerEntity.this.getTarget().addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1, false, false));
            }

        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
        }

        @Override
        public SpellCastingEntity.Spell getSpell() {
            return SpellCastingEntity.Spell.SHIELD;
        }
    }

    private class SummonPuppetGoal extends SpellCastingEntity.CastSpellGoal {
        private final TargetPredicate PUPPET_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(24.0D).ignoreVisibility().ignoreDistanceScalingFactor();

        private SummonPuppetGoal() {
            super();

        }

        @Override
        public boolean canStart() {
            if (!super.canStart() || tooManySlaves()) {
                return false;
            } else {
                int i = SummonerEntity.this.getWorld().getTargets(WitherPuppetEntity.class, this.PUPPET_PREDICATE, SummonerEntity.this, SummonerEntity.this.getBoundingBox().expand(16.0D)).size();
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
            ServerWorld serverWorld = (ServerWorld) SummonerEntity.this.getWorld();
            int spellCount = 0;
            for (int i = 0; i < 20; ++i) {
                BlockPos blockPos = SummonerEntity.this.getBlockPos().add(-2 + SummonerEntity.this.random.nextInt(5), SummonerEntity.this.random.nextInt(3),
                        -2 + SummonerEntity.this.random.nextInt(5));
                if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, SummonerEntity.this.getWorld(), blockPos, EntityInit.SKELETON_VANGUARD)) {
                    spellCount++;
                    if (SummonerEntity.this.getHealth() <= 40.0F || SummonerEntity.this.getEntityWorld().isDay()) {
                        SkeletonVanguardEntity skeletonVanguardEntity = (SkeletonVanguardEntity) EntityInit.SKELETON_VANGUARD.create(serverWorld);
                        skeletonVanguardEntity.refreshPositionAndAngles(blockPos, SummonerEntity.this.getWorld().getRandom().nextFloat() * 360F, 0.0F);
                        skeletonVanguardEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(blockPos), SpawnReason.EVENT, null, null);
                        serverWorld.spawnEntityAndPassengers(skeletonVanguardEntity);
                    } else {
                        ZombieEntity zombieEntity = (ZombieEntity) EntityType.ZOMBIE.create(serverWorld);
                        zombieEntity.refreshPositionAndAngles(blockPos, SummonerEntity.this.getWorld().getRandom().nextFloat() * 360F, 0.0F);
                        zombieEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(blockPos), SpawnReason.EVENT, null, null);
                        serverWorld.spawnEntityAndPassengers(zombieEntity);
                        int skeletonChance = SummonerEntity.this.getWorld().getRandom().nextInt(8);
                        if (skeletonChance == 0) {
                            SkeletonEntity skeletonEntity = (SkeletonEntity) EntityType.SKELETON.create(serverWorld);
                            skeletonEntity.refreshPositionAndAngles(blockPos, SummonerEntity.this.getWorld().getRandom().nextFloat() * 360F, 0.0F);
                            skeletonEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(blockPos), SpawnReason.EVENT, null, null);
                            if (SummonerEntity.this.gotShotByABow) {
                                skeletonEntity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                            }
                            serverWorld.spawnEntityAndPassengers(skeletonEntity);
                        }
                    }
                }
                if (spellCount >= 3)
                    break;
            }

        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
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
                SummonerEntity.this.getLookControl().lookAt(SummonerEntity.this.getTarget(), (float) SummonerEntity.this.getMaxHeadRotation(), (float) SummonerEntity.this.getMaxLookPitchChange());
            }

        }
    }

    private boolean tooManySlaves() {
        List<LivingEntity> list = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(40D), EntityPredicates.EXCEPT_SPECTATOR);
        if (!list.isEmpty()) {
            int vanguards = 0;
            int othermobs = 0;
            for (int i = 0; i < list.size(); i++) {
                LivingEntity entity = (LivingEntity) list.get(i);
                if (entity.getType() == EntityInit.SKELETON_VANGUARD) {
                    vanguards++;
                } else if (entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.SKELETON) {
                    othermobs++;
                }
                if (vanguards >= 5 || othermobs >= 7) {
                    return true;
                }
            }
        }
        return false;
    }

    private class NormalAttack extends MeleeAttackGoal {
        private final SummonerEntity summonerEntity;

        public NormalAttack(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.summonerEntity = (SummonerEntity) mob;
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.summonerEntity.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.summonerEntity.canTarget(livingEntity) && this.summonerEntity.squaredDistanceTo(livingEntity) < 7.0D && super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null || this.summonerEntity.squaredDistanceTo(livingEntity) > 5D) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
                return false;
            } else {
                return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative();
            }
        }

        @Override
        public double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double) (this.mob.getWidth() * 2.5F * this.mob.getWidth() * 2.5F + entity.getWidth());
        }

    }

}
