package net.adventurez.entity.render;

import net.adventurez.entity.render.state.IguanaRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.IguanaEntity;
import net.adventurez.entity.model.IguanaModel;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class IguanaRenderer extends AdventureAgeableMobRenderer<IguanaEntity, IguanaRenderState, IguanaModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/iguana.png");

    public IguanaRenderer(EntityRendererProvider.Context context) {
        super(context, new IguanaModel(context.bakeLayer(RenderInit.IGUANA_LAYER)), new IguanaModel(context.bakeLayer(RenderInit.IGUANA_LAYER_BABY)), 0.7F);
    }

    @Override
    public Identifier getTextureLocation(IguanaRenderState iguanaEntity) {
        return TEXTURE;
    }

    @Override
    public IguanaRenderState createRenderState() {
        return new IguanaRenderState();
    }

    @Override
    public void extractRenderState(IguanaEntity entity, IguanaRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.openMouth = entity.getEntityData().get(IguanaEntity.OPEN_MOUTH);
    }
}
