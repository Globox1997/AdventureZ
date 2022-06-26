package net.adventurez.entity.render;

import net.adventurez.entity.DragonEntity;
import net.adventurez.entity.model.DragonModel;
import net.adventurez.entity.render.feature.DragonEyesFeatureRenderer;
import net.adventurez.entity.render.feature.DragonSaddleFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DragonRenderer extends MobEntityRenderer<DragonEntity, DragonModel<DragonEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/dragon.png");
    private static final Identifier RED_DRAGON_TEXTURE = new Identifier("adventurez:textures/entity/red_dragon.png");

    public DragonRenderer(EntityRendererFactory.Context context) {
        super(context, new DragonModel<>(context.getPart(RenderInit.DRAGON_LAYER)), 0.4F);
        this.addFeature(new DragonEyesFeatureRenderer(this));
        this.addFeature(new DragonSaddleFeatureRenderer(this));
    }

    @Override
    public void render(DragonEntity dragonEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.6F * (float) dragonEntity.getSize();
        super.render(dragonEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public void scale(DragonEntity dragonEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale((float) dragonEntity.getSize() / 3.0F, (float) dragonEntity.getSize() / 3.0F, (float) dragonEntity.getSize() / 3.0F);
    }

    @Override
    public Identifier getTexture(DragonEntity dragonEntity) {
        if (dragonEntity.getDataTracker().get(DragonEntity.RED_DRAGON)) {
            return RED_DRAGON_TEXTURE;
        }
        return TEXTURE;
    }
}
