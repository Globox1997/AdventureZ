package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.VoidShadeEntity;
import net.adventurez.entity.model.VoidShadeModel;
import net.adventurez.entity.render.feature.VoidShadeEyesFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class VoidShadeRenderer extends MobRenderer<VoidShadeEntity, VoidShadeModel<VoidShadeEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/void_shade.png");

    public VoidShadeRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidShadeModel<>(context.bakeLayer(RenderInit.VOID_SHADE_LAYER)), 0.5F);
        this.addLayer(new VoidShadeEyesFeatureRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(VoidShadeEntity voidShadeEntity) {
        return TEXTURE;
    }
}
