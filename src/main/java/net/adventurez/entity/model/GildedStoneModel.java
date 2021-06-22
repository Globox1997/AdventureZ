package net.adventurez.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class GildedStoneModel extends Model {

    private final ModelPart base;

    public GildedStoneModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.base = root.getChild("base");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("base",
                ModelPartBuilder.create().uv(12, 2).cuboid(1.5F, -3.0F, -0.5F, 1.0F, 5.0F, 1.0F).uv(12, 2).cuboid(-0.5F, -6.0F, -0.5F, 1.0F, 12.0F, 1.0F).uv(12, 2)
                        .cuboid(-0.5F, -5.0F, 0.5F, 1.0F, 8.0F, 1.0F).uv(12, 2).cuboid(-0.5F, -4.0F, 1.5F, 1.0F, 4.0F, 1.0F).uv(12, 2).cuboid(-0.5F, -4.0F, -1.5F, 1.0F, 9.0F, 1.0F).uv(12, 2)
                        .cuboid(-0.5F, -3.0F, -2.5F, 1.0F, 5.0F, 1.0F).uv(12, 2).cuboid(-1.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F).uv(12, 2).cuboid(-2.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F).uv(12, 2)
                        .cuboid(0.5F, -5.0F, -0.5F, 1.0F, 9.0F, 1.0F).uv(12, 2).cuboid(0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 1.0F).uv(12, 2).cuboid(0.5F, -4.0F, 0.5F, 1.0F, 6.0F, 1.0F).uv(12, 2)
                        .cuboid(-1.5F, -3.0F, -1.5F, 1.0F, 5.0F, 1.0F).uv(12, 4).cuboid(-1.5F, -4.0F, 0.5F, 1.0F, 6.0F, 1.0F),
                ModelTransform.pivot(0.0F, 18.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.base.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
