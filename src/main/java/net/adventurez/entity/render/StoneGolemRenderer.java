package net.adventurez.entity.render;

import net.adventurez.entity.StoneGolemEntity;
import net.adventurez.entity.model.StoneGolemModel;
import net.adventurez.entity.render.feature.StoneGolemBlueLavaFeatureRenderer;
import net.adventurez.entity.render.feature.StoneGolemLavaFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class StoneGolemRenderer extends MobEntityRenderer<StoneGolemEntity, StoneGolemModel<StoneGolemEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/stone_golem.png");

    public StoneGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new StoneGolemModel<>(context.getPart(RenderInit.STONE_GOLEM_LAYER)), 1.7F);
        this.addFeature(new StoneGolemLavaFeatureRenderer(this));
        this.addFeature(new StoneGolemBlueLavaFeatureRenderer(this));
    }

    @Override
    public void scale(StoneGolemEntity stone, MatrixStack matrixStack, float f) {
        matrixStack.scale(2.4F, 2.4F, 2.4F);
    }

    @Override
    public Identifier getTexture(StoneGolemEntity stoneGolem) {
        return TEXTURE;
    }
}
