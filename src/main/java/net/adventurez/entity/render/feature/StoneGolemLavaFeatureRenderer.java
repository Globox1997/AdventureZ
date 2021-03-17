package net.adventurez.entity.render.feature;

import net.adventurez.entity.StoneGolemEntity;
import net.adventurez.entity.model.StoneGolemModel;
import net.adventurez.entity.render.feature.layer.ExtraRenderLayer;
import net.adventurez.entity.render.feature.layer.FadingRenderLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class StoneGolemLavaFeatureRenderer
    extends FeatureRenderer<StoneGolemEntity, StoneGolemModel<StoneGolemEntity>> {
  private static final RenderLayer LAVA_LAYER = ExtraRenderLayer
      .getGlowing("adventurez:textures/entity/feature/lava_feature_golem.png");
  private static final RenderLayer FLOWING_LAVA_LAYER = FadingRenderLayer
      .getFading("adventurez:textures/entity/feature/lava_feature_golem.png");

  public StoneGolemLavaFeatureRenderer(
      FeatureRendererContext<StoneGolemEntity, StoneGolemModel<StoneGolemEntity>> featureRendererContext) {
    super(featureRendererContext);
  }

  @Override
  public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i,
      StoneGolemEntity stoneGolemEntity, float f, float g, float h, float j, float k, float l) {
    if (!stoneGolemEntity.getDataTracker().get(StoneGolemEntity.HALF_LIFE_CHANGE)) {
      int lavaFlow = stoneGolemEntity.getDataTracker().get(StoneGolemEntity.LAVA_TEXTURE);
      VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAVA_LAYER);
      FabricLoader loader = FabricLoader.getInstance();
      if (lavaFlow < 240 && !loader.isModLoaded("canvas") && !loader.isModLoaded("sodium")) {
        vertexConsumer = vertexConsumerProvider.getBuffer(FLOWING_LAVA_LAYER);
      } else {
        lavaFlow = Integer.MAX_VALUE;
      }
      this.getContextModel().render(matrixStack, vertexConsumer, lavaFlow, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F,
          1.0F);
    }
  }

}

// Works
// private static final RenderLayer BLUE_LAVA_LAYER = RenderLayer
// .getEntityTranslucentCull(new
// Identifier("adventurez:textures/entity/feature/blue_lava_feature_golem.png"));