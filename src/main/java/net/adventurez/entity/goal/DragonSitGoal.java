package net.adventurez.entity.goal;

import java.util.EnumSet;

import net.adventurez.entity.DragonEntity;
import net.minecraft.entity.ai.goal.Goal;

public class DragonSitGoal extends Goal {
    private final DragonEntity dragonEntity;

    public DragonSitGoal(DragonEntity dragonEntity) {
        this.dragonEntity = dragonEntity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    @Override
    public boolean shouldContinue() {
        return this.dragonEntity.isInSittingPose();
    }

    @Override
    public boolean canStart() {
        if (!this.dragonEntity.isTamed()) {
            return false;
        } else if (this.dragonEntity.isInsideWaterOrBubbleColumn()) {
            return false;
        } else if (!this.dragonEntity.isOnGround()) {
            return false;
        } else {
            if (this.dragonEntity.getOwner() == null) {
                return false;
            } else {
                return this.dragonEntity.isInSittingPose();
            }
        }
    }

    @Override
    public void start() {
        this.dragonEntity.getNavigation().stop();
        this.dragonEntity.setInSittingPose(true);
    }

    @Override
    public void stop() {
        this.dragonEntity.setInSittingPose(false);
    }
}
