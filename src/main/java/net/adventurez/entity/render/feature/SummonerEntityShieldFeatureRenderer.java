package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.SummonerModel;
import net.adventurez.entity.render.state.SummonerRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;

@Environment(EnvType.CLIENT)
public class SummonerEntityShieldFeatureRenderer extends RenderLayer<SummonerRenderState, SummonerModel> {
    private static final RenderType SHIELD_LAYER = RenderTypes.trimmedArmorGlint();

    public SummonerEntityShieldFeatureRenderer(RenderLayerParent<SummonerRenderState, SummonerModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, SummonerRenderState state, float yRot, float xRot) {
        if (state.invulnerableShield) {
            submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, SHIELD_LAYER, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }
    }
}
