package net.adventurez.entity.render.feature;

import net.adventurez.entity.StoneGolemEntity;
import net.adventurez.entity.model.StoneGolemModel;

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
public class StoneGolemLavaFeatureRenderer
    extends FeatureRenderer<StoneGolemEntity, StoneGolemModel<StoneGolemEntity>> {
  private static final RenderLayer LAVA_LAYER = RenderLayer
      .getEntityTranslucentCull(new Identifier("adventurez:textures/entity/lava_feature_golem.png"));

  public StoneGolemLavaFeatureRenderer(
      FeatureRendererContext<StoneGolemEntity, StoneGolemModel<StoneGolemEntity>> featureRendererContext) {
    super(featureRendererContext);
  }

  @Override
  public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i,
      StoneGolemEntity stoneGolemEntity, float f, float g, float h, float j, float k, float l) {
    if (!stoneGolemEntity.getDataTracker().get(StoneGolemEntity.inVulnerable)) {
      VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAVA_LAYER);
      this.getContextModel().render(matrixStack, vertexConsumer,
          stoneGolemEntity.getDataTracker().get(StoneGolemEntity.lavaTexture), OverlayTexture.DEFAULT_UV, 1.0F, 1.0F,
          1.0F, 1.0F);
    }
  }

}