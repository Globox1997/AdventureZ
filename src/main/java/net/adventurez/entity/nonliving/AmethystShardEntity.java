package net.adventurez.entity.nonliving;

import net.adventurez.entity.AmethystGolemEntity;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ParticleInit;
import net.adventurez.init.SoundInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AmethystShardEntity extends ThrownEntity {

    private int removeTicker;

    public AmethystShardEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public AmethystShardEntity(LivingEntity owner, World world) {
        super(EntityInit.AMETHYST_SHARD, owner, world);
        Vec3d vec3d = owner.getRotationVec(1.0F);
        this.updatePosition(this.getX() + vec3d.x, this.getY() - owner.getHeight() * 0.3D, this.getZ() + vec3d.z);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void tick() {
        super.tick();
        this.removeTicker++;
        if (this.getWorld().isClient()) {
            if (this.removeTicker > 133)
                for (int i = 0; i < 20; i++) {
                    double d = (double) this.getPos().getX() + 0.3F * this.getWorld().getRandom().nextFloat();
                    double e = (double) ((float) this.getPos().getY() + this.getWorld().getRandom().nextFloat() * 0.3F);
                    double f = (double) this.getPos().getZ() + 0.3F * this.getWorld().getRandom().nextFloat();
                    double g = (double) (this.getWorld().getRandom().nextFloat() * 0.2D);
                    double h = (double) this.getWorld().getRandom().nextFloat() * 0.2D;
                    double l = (double) (this.getWorld().getRandom().nextFloat() * 0.2D);
                    this.getWorld().addParticle(ParticleInit.AMETHYST_SHARD_PARTICLE, d, e, f, g, h, l);
                }
        } else {
            if (this.removeTicker >= 140) {
                this.playSound(SoundInit.SHARD_DESTROY_EVENT, 1.0F, 1.0F);
                this.discard();
            }
        }
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = this.getOwner();
        Entity hittedEntity = entityHitResult.getEntity();
        if (!this.getWorld().isClient() && entity != null && !(hittedEntity instanceof AmethystGolemEntity)) {
            this.playSound(SoundInit.SHARD_DESTROY_EVENT, 1.0F, 1.0F);
            hittedEntity.damage(createDamageSource(this), 7.0F);
            this.discard();
        }

    }

    private DamageSource createDamageSource(Entity entity) {
        return entity.getDamageSources().create(EntityInit.AMETHYST_SHARD_KEY, entity);
    }

    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.playSound(SoundInit.SHARD_DESTROY_EVENT, 1.0F, 1.0F);
        if (!this.getWorld().isClient()) {
            for (int i = 0; i < 20; i++) {
                double d = (double) this.getPos().getX() + 0.3F * this.getWorld().getRandom().nextFloat();
                double e = (double) ((float) this.getPos().getY() + this.getWorld().getRandom().nextFloat() * 0.3F);
                double f = (double) this.getPos().getZ() + 0.3F * this.getWorld().getRandom().nextFloat();
                double g = (double) (this.getWorld().getRandom().nextFloat() * 0.2D);
                double h = (double) this.getWorld().getRandom().nextFloat() * 0.2D;
                double l = (double) (this.getWorld().getRandom().nextFloat() * 0.2D);
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleInit.AMETHYST_SHARD_PARTICLE, d, e, f, 1, g, h, l, 1.0D);
            }
            this.discard();
        }
    }

}
