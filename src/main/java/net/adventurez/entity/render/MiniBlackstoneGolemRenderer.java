package net.adventurez.entity.render;

import net.adventurez.AdventureMain;
import net.adventurez.entity.MiniBlackstoneGolemEntity;
import net.adventurez.entity.model.MiniBlackstoneGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class MiniBlackstoneGolemRenderer extends MobRenderer<MiniBlackstoneGolemEntity, MiniBlackstoneGolemModel<MiniBlackstoneGolemEntity>> {
    private static final ResourceLocation TEXTURE = AdventureMain.identifierOf("textures/entity/mini_blackstone_golem.png");

    public MiniBlackstoneGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new MiniBlackstoneGolemModel<>(context.bakeLayer(RenderInit.MINI_BLACKSTONE_GOLEM_LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(MiniBlackstoneGolemEntity stoneGolem) {
        return TEXTURE;
    }
}
