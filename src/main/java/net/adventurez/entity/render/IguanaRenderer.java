package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.IguanaEntity;
import net.adventurez.entity.model.IguanaModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class IguanaRenderer extends MobRenderer<IguanaEntity, IguanaModel<IguanaEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/iguana.png");

    public IguanaRenderer(EntityRendererProvider.Context context) {
        super(context, new IguanaModel<>(context.bakeLayer(RenderInit.IGUANA_LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(IguanaEntity iguanaEntity) {
        return TEXTURE;
    }
}
