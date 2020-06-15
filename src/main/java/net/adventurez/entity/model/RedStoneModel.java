package net.adventurez.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class RedStoneModel extends Model {

  private final ModelPart simpleblock = new ModelPart(32, 32, 0, 0);

  public RedStoneModel() {
    super(RenderLayer::getEntityTranslucent);
    this.simpleblock.setPivot(0.0F, 17.3846F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(1.5F, -2.3846F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(-0.5F, -5.3846F, -0.5F, 1.0F, 12.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(-0.5F, -4.3846F, 0.5F, 1.0F, 8.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(-0.5F, -3.3846F, 1.5F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(-0.5F, -3.3846F, -1.5F, 1.0F, 9.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(-0.5F, -2.3846F, -2.5F, 1.0F, 5.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(-1.5F, -3.3846F, -0.5F, 1.0F, 8.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(-2.5F, -2.3846F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(0.5F, -4.3846F, -0.5F, 1.0F, 9.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(0.5F, -1.3846F, -1.5F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(0.5F, -3.3846F, 0.5F, 1.0F, 6.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 2).addCuboid(-1.5F, -2.3846F, -1.5F, 1.0F, 5.0F, 1.0F, 0.0F);
    this.simpleblock.setTextureOffset(12, 4).addCuboid(-1.5F, -3.3846F, 0.5F, 1.0F, 6.0F, 1.0F, 0.0F);
  }

  @Override
  public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
      float blue, float alpha) {
    this.simpleblock.render(matrices, vertices, light, overlay, red, green, blue, alpha);
  }
}
