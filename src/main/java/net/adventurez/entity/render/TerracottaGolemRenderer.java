package net.adventurez.entity.render;

import net.adventurez.entity.render.state.TerracottaGolemRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.TerracottaGolemEntity;
import net.adventurez.entity.model.TerracottaGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class TerracottaGolemRenderer extends AdventureMobRenderer<TerracottaGolemEntity, TerracottaGolemRenderState, TerracottaGolemModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/terracotta_golem.png");

    public TerracottaGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new TerracottaGolemModel(context.bakeLayer(RenderInit.TERRACOTTA_GOLEM_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTextureLocation(TerracottaGolemRenderState terracottaGolemEntity) {
        return TEXTURE;
    }

    @Override
    public TerracottaGolemRenderState createRenderState() {
        return new TerracottaGolemRenderState();
    }

    @Override
    public void extractRenderState(TerracottaGolemEntity entity, TerracottaGolemRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.attackAnim = entity.getSwingAnimation(1.0F);
        state.blockVariant = entity.getEntityData().get(TerracottaGolemEntity.BLOCK_VARIANT);
    }
}
