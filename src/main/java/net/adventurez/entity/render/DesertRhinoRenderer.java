package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.DesertRhinoEntity;
import net.adventurez.entity.model.DesertRhinoModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class DesertRhinoRenderer extends MobRenderer<DesertRhinoEntity, DesertRhinoModel<DesertRhinoEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/desert_rhino.png");

    public DesertRhinoRenderer(EntityRendererProvider.Context context) {
        super(context, new DesertRhinoModel<>(context.bakeLayer(RenderInit.DESERT_RHINO_LAYER)), 1.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(DesertRhinoEntity desertRhinoEntity) {
        return TEXTURE;
    }
}
