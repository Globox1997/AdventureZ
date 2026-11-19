package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Animal;

@Environment(EnvType.CLIENT)
public class SkunkModel<T extends Animal> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart leftFrontLeg;
    private final ModelPart leftBackLeg;
    private final ModelPart rightBackLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart tail;
    private final ModelPart head;

    public SkunkModel(ModelPart root) {
        super(true, 10.0F, 4.0F);
        this.body = root.getChild("body");
        this.leftFrontLeg = this.body.getChild("leftFrontLeg");
        this.leftBackLeg = this.body.getChild("leftBackLeg");
        this.rightBackLeg = this.body.getChild("rightBackLeg");
        this.rightFrontLeg = this.body.getChild("rightFrontLeg");
        this.tail = this.body.getChild("tail");
        this.head = root.getChild("head");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition main_r1 = body.addOrReplaceChild("main_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -7.5F, 7.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.0F, 0.5F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leftFrontLeg = body.addOrReplaceChild("leftFrontLeg", CubeListBuilder.create().texOffs(0, 33).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 2.0F, -5.0F));

        PartDefinition leftBackLeg = body.addOrReplaceChild("leftBackLeg", CubeListBuilder.create().texOffs(30, 27).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 1.0F, 3.0F));

        PartDefinition rightBackLeg = body.addOrReplaceChild("rightBackLeg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 1.0F, 3.0F));

        PartDefinition rightFrontLeg = body.addOrReplaceChild("rightFrontLeg", CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 2.0F, -5.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, -1.75F, 0.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(26, 0).addBox(-1.5F, 1.25F, 5.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 4.0F));

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 18).addBox(-3.0F, -2.0F, -5.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(8, 6).addBox(2.0F, -3.0F, -1.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(-4.0F, -3.0F, -1.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, 0.0F, -7.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.75F, -7.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
//        this.root. = 10f;
        this.head.xRot = headPitch * 0.010453292F;
        this.head.yRot = headYaw * 0.010453292F;

        this.leftFrontLeg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.rightFrontLeg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftBackLeg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.rightBackLeg.xRot = Mth.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }

}
