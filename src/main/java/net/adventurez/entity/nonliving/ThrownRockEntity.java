package net.adventurez.entity.nonliving;

import net.adventurez.init.EffectInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;

@SuppressWarnings("deprecation")
public class ThrownRockEntity extends ThrowableItemProjectile {

    public ThrownRockEntity(EntityType<? extends ThrownRockEntity> entityType, Level world) {
        super(entityType, world);
    }

    public ThrownRockEntity(Level world, LivingEntity owner) {
        super(EntityInit.THROWN_ROCK, owner, world, new ItemStack(Items.BLACKSTONE));
    }

    public ThrownRockEntity(Level world, double x, double y, double z) {
        super(EntityInit.THROWN_ROCK, x, y, z, world, new ItemStack(Items.BLACKSTONE));
    }

    @Override
    public Item getDefaultItem() {
        return Items.BLACKSTONE;
    }

    // getLandingBlockState found at addSoulSpeedBoostIfNeeded in LivingEntity
    @Environment(EnvType.CLIENT)
    private ParticleOptions getParticleParameters() {
        BlockState state = this.getBlockStateOnLegacy();
        return new BlockParticleOption(ParticleTypes.BLOCK, state);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            ParticleOptions particleEffect = this.getParticleParameters();
            for (int i = 0; i < 32; ++i)
                this.level().addParticle(particleEffect, this.getX() + this.level().getRandom().nextDouble() * 0.35D - 0.175D, this.getY(),
                        this.getZ() + this.level().getRandom().nextDouble() * 0.35D - 0.175D, 0.0D, 0.1D, 0.0D);
        }

    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        BlockState state = this.getBlockStateOnLegacy();
        if (this.level().isClientSide()) {
            for (int i = 0; i < 32; ++i)
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX() + this.level().getRandom().nextDouble() * 0.35D - 0.175D, this.getY(),
                        this.getZ() + this.level().getRandom().nextDouble() * 0.35D - 0.175D, 0.0D, 0.1D, 0.0D);
        } else {
            if (this.getOwner() instanceof Player playerEntity && playerEntity.hasEffect(EffectInit.BLACKSTONED_HEART)) {
                this.level().explode(this, this.getX(), this.getEyeY(), this.getZ(), 1.5F, false, Level.ExplosionInteraction.MOB);
            }
            this.level().playSound(null, this.blockPosition(), SoundInit.ROCK_IMPACT_EVENT, SoundSource.BLOCKS, 0.7F, 1F);
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    // Causes crash if not overridden, maybe due to getStack client?
    // @Override
    // public ItemStack getStack() {
    // // super.getStack()
    // ItemStack itemStack = this.getStack();
    // return itemStack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemStack;
    // }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide()) {
            Entity entity = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            DamageSource damageSource = createDamageSource(this, owner == null ? this : owner);
            if (owner instanceof LivingEntity livingEntity) {
                if (entity.hurtServer((ServerLevel) this.level(), damageSource, 16F)) {
                    if (entity instanceof LivingEntity livingEntity2) {
                        livingEntity.setLastHurtMob(livingEntity2);
                    }
                }
            }
            if (entity instanceof LivingEntity livingEntity) {
                int slownessAddition = 400;

                if (this.getItem().getItem() == this.getDefaultItem()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 2000, 2));
                    slownessAddition = 200;
                }
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50, 0));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, slownessAddition, slownessAddition / 200 - 1));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 1));
            }

        }
    }

    private DamageSource createDamageSource(Entity entity, Entity owner) {
        return entity.damageSources().source(EntityInit.ROCK_KEY, entity, owner);
    }

}
