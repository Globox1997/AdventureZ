package net.adventurez.entity;

import net.adventurez.init.EffectInit;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import java.util.List;
import net.minecraft.world.entity.EntityTypes;

public class NecromancerEntity extends SpellCastingEntity {

    public NecromancerEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 15;
    }

    public static AttributeSupplier.Builder createNecromancerAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 45.0D).add(Attributes.MOVEMENT_SPEED, 0.34D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.ATTACK_KNOCKBACK, 0.1D).add(Attributes.FOLLOW_RANGE, 38.0D);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtTargetGoalNecro());
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 1.0D));
        this.goalSelector.addGoal(4, new SummonPuppetGoal());
        this.goalSelector.addGoal(5, new MagicWitheringGoal());
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 5.0F, 1.0F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{WitherPuppetEntity.class})));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, new Class[]{WitherSkeleton.class})));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, Player.class, true)).setUnseenMemoryTicks(300));
    }

    public static boolean canSpawn(EntityType<NecromancerEntity> type, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return (checkMonsterSpawnRules(type, world, spawnReason, pos, random) && world.getBlockState(pos.below()).equals(Blocks.NETHER_BRICKS.defaultBlockState())
                && world.getEntitiesOfClass(NecromancerEntity.class, new AABB(pos).inflate(60D), EntitySelector.NO_SPECTATORS).isEmpty()) || spawnReason == EntitySpawnReason.SPAWNER;
    }

    @Override
    public void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        if (this.isSpellcasting() && this.spellTicks == 3) {
            int invisibleChance = this.level().getRandom().nextInt(5);
            if (invisibleChance == 0) {
                this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 100));
                if (this.level().isClientSide()) {
                    for (int i = 0; i < 10; ++i) {
                        ((ServerLevel) this.level()).sendParticles(ParticleTypes.SMOKE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 5, 0.0D, 0.0D, 0.0D, 1.0f);
                    }
                }
            }
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() == MobEffects.WITHER ? false : super.canBeAffected(effect);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.EVOKER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.EVOKER_DEATH;
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
    protected boolean considersEntityAsAlly(Entity other) {
        if (other == null) {
            return false;
        } else if (other == this) {
            return true;
        } else if (super.considersEntityAsAlly(other)) {
            return true;
        } else if (other instanceof WitherPuppetEntity) {
            return this.isAlliedTo(((WitherPuppetEntity) other).getOwner());
        } else {
            return false;
        }
    }

    private class MagicWitheringGoal extends SpellCastingEntity.CastSpellGoal {
        private MagicWitheringGoal() {
            super();
        }

        @Override
        public int getSpellTicks() {
            return 60;
        }

        @Override
        public int startTimeDelay() {
            return 60;
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = NecromancerEntity.this.getTarget();
            if (!super.canUse()) {
                return false;
            } else if (NecromancerEntity.this.distanceToSqr(livingEntity) < 15.0D && NecromancerEntity.this.tickCount >= this.startTime) {
                return true;
            } else
                return false;
        }

        @Override
        public void castSpell() {
            LivingEntity livingEntity = NecromancerEntity.this.getTarget();
            if (livingEntity != null) {
                livingEntity.addEffect(new MobEffectInstance(EffectInit.WITHERING, 180 + NecromancerEntity.this.level().getRandom().nextInt(5) * 20, 0));
                ((ServerLevel) NecromancerEntity.this.level()).sendParticles(ParticleTypes.SMOKE, livingEntity.getX(), livingEntity.getY(0.5D), livingEntity.getZ(), 60, livingEntity.getBbWidth(), livingEntity.getBbHeight(), livingEntity.getBbWidth(), 0.0D);
            }

        }

        @Override
        public SoundEvent getSoundPrepare() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        @Override
        public SpellCastingEntity.Spell getSpell() {
            return Spell.WITHERING;
        }
    }

    private class SummonPuppetGoal extends SpellCastingEntity.CastSpellGoal {
        private final TargetingConditions CLOSE_PUPPET_PREDICATE = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight().ignoreInvisibilityTesting();

        private SummonPuppetGoal() {
            super();
        }

        @Override
        public boolean canUse() {
            if (!super.canUse() || arePuppetsNearby()) {
                return false;
            } else {
                int i = ((ServerLevel) NecromancerEntity.this.level())
                        .getNearbyEntities(WitherPuppetEntity.class, this.CLOSE_PUPPET_PREDICATE, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(16.0D)).size();
                return NecromancerEntity.this.random.nextInt(8) + 1 > i;
            }
        }

        @Override
        public int getSpellTicks() {
            return 100;
        }

        @Override
        public int startTimeDelay() {
            return 340;
        }

        @Override
        public void castSpell() {
            ServerLevel serverWorld = (ServerLevel) NecromancerEntity.this.level();
            int spellCount = 0;
            for (int i = 0; i < 20; ++i) {
                BlockPos blockPos = NecromancerEntity.this.blockPosition().offset(-2 + NecromancerEntity.this.getRandom().nextInt(5), 1, -2 + NecromancerEntity.this.getRandom().nextInt(5));
                if (SpawnPlacements.checkSpawnRules(EntityInit.WITHER_PUPPET, serverWorld, EntitySpawnReason.EVENT, blockPos, serverWorld.getRandom())) {
                    spellCount++;
                    WitherPuppetEntity puppet = EntityInit.WITHER_PUPPET.create(serverWorld, EntitySpawnReason.MOB_SUMMONED);
                    puppet.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(blockPos), EntitySpawnReason.EVENT, null);
                    puppet.snapTo(blockPos, serverWorld.getRandom().nextFloat() * 360F, 0.0F);
                    puppet.setOwner(NecromancerEntity.this);
                    puppet.setLifeTicks(20 * (40 + NecromancerEntity.this.getRandom().nextInt(90)));
                    serverWorld.addFreshEntityWithPassengers(puppet);
                    int skeletonChance = serverWorld.getRandom().nextInt(14);
                    if (skeletonChance == 0) {
                        WitherSkeleton witherSkeletonEntity = EntityTypes.WITHER_SKELETON.create(serverWorld, EntitySpawnReason.MOB_SUMMONED);
                        witherSkeletonEntity.snapTo(blockPos, serverWorld.getRandom().nextFloat() * 360F, 0.0F);
                        witherSkeletonEntity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(blockPos), EntitySpawnReason.EVENT, null);
                        serverWorld.addFreshEntityWithPassengers(witherSkeletonEntity);
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
            if (NecromancerEntity.this.getTarget() != null) {
                NecromancerEntity.this.getLookControl().setLookAt(NecromancerEntity.this.getTarget(), (float) NecromancerEntity.this.getMaxHeadYRot(),
                        (float) NecromancerEntity.this.getMaxHeadXRot());
            }
        }
    }

    private boolean arePuppetsNearby() {
        List<WitherPuppetEntity> list = this.level().getEntitiesOfClass(WitherPuppetEntity.class, this.getBoundingBox().inflate(40D), EntitySelector.NO_SPECTATORS);
        return !list.isEmpty();
    }

}
