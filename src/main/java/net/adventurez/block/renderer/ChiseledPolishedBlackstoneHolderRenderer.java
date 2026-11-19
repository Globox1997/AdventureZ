package net.adventurez.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.adventurez.block.entity.ChiseledPolishedBlackstoneHolderEntity;

import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ChiseledPolishedBlackstoneHolderRenderer implements BlockEntityRenderer<ChiseledPolishedBlackstoneHolderEntity> {

    public ChiseledPolishedBlackstoneHolderRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(ChiseledPolishedBlackstoneHolderEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!blockEntity.isEmpty()) {
            matrices.pushPose();
            double offset = Math.sin((blockEntity.getLevel().getGameTime() + tickDelta) / 8.0D) / 4.5D;
            matrices.translate(0.5D, 1.3D + offset, 0.5D);
            matrices.mulPose(Axis.YP.rotationDegrees((blockEntity.getLevel().getGameTime() + tickDelta) * 4F));
            Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getItem(0), ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, blockEntity.getLevel(),
                    (int) blockEntity.getBlockPos().asLong());
            matrices.popPose();
        }
    }
}