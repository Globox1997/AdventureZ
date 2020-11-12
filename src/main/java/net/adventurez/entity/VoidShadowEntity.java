package net.adventurez.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

// import net.minecraft.entity.mob.ZombieEntity;
// import net.minecraft.entity.ai.goal.MeleeAttackGoal;
// import net.minecraft.entity.mob.PhantomEntity;
//Spawn mirror ghost, throw chunks of end stone

public class VoidShadowEntity extends FlyingEntity {

    public static final TrackedData<Boolean> halfLifeChange = DataTracker.registerData(VoidShadowEntity.class,
            TrackedDataHandlerRegistry.BOOLEAN);

    public VoidShadowEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        // this.goalSelector.add(1, new AttackGoal(this));
        // this.goalSelector.add(6, new WanderAroundGoal(this, 0.9D));
        // this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F,
        // 1.0F));
        // this.goalSelector.add(8, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        // this.targetSelector.add(1, (new RevengeGoal(this, new Class[] {
        // NecromancerEntity.class })));
        // this.targetSelector.add(2, (new RevengeGoal(this, new Class[] {
        // PiglinEntity.class })));
        // this.targetSelector.add(4, new FollowTargetGoal<>(this, PlayerEntity.class,
        // true));
    }

    public static DefaultAttributeContainer.Builder createVoidShadowAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1000.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 60.0D);
    }

    @Override
    public void mobTick() {
        super.mobTick();
        this.setNoGravity(true);
        if (this.getHealth() < this.getMaxHealth() / 2) {
            dataTracker.set(halfLifeChange, true);
        }

    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(halfLifeChange, false);
    }

    @Override
    public boolean canImmediatelyDespawn(double num) {
        return false;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove();
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
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

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isProjectile()) {
            if (source.getSource() instanceof ArrowEntity) {
                ArrowEntity arrowEntity = (ArrowEntity) source.getSource();
                if (arrowEntity.isGlowing()) {
                    return true;
                }
                source.getSource().remove();
                return false;
            }

        }
        return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
    }

    // static class AttackGoal extends Goal {
    // private final VoidShadowEntity voidShadow;
    // private int beamTicks;
    // private final boolean shadow;

    // public AttackGoal(VoidShadowEntity voidShadow) {
    // this.voidShadow = voidShadow;
    // this.shadow = voidShadow instanceof VoidShadowEntity;
    // this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    // }

    // public boolean canStart() {
    // LivingEntity livingEntity = this.voidShadow.getTarget();
    // if (livingEntity == null) {
    // return false;
    // }
    // return livingEntity != null && this.voidShadow != null &&
    // livingEntity.isAlive() || 1.0D >= this.voidShadow
    // .squaredDistanceTo(livingEntity.getX(), livingEntity.getY(),
    // livingEntity.getZ());
    // }

    // // public boolean shouldContinue() {
    // // return super.shouldContinue()
    // // && (this.shadow ||
    // // this.voidShadow.squaredDistanceTo(this.voidShadow.getTarget()) > 8.0D);
    // // }

    // public void start() {
    // this.beamTicks = -20;
    // // this.voidShadow.getNavigation().stop();
    // this.voidShadow.getLookControl().lookAt(this.voidShadow.getTarget(), 90.0F,
    // 90.0F);
    // // this.voidShadow.velocityDirty = true;
    // }

    // public void stop() {
    // this.voidShadow.setTarget((LivingEntity) null);
    // }

    // public void tick() {
    // LivingEntity livingEntity = this.voidShadow.getTarget();
    // this.voidShadow.getNavigation().stop();
    // this.voidShadow.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
    // if (!this.voidShadow.canSee(livingEntity)) {
    // this.voidShadow.setTarget((LivingEntity) null);
    // } else {
    // ++this.beamTicks;
    // if (this.beamTicks >= 100) {
    // this.voidShadow.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
    // double d = this.voidShadow.squaredDistanceTo(livingEntity.getX(),
    // livingEntity.getY(),
    // livingEntity.getZ());
    // this.attack(livingEntity, d);
    // this.voidShadow.setTarget((LivingEntity) null);

    // super.tick();
    // }

    // }
    // }

    // public void attack(LivingEntity target, double squaredDistance) {
    // double d = 8.0D;
    // if (squaredDistance <= d) {

    // this.voidShadow.swingHand(Hand.MAIN_HAND);
    // this.voidShadow.tryAttack(target);
    // }

    // }

    // }
}
