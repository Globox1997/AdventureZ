package net.adventurez.entity.render;

import net.adventurez.entity.render.state.VoidFragmentRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.VoidFragmentEntity;
import net.adventurez.entity.model.VoidFragmentModel;
import net.adventurez.entity.render.feature.VoidFragmentCorruptFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class VoidFragmentRenderer extends AdventureMobRenderer<VoidFragmentEntity, VoidFragmentRenderState, VoidFragmentModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/void_fragment.png");

    public VoidFragmentRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidFragmentModel(context.bakeLayer(RenderInit.VOID_FRAGMENT_LAYER)), 0.5F);
        this.addLayer(new VoidFragmentCorruptFeatureRenderer(this));
    }

    @Override
    public Identifier getTextureLocation(VoidFragmentRenderState voidFragmentEntity) {
        return TEXTURE;
    }

    @Override
    public VoidFragmentRenderState createRenderState() {
        return new VoidFragmentRenderState();
    }

    @Override
    public void extractRenderState(VoidFragmentEntity entity, VoidFragmentRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.voidOrb = entity.getEntityData().get(VoidFragmentEntity.IS_VOID_ORB);
    }
}
