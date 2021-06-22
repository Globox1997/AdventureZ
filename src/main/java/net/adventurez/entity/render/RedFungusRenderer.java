package net.adventurez.entity.render;

import net.adventurez.entity.RedFungusEntity;
import net.adventurez.entity.model.RedFungusModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RedFungusRenderer extends MobEntityRenderer<RedFungusEntity, RedFungusModel<RedFungusEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/red_fungus.png");

    public RedFungusRenderer(EntityRendererFactory.Context context) {
        super(context, new RedFungusModel<>(context.getPart(RenderInit.RED_FUNGUS_LAYER)), 0.3F);
    }

    @Override
    public Identifier getTexture(RedFungusEntity fungusEntity) {
        return TEXTURE;
    }
}
