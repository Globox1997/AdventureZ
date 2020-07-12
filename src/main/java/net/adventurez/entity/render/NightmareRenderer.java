package net.adventurez.entity.render;

import net.adventurez.entity.NightmareEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.HorseBaseEntityRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class NightmareRenderer
    extends HorseBaseEntityRenderer<NightmareEntity, HorseEntityModel<NightmareEntity>> {
  private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/nightmare.png");

  public NightmareRenderer(EntityRenderDispatcher entityRenderDispatcher) {
    super(entityRenderDispatcher, new HorseEntityModel<>(0.0F), 1.1F);
  }

  @Override
  public Identifier getTexture(NightmareEntity nightmareEntity) {
    return TEXTURE;
  }
}
