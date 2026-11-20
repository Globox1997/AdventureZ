package net.adventurez.entity.render.feature.layer;

import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.irisshaders.iris.api.v0.IrisApi;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class ExtraRenderLayer {
    public static RenderType getGlowing(Identifier identifier) {
        if (RenderInit.isIrisLoaded && IrisApi.getInstance().isShaderPackInUse()) {
            return RenderTypes.eyes(identifier);
        } else {
            return RenderTypes.entityTranslucentEmissive(identifier);
        }
    }

}
