package net.adventurez.entity.render.state;

import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class TheEyeRenderState extends AdventureRenderState {
    public float health;
    public float maxHealth;
    public int invulTimer;
    public @Nullable Vec3 beamTargetPosition;
    public Vec3 beamStartPosition = Vec3.ZERO;
    public float beamTime;
    public int tickCount;
}
