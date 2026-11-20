package net.adventurez.entity.render;

import net.adventurez.entity.render.state.SkeletonVanguardRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.SkeletonVanguardEntity;
import net.adventurez.entity.model.SkeletonVanguardModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class SkeletonVanguardRenderer extends AdventureMobRenderer<SkeletonVanguardEntity, SkeletonVanguardRenderState, SkeletonVanguardModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/skeleton_vanguard.png");

    public SkeletonVanguardRenderer(EntityRendererProvider.Context context) {
        super(context, new SkeletonVanguardModel(context.bakeLayer(RenderInit.SKELETON_VANGUARD_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTextureLocation(SkeletonVanguardRenderState skeletonVanguardEntity) {
        return TEXTURE;
    }

    @Override
    public SkeletonVanguardRenderState createRenderState() {
        return new SkeletonVanguardRenderState();
    }

    @Override
    public void extractRenderState(SkeletonVanguardEntity entity, SkeletonVanguardRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.shieldSwing = entity.getEntityData().get(SkeletonVanguardEntity.SHIELD_SWING);
    }
}
