package net.adventurez.entity.render;

import net.adventurez.entity.NecromancerEntity;
import net.adventurez.entity.model.NecromancerModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class NecromancerRenderer extends MobEntityRenderer<NecromancerEntity, NecromancerModel<NecromancerEntity>> {
  private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/necromancer.png");

  public NecromancerRenderer(EntityRenderDispatcher entityRenderDispatcher) {
    super(entityRenderDispatcher, new NecromancerModel<>(), 0.7F);
  }

  @Override
  public Identifier getTexture(NecromancerEntity necromancerEntity) {
    return TEXTURE;
  }
}
