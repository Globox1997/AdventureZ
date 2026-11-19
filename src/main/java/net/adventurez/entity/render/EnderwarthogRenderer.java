package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.EnderwarthogEntity;
import net.adventurez.entity.model.EnderwarthogModel;
import net.adventurez.entity.render.feature.EnderwarthogEyesFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class EnderwarthogRenderer extends MobRenderer<EnderwarthogEntity, EnderwarthogModel<EnderwarthogEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/enderwarthog.png");

    public EnderwarthogRenderer(EntityRendererProvider.Context context) {
        super(context, new EnderwarthogModel<>(context.bakeLayer(RenderInit.ENDERWARTHOG_LAYER)), 1.5F);
        this.addLayer(new EnderwarthogEyesFeatureRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(EnderwarthogEntity enderwarthogEntity) {
        return TEXTURE;
    }
}
