package net.adventurez.entity.render;

import net.adventurez.entity.DragonEntity;
import net.adventurez.entity.model.DragonModel;
import net.adventurez.entity.render.feature.DragonEyesFeatureRenderer;
import net.adventurez.entity.render.feature.DragonSaddleFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DragonRenderer extends MobEntityRenderer<DragonEntity, DragonModel<DragonEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/dragon.png");

    public DragonRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new DragonModel<>(), 0.7F);
        this.addFeature(new DragonEyesFeatureRenderer(this));
        this.addFeature(new DragonSaddleFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(DragonEntity dragonEntity) {
        return TEXTURE;
    }
}
