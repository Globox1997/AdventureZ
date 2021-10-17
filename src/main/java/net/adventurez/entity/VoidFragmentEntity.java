package net.adventurez.entity;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.adventurez.entity.nonliving.VoidBulletEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class VoidFragmentEntity extends FlyingEntity implements Monster {

    public static final TrackedData<Boolean> IS_VOID_ORB = DataTracker.registerData(VoidFragmentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public boolean isVoidOrb;

    public VoidFragmentEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createVoidFragmentAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10.0D);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        if (spawnReason == SpawnReason.SPAWN_EGG) {
            this.setVoidOrb(true);
        }

        return super.initialize(world, difficulty, spawnReason, (EntityData) entityData, entityTag);
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(IS_VOID_ORB, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putBoolean("IsVoidOrb", this.isVoidOrb);

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.isVoidOrb = tag.getBoolean("IsVoidOrb");
        this.setVoidOrb(this.isVoidOrb);
    }

    public void setVoidOrb(boolean orb) {
        this.isVoidOrb = orb;
        dataTracker.set(IS_VOID_ORB, orb);
        this.refreshPosition();
        this.calculateDimensions();
        if (orb) {
            this.setCustomName(new TranslatableText("entity.adventurez.void_orb"));
        }
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(1.0F, (this.isVoidOrb || this.dataTracker.get(IS_VOID_ORB)) ? 2.0F : 1.0F);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (IS_VOID_ORB.equals(data)) {
            this.refreshPosition();
            this.calculateDimensions();
            this.setYaw(this.headYaw);
            this.bodyYaw = this.headYaw;
        }

        super.onTrackedDataSet(data);
    }

    @Override
    public void updatePostDeath() {
        if (this.world.isClient) {
            for (int i = 0; i < 10; ++i) {
                double d = this.random.nextGaussian() * 0.05D;
                double e = this.random.nextGaussian() * 0.05D;
                double f = this.random.nextGaussian() * 0.05D;
                this.world.addParticle(ParticleTypes.END_ROD, this.getParticleX(1.0D), this.getRandomBodyY(), this.getParticleZ(1.0D), d, e, f);
            }
        }
        super.updatePostDeath();
    }

    @Override
    public void onDeath(DamageSource source) {
        if (!this.world.isClient && this.isVoidOrb) {
            Box box = new Box(this.getBlockPos());
            List<VoidShadowEntity> list = world.getEntitiesByClass(VoidShadowEntity.class, box.expand(120D), EntityPredicates.EXCEPT_SPECTATOR);
            for (int i = 0; i < list.size(); ++i) {
                list.get(i).damage(DamageSource.MAGIC, 40F);
            }
        }
        super.onDeath(source);
    }

    @Override
    public void mobTick() {
        super.mobTick();
        if (!this.world.isClient && this.world.getClosestPlayer(TargetPredicate.createAttackable().setBaseMaxDistance(0.8D), this) != null) {
            this.dead = true;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), this.isVoidOrb ? 4.0F : 3.0F, Explosion.DestructionType.NONE);
            this.discard();
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if ((source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity && ((PlayerEntity) source.getAttacker()).isCreative())) {
            return super.damage(source, amount);
        }
        if (this.isInvulnerableTo(source) || source.getSource() instanceof ThrownRockEntity || (this.isVoidOrb && !(source.getSource() instanceof VoidBulletEntity))) {
            return false;
        } else
            return super.damage(source, source.getSource() instanceof VoidBulletEntity ? this.getHealth() : amount);
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect, Entity entity) {
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

    @Override
    public boolean isPushable() {
        return false;
    }

}
