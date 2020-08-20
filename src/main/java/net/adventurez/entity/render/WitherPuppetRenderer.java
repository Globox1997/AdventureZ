// package net.adventurez.entity.render;

// import net.adventurez.entity.WitherPuppetEntity;
// import net.adventurez.entity.model.WitherPuppetModel;
// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.minecraft.client.render.entity.EntityRenderDispatcher;
// import net.minecraft.client.render.entity.MobEntityRenderer;
// import net.minecraft.util.Identifier;

// @Environment(EnvType.CLIENT)
// public class WitherPuppetRenderer extends MobEntityRenderer<WitherPuppetEntity, WitherPuppetModel<WitherPuppetEntity>> {
//   private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/wither_puppet.png");

//   public WitherPuppetRenderer(EntityRenderDispatcher entityRenderDispatcher) {
//     super(entityRenderDispatcher, new WitherPuppetModel<>(), 0.7F);
//   }

//   @Override
//   public Identifier getTexture(WitherPuppetEntity necromancerEntity) {
//     return TEXTURE;
//   }
// }

package net.adventurez.entity.render;

import net.adventurez.entity.WitherPuppetEntity;
import net.adventurez.entity.model.WitherPuppetModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WitherPuppetRenderer
    extends BipedEntityRenderer<WitherPuppetEntity, WitherPuppetModel<WitherPuppetEntity>> {
  private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/wither_puppet.png");

  public WitherPuppetRenderer(EntityRenderDispatcher entityRenderDispatcher) {
    super(entityRenderDispatcher, new WitherPuppetModel<>(), 0.5F);
  }

  @Override
  public Identifier getTexture(WitherPuppetEntity witherPuppetEntity) {
    return TEXTURE;
  }

}