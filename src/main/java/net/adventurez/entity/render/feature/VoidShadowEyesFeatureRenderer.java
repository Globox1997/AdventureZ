package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.entity.VoidShadowEntity;
import net.adventurez.entity.model.VoidShadowModel;
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
public class VoidShadowEyesFeatureRenderer extends RenderLayer<VoidShadowEntity, VoidShadowModel<VoidShadowEntity>> {
    private static final RenderType EYE_LAYER = ExtraRenderLayer.getGlowing(ResourceLocation.parse("adventurez:textures/entity/feature/void_shadow_eyes_feature.png"));

    public VoidShadowEyesFeatureRenderer(RenderLayerParent<VoidShadowEntity, VoidShadowModel<VoidShadowEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, VoidShadowEntity voidShadowEntity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(EYE_LAYER);
        this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
    }

}