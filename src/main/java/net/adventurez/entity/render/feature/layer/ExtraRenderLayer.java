package net.adventurez.entity.render.feature.layer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class ExtraRenderLayer {
    public static RenderLayer getGlowing(String string) {
        return RenderLayer.getEntityAlpha(new Identifier(string));
    }

}