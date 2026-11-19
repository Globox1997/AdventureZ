package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.SkunkEntity;
import net.adventurez.entity.model.SkunkModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class SkunkRenderer extends MobRenderer<SkunkEntity, SkunkModel<SkunkEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/skunk.png");

    public SkunkRenderer(EntityRendererProvider.Context context) {
        super(context, new SkunkModel<>(context.bakeLayer(RenderInit.SKUNK_LAYER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(SkunkEntity skunkEntity) {
        return TEXTURE;
    }
}
