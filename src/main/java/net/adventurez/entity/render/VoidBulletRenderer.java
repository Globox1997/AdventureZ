package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.AdventureMain;
import net.adventurez.entity.model.VoidBulletModel;
import net.adventurez.entity.nonliving.VoidBulletEntity;
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
public class VoidBulletRenderer extends EntityRenderer<VoidBulletEntity> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/void_bullet.png");
    private final VoidBulletModel<VoidBulletEntity> model = new VoidBulletModel<>(VoidBulletModel.getTexturedModelData().bakeRoot());

    public VoidBulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public int getBlockLightLevel(VoidBulletEntity voidBulletEntity, BlockPos blockPos) {
        return voidBulletEntity.level().getMaxLocalRawBrightness(blockPos);
    }

    @Override
    public void render(VoidBulletEntity voidBulletEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        float h = Mth.rotLerp(g, voidBulletEntity.yRotO, voidBulletEntity.getYRot());
        float j = Mth.lerp(g, voidBulletEntity.xRotO, voidBulletEntity.getXRot());
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.translate(0.0D, -1.55D, 0.0D);
        this.model.setupAnim(voidBulletEntity, 0.0F, 0.0F, 0.0F, h, j);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
        super.render(voidBulletEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(VoidBulletEntity voidBulletEntity) {
        return TEXTURE;
    }

}
