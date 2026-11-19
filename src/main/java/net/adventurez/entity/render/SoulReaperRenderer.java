package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.SoulReaperEntity;
import net.adventurez.entity.model.SoulReaperModel;
import net.adventurez.entity.render.feature.SoulReaperEyesFeatureRenderer;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class SoulReaperRenderer extends HumanoidMobRenderer<SoulReaperEntity, SoulReaperModel<SoulReaperEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/soul_reaper.png");

    public SoulReaperRenderer(EntityRendererProvider.Context context) {
        super(context, new SoulReaperModel<>(context.bakeLayer(RenderInit.SOUL_REAPER_LAYER)), 0.5F);
        this.addLayer(new SoulReaperEyesFeatureRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SoulReaperEntity soulReaperEntity) {
        return TEXTURE;
    }

    @Override
    public void scale(SoulReaperEntity soulReaperEntity, PoseStack matrixStack, float f) {
        matrixStack.scale(1.2F, 1.2F, 1.2F);
    }

}
