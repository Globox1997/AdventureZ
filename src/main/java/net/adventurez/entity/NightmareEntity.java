package net.adventurez.entity;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.block.Blocks;

public class NightmareEntity extends SkeletonHorseEntity {
  private int eatingGrassTicks;

  public NightmareEntity(EntityType<? extends SkeletonHorseEntity> entityType, World world) {
    super(entityType, world);
    this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
    this.experiencePoints = 5;
  }

  public static DefaultAttributeContainer.Builder createNightmareAttributes() {
    return createBaseHorseAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D);
  }

  @Nullable
  public EntityData initialize(WorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
      @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
    if (spawnReason.equals(SpawnReason.COMMAND) || spawnReason.equals(SpawnReason.NATURAL)) {
      SoulReaperEntity soulReaperEntity = (SoulReaperEntity) EntityInit.SOULREAPER_ENTITY.create(this.world);
      soulReaperEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, 0.0F);
      soulReaperEntity.initialize(world, difficulty, spawnReason, (EntityData) null, (CompoundTag) null);
      soulReaperEntity.startRiding(this);
      world.spawnEntity(soulReaperEntity);
    }
    return (EntityData) entityData;
  }

  @Override
  public void tickMovement() {
    super.tickMovement();
    if (!this.world.isClient && this.isAlive()) {
      if (this.random.nextInt(700) == 0 && this.deathTime == 0) {
        this.heal(1.0F);
      }

      if (this.eatsGrass()) {
        if (!this.isEatingGrass() && !this.hasPassengers() && this.random.nextInt(500) == 0
            && this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.SOUL_SAND)) {
          this.setEatingGrass(true);
        }

        if (this.isEatingGrass() && ++this.eatingGrassTicks > 50) {
          this.eatingGrassTicks = 0;
          this.setEatingGrass(false);
        }
      }

      this.walkToParent();
    }
    if (world.isClient) {
      double g = this.random.nextDouble() * 0.7F - 0.3F;
      double e = this.random.nextDouble() * 0.8F;
      double f = this.random.nextDouble() * 0.7F - 0.3F;
      double g2 = world.random.nextDouble() * 0.7F - 0.3F;
      double e2 = world.random.nextDouble() * 0.8F;
      double f2 = world.random.nextDouble() * 0.7F - 0.3F;
      this.world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + g, this.getY() + 0.5D + e, this.getZ() + f,
          0D, 0D, 0D);
      this.world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + g2, this.getY() + 0.5D + e2, this.getZ() + f2,
          0D, 0D, 0D);
    }
  }

  @Override
  public void walkToParent() {
    if (this.isBred() && this.isBaby() && !this.isEatingGrass()) {
      LivingEntity livingEntity = this.world.getClosestPlayer(this, 16D);
      if (livingEntity != null && this.squaredDistanceTo(livingEntity) > 4.0D) {
        this.navigation.findPathTo((Entity) livingEntity, 0);
      }
    }

  }

  @Override
  public ActionResult interactMob(PlayerEntity player, Hand hand) {
    ItemStack itemStack = player.getStackInHand(hand);

    if (this.hasPassengers()) {
      return super.interactMob(player, hand);
    }

    if (!itemStack.isEmpty()) {
      if (itemStack.isItemEqual(new ItemStack(Items.WITHER_ROSE))) {
        return this.method_30009(player, itemStack);
      }

      ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
      if (actionResult.isAccepted()) {
        return actionResult;
      }

      if (!this.isTame()) {
        world.playSoundFromEntity(null, this, SoundInit.NIGHTMARE_ANGRY_EVENT, SoundCategory.HOSTILE, 1F, 1F);
        return ActionResult.success(this.world.isClient);
      }

    }

    this.putPlayerOnBack(player);
    return ActionResult.success(this.world.isClient);
  }

  @Override
  public SoundEvent getAngrySound() {
    return SoundInit.NIGHTMARE_ANGRY_EVENT;
  }

  @Override
  public SoundEvent getAmbientSound() {
    return SoundInit.NIGHTMARE_IDLE_EVENT;
  }

  @Override
  public SoundEvent getDeathSound() {
    return SoundInit.NIGHTMARE_DEATH_EVENT;
  }

  @Override
  public SoundEvent getHurtSound(DamageSource source) {
    return SoundInit.NIGHTMARE_HURT_EVENT;
  }

  @Override
  public boolean canHaveStatusEffect(StatusEffectInstance effect) {
    return effect.getEffectType() == StatusEffects.WITHER ? false : super.canHaveStatusEffect(effect);
  }

  @Override
  public EntityGroup getGroup() {
    return EntityGroup.UNDEAD;
  }

  @Override
  public boolean canSpawn(WorldView world) {
    return !world.containsFluid(this.getBoundingBox());
  }

  @Override
  public boolean cannotDespawn() {
    return true;
  }

}