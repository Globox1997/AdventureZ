package net.adventurez.entity.goal;

import java.util.List;

import net.adventurez.entity.OrcEntity;
import net.minecraft.entity.ai.goal.Goal;

public class OrcGroupGoal extends Goal {
    private final OrcEntity orcEntity;
    private int moveDelay;
    private int checkSurroundingDelay;
    private OrcEntity orcLeader;

    public OrcGroupGoal(OrcEntity orcEntity) {
        this.orcEntity = orcEntity;
        this.checkSurroundingDelay = this.getSurroundingSearchDelay(orcEntity);
    }

    private int getSurroundingSearchDelay(OrcEntity orcEntity) {
        return 200 + orcEntity.getRandom().nextInt(200) % 20;
    }

    private boolean isCloseEnoughToOrcLeader() {
        return this.orcEntity.distanceTo(this.orcLeader) <= 10.0F;
    }

    @Override
    public boolean canStart() {
        if (this.orcEntity.getSize() == 3 || this.orcEntity.isAttacking()) {
            return false;
        }
        if (this.orcLeader != null && this.isCloseEnoughToOrcLeader()) {
            return false;
        }
        if (this.checkSurroundingDelay > 0) {
            --this.checkSurroundingDelay;
            return false;
        } else {
            List<OrcEntity> list = orcEntity.world.getNonSpectatingEntities(orcEntity.getClass(),
                    orcEntity.getBoundingBox().expand(30.0D, 8.0D, 30.0D));
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getSize() == 3) {
                        this.orcLeader = list.get(i);
                        return true;
                    }
                }
                this.checkSurroundingDelay = this.getSurroundingSearchDelay(orcEntity);
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        if (this.orcLeader != null || this.orcEntity.getAttacker() == null) {
            return !this.isCloseEnoughToOrcLeader();
        } else
            return false;
    }

    @Override
    public void start() {
        this.moveDelay = 0;
    }

    @Override
    public void stop() {
        this.checkSurroundingDelay = this.getSurroundingSearchDelay(orcEntity);
    }

    @Override
    public void tick() {
        if (--this.moveDelay <= 0) {
            this.moveDelay = 20;
            if (this.orcLeader != null) {
                this.orcEntity.getNavigation().startMovingTo(this.orcLeader, 1.0D);
            }
        }
    }
}
