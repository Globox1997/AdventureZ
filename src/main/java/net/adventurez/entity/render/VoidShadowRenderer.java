package net.adventurez.entity.render;

import net.adventurez.entity.VoidShadowEntity;
import net.adventurez.entity.model.VoidShadowModel;
import net.adventurez.entity.render.feature.VoidShadowBlackFeatureRenderer;
import net.adventurez.entity.render.feature.VoidShadowEyesFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VoidShadowRenderer extends MobEntityRenderer<VoidShadowEntity, VoidShadowModel<VoidShadowEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/void_shadow.png");

    public VoidShadowRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new VoidShadowModel<>(), 1.7F);
        this.addFeature(new VoidShadowEyesFeatureRenderer(this));
        this.addFeature(new VoidShadowBlackFeatureRenderer(this));
    }

    @Override
    public void scale(VoidShadowEntity voidShadowEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(3.0F, 3.0F, 3.0F);
    }

    @Override
    public Identifier getTexture(VoidShadowEntity voidShadowEntity) {
        return TEXTURE;
    }
}
