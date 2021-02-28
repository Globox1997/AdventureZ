package net.adventurez.entity.goal;

import java.util.List;

import net.adventurez.entity.OrkEntity;
import net.minecraft.entity.ai.goal.Goal;

public class OrkGroupGoal extends Goal {
    private final OrkEntity orkEntity;
    private int moveDelay;
    private int checkSurroundingDelay;
    private OrkEntity orkLeader;

    public OrkGroupGoal(OrkEntity orkEntity) {
        this.orkEntity = orkEntity;
        this.checkSurroundingDelay = this.getSurroundingSearchDelay(orkEntity);
    }

    private int getSurroundingSearchDelay(OrkEntity orkEntity) {
        return 200 + orkEntity.getRandom().nextInt(200) % 20;
    }

    private boolean isCloseEnoughToOrkLeader() {
        return this.orkEntity.distanceTo(this.orkLeader) <= 10.0F;
    }

    @Override
    public boolean canStart() {
        if (this.orkEntity.getSize() == 3 || this.orkEntity.isAttacking()) {
            return false;
        }
        if (this.orkLeader != null && this.isCloseEnoughToOrkLeader()) {
            return false;
        }
        if (this.checkSurroundingDelay > 0) {
            --this.checkSurroundingDelay;
            return false;
        } else {
            List<OrkEntity> list = orkEntity.world.getNonSpectatingEntities(orkEntity.getClass(),
                    orkEntity.getBoundingBox().expand(30.0D, 8.0D, 30.0D));
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getSize() == 3) {
                        this.orkLeader = list.get(i);
                        return true;
                    }
                }
                this.checkSurroundingDelay = this.getSurroundingSearchDelay(orkEntity);
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        if (this.orkLeader != null || this.orkEntity.getAttacker() == null) {
            return !this.isCloseEnoughToOrkLeader();
        } else
            return false;
    }

    @Override
    public void start() {
        this.moveDelay = 0;
    }

    @Override
    public void stop() {
        this.checkSurroundingDelay = this.getSurroundingSearchDelay(orkEntity);
    }

    @Override
    public void tick() {
        if (--this.moveDelay <= 0) {
            this.moveDelay = 20;
            if (this.orkLeader != null) {
                this.orkEntity.getNavigation().startMovingTo(this.orkLeader, 1.0D);
            }
        }
    }
}
