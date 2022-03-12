package net.adventurez.entity.render.feature.layer;

import net.fabricmc.api.Environment;
import net.irisshaders.iris.api.v0.IrisApi;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ExtraRenderLayer {
    public static RenderLayer getGlowing(Identifier identifier) {
        if (RenderInit.isIrisLoaded && IrisApi.getInstance().isShaderPackInUse()) {
            return RenderLayer.getEyes(identifier);
        } else if (RenderInit.isCanvasLoaded)
            return RenderLayer.getEntityDecal(identifier);
        else
            return RenderLayer.getEntityAlpha(identifier);
    }

}