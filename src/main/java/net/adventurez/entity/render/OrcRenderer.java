package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.adventurez.AdventureMain;
import net.adventurez.entity.OrcEntity;
import net.adventurez.entity.model.OrcModel;
import net.adventurez.entity.render.feature.OrcInventoryFeatureRenderer;
import net.adventurez.entity.render.state.OrcRenderState;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;

@Environment(EnvType.CLIENT)
public class OrcRenderer extends AdventureMobRenderer<OrcEntity, OrcRenderState, OrcModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/orc.png");

    public OrcRenderer(EntityRendererProvider.Context context) {
        super(context, new OrcModel(context.bakeLayer(RenderInit.ORC_LAYER)), 0.7F);
        this.addLayer(new OrcInventoryFeatureRenderer(this));
    }

    @Override
    protected float getShadowRadius(OrcRenderState state) {
        return 0.5F * (float) state.size;
    }

    @Override
    protected void scale(OrcRenderState orcEntity, PoseStack matrixStack) {
        matrixStack.scale(0.55F * orcEntity.size, 0.55F * orcEntity.size, 0.55F * orcEntity.size);
    }

    @Override
    public Identifier getTextureLocation(OrcRenderState orcEntity) {
        return TEXTURE;
    }

    @Override
    public OrcRenderState createRenderState() {
        return new OrcRenderState();
    }

    @Override
    public void extractRenderState(OrcEntity entity, OrcRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.attackAnim = entity.getSwingAnimation(1.0F);
        state.doubleHandAttack = entity.getEntityData().get(OrcEntity.DOUBLE_HAND_ATTACK);
        state.size = entity.getSize();
        state.bigOrc = entity.isBigOrc();
        int itemId = entity.getEntityData().get(OrcEntity.INVENTORY_ITEM_ID);
        if (!state.bigOrc && itemId != -1) {
            this.itemModelResolver.updateForLiving(state.inventoryItem, BuiltInRegistries.ITEM.byId(itemId).getDefaultInstance(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, entity);
        } else {
            state.inventoryItem.clear();
        }
    }
}
