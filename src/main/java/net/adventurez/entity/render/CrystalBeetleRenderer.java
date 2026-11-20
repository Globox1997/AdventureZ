package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AdventureRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.CrystalBeetleEntity;
import net.adventurez.entity.model.CrystalBeetleModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class CrystalBeetleRenderer extends AdventureMobRenderer<CrystalBeetleEntity, AdventureRenderState, CrystalBeetleModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/crystal_beetle.png");

    public CrystalBeetleRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalBeetleModel(context.bakeLayer(RenderInit.CRYSTAL_BEETLE_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTextureLocation(AdventureRenderState amethystGolemEntity) {
       return TEXTURE;
    }

    @Override
    public AdventureRenderState createRenderState() {
        return new AdventureRenderState();
    }
}
