package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

@Environment(EnvType.CLIENT)
public class ProjectileRenderState extends EntityRenderState {
    public float yRot;
    public float xRot;
    public boolean golemRock;
}
