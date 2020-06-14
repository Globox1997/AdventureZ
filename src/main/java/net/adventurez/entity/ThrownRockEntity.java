package net.adventurez.entity;

// import net.adventurez.init.EntityInit;
// import net.adventurez.network.EntitySpawnPacket;
// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.minecraft.block.BlockState;
// import net.minecraft.entity.Entity;
// import net.minecraft.entity.EntityType;
// import net.minecraft.entity.LivingEntity;
// import net.minecraft.entity.damage.DamageSource;
// import net.minecraft.entity.effect.StatusEffectInstance;
// import net.minecraft.entity.effect.StatusEffects;
// import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
// import net.minecraft.fluid.FluidState;
// import net.minecraft.network.Packet;
// import net.minecraft.particle.BlockStateParticleEffect;
// import net.minecraft.particle.ParticleEffect;
// import net.minecraft.particle.ParticleTypes;
// import net.minecraft.util.hit.EntityHitResult;
// import net.minecraft.util.hit.HitResult;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.world.BlockView;
// import net.minecraft.world.GameRules;
// import net.minecraft.world.World;
// import net.minecraft.world.explosion.Explosion;

// public class ThrownRockEntity extends ExplosiveProjectileEntity {

//    public ThrownRockEntity(EntityType<? extends ThrownRockEntity> entityType, World world) {
//       super(entityType, world);
//    }

//    public ThrownRockEntity(World world, LivingEntity owner, double directionX, double directionY, double directionZ) {
//       super(EntityInit.THROWNROCK_ENTITY, owner, directionX, directionY, directionZ, world);

//    }

//    @Environment(EnvType.CLIENT)
//    public ThrownRockEntity(World world, double x, double y, double z, double directionX, double directionY,
//          double directionZ) {
//       super(EntityInit.THROWNROCK_ENTITY, x, y, z, directionX, directionY, directionZ, world);

//    }

//    @Override
//    public Packet<?> createSpawnPacket() {
//       return EntitySpawnPacket.createPacket(this);
//    }

//    @Override
//    protected float getDrag() {
//       return 1F;
//    }

//    @Override
//    public boolean isOnFire() {
//       return false;
//    }

//    @Override
//    public float getEffectiveExplosionResistance(Explosion explosion, BlockView world, BlockPos pos,
//          BlockState blockState, FluidState fluidState, float max) {
//       return 2F;
//    }

//    @Override
//    protected void onEntityHit(EntityHitResult entityHitResult) {
//       super.onEntityHit(entityHitResult);
//       if (!this.world.isClient) {
//          Entity entity = entityHitResult.getEntity();
//          Entity entity2 = this.getOwner();
//          boolean bl2;
//          if (entity2 instanceof LivingEntity) {
//             LivingEntity livingEntity = (LivingEntity) entity2;
//             bl2 = entity.damage(DamageSource.ANVIL, 16F);// (this, livingEntity), 8.0F);
//             if (bl2) {
//                if (entity.isAlive()) {
//                   this.dealDamage(livingEntity, entity);
//                } else {
//                   livingEntity.heal(5.0F);
//                }
//             }
//          } else {
//             bl2 = entity.damage(DamageSource.ANVIL, 16F);
//          }

//          if (bl2 && entity instanceof LivingEntity) {
//             int i = 10;
//             if (i > 0) {
//                ((LivingEntity) entity)
//                      .addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200 * i, 2));
//                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 5 * i, 0));
//                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * i, 0));
//                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 60 * i, 1));
//             }
//          }

//       }
//    }

//    @Override
//    protected void onCollision(HitResult hitResult) {
//       super.onCollision(hitResult);
//       if (!this.world.isClient) {
//          Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.field_19388)
//                ? Explosion.DestructionType.DESTROY
//                : Explosion.DestructionType.NONE;
//          this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, destructionType);
//          this.remove();
//       }

//    }

//    @Override
//    public boolean collides() {
//       return false;
//    }

//    @Override
//    protected ParticleEffect getParticleType() {
//       BlockState state = this.getLandingBlockState();
//       return new BlockStateParticleEffect(ParticleTypes.BLOCK, state);
//    }

//    @Override
//    public boolean damage(DamageSource source, float amount) {
//       return false; // true?
//    }

//    @Override
//    protected boolean isBurning() {
//       return false;
//    }

//    @Override
//    @Environment(EnvType.CLIENT)
//    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
//       return true;
//    }
// }

import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.adventurez.network.EntitySpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ThrownRockEntity extends ThrownItemEntity {
   public ThrownRockEntity(EntityType<? extends ThrownRockEntity> entityType, World world) {
      super(entityType, world);
   }

   public ThrownRockEntity(World world, LivingEntity owner) {
      super(EntityInit.THROWNROCK_ENTITY, owner, world);
   }

   public ThrownRockEntity(World world, double x, double y, double z) {
      super(EntityInit.THROWNROCK_ENTITY, x, y, z, world);
   }

   @Override
   protected Item getDefaultItem() {
      return Items.STONE;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return EntitySpawnPacket.createPacket(this);
   }

   @Environment(EnvType.CLIENT)
   private ParticleEffect getParticleParameters() {
      BlockState state = this.getLandingBlockState();
      return new BlockStateParticleEffect(ParticleTypes.BLOCK, state);
   }

   @Override
   @Environment(EnvType.CLIENT)
   public void handleStatus(byte status) {
      if (status == 3) {
         ParticleEffect particleEffect = this.getParticleParameters();

         for (int i = 0; i < 32; ++i) {
            this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
         }
      }

   }

   @Override
   protected void onCollision(HitResult hitResult) {
      super.onCollision(hitResult);
      BlockState state = this.getLandingBlockState();
      if (!this.world.isClient) {
         this.world.playSound(null, this.getBlockPos(), SoundInit.ROCK_IMPACT_EVENT, SoundCategory.BLOCKS, 0.7F, 1F);
         this.world.sendEntityStatus(this, (byte) 3);
         this.remove();
      } else {
         for (int i = 0; i < 32; ++i) {
            this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), this.getX(), this.getY(),
                  this.getZ(), 0.0D, 0.0D, 0.0D);
         }
      }

   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      if (!this.world.isClient) {
         Entity entity = entityHitResult.getEntity();
         Entity entity2 = this.getOwner();
         boolean bl2;
         if (entity2 instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity2;
            bl2 = entity.damage(DamageSource.ANVIL, 16F);
            if (bl2) {
               if (entity.isAlive()) {
                  this.dealDamage(livingEntity, entity);
               } else {
                  livingEntity.heal(5.0F);
               }
            }
         } else {
            bl2 = entity.damage(DamageSource.ANVIL, 16F);
         }

         if (bl2 && entity instanceof LivingEntity) {
            int i = 10;
            if (i > 0) {
               ((LivingEntity) entity)
                     .addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200 * i, 2));
               ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 5 * i, 0));
               ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * i, 0));
               ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 60 * i, 1));
            }
         }

      }
   }
}