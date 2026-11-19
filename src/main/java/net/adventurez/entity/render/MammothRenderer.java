package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.MammothEntity;
import net.adventurez.entity.model.MammothModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class MammothRenderer extends MobRenderer<MammothEntity, MammothModel<MammothEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/mammoth.png");

    public MammothRenderer(EntityRendererProvider.Context context) {
        super(context, new MammothModel<>(context.bakeLayer(RenderInit.MAMMOTH_LAYER)), 1.2F);
    }

    @Override
    public void scale(MammothEntity mammothEntity, PoseStack matrixStack, float f) {
        if (mammothEntity.isBaby()) {
            matrixStack.scale(1.15F, 1.15F, 1.15F);
        } else {
            matrixStack.scale(2.1F, 2.1F, 2.1F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(MammothEntity mammothEntity) {
        return TEXTURE;
    }
}
