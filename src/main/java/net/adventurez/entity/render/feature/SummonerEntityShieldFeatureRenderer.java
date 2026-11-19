package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.entity.SummonerEntity;
import net.adventurez.entity.model.SummonerModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

@Environment(EnvType.CLIENT)
public class SummonerEntityShieldFeatureRenderer extends RenderLayer<SummonerEntity, SummonerModel<SummonerEntity>> {
    private static final RenderType SHIELD_LAYER = RenderType.entityGlint();

    public SummonerEntityShieldFeatureRenderer(RenderLayerParent<SummonerEntity, SummonerModel<SummonerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, SummonerEntity summonerEntity, float f, float g, float h, float j, float k, float l) {
        if (summonerEntity.getEntityData().get(SummonerEntity.INVULNERABLE_SHIELD)) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(SHIELD_LAYER);
            this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
        }
    }

}
