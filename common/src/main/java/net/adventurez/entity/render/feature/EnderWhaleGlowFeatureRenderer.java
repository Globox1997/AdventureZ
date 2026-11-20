package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.EnderWhaleModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class EnderWhaleGlowFeatureRenderer extends RenderLayer<AdventureRenderState, EnderWhaleModel> {
    private static final RenderType GLOW_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/ender_whale_glow_feature.png"));

    public EnderWhaleGlowFeatureRenderer(RenderLayerParent<AdventureRenderState, EnderWhaleModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, AdventureRenderState state, float yRot, float xRot) {
        submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, GLOW_LAYER, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
    }
}
