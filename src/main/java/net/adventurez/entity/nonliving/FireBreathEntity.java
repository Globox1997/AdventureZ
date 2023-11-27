package net.adventurez.entity.nonliving;

import net.adventurez.init.EntityInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
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
    public FireBreathEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this(EntityInit.FIRE_BREATH, world);
        this.refreshPositionAndAngles(x, y, z, this.getYaw(), this.getPitch());
        this.setVelocity(velocityX, velocityY, velocityZ);
    }

    public FireBreathEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(EntityInit.FIRE_BREATH, owner, velocityX, velocityY, velocityZ, world);
        Vec3d newVec3d = this.getVelocity().normalize().add(this.random.nextGaussian() * 0.1D, -this.random.nextDouble() * 0.1D, this.random.nextGaussian() * 0.1D);
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
        if (this.getWorld().isClient()) {
            for (int i = 0; i < 10; i++) {
                double d = (double) this.getWorld().getRandom().nextGaussian() * 0.01D;
                double e = (double) this.getWorld().getRandom().nextGaussian() * 0.01D;
                double f = (double) this.getWorld().getRandom().nextGaussian() * 0.01D;
                this.getWorld().addParticle(ParticleTypes.FLAME, this.getParticleX(1.0D), this.getRandomBodyY(), this.getParticleZ(1.0D), d, e, f);
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
        if (!this.getWorld().isClient() && entity != null && hittedEntity instanceof LivingEntity) {
            hittedEntity.setOnFireFor(8);
            hittedEntity.damage(createDamageSource(this), 3.0F);
            this.discard();
        }

    }

    private DamageSource createDamageSource(Entity entity) {
        return entity.getDamageSources().create(EntityInit.FIRE_BREATH_KEY, entity);
    }

    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient()) {
            Entity entity = this.getOwner();
            if (entity == null || !(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                if (this.getWorld().isAir(blockPos)) {
                    this.getWorld().setBlockState(blockPos, AbstractFireBlock.getState(this.getWorld(), blockPos));
                }
            }
            this.discard();
        }
    }

}
