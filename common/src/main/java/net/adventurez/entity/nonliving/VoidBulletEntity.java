package net.adventurez.entity.nonliving;

import net.minecraft.core.registries.Registries;

import net.adventurez.entity.VoidShadeEntity;
import net.adventurez.init.EntityInit;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class VoidBulletEntity extends AbstractHurtingProjectile {

    private int removeTicker;

    public VoidBulletEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public VoidBulletEntity(Level world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this(EntityInit.VOID_BULLET, world);
        this.snapTo(x, y, z, this.getYRot(), this.getXRot());
        this.setDeltaMovement(velocityX, velocityY, velocityZ);
    }

    public VoidBulletEntity(LivingEntity owner, Vec3 velocity, Level world) {
        super(EntityInit.VOID_BULLET, owner, velocity, world);
        this.snapTo(owner.getX() + velocity.x() * 1.2D, owner.getY() + owner.getBoundingBox().getYsize() * 0.6D + velocity.y() * 1.2D, owner.getZ() + velocity.z() * 1.2D,
                -owner.getYRot(), owner.getXRot());
        this.xRotO = owner.getXRot();
        this.setYRot(-owner.getYRot());
        this.accelerationPower = 0.32D;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AIR.defaultBlockState());
    }

    @Override
    protected float getInertia() {
        return 0.7F;
    }

    @Override
    public void tick() {
        super.tick();
        this.removeTicker++;
        if (this.level().isClientSide()) {
            if (this.removeTicker > 75)
                for (int i = 0; i < 20; i++) {
                    double d = (double) this.position().x() + 0.3F * this.level().getRandom().nextFloat();
                    double e = (double) ((float) this.position().y() + this.level().getRandom().nextFloat() * 0.3F);
                    double f = (double) this.position().z() + 0.3F * this.level().getRandom().nextFloat();
                    double g = (double) (this.level().getRandom().nextFloat() * 0.2D);
                    double h = (double) this.level().getRandom().nextFloat() * 0.2D;
                    double l = (double) (this.level().getRandom().nextFloat() * 0.2D);
                    this.level().addParticle(ParticleTypes.SMOKE, d, e, f, g, h, l);
                }
        } else if (this.removeTicker >= 80) {
            this.discard();
        }
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 16384.0D;
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity hittedEntity = entityHitResult.getEntity();

        if (!this.level().isClientSide() && !(hittedEntity instanceof VoidBulletEntity) && !(hittedEntity instanceof VoidShadeEntity)) {
            this.playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F);
            hittedEntity.hurt(createDamageSource(this), 7.0F);
            this.discard();
        }

    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) {
            for (int i = 0; i < 20; i++) {
                double d = (double) this.position().x() + 0.3F * this.level().getRandom().nextFloat();
                double e = (double) ((float) this.position().y() + this.level().getRandom().nextFloat() * 0.3F);
                double f = (double) this.position().z() + 0.3F * this.level().getRandom().nextFloat();
                double g = (double) (this.level().getRandom().nextFloat() * 0.1D);
                double h = (double) this.level().getRandom().nextFloat() * 0.1D;
                double l = (double) (this.level().getRandom().nextFloat() * 0.1D);
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.SMOKE, d, e, f, 3, g, h, l, 0.1D);
            }
            this.playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F);
            this.discard();
        }
    }

    private DamageSource createDamageSource(Entity entity) {
        return new DamageSource(entity.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(EntityInit.VOID_BULLET_KEY), entity);
    }

}
