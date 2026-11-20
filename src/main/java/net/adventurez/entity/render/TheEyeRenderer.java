package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.adventurez.AdventureMain;
import net.adventurez.entity.TheEyeEntity;
import net.adventurez.entity.model.TheEyeModel;
import net.adventurez.entity.render.state.TheEyeRenderState;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class TheEyeRenderer extends AdventureMobRenderer<TheEyeEntity, TheEyeRenderState, TheEyeModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/the_eye.png");
    private static final Identifier EXPLOSION_BEAM_TEXTURE = AdventureMain.identifierOf("textures/entity/eye_beam.png");
    private static final RenderType LAYER = RenderTypes.entityCutout(EXPLOSION_BEAM_TEXTURE);

    public TheEyeRenderer(EntityRendererProvider.Context context) {
        super(context, new TheEyeModel(context.bakeLayer(RenderInit.THE_EYE_LAYER)), 1.0F);
    }

    @Override
    protected void scale(TheEyeRenderState eye, PoseStack matrixStack) {
        matrixStack.scale(4.0F, 4.0F, 4.0F);
    }

    @Override
    public Identifier getTextureLocation(TheEyeRenderState eye) {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(TheEyeEntity theEyeEntity, Frustum frustum, double d, double e, double f, float partialTicks) {
        if (super.shouldRender(theEyeEntity, frustum, d, e, f, partialTicks)) {
            return true;
        } else {
            if (theEyeEntity.hasBeamTarget()) {
                LivingEntity livingEntity = theEyeEntity.getBeamTarget();
                if (livingEntity != null) {
                    Vec3 vec3d = this.fromLerpedPosition(livingEntity, (double) livingEntity.getBbHeight() * 0.5D, 1.0F);
                    Vec3 vec3d2 = this.fromLerpedPosition(theEyeEntity, (double) theEyeEntity.getEyeHeight(), 1.0F);
                    return frustum.isVisible(new AABB(vec3d2.x, vec3d2.y, vec3d2.z, vec3d.x, vec3d.y, vec3d.z));
                }
            }

            return false;
        }
    }

    private Vec3 fromLerpedPosition(LivingEntity entity, double yOffset, float delta) {
        double d = Mth.lerp((double) delta, entity.xOld, entity.getX());
        double e = Mth.lerp((double) delta, entity.yOld, entity.getY()) + yOffset;
        double f = Mth.lerp((double) delta, entity.zOld, entity.getZ());
        return new Vec3(d, e, f);
    }

    @Override
    public TheEyeRenderState createRenderState() {
        return new TheEyeRenderState();
    }

    @Override
    public void extractRenderState(TheEyeEntity entity, TheEyeRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.health = entity.getHealth();
        state.maxHealth = entity.getMaxHealth();
        state.invulTimer = entity.getEntityData().get(TheEyeEntity.INVUL_TIMER);
        state.tickCount = entity.tickCount;
        LivingEntity livingEntity = entity.getBeamTarget();
        if (livingEntity != null && livingEntity.isAlive() && entity.isAlive()) {
            state.beamTime = (float) entity.level().getGameTime() + partialTicks;
            state.beamTargetPosition = this.fromLerpedPosition(livingEntity, (double) livingEntity.getBbHeight() * 0.5, partialTicks);
            state.beamStartPosition = this.fromLerpedPosition(entity, (double) entity.getEyeHeight(), partialTicks);
        } else {
            state.beamTargetPosition = null;
        }
    }

    @Override
    public void submit(TheEyeRenderState state, PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, matrixStack, submitNodeCollector, camera);
        Vec3 targetPosition = state.beamTargetPosition;
        if (targetPosition != null) {
            float j = state.beamTime;
            float k = j * 0.5F % 1.0F;
            float l = state.eyeHeight;
            matrixStack.pushPose();
            matrixStack.translate(0.0F, l, 0.0F);
            Vec3 vec3d3 = targetPosition.subtract(state.beamStartPosition);
            float m = (float) (vec3d3.length() + 1.0);
            vec3d3 = vec3d3.normalize();
            float n = (float) Math.acos(vec3d3.y);
            float o = (float) Math.atan2(vec3d3.z, vec3d3.x);
            matrixStack.rotate(Axis.YP.rotationDegrees(((float) (Math.PI / 2) - o) * (180.0F / (float) Math.PI)));
            matrixStack.rotate(Axis.XP.rotationDegrees(n * (180.0F / (float) Math.PI)));
            float q = j * 0.05F * -1.5F;
            float r = j * j;
            int s = 64 + (int) (r * 191.0F);
            int t = 32 + (int) (r * 191.0F);
            int u = 128 - (int) (r * 64.0F);
            float x = Mth.cos(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
            float y = Mth.sin(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
            float z = Mth.cos(q + (float) (Math.PI / 4)) * 0.282F;
            float aa = Mth.sin(q + (float) (Math.PI / 4)) * 0.282F;
            float ab = Mth.cos(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
            float ac = Mth.sin(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
            float ad = Mth.cos(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
            float ae = Mth.sin(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
            float af = Mth.cos(q + (float) Math.PI) * 0.2F;
            float ag = Mth.sin(q + (float) Math.PI) * 0.2F;
            float ah = Mth.cos(q + 0.0F) * 0.2F;
            float ai = Mth.sin(q + 0.0F) * 0.2F;
            float aj = Mth.cos(q + (float) (Math.PI / 2)) * 0.2F;
            float ak = Mth.sin(q + (float) (Math.PI / 2)) * 0.2F;
            float al = Mth.cos(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
            float am = Mth.sin(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
            float aq = -1.0F + k;
            float ar = m * 2.5F + aq;
            float as = state.tickCount % 2 == 0 ? 0.5F : 0.0F;
            submitNodeCollector.submitCustomGeometry(matrixStack, LAYER, (entry, vertexConsumer) -> {
                vertex(vertexConsumer, entry, af, m, ag, s, t, u, 0.4999F, ar);
                vertex(vertexConsumer, entry, af, 0.0F, ag, s, t, u, 0.4999F, aq);
                vertex(vertexConsumer, entry, ah, 0.0F, ai, s, t, u, 0.0F, aq);
                vertex(vertexConsumer, entry, ah, m, ai, s, t, u, 0.0F, ar);
                vertex(vertexConsumer, entry, aj, m, ak, s, t, u, 0.4999F, ar);
                vertex(vertexConsumer, entry, aj, 0.0F, ak, s, t, u, 0.4999F, aq);
                vertex(vertexConsumer, entry, al, 0.0F, am, s, t, u, 0.0F, aq);
                vertex(vertexConsumer, entry, al, m, am, s, t, u, 0.0F, ar);
                vertex(vertexConsumer, entry, x, m, y, s, t, u, 0.5F, as + 0.5F);
                vertex(vertexConsumer, entry, z, m, aa, s, t, u, 1.0F, as + 0.5F);
                vertex(vertexConsumer, entry, ad, m, ae, s, t, u, 1.0F, as);
                vertex(vertexConsumer, entry, ab, m, ac, s, t, u, 0.5F, as);
            });
            matrixStack.popPose();
        }
    }

    private static void vertex(VertexConsumer vertexConsumer, PoseStack.Pose matrix, float x, float y, float z, int red, int green, int blue, float u, float v) {
        vertexConsumer.addVertex(matrix, x, y, z).setColor(red, green, blue, 255).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrix, 0.0F, 1.0F, 0.0F);
    }
}
