package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.WitherPuppetEntity;
import net.adventurez.entity.model.WitherPuppetModel;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class WitherPuppetRenderer extends HumanoidMobRenderer<WitherPuppetEntity, HumanoidRenderState, WitherPuppetModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/wither_puppet.png");

    public WitherPuppetRenderer(EntityRendererProvider.Context context) {
        super(context, new WitherPuppetModel(context.bakeLayer(RenderInit.WITHER_PUPPET_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState witherPuppetEntity) {
        return TEXTURE;
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }
}
