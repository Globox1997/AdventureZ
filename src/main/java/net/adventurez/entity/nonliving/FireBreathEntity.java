package net.adventurez.entity.nonliving;

import net.adventurez.init.EntityInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class FireBreathEntity extends AbstractHurtingProjectile {

    private int removeTicker;

    public FireBreathEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public FireBreathEntity(Level world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this(EntityInit.FIRE_BREATH, world);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.setDeltaMovement(velocityX, velocityY, velocityZ);
    }

    public FireBreathEntity(Level world, LivingEntity owner, Vec3 velocity) {
        super(EntityInit.FIRE_BREATH, owner, velocity, world);
        Vec3 newVec3d = this.getDeltaMovement().normalize().add(this.random.nextGaussian() * 0.1D, -this.random.nextDouble() * 0.1D, this.random.nextGaussian() * 0.1D);
        this.setDeltaMovement(newVec3d);
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
        return 0.9F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            for (int i = 0; i < 10; i++) {
                double d = (double) this.level().getRandom().nextGaussian() * 0.01D;
                double e = (double) this.level().getRandom().nextGaussian() * 0.01D;
                double f = (double) this.level().getRandom().nextGaussian() * 0.01D;
                this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d, e, f);
            }
        } else {
            removeTicker++;
            if (removeTicker >= 80)
                this.discard();
        }
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Environment(EnvType.CLIENT)
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
        Entity entity = this.getOwner();
        Entity hittedEntity = entityHitResult.getEntity();
        if (!this.level().isClientSide() && entity != null && hittedEntity instanceof LivingEntity) {
            hittedEntity.igniteForSeconds(8);
            hittedEntity.hurt(createDamageSource(this), 3.0F);
            this.discard();
        }

    }

    private DamageSource createDamageSource(Entity entity) {
        return entity.damageSources().source(EntityInit.FIRE_BREATH_KEY, entity);
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) {
            Entity entity = this.getOwner();
            if (entity == null || !(entity instanceof Mob) || this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
                if (this.level().isEmptyBlock(blockPos)) {
                    this.level().setBlockAndUpdate(blockPos, BaseFireBlock.getState(this.level(), blockPos));
                }
            }
            this.discard();
        }
    }

}
