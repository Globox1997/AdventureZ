package net.adventurez.entity.render;

import net.adventurez.entity.OrcEntity;
import net.adventurez.entity.model.OrcModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OrcRenderer extends MobEntityRenderer<OrcEntity, OrcModel<OrcEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/ork.png");

    public OrcRenderer(EntityRendererFactory.Context context) {
        super(context, new OrcModel<>(context.getPart(RenderInit.ORC_LAYER)), 0.7F);
    }

    @Override
    public void render(OrcEntity orcEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.5F * (float) orcEntity.getSize();
        super.render(orcEntity, f, g, matrixStack, vertexConsumerProvider, i);
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
