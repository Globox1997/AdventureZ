package net.adventurez.entity.render;

import net.adventurez.entity.ThrownRockEntity;
import net.adventurez.entity.model.RockModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class ThrownRockRenderer extends EntityRenderer<ThrownRockEntity> {
   private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/thrown_rock.png");
   private final RockModel model = new RockModel();

   public ThrownRockRenderer(EntityRenderDispatcher entityRenderDispatcher) {
      super(entityRenderDispatcher);
   }

   @Override
   protected int getBlockLight(ThrownRockEntity ThrownRockEntity, BlockPos blockPos) {
      return 15;
   }

   @Override
   public void render(ThrownRockEntity thrownRockEntity, float f, float g, MatrixStack matrixStack,
         VertexConsumerProvider vertexConsumerProvider, int i) {

      matrixStack.push();
      VertexConsumer vertexConsumer = vertexConsumerProvider
            .getBuffer(this.model.getLayer(this.getTexture(thrownRockEntity)));
      model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.scale(1.5F, 1.5F, 1.5F);
      matrixStack.pop();
      super.render(thrownRockEntity, f, g, matrixStack, vertexConsumerProvider, i);

   }

   @Override
   public Identifier getTexture(ThrownRockEntity ThrownRockEntity) {
      return TEXTURE;
   }
}
