package net.adventurez.entity.render.feature;

import net.adventurez.entity.VoidShadowEntity;
import net.adventurez.entity.model.VoidShadowModel;
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
public class VoidShadowBlackFeatureRenderer
        extends FeatureRenderer<VoidShadowEntity, VoidShadowModel<VoidShadowEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/black_void_shadow.png");
    private static final RenderLayer BLACK_LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);

    public VoidShadowBlackFeatureRenderer(
            FeatureRendererContext<VoidShadowEntity, VoidShadowModel<VoidShadowEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i,
            VoidShadowEntity voidShadowEntity, float f, float g, float h, float j, float k, float l) {
        if (voidShadowEntity.getDataTracker().get(VoidShadowEntity.HALF_LIFE_CHANGE)) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(BLACK_LAYER);
            this.getContextModel().render(matrixStack, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F,
                    1.0F, 1.0F);
        }
    }

}