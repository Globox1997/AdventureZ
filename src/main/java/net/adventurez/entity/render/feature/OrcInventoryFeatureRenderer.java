package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.adventurez.entity.OrcEntity;
import net.adventurez.entity.model.OrcModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemDisplayContext;

@Environment(EnvType.CLIENT)
public class OrcInventoryFeatureRenderer extends RenderLayer<OrcEntity, OrcModel<OrcEntity>> {

    public OrcInventoryFeatureRenderer(RenderLayerParent<OrcEntity, OrcModel<OrcEntity>> featureRendererContext) {
        super(featureRendererContext);

    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, OrcEntity orcEntity, float f, float g, float h, float j, float k, float l) {
        int itemId = orcEntity.getEntityData().get(OrcEntity.INVENTORY_ITEM_ID);
        if (!orcEntity.isBigOrc() && itemId != -1) {
            matrixStack.pushPose();
            ModelPart modelPart = this.getParentModel().getTorso();
            modelPart.translateAndRotate(matrixStack);
            matrixStack.mulPose(Axis.YP.rotationDegrees(90F));
            matrixStack.translate(-0.295D, -0.3D, 0.0D);
            Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(orcEntity, BuiltInRegistries.ITEM.byId(itemId).getDefaultInstance(),
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
            matrixStack.popPose();
        }
    }

}
