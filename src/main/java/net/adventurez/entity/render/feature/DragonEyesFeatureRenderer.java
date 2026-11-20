package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.DragonModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.entity.render.state.DragonRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class DragonEyesFeatureRenderer extends RenderLayer<DragonRenderState, DragonModel> {
    private static final RenderType EYE_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/dragon_eyes_feature.png"));
    private static final RenderType FRIENDLY_EYE_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/friendly_dragon_eyes_feature.png"));
    private static final RenderType FRIENDLY_ORANGE_EYE_LAYER = ExtraRenderLayer.getGlowing(Identifier.parse("adventurez:textures/entity/feature/friendly_orange_dragon_eyes_feature.png"));

    public DragonEyesFeatureRenderer(RenderLayerParent<DragonRenderState, DragonModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, DragonRenderState state, float yRot, float xRot) {
        RenderType renderType = EYE_LAYER;
        if (state.tamed) {
            if (state.otherEyes) {
                renderType = FRIENDLY_ORANGE_EYE_LAYER;
            } else {
                renderType = FRIENDLY_EYE_LAYER;
            }
        }
        submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, renderType, 15728640, OverlayTexture.NO_OVERLAY, state.outlineColor);
    }
}
