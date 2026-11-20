package net.adventurez.entity.render;

import net.adventurez.entity.render.state.DubiousPumpkinRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.DubiousPumpkinEntity;
import net.adventurez.entity.model.DubiousPumpkinModel;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class DubiousPumpkinRenderer extends AdventureMobRenderer<DubiousPumpkinEntity, DubiousPumpkinRenderState, DubiousPumpkinModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/dubious_pumpkin.png");
    private static final Identifier TEXTURE_ANGRY = AdventureMain.identifierOf("textures/entity/dubious_pumpkin_angry.png");

    public DubiousPumpkinRenderer(EntityRendererProvider.Context context) {
        super(context, new DubiousPumpkinModel(context.bakeLayer(RenderInit.DUBIOUS_PUMPKIN_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTextureLocation(DubiousPumpkinRenderState dubiousPumpkinEntity) {
        if (dubiousPumpkinEntity.angry) {
            return TEXTURE_ANGRY;
        }
        return TEXTURE;
    }

    @Override
    public DubiousPumpkinRenderState createRenderState() {
        return new DubiousPumpkinRenderState();
    }

    @Override
    public void extractRenderState(DubiousPumpkinEntity entity, DubiousPumpkinRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.angry = entity.getEntityData().get(DubiousPumpkinEntity.ANGRY);
    }
}
