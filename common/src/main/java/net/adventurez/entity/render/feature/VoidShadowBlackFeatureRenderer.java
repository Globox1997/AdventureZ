package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.VoidShadowModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.entity.render.state.VoidShadowRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class VoidShadowBlackFeatureRenderer extends RenderLayer<VoidShadowRenderState, VoidShadowModel> {
    private static final RenderType BLACK_LAYER = RenderTypes.entityCutout(Identifier.parse("adventurez:textures/entity/black_void_shadow.png"));
    private static final RenderType EYE_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/black_void_shadow_eyes_feature.png"));

    public VoidShadowBlackFeatureRenderer(RenderLayerParent<VoidShadowRenderState, VoidShadowModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, VoidShadowRenderState state, float yRot, float xRot) {
        if (state.halfLifeChange) {
            submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, BLACK_LAYER, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
            submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, EYE_LAYER, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }
    }
}
