package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.entity.BlackstoneGolemEntity;
import net.adventurez.entity.model.BlackstoneGolemModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class StoneGolemLavaFeatureRenderer extends RenderLayer<BlackstoneGolemEntity, BlackstoneGolemModel<BlackstoneGolemEntity>> {
    private static final RenderType LAVA_LAYER = ExtraRenderLayer.getGlowing(ResourceLocation.parse("adventurez:textures/entity/feature/lava_feature_golem.png"));
    private static final RenderType FLOWING_LAVA_LAYER = RenderType.entityCutoutNoCull(ResourceLocation.parse("adventurez:textures/entity/feature/lava_feature_golem.png"));

    public StoneGolemLavaFeatureRenderer(RenderLayerParent<BlackstoneGolemEntity, BlackstoneGolemModel<BlackstoneGolemEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, BlackstoneGolemEntity stoneGolemEntity, float f, float g, float h, float j, float k, float l) {
        if (!stoneGolemEntity.getEntityData().get(BlackstoneGolemEntity.HALF_LIFE_CHANGE)) {
            int lavaFlow = stoneGolemEntity.getEntityData().get(BlackstoneGolemEntity.LAVA_TEXTURE);
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAVA_LAYER);
            if (lavaFlow < 240 && !RenderInit.isCanvasLoaded && !RenderInit.isSodiumLoaded) {
                vertexConsumer = vertexConsumerProvider.getBuffer(FLOWING_LAVA_LAYER);
            } else {
                lavaFlow = Integer.MAX_VALUE;
            }
            this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, lavaFlow, OverlayTexture.NO_OVERLAY);
        }
    }

}