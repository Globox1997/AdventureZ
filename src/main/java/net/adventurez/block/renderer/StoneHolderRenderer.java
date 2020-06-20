package net.adventurez.block.renderer;

import net.adventurez.block.entity.StoneHolderEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;

public class StoneHolderRenderer extends BlockEntityRenderer<StoneHolderEntity> {

  public StoneHolderRenderer(BlockEntityRenderDispatcher dispatcher) {
    super(dispatcher);
  }

  @Override
  public void render(StoneHolderEntity blockEntity, float tickDelta, MatrixStack matrices,
      VertexConsumerProvider vertexConsumers, int light, int overlay) {
    if (!blockEntity.isEmpty()) {
      matrices.push();
      double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0D) / 4.5D;
      matrices.translate(0.5D, 1.3D + offset, 0.5D);
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4F));
      int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
      MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getStack(0),
          ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers);
      matrices.pop();
    }
  }
}