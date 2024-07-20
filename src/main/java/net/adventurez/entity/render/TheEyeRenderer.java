package net.adventurez.entity.render;

import net.adventurez.entity.TheEyeEntity;
import net.adventurez.entity.model.TheEyeModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class TheEyeRenderer extends MobEntityRenderer<TheEyeEntity, TheEyeModel<TheEyeEntity>> {
    private static final Identifier TEXTURE = Identifier.of("adventurez:textures/entity/the_eye.png");
    private static final Identifier EXPLOSION_BEAM_TEXTURE = Identifier.of("adventurez:textures/entity/eye_beam.png");
    private static final RenderLayer LAYER;

    public TheEyeRenderer(EntityRendererFactory.Context context) {
        super(context, new TheEyeModel<>(context.getPart(RenderInit.THE_EYE_LAYER)), 1.0F);

    }

    @Override
    public void scale(TheEyeEntity eye, MatrixStack matrixStack, float f) {
        matrixStack.scale(4.0F, 4.0F, 4.0F);
    }

    @Override
    public Identifier getTexture(TheEyeEntity eye) {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(TheEyeEntity theEyeEntity, Frustum frustum, double d, double e, double f) {
        if (super.shouldRender((TheEyeEntity) theEyeEntity, frustum, d, e, f)) {
            return true;
        } else {
            if (theEyeEntity.hasBeamTarget()) {
                LivingEntity livingEntity = theEyeEntity.getBeamTarget();
                if (livingEntity != null) {
                    Vec3d vec3d = this.fromLerpedPosition(livingEntity, (double) livingEntity.getHeight() * 0.5D, 1.0F);
                    Vec3d vec3d2 = this.fromLerpedPosition(theEyeEntity, (double) theEyeEntity.getStandingEyeHeight(), 1.0F);
                    return frustum.isVisible(new Box(vec3d2.x, vec3d2.y, vec3d2.z, vec3d.x, vec3d.y, vec3d.z));
                }
            }

            return false;
        }
    }

    private Vec3d fromLerpedPosition(LivingEntity entity, double yOffset, float delta) {
        double d = MathHelper.lerp((double) delta, entity.lastRenderX, entity.getX());
        double e = MathHelper.lerp((double) delta, entity.lastRenderY, entity.getY()) + yOffset;
        double f = MathHelper.lerp((double) delta, entity.lastRenderZ, entity.getZ());
        return new Vec3d(d, e, f);
    }

    @Override
    public void render(TheEyeEntity theEyeEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(theEyeEntity, f, g, matrixStack, vertexConsumerProvider, i);
        LivingEntity livingEntity = theEyeEntity.getBeamTarget();
        if (livingEntity != null && livingEntity.isAlive() && theEyeEntity.isAlive()) {
            float j = (float) theEyeEntity.getWorld().getTime() + g;
            float k = j * 0.5F % 1.0F;
            float l = theEyeEntity.getStandingEyeHeight();
            matrixStack.push();
            matrixStack.translate(0.0F, l, 0.0F);
            Vec3d vec3d = this.fromLerpedPosition(livingEntity, (double) livingEntity.getHeight() * 0.5, g);
            Vec3d vec3d2 = this.fromLerpedPosition(theEyeEntity, (double) l, g);
            Vec3d vec3d3 = vec3d.subtract(vec3d2);
            float m = (float) (vec3d3.length() + 1.0);
            vec3d3 = vec3d3.normalize();
            float n = (float) Math.acos(vec3d3.y);
            float o = (float) Math.atan2(vec3d3.z, vec3d3.x);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(((float) (Math.PI / 2) - o) * (180.0F / (float) Math.PI)));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(n * (180.0F / (float) Math.PI)));
            float q = j * 0.05F * -1.5F;
            float r = j * j;
            int s = 64 + (int) (r * 191.0F);
            int t = 32 + (int) (r * 191.0F);
            int u = 128 - (int) (r * 64.0F);
            float x = MathHelper.cos(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
            float y = MathHelper.sin(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
            float z = MathHelper.cos(q + (float) (Math.PI / 4)) * 0.282F;
            float aa = MathHelper.sin(q + (float) (Math.PI / 4)) * 0.282F;
            float ab = MathHelper.cos(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
            float ac = MathHelper.sin(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
            float ad = MathHelper.cos(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
            float ae = MathHelper.sin(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
            float af = MathHelper.cos(q + (float) Math.PI) * 0.2F;
            float ag = MathHelper.sin(q + (float) Math.PI) * 0.2F;
            float ah = MathHelper.cos(q + 0.0F) * 0.2F;
            float ai = MathHelper.sin(q + 0.0F) * 0.2F;
            float aj = MathHelper.cos(q + (float) (Math.PI / 2)) * 0.2F;
            float ak = MathHelper.sin(q + (float) (Math.PI / 2)) * 0.2F;
            float al = MathHelper.cos(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
            float am = MathHelper.sin(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
            float aq = -1.0F + k;
            float ar = m * 2.5F + aq;
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
            MatrixStack.Entry entry = matrixStack.peek();
            vertex(vertexConsumer, entry, af, m, ag, s, t, u, 0.4999F, ar);
            vertex(vertexConsumer, entry, af, 0.0F, ag, s, t, u, 0.4999F, aq);
            vertex(vertexConsumer, entry, ah, 0.0F, ai, s, t, u, 0.0F, aq);
            vertex(vertexConsumer, entry, ah, m, ai, s, t, u, 0.0F, ar);
            vertex(vertexConsumer, entry, aj, m, ak, s, t, u, 0.4999F, ar);
            vertex(vertexConsumer, entry, aj, 0.0F, ak, s, t, u, 0.4999F, aq);
            vertex(vertexConsumer, entry, al, 0.0F, am, s, t, u, 0.0F, aq);
            vertex(vertexConsumer, entry, al, m, am, s, t, u, 0.0F, ar);
            float as = 0.0F;
            if (theEyeEntity.age % 2 == 0) {
                as = 0.5F;
            }

            vertex(vertexConsumer, entry, x, m, y, s, t, u, 0.5F, as + 0.5F);
            vertex(vertexConsumer, entry, z, m, aa, s, t, u, 1.0F, as + 0.5F);
            vertex(vertexConsumer, entry, ad, m, ae, s, t, u, 1.0F, as);
            vertex(vertexConsumer, entry, ab, m, ac, s, t, u, 0.5F, as);
            matrixStack.pop();
        }

    }

    private static void vertex(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, float x, float y, float z, int red, int green, int blue, float u, float v) {
        vertexConsumer.vertex(matrix, x, y, z).color(red, green, blue, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0.0F,
                1.0F, 0.0F);
    }

    static {
        LAYER = RenderLayer.getEntityCutoutNoCull(EXPLOSION_BEAM_TEXTURE);
    }
}
