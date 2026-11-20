package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AdventureRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Mob;

public abstract class AdventureAgeableMobRenderer<T extends Mob, S extends AdventureRenderState, M extends EntityModel<? super S>> extends AgeableMobRenderer<T, S, M> {

    public AdventureAgeableMobRenderer(EntityRendererProvider.Context context, M adultModel, M babyModel, float shadow) {
        super(context, adultModel, babyModel, shadow);
    }

    @Override
    public void extractRenderState(T entity, S state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.attackTime = entity.getSwingAnimation(partialTicks);
    }
}
