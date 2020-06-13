package net.adventurez.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class RockModel extends Model {

  private final ModelPart simpleblock = new ModelPart(64, 64, 0, 0);

  public RockModel() {
    super(RenderLayer::getEntitySolid);
    this.simpleblock.addCuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F);
    this.simpleblock.setPivot(0.0F, 8.0F, 0.0F);
  }

  @Override
  public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
      float blue, float alpha) {
    this.simpleblock.render(matrices, vertices, light, overlay, red, green, blue, alpha);
  }
}
