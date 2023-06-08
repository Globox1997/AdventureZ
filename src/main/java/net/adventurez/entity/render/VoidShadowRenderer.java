package net.adventurez.entity.render;

import java.util.Random;

import org.joml.Matrix4f;

import net.adventurez.entity.VoidShadowEntity;
import net.adventurez.entity.model.VoidShadowModel;
import net.adventurez.entity.render.feature.VoidShadowBlackFeatureRenderer;
import net.adventurez.entity.render.feature.VoidShadowEyesFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class VoidShadowRenderer extends MobEntityRenderer<VoidShadowEntity, VoidShadowModel<VoidShadowEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/void_shadow.png");

    public VoidShadowRenderer(EntityRendererFactory.Context context) {
        super(context, new VoidShadowModel<>(context.getPart(RenderInit.VOID_SHADOW_LAYER)), 1.7F);
        this.addFeature(new VoidShadowEyesFeatureRenderer(this));
        this.addFeature(new VoidShadowBlackFeatureRenderer(this));
    }

    @Override
    public void scale(VoidShadowEntity voidShadowEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(9.0F, 9.0F, 9.0F);
    }

    @Override
    public Identifier getTexture(VoidShadowEntity voidShadowEntity) {
        return TEXTURE;
    }

    @Override
    public void render(VoidShadowEntity voidShadowEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (voidShadowEntity.ticksSinceDeath > 40) {
            matrixStack.push();
            VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getEntityDecal(TEXTURE));
            this.model.render(matrixStack, vertexConsumer2, i, OverlayTexture.getUv(0.0F, false), 1.0F, 1.0F, 1.0F, 1.0F);
            float l = ((float) voidShadowEntity.ticksSinceDeath + g) / 200.0F;
            float m = Math.min(l > 0.8F ? (l - 0.8F) / 0.2F : 0.0F, 1.0F);
            Random random = new Random(432L);
            VertexConsumer vertexConsumer5 = vertexConsumerProvider.getBuffer(RenderLayer.getLightning());
            matrixStack.translate(0.0D, 5.0D, 0.0D);
            for (int n = 0; (float) n < (l + l * l) / 2.0F * 60.0F; ++n) {
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(random.nextFloat() * 360.0F + l * 90.0F));
                float o = random.nextFloat() * 20.0F + 5.0F + m * 10.0F;
                float p = random.nextFloat() * 2.0F + 1.0F + m * 2.0F;
                Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
                int q = (int) (255.0F * (1.0F - m));
                method_23157(vertexConsumer5, matrix4f, q);
                method_23156(vertexConsumer5, matrix4f, o, p);
                method_23158(vertexConsumer5, matrix4f, o, p);
                method_23157(vertexConsumer5, matrix4f, q);
                method_23158(vertexConsumer5, matrix4f, o, p);
                method_23159(vertexConsumer5, matrix4f, o, p);
                method_23157(vertexConsumer5, matrix4f, q);
                method_23159(vertexConsumer5, matrix4f, o, p);
                method_23156(vertexConsumer5, matrix4f, o, p);
            }
            matrixStack.pop();
        }
        super.render(voidShadowEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    private static void method_23157(VertexConsumer vertices, Matrix4f matrix, int alpha) {
        vertices.vertex(matrix, 0.0F, 0.0F, 0.0F).color(36, 0, 90, alpha).next();
    }

    private static void method_23156(VertexConsumer vertices, Matrix4f matrix, float y, float x) {
        vertices.vertex(matrix, -(float) (Math.sqrt(3.0D) / 2.0D) * x, y, -0.5F * x).color(36, 0, 90, 0).next();
    }

    private static void method_23158(VertexConsumer vertices, Matrix4f matrix, float y, float x) {
        vertices.vertex(matrix, (float) (Math.sqrt(3.0D) / 2.0D) * x, y, -0.5F * x).color(36, 0, 90, 0).next();
    }

    private static void method_23159(VertexConsumer vertices, Matrix4f matrix, float y, float z) {
        vertices.vertex(matrix, 0.0F, y, 1.0F * z).color(36, 0, 90, 0).next();
    }
}
