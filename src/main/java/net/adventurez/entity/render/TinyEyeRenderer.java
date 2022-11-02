package net.adventurez.entity.render;

import net.adventurez.entity.model.TinyEyeModel;
import net.adventurez.entity.nonliving.TinyEyeEntity;
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

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class TinyEyeRenderer extends EntityRenderer<TinyEyeEntity> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/the_eye.png");
    private final TinyEyeModel<TinyEyeEntity> model = new TinyEyeModel<>(TinyEyeModel.getTexturedModelData().createModel());

    public TinyEyeRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public int getBlockLight(TinyEyeEntity tinyEyeEntity, BlockPos blockPos) {
        return tinyEyeEntity.world.getLightLevel(blockPos);
    }

    @Override
    public void render(TinyEyeEntity tinyEyeEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float h = MathHelper.lerpAngle(tinyEyeEntity.prevYaw, tinyEyeEntity.getYaw(), g);
        matrixStack.scale(-0.5F, -0.5F, 0.5F);
        matrixStack.translate(0.0D, -1.4D, 0.0D);
        this.model.setAngles(tinyEyeEntity, 0.0F, 0.0F, 0.0F, h, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(tinyEyeEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(TinyEyeEntity eye) {
        return TEXTURE;
    }

}