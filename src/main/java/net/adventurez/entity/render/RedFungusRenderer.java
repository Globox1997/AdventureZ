package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.RedFungusEntity;
import net.adventurez.entity.model.RedFungusModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RedFungusRenderer extends MobRenderer<RedFungusEntity, RedFungusModel<RedFungusEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/red_fungus.png");

    public RedFungusRenderer(EntityRendererProvider.Context context) {
        super(context, new RedFungusModel<>(context.bakeLayer(RenderInit.RED_FUNGUS_LAYER)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(RedFungusEntity fungusEntity) {
        return TEXTURE;
    }
}
