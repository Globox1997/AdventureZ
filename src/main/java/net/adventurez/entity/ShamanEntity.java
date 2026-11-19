package net.adventurez.entity;

import java.util.List;

import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
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
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class ShamanEntity extends SpellCastingEntity {

    public ShamanEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createShamanAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtTargetGoalShaman());
        this.goalSelector.addGoal(2, new NormalAttack(this, 1.0D, false));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.7D, 0.9D));
        this.goalSelector.addGoal(4, new SummonCompanionsGoal());
        this.goalSelector.addGoal(5, new ThunderboltSpellGoal());
        this.goalSelector.addGoal(7, new EffectSpellGoal());
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 7.0F, 1.0F));
        this.goalSelector.addGoal(9, new RandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{Witch.class})));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, new Class[]{Zombie.class})));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[]{Skeleton.class})));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[]{Spider.class})));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[]{CaveSpider.class})));
        this.targetSelector.addGoal(4, (new NearestAttackableTargetGoal<>(this, Player.class, true)).setUnseenMemoryTicks(300));
    }

    public static boolean canSpawn(EntityType<ShamanEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return (checkMonsterSpawnRules(type, world, spawnReason, pos, random) && world.canSeeSky(pos)) || spawnReason == MobSpawnType.SPAWNER;

    }

    @Override
    public int getMaxSpawnClusterSize() {
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
        this.playSound(SoundInit.SHAMAN_STEP_EVENT, 0.5F, 1.0F);
    }

    @Override
    public SoundEvent getCastSpellSound() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    private boolean tooManyCompanions() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(40D), EntitySelector.NO_SPECTATORS);
        if (!list.isEmpty()) {
            int spiders = 0;
            int othermobs = 0;
            for (LivingEntity livingEntity : list) {
                if (livingEntity.getType() == EntityType.CAVE_SPIDER) {
                    spiders++;
                } else if (livingEntity.getType() == EntityType.ZOMBIE || livingEntity.getType() == EntityType.SKELETON || livingEntity.getType() == EntityType.SPIDER) {
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
        public boolean canUse() {
            LivingEntity attacker = ShamanEntity.this.getLastHurtByMob();
            if (super.canUse() && attacker != null && attacker.getMainHandItem().getItem() instanceof ProjectileWeaponItem && ShamanEntity.this.distanceToSqr(attacker) > 12
                    && ShamanEntity.this.level().isRaining()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void castSpell() {
            LivingEntity attacker = ShamanEntity.this.getLastHurtByMob();
            if (attacker != null) {
                ServerLevel serverWorld = (ServerLevel) attacker.level();
                double posX = attacker.getX() + ShamanEntity.this.level().getRandom().nextInt(3);
                double posY = attacker.getY();
                double posZ = attacker.getZ() + ShamanEntity.this.level().getRandom().nextInt(3);
                BlockPos pos = BlockPos.containing(posX, posY, posZ);
                LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(attacker.level());
                lightningEntity.moveTo(pos, 0.0F, 0.0F);
                serverWorld.addFreshEntity(lightningEntity);
            }
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
        public boolean canUse() {
            return super.canUse();
        }

        @Override
        public void castSpell() {
            LivingEntity livingEntity = ShamanEntity.this.getTarget();
            if (livingEntity != null) {
                Holder<MobEffect> statusEffect;
                if (ShamanEntity.this.getRandom().nextFloat() <= 0.5F) {
                    statusEffect = MobEffects.WEAKNESS;
                } else {
                    statusEffect = MobEffects.POISON;
                }
                livingEntity.addEffect(new MobEffectInstance(statusEffect, 120 + ShamanEntity.this.getRandom().nextInt(80), 1, false, false, true));
            }

        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
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
        public boolean canUse() {
            return super.canUse() && !tooManyCompanions();
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
            ServerLevel serverWorld = (ServerLevel) ShamanEntity.this.level();
            int spellCount = 0;
            for (int i = 0; i < 20; ++i) {
                BlockPos blockPos = ShamanEntity.this.blockPosition().offset(-3 + ShamanEntity.this.getRandom().nextInt(6), ShamanEntity.this.getRandom().nextInt(3),
                        -3 + ShamanEntity.this.getRandom().nextInt(6));
                if (SpawnPlacements.checkSpawnRules(EntityType.ZOMBIE, serverWorld, MobSpawnType.EVENT, blockPos, serverWorld.getRandom())) {
                    spellCount++;
                    if (ShamanEntity.this.getRandom().nextFloat() < 0.6F) {
                        CaveSpider caveSpiderEntity = EntityType.CAVE_SPIDER.create(serverWorld);
                        caveSpiderEntity.moveTo(blockPos, serverWorld.getRandom().nextFloat() * 360F, 0.0F);
                        caveSpiderEntity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(blockPos), MobSpawnType.EVENT, null);
                        serverWorld.addFreshEntityWithPassengers(caveSpiderEntity);
                    } else {
                        Mob mobEntity;
                        int randomInt = ShamanEntity.this.getRandom().nextInt(3);
                        if (randomInt == 0) {
                            mobEntity = EntityType.ZOMBIE.create(serverWorld);
                        } else if (randomInt == 1) {
                            mobEntity = EntityType.SKELETON.create(serverWorld);
                        } else {
                            mobEntity = EntityType.SPIDER.create(serverWorld);
                        }
                        mobEntity.moveTo(blockPos, serverWorld.getRandom().nextFloat() * 360F, 0.0F);
                        mobEntity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(blockPos), MobSpawnType.EVENT, null);
                        serverWorld.addFreshEntityWithPassengers(mobEntity);
                    }
                }
                if (spellCount >= 3) {
                    break;
                }
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

    private class LookAtTargetGoalShaman extends SpellCastingEntity.LookAtTargetGoal {
        private LookAtTargetGoalShaman() {
            super();
        }

        @Override
        public void tick() {
            if (ShamanEntity.this.getTarget() != null) {
                ShamanEntity.this.getLookControl().setLookAt(ShamanEntity.this.getTarget(), (float) ShamanEntity.this.getMaxHeadYRot(), (float) ShamanEntity.this.getMaxHeadXRot());
            }

        }
    }

    private static class NormalAttack extends MeleeAttackGoal {
        private final ShamanEntity summonerEntity;

        public NormalAttack(PathfinderMob mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.summonerEntity = (ShamanEntity) mob;
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
            } else if (!this.mob.isWithinRestriction(livingEntity.blockPosition())) {
                return false;
            } else {
                return !(livingEntity instanceof Player) || !livingEntity.isSpectator() && !((Player) livingEntity).isCreative();
            }
        }

    }

}
