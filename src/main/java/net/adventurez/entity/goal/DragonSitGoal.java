package net.adventurez.entity.goal;

import java.util.EnumSet;

import net.adventurez.entity.DragonEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class DragonSitGoal extends Goal {
    private final DragonEntity dragonEntity;

    public DragonSitGoal(DragonEntity dragonEntity) {
        this.dragonEntity = dragonEntity;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean canContinueToUse() {
        return this.dragonEntity.isInSittingPose();
    }

    @Override
    public boolean canUse() {
        if (!this.dragonEntity.isTamed()) {
            return false;
        } else if (this.dragonEntity.isInWaterOrBubble()) {
            return false;
        } else if (!this.dragonEntity.onGround()) {
            return false;
        } else {
            if (this.dragonEntity.getOwner() == null) {
                // Has to be true cause if Owner is not there
                // Only if owner is there, dragon can get set to walk
                return true;
            } else {
                return this.dragonEntity.isInSittingPose();
            }
        }
    }

    @Override
    public void start() {
        this.dragonEntity.getNavigation().stop();
        this.dragonEntity.setSitting(true);
    }

    @Override
    public void stop() {
        this.dragonEntity.setSitting(false);
    }
}
