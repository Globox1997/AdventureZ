package net.adventurez.entity.render;

import net.adventurez.entity.render.state.NecromancerRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.NecromancerEntity;
import net.adventurez.entity.model.NecromancerModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class NecromancerRenderer extends AdventureMobRenderer<NecromancerEntity, NecromancerRenderState, NecromancerModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/necromancer.png");

    public NecromancerRenderer(EntityRendererProvider.Context context) {
        super(context, new NecromancerModel(context.bakeLayer(RenderInit.NECROMANCER_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTextureLocation(NecromancerRenderState necromancerEntity) {
        return TEXTURE;
    }

    @Override
    public NecromancerRenderState createRenderState() {
        return new NecromancerRenderState();
    }

    @Override
    public void extractRenderState(NecromancerEntity entity, NecromancerRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.spellcasting = entity.isSpellcasting();
    }
}
