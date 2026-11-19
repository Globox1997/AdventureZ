package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.EnderWhaleEntity;
import net.adventurez.entity.model.EnderWhaleModel;
import net.adventurez.entity.render.feature.EnderWhaleGlowFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class EnderWhaleRenderer extends MobRenderer<EnderWhaleEntity, EnderWhaleModel<EnderWhaleEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/ender_whale.png");

    public EnderWhaleRenderer(EntityRendererProvider.Context context) {
        super(context, new EnderWhaleModel<>(context.bakeLayer(RenderInit.ENDER_WHALE_LAYER)), 2.3F);
        this.addLayer(new EnderWhaleGlowFeatureRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(EnderWhaleEntity enderWhaleEntity) {
        return TEXTURE;
    }
}
