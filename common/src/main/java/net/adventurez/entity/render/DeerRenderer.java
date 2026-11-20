package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AdventureRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.DeerEntity;
import net.adventurez.entity.model.DeerModel;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class DeerRenderer extends AdventureAgeableMobRenderer<DeerEntity, AdventureRenderState, DeerModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/deer.png");

    public DeerRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerModel(context.bakeLayer(RenderInit.DEER_LAYER)), new DeerModel(context.bakeLayer(RenderInit.DEER_LAYER_BABY)), 0.7F);
    }

    @Override
    public Identifier getTextureLocation(AdventureRenderState deerEntity) {
        return TEXTURE;
    }

    @Override
    public AdventureRenderState createRenderState() {
        return new AdventureRenderState();
    }
}
