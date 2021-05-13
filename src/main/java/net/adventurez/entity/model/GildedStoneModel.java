package net.adventurez.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class GildedStoneModel extends Model {

  private final ModelPart modelPart = new ModelPart(32, 32, 0, 0);

  public GildedStoneModel() {
    super(RenderLayer::getEntitySolid);
    this.modelPart.setPivot(0.0F, 18.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(1.5F, -3.0F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(-0.5F, -6.0F, -0.5F, 1.0F, 12.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(-0.5F, -5.0F, 0.5F, 1.0F, 8.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(-0.5F, -4.0F, 1.5F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(-0.5F, -4.0F, -1.5F, 1.0F, 9.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(-0.5F, -3.0F, -2.5F, 1.0F, 5.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(-1.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(-2.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(0.5F, -5.0F, -0.5F, 1.0F, 9.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(0.5F, -4.0F, 0.5F, 1.0F, 6.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 2).addCuboid(-1.5F, -3.0F, -1.5F, 1.0F, 5.0F, 1.0F, 0.0F);
    this.modelPart.setTextureOffset(12, 4).addCuboid(-1.5F, -4.0F, 0.5F, 1.0F, 6.0F, 1.0F, 0.0F);
  }

  @Override
  public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
      float blue, float alpha) {
    this.modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha);
  }
}
