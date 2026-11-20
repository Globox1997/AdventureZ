package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.adventurez.entity.VoidShadowEntity;
import net.adventurez.entity.render.state.VoidShadowRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.adventurez.entity.model.VoidShadowModel;
import net.adventurez.entity.render.feature.VoidShadowBlackFeatureRenderer;
import net.adventurez.entity.render.feature.VoidShadowEyesFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class VoidShadowRenderer extends AdventureMobRenderer<VoidShadowEntity, VoidShadowRenderState, VoidShadowModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/void_shadow.png");

    public VoidShadowRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidShadowModel(context.bakeLayer(RenderInit.VOID_SHADOW_LAYER)), 1.7F);
        this.addLayer(new VoidShadowEyesFeatureRenderer(this));
        this.addLayer(new VoidShadowBlackFeatureRenderer(this));
    }

    @Override
    protected void scale(VoidShadowRenderState voidShadowEntity, PoseStack matrixStack) {
        matrixStack.scale(9.0F, 9.0F, 9.0F);
    }

    @Override
    public Identifier getTextureLocation(VoidShadowRenderState voidShadowEntity) {
        return TEXTURE;
    }

    @Override
    public VoidShadowRenderState createRenderState() {
        return new VoidShadowRenderState();
    }

    @Override
    public void extractRenderState(VoidShadowEntity entity, VoidShadowRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.throwingBlocks = entity.getEntityData().get(VoidShadowEntity.IS_THROWING_BLOCKS);
        state.hoveringMagicHands = entity.getEntityData().get(VoidShadowEntity.HOVERING_MAGIC_HANDS);
        state.circlingHands = entity.getEntityData().get(VoidShadowEntity.CIRCLING_HANDS);
        state.halfLifeChange = entity.getEntityData().get(VoidShadowEntity.HALF_LIFE_CHANGE);
        if (state.throwingBlocks) {
            entity.throwingBlocksTicker = Mth.clamp(entity.throwingBlocksTicker + 0.05817666666666F, 0.0F, 1.7453F);
        } else {
            entity.throwingBlocksTicker = Mth.clamp(entity.throwingBlocksTicker - 0.05817666666666F, 0.0F, 1.7453F);
        }
        state.throwingBlocksTicker = entity.throwingBlocksTicker;
        state.ticksSinceDeath = entity.ticksSinceDeath;
        state.deathAnimationProgress = ((float) entity.ticksSinceDeath + partialTicks) / 200.0F;
    }

    @Override
    public void submit(VoidShadowRenderState state, PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (state.ticksSinceDeath > 40) {
            matrixStack.pushPose();
            float m = state.deathAnimationProgress;
            matrixStack.translate(0.0F, 8.0F, -2.0F);
            renderDeathAnimation(matrixStack, m, submitNodeCollector);
            matrixStack.popPose();
        }
        super.submit(state, matrixStack, submitNodeCollector, camera);
    }

    private static void renderDeathAnimation(PoseStack matrices, float animationProgress, SubmitNodeCollector submitNodeCollector) {
        submitNodeCollector.submitCustomGeometry(matrices, RenderTypes.dragonRays(), (entry, vertexCOnsumer) -> {
            float f = Math.min(animationProgress > 0.8F ? (animationProgress - 0.8F) / 0.2F : 0.0F, 1.0F);
            int i = ARGB.colorFromFloat(1.0F - f, 1.0F, 1.0F, 1.0F);
            RandomSource random = RandomSource.create(432L);
            Vector3f vector3f = new Vector3f();
            Vector3f vector3f2 = new Vector3f();
            Vector3f vector3f3 = new Vector3f();
            Vector3f vector3f4 = new Vector3f();
            Quaternionf quaternionf = new Quaternionf();
            int k = Mth.floor((animationProgress + animationProgress * animationProgress) / 2.0F * 60.0F);

            for (int l = 0; l < k; l++) {
                quaternionf.rotationXYZ(random.nextFloat() * (float) (Math.PI * 2), random.nextFloat() * (float) (Math.PI * 2), random.nextFloat() * (float) (Math.PI * 2)).rotateXYZ(
                        random.nextFloat() * (float) (Math.PI * 2), random.nextFloat() * (float) (Math.PI * 2), random.nextFloat() * (float) (Math.PI * 2) + animationProgress * (float) (Math.PI / 2));
                entry.rotate(quaternionf);
                float g = random.nextFloat() * 20.0F + 5.0F + f * 10.0F;
                float h = random.nextFloat() * 2.0F + 1.0F + f * 2.0F;
                vector3f2.set(-(float) (Math.sqrt(3.0) / 2.0) * h, g, -0.5F * h);
                vector3f3.set((float) (Math.sqrt(3.0) / 2.0) * h, g, -0.5F * h);
                vector3f4.set(0.0F, g, h);
                vertexCOnsumer.addVertex(entry, vector3f).setColor(i);
                vertexCOnsumer.addVertex(entry, vector3f2).setColor(16777215);
                vertexCOnsumer.addVertex(entry, vector3f3).setColor(16777215);
                vertexCOnsumer.addVertex(entry, vector3f).setColor(i);
                vertexCOnsumer.addVertex(entry, vector3f3).setColor(16777215);
                vertexCOnsumer.addVertex(entry, vector3f4).setColor(16777215);
                vertexCOnsumer.addVertex(entry, vector3f).setColor(i);
                vertexCOnsumer.addVertex(entry, vector3f4).setColor(16777215);
                vertexCOnsumer.addVertex(entry, vector3f2).setColor(16777215);
            }
        });
    }

}
