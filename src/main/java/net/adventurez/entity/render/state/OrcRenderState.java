package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Environment(EnvType.CLIENT)
public class OrcRenderState extends AdventureRenderState {
    public float attackAnim;
    public boolean doubleHandAttack;
    public int size;
    public boolean bigOrc;
    public final ItemStackRenderState inventoryItem = new ItemStackRenderState();
}
