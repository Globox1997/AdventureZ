package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.CrystalBeetleEntity;
import net.adventurez.entity.model.CrystalBeetleModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class CrystalBeetleRenderer extends MobRenderer<CrystalBeetleEntity, CrystalBeetleModel<CrystalBeetleEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/crystal_beetle.png");

    public CrystalBeetleRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalBeetleModel<>(context.bakeLayer(RenderInit.CRYSTAL_BEETLE_LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalBeetleEntity amethystGolemEntity) {
       return TEXTURE;
    }
}
