package net.adventurez.entity.render.feature;

import net.adventurez.entity.VoidFragmentEntity;
import net.adventurez.entity.model.VoidFragmentModel;
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
public class VoidFragmentCorruptFeatureRenderer extends FeatureRenderer<VoidFragmentEntity, VoidFragmentModel<VoidFragmentEntity>> {
    private static final RenderLayer CORRUPT_LAYER = ExtraRenderLayer.getGlowing(new Identifier("adventurez:textures/entity/feature/void_fragment_corrupt_feature.png"));

    public VoidFragmentCorruptFeatureRenderer(FeatureRendererContext<VoidFragmentEntity, VoidFragmentModel<VoidFragmentEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, VoidFragmentEntity voidFragmentEntity, float f, float g, float h, float j, float k, float l) {
        if (voidFragmentEntity.getDataTracker().get(VoidFragmentEntity.IS_VOID_ORB)) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(CORRUPT_LAYER);
            this.getContextModel().render(matrixStack, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

}