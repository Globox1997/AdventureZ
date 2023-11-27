package net.adventurez.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;

public class NightmareEntity extends SkeletonHorseEntity {
    private int eatingGrassTicks;
    private int damageWaterTicks;
    private static final UUID WALKING_SPEED_INCREASE_ID;
    private static final EntityAttributeModifier WALKING_SPEED_INCREASE;

    public NightmareEntity(EntityType<? extends SkeletonHorseEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
        this.experiencePoints = 5;
    }

    public static DefaultAttributeContainer.Builder createNightmareAttributes() {
        return createBaseHorseAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.215D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D);
    }

    @Override
    public void tickMovement() {
        if (!this.getWorld().isClient() && this.isAlive()) {
            if (this.random.nextInt(700) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }

            if (this.eatsGrass()) {
                if (!this.isEatingGrass() && !this.hasPassengers() && this.random.nextInt(500) == 0 && this.getWorld().getBlockState(this.getBlockPos().down()).isOf(Blocks.SOUL_SAND)) {
                    this.setEatingGrass(true);
                }

                if (this.isEatingGrass() && ++this.eatingGrassTicks > 50) {
                    this.eatingGrassTicks = 0;
                    this.setEatingGrass(false);
                }
            }

            this.walkToParent();
        }
        if (this.getWorld().isClient()) {
            double g = this.getWorld().getRandom().nextDouble() * 0.75F - 0.375F;
            double e = this.getWorld().getRandom().nextDouble() * 0.9F;
            double f = this.getWorld().getRandom().nextDouble() * 0.75F - 0.375F;
            if (!this.isBaby()) {
                double g2 = this.getWorld().getRandom().nextDouble() * 0.75F - 0.375F;
                double e2 = this.getWorld().getRandom().nextDouble() * 0.9F;
                double f2 = this.getWorld().getRandom().nextDouble() * 0.75F - 0.375F;
                this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + g, this.getY() + 0.5D + e, this.getZ() + f, 0D, 0D, 0D);
                this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + g2, this.getY() + 0.5D + e2, this.getZ() + f2, 0D, 0D, 0D);
            } else {
                this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + g, this.getY() + 0.5D + e, this.getZ() + f, 0D, 0D, 0D);
            }
        }
        if (this.touchingWater && !this.getWorld().isClient()) {
            damageWaterTicks++;
            if (damageWaterTicks == 40) {
                this.damage(this.getDamageSources().freeze(), 1F);
                damageWaterTicks = 0;
            }
        }
        if (this.isOnSoulSpeedBlock() && !this.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKING_SPEED_INCREASE_ID)) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(WALKING_SPEED_INCREASE);
        } else if (!this.isOnSoulSpeedBlock() && this.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKING_SPEED_INCREASE_ID)) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(WALKING_SPEED_INCREASE_ID);
        }
        super.tickMovement();
    }

    @Override
    public void removeAllPassengers() {
        if (!this.isAlive() || !this.hasPassengers() || !this.getPassengerList().get(0).isAlive() || !(this.getPassengerList().get(0) instanceof SoulReaperEntity)) {
            super.removeAllPassengers();
        }
    }

    @Override
    public void walkToParent() {
        if (this.isBred() && this.isBaby() && !this.isEatingGrass()) {
            LivingEntity livingEntity = this.getWorld().getClosestPlayer(this, 16D);
            if (livingEntity != null && this.squaredDistanceTo(livingEntity) > 4.0D) {
                this.navigation.findPathTo((Entity) livingEntity, 0);
            }
        }

    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.hasPassengers() || this.isBaby()) {
            return super.interactMob(player, hand);
        }

        if (!itemStack.isEmpty()) {
            if (itemStack.isOf(Items.WITHER_ROSE)) {
                return this.interactHorse(player, itemStack);
            }
            ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
            if (actionResult.isAccepted()) {
                return actionResult;
            }
            if (!this.isTame()) {
                this.getWorld().playSoundFromEntity(null, this, SoundInit.NIGHTMARE_ANGRY_EVENT, SoundCategory.HOSTILE, 1F, 1F);
                return ActionResult.success(this.getWorld().isClient());
            }
        }

        this.putPlayerOnBack(player);
        return ActionResult.success(this.getWorld().isClient());
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
    @Nullable
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return (PassiveEntity) EntityInit.NIGHTMARE.create(serverWorld);
    }

    static {
        WALKING_SPEED_INCREASE_ID = UUID.fromString("020E0DFB-87AE-8274-9556-928370E291A0");
        WALKING_SPEED_INCREASE = new EntityAttributeModifier(WALKING_SPEED_INCREASE_ID, "LavaAndSoulSpeed", 0.5D, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    }

}
