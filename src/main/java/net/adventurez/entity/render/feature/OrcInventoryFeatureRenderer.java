package net.adventurez.entity.render.feature;

import net.adventurez.entity.OrcEntity;
import net.adventurez.entity.model.OrcModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class OrcInventoryFeatureRenderer extends FeatureRenderer<OrcEntity, OrcModel<OrcEntity>> {

    public OrcInventoryFeatureRenderer(FeatureRendererContext<OrcEntity, OrcModel<OrcEntity>> featureRendererContext) {
        super(featureRendererContext);

    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, OrcEntity orcEntity, float f, float g, float h, float j, float k, float l) {
        int itemId = orcEntity.getDataTracker().get(OrcEntity.INVENTORY_ITEM_ID);
        if (!orcEntity.isBigOrc() && itemId != -1) {
            matrixStack.push();
            ModelPart modelPart = this.getContextModel().getTorso();
            modelPart.rotate(matrixStack);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
            matrixStack.translate(-0.295D, -0.3D, 0.0D);
            MinecraftClient.getInstance().getEntityRenderDispatcher().getHeldItemRenderer().renderItem(orcEntity, Registry.ITEM.get(itemId).getDefaultStack(),
                    ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }
    }

}
