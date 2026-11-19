package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.AmethystGolemEntity;
import net.adventurez.entity.model.AmethystGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class AmethystGolemRenderer extends MobRenderer<AmethystGolemEntity, AmethystGolemModel<AmethystGolemEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/amethyst_golem.png");
    private static final ResourceLocation OTHER_TEXTURE = AdventureMain.identifierOf("textures/entity/deepslate_amethyst_golem.png");

    public AmethystGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new AmethystGolemModel<>(context.bakeLayer(RenderInit.AMETHYST_GOLEM_LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(AmethystGolemEntity amethystGolemEntity) {
        if (amethystGolemEntity.getEntityData().get(AmethystGolemEntity.DEEPSLATE_VARIANT)) {
            return OTHER_TEXTURE;
        } else {
            return TEXTURE;
        }
    }
}
