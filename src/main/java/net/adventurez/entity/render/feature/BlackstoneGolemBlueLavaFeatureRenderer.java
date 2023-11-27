package net.adventurez.entity.render.feature;

import net.adventurez.entity.BlackstoneGolemEntity;
import net.adventurez.entity.model.BlackstoneGolemModel;
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
public class BlackstoneGolemBlueLavaFeatureRenderer extends FeatureRenderer<BlackstoneGolemEntity, BlackstoneGolemModel<BlackstoneGolemEntity>> {
    private static final RenderLayer BLUE_LAVA_LAYER = ExtraRenderLayer.getGlowing(new Identifier("adventurez:textures/entity/feature/blue_lava_feature_golem.png"));

    public BlackstoneGolemBlueLavaFeatureRenderer(FeatureRendererContext<BlackstoneGolemEntity, BlackstoneGolemModel<BlackstoneGolemEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BlackstoneGolemEntity stoneGolemEntity, float f, float g, float h, float j, float k, float l) {
        if (stoneGolemEntity.getDataTracker().get(BlackstoneGolemEntity.HALF_LIFE_CHANGE)) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(BLUE_LAVA_LAYER);
            this.getContextModel().render(matrixStack, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

}