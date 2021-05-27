package net.adventurez.entity.nonliving;

import net.adventurez.entity.VoidShadeEntity;
import net.adventurez.init.EntityInit;
import net.adventurez.network.EntitySpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.network.Packet;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
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
    public VoidBulletEntity(World world, double x, double y, double z, double velocityX, double velocityY,
            double velocityZ) {
        this(EntityInit.VOID_BULLET_ENTITY, world);
        this.refreshPositionAndAngles(x, y, z, this.yaw, this.pitch);
        this.setVelocity(velocityX, velocityY, velocityZ);
    }

    public VoidBulletEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(EntityInit.VOID_BULLET_ENTITY, owner, velocityX, velocityY, velocityZ, world);
        this.refreshPositionAndAngles(owner.getX() + velocityX * 1.2D,
                owner.getY() + owner.getBoundingBox().getYLength() * 0.6D + velocityY * 1.2D,
                owner.getZ() + velocityZ * 1.2D, -owner.yaw, owner.pitch);
        this.prevPitch = owner.pitch;
        this.prevYaw = -owner.yaw;

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
        removeTicker++;
        if (removeTicker >= 80) {
            this.remove();
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
        Entity entity = this.getOwner();
        Entity hittedEntity = entityHitResult.getEntity();
        if (!this.world.isClient && entity != null && !(hittedEntity instanceof VoidShadeEntity)) {
            this.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1.0F, 1.0F);
            hittedEntity.damage(createDamageSource(this), 4.0F);
            this.remove();
        }

    }

    public static DamageSource createDamageSource(Entity entity) {
        return new EntityDamageSource("voidBullet", entity).setProjectile();
    }

    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.remove();
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.createPacket(this);
    }

}