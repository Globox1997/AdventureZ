package net.adventurez.entity.render.feature;

import net.adventurez.entity.EnderWhaleEntity;
import net.adventurez.entity.model.EnderWhaleModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EnderWhaleGlowFeatureRenderer extends FeatureRenderer<EnderWhaleEntity, EnderWhaleModel<EnderWhaleEntity>> {
    private static final RenderLayer GLOW_LAYER = ExtraRenderLayer.getGlowing(new Identifier("adventurez:textures/entity/feature/ender_whale_glow_feature.png"));

    public EnderWhaleGlowFeatureRenderer(FeatureRendererContext<EnderWhaleEntity, EnderWhaleModel<EnderWhaleEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, EnderWhaleEntity enderWhaleEntity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(GLOW_LAYER);
        this.getContextModel().render(matrixStack, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }

}