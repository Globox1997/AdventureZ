package net.adventurez.entity.render;

import net.adventurez.entity.render.state.BlazeGuardianRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.BlazeGuardianEntity;
import net.adventurez.entity.model.BlazeGuardianModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class BlazeGuardianRenderer extends AdventureMobRenderer<BlazeGuardianEntity, BlazeGuardianRenderState, BlazeGuardianModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/blaze_guardian.png");

    public BlazeGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new BlazeGuardianModel(context.bakeLayer(RenderInit.BLAZE_GUARDIAN_LAYER)), 0.7F);
    }

    @Override
    protected void scale(BlazeGuardianRenderState blazeGuardianEntity, PoseStack matrixStack) {
        matrixStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    public Identifier getTextureLocation(BlazeGuardianRenderState blazeGuardianEntity) {
        return TEXTURE;
    }

    @Override
    public BlazeGuardianRenderState createRenderState() {
        return new BlazeGuardianRenderState();
    }

    @Override
    public void extractRenderState(BlazeGuardianEntity entity, BlazeGuardianRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.shieldEast = entity.getEntityData().get(BlazeGuardianEntity.SHIELD_EAST);
        state.shieldNorth = entity.getEntityData().get(BlazeGuardianEntity.SHIELD_NORTH);
        state.shieldSouth = entity.getEntityData().get(BlazeGuardianEntity.SHIELD_SOUTH);
        state.shieldWest = entity.getEntityData().get(BlazeGuardianEntity.SHIELD_WEST);
    }
}
