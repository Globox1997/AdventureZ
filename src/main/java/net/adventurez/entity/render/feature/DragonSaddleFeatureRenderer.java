package net.adventurez.entity.render.feature;

import net.adventurez.entity.DragonEntity;
import net.adventurez.entity.model.DragonModel;
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
public class DragonSaddleFeatureRenderer extends FeatureRenderer<DragonEntity, DragonModel<DragonEntity>> {
    private static final RenderLayer SADDLE_LAYER = RenderLayer.getEntityCutoutNoCull(new Identifier("adventurez:textures/entity/feature/dragon_saddle.png"));

    public DragonSaddleFeatureRenderer(FeatureRendererContext<DragonEntity, DragonModel<DragonEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, DragonEntity dragonEntity, float limbAngle, float limbDistance, float tickDelta,
            float animationProgress, float headYaw, float headPitch) {
        if (dragonEntity.getDataTracker().get(DragonEntity.HAS_SADDLE)) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(SADDLE_LAYER);
            this.getContextModel().render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

}