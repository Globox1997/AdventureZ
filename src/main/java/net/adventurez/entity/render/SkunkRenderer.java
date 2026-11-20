package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AdventureRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.SkunkEntity;
import net.adventurez.entity.model.SkunkModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class SkunkRenderer extends AdventureAgeableMobRenderer<SkunkEntity, AdventureRenderState, SkunkModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/skunk.png");

    public SkunkRenderer(EntityRendererProvider.Context context) {
        super(context, new SkunkModel(context.bakeLayer(RenderInit.SKUNK_LAYER)), new SkunkModel(context.bakeLayer(RenderInit.SKUNK_LAYER_BABY)), 0.4F);
    }

    @Override
    public Identifier getTextureLocation(AdventureRenderState skunkEntity) {
        return TEXTURE;
    }

    @Override
    public AdventureRenderState createRenderState() {
        return new AdventureRenderState();
    }
}
