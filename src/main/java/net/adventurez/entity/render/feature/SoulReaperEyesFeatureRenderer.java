package net.adventurez.entity.render.feature;

import net.adventurez.entity.SoulReaperEntity;
import net.adventurez.entity.model.SoulReaperModel;
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
public class SoulReaperEyesFeatureRenderer
    extends FeatureRenderer<SoulReaperEntity, SoulReaperModel<SoulReaperEntity>> {
  private static final RenderLayer EYE_LAYER = RenderLayer
      .getEntityTranslucentCull(new Identifier("adventurez:textures/entity/soul_reaper_eyes_feature.png"));

  public SoulReaperEyesFeatureRenderer(
      FeatureRendererContext<SoulReaperEntity, SoulReaperModel<SoulReaperEntity>> featureRendererContext) {
    super(featureRendererContext);
  }

  @Override
  public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i,
      SoulReaperEntity soulReaperEntity, float f, float g, float h, float j, float k, float l) {
    VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(EYE_LAYER);
    this.getContextModel().render(matrixStack, vertexConsumer, 400, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
  }

}