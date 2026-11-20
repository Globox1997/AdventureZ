package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.nonliving.AmethystShardEntity;
import net.adventurez.entity.model.AmethystShardModel;
import net.adventurez.entity.render.state.ProjectileRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class AmethystShardRenderer extends EntityRenderer<AmethystShardEntity, ProjectileRenderState> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/amethyst_shard.png");
    private final AmethystShardModel model = new AmethystShardModel(AmethystShardModel.getTexturedModelData().bakeRoot());

    public AmethystShardRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected int getBlockLightLevel(AmethystShardEntity entity, BlockPos blockPos) {
        return entity.level().getMaxLocalRawBrightness(blockPos);
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        matrixStack.pushPose();
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.translate(0.0D, -1.55D, 0.0D);
        submitNodeCollector.submitModel(this.model, state, matrixStack, this.model.renderType(TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        matrixStack.popPose();
        super.submit(state, matrixStack, submitNodeCollector, camera);
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(AmethystShardEntity entity, ProjectileRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yRot = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
        state.xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
    }
}
