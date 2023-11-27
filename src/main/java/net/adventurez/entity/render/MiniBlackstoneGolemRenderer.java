package net.adventurez.entity.render;

import net.adventurez.entity.MiniBlackstoneGolemEntity;
import net.adventurez.entity.model.MiniBlackstoneGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MiniBlackstoneGolemRenderer extends MobEntityRenderer<MiniBlackstoneGolemEntity, MiniBlackstoneGolemModel<MiniBlackstoneGolemEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/mini_blackstone_golem.png");

    public MiniBlackstoneGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new MiniBlackstoneGolemModel<>(context.getPart(RenderInit.MINI_BLACKSTONE_GOLEM_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTexture(MiniBlackstoneGolemEntity stoneGolem) {
        return TEXTURE;
    }
}
