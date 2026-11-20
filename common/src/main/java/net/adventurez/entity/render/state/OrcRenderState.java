package net.adventurez.entity.render.state;

import net.minecraft.client.renderer.item.ItemStackRenderState;

public class OrcRenderState extends AdventureRenderState {
    public float attackAnim;
    public boolean doubleHandAttack;
    public int size;
    public boolean bigOrc;
    public final ItemStackRenderState inventoryItem = new ItemStackRenderState();
}
