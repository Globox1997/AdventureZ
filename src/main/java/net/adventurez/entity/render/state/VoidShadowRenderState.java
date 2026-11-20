package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VoidShadowRenderState extends AdventureRenderState {
    public boolean throwingBlocks;
    public boolean hoveringMagicHands;
    public boolean circlingHands;
    public boolean halfLifeChange;
    public float throwingBlocksTicker;
    public int ticksSinceDeath;
    public float deathAnimationProgress;
}
