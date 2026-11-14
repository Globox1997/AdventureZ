package net.adventurez.block.renderer;

import net.adventurez.AdventureMain;
import net.adventurez.block.PiglinFlag;
import net.adventurez.block.entity.PiglinFlagEntity;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
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

    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/block/piglin_flag.png");
    private final ModelPart flag;
    private final ModelPart cloth;

    public PiglinFlagRenderer(BlockEntityRendererFactory.Context ctx) {
        this.flag = ctx.getLayerModelPart(RenderInit.PIGLIN_FLAG_LAYER).getChild("flag");
        this.cloth = this.flag.getChild("cloth");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData flag = modelPartData.addChild("flag", ModelPartBuilder.create().uv(28, 25).cuboid(-4.0F, 7.0F, 0.0F, 4.0F, 10.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-4.0F, -15.0F, 0.0F, 4.0F, 5.0F, 20.0F, new Dilation(0.0F))
                .uv(28, 39).cuboid(-3.0F, -10.0F, 1.0F, 2.0F, 17.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 9.0F, 2.0F, 3.1416F, 0.0F, 0.0F));

        ModelPartData cloth = flag.addChild("cloth", ModelPartBuilder.create().uv(0, 25).cuboid(0.0F, 0.0F, -7.0F, 0.0F, 24.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -10.0F, 12.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(PiglinFlagEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        matrices.push();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));
        BlockState state = entity.getCachedState();
        Direction blockDirection = state.get(HorizontalFacingBlock.FACING);
        if (state.getBlock() instanceof PiglinFlag) {
            if (blockDirection.equals(Direction.NORTH)) {
            } else if (blockDirection.equals(Direction.EAST)) {
                matrices.translate(1D, 0D, 0D);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270F));
            } else if (blockDirection.equals(Direction.SOUTH)) {
                matrices.translate(1D, 0D, 1D);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180F));
            } else if (blockDirection.equals(Direction.WEST)) {
                matrices.translate(0D, 0D, 1D);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90F));
            }
        }
        matrices.translate(0.5D, 0.5D, 0.5D);
        BlockPos blockPos = entity.getPos();
        long worldTime = entity.getWorld().getTime();
        float goodFloat = ((float) Math.floorMod((long) (blockPos.getX() * 7 + blockPos.getY() * 9 + blockPos.getZ() * 13) + worldTime, 100L) + tickDelta) / 100.0F;
        this.cloth.roll = MathHelper.cos(6.2831855F * goodFloat) / 4;
        this.flag.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
    }
}
