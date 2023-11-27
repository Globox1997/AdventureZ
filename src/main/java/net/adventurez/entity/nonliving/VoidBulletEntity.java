package net.adventurez.entity.nonliving;

import net.adventurez.entity.VoidShadeEntity;
import net.adventurez.init.EntityInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class VoidBulletEntity extends ExplosiveProjectileEntity {

    private int removeTicker;

    public VoidBulletEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public VoidBulletEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this(EntityInit.VOID_BULLET, world);
        this.refreshPositionAndAngles(x, y, z, this.getYaw(), this.getPitch());
        this.setVelocity(velocityX, velocityY, velocityZ);
    }

    public VoidBulletEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(EntityInit.VOID_BULLET, owner, velocityX, velocityY, velocityZ, world);
        this.refreshPositionAndAngles(owner.getX() + velocityX * 1.2D, owner.getY() + owner.getBoundingBox().getYLength() * 0.6D + velocityY * 1.2D, owner.getZ() + velocityZ * 1.2D, -owner.getYaw(),
                owner.getPitch());
        this.prevPitch = owner.getPitch();
        this.setYaw(-owner.getYaw());
        this.powerX *= 3.6D;
        this.powerZ *= 3.6D;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected ParticleEffect getParticleType() {
        return new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.AIR.getDefaultState());
    }

    @Override
    protected float getDrag() {
        return 0.7F;
    }

    @Override
    public void tick() {
        super.tick();
        this.removeTicker++;
        if (this.getWorld().isClient()) {
            if (this.removeTicker > 75)
                for (int i = 0; i < 20; i++) {
                    double d = (double) this.getPos().getX() + 0.3F * this.getWorld().getRandom().nextFloat();
                    double e = (double) ((float) this.getPos().getY() + this.getWorld().getRandom().nextFloat() * 0.3F);
                    double f = (double) this.getPos().getZ() + 0.3F * this.getWorld().getRandom().nextFloat();
                    double g = (double) (this.getWorld().getRandom().nextFloat() * 0.2D);
                    double h = (double) this.getWorld().getRandom().nextFloat() * 0.2D;
                    double l = (double) (this.getWorld().getRandom().nextFloat() * 0.2D);
                    this.getWorld().addParticle(ParticleTypes.SMOKE, d, e, f, g, h, l);
                }
        } else if (this.removeTicker >= 80) {
            this.discard();
        }
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        return distance < 16384.0D;
    }

    @Override
    public float getBrightnessAtEyes() {
        return 1.0F;
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity hittedEntity = entityHitResult.getEntity();

        if (!this.getWorld().isClient() && !(hittedEntity instanceof VoidBulletEntity) && !(hittedEntity instanceof VoidShadeEntity)) {
            this.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1.0F, 1.0F);
            hittedEntity.damage(createDamageSource(this), 7.0F);
            this.discard();
        }

    }

    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient()) {
            for (int i = 0; i < 20; i++) {
                double d = (double) this.getPos().getX() + 0.3F * this.getWorld().getRandom().nextFloat();
                double e = (double) ((float) this.getPos().getY() + this.getWorld().getRandom().nextFloat() * 0.3F);
                double f = (double) this.getPos().getZ() + 0.3F * this.getWorld().getRandom().nextFloat();
                double g = (double) (this.getWorld().getRandom().nextFloat() * 0.1D);
                double h = (double) this.getWorld().getRandom().nextFloat() * 0.1D;
                double l = (double) (this.getWorld().getRandom().nextFloat() * 0.1D);
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.SMOKE, d, e, f, 3, g, h, l, 0.1D);
            }
            this.discard();
        }
    }

    private DamageSource createDamageSource(Entity entity) {
        return entity.getDamageSources().create(EntityInit.VOID_BULLET_KEY, entity);
    }

}
