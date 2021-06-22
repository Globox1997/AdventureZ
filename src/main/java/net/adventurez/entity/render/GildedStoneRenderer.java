package net.adventurez.entity.render;

import net.adventurez.entity.model.GildedStoneModel;
import net.adventurez.entity.nonliving.GildedStoneEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class GildedStoneRenderer extends EntityRenderer<GildedStoneEntity> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/item/gilded_stone.png");
    private final GildedStoneModel model = new GildedStoneModel(GildedStoneModel.getTexturedModelData().createModel());

    public GildedStoneRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public int getBlockLight(GildedStoneEntity gildedStoneEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(GildedStoneEntity gildedStoneEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        matrixStack.push();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(gildedStoneEntity)));
        int lightAbove = WorldRenderer.getLightmapCoordinates(gildedStoneEntity.getEntityWorld(), gildedStoneEntity.getBlockPos().up());
        matrixStack.scale(1.0F, -1.0F, 1.0F);
        matrixStack.translate(0D, -1.45D, 0D);
        model.render(matrixStack, vertexConsumer, lightAbove, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(gildedStoneEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(GildedStoneEntity gildedStoneEntity) {
        return TEXTURE;
    }
}
