package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BlackstoneGolemRenderState extends AdventureRenderState {
    public int throwCooldown;
    public int roarTick;
    public boolean halfLifeChange;
    public int lavaTexture;
}
