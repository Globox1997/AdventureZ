package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class AdventureRenderState extends LivingEntityRenderState {
    public float attackTime;
}
