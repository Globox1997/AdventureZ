package net.adventurez.entity.render;

import net.adventurez.entity.AmethystGolemEntity;
import net.adventurez.entity.model.AmethystGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AmethystGolemRenderer extends MobEntityRenderer<AmethystGolemEntity, AmethystGolemModel<AmethystGolemEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/amethyst_golem.png");
    private static final Identifier OTHER_TEXTURE = new Identifier("adventurez:textures/entity/deepslate_amethyst_golem.png");

    public AmethystGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new AmethystGolemModel<>(context.getPart(RenderInit.AMETHYST_GOLEM_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTexture(AmethystGolemEntity amethystGolemEntity) {
        if (amethystGolemEntity.getDataTracker().get(AmethystGolemEntity.DEEPSLATE_VARIANT))
            return OTHER_TEXTURE;
        else
            return TEXTURE;
    }
}
