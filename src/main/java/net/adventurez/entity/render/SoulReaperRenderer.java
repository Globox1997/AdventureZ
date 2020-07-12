package net.adventurez.entity.render;

import net.adventurez.entity.SoulReaperEntity;
import net.adventurez.entity.model.SoulReaperModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SoulReaperRenderer extends BipedEntityRenderer<SoulReaperEntity, SoulReaperModel<SoulReaperEntity>> {
  private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/soul_reaper.png");

  public SoulReaperRenderer(EntityRenderDispatcher entityRenderDispatcher) {
    super(entityRenderDispatcher, new SoulReaperModel<>(), 0.5F);
  }

  @Override
  public Identifier getTexture(SoulReaperEntity soulReaperEntity) {
    return TEXTURE;
  }

  @Override
  public void scale(SoulReaperEntity soulReaperEntity, MatrixStack matrixStack, float f) {
    matrixStack.scale(1.2F, 1.2F, 1.2F);
  }
}