package net.adventurez.entity.render;

import net.adventurez.entity.NightmareEntity;
import net.adventurez.entity.render.feature.NightmareEyesFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class NightmareRenderer extends AbstractHorseEntityRenderer<NightmareEntity, HorseEntityModel<NightmareEntity>> {

    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/nightmare.png");

    public NightmareRenderer(EntityRendererFactory.Context context) {
        super(context, new HorseEntityModel<NightmareEntity>(context.getPart(EntityModelLayers.HORSE)), 1.1F);
        this.addFeature(new NightmareEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(NightmareEntity nightmareEntity) {
        return TEXTURE;
    }
}
