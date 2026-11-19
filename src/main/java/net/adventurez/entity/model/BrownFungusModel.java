package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.BrownFungusEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class BrownFungusModel<T extends BrownFungusEntity> extends ListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftFoot;
    private final ModelPart rightFoot;

    public BrownFungusModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftFoot = root.getChild("leftFoot");
        this.rightFoot = root.getChild("rightFoot");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -5.204F, -8.5F, 17.0F, 7.0F, 16.0F), PartPose.offset(0.0F, 10.0F, 0.0F));
        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 23).addBox(-4.0F, -4.0F, -3.0F, 8.0F, 7.0F, 5.0F), PartPose.offset(0.0F, 15.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(26, 26).addBox(-1.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), PartPose.offset(-2.0F, 18.0F, -0.5F));
        modelPartData.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), PartPose.offset(2.0F, 18.0F, -0.5F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.head, this.body, this.leftFoot, this.rightFoot);
    }

    @Override
    public void setupAnim(T fungus, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.xRot = -0.1745F;
        this.rightFoot.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.leftFoot.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;

        float k = Mth.sin(this.attackTime * 3.1415927F);
        if (k > 0) {
            this.head.xRot = -k * 1.2F;
        }

    }

}
