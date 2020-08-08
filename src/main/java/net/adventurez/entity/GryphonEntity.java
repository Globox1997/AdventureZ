// // package net.adventurez.entity;

// // import net.adventurez.init.SoundInit;
// // import net.minecraft.block.BlockState;
// // import net.minecraft.entity.EntityType;
// // import net.minecraft.entity.MovementType;
// // import net.minecraft.entity.Saddleable;
// // import net.minecraft.entity.attribute.DefaultAttributeContainer;
// // import net.minecraft.entity.attribute.EntityAttributes;
// // import net.minecraft.entity.damage.DamageSource;
// // import net.minecraft.entity.data.DataTracker;
// // import net.minecraft.entity.data.TrackedData;
// // import net.minecraft.entity.data.TrackedDataHandlerRegistry;
// // import net.minecraft.entity.passive.PassiveEntity;
// // import net.minecraft.entity.passive.TameableEntity;
// // import net.minecraft.entity.player.PlayerEntity;
// // import net.minecraft.inventory.Inventory;
// // import net.minecraft.inventory.InventoryChangedListener;
// // import net.minecraft.inventory.SimpleInventory;
// // import net.minecraft.item.Item;
// // import net.minecraft.item.ItemStack;
// // import net.minecraft.item.Items;
// // import net.minecraft.nbt.CompoundTag;
// // import net.minecraft.sound.SoundCategory;
// // import net.minecraft.sound.SoundEvent;
// // import net.minecraft.sound.SoundEvents;
// // import net.minecraft.util.ActionResult;
// // import net.minecraft.util.Hand;
// // import net.minecraft.util.math.BlockPos;
// // import net.minecraft.util.math.Vec3d;
// // import net.minecraft.world.World;

// // import net.minecraft.entity.mob.GhastEntity;
// // import net.minecraft.entity.mob.FlyingEntity;
// // import net.minecraft.entity.mob.PhantomEntity;
// // import net.minecraft.entity.passive.GryphonEntity;
// // import net.minecraft.entity.passive.HorseEntity;

// // public class GryphonEntity extends TameableEntity implements InventoryChangedListener, Saddleable {
// //   public static final TrackedData<Boolean> tameGryphon = DataTracker.registerData(GryphonEntity.class,
// //   TrackedDataHandlerRegistry.BOOLEAN);
// //   public SimpleInventory items;
// //   private boolean tamed = false;

// //   public GryphonEntity(EntityType<? extends TameableEntity> entityType, World world) {
// //     super(entityType, world);
// //     this.stepHeight = 1.0F;
// //     this.onChestedStatusChanged();
// //   }

// //   public static DefaultAttributeContainer.Builder createGryphonAttributes() {
// //     return createGryphonAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
// //         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.215D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
// //         .add(EntityAttributes.GENERIC_ARMOR, 1.0D);
// //   }

// //   @Override
// //   public void initGoals() {
// //     super.initGoals();
// //   }

// //   @Override
// //   public void writeCustomDataToTag(CompoundTag tag) {
// //     super.writeCustomDataToTag(tag);
// //     tag.putBoolean("IsGryphonTamed", this.tamed);
// //   }

// //   @Override
// //   public void readCustomDataFromTag(CompoundTag tag) {
// //     super.readCustomDataFromTag(tag);
// //     this.tamed = tag.getBoolean("IsGryphonTamed");
// //     this.updateSaddle();
// //   }

// //   @Override
// //   public void initDataTracker() {
// //     super.initDataTracker();
// //     dataTracker.startTracking(tameGryphon, tamed);
// //   }

// //   public ActionResult interactMob(PlayerEntity player, Hand hand) {
// //     ItemStack itemStack = player.getStackInHand(hand);
// //     // if (!this.isBaby()) {
// //     // if (this.isTame() && player.shouldCancelInteraction()) {
// //     // this.openInventory(player);
// //     // return ActionResult.success(this.world.isClient);
// //     // }

// //     // if (this.hasPassengers()) {
// //     // return super.interactMob(player, hand);
// //     // }
// //     // }

// //     if (!itemStack.isEmpty()) {
// //       if (this.isBreedingItem(itemStack)) {
// //         return this.method_30009(player, itemStack);
// //       }

// //       ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
// //       if (actionResult.isAccepted()) {
// //         return actionResult;
// //       }

// //       // if (!this.isTame()) {
// //       // this.playAngrySound();
// //       // return ActionResult.success(this.world.isClient);
// //       // }

// //       boolean bl = !this.isBaby() && !this.isSaddled() && itemStack.getItem() == Items.SADDLE;
// //       if (bl) {
// //         this.saddle(SoundCategory.NEUTRAL);
// //         return ActionResult.success(this.world.isClient);
// //       }
// //     }

// //     if (this.isBaby()) {
// //       return super.interactMob(player, hand);
// //     } else {
// //       this.putPlayerOnBack(player);
// //       return ActionResult.success(this.world.isClient);
// //     }
// //   }

// //   private ActionResult method_30009(PlayerEntity playerEntity, ItemStack itemStack) {
// //     boolean bl = this.receiveFood(playerEntity, itemStack);
// //     if (!playerEntity.abilities.creativeMode) {
// //       itemStack.decrement(1);
// //     }

// //     if (this.world.isClient) {
// //       return ActionResult.CONSUME;
// //     } else {
// //       return bl ? ActionResult.SUCCESS : ActionResult.PASS;
// //     }
// //   }

// //   private boolean receiveFood(PlayerEntity player, ItemStack item) {
// //     boolean bl = false;
// //     float f = 0.0F;
// //     Item foodItem = item.getItem();
// //     if (foodItem == Items.CHICKEN || foodItem == Items.PORKCHOP || foodItem == Items.RABBIT || foodItem == Items.BEEF
// //         || foodItem == Items.MUTTON) {
// //       f = 2.0F;
// //       if (!this.world.isClient && this.isTame() && this.getBreedingAge() == 0 && !this.isInLove()) {
// //         bl = true;
// //         this.lovePlayer(player);
// //       }
// //     }
// //     if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
// //       this.heal(f);
// //       bl = true;
// //     }

// //     return bl;
// //   }

// //   private void putPlayerOnBack(PlayerEntity player) {
// //     // this.setAngry(false);
// //     if (!this.world.isClient) {
// //       player.yaw = this.yaw;
// //       player.pitch = this.pitch;
// //       player.startRiding(this);
// //     }

// //   }

// //   @Override
// //   public PassiveEntity createChild(PassiveEntity mate) {
// //     return null;
// //   }

// //   public boolean isTame() {
// //     return this.getDataTracker().get(GryphonEntity.tameGryphon);
// //   }

// //   public boolean bondWithPlayer(PlayerEntity player) {
// //     this.setOwnerUuid(player.getUuid());
// //     this.setTame(true);
// //     if (player instanceof ServerPlayerEntity) {
// //        Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
// //     }

// //     this.world.sendEntityStatus(this, (byte)7);
// //     return true;
// //  }

// //   @Override
// //   public boolean canBeSaddled() {
// //     return this.isAlive() && !this.isBaby() && this.isTame();
// //   }

// //   @Override
// //   public void saddle(@Nullable SoundCategory sound) {
// //     this.items.setStack(0, new ItemStack(Items.SADDLE));
// //     if (sound != null) {
// //       this.world.playSoundFromEntity((PlayerEntity) null, this, SoundEvents.ENTITY_HORSE_SADDLE, sound, 0.5F, 1.0F);
// //     }

// //   }

// //   public void onChestedStatusChanged() {
// //     SimpleInventory simpleInventory = this.items;
// //     this.items = new SimpleInventory(1);
// //     if (simpleInventory != null) {
// //       simpleInventory.removeListener(this);
// //       int i = Math.min(simpleInventory.size(), this.items.size());

// //       for (int j = 0; j < i; ++j) {
// //         ItemStack itemStack = simpleInventory.getStack(j);
// //         if (!itemStack.isEmpty()) {
// //           this.items.setStack(j, itemStack.copy());
// //         }
// //       }
// //     }

// //     this.items.addListener(this);
// //     this.updateSaddle();
// //   }

// //   @Override
// //   public boolean isSaddled() {
// //     return this.getGryphonFlag(4);
// //   }

// //   // Flying Entity
// //   @Override
// //   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
// //     return false;
// //   }

// //   @Override
// //   public void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
// //   }

// //   @Override
// //   public void travel(Vec3d movementInput) {
// //     if (this.isTouchingWater()) {
// //       this.updateVelocity(0.02F, movementInput);
// //       this.move(MovementType.SELF, this.getVelocity());
// //       this.setVelocity(this.getVelocity().multiply(0.800000011920929D));
// //     } else if (this.isInLava()) {
// //       this.updateVelocity(0.02F, movementInput);
// //       this.move(MovementType.SELF, this.getVelocity());
// //       this.setVelocity(this.getVelocity().multiply(0.5D));
// //     } else {
// //       float f = 0.91F;
// //       if (this.onGround) {
// //         f = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock()
// //             .getSlipperiness() * 0.91F;
// //       }

// //       float g = 0.16277137F / (f * f * f);
// //       f = 0.91F;
// //       if (this.onGround) {
// //         f = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock()
// //             .getSlipperiness() * 0.91F;
// //       }

// //       this.updateVelocity(this.onGround ? 0.1F * g : 0.02F, movementInput);
// //       this.move(MovementType.SELF, this.getVelocity());
// //       this.setVelocity(this.getVelocity().multiply((double) f));
// //     }

// //     this.method_29242(this, false);
// //   }

// //   @Override
// //   public boolean isClimbing() {
// //     return false;
// //   }

// //   public void updateSaddle() {
// //     if (!this.world.isClient) {
// //       this.setGryphonFlag(4, !this.items.getStack(0).isEmpty());
// //     }
// //   }

// //   @Override
// //   public void onInventoryChanged(Inventory sender) {
// //     boolean bl = this.isSaddled();
// //     this.updateSaddle();
// //     if (this.age > 20 && !bl && this.isSaddled()) {
// //       this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
// //     }

// //   }

// //   @Override
// //   protected SoundEvent getAmbientSound() {
// //     return SoundInit.SOULREAPER_IDLE_EVENT;
// //   }

// //   @Override
// //   protected SoundEvent getHurtSound(DamageSource source) {
// //     return SoundInit.SOULREAPER_HURT_EVENT;
// //   }

// //   @Override
// //   protected SoundEvent getDeathSound() {
// //     return SoundInit.SOULREAPER_DEATH_EVENT;
// //   }

// //   @Override
// //   protected void playStepSound(BlockPos pos, BlockState state) {
// //     this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.5F, 1.0F);
// //   }

// // }

// package net.adventurez.entity;

// import com.google.common.collect.UnmodifiableIterator;
// import java.util.Iterator;
// import java.util.Optional;
// import java.util.UUID;

// import net.adventurez.entity.goal.GryphonBondWithPlayerGoal;
// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.minecraft.advancement.criterion.Criteria;
// import net.minecraft.block.BlockState;
// import net.minecraft.block.Blocks;
// import net.minecraft.entity.Dismounting;
// import net.minecraft.entity.Entity;
// import net.minecraft.entity.EntityDimensions;
// import net.minecraft.entity.EntityPose;
// import net.minecraft.entity.EntityType;
// import net.minecraft.entity.EquipmentSlot;
// import net.minecraft.entity.LivingEntity;
// import net.minecraft.entity.Saddleable;
// import net.minecraft.entity.ai.goal.AnimalMateGoal;
// import net.minecraft.entity.ai.goal.EscapeDangerGoal;
// import net.minecraft.entity.ai.goal.FollowParentGoal;
// import net.minecraft.entity.ai.goal.LookAroundGoal;
// import net.minecraft.entity.ai.goal.LookAtEntityGoal;
// import net.minecraft.entity.ai.goal.SwimGoal;
// import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
// import net.minecraft.entity.attribute.DefaultAttributeContainer;
// import net.minecraft.entity.attribute.EntityAttributes;
// import net.minecraft.entity.damage.DamageSource;
// import net.minecraft.entity.data.DataTracker;
// import net.minecraft.entity.data.TrackedData;
// import net.minecraft.entity.data.TrackedDataHandlerRegistry;
// import net.minecraft.entity.mob.MobEntity;
// import net.minecraft.entity.passive.AnimalEntity;
// import net.minecraft.entity.passive.PassiveEntity;
// import net.minecraft.entity.player.PlayerEntity;
// import net.minecraft.inventory.Inventory;
// import net.minecraft.inventory.InventoryChangedListener;
// import net.minecraft.inventory.SimpleInventory;
// import net.minecraft.item.Item;
// import net.minecraft.item.ItemStack;
// import net.minecraft.item.Items;
// import net.minecraft.nbt.CompoundTag;
// import net.minecraft.particle.ParticleEffect;
// import net.minecraft.particle.ParticleTypes;
// import net.minecraft.server.ServerConfigHandler;
// import net.minecraft.server.network.ServerPlayerEntity;
// import net.minecraft.sound.BlockSoundGroup;
// import net.minecraft.sound.SoundCategory;
// import net.minecraft.sound.SoundEvents;
// import net.minecraft.util.ActionResult;
// import net.minecraft.util.Arm;
// import net.minecraft.util.Hand;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.util.math.Box;
// import net.minecraft.util.math.Direction;
// import net.minecraft.util.math.MathHelper;
// import net.minecraft.util.math.Vec3d;
// import net.minecraft.world.World;
// import software.bernie.geckolib.animation.builder.AnimationBuilder;
// import software.bernie.geckolib.animation.controller.EntityAnimationController;
// import software.bernie.geckolib.entity.IAnimatedEntity;
// import software.bernie.geckolib.event.AnimationTestEvent;
// import software.bernie.geckolib.manager.EntityAnimationManager;

// //import net.minecraft.entity.passive.HorseEntity;

// public class GryphonEntity extends AnimalEntity implements InventoryChangedListener, Saddleable, IAnimatedEntity {
//   EntityAnimationManager manager = new EntityAnimationManager();
//   EntityAnimationController<GryphonEntity> controller = new EntityAnimationController<GryphonEntity>(this,
//       "walkController", 20, this::animationPredicate);
//   private static final TrackedData<Byte> GRYPHON_FLAGS;
//   private static final TrackedData<Optional<UUID>> OWNER_UUID;
//   public boolean inAir;
//   public SimpleInventory items;

//   public GryphonEntity(EntityType<? extends GryphonEntity> entityType, World world) {
//     super(entityType, world);
//     this.stepHeight = 1.0F;
//     this.onChestedStatusChanged();
//     manager.addAnimationController(controller);
//     registerAnimationController();
//   }

//   public static DefaultAttributeContainer.Builder createGryphonAttributes() {
//     return AnimalEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
//         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.215D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
//         .add(EntityAttributes.GENERIC_ARMOR, 1.0D);
//   }

//   @Override
//   protected void initGoals() {
//     this.goalSelector.add(0, new SwimGoal(this));
//     this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2D));
//     this.goalSelector.add(1, new GryphonBondWithPlayerGoal(this, 1.2D));
//     this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, GryphonEntity.class));
//     this.goalSelector.add(4, new FollowParentGoal(this, 1.0D));
//     this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7D));
//     this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
//     this.goalSelector.add(8, new LookAroundGoal(this));
//   }

//   @Override
//   public void initDataTracker() {
//     super.initDataTracker();
//     this.dataTracker.startTracking(GRYPHON_FLAGS, (byte) 0);
//     this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
//   }

//   public boolean getGryphonFlag(int bitmask) {
//     return ((Byte) this.dataTracker.get(GRYPHON_FLAGS) & bitmask) != 0;
//   }

//   public void setGryphonFlag(int bitmask, boolean flag) {
//     byte b = (Byte) this.dataTracker.get(GRYPHON_FLAGS);
//     if (flag) {
//       this.dataTracker.set(GRYPHON_FLAGS, (byte) (b | bitmask));
//     } else {
//       this.dataTracker.set(GRYPHON_FLAGS, (byte) (b & ~bitmask));
//     }

//   }

//   public boolean isTame() {
//     return this.getGryphonFlag(2);
//   }

//   @Nullable
//   public UUID getOwnerUuid() {
//     return (UUID) ((Optional<UUID>) this.dataTracker.get(OWNER_UUID)).orElse((UUID) null);
//   }

//   public void setOwnerUuid(@Nullable UUID uuid) {
//     this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
//   }

//   public boolean isInAir() {
//     return this.inAir;
//   }

//   public void setTame(boolean tame) {
//     this.setGryphonFlag(2, tame);
//   }

//   public void setInAir(boolean inAir) {
//     this.inAir = inAir;
//   }

//   @Override
//   public boolean canBeSaddled() {
//     return this.isAlive() && !this.isBaby() && this.isTame();
//   }

//   @Override
//   public void saddle(@Nullable SoundCategory sound) {
//     this.items.setStack(0, new ItemStack(Items.SADDLE));
//     if (sound != null) {
//       this.world.playSoundFromEntity((PlayerEntity) null, this, SoundEvents.ENTITY_HORSE_SADDLE, sound, 0.5F, 1.0F);
//     }

//   }

//   public boolean isSaddled() {
//     return this.getGryphonFlag(4);
//   }

//   @Override
//   public boolean isPushable() {
//     return !this.hasPassengers();
//   }

//   @Override
//   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
//     if (fallDistance > 1.0F) {
//       this.playSound(SoundEvents.ENTITY_HORSE_LAND, 0.4F, 1.0F);
//     }

//     int i = this.computeFallDamage(fallDistance, damageMultiplier);
//     if (i <= 0) {
//       return false;
//     } else {
//       this.damage(DamageSource.FALL, (float) i);
//       if (this.hasPassengers()) {
//         Iterator<Entity> var4 = this.getPassengersDeep().iterator();

//         while (var4.hasNext()) {
//           Entity entity = (Entity) var4.next();
//           entity.damage(DamageSource.FALL, (float) i);
//         }
//       }

//       this.playBlockFallSound();
//       return true;
//     }
//   }

//   @Override
//   public int computeFallDamage(float fallDistance, float damageMultiplier) {
//     return MathHelper.ceil((fallDistance * 0.5F - 3.0F) * damageMultiplier);
//   }

//   public void onChestedStatusChanged() {
//     SimpleInventory simpleInventory = this.items;
//     this.items = new SimpleInventory(1);
//     if (simpleInventory != null) {
//       simpleInventory.removeListener(this);
//       int i = Math.min(simpleInventory.size(), this.items.size());

//       for (int j = 0; j < i; ++j) {
//         ItemStack itemStack = simpleInventory.getStack(j);
//         if (!itemStack.isEmpty()) {
//           this.items.setStack(j, itemStack.copy());
//         }
//       }
//     }

//     this.items.addListener(this);
//     this.updateSaddle();
//   }

//   public void updateSaddle() {
//     if (!this.world.isClient) {
//       this.setGryphonFlag(4, !this.items.getStack(0).isEmpty());
//     }
//   }

//   @Override
//   public void onInventoryChanged(Inventory sender) {
//     boolean bl = this.isSaddled();
//     this.updateSaddle();
//     if (this.age > 20 && !bl && this.isSaddled()) {
//       this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
//     }

//   }

//   @Override
//   public void playStepSound(BlockPos pos, BlockState state) {
//     if (!state.getMaterial().isLiquid()) {
//       BlockState blockState = this.world.getBlockState(pos.up());
//       BlockSoundGroup blockSoundGroup = state.getSoundGroup();
//       if (blockState.isOf(Blocks.SNOW)) {
//         blockSoundGroup = blockState.getSoundGroup();
//       }
//       if (blockSoundGroup == BlockSoundGroup.WOOD) {
//         this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, blockSoundGroup.getVolume() * 0.15F,
//             blockSoundGroup.getPitch());
//       } else {
//         this.playSound(SoundEvents.ENTITY_HORSE_STEP, blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
//       }

//     }
//   }

//   public void playWalkSound(BlockSoundGroup group) {
//     this.playSound(SoundEvents.ENTITY_HORSE_GALLOP, group.getVolume() * 0.15F, group.getPitch());
//   }

//   @Override
//   public int getLimitPerChunk() {
//     return 1;
//   }

//   // public void openInventory(PlayerEntity player) {
//   // if (!this.world.isClient && (!this.hasPassengers() ||
//   // this.hasPassenger(player)) && this.isTame()) {
//   // player.openHorseInventory(this, this.items);
//   // }

//   // }
//   @Override
//   public ActionResult interactMob(PlayerEntity player, Hand hand) {
//     ItemStack itemStack = player.getStackInHand(hand);
//     if (!itemStack.isEmpty()) {
//       if (this.isBreedingItem(itemStack)) {
//         return this.healGryphon(player, itemStack);
//       }

//       ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
//       if (actionResult.isAccepted()) {
//         return actionResult;
//       }

//       boolean bl = !this.isBaby() && !this.isSaddled() && itemStack.getItem() == Items.SADDLE;
//       if (bl) {
//         this.saddle(SoundCategory.NEUTRAL);
//         return ActionResult.success(this.world.isClient);
//       }
//     }
//     this.putPlayerOnBack(player);
//     return ActionResult.success(this.world.isClient);
//   }

//   private ActionResult healGryphon(PlayerEntity playerEntity, ItemStack itemStack) {
//     boolean bl = this.receiveFood(playerEntity, itemStack);
//     if (!playerEntity.abilities.creativeMode) {
//       itemStack.decrement(1);
//     }

//     if (this.world.isClient) {
//       return ActionResult.CONSUME;
//     } else {
//       return bl ? ActionResult.SUCCESS : ActionResult.PASS;
//     }
//   }

//   private boolean receiveFood(PlayerEntity player, ItemStack item) {
//     boolean bl = false;
//     float f = 0.0F;
//     Item foodItem = item.getItem();
//     if (foodItem == Items.CHICKEN || foodItem == Items.PORKCHOP || foodItem == Items.RABBIT || foodItem == Items.BEEF
//         || foodItem == Items.MUTTON) {
//       f = 2.0F;
//       if (!this.world.isClient && this.isTame() && this.getBreedingAge() == 0 && !this.isInLove()) {
//         bl = true;
//         this.lovePlayer(player);
//       }
//     }
//     if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
//       this.heal(f);
//       bl = true;
//     }

//     return bl;
//   }

//   public void putPlayerOnBack(PlayerEntity player) {
//     if (!this.world.isClient) {
//       player.yaw = this.yaw;
//       player.pitch = this.pitch;
//       player.startRiding(this);
//     }

//   }

//   @Override
//   public void tickMovement() {
//     super.tickMovement();
//     if (!this.world.isClient && this.isAlive()) {
//       if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
//         this.heal(1.0F);
//       }
//     }
//   }

//   public boolean bondWithPlayer(PlayerEntity player) {
//     this.setOwnerUuid(player.getUuid());
//     this.setTame(true);
//     if (player instanceof ServerPlayerEntity) {
//       Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity) player, this);
//     }

//     this.world.sendEntityStatus(this, (byte) 7);
//     return true;
//   }

//   @Override
//   public void travel(Vec3d movementInput) {
//     if (this.isAlive()) {
//       if (this.hasPassengers() && this.canBeControlledByRider() && this.isSaddled()) {
//         LivingEntity livingEntity = (LivingEntity) this.getPrimaryPassenger();
//         this.yaw = livingEntity.yaw;
//         this.prevYaw = this.yaw;
//         this.pitch = livingEntity.pitch * 0.5F;
//         this.setRotation(this.yaw, this.pitch);
//         this.bodyYaw = this.yaw;
//         this.headYaw = this.bodyYaw;
//         float f = livingEntity.sidewaysSpeed * 0.5F;
//         float g = livingEntity.forwardSpeed;

//         this.flyingSpeed = this.getMovementSpeed() * 0.1F;
//         if (this.isLogicalSideForUpdatingMovement()) {
//           this.setMovementSpeed((float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
//           super.travel(new Vec3d((double) f, movementInput.y, (double) g));
//         } else if (livingEntity instanceof PlayerEntity) {
//           this.setVelocity(Vec3d.ZERO);
//         }

//         if (this.onGround) {
//           this.setInAir(false);
//         }

//         this.method_29242(this, false);
//       } else {
//         this.flyingSpeed = 0.02F;
//         super.travel(movementInput);
//       }
//     }
//   }

//   @Override
//   public void writeCustomDataToTag(CompoundTag tag) {
//     super.writeCustomDataToTag(tag);
//     tag.putBoolean("Tame", this.isTame());
//     if (this.getOwnerUuid() != null) {
//       tag.putUuid("Owner", this.getOwnerUuid());
//     }

//     if (!this.items.getStack(0).isEmpty()) {
//       tag.put("SaddleItem", this.items.getStack(0).toTag(new CompoundTag()));
//     }

//   }

//   @Override
//   public void readCustomDataFromTag(CompoundTag tag) {
//     super.readCustomDataFromTag(tag);
//     this.setTame(tag.getBoolean("Tame"));
//     UUID uUID2;
//     if (tag.containsUuid("Owner")) {
//       uUID2 = tag.getUuid("Owner");
//     } else {
//       String string = tag.getString("Owner");
//       uUID2 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
//     }

//     if (uUID2 != null) {
//       this.setOwnerUuid(uUID2);
//     }

//     if (tag.contains("SaddleItem", 10)) {
//       ItemStack itemStack = ItemStack.fromTag(tag.getCompound("SaddleItem"));
//       if (itemStack.getItem() == Items.SADDLE) {
//         this.items.setStack(0, itemStack);
//       }
//     }

//     this.updateSaddle();
//   }

//   @Override
//   public boolean canBreedWith(AnimalEntity other) {
//     return false;
//   }

//   @Nullable
//   public PassiveEntity createChild(PassiveEntity mate) {
//     return null;
//   }

//   @Override
//   public boolean canBeControlledByRider() {
//     return this.getPrimaryPassenger() instanceof LivingEntity;
//   }

//   @Environment(EnvType.CLIENT)
//   protected void spawnPlayerReactionParticles(boolean positive) {
//     ParticleEffect particleEffect = positive ? ParticleTypes.HEART : ParticleTypes.SMOKE;

//     for (int i = 0; i < 7; ++i) {
//       double d = this.random.nextGaussian() * 0.02D;
//       double e = this.random.nextGaussian() * 0.02D;
//       double f = this.random.nextGaussian() * 0.02D;
//       this.world.addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D,
//           this.getParticleZ(1.0D), d, e, f);
//     }

//   }

//   @Environment(EnvType.CLIENT)
//   public void handleStatus(byte status) {
//     if (status == 7) {
//       this.spawnPlayerReactionParticles(true);
//     } else if (status == 6) {
//       this.spawnPlayerReactionParticles(false);
//     } else {
//       super.handleStatus(status);
//     }

//   }

//   @Override
//   public void updatePassengerPosition(Entity passenger) {
//     super.updatePassengerPosition(passenger);
//     if (passenger instanceof MobEntity) {
//       MobEntity mobEntity = (MobEntity) passenger;
//       this.bodyYaw = mobEntity.bodyYaw;
//     }

//     // if (this.lastAngryAnimationProgress > 0.0F) {
//     // float f = MathHelper.sin(this.bodyYaw * 0.017453292F);
//     // float g = MathHelper.cos(this.bodyYaw * 0.017453292F);
//     // float h = 0.7F * this.lastAngryAnimationProgress;
//     // float i = 0.15F * this.lastAngryAnimationProgress;
//     // passenger.updatePosition(this.getX() + (double)(h * f), this.getY() +
//     // this.getMountedHeightOffset() + passenger.getHeightOffset() + (double)i,
//     // this.getZ() - (double)(h * g));
//     // if (passenger instanceof LivingEntity) {
//     // ((LivingEntity)passenger).bodyYaw = this.bodyYaw;
//     // }
//     // }

//   }

//   @Override
//   public boolean isClimbing() {
//     return false;
//   }

//   @Override
//   public float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
//     return dimensions.height * 0.95F;
//   }

//   public boolean setSaddled() {
//     return !this.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
//   }

//   // public boolean equip(int slot, ItemStack item) {
//   // int i = slot - 400;
//   // if (i >= 0 && i < 2 && i < this.items.size()) {
//   // if (i == 0 && item.getItem() != Items.SADDLE) {
//   // return false;
//   // } else if (i != 1 || this.canEquip() && this.canEquip(item)) {
//   // this.items.setStack(i, item);
//   // this.updateSaddle();
//   // return true;
//   // } else {
//   // return false;
//   // }
//   // } else {
//   // int j = slot - 500 + 2;
//   // if (j >= 2 && j < this.items.size()) {
//   // this.items.setStack(j, item);
//   // return true;
//   // } else {
//   // return false;
//   // }
//   // }
//   // }
//   @Override
//   @Nullable
//   public Entity getPrimaryPassenger() {
//     return this.getPassengerList().isEmpty() ? null : (Entity) this.getPassengerList().get(0);
//   }

//   @Nullable
//   private Vec3d method_27930(Vec3d vec3d, LivingEntity livingEntity) {
//     double d = this.getX() + vec3d.x;
//     double e = this.getBoundingBox().minY;
//     double f = this.getZ() + vec3d.z;
//     BlockPos.Mutable mutable = new BlockPos.Mutable();
//     UnmodifiableIterator<EntityPose> var10 = livingEntity.getPoses().iterator();

//     while (var10.hasNext()) {
//       EntityPose entityPose = (EntityPose) var10.next();
//       mutable.set(d, e, f);
//       double g = this.getBoundingBox().maxY + 0.75D;

//       while (true) {
//         double h = this.world.getCollisionHeightAt(mutable);
//         if ((double) mutable.getY() + h > g) {
//           break;
//         }

//         if (Dismounting.canDismountInBlock(h)) {
//           Box box = livingEntity.getBoundingBox(entityPose);
//           Vec3d vec3d2 = new Vec3d(d, (double) mutable.getY() + h, f);
//           if (Dismounting.canPlaceEntityAt(this.world, livingEntity, box.offset(vec3d2))) {
//             livingEntity.setPose(entityPose);
//             return vec3d2;
//           }
//         }

//         mutable.move(Direction.UP);
//         if ((double) mutable.getY() >= g) {
//           break;
//         }
//       }
//     }

//     return null;
//   }

//   @Override
//   public Vec3d updatePassengerForDismount(LivingEntity passenger) {
//     Vec3d vec3d = getPassengerDismountOffset((double) this.getWidth(), (double) passenger.getWidth(),
//         this.yaw + (passenger.getMainArm() == Arm.RIGHT ? 90.0F : -90.0F));
//     Vec3d vec3d2 = this.method_27930(vec3d, passenger);
//     if (vec3d2 != null) {
//       return vec3d2;
//     } else {
//       Vec3d vec3d3 = getPassengerDismountOffset((double) this.getWidth(), (double) passenger.getWidth(),
//           this.yaw + (passenger.getMainArm() == Arm.LEFT ? 90.0F : -90.0F));
//       Vec3d vec3d4 = this.method_27930(vec3d3, passenger);
//       return vec3d4 != null ? vec3d4 : this.getPos();
//     }
//   }

//   static {
//     GRYPHON_FLAGS = DataTracker.registerData(GryphonEntity.class, TrackedDataHandlerRegistry.BYTE);
//     OWNER_UUID = DataTracker.registerData(GryphonEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
//   }

//   @Override
//   public EntityAnimationManager getAnimationManager() {
//     return manager;
//   }

//   private <E extends Entity> boolean animationPredicate(AnimationTestEvent<E> event) {

//     if (this.moveControl.isMoving() || event.isWalking()) {
//       controller.setAnimation(new AnimationBuilder().addAnimation("walking_gryphon"));
//       return true;
//     } else

//       return false;
//   }

//   private void registerAnimationController() {
//     manager.addAnimationController(controller);
//   }
// }
