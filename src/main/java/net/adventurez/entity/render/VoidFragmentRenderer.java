package net.adventurez.entity.render;

import net.adventurez.entity.VoidFragmentEntity;
import net.adventurez.entity.model.VoidFragmentModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VoidFragmentRenderer extends MobEntityRenderer<VoidFragmentEntity, VoidFragmentModel<VoidFragmentEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/void_fragment.png");

    public VoidFragmentRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new VoidFragmentModel<>(), 0.5F);
    }

    @Override
    public Identifier getTexture(VoidFragmentEntity voidFragmentEntity) {
        return TEXTURE;
    }
}
