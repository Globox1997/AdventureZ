package net.adventurez.entity.render;

import net.adventurez.entity.render.state.PiglinBeastRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.PiglinBeastEntity;
import net.adventurez.entity.model.PiglinBeastModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class PiglinBeastRenderer extends AdventureMobRenderer<PiglinBeastEntity, PiglinBeastRenderState, PiglinBeastModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/piglin_beast.png");

    public PiglinBeastRenderer(EntityRendererProvider.Context context) {
        super(context, new PiglinBeastModel(context.bakeLayer(RenderInit.PIGLIN_BEAST_LAYER)), 1F);
    }

    @Override
    protected void scale(PiglinBeastRenderState beast, PoseStack matrixStack) {
        matrixStack.scale(1.1F, 1.1F, 1.1F);
    }

    @Override
    public Identifier getTextureLocation(PiglinBeastRenderState piglinBeast) {
        return TEXTURE;
    }

    @Override
    public PiglinBeastRenderState createRenderState() {
        return new PiglinBeastRenderState();
    }

    @Override
    public void extractRenderState(PiglinBeastEntity entity, PiglinBeastRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.attackAnim = entity.getSwingAnimation(1.0F);
        state.leadArm = entity.getEntityData().get(PiglinBeastEntity.LEAD_ARM);
    }
}
