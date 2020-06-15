package net.adventurez.entity.render;

import net.adventurez.entity.RedStoneEntity;
import net.adventurez.entity.model.RedStoneModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class RedStoneRenderer extends EntityRenderer<RedStoneEntity> {
  private static final Identifier TEXTURE = new Identifier("adventurez:textures/item/red_stone_item.png");
  private final RedStoneModel model = new RedStoneModel();

  public RedStoneRenderer(EntityRenderDispatcher entityRenderDispatcher) {
    super(entityRenderDispatcher);
  }

  @Override
  protected int getBlockLight(RedStoneEntity redStoneEntity, BlockPos blockPos) {
    return 15;
  }

  @Override
  public void render(RedStoneEntity redStoneEntity, float f, float g, MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider, int i) {

    matrixStack.push();
    VertexConsumer vertexConsumer = vertexConsumerProvider
        .getBuffer(this.model.getLayer(this.getTexture(redStoneEntity)));
    int lightAbove = WorldRenderer.getLightmapCoordinates(redStoneEntity.getEntityWorld(),
        redStoneEntity.getBlockPos().up());
    model.render(matrixStack, vertexConsumer, lightAbove, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    matrixStack.scale(1.0F, 1.0F, 1.0F);
    matrixStack.pop();
    super.render(redStoneEntity, f, g, matrixStack, vertexConsumerProvider, i);
    System.out.println("light above:" + lightAbove);
  }

  @Override
  public Identifier getTexture(RedStoneEntity redStoneEntity) {
    return TEXTURE;
  }
}
