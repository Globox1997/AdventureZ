package net.adventurez.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.adventurez.AdventureMain;
import net.adventurez.block.PiglinFlag;
import net.adventurez.block.entity.PiglinFlagEntity;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class PiglinFlagRenderer implements BlockEntityRenderer<PiglinFlagEntity> {

    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/block/piglin_flag.png");
    private final ModelPart flag;
    private final ModelPart cloth;

    public PiglinFlagRenderer(BlockEntityRendererProvider.Context ctx) {
        this.flag = ctx.bakeLayer(RenderInit.PIGLIN_FLAG_LAYER).getChild("flag");
        this.cloth = this.flag.getChild("cloth");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition flag = modelPartData.addOrReplaceChild("flag", CubeListBuilder.create().texOffs(28, 25).addBox(-4.0F, 7.0F, 0.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -15.0F, 0.0F, 4.0F, 5.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(28, 39).addBox(-3.0F, -10.0F, 1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 9.0F, 2.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cloth = flag.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, 0.0F, -7.0F, 0.0F, 24.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 12.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void render(PiglinFlagEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumerProvider, int light, int overlay) {
        matrices.pushPose();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        BlockState state = entity.getBlockState();
        Direction blockDirection = state.getValue(HorizontalDirectionalBlock.FACING);
        if (state.getBlock() instanceof PiglinFlag) {
            if (blockDirection.equals(Direction.NORTH)) {
            } else if (blockDirection.equals(Direction.EAST)) {
                matrices.translate(1D, 0D, 0D);
                matrices.mulPose(Axis.YP.rotationDegrees(270F));
            } else if (blockDirection.equals(Direction.SOUTH)) {
                matrices.translate(1D, 0D, 1D);
                matrices.mulPose(Axis.YP.rotationDegrees(180F));
            } else if (blockDirection.equals(Direction.WEST)) {
                matrices.translate(0D, 0D, 1D);
                matrices.mulPose(Axis.YP.rotationDegrees(90F));
            }
        }
        matrices.translate(0.5D, 0.5D, 0.5D);
        BlockPos blockPos = entity.getBlockPos();
        long worldTime = entity.getLevel().getGameTime();
        float goodFloat = ((float) Math.floorMod((long) (blockPos.getX() * 7 + blockPos.getY() * 9 + blockPos.getZ() * 13) + worldTime, 100L) + tickDelta) / 100.0F;
        this.cloth.zRot = Mth.cos(6.2831855F * goodFloat) / 4;
        this.flag.render(matrices, vertexConsumer, light, overlay);
        matrices.popPose();
    }
}
