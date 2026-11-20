package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.animal.equine.AbstractEquineModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class NightmareEyesFeatureRenderer extends RenderLayer<EquineRenderState, AbstractEquineModel<EquineRenderState>> {
    private static final RenderType EYE_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/nightmare_eyes_feature.png"));

    public NightmareEyesFeatureRenderer(RenderLayerParent<EquineRenderState, AbstractEquineModel<EquineRenderState>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, EquineRenderState state, float yRot, float xRot) {
        submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, EYE_LAYER, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
    }
}
