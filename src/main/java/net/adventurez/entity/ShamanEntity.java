package net.adventurez.entity;

import java.util.List;
import java.util.Random;

import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
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
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.ServerWorldAccess;

public class ShamanEntity extends SpellCastingEntity {

    public ShamanEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
    }

    public static DefaultAttributeContainer.Builder createShamanAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new LookAtTargetGoalShaman());
        this.goalSelector.add(2, new NormalAttack(this, 1.0D, false));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.7D, 0.9D));
        this.goalSelector.add(4, new SummonCompanionsGoal());
        this.goalSelector.add(5, new ThunderboltSpellGoal());
        this.goalSelector.add(7, new EffectSpellGoal());
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 7.0F, 1.0F));
        this.goalSelector.add(9, new WanderAroundGoal(this, 0.9D));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { WitchEntity.class })));
        this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { ZombieEntity.class })));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[] { SkeletonEntity.class })));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[] { SpiderEntity.class })));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[] { CaveSpiderEntity.class })));
        this.targetSelector.add(4, (new ActiveTargetGoal<>(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
    }

    public static boolean canSpawn(EntityType<ShamanEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (canSpawnInDark(type, world, spawnReason, pos, random) && world.isSkyVisible(pos)) || spawnReason == SpawnReason.SPAWNER;

    }

    @Override
    public int getLimitPerChunk() {
        return 1;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.SHAMAN_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.SHAMAN_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.SHAMAN_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.SHAMAN_WALK_EVENT, 0.5F, 1.0F);
    }

    @Override
    public SoundEvent getCastSpellSound() {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }

    private boolean tooManyCompanions() {
        List<LivingEntity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(40D), EntityPredicates.EXCEPT_SPECTATOR);
        if (!list.isEmpty()) {
            int spiders = 0;
            int othermobs = 0;
            for (int i = 0; i < list.size(); i++) {
                LivingEntity entity = (LivingEntity) list.get(i);
                if (entity.getType() == EntityType.CAVE_SPIDER) {
                    spiders++;
                } else if (entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.SKELETON || entity.getType() == EntityType.SPIDER) {
                    othermobs++;
                }
                if (spiders >= 2 || othermobs >= 4) {
                    return true;
                }
            }
        }
        return false;
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
            LivingEntity attacker = ShamanEntity.this.getAttacker();
            if (super.canStart() && attacker != null && attacker.getMainHandStack().getItem() instanceof RangedWeaponItem && ShamanEntity.this.squaredDistanceTo(attacker) > 12
                    && ShamanEntity.this.world.isRaining()) {
                return true;
            } else
                return false;
        }

        @Override
        public void castSpell() {
            LivingEntity attacker = ShamanEntity.this.getAttacker();
            if (attacker != null) {
                ServerWorld serverWorld = (ServerWorld) attacker.world;
                double posX = attacker.getX() + world.getRandom().nextInt(3);
                double posY = attacker.getY();
                double posZ = attacker.getZ() + world.getRandom().nextInt(3);
                BlockPos pos = new BlockPos(posX, posY, posZ);
                LightningEntity lightningEntity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(attacker.world);
                lightningEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                serverWorld.spawnEntity(lightningEntity);
            }
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

    private class EffectSpellGoal extends SpellCastingEntity.CastSpellGoal {
        private EffectSpellGoal() {
            super();
        }

        @Override
        protected int getInitialCooldown() {
            return 200;
        }

        @Override
        public int getSpellTicks() {
            return 60;
        }

        @Override
        public int startTimeDelay() {
            return 10;
        }

        @Override
        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else
                return true;
        }

        @Override
        public void castSpell() {
            LivingEntity livingEntity = ShamanEntity.this.getTarget();
            if (livingEntity != null) {
                StatusEffect statusEffect;
                if (ShamanEntity.this.random.nextFloat() <= 0.5F)
                    statusEffect = StatusEffects.WEAKNESS;
                else
                    statusEffect = StatusEffects.POISON;
                livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, 120 + ShamanEntity.this.random.nextInt(80), 1, false, false, true));
            }

        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
        }

        @Override
        public SpellCastingEntity.Spell getSpell() {
            return SpellCastingEntity.Spell.WITHERING;
        }
    }

    private class SummonCompanionsGoal extends SpellCastingEntity.CastSpellGoal {
        private SummonCompanionsGoal() {
            super();
        }

        @Override
        public boolean canStart() {
            if (!super.canStart() || tooManyCompanions()) {
                return false;
            } else
                return true;
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
            ServerWorld serverWorld = (ServerWorld) ShamanEntity.this.world;
            int spellCount = 0;
            for (int i = 0; i < 20; ++i) {
                BlockPos blockPos = ShamanEntity.this.getBlockPos().add(-3 + ShamanEntity.this.random.nextInt(6), ShamanEntity.this.random.nextInt(3), -3 + ShamanEntity.this.random.nextInt(6));
                if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, blockPos, EntityType.ZOMBIE)) {
                    spellCount++;
                    if (ShamanEntity.this.random.nextFloat() < 0.6F) {
                        CaveSpiderEntity caveSpiderEntity = (CaveSpiderEntity) EntityType.CAVE_SPIDER.create(serverWorld);
                        caveSpiderEntity.refreshPositionAndAngles(blockPos, ShamanEntity.this.world.random.nextFloat() * 360F, 0.0F);
                        caveSpiderEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(blockPos), SpawnReason.EVENT, null, null);
                        serverWorld.spawnEntityAndPassengers(caveSpiderEntity);
                    } else {
                        MobEntity mobEntity;
                        int randomInt = ShamanEntity.this.random.nextInt(3);
                        if (randomInt == 0)
                            mobEntity = EntityType.ZOMBIE.create(serverWorld);
                        else if (randomInt == 1)
                            mobEntity = EntityType.SKELETON.create(serverWorld);
                        else
                            mobEntity = EntityType.SPIDER.create(serverWorld);
                        mobEntity.refreshPositionAndAngles(blockPos, ShamanEntity.this.world.random.nextFloat() * 360F, 0.0F);
                        mobEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(blockPos), SpawnReason.EVENT, null, null);
                        serverWorld.spawnEntityAndPassengers(mobEntity);
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

    private class LookAtTargetGoalShaman extends SpellCastingEntity.LookAtTargetGoal {
        private LookAtTargetGoalShaman() {
            super();
        }

        @Override
        public void tick() {
            if (ShamanEntity.this.getTarget() != null) {
                ShamanEntity.this.getLookControl().lookAt(ShamanEntity.this.getTarget(), (float) ShamanEntity.this.getMaxHeadRotation(), (float) ShamanEntity.this.getMaxLookPitchChange());
            }

        }
    }

    private class NormalAttack extends MeleeAttackGoal {
        private final ShamanEntity summonerEntity;

        public NormalAttack(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.summonerEntity = (ShamanEntity) mob;
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
            return (double) (this.mob.getWidth() * 2.3F * this.mob.getWidth() * 2.3F + entity.getWidth());
        }

    }

}
