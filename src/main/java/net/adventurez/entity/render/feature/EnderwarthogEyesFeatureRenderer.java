package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.entity.EnderwarthogEntity;
import net.adventurez.entity.model.EnderwarthogModel;
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
public class EnderwarthogEyesFeatureRenderer extends RenderLayer<EnderwarthogEntity, EnderwarthogModel<EnderwarthogEntity>> {
    private static final RenderType EYE_LAYER = ExtraRenderLayer.getGlowing(ResourceLocation.parse("adventurez:textures/entity/feature/enderwarthog_eyes_feature.png"));
    private static final RenderType RARE_LAYER = RenderType.entityCutoutNoCull(ResourceLocation.parse("adventurez:textures/entity/feature/rare_enderwarthog.png"));

    public EnderwarthogEyesFeatureRenderer(RenderLayerParent<EnderwarthogEntity, EnderwarthogModel<EnderwarthogEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, EnderwarthogEntity enderwarthogEntity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(EYE_LAYER);
        this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
        if (enderwarthogEntity.getEntityData().get(EnderwarthogEntity.RARE_VARIANT)) {
            VertexConsumer otherVertexConsumer = vertexConsumerProvider.getBuffer(RARE_LAYER);
            this.getParentModel().renderToBuffer(matrixStack, otherVertexConsumer, 20, OverlayTexture.NO_OVERLAY);
        }
    }

}