package net.adventurez.entity.render;

import net.adventurez.entity.model.VoidBulletModel;
import net.adventurez.entity.nonliving.VoidBulletEntity;
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
public class VoidBulletRenderer extends EntityRenderer<VoidBulletEntity> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/void_bullet.png");
    private final VoidBulletModel<VoidBulletEntity> model = new VoidBulletModel<>(VoidBulletModel.getTexturedModelData().createModel());

    public VoidBulletRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public int getBlockLight(VoidBulletEntity voidBulletEntity, BlockPos blockPos) {
        return voidBulletEntity.world.getLightLevel(blockPos);
    }

    @Override
    public void render(VoidBulletEntity voidBulletEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float h = MathHelper.lerpAngle(voidBulletEntity.prevYaw, voidBulletEntity.getYaw(), g);
        float j = MathHelper.lerp(g, voidBulletEntity.prevPitch, voidBulletEntity.getPitch());
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.translate(0.0D, -1.55D, 0.0D);
        this.model.setAngles(voidBulletEntity, 0.0F, 0.0F, 0.0F, h, j);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(voidBulletEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(VoidBulletEntity voidBulletEntity) {
        return TEXTURE;
    }

}