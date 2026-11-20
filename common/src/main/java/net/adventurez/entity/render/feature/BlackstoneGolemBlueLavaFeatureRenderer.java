package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.BlackstoneGolemModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.entity.render.state.BlackstoneGolemRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class BlackstoneGolemBlueLavaFeatureRenderer extends RenderLayer<BlackstoneGolemRenderState, BlackstoneGolemModel> {
    private static final RenderType BLUE_LAVA_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/blue_lava_feature_golem.png"));

    public BlackstoneGolemBlueLavaFeatureRenderer(RenderLayerParent<BlackstoneGolemRenderState, BlackstoneGolemModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, BlackstoneGolemRenderState state, float yRot, float xRot) {
        if (state.halfLifeChange) {
            submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, BLUE_LAVA_LAYER, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }
    }
}
