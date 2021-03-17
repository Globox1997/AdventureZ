package net.adventurez.entity.render;

import net.adventurez.entity.OrkEntity;
import net.adventurez.entity.model.OrkModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OrkRenderer extends MobEntityRenderer<OrkEntity, OrkModel<OrkEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/ork.png");

    public OrkRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new OrkModel<>(), 0.7F);
    }

    @Override
    public void scale(OrkEntity orkEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.55F * orkEntity.getSize(), 0.55F * orkEntity.getSize(), 0.55F * orkEntity.getSize());
    }

    @Override
    public Identifier getTexture(OrkEntity orkEntity) {
        return TEXTURE;
    }
}
