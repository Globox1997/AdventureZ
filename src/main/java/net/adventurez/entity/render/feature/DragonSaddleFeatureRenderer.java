package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.entity.DragonEntity;
import net.adventurez.entity.model.DragonModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class DragonSaddleFeatureRenderer extends RenderLayer<DragonEntity, DragonModel<DragonEntity>> {
    private static final RenderType SADDLE_LAYER = RenderType.entityCutoutNoCull(ResourceLocation.parse("adventurez:textures/entity/feature/dragon_saddle.png"));

    public DragonSaddleFeatureRenderer(RenderLayerParent<DragonEntity, DragonModel<DragonEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light, DragonEntity dragonEntity, float limbAngle, float limbDistance, float tickDelta,
            float animationProgress, float headYaw, float headPitch) {
        if (dragonEntity.getEntityData().get(DragonEntity.HAS_SADDLE)) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(SADDLE_LAYER);
            this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
    }

}