package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.nonliving.GildedBlackstoneShardEntity;
import net.adventurez.entity.model.GildedStoneModel;
import net.adventurez.entity.render.state.ProjectileRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Unit;

@Environment(EnvType.CLIENT)
public class GildedStoneRenderer extends EntityRenderer<GildedBlackstoneShardEntity, ProjectileRenderState> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/item/gilded_stone.png");
    private final GildedStoneModel model = new GildedStoneModel(GildedStoneModel.getTexturedModelData().bakeRoot());

    public GildedStoneRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected int getBlockLightLevel(GildedBlackstoneShardEntity entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        matrixStack.pushPose();
        matrixStack.scale(1.0F, -1.0F, 1.0F);
        matrixStack.translate(0D, -1.45D, 0D);
        submitNodeCollector.submitModel(this.model, Unit.INSTANCE, matrixStack, this.model.renderType(TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        matrixStack.popPose();
        super.submit(state, matrixStack, submitNodeCollector, camera);
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(GildedBlackstoneShardEntity entity, ProjectileRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.lightCoords = LightCoordsUtil.getLightCoords(entity.level(), entity.blockPosition().above());
    }
}
