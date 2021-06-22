package net.adventurez.entity.render;

import net.adventurez.entity.SkeletonVanguardEntity;
import net.adventurez.entity.model.SkeletonVanguardModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SkeletonVanguardRenderer extends MobEntityRenderer<SkeletonVanguardEntity, SkeletonVanguardModel<SkeletonVanguardEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/skeleton_vanguard.png");

    public SkeletonVanguardRenderer(EntityRendererFactory.Context context) {
        super(context, new SkeletonVanguardModel<>(context.getPart(RenderInit.SKELETON_VANGUARD_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTexture(SkeletonVanguardEntity skeletonVanguardEntity) {
        return TEXTURE;
    }
}
