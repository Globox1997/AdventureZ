package net.adventurez.entity.goal;

import java.util.EnumSet;

import net.adventurez.entity.DragonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.Vec3d;

public class DragonFindOwnerGoal extends Goal {
    private final DragonEntity dragonEntity;
    private LivingEntity owner;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private float oldWaterPathfindingPenalty;

    public DragonFindOwnerGoal(DragonEntity dragonEntity, double speed, float minDistance, float maxDistance) {
        this.dragonEntity = dragonEntity;
        this.speed = speed;
        this.navigation = dragonEntity.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.dragonEntity.getOwner();
        if (livingEntity == null) {
            return false;
        } else if (livingEntity.isSpectator()) {
            return false;
        } else if (this.dragonEntity.isInSittingPose()) {
            return false;
        } else if (!this.dragonEntity.isFlying) {
            return false;
        } else if (this.dragonEntity.hasPassengers()) {
            return false;
        } else if (!this.dragonEntity.canSee(livingEntity)) {
            return false;
        } else if (this.dragonEntity.squaredDistanceTo(livingEntity) < (double) (this.minDistance * this.minDistance)) {
            return false;
        } else {
            this.owner = livingEntity;
            return true;
        }
    }

    @Override
    public boolean shouldContinue() {
        if (this.dragonEntity.squaredDistanceTo(this.owner) > 800.0D) {
            return false;
        } else if (this.dragonEntity.isInSittingPose() || !this.dragonEntity.canSee(this.owner)) {
            return false;
        } else {
            return this.dragonEntity.squaredDistanceTo(this.owner) > (double) (this.maxDistance * this.maxDistance) && !dragonEntity.isOnGround() && dragonEntity.isFlying;
        }
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.dragonEntity.getPathfindingPenalty(PathNodeType.WATER);
        this.dragonEntity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.dragonEntity.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        this.dragonEntity.getLookControl().lookAt(this.owner, 10.0F, (float) this.dragonEntity.getLookPitchSpeed());
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            if (!this.dragonEntity.isLeashed() && !this.dragonEntity.hasVehicle()) {
                if (this.dragonEntity.squaredDistanceTo(this.owner) <= 800.0D) {
                    Vec3d vec3d = new Vec3d(this.owner.getX() - this.dragonEntity.getX(), this.owner.getY() - this.dragonEntity.getY(), this.owner.getZ() - this.dragonEntity.getZ());
                    vec3d = vec3d.normalize();
                    this.dragonEntity.setVelocity(this.dragonEntity.getVelocity().add(vec3d.multiply(this.speed)));
                }

            }
        }
    }

}
