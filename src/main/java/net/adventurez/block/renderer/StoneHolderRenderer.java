package net.adventurez.block.renderer;

import net.adventurez.block.entity.StoneHolderEntity;
import net.adventurez.entity.RedStoneEntity;
import net.adventurez.init.EntityInit;
import net.adventurez.network.EntitySpawnPacket;
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
    // this.redStoneEntity = new RedStoneEntity(blockEntity.getWorld(),
    // blockEntity.getPos().getX(),
    // blockEntity.getPos().getY(), blockEntity.getPos().getZ());
    if (!blockEntity.isEmpty()) {
      // if (blockDirection == Direction.NORTH) {
      matrices.push();
      double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0D) / 4.0D;
      matrices.translate(0.5D, 1.3D + offset, 0.5D);
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4F));
      int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
      // MinecraftClient.getInstance().getEntityRenderManager().render(redStoneEntity,
      // blockEntity.getPos().getX(),
      // blockEntity.getPos().getY() + 1.2D, blockEntity.getPos().getZ(), 1F,
      // tickDelta, matrices, vertexConsumers,
      // light);
      MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getStack(0),
          ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers);
      matrices.pop();
      // }
    }
  }
}