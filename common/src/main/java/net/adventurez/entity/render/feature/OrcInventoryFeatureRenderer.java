package net.adventurez.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.adventurez.entity.model.OrcModel;
import net.adventurez.entity.render.state.OrcRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class OrcInventoryFeatureRenderer extends RenderLayer<OrcRenderState, OrcModel> {

    public OrcInventoryFeatureRenderer(RenderLayerParent<OrcRenderState, OrcModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, OrcRenderState state, float yRot, float xRot) {
        if (!state.bigOrc && !state.inventoryItem.isEmpty()) {
            matrixStack.pushPose();
            ModelPart modelPart = this.getParentModel().getTorso();
            modelPart.translateAndRotate(matrixStack);
            matrixStack.rotate(Axis.YP.rotationDegrees(90F));
            matrixStack.translate(-0.295D, -0.3D, 0.0D);
            state.inventoryItem.submit(matrixStack, submitNodeCollector, i, OverlayTexture.NO_OVERLAY, state.outlineColor);
            matrixStack.popPose();
        }
    }
}
