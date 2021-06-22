package net.adventurez.entity.render;

import net.adventurez.entity.MammothEntity;
import net.adventurez.entity.model.MammothModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MammothRenderer extends MobEntityRenderer<MammothEntity, MammothModel<MammothEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/mammoth.png");

    public MammothRenderer(EntityRendererFactory.Context context) {
        super(context, new MammothModel<>(context.getPart(RenderInit.MAMMOTH_LAYER)), 1.2F);
    }

    @Override
    public void scale(MammothEntity mammothEntity, MatrixStack matrixStack, float f) {
        if (mammothEntity.isBaby()) {
            matrixStack.scale(1.15F, 1.15F, 1.15F);
        } else
            matrixStack.scale(2.1F, 2.1F, 2.1F);
    }

    @Override
    public Identifier getTexture(MammothEntity mammothEntity) {
        return TEXTURE;
    }
}
