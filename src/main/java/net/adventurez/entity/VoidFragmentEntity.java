package net.adventurez.entity;

import java.util.List;
import org.jetbrains.annotations.Nullable;

import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.adventurez.entity.nonliving.VoidBulletEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;

public class VoidFragmentEntity extends FlyingMob implements Enemy {

    public static final EntityDataAccessor<Boolean> IS_VOID_ORB = SynchedEntityData.defineId(VoidFragmentEntity.class, EntityDataSerializers.BOOLEAN);
    public boolean isVoidOrb;
    private int deathTick;

    public VoidFragmentEntity(EntityType<? extends FlyingMob> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createVoidFragmentAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0D);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        if (spawnReason == MobSpawnType.SPAWN_EGG) {
            this.setVoidOrb(true);
        }

        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_VOID_ORB, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IsVoidOrb", this.isVoidOrb);

    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.isVoidOrb = tag.getBoolean("IsVoidOrb");
        this.setVoidOrb(this.isVoidOrb);
    }

    public void setVoidOrb(boolean orb) {
        this.isVoidOrb = orb;
        entityData.set(IS_VOID_ORB, orb);
        this.reapplyPosition();
        this.refreshDimensions();
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose).scale(1.0F, (this.isVoidOrb || this.entityData.get(IS_VOID_ORB)) ? 2.1F : 1.2F);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        if (IS_VOID_ORB.equals(data)) {
            this.reapplyPosition();
            this.refreshDimensions();
            this.setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
        }

        super.onSyncedDataUpdated(data);
    }

    @Override
    public void tickDeath() {
        if (this.level().isClientSide()) {
            for (int i = 0; i < 10; ++i) {
                double d = this.random.nextGaussian() * 0.05D;
                double e = this.random.nextGaussian() * 0.05D;
                double f = this.random.nextGaussian() * 0.05D;
                this.level().addParticle(ParticleTypes.END_ROD, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d, e, f);
            }
        }
        ++this.deathTick;
        if (this.deathTick >= 20 && !this.level().isClientSide()) {
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide() && this.isVoidOrb) {
            AABB box = new AABB(this.blockPosition());
            List<VoidShadowEntity> list = this.level().getEntitiesOfClass(VoidShadowEntity.class, box.inflate(120D), EntitySelector.NO_SPECTATORS);
            for (int i = 0; i < list.size(); ++i) {
                list.get(i).hurt(this.damageSources().magic(), 40f);
            }
        }
        super.die(source);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (!this.level().isClientSide() && this.level().getNearestPlayer(TargetingConditions.forCombat().range(0.8D), this) != null) {
            this.dead = true;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.isVoidOrb ? 4.0F : 3.0F, ExplosionInteraction.NONE);
            this.discard();
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return super.hurt(source, amount);
        } else if ((source.getEntity() != null && source.getEntity() instanceof Player && ((Player) source.getEntity()).isCreative())) {
            return super.hurt(source, amount);
        }
        if (this.isInvulnerableTo(source) || source.getDirectEntity() instanceof ThrownRockEntity || (this.isVoidOrb && !(source.getDirectEntity() instanceof VoidBulletEntity))) {
            return false;
        } else {
            return super.hurt(source, source.getDirectEntity() instanceof VoidBulletEntity ? this.getHealth() : amount);
        }
    }

    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean addEffect(MobEffectInstance effect, Entity entity) {
        return false;
    }

    @Override
    public boolean canRide(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortal(boolean allowVehicles) {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

}
