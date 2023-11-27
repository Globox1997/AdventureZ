package net.adventurez.block.renderer;

import net.adventurez.block.PiglinFlag;
import net.adventurez.block.entity.PiglinFlagEntity;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class PiglinFlagRenderer implements BlockEntityRenderer<PiglinFlagEntity> {
    private final ModelPart flag;
    private final ModelPart holder;

    public PiglinFlagRenderer(BlockEntityRendererFactory.Context ctx) {
        this.holder = ctx.getLayerModelPart(RenderInit.PIGLIN_FLAG_LAYER).getChild("holder");
        this.flag = holder.getChild("flag");
    }

    public PiglinFlagRenderer(ModelPart root) {
        this.holder = root.getChild("holder");
        this.flag = holder.getChild("flag");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData modelPartData1 = modelPartData.addChild("holder", ModelPartBuilder.create().uv(0, 42).cuboid(-2.0F, -11.0F, -2.0F, 4.0F, 11.0F, 4.0F).uv(10, 20)
                .cuboid(-2.0F, -44.0F, -2.0F, 4.0F, 4.0F, 18.0F).uv(36, 0).cuboid(-1.0F, -40.0F, -1.0F, 2.0F, 29.0F, 2.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData1.addChild("flag", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, -6.6F, 0.0F, 24.0F, 14.0F), ModelTransform.pivot(0.0F, -40.0F, 8.0F));
        return TexturedModelData.of(modelData, 64, 64);

    }

    // Light rendering bug
    @Override
    public void render(PiglinFlagEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        matrices.push();
        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
        // getEntityAlpha works but will always lighten up the flag
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(new Identifier("adventurez:textures/block/piglin_flag.png")));
        BlockState state = entity.getCachedState();
        Direction blockDirection = state.get(HorizontalFacingBlock.FACING);
        if (state.getBlock() instanceof PiglinFlag) {

            if (blockDirection.equals(Direction.NORTH)) {
                matrices.translate(1D, 0D, 1D);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180F));
            } else if (blockDirection.equals(Direction.EAST)) {
                matrices.translate(0D, 0D, 1D);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90F));
            } else if (blockDirection.equals(Direction.SOUTH)) {
            } else if (blockDirection.equals(Direction.WEST)) {
                matrices.translate(1D, 0D, 0D);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270F));
            }
        }
        matrices.scale(1.0F, -1.0F, 1.0F);
        matrices.translate(0.5D, -1.5D, 0.5D);
        BlockPos blockPos = entity.getPos();
        long worldTime = entity.getWorld().getTime();
        float goodFloat = ((float) Math.floorMod((long) (blockPos.getX() * 7 + blockPos.getY() * 9 + blockPos.getZ() * 13) + worldTime, 100L) + tickDelta) / 100.0F;
        flag.roll = MathHelper.cos(6.2831855F * goodFloat) / 4;
        holder.render(matrices, vertexConsumer, lightAbove, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();

    }
}