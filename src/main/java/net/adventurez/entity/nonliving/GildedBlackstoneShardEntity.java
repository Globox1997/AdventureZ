package net.adventurez.entity.nonliving;

import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class GildedBlackstoneShardEntity extends ThrowableItemProjectile {
    public GildedBlackstoneShardEntity(EntityType<? extends GildedBlackstoneShardEntity> entityType, Level world) {
        super(entityType, world);
    }

    public GildedBlackstoneShardEntity(Level world, LivingEntity owner) {
        super(EntityInit.GILDED_BLACKSTONE_SHARD, owner, world);
    }

    public GildedBlackstoneShardEntity(Level world, double x, double y, double z) {
        super(EntityInit.GILDED_BLACKSTONE_SHARD, x, y, z, world);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            for (int i = 0; i < 22; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(),
                        (this.level().getRandom().nextDouble() - 0.5D) * 0.08D, (this.level().getRandom().nextDouble() - 0.5D) * 0.08D,
                        (this.level().getRandom().nextDouble() - 0.5D) * 0.08D);
            }
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(this.damageSources().mobProjectile(this, this.getOwner() instanceof LivingEntity ? (LivingEntity) this.getOwner() : null), 1.0F);
        if (entityHitResult.getEntity().getType() == EntityInit.BLACKSTONE_GOLEM) {
            entityHitResult.getEntity().hurt(this.damageSources().mobProjectile(this, this.getOwner() instanceof LivingEntity ? (LivingEntity) this.getOwner() : null), 10.0F);
            ((LivingEntity) entityHitResult.getEntity()).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 0));
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.7F, 1F);
            if (this.level().getRandom().nextInt(8) == 0) {
                int i = 1;
                if (this.level().getRandom().nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; ++j) {
                    Silverfish silverfishEntity = (Silverfish) EntityType.SILVERFISH.create(this.level());
                    silverfishEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                    this.level().addFreshEntity(silverfishEntity);
                }
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }

    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.GILDED_BLACKSTONE_SHARD;
    }

}
