package net.adventurez.entity.render;

import net.adventurez.entity.SkunkEntity;
import net.adventurez.entity.model.SkunkModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SkunkRenderer extends MobEntityRenderer<SkunkEntity, SkunkModel<SkunkEntity>> {
    private static final Identifier TEXTURE = Identifier.of("adventurez:textures/entity/skunk.png");

    public SkunkRenderer(EntityRendererFactory.Context context) {
        super(context, new SkunkModel<>(context.getPart(RenderInit.SKUNK_LAYER)), 0.4F);
    }

    @Override
    public Identifier getTexture(SkunkEntity skunkEntity) {
        return TEXTURE;
    }
}
