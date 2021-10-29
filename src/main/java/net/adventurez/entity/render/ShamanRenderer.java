package net.adventurez.entity.render;

import net.adventurez.entity.ShamanEntity;
import net.adventurez.entity.model.ShamanModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ShamanRenderer extends MobEntityRenderer<ShamanEntity, ShamanModel<ShamanEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/shaman.png");

    public ShamanRenderer(EntityRendererFactory.Context context) {
        super(context, new ShamanModel<>(context.getPart(RenderInit.SHAMAN_LAYER)), 0.6F);
    }

    @Override
    public Identifier getTexture(ShamanEntity shamanEntity) {
        return TEXTURE;
    }
}
