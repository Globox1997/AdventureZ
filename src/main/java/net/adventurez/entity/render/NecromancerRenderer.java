package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.NecromancerEntity;
import net.adventurez.entity.model.NecromancerModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class NecromancerRenderer extends MobRenderer<NecromancerEntity, NecromancerModel<NecromancerEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/necromancer.png");

    public NecromancerRenderer(EntityRendererProvider.Context context) {
        super(context, new NecromancerModel<>(context.bakeLayer(RenderInit.NECROMANCER_LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(NecromancerEntity necromancerEntity) {
        return TEXTURE;
    }
}
