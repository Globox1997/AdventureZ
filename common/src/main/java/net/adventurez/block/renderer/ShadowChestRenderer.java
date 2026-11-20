package net.adventurez.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.adventurez.block.ShadowChest;
import net.adventurez.block.entity.ShadowChestEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.chest.ChestModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class ShadowChestRenderer implements BlockEntityRenderer<ShadowChestEntity, ChestRenderState> {
    private static final SpriteId SHADOW_CHEST = Sheets.CHEST_MAPPER.defaultNamespaceApply("shadow_chest");
    private final SpriteGetter sprites;
    private final ChestModel model;

    public ShadowChestRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
        this.model = new ChestModel(context.bakeLayer(ModelLayers.CHEST));
    }

    @Override
    public ChestRenderState createRenderState() {
        return new ChestRenderState();
    }

    @Override
    public void extractRenderState(ShadowChestEntity blockEntity, ChestRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.type = ChestType.SINGLE;
        state.facing = blockEntity.getLevel() != null ? blockEntity.getBlockState().getValue(ShadowChest.FACING) : Direction.SOUTH;
        state.open = blockEntity.getOpenNess(partialTicks);
    }

    @Override
    public void submit(ChestRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.mulPose(ChestRenderer.modelTransformation(state.facing));
        float open = state.open;
        open = 1.0F - open;
        open = 1.0F - open * open * open;
        submitNodeCollector.submitModel(this.model, open, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, SHADOW_CHEST, this.sprites, 0);
        if (state.breakProgress != null) {
            submitNodeCollector.order(1).submitCrumblingOverlay(this.model, open, poseStack, SHADOW_CHEST.renderType(this.model.renderType()), state.lightCoords, OverlayTexture.NO_OVERLAY, -1,
                    state.breakProgress);
        }
        poseStack.popPose();
    }
}
