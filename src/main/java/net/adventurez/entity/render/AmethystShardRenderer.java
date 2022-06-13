package net.adventurez.entity.render;

import net.adventurez.entity.model.AmethystShardModel;
import net.adventurez.entity.nonliving.AmethystShardEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class AmethystShardRenderer extends EntityRenderer<AmethystShardEntity> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/amethyst_shard.png");
    private final AmethystShardModel<AmethystShardEntity> model = new AmethystShardModel<>(AmethystShardModel.getTexturedModelData().createModel());

    public AmethystShardRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public int getBlockLight(AmethystShardEntity amethystShardEntity, BlockPos blockPos) {
        return amethystShardEntity.world.getLightLevel(blockPos);
    }

    @Override
    public void render(AmethystShardEntity amethystShardEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        // lerpAngle found at this.dolphin.setPitch(MathHelper.lerpAngle(this.dolphin.getPitch(), 0.0f, 0.2f));
        float h = MathHelper.lerpAngle(amethystShardEntity.prevYaw, amethystShardEntity.getYaw(), g);
        float j = MathHelper.lerp(g, amethystShardEntity.prevPitch, amethystShardEntity.getPitch());
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.translate(0.0D, -1.55D, 0.0D);
        this.model.setAngles(amethystShardEntity, 0.0F, 0.0F, 0.0F, h, j);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(amethystShardEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(AmethystShardEntity amethystShardEntity) {
        return TEXTURE;
    }

}