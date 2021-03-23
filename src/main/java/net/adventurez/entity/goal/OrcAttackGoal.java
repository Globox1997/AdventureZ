package net.adventurez.entity.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class OrcAttackGoal extends MeleeAttackGoal {
    protected final PathAwareEntity mob;

    public OrcAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        this.mob = mob;
    }

    @Override
    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        return (double) (this.mob.getWidth() * 2.0F * this.mob.getWidth() + entity.getWidth());
    }
}
