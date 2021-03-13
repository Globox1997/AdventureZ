package net.adventurez.entity;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.adventurez.entity.goal.OrkGroupGoal;
import net.adventurez.entity.goal.WanderAroundVeryFarGoal;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class OrkEntity extends HostileEntity {
   public static final TrackedData<Integer> orkSize = DataTracker.registerData(OrkEntity.class,
         TrackedDataHandlerRegistry.INTEGER);
   public static final TrackedData<Boolean> doubleHandAttack = DataTracker.registerData(OrkEntity.class,
         TrackedDataHandlerRegistry.BOOLEAN);

   public OrkEntity(EntityType<? extends HostileEntity> entityType, World world) {
      super(entityType, world);
   }

   public static DefaultAttributeContainer.Builder createOrkAttributes() {
      return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225D)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
            .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0D)
            .add(EntityAttributes.GENERIC_ARMOR, 1.0D);
   }

   @Override
   public void initGoals() {
      super.initGoals();
      this.goalSelector.add(0, new SwimGoal(this));
      this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
      this.goalSelector.add(3, new OrkGroupGoal(this));
      this.goalSelector.add(4, new WanderAroundVeryFarGoal(this, 1.0D, 0.3F));
      this.goalSelector.add(5, new WanderAroundGoal(this, 0.9D));
      this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(7, new LookAroundGoal(this));
      this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, true));
      this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { OrkEntity.class })));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, WanderingTraderEntity.class, true));
      this.targetSelector.add(4, new FollowTargetGoal<>(this, VillagerEntity.class, true));
   }

   public static boolean canSpawn(EntityType<OrkEntity> type, ServerWorldAccess world, SpawnReason spawnReason,
         BlockPos pos, Random random) {
      boolean bl = (world.getDifficulty() != Difficulty.PEACEFUL
            && canSpawnInDark(type, world, spawnReason, pos, random) && world.isSkyVisible(pos))
            || spawnReason == SpawnReason.SPAWNER;
      return bl;
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(orkSize, 1);
      this.dataTracker.startTracking(doubleHandAttack, false);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      this.setSize(tag.getInt("OrkSize"));
      super.readCustomDataFromTag(tag);

   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("OrkSize", this.getSize());

   }

   public int getSize() {
      return (Integer) this.dataTracker.get(orkSize);
   }

   public void setSize(int size) {
      this.dataTracker.set(orkSize, size);
      this.refreshPosition();
      this.calculateDimensions();
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (orkSize.equals(data)) {
         this.calculateDimensions();
         this.yaw = this.headYaw;
         this.bodyYaw = this.headYaw;
      }

      super.onTrackedDataSet(data);
   }

   @Override
   protected float getSoundVolume() {
      return 0.3F * (float) this.getSize();
   }

   @Override
   protected float getSoundPitch() {
      return 1.6F - ((float) 0.2F * this.getSize());
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundInit.ORC_IDLE_EVENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundInit.ORC_HURT_EVENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundInit.ORC_DEATH_EVENT;
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundInit.ORC_STEP_EVENT, 1.0F, 1.0F);
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 0.82F * dimensions.height;
   }

   @Nullable
   @Override
   public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
         @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
      int random = this.random.nextInt(3) + 1;
      this.setSize(random);
      this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double) (16D + this.getSize() * 6D));
      this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
            .setBaseValue((double) (0.26F - 0.012F * (float) this.getSize()));
      this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) this.getSize() * 3D + 2D);
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   @Override
   public EntityDimensions getDimensions(EntityPose pose) {
      return super.getDimensions(pose).scaled(0.55F * (float) this.getSize());
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.world.random.nextInt(3) == 0 && !this.dataTracker.get(doubleHandAttack)) {
         dataTracker.set(doubleHandAttack, true);
      } else {
         dataTracker.set(doubleHandAttack, false);
      }
      return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
   }

   @Override
   public int getLimitPerChunk() {
      return 4;
   }

}