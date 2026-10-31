package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.CrystalBeetleEntity;
import net.adventurez.entity.model.CrystalBeetleModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CrystalBeetleRenderer extends MobEntityRenderer<CrystalBeetleEntity, CrystalBeetleModel<CrystalBeetleEntity>> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/crystal_beetle.png");

    public CrystalBeetleRenderer(EntityRendererFactory.Context context) {
        super(context, new CrystalBeetleModel<>(context.getPart(RenderInit.CRYSTAL_BEETLE_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTexture(CrystalBeetleEntity amethystGolemEntity) {
       return TEXTURE;
    }
}
