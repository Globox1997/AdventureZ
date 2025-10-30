package net.adventurez.entity;

import net.adventurez.init.EntityInit;
import net.adventurez.init.ParticleInit;
import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;

public class SkunkEntity extends AnimalEntity {

    private int fartTick = 1200;

    public SkunkEntity(EntityType<? extends SkunkEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSkunkAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.8D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.20D, Ingredient.ofItems(Items.SHORT_GRASS, Items.FERN), true));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.add(5, new EscapePlayerGoal(this, 2.0D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            this.fartTick--;

            Vec3d velocity = this.getVelocity();
            Vec3d rotationVec = this.getRotationVector();

            if (this.fartTick <= 0) {
                for (int i = 0; i < 10; i++) {
                    this.getWorld().addParticle(ParticleInit.FART_PARTICLE, this.getX() - rotationVec.x * 0.5, this.getEyeY() - 0.1F, this.getZ() - rotationVec.z * 0.5, -velocity.x, 0.0, -velocity.z);
                }
                this.getWorld().playSoundFromEntity(this, SoundInit.SKUNK_FART_EVENT, SoundCategory.NEUTRAL, 0.7f, this.getRandom().nextFloat() * 0.4f + 0.8f);
                this.fartTick = 800 + this.getRandom().nextInt(400);
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.SKUNK_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.SKUNK_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.SKUNK_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    public SkunkEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return EntityInit.SKUNK.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SHORT_GRASS) || stack.isOf(Items.FERN) || stack.isOf(Items.TALL_GRASS);
    }

    private static class EscapePlayerGoal extends Goal {
        protected final SkunkEntity skunkEntity;
        protected final double speed;
        protected double targetX;
        protected double targetY;
        protected double targetZ;

        public EscapePlayerGoal(SkunkEntity skunkEntity, double speed) {
            this.skunkEntity = skunkEntity;
            this.speed = speed;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (this.skunkEntity.getWorld().getClosestPlayer(this.skunkEntity, 4.0D) == null) {
                return false;
            } else {
                return this.findTarget();
            }
        }

        private boolean findTarget() {
            Vec3d vec3d = NoPenaltyTargeting.find(this.skunkEntity, 12, 4);
            if (vec3d == null) {
                return false;
            } else {
                this.targetX = vec3d.x;
                this.targetY = vec3d.y;
                this.targetZ = vec3d.z;
                return true;
            }
        }

        @Override
        public void start() {
            this.skunkEntity.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
        }

        @Override
        public boolean shouldContinue() {
            return !this.skunkEntity.getNavigation().isIdle();
        }

    }

}
