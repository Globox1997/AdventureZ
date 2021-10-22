package net.adventurez.entity.render.feature.layer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class ExtraRenderLayer {
    public static RenderLayer getGlowing(String string) {
        if (FabricLoader.getInstance().isModLoaded("canvas"))
            return RenderLayer.getEntityDecal(new Identifier(string));
        else if (FabricLoader.getInstance().isModLoaded("iris"))
            return RenderLayer.getEyes(new Identifier(string));
        else
            return RenderLayer.getEntityAlpha(new Identifier(string));
    }

}