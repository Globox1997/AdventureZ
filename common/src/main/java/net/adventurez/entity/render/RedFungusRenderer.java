package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AdventureRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.RedFungusEntity;
import net.adventurez.entity.model.RedFungusModel;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class RedFungusRenderer extends AdventureMobRenderer<RedFungusEntity, AdventureRenderState, RedFungusModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/red_fungus.png");

    public RedFungusRenderer(EntityRendererProvider.Context context) {
        super(context, new RedFungusModel(context.bakeLayer(RenderInit.RED_FUNGUS_LAYER)), 0.3F);
    }

    @Override
    public Identifier getTextureLocation(AdventureRenderState fungusEntity) {
        return TEXTURE;
    }

    @Override
    public AdventureRenderState createRenderState() {
        return new AdventureRenderState();
    }
}
