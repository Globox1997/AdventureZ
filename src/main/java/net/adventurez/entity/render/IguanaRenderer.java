package net.adventurez.entity.render;

import net.adventurez.entity.IguanaEntity;
import net.adventurez.entity.model.IguanaModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class IguanaRenderer extends MobEntityRenderer<IguanaEntity, IguanaModel<IguanaEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/iguana.png");

    public IguanaRenderer(EntityRendererFactory.Context context) {
        super(context, new IguanaModel<>(context.getPart(RenderInit.IGUANA_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTexture(IguanaEntity iguanaEntity) {
        return TEXTURE;
    }
}
