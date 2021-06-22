package net.adventurez.entity.render;

import net.adventurez.entity.BrownFungusEntity;
import net.adventurez.entity.model.BrownFungusModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BrownFungusRenderer extends MobEntityRenderer<BrownFungusEntity, BrownFungusModel<BrownFungusEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/brown_fungus.png");

    public BrownFungusRenderer(EntityRendererFactory.Context context) {
        super(context, new BrownFungusModel<>(context.getPart(RenderInit.BROWN_FUNGUS_LAYER)), 0.4F);
    }

    @Override
    public void scale(BrownFungusEntity brownFungusEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.3F, 1.3F, 1.3F);
    }

    @Override
    public Identifier getTexture(BrownFungusEntity fungusEntity) {
        return TEXTURE;
    }
}
