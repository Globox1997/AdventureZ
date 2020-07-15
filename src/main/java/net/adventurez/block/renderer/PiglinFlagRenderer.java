package net.adventurez.block.renderer;

import net.adventurez.block.PiglinFlagBlock;
import net.adventurez.block.entity.PiglinFlagEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class PiglinFlagRenderer extends BlockEntityRenderer<PiglinFlagEntity> {
  private final ModelPart flag;
  private final ModelPart holder;

  public PiglinFlagRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
    super(blockEntityRenderDispatcher);
    holder = new ModelPart(64, 64, 0, 0);
    holder.setPivot(0.0F, 24.0F, 0.0F);
    holder.setTextureOffset(0, 42).addCuboid(-2.0F, -11.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F);
    holder.setTextureOffset(10, 20).addCuboid(-2.0F, -44.0F, -2.0F, 4.0F, 4.0F, 18.0F, 0.0F);
    holder.setTextureOffset(36, 0).addCuboid(-1.0F, -40.0F, -1.0F, 2.0F, 29.0F, 2.0F, 0.0F);
    flag = new ModelPart(64, 64, 0, 0);
    flag.setPivot(0.0F, -40.0F, 8.0F);
    holder.addChild(flag);
    flag.setTextureOffset(0, 0).addCuboid(0.0F, 0.0F, -6.6F, 0.0F, 24.0F, 14.0F, 0.0F);
  }

  @Override
  public boolean rendersOutsideBoundingBox(PiglinFlagEntity blockEntity) {
    return true;
  }

  @Override
  public void render(PiglinFlagEntity entity, float tickDelta, MatrixStack matrices,
      VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
    matrices.push();
    int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
    VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(
        RenderLayer.getEntityCutoutNoCull(new Identifier("adventurez:textures/block/piglin_flag_block.png")));
    BlockState state = entity.getCachedState();
    Direction blockDirection = state.get(HorizontalFacingBlock.FACING);
    if (state.getBlock() instanceof PiglinFlagBlock) {

      if (blockDirection.equals(Direction.NORTH)) {
        matrices.translate(1D, 0D, 1D);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180F));
      } else if (blockDirection.equals(Direction.EAST)) {
        matrices.translate(0D, 0D, 1D);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90F));
      } else if (blockDirection.equals(Direction.SOUTH)) {
      } else if (blockDirection.equals(Direction.WEST)) {
        matrices.translate(1D, 0D, 0D);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270F));
      }
    }
    matrices.scale(1.0F, -1.0F, 1.0F);
    matrices.translate(0.5D, -1.5D, 0.5D);
    BlockPos blockPos = entity.getPos();
    long worldTime = entity.getWorld().getTime();
    float goodFloat = ((float) Math
        .floorMod((long) (blockPos.getX() * 7 + blockPos.getY() * 9 + blockPos.getZ() * 13) + worldTime, 100L)
        + tickDelta) / 100.0F;
    flag.roll = MathHelper.cos(6.2831855F * goodFloat) / 4;
    holder.render(matrices, vertexConsumer, lightAbove, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    matrices.pop();

  }
}