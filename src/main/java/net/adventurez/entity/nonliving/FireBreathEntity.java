package net.adventurez.entity.nonliving;

import net.adventurez.init.EntityInit;
import net.adventurez.network.EntitySpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.network.Packet;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class FireBreathEntity extends ExplosiveProjectileEntity {

    private int removeTicker;

    public FireBreathEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public FireBreathEntity(World world, double x, double y, double z, double velocityX, double velocityY,
            double velocityZ) {
        this(EntityInit.FIRE_BREATH_ENTITY, world);
        this.refreshPositionAndAngles(x, y, z, this.yaw, this.pitch);
        this.setVelocity(velocityX, velocityY, velocityZ);
    }

    public FireBreathEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(EntityInit.FIRE_BREATH_ENTITY, owner, velocityX, velocityY, velocityZ, world);
        Vec3d newVec3d = this.getVelocity().normalize().add(this.random.nextGaussian() * 0.1D,
                -this.random.nextDouble() * 0.1D, this.random.nextGaussian() * 0.1D);
        this.setVelocity(newVec3d);
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
        return 0.9F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient) {
            for (int i = 0; i < 10; i++) {
                double d = (double) world.random.nextGaussian() * 0.01D;
                double e = (double) world.random.nextGaussian() * 0.01D;
                double f = (double) world.random.nextGaussian() * 0.01D;
                this.world.addParticle(ParticleTypes.FLAME, this.getParticleX(1.0D), this.getRandomBodyY(),
                        this.getParticleZ(1.0D), d, e, f);
            }
        }
        removeTicker++;
        if (!this.world.isClient && removeTicker >= 80) {
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
        if (!this.world.isClient && entity != null && hittedEntity instanceof LivingEntity) {
            hittedEntity.setOnFireFor(8);
            this.remove();
        }

    }

    public static DamageSource createDamageSource(Entity entity) {
        return new EntityDamageSource("voidBullet", entity).setProjectile();
    }

    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.world.isClient) {
            Entity entity = this.getOwner();
            if (entity == null || !(entity instanceof MobEntity)
                    || this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                if (this.world.isAir(blockPos)) {
                    this.world.setBlockState(blockPos, AbstractFireBlock.getState(this.world, blockPos));
                }
            }
            this.remove();
        }

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