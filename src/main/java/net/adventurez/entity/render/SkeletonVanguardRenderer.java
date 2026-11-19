package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.SkeletonVanguardEntity;
import net.adventurez.entity.model.SkeletonVanguardModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class SkeletonVanguardRenderer extends MobRenderer<SkeletonVanguardEntity, SkeletonVanguardModel<SkeletonVanguardEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/skeleton_vanguard.png");

    public SkeletonVanguardRenderer(EntityRendererProvider.Context context) {
        super(context, new SkeletonVanguardModel<>(context.bakeLayer(RenderInit.SKELETON_VANGUARD_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonVanguardEntity skeletonVanguardEntity) {
        return TEXTURE;
    }
}
