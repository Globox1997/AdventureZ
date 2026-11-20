package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NecromancerRenderState extends AdventureRenderState {
    public boolean spellcasting;
}
