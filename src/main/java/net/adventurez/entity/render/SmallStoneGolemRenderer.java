package net.adventurez.entity.render;

import net.adventurez.entity.SmallStoneGolemEntity;
import net.adventurez.entity.model.SmallStoneGolemModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SmallStoneGolemRenderer extends MobEntityRenderer<SmallStoneGolemEntity, SmallStoneGolemModel<SmallStoneGolemEntity>> {
  private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/stone_golem.png");

  public SmallStoneGolemRenderer(EntityRenderDispatcher entityRenderDispatcher) {
    super(entityRenderDispatcher, new SmallStoneGolemModel<>(), 0.7F);
  }

  protected void scale(SmallStoneGolemEntity stone, MatrixStack matrixStack, float f) {
    matrixStack.scale(1.0F, 1.0F, 1.0F);
  }

  @Override
  public Identifier getTexture(SmallStoneGolemEntity stoneGolem) {
    return TEXTURE;
  }
}
