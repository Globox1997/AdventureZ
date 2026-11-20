package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class AmethystGolemRenderState extends AdventureRenderState {
    public float attackAnim;
    public int backCrystals;
    public boolean deepslateVariant;
}
