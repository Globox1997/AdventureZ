package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.OrcEntity;
import net.adventurez.entity.model.OrcModel;
import net.adventurez.entity.render.feature.OrcInventoryFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class OrcRenderer extends MobRenderer<OrcEntity, OrcModel<OrcEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/orc.png");

    public OrcRenderer(EntityRendererProvider.Context context) {
        super(context, new OrcModel<>(context.bakeLayer(RenderInit.ORC_LAYER)), 0.7F);
        this.addLayer(new OrcInventoryFeatureRenderer(this));
    }

    @Override
    public void render(OrcEntity orcEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        this.shadowRadius = 0.5F * (float) orcEntity.getSize();
        super.render(orcEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public void scale(OrcEntity orcEntity, PoseStack matrixStack, float f) {
        matrixStack.scale(0.55F * orcEntity.getSize(), 0.55F * orcEntity.getSize(), 0.55F * orcEntity.getSize());
    }

    @Override
    public ResourceLocation getTextureLocation(OrcEntity orcEntity) {
        return TEXTURE;
    }
}
