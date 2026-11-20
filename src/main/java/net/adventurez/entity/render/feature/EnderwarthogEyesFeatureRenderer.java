package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.EnderwarthogModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.entity.render.state.EnderwarthogRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class EnderwarthogEyesFeatureRenderer extends RenderLayer<EnderwarthogRenderState, EnderwarthogModel> {
    private static final RenderType EYE_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/enderwarthog_eyes_feature.png"));
    private static final RenderType RARE_LAYER = RenderTypes.entityCutout(Identifier.parse("adventurez:textures/entity/feature/rare_enderwarthog.png"));

    public EnderwarthogEyesFeatureRenderer(RenderLayerParent<EnderwarthogRenderState, EnderwarthogModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, EnderwarthogRenderState state, float yRot, float xRot) {
        submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, EYE_LAYER, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
        if (state.rareVariant) {
            submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, RARE_LAYER, 20, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }
    }
}
