package net.adventurez.entity.render;

import net.adventurez.entity.DeerEntity;
import net.adventurez.entity.model.DeerModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DeerRenderer extends MobEntityRenderer<DeerEntity, DeerModel<DeerEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/deer.png");

    public DeerRenderer(EntityRendererFactory.Context context) {
        super(context, new DeerModel<>(context.getPart(RenderInit.DEER_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTexture(DeerEntity deerEntity) {
        return TEXTURE;
    }
}
