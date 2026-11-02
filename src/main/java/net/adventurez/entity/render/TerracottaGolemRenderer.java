package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.TerracottaGolemEntity;
import net.adventurez.entity.model.TerracottaGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TerracottaGolemRenderer extends MobEntityRenderer<TerracottaGolemEntity, TerracottaGolemModel<TerracottaGolemEntity>> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/terracotta_golem.png");

    public TerracottaGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new TerracottaGolemModel<>(context.getPart(RenderInit.TERRACOTTA_GOLEM_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTexture(TerracottaGolemEntity terracottaGolemEntity) {
        return TEXTURE;
    }
}
