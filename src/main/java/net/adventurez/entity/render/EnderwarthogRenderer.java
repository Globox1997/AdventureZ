package net.adventurez.entity.render;

import net.adventurez.entity.EnderwarthogEntity;
import net.adventurez.entity.model.EnderwarthogModel;
import net.adventurez.entity.render.feature.EnderwarthogEyesFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EnderwarthogRenderer extends MobEntityRenderer<EnderwarthogEntity, EnderwarthogModel<EnderwarthogEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/enderwarthog.png");

    public EnderwarthogRenderer(EntityRendererFactory.Context context) {
        super(context, new EnderwarthogModel<>(context.getPart(RenderInit.ENDERWARTHOG_LAYER)), 1.5F);
        this.addFeature(new EnderwarthogEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(EnderwarthogEntity enderwarthogEntity) {
        return TEXTURE;
    }
}
