package net.adventurez.entity.render;

import net.adventurez.entity.OrcEntity;
import net.adventurez.entity.model.OrcModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OrcRenderer extends MobEntityRenderer<OrcEntity, OrcModel<OrcEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/ork.png");

    public OrcRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new OrcModel<>(), 0.7F);
    }

    @Override
    public void scale(OrcEntity orcEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.55F * orcEntity.getSize(), 0.55F * orcEntity.getSize(), 0.55F * orcEntity.getSize());
    }

    @Override
    public Identifier getTexture(OrcEntity orcEntity) {
        return TEXTURE;
    }
}
