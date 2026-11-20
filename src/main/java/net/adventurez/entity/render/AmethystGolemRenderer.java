package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AmethystGolemRenderState;
import net.adventurez.AdventureMain;
import net.adventurez.entity.AmethystGolemEntity;
import net.adventurez.entity.model.AmethystGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class AmethystGolemRenderer extends AdventureMobRenderer<AmethystGolemEntity, AmethystGolemRenderState, AmethystGolemModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/amethyst_golem.png");
    private static final Identifier OTHER_TEXTURE = AdventureMain.identifierOf("textures/entity/deepslate_amethyst_golem.png");

    public AmethystGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new AmethystGolemModel(context.bakeLayer(RenderInit.AMETHYST_GOLEM_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTextureLocation(AmethystGolemRenderState amethystGolemEntity) {
        if (amethystGolemEntity.deepslateVariant) {
            return OTHER_TEXTURE;
        } else {
            return TEXTURE;
        }
    }

    @Override
    public AmethystGolemRenderState createRenderState() {
        return new AmethystGolemRenderState();
    }

    @Override
    public void extractRenderState(AmethystGolemEntity entity, AmethystGolemRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.attackAnim = entity.getSwingAnimation(1.0F);
        state.backCrystals = entity.getEntityData().get(AmethystGolemEntity.BACK_CRYSTALS);
        state.deepslateVariant = entity.getEntityData().get(AmethystGolemEntity.DEEPSLATE_VARIANT);
    }
}
