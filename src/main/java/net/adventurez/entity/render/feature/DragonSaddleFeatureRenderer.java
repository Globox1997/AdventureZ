package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.entity.model.DragonModel;
import net.adventurez.entity.render.state.DragonRenderState;
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
public class DragonSaddleFeatureRenderer extends RenderLayer<DragonRenderState, DragonModel> {
    private static final RenderType SADDLE_LAYER = RenderTypes.entityCutout(Identifier.parse("adventurez:textures/entity/feature/dragon_saddle.png"));

    public DragonSaddleFeatureRenderer(RenderLayerParent<DragonRenderState, DragonModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, DragonRenderState state, float yRot, float xRot) {
        if (state.hasSaddle) {
            submitNodeCollector.order(1).submitModel(this.getParentModel(), state, matrixStack, SADDLE_LAYER, i, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }
    }
}
