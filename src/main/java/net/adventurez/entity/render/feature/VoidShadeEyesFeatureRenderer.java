package net.adventurez.entity.render.feature;

import net.adventurez.entity.VoidShadeEntity;
import net.adventurez.entity.model.VoidShadeModel;
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

@Environment(EnvType.CLIENT)
public class VoidShadeEyesFeatureRenderer extends FeatureRenderer<VoidShadeEntity, VoidShadeModel<VoidShadeEntity>> {
    private static final RenderLayer EYE_LAYER = ExtraRenderLayer.getGlowing("adventurez:textures/entity/feature/void_shade_eyes_feature.png");

    public VoidShadeEyesFeatureRenderer(FeatureRendererContext<VoidShadeEntity, VoidShadeModel<VoidShadeEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, VoidShadeEntity voidShadeEntity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(EYE_LAYER);
        this.getContextModel().render(matrixStack, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }

}