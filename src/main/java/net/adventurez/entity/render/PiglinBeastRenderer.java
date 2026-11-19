package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.PiglinBeastEntity;
import net.adventurez.entity.model.PiglinBeastModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class PiglinBeastRenderer extends MobRenderer<PiglinBeastEntity, PiglinBeastModel<PiglinBeastEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/piglin_beast.png");

    public PiglinBeastRenderer(EntityRendererProvider.Context context) {
        super(context, new PiglinBeastModel<>(context.bakeLayer(RenderInit.PIGLIN_BEAST_LAYER)), 1F);
    }

    @Override
    public void scale(PiglinBeastEntity beast, PoseStack matrixStack, float f) {
        matrixStack.scale(1.1F, 1.1F, 1.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(PiglinBeastEntity piglinBeast) {
        return TEXTURE;
    }
}
