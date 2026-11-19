package net.adventurez.entity.render.feature.layer;

import net.fabricmc.api.Environment;
import net.irisshaders.iris.api.v0.IrisApi;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ExtraRenderLayer {
    public static RenderType getGlowing(ResourceLocation identifier) {
        if (RenderInit.isIrisLoaded && IrisApi.getInstance().isShaderPackInUse()) {
            return RenderType.eyes(identifier);
        } else if (RenderInit.isCanvasLoaded)
            return RenderType.entityDecal(identifier);
        else
            return RenderType.dragonExplosionAlpha(identifier);
    }

}