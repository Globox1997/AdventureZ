package net.adventurez.entity.render;

import net.adventurez.entity.SmallStoneGolemEntity;
import net.adventurez.entity.model.SmallStoneGolemModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SmallStoneGolemRenderer extends MobEntityRenderer<SmallStoneGolemEntity, SmallStoneGolemModel<SmallStoneGolemEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/small_stone_golem.png");

    public SmallStoneGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new SmallStoneGolemModel<>(context.getPart(RenderInit.SMALL_STONE_GOLEM_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTexture(SmallStoneGolemEntity stoneGolem) {
        return TEXTURE;
    }
}
