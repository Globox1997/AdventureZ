package net.adventurez.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.adventurez.block.entity.ChiseledPolishedBlackstoneHolderEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class ChiseledPolishedBlackstoneHolderRenderer implements BlockEntityRenderer<ChiseledPolishedBlackstoneHolderEntity, ChiseledPolishedBlackstoneHolderRenderer.HolderRenderState> {
    private final ItemModelResolver itemModelResolver;

    public ChiseledPolishedBlackstoneHolderRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelResolver = ctx.itemModelResolver();
    }

    @Override
    public HolderRenderState createRenderState() {
        return new HolderRenderState();
    }

    @Override
    public void extractRenderState(ChiseledPolishedBlackstoneHolderEntity blockEntity, HolderRenderState state, float tickDelta, Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickDelta, cameraPosition, breakProgress);
        state.time = blockEntity.getLevel().getGameTime() + tickDelta;
        if (!blockEntity.isEmpty()) {
            this.itemModelResolver.updateForTopItem(state.item, blockEntity.getItem(0), ItemDisplayContext.GROUND, blockEntity.getLevel(), null, (int) blockEntity.getBlockPos().asLong());
        } else {
            state.item.clear();
        }
    }

    @Override
    public void submit(HolderRenderState state, PoseStack matrices, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (!state.item.isEmpty()) {
            matrices.pushPose();
            double offset = Math.sin(state.time / 8.0D) / 4.5D;
            matrices.translate(0.5D, 1.3D + offset, 0.5D);
            matrices.rotate(Axis.YP.rotationDegrees(state.time * 4F));
            state.item.submit(matrices, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            matrices.popPose();
        }
    }

    public static class HolderRenderState extends BlockEntityRenderState {
        public float time;
        public final ItemStackRenderState item = new ItemStackRenderState();
    }
}
