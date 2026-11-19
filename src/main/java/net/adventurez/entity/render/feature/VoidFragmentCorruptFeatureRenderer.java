package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.entity.VoidFragmentEntity;
import net.adventurez.entity.model.VoidFragmentModel;
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
public class VoidFragmentCorruptFeatureRenderer extends RenderLayer<VoidFragmentEntity, VoidFragmentModel<VoidFragmentEntity>> {
    private static final RenderType CORRUPT_LAYER = ExtraRenderLayer.getGlowing(ResourceLocation.parse("adventurez:textures/entity/feature/void_fragment_corrupt_feature.png"));

    public VoidFragmentCorruptFeatureRenderer(RenderLayerParent<VoidFragmentEntity, VoidFragmentModel<VoidFragmentEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, VoidFragmentEntity voidFragmentEntity, float f, float g, float h, float j, float k, float l) {
        if (voidFragmentEntity.getEntityData().get(VoidFragmentEntity.IS_VOID_ORB)) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(CORRUPT_LAYER);
            this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
        }
    }

}