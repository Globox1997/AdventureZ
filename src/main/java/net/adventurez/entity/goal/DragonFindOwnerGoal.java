package net.adventurez.entity.goal;

import java.util.EnumSet;

import net.adventurez.entity.DragonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class DragonFindOwnerGoal extends Goal {
    private final DragonEntity dragonEntity;
    private LivingEntity owner;
    private final double speed;
    private final PathNavigation navigation;
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
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity = this.dragonEntity.getOwner();
        if (livingEntity == null) {
            return false;
        } else if (livingEntity.isSpectator()) {
            return false;
        } else if (this.dragonEntity.isInSittingPose()) {
            return false;
        } else if (!this.dragonEntity.isFlying) {
            return false;
        } else if (this.dragonEntity.isVehicle()) {
            return false;
        } else if (!this.dragonEntity.hasLineOfSight(livingEntity)) {
            return false;
        } else if (this.dragonEntity.distanceToSqr(livingEntity) < (double) (this.minDistance * this.minDistance)) {
            return false;
        } else {
            this.owner = livingEntity;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.dragonEntity.distanceToSqr(this.owner) > 800.0D) {
            return false;
        } else if (this.dragonEntity.isInSittingPose() || !this.dragonEntity.hasLineOfSight(this.owner)) {
            return false;
        } else {
            return this.dragonEntity.distanceToSqr(this.owner) > (double) (this.maxDistance * this.maxDistance) && !dragonEntity.onGround() && dragonEntity.isFlying;
        }
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.dragonEntity.getPathfindingMalus(PathType.WATER);
        this.dragonEntity.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.dragonEntity.setPathfindingMalus(PathType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        this.dragonEntity.getLookControl().setLookAt(this.owner, 10.0F, (float) this.dragonEntity.getMaxHeadXRot());
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            if (!this.dragonEntity.isLeashed() && !this.dragonEntity.isPassenger()) {
                if (this.dragonEntity.distanceToSqr(this.owner) <= 800.0D) {
                    Vec3 vec3d = new Vec3(this.owner.getX() - this.dragonEntity.getX(), this.owner.getY() - this.dragonEntity.getY(), this.owner.getZ() - this.dragonEntity.getZ());
                    vec3d = vec3d.normalize();
                    this.dragonEntity.setDeltaMovement(this.dragonEntity.getDeltaMovement().add(vec3d.scale(this.speed)));
                }

            }
        }
    }

}
