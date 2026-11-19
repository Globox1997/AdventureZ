package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.AdventureMain;
import net.adventurez.entity.model.TinyEyeModel;
import net.adventurez.entity.nonliving.TinyEyeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class TinyEyeRenderer extends EntityRenderer<TinyEyeEntity> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/the_eye.png");
    private final TinyEyeModel<TinyEyeEntity> model = new TinyEyeModel<>(TinyEyeModel.getTexturedModelData().bakeRoot());

    public TinyEyeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public int getBlockLightLevel(TinyEyeEntity tinyEyeEntity, BlockPos blockPos) {
        return tinyEyeEntity.level().getMaxLocalRawBrightness(blockPos);
    }

    @Override
    public void render(TinyEyeEntity tinyEyeEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        float h = Mth.rotLerp(g, tinyEyeEntity.yRotO, tinyEyeEntity.getYRot());
        matrixStack.scale(-0.5F, -0.5F, 0.5F);
        matrixStack.translate(0.0D, -1.4D, 0.0D);
        this.model.setupAnim(tinyEyeEntity, 0.0F, 0.0F, 0.0F, h, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
        super.render(tinyEyeEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(TinyEyeEntity eye) {
        return TEXTURE;
    }

}
