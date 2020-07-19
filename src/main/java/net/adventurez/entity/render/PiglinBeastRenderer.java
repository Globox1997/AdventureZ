package net.adventurez.entity.render;

import net.adventurez.entity.PiglinBeastEntity;
import net.adventurez.entity.model.PiglinBeastModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PiglinBeastRenderer extends MobEntityRenderer<PiglinBeastEntity, PiglinBeastModel<PiglinBeastEntity>> {
  private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/piglin_beast.png");

  public PiglinBeastRenderer(EntityRenderDispatcher entityRenderDispatcher) {
    super(entityRenderDispatcher, new PiglinBeastModel<>(), 1F);
  }

  @Override
  public void scale(PiglinBeastEntity beast, MatrixStack matrixStack, float f) {
    matrixStack.scale(1.1F, 1.1F, 1.1F);
  }

  @Override
  public Identifier getTexture(PiglinBeastEntity piglinBeast) {
    return TEXTURE;
  }
}
