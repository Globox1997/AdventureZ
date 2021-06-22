package net.adventurez.entity.nonliving;

import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;
import net.adventurez.network.EntitySpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class GildedStoneEntity extends ThrownItemEntity {
    public GildedStoneEntity(EntityType<? extends GildedStoneEntity> entityType, World world) {
        super(entityType, world);
    }

    public GildedStoneEntity(World world, LivingEntity owner) {
        super(EntityInit.GILDEDSTONE_ENTITY, owner, world);
    }

    public GildedStoneEntity(World world, double x, double y, double z) {
        super(EntityInit.GILDEDSTONE_ENTITY, x, y, z, world);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            for (int i = 0; i < 22; ++i) {
                this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D,
                        ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 1.0F);
        if (entityHitResult.getEntity().getType() == EntityInit.STONEGOLEM_ENTITY) {
            entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 10.0F);
            ((LivingEntity) entityHitResult.getEntity()).addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 600, 0));
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.playSound(null, this.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 0.7F, 1F);
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; ++j) {
                    SilverfishEntity silverfishEntity = (SilverfishEntity) EntityType.SILVERFISH.create(this.world);
                    silverfishEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                    this.world.spawnEntity(silverfishEntity);
                }
            }

            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }

    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.GILDED_STONE;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.createPacket(this);
    }
}