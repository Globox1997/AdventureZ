package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;

public abstract class AdventureMobRenderer<T extends Mob, S extends AdventureRenderState, M extends EntityModel<? super S>> extends MobRenderer<T, S, M> {

    public AdventureMobRenderer(EntityRendererProvider.Context context, M model, float shadow) {
        super(context, model, shadow);
    }

    @Override
    public void extractRenderState(T entity, S state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.attackTime = entity.getSwingAnimation(partialTicks);
    }
}
