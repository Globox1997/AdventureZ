package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.nonliving.TinyEyeEntity;
import net.adventurez.entity.model.TinyEyeModel;
import net.adventurez.entity.render.state.ProjectileRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class TinyEyeRenderer extends EntityRenderer<TinyEyeEntity, ProjectileRenderState> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/the_eye.png");
    private final TinyEyeModel model = new TinyEyeModel(TinyEyeModel.getTexturedModelData().bakeRoot());

    public TinyEyeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected int getBlockLightLevel(TinyEyeEntity entity, BlockPos blockPos) {
        return entity.level().getMaxLocalRawBrightness(blockPos);
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        matrixStack.pushPose();
        matrixStack.scale(-0.5F, -0.5F, 0.5F);
        matrixStack.translate(0.0D, -1.4D, 0.0D);
        submitNodeCollector.submitModel(this.model, state, matrixStack, this.model.renderType(TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        matrixStack.popPose();
        super.submit(state, matrixStack, submitNodeCollector, camera);
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(TinyEyeEntity entity, ProjectileRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yRot = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
        state.xRot = 0.0F;
    }
}
