package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.entity.BlackstoneGolemEntity;
import net.adventurez.entity.model.BlackstoneGolemModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class BlackstoneGolemBlueLavaFeatureRenderer extends RenderLayer<BlackstoneGolemEntity, BlackstoneGolemModel<BlackstoneGolemEntity>> {
    private static final RenderType BLUE_LAVA_LAYER = ExtraRenderLayer.getGlowing(ResourceLocation.parse("adventurez:textures/entity/feature/blue_lava_feature_golem.png"));

    public BlackstoneGolemBlueLavaFeatureRenderer(RenderLayerParent<BlackstoneGolemEntity, BlackstoneGolemModel<BlackstoneGolemEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, BlackstoneGolemEntity stoneGolemEntity, float f, float g, float h, float j, float k, float l) {
        if (stoneGolemEntity.getEntityData().get(BlackstoneGolemEntity.HALF_LIFE_CHANGE)) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(BLUE_LAVA_LAYER);
            this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
        }
    }

}