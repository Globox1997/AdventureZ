package net.adventurez.entity;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.UnmodifiableIterator;

import net.adventurez.entity.goal.DragonFindOwnerGoal;
import net.adventurez.entity.goal.DragonFlyRandomlyGoal;
import net.adventurez.entity.goal.DragonSitGoal;
import net.adventurez.init.ItemInit;
import net.adventurez.init.SoundInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

// import net.minecraft.entity.passive.WolfEntity;
// import net.minecraft.entity.passive.HorseBaseEntity;
// import net.minecraft.entity.passive.HorseEntity;
// import net.minecraft.entity.passive.TameableEntity;
// import net.minecraft.entity.mob.FlyingEntity;
// import net.minecraft.entity.mob.GhastEntity;
// import net.minecraft.entity.vehicle.BoatEntity;
// import net.minecraft.fluid.FluidState;
// import net.minecraft.entity.boss.dragon.EnderDragonEntity;

public class DragonEntity extends PathAwareEntity {

   public static final TrackedData<Boolean> IS_FLYING = DataTracker.registerData(DragonEntity.class,
         TrackedDataHandlerRegistry.BOOLEAN);
   public static final TrackedData<Boolean> IS_START_FLYING = DataTracker.registerData(DragonEntity.class,
         TrackedDataHandlerRegistry.BOOLEAN);
   public static final TrackedData<Boolean> CLIENT_START_FLYING = DataTracker.registerData(DragonEntity.class,
         TrackedDataHandlerRegistry.BOOLEAN);
   public static final TrackedData<Boolean> CLIENT_END_FLYING = DataTracker.registerData(DragonEntity.class,
         TrackedDataHandlerRegistry.BOOLEAN);
   protected static final TrackedData<Byte> TAMEABLE_FLAGS = DataTracker.registerData(DragonEntity.class,
         TrackedDataHandlerRegistry.BYTE);
   protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(DragonEntity.class,
         TrackedDataHandlerRegistry.OPTIONAL_UUID);
   public static final TrackedData<Boolean> HAS_SADDLE = DataTracker.registerData(DragonEntity.class,
         TrackedDataHandlerRegistry.BOOLEAN);

   private boolean sitting;
   public boolean isFlying;
   private int startFlyingTimer = 0;
   private float dragonSideSpeed = 0.0F;
   private float dragonForwardSpeed = 0.0F;
   public int keyBind = 342;
   private float turningFloat;
   private boolean hasSaddle;
   private int healingFood;

   public DragonEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
      super(entityType, world);
      this.stepHeight = 1.0F;
   }

   public static DefaultAttributeContainer.Builder createDragonAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 45.0D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28D)
            .add(EntityAttributes.GENERIC_ARMOR, 4.0D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D);
   }

   @Override
   public void initGoals() {
      super.initGoals();
      this.goalSelector.add(0, new SwimGoal(this));
      this.goalSelector.add(1, new DragonSitGoal(this));
      this.goalSelector.add(2, new DragonFindOwnerGoal(this, 0.1D, 10.0F, 2.0F));
      this.goalSelector.add(3, new DragonFlyRandomlyGoal(this));
      this.goalSelector.add(5, new WanderAroundGoal(this, 0.9D));
      this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F, 1.0F));
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte) 0);
      this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
      this.dataTracker.startTracking(IS_FLYING, false);
      this.dataTracker.startTracking(CLIENT_END_FLYING, false);
      this.dataTracker.startTracking(IS_START_FLYING, false);
      this.dataTracker.startTracking(CLIENT_START_FLYING, false);
      this.dataTracker.startTracking(HAS_SADDLE, false);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      if (this.getOwnerUuid() != null) {
         tag.putUuid("Dragon_Owner", this.getOwnerUuid());
      }
      tag.putBoolean("Is_Flying", this.isFlying);
      tag.putBoolean("Sitting_Dragon", this.sitting);
      tag.putBoolean("Has_Saddle", this.hasSaddle);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      UUID uUID2;
      if (tag.containsUuid("Dragon_Owner")) {
         uUID2 = tag.getUuid("Dragon_Owner");
      } else {
         String string = tag.getString("Dragon_Owner");
         uUID2 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
      }
      if (uUID2 != null) {
         try {
            this.setOwnerUuid(uUID2);
            this.setTamed(true);
         } catch (Throwable var4) {
            this.setTamed(false);
         }
      }
      this.isFlying = tag.getBoolean("Is_Flying");
      this.dataTracker.set(IS_FLYING, this.isFlying);
      this.sitting = tag.getBoolean("Sitting_Dragon");
      this.setInSittingPose(this.sitting);
      this.hasSaddle = tag.getBoolean("Has_Saddle");
      this.dataTracker.set(HAS_SADDLE, this.hasSaddle);
   }

   @Override
   public void travel(Vec3d movementInput) {
      if (this.isAlive()) {
         if (this.hasPassengers() && this.canBeControlledByRider()) {
            LivingEntity livingEntity = (LivingEntity) this.getPrimaryPassenger();
            // this.yaw = livingEntity.yaw;
            double wrapper = MathHelper.wrapDegrees(this.bodyYaw - (double) this.yaw);
            this.yaw = (float) ((double) this.yaw + wrapper);/// (double)this.field_7708

            this.prevYaw = this.yaw;
            this.pitch = livingEntity.pitch * 0.5F;
            this.setRotation(this.yaw, this.pitch);
            // this.bodyYaw = this.yaw;
            this.headYaw = livingEntity.headYaw;// this.bodyYaw;

            if (livingEntity instanceof ClientPlayerEntity) {
               ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) livingEntity;
               if (clientPlayerEntity.input.pressingLeft) {
                  turningFloat -= 0.05F;
               }
               if (!clientPlayerEntity.input.pressingLeft && turningFloat < 0.0F) {
                  turningFloat = 0.0F;
               }
               if (clientPlayerEntity.input.pressingRight) {
                  turningFloat += 0.05F;
               }
               if (!clientPlayerEntity.input.pressingRight && turningFloat > 0.0F) {
                  turningFloat = 0.0F;
               }
            }

            this.yaw += MathHelper.wrapDegrees(turningFloat);

            float f = livingEntity.sidewaysSpeed * 0.1F;

            float g = livingEntity.forwardSpeed * 0.5F;
            float flySpeed = 0.0F;
            float maxForwardSpeed = 0.6F;
            float maxSidewaysSpeed = 0.15F;

            if ((this.dragonForwardSpeed < maxForwardSpeed && livingEntity.forwardSpeed > 0.0F)
                  || (this.dragonForwardSpeed > maxForwardSpeed * -0.3F && livingEntity.forwardSpeed < 0.0F)) {
               this.dragonForwardSpeed += g * 0.04F;
            }
            if ((this.dragonSideSpeed < maxSidewaysSpeed && livingEntity.sidewaysSpeed > 0.0F)
                  || (this.dragonSideSpeed > maxSidewaysSpeed * -1 && livingEntity.sidewaysSpeed < 0.0F)) {
               this.dragonSideSpeed += f * 0.03F;
            }
            if (livingEntity.sidewaysSpeed == 0.0F) {
               this.dragonSideSpeed *= 0.7F;
            }
            if (livingEntity.forwardSpeed == 0.0F) {
               this.dragonForwardSpeed *= 0.7F;
            }

            boolean shouldFlyUp = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 32);
            boolean shouldFlyDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(),
                  this.keyBind);

            if (shouldFlyUp && this.onGround && !this.isFlying && this.startFlyingTimer < 40) {
               this.startFlyingTimer++;
               this.getDataTracker().set(CLIENT_START_FLYING, true);
               this.getDataTracker().set(IS_START_FLYING, true);
            }
            if (!shouldFlyUp && this.onGround && !this.isFlying && this.startFlyingTimer > 0) {
               this.startFlyingTimer--;
               this.getDataTracker().set(IS_START_FLYING, false);
            }
            if ((this.startFlyingTimer <= 0 && !this.isFlying) || this.isFlying
                  || this.getDataTracker().get(IS_FLYING)) {
               this.getDataTracker().set(CLIENT_START_FLYING, false);
            }

            if (this.startFlyingTimer >= 40 && !this.isFlying) {
               this.isFlying = true;
               this.getDataTracker().set(IS_FLYING, true);
            }
            if (this.isFlying && this.onGround && shouldFlyDown) {
               this.isFlying = false;
               this.getDataTracker().set(IS_FLYING, false);
               this.startFlyingTimer = 0;
               this.getDataTracker().set(CLIENT_END_FLYING, true);
            }
            if ((this.isFlying || this.getDataTracker().get(IS_FLYING)) && shouldFlyUp) {
               flySpeed = 0.1F;
            }
            if ((this.isFlying || this.getDataTracker().get(IS_FLYING)) && shouldFlyDown) {
               flySpeed = -0.1F;
            }
            if ((this.isFlying || this.getDataTracker().get(IS_FLYING)) && !shouldFlyDown && !shouldFlyUp) {
               flySpeed *= 0.5F;
            }

            if (this.isLogicalSideForUpdatingMovement()) {
               this.setMovementSpeed((float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
               if (!this.getDataTracker().get(IS_FLYING) && !this.isFlying) {
                  super.travel(new Vec3d((double) f, movementInput.y, (double) g));
               } else {
                  Vec3d vec3d = new Vec3d((double) this.dragonSideSpeed, movementInput.y + flySpeed,
                        (double) this.dragonForwardSpeed);
                  this.updateVelocity(0.05F, vec3d);
                  this.move(MovementType.SELF, this.getVelocity());
                  this.setVelocity(this.getVelocity());
               }
            } else if (livingEntity instanceof PlayerEntity) {
               this.setVelocity(Vec3d.ZERO);
            }
            this.method_29242(this, false);
         } else {
            if (this.isFlying || this.getDataTracker().get(IS_FLYING)) {
               this.updateVelocity(0.02F, movementInput);
               this.move(MovementType.SELF, this.getVelocity());
               this.setVelocity(this.getVelocity().multiply((0.91F)));
               double wrapper = MathHelper.wrapDegrees(this.headYaw - (double) this.yaw);
               this.yaw = (float) ((double) this.yaw + wrapper);
               BlockPos blockPos = this.getBlockPos().down(2);
               if (this.world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                  this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
               }
               if (this.onGround) {
                  this.getDataTracker().set(CLIENT_END_FLYING, true);
                  this.isFlying = false;
                  this.getDataTracker().set(IS_FLYING, false);

               }
            } else {
               this.flyingSpeed = 0.02F;
               super.travel(movementInput);
            }
         }
      }

   }

   @Override
   public boolean canBeControlledByRider() {
      return this.getPrimaryPassenger() instanceof LivingEntity;
   }

   @Override
   public void updatePassengerPosition(Entity passenger) {
      super.updatePassengerPosition(passenger);
      if (passenger instanceof MobEntity) {
         MobEntity mobEntity = (MobEntity) passenger;
         this.bodyYaw = mobEntity.bodyYaw;
      }
   }

   @Override
   public boolean isClimbing() {
      return false;
   }

   @Override
   @Nullable
   public Entity getPrimaryPassenger() {
      return this.getPassengerList().isEmpty() ? null : (Entity) this.getPassengerList().get(0);
   }

   @Nullable
   private Vec3d method_27930(Vec3d vec3d, LivingEntity livingEntity) {
      double d = this.getX() + vec3d.x;
      double e = this.getBoundingBox().minY;
      double f = this.getZ() + vec3d.z;
      BlockPos.Mutable mutable = new BlockPos.Mutable();
      UnmodifiableIterator<EntityPose> var10 = livingEntity.getPoses().iterator();

      while (var10.hasNext()) {
         EntityPose entityPose = (EntityPose) var10.next();
         mutable.set(d, e, f);
         double g = this.getBoundingBox().maxY + 0.75D;

         while (true) {
            double h = this.world.getDismountHeight(mutable);
            if ((double) mutable.getY() + h > g) {
               break;
            }

            if (Dismounting.canDismountInBlock(h)) {
               Box box = livingEntity.getBoundingBox(entityPose);
               Vec3d vec3d2 = new Vec3d(d, (double) mutable.getY() + h, f);
               if (Dismounting.canPlaceEntityAt(this.world, livingEntity, box.offset(vec3d2))) {
                  livingEntity.setPose(entityPose);
                  return vec3d2;
               }
            }

            mutable.move(Direction.UP);
            if ((double) mutable.getY() >= g) {
               break;
            }
         }
      }

      return null;
   }

   @Override
   public Vec3d updatePassengerForDismount(LivingEntity passenger) {
      Vec3d vec3d = getPassengerDismountOffset((double) this.getWidth(), (double) passenger.getWidth(),
            this.yaw + (passenger.getMainArm() == Arm.RIGHT ? 90.0F : -90.0F));
      Vec3d vec3d2 = this.method_27930(vec3d, passenger);
      if (vec3d2 != null) {
         return vec3d2;
      } else {
         Vec3d vec3d3 = getPassengerDismountOffset((double) this.getWidth(), (double) passenger.getWidth(),
               this.yaw + (passenger.getMainArm() == Arm.LEFT ? 90.0F : -90.0F));
         Vec3d vec3d4 = this.method_27930(vec3d3, passenger);
         return vec3d4 != null ? vec3d4 : this.getPos();
      }
   }

   private void putPlayerOnBack(PlayerEntity player) {
      if (!this.world.isClient) {
         player.yaw = this.yaw;
         player.pitch = this.pitch;
         player.startRiding(this);
      }

   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack itemStack = player.getStackInHand(hand);
      Item item = itemStack.getItem();
      if (!this.isBaby()) {
         if (this.hasPassengers()) {
            return super.interactMob(player, hand);
         }
      }
      if (this.world.isClient) {
         boolean bl = this.isOwner(player) || this.isTamed() || this.dragonFood(item) && !this.isTamed();
         return bl ? ActionResult.CONSUME : ActionResult.PASS;
      } else {
         if (this.isTamed()) {
            if (this.dragonFood(item) && this.getHealth() < this.getMaxHealth() && player.isSneaking()) {
               if (!player.abilities.creativeMode) {
                  itemStack.decrement(1);
               }
               this.heal((float) this.healingFood);
               return ActionResult.SUCCESS;
            } else if (!player.isSneaking()) {
               if (!this.hasSaddle) {
                  if (item == ItemInit.DRAGON_SADDLE) {
                     if (!player.abilities.creativeMode) {
                        itemStack.decrement(1);
                     }
                     this.hasSaddle = true;
                     this.getDataTracker().set(HAS_SADDLE, true);
                     return ActionResult.SUCCESS;
                  }
                  return ActionResult.FAIL;
               }
               this.setInSittingPose(false);
               this.putPlayerOnBack(player);
               return ActionResult.SUCCESS;
            } else if (this.isInSittingPose()) {
               this.setInSittingPose(false);
               return ActionResult.SUCCESS;
            } else if (this.onGround) {
               this.setInSittingPose(true);
               return ActionResult.SUCCESS;
            } else
               return ActionResult.PASS;
         } else if (this.dragonFood(item)) {
            if (!player.abilities.creativeMode) {
               itemStack.decrement(1);
            }
            if (this.random.nextInt(8) == 0) {
               this.setOwner(player);
               this.navigation.stop();
               this.setTarget((LivingEntity) null);
               this.world.sendEntityStatus(this, (byte) 7);
            } else {
               this.world.sendEntityStatus(this, (byte) 6);
            }

            return ActionResult.SUCCESS;
         }

         return super.interactMob(player, hand);
      }
   }

   private boolean dragonFood(Item item) {
      if (item == ItemInit.ORC_SKIN) {
         healingFood = 6;
         return true;
      } else if (item == Items.PORKCHOP || item == Items.BEEF) {
         healingFood = 5;
         return true;
      } else if (item == Items.MUTTON || item == Items.CHICKEN) {
         healingFood = 4;
         return true;
      } else if (item == Items.RABBIT) {
         healingFood = 3;
         return true;
      }
      return false;
   }

   // @Override
   // public void tick() {
   // super.tick();
   // }

   @Override
   public boolean isPushable() {
      return !this.hasPassengers();
   }

   @Override
   public boolean canBeLeashedBy(PlayerEntity player) {
      return !this.isLeashed();
   }

   @Environment(EnvType.CLIENT)
   protected void showEmoteParticle(boolean positive) {
      ParticleEffect particleEffect = ParticleTypes.HEART;
      if (!positive) {
         particleEffect = ParticleTypes.SMOKE;
      }

      for (int i = 0; i < 7; ++i) {
         double d = this.random.nextGaussian() * 0.02D;
         double e = this.random.nextGaussian() * 0.02D;
         double f = this.random.nextGaussian() * 0.02D;
         this.world.addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D,
               this.getParticleZ(1.0D), d, e, f);
      }

   }

   @Override
   @Environment(EnvType.CLIENT)
   public void handleStatus(byte status) {
      if (status == 7) {
         this.showEmoteParticle(true);
      } else if (status == 6) {
         this.showEmoteParticle(false);
      } else {
         super.handleStatus(status);
      }

   }

   public boolean isTamed() {
      return ((Byte) this.dataTracker.get(TAMEABLE_FLAGS) & 4) != 0;
   }

   public void setTamed(boolean tamed) {
      byte b = (Byte) this.dataTracker.get(TAMEABLE_FLAGS);
      if (tamed) {
         this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 4));
      } else {
         this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & -5));
      }
   }

   @Nullable
   public UUID getOwnerUuid() {
      return (UUID) ((Optional<UUID>) this.dataTracker.get(OWNER_UUID)).orElse((UUID) (Object) null);
   }

   public void setOwnerUuid(@Nullable UUID uuid) {
      this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
   }

   public void setOwner(PlayerEntity player) {
      this.setTamed(true);
      this.setOwnerUuid(player.getUuid());
   }

   @Nullable
   public LivingEntity getOwner() {
      try {
         UUID uUID = this.getOwnerUuid();
         return uUID == null ? null : this.world.getPlayerByUuid(uUID);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   @Override
   public boolean canTarget(LivingEntity target) {
      return this.isOwner(target) ? false : super.canTarget(target);
   }

   public boolean isOwner(LivingEntity entity) {
      return entity == this.getOwner();
   }

   public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
      return true;
   }

   @Override
   public boolean isTeammate(Entity other) {
      if (this.isTamed()) {
         LivingEntity livingEntity = this.getOwner();
         if (other == livingEntity) {
            return true;
         }

         if (livingEntity != null) {
            return livingEntity.isTeammate(other);
         }
      }

      return super.isTeammate(other);
   }

   @Override
   public void onDeath(DamageSource source) {
      if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES)
            && this.getOwner() instanceof ServerPlayerEntity) {
         this.getOwner().sendSystemMessage(this.getDamageTracker().getDeathMessage(), Util.NIL_UUID);
      }

      super.onDeath(source);
   }

   public boolean isInSittingPose() {
      return ((Byte) this.dataTracker.get(TAMEABLE_FLAGS) & 1) != 0;
   }

   public void setInSittingPose(boolean inSittingPose) {
      byte b = (Byte) this.dataTracker.get(TAMEABLE_FLAGS);
      if (inSittingPose) {
         this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 1));
      } else {
         this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & -2));
      }

   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      return false;
   }

   @Override
   protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
      if (this.isFlying || this.getDataTracker().get(IS_FLYING)) {
      } else
         super.fall(heightDifference, onGround, landedState, landedPosition);
   }

   public void setKeyBind(String key) {
      this.keyBind = InputUtil.fromTranslationKey(key).getCode();
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundInit.DRAGON_IDLE_EVENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundInit.DRAGON_HIT_EVENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundInit.DRAGON_DEATH_EVENT;
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundInit.ORC_STEP_EVENT, 0.7F, 1.0F);
   }

   @Override
   protected float calculateNextStepSoundDistance() {
      return (float) ((int) this.distanceTraveled + 2) - 0.01F;
   }

}