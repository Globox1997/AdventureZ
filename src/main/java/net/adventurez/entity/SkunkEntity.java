package net.adventurez.entity;

import net.adventurez.init.EntityInit;
import net.adventurez.init.ParticleInit;
import net.adventurez.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;

public class SkunkEntity extends Animal {

    private int fartTick = 1200;

    public SkunkEntity(EntityType<? extends SkunkEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createSkunkAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.21D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.8D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.20D, Ingredient.of(Items.SHORT_GRASS, Items.FERN), true));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new EscapePlayerGoal(this, 2.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.fartTick--;

            Vec3 velocity = this.getDeltaMovement();
            Vec3 rotationVec = this.getLookAngle();

            if (this.fartTick <= 0) {
                for (int i = 0; i < 10; i++) {
                    this.level().addParticle(ParticleInit.FART_PARTICLE, this.getX() - rotationVec.x * 0.5, this.getEyeY() - 0.1F, this.getZ() - rotationVec.z * 0.5, -velocity.x, 0.0, -velocity.z);
                }
                this.level().playLocalSound(this, SoundInit.SKUNK_FART_EVENT, SoundSource.NEUTRAL, 0.7f, this.getRandom().nextFloat() * 0.4f + 0.8f);
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
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    public SkunkEntity getBreedOffspring(ServerLevel serverWorld, AgeableMob passiveEntity) {
        return EntityInit.SKUNK.create(serverWorld);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SHORT_GRASS) || stack.is(Items.FERN) || stack.is(Items.TALL_GRASS);
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
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.skunkEntity.level().getNearestPlayer(this.skunkEntity, 4.0D) == null) {
                return false;
            } else {
                return this.findTarget();
            }
        }

        private boolean findTarget() {
            Vec3 vec3d = DefaultRandomPos.getPos(this.skunkEntity, 12, 4);
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
            this.skunkEntity.getNavigation().moveTo(this.targetX, this.targetY, this.targetZ, this.speed);
        }

        @Override
        public boolean canContinueToUse() {
            return !this.skunkEntity.getNavigation().isDone();
        }

    }

}
