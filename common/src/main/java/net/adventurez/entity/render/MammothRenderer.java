package net.adventurez.entity.render;

import net.adventurez.entity.render.state.MammothRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.MammothEntity;
import net.adventurez.entity.model.MammothModel;
import net.adventurez.init.RenderInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class MammothRenderer extends AdventureMobRenderer<MammothEntity, MammothRenderState, MammothModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/mammoth.png");

    public MammothRenderer(EntityRendererProvider.Context context) {
        super(context, new MammothModel(context.bakeLayer(RenderInit.MAMMOTH_LAYER)), 1.2F);
    }

    @Override
    protected void scale(MammothRenderState mammothEntity, PoseStack matrixStack) {
        if (mammothEntity.isBaby) {
            matrixStack.scale(1.15F, 1.15F, 1.15F);
        } else {
            matrixStack.scale(2.1F, 2.1F, 2.1F);
        }
    }

    @Override
    public Identifier getTextureLocation(MammothRenderState mammothEntity) {
        return TEXTURE;
    }

    @Override
    public MammothRenderState createRenderState() {
        return new MammothRenderState();
    }

    @Override
    public void extractRenderState(MammothEntity entity, MammothRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.attackTicks = entity.getEntityData().get(MammothEntity.ATTACK_TICKS);
    }
}
