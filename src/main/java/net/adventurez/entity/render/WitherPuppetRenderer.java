package net.adventurez.entity.render;

import net.adventurez.entity.WitherPuppetEntity;
import net.adventurez.entity.model.WitherPuppetModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WitherPuppetRenderer extends BipedEntityRenderer<WitherPuppetEntity, WitherPuppetModel<WitherPuppetEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/wither_puppet.png");

    public WitherPuppetRenderer(EntityRendererFactory.Context context) {
        super(context, new WitherPuppetModel<>(context.getPart(RenderInit.WITHER_PUPPET_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTexture(WitherPuppetEntity witherPuppetEntity) {
        return TEXTURE;
    }

}