package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.WitherPuppetEntity;
import net.adventurez.entity.model.WitherPuppetModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class WitherPuppetRenderer extends HumanoidMobRenderer<WitherPuppetEntity, WitherPuppetModel<WitherPuppetEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/wither_puppet.png");

    public WitherPuppetRenderer(EntityRendererProvider.Context context) {
        super(context, new WitherPuppetModel<>(context.bakeLayer(RenderInit.WITHER_PUPPET_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(WitherPuppetEntity witherPuppetEntity) {
        return TEXTURE;
    }

}
