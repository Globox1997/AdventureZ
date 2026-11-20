package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.VoidFragmentModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.entity.render.state.VoidFragmentRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class VoidFragmentCorruptFeatureRenderer extends RenderLayer<VoidFragmentRenderState, VoidFragmentModel> {
    private static final RenderType CORRUPT_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/void_fragment_corrupt_feature.png"));

    public VoidFragmentCorruptFeatureRenderer(RenderLayerParent<VoidFragmentRenderState, VoidFragmentModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, VoidFragmentRenderState state, float yRot, float xRot) {
        if (state.voidOrb) {
            submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, CORRUPT_LAYER, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }
    }
}
