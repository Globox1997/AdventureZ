package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AdventureRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.MiniBlackstoneGolemEntity;
import net.adventurez.entity.model.MiniBlackstoneGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class MiniBlackstoneGolemRenderer extends AdventureMobRenderer<MiniBlackstoneGolemEntity, AdventureRenderState, MiniBlackstoneGolemModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/mini_blackstone_golem.png");

    public MiniBlackstoneGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new MiniBlackstoneGolemModel(context.bakeLayer(RenderInit.MINI_BLACKSTONE_GOLEM_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTextureLocation(AdventureRenderState stoneGolem) {
        return TEXTURE;
    }

    @Override
    public AdventureRenderState createRenderState() {
        return new AdventureRenderState();
    }
}
