package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

@Environment(EnvType.CLIENT)
public class SoulReaperRenderState extends HumanoidRenderState {
    public boolean aggressive;
    public boolean holdingBow;
    public boolean mainHandEmpty;
}
