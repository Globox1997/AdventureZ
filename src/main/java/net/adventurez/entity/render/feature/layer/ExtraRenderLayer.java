package net.adventurez.entity.render.feature.layer;

import net.fabricmc.api.Environment;
import net.adventurez.init.RenderInit;
import net.coderbot.iris.Iris;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ExtraRenderLayer {
    public static RenderLayer getGlowing(Identifier identifier) {
        if (RenderInit.isIrisLoaded && Iris.getIrisConfig().areShadersEnabled() && Iris.getCurrentPack().isPresent()) {
            return RenderLayer.getEyes(identifier);
        } else if (RenderInit.isCanvasLoaded)
            return RenderLayer.getEntityDecal(identifier);
        else
            return RenderLayer.getEntityAlpha(identifier);
    }

}