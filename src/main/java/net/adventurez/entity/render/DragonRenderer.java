package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.DragonEntity;
import net.adventurez.entity.render.state.DragonRenderState;
import net.adventurez.entity.model.DragonModel;
import net.adventurez.entity.render.feature.DragonEyesFeatureRenderer;
import net.adventurez.entity.render.feature.DragonSaddleFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class DragonRenderer extends AdventureMobRenderer<DragonEntity, DragonRenderState, DragonModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/dragon.png");
    private static final Identifier RED_DRAGON_TEXTURE = AdventureMain.identifierOf("textures/entity/red_dragon.png");

    public DragonRenderer(EntityRendererProvider.Context context) {
        super(context, new DragonModel(context.bakeLayer(RenderInit.DRAGON_LAYER)), 0.4F);
        this.addLayer(new DragonEyesFeatureRenderer(this));
        this.addLayer(new DragonSaddleFeatureRenderer(this));
    }

    @Override
    protected float getShadowRadius(DragonRenderState state) {
        return 0.6F * (float) state.size;
    }

    @Override
    protected void scale(DragonRenderState dragonEntity, PoseStack matrixStack) {
        matrixStack.scale((float) dragonEntity.size / 3.0F, (float) dragonEntity.size / 3.0F, (float) dragonEntity.size / 3.0F);
    }

    @Override
    public Identifier getTextureLocation(DragonRenderState dragonEntity) {
        if (dragonEntity.redDragon) {
            return RED_DRAGON_TEXTURE;
        }
        return TEXTURE;
    }

    @Override
    public DragonRenderState createRenderState() {
        return new DragonRenderState();
    }

    @Override
    public void extractRenderState(DragonEntity entity, DragonRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.partialTick = partialTicks;
        state.fireBreath = entity.getEntityData().get(DragonEntity.FIRE_BREATH);
        state.clientStartFlying = entity.getEntityData().get(DragonEntity.CLIENT_START_FLYING);
        state.flying = entity.getEntityData().get(DragonEntity.IS_FLYING);
        state.clientEndFlying = entity.getEntityData().get(DragonEntity.CLIENT_END_FLYING);
        state.wingOpen = Mth.lerp(partialTicks, entity.prevWingOpen, entity.wingOpen);
        state.flapPhase = entity.flapPhase;
        state.onGround = entity.onGround();
        state.landingWings = entity.landingWings;
        state.landing = Mth.lerp(partialTicks, entity.prevLanding, entity.landing);
        state.sittingPose = entity.isInSittingPose();
        state.vehicle = entity.isVehicle();
        state.yawning = entity.isYawning();
        state.yawnPitch = entity.getYawnPitch(partialTicks);
        state.hasSaddle = entity.getEntityData().get(DragonEntity.HAS_SADDLE);
        state.hasChest = entity.getEntityData().get(DragonEntity.HAS_CHEST);
        state.otherEars = entity.getEntityData().get(DragonEntity.OTHER_EARS);
        state.otherTail = entity.getEntityData().get(DragonEntity.OTHER_TAIL);
        state.otherEyes = entity.getEntityData().get(DragonEntity.OTHER_EYES);
        state.tamed = entity.isTamed();
        state.redDragon = entity.getEntityData().get(DragonEntity.RED_DRAGON);
        state.size = entity.getSize();
    }
}
