package net.adventurez.entity.render;

import net.adventurez.entity.VoidShadeEntity;
import net.adventurez.entity.model.VoidShadeModel;
import net.adventurez.entity.render.feature.VoidShadeEyesFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VoidShadeRenderer extends MobEntityRenderer<VoidShadeEntity, VoidShadeModel<VoidShadeEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/void_shade.png");

    public VoidShadeRenderer(EntityRendererFactory.Context context) {
        super(context, new VoidShadeModel<>(context.getPart(RenderInit.VOID_SHADE_LAYER)), 0.5F);
        this.addFeature(new VoidShadeEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(VoidShadeEntity voidShadeEntity) {
        return TEXTURE;
    }
}
