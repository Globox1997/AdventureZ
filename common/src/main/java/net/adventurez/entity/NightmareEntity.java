package net.adventurez.entity;

import org.jetbrains.annotations.Nullable;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.SkeletonHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.entity.EntitySpawnReason;

public class NightmareEntity extends SkeletonHorse {
    private int eatingGrassTicks;
    private int damageWaterTicks;
    private static final Identifier WALKING_SPEED_INCREASE_ID;
    private static final AttributeModifier WALKING_SPEED_INCREASE;

    public NightmareEntity(EntityType<? extends SkeletonHorse> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createNightmareAttributes() {
        return createBaseHorseAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.215D).add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide() && this.isAlive()) {
            if (this.random.nextInt(700) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }

            if (this.canEatGrass()) {
                if (!this.isEating() && !this.isVehicle() && this.random.nextInt(500) == 0 && this.level().getBlockState(this.blockPosition().below()).is(Blocks.SOUL_SAND)) {
                    this.setEating(true);
                }

                if (this.isEating() && ++this.eatingGrassTicks > 50) {
                    this.eatingGrassTicks = 0;
                    this.setEating(false);
                }
            }

            this.followMommy((ServerLevel) this.level());
        }
        if (this.level().isClientSide()) {
            double g = this.level().getRandom().nextDouble() * 0.75F - 0.375F;
            double e = this.level().getRandom().nextDouble() * 0.9F;
            double f = this.level().getRandom().nextDouble() * 0.75F - 0.375F;
            if (!this.isBaby()) {
                double g2 = this.level().getRandom().nextDouble() * 0.75F - 0.375F;
                double e2 = this.level().getRandom().nextDouble() * 0.9F;
                double f2 = this.level().getRandom().nextDouble() * 0.75F - 0.375F;
                this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + g, this.getY() + 0.5D + e, this.getZ() + f, 0D, 0D, 0D);
                this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + g2, this.getY() + 0.5D + e2, this.getZ() + f2, 0D, 0D, 0D);
            } else {
                this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + g, this.getY() + 0.5D + e, this.getZ() + f, 0D, 0D, 0D);
            }
        }
        if (this.wasTouchingWater && !this.level().isClientSide()) {
            damageWaterTicks++;
            if (damageWaterTicks == 40) {
                this.hurt(this.damageSources().freeze(), 1F);
                damageWaterTicks = 0;
            }
        }

        if (this.isOnSoulSpeedBlock() && !this.getAttributes().hasModifier(Attributes.MOVEMENT_SPEED, WALKING_SPEED_INCREASE_ID)) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(WALKING_SPEED_INCREASE);
        } else if (!this.isOnSoulSpeedBlock() && this.getAttributes().hasModifier(Attributes.MOVEMENT_SPEED, WALKING_SPEED_INCREASE_ID)) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(WALKING_SPEED_INCREASE_ID);
        }
        super.aiStep();
    }

    private boolean isOnSoulSpeedBlock() {
        return this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.SOUL_SPEED_BLOCKS);
    }

    @Override
    public void ejectPassengers() {
        if (!this.isAlive() || !this.isVehicle() || !this.getPassengers().get(0).isAlive() || !(this.getPassengers().get(0) instanceof SoulReaperEntity)) {
            super.ejectPassengers();
        }
    }

    @Override
    protected void followMommy(ServerLevel level) {
        if (this.isBred() && this.isBaby() && !this.isEating()) {
            LivingEntity livingEntity = this.level().getNearestPlayer(this, 16D);
            if (livingEntity != null && this.distanceToSqr(livingEntity) > 4.0D) {
                this.navigation.createPath((Entity) livingEntity, 0);
            }
        }

    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (this.isVehicle() || this.isBaby()) {
            return super.mobInteract(player, hand);
        }

        if (!itemStack.isEmpty()) {
            if (itemStack.is(Items.WITHER_ROSE)) {
                return this.fedFood(player, itemStack);
            }
            InteractionResult actionResult = itemStack.interactLivingEntity(player, this, hand);
            if (actionResult.consumesAction()) {
                return actionResult;
            }
            if (!this.isTamed()) {
                this.level().playSound(null, this, SoundInit.NIGHTMARE_ANGRY_EVENT, SoundSource.HOSTILE, 1F, 1F);
                return InteractionResult.SUCCESS;
            }
        }

        this.doPlayerRide(player);
        return InteractionResult.SUCCESS;
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
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() == MobEffects.WITHER ? false : super.canBeAffected(effect);
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob passiveEntity) {
        return EntityInit.NIGHTMARE.create(serverWorld, EntitySpawnReason.MOB_SUMMONED);
    }

    static {
        WALKING_SPEED_INCREASE_ID = Identifier.parse("adventurez:walking_speed");
        WALKING_SPEED_INCREASE = new AttributeModifier(WALKING_SPEED_INCREASE_ID, 0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

}
