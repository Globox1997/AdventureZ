package net.adventurez.entity.render.feature;

import net.adventurez.entity.EnderwarthogEntity;
import net.adventurez.entity.model.EnderwarthogModel;
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
public class EnderwarthogEyesFeatureRenderer extends FeatureRenderer<EnderwarthogEntity, EnderwarthogModel<EnderwarthogEntity>> {
    private static final RenderLayer EYE_LAYER = ExtraRenderLayer.getGlowing("adventurez:textures/entity/feature/enderwarthog_eyes_feature.png");
    private static final RenderLayer RARE_LAYER = RenderLayer.getEntityCutoutNoCull(new Identifier("adventurez:textures/entity/feature/rare_enderwarthog.png"));

    public EnderwarthogEyesFeatureRenderer(FeatureRendererContext<EnderwarthogEntity, EnderwarthogModel<EnderwarthogEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, EnderwarthogEntity enderwarthogEntity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(EYE_LAYER);
        this.getContextModel().render(matrixStack, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        if (enderwarthogEntity.getDataTracker().get(EnderwarthogEntity.RARE_VARIANT)) {
            VertexConsumer otherVertexConsumer = vertexConsumerProvider.getBuffer(RARE_LAYER);
            this.getContextModel().render(matrixStack, otherVertexConsumer, 20, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

}