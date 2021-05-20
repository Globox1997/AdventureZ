package net.adventurez.entity.render;

import net.adventurez.entity.MammothEntity;
import net.adventurez.entity.model.MammothModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MammothRenderer extends MobEntityRenderer<MammothEntity, MammothModel<MammothEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/mammoth.png");

    public MammothRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new MammothModel<>(), 1.2F);
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
