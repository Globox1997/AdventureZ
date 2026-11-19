package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.entity.DragonEntity;
import net.adventurez.entity.model.DragonModel;
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
public class DragonEyesFeatureRenderer extends RenderLayer<DragonEntity, DragonModel<DragonEntity>> {
    private static final RenderType EYE_LAYER = ExtraRenderLayer.getGlowing(ResourceLocation.parse("adventurez:textures/entity/feature/dragon_eyes_feature.png"));
    private static final RenderType FRIENDLY_EYE_LAYER = ExtraRenderLayer.getGlowing(ResourceLocation.parse("adventurez:textures/entity/feature/friendly_dragon_eyes_feature.png"));
    private static final RenderType FRIENDLY_ORANGE_EYE_LAYER = ExtraRenderLayer.getGlowing(ResourceLocation.parse("adventurez:textures/entity/feature/friendly_orange_dragon_eyes_feature.png"));

    public DragonEyesFeatureRenderer(RenderLayerParent<DragonEntity, DragonModel<DragonEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, DragonEntity dragonEntity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(EYE_LAYER);
        if (dragonEntity.isTamed()) {
            if (dragonEntity.getEntityData().get(DragonEntity.OTHER_EYES)) {
                vertexConsumer = vertexConsumerProvider.getBuffer(FRIENDLY_ORANGE_EYE_LAYER);
            } else {
                vertexConsumer = vertexConsumerProvider.getBuffer(FRIENDLY_EYE_LAYER);
            }
        }
        this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
    }

}