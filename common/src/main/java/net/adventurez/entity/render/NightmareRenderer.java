package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.adventurez.AdventureMain;
import net.adventurez.entity.NightmareEntity;
import net.adventurez.entity.render.feature.NightmareEyesFeatureRenderer;
import net.minecraft.client.model.animal.equine.AbstractEquineModel;
import net.minecraft.client.model.animal.equine.BabyHorseModel;
import net.minecraft.client.model.animal.equine.EquineSaddleModel;
import net.minecraft.client.model.animal.equine.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;

public final class NightmareRenderer extends AbstractHorseRenderer<NightmareEntity, EquineRenderState, AbstractEquineModel<EquineRenderState>> {

    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/nightmare.png");

    public NightmareRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel(context.bakeLayer(ModelLayers.HORSE)), new BabyHorseModel(context.bakeLayer(ModelLayers.HORSE_BABY)));
        this.addLayer(new SimpleEquipmentLayer<>(this, context.getEquipmentRenderer(), EquipmentClientInfo.LayerType.HORSE_SADDLE, state -> state.saddle,
                new EquineSaddleModel(context.bakeLayer(ModelLayers.HORSE_SADDLE)), null, 2));
        this.addLayer(new NightmareEyesFeatureRenderer(this));
    }

    @Override
    protected void scale(EquineRenderState state, PoseStack matrixStack) {
        matrixStack.scale(1.1F, 1.1F, 1.1F);
    }

    @Override
    public Identifier getTextureLocation(EquineRenderState state) {
        return TEXTURE;
    }

    @Override
    public EquineRenderState createRenderState() {
        return new EquineRenderState();
    }
}
