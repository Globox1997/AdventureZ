package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

@Environment(EnvType.CLIENT)
public class VoidBulletModel<T extends Entity> extends ListModel<T> {
    private final ModelPart body;

    public VoidBulletModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(18, 20).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 1.0F).texOffs(12, 20).addBox(-1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 1.0F).texOffs(16, 5)
                        .addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 1.0F).texOffs(15, 15).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 1.0F).texOffs(16, 12).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 1.0F, 1.0F).texOffs(16, 10)
                        .addBox(-2.0F, 2.0F, -2.0F, 4.0F, 1.0F, 1.0F).texOffs(0, 16).addBox(-2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 2.0F).texOffs(0, 0).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 2.0F),
                PartPose.offset(0.0F, 21.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setupAnim(T bullet, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.body.yRot = headYaw * 0.017453292F;
        this.body.xRot = headPitch * 0.017453292F;
    }
}