package net.adventurez.entity.render;

import net.adventurez.entity.render.state.SummonerRenderState;
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
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class SummonerRenderer extends AdventureMobRenderer<SummonerEntity, SummonerRenderState, SummonerModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/summoner.png");

    public SummonerRenderer(EntityRendererProvider.Context context) {
        super(context, new SummonerModel(context.bakeLayer(RenderInit.SUMMONER_LAYER)), 0.7F);
        this.addLayer(new SummonerEntityShieldFeatureRenderer(this));
        this.addLayer(new SummonerEyesFeatureRenderer(this));
    }

    @Override
    protected void scale(SummonerRenderState summonerEntity, PoseStack matrixStack) {
        matrixStack.scale(1.3F, 1.3F, 1.3F);
    }

    @Override
    public Identifier getTextureLocation(SummonerRenderState summonerEntity) {
        return TEXTURE;
    }

    @Override
    public SummonerRenderState createRenderState() {
        return new SummonerRenderState();
    }

    @Override
    public void extractRenderState(SummonerEntity entity, SummonerRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.spellcasting = entity.isSpellcasting();
        state.invulnerableShield = entity.getEntityData().get(SummonerEntity.INVULNERABLE_SHIELD);
    }
}
