package net.adventurez.entity;

import java.util.EnumSet;

import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.adventurez.entity.nonliving.VoidBulletEntity;
import net.adventurez.init.SoundInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class VoidShadeEntity extends FlyingEntity implements Monster {

    public VoidShadeEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new VoidShadeEntity.VoidShadeMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder createVoidShadeAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.11D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.5D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 80.0D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(3, new VoidShadeEntity.LookAtTargetGoal(this));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.goalSelector.add(3, new VoidShadeEntity.MoveGoal(this));
        this.goalSelector.add(5, new VoidShadeEntity.ShootBulletGoal(this));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source) || source.getSource() instanceof ThrownRockEntity) {
            return false;
        } else
            return super.damage(source, amount);
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect, Entity entity) {
        return false;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    static class ShootBulletGoal extends Goal {
        private final VoidShadeEntity voidShadeEntity;
        public int cooldown;

        public ShootBulletGoal(VoidShadeEntity voidShadeEntity) {
            this.voidShadeEntity = voidShadeEntity;
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.voidShadeEntity.getTarget();
            return livingEntity != null && this.voidShadeEntity.canSee(livingEntity);
        }

        @Override
        public void start() {
            this.cooldown = 0;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.voidShadeEntity.getTarget();
            if (livingEntity.distanceTo(this.voidShadeEntity) < 7.0D) {
                World world = this.voidShadeEntity.world;
                ++this.cooldown;
                if (this.cooldown == 20) {
                    ((ServerWorld) this.voidShadeEntity.world).playSoundFromEntity(null, this.voidShadeEntity, SoundInit.SHADOW_CAST_EVENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
                    Vec3d vec3d = this.voidShadeEntity.getRotationVec(1.0F);
                    VoidBulletEntity voidBulletEntity = new VoidBulletEntity(world, this.voidShadeEntity, vec3d.x + world.random.nextFloat() * 0.5F - 0.25F, vec3d.y,
                            vec3d.z + world.random.nextFloat() * 0.5F - 0.25F);
                    world.spawnEntity(voidBulletEntity);

                    this.cooldown = -40;
                }
            } else if (this.cooldown > 0) {
                --this.cooldown;
            }
        }
    }

    static class LookAtTargetGoal extends Goal {
        private final VoidShadeEntity voidShadeEntity;

        public LookAtTargetGoal(VoidShadeEntity voidShadeEntity) {
            this.voidShadeEntity = voidShadeEntity;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void tick() {
            if (this.voidShadeEntity.getTarget() == null) {
                Vec3d vec3d = this.voidShadeEntity.getVelocity();
                this.voidShadeEntity.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
                this.voidShadeEntity.bodyYaw = this.voidShadeEntity.getYaw();
            } else {
                LivingEntity livingEntity = this.voidShadeEntity.getTarget();
                if (livingEntity.squaredDistanceTo(this.voidShadeEntity) < 4096.0D) {
                    double e = livingEntity.getX() - this.voidShadeEntity.getX();
                    double f = livingEntity.getZ() - this.voidShadeEntity.getZ();
                    this.voidShadeEntity.setYaw(-((float) MathHelper.atan2(e, f)) * 57.295776F);
                    this.voidShadeEntity.bodyYaw = this.voidShadeEntity.getYaw();
                }
            }

        }
    }

    static class MoveGoal extends Goal {
        private final VoidShadeEntity voidShadeEntity;

        public MoveGoal(VoidShadeEntity voidShadeEntity) {
            this.voidShadeEntity = voidShadeEntity;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (this.voidShadeEntity.getTarget() != null) {
                return true;
            } else
                return false;
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            if (this.voidShadeEntity.getTarget() != null) {
                LivingEntity livingEntity = this.voidShadeEntity.getTarget();
                this.voidShadeEntity.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);

            }
        }
    }

    static class VoidShadeMoveControl extends MoveControl {
        private final VoidShadeEntity voidShadeEntity;

        public VoidShadeMoveControl(VoidShadeEntity voidShadeEntity) {
            super(voidShadeEntity);
            this.voidShadeEntity = voidShadeEntity;
        }

        @Override
        public void tick() {
            if (this.state == MoveControl.State.MOVE_TO) {
                Vec3d vec3d = new Vec3d(this.targetX - this.voidShadeEntity.getX(), this.targetY - this.voidShadeEntity.getY(), this.targetZ - this.voidShadeEntity.getZ());
                vec3d = vec3d.normalize();
                this.voidShadeEntity.setVelocity(this.voidShadeEntity.getVelocity().add(vec3d.multiply(0.012D)));
                if (this.voidShadeEntity.getTarget() == null) {
                    this.state = MoveControl.State.WAIT;
                }

            }
        }
    }

}
