package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;
import net.adventurez.entity.CrystalBeetleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class CrystalBeetleModel<T extends CrystalBeetleEntity> extends ListModel<T> {
    private final ModelPart body;
    private final ModelPart right_front_leg;
    private final ModelPart right_back_leg;
    private final ModelPart left_back_leg;
    private final ModelPart left_front_leg;
    private final ModelPart body_spike;
    private final ModelPart head;

    public CrystalBeetleModel(ModelPart root) {
        this.body = root.getChild("body");
        this.right_front_leg = body.getChild("right_front_leg");
        this.right_back_leg = body.getChild("right_back_leg");
        this.left_back_leg = body.getChild("left_back_leg");
        this.left_front_leg = body.getChild("left_front_leg");
        this.body_spike = body.getChild("body_spike");
        this.head = body.getChild("head");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.05F, -5.0F, -5.5F, 16.0F, 10.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.05F, 19.0F, 3.5F));

        PartDefinition right_front_leg = body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(38, 39).addBox(-4.0F, -0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.05F, -1.5F, -3.5F));

        PartDefinition right_back_leg = body.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(38, 39).addBox(-4.0F, -0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.05F, -1.5F, 5.5F));

        PartDefinition left_back_leg = body.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(38, 39).mirror().addBox(0.0F, -0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(7.95F, -1.5F, 5.5F));

        PartDefinition left_front_leg = body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(38, 39).mirror().addBox(0.0F, -0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(7.95F, -1.5F, -3.5F));

        PartDefinition body_spike = body.addOrReplaceChild("body_spike", CubeListBuilder.create().texOffs(40, 23).addBox(-1.0F, -9.0F, 3.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.45F, -2.0F, -6.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 23).addBox(-5.6F, -4.0F, -8.0F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-1.0F, -4.0F, -16.0F, 3.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(22, 39).addBox(-1.0F, -10.0F, -16.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.45F, 1.0F, -5.5F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yRot = headYaw * 0.012453292F;
        this.head.xRot = headPitch * 0.014453292F;

        this.right_front_leg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.right_back_leg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;
        this.left_front_leg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.left_back_leg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;
    }

}
