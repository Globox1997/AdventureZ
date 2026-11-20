package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.BlackstoneGolemModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.entity.render.state.BlackstoneGolemRenderState;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class StoneGolemLavaFeatureRenderer extends RenderLayer<BlackstoneGolemRenderState, BlackstoneGolemModel> {
    private static final RenderType LAVA_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/lava_feature_golem.png"));
    private static final RenderType FLOWING_LAVA_LAYER = RenderTypes.entityCutout(Identifier.parse("adventurez:textures/entity/feature/lava_feature_golem.png"));

    public StoneGolemLavaFeatureRenderer(RenderLayerParent<BlackstoneGolemRenderState, BlackstoneGolemModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, BlackstoneGolemRenderState state, float yRot, float xRot) {
        if (!state.halfLifeChange) {
            int lavaFlow = state.lavaTexture;
            RenderType renderType = LAVA_LAYER;
            if (lavaFlow < 240 && !RenderInit.isCanvasLoaded && !RenderInit.isSodiumLoaded) {
                renderType = FLOWING_LAVA_LAYER;
            } else {
                lavaFlow = Integer.MAX_VALUE;
            }
            submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, renderType, lavaFlow, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }
    }
}
