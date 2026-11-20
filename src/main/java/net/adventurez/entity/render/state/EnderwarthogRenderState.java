package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EnderwarthogRenderState extends AdventureRenderState {
    public boolean biteAttack;
    public boolean rareVariant;
}
