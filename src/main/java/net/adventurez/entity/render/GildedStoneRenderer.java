package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.AdventureMain;
import net.adventurez.entity.model.GildedStoneModel;
import net.adventurez.entity.nonliving.GildedBlackstoneShardEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class GildedStoneRenderer extends EntityRenderer<GildedBlackstoneShardEntity> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/item/gilded_stone.png");
    private final GildedStoneModel model = new GildedStoneModel(GildedStoneModel.getTexturedModelData().bakeRoot());

    public GildedStoneRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public int getBlockLightLevel(GildedBlackstoneShardEntity gildedStoneEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(GildedBlackstoneShardEntity gildedStoneEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(this.getTextureLocation(gildedStoneEntity)));
        int lightAbove = LevelRenderer.getLightColor(gildedStoneEntity.getCommandSenderWorld(), gildedStoneEntity.blockPosition().above());
        matrixStack.scale(1.0F, -1.0F, 1.0F);
        matrixStack.translate(0D, -1.45D, 0D);
        model.renderToBuffer(matrixStack, vertexConsumer, lightAbove, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
        super.render(gildedStoneEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(GildedBlackstoneShardEntity gildedStoneEntity) {
        return TEXTURE;
    }
}
