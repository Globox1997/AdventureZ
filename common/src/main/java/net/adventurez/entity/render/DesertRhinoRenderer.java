package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AdventureRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.DesertRhinoEntity;
import net.adventurez.entity.model.DesertRhinoModel;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class DesertRhinoRenderer extends AdventureMobRenderer<DesertRhinoEntity, AdventureRenderState, DesertRhinoModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/desert_rhino.png");

    public DesertRhinoRenderer(EntityRendererProvider.Context context) {
        super(context, new DesertRhinoModel(context.bakeLayer(RenderInit.DESERT_RHINO_LAYER)), 1.5F);
    }

    @Override
    public Identifier getTextureLocation(AdventureRenderState desertRhinoEntity) {
        return TEXTURE;
    }

    @Override
    public AdventureRenderState createRenderState() {
        return new AdventureRenderState();
    }
}
