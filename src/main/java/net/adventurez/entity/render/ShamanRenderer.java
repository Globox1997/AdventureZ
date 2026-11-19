package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.ShamanEntity;
import net.adventurez.entity.model.ShamanModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ShamanRenderer extends MobRenderer<ShamanEntity, ShamanModel<ShamanEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/shaman.png");

    public ShamanRenderer(EntityRendererProvider.Context context) {
        super(context, new ShamanModel<>(context.bakeLayer(RenderInit.SHAMAN_LAYER)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(ShamanEntity shamanEntity) {
        return TEXTURE;
    }
}
