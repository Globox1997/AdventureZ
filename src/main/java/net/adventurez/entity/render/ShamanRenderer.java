package net.adventurez.entity.render;

import net.adventurez.entity.render.state.ShamanRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.ShamanEntity;
import net.adventurez.entity.model.ShamanModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class ShamanRenderer extends AdventureMobRenderer<ShamanEntity, ShamanRenderState, ShamanModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/shaman.png");

    public ShamanRenderer(EntityRendererProvider.Context context) {
        super(context, new ShamanModel(context.bakeLayer(RenderInit.SHAMAN_LAYER)), 0.6F);
    }

    @Override
    public Identifier getTextureLocation(ShamanRenderState shamanEntity) {
        return TEXTURE;
    }

    @Override
    public ShamanRenderState createRenderState() {
        return new ShamanRenderState();
    }

    @Override
    public void extractRenderState(ShamanEntity entity, ShamanRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.spellcasting = entity.isSpellcasting();
    }
}
