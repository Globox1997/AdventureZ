package net.adventurez.entity.render.feature.layer;

import net.fabricmc.api.Environment;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ExtraRenderLayer {
    public static RenderLayer getGlowing(String string) {
        if (RenderInit.isCanvasLoaded)
            return RenderLayer.getEntityDecal(new Identifier(string));
        else if (RenderInit.isIrisLoaded)
            return RenderLayer.getEyes(new Identifier(string));
        else
            return RenderLayer.getEntityAlpha(new Identifier(string));
    }

}