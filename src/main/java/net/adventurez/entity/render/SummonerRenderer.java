package net.adventurez.entity.render;

import net.adventurez.entity.SummonerEntity;
import net.adventurez.entity.model.SummonerModel;
import net.adventurez.entity.render.feature.SummonerEntityShieldFeatureRenderer;
import net.adventurez.entity.render.feature.SummonerEyesFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SummonerRenderer extends MobEntityRenderer<SummonerEntity, SummonerModel<SummonerEntity>> {
  private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/summoner.png");

  public SummonerRenderer(EntityRenderDispatcher entityRenderDispatcher) {
    super(entityRenderDispatcher, new SummonerModel<>(), 0.7F);
    this.addFeature(new SummonerEntityShieldFeatureRenderer(this));
    this.addFeature(new SummonerEyesFeatureRenderer(this));
  }

  @Override
  public void scale(SummonerEntity summonerEntity, MatrixStack matrixStack, float f) {
    matrixStack.scale(1.3F, 1.3F, 1.3F);
  }

  @Override
  public Identifier getTexture(SummonerEntity summonerEntity) {
    return TEXTURE;
  }
}
