package net.adventurez.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

@Environment(EnvType.CLIENT)
public class GildedStoneModel extends Model<Unit> {

    private final ModelPart base;

    public GildedStoneModel(ModelPart root) {
        super(root, RenderTypes::entitySolid);
        this.base = root.getChild("base");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(12, 2).addBox(1.5F, -3.0F, -0.5F, 1.0F, 5.0F, 1.0F).texOffs(12, 2).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 12.0F, 1.0F).texOffs(12, 2)
                        .addBox(-0.5F, -5.0F, 0.5F, 1.0F, 8.0F, 1.0F).texOffs(12, 2).addBox(-0.5F, -4.0F, 1.5F, 1.0F, 4.0F, 1.0F).texOffs(12, 2).addBox(-0.5F, -4.0F, -1.5F, 1.0F, 9.0F, 1.0F).texOffs(12, 2)
                        .addBox(-0.5F, -3.0F, -2.5F, 1.0F, 5.0F, 1.0F).texOffs(12, 2).addBox(-1.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F).texOffs(12, 2).addBox(-2.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F).texOffs(12, 2)
                        .addBox(0.5F, -5.0F, -0.5F, 1.0F, 9.0F, 1.0F).texOffs(12, 2).addBox(0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 1.0F).texOffs(12, 2).addBox(0.5F, -4.0F, 0.5F, 1.0F, 6.0F, 1.0F).texOffs(12, 2)
                        .addBox(-1.5F, -3.0F, -1.5F, 1.0F, 5.0F, 1.0F).texOffs(12, 4).addBox(-1.5F, -4.0F, 0.5F, 1.0F, 6.0F, 1.0F),
                PartPose.offset(0.0F, 18.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }


}
