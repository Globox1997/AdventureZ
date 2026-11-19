package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.adventurez.AdventureMain;
import net.adventurez.entity.BlazeGuardianEntity;
import net.adventurez.entity.model.BlazeGuardianModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class BlazeGuardianRenderer extends MobRenderer<BlazeGuardianEntity, BlazeGuardianModel<BlazeGuardianEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/blaze_guardian.png");

    public BlazeGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new BlazeGuardianModel<>(context.bakeLayer(RenderInit.BLAZE_GUARDIAN_LAYER)), 0.7F);
    }

    @Override
    public void scale(BlazeGuardianEntity blazeGuardianEntity, PoseStack matrixStack, float f) {
        matrixStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(BlazeGuardianEntity blazeGuardianEntity) {
        return TEXTURE;
    }
}
