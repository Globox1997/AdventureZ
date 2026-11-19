package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.BlazeGuardianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public class BlazeGuardianModel<T extends BlazeGuardianEntity> extends ListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart shieldOne;
    private final ModelPart shieldTwo;
    private final ModelPart shieldThree;
    private final ModelPart shieldFour;

    public BlazeGuardianModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.shieldOne = root.getChild("shieldOne");
        this.shieldTwo = root.getChild("shieldTwo");
        this.shieldThree = root.getChild("shieldThree");
        this.shieldFour = root.getChild("shieldFour");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 42).addBox(-2.0F, -12.0F, -2.0F, 4.0F, 22.0F, 4.0F), PartPose.offset(0.0F, 13.0F, 0.0F));
        modelPartData.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(38, 20).addBox(-3.0F, -9.0F, -3.0F, 6.0F, 7.0F, 6.0F).texOffs(38, 33).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 2.0F, 1.0F).texOffs(0, 40)
                        .addBox(3.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F).texOffs(10, 28).addBox(3.0F, -9.0F, -3.0F, 1.0F, 7.0F, 1.0F).texOffs(24, 7).addBox(-4.0F, -9.0F, -3.0F, 1.0F, 7.0F, 1.0F).texOffs(22, 46)
                        .addBox(3.0F, -9.0F, 2.0F, 1.0F, 7.0F, 2.0F).texOffs(0, 2).addBox(2.0F, -3.0F, 3.0F, 1.0F, 1.0F, 1.0F).texOffs(0, 0).addBox(-3.0F, -3.0F, 3.0F, 1.0F, 1.0F, 1.0F).texOffs(16, 46)
                        .addBox(-4.0F, -9.0F, 2.0F, 1.0F, 7.0F, 2.0F).texOffs(14, 0).addBox(-3.0F, -9.0F, 3.0F, 6.0F, 6.0F, 1.0F).texOffs(0, 28).addBox(3.0F, -9.0F, -2.0F, 1.0F, 4.0F, 4.0F).texOffs(0, 0)
                        .addBox(-4.0F, -9.0F, -2.0F, 1.0F, 4.0F, 4.0F).texOffs(0, 28).addBox(3.0F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F).texOffs(6, 2).addBox(-4.0F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F).texOffs(24, 15)
                        .addBox(3.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F).texOffs(6, 0).addBox(-4.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F).texOffs(8, 36).addBox(-4.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F).texOffs(32, 46)
                        .addBox(-3.0F, -6.0F, -4.0F, 1.0F, 4.0F, 1.0F).texOffs(28, 46).addBox(2.0F, -6.0F, -4.0F, 1.0F, 4.0F, 1.0F).texOffs(14, 7).addBox(-2.0F, -10.0F, -4.0F, 4.0F, 1.0F, 1.0F).texOffs(0, 36)
                        .addBox(-4.0F, -10.0F, -4.0F, 1.0F, 1.0F, 3.0F).texOffs(28, 24).addBox(3.0F, -10.0F, -4.0F, 1.0F, 1.0F, 3.0F).texOffs(33, 21).addBox(-4.0F, -10.0F, 1.0F, 1.0F, 1.0F, 3.0F).texOffs(28, 20)
                        .addBox(3.0F, -10.0F, 1.0F, 1.0F, 1.0F, 3.0F).texOffs(0, 8).addBox(-2.0F, -10.0F, 3.0F, 4.0F, 1.0F, 1.0F).texOffs(38, 22).addBox(-1.0F, -11.0F, -4.0F, 2.0F, 1.0F, 1.0F).texOffs(38, 20)
                        .addBox(-1.0F, -11.0F, 3.0F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 2.0F, 0.0F));
        modelPartData.addOrReplaceChild("shieldTwo", CubeListBuilder.create().texOffs(28, 0).addBox(-5.0F, -6.0F, -12.0F, 10.0F, 18.0F, 2.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
        modelPartData.addOrReplaceChild("shieldThree", CubeListBuilder.create().texOffs(38, 38).addBox(-5.0F, -6.0F, 10.0F, 10.0F, 18.0F, 2.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
        modelPartData.addOrReplaceChild("shieldFour", CubeListBuilder.create().texOffs(28, 0).addBox(-5.0F, -6.0F, -12.0F, 10.0F, 18.0F, 2.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
        modelPartData.addOrReplaceChild("shieldOne", CubeListBuilder.create().texOffs(28, 0).addBox(-5.0F, -6.0F, -12.0F, 10.0F, 18.0F, 2.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.head, this.body, this.shieldOne, this.shieldTwo, this.shieldThree, this.shieldFour);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float f = animationProgress / 6.2831853F;
        this.shieldOne.xRot = -0.3927F;
        this.shieldTwo.xRot = -0.3927F;
        this.shieldThree.xRot = 0.3927F;
        this.shieldFour.xRot = -0.3927F;
        this.shieldOne.yRot = f + 1.5708F;
        this.shieldTwo.yRot = f - 1.5708F;
        this.shieldThree.yRot = f;
        this.shieldFour.yRot = f;
        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;

        this.shieldThree.visible = entity.getEntityData().get(BlazeGuardianEntity.SHIELD_NORTH);
        this.shieldTwo.visible = entity.getEntityData().get(BlazeGuardianEntity.SHIELD_EAST);
        this.shieldFour.visible = entity.getEntityData().get(BlazeGuardianEntity.SHIELD_SOUTH);
        this.shieldOne.visible = entity.getEntityData().get(BlazeGuardianEntity.SHIELD_WEST);
    }

}
