package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.AdventureMain;
import net.adventurez.entity.model.AmethystShardModel;
import net.adventurez.entity.nonliving.AmethystShardEntity;
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
public class AmethystShardRenderer extends EntityRenderer<AmethystShardEntity> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/amethyst_shard.png");
    private final AmethystShardModel<AmethystShardEntity> model = new AmethystShardModel<>(AmethystShardModel.getTexturedModelData().bakeRoot());

    public AmethystShardRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public int getBlockLightLevel(AmethystShardEntity amethystShardEntity, BlockPos blockPos) {
        return amethystShardEntity.level().getMaxLocalRawBrightness(blockPos);
    }

    @Override
    public void render(AmethystShardEntity amethystShardEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        float h = Mth.rotLerp(g, amethystShardEntity.yRotO, amethystShardEntity.getYRot());
        float j = Mth.lerp(g, amethystShardEntity.xRotO, amethystShardEntity.getXRot());
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.translate(0.0D, -1.55D, 0.0D);
        this.model.setupAnim(amethystShardEntity, 0.0F, 0.0F, 0.0F, h, j);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
        super.render(amethystShardEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(AmethystShardEntity amethystShardEntity) {
        return TEXTURE;
    }

}
