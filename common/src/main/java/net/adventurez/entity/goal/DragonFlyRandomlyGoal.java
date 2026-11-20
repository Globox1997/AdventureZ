package net.adventurez.entity.goal;

import java.util.EnumSet;

import net.adventurez.entity.DragonEntity;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class DragonFlyRandomlyGoal extends Goal {
    private final DragonEntity dragonEntity;
    private int flyTimer;
    private int waitTimer;
    private double d;
    private double e;
    private double f;

    public DragonFlyRandomlyGoal(DragonEntity dragonEntity) {
        this.dragonEntity = dragonEntity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        waitTimer++;
        if (waitTimer > 100 && flyTimer <= 0 && dragonEntity.xxa == 0.0F && dragonEntity.zza == 0.0F && dragonEntity.isFlying && !this.dragonEntity.isVehicle()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        flyTimer--;
        Vec3 vec3d2 = new Vec3(d, e, f);
        Vec3 vec3d = this.dragonEntity.getDeltaMovement().add(vec3d2);
        this.dragonEntity.setDeltaMovement(vec3d);
        this.dragonEntity.yHeadRot = Mth.wrapDegrees((float) (Mth.atan2(f, d) * 57.2957763671875D) - 90.0F);
    }

    @Override
    public boolean canContinueToUse() {
        if (flyTimer > 0) {
            return true;
        } else
            return false;
    }

    @Override
    public void stop() {
        flyTimer = 0;
        waitTimer = 0;
    }

    @Override
    public void start() {
        RandomSource random = this.dragonEntity.getRandom();
        flyTimer = 100 + random.nextInt(60);
        Math.sin(Math.PI * random.nextDouble());
        d = Math.sin(Math.PI * random.nextDouble() * 2.0D) * 0.007D;
        e = Math.sin(Math.PI * random.nextDouble() * 2.0D) * 0.001D;
        f = Math.sin(Math.PI * random.nextDouble() * 2.0D) * 0.007D;
    }
}
