package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.VoidShadeModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class VoidShadeEyesFeatureRenderer extends RenderLayer<AdventureRenderState, VoidShadeModel> {
    private static final RenderType EYE_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/void_shade_eyes_feature.png"));

    public VoidShadeEyesFeatureRenderer(RenderLayerParent<AdventureRenderState, VoidShadeModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, AdventureRenderState state, float yRot, float xRot) {
        submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, EYE_LAYER, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
    }
}
