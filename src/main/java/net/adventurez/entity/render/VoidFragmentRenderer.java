package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.VoidFragmentEntity;
import net.adventurez.entity.model.VoidFragmentModel;
import net.adventurez.entity.render.feature.VoidFragmentCorruptFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class VoidFragmentRenderer extends MobRenderer<VoidFragmentEntity, VoidFragmentModel<VoidFragmentEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/void_fragment.png");

    public VoidFragmentRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidFragmentModel<>(context.bakeLayer(RenderInit.VOID_FRAGMENT_LAYER)), 0.5F);
        this.addLayer(new VoidFragmentCorruptFeatureRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(VoidFragmentEntity voidFragmentEntity) {
        return TEXTURE;
    }
}
