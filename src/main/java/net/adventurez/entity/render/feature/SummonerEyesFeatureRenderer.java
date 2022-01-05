package net.adventurez.entity.render.feature;

import net.adventurez.entity.SummonerEntity;
import net.adventurez.entity.model.SummonerModel;
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
public class SummonerEyesFeatureRenderer extends FeatureRenderer<SummonerEntity, SummonerModel<SummonerEntity>> {
    private static final RenderLayer EYE_LAYER = ExtraRenderLayer.getGlowing(new Identifier("adventurez:textures/entity/feature/summoner_eyes_feature.png"));

    public SummonerEyesFeatureRenderer(FeatureRendererContext<SummonerEntity, SummonerModel<SummonerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SummonerEntity summonerEntity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(EYE_LAYER);
        this.getContextModel().render(matrixStack, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }

}