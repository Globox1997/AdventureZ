package net.adventurez.entity.render;

import net.adventurez.entity.render.state.AdventureRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.BrownFungusEntity;
import net.adventurez.entity.model.BrownFungusModel;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class BrownFungusRenderer extends AdventureMobRenderer<BrownFungusEntity, AdventureRenderState, BrownFungusModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/brown_fungus.png");

    public BrownFungusRenderer(EntityRendererProvider.Context context) {
        super(context, new BrownFungusModel(context.bakeLayer(RenderInit.BROWN_FUNGUS_LAYER)), 0.4F);
    }

    @Override
    protected void scale(AdventureRenderState brownFungusEntity, PoseStack matrixStack) {
        matrixStack.scale(1.3F, 1.3F, 1.3F);
    }

    @Override
    public Identifier getTextureLocation(AdventureRenderState fungusEntity) {
        return TEXTURE;
    }

    @Override
    public AdventureRenderState createRenderState() {
        return new AdventureRenderState();
    }
}
