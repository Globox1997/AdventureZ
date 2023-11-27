package net.adventurez.entity.render;

import net.adventurez.entity.BlackstoneGolemEntity;
import net.adventurez.entity.model.BlackstoneGolemModel;
import net.adventurez.entity.render.feature.BlackstoneGolemBlueLavaFeatureRenderer;
import net.adventurez.entity.render.feature.StoneGolemLavaFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BlackstoneGolemRenderer extends MobEntityRenderer<BlackstoneGolemEntity, BlackstoneGolemModel<BlackstoneGolemEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/blackstone_golem.png");

    public BlackstoneGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new BlackstoneGolemModel<>(context.getPart(RenderInit.BLACKSTONE_GOLEM_LAYER)), 1.7F);
        this.addFeature(new StoneGolemLavaFeatureRenderer(this));
        this.addFeature(new BlackstoneGolemBlueLavaFeatureRenderer(this));
    }

    @Override
    public void scale(BlackstoneGolemEntity stone, MatrixStack matrixStack, float f) {
        matrixStack.scale(2.4F, 2.4F, 2.4F);
    }

    @Override
    public Identifier getTexture(BlackstoneGolemEntity stoneGolem) {
        return TEXTURE;
    }
}
