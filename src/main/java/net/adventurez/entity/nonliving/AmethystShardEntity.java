package net.adventurez.entity.nonliving;

import net.adventurez.entity.AmethystGolemEntity;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ParticleInit;
import net.adventurez.init.SoundInit;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class AmethystShardEntity extends ThrowableProjectile {

    private int removeTicker;

    public AmethystShardEntity(EntityType<? extends ThrowableProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public AmethystShardEntity(LivingEntity owner, Level world) {
        super(EntityInit.AMETHYST_SHARD, owner, world);
        Vec3 vec3d = owner.getViewVector(1.0F);
        this.absMoveTo(this.getX() + vec3d.x, this.getY() - owner.getBbHeight() * 0.3D, this.getZ() + vec3d.z);
    }

    @Override
    protected void defineSynchedData(Builder builder) {
    }

    @Override
    public void tick() {
        super.tick();
        this.removeTicker++;
        if (this.level().isClientSide()) {
            if (this.removeTicker > 133)
                for (int i = 0; i < 20; i++) {
                    double d = (double) this.position().x() + 0.3F * this.level().getRandom().nextFloat();
                    double e = (double) ((float) this.position().y() + this.level().getRandom().nextFloat() * 0.3F);
                    double f = (double) this.position().z() + 0.3F * this.level().getRandom().nextFloat();
                    double g = (double) (this.level().getRandom().nextFloat() * 0.2D);
                    double h = (double) this.level().getRandom().nextFloat() * 0.2D;
                    double l = (double) (this.level().getRandom().nextFloat() * 0.2D);
                    this.level().addParticle(ParticleInit.AMETHYST_SHARD_PARTICLE, d, e, f, g, h, l);
                }
        } else {
            if (this.removeTicker >= 140) {
                this.playSound(SoundInit.SHARD_DESTROY_EVENT, 1.0F, 1.0F);
                this.discard();
            }
        }
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = this.getOwner();
        Entity hittedEntity = entityHitResult.getEntity();
        if (!this.level().isClientSide() && entity != null && !(hittedEntity instanceof AmethystGolemEntity)) {
            this.playSound(SoundInit.SHARD_DESTROY_EVENT, 1.0F, 1.0F);
            hittedEntity.hurt(createDamageSource(this), 7.0F);
            this.discard();
        }

    }

    private DamageSource createDamageSource(Entity entity) {
        return entity.damageSources().source(EntityInit.AMETHYST_SHARD_KEY, entity);
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.playSound(SoundInit.SHARD_DESTROY_EVENT, 1.0F, 1.0F);
        if (!this.level().isClientSide()) {
            for (int i = 0; i < 20; i++) {
                double d = (double) this.position().x() + 0.3F * this.level().getRandom().nextFloat();
                double e = (double) ((float) this.position().y() + this.level().getRandom().nextFloat() * 0.3F);
                double f = (double) this.position().z() + 0.3F * this.level().getRandom().nextFloat();
                double g = (double) (this.level().getRandom().nextFloat() * 0.2D);
                double h = (double) this.level().getRandom().nextFloat() * 0.2D;
                double l = (double) (this.level().getRandom().nextFloat() * 0.2D);
                ((ServerLevel) this.level()).sendParticles(ParticleInit.AMETHYST_SHARD_PARTICLE, d, e, f, 1, g, h, l, 1.0D);
            }
            this.discard();
        }
    }

}
