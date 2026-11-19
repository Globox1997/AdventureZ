package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.DubiousPumpkinEntity;
import net.adventurez.entity.model.DubiousPumpkinModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class DubiousPumpkinRenderer extends MobRenderer<DubiousPumpkinEntity, DubiousPumpkinModel<DubiousPumpkinEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/dubious_pumpkin.png");
    private static final ResourceLocation TEXTURE_ANGRY = AdventureMain.identifierOf("textures/entity/dubious_pumpkin_angry.png");

    public DubiousPumpkinRenderer(EntityRendererProvider.Context context) {
        super(context, new DubiousPumpkinModel<>(context.bakeLayer(RenderInit.DUBIOUS_PUMPKIN_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(DubiousPumpkinEntity dubiousPumpkinEntity) {
        if (dubiousPumpkinEntity.getEntityData().get(DubiousPumpkinEntity.ANGRY)) {
            return TEXTURE_ANGRY;
        }
        return TEXTURE;
    }
}
