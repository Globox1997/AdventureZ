// package net.adventurez.entity.render;

// import net.adventurez.entity.GryphonEntity;
// import net.adventurez.entity.model.GryphonModel;
// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.minecraft.client.render.entity.EntityRenderDispatcher;
// import net.minecraft.client.render.entity.MobEntityRenderer;
// import net.minecraft.util.Identifier;

// @Environment(EnvType.CLIENT)
// public class GryphonRenderer extends MobEntityRenderer<GryphonEntity, GryphonModel> {
//   private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/gryphon.png");

//   public GryphonRenderer(EntityRenderDispatcher entityRenderDispatcher) {
//     super(entityRenderDispatcher, new GryphonModel(), 0.5F);
//   }

//   @Override
//   public Identifier getTexture(GryphonEntity gryphonEntity) {
//     return TEXTURE;
//   }
// }