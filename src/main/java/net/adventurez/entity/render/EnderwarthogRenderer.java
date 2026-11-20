package net.adventurez.entity.render;

import net.adventurez.entity.render.state.EnderwarthogRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.EnderwarthogEntity;
import net.adventurez.entity.model.EnderwarthogModel;
import net.adventurez.entity.render.feature.EnderwarthogEyesFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class EnderwarthogRenderer extends AdventureMobRenderer<EnderwarthogEntity, EnderwarthogRenderState, EnderwarthogModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/enderwarthog.png");

    public EnderwarthogRenderer(EntityRendererProvider.Context context) {
        super(context, new EnderwarthogModel(context.bakeLayer(RenderInit.ENDERWARTHOG_LAYER)), 1.5F);
        this.addLayer(new EnderwarthogEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTextureLocation(EnderwarthogRenderState enderwarthogEntity) {
        return TEXTURE;
    }

    @Override
    public EnderwarthogRenderState createRenderState() {
        return new EnderwarthogRenderState();
    }

    @Override
    public void extractRenderState(EnderwarthogEntity entity, EnderwarthogRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.biteAttack = entity.getEntityData().get(EnderwarthogEntity.BITE_ATTACK);
        state.rareVariant = entity.getEntityData().get(EnderwarthogEntity.RARE_VARIANT);
    }
}
