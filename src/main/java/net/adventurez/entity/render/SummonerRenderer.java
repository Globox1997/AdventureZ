package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.SummonerEntity;
import net.adventurez.entity.model.SummonerModel;
import net.adventurez.entity.render.feature.SummonerEntityShieldFeatureRenderer;
import net.adventurez.entity.render.feature.SummonerEyesFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class SummonerRenderer extends MobRenderer<SummonerEntity, SummonerModel<SummonerEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/summoner.png");

    public SummonerRenderer(EntityRendererProvider.Context context) {
        super(context, new SummonerModel<>(context.bakeLayer(RenderInit.SUMMONER_LAYER)), 0.7F);
        this.addLayer(new SummonerEntityShieldFeatureRenderer(this));
        this.addLayer(new SummonerEyesFeatureRenderer(this));
    }

    @Override
    public void scale(SummonerEntity summonerEntity, PoseStack matrixStack, float f) {
        matrixStack.scale(1.3F, 1.3F, 1.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(SummonerEntity summonerEntity) {
        return TEXTURE;
    }
}
