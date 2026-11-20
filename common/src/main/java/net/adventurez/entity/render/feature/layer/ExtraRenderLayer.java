package net.adventurez.entity.render.feature.layer;

import net.adventurez.init.RenderInit;
import net.irisshaders.iris.api.v0.IrisApi;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class ExtraRenderLayer {
    public static RenderType getGlowing(Identifier identifier) {
        if (RenderInit.isIrisLoaded && IrisApi.getInstance().isShaderPackInUse()) {
            return RenderTypes.eyes(identifier);
        } else {
            return RenderTypes.entityTranslucentEmissive(identifier);
        }
    }

}
