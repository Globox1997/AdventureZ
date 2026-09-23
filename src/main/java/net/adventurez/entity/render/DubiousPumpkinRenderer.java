package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.DubiousPumpkinEntity;
import net.adventurez.entity.model.DubiousPumpkinModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DubiousPumpkinRenderer extends MobEntityRenderer<DubiousPumpkinEntity, DubiousPumpkinModel<DubiousPumpkinEntity>> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/dubious_pumpkin.png");
    private static final Identifier TEXTURE_ANGRY = AdventureMain.identifierOf("textures/entity/dubious_pumpkin_angry.png");

    public DubiousPumpkinRenderer(EntityRendererFactory.Context context) {
        super(context, new DubiousPumpkinModel<>(context.getPart(RenderInit.DUBIOUS_PUMPKIN_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTexture(DubiousPumpkinEntity dubiousPumpkinEntity) {
        if (dubiousPumpkinEntity.getDataTracker().get(DubiousPumpkinEntity.ANGRY)) {
            return TEXTURE_ANGRY;
        }
        return TEXTURE;
    }
}
