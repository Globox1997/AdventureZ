package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.adventurez.entity.model.RockModel;
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
import net.minecraft.util.Unit;

@Environment(EnvType.CLIENT)
public class ThrownRockRenderer extends EntityRenderer<ThrownRockEntity, ProjectileRenderState> {
    private static final Identifier GOLEM_TEXTURE = AdventureMain.identifierOf("textures/entity/thrown_golem_rock.png");
    private static final Identifier VOID_TEXTURE = AdventureMain.identifierOf("textures/entity/thrown_void_rock.png");
    private final RockModel model = new RockModel(RockModel.getTexturedModelData().bakeRoot());

    public ThrownRockRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected int getBlockLightLevel(ThrownRockEntity entity, BlockPos blockPos) {
        return entity.level().getMaxLocalRawBrightness(blockPos);
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        matrixStack.pushPose();
        submitNodeCollector.submitModel(this.model, Unit.INSTANCE, matrixStack, this.model.renderType(state.golemRock ? GOLEM_TEXTURE : VOID_TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        matrixStack.popPose();
        super.submit(state, matrixStack, submitNodeCollector, camera);
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(ThrownRockEntity entity, ProjectileRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.golemRock = entity.getItem().getItem() == entity.getDefaultItem();
    }
}
