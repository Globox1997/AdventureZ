package net.adventurez.entity.render;

import net.adventurez.entity.render.state.BlackstoneGolemRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.BlackstoneGolemEntity;
import net.adventurez.entity.model.BlackstoneGolemModel;
import net.adventurez.entity.render.feature.BlackstoneGolemBlueLavaFeatureRenderer;
import net.adventurez.entity.render.feature.StoneGolemLavaFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class BlackstoneGolemRenderer extends AdventureMobRenderer<BlackstoneGolemEntity, BlackstoneGolemRenderState, BlackstoneGolemModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/blackstone_golem.png");

    public BlackstoneGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new BlackstoneGolemModel(context.bakeLayer(RenderInit.BLACKSTONE_GOLEM_LAYER)), 1.7F);
        this.addLayer(new StoneGolemLavaFeatureRenderer(this));
        this.addLayer(new BlackstoneGolemBlueLavaFeatureRenderer(this));
    }

    @Override
    protected void scale(BlackstoneGolemRenderState stone, PoseStack matrixStack) {
        matrixStack.scale(2.4F, 2.4F, 2.4F);
    }

    @Override
    public Identifier getTextureLocation(BlackstoneGolemRenderState stoneGolem) {
        return TEXTURE;
    }

    @Override
    public BlackstoneGolemRenderState createRenderState() {
        return new BlackstoneGolemRenderState();
    }

    @Override
    public void extractRenderState(BlackstoneGolemEntity entity, BlackstoneGolemRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.throwCooldown = entity.getEntityData().get(BlackstoneGolemEntity.THROW_COOLDOWN);
        state.roarTick = entity.getRoarTick();
        state.halfLifeChange = entity.getEntityData().get(BlackstoneGolemEntity.HALF_LIFE_CHANGE);
        state.lavaTexture = entity.getEntityData().get(BlackstoneGolemEntity.LAVA_TEXTURE);
    }
}
