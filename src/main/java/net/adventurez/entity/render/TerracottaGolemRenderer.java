package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.TerracottaGolemEntity;
import net.adventurez.entity.model.TerracottaGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TerracottaGolemRenderer extends MobRenderer<TerracottaGolemEntity, TerracottaGolemModel<TerracottaGolemEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/terracotta_golem.png");

    public TerracottaGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new TerracottaGolemModel<>(context.bakeLayer(RenderInit.TERRACOTTA_GOLEM_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(TerracottaGolemEntity terracottaGolemEntity) {
        return TEXTURE;
    }
}
